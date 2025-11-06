package goedegep.media.common;

import goedegep.myworld.common.Registry;

public class MediaRegistry extends Registry {
  
  /**
   * Name of the file with de media database.
   */
  private static String mediaDbFile = null;
  
  /**
   * Directory where pictures of albums and artists are located.
   */
  private static String musicDataDirectory = null;
  
  /**
   * Directory where all music is stored.
   */
  private static String musicDirectory = null;
  
  /**
   * Default location for selecting a directory from which album import information is to be derived.
   */
  private static String albumImportDirectory = null;
  
  /**
   * Directory on the Dune where the music is stored.
   */
  private static String duneMusicFolderPath = null;
  
  /**
   *  Directory on the Dune where the playlists are stored.
   */
  private static String dunePlaylistsFolderPath = null;
  
  /**
   * Full path to the MPC-HC executable.
   */
  private static String mediaPlayerClassicExecutable = null;
  
  /*
   * Photoshow fields
   */
  
  /**
   * Default value for the folder where the photos are stored.
   */
  private static String photosFolder = null;
  
  /**
   * Default list of folders to ignore when looking for photo folders for a photo show.<br/>
   * The list consists of folder names, separated by semicolons.
   */
  private static String ignoreFolderNames = null;        // Folder names to ignore
  
  /**
   * Singleton instance of the MediaRegistry.
   */
  private static MediaRegistry instance = null;

  /**
   * Get the singleton instance of the MediaRegistry.
   * 
   * @return the singleton instance of the MediaRegistry.
   */
  public static MediaRegistry getInstance() {
    if (instance == null) {
      instance = new MediaRegistry();
    }
    
    return instance;
  }
  
  /**
   * Get the name of the file with de media database.
   * 
   * @return the name of the file with de media database.
   */
  public String getMediaDbFile() {
    return mediaDbFile;
  }

  /**
   * Set the name of the file with de media database.
   * 
   * @param mediaDbFile the name of the file with de media database.
   */
  public void setMediaDbFile(String mediaDbFile) {
    MediaRegistry.mediaDbFile = mediaDbFile;
  }

  /**
   * Get the directory where pictures of albums and artists are located.
   * 
   * @return the directory where pictures of albums and artists are located.
   */
  public String getMusicDataDirectory() {
    return musicDataDirectory;
  }
  
  /**
   * Set the directory where pictures of albums and artists are located.
   * 
   * @param musicDataDirectory the directory where pictures of albums and artists are located.
   */
  public void setMusicDataDirectory(String musicDataDirectory) {
    MediaRegistry.musicDataDirectory = musicDataDirectory;
  }

  /**
   * Get the directory where all music is stored.
   * 
   * @return the directory where all music is stored.
   */
  public String getMusicDirectory() {
    return musicDirectory;
  }

  /**
   * Set the directory where all music is stored.
   * 
   * @param musicDirectory the directory where all music is stored.
   */
  public void setMusicDirectory(String musicDirectory) {
    MediaRegistry.musicDirectory = musicDirectory;
  }

  /**
   * Get the default location for selecting a directory from which album import information is to be derived.
   * 
   * @return the default location for selecting a directory from which album import information is to be derived.
   */
  public String getAlbumImportDirectory() {
    return albumImportDirectory;
  }

  /**
   * Set the default location for selecting a directory from which album import information is to be derived.
   * 
   * @param albumImportDirectory the default location for selecting a directory from which album import information is to be derived.
   */
  public void setAlbumImportDirectory(String albumImportDirectory) {
    MediaRegistry.albumImportDirectory = albumImportDirectory;
  }

  /**
   * Get the directory on the Dune where the music is stored.
   * 
   * @return the directory on the Dune where the music is stored.
   */
  public String getDuneMusicFolderPath() {
    return duneMusicFolderPath;
  }
  
  /**
   * Set the directory on the Dune where the music is stored.
   * 
   * @param duneMusicFolderPath the directory on the Dune where the music is stored.
   */
  public void setDuneMusicFolderPath(String duneMusicFolderPath) {
    MediaRegistry.duneMusicFolderPath = duneMusicFolderPath;
  }

  /**
   * Get the directory on the Dune where the playlists are stored.
   * 
   * @return the directory on the Dune where the playlists are stored.
   */
  public String getDunePlaylistsFolderPath() {
    return dunePlaylistsFolderPath;
  }

  /**
   * Set the directory on the Dune where the playlists are stored.
   * 
   * @param dunePlaylistsFolderPath the directory on the Dune where the playlists are stored.
   */
  public void setDunePlaylistsFolderPath(String dunePlaylistsFolderPath) {
    MediaRegistry.dunePlaylistsFolderPath = dunePlaylistsFolderPath;
  }

  /**
   * Get the full path to the MPC-HC executable.
   * 
   * @return the full path to the MPC-HC executable.
   */
  public String getMediaPlayerClassicExecutable() {
    return mediaPlayerClassicExecutable;
  }
  
  /**
   * Set the full path to the MPC-HC executable.
   * 
   * @param mediaPlayerClassicExecutable the full path to the MPC-HC executable.
   */
  public void setMediaPlayerClassicExecutable(String mediaPlayerClassicExecutable) {
    MediaRegistry.mediaPlayerClassicExecutable = mediaPlayerClassicExecutable;
  }

  /**
   * Get the folder where the photos are stored.
   * 
   * @return the folder where the photos are stored.
   */
  public String getPhotosFolder() {
    return photosFolder;
  }
  
  /**
   * Set the folder where the photos are stored.
   * 
   * @param photosFolder the folder where the photos are stored.
   */
  public void setPhotosFolder(String photosFolder) {
    MediaRegistry.photosFolder = photosFolder;
  }

  /**
   * Get the list of folder names to ignore when looking for photo folders for a photo show.
   * 
   * @return the list of folder names to ignore when looking for photo folders for a photo show.
   */
  public String getIgnoreFolderNames() {
    return ignoreFolderNames;
  }
  /**
   * Set the list of folder names to ignore when looking for photo folders for a photo show.
   * 
   * @param ignoreFolderNames the list of folder names to ignore when looking for photo folders for a photo show.
   */
  public void setIgnoreFolderNames(String ignoreFolderNames) {
    MediaRegistry.ignoreFolderNames = ignoreFolderNames;
  }

  /**
   * Private constructor for the singleton MediaRegistry.
   */
  private MediaRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Media database");
    setPropertyDescriptorsFileName("MediaPropertyDescriptors.xmi");
    setUserPropertiesFileName("MediaUserPreferences.xmi");
    setMediaDbFile("D:\\Database\\MyWorld\\MediaDb.xmi");
    setMusicDataDirectory("D:\\Database\\Muziek");
    setMusicDirectory("D:\\Music");
    setAlbumImportDirectory("D:\\SoulSeek\\complete");
    setDuneMusicFolderPath("\\\\Dune\\DuneHDD_00f1f4a3_110b_40ec_b5a1_d89bd7c8f93f\\Music");
    setDunePlaylistsFolderPath("\\\\Dune\\DuneHDD_00f1f4a3_110b_40ec_b5a1_d89bd7c8f93f\\Playlists");
    setPhotosFolder("D:\\Photo");
    setIgnoreFolderNames("show;selectie;weg;Originals;.SynologyWorkingDirectory");
    setMediaPlayerClassicExecutable("C:\\Program Files\\MPC-BE x64\\mpc-be64.exe");
  }

}
