package goedegep.demo.guifx;

import java.util.logging.Logger;

import goedegep.demo.DemoRegistry;
import goedegep.demo.jfx.editor.CompanyService;
import goedegep.demo.jfx.eobjecttreeview.guifx.EObjectTreeViewDemo;
import goedegep.demo.jfx.objectcontrols.guifx.EditorControlsDemo;
import goedegep.demo.resources.guifx.ImageResourceDemo;
import goedegep.demo.xtree.guifx.XTreeDemo;
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
  
  public DemoMenuWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
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
      goedegep.demo.jfx.editor.step1.CompanyEditor.newInstance(customization, companyService).show();
      
    });
    editorMenu.getItems().add(menuItem);
    
    menu.getItems().add(editorMenu);
    
    // Jfx:EObjectTreeView
    menuItem = componentFactory.createMenuItem("EObjectTreeView");
    menuItem.setOnAction((_) -> new EObjectTreeViewDemo(customization));
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
        "About " + DemoRegistry.applicationName,
        customization.getResources().getApplicationImage(ImageSize.SIZE_3),
        null,
        DemoRegistry.shortProductInfo + NEWLINE +
        "Version: " + DemoRegistry.version + NEWLINE +
        DemoRegistry.copyrightMessage + NEWLINE +
        "Author: " + DemoRegistry.author)
        .showAndWait();
  }
  
}
