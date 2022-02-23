package goedegep.app.configuration.testregistry;

import goedegep.appgen.EMFResource;
import goedegep.properties.model.PropertyDescriptorGroup;

public class TestRegistry {
  
  public static String author = null;                   // Name of the author of the application.
  public static String configurationFile = null;        // Name of the system file (in the currentDirectory) with Configuration data.
  public static String copyrightMessage = null;         // Copyright message for the application.
  public static String customPropertiesFile = null;     // Name of the file with custom properties.
  public static String dataDirectory = null;            // Directory where all data files are stored.
  public static String version = null;                  // Current software version.
  public static boolean developmentMode = false;        // Voor extra functies tijdens ontwikkeling
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
}
