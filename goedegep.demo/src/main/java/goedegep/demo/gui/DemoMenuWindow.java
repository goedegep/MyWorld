package goedegep.demo.gui;

import java.util.logging.Logger;

import goedegep.demo.fontsamples.gui.FontSamplesWindow;
import goedegep.demo.jfx.editor.logic.CompanyService;
import goedegep.demo.jfx.eobjecttreeview.gui.EObjectTreeViewDemo;
import goedegep.demo.jfx.objectcontrols.gui.EditorControlsDemo;
import goedegep.demo.logic.DemoRegistry;
import goedegep.demo.resources.gui.ImageResourceDemo;
import goedegep.demo.xtree.gui.XTreeDemo;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.resources.ImageSize;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

/**
 * This class provides demo's for the goedegep libraries.
 * <p>
 */
public class DemoMenuWindow extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(DemoMenuWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private final static String WINDOW_TITLE = "Demo";
  
  private ComponentFactoryFx componentFactory;
  private DemoRegistry demoRegistry;
  
  /**
   * Constructor for the DemoMenuWindow.
   * 
   * @param customization the GUI customization.
   */
  public DemoMenuWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    demoRegistry = DemoRegistry.getInstance();
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    show();
  }
  
  
  /**
   * Create the main window of the application.
   * <p>
   * Layout:
   * Top level is VBox:
   */
  private void createGUI() {
    VBox topLevelVBox = componentFactory.createVBox();
    
    // Menu bar
    topLevelVBox.getChildren().add(createMenuBar());
    
    Scene scene = new Scene(topLevelVBox, 1200, 950);
    setScene(scene);
  }
  
  /**
   * Create the menu bar for this window.
   * @param developmentMode if true, the application is in development mode, in which case extra menu options may be available.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    MenuItem menuItem;

    // Resources
    menu = componentFactory.createMenu("Resources");
    
    menuItem = componentFactory.createMenuItem("ImageResource");
    menuItem.setOnAction((_) -> new ImageResourceDemo(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    // Util
    menu = componentFactory.createMenu("Util");
    
    menuItem = componentFactory.createMenuItem("XTree");
    menuItem.setOnAction((_) -> new XTreeDemo(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    // Jfx
    menu = componentFactory.createMenu("Jfx");
    
    // Jfx:ObjectControls
    menuItem = componentFactory.createMenuItem("EditorControls");
    menuItem.setOnAction((_) -> new EditorControlsDemo(customization));
    menu.getItems().add(menuItem);
    
    // Jfx:Editor
    Menu editorMenu = componentFactory.createMenu("Editor");
    
    // Jfx:Editor/Step 1
    menuItem = componentFactory.createMenuItem("Editor step1");
    menuItem.setOnAction((_) -> {
      CompanyService companyService = new CompanyService();
      goedegep.demo.jfx.editor.step1.gui.CompanyEditor.newInstance(customization, companyService).show();
      
    });
    editorMenu.getItems().add(menuItem);
    
    menu.getItems().add(editorMenu);
    
    // Jfx:EObjectTreeView
    menuItem = componentFactory.createMenuItem("EObjectTreeView");
    menuItem.setOnAction((_) -> new EObjectTreeViewDemo(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    // Tools
    menu = componentFactory.createMenu("Tools");
    
    menuItem = componentFactory.createMenuItem("Font samples");
    menuItem.setOnAction((_) -> new FontSamplesWindow(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);


    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", _ -> showHelpAboutDialog());

    menuBar.getMenus().add(menu);
    return menuBar;
  }



  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + demoRegistry.getApplicationName(),
        customization.getResources().getApplicationImage(ImageSize.SIZE_3),
        null,
        demoRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + demoRegistry.getVersion() + NEWLINE +
        demoRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + demoRegistry.getAuthor() + 
        (demoRegistry.isDevelopmentMode() ? (NEWLINE + NEWLINE + "Running in development mode!") : "")
        )
        .showAndWait();
  }
  
}
