package goedegep.util.dir;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * This is a utility class, with methods that operate on directories (a Path, which represents a directory).
 */
public final class DirectoryUtils {
  private final static Logger LOGGER = Logger.getLogger(DirectoryUtils.class.getName()); 
  
  /**
   * Private constructor, as this is a utility class which cannot be instantiated.
   */
  private DirectoryUtils() {
  }

  
  /**
   * Check that the files in one directory also exist in some other directory.
   * 
   * @param oneDirectoryPath a Path to the 'one directory'.
   * @param otherDirectoryPath a Path to 'some other directory'.
   * @param directoryCheckOptions the {@link DirectoryCheckOptions}.
   * @return true, if all files in the 'one directory' also exist in 'some other directory', false otherwise.
   */
  public static boolean checkThatFilesInOneDirectoryAlsoExistInOtherDirectory(Path oneDirectoryPath, Path otherDirectoryPath, DirectoryCheckOptions directoryCheckOptions) {
    LOGGER.info("=> oneDirectoryPath=" + oneDirectoryPath.toAbsolutePath().toString() + ", otherDirectoryPath=" + otherDirectoryPath.toAbsolutePath().toString() + ", recurseIntoSubDirectories=" + directoryCheckOptions);
    boolean returnValue = true;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(oneDirectoryPath)) {
      for (Path file: stream) {        
        LOGGER.info("Handling file: " + file.toAbsolutePath().toString());
        
        if (Files.isDirectory(file)) {
          // First check whether directories with this name have to be ignored.
          if (directoryCheckOptions.getIgnoreDirectoryNames().contains(file.getFileName().toString())) {
            LOGGER.info("Skipping directory because it is in the ignore list: "  + file.toAbsolutePath().toString());
          } else if (directoryCheckOptions.isRecurseIntoSubDirectories()) {  // Recurse into sub directories if specified.
            Path fileToCheck = otherDirectoryPath.resolve(file.getFileName().toString());
            LOGGER.info("Checking the existance of directory: " + fileToCheck.toAbsolutePath().getFileName());
            if (!Files.exists(fileToCheck)) {
              System.out.println("Directory not in otherDirectory: " + file.toAbsolutePath().toString());
              returnValue = false;
            } else {
              if (!checkThatFilesInOneDirectoryAlsoExistInOtherDirectory(file, fileToCheck, directoryCheckOptions)) {
                returnValue = false;
              }
            }
          } else {  // If we don't recurse into sub directories, any directory found results in a failed check
            System.out.println("oneDirectory contains a subdirectory, which is not checked: " + file.toAbsolutePath().toString());
            returnValue = false;
          }
        } else {
          Path fileToCheck = otherDirectoryPath.resolve(file.getFileName().toString());
          LOGGER.info("Checking the existance of file: " + fileToCheck.toAbsolutePath().getFileName());
          if (!Files.exists(fileToCheck)) {
            if (directoryCheckOptions.isCheckWhetherFileIsMovedIntoSubDirectory()) {
              Path movedToSubDirectory = checkWhetherFileIsMovedIntoSubDirectory(file, otherDirectoryPath);
              if (movedToSubDirectory == null) {
                System.out.println("File not in otherDirectory: " + file.toAbsolutePath().toString());
                returnValue = false;
              } else {
                System.out.println("File: " + file.toAbsolutePath().toString() + ", moved to sub directory: " + movedToSubDirectory.toAbsolutePath().toString());
              }
            } else {
              System.out.println("File not in otherDirectory: " + file.toAbsolutePath().toString());
              returnValue = false;
            }
          } else {
            long size = Files.size(file);
            long fileToCheckSize = Files.size(fileToCheck);
            LOGGER.info("one size: " + size + ", other size" + fileToCheckSize);
            if (size != fileToCheckSize) {
              System.out.println("File sizes differ for file: " + file.toAbsolutePath().toString() + ", this size: " + size + ", other size" + fileToCheckSize);
              returnValue = false;
            }
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return returnValue;
  }

  /**
   * Check that the files in one directory also exist in some other directory.
   * 
   * @param oneDirectoryName Name of the 'one directory'.
   * @param otherDirectoryName Name of 'some other directory'.
   * @param directoryCheckOptions the {@link DirectoryCheckOptions}.
   * @return true, if all files in the 'one directory' also exist in 'some other directory', false otherwise.
   */
  public static boolean checkThatFilesInOneDirectoryAlsoExistInOtherDirectory(String oneDirectoryName, String otherDirectoryName, DirectoryCheckOptions directoryCheckOptions) {
    Path oneDirectoryPath = Paths.get(oneDirectoryName);
    Path otherDirectoryPath = Paths.get(otherDirectoryName);

    return checkThatFilesInOneDirectoryAlsoExistInOtherDirectory(oneDirectoryPath, otherDirectoryPath, directoryCheckOptions);
  }

  /**
   * Check whether a file exists in a directory or in any of its sub-directories.
   * 
   * @param file A Path for the file to check.
   * @param directory The directory in which to search for the <code>file</code>.
   * @return true, if the <code>file</code> exists below the <code>directory</code>, false otherwise.
   */
  private static Path checkWhetherFileIsMovedIntoSubDirectory(Path file, Path directory) {
    LOGGER.info("=> file=" + file.toAbsolutePath().toString() + ", movedToParentDirectoryPath" + directory.toAbsolutePath().toString());
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
      for (Path checkDirectory: stream) {        
        LOGGER.info("Checking in directory: " + checkDirectory.toAbsolutePath().toString());
        if (Files.isDirectory(checkDirectory)) {
          Path fileToCheck = directory.resolve(checkDirectory.getFileName().toString()).resolve(file.getFileName().toString());
          LOGGER.info("Checking the existance of file: " + fileToCheck.toAbsolutePath().toString());
          if (Files.exists(fileToCheck)) {
            LOGGER.info("Found");
            return fileToCheck;
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    return null;
  }
  
}
