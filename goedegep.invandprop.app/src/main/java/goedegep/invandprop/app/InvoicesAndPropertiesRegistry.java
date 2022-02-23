package goedegep.invandprop.app;

import goedegep.appgen.EMFResource;
import goedegep.properties.model.PropertyDescriptorGroup;

public class InvoicesAndPropertiesRegistry {
  public static String author = null;                        // Name of the author of the application.
  public static String configurationFile = null;             // Name of the file with Configuration data.
  public static String copyrightMessage = null;              // Copyright message for the application.
  public static String customPropertiesFile = null;          // Name of the file with custom properties.
//  public static String dataDirectory = null;                 // Directory where all data files are stored.
  public static String propertyRelatedFilesFolder = null;    // Directory with files related to the properties (documents and pictures).
  public static String invoicesAndPropertiesFile = null;     // Name of the file with all invoices and properties.
  public static String shortProductInfo = null;              // Short description of this application.
  public static String version = null;                       // Current software version.
  public static boolean developmentMode = false;             // For extra functionality during development
  public static String projectPath = null;                   // Used in development mode to find e.g. the customPropertiesFile.
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
}
