package goedegep.demo.guifx;

import java.util.logging.Logger;

import goedegep.demo.jfx.eobjecttreeview.guifx.EObjectTreeViewDemo;
import goedegep.demo.jfx.objectcontrols.guifx.ObjectControlsDemo;
import goedegep.demo.resources.guifx.ImageResourceDemo;
import goedegep.demo.xtree.guifx.XTreeDemo;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
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
  
  private final static String WINDOW_TITLE = "Demo";
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  public DemoMenuWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
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
    menuItem.setOnAction((e) -> new ImageResourceDemo(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    // Util
    menu = componentFactory.createMenu("Util");
    
    menuItem = componentFactory.createMenuItem("XTree");
    menuItem.setOnAction((e) -> new XTreeDemo(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    // Jfx
    menu = componentFactory.createMenu("Jfx");
    
    // Jfx:ObjectControls
    menuItem = componentFactory.createMenuItem("ObjectControls");
    menuItem.setOnAction((e) -> new ObjectControlsDemo(customization));
    menu.getItems().add(menuItem);
    
    // Jfx:EObjectTreeView
    menuItem = componentFactory.createMenuItem("EObjectTreeView");
    menuItem.setOnAction((e) -> new EObjectTreeViewDemo(customization));
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
}
