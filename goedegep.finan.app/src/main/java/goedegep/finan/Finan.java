package goedegep.finan;

import java.net.URL;
import java.util.logging.Logger;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.rolodex.model.Rolodex;

public class Finan implements PropertyFileURLProvider {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(Finan.class.getName());
  
  private static final String FINAN_PROPERTY_DESCRIPTORS_FILE = "FinanPropertyDescriptors.xmi";
  private static final String FINAN_CONFIGURATION_FILE = "FinanConfiguration.xmi";
  
  // Rolodex
  private Rolodex rolodex;
  
  
  public Finan(Rolodex rolodex) {
    this.rolodex = rolodex;
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
}
