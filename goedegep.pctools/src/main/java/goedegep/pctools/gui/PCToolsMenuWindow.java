package goedegep.pctools.gui;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.pctools.filefinder.guifx.FileFinderWindow;
import goedegep.pctools.filescontrolled.guifx.FilesControlledWindow;
import goedegep.pctools.logic.PCToolsRegistry;
import goedegep.pctools.svc.PCToolsService;
import goedegep.resources.ImageSize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PCToolsMenuWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PCToolsMenuWindow.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE = "PC Tools";
  
  private PCToolsRegistry pcToolsRegistry;
  private PCToolsAppResourcesFx appResources;

  public PCToolsMenuWindow(CustomizationFx customization, String fileToOpen) {
    super(customization, WINDOW_TITLE);
        
    pcToolsRegistry = PCToolsRegistry.getInstance();
    appResources = (PCToolsAppResourcesFx) getResources();
    
    createGUI();
    
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
    
    if (pcToolsRegistry.isDevelopmentMode()) {
      // Bestand menu
      menu = componentFactory.createMenu("Bestand");

      // Bestand: property descriptors bewerken
      MenuUtil.addMenuItem(menu, "Property Descriptors bewerken", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          PCToolsService.getInstance().showPropertyDescriptorsEditor();
        }
      });

      // Bestand: properties bewerken
      MenuUtil.addMenuItem(menu, "Properties bewerken", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          PCToolsService.getInstance().showPropertiesEditor();
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
   * Show the Help - About dialog.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + pcToolsRegistry.getApplicationName(),
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        pcToolsRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + pcToolsRegistry.getVersion() + NEWLINE +
        pcToolsRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + pcToolsRegistry.getAuthor() + 
        (pcToolsRegistry.isDevelopmentMode() ? (NEWLINE + NEWLINE + "Running in development mode!") : "")
        )
        .showAndWait();
  }
}