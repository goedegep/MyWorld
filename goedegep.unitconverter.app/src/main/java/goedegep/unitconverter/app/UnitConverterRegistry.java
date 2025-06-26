package goedegep.unitconverter.app;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class UnitConverterRegistry implements PropertyFileURLProvider {
  
  private static final String UNIT_CONVERTER_PROPERTY_DESCRIPTORS_FILE = "UnitConverterPropertyDescriptors.xmi";
  private static final String UNIT_CONVERTER_CONFIGURATION_FILE = "UnitConverterConfiguration.xmi";
  
  public static String author = null;                 // Name of the author of the application.
  public static String configurationFile = null;      // Name of the file with Configuration data.
  public static String copyrightMessage = null;       // Copyright message for the application.
  public static String customPropertiesFile = null;   // Name of the file with custom properties.
  public static String dataDirectory = null;          // Directory where all data files are stored.
  public static String shortProductInfo = null;       // Short description of this application.
  public static String version = null;                // Current software version.
  public static boolean developmentMode = false;      // Voor extra functies tijdens ontwikkeling
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;


  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(UNIT_CONVERTER_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(UNIT_CONVERTER_CONFIGURATION_FILE);
    
    return url;
  }
}
