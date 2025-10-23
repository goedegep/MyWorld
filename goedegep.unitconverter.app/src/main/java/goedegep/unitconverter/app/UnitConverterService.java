package goedegep.unitconverter.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.properties.app.PropertiesHandler;
import goedegep.unitconverter.app.guifx.UnitConverterAppResourcesFx;
import goedegep.unitconverter.app.guifx.UnitConverterWindow;
import goedegep.util.RunningInEclipse;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is the main class of the Unit Converter application.
 */
public class UnitConverterService extends Service {

  private static UnitConverterService instance = null;
  
  /**
   * Get the singleton instance of the UnitConverterService.
   * 
   * @return the singleton instance of UnitConverterService.
   */
  public static UnitConverterService getInstance() {
    if (instance == null) {
      instance = new UnitConverterService();
      instance.initialize();
      
      try {
        // Read the properties, which are stored in the registry.
        URL url = instance.getClass().getResource(UnitConverterRegistry.propertyDescriptorsFile);
        PropertiesHandler.handleProperties(url, null);
      } catch (IOException e) {
        JfxApplication.reportException(null, e);
      }
    }
    return instance;
  }
  
  /**
   * Show the main window of the application.
   */
  public void showUnitConverterMenuWindow() {
    Stage stage = new UnitConverterWindow(customization);
    stage.show();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private UnitConverterService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      UnitConverterRegistry.developmentMode = true;
    }
    
  }

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    UnitConverterRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("UnitConverterApplication.properties")) {
        props.load(in);
        
        UnitConverterRegistry.version = props.getProperty("unitconverter.app.version");
        UnitConverterRegistry.applicationName = props.getProperty("unitconverter.app.name");
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
    return new UnitConverterAppResourcesFx();
  }
}
