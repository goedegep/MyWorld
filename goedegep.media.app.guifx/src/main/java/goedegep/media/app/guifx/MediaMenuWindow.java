package goedegep.media.app.guifx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.WrappedException;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.media.app.MediaRegistry;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.app.base.MediaCollageCreator;
import goedegep.media.fotomapview.guifx.PhotoEditor;
import goedegep.media.fotomapview.guifx.PhotoMapView;
import goedegep.media.fotoshow.app.guifx.FullScreenViewer;
import goedegep.media.fotoshow.app.guifx.PhotoShowBuilder;
import goedegep.media.fotoshow.app.guifx.PhotoShowViewer;
import goedegep.media.mediadb.albumeditor.guifx.AlbumEditor;
import goedegep.media.mediadb.app.MediaDbChecker;
import goedegep.media.mediadb.app.derivealbuminfo.DeriveAlbumInfo;
import goedegep.media.mediadb.app.guifx.DuneWindow;
import goedegep.media.mediadb.app.guifx.MediaDbWindow;
import goedegep.media.mediadb.app.guifx.MusicFolderWindow;
import goedegep.media.mediadb.app.guifx.VideoDbWindow;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class provides the menu window for the different media related applications.
 */
public class MediaMenuWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(MediaMenuWindow.class.getName());
  private static final String WINDOW_TITLE   = "Media";
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final int MAX_NR_OF_PICTURES_IN_COLLAGE = 30;
  private static final int MIN_NR_OF_PICTURES_IN_COLLAGE = 5;
  private static final int WINDOW_WIDTH = 1920 / 2;
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private MediaAppResourcesFx appResources;
  private EMFResource<MediaDb> mediaDbResource = null;
  private MediaDb mediaDb;
  
  
  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   */
  public MediaMenuWindow(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    componentFactory = getComponentFactory();
    appResources = (MediaAppResourcesFx) getResources();
    
    
    if (MediaRegistry.mediaDbFile == null) {
      Alert alert = componentFactory.createErrorDialog(
          "There's no filename configured for the file with the media database",
          "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application." +
              NEWLINE +
              "A restart is needed, because the settings are only read at startup.");
      
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor();
        }
      });
      
      return;
    }
    
    mediaDbResource = new EMFResource<>(
        MediadbPackage.eINSTANCE, 
        () -> MediadbFactory.eINSTANCE.createMediaDb(),
        ".xmiT",
        true);
    
    try {
      mediaDb = mediaDbResource.load(MediaRegistry.mediaDbFile + "T");
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          "The file with media information (" + MediaRegistry.mediaDbFile + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" you can't do anything in this screen.");
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
          LOGGER.severe("yes, create file");
          mediaDb = mediaDbResource.newEObject();
          try {
            mediaDbResource.save(MediaRegistry.mediaDbFile);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        } else {
          LOGGER.severe("no, don't create file");
        }
      });
    }
    
//    mediaDb = MediadbFactory.eINSTANCE.createMediaDb();
//    AlbumInfoFilesReader albumInfoFilesReader = new AlbumInfoFilesReader(mediaDb);
//    List<Object> errors = new ArrayList<>();
//    albumInfoFilesReader.readAlbumInfoFiles(errors, MediaRegistry.albumInfoDirectory);

    createGUI();
        
    mediaDbResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());
    
//    mediaDbResource.addNotificationListener(n -> LOGGER.severe(EmfUtil.printNotification(n)));
//    tempForRemoveMyCompilation();
    
//    List<Path> flacFilePaths = new ArrayList<>();
//    Path path = Paths.get("D:\\SoulSeek\\complete\\2021 - A Momentary Lapse Of Reason (Remixed & Updated) (Remastered)", "03 - The Dogs Of War.flac");
//    flacFilePaths.add(path);
//    DeriveAlbumInfo.deriveInfoFromFlacFiles(null, flacFilePaths, mediaDb);
  }
  
