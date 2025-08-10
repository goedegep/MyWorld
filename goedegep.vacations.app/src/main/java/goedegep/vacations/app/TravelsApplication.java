package goedegep.vacations.app;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.resources.ImageResource;
import goedegep.vacations.app.guifx.VacationsWindow;
import goedegep.vacations.app.logic.VacationsRegistry;

/**
 * This class is the Travels application.
 * <p>
 * It holds the customization information and provides methods to show the main window of the application.
 * It is built on top of logic and guifx sub packages.
 */
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
    
    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(VacationsRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

      // Read the customization info.
      url = getClass().getResource(VACATIONS_CONFIGURATION_FILE);
      customization = CustomizationsFx.readCustomization(url);
    } catch (FileNotFoundException e) {
      JfxApplication.reportException(null, e);
    }

  }
  
  /**
   * Get the singleton instance of the TravelsApplication.
   * 
   * @return the singleton instance of TravelsApplication.
   */
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

  public CustomizationFx getCustomization() {
    return customization;
  }
}
