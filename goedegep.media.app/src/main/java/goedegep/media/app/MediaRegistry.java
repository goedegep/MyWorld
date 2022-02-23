package goedegep.media.app;

import goedegep.appgen.EMFResource;
import goedegep.properties.model.PropertyDescriptorGroup;

public class MediaRegistry {
  public static String author = null;                   // Name of the author of the application.
  public static String configurationFile = null;        // Name of the system file (in the currentDirectory) with Configuration data.
  public static String copyrightMessage = null;         // Copyright message for the application.
  public static String customPropertiesFile = null;     // Name of the file with custom properties.
  public static String albumInfoDirectory = null;       // Directory where the AlbumInfo xml files are located.
  public static String musicDirectory = null;           // Directory where all music is stored.
  public static String duneMusicFolderPath = null;      // Directory on the Dune where the music is stored.
  public static String dunePlaylistsFolderPath = null;  // Directory on the Dune where the playlists are stored.
  public static String shortProductInfo = null;         // Short description of this application.
  public static String version = null;                  // Current software version.
  public static boolean developmentMode = false;        // Voor extra functies tijdens ontwikkeling
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  public static String mediaPlayerClassicExecutable = null;  // Full path to the MPC-HC executable.
  
  /*
   * Photoshow fields
   */
  
  /**
   * Default value for the folder where the photos are stored.
   */
  public static String photosFolder = null;
  
  /**
   * Default list of folders to ignore when looking for photo folders for a photo show.<br/>
   * The list consists of folder names, separated by semicolons.
   */
  public static String ignoreFolderNames = null;        // Folder names to ignore
}
