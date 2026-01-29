package goedegep.media.musicfolder;

import java.nio.file.Path;

/**
 * This class provides error information.
 */
public class MusicFolderStructureErrorInfo {
  /**
   * Indication of the type of error.
   */
  private MusicFolderStructureError errorCode;
  
  /**
   * The folder path related to the problem.
   */
  private Path folderPath;
  
  /**
   * The related file.
   */
  private String file;
  
  /**
   * Additional textual information
   */
  private String details;
  
  /**
   * If true, this error was repaired.
   */
  private boolean repaired = false;
  
  
  /**
   * Constructor.
   * 
   * @param errorCode the error code.
   */
  public MusicFolderStructureErrorInfo(MusicFolderStructureError errorCode) {
    this.errorCode = errorCode;
  }
  
  /**
   * Get the error code.
   * @return the error code
   */
  public MusicFolderStructureError getErrorCode() {
    return errorCode;
  }
  
  /**
   * Get the folder path.
   * 
   * @return returns the folder path.
   */
  public Path getFolder() {
    return folderPath;
  }
  
  /**
   * Set the folder path.
   * 
   * @param folder the folder path.
   */
  public void setFolder(Path folder) {
    this.folderPath = folder;
  }

  /**
   * Get the file.
   * 
   * @return the file.
   */
  public String getFile() {
    return file;
  }

  /**
   * Set the file.
   * 
   * @param file the file.
   */
  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Get the details.
   * 
   * @return the details.
   */
  public String getDetails() {
    return details;
  }

  /**
   * Set the details.
   * 
   * @param details the details.
   */
  public void setDetails(String details) {
    this.details = details;
  }

  /**
   * Check whether the problem is repaired.
   * 
   * @return true if the error is repaired, false otherwise.
   */
  public boolean isRepaired() {
    return repaired;
  }

  /**
   * Indicate that the error is repaired.
   * 
   * @param repaired true indicates that the error is repaired.
   */
  public void setRepaired(boolean repaired) {
    this.repaired = repaired;
  }

}
