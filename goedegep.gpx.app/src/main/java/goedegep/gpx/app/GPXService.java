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
  
  private GPXService() {
    RunningInEclipse.runningInEclipse();
  }
}
