package goedegep.demo;

import goedegep.demo.guifx.DemoCustomization;
import goedegep.demo.guifx.DemoMenuWindow;
import goedegep.jfx.CustomizationFx;
import goedegep.util.RunningInEclipse;

public class DemoService {
  private CustomizationFx customization;
  private static DemoService instance = null;
  
  public static DemoService getInstance() {
    if (instance == null) {
      instance = new DemoService();
    }
    
    return instance;
  }
  
  public void showDemoWindow() {
    new DemoMenuWindow(customization);
  }
  
  private DemoService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      DemoRegistry.developmentMode = true;
    }
    
    customization = DemoCustomization.getInstance();
  }
}
