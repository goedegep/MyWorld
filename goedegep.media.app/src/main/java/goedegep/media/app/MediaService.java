package goedegep.media.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.media.app.guifx.MediaMenuWindow;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The MediaService class is the main class for the Media application.
 * <p>
 * It provides methods to show the main media menu window and manages
 * application-wide resources and customization.
 */
public class MediaService extends Service {

  /**
   * The singleton instance of the MediaService.
   */
  private static MediaService instance = null;
  
  private MediaRegistry mediaRegistry;
  
  /**
   * Get the singleton instance of the MediaService.
   * 
   * @return the singleton instance of MediaService.
   */
  public static MediaService getInstance() {
    if (instance == null) {
      instance = new MediaService();
      instance.initialize();      
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
    mediaRegistry = MediaRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("MediaApplication.properties")) {
        props.load(in);
        
        mediaRegistry.setVersion(props.getProperty("media.app.version"));
        mediaRegistry.setApplicationName(props.getProperty("media.app.name"));
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
  
  @Override
  protected Registry getRegistry() {
    return mediaRegistry;
  }
}
