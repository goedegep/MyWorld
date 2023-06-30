package goedegep.pctools.filescontrolled.logic;

import java.io.File;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.pctools.filescontrolled.model.ControlledFolderInfo;
import goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo;
import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.EqualityType;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.pctools.filescontrolled.model.Result;
import goedegep.pctools.filescontrolled.types.FileCopyInfo;
import goedegep.pctools.filescontrolled.types.FileInfoMap;
import goedegep.util.Tuplet;
import javafx.concurrent.Task;

/**
 * This class is a {@link Task} for building a Controlled Set.
 * <p>
 * For all controlled directories in the Disc Structure Specification, the task iterates over all directories and files.
 * A FileInfoMap is created which contains, for each file, a mapping from the unique file identification to the file information.
 * While generating this map, files which are a 'copy' of a file already in the map are also kept in a separate list.
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
  
  private static final PCToolsFactory PCTOOLS_FACTORY = PCToolsFactory.eINSTANCE;

  /**
   * The {@link DiscStructureSpecification} to handle.
   */
  private final DiscStructureSpecification discStructureSpecification;
  
  /**
   * List of file names to be ignored.
   * In this task we're not using the description of the filesToIgnoreCompletely of the DiscStructureSpecification.
   * So from that list we create this list for an easy check on whether a file is to be ignored.
   */
  private List<String> filesToIgnoreCompletely = null;
  
  /**
   * List of directory names to be ignored.
   * In this task we're not using the description of the directoriesToIgnoreCompletely of the DiscStructureSpecification.
   * So from that list we create this list for an easy check on whether a directory is to be ignored.
   */
  private List<String> directoriesToIgnoreCompletely = null;
  
  private long totalSizeOfAllFiles = 0;
  private long totalSizeOfAllFilesHandled = 0;
  private Instant lastReportingMoment = null;
  private boolean resultChanged;
  private Result result;
  
  /**
   * Map from file size to its information.
   * To only compare files of same sizes.
   */
  private Map<Long, List<FileInfo>> sizeToFileInfosMap;
  
  /**
   * Constructor
   * 
   * @param discStructureSpecification the disc structure specification according to which the Controlled Set is to be created.
   * @param result the data stucture in which the Controlled Set will be created.
   */
  public ControlledSetBuildingTask(final DiscStructureSpecification discStructureSpecification, Result result) {
    updateTitle("Build Control Set Task");
    this.discStructureSpecification = discStructureSpecification;
    this.result = result;
    
    // Create a list of files to be ignored.
    filesToIgnoreCompletely = new ArrayList<>();
    for (DescribedItem fileToIgnoreCompletely: discStructureSpecification.getFilesToIgnoreCompletely()) {
      filesToIgnoreCompletely.add(fileToIgnoreCompletely.getItem());
    }
    
    // Create a list of directories to be ignored.
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
    
    sizeToFileInfosMap = new HashMap<>();
    
    // Initialise progress reporting
    totalSizeOfAllFilesHandled = 0;
    createControlledSet(true);  // determine totalSizeOfAllFiles
    updateProgress(totalSizeOfAllFilesHandled, totalSizeOfAllFiles);
    lastReportingMoment = Instant.now();
    resultChanged = false;

    // do the real work
    createControlledSet(false);
    
    LOGGER.info("<=");
    return null;
  }
  
  /**
   * Create a 'controlled set' in  {@code result}, or determine the {@code totalSizeOfAllFiles}.
   * <p>
   * This method calls handleControlledDirectory for each directory specification in the discStructureSpecification,
   * which is marked as 'controlled'.
   * 
   * @param countOnly if true, no information is generated, only the totalSizeOfAllFiles is calculated.
   */
  private void createControlledSet(boolean countOnly) {
    for (DirectorySpecification directorySpecification: discStructureSpecification.getDirectorySpecifications()) {
      if (!directorySpecification.isControlled()) {
        LOGGER.info("Skipping uncontrolled directory: " + directorySpecification.getDirectoryPath());
        continue;
      }
      
      LOGGER.severe("Handling directory: " + directorySpecification.getDirectoryPath());
      Path path = Paths.get(directorySpecification.getDirectoryPath());
      if (countOnly) {
        handleControlledDirectory(path, null, countOnly);
      } else {
        ControlledRootFolderInfo controlledRootFolderInfo = PCTOOLS_FACTORY.createControlledRootFolderInfo();
        result.getControlledrootfolderinfos().add(controlledRootFolderInfo);
        controlledRootFolderInfo.setFolderBasePath(directorySpecification.getDirectoryPath());
        File folder = new File(directorySpecification.getDirectoryPath());
        controlledRootFolderInfo.setFolderName(folder.getName());
        handleControlledDirectory(path, controlledRootFolderInfo, countOnly);
      }
    }
  }
  
  /**
   * Add 'controlled set' information for a directory (including all its subdirectories).
   * <p>
   * To handle subdirectories, this method recursively calls itself.
   * 
   * @param directory The directory to handle.
   * @param controlledFolderInfo the {@code ControlledFolderInfo} to which the information will be added.
   *        This value is ignored if {@code countOnly} is true, so in this case it can be null.
   * @param countOnly if true, no information is generated, only the totalSizeOfAllFiles is calculated.
   */
  private void handleControlledDirectory(Path directory, ControlledFolderInfo controlledFolderInfo, boolean countOnly) {
    LOGGER.info("=> path=" + directory.toString());
    
    // use this i.s.o. Files.walkFileTree() to be able to skip controlled directories and directories to ignore.
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
      for (Path file: stream) {
        String fileName = file.getFileName().toString();
        LOGGER.info("Handling file: " + fileName);
        
        if (Files.isDirectory(file)) {
          if (!countOnly) {
            updateMessage("Handling controlled directory: " + file);
            updateProgress(totalSizeOfAllFilesHandled, totalSizeOfAllFiles);
          }
          
          if (!directoriesToIgnoreCompletely.contains(fileName)) {
            if (countOnly) {
              handleControlledDirectory(file, null, countOnly);              
            } else {
              ControlledFolderInfo controlledSubFolderInfo = PCTOOLS_FACTORY.createControlledFolderInfo();
              controlledFolderInfo.getSubFolderInfos().add(controlledSubFolderInfo);
              controlledSubFolderInfo.setFolderName(fileName);
              handleControlledDirectory(file, controlledSubFolderInfo, countOnly);
            }
          } else {
            LOGGER.info("skipping directory to ignore completely: " + fileName);
          }
        } else {
//          LOGGER.fine("Handling file: " + file);
          if (!filesToIgnoreCompletely.contains(fileName)) {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            if (countOnly) {
              totalSizeOfAllFiles += attrs.size();
            } else {
//              if (addControlledFile(result.getObject1(), file, attrs, result.getObject2(), referenceDirectoryName)) {
              if (addControlledFile(controlledFolderInfo, fileName)) {
//                LOGGER.severe("Updating value");
//                updateValue(result.getObject2());
              }
              totalSizeOfAllFilesHandled += attrs.size();
              Instant now = Instant.now();
              Duration timeElapsed = Duration.between(lastReportingMoment, now);
//              LOGGER.info("now=" + now.toString() + ", lastReportingMoment=" + lastReportingMoment.toString() + ", timeElapsed=" + timeElapsed.toString());
              if (timeElapsed.compareTo(REPORTING_INTERVAL) > 0) {
//                LOGGER.severe("now=" + now.toString() + ", lastReportingMoment=" + lastReportingMoment.toString() + ", timeElapsed=" + timeElapsed.toString());
                updateProgress(totalSizeOfAllFilesHandled, totalSizeOfAllFiles);
                updateMessage("Handled controlled file: " + file);
                if (resultChanged) {
//                  updateValue(result.getObject2());
                }
                lastReportingMoment = now;
                resultChanged = false;
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

  /**
   * Handle a single controlled file.
   * <p>
   * The fileId is generated and a {@code FileInfo} is created for the file, containing its file name and directory.
   * If there isn't a controlled file with this fileId yet, the file is added to {@code controlledFiles}.
   * Otherwise, the {@code EqualityType} is determined, {@code FileCopyInfo} is created and this is added to the {@code fileCopyInfos}.
   * 
   * @param controlledFiles The list of controlled files (which is being built).
   * @param file The file to handle
   * @param attrs Attributes of the {@code file}.
   * @param fileCopyInfos The list of files with copies (which is being built).
   * @return true if {@code file} is a 'copy' of a controlled file.
   * @throws IOException In case something goes wrong.
   */
//  private boolean addControlledFile(FileInfoMap controlledFiles, Path file, BasicFileAttributes attrs, List<FileCopyInfo> fileCopyInfos, String referenceDirectoryName) throws IOException {
  private boolean addControlledFile(ControlledFolderInfo controlledFolderInfo, String fileName) throws IOException {
    LOGGER.fine("=>");
    
    boolean fileIsACopy = false;
//    String fileId = FilesControlled.generateFileId(file, attrs);
//    FileInfo fileInfo = PCTOOLS_FACTORY.createFileInfo();
//    fileInfo.setDirectoryName(FileUtils.getPathRelativeToFolder(referenceDirectoryName, file.getParent().toString()));
//    fileInfo.setFileName(file.getFileName().toString());
//    LOGGER.fine("Adding fileInfo: " + fileInfo.toString());
    
//    // check whether this file already exists, if so create 'copy found' report.
//    FileInfo copyFileInfo = controlledFiles.get(fileId);
//    if (copyFileInfo == null) {
//      controlledFiles.put(fileId, fileInfo);
//    } else {
//      // sizes are the same but files may differ, so add more information
//      FileCopyInfo fileCopyInfo = new FileCopyInfo();
//      fileCopyInfo.setFirstFileFoundInfo(copyFileInfo);
//      fileCopyInfo.setSecondFileFoundInfo(fileInfo);
//      fileCopyInfo.setEqualityType(EqualityType.SIZE);
//      
//      if (copyFileInfo.getMd5String().equals(fileInfo.getMd5String())) {
//        fileCopyInfo.setEqualityType(EqualityType.MD5);
//        
//        if (FileUtils.contentEquals(copyFileInfo.getFullPathname(), fileInfo.getFullPathname())) {
//          fileCopyInfo.setEqualityType(EqualityType.EQUAL);
//        }
//      }
//
//      fileCopyInfos.add(fileCopyInfo);
//      resultChanged = true;
//      fileIsACopy = true;
//    }
    
    FileInfo controlledFileInfo = PCTOOLS_FACTORY.createFileInfo();
    controlledFolderInfo.getFileinfos().add(controlledFileInfo);
    controlledFileInfo.setFileName(fileName);
    FileInfo copyFileInfo = isFileACopy(controlledFileInfo);
    if (copyFileInfo != null) {
      controlledFileInfo.setCopyOf(copyFileInfo);
    }
    
    BasicFileAttributes attrs;
    try {
      attrs = Files.readAttributes(Paths.get(controlledFileInfo.getFullPathname()), BasicFileAttributes.class);
      Long fileSize = attrs.size();
      List<FileInfo> fileInfos = sizeToFileInfosMap.get(fileSize);
      if (fileInfos == null) {
        fileInfos = new ArrayList<FileInfo>();
        sizeToFileInfosMap.put(fileSize, fileInfos);
      }
      fileInfos.add(controlledFileInfo);
    } catch (IOException e) {
      e.printStackTrace();
    }

    
//    controlledFileInfo.setMd5String(fileInfo.getMd5String());
//    controlledFileInfo.setUnControlled(false);
    
    LOGGER.fine("<= " + fileIsACopy);
//    return fileIsACopy;
    return false;
  }
  
  /**
   * Check whether a file is a copy of one of already handled files.
   * 
   * @param fileInfo the file to check.
   * @return the FileInfo of the file of which {@code fileInfo} is a copy, or null if the file is not a copy.
   */
  private FileInfo isFileACopy(FileInfo fileInfo) {
    BasicFileAttributes attrs;
    try {
      attrs = Files.readAttributes(Paths.get(fileInfo.getFullPathname()), BasicFileAttributes.class);
      Long fileSize = attrs.size();
      List<FileInfo> fileInfos = sizeToFileInfosMap.get(fileSize);
      if (fileInfos != null) {
        String thisMd5String = fileInfo.getMd5String();
        
        for (FileInfo checkFileInfo: fileInfos) {
          if (thisMd5String.equals(checkFileInfo.getMd5String()) &&
              fileInfo.getFileName().equals(checkFileInfo.getFileName())) {
            LOGGER.severe("Copy found: " + fileInfo.toString() + ", " + checkFileInfo.toString());
            return checkFileInfo;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
        
    return null;
  }
}

