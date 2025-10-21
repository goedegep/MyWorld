package goedegep.demo;

import java.io.InputStream;
import java.util.Properties;

import goedegep.demo.guifx.DemoCustomization;
import goedegep.demo.guifx.DemoMenuWindow;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.util.RunningInEclipse;

public class DemoService extends Service {
  private CustomizationFx customization;
  private static DemoService instance = null;
  
  public static DemoService getInstance() {
    if (instance == null) {
      instance = new DemoService();
      instance.initialize();
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

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    DemoRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("DemoApplication.properties")) {
        props.load(in);
        
        DemoRegistry.version = props.getProperty("demo.app.version");
        DemoRegistry.applicationName = props.getProperty("demo.app.name");
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
}
