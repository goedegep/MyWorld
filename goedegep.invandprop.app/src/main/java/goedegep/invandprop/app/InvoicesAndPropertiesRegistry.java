package goedegep.invandprop.app;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class InvoicesAndPropertiesRegistry implements PropertyFileURLProvider {
  
  private static final String INV_AND_PROP_PROPERTY_DESCRIPTORS_FILE = "InvoicesAndPropertiesPropertyDescriptors.xmi";
  private static final String INV_AND_PROP_CONFIGURATION_FILE = "InvoicesAndPropertiesConfiguration.xmi";
  
  public static String author = null;                        // Name of the author of the application.
  public static String configurationFile = null;             // Name of the file with Configuration data.
  public static String copyrightMessage = null;              // Copyright message for the application.
  
  /**
   * Name of the file with the property descriptors.
   */
  public static String propertyDescriptorsFile = "InvoicesAndPropertiesPropertyDescriptors.xmi";
  public static String customPropertiesFile = null;          // Name of the file with custom properties.
  public static String propertyRelatedFilesFolder = null;    // Directory with files related to the properties (documents and pictures).
  public static String invoicesAndPropertiesFile = null;     // Name of the file with all invoices and properties.
  public static String shortProductInfo = null;              // Short description of this application.
  public static String version = null;                       // Current software version.
  public static boolean developmentMode = false;             // For extra functionality during development
  public static String projectPath = null;                   // Used in development mode to find e.g. the customPropertiesFile.
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(INV_AND_PROP_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(INV_AND_PROP_CONFIGURATION_FILE);
    
    return url;
  }
}
