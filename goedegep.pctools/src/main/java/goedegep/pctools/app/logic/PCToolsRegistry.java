package goedegep.pctools.app.logic;

import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class PCToolsRegistry {
  public static String author = null;                 // Name of the author of the application.
  public static String configurationFile = null;      // Name of the file with Configuration data.
  public static String copyrightMessage = null;       // Copyright message for the application.
  public static String customPropertiesFile = null;   // Name of the file with custom properties.
  public static String dataDirectory = null;          // Directory where all data files are stored.
  public static String shortProductInfo = null;       // Short description of this application.
  public static String version = null;                // Current software version.
  public static boolean developmentMode = false;      // Voor extra functies tijdens ontwikkeling
  public static String projectPath = null;            // Used in development mode to find e.g. the customPropertiesFile.
  public static String defaultDiscStructureSpecificationFile = null;  // Bij het starten van de FilesControlled applicatie, wordt dit bestand geopend.
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
}