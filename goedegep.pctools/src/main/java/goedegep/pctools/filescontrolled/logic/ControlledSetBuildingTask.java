package goedegep.pctools.filescontrolled.logic;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.types.EqualityType;
import goedegep.pctools.filescontrolled.types.FileCopyInfo;
import goedegep.pctools.filescontrolled.types.FileInfo;
import goedegep.pctools.filescontrolled.types.FileInfoMap;
import goedegep.util.Tuplet;
import goedegep.util.file.FileUtils;
import javafx.concurrent.Task;

/**
 * This class is a {@link Task} for building a Controlled Set.
 * <p>
 * For all controlled directories in the Disc Structure Specification, the task iterates over all directories and files.
 * A FileInfoMap is created which contains, for each file, a mapping from the unique file identification to the file information.
 * While generating this map, files which are a 'copy' of a file already in the map are kept in a separate list.
 * <p>
 * <b>Equality levels</b><br/>
 * Mainly for performance reasons, three equality types {@link EqualityType} are used:
 * <ul>
 * <li>SIZE<br/>
 * The first check, which is very fast, is only based on the name of the files and their size.
 * </li>
 * <li>MD5<br/>
 * For files with the same name and size, an MD5 hash is calculated. If these values are also equal, the equality type is
 * raised to this level. This is an almost 100% check, but not fully.
 * </li>
 * <li>EQUAL<br/>
 * For files which are MD5 equal a real byte by byte compare is performed. If this is positive, the equality type is raised to this level.
 * </li>
 * </ul>
 * 
 * <p>
 * <b>Progress reporting</b><br/>
 * As usual, good progress reporting is hardly possible. In this case the amount of work depends on the total amount of files and their sizes, and
 * (most important) the total amount of data for which MD5 hashes have to be generated and byte by byte compare has to be performed. Therefore:
 * <ul>
 * <li>
 * Before any progress is reported, the total number of bytes to handle is calculated.
 * </li>
 * <li>
 * During the actual work, after handling each file and if 3 seconds have passed since the last report:
 * <ul>
 * <li>
 * progress is reported
 * </li>
 * <li>
 * the message is updated with the last file handled
 * </li>
 * <li>
 * and, if new copies are found, the intermediate result is provided.
 * </li>
 * <ul>
 * </li>
 * </ul>
 * 
 */
public class ControlledSetBuildingTask extends Task<Tuplet<FileInfoMap, List<FileCopyInfo>>> {
  private static final Logger LOGGER = Logger.getLogger(ControlledSetBuildingTask.class.getName());
  private static final Duration REPORTING_INTERVAL = Duration.ofSeconds(3l);

  private final DiscStructureSpecification discStructureSpecification;
  private List<String> filesToIgnoreCompletely = null;
  private List<String> directoriesToIgnoreCompletely = null;
  
  private long totalSizeOfAllFiles = 0;
  private long totalSizeOfAllFilesHandled = 0;
  private Instant lastReportingMoment = null;
  private boolean resultChanged;
  
  /**
   * Constructor
   * 
   * @param discStructureSpecification the disc structure specification according to which the Controlled Set is to be created.
   */
  public ControlledSetBuildingTask(final DiscStructureSpecification discStructureSpecification) {
    updateTitle("Build Control Set Task");
    this.discStructureSpecification = discStructureSpecification;
    
    filesToIgnoreCompletely = new ArrayList<>();
    for (DescribedItem fileToIgnoreCompletely: discStructureSpecification.getFilesToIgnoreCompletely()) {
      filesToIgnoreCompletely.add(fileToIgnoreCompletely.getItem());
    }
    directoriesToIgnoreCompletely = new ArrayList<>();
    for (DescribedItem directoryToIgnoreCompletely: discStructureSpecification.getDirectoriesToIgnoreCompletely()) {
      directoriesToIgnoreCompletely.add(directoryToIgnoreCompletely.getItem());
    }
  }

  /**
   * Do the actual work.
   */
  @Override
  protected Tuplet<FileInfoMap, List<FileCopyInfo>> call() throws Exception {
    LOGGER.info("=>");
    
    // Set up result data structures
    FileInfoMap controlledFiles = new FileInfoMap();
    List<FileCopyInfo> fileCopyInfos = new ArrayList<>();
    Tuplet<FileInfoMap, List<FileCopyInfo>> result = new Tuplet<>(controlledFiles, fileCopyInfos);
    
    // Initialise progress reporting
    createControlledSet(null, true);
    updateProgress(totalSizeOfAllFilesHandled, totalSizeOfAllFiles);
    lastReportingMoment = Instant.now();
    resultChanged = false;

    // do the real work
    createControlledSet(result, false);
    
    LOGGER.info("<=");
    return result;
  }
  
  /**
   * Create a 'controlled set'.
   * <p>
   * This method calls handleControlledDirectory for each directory specification in the discStructureSpecification,
   * which is marked as 'controlled'.
   * 
   * @param result a tuplet via which the result will be provided
   * @param countOnly if true, no information is generated, only the totalSizeOfAllFiles is calculated.
   */
  private void createControlledSet(Tuplet<FileInfoMap, List<FileCopyInfo>> result, boolean countOnly) {
    for (DirectorySpecification directorySpecification: discStructureSpecification.getDirectorySpecifications()) {
      if (!directorySpecification.isControlled()) {
        LOGGER.info("Skipping uncontrolled directory: " + directorySpecification.getDirectoryPath());
        continue;
      }
      
      LOGGER.info("Handling directory: " + directorySpecification.getDirectoryPath());
      Path path = Paths.get(directorySpecification.getDirectoryPath());
      
      handleControlledDirectory(path, result, countOnly);
    }
  }
  
