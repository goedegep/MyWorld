package goedegep.gpx.app;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.util.RunningInEclipse;

public class GPXService {
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
}
