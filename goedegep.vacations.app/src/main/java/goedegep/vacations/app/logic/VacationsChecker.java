package goedegep.vacations.app.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.types.model.FileReference;
import goedegep.util.string.StringUtil;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsPackage;

public class VacationsChecker {
  private static final Logger LOGGER = Logger.getLogger(VacationsChecker.class.getName());
  
  private static List<String> specialFolders = Arrays.asList(
      "backup"                 // Folder with backup files.
      );
    
  /**
   * Check that all references (type <code>BestandReferentie</code>) refer to an existing file.
   * 
   * @param vacations the vacations structure to check
   * @return a list of file references which refer to files that don't exist, or null if there are no errors.
   */
  public static List<FileReference> checkThatAllReferencesExist(Vacations vacations) {
    List<FileReference> fileReferencesNotFound = null;
    
    TreeIterator<EObject> vacationIterator = vacations.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference) {
        FileReference fileReference = (FileReference) eObject;
        File file = new File(fileReference.getFile());
        if (!file.exists()) {
          if (fileReferencesNotFound == null) {
            fileReferencesNotFound = new ArrayList<>();
          }
          fileReferencesNotFound.add(fileReference);
        }
      }
    }
    
    return fileReferencesNotFound;
  }

  /**
   * Check that all folders in the vacations folder are referenced from vacations.
   * 
   * @param vacations the vacations structure to check.
   * @return a list of folder names for which no vacation exists, or null if there are no errors.
   */
  public static List<String> checkThatAllVacationFoldersAndFilesAreReferredTo(List<Vacation> vacations) {
    String vacationsFoldername = VacationsRegistry.vacationsFolderName;
    if (vacationsFoldername == null) {
      throw new IllegalArgumentException("VacationsRegistry.vacationsFolderName is null");
    }
    LOGGER.severe("vacationsFoldername=" + vacationsFoldername);
    
    Path vacationsPath = Paths.get(vacationsFoldername);
    LOGGER.severe("vacationsPath: " + vacationsPath.toString());
    
    List<String> paths = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationsPath)) {
      for (Path path: stream) {
        if (Files.isDirectory(path)) {
          String folderName = path.getFileName().toString();
          if (!specialFolders.contains(folderName)) {
            getAllPathsInFolderResursively(path, paths);
          } else {
            LOGGER.severe("Skipping special folder: " + folderName);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    LOGGER.severe("Total number of paths in vacation folders: " + paths.size());
    int removeCount = 0;
    for (Vacation vacation: vacations) {
      TreeIterator<EObject> vacationIterator = vacation.eAllContents();
      while (vacationIterator.hasNext()) {
        EObject eObject = vacationIterator.next();
        if (eObject instanceof FileReference) {
          FileReference fileReference = (FileReference) eObject;
          Path path = Paths.get(fileReference.getFile());
          LOGGER.info("Removing path: " + path.toString());
          if (!paths.contains(path.toString())) {
            LOGGER.info("referred path not in paths: " + path.toString());
          } else {
            paths.remove(path.toString());
          }
          removeCount++;
        }
      }
    }
    LOGGER.severe("Number of references resolved: " + removeCount);
    
    LOGGER.severe("Totaal number of paths not referred to: " + paths.size());
    for (String path: paths) {
      LOGGER.info("Error: " + path);
    }
    
    return paths;
  }
  
  /**
   * Recursively get all Paths in a folder.
   * 
   * @param startingFolder The top level folder.
   * @param paths A list of Strings to which all Paths will be added.
   */
  private static void getAllPathsInFolderResursively(Path startingFolder, List<String> paths) {
    LOGGER.severe("=> " + startingFolder.toString());
    try {
      Files.walkFileTree(startingFolder, new FileVisitor<Path>() {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          LOGGER.info("adding path: " + file.toString());
          paths.add(file.toString());
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
          exc.printStackTrace();
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Find any files at the top level, which probably don't belong there.
   * 
   * @return a list of suspicious files, or null if there are no such files.
   */
  public static List<String> findSupiciousTopLevelFiles() {
    String vacationsFoldername = VacationsRegistry.vacationsFolderName;
    if (vacationsFoldername == null) {
      throw new IllegalArgumentException("VacationsRegistry.vacationsFolderName is null");
    }
    LOGGER.info("vacationsFoldername=" + vacationsFoldername);
    
    List<String> suspiciousFiles = null;
    List<String> knownFileNames = StringUtil.semicolonSeparatedValuesToListOfValues(VacationsRegistry.knownFiles);
    LOGGER.info("knownFileNames: " + knownFileNames);
    
    Path vacationsPath = Paths.get(vacationsFoldername);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationsPath)) {
      for (Path path: stream) {
        LOGGER.info("Handling path=" + path.toString());
        if (!Files.isDirectory(path)) {
          if (!knownFileNames.contains(path.getFileName().toString())) {
          if (suspiciousFiles == null) {
            suspiciousFiles = new ArrayList<>();
          }
          LOGGER.info("Adding suspicious file");
          suspiciousFiles.add(path.toString());
          }
        }
      }
      
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return suspiciousFiles;
  }
  

  public static boolean checkVacation(Vacation vacation) {
    LOGGER.info("=>");
    
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      switch(eObject.eClass().getClassifierID()) {
        
      case VacationsPackage.VACATION_ELEMENT:
        VacationElement vacationElement = (VacationElement) eObject;
        System.out.println("VacationElement detected:");
        System.out.println("    vacation = " + vacation.getId());
        System.out.println("    vacationElement = " + vacationElement.toString());
        break;
        
      case VacationsPackage.DAY:
//        VacationElementDay vacationElementDay = (VacationElementDay) eObject;
//        if (vacationElementDay.getDocuments().size() > 0) {
//          System.out.println("Documents is set in VacationElementDay for vacation: " + vacation.getTitle());
//        }
        break;
        
      case VacationsPackage.PICTURE:
//        VacationElementPicture vacationElementPicture = (VacationElementPicture) eObject;
//        if (vacationElementPicture.getDocuments().size() > 0) {
//          System.out.println("Documents is set in vacationElementPicture for vacation: " + vacation.getTitle());
//        }
        break;
        
//      case VacationsPackage.VACATION_ELEMENT_LABEL:
//        System.out.println("Label in vacation: " + vacation.getTitle());
//        break;
        
      case VacationsPackage.LOCATION:
//        Location vacationElementLocation = (Location) eObject;
//        if (vacationElementLocation.getDocuments().size() > 0) {
//          System.out.println("Documents is set in vacationElementLocation for vacation: " + vacation.getTitle());
//        }
        break;
        
      case VacationsPackage.TEXT:
//        VacationElementText vacationElementText = (VacationElementText) eObject;
//        if (vacationElementText.getDocuments().size() > 0) {
//          System.out.println("Documents is set in vacationElementText for vacation: " + vacation.getTitle());
//        }
        break;
        
      case VacationsPackage.MAP_IMAGE:
        break;
      }
      
    }
    
    LOGGER.info("<=");
    return false;
  }
}