//  private void tempForRemoveMyCompilation() {
//    for (int i = 0; i < mediaDb.getAlbums().size(); i++) {
//      Album album = mediaDb.getAlbums().get(i);
//      if (album instanceof MyCompilation myCompilation) {
//        LOGGER.severe("MyCompilation found: " + album.getArtistAndTitle());
//        Album convertedAlbum = convertMyCompilationToAlbum(myCompilation);
//        updateReferences(myCompilation, convertedAlbum);
//        mediaDb.getAlbums().add(i, convertedAlbum);
//        myCompilation.setMyInfo(null);
//        mediaDb.getAlbums().remove(myCompilation);
//      }
//    }
//    
//  }

//  /**
//   * Create a MyCompilation copy of an 'Album'.
//   * 
//   * @param album the Album to be 'copied' into a MyCompilation.
//   * @return a MyCompilation copy of the 'album'.
//   */
//  private Album convertMyCompilationToAlbum(MyCompilation myCompilation) {
//    myCompilation.eCrossReferences();
//    
//    MediadbFactory FACTORY = MediadbFactory.eINSTANCE;
//    
//    Album album = FACTORY.createAlbum();
//    
//    if (myCompilation.isSetArtist()) {
//      album.setArtist(myCompilation.getArtist());
//    }
//    
//    if (myCompilation.isSetDescription()) {
//      album.setDescription(myCompilation.getDescription());
//    }
//    
//    if (myCompilation.isSetDescriptionTitle()) {
//      album.setDescriptionTitle(myCompilation.getDescriptionTitle());
//    }
//    
//    if (myCompilation.isSetId()) {
//      album.setId(myCompilation.getId());
//    }
//    
//    for (String imageBack: myCompilation.getImagesBack()) {
//      album.getImagesBack().add(imageBack);
//    }
//    
//    for (String imageFront: myCompilation.getImagesFront()) {
//      album.getImagesFront().add(imageFront);
//    }
//    
//    for (String imageFrontInside: myCompilation.getImagesFrontInside()) {
//      album.getImagesFrontInside().add(imageFrontInside);
//    }
//    
//    for (String label: myCompilation.getImagesLabel()) {
//      album.getImagesLabel().add(label);
//    }
//    
//    for (MediumType mediumType: myCompilation.getIssuedOnMediums()) {
//      album.getIssuedOnMediums().add(mediumType);
//    }
//    
//    if (myCompilation.isSetMyInfo()) {
//      album.setMyInfo(myCompilation.getMyInfo());
//      album.getMyInfo().setAlbumType(AlbumType.OWN_COMPILATION_ALBUM);
//    } else {
//      throw new RuntimeException("No MyInfo set for album:" + myCompilation.getArtistAndTitle());
//    }
//    
//    for (Player player: myCompilation.getPlayers()) {
//      album.getPlayers().add(player);
//    }
//    
//    if (myCompilation.isSetReleaseDate()) {
//      album.setReleaseDate(myCompilation.getReleaseDate());
//    }
//    
//    if (myCompilation.isSetTitle()) {
//      album.setTitle(myCompilation.getTitle());
//    }
//    
//    List<Disc> discs = new ArrayList<>();
//    for (Disc disc: myCompilation.getDiscs()) {
//      discs.add(disc);
//    }
//    myCompilation.getDiscs().clear();
//    album.getDiscs().addAll(discs);
//    
//    return album;
//  }
  
