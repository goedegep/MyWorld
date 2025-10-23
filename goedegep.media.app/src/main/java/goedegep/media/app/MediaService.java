package goedegep.media.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.media.app.guifx.MediaMenuWindow;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.myworld.common.Service;
import goedegep.properties.app.PropertiesHandler;
import goedegep.util.RunningInEclipse;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MediaService extends Service {

  /**
   * The singleton instance of the MediaService.
   */
  private static MediaService instance = null;
  
  /**
   * Get the singleton instance of the MediaService.
   * 
   * @return the singleton instance of MediaService.
   */
  public static MediaService getInstance() {
    if (instance == null) {
      instance = new MediaService();
      instance.initialize();
      
      try {
        // Read the properties, which are stored in the registry.
        URL url = instance.getClass().getResource(MediaRegistry.propertyDescriptorsFile);
        PropertiesHandler.handleProperties(url, null);
      } catch (IOException e) {
        JfxApplication.reportException(null, e);
      }
    }
    return instance;
  }
  
  /**
   * Show the main media menu window.
   */
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

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    MediaRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("MediaApplication.properties")) {
        props.load(in);
        
        MediaRegistry.version = props.getProperty("media.app.version");
        MediaRegistry.applicationName = props.getProperty("media.app.name");
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(140,140,170));
    look.setButtonBackgroundColor(Color.rgb(100,100,130));
    look.setPanelBackgroundColor(Color.rgb(130,130,190));
    look.setListBackgroundColor(Color.rgb(135,135,180));
    look.setLabelBackgroundColor(Color.rgb(135,135,180));
    look.setBoxBackgroundColor(Color.rgb(165,165,195));
    look.setTextFieldBackgroundColor(Color.rgb(155,155,200));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new MediaAppResourcesFx();
  }
}
