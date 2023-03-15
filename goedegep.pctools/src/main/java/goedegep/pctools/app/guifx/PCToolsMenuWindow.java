package goedegep.pctools.app.guifx;

import java.util.logging.Logger;

import goedegep.gpx.app.GPXWindow;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.pctools.app.logic.PCToolsRegistry;
import goedegep.pctools.filefinder.guifx.FileFinderWindow;
import goedegep.pctools.filescontrolled.guifx.FilesControlledWindow;
import goedegep.pctools.markdown.guifx.MarkdownViewer;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.util.file.FileUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tim.prune.GpsPrune;
import tim.prune.gui.IconManager;

public class PCToolsMenuWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PCToolsMenuWindow.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE = "PC Tools";
  
  private CustomizationFx customization;
  private String gpxFileToOpen;
  private ComponentFactoryFx componentFactory;
  private PCToolsAppResourcesFx appResources;

  public PCToolsMenuWindow(CustomizationFx customization, String fileToOpen) {
    super(WINDOW_TITLE, customization);
        
    this.customization = customization;
    this.gpxFileToOpen = fileToOpen;
    componentFactory = getComponentFactory();
    appResources = (PCToolsAppResourcesFx) getResources();
    
    createGUI();
    
    setOnShown(e -> {
      if (fileToOpen != null) {
        String fileToOpenExtension = FileUtils.getFileExtension(fileToOpen);
        if (".gpx".equals(fileToOpenExtension)) {
          showGPXWindow(fileToOpen);
        } else if (".md".equals(fileToOpenExtension)) {
          new MarkdownViewer(customization, fileToOpen);
        }
      }
      
    });
    
    show();
    
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    // The scene
    //   center is mainLayout - a background image with the tool buttons on top.
    BorderPane rootPane = new BorderPane();
    
    rootPane.setTop(createMenuBar());

    // mainLayout - the application picture with a gridPane for the tool buttons.
    StackPane mainLayout = new StackPane();
    
    // Add the background image
    ImageView backGroundImageView = new ImageView(appResources.getPicture());
    backGroundImageView.setPreserveRatio(true);
    backGroundImageView.setSmooth(true);
    mainLayout.getChildren().add(backGroundImageView);
    
    // Add the buttons
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(50, 50, 50, 50));
    
    // The tool buttons
    Button toolButton;
    
    toolButton = componentFactory.createToolButton("Files Controlled", null, "Controleer de disk structuur");
    toolButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Stage stage = new FilesControlledWindow(customization);
        stage.centerOnScreen();
        stage.show();
      }
      
    });
    gridPane.add(toolButton, 0, 0);
    
    toolButton = componentFactory.createToolButton("Bestanden zoeker", null, "Zoek specifieke bestanden");
    toolButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Stage stage = new FileFinderWindow(customization);
        stage.centerOnScreen();
        stage.show();
      }
      
    });
    gridPane.add(toolButton, 1, 0);
    
    Image gpsPruneImage = IconManager.getImage("window_icon_72.png");
    toolButton = componentFactory.createToolButton("GpsPrune", gpsPruneImage, "Edit gpx file");
    toolButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showGpsPruneWindow();
      }
      
    });
    gridPane.add(toolButton, 2, 0);
    
    toolButton = componentFactory.createToolButton("GPX Editor", ImageResource.GPX.getImage(), "Edit gpx file");
    toolButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showGPXWindow(null);
      }
      
    });
    gridPane.add(toolButton, 3, 0);
    
    toolButton = componentFactory.createToolButton("Markdown Viewer", ImageResource.MARKDOWN.getImage(), "View the content of a Markdown file");
    toolButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        new MarkdownViewer(customization, null);
      }
      
    });
    gridPane.add(toolButton, 0, 1);
   
    mainLayout.getChildren().add(gridPane);
        
    rootPane.setCenter(mainLayout);

    setScene(new Scene(rootPane));
  }
  

  /**
   * Create the menu bar.
   * 
   * @return the menu bar.
   */
  private MenuBar createMenuBar() {
    MenuBar    menuBar = new MenuBar();
    Menu       menu;
    
    if (PCToolsRegistry.developmentMode) {
      // Bestand menu
      menu = componentFactory.createMenu("Bestand");

      // Bestand: property descriptors bewerken
      MenuUtil.addMenuItem(menu, "Property Descriptors bewerken", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });

      // Bestand: properties bewerken
      MenuUtil.addMenuItem(menu, "Properties bewerken", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertiesEditor();
        }
      });

      menuBar.getMenus().add(menu);
    }

    
    // Help menu
    menu = componentFactory.createMenu("Help");

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
   * Show the standard PropertyDescriptors editor.
   */
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, PCToolsRegistry.propertyDescriptorsResource);
  }
  
  /**
   * Show the standard Properties editor.
   */
  private void showPropertiesEditor() {
    new PropertiesEditor("PC Tools Properties Editor", customization, PCToolsRegistry.propertyDescriptorsResource, PCToolsRegistry.customPropertiesFile);
  }
  
  /**
   * Show the GpsPrune window.
   */
  private void showGpsPruneWindow() {
    int nrOfArguments = (gpxFileToOpen == null) ? 1 : 2;
    String[] args = new String[nrOfArguments];
    args[0] = "--lang=en";
    if (gpxFileToOpen != null) {
      args[1] = gpxFileToOpen;
    }
    GpsPrune.main(args);
  }
  
  /**
   * Show the GPX window.
   */
  private void showGPXWindow(String fileToOpen) {
    new GPXWindow(customization, fileToOpen);
  }
  
  /**
   * Show the Help - About dialog.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + WINDOW_TITLE,
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        PCToolsRegistry.shortProductInfo + NEWLINE +
        "Versie: " + PCToolsRegistry.version + NEWLINE +
        PCToolsRegistry.copyrightMessage + NEWLINE +
        "Auteur: " + PCToolsRegistry.author)
        .showAndWait();
  }
}