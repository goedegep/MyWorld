package goedegep.gpx.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.gpx.app.guifx.GPXAppResources;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import javafx.scene.paint.Color;

/**
 * The GPXService class is the main class for the GPX application.
 */
public class GPXService extends Service {
  /**
   * The singleton instance of the GPXService.
   */
  private static GPXService instance;
  
  private GPXRegistry gpxRegistry;

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
  
  /**
   * Show the GPX main window.
   * 
   * @param fileToOpen the GPX file to open, or null to start without a file.
   */
  public void showGPXWindow(String fileToOpen) {
    GPXWindow gpxWindow = new GPXWindow(customization, fileToOpen);
    gpxWindow.show();
  }
  
  /**
   * Private constructor to enforce singleton pattern.
   */
  private GPXService() {
    gpxRegistry = GPXRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("GPXApplication.properties")) {
        props.load(in);
        
        gpxRegistry.setVersion(props.getProperty("gpx.app.version"));
        gpxRegistry.setApplicationName(props.getProperty("gpx.app.name"));
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
    look.setLabelBackgroundColor(Color.rgb(238,238,238));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new GPXAppResources();
  }
  
  @Override
  protected Registry getRegistry() {
    return gpxRegistry;
  }
}
