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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.types.model.FileReference;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsPackage;

/**
 * This class provides various checks on a {@code Vacation}.
 */
public class VacationsChecker {
  private static final Logger LOGGER = Logger.getLogger(VacationsChecker.class.getName());
  
  private static List<String> specialFolders = Arrays.asList(
      "backup"                 // Folder with backup files.
      );
  
  private static VacationsRegistry vacationsRegistry = VacationsRegistry.getInstance();
  
  /**
   * Check that all references (type <code>FileReference</code>) of a Vacation have the 'file' attribute set.
   * 
   * @param vacation the Vacation structure to check
   * @return a list of file references which don't have the 'file' attribute set.
   */
  public static Set<String> checkThatAllReferencesAreSet(EObjectTreeView treeView, Vacation vacation) {
    Set<String> fileReferencesNotSet = new HashSet<>();
    
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference fileReference) {
        if (fileReference.getFile() == null) {
          String referencePath = getReferencePathFromTreeView(treeView, fileReference, fileReference.getTitle()); 
          fileReferencesNotSet.add(referencePath);
        }
      }
    }
    
    return fileReferencesNotSet;
  }
  
  /**
   * Get a textual representation for a path from a vacation to one of its children.
   * This is used for reporting errors.
   * 
   * @param treeView the {@code VacationsTreeView} from which to derive the path
   * @param eObject the {@code EObject} below a {@code Vacation}.
   * @param objectText an optional text for the {@code eObject} itself.
   * @return a textual representation for a path from a vacation to the {@code eObject}.
   */
  private static String getReferencePathFromTreeView(EObjectTreeView treeView, EObject eObject, String objectText) {
    LOGGER.severe("=> eObject" + eObject.toString());
    
    EObjectTreeItem treeItem = treeView.findTreeItem(eObject);
    String path = objectText;
    if (path == null) {
      path = "...";
    }
    boolean foundVacationItem = false;
    do {
      EObjectTreeItem parentTreeItem = (EObjectTreeItem) treeItem.getParent();
      Object value = parentTreeItem.getValue();
      if (path == null) {
        path = parentTreeItem.getText();
      } else {
        path = parentTreeItem.getText() + "/" + path;
      }
      if (value instanceof Vacation) {
        foundVacationItem = true;
      } else {
        treeItem = parentTreeItem;
      }
    } while (!foundVacationItem);
    
    return path;
  }
 
  /**
   * Check that all references (type {@code FileReference}) of a Vacation refer to an existing file.
   * <p>
   * Note that this method doesn't report file references for which the 'file' attribute isn't set, this is handled by the method {@code checkThatAllReferencesAreSet}.
   * 
   * @param vacation the Vacation structure to check
   * @return a list of file references which refer to files that don't exist.
   */
  public static Set<String> checkThatAllReferencesExist(EObjectTreeView treeView, Vacation vacation) {
    Set<String> fileReferencesNotFound = new HashSet<>();
    
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference fileReference) {
        String fileName = fileReference.getFile();
        if (fileName != null) {
          File file = new File(fileName);
          if (!file.exists()) {
            String referencePath = getReferencePathFromTreeView(treeView, fileReference, fileReference.getFile()); 
            fileReferencesNotFound.add(referencePath);
          }
        }
      }
    }
    
    return fileReferencesNotFound;
  }
  
  /**
   * Check that all references (type {@code FileReference}) refer to an existing file.
   * TODO check whether this method is still needed, or that check all vacations should check and report per vacation.
   * 
   * @param vacations the {@code Vacation} to check
   * @return a list of file references which refer to files that don't exist.
   */
  public static List<FileReference> checkThatAllReferencesExist(Vacations vacations) {
    List<FileReference> fileReferencesNotFound = new ArrayList<>();
    
    TreeIterator<EObject> vacationIterator = vacations.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference fileReference) {
        File file = new File(fileReference.getFile());
        if (!file.exists()) {
          fileReferencesNotFound.add(fileReference);
        }
      }
    }
    
    return fileReferencesNotFound;
  }
  
  /**
   * Check that all files in the vacation folder are referred to.
   * 
   * @param vacation the Vacation structure to check
   * @return a set of file names of files which aren't referred to.
   */
  public static Set<String> checkThatAllFilesAreReferredTo(Vacation vacation) {
    // Build a set of all references
    Set<String> referredFiles = new HashSet<>();
    
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference fileReference) {
        if (fileReference.getFile() != null) {
          referredFiles.add(fileReference.getFile());
        }
      }
    }
    
    // Get the vacations folder
    String vacationFoldername = VacationsUtils.getTravelFilesFolder(vacation);
    
    // For each file in the vacations folder, check that it is in the set of references.
    Set<String> filesNotReferredTo = null;
    
    Path vacationFolderPath = Paths.get(vacationFoldername);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationFolderPath)) {
      for (Path path: stream) {
        if (!Files.isDirectory(path)) {
          String fileName = path.toString();
          if (!referredFiles.contains(fileName)) {
            if (filesNotReferredTo == null) {
              filesNotReferredTo = new HashSet<>();
            }
            filesNotReferredTo.add(fileName);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return filesNotReferredTo;
  }

  /**
   * Check that all folders in the vacations folder are referenced from vacations.
   * TODO check whether this method is still needed, or that check all vacations should check and report per vacation.
   * 
   * @param vacations the vacations structure to check.
   * @return a list of folder names for which no vacation exists, or null if there are no errors.
   */
  public static List<String> checkThatAllVacationFoldersAndFilesAreReferredTo(List<Vacation> vacations) {
    String vacationsFoldername = vacationsRegistry.getVacationsFolderName();
    if (vacationsFoldername == null) {
      throw new IllegalArgumentException("VacationsRegistry.vacationsFolderName is null");
    }
    
    Path vacationsPath = Paths.get(vacationsFoldername);
    
    List<String> paths = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationsPath)) {
      for (Path path: stream) {
        if (Files.isDirectory(path)) {
          String folderName = path.getFileName().toString();
          if (!specialFolders.contains(folderName)) {
            getAllPathsInFolderResursively(path, paths);
          } else {
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    for (Vacation vacation: vacations) {
      TreeIterator<EObject> vacationIterator = vacation.eAllContents();
      while (vacationIterator.hasNext()) {
        EObject eObject = vacationIterator.next();
        if (eObject instanceof FileReference) {
          FileReference fileReference = (FileReference) eObject;
          Path path = Paths.get(fileReference.getFile());
          if (!paths.contains(path.toString())) {
          } else {
            paths.remove(path.toString());
          }
        }
      }
    }
    
    for (String path: paths) {
      LOGGER.info("Error: " + path);
    }
    
    return paths;
  }
  
  /**
   * Check that all photos in the vacation photos folder are referred to.
   * MediaRegistry.ignoreFolderNames
   * 
   * @param vacation the {@code Vacation} to check
   * @return the set of file names of photos which aren't referred to.
   */
  public static Set<String> checkThatAllPhotosAreReferredTo(Vacation vacation) {
    Set<String> photosNotReferredTo = new HashSet<>();
    
    String vacationPhotoFolderName = vacation.getPictures();
    if (vacationPhotoFolderName == null) {
      photosNotReferredTo.add("Check could not be performed because the 'Photos' attribute is not set.");
      return photosNotReferredTo;
    }
    
    Set<String> referredPhotos = getReferredPhotos(vacation);
    
    Path vacationPhotoFolderPath = Paths.get(vacationPhotoFolderName);
    List<String> photosInVacationPhotosFolder = new ArrayList<>();
    getPhotosInFolder(vacationPhotoFolderPath, photosInVacationPhotosFolder);
    
    for (String photoInVacationPhotosFolder: photosInVacationPhotosFolder) {
      if (!referredPhotos.contains(photoInVacationPhotosFolder)) {
        photosNotReferredTo.add(photoInVacationPhotosFolder);
      }
    }    
    
    return photosNotReferredTo;
  }
  
  /**
   * Get the set of all photos referred to in a {@code Vacation}.
   * 
   * @param vacation the {@code Vacation} to check
   * @return the set of file names of photos referred to in the {@code Vacation}.
   */
  public static Set<String> getReferredPhotos(Vacation vacation) {
    Set<String> referredPhotos = new HashSet<>();
     
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference.getFile() != null) {
          referredPhotos.add(fileReference.getFile());
        }
      }
    }
    
    return referredPhotos;
  }
  
  /**
   * Get all photos in a folder, recursively.
   * <p>
   * Folders whose names occur in ("Originals, weg") are ignored.
   * 
   * @param folder the folder to check
   * @return a list of file names of photos in the folder.
   */
  public static void getPhotosInFolder(Path photosFolder, List<String> photos) {
    List<String> ignoreFoldersAsList = StringUtil.semicolonSeparatedValuesToListOfValues("Originals; weg");
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photosFolder)) {
      for (Path path: stream) {
        LOGGER.info("Handling path=" + path.toString());
        if (!Files.isDirectory(path)) {
          if (FileUtils.isPictureFile(path)) {  // only add picture files
            photos.add(path.toString());
          }
        } else {
          if (!ignoreFoldersAsList.contains(path.getFileName().toString())) {
            getPhotosInFolder(path, photos);
          }
        }
      }
      
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
  }
  
  /**
   * Recursively get all Paths in a folder.
   * TODO this can probably be done more easily with Files.walk (if it is still needed at all).
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
    String vacationsFoldername = vacationsRegistry.getVacationsFolderName();
    if (vacationsFoldername == null) {
      throw new IllegalArgumentException("VacationsRegistry.vacationsFolderName is null");
    }
    LOGGER.info("vacationsFoldername=" + vacationsFoldername);
    
    List<String> suspiciousFiles = null;
    List<String> knownFileNames = StringUtil.semicolonSeparatedValuesToListOfValues(vacationsRegistry.getKnownFiles());
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

