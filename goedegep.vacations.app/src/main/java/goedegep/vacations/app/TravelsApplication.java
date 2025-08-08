package goedegep.vacations.app;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.properties.app.PropertiesHandler;
import goedegep.resources.ImageResource;
import goedegep.vacations.app.guifx.VacationsWindow;
import goedegep.vacations.app.logic.VacationsRegistry;

public class TravelsApplication {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelsApplication.class.getName());
  
  private static final String VACATIONS_CONFIGURATION_FILE = "VacationsConfiguration.xmi";

  private static TravelsApplication instance;
  private static CustomizationFx customization;
  
  /**
   * Private constructor to ensure singleton pattern.
   */
  private TravelsApplication() {
    
    // Handle properties
    URL url = getClass().getResource(VacationsRegistry.propertyDescriptorsFile);
    
    try {
      PropertiesHandler.handleProperties(url, null);

      // Read the customization info.
      url = getClass().getResource(VACATIONS_CONFIGURATION_FILE);
      CustomizationsFx.addCustomizations(url);
      
      customization = CustomizationsFx.getCustomization("VACATIONS");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static TravelsApplication getInstance() {
    // Ensure that the application is a singleton
    if (instance == null) {
      instance = new TravelsApplication();
    }
    ImageResource.checkResources();
    
    return instance;    
  }
  
  /**
   * Show the main window of the application.
   */
  public void showTravelsWindow() {
    // Show the main window of the application
    VacationsWindow travelsWindow = new VacationsWindow(customization);
    travelsWindow.show();
  }
}