//  private void updateReferences(MyCompilation myCompilation, Album convertedAlbum) {
//    LOGGER.severe("Object to remove=" + myCompilation.getArtistAndTitle());
//    
//    Resource resource = myCompilation.eResource();
//    if (resource == null) {
//      throw new RuntimeException("Object cannot be deleted as it is not part of a Resource: " + myCompilation.toString());
//    }
//    
//    ResourceSet resourceSet = resource.getResourceSet();
//    
//    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(myCompilation, resourceSet);
//    if (settings.size() != 0) {
//      StringBuffer buf = new StringBuffer();
//      buf.append("There are ");
//      buf.append(settings.size());
//      buf.append(" references to this object.");
//      buf.append(System.getProperty("line.separator"));
//
//      for (Object object: settings) {
//        EStructuralFeature.Setting setting = (EStructuralFeature.Setting) object;
//        EStructuralFeature feature = setting.getEStructuralFeature();
//        String inOrAs = " as ";
//        if (feature.isMany()) {
//          inOrAs = " in ";
//        }
//        EObject referringObject = setting.getEObject();
//        EObject container = referringObject.eContainer();
//        if (container instanceof Album album) {
//          buf.append("Album: ").append(album.getArtistAndTitle()).append(System.getProperty("line.separator"));
//        }
//        buf.append("In: ").append(referringObject.getClass().getName()).append(System.getProperty("line.separator"));
//        buf.append("Feature: ").append(feature.getName()).append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
//      }
//      
//      LOGGER.severe(buf.toString());
//    }
//    
//    
//    for (Object object: settings) {
//      EStructuralFeature.Setting setting = (EStructuralFeature.Setting) object;
//      EStructuralFeature feature = setting.getEStructuralFeature();
//      EObject referringObject = setting.getEObject();
//      
//      if (feature.isMany()) {
//        @SuppressWarnings("unchecked")
//        List<Album> list = (List<Album>) referringObject.eGet(feature);
//        list.remove(myCompilation);
//        list.add(convertedAlbum);
//      } else {
//        referringObject.eUnset(feature);
//      }
//    }
//    
//  }
  
  /**
   * Create the GUI.
   * <p>
   * The root layout is a BorderPane.<br/>
   * The center is a background image, with the application buttons on top.
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    
    rootLayout.setTop(createMenuBar());

    StackPane mainLayout = new StackPane();
    
    // Add the background image
    Canvas backGroundImage = MediaCollageCreator.createCollage(WINDOW_WIDTH,MIN_NR_OF_PICTURES_IN_COLLAGE, MAX_NR_OF_PICTURES_IN_COLLAGE, mediaDb);
    if (backGroundImage == null) {
      backGroundImage = new Canvas();
      GraphicsContext gc = backGroundImage.getGraphicsContext2D();
      gc.drawImage(appResources.getPicture(), 0, 0);
    }
    mainLayout.getChildren().add(backGroundImage);
        
    // Add the buttons
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(50, 50, 50, 50));
    
    Button applicationButton;
    
    // First row: Music
    applicationButton = componentFactory.createToolButton("Music database", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Music database window");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showMusicDbWindow();
      }
      
    });
    grid.add(applicationButton, 0, 0);
    
    applicationButton = componentFactory.createToolButton("Music Folder", appResources.getMusicFolderImage(), "Open the Music Folder window");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showMusicFolderWindow();
      }
      
    });
    grid.add(applicationButton, 1, 0);
    
    // Second row: Photos
    applicationButton = componentFactory.createToolButton("Photoshow builder", appResources.getApplicationImage(ImageSize.SIZE_0), "Start the Photoshow builder");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        new PhotoShowBuilder(customization);
      }
      
    });
    grid.add(applicationButton, 0, 1);
    
    applicationButton = componentFactory.createToolButton("Photoshow viewer", appResources.getApplicationImage(ImageSize.SIZE_0), "Start the Photoshow viewer");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        startPhotoShow();
      }
      
    });
    grid.add(applicationButton, 1, 1);
    
    applicationButton = componentFactory.createToolButton("Photo Map View", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Photo Map View");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        new PhotoMapView(customization);
      }
      
    });
    grid.add(applicationButton, 2, 1);
    
    applicationButton = componentFactory.createToolButton("Photo Editor", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Photo Editor");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        new PhotoEditor(customization);
      }
      
    });
    grid.add(applicationButton, 3, 1);
    
    // Third row: Videos
    applicationButton = componentFactory.createToolButton("Video database", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the video database window");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showFilmDbWindow();
      }
      
    });
    grid.add(applicationButton, 0, 2);
    
    // Fourth row: Other    
    applicationButton = componentFactory.createToolButton("Dune", appResources.getDuneImage(), "Open the Dune media player window");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showDuneWindow();
      }
      
    });
    grid.add(applicationButton, 0, 3);
    
    mainLayout.getChildren().add(grid);
        
    rootLayout.setCenter(mainLayout);

    setScene(new Scene(rootLayout));
  }
  
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
    
    // File: Edit Properties
    MenuUtil.addMenuItem(menu, "Edit Properties", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showPropertiesEditor();
      }
    });
    
    // File: Edit Property Descriptors
    if (MediaRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
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

    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showHelpAboutDialog();
      }
    });

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, MediaRegistry.propertyDescriptorsResource);
  }
  
  private void showPropertiesEditor() {
    new PropertiesEditor("Media properties", customization, MediaRegistry.propertyDescriptorsResource, MediaRegistry.customPropertiesFile);
  }
  
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
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About Vacations",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        MediaRegistry.shortProductInfo + NEWLINE +
        "Version: " + MediaRegistry.version + NEWLINE +
        MediaRegistry.copyrightMessage + NEWLINE +
        "Author: " + MediaRegistry.author)
        .showAndWait();
  }

  /**
   * Show the media database window.
   */
  private void showMusicDbWindow() {
    Stage stage = new MediaDbWindow(customization, mediaDb);
    stage.show();
  }
  
  /**
   * Show the Music Folder window.
   */
  private void showMusicFolderWindow() {
    Stage stage = new MusicFolderWindow(customization);
    stage.show();
  }

  /**
   * Show the film database window.
   */
  private void showFilmDbWindow() {
    
    
    Stage stage = new VideoDbWindow(customization);
    stage.show();
  }
  
  /**
   * Show the Dune window.
   */
  private void showDuneWindow() {
    Stage stage = new DuneWindow(customization);
    stage.show();
  }
  
  private void startPhotoShow() {
    new PhotoShowViewer(customization);
//    FileChooser fileChooser = componentFactory.createFileChooser("Select Photo Show");
//    if (MediaRegistry.photosFolder != null) {
//      fileChooser.setInitialDirectory(new File(MediaRegistry.photosFolder));
//    }
//    File file = fileChooser.showOpenDialog(this);
//    if (file != null) {
//      
//      try {
//        EMFResource<PhotoShowSpecification> emfResource = new EMFResource<PhotoShowSpecification>(
//            PhotoShowPackage.eINSTANCE,
//            () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification(),
//            ".xmi");
//        PhotoShowSpecification photoShowSpecification = emfResource.load(file.getAbsolutePath());
//        List<String> photosToShow = photoShowSpecification.getPhotosToShow();
//        new FullScreenViewer(photosToShow, photoShowSpecification.getTitle());
//      } catch (FileNotFoundException e) {
//        e.printStackTrace();
//      } catch (WrappedException wrappedException) {
//        componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + file.getAbsolutePath() + "'.", wrappedException).show();
////        new JavaFxExceptionAlert(customization, null, "THE HEADER", wrappedException).showAndWait();
//      }
//    }
  }
  
  private void checkAndSaveMediaDb() {
    MediaDbChecker.checkMediaDb(mediaDb, true);
    saveMediaDb();
  }

  /**
   * Save the media information to the related file.
   */
  private void saveMediaDb() {
//    for (Album album: mediaDb.getAlbums()) {
//      
//      for (Disc disc: album.getDiscs()) {
//        for (TrackReference trackReference: disc.getTrackReferences()) {
//          Track track = trackReference.getTrack();
//          if (!mediaDb.getTracks().contains(track)) {
//            LOGGER.severe("Track not in tracks: " + track.toString());
//          } else {
////            LOGGER.severe("Track OK: " + track.toString());
//          }
//        }
//      }
//    }
    
    EmfUtil.checkCompleteContainment(mediaDb);
    
    
    if (mediaDbResource != null) {
      try {
        mediaDbResource.save(MediaRegistry.mediaDbFile + "T");
      } catch (IOException e) {        
        componentFactory.createErrorDialog(
            "Saving the media information has failed.",
            "System error message: "  + e.getMessage()
            ).showAndWait();
      }
    }
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