  /**
   * Add 'controlled set' information for a directory (including all its subdirectories).
   * <p>
   * To handle subdirectories, this method recursively calls itself.
   * 
   * @param path the directory for which the information is to be generated
   * @param result a tuplet via which the result will be provided
   * @param countOnly if true, no information is generated, only the totalSizeOfAllFiles is calculated.
   */
  private void handleControlledDirectory(Path path, Tuplet<FileInfoMap, List<FileCopyInfo>> result, boolean countOnly) {
    LOGGER.info("=>");
    // use this i.s.o. Files.walkFileTree() to be able to skip controlled directories and directories to ignore.
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
      for (Path file: stream) {
        String fileName = file.getFileName().toString();
        LOGGER.fine("Handling file: " + fileName);
        
        if (Files.isDirectory(file)) {
          LOGGER.fine("handling dir: " + file);
          if (!countOnly) {
//            updateMessage("Handling controlled directory: " + file);
//            updateProgress(totalSizeOfAllFilesHandled, totalSizeOfAllFiles);
          }
          if (!directoriesToIgnoreCompletely.contains(fileName)) {
            handleControlledDirectory(file, result, countOnly);
          } else {
            LOGGER.info("skipping directory to ignore completely: " + fileName);
          }
        } else {
          LOGGER.fine("Handling file: " + file);
          LOGGER.fine("fileName: " + fileName);
          if (!filesToIgnoreCompletely.contains(fileName)) {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            if (countOnly) {
              totalSizeOfAllFiles += attrs.size();
            } else {
              if (addControlledFile(result.getObject1(), file, attrs, result.getObject2())) {
                LOGGER.info("Updating value");
//                updateValue(result.getObject2());
              }
              totalSizeOfAllFilesHandled += attrs.size();
              Instant now = Instant.now();
              Duration timeElapsed = Duration.between(lastReportingMoment, now);
              LOGGER.info("now=" + now.toString() + ", lastReportingMoment=" + lastReportingMoment.toString() + ", timeElapsed=" + timeElapsed.toString());
              if (timeElapsed.compareTo(REPORTING_INTERVAL) > 0) {
                LOGGER.fine("now=" + now.toString() + ", lastReportingMoment=" + lastReportingMoment.toString() + ", timeElapsed=" + timeElapsed.toString());
                LOGGER.fine("REPORT");
                updateProgress(totalSizeOfAllFilesHandled, totalSizeOfAllFiles);
                updateMessage("Handled controlled file: " + file);
                if (resultChanged) {
                  updateValue(result.getObject2());
                }
                lastReportingMoment = now;
                resultChanged = false;
              } else {
                LOGGER.fine("DON'T REPORT");
              }
            }
          } else {
            LOGGER.info("skipping file to ignore completely: " + fileName);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      if (x instanceof ClosedByInterruptException) {
        LOGGER.severe("ClosedByInterruptException!!!!!!!!!!!!!!!!");
      } else {
        LOGGER.severe("Exception: " + x);
        x.printStackTrace();
      }
    }
    LOGGER.info("<=");
  }
  
  private void updateValue(List<FileCopyInfo> fileCopyInfos) {
    List<FileCopyInfo> fileCopyInfosClone = new ArrayList<>(fileCopyInfos);
    Tuplet<FileInfoMap, List<FileCopyInfo>> result = new Tuplet<>(null, fileCopyInfosClone);
    
    updateValue(result);
  }

  private boolean addControlledFile(FileInfoMap controlledFiles, Path file, BasicFileAttributes attrs, List<FileCopyInfo> fileCopyInfos) throws IOException {
    LOGGER.fine("=>");
    
    boolean fileIsACopy = false;
    String fileId = FilesControlled.generateFileId(file, attrs);
    
    FileInfo fileInfo = new FileInfo();
    fileInfo.setDirectory(file.getParent());
    fileInfo.setFile(file.getFileName());
    LOGGER.fine("Adding fileInfo: " + fileInfo.toString());
    
    // check whether this file already exists, if so create 'copy found' report.
    FileInfo copyFileInfo = controlledFiles.get(fileId);
    if (copyFileInfo == null) {
      controlledFiles.put(fileId, fileInfo);
    } else {
      // sizes are the same but files may differ, so add more information
      FileCopyInfo fileCopyInfo = new FileCopyInfo();
      fileCopyInfo.setFirstFileFoundInfo(copyFileInfo);
      fileCopyInfo.setSecondFileFoundInfo(fileInfo);
      fileCopyInfo.setEqualityType(EqualityType.SIZE);
      
      if (copyFileInfo.getMd5String().equals(fileInfo.getMd5String())) {
        fileCopyInfo.setEqualityType(EqualityType.MD5);
        
        if (FileUtils.contentEquals(copyFileInfo.getFullPath(), fileInfo.getFullPath())) {
          fileCopyInfo.setEqualityType(EqualityType.EQUAL);
        }
      }

      fileCopyInfos.add(fileCopyInfo);
      resultChanged = true;
      fileIsACopy = true;
    }
    
    LOGGER.fine("<= " + fileIsACopy);
    return fileIsACopy;
  }
}

