package goedegep.media.mediadb.app.guifx;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.media.mediadb.app.MediaDbAppError;
import goedegep.media.mediadb.app.MediaDbAppErrorInfo;
import goedegep.media.mediadb.app.MediaDbAppUtil;
import goedegep.media.mediadb.app.MediaDbErrorInfo;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.AlbumFolder;
import goedegep.media.musicfolder.AlbumOnDiscInfo;
import goedegep.media.musicfolder.MusicFolderStructureErrorInfo;
import goedegep.media.musicfolder.MusicFolderUtil;
import goedegep.media.musicfolder.TrackFile;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;
import goedegep.util.sax.ParseException;
import goedegep.util.text.FuzzyStringCompare;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
* This class provides a window which lists the problems in media information.
* 
*/
public class MediaDbProblemsWindow extends JfxStage {
private static final Logger LOGGER = Logger.getLogger(MediaDbProblemsWindow.class.getName());

private static final String WINDOW_TITLE = "Media Database problems";
private static final String NEWLINE = System.getProperty("line.separator");
private static final FlexDateFormat FDF = new FlexDateFormat(true, true);

private static final Pattern ALBUM_FOLDER_NAME_PATTERN_MISSING_HYPHEN_AFTER_DATE = Pattern.compile("^(\\d{4})(-\\d{2})?(-\\d{2})? (.*) - (.*)");  // Album date Artist - Album title  TODO add disc nr e.g. [cd 1]
private static final int MAX_ERRORS_SHOWN = 18;

/**
 * The media database, used to propose solutions for problems.
 */
private MediaDb mediaDb;


  /**
   * Constructor.
   * 
   * @param customization GUI customization
   * @param parent owner of this window
   * @param mediaDb media database, used to propose solutions for problems
   * @param errors the problems to be listed
   */
  public MediaDbProblemsWindow(CustomizationFx customization, MediaDb mediaDb, List<Object> errors) {
    super(customization, WINDOW_TITLE);

    this.mediaDb = mediaDb;

    initGUI(errors);
  }

  /**
   * Create the GUI.
   * 
   * The problems are listed as a list of error panels, using a VBox.
   * 
   * @param errors the problems to be listed
   */
  private void initGUI(List<Object> errors) {

    VBox errorListPanel = componentFactory.createVBox(12.0);

    int errorCount = 0;
    for (Object error: errors) {
//      if (errorCount <= 0) {
//        LOGGER.severe("Skipping error: " + errorCount);
//        errorCount++;
//        continue;
//      }
      ErrorPanel errorPanel = new ErrorPanel(componentFactory);
      fillErrorPanel(errorPanel, error);
      errorListPanel.getChildren().add(errorPanel);

      if (errorCount++ == MAX_ERRORS_SHOWN) {
        LOGGER.severe("ERROR LIST TRUNCATED");
        break;
      }
    }

    Scene scene = new Scene(errorListPanel, 1200, 950);
    setScene(scene);
    show();
  }

  /**
   * Fill a panel to report and possibly fix a problem.
   * <p>
   * The panel shall at least describe the problem.
   * If possible it provides a fix for the problem, or helps fixing the problem.
   * 
   * @param the panel to be filled
   * @param error the problem to be reported
   */
  private void fillErrorPanel(ErrorPanel errorPanel, Object error) {
    if (error instanceof ParseException) {
      ParseException parseException = (ParseException) error;
      fillParseExceptionPanel(errorPanel, parseException);
    } else if (error instanceof MediaDbErrorInfo) {
      MediaDbErrorInfo mediaDbErrorInfo = (MediaDbErrorInfo) error;
      fillMediaDbErrorInfoPanel(errorPanel, mediaDbErrorInfo);
    } else if (error instanceof MusicFolderStructureErrorInfo) {
      MusicFolderStructureErrorInfo musicFolderStructureErrorInfo = (MusicFolderStructureErrorInfo) error;
      fillMusicFolderStructureErrorInfoPanel(errorPanel, musicFolderStructureErrorInfo);
    } else if (error instanceof MediaDbAppErrorInfo) {
      MediaDbAppErrorInfo mediaDbAppErrorInfo = (MediaDbAppErrorInfo) error;
      fillErrorPanelForMediaDbAppErrorInfo(errorPanel, mediaDbAppErrorInfo);
    } else {
      throw new IllegalArgumentException("Unknown error class: " + error.getClass().getName());
    }
  }

