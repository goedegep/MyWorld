package goedegep.travels.app.logic;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.types.model.FileReference;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Travel;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.Vacations;

/**
 * This class provides various checks on a {@code Vacations} structure.
 * <p>
 * Where applicable, the checks are per travel, because reporting is also done per travel.
 * So the application has to iterate over all travels and call the checks and reporting for each travel.
 * The following checks are available:
 * <ul>
 * <li>Check that all references (type <code>FileReference</code>) of a Vacation have the 'file' attribute set. See {@link #checkThatAllReferencesAreSet}.</li>
 * </ul>
 */
public class VacationsChecker {
  private static final Logger LOGGER = Logger.getLogger(VacationsChecker.class.getName());
  
  /**
   * Folders in the vacations folder, which can exist and or not related to a vacation.
   */
  private static List<String> specialFolders = Arrays.asList(
      "backup"                 // Folder with backup files.
      );
  
  /**
   * The {@code VacationsRegistry}
   */
  private static TravelsRegistry vacationsRegistry = TravelsRegistry.getInstance();
  
  /**
   * Check that all references (type <code>FileReference</code>) of a {@code Travel} have the 'file' attribute set.
   * 
   * @param travel the {@code Travel} to check
   * @return a list of file references which don't have the 'file' attribute set.
   */
  public static List<FileReference> checkThatAllReferencesAreSet(Travel travel) {
    List<FileReference> fileReferencesNotSet = new ArrayList<>();
    
    TreeIterator<EObject> vacationIterator = travel.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference fileReference) {
        if (fileReference.getFile() == null) {
//          String referencePath = getReferencePathFromTreeView(treeView, fileReference, fileReference.getTitle()); 
          fileReferencesNotSet.add(fileReference);
        }
      }
    }
    
    return fileReferencesNotSet;
  }
  
  /**
   * Check that all references (type {@code FileReference}) refer to an existing file.
   * 
   * @param travel the {@code Travel} to check
   * @return a (possibly empty) list of file references which refer to files that don't exist.
   */
  public static List<FileReference> checkThatAllReferencesExist(Travel travel) {
    List<FileReference> fileReferencesNotFound = new ArrayList<>();

    TreeIterator<EObject> vacationIterator = travel.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof FileReference fileReference) {
        String fileName = fileReference.getFile();
        if (fileName == null) {
          fileReferencesNotFound.add(fileReference);
        } else {
          Path path = Paths.get(fileName);
           if (!Files.exists(path)) {
            fileReferencesNotFound.add(fileReference);
          }
        }
      }
    }

    return fileReferencesNotFound;
  }
    
  public static List<Path> checkThatAllVacationFoldersAreReferredTo(Vacations vacations) {
    // Create a list of all vacation folders
    List<Path> vacationFolders = getVacationFolders();
    
    // For each Vacation, iterate over its contents. If a reference is found, get the related vacation folder. Remove this folder from the list.
    for (Vacation vacation: vacations.getVacations()) {
      FileReference fileReference = getFirstNonPictureFileReference(vacation);
      if (fileReference != null) {
        Path fileReferencePath = Paths.get(fileReference.getFile());
        Path vacationFolderPath = getVacationFolderPath(fileReferencePath);
        vacationFolders.remove(vacationFolderPath);
      }
    }
    
    return vacationFolders;
   }
  
  /**
   * Get a list of all vacation folders.
   * 
   * @return a list of all vacation folders.
   */
  public static List<Path> getVacationFolders() {
    String vacationsFoldername = vacationsRegistry.getVacationsFolderName();
    if (vacationsFoldername == null) {
      throw new IllegalArgumentException("VacationsRegistry.vacationsFolderName is null");
    }
    
    Path vacationsPath = Paths.get(vacationsFoldername);
    
    List<Path> vacationFolders = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationsPath)) {
      for (Path path: stream) {
        if (Files.isDirectory(path)) {
          String folderName = path.getFileName().toString();
          if (!specialFolders.contains(folderName)) {
            vacationFolders.add(path);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      LOGGER.severe("Exception in creating a list of vacation folders: " + x);
    }
    
    return vacationFolders;
  }
  
  /**
   * Get the first {@code FileReference} in a {@code Vacation} which isn't part of a Picture.
   * <p>
   * Picture references are excluded, because the pictures aren't normally in the vacation folder.
   * 
   * @param vacation the {@code Vacation} to get the reference for.
   * @return the first {@code FileReference} in {@code vacation} which isn't part of a Picture, or null if this doesn't exist.
   */
  public static FileReference getFirstNonPictureFileReference(Vacation vacation) {
    Iterator<EObject> iterator = vacation.eAllContents();
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof FileReference fileReference) {
        EObject container = eObject.eContainer();
        if (!(container instanceof Picture)  &&  fileReference.getFile() != null) {
          return fileReference;
        }
      }
    }
    
    return null;
  }
  
  /**
   * Get the {@code Path} for a vacation folder, based on a {@code Path} within that vacation.
   *  
   * @param pathInVacation a {@code Path} within a {@code Vacation}
   * @return the {@code Path} for the {@code Vacation} which contains {@code pathInVacation},
   * or null if the {@code Path} couldn't be determined (which only happens if {@code pathInVacation} isn't in a vacation).
   */
  public static Path getVacationFolderPath(Path pathInVacation) {
    String vacationsFoldername = vacationsRegistry.getVacationsFolderName();
    Path vacationsPath = Paths.get(vacationsFoldername);
    
    Path parentPath = pathInVacation.getParent();
    
    while (parentPath != null  &&  !parentPath.equals(vacationsPath)) {
      pathInVacation = parentPath;
      parentPath = parentPath.getParent();
    }
    
    return pathInVacation;
  }

  /**
   * Check that all files in a travel folder are referenced from a travel.
   * <p>
   * The vacations folder is obtained from the {@code vacationsRegistry}.
   * 
   * @param travel the {@code Travel} for which the related folder is to be checked.
   * @param travelFolderPath the {@code Path} to the travel related files folder.
   * @return a (possibly empty) list of file names which are not referred to.
   * @throws IOException 
   */
  public static List<Path> checkThatAllFilesInTravelFolderAreReferredTo(Travel travel, Path travelFolderPath) throws IOException {

    List<Path> paths = getAllPathsInFolderResursively(travelFolderPath);

    TreeIterator<EObject> travelIterator = travel.eAllContents();
    while (travelIterator.hasNext()) {
      EObject eObject = travelIterator.next();
      if (eObject instanceof FileReference fileReference  &&  fileReference.getFile() != null) {
        Path path = Paths.get(fileReference.getFile());
        if (paths.contains(path)) {
          paths.remove(path);
        }
      }
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
   * 
   * @param startingFolder The top level folder.
   * @param paths A list of Strings to which all Paths will be added.
   * @throws IOException 
   */
  private static List<Path> getAllPathsInFolderResursively(Path startingFolder) throws IOException {
    LOGGER.info("=> " + startingFolder.toString());
    List<Path> filesInFolder = Files.walk(startingFolder)
        .filter(Files::isRegularFile)
        .collect(Collectors.toCollection(ArrayList::new));

    return filesInFolder;
  }

  /**
   * Find any files at the top level, which probably don't belong there.
   * 
   * @return a (possibly empty) list of suspicious files.
   */
  public static List<String> findSupiciousTopLevelFiles() {
    String vacationsFoldername = vacationsRegistry.getVacationsFolderName();
    if (vacationsFoldername == null) {
      throw new IllegalArgumentException("VacationsRegistry.vacationsFolderName is null");
    }
    
    List<String> suspiciousFiles = new ArrayList<>();
    List<String> knownFileNames = StringUtil.semicolonSeparatedValuesToListOfValues(vacationsRegistry.getKnownFiles());
    
    Path vacationsPath = Paths.get(vacationsFoldername);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationsPath)) {
      for (Path path: stream) {
        if (!Files.isDirectory(path)) {
          if (!knownFileNames.contains(path.getFileName().toString())) {
          suspiciousFiles.add(path.toString());
          }
        }
      }
      
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return suspiciousFiles;
  }
  
  /**
   * Get all locations which have boundaries with more than {@code thresholdNrOfPoints} points.
   * 
   * @param travel the {@code Travel} to check
   * @param thresholdNrOfPoints the threshold number of points.
   * @return a (possibly empty) list of {@code Location}s which have boundaries with more than {@code thresholdNrOfPoints} points.
   */
  public static List<Location> getLocationsWithLargeBoundaries(Travel travel, int thresholdNrOfPoints) {
    List<Location> largeBoundaries = new ArrayList<>();
    
    TreeIterator<EObject> travelIterator = travel.eAllContents();
    while (travelIterator.hasNext()) {
      EObject eObject = travelIterator.next();
      if (eObject instanceof Location location) {
        int nrOfBoundaryPoints = location.getBoundaries().stream()
        .mapToInt(boundary -> boundary.getPoints().size())
        .filter(nrOfPoints -> nrOfPoints > thresholdNrOfPoints)
        .sum();
        if (nrOfBoundaryPoints > thresholdNrOfPoints) {
          largeBoundaries.add(location);
        }
      }
    }
    
    return largeBoundaries;
  }
  
  /**
   * Check whether the picture folder of a vacation, if set, is according to the convention.
   * 
   * @param vacation the {@code Vacation} to check
   * @return true if the picture folder is according to the convention (or can't be checked), false otherwise.
   */
  public static boolean isPicturesFolderAccordingToConvention(Vacation vacation) {
    String picturesFoldername = vacation.getPictures();
    if (picturesFoldername == null) {
      // Checking whether the picture folder is set is done elsewhere. So just return true (OK) here.
      return true;
    }
    
    Path vacationPhotosFolderPathByConvention = VacationsUtils.getVacationPhotosFolderPathByConvention(vacation);
    if (vacationPhotosFolderPathByConvention == null) {
      // If the vacationPhotosFolderPathByConvention couldn't be determined, it means that the title and/or date of the vacation isn't set.
      // This is checked elsewhere, so just return true (OK) here.
      return true;
    }
    
    Path picturesFolderPath = Paths.get(picturesFoldername);
    if (picturesFolderPath.equals(vacationPhotosFolderPathByConvention)) {
      return true;
    } else {
      return false;
    }
  }
  
  public static boolean isPicturesSetIfTravelHasPictures(Travel travel) {
    if (!(travel instanceof Vacation)) {
      return true;
    }
    
    Vacation vacation = (Vacation) travel;
    
    boolean travelHasPictures = VacationsUtils.doesTravelHavePictures(vacation);
    if (travelHasPictures) {
      String picturesFoldername = vacation.getPictures();
      if (picturesFoldername == null) {
        return false;
      }
    }
    
    return true;
  }

  public static List<Path> getTravelPhotosNotReferredTo(Travel travel) {
    List<Path> photosNotReferredTo = new ArrayList<>();
    
    if (!(travel instanceof Vacation)) {
      return photosNotReferredTo;
    }
    
    Vacation vacation = (Vacation) travel;
    
    // Get all photos in the vacation photos folder
    List<Path> vacationPhotosFolderPaths = VacationsUtils.getVactionPhotosSubFoldersPaths(vacation);
    for (Path vacationPhotosFolderPath: vacationPhotosFolderPaths) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(vacationPhotosFolderPath)) {
        for (Path path: stream) {
          if (Files.isRegularFile(path)  &&  FileUtils.isPictureFile(path)) {
            photosNotReferredTo.add(path);
          }
        }
      } catch (IOException | DirectoryIteratorException x) {
        LOGGER.severe("Exception in creating a list of vacation photos: " + x);
      }
    }
    
    // Get all referred photos. Remove them from the list of photos.
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String pictureFileName = fileReference.getFile();
          if (pictureFileName != null) {
            Path picturePath = Paths.get(pictureFileName);
            photosNotReferredTo.remove(picturePath);
          }
        }
      }
    }
    
    LOGGER.severe("Number of photos not referred to: " + photosNotReferredTo.size());
    
    return photosNotReferredTo;
  }
}