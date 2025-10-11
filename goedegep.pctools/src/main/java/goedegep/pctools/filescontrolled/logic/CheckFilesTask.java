package goedegep.pctools.filescontrolled.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.EqualityType;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.pctools.filescontrolled.model.Result;
import goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo;
import goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo;
import javafx.concurrent.Task;

/**
 * A {@link Task} to check for uncontrolled files and probable copies of files.
 * <p>
 * A DiscStructureSpecification specified the directories to check.<br/>
 * A FileInfoMap provides a collection of controlled files to check against.
 */
public class CheckFilesTask extends Task<Result> {
  private static final Logger LOGGER = Logger.getLogger(FilesControlled.class.getName());
  
  private static final PCToolsFactory PCTOOLS_FACTORY = PCToolsFactory.eINSTANCE;

  /**
   * The specification of the directories to check.
   */
  private final DiscStructureSpecification discStructureSpecification;
  
  private static Path tmpFilesPath;
  
  /**
   * Structure in which gathered information is stored.
   */
  private Result result;

  static {
    // Create an in-memory file system for faster file compare
    FileSystem imfs = Jimfs.newFileSystem(Configuration.unix());
    tmpFilesPath = imfs.getPath("/tmpPdf");
    try {
      Files.createDirectory(tmpFilesPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Constructor.
   * 
   * @param discStructureSpecification  Specification of the directories to check.
   * @param result Structure in which the gathered information will be stored.
   */
  public CheckFilesTask(final DiscStructureSpecification discStructureSpecification, Result result) {
    this.discStructureSpecification = discStructureSpecification;
    this.result = result;
  }

  /**
   * Run the files check.
   */
  @Override
  protected Result call() throws Exception {
    LOGGER.severe("=>");
    
    checkFiles(discStructureSpecification, result);
          
    LOGGER.severe("<=");
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
//  private void checkFiles(FileInfoMap controlledFiles, DiscStructureSpecification discStructureSpecification, Result result) {
  private void checkFiles(DiscStructureSpecification discStructureSpecification, Result result) {
    LOGGER.severe("=>");
    
    for (DirectorySpecification directorySpecification: discStructureSpecification.getDirectorySpecifications()) {
      if (directorySpecification.isControlled()) {
        LOGGER.severe("Skipping controlled directory: " + directorySpecification.getDirectoryPath());
        continue;
      }
      
//      Path directory = Paths.get(directorySpecification.getDirectoryPath());
//      FileInfoTreeNode fileInfoTreeNode = new FileInfoTreeNode();
//      fileInfoTreeNode.setContent(directory);
//      fileInfoTreeNode.setFolder(true);
      UncontrolledRootFolderInfo uncontrolledRootFolderInfo = PCTOOLS_FACTORY.createUncontrolledRootFolderInfo();
      result.getUncontrolledRootFolderInfos().add(uncontrolledRootFolderInfo);
      uncontrolledRootFolderInfo.setFolderBasePath(directorySpecification.getDirectoryPath());
      File folder = new File(directorySpecification.getDirectoryPath());
      uncontrolledRootFolderInfo.setFolderName(folder.getName());
      checkFilesInDirectory(uncontrolledRootFolderInfo);
//      printInfo(fileInfoTreeNode);
    }
        
    LOGGER.severe("<=");
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
  private void checkFilesInDirectory(UncontrolledFolderInfo uncontrolledFolderInfo) {
    
    // Note: we cannot use Files.walkFileTree(), because we have to skip controlled directories and directories to ignore.
    LOGGER.severe("=> directory=" + uncontrolledFolderInfo.getFullPathname());
    boolean allContentsHasCopies = true;
    Path directory = Paths.get(uncontrolledFolderInfo.getFullPathname());
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
      for (Path file: stream) {
        LOGGER.info("Handling file/directory: " + file.getFileName().toString());
        if (Files.isDirectory(file)) {
          // recursively call this method.
          UncontrolledFolderInfo childFolderInfo = PCTOOLS_FACTORY.createUncontrolledFolderInfo();
          uncontrolledFolderInfo.getSubFoldersInfos().add(childFolderInfo);
          childFolderInfo.setFolderName(file.getFileName().toString());
          checkFilesInDirectory(childFolderInfo);
          if (!childFolderInfo.isAllContentsHasCopies()) {
            allContentsHasCopies = false;
          }
        } else {
          // For each file in the directory: generate the fileId and create the FileInfo
          BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
          String fileId = FilesControlled.generateFileId(file, attr);
          FileInfo thisFileInfo = PCTOOLS_FACTORY.createFileInfo();
          uncontrolledFolderInfo.getFileinfos().add(thisFileInfo);
          thisFileInfo.setFileName(file.getFileName().toString());
//          thisFileInfo.setDirectoryName(directory.toString());
          
//          // Check whether a file with the same id exists in the controlled files.
//          FileInfo copyFileInfo = controlledFiles.get(fileId);
//          if (copyFileInfo == null) {
//            // No, so we've found an uncontrolled file.
////            uncontrolledFiles.add(thisFileInfo);
//            thisFileInfo.setUnControlled(true);
//            LOGGER.severe("Uncontrolled file found: " + thisFileInfo.toString());
//            allContentsHasCopies = false;
//          } else {
//            // names and sizes are the same but files still may differ. So add more information based on same MD5 or real equality.
////            FileCopyInfo fileCopyInfo = new FileCopyInfo();
////            fileCopyInfo.setFirstFileFoundInfo(fileInfo);
//            thisFileInfo.setCopyOf(copyFileInfo);
////            fileCopyInfo.setSecondFileFoundInfo(thisFileInfo);
//            thisFileInfo.setEqualityType(EqualityType.SIZE);
//            
//            if (copyFileInfo.getMd5String().equals(thisFileInfo.getMd5String())) {
//              thisFileInfo.setEqualityType(EqualityType.MD5);
//              
//              Path tmpFile1 = tmpFilesPath.resolve("file1");
//              Path tmpFile2 = tmpFilesPath.resolve("file2");
//              Files.copy(Paths.get(copyFileInfo.getFullPathname()), tmpFile1);
//              Files.copy(Paths.get(thisFileInfo.getFullPathname()), tmpFile2);
//              if (FileUtils.contentEquals(tmpFile1, tmpFile2)) {
//                thisFileInfo.setEqualityType(EqualityType.EQUAL);
//              }
//              Files.delete(tmpFile1);
//              Files.delete(tmpFile2);
//            } else {
//              LOGGER.severe("Different MD5 values: copy=" + copyFileInfo.getMd5String() + ", this=" + thisFileInfo.getMd5String());
//            }
//            
////            probablyRemovableFileCopyInfos.add(fileCopyInfo);
//            if (!(thisFileInfo.getEqualityType() == EqualityType.EQUAL)) {
//              allContentsHasCopies = false;
//            }
//            LOGGER.severe("Copy of file found: " + thisFileInfo.toString());
//          }
          
          FileInfo copyFileInfo = isFileACopy(thisFileInfo);
          if (copyFileInfo != null) {
            thisFileInfo.setEqualityType(EqualityType.EQUAL);
            thisFileInfo.setCopyOf(copyFileInfo);
          } else {
            allContentsHasCopies = false;
          }
          
        }
      }
      uncontrolledFolderInfo.setAllContentsHasCopies(allContentsHasCopies);
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    updateMessage("Directory checked: " + directory.toString());
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    LOGGER.info("<=");
    
  }

  /**
   * Check whether a file is a copy of one of the controlled files.
   * 
   * @param fileInfo the file to check, typically not a controlled file.
   * @return the FileInfo of the file of which {@code fileInfo} is a copy, or null if the file is not a copy.
   */
  private FileInfo isFileACopy(FileInfo fileInfo) {
    String thisMd5String = fileInfo.getMd5String();
    
    for (ControlledRootFolderInfo rootFolder: result.getControlledrootfolderinfos()) {
      TreeIterator<EObject> treeIterator = rootFolder.eAllContents();
      while (treeIterator.hasNext()) {
        EObject eObject = treeIterator.next();
        if (eObject instanceof FileInfo controlledFileInfo) {
          if (thisMd5String.equals(controlledFileInfo.getMd5String()) &&
              fileInfo.getFileName().equals(controlledFileInfo.getFileName())) {
            LOGGER.info("Copy found: " + fileInfo.toString() + ", " + controlledFileInfo.toString());
            return controlledFileInfo;
          }
        }
      }
    }
        
    return null;
  }
}