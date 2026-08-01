package goedegep.media.mediadb.gui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.common.IAlbumDetailsWindow;
import goedegep.media.common.IMediaService;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.gui.albumeditor.AlbumEditor;
import goedegep.media.mediadb.gui.albumeditor.TrackEditor;
import goedegep.media.mediadb.logic.MediaDbAppUtil;
import goedegep.media.mediadb.logic.MediaDbChecker;
import goedegep.media.mediadb.logic.MediaDbService;
import goedegep.media.mediadb.logic.MediaDbToDiscLocationMap;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.logic.AlbumOnDiscInfo;
import goedegep.media.musicfolder.logic.MusicFolderContent;
import goedegep.resources.ImageSize;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.datetime.FlexDate;
import goedegep.util.desktop.DesktopUtil;
import goedegep.util.emf.EMFResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides the main window for the music database
 * <p>
 * At startup this class gets a reference to the media database.
 * <ul>
 * <li>An empty MediaDb is created</li>
 * <li>The Album Info files are read, and their information is added to the media database</li>
 * <li>The Tracks.xml file is read, and its track information is added to the media database</li>
 * </ul>
 * After this the media database is checked:
 * <ul>
 * <li>
 * <Every album shall have:
 *   <ul>
 *   <li>An album title</li>
 *   <li>An album artist</li>
 *   </ul>
 * </li>
 * </ul>
 * Next the Music Folder is scanned: The Main Index folders, the soundtrack folder and the tracks folders.<br/>
 * Now that the media database describes what we should have, and we know what we actually have in the Music Folder, we can link
 * the two together, reporting any inconsistencies.
 * 
 * 
 */
