package goedegep.media.mediadb.app.guifx;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.jfx.browser.BrowserWindow;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.albumeditor.guifx.AlbumEditor;
import goedegep.media.mediadb.app.MediaDbAppUtil;
import goedegep.media.mediadb.app.MediaDbChecker;
import goedegep.media.mediadb.app.MediaDbToDiscLocationMap;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
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
import goedegep.media.mediadb.model.util.TrackReferencesIterator;
import goedegep.media.mediadb.trackeditor.guifx.TrackEditor;
import goedegep.media.musicfolder.AlbumOnDiscInfo;
import goedegep.media.musicfolder.MusicFolderContent;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.util.datetime.FlexDate;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
  private static final MediadbFactory FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private AppResourcesFx appResources;

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
   */
  public MediaDbWindow(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);

    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    appResources = customization.getResources();
    
    if (!checkThatMediaDbFileNameIsSetInRegistry()) {
      return;
    }
    
    // Create the MedaDb resource
    mediaDbResource = new EMFResource<>(
        MediadbPackage.eINSTANCE, 
        () -> MediadbFactory.eINSTANCE.createMediaDb(),
        ".xmi",
        true);
    
    loadMediaDb();
    if (mediaDb == null) {
      return;
    }

    allErrors = new ArrayList<>();
    
//    // TODO Temp read artist information from ArtistInformation.xml. Delete if all works well.
//    ArtistInfoHandler artistInfoHandler = new ArtistInfoHandler();
//    artistInfoHandler.setMediaDb(mediaDb);
//    artistInfoHandler.read("D:\\Database\\Muziek\\ArtistInformation\\ArtistInformation.xml");
    

    tempFixProblems();

    // Check the media database
    MediaDbChecker.checkMediaDb(mediaDb, allErrors);

    // Scan the Music Folder: The Main Index folders, the soundtrack folder and the tracks folders.
    MusicFolderContent musicFolderContent = new MusicFolderContent(MediaRegistry.musicDirectory);
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
      
      
      if (!sourceTypeInMyInfo && trackWithoutSourceType  &&  !usesReference  &&  (MediaDbUtil.haveAlbumOnDisc(album)  ||  MediaDbUtil.haveAlbumPartlyOnDisc(album))) {
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
            tracksTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Tracks());
            artistsTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Artists());
          }
      }

    };
    mediaDb.eAdapters().add(eContentAdapter);
    
    mediaDbResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());    
  }
  
  private void tempFixProblems() {
  }
        
  /**
   * Check that the file name for the media database is set in the {@link MediaRegistry}.
   * <p>
   * If the file name is not set, a dialog is shown to inform the user about this and to tell him what to do.
   * 
   * @return true if the file name is set, false otherwise.
   */
  private boolean checkThatMediaDbFileNameIsSetInRegistry() {
    if (MediaRegistry.mediaDbFile != null) {
      return true;
    } else {
      Alert alert = componentFactory.createErrorDialog(
          "There's no filename configured for the file with the media database",
          """
          Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application.
          A restart is needed, because the settings are only read at startup.""");

      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);

      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showUserSettingsEditor();
        }
      });

      return false;
    }
  }
  
  /**
   * Load the media database.
   * <p>
   * The media database is loaded from the file specified in the registry.<br/>
   * If the file doesn't exist, there are two options; either the file name in the registry is incorrect, or the file hasn't been created yet.
   * Therefore a dialog is shown asking the user whether this file shall be created or not. In the latter case the user has to correct the file name in the registry.
   */
  private void loadMediaDb() {
    try {
      mediaDb = mediaDbResource.load(MediaRegistry.mediaDbFile);
    } catch (FileNotFoundException e) {
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          "The file with media information (" + MediaRegistry.mediaDbFile + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" you can't do anything in this screen.");
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
          mediaDb = mediaDbResource.newEObject();
          try {
            mediaDbResource.save(MediaRegistry.mediaDbFile);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      });
    }
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
    
    // File: Convert Database
    if (MediaRegistry.developmentMode) {
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
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      setFilter(newValue);
    });
    controlPanel.getChildren().add(textField);
    
    // Button to open the MediaDbProblemsWindow
    Button openProblemsWindowButton = componentFactory.createButton("Open problem solving screen", "opens the problem solving screen");
    openProblemsWindowButton.setOnAction((e) -> new MediaDbProblemsWindow(customization, mediaDb, allErrors));
    controlPanel.getChildren().add(openProblemsWindowButton);

    Button albumsButton = componentFactory.createButton("Albums", "switch to the albums table");
    Button tracksButton = componentFactory.createButton("Tracks", "switch to the tracks table");
    Button artistsButton = componentFactory.createButton("Artists", "switch to the artists table");
    
    albumsButton.setOnAction((e) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      currentTable = albumsTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(true);
      tracksButton.setDisable(false);
      artistsButton.setDisable(false);
    });
    albumsButton.setDisable(true);
    
    tracksButton.setOnAction((e) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      currentTable = tracksTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(false);
      tracksButton.setDisable(true);
      artistsButton.setDisable(false);
    });
    
    artistsButton.setOnAction((e) -> {
      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
      centerPaneChildren.remove(centerPaneChildren.size() - 1);
      currentTable = artistsTable;
      centerPaneChildren.add(currentTable);
      albumsButton.setDisable(false);
      tracksButton.setDisable(false);
      artistsButton.setDisable(true);
    });
    
//    Button toggleAlbumsTracksButton = componentFactory.createButton("Tracks", "switch from the albums table to the tracks table");
//    toggleAlbumsTracksButton.setOnAction((e) -> {
//      ObservableList<Node> centerPaneChildren = centerPane.getChildren();
//      centerPaneChildren.remove(centerPaneChildren.size() - 1);
//      if (toggleAlbumsTracksButton.getText().equals("Tracks")) {
//        currentTable = tracksTable;
//        toggleAlbumsTracksButton.setText("Albums");
//      } else {
//        currentTable = albumsTable;
//        toggleAlbumsTracksButton.setText("Tracks");
//      }
//      centerPaneChildren.add(currentTable);
//    });
    controlPanel.getChildren().addAll(albumsButton, tracksButton, artistsButton);
    
    return controlPanel;
  }
  
  /**
   * Check and save the media database.
   */
  private void checkAndSaveMediaDb() {
    List<Object> errors = new ArrayList<>();
    MediaDbChecker.checkMediaDb(mediaDb, errors);
    saveMediaDb();
  }

  /**
   * Save the media database to the related file.
   */
  private void saveMediaDb() {    
    EmfUtil.checkCompleteContainment(mediaDb);
    
    if (mediaDbResource != null) {
      try {
        mediaDbResource.save(MediaRegistry.mediaDbFile);
      } catch (IOException e) {        
        componentFactory.createErrorDialog(
            "Saving the media information has failed.",
            "System error message: "  + e.getMessage()
            ).showAndWait();
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
  
  void openArtistEditor() {
    new ArtistDetailsEditor(customization, "Artist editor", mediaDb).runEditor().setObject((Artist) artistsTable.getSelectedObject());
  }

  /**
   * Open the AlbumEditor to enter a new album.
   */
  void openAlbumEditor() {
    new AlbumEditor(customization, mediaDb).runEditor();
  }

  /**
   * Open the TrackEditor to enter a new album.
   */
  void openTrackEditor() {
    new TrackEditor(customization, mediaDb).runEditor();
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