  /**
   * Fill a panel to report and possibly fix a <code>ParseException</code>.
   * <p>
   * The panel shows the problem.
   * If possible it provides a fix for the problem, or helps fixing the problem.
   * @param the panel to be filled
   * @param error the problem to be reported
   */
  private void fillParseExceptionPanel(ErrorPanel errorPanel, ParseException parseException) {
    errorPanel.getTextArea().setText(
        "Fout in bestand '" + parseException.getFileName() + "'" + NEWLINE +
        "Regel: " + parseException.getLineNumber() + ", kolom:; " + parseException.getColumnNumber() + NEWLINE +
        parseException.getMessage()
        );

    // Button to open the file with the problem in Notapad++.
    Button editButton = componentFactory.createButton("Open bestand in Notepad++", "opent " + parseException.getFileName() + " met Notepad++");
    editButton.setOnAction((e) -> {
      List<String> commandArguments = new ArrayList<>();

      commandArguments.add("C:\\Program Files\\Notepad++\\Notepad++.exe");
      commandArguments.add(parseException.getFileName());
      commandArguments.add("-n " + parseException.getLineNumber());
      commandArguments.add("-c " + parseException.getColumnNumber());

      try {
        new ProcessBuilder(commandArguments).start();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });
    errorPanel.getButtonsBox().getChildren().add(editButton);
  }

  private void fillMediaDbErrorInfoPanel(ErrorPanel errorPanel, MediaDbErrorInfo mediaDbErrorInfo) {
    // Report the error in a JTextArea.
    StringBuilder buf = new StringBuilder();
    Album album;
    Artist artist;

    switch (mediaDbErrorInfo.getErrorCode()) {
    case ALBUM_WITHOUT_ARTIST:
      buf.append("Geen artiest ingevuld voor album ");
      album = mediaDbErrorInfo.getAlbum();
      if (album.isSetTitle()) {
        buf.append(album.getTitle());
      } else {
        buf.append(album.toString());
      }
      break;

    case ALBUM_WITHOUT_TITLE:
      buf.append("Geen titel ingevuld voor album ");
      album = mediaDbErrorInfo.getAlbum();
      buf.append(album.toString());
      break;

    case BONUS_TRACK_IN_TITLE:
      buf.append("Track title contains 'bonus track'");
      album = mediaDbErrorInfo.getAlbum();
      if (album != null) {
        buf.append(NEWLINE).append("Album: ").append(album.getArtistAndTitle());
      }
      TrackReference trackReference = mediaDbErrorInfo.getTrackReference();
      if (trackReference != null) {
        buf.append(NEWLINE).append("Track: ").append(trackReference.getTrack().getTitle());
      }
      break;
      
    case OBSOLETE_TRACK:
      buf.append("Obsolete track (track that isn't referred to): ");
      Track track = mediaDbErrorInfo.getTrack();
      artist = track.getArtist();
      buf.append(artist != null ? artist.getName() : "<no-artist>").append(" - ").append(track.getTitle());

      Button removeObsoleteTrack = componentFactory.createButton("Remove obsolete track", "Remove the obsolete track from the data base");
      removeObsoleteTrack.setOnAction((e) -> mediaDb.getTracks().remove(track));
      errorPanel.getButtonsBox().getChildren().add(removeObsoleteTrack);
      break;
      
    case ARTIST_NOT_REFERRED_TO:
      fillMediaDbErrorInfoPanelForArtistNotReferredTo(errorPanel, mediaDbErrorInfo, buf);
      break;

    default:
      throw new IllegalArgumentException("Unknown Error Code: " + mediaDbErrorInfo.getErrorCode());
    }
    errorPanel.getTextArea().setText(buf.toString());
  }
  
  private void fillMediaDbErrorInfoPanelForArtistNotReferredTo(ErrorPanel errorPanel, MediaDbErrorInfo mediaDbErrorInfo, StringBuilder buf) {
    buf.append("Artist that isn't referred to: ");
    Artist artist = mediaDbErrorInfo.getArtist();
    buf.append(artist.getName()).append(NEWLINE);
    
    // In case of this error, there are no references to the artist, except for a container artist reference.
    // Check for these references and report and remove them.
    
    // Get the object to be deleted.
    EObject eObjectToBeDeleted = mediaDbErrorInfo.getArtist();
    
    // The object to be deleted is referenced by a containment reference, check whether there are other references to this object.
    LOGGER.info("Containment");
    ResourceSet resourceSet = eObjectToBeDeleted.eResource().getResourceSet();
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObjectToBeDeleted, resourceSet);

    if (settings.size() != 0) {
      buf.append("The following artists refer to this artist as container artist:").append(NEWLINE);
      for (EStructuralFeature.Setting setting: settings) {
        Artist referringArtist = (Artist) setting.getEObject();
        
        buf.append(referringArtist.getName()).append(NEWLINE);
      }


    } else {
      buf.append("There are also no container artist references to this artist");
    }

    Button removeArtistNotReferredTo = componentFactory.createButton("Remove artist and references", "Remove this artist (and the references to this artist) from the data base");
    removeArtistNotReferredTo.setOnAction((e) -> {
      // Delete the references
      for (EStructuralFeature.Setting setting: settings) {
        Artist referringArtist = (Artist) setting.getEObject();
        LOGGER.severe("Clearing container artist for: " + referringArtist.getName());
        referringArtist.setContainerArtist(null);
      }
      
      mediaDb.getArtists().remove(artist);
    });
    errorPanel.getButtonsBox().getChildren().add(removeArtistNotReferredTo);
          
  }

