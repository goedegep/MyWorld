package goedegep.pctools.app.logic;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class PCToolsRegistry implements PropertyFileURLProvider {
  private static final String PC_TOOLS_PROPERTY_DESCRIPTORS_FILE = "PCToolsPropertyDescriptors.xmi";
  private static final String PC_TOOLS_CONFIGURATION_FILE = "PCToolsConfiguration.xmi";

  
  public static String author = null;                 // Name of the author of the application.
  public static String configurationFile = null;      // Name of the file with Configuration data.
  public static String copyrightMessage = null;       // Copyright message for the application.
  
  /**
   * Name of the file with the property descriptors.
   */
  public static String propertyDescriptorsFile = "PCToolsPropertyDescriptors.xmi";

  public static String customPropertiesFile = null;   // Name of the file with custom properties.
  public static String dataDirectory = null;          // Directory where all data files are stored.
  public static String shortProductInfo = null;       // Short description of this application.
  public static String version = null;                // Current software version.
  public static boolean developmentMode = false;      // Voor extra functies tijdens ontwikkeling
  public static String projectPath = null;            // Used in development mode to find e.g. the customPropertiesFile.
  public static String defaultDiscStructureSpecificationFile = null;  // Bij het starten van de FilesControlled applicatie, wordt dit bestand geopend.
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
  public URL getPropertyFileURL() {    
    URL url = getClass().getResource(PC_TOOLS_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }
  
  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(PC_TOOLS_CONFIGURATION_FILE);
    
    return url;
  }
}