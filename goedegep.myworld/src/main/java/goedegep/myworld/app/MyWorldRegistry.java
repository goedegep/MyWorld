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
  public static String finanPropertyDescriptorFileName = null;   // Naam van het bestand met property descriptors voor de module Finan.
  public static String mediaPropertyDescriptorFileName = null;   // Naam van het bestand met property descriptors voor de module MediaDb.
  public static String rolodexPropertyDescriptorFileName = null;   // Naam van het bestand met property descriptors voor de module Rolodex.
  public static String invoicesAndPropertiesPropertyDescriptorFileName = null;   // Naam van het bestand met property descriptors voor de module NotasEnEigendommen.
  public static String unitConverterPropertyDescriptorFileName = null;   // Naam van het bestand met property descriptors voor de module UnitConverter.
  public static String pctoolsPropertyDescriptorsFileName = null;   // Naam van het bestand met property descriptors voor de module PCTools.
  public static String vacationsPropertyDescriptorsFileName = null;   // Naam van het bestand met property descriptors voor de module Vakanties.
  public static boolean developmentMode = false;      // Voor extra functies tijdens ontwikkeling
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
}
