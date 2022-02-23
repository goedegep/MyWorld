package goedegep.pctools.filescontrolled.types;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import goedegep.util.file.FileUtils;

/**
 * This class stores information about a file.
 * <p>
 * The information consist of:
 * <ul>
 * <li>The Path for the directory in which the file resides.</li>
 * <li>The Path for the file (without directory).</li>
 * <li>The MD5 hash string of the file.</li>
 * </ul>
 */
public class FileInfo implements Comparable<FileInfo> {
  private static final Logger LOGGER = Logger.getLogger(FileInfo.class.getName());

  /**
   * The directory in which the file is located.
   */
  private Path directory = null;
  
  /**
   * The plain file (without directory path).
   */
  private Path file = null;
  
  /**
   * The MD5 hash string of the file.
   */
  private String md5String = null;

  /**
   * Get the directory in which the file is located.
   * @return the directory in which the file is located.
   */
  public Path getDirectory() {
    return directory;
  }

  /**
   * Set the directory in which the file is located.
   * 
   * @param directory the directory in which the file is located.
   */
  public void setDirectory(Path directory) {
    this.directory = directory;
  }

  /**
   * Get the plain file (without directory path).
   * 
   * @return the plain file (without directory path).
   */
  public Path getFile() {
    return file;
  }

  /**
   * Set the plain file (without directory path).
   * 
   * @param file the plain file (without directory path).
   */
  public void setFile(Path file) {
    this.file = file;
  }
  
  /**
   * Get the full path for the file.
   * 
   * @return the full path for the file.
   */
  public Path getFullPath() {
    Path path = directory.resolve(file);
    
    return path;
  }  
  
  /**
   * Get the full path for the file as String.
   * 
   * @return the full path for the file as String.
   */
  public String getFullPathname() {
    Path path = directory.resolve(file);
    String pathName = path.toAbsolutePath().toString();
    
    return pathName;
  }
  
  /**
   * Get the Md5 hash String for the file.
   * <p>
   * This string is generated when it is requested for the first time.
   * 
   * @return the Md5 hash String for the file.
   */
  public String getMd5String() {
    if (md5String == null) {
      try {
        md5String = FileUtils.generateMD5String(getFullPathname());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return md5String;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(directory != null ? directory.toString() : "(null)");
    buf.append(" ");
    buf.append(file != null ? file.toString() : (null));
    
    return buf.toString();
  }

  @Override
  public int compareTo(FileInfo fileInfo) {
    Path thisPath = directory.resolve(file);
    Path otherPath = fileInfo.getDirectory().resolve(fileInfo.getFile());
    
    LOGGER.info("thisPath=" + thisPath.toAbsolutePath().toString());
    LOGGER.info("otherPath=" + otherPath.toAbsolutePath().toString());
    
    return thisPath.compareTo(otherPath);
  }
}
