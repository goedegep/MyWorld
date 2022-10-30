/**
 * 
 */
package goedegep.myworld.app;

import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

/**
 * See also {@link java.util.Calendar#DAY_OF_WEEK}.
 * @author Peter
 *
 */
public class MyWorldRegistry {
  public static String author = null;                 // Name of the author of the application.
  public static String configurationFile = null;      // Name of the file with Configuration data.
  public static String copyrightMessage = null;       // Copyright message for the application.
  public static String customPropertiesFile = null;   // Name of the file with custom properties.
  public static String dataDirectory = null;          // Directory where all data files are stored.
  public static String shortProductInfo = null;       // Short description of this application.
  public static String version = null;                // Current software version.
  public static String eventsPropertyDescriptorFileName = null;  // Name of the file with property descriptors for the module Events
  public static String finanPropertyDescriptorFileName = null;   // Name of the file with property descriptors for the module Finan.
  public static String mediaPropertyDescriptorFileName = null;   // Name of the file with property descriptors for the module MediaDb.
  public static String rolodexPropertyDescriptorFileName = null;   // Name of the file with property descriptors for the module Rolodex.
  public static String invoicesAndPropertiesPropertyDescriptorFileName = null;   // Name of the file with property descriptors for the module InvoicesAndProperties.
  public static String unitConverterPropertyDescriptorFileName = null;   // Name of the file with property descriptors for the module UnitConverter.
  public static String pctoolsPropertyDescriptorsFileName = null;   // Name of the file with property descriptors for the module PCTools.
  public static String vacationsPropertyDescriptorsFileName = null;   // Name of the file with property descriptors for the module Vacations.
  public static boolean developmentMode = false;      // For extra functionality during development.
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
}
