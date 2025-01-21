package goedegep.media.mediadb.app.guifx;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
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
import goedegep.jfx.browser.BrowserWindow;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.albumeditor.guifx.AlbumEditor;
import goedegep.media.mediadb.albumeditor.guifx.TrackEditor;
import goedegep.media.mediadb.app.MediaDbAppLauncher;
import goedegep.media.mediadb.app.MediaDbAppUtil;
import goedegep.media.mediadb.app.MediaDbChecker;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.app.MediaDbToDiscLocationMap;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.AlbumOnDiscInfo;
import goedegep.media.musicfolder.MusicFolderContent;
import goedegep.resources.ImageSize;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
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

  private MediaDbService mediaDbService;
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
   * @param mediaDbService 
   */
  public MediaDbWindow(CustomizationFx customization, MediaDbService mediaDbService) {
    super(customization, WINDOW_TITLE);

    this.mediaDbService = mediaDbService;
    appResources = customization.getResources();
    mediaDbResource = mediaDbService.getMediaDbResource();
    mediaDb = mediaDbService.getMediaDb();

    allErrors = new ArrayList<>();
    
    tempFixProblems();

    // Check the media database TODO if tempFixProblems() isn't needed anymore, move this check to the launcher.
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
            LOGGER.severe("No MyTrackInfo for Track: " + trackReference.getTrack().getTitle() + " of album: " + album.getArtistAndTitle());
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
    if (MediaRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          MediaDbAppLauncher.showPropertyDescriptorsEditor(customization);
        }
      });

      // File: Edit User Settings
      MenuUtil.addMenuItem(menu, "Edit User Settings", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          MediaDbAppLauncher.showUserSettingsEditor(customization);
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
    
    controlPanel.getChildren().addAll(albumsButton, tracksButton, artistsButton);
    
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
  
  void openAlbumInAlbumDetailsWindow() {
    AlbumDetailsWindow albumDetailsWindow = MediaDbAppLauncher.openAlbumDetailsWindow(customization, mediaDbService, trackDiscLocationMap, albumsTable);
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