public class MediaDbWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(MediaDbWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private static final String WINDOW_TITLE = "Music Database";
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  /**
   * An instance of an {@code IMediaService}
   * <p>
   * This used for media (not media database) functionality, like opening a properties editor.
   */
  private IMediaService iMediaService;
  
  /**
   * A {@code MediaDbService}.
   * <p>
   * This is used for media database related funtionality. For example for adding an album to the database or saving the database.
   */
  private MediaDbService mediaDbService;
  
  
  /**
   * The {@code AppResourcesFx} obtained via the {@code customization} parameter of the constructor.
   */
  private AppResourcesFx appResources;
  
  /**
   * The {@code MediaRegistry}.
   */
  private MediaRegistry mediaRegistry;

  /**
   * The {@code EMFResource} for the media database ({@link #mediaDb}).
   */
  private EMFResource<MediaDb> mediaDbResource = null;
  
  /**
   * The Media Database.
   */
  private MediaDb mediaDb;

  /**
   * A table listing all albums.
   */
  private AlbumsTable albumsTable;
  
  /**
   * A table listing all tracks.
   */
  private TracksTable tracksTable;
  
  /**
   * A table listing all artists.
   */
  private ArtistsTable artistsTable;
  
  /**
   * Current table, either albumsTable, trackTable or artistsTable.
   */
  private EObjectTable<?> currentTable;

  /**
   * This map provides the on disc location for single disc albums.
   * TODO shouldn't this be renamed to singleDiscAlbumToAlbumDiscLocationInfoMap?
   */
  private Map<Album, AlbumOnDiscInfo> albumToMusicFolderLocationMap;
  
  /**
   * This map provides the on disc location for the discs of multi-disc albums.
   * TODO shouldn't this be renamed to albumDiscToAlbumDiscLocationInfoMap?
   */
  private Map<Disc, AlbumOnDiscInfo> albumDiscToMusicFolderLocationMap;
  
  /**
   * This map provides the on disc location for separate tracks (i.e. tracks which aren't part of a complete disc).
   * TODO shouldn't this be renamed to trackToMusicFolderLocationMap;
   */
  private Map<Track, Path> trackDiscLocationMap;
  
  /**
   * Complete list of errors, of different types.
   */
  private List<Object> allErrors;
  
  private VBox centerPane;
  private Label statusLabel;
  
  /**
   * Constructor
   * <p>
   * 
   * @param customization the GUI customization.
   * @param iMediaService an implementation of an {@code IMediaService).
   * @param mediaDbService a {@code MediaDbService}
   */
  public MediaDbWindow(CustomizationFx customization, IMediaService iMediaService, MediaDbService mediaDbService) {
    super(customization, WINDOW_TITLE);

    this.iMediaService = iMediaService;
    this.mediaDbService = mediaDbService;
    mediaRegistry = MediaRegistry.getInstance();
    appResources = customization.getResources();
    mediaDbResource = mediaDbService.getMediaDbResource();
    mediaDb = mediaDbService.getMediaDb();

    allErrors = new ArrayList<>();
    
    tempFixProblems();

    // Check the media database TODO if tempFixProblems() isn't needed anymore, move this check to the launcher.
    MediaDbChecker.checkMediaDb(mediaDb, allErrors);

    // Scan the Music Folder: The Main Index folders, the soundtrack folder and the tracks folders.
    MusicFolderContent musicFolderContent = new MusicFolderContent(mediaRegistry.getMusicDirectory());
    boolean allOk = musicFolderContent.gatherMusicFolderContent();
    if (!allOk) {
      allErrors.addAll(musicFolderContent.getErrors());
      LOGGER.severe("Errors detected while gathering MusicFolder content: " + musicFolderContent.getErrors().size());
    }
    
    // Link the information in the media database to the information of the Music Folder.    
    MediaDbToDiscLocationMap mediaDbToDiscLocationMap = new MediaDbToDiscLocationMap(mediaDb, musicFolderContent);
    allOk = mediaDbToDiscLocationMap.createMediaDbToDiscLocationMap(true);
    if (!allOk) {
      allErrors.addAll(mediaDbToDiscLocationMap.getErrors());
      LOGGER.severe("Errors detected while linking the information in the media database to the information of the Music Folder: " + mediaDbToDiscLocationMap.getErrors().size());
    }
    albumToMusicFolderLocationMap = mediaDbToDiscLocationMap.getAlbumToMusicFolderLocationMap();
    albumDiscToMusicFolderLocationMap = mediaDbToDiscLocationMap.getAlbumDiscToMusicFolderLocationMap();
    trackDiscLocationMap = mediaDbToDiscLocationMap.getTrackDiscLocationMap();

    createGUI();
    
    // TODO ??
    for (Album album: mediaDb.getAlbums()) {
      boolean sourceTypeInMyInfo = false;
      MyInfo myInfo = album.getMyInfo();
      boolean usesReference = !myInfo.getAlbumReferences().isEmpty();
      List<MediumInfo> mediumInfos = myInfo.getIHaveOn();
      for (MediumInfo mediumInfo: mediumInfos) {
        InformationType sourceType = mediumInfo.getSourceType();
        if (sourceType != null) {
          sourceTypeInMyInfo = true;
        }
      }
      
      boolean trackWithoutSourceType = false;
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
          if (myTrackInfo == null) {
            String trackId = null;
            if (trackReference.getTrack() != null) {
              trackId = trackReference.getTrack().getTitle();
            } else {
              trackId = String.valueOf(trackReference.getTrackNr());
            }
            LOGGER.severe("No MyTrackInfo for Track: " + trackId + " of album: " + album.getArtistAndTitle());
            trackWithoutSourceType = true;
          } else {
            List<MediumInfo> mediumInfos2 = myTrackInfo.getIHaveOn();
            for (MediumInfo mediumInfo: mediumInfos2) {
              InformationType sourceType = mediumInfo.getSourceType();
              if (sourceType == null) {
                trackWithoutSourceType = true;
//                LOGGER.severe("no SourceType for Track: " + trackReference.getTrack().getTitle() + " of album: " + album.getArtistAndTitle());
              }
            }
          }
        }
      }
      
      
      if (!sourceTypeInMyInfo && trackWithoutSourceType  &&  !usesReference  &&  (MediaDbUtil.haveAlbumOnHardDisk(album)  ||  MediaDbUtil.haveAlbumPartlyOnHardDisk(album))) {
        if (!sourceTypeInMyInfo) {
//          LOGGER.severe("no SourceType in MyInfo of album: " + album.getArtistAndTitle()); TODO Enable
        } else {
          LOGGER.severe("No MyTrackInfo for a Track of album: " + album.getArtistAndTitle());
        }
      }
    }
    
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
//        LOGGER.info("Change detected: " + notification.toString());
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }
        
        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
          if (MEDIA_DB_PACKAGE.getMediaDb_Albums().equals(feature)) {
            albumsTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Albums());
            artistsTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Artists());
          }
      }

    };
    mediaDb.eAdapters().add(eContentAdapter);
    
    mediaDbResource.dirtyProperty().addListener((_, _, _) -> updateTitle());
    
    updateTitle();
  }
  
  private void tempFixProblems() {
//    TrackReference trToRemove = null;
//    for (TrackReference ttt: mediaDb.getTrackCollection(Collection.SAMPLE).getTrackReferences()) {
//      Track tttt = ttt.getTrack();
//      if (tttt != null  &&  tttt.getTitle().contains("Nothing Turned")) {
//        trToRemove = ttt;
//        LOGGER.severe("Found");
//      }
//    }
//    if (trToRemove != null) {
//      mediaDb.getTrackCollection(Collection.SAMPLE).getTrackReferences().remove(trToRemove);
//    }
//    
//    // yo la tengo
//    // change sample track to Big Day Coming
//    Artist artist = mediaDb.getArtist("Yo La Tengo");
//    if (artist != null) {
//      LOGGER.severe("artist found");
//    } else {
//      LOGGER.severe("artist NOT found");
//      return;
//    }
//    TrackReference tr = artist.getSample();
//    MyTrackInfo mti2 = tr.getMyTrackInfo();
//    artist.setSample(null);
//    mediaDb.getTrackCollection(Collection.SAMPLE).getTrackReferences().remove(tr);
//    
//    Track track = mediaDb.getTrack(artist, "Big Day Coming");
//    if (track != null) {
//      LOGGER.severe("track found");
//    } else {
//      LOGGER.severe("track NOT found");
//      return;
//    }
//    
//    Album album = mediaDb.getAlbum(new FlexDate(1993), artist, "Painful");
//    if (album != null) {
//      LOGGER.severe("album found");
//    } else {
//      LOGGER.severe("album NOT found");
//      return;
//    }
//    
//    Disc disc = album.getDisc();
//    TrackReference trackReferenceOriginal = disc.getTrackReferences().get(0);
//    trackReferenceOriginal.setOriginalAlbumTrackReference(true);
//
//    artist.setMyComments("Hoef ik niet te hebben. Wel het album 'Painful' een tijd gehad.");
//    TrackReference trackReference = MediadbFactory.eINSTANCE.createTrackReference();
//    trackReference.setTrack(track);
//    MyTrackInfo myTrackInfo = MediadbFactory.eINSTANCE.createMyTrackInfo();
//    myTrackInfo.setTrackReference(trackReferenceOriginal);
//    MediumInfo iHaveOn = MediadbFactory.eINSTANCE.createMediumInfo();
//    iHaveOn.setMediumType(MediumType.HARDDISK);
//    iHaveOn.setInformationType(InformationType.MP3);
//    myTrackInfo.getIHaveOn().add(iHaveOn);
//    trackReference.setMyTrackInfo(myTrackInfo);
//    
//    artist.setSample(trackReference);
//    
//    // on the album, refer to sample, rest Iwant no
//    trackReferenceOriginal.getMyTrackInfo().setTrackReference(trackReference);
//    for (TrackReference t: disc.getTrackReferences()) {
//      t.getMyTrackInfo().setIWant(IWant.NO);
//    }
//    
//    Iterator iterator = mediaDb.eAllContents();
//    while (iterator.hasNext()) {
//      Object object = iterator.next();
//      if (object instanceof MyTrackInfo mti) {
//        TrackReference tref = mti.getTrackReference();
//        if (tref != null) {
//          Track trck = tref.getTrack();
//          if (trck != null  &&  trck.getTitle().contains("Nothing Turned")) {
//            LOGGER.severe("Found");
//          }
//        }
//      }
//      if (object instanceof TrackReference tref2) {
//        Track trck = tref2.getTrack();
//        if (trck != null  &&  trck.getTitle().contains("Nothing Turned")) {
//          TrackCollection container = (TrackCollection) tref2.eContainer();
//          container.getTrackReferences().remove(tref2);
//          LOGGER.severe("Found");
//        }
//      }
//      
//      if (object instanceof EObject eObject) {
//        EObject container = eObject.eContainer();
//        if (container == null) {
//          LOGGER.severe("No eContainer");
//        }
//      }
//    }
    
//    Artist artist = mediaDb.getArtist("The Smiths");
//    if (artist == null) {
//      LOGGER.severe("Artist not found");
//      return;
//    }
//    
//    Album album = mediaDb.getAlbum(null, artist, "Hatful Of Hollow");
//    if (album == null) {
//      LOGGER.severe("Album not found");
//      return;
//    }
//    LOGGER.severe(album.toString());
//    
//    MyInfo myInfo = album.getMyInfo();
//    if (myInfo == null) {
//      LOGGER.severe("Album has no MyInfo");
//      return;
//    }
//
//    LOGGER.severe("Clearing \'I want\' for: " + album.getArtistAndTitle());
//    // Clear I want
//    Disc disc = album.getDisc();
//    for (TrackReference trackReference: disc.getTrackReferences()) {
//      MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
//      if (myTrackInfo != null) {
//        myTrackInfo.unsetIWant();
//      }
//    }
//    
//    // Set I have on to harddisk/flac
//    List<MediumInfo> iHaveOn = myInfo.getIHaveOn();
//    if (!iHaveOn.isEmpty()) {
//      LOGGER.severe("Some i have on already filled in");
//      return;
//    }
    
//    MediumInfo mediumInfo = MediadbFactory.eINSTANCE.createMediumInfo();
//    mediumInfo.setMediumType(MediumType.HARDDISK);
//    mediumInfo.setInformationType(InformationType.FLAC);
//    iHaveOn.add(mediumInfo);
    
    
//    TrackCollection samplesCollection = mediaDb.getTrackCollection(Collection.SAMPLE);
//    Artist artist = mediaDb.getArtist("Chris Jones");
//    TrackReference reference = samplesCollection.getTrackReferece(artist, "Long After You're Gone");
//    samplesCollection.getTrackReferences().remove(reference);
//    TrackCollection eastCollection = mediaDb.getTrackCollection(Collection.EASY_LISTENING);
//    eastCollection.getTrackReferences().add(reference);
    
    
//  Artist artist = mediaDb.getArtist("Sage Francis");
//  Track track = mediaDb.getTrack(artist, "The Runaways");
//  track.setTitle("Runaways");
    
    addAlbumForSample("Breathless", "Viktor Lazlo", new FlexDate(1992), 10, "Champagne And Wine");
//    addAlbumForSample("Ágætis Byrjun", "Sigur Rós", new FlexDate(1999), 8, "Ágætis Byrjun");
//    addAlbumForSample("Where Our Love Grows", "Swing Out Sister", new FlexDate(2004), 0, "Love Won't Let You Down");
//    addAlbumForSample("Red", "Taylor Swift", new FlexDate(2012), 1, "Red");
//    addAlbumForSample("These Were The Earlies", "The Earlies", new FlexDate(2004), 4, "25 Easy Pieces");
//    addAlbumForSample("The United States of America", "The United States of America", new FlexDate(1968), 1, "Hard Coming Love");
    
//    listTracksIWantToHave();
    
    LOGGER.severe("Done");
  }
  
  private void listTracksIWantToHave() {
    for (Album album: mediaDb.getAlbums()) {
      List<DiscAndTrackNrs> discAndTrackNrsList = MediaDbUtil.getAlbumTracksIWant(album);
      if (!discAndTrackNrsList.isEmpty()) {
        StringBuilder buf = new StringBuilder();
        buf.append("Tracks/discs I want to have: ");
        boolean first = true;
        for (DiscAndTrackNrs discAndTrackNrs: discAndTrackNrsList) {
          Integer discNr = null;
          if (discAndTrackNrs.isSetDiscNr()) {
            discNr = discAndTrackNrs.getDiscNr();
          }
          for (int trackNr: discAndTrackNrs.getTrackNrs()) {
            if (first == true) {
              first = false;
            } else {
              buf.append(", ");
            }
            if ((discNr != null)  && (discNr != -1)) {
              buf.append(discNr);
              buf.append(".");
            }
            buf.append(trackNr);
          }
        }
        buf.append(" for album: ").append(album.getArtistAndTitle());
        LOGGER.severe(buf.toString());
      }
    }
  }
  
  private void addAlbumForSample(String albumTitle, String artistName, FlexDate releaseDate, int trackIndex, String trackTitle) {
    Album newAlbum = MediadbFactory.eINSTANCE.createAlbum();
    newAlbum.setTitle(albumTitle);
    Artist artist = mediaDb.getArtist(artistName);
    if (artist != null) {
      LOGGER.severe("artist found");
    } else {
      LOGGER.severe("artist NOT found");
      return;
    }
    newAlbum.setArtist(artist);
    newAlbum.setReleaseDate(releaseDate);
    MyInfo myInfo = MediadbFactory.eINSTANCE.createMyInfo();
    myInfo.setIWant(IWant.NO);
    newAlbum.setMyInfo(myInfo);
    Disc disc = MediadbFactory.eINSTANCE.createDisc();
    TrackReference trackReference;
    
    for (int i = 0; i < trackIndex; i++) {
      trackReference = MediadbFactory.eINSTANCE.createTrackReference();
      disc.getTrackReferences().add(trackReference);
    }
    
    Track track = mediaDb.getTrack(artist, trackTitle);
    if (track != null) {
      LOGGER.severe("track found");
    } else {
      LOGGER.severe("track NOT found");
      return;
    }
    
    TrackCollection samplesCollection = mediaDb.getTrackCollection(Collection.SAMPLE);
    TrackReference sampleReference = samplesCollection.getTrackReferece(artist, trackTitle);
    if (sampleReference != null) {
      LOGGER.severe("Sample reference found");
    } else {
      LOGGER.severe("Sample reference NOT found");
      return;
    }
    
    trackReference = MediadbFactory.eINSTANCE.createTrackReference();
    trackReference.setTrack(track);
    MyTrackInfo myTrackInfo = MediadbFactory.eINSTANCE.createMyTrackInfo();
    myTrackInfo.setTrackReference(sampleReference);
    trackReference.setMyTrackInfo(myTrackInfo);
    trackReference.setOriginalAlbumTrackReference(true);
    disc.getTrackReferences().add(trackReference);
    newAlbum.getDiscs().add(disc);
    
    mediaDb.getAlbums().add(newAlbum);
    
  }
  
  public String iHaveOnToString(List<MediumInfo> iHaveOnMedia) {
    StringBuilder buf = new StringBuilder();
    boolean first = true;
    buf.append("I have this album on: ");
    for (MediumInfo iHaveOnMedium: iHaveOnMedia) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      buf.append(GuiUtils.createMediumText(iHaveOnMedium.getMediumType()));
      if (iHaveOnMedium.isSetSourceType()) {
        buf.append(" (from ");
        InformationType sourceType = iHaveOnMedium.getSourceType();
        if (sourceType != InformationType.NOT_SET) {
          buf.append(sourceType.getLiteral());
        } else {
          buf.append("??");
        }
        buf.append(")");
      }
      if (iHaveOnMedium.isSetSourceBitRate()) {
        buf.append(" {bit rate ");
        buf.append(iHaveOnMedium.getSourceBitRate());
        buf.append("}");
      }
    }
    
    return buf.toString();
  }
  

  /**
   * Create the GUI.
   */
  private void createGUI() {

    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is a VBox; the control panel and below that the media table
     * Bottom is a status label
     */

    BorderPane mainPane = new BorderPane();

    mainPane.setTop(createMenuBar());
    
    centerPane = componentFactory.createVBox(18);
    
    centerPane.getChildren().add(createControlPanel());
    
    albumsTable = new AlbumsTable(customization, this, mediaDbService::addAlbumToMediaDatabase, mediaDbService, albumToMusicFolderLocationMap, albumDiscToMusicFolderLocationMap, trackDiscLocationMap);
    albumsTable.setMinHeight(800);
    currentTable = albumsTable;
    centerPane.getChildren().add(albumsTable);
    tracksTable = new TracksTable(customization, this, mediaDb, trackDiscLocationMap);
    tracksTable.setMinHeight(800);
    artistsTable = new ArtistsTable(customization, this, mediaDb);
    artistsTable.setMinHeight(800);

    mainPane.setCenter(centerPane);
    
    // Bottom: Status labal
    String statusText;
    if (allErrors.size() == 0) {
      statusText = "No problems found";
    } else if (allErrors.size() == 1) {
      statusText = "found 1 problem";
    }  else {
      statusText = allErrors.size() + " problems found";
    }
    
    statusLabel = componentFactory.createLabel(statusText);
    mainPane.setBottom(statusLabel);

    Scene scene = new Scene(mainPane, 1300, 950);
    setScene(scene);
    show();
  }

  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;

    // File menu
    menu = new Menu("File");
        
    // File: Save media information
    MenuUtil.addMenuItem(menu, "Save media information", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        checkAndSaveMediaDb();
      }
    });

    // File: Edit Property Descriptors
    if (mediaRegistry.isDevelopmentMode()) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          iMediaService.showPropertyDescriptorsEditor();
        }
      });

      // File: Edit User Settings
      MenuUtil.addMenuItem(menu, "Edit User Settings", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          iMediaService.showPropertiesEditor();
        }
      });
    }
    
    // File: Convert Database
    if (mediaRegistry.isDevelopmentMode()) {
      MenuUtil.addMenuItem(menu, "Convert Database", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          convertDatabase();
        }
      });
    }

    menuBar.getMenus().add(menu);

    // Media menu
    menu = new Menu("Media");

    // Media: Show only albums that need attention
    CheckMenuItem albumsThatNeedAttentionMenuItem = new CheckMenuItem("Show only albums that need attention");
    albumsThatNeedAttentionMenuItem.setSelected(false);

    albumsThatNeedAttentionMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        setOnlyAlbumsThatNeedAttentionFilter(albumsThatNeedAttentionMenuItem.isSelected());
      }
    });
    menu.getItems().add(albumsThatNeedAttentionMenuItem);

    // Media: New album
    MenuUtil.addMenuItem(menu, "New album", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        openAlbumEditor();
      }
    });

    // Media: New track
    MenuUtil.addMenuItem(menu, "New track", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        openTrackEditor();
      }
    });

    // Media: New album from album tracks
    MenuUtil.addMenuItem(menu, "New album from album tracks", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        createAlbumFromAlbumTracks();
      }
    });

    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: Media DB Information
    MenuUtil.addMenuItem(menu, "Media DB Information", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        DesktopUtil.open("http://mydigitallife.rf.gd/myworld-user-manual/media/mediadb-the-media-database/");
//        new BrowserWindow("MediaDbHelp", customization, "http://mydigitallife.rf.gd/myworld-user-manual/media/mediadb-the-media-database/");
      }
    });

    // Help: About
    MenuUtil.addMenuItem(menu, "About", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showHelpAboutDialog();
      }
    });

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  /**
   * Create the control panel.
   * <p>
   * The control panel contains:
   * a label and a textfield for specifying a filter value.
   * a button to open the problem solving screen.
   * buttons to select albums, tracks or artists view
   * 
   * @return the created control panel.
   */
  private Node createControlPanel() {
    HBox controlPanel = componentFactory.createHBox(18, 12);
    
    // Label: "Filter op de tekst:"
    Label label = componentFactory.createLabel("Filter op de tekst:");
    controlPanel.getChildren().add(label);
    
    // TextField: for the text to filter on
    TextField textField = componentFactory.createTextField(200, "Voer de tekst in die in de album informatie voor moet komen");
    textField.textProperty().addListener((_, _, newValue) -> {
      setFilter(newValue);
    });
    controlPanel.getChildren().add(textField);
    
    // Button to open the MediaDbProblemsWindow
    Button openProblemsWindowButton = componentFactory.createButton("Open problem solving screen", "opens the problem solving screen");
    openProblemsWindowButton.setOnAction((_) -> new MediaDbProblemsWindow(customization, mediaDb, allErrors));
    controlPanel.getChildren().add(openProblemsWindowButton);

    Button albumsButton = componentFactory.createButton("Albums", "switch to the albums table");
    Button tracksButton = componentFactory.createButton("Tracks", "switch to the tracks table");
    Button artistsButton = componentFactory.createButton("Artists", "switch to the artists table");
    ComboBox<Collection> collectionComboBox = componentFactory.createComboBox(Collection.VALUES);
    collectionComboBox.getSelectionModel().select(Collection.NOT_SET);
    
    albumsButton.setOnAction((_) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      currentTable = albumsTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(true);
      tracksButton.setDisable(false);
      artistsButton.setDisable(false);
    });
    albumsButton.setDisable(true);
    
    tracksButton.setOnAction((_) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      tracksTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Tracks());
      currentTable = tracksTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(false);
      tracksButton.setDisable(true);
      artistsButton.setDisable(false);
    });
    
    artistsButton.setOnAction((_) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      currentTable = artistsTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(false);
      tracksButton.setDisable(false);
      artistsButton.setDisable(true);
    });
    
    collectionComboBox.setOnAction((_) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      Collection collection = collectionComboBox.getValue();
      ObservableList<Track> tracks = javafx.collections.FXCollections.observableArrayList();
      for (TrackReference trackReference: mediaDb.getTrackCollection(collection).getTrackReferences()) {
        tracks.add(trackReference.getTrack());
      }
      tracksTable.setItems(tracks);
      currentTable = tracksTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(false);
      tracksButton.setDisable(false);
      artistsButton.setDisable(false);
    });
    
    controlPanel.getChildren().addAll(albumsButton, tracksButton, artistsButton, collectionComboBox);
    
    return controlPanel;
  }

  /**
   * Save the media database to the related file.
   */
  private void checkAndSaveMediaDb() {
    Result result = mediaDbService.checkAndSaveMediaDb();
    
    if (result.getResultType() == ResultType.FAILED) {
      componentFactory.createErrorDialog(
          "Saving the media information has failed.",
          result.getMessage()
          ).showAndWait();
    }
  }


  /**
   * Show only albums that need attention in the table.
   * 
   * @param isSelected
   */
  protected void setOnlyAlbumsThatNeedAttentionFilter(boolean isSelected) {
    if (isSelected) {
      List<Album> albumsThatNeedAttention = MediaDbAppUtil.getAlbumsThatNeedAttention(mediaDb);
      ObservableList<Album> albumsThatNeedAttentionObservable = FXCollections.observableList(albumsThatNeedAttention);
      albumsTable.setItems(albumsThatNeedAttentionObservable);
    } else {
      ObservableList<Album> albums = FXCollections.observableList(mediaDb.getAlbums());
      albumsTable.setItems(albums);
    }
  }
  
  /**
   * Make changes to the media database.
   * 
   */
  private void convertDatabase() {
    // Change empty bonus track to null
    for (Album album: mediaDb.getAlbums()) {
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          String bonusTrack = trackReference.getBonusTrack();
          if (bonusTrack != null  &&  bonusTrack.isEmpty()) {
            LOGGER.severe("Bonus track empty: " + album.getArtistAndTitle() + ", " + disc.getTitle() + ", " + trackReference.getTrack().getTitle());
            trackReference.setBonusTrack(null);
          } else if (bonusTrack == null) {
//            LOGGER.severe("Bonus track null: " + album.getArtistAndTitle() + ", " + disc.getTitle() + ", " + trackReference.getTrack().getTitle());
          } else {
//            LOGGER.severe("Bonus track set: " + album.getArtistAndTitle() + ", " + disc.getTitle() + ", " + trackReference.getTrack().getTitle() + "::" + bonusTrack);
          }
        }
      }
    }
  }

  /**
   * Show the dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About MediaDb",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null,
        mediaRegistry.getShortProductInfo() + NEWLINE +
        "Versie: " + mediaRegistry.getVersion() + NEWLINE +
        mediaRegistry.getCopyrightMessage() + NEWLINE +
        "Auteur: " + mediaRegistry.getAuthor())
        .showAndWait();
  }
  
  /**
   * Set the filter expression on the <code>albumsTable</code>.
   * 
   * @param text the filter expression to set on the table.
   */
  private void setFilter(String text) {
    currentTable.setFilterExpression(text, null);
  }
  
  void openAlbumInAlbumDetailsWindow() {
    IAlbumDetailsWindow albumDetailsWindow = mediaDbService.openAlbumDetailsWindow(trackDiscLocationMap, albumsTable);
    albumDetailsWindow.setAlbum((Album) albumsTable.getSelectedObject());
  }
  
  void openArtistEditor() {  // HIER VERDER
    ArtistDetailsEditor artistDetailsEditor = ArtistDetailsEditor.newInstance(customization, "Artist editor", mediaDbService);
    artistDetailsEditor.setObject((Artist) artistsTable.getSelectedObject());
    artistDetailsEditor.show();
  }

  /**
   * Open the AlbumEditor to enter a new album.
   */
  void openAlbumEditor() {
    AlbumEditor.newInstance(customization, mediaDbService).show();
  }

  /**
   * Open the TrackEditor to enter a new album.
   */
  void openTrackEditor() {
    TrackEditor.newInstance(customization, mediaDbService).show();
  }
    
  /**
   * Create a new Album from the tracks in a folder.
   */
  private void createAlbumFromAlbumTracks() {
    new CreateAlbumFromAlbumTracksWindow(customization);
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the media file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the media file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (mediaDbResource.isDirty()) {
      buf.append("*");
    }
    String fileName = mediaDbResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
}
