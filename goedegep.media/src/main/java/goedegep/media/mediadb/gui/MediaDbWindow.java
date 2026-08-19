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
import goedegep.media.mediadb.model.AlbumType;
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
import goedegep.media.musicfolder.logic.AlbumOnHardDiskInfo;
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
  private Map<Album, AlbumOnHardDiskInfo> albumToMusicFolderLocationMap;
  
  /**
   * This map provides the on disc location for the discs of multi-disc albums.
   * TODO shouldn't this be renamed to albumDiscToAlbumDiscLocationInfoMap?
   */
  private Map<Disc, AlbumOnHardDiskInfo> albumDiscToMusicFolderLocationMap;
  
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
    allOk = mediaDbToDiscLocationMap.createMediaDbToHardDiskLocationMap(true);
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
//            String trackId = null;
//            if (trackReference.getTrack() != null) {
//              trackId = trackReference.getTrack().getTitle();
//            } else {
//              trackId = String.valueOf(MediaDbUtil.getTrackNr(trackReference));
//            }
//            LOGGER.severe("No MyTrackInfo for Track: " + trackId + " of album: " + MediaDbUtil.getArtistAndTitle(album));
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
          LOGGER.severe("No MyTrackInfo for a Track of album: " + MediaDbUtil.getArtistAndTitle(album));
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
    MediadbFactory factory = MediadbFactory.eINSTANCE;
    TrackReference trackReference;
    Track track;
    Artist artist;
    Album album;
    Disc disc;
    
    for (Album a: mediaDb.getAlbums()) {
      MyInfo m = a.getMyInfo();
      Boolean soundTrackAlbum = false;
      if (m != null  &&  m.getAlbumType() == AlbumType.SOUNDTRACK_ALBUM) {
        soundTrackAlbum = true;
      }
      if (a.isSoundtrack()  ||  soundTrackAlbum) {
        LOGGER.severe("Soundtrack: " + a.getTitle() + "  ==  " + a.isSoundtrack() + " - " + soundTrackAlbum);
      }
    }
    
    
    
    
    artist = MediaDbUtil.getArtist(mediaDb, "Het Gebeurde In Het Westen");
    album = MediaDbUtil.getAlbum(mediaDb, null, artist, "Het Gebeurde In Het Westen");
    
    Artist newArtist = factory.createArtist();
    newArtist.setName("Ennio Morricone");
    
    album.setArtist(newArtist);
    mediaDb.getArtists().add(newArtist);
    mediaDb.getArtists().remove(artist);
    album.getMyInfo().setAlbumType(AlbumType.SOUNDTRACK_ALBUM);
    
//    
//    disc = album.getDiscs().getFirst();
//    trackReference = disc.getTrackReferences().get(1);
//    track = trackReference.getTrack();
//    track.setTitle("Song Of India (from the opera 'Sadko')");
//    
//    trackReference = disc.getTrackReferences().get(2);
//    track = trackReference.getTrack();
//    track.setTitle("Romance (from suite 'Gadfly')");
//    
//    trackReference = disc.getTrackReferences().get(3);
//    track = trackReference.getTrack();
//    track.setTitle("Scenes From Romeo And Juliet ~ Ballet");
    