  private void fillMusicFolderStructureErrorInfoPanel(ErrorPanel errorPanel, MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    switch (musicFolderStructureErrorInfo.getErrorCode()) {
    case FOUND_NON_AUDIO_FILE:
      fillMusicFolderStructureErrorInfoPanelForFoundNonAudioFile(errorPanel, musicFolderStructureErrorInfo);
      break;

    case WRONG_FOLDER_NAME:
      fillMusicFolderStructureErrorInfoPanelForWrongFolderName(errorPanel, musicFolderStructureErrorInfo);
      break;

    case WRONG_TRACK_NAME_FORMAT:
      fillMusicFolderStructureErrorInfoPanelForWrongTrackNameFormat(errorPanel, musicFolderStructureErrorInfo);
      break;
      
    case UNEXPECTED_FILE:
      fillMusicFolderStructureErrorInfoPanelForFileFoundInArtistFolder(errorPanel, musicFolderStructureErrorInfo);

    default:
      throw new IllegalArgumentException("Unknown Error Code: " + musicFolderStructureErrorInfo.getErrorCode());
    }
  }

  private void fillMusicFolderStructureErrorInfoPanelForFoundNonAudioFile(ErrorPanel errorPanel, MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    StringBuilder buf = new StringBuilder();

    buf.append("Bestand gevonden wat geen muziek bestand is. Bestand: '" + musicFolderStructureErrorInfo.getFile() + "', in map '" + musicFolderStructureErrorInfo.getFolder() + "'");
    errorPanel.getTextArea().setText(buf.toString());


    Button openLocation = componentFactory.createButton("Open locatie", "Opent een Windows explorer window om het probleem op the lossen");
    openLocation.setOnAction((e) -> {
      Path path = musicFolderStructureErrorInfo.getFolder();
      try {
        Runtime.getRuntime().exec("explorer.exe /select,\"" + path.toString() + "\"");
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    });
    errorPanel.getButtonsBox().getChildren().add(openLocation);
  }

  private void fillMusicFolderStructureErrorInfoPanelForWrongFolderName(ErrorPanel errorPanel, MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    StringBuilder buf = new StringBuilder();

    buf.append("Wrong name for album folder: ");
    buf.append(musicFolderStructureErrorInfo.getFolder());
    buf.append(NEWLINE);
    errorPanel.getTextArea().setText(buf.toString());


    Button openLocation = componentFactory.createButton("Open location", "Opens a Windows explorer window to solve the problem");
    openLocation.setOnAction((e) -> {
      Path path = musicFolderStructureErrorInfo.getFolder();
      try {
        Runtime.getRuntime().exec("explorer.exe /select,\"" + path.toString() + "\"");
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    });
    errorPanel.getButtonsBox().getChildren().add(openLocation);

    List<String> proposedAlbumFolderNames = proposeAlbumFolderNames(musicFolderStructureErrorInfo);
    if (!proposedAlbumFolderNames.isEmpty()) {
      ComboBox<String> proposedFolderNamesComboBox = componentFactory.createComboBox(proposedAlbumFolderNames);
      proposedFolderNamesComboBox.getSelectionModel().select(0);
      
      Button renameButton = componentFactory.createButton("Change folder name to:", "Rename the folder");
      renameButton.setOnAction((e) -> {
        Path currentAlbumFolder = musicFolderStructureErrorInfo.getFolder();
        Path newAlbumFolder = currentAlbumFolder.getParent().resolve(proposedFolderNamesComboBox.getValue());
        try {
          LOGGER.severe("Renaming: " + currentAlbumFolder.toString() + ", to: " + newAlbumFolder.toString());
          Files.move(currentAlbumFolder, newAlbumFolder);
          renameButton.setDisable(true);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
      errorPanel.getButtonsBox().getChildren().add(renameButton);
      errorPanel.getButtonsBox().getChildren().add(proposedFolderNamesComboBox);
    }
  }


  /**
   * Fill an <code>errorPanel</code> for a wrong format of the filename of a track.
   * 
   * @param errorPanel the error panel to be filled.
   * @param musicFolderStructureErrorInfo the error information.
   */
  private void fillMusicFolderStructureErrorInfoPanelForWrongTrackNameFormat(ErrorPanel errorPanel, MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    // Report the error in a TextArea.
    StringBuilder buf = new StringBuilder();
    buf.append("Wrong format of a filename of a track: ");
    buf.append(musicFolderStructureErrorInfo.getFile());
    buf.append(NEWLINE);
    buf.append("Album folder: ");
    buf.append(musicFolderStructureErrorInfo.getFolder());
//    TextArea textArea = componentFactory.createTextArea(buf.toString());
    errorPanel.getTextArea().setText(buf.toString());

    // JButton to open the folder with the track.
    Button openLocation = componentFactory.createButton("Open location", "Opens a Windows explorer window so you can solve the problem");
    openLocation.setOnAction((e) -> {
      Path albumFolder = musicFolderStructureErrorInfo.getFolder();
      LOGGER.severe("albumFolder=" + albumFolder.toAbsolutePath().toString());
      try {
        Runtime.getRuntime().exec("explorer.exe /select,\"" + albumFolder.toString() + "\"");
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    });
    errorPanel.getButtonsBox().getChildren().add(openLocation);

    // JButton to automatically rename the file
    String proposedTrackName = proposeTrackName(musicFolderStructureErrorInfo);
    if (proposedTrackName != null) {
      Button renameButton = componentFactory.createButton("Verander naam in: " + proposedTrackName,
          "hernoem het nummer");
      renameButton.setOnAction((e) -> {
        Path albumFolder = musicFolderStructureErrorInfo.getFolder();
        Path currentTrackPath = Paths.get(albumFolder.toAbsolutePath().toString(), musicFolderStructureErrorInfo.getFile());
        Path newTrackPath = Paths.get(albumFolder.toAbsolutePath().toString(), proposedTrackName);
        try {
          Files.move(currentTrackPath, newTrackPath);
          renameButton.setDisable(true);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        LOGGER.severe("Renaming: " + currentTrackPath.toAbsolutePath().toString() + ", to: " + newTrackPath.toAbsolutePath().toString());
      });
      errorPanel.getButtonsBox().getChildren().add(renameButton);
    }
  }

  /**
   * Fill an <code>errorPanel</code> for a wrong format of the filename of a track.
   * 
   * @param errorPanel the error panel to be filled.
   * @param musicFolderStructureErrorInfo the error information.
   */
  private void fillMusicFolderStructureErrorInfoPanelForFileFoundInArtistFolder(ErrorPanel errorPanel, MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    // Report the error in a TextArea.
    StringBuilder buf = new StringBuilder();
    buf.append("Unexpected file found: ");
    buf.append(musicFolderStructureErrorInfo.getFolder());
    buf.append(NEWLINE);
    buf.append("File: ");
    buf.append(musicFolderStructureErrorInfo.getFile());
    errorPanel.getTextArea().setText(buf.toString());

    // JButton to open the folder with the file.
    Button openLocationButton = componentFactory.createButton("Open location", "Opens a Windows Explorer window to solve the problem");
    openLocationButton.setOnAction((e) -> {
      Path albumFolder = musicFolderStructureErrorInfo.getFolder();
      LOGGER.severe("albumFolder=" + albumFolder.toAbsolutePath().toString());
      try {
        Runtime.getRuntime().exec("explorer.exe /select,\"" + albumFolder.toString() + "\"");
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    });
    errorPanel.getButtonsBox().getChildren().add(openLocationButton);

    // JButton to automatically delete the file
    Button deleteButton = componentFactory.createButton("Delete file: " + musicFolderStructureErrorInfo.getFile(), "Delete the file");
    deleteButton.setOnAction((e) -> {
//      try {
        Path path = musicFolderStructureErrorInfo.getFolder().resolve(musicFolderStructureErrorInfo.getFile());
        LOGGER.severe("Deleting: " + path.toAbsolutePath().toString());
//      TODO   Files.delete(path);
        deleteButton.setDisable(true);
//      } catch (IOException e1) {
//        e1.printStackTrace();
//      }
    });
    errorPanel.getButtonsBox().getChildren().add(deleteButton);
  }

  /**
   * Propose folder names for an incorrect album folder name.
   * 
   * @param musicFolderStructureErrorInfo
   * @return
   */
  public List<String> proposeAlbumFolderNames(MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    /*
     * There are two ways to come to a proposal:
     * - find problems in the name, like a missing space before or after a hyphen.
     * - try to determine the album and generate the name for this album.
     */
    List<String> proposedNames = new ArrayList<>();
    
    addProposedNameForMissingHyphenAfterDate(musicFolderStructureErrorInfo.getFolder(), proposedNames);
    
    List<Album> candidateAlbums = null;
    Path albumFolder = musicFolderStructureErrorInfo.getFolder();
    String albumFolderName = albumFolder.getFileName().toString();
    LOGGER.severe("=> albumFolderName=" + albumFolderName);
    String[] albumFolderNameParts = albumFolderName.split("-");
    if (albumFolderNameParts.length > 0) {
      String issueDateString = albumFolderNameParts[0].trim();
      try {
        FlexDate issueDate = FDF.parse(issueDateString);

        if (albumFolderNameParts.length > 1) {
          String albumTitle = albumFolderNameParts[1].trim();
          candidateAlbums = mediaDb.getAlbums(issueDate, null, albumTitle);
        } else {
          candidateAlbums = mediaDb.getAlbums(issueDate, null, null);
        }
      } catch (java.text.ParseException e) {
        e.printStackTrace();
      }
    }

    if (candidateAlbums != null) {
      List<Album> candidateAlbumsIHave = new ArrayList<>();
      for (Album album: candidateAlbums) {
        if (album.isSetMyInfo()) {
          Set<MediumInfo> haveOnMediumTypes = MediaDbUtil.haveAlbumOnMediumTypes(album);
          for (MediumInfo mediumInfo: haveOnMediumTypes) {
            LOGGER.severe("MediumInfo: " + mediumInfo.toString());
            if (mediumInfo.isSetMediumType()  &&  mediumInfo.getMediumType().equals(MediumType.HARDDISK)) {
              candidateAlbumsIHave.add(album);
            }
          }
        }
      }

      LOGGER.severe("Number of candidates: " + candidateAlbumsIHave.size());
      for (Album albumIHave: candidateAlbumsIHave) {
        LOGGER.severe("Album: " + albumIHave.toString());
      }

      if (candidateAlbumsIHave.size() == 1) {
        Album proposedAlbum = candidateAlbumsIHave.get(0);
        StringBuilder buf = new StringBuilder();
        buf.append(FDF.format(proposedAlbum.getReleaseDate()));
        buf.append(" - ");
        buf.append(proposedAlbum.getArtist().getName());
        buf.append(" - ");
        buf.append(proposedAlbum.getTitle());

        proposedNames.add(buf.toString());
      }
    }

    LOGGER.severe("<=");
    return proposedNames;
  }

  /**
   * Add a proposed name for a possible hyphen missing after the release date.
   * <p>
   * If the album folder name seems to be missing a hyphen between the release date and the artist,
   * a proposed name is added to the <code>proposednames</code> with the hyphen added.
   * 
   * @param folder an incorrect folder name.
   * @param proposedNames a list of proposed names, to which a name may be added.
   */
  private void addProposedNameForMissingHyphenAfterDate(Path folder, List<String> proposedNames) {
    String folderName = folder.getFileName().toString();
    
    LOGGER.severe("folderName: " + folderName);
    Matcher matcher = ALBUM_FOLDER_NAME_PATTERN_MISSING_HYPHEN_AFTER_DATE.matcher(folderName);
    if (matcher.matches()) {
      for (int i = 0; i <= matcher.groupCount(); i++) {
        LOGGER.severe("Group: " + i + ", \"" + matcher.group(i) + "\"");
      }
      StringBuilder buf = new StringBuilder();
      String datePart = matcher.group(1);  // year
      buf.append(datePart);
      
      datePart = matcher.group(2);  // -month
      if (datePart != null) {
        buf.append(datePart);
      }
      
      datePart = matcher.group(3);  // -day
      if (datePart != null) {
        buf.append(datePart);
      }
      
      buf.append(" - ");
      
      String artist = matcher.group(4);
      LOGGER.severe("artist:" + artist );
      buf.append(artist);
      
      buf.append(" - ");
      
      String title = matcher.group(5);
      LOGGER.fine("title:" + title );
      buf.append(title);

      proposedNames.add(buf.toString());
    }
  }

  /**
   * Proposes a corrected file name for a music track.
   * 
   * The error information is used to try to obtain the right album and track from the <mediaDb>.
   * For this track the correct name is generated, using the extension from the incorrect name.
   * 
   * @param musicFolderStructureErrorInfo the error information
   * @return a proposed corrected track name
   */
  private String proposeTrackName(MusicFolderStructureErrorInfo musicFolderStructureErrorInfo) {
    /*
     * Als het album bekend is, is het track nummer voldoende om de juiste naam te genereren.
     */
    List<Album> candidateAlbums = null;
    Path albumFolder = musicFolderStructureErrorInfo.getFolder();
    String albumFolderName = albumFolder.getFileName().toString();
    LOGGER.severe("=> albumFolderName=" + albumFolderName);
    String[] albumFolderNameParts = albumFolderName.split("-");
    if (albumFolderNameParts.length > 0) {
      String issueDateString = albumFolderNameParts[0].trim();
      try {
        FlexDate issueDate = FDF.parse(issueDateString);

        if (albumFolderNameParts.length > 1) {
          String artistName = albumFolderNameParts[1].trim();
          Artist artist = mediaDb.getArtist(artistName);
          if (albumFolderNameParts.length > 2) {
            String albumTitle = albumFolderNameParts[2].trim();
            candidateAlbums = getAlbums(issueDate, artist, albumTitle);
          } else {
            candidateAlbums = getAlbums(issueDate, artist, null);
          }
        } else {
          candidateAlbums = getAlbums(issueDate, null, null);
        }
      } catch (java.text.ParseException e) {
        e.printStackTrace();
      }
    }

    if (candidateAlbums.size() > 0) {
      LOGGER.severe("candidateAlbums available");
      List<Album> candidateAlbumsIHave = new ArrayList<>();
      for (Album album: candidateAlbums) {
        if (album.isSetMyInfo()) {
          Set<MediumInfo> iHaveOnMedia = MediaDbUtil.haveAlbumOnMediumTypes(album);
          LOGGER.severe("MediumInfo: " + iHaveOnMedia.toString());
          for (MediumInfo mediumInfo: iHaveOnMedia) {
            if (mediumInfo.isSetMediumType()  &&  mediumInfo.getMediumType().equals(MediumType.HARDDISK)) {
              candidateAlbumsIHave.add(album);
            }
          }
        }
      }

      LOGGER.severe("Number of candidates: " + candidateAlbumsIHave.size());
      for (Album albumIHave: candidateAlbumsIHave) {
        LOGGER.severe("Album: " + albumIHave.toString());
      }

      if (candidateAlbumsIHave.size() == 1) {
        Album proposedAlbum = candidateAlbumsIHave.get(0);
        String trackName = musicFolderStructureErrorInfo.getFile();
        // assume extension is correct.
        String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(trackName);

        // parse 1 or 2 digits tracknumber
        int trackNumber = 0;
        for (int i = 0; i < 2; i++) {
          if (i >= trackName.length() - 1) {
            break;
          }

          char c = trackName.charAt(i);
          if (!Character.isDigit(c)) {
            break;
          }

          trackNumber = 10 * trackNumber + Character.getNumericValue(c);
        }
        LOGGER.severe("trackNr=" + trackNumber);

        if (trackNumber > 0) {
          List<Disc> discs = proposedAlbum.getDiscs();
          if (discs.size() == 1) {
            Disc disc = discs.get(0);
            List<TrackReference> tracks = disc.getTrackReferences();
            if (tracks.size() >= trackNumber) {
              TrackReference trackReference = tracks.get(trackNumber - 1);
              Track track = trackReference.getTrack();
              LOGGER.severe("track=" + track.toString());
              String trackTitle = track.getTitle();

              StringBuilder buf = new StringBuilder();
              buf.append(String.format("%02d", trackNumber));
              buf.append(" - ");
              buf.append(trackTitle);
              buf.append(".");
              buf.append(fileNameAndExtension[1]);

              LOGGER.severe("<= " + buf.toString());
              return buf.toString();
            }
          }
        }
      }
    }

    LOGGER.severe("<=");
    return null;
  }

  private void fillErrorPanelForMediaDbAppErrorInfo(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    switch (mediaDbAppErrorInfo.getErrorCode()) {
    case ALBUM_ON_DISC_NOT_FOUND_IN_MEDIADB:
      fillErrorPanelFor_ALBUM_ON_DISC_NOT_FOUND_IN_MEDIADB(errorPanel, mediaDbAppErrorInfo);
      break;

    case ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC:
      fillErrorPanelFor_ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC(errorPanel, mediaDbAppErrorInfo);
      break;

    case NO_MY_INFO_FOR_ALBUM:
      fillErrorPanelFor_NO_MY_INFO_FOR_ALBUM(errorPanel, mediaDbAppErrorInfo);
      break;

    case NO_RELEASE_DATE_FOR_ALBUM:
      fillErrorPanelFor_NO_RELEASE_DATE_FOR_ALBUM(errorPanel, mediaDbAppErrorInfo);
      break;

    case TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC:
      fillErrorPanelFor_TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC(errorPanel, mediaDbAppErrorInfo);
      break;

    case TRACK_ON_DISC_NOT_FOUND_IN_MEDIADB:
      fillErrorPanelFor_TRACK_ON_DISC_NOT_FOUND_IN_MEDIADB(errorPanel, mediaDbAppErrorInfo);
      break;
      
    case ALBUM_IN_MEDIADB_FOUND_MORE_THAN_ONCE_ON_DISC:
      errorPanel.getTextArea().setText("ToDo: " + MediaDbAppError.ALBUM_IN_MEDIADB_FOUND_MORE_THAN_ONCE_ON_DISC);      
      break;
      
    case TRACKS_WITH_DIFFERENT_EXTENSIONS:
      errorPanel.getTextArea().setText("ToDo: " + MediaDbAppError.TRACKS_WITH_DIFFERENT_EXTENSIONS);      
      break;
      
    case WRONG_NUMBER_OF_TRACKS:
      errorPanel.getTextArea().setText("ToDo: " + MediaDbAppError.WRONG_NUMBER_OF_TRACKS);      
      break;
      
    default:
      break;

    }
  }

  private void fillErrorPanelFor_ALBUM_ON_DISC_NOT_FOUND_IN_MEDIADB(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    StringBuilder buf = new StringBuilder();
    AlbumOnDiscInfo albumOnDiscInfo = mediaDbAppErrorInfo.getAlbumOnDiscInfo();

    buf.append("Found an album on disc that is not found in the media database:");
    buf.append(NEWLINE);

    buf.append("Issue date: ");
    if (albumOnDiscInfo.getIssueDate() != null) {
      buf.append(FDF.format(albumOnDiscInfo.getIssueDate()));
    }
    buf.append(NEWLINE);

    buf.append("Artist: ");
    if (albumOnDiscInfo.getArtist() != null) {
      buf.append(albumOnDiscInfo.getArtist());
    }
    buf.append(NEWLINE);

    buf.append("Title: ");
    if (albumOnDiscInfo.getTitle() != null) {
      buf.append(albumOnDiscInfo.getTitle());
    }
    buf.append(NEWLINE);

    buf.append("Folder: ");
    if (albumOnDiscInfo.getAlbumFolderName() != null) {
      buf.append(albumOnDiscInfo.getAlbumFolderName());
    }
    buf.append(NEWLINE);

    errorPanel.getTextArea().setText(buf.toString());
  }

  private void fillErrorPanelFor_ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    StringBuilder buf = new StringBuilder();
    Album album = mediaDbAppErrorInfo.getAlbum();

    buf.append("Album disc in media database not found in Music Folder:");
    buf.append(NEWLINE);

    buf.append("Release date: ");
    if (album.isSetReleaseDate()) {
      buf.append(FDF.format(album.getReleaseDate()));
    }
    buf.append(NEWLINE);

    buf.append("Artist: ");

    if (album.isSetArtist()) {
      buf.append(album.getArtist().getName());
    }
    buf.append(NEWLINE);

    buf.append("Title: ");
    if (album.isSetTitle()) {
      buf.append(album.getTitle());
    }
    buf.append(NEWLINE);
    
    Disc disc = mediaDbAppErrorInfo.getDisc();
    if (disc != null) {
      buf.append("Disc: ");
      if (disc.isSetTitle()) {
        buf.append(disc.getTitle());
      } else {
        int discNr = album.getDiscs().indexOf(disc) + 1;
        buf.append(discNr);
      }
      buf.append(NEWLINE);
    }
    
    buf.append("Disc should be in folder: ");
    if (disc == null) {
      disc = album.getDiscs().get(0);
    }
    String albumFolderName;
    if (album.isSoundtrack()) {
      albumFolderName = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(disc, null);
    } else {
      albumFolderName = MediaDbAppUtil.generateAlbumDiscFolderName(disc, mediaDb, null);
    }
    buf.append(albumFolderName);
    buf.append(NEWLINE);

    errorPanel.getTextArea().setText(buf.toString());
  }

  private void fillErrorPanelFor_NO_MY_INFO_FOR_ALBUM(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    StringBuilder buf = new StringBuilder();
    Album album = mediaDbAppErrorInfo.getAlbum();

    buf.append("No 'MyInfo' for album:");
    buf.append(NEWLINE);

    buf.append("Issue date: ");
    if (album.isSetReleaseDate()) {
      buf.append(FDF.format(album.getReleaseDate()));
    }
    buf.append(NEWLINE);

    buf.append("Artist: ");

    if (album.isSetArtist()) {
      buf.append(album.getArtist().getName());
    }
    buf.append(NEWLINE);

    buf.append("Title: ");
    if (album.isSetTitle()) {
      buf.append(album.getTitle());
    }
    buf.append(NEWLINE);

    errorPanel.getTextArea().setText(buf.toString());
  }

  private void fillErrorPanelFor_NO_RELEASE_DATE_FOR_ALBUM(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    StringBuilder buf = new StringBuilder();
    Album album = mediaDbAppErrorInfo.getAlbum();

    buf.append("No 'IssueDate' for album:");
    buf.append(NEWLINE);

    buf.append("Artist: ");

    if (album.isSetArtist()) {
      buf.append(album.getArtist().getName());
    }
    buf.append(NEWLINE);

    buf.append("Title: ");
    if (album.isSetTitle()) {
      buf.append(album.getTitle());
    }
    buf.append(NEWLINE);

    errorPanel.getTextArea().setText(buf.toString());
  }
  
  /**
   * Fill an error panel for 'an album track not found on disc' error.
   * <p>
   * In the <code>mediaDbAppErrorInfo</code> either <code>trackReference</code> of <code>track</code> shall be set.
   * 
   * @param errorPanel The error panel to be filled.
   * @param mediaDbAppErrorInfo the error information.
   */
  private void fillErrorPanelFor_TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    TrackReference trackReference = mediaDbAppErrorInfo.getTrackReference();
    Track track = mediaDbAppErrorInfo.getTrack();
    
    if ((trackReference != null)  &&  (track != null)) {
      LOGGER.severe("Both trackReference and track are set in MediaDbAppErrorInfo: mediaDbAppErrorInfo=" + mediaDbAppErrorInfo.toString()) ;
    }
    
    if (trackReference != null) {
      track = trackReference.getTrack();
    }
    
    StringBuilder buf = new StringBuilder();
    Album album = mediaDbAppErrorInfo.getAlbum();
    buf.append("Track of an album not found on disc:");
    buf.append(NEWLINE);
    buf.append("Artist: ");
    if (album != null) {
    buf.append(album.isSetArtist() ? album.getArtist().getName() : "no artist");
    } else if (track != null) {
      buf.append(track.isSetArtist() ? track.getArtist().getName() : "no artist");
    } else {
      buf.append("no artist");
    }
    buf.append(NEWLINE);
    buf.append("Album title: ");
    if (album != null) {
      buf.append(album.getTitle());
    }
    buf.append(NEWLINE);
    buf.append("Track: ");
    if (track != null) {
      buf.append(track.getTitle());
    } else {
      buf.append("no title");
    }
    buf.append(NEWLINE);
    
    String albumFolderName = null;
    if (trackReference != null) {
      if (album.isSoundtrack()) {
        albumFolderName = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(trackReference.getDisc(), null);
      } else {
        albumFolderName = MediaDbAppUtil.generateAlbumDiscFolderName(trackReference.getDisc(), mediaDb, null);
      }
      buf.append("Track expected in folder: ").append(albumFolderName).append(NEWLINE);
    }


    errorPanel.getTextArea().setText(buf.toString());
    
    // Offer rename option if possible
    if (albumFolderName != null) {
      Path albumFolderPath = Paths.get(albumFolderName);
      String sourceTrackFileName = TrackFile.getTrackFileNameForTrackIndex(albumFolderPath, trackReference.getTrackNr());
      String trackFileNameWithoutExtension = null;
      if (MediaDbUtil.isOwnCompilationAlbum(album)) {
        TrackReference originalAlbumTrackReference = trackReference.getTrack().getOriginalDiscTrackReference();
        if (originalAlbumTrackReference != null) {
          trackFileNameWithoutExtension = MediaDbAppUtil.generateTrackFileNameForMyCompilationAlbum(originalAlbumTrackReference, mediaDb);
        } else {
          trackFileNameWithoutExtension = MediaDbAppUtil.generateTrackFileNameForStandardAlbum(trackReference);
        }
      } else {
        trackFileNameWithoutExtension = MediaDbAppUtil.generateTrackFileNameForStandardAlbum(trackReference);
      }
      if (sourceTrackFileName == null) {
        sourceTrackFileName = AlbumFolder.findBestMatchingTrackFileName(albumFolderPath, trackFileNameWithoutExtension);
      }
      final String finalsourceTrackFileName = sourceTrackFileName;
      if ((sourceTrackFileName != null)  &&  (trackFileNameWithoutExtension != null)) {
        String fileExtension = FileUtils.getFileExtension(sourceTrackFileName);
        String trackFileName = trackFileNameWithoutExtension + fileExtension;
        Label label = componentFactory.createLabel("file " + sourceTrackFileName + " to " + trackFileName);

        Button renameButton = componentFactory.createButton("Rename", "Rename audio file to proposed name");
        renameButton.setOnAction(e -> {
          Path fromPath = albumFolderPath.resolve(finalsourceTrackFileName);
          Path tempPath = albumFolderPath.resolve(finalsourceTrackFileName + "_tempForRename");
          Path toPath = albumFolderPath.resolve(trackFileName);
          LOGGER.severe("Moving " + fromPath.toString() + " to " + toPath.toString());
          try {
            Files.move(fromPath, tempPath);
            Files.move(tempPath, toPath);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        });
        errorPanel.getButtonsBox().getChildren().add(renameButton);
        errorPanel.getButtonsBox().getChildren().add(label);
      }
    }
    
    LOGGER.info("mediaDbAppErrorInfo: " + mediaDbAppErrorInfo.toString());
  }

  private void fillErrorPanelFor_TRACK_ON_DISC_NOT_FOUND_IN_MEDIADB(ErrorPanel errorPanel, MediaDbAppErrorInfo mediaDbAppErrorInfo) {
    StringBuilder buf = new StringBuilder();

    Path trackPath = mediaDbAppErrorInfo.getTrackPath();
    buf.append("Track on disc not found in media database:");
    buf.append(NEWLINE);
    buf.append("Bestand: ");
    buf.append(trackPath.toString());
    buf.append(NEWLINE);

    errorPanel.getTextArea().setText(buf.toString());
  }
  
  public EList<Album> getAlbums(FlexDate releaseDate, Artist artist, String title) {
    EList<Album> albums = new BasicEList<Album>();
    if ((artist != null) && artist.isSetContainerArtist()) {
      artist = artist.getContainerArtist();
    }

    for (Album album : mediaDb.getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate == null) {
        // no release date check needed
        LOGGER.fine("date OK, not specified");
      } else if (!album.isSetReleaseDate()) {
        LOGGER.fine("date not OK; date specified, but not available in album");
        compareOkSoFar = false;
      } else {
        LOGGER.fine("Real date check");
        LOGGER.fine("releaseDate: " + FDF.format(releaseDate) + ", this album: " + FDF.format(album.getReleaseDate()));
        if (album.getReleaseDate().getYear() == null) {
          LOGGER.severe("Year is null for album: " + album.toString());
        }
        //        if (album.getReleaseDate().compareTo(releaseDate) == 0) {
        if (album.getReleaseDate().isSpecializationOf(releaseDate)) {
          LOGGER.fine("date OK!!!");
        } else {
          LOGGER.fine("date not OK");
          compareOkSoFar = false;
        }
      }

      Artist albumArtist = album.getArtist();
      if ((albumArtist != null) && albumArtist.isSetContainerArtist()) {
        albumArtist = albumArtist.getContainerArtist();
      }
      if (compareOkSoFar && (albumArtist != null)) {
        if (!FuzzyStringCompare.fuzzyStringCompare(albumArtist.getName(), artist.getName())) {
          compareOkSoFar = false;
        }
      }

      if (compareOkSoFar && (title != null)) {
        String albumTitleOnDisc = MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(album.getTitle(), false);
        if (!albumTitleOnDisc.equals(title)) {
          compareOkSoFar = false;
        }
      }

      if (compareOkSoFar) {
        albums.add(album);
      }
    }

    return albums;
  }
}

class ErrorPanel extends VBox {
  private TextArea textArea;
  private HBox buttonsBox;
  
  ErrorPanel(ComponentFactoryFx componentFactory) {
    setPadding(new Insets(12.0));
    setSpacing(10.0);
    setMinHeight(150);
    
    HBox hBox = componentFactory.createHBox();
    hBox.setMaxHeight(120.0);
    hBox.setMinWidth(900.0);
    textArea = componentFactory.createTextArea();
    hBox.getChildren().add(textArea);
    
    buttonsBox = componentFactory.createHBox(12.0);
    getChildren().addAll(textArea, buttonsBox);
  }
  
  TextArea getTextArea() {
    return textArea;
  }
  
  HBox getButtonsBox() {
    return buttonsBox;
  }
}
