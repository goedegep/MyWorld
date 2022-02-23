package goedegep.pctools.filefinder.logic;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.concurrent.Task;

public class FileFinderTask extends Task<List<String>> {
  private static final Logger LOGGER = Logger.getLogger(FileFinderTask.class.getName());
  
  private String searchFolder;
  private long totalNumberOfFiles;
  private int numberOfFilesHandled;
  
  /**
   * Constructor
   * 
   * @param discStructureSpecification the disc structure specification according to which the Controlled Set is to be created.
   */
  public FileFinderTask(final String searchFolder) {
    updateTitle("Find Finder Task");
    this.searchFolder = searchFolder;
  }

  /**
   * Do the actual work.
   */
  @Override
  protected List<String> call() throws Exception {
    LOGGER.info("=>");
    
    List<String> foundFiles = new ArrayList<>();

    Path searchPath = Paths.get(searchFolder);
    totalNumberOfFiles = fileCount(searchPath);
    numberOfFilesHandled = 0;
    updateMessage("Searching FrameMaker files in folder: " + searchFolder);
    updateProgress(numberOfFilesHandled, totalNumberOfFiles);
    findFrameMakerFiles(foundFiles, searchPath);
    updateMessage("Search complete, " + foundFiles.size() + " files found");
    
    LOGGER.info("<=");
    return foundFiles;
  }

  public void findFrameMakerFiles(List<String> foundFiles, Path directory) {
    try {
      Files.walkFileTree(directory, new FileVisitor<Path>() {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          // no action
          return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          if (FileFinder.isFramemakerFile(file.toAbsolutePath().toString(), attrs)) {
            foundFiles.add(file.toAbsolutePath().toString());
            updateValue(foundFiles);
          }
          updateProgress(++numberOfFilesHandled, totalNumberOfFiles);
          return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
          System.out.println("visitFileFailed: file=" + file.toAbsolutePath().toString());
          return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          // no action
          return CONTINUE;
        }
        
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
  /**
   * Count the number of files in a folder.
   * <p>
   * This method counts the number of files in the specified folder.
   * 
   * @param folder the folder in which to count the files
   * @return the number of files in folder {@code folder}.
   */
  public long fileCount(Path dir) {
    try {
      return Files.walk(dir)
                  .parallel()
                  .filter(p -> !p.toFile().isDirectory())
                  .count();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
