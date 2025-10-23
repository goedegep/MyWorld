package goedegep.demo;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.demo.guifx.DemoAppResources;
import goedegep.demo.guifx.DemoMenuWindow;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import javafx.scene.paint.Color;

/**
 * This class is the Demo application.
 * <p>
 * It holds the customization information and provides methods to show the main window of the application.
 * It is built on top of logic and guifx sub packages.
 */
public class DemoService extends Service {
  /**
   * The singleton instance of the DemoService.
   */
  private static DemoService instance = null;
  
  /**
   * Get the singleton instance of the DemoService.
   * 
   * @return the singleton instance of DemoService.
   */
  public static DemoService getInstance() {
    if (instance == null) {
      instance = new DemoService();
      instance.initialize();
    }
    
    return instance;
  }
  
  /**
   * Show the demo menu window.
   */
  public void showDemoWindow() {
    new DemoMenuWindow(customization);
  }
  
  /**
   * Private constructor to ensure singleton pattern.
   */
  private DemoService() {
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
  
  @Override
  protected void fillLook(Look look) {
    // Base the colors on ALICEBLUE
    Color bgColor = Color.ALICEBLUE;
    double bgRed = bgColor.getRed();
    double bgGreen = bgColor.getGreen();
    double bgbBlue = bgColor.getBlue();
    
    // Make the button color a little darker
    Color buttonColor = new Color(0.8 * bgRed, 0.8 * bgGreen, 0.9 * bgbBlue, 1.0);
    
    // Make the panel color a little darker
    Color panelColor = new Color(0.86 * bgRed, 0.86 * bgGreen, 0.92 * bgbBlue, 1.0);
    
    look.setBackgroundColor(Color.ALICEBLUE);
    look.setBoxBackgroundColor(Color.BLUEVIOLET);
    look.setButtonBackgroundColor(buttonColor);
    look.setListBackgroundColor(Color.LIGHTBLUE);
    look.setPanelBackgroundColor(panelColor);
    look.setTextFieldBackgroundColor(Color.ALICEBLUE);
    look.setLabelBackgroundColor(panelColor);
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new DemoAppResources();
  }
}
