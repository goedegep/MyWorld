package goedegep.media.common;

import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class MediaRegistry {
  
  /**
   * The name of the application.
   */
  public static String applicationName;

  /**
   * Name of the author of the application.
   */
  public static String author = "Peter Goedegebure";
  
  /**
   * Name of the file with Configuration data.
   */
  public static String configurationFile = null;
  
  /**
   * Copyright message for the application.
   */
  public static String copyrightMessage = "Copyright (c) 2001-2025";
  
  /**
   * Name of the file with the property descriptors.
   */
  public static String propertyDescriptorsFile = "MediaPropertyDescriptors.xmi";
  
  /**
   * Name of the file with custom properties.
   * <p>
   * This file shall be located in the "MyWorld" folder under the user's home directory.
   */
  public static String customPropertiesFile = "VacationsUserPreferences.xmi";
  
  /**
   * Name of the file with de media database.
   */
  public static String mediaDbFile = null;
  
  /**
   * Directory where pictures of albums and artists are located.
   */
  public static String musicDataDirectory = null;
  
  /**
   * Directory where all music is stored.
   */
  public static String musicDirectory = null;
  
  /**
   * Default location for selecting a directory from which album import information is to be derived.
   */
  public static String albumImportDirectory = null;
  
  /**
   * Directory on the Dune where the music is stored.
   */
  public static String duneMusicFolderPath = null;
  
  /**
   *  Directory on the Dune where the playlists are stored.
   */
  public static String dunePlaylistsFolderPath = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "Media database";
  
  /**
   * Current software version.
   */
  public static String version = null;
  
  /**
   * For extra functionality during development.
   */
  public static boolean developmentMode = false;
  
  /**
   * EMFResource for the Property Descriptors.
   */
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
  /**
   * Full path to the MPC-HC executable.
   */
  public static String mediaPlayerClassicExecutable = null;
  
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
