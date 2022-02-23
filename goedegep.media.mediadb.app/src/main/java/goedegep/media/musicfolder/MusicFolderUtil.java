package goedegep.media.musicfolder;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * This is a utility class for handling a Music Folder.
 */
public class MusicFolderUtil {
  private static final Logger LOGGER = Logger.getLogger(MusicFolderUtil.class.getName());

  
  /**
   * Constructor
   * <p>
   * As this is a utility class, it should not be instantiated. This is guaranteed by this private constructor.
   */
  private MusicFolderUtil() {
  }
  
  /**
   * Remove special characters from a part of a file or folder name.
   * <p>
   * 
   * Names can contain any character, while there are restrictions on file names.
   * Under Windows the following characters are not allowed in file or folder names: '\', '/', ':', '*', '?', '"', '<', '>', '|'.
   * The following conversions are applied:
   * <pre>
   * character           replaced by         explanation
   * \                   ,                   use comma as separator
   * /                   ,                   use comma as separator
   * :                   ;                   a colon is replaced by a semicolon
   * *                   +
   * ?                   , or nothing        a question mark on the end, or before quotes is simply removed. If it is in the middle we need some kind of separation,
   *                                         so we use a comma.
   * "                   '                   double quotes are replaced by single quotes
   * <                   [
   * >                   ]
   * |                   !
   * </pre>
   * In stupid Windows, a name cannot end with a '.'. So any dots or spaces at the end are removed.<br/>
   * Also commas are removed from the end. These are typically replacements of question marks.
   * 
   * @param name the absolute pathname to be converted
   * @return the converted <code>name</code>
   */
  public static String nameToFileName(String name, boolean getsExtension) {
      char[] chars = name.toCharArray();
      StringBuilder buf = new StringBuilder();
      
      for (int i = 0; i < chars.length; i++) {
        char c = chars[i];
        
        // remove double quotes.
        if ((c == '\"')) {
          buf.append('\'');
          continue;
        }
        
        // replace a colon by a semicolon.
        if ((c == ':')) {
          buf.append(';');
          continue;
        }
        
        // replace a '*' by a '+'.
        if ((c == '*')) {
          buf.append('+');
          continue;
        }
       
        // a question mark on the end or before quotes is simply removed. If it is in the middle we need some kind of separation,
        //  so we use a comma.
        if ((c == '?')) {
          if (i == chars.length - 1) {
            continue;
          } else {
            char next = chars[i + 1];
            if ((next == '\'')  ||  (next == '\"')) {
              continue;
            } else {
            buf.append(',');
            continue;
            }
          }
        }
        
        if (c == '/' ||  c == '\\') {
          if (buf.charAt(buf.length() - 1) == ' ') {
            buf.delete(buf.length() - 1, buf.length());
          }
          buf.append(',');
          continue;
        }
        
        // replace a '<' by a '['.
        if ((c == '<')) {
          buf.append('[');
          continue;
        }
        
        // replace a '>' by a ']'.
        if ((c == '>')) {
          buf.append(']');
          continue;
        }
        
        // replace a '|' by a '!'.
        if ((c == '|')) {
          buf.append('!');
          continue;
        }
                
        buf.append(c);
      }
      
      // In stupid Windows, a name cannot end with a '.'.
      // So remove any dots and spaces from the end.
      // Also remove comma's from the end (this can be a translated question mark, which wasn't at the end originally).
      if (!getsExtension) {
        boolean lastCharOk = false;
        do {
          char c = buf.charAt(buf.length() - 1);
          if (c == '.'  ||  c == ','  ||  c == ' ') {
            buf.delete(buf.length() - 1, buf.length());
          } else {
            lastCharOk = true;
          }
        } while (!lastCharOk);
      }
      

      LOGGER.fine("<= " + buf.toString());
      return buf.toString();
  }
  
  /**
   * Replace the group separator (' - ') by ', '.
   * <p>
   * In the naming conventions for files and folders, the sequence ' - ' is used as a separator between groups in the name (e.g. <trackNr> - <trackTitle>).<br/>
   * Therefore, within the groups, the group separator is replaced by ', '.
   * 
   * @param group The name group in which to replace the group separator. 
   * @return <code>group</code> with all instances of ' - ' replaced by ', '.
   */
  public static String replaceGroupSeparator(String group) {
    return group.replaceAll(" \\- ", "\\, ");
  }
  
  /**
   * Replace the group separator (' - ') by ', ' and remove special characters.
   * <p>
   * Calling this method is identical to calling: <code>nameToFileName(replaceGroupSeparator(group), getsExtended)</code>.
   * @param group
   * @param getsExtended
   * @return
   */
  public static String replaceGroupSeparatorAndNameToFileName(String group, boolean getsExtended) {
    return nameToFileName(replaceGroupSeparator(group), getsExtended);
  }

  /**
   * Check whether a file is a Windows Media Player picture.
   * <p>
   * The Windows Media Player files are files with one of the following naming schemes:<br/>
   * "^AlbumArt.*Small.jpg$"<br/>
   * "^AlbumArt.*Large.jpg$"<br/>
   * "^Folder.jpg$"
   * 
   * @param file the Path for the file to be checked.
   * @return true if the file is a Windows Media Player picture, false otherwise.
   */
  public static boolean isWindowsMediaPlayerPicture(Path file) {
    LOGGER.fine("=> file = " + file);

    boolean returnValue = false;

    String fileName = file.getFileName().toString();

    String regex = "^AlbumArt.*Small.jpg$";
    if (fileName.matches(regex)) {
      returnValue = true;
    }

    if (!returnValue) {
      regex = "^AlbumArt.*Large.jpg$";
      if (fileName.matches(regex)) {
        returnValue = true;
      }
    }

    if (!returnValue) {
      regex = "^Folder.jpg$";
      if (fileName.matches(regex)) {
        returnValue = true;
      }
    }

    LOGGER.fine("<= " + returnValue);

    return returnValue;
  }
}
