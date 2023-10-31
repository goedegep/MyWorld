package goedegep.media.mediadb.app.guifx;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.jfx.browser.BrowserWindow;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.albumeditor.guifx.AlbumEditor;
import goedegep.media.mediadb.albuminfo.AlbumInfoFilesReader;
import goedegep.media.mediadb.app.AlbumDiscLocationInfo;
import goedegep.media.mediadb.app.MediaDbAppUtil;
import goedegep.media.mediadb.app.MediaDbToDiscLocationMap;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.MusicFolderContent;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
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
 * Currently the media database (type MediaDb) isn't saved as an EMF resource yet. Instead:
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
  private static final MediadbFactory FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private AppResourcesFx appResources;

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
   * Current table, either albumsTable or trackTable
   */
  private EObjectTable<?> currentTable;

  /**
   * This map provides the on disc location for single disc albums.
   * TODO shouldn't this be renamed to singleDiscAlbumToAlbumDiscLocationInfoMap?
   */
  private Map<Album, AlbumDiscLocationInfo> albumToMusicFolderLocationMap;
  
  /**
   * This map provides the on disc location for the discs of multi-disc albums.
   * TODO shouldn't this be renamed to albumDiscToAlbumDiscLocationInfoMap?
   */
  private Map<Disc, AlbumDiscLocationInfo> albumDiscToMusicFolderLocationMap;
  
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
   */
  public MediaDbWindow(CustomizationFx customization, MediaDb mediaDb) {
    super(WINDOW_TITLE, customization);

    this.customization = customization;
    this.mediaDb = mediaDb;
    componentFactory = customization.getComponentFactoryFx();
    appResources = customization.getResources();

//    // Start with an empty media database
//    mediaDb = FACTORY.createMediaDb();
//
    allErrors = new ArrayList<>();
//
//    // Add album information from the Album Info files.
//    new AlbumInfoFilesReader(mediaDb).readAlbumInfoFiles(allErrors, MediaRegistry.albumInfoDirectory);
//    
//    // Add track information from the Tracks.xml file
//    String tracksInfoFileName = MediaRegistry.albumInfoDirectory + "\\..\\Tracks\\Tracks.xml";
//    List<TrackInfoErrorInfo> trackInfoErrors = new TrackInfoHandler().setMediaDb(mediaDb).read(tracksInfoFileName);
//    allErrors.addAll(trackInfoErrors);
//
//    // Check the media database
//    MediaDbAppUtil.checkMediaDb(mediaDb, allErrors);

    // Scan the Music Folder: The Main Index folders, the soundtrack folder and the tracks folders.
    MusicFolderContent musicFolderContent = new MusicFolderContent(MediaRegistry.musicDirectory);
    boolean allOk = musicFolderContent.gatherMusicFolderContent();
    if (!allOk) {
      allErrors.addAll(musicFolderContent.getErrors());
    }
    
    // Link the information in the media database to the information of the Music Folder.    
    MediaDbToDiscLocationMap mediaDbToDiscLocationMap = new MediaDbToDiscLocationMap(mediaDb, musicFolderContent);
    allOk = mediaDbToDiscLocationMap.createMediaDbToDiscLocationMap(true);
    if (!allOk) {
      allErrors.addAll(mediaDbToDiscLocationMap.getErrors());
    }
    albumToMusicFolderLocationMap = mediaDbToDiscLocationMap.getAlbumToMusicFolderLocationMap();
    albumDiscToMusicFolderLocationMap = mediaDbToDiscLocationMap.getAlbumDiscToMusicFolderLocationMap();
    trackDiscLocationMap = mediaDbToDiscLocationMap.getTrackDiscLocationMap();

    createGUI();
    
    for (Album album: mediaDb.getAlbums()) {
      boolean sourceTypeInMyInfo = false;
      MyInfo myInfo = album.getMyInfo();
      boolean usesReference = !myInfo.getAlbumReferences().isEmpty();
      List<MediumInfo> mediumInfos = myInfo.getIHaveOn();
      for (MediumInfo mediumInfo: mediumInfos) {
        List<InformationType> sourceTypes = mediumInfo.getSourceTypes();
        if (sourceTypes.size() == 0) {
//          LOGGER.severe("no SourceType in MyInfo of album: " + album.getArtistAndTitle());
        } else {
          sourceTypeInMyInfo = true;
          if (sourceTypes.size() > 1) {
            LOGGER.severe("more than one SourceType in MyInfo of album: " + album.getArtistAndTitle());
          }
        }
      }
      
      boolean trackWithoutSourceType = false;
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
          if (myTrackInfo == null) {
            LOGGER.severe("No MyTrackInfo for Track: " + trackReference.getTrack().getTitle() + " of album: " + album.getArtistAndTitle());
            trackWithoutSourceType = true;
          } else {
            List<MediumInfo> mediumInfos2 = myTrackInfo.getIHaveOn();
            for (MediumInfo mediumInfo: mediumInfos2) {
              List<InformationType> sourceTypes = mediumInfo.getSourceTypes();
              if (sourceTypes.size() == 0) {
                trackWithoutSourceType = true;
//                LOGGER.severe("no SourceType for Track: " + trackReference.getTrack().getTitle() + " of album: " + album.getArtistAndTitle());
              } else if (sourceTypes.size() > 1) {
                LOGGER.severe("more than one SourceType for Track: " + trackReference.getTrack().getTitle() + " of album: " + album.getArtistAndTitle());
              }
            }
          }
        }
      }
      
      
      if (!sourceTypeInMyInfo && trackWithoutSourceType  &&  !usesReference  &&  (MediaDbUtil.haveAlbumOnDisc(album, null)  ||  MediaDbUtil.haveAlbumPartlyOnDisc(album))) {
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
        LOGGER.info("Change detected: " + notification.toString());
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }
        
        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
          if (MEDIA_DB_PACKAGE.getMediaDb_Albums().equals(feature)) {
            albumsTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Albums());
            tracksTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Tracks());
          }
      }

    };
    mediaDb.eAdapters().add(eContentAdapter);

