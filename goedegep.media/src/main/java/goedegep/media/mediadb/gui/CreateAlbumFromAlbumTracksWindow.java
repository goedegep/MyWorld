package goedegep.media.mediadb.gui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.media.mediadb.logic.MediaDbService;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.logic.TrackFile;
import goedegep.media.svc.MediaService;
import goedegep.util.datetime.FlexDate;
import goedegep.util.file.FileUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is temporary.
 * Based on the audio files in a folder, it creates an {@code Album} in the media database.
 */
public class CreateAlbumFromAlbumTracksWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(CreateAlbumFromAlbumTracksWindow.class.getName());
  private static final String WINDOW_TITLE = "Create Album from tracks in a folder";
  private static final String NEW_LINE = System.getProperty("line.separator");
  private String SOURCE_FOLDER = "D:\\Music";
//  private String SOURCE_FOLDER = "D:\\SoulSeek\\Complete";
  
  private ObjectControlFolderSelecter sourceFolderSelector1;
  private ObjectControlFolderSelecter sourceFolderSelector2;
  private TextArea statusPanel;
  private Button actionButton; // To perform preview or import
  private String sourceFolderName1;
  private String sourceFolderName2;
  private TextArea albumInfoPreviewArea;
  private MediaDb mediaDb;
  
  public CreateAlbumFromAlbumTracksWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
        
    mediaDb = MediaDbService.getInstance().getMediaDb();
        
    createGUI();
    
    sourceFolderName1 = sourceFolderSelector1.getAbsolutePath();
    sourceFolderName2 = sourceFolderSelector2.getAbsolutePath();
    
    show();
  }
  
  /**
   * Create the GUI
   */
  private void createGUI() {
    VBox mainLayout =  componentFactory.createVBox(12.0);
    
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(12.0));
    gridPane.setHgap(12.0);
    gridPane.setVgap(12.0);
    
    /*
     * Part 1; main controls
     */
    
    // First row: 'Source folder 1:' <source-folder-text-field> <folder-chooser-button>
    Label label = componentFactory.createLabel("Source folder 1:");
    
    sourceFolderSelector1 = componentFactory.createFolderSelecter(
        400,
        "Enter the name of the folder with the tracks of (the first disc of) the album that has to be imported",
        "Choose source folder",
        "Click to start folder chooser",
        "Select the folder with the tracks of (the first disc of) the album that has to be imported",
        false);
    sourceFolderSelector1.setInitialFolderProvider(() -> SOURCE_FOLDER);
    Node sourceFolderTextField = sourceFolderSelector1.getControl();
    sourceFolderSelector1.addListener((_) -> handleNewSourceFolder());
    
    Button sourceFolderChooserButton = sourceFolderSelector1.getFolderChooserButton();
    
    gridPane.addRow(0, label, sourceFolderTextField, sourceFolderChooserButton);
    
    // Second row: 'Source folder 2:' <source-folder-text-field> <folder-chooser-button>
    label = componentFactory.createLabel("Source folder 2:");
    
    sourceFolderSelector2 = componentFactory.createFolderSelecter(
        400,
        "Enter the name of the folder with the tracks of any second disc of the album that has to be imported",
        "Choose source folder",
        "Click to start folder chooser",
        "Select the folder with the tracks of any second disc of the album that has to be imported",
        false);
    sourceFolderSelector2.setInitialFolderProvider(() -> SOURCE_FOLDER);
    sourceFolderTextField = sourceFolderSelector2.getControl();
    sourceFolderSelector2.addListener((_) -> handleNewSourceFolder());
    
    sourceFolderChooserButton = sourceFolderSelector2.getFolderChooserButton();
    
    gridPane.addRow(1, label, sourceFolderTextField, sourceFolderChooserButton);
    
    mainLayout.getChildren().add(gridPane);
        
    /*
     * Part 2; Text Area with proposed tracks
     */
    albumInfoPreviewArea = componentFactory.createTextArea();
    albumInfoPreviewArea.setMinHeight(800.0);
    albumInfoPreviewArea.setPrefHeight(800.0);
    
    mainLayout.getChildren().add(albumInfoPreviewArea);
    
    /*
     * Part 3; Action buttons
     */
    mainLayout.getChildren().add(createOkOrCancelPanel());
    
    /*
     * Part 4; Status panel
     */
    statusPanel = componentFactory.createTextArea("Nothing to report for now");
    mainLayout.getChildren().add(statusPanel);

    Scene scene = new Scene(mainLayout, 1100, 700);
    setScene(scene);
  }
  
  /**
   * Create a panel with 'Import album' and a 'Cancel' buttons.
   * <p>
   * The action for the 'Import album' button is {@link #importTrack()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return
   */
  private Node createOkOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Preview/Import tracks", "Cancel".
    actionButton = componentFactory.createButton("Scan tracks", null);
    actionButton.setOnAction((_) -> scanAlbumFolder());
    hBox.getChildren().add(actionButton);
    
    Button button = componentFactory.createButton("Cancel", "Exit window");
    button.setOnAction((_) -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  /**
   * Handle the fact that a new source folder is selected.
   * 
   * @param newSourceFolderName the name of the new source folder.
   */
  private void handleNewSourceFolder() {
    LOGGER.severe("=> sourceFolderName=" + sourceFolderName1);
    
    sourceFolderName1 = sourceFolderSelector1.getAbsolutePath();
    
    Path path = Paths.get(sourceFolderName1);
    if (Files.isDirectory(path)) {
      sourceFolderName2 = sourceFolderSelector2.getAbsolutePath();
      if (sourceFolderName2 != null) {
        path = Paths.get(sourceFolderName2);
        if (Files.isDirectory(path)) {
          // double album
          scanAlbumFolder();
        }
      } else {
        // single disc album
        scanAlbumFolder();
      }
    }
    
    LOGGER.severe("<=");
  }
  
  private void closeWindow() {
    LOGGER.severe("AlbumDetailsEditor: closeWindow");
    this.close();
  }
  
  public void scanAlbumFolder(String sourceFolderName1, String sourceFolderName2) {
    this.sourceFolderName1 = sourceFolderName1;
    this.sourceFolderName2 = sourceFolderName2;
    scanAlbumFolder();
  }
  
  /**
   * Scan an album folder for information.
   * <p>
   * 
   */
  private void scanAlbumFolder() {
    
    MediadbFactory factory = MediadbFactory.eINSTANCE;
    Path path = Paths.get(sourceFolderName1);
    String fileName = path.getFileName().toString();
    String[] nameParts = fileName.split(" - ");
    
    // release date
    FlexDate releaseDate = null;
    if (nameParts.length > 0) {
      int releaseYear = Integer.valueOf(nameParts[0]);
      releaseDate = new FlexDate(releaseYear);
    }
    
    // artist
    Artist artist = null;
    if (nameParts.length > 1) {
      String artistName = nameParts[1];
      artist = MediaDbUtil.getArtist(mediaDb, artistName);
    }
    
    // title
    String title = null;
    if (nameParts.length > 2) {
      title = nameParts[2];
    }
    
    if (artist == null) {
      LOGGER.severe("ARTIST is NULL for source folder: " + sourceFolderName1);
      return;
    }
    Album album = MediaDbUtil.getAlbum(mediaDb, null, artist, title);
    if (album == null) {
      LOGGER.severe("ALBUM is NULL for source folder: " + sourceFolderName1);
      return;
    }

    MyInfo myInfo = album.getMyInfo();  
    MediumInfo mediumInfo = factory.createMediumInfo();
    mediumInfo.setMediumType(MediumType.HARDDISK);
    mediumInfo.setInformationType(InformationType.FLAC);
    mediumInfo.setSourceType(InformationType.WAV);
    myInfo.getIHaveOn().add(mediumInfo);
        
    albumInfoPreviewArea.setText(album.toString());
    statusPanel.setText("MyInfo updated for album: " + MediaDbUtil.getArtistAndTitle(album));
  }
  
  /**
   * Scan an album folder for information.
   * <p>
   * 
   */
  private void scanAlbumFolder2() {
    MediadbFactory factory = MediadbFactory.eINSTANCE;
    Album album = factory.createAlbum();
    Path path = Paths.get(sourceFolderName1);
    String fileName = path.getFileName().toString();
    String[] nameParts = fileName.split(" - ");
    
    // release date
    if (nameParts.length > 0) {
      int releaseYear = Integer.valueOf(nameParts[0]);
      FlexDate releaseDate = new FlexDate(releaseYear);
      album.setReleaseDate(releaseDate);
    }
    
    // artist
    if (nameParts.length > 1) {
      String artistName = nameParts[1];
      Artist artist = MediaDbUtil.getArtist(mediaDb, artistName);
      album.setArtist(artist);
    }
    
    // title
    if (nameParts.length > 2) {
      String title = nameParts[2];
      album.setTitle(title);
    }
    
    
    MyInfo myInfo = factory.createMyInfo();
    MediumInfo mediumInfo = factory.createMediumInfo();
    mediumInfo.setMediumType(MediumType.HARDDISK);
    mediumInfo.setInformationType(InformationType.FLAC);
    mediumInfo.setSourceType(InformationType.WAV);
    myInfo.getIHaveOn().add(mediumInfo);
    
    Disc disc = factory.createDisc();
    if (nameParts.length > 3) {
      disc.setTitle(nameParts[3]);
    }
    List<String> trackFileNames = FileUtils.getAudioFileNamesInFolder(sourceFolderName1);
    for (String trackFileName: trackFileNames) {
      String trackName = deriveTrackTitleFromFileName(trackFileName);
      Track track = factory.createTrack();
      track.setTitle(trackName);
      TrackReference trackReference = factory.createTrackReference();
      trackReference.setTrack(track);
      trackReference.setOriginalAlbumTrackReference(true);
//      MyTrackInfo myTrackInfo = factory.createMyTrackInfo();
//      trackReference.setMyTrackInfo(myTrackInfo);
      disc.getTrackReferences().add(trackReference);
    }
    album.getDiscs().add(disc);
    
    mediaDb.getAlbums().add(album);
    
    albumInfoPreviewArea.setText(album.toString());
  }
  
  private String deriveTrackTitleFromFileName(String fileName) {
    fileName = FileUtils.getFileNameWithoutExtension(fileName);
    String title = TrackFile.getTrackTitle(fileName);
    if (title == null) {
      title = "<no-title>";
    }
    
    String origTitle = title;
    title = title.trim();
    if (!title.equals(origTitle)) {
      LOGGER.severe("Stripped");
    }
    
    return title;
  }
}
