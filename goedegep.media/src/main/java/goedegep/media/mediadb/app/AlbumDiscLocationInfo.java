package goedegep.media.mediadb.app;

import java.util.List;

import goedegep.util.string.StringUtil;

/**
 * This class provides information about the folder and track files of an album.
 * <p>
 * It stores:
 * <ul>
 * <li>
 * The absolute pathname of the folder in which the album tracks are located.
 * </li>
 * <li>
 * The filenames of the tracks.
 * </li>
 * </ul>
 * Typical use of this class is in a Map, which maps an {@link goedegep.media.mediadb.model.Album} to this AlbumDiscLocationInfo.
 * For example: <code>Map<Album, AlbumDiscLocationInfo> albumOnDiscLocations;</code>
 * 
 */
public class AlbumDiscLocationInfo {
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  /**
   * The absolute pathname of the older in which the album tracks are located.
   */
  private String absAlbumFolderPathname;
  
  /**
   * The filenames of the tracks.
   */
  private List<String> trackFileNames;
  
  /**
   * Constructor
   * 
   * @param absAlbumFolderPathname The absolute pathname of the folder in which the album tracks are located.
   * @param trackFileNames The filenames of the tracks.
   */
  private AlbumDiscLocationInfo(String absAlbumFolderPathname, List<String> trackFileNames) {
    this.absAlbumFolderPathname = absAlbumFolderPathname;
    this.trackFileNames = trackFileNames;
  }

  /**
   * Get the absolute pathname of the folder in which the album tracks are located.
   * 
   * @return the absolute pathname of the folder in which the album tracks are located.
   */
  public String getAbsAlbumFolderPathname() {
    return absAlbumFolderPathname;
  }

  /**
   * Get the filenames of the tracks.
   * 
   * @return the filenames of the tracks.
   */
  public List<String> getTrackFileNames() {
    return trackFileNames;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("absAlbumFolderPathname: ");
    buf.append(absAlbumFolderPathname).append(NEW_LINE);
    
    buf.append("trackFileNames: ");
    buf.append(StringUtil.stringCollectionToCommaSeparatedStrings(trackFileNames)).append(NEW_LINE);
    
    return buf.toString();
  }
}
