package goedegep.pctools.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.pctools.app.guifx.PCToolsAppResourcesFx;
import goedegep.pctools.app.guifx.PCToolsMenuWindow;
import goedegep.pctools.app.logic.PCToolsRegistry;
import goedegep.properties.app.PropertiesHandler;
import goedegep.util.RunningInEclipse;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PCToolsService extends Service {

  private static PCToolsService instance = null;
  
  public static PCToolsService getInstance() {
    if (instance == null) {
      instance = new PCToolsService();
      instance.initialize();
      
      try {
        // Read the properties, which are stored in the registry.
        URL url = instance.getClass().getResource(PCToolsRegistry.propertyDescriptorsFile);
        PropertiesHandler.handleProperties(url, null);
      } catch (IOException e) {
        JfxApplication.reportException(null, e);
      }
    }
    return instance;
  }
  
  public void showPCToolsMenuWindow() {
    Stage stage = new PCToolsMenuWindow(customization, null);
    stage.centerOnScreen();
    stage.show();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private PCToolsService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      PCToolsRegistry.developmentMode = true;
    }
    
  }

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    PCToolsRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("PCToolsApplication.properties")) {
        props.load(in);
        
        PCToolsRegistry.version = props.getProperty("pctools.app.version");
        PCToolsRegistry.applicationName = props.getProperty("pctools.app.name");
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
}
