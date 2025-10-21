package goedegep.pctools.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.pctools.app.guifx.PCToolsMenuWindow;
import goedegep.pctools.app.logic.PCToolsRegistry;
import goedegep.properties.app.PropertiesHandler;
import goedegep.util.RunningInEclipse;
import javafx.stage.Stage;

public class PCToolsService extends Service {
  
  private static final String PC_TOOLS_CONFIGURATION_FILE = "PCToolsConfiguration.xmi";

  private static PCToolsService instance = null;
  
  private static CustomizationFx customization;
  
  public static PCToolsService getInstance() {
    if (instance == null) {
      instance = new PCToolsService();
      instance.initialize();
      
      try {
        // Read the properties, which are stored in the registry.
        URL url = instance.getClass().getResource(PCToolsRegistry.propertyDescriptorsFile);
        PropertiesHandler.handleProperties(url, null);

        // Read the customization info.
        url = instance.getClass().getResource(PC_TOOLS_CONFIGURATION_FILE);
        customization = CustomizationsFx.readCustomization(url);
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
}
