package goedegep.util.dir;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides options for checking a directory (see {@link DirectoryUtils}).
 * Options:
 * <ul>
 * <li>recurseIntoSubDirectories<br/>
 * Indication of whether to recurse into subdirectories or not.
 * </li>
 * <li>checkWhetherFileIsMovedIntoSubDirectory</br>
 * Check whether a file is moved into a subdirectory.
 * </li>
 * <li>ignoreDirectoryNames<br/>
 * A list of directory names to be ignored.
 * </li>
 * </ul>
 *
 */
public class DirectoryCheckOptions {
  private boolean recurseIntoSubDirectories = false;
  private boolean checkWhetherFileIsMovedIntoSubDirectory = false;
  private List<String> ignoreDirectoryNames = new ArrayList<>();
  
  /**
   * Check whether to recurse into subdirectories or not.
   * 
   * @return whether to recurse into subdirectories or not.
   */
  public boolean isRecurseIntoSubDirectories() {
    return recurseIntoSubDirectories;
  }
  
  /**
   * Set whether to recurse into subdirectories of not.
   * 
   * @param recurseIntoSubDirectories whether to recurse into subdirectories of not.
   */
  public void setRecurseIntoSubDirectories(boolean recurseIntoSubDirectories) {
    this.recurseIntoSubDirectories = recurseIntoSubDirectories;
  }
  
  /**
   * Check on check whether a file is moved into a subdirectory.
   * 
   * @return check whether a file is moved into a subdirectory.
   */
  public boolean isCheckWhetherFileIsMovedIntoSubDirectory() {
    return checkWhetherFileIsMovedIntoSubDirectory;
  }
  
  /**
   * Set check whether a file is moved into a subdirectory.
   * 
   * @param checkWhetherFileIsMovedIntoSubDirectory check whether a file is moved into a subdirectory.
   */
  public void setCheckWhetherFileIsMovedIntoSubDirectory(boolean checkWhetherFileIsMovedIntoSubDirectory) {
    this.checkWhetherFileIsMovedIntoSubDirectory = checkWhetherFileIsMovedIntoSubDirectory;
  }

  /**
   * Get the list of directory names to be ignored.
   * 
   * @return the list of directory names to be ignored.
   */
  public List<String> getIgnoreDirectoryNames() {
    return ignoreDirectoryNames;
  }
}
