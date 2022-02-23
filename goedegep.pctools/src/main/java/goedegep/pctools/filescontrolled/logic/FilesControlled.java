package goedegep.pctools.filescontrolled.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

/**
 * This class provides the support logic needed to check for uncontrolled files and to get information on whether uncontrolled files
 * are copies of controlled files.
 */
public class FilesControlled {
  private static final Logger LOGGER = Logger.getLogger(FilesControlled.class.getName());
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public FilesControlled() {
    LOGGER.severe("=>");
    
    LOGGER.severe("<=");
  }
  
  
  /**
   * Generate an identification for a file, referred to as the FileId.
   * <p>
   * A FileId has the following format: &lt;MD5&gt;=&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;MD5&gt is the 128-bit MD5 hash value<br/>
   * &lt;file-name&gt; is the plain file name (i.e. without directory path)
   * TODO Determine the right approach. Using a calculated MD5 in the FileId is safe but very time consuming.
   * So for now I use the file size. But then I probably have to do a real compare to check if files are really the same.
   * Files found so far with same size, but which are not the same:
   * - Thumbs.db
   * These shall be ignored
   * - .indexes
   * Are in a subdirectory of an Eclipse .metadata directory.
   * 
   * @param file the file for which a fileId is to be generated
   * @param attrs the BasicFileAttributes of the file (currently not used)
   * @return the fileId of the file
   * @throws IOException if the file could not be read
   */
  protected static String generateFileId(Path file, BasicFileAttributes attrs) throws IOException {
    String fileName = file.getFileName().toString();
    LOGGER.fine("fileName: " + fileName);
    
//    String md5 = generateMD5String(file.toAbsolutePath().toString());
//    LOGGER.fine("md5: " + md5);
    
//    FileTime lastModifiedTime = attrs.lastModifiedTime();
//    LOGGER.fine("lastModifiedTime: " + lastModifiedTime.toString());
//    String lastModifiedTimeText = String.valueOf(lastModifiedTime.toMillis());
//    LOGGER.fine("lastModifiedTimeText: " + lastModifiedTimeText);
    
//    String fileId = md5 + "=" + lastModifiedTimeText + "=" + fileName;
//    String fileId = md5 + "=" + fileName;
    String fileId = attrs.size() + "=" + fileName;
    LOGGER.fine("fileId: " + fileId);

    return fileId;
  }
}




