package goedegep.pctools.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.pctools.app.guifx.PCToolsAppResourcesFx;
import goedegep.pctools.app.guifx.PCToolsMenuWindow;
import goedegep.pctools.app.logic.PCToolsRegistry;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The PCToolsService class is the main class for the PCTools application.
 * <p>
 * It provides methods to show the menu window, and manages
 * application-wide resources and customization.
 */
public class PCToolsService extends Service {

  /**
   * The singleton instance of the PCToolsService.
   */
  private static PCToolsService instance = null;
  
  private PCToolsRegistry pcToolsRegistry;
  
  /**
   * Get the singleton instance of the PCToolsService.
   * 
   * @return the singleton instance of PCToolsService.
   */
  public static PCToolsService getInstance() {
    if (instance == null) {
      instance = new PCToolsService();
      instance.initialize();      
    }
    
    return instance;
  }
  
  /**
   * Show the PCTools menu window.
   */
  public void showPCToolsMenuWindow() {
    Stage stage = new PCToolsMenuWindow(customization, null);
    stage.centerOnScreen();
    stage.show();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private PCToolsService() {
    pcToolsRegistry = PCToolsRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("PCToolsApplication.properties")) {
        props.load(in);
        
        pcToolsRegistry.setVersion(props.getProperty("pctools.app.version"));
        pcToolsRegistry.setApplicationName(props.getProperty("pctools.app.name"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(238,238,238));
    look.setButtonBackgroundColor(Color.rgb(238,238,238));
    look.setPanelBackgroundColor(Color.rgb(238,238,238));
    look.setListBackgroundColor(Color.rgb(238,238,238));
    look.setLabelBackgroundColor(Color.rgb(200,200,200));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new PCToolsAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return pcToolsRegistry;
  }
}