//    init();
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
    
    albumsTable = new AlbumsTable(customization, this, mediaDb, albumToMusicFolderLocationMap, albumDiscToMusicFolderLocationMap, trackDiscLocationMap);
    albumsTable.setMinHeight(800);
    currentTable = albumsTable;
    centerPane.getChildren().add(albumsTable);
    tracksTable = new TracksTable(customization, this, mediaDb, trackDiscLocationMap);
    tracksTable.setMinHeight(800);

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
    
    
//    // File: Save AlbumInfo files
//    MenuUtil.addMenuItem(menu, "Save AlbumInfo files", new EventHandler<ActionEvent>()  {
//      public void handle(ActionEvent e) {
//        saveMediaDbToAlbumInfoFiles();
//      }
//    });
//    
//    // File: Save AlbumInfo file ...
//    MenuUtil.addMenuItem(menu, "Save AlbumInfo file ...", new EventHandler<ActionEvent>()  {
//      public void handle(ActionEvent e) {
//        saveMediaDbArtistAlbumsToAlbumInfoFile();
//      }
//    });

    // File: Edit Property Descriptors
    if (MediaRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });

      // File: Edit User Settings
      MenuUtil.addMenuItem(menu, "Edit User Settings", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showUserSettingsEditor();
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
        openAlbumDetailsEditor();
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
        new BrowserWindow("MediaDbHelp", customization, "http://mydigitallife.rf.gd/myworld-user-manual/media/mediadb-the-media-database/");
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
  
  private Node createControlPanel() {
    HBox controlPanel = componentFactory.createHBox(18, 12);
    
    // Label: "Filter op de tekst:"
    Label label = componentFactory.createLabel("Filter op de tekst:");
    controlPanel.getChildren().add(label);
    
    // TextField: for the text to filter on
    TextField textField = componentFactory.createTextField(200, "Voer de tekst in die in de album informatie voor moet komen");
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      setFilter(newValue);
    });
    controlPanel.getChildren().add(textField);
    

    Button openProblemsWindowButton = componentFactory.createButton("Open problem solving screen", "opens the problem solving screen");
    openProblemsWindowButton.setOnAction((e) -> {
      new MediaDbProblemsWindow(customization, mediaDb, allErrors);
    });
    controlPanel.getChildren().add(openProblemsWindowButton);

    Button toggleAlbumsTracksButton = componentFactory.createButton("Tracks", "switch from the albums table to the tracks table");
    toggleAlbumsTracksButton.setOnAction((e) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      if (toggleAlbumsTracksButton.getText().equals("Tracks")) {
        currentTable = tracksTable;
        toggleAlbumsTracksButton.setText("Albums");
      } else {
        currentTable = albumsTable;
        toggleAlbumsTracksButton.setText("Tracks");
      }
      centerPaneChildren.add(currentTable);
    });
    controlPanel.getChildren().add(toggleAlbumsTracksButton);
    
    return controlPanel;
  }
  
  /**
   * Save the information from the <code>mediaDb</code> to a set of AlbumInfo files, one per artist.
   */
  private void saveMediaDbToAlbumInfoFiles() {
    List<Object> errors = new ArrayList<>();
    AlbumInfoFilesReader albumInfoFilesReader = new AlbumInfoFilesReader(mediaDb);
    albumInfoFilesReader.writeAlbumInfoFiles(errors, "C:\\ApplicLocal\\AlbumInfo");
  }
  
  /**
   * Save the information for one artist from the <code>mediaDb</code> to an AlbumInfo file.
   */
  private void saveMediaDbArtistAlbumsToAlbumInfoFile() {
    List<Artist> containerArtists = MediaDbAppUtil.getContainerArtists(mediaDb);
    
    ArtistSelectionDialog artistSelectionDialog = new ArtistSelectionDialog(customization, this, containerArtists);
    Optional<ButtonType> choice = artistSelectionDialog.showAndWait();
    if (choice.isPresent()) {
      Artist artist = artistSelectionDialog.getSelectedArtist();
      if (artist != null) {
        List<Object> errors = new ArrayList<>();
        AlbumInfoFilesReader albumInfoFilesReader = new AlbumInfoFilesReader(mediaDb);
        albumInfoFilesReader.writeAlbumInfoFile(errors, "C:\\ApplicLocal\\AlbumInfo", artist);
      }
    }
  }

  /**
   * Open the PropertyDescriptors editor.
   */
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, MediaRegistry.propertyDescriptorsResource);
  }

  /**
   * Open the User Settings editor.
   */
  private void showUserSettingsEditor() {
    PropertiesEditor propertiesEditor = new PropertiesEditor("Edit Media settings", getCustomization(),
        MediaRegistry.propertyDescriptorsResource, MediaRegistry.customPropertiesFile);
    propertiesEditor.show();
  }


  /**
   * Show only albums that need attention in the table.
   * 
   * @param isSelected
   */
  protected void setOnlyAlbumsThatNeedAttentionFilter(boolean isSelected) {
    if (isSelected) {
      List<Album> albumsThatNeedAttention = MediaDbUtil.getAlbumsThatNeedAttention(mediaDb);
      ObservableList<Album> albumsThatNeedAttentionObservable = FXCollections.observableList(albumsThatNeedAttention);
      albumsTable.setItems(albumsThatNeedAttentionObservable);
    } else {
      ObservableList<Album> albums = FXCollections.observableList(mediaDb.getAlbums());
      albumsTable.setItems(albums);
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
        MediaRegistry.shortProductInfo + NEWLINE +
        "Versie: " + MediaRegistry.version + NEWLINE +
        MediaRegistry.copyrightMessage + NEWLINE +
        "Auteur: " + MediaRegistry.author)
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
  
  void openAlbumDetailsWindow() {
    AlbumDetailsWindow albumDetailsWindow = new AlbumDetailsWindow(customization, mediaDb, trackDiscLocationMap, albumsTable);
    
    
    albumDetailsWindow.setAlbum((Album) albumsTable.getSelectedObject());
  }



  /**
   * Open the AlbumDetailsEditor to enter a new album.
   */
  void openAlbumDetailsEditor() {
    new AlbumEditor(customization, mediaDb, trackDiscLocationMap).runEditor();
//    AlbumDetailsEditorFx albumDetailsEditor = new AlbumDetailsEditorFx(customization, mediaDb);
  }
  /**
   * Create a new Album from the tracks in a folder.
   */
  private void createAlbumFromAlbumTracks() {
    new CreateAlbumFromAlbumTracksWindow(customization);
  }
}
