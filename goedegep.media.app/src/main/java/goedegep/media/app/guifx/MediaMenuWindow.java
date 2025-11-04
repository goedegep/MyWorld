package goedegep.media.app.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.media.common.IMediaService;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.app.guifx.DuneWindow;
import goedegep.media.mediadb.app.guifx.MusicFolderWindow;
import goedegep.media.mediadb.app.guifx.VideoDbWindow;
import goedegep.media.photo.photomapview.guifx.PhotoEditor;
import goedegep.media.photo.photomapview.guifx.PhotoMapView;
import goedegep.media.photo.photoshow.guifx.PhotoShowBuilder;
import goedegep.media.photo.photoshow.guifx.PhotoShowViewer;
import goedegep.resources.ImageSize;
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
import javafx.stage.Stage;

/**
 * This class provides the menu window for the different media related applications.
 * <p>
 * At startup this class loads the media database, so it can be passed on to the applications using it.<br/>
 * Saving the media database is also done via this window.
 */
public class MediaMenuWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MediaMenuWindow.class.getName());
  private static final String WINDOW_TITLE   = "Media";
  private static final String NEWLINE = System.getProperty("line.separator");
//  private static final int MAX_NR_OF_PICTURES_IN_COLLAGE = 30;
//  private static final int MIN_NR_OF_PICTURES_IN_COLLAGE = 5;
//  private static final int WINDOW_WIDTH = 1920 / 2;
  
  private MediaRegistry mediaRegistry;
  private IMediaService iMediaService;
  private MediaAppResourcesFx appResources;
  
  
  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   */
  public MediaMenuWindow(CustomizationFx customization, IMediaService iMediaService) {
    super(customization, WINDOW_TITLE);
    
    this.iMediaService = iMediaService;
    mediaRegistry = MediaRegistry.getInstance();
    appResources = (MediaAppResourcesFx) getResources();
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The root layout is a BorderPane.<br/>
   * The top is the menu bar.
   * The center is a background image, with the application buttons on top.
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    
    rootLayout.setTop(createMenuBar());

    StackPane mainLayout = new StackPane();
    
    // Add the background image
//    Canvas backGroundImage = MediaCollageCreator.createCollage(WINDOW_WIDTH,MIN_NR_OF_PICTURES_IN_COLLAGE, MAX_NR_OF_PICTURES_IN_COLLAGE, mediaDb);
//    if (backGroundImage == null) {
    Canvas backGroundImage = new Canvas(600, 600);
    GraphicsContext gc = backGroundImage.getGraphicsContext2D();
    gc.setGlobalAlpha(0.4);
    gc.drawImage(appResources.getPicture(), 0, 0, 600, 600);
//    }
    mainLayout.getChildren().add(backGroundImage);
        
    // Add the buttons
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(50, 50, 50, 50));
    
    Button applicationButton;
    
    // First row: Music
    applicationButton = componentFactory.createToolButton("Music database", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Music database window");
    applicationButton.setOnAction((_) -> iMediaService.showMediaDbWindow());
    grid.add(applicationButton, 0, 0);
    
    applicationButton = componentFactory.createToolButton("Music Folder", appResources.getMusicFolderImage(), "Open the Music Folder window");
    applicationButton.setOnAction((_) -> showMusicFolderWindow());
    grid.add(applicationButton, 1, 0);
    
    // Second row: Photos
    applicationButton = componentFactory.createToolButton("Photoshow builder", appResources.getApplicationImage(ImageSize.SIZE_0), "Start the Photoshow builder");
    applicationButton.setOnAction((_) -> new PhotoShowBuilder(customization));
    grid.add(applicationButton, 0, 1);
    
    applicationButton = componentFactory.createToolButton("Photoshow viewer", appResources.getApplicationImage(ImageSize.SIZE_0), "Start the Photoshow viewer");
    applicationButton.setOnAction((_) -> startPhotoShow());
    grid.add(applicationButton, 1, 1);
    
    applicationButton = componentFactory.createToolButton("Photo Map View", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Photo Map View");
    applicationButton.setOnAction((_) -> new PhotoMapView(customization));
    grid.add(applicationButton, 2, 1);
    
    applicationButton = componentFactory.createToolButton("Photo Editor", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the Photo Editor");
    applicationButton.setOnAction((_) -> new PhotoEditor(customization));
    grid.add(applicationButton, 3, 1);
    
    // Third row: Videos
    applicationButton = componentFactory.createToolButton("Video database", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the video database window");
    applicationButton.setOnAction((_) -> showFilmDbWindow());
    grid.add(applicationButton, 0, 2);
    
    // Fourth row: Other    
    applicationButton = componentFactory.createToolButton("Dune", appResources.getDuneImage(), "Open the Dune media player window");
    applicationButton.setOnAction((_) -> showDuneWindow());
    grid.add(applicationButton, 0, 3);
    
    mainLayout.getChildren().add(grid);
        
    rootLayout.setCenter(mainLayout);

    setScene(new Scene(rootLayout));
  }
  
  /**
   * Create the menu bar.
   * 
   * @return the created menu bar.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;

    // File menu
    menu = new Menu("File");
    
    
    // File: Edit Properties
    MenuUtil.addMenuItem(menu, "Edit Properties", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        iMediaService.showPropertiesEditor();
      }
    });
    
    // File: Edit Property Descriptors
    if (mediaRegistry.isDevelopmentMode()) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          iMediaService.showPropertyDescriptorsEditor();
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
  
  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + mediaRegistry.getApplicationName(),
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        mediaRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + mediaRegistry.getVersion() + NEWLINE +
        mediaRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + mediaRegistry.getAuthor())
        .showAndWait();
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
    
    
    Stage stage = new VideoDbWindow(customization, iMediaService);
    stage.show();
  }
  
  /**
   * Show the Dune window.
   */
  private void showDuneWindow() {
    Stage stage = new DuneWindow(customization);
    stage.show();
  }
  
  /**
   * Show the photo show window.
   */
  private void startPhotoShow() {
    new PhotoShowViewer(customization);
  }
}
