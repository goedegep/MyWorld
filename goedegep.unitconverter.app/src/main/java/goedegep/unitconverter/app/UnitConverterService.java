package goedegep.unitconverter.app;

import java.io.IOException;
import java.net.URL;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.unitconverter.app.guifx.UnitConverterWindow;
import javafx.stage.Stage;

public class UnitConverterService {
  
  private static final String UNIT_CONVERTER_CONFIGURATION_FILE = "UnitConverterConfiguration.xmi";

  private static UnitConverterService instance = null;
  
  private static CustomizationFx customization;
  
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
  
  public void showUnitConverterMenuWindow() {
    Stage stage = new UnitConverterWindow(customization);
    stage.show();
  }
  
  private UnitConverterService() {
    
  }

}
