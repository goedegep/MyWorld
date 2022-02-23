package goedegep.pctools.filescontrolled.logic;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
 * A {@link Task} to check for uncontrolled files and probable copies of files.
 * <p>
 * A DiscStructureSpecification specified the directories to check.<br/>
 * A FileInfoMap provides a collection of controlled files to check against.
 */
public class CheckFilesTask extends Task<Tuplet<List<FileCopyInfo>, List<FileInfo>>> {
  private static final Logger LOGGER = Logger.getLogger(FilesControlled.class.getName());

  /**
   * The collection of controlled files to check against.
   */
  private FileInfoMap controlledFiles;
  
  /**
   * The specification of the directories to check.
   */
  private final DiscStructureSpecification discStructureSpecification;
  
  /**
   * Constructor.
   * 
   * @param controlledFiles a collection of controlled files to check against.
   * @param discStructureSpecification  Specification of the directories to check.
   */
  public CheckFilesTask(FileInfoMap controlledFiles, final DiscStructureSpecification discStructureSpecification) {
    this.controlledFiles = controlledFiles;
    this.discStructureSpecification = discStructureSpecification;
  }

  @Override
  protected Tuplet<List<FileCopyInfo>, List<FileInfo>> call() throws Exception {
    LOGGER.info("=>");
    List<FileCopyInfo> probablyRemovableFileCopyInfos = new ArrayList<>();
    List<FileInfo> uncontrolledFiles = new ArrayList<>();
    Tuplet<List<FileCopyInfo>, List<FileInfo>> result = new Tuplet<>(probablyRemovableFileCopyInfos, uncontrolledFiles);
    
    checkFiles(controlledFiles, discStructureSpecification, probablyRemovableFileCopyInfos, uncontrolledFiles);
          
    LOGGER.info("<=");
    return result;
  }
  
  /**
   * Check for uncontrolled files and files which are probably copies of controlled files in the uncontrolled directories
   * specified by a DiscStructureSpecification.
   * 
   * @param controlledFiles a collection of controlled files to check against.
   * @param discStructureSpecification Specification of the directories to check.
   * @param probablyRemovableFileCopyInfos the list to which probable copies will be added.
   * @param uncontrolledFiles the list to which uncontrolled files are added.
   */
  private void checkFiles(FileInfoMap controlledFiles, DiscStructureSpecification discStructureSpecification, List<FileCopyInfo> probablyRemovableFileCopyInfos, List<FileInfo> uncontrolledFiles) {
    LOGGER.fine("=>");
    
    for (DirectorySpecification directorySpecification: discStructureSpecification.getDirectorySpecifications()) {
      if (directorySpecification.isControlled()) {
        LOGGER.fine("Skipping controlled directory: " + directorySpecification.getDirectoryPath());
        continue;
      }
      
      Path directory = Paths.get(directorySpecification.getDirectoryPath());
      checkFilesInDirectory(controlledFiles, directory, probablyRemovableFileCopyInfos, uncontrolledFiles);
    }
        
    LOGGER.fine("<=");
  }

  /**
   * Check for uncontrolled files and files which are probably copies of controlled files in a directory.
   * <p>
   * This method calls itself recursively for each sub directory.
   * 
   * @param controlledFiles a collection of controlled files to check against.
   * @param directory the directory in which the files are to be checked.
   * @param probablyRemovableFileCopyInfos the list to which probable copies will be added.
   * @param uncontrolledFiles the list to which uncontrolled files are added.
   */
  private void checkFilesInDirectory(FileInfoMap controlledFiles, Path directory,
      List<FileCopyInfo> probablyRemovableFileCopyInfos, List<FileInfo> uncontrolledFiles) {
    // Note: we cannot use Files.walkFileTree(), because we have to skip controlled directories and directories to ignore.
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
      for (Path file: stream) {
        LOGGER.fine("Handling file/directory: " + file.getFileName().toString());
        if (Files.isDirectory(file)) {
          // recursively call this method.
          checkFilesInDirectory(controlledFiles, file, probablyRemovableFileCopyInfos, uncontrolledFiles);
        } else {
          // For each file in the directory: generate the fileId and create the FileInfo
          BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
          String fileId = FilesControlled.generateFileId(file, attr);
          FileInfo thisFileInfo = new FileInfo();
          thisFileInfo.setDirectory(file.getParent());
          thisFileInfo.setFile(file.getFileName());
          
          // Check whether a file with the same id exists in the controlled files.
          FileInfo fileInfo = controlledFiles.get(fileId);
          if (fileInfo == null) {
            // No, so we've found an uncontrolled file.
            uncontrolledFiles.add(thisFileInfo);
            LOGGER.fine("Uncontrolled file found: " + thisFileInfo.toString());
          } else {
            // names and sizes are the same but files still may differ. So add more information based on same MD5 or real equality.
            FileCopyInfo fileCopyInfo = new FileCopyInfo();
            fileCopyInfo.setFirstFileFoundInfo(fileInfo);
            fileCopyInfo.setSecondFileFoundInfo(thisFileInfo);
            fileCopyInfo.setEqualityType(EqualityType.SIZE);
            
            if (fileInfo.getMd5String().equals(thisFileInfo.getMd5String())) {
              fileCopyInfo.setEqualityType(EqualityType.MD5);
              
              if (FileUtils.contentEquals(fileInfo.getFullPath(), thisFileInfo.getFullPath())) {
                fileCopyInfo.setEqualityType(EqualityType.EQUAL);
              }
            }
            
            probablyRemovableFileCopyInfos.add(fileCopyInfo);
            LOGGER.fine("Copy of file found: " + fileInfo.toString());
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    updateMessage("Directory checked: " + directory.toString());
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
  }
}