package goedegep.media.app;

import java.io.IOException;
import java.net.URL;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.media.app.guifx.MediaMenuWindow;
import goedegep.media.common.MediaRegistry;
import goedegep.properties.app.PropertiesHandler;
import goedegep.util.RunningInEclipse;
import javafx.stage.Stage;

public class MediaService {
  
  private static final String MEDIA_CONFIGURATION_FILE = "MediaConfiguration.xmi";

  private static MediaService instance = null;
  
  private static CustomizationFx customization;
  
  public static MediaService getInstance() {
    if (instance == null) {
      instance = new MediaService();
      
      try {
        // Read the properties, which are stored in the registry.
        URL url = instance.getClass().getResource(MediaRegistry.propertyDescriptorsFile);
        PropertiesHandler.handleProperties(url, null);

        // Read the customization info.
        url = instance.getClass().getResource(MEDIA_CONFIGURATION_FILE);
        customization = CustomizationsFx.readCustomization(url);
      } catch (IOException e) {
        JfxApplication.reportException(null, e);
      }
    }
    return instance;
  }
  
  public void showMediaMenuWindow() {
    Stage stage = new MediaMenuWindow(customization);
    stage.show();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private MediaService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      MediaRegistry.developmentMode = true;
    }
    
  }
}
