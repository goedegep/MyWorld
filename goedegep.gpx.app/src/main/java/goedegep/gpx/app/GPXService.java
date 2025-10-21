package goedegep.gpx.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.util.RunningInEclipse;

public class GPXService extends Service {
  private static GPXService instance;
  private static CustomizationFx customization = DefaultCustomizationFx.getInstance();

  /**
   * Get the singleton instance of the GPXService.
   * 
   * @return the singleton instance of GPXService.
   */
  public static GPXService getInstance() {
    if (instance == null) {
      instance = new GPXService();
      instance.initialize();
    }

    return instance;
  }
  
  public void showGPXWindow(String fileToOpen) {
    GPXWindow gpxWindow = new GPXWindow(customization, fileToOpen);
    gpxWindow.show();
  }
  
  /**
   * Private constructor to enforce singleton pattern.
   */
  private GPXService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      GPXRegistry.developmentMode = true;
    }
    
    RunningInEclipse.runningInEclipse();
  }
  

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    GPXRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("GPXApplication.properties")) {
        props.load(in);
        
        GPXRegistry.version = props.getProperty("gpx.app.version");
        GPXRegistry.applicationName = props.getProperty("gpx.app.name");
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
}