//    
//    Disc newDisc = album.getDiscs().get(1);
//    trackReference = disc.getTrackReferences().remove(19);
//    newDisc.getTrackReferences().add(0, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    newDisc.getTrackReferences().add(1, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    newDisc.getTrackReferences().add(2, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    trackReference.getTrack().setTitle("D.J.");
//    newDisc.getTrackReferences().add(3, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    newDisc.getTrackReferences().add(4, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    newDisc.getTrackReferences().add(5, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    newDisc.getTrackReferences().add(6, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    trackReference.getTrack().setTitle("Scary Monsters (And Super Creeps)");
//    newDisc.getTrackReferences().add(7, trackReference);
//    trackReference = disc.getTrackReferences().remove(19);
//    trackReference.getTrack().setTitle("Under Pressure");
//    newDisc.getTrackReferences().add(8, trackReference);
//    
//    
//    trackReference = newDisc.getTrackReferences().get(15);
//    track = trackReference.getTrack();
//    track.setTitle("Dancing In The Street");
    
    
//    artist = MediaDbUtil.getArtist(mediaDb, "Evanescence");
//    album = MediaDbUtil.getAlbum(mediaDb, null, artist, "The Open Door");
//    disc = album.getDiscs().getFirst();
//    
//    trackReference = disc.getTrackReferences().get(6);
//    track = trackReference.getTrack();
//    track.setTitle("Lacrymosa");
//    
//    
//    artist = MediaDbUtil.getArtist(mediaDb, "Harry Sacksioni");
//    album = MediaDbUtil.getAlbum(mediaDb, null, artist, "Harry Sacksioni Speciale Compilatie");
//    disc = album.getDiscs().getFirst();
//
//    trackReference = disc.getTrackReferences().get(13);
//    track = trackReference.getTrack();
//    track.setTitle("Johan En Pirrewiet");
//
//    
//    
//    artist = MediaDbUtil.getArtist(mediaDb, "Iggy Pop");
//    album = MediaDbUtil.getAlbum(mediaDb, null, artist, "Brick By Brick");
//    disc = album.getDiscs().getFirst();
//    
//    trackReference = disc.getTrackReferences().get(13);
//    track = trackReference.getTrack();
//    track.setTitle("Livin' On The Edge Of The Night");
//    
//    
    CreateAlbumFromAlbumTracksWindow w = new CreateAlbumFromAlbumTracksWindow(customization);
    w.scanAlbumFolder("D:\\Music\\zSoundtracks\\1987 - Ennio Morricone - Het Gebeurde In Het Westen", null);
//    w.scanAlbumFolder("D:\\Music\\E\\Eagles\\1987 - Eagles - The Legend Of Eagles");
//    w.scanAlbumFolder("D:\\Music\\E\\Eurythmics\\1991 - Eurythmics - Greatest Hits");
//    w.scanAlbumFolder("D:\\Music\\E\\Evanescence\\2003 - Evanescence - Fallen");
//    w.scanAlbumFolder("D:\\Music\\E\\Evanescence\\2006 - Evanescence - The Open Door");
//    w.scanAlbumFolder("D:\\Music\\F\\Fotheringay\\1970 - Fotheringay - Fotheringay");
//    w.scanAlbumFolder("D:\\Music\\G\\Genesis\\1973 - Genesis - Selling England By The Pound");
//    w.scanAlbumFolder("D:\\Music\\G\\Genesis\\1990 - Genesis - And The Word Was");
//    w.scanAlbumFolder("D:\\Music\\H\\Harry Sacksioni\\1987 - Harry Sacksioni - Harry Sacksioni Speciale Compilatie");
//    w.scanAlbumFolder("D:\\Music\\I\\Iggy Pop\\1990 - Iggy Pop - Brick By Brick");
//    w.scanAlbumFolder("D:\\Music\\L\\London Symphony Orchestra\\1986 - London Symphony Orchestra - Bolero");
//    w.scanAlbumFolder("D:\\Music\\V\\Various Artists\\1990 - Various Artists - 25 Golden Juke-Box Classics");
//    w.scanAlbumFolder("D:\\Music\\zSoundtracks\\1987 - Ennio Morricone - Het Gebeurde In Het Westen");
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
        buf.append(" for album: ").append(MediaDbUtil.getArtistAndTitle(album));
        LOGGER.severe(buf.toString());
      }
    }
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
      for (TrackReference trackReference: MediaDbUtil.getTrackCollection(mediaDb, collection).getTrackReferences()) {
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
            LOGGER.severe("Bonus track empty: " + MediaDbUtil.getArtistAndTitle(album) + ", " + disc.getTitle() + ", " + trackReference.getTrack().getTitle());
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
