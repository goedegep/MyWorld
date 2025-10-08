package goedegep.rolodex.app;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.rolodex.model.Rolodex;
import goedegep.util.emf.EMFResource;

public class RolodexRegistry implements PropertyFileURLProvider {
  
  private static final String ROLODEX_PROPERTY_DESCRIPTORS_FILE = "RolodexPropertyDescriptors.xmi";
  private static final String ROLODEX_CONFIGURATION_FILE = "RolodexConfiguration.xmi";
  
  public static String author = null;                        // Name of the author of the application.
  public static String configurationFile = null;             // Name of the file with Configuration data.
  public static String copyrightMessage = null;              // Copyright message for the application.
  
  /**
   * Name of the file with the property descriptors.
   */
  public static String propertyDescriptorsFile = "RolodexPropertyDescriptors.xmi";
  public static String customPropertiesFile = null;          // Name of the file with custom properties.
//  public static String dataDirectory = null;                 // Directory where all data files are stored.
  public static String rolodexFile = null;                   // Name of the file with all rolodex data.
  public static String shortProductInfo = null;              // Short description of this application.
  public static String version = null;                       // Current software version.
  public static boolean developmentMode = false;             // Voor extra functies tijdens ontwikkeling
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  public static EMFResource<Rolodex> rolodexResource = null;  // TODO as the rolodex resource is part of the RolodexRegistry it doesn't have to be passed as a parameter
  

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(ROLODEX_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(ROLODEX_CONFIGURATION_FILE);
    
    return url;
  }
}
