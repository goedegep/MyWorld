package goedegep.media.app.guifx;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import goedegep.appgen.EMFResource;
import goedegep.appgen.ImageSize;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.media.app.MediaRegistry;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.app.base.MediaCollageCreator;
import goedegep.media.fotomapview.guifx.PhotoMapView;
import goedegep.media.fotoshow.app.guifx.FullScreenViewer;
import goedegep.media.fotoshow.app.guifx.PhotoShowBuilder;
import goedegep.media.mediadb.albuminfo.AlbumInfoFilesReader;
import goedegep.media.mediadb.app.guifx.DuneWindow;
import goedegep.media.mediadb.app.guifx.MediaDbWindow;
import goedegep.media.mediadb.app.guifx.MusicFolderWindow;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.properties.app.guifx.PropertiesEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MediaMenuWindow.class.getName());
  private static final String WINDOW_TITLE   = "Media";
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final int MAX_NR_OF_PICTURES_IN_COLLAGE = 30;
  private static final int MIN_NR_OF_PICTURES_IN_COLLAGE = 5;
  private static final int WINDOW_WIDTH = 1920 / 2;
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private MediaAppResourcesFx appResources;
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
    
    mediaDb = MediadbFactory.eINSTANCE.createMediaDb();
    AlbumInfoFilesReader albumInfoFilesReader = new AlbumInfoFilesReader(mediaDb);
    List<Object> errors = new ArrayList<>();
    albumInfoFilesReader.readAlbumInfoFiles(errors, MediaRegistry.albumInfoDirectory);

    createGUI();
  }
  
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
    
    applicationButton = componentFactory.createToolButton("Media database", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Media database window");
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
    
    applicationButton = componentFactory.createToolButton("Dune", appResources.getDuneImage(), "Open the Dune media player window");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showDuneWindow();
      }
      
    });
    grid.add(applicationButton, 2, 0);
    
    applicationButton = componentFactory.createToolButton("Photoshow builder", appResources.getApplicationImage(ImageSize.SIZE_0), "Start the Photo Show builder");
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        new PhotoShowBuilder(customization);
      }
      
    });
    grid.add(applicationButton, 0, 1);
    
    applicationButton = componentFactory.createToolButton("Photoshow", appResources.getApplicationImage(ImageSize.SIZE_0), "Start a Photo Show");
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
    
    mainLayout.getChildren().add(grid);
        
    rootLayout.setCenter(mainLayout);

    setScene(new Scene(rootLayout));
  }
  
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;

    // File menu
    menu = new Menu("File");
    
    // File: property descriptors bewerken
    if (MediaRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });
      
      MenuUtil.addMenuItem(menu, "Edit Properties", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertiesEditor();
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

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createInformationDialog(
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
    Stage stage = new MediaDbWindow(customization);
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
   * Show the Dune window.
   */
  private void showDuneWindow() {
    Stage stage = new DuneWindow(customization);
    stage.show();
  }
  
  private void startPhotoShow() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select Photo Show");
    if (MediaRegistry.photosFolder != null) {
      fileChooser.setInitialDirectory(new File(MediaRegistry.photosFolder));
    }
    File file = fileChooser.showOpenDialog(this);
    if (file != null) {
      
      try {
        EMFResource<PhotoShowSpecification> emfResource = new EMFResource<PhotoShowSpecification>(
            PhotoShowPackage.eINSTANCE,
            () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification());
        PhotoShowSpecification photoShowSpecification = emfResource.load(file.getAbsolutePath());
        List<String> photosToShow = photoShowSpecification.getPhotosToShow();
        new FullScreenViewer(photosToShow);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (WrappedException wrappedException) {
        componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + file.getAbsolutePath() + "'.", wrappedException).show();
//        new JavaFxExceptionAlert(customization, null, "THE HEADER", wrappedException).showAndWait();
      }
    }
  }
}
