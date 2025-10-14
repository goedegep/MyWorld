package goedegep.unitconverter.app;

import java.io.IOException;
import java.net.URL;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.unitconverter.app.guifx.UnitConverterWindow;
import goedegep.util.RunningInEclipse;
import javafx.stage.Stage;

/**
 * This class is the main class of the Unit Converter application.
 */
public class UnitConverterService {
  
  private static final String UNIT_CONVERTER_CONFIGURATION_FILE = "UnitConverterConfiguration.xmi";

  private static UnitConverterService instance = null;
  
  private static CustomizationFx customization;
  
  /**
   * Get the singleton instance of the UnitConverterService.
   * 
   * @return the singleton instance of UnitConverterService.
   */
  public static UnitConverterService getInstance() {
    if (instance == null) {
      instance = new UnitConverterService();
      
      try {
        // Read the properties, which are stored in the registry.
        URL url = instance.getClass().getResource(UnitConverterRegistry.propertyDescriptorsFile);
        PropertiesHandler.handleProperties(url, null);

        // Read the customization info.
        url = instance.getClass().getResource(UNIT_CONVERTER_CONFIGURATION_FILE);
        customization = CustomizationsFx.readCustomization(url);
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

}
