package goedegep.finan;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import goedegep.app.finan.finanapp.FinanResources;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.swing.Customization;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.rolodex.model.Rolodex;

public class Finan implements PropertyFileURLProvider {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(Finan.class.getName());
  
  private static final String FINAN_PROPERTY_DESCRIPTORS_FILE = "FinanPropertyDescriptors.xmi";
  private static final String FINAN_CONFIGURATION_FILE = "FinanConfiguration.xmi";
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customizationFx;
  
  /**
   *  The customization in the old Swing format.
   */
  private Customization customization;
  
  // Rolodex
  private Rolodex rolodex;
  
  
  public Finan(Rolodex rolodex) {
    this.rolodex = rolodex;
    
    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(FinanRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

      // Read the customization info.
      url = getClass().getResource(FINAN_CONFIGURATION_FILE);
      customizationFx = CustomizationsFx.readCustomization(url);
    } catch (IOException e) {
      JfxApplication.reportException(null, e);
    }
    
    customization = new Customization(new FinanResources());
  }
  
  public Rolodex getRolodex() {
    return rolodex;
  }

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(FINAN_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }
  
  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(FINAN_CONFIGURATION_FILE);
    
    return url;
  }

  public CustomizationFx getCustomizationFx() {
    return customizationFx;
  }

  public Customization getCustomization() {
    return customization;
  }
  
  
}
