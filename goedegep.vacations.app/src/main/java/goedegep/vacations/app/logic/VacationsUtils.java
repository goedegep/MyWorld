package goedegep.vacations.app.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.gpx.model.util.GPXResourceFactoryImpl;
import goedegep.types.model.FileReference;
import goedegep.util.Triplet;
import goedegep.util.datetime.FlexDate;
import goedegep.util.emf.EMFResource;
import goedegep.util.file.FileUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.vacations.model.ActivityLabel;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.VacationsPackage;

/**
 * This class provides utility methods for the Vacations application.
 */
public class VacationsUtils {
  private static final Logger LOGGER = Logger.getLogger(VacationsUtils.class.getName());
  
  /**
   * Get all geo-locations of a vacation.
   * 
   * @param vacation the <code>Vacation</code> for which the locations are to be collected.
   * @return a list of coordinates of all locations of <code>vacation</code>.
   * @throws FileNotFoundException if a file, referenced by an element, doesn't exist.
   */
  public static List<WGS84Coordinates> getGeoLocations(Vacation vacation) throws FileNotFoundException {
    List<WGS84Coordinates> geoLocations = new ArrayList<>();
    int numberOfDays = getNumberOfDays(vacation);
    WGS84Coordinates[] stayedAtLocations = null;
    if (numberOfDays != 0) {
      stayedAtLocations = new WGS84Coordinates[numberOfDays];
    }
    
    for (VacationElement element: vacation.getElements()) {
      addGeoLocationsForVacationElement(geoLocations, element, stayedAtLocations);
    }
    
    return geoLocations;
  }
  
  /**
   * Get all geo-locations of a day.
   * 
   * @param day the <code>Day</code> for which the locations are to be collected.
   * @return a list of coordinates of all locations of <code>day</code>.
   * @throws FileNotFoundException In case a file, which is referenced by an element, doesn't exist
   */
  public static List<WGS84Coordinates> getGeoLocations(Day day) throws FileNotFoundException {
    List<WGS84Coordinates> geoLocations = new ArrayList<>();
    int numberOfDays = 0;
    Vacation vacation = day.getVacation();
    if (vacation != null) {
      numberOfDays = getNumberOfDays(vacation);
    }
    WGS84Coordinates[] stayedAtLocations = null;
    if (numberOfDays != 0) {
      stayedAtLocations = new WGS84Coordinates[numberOfDays];
    }
    
    for (VacationElement element: day.getChildren()) {
      addGeoLocationsForVacationElement(geoLocations, element, stayedAtLocations);
    }
    
    return geoLocations;
  }
  
  /**
   * Get the number of days of a vacation.
   * <p>
   * If the (start) date and end date are set, and both are complete dates, then these dates are used to calculate the duration.
   * Otherwise the number of days is the sum of all days of all Day elements of the vacation (calculated by calling {@link #countNumberOfDays}.
   * 
   * @param vacation a <code>Vacation</code>
   * @return the number of days of the vacation.
   */
  public static int getNumberOfDays(Vacation vacation) {
    int numberOfDays = 0;
    
    if (vacation.isSetDate()  &&  vacation.isSetEndDate()) {
      FlexDate flexDate = vacation.getDate();
      LocalDateTime startDate = flexDate.toLocalDateTime();
      
      flexDate = vacation.getEndDate();
      LocalDateTime endDate = flexDate.toLocalDateTime();

// Tried this, but it sometimes the result is one day short.
//      if (startDate != null  &&  endDate != null) {
//        numberOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate);
//        LOGGER.severe("numberOfDays: " + numberOfDays);
//      }
      
      if (startDate != null  &&  endDate != null) {
        numberOfDays = 1;
        while (startDate.isBefore(endDate)) {
          numberOfDays++;
          startDate = startDate.plusDays(1);
        }
        LOGGER.info("numberOfDays: " + numberOfDays);
      }
      
    }
    
    if (numberOfDays == 0) {
      numberOfDays = countNumberOfDays(vacation);
    }
    
    return numberOfDays;
  }
  
  /**
   * Count the number of days of a vacation.
   * <p>
   * The number of days is the sum of all days of all Day elements of the vacation.
   * This method only gives the correct value if Day elements are consistently used for the vacation.
   * 
   * @param vacation a <code>Vacation</code>
   * @return the number of days of the <code>vacation</code>
   */
  public static int countNumberOfDays(Vacation vacation) {
    int numberOfDays = 0;
    
    TreeIterator<EObject> iterator = vacation.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Day day) {
        if (day.isSetDays()) {
          numberOfDays += day.getDays();
        } else {
          numberOfDays++;
        }
      }
    }
    
    LOGGER.info("counted number of days: " + numberOfDays);
    return numberOfDays;
  }
  
  /**
   * Get a list of all the <code>Day</code> elements of a vacation.
   * 
   * @param vacation a <code>Vacation</code>
   * @return a list of all the <code>Day</code> elements of a vacation.
   */
  public static List<Day> getVacationDays(Vacation vacation) {
    List<Day> days = new ArrayList<>();
    
    TreeIterator<EObject> iterator = vacation.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Day day) {
        days.add(day);
      }
    }
    
    return days;
  }
  
  /**
   * Get a mapping of VacationElements to their geo locations.
   * <p>
   * Only VacationElements which have at least one type of location information are added to the mapping.<br/>
   * The location information is provided in a Triplet, where:
   * <ul>
   * <li>the first object contains the coordinates, or null if these aren't available.</li>
   * <li>the second object contains the bounding box, or null if this isn't available.</li>
   * <li>the third object contains the boundaries, or null if these aren't available.</li>
   * </ul>
   * 
   * @param vacation a <code>Vacation</code>
   * @return a mapping of VacationElements to their geo locations.
   */
  public static Map<VacationElement, Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>> getVacationGeoLocations(Vacation vacation) {
    Map<VacationElement, Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>> geoLocations = new HashMap<>();
    
    TreeIterator<EObject> iterator = vacation.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof VacationElement vacationElement) {
        LOGGER.severe("Handling vacation element: " + vacationElement.toString());
        Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> elementGeoLocations = getGeoLocation(vacationElement);
        if (elementGeoLocations != null) {
          geoLocations.put(vacationElement, elementGeoLocations);
        }
      }
    }
    
    return geoLocations;
  }

  /**
   * Add the geo-locations of a <code>VacationElement</code> and all its children to a list of geo-locations.
   * 
   * @param geoLocations the list to which the locations are added.
   * @param element the <code>VacationElement</code> for which the locations will be added.
   * @param stayedAtLocations optional array of stayed at locations
   * @throws FileNotFoundException in case the file specified by the pictureReference of a Picture element doesn't exist.
   */
  private static void addGeoLocationsForVacationElement(List<WGS84Coordinates> geoLocations, VacationElement element, WGS84Coordinates[] stayedAtLocations) throws FileNotFoundException {
    
    switch(element.eClass().getClassifierID()) {
    case VacationsPackage.DAY:
      // A day has no location. Stayed at locations are handled after handling the children.
      break;
      
    case VacationsPackage.LOCATION:
      addGeoLocationForVacationElementLocation(geoLocations, (Location) element, stayedAtLocations);
      break;
      
    case VacationsPackage.TEXT:
      // No action; a Text has no location.
      break;
      
    case VacationsPackage.PICTURE:
      addGeoLocationForVacationElementPicture(geoLocations, (Picture) element);
      break;
      
    case VacationsPackage.GPX_TRACK:
      addGeoLocationForVacationElementGPXTrack(geoLocations, (GPXTrack) element);
      break;
    }
    
    for (VacationElement childElement: element.getChildren()) {
      addGeoLocationsForVacationElement(geoLocations, childElement, stayedAtLocations);
    }
    
    if (element.eClass().getClassifierID() == VacationsPackage.DAY) {
      Day day = (Day) element;
      int dayNr = day.getDayNr();
      LOGGER.info("Trying to retrieve stayed at for day number: " + dayNr);
      if (stayedAtLocations.length >= dayNr) {
        WGS84Coordinates coordinates = stayedAtLocations[dayNr - 1];
        if (coordinates != null) {
          LOGGER.info("Found stayed at");
          geoLocations.add(coordinates);
        }
      }
    }
  }

  /**
   * Add the geo-location of a <code>Location</code> to a list of geo-locations or to an array of stayed at locations.
   * <p>
   * The location can only be added if both latitude and longitude of the <code>Location</code> are set.<br/>
   * If the location is a 'stayed at' location, it is added to the stayed at locations for the corresponding days.
   * Otherwise it is added to the geoLocations.
   * 
   * @param geoLocations a list of coordinates to which the coordinates of the <code>location</code> are added if this <code>location</code> isn't a 'stayed at' location.
   * @param location a <code>Location</code>
   * @param stayedAtLocations optional array of 'stayed at' locations. If the <code>location</code> is a 'stayed at' location, the coordinates of the <code>location</code> are set
   *        in the elements corresponding to the days you stayed at this location.
   */
  private static void addGeoLocationForVacationElementLocation(List<WGS84Coordinates> geoLocations, Location location, WGS84Coordinates[] stayedAtLocations) {
    
    if (!location.isReferenceOnly()  &&  location.isSetLatitude()  &&  location.isSetLongitude()) {
      WGS84Coordinates coordinates = new WGS84Coordinates(location.getLatitude(), location.getLongitude());
      /*
       * If it is a stayed at location, add the location to the stayedAtLocations for the day numbers we stayed there.
       */
      if (stayedAtLocations != null  &&  location.isSetLabel()  &&  location.getLabel().equals(ActivityLabel.VERBLIJF)) {
        LOGGER.info("Stayed at: " + location.getName());
        Integer dayNr = location.getDayNr();
        if (dayNr == 0) {
          // The location is in the hierarchy before the first day, assume it's a stayed at location for the complete vacation. So dayNr is 1.
          dayNr = 1;
        }
        int nrOfNights = 1;
        if (location.isSetDuration()) {
          nrOfNights = location.getDuration();
        }
        
        for (int i = dayNr; i < dayNr + nrOfNights; i++) {
          LOGGER.info("Adding location to stayedAtLocations for day number: " + i);
          stayedAtLocations[i - 1] = coordinates;
        }
      } else {
        geoLocations.add(coordinates);
      }
    }
    
  }

  /**
   * Add the geo-location of a picture to a list of geo-locations.
   * <p>
   * The coordinates can only be added if the picture element has a valid reference to a picture file which has coordinates set.
   * 
   * @param geoLocations a list of coordinates to which the coordinates of the <code>picture</code> are added.
   * @param picture a <code>Picture</code> element.
   * @throws FileNotFoundException in case the file specified by the pictureReference doesn't exist.
   */
  private static void addGeoLocationForVacationElementPicture(List<WGS84Coordinates> geoLocations, Picture picture) throws FileNotFoundException {
    WGS84Coordinates coordinates = null;
    FileReference pictureReference = picture.getPictureReference();
    if (pictureReference == null) {
      return;
    }
    String fileName = pictureReference.getFile();
    if (fileName == null) {
      return;
    }
    
    File file = new File(fileName);
    if (!file.exists()) {
      throw new FileNotFoundException("fileName");
    }
    
    coordinates = PhotoFileMetaDataHandler.getGeoLocation(fileName);

    if (coordinates != null) {      
      geoLocations.add(coordinates);
    }
  }

  /**
   * Add the location for a GPX track to the list of geo locations.
   * <p>
   * The location added is the location of the first point of the first segment of the first track.
   * 
   * @param geoLocations a list of geo locations to which the location is to be added.
   * @param gpxTrack the GPX track of which the location is to be added.
   */
  private static void addGeoLocationForVacationElementGPXTrack(List<WGS84Coordinates> geoLocations, GPXTrack gpxTrack) {
    FileReference trackReference = gpxTrack.getTrackReference();
    if (trackReference == null) {
      return;
    }
    
    String fileName = trackReference.getFile();
    if ((fileName == null)  ||  fileName.isEmpty()) {
      return;
    }    

    EMFResource<DocumentRoot> gpxResource = new EMFResource<>(GPXPackage.eINSTANCE, () -> GPXFactory.eINSTANCE.createDocumentRoot(), false);
    gpxResource.addResourceFactoryForFileExtension("gpx", new GPXResourceFactoryImpl());
    try {
      gpxResource.load(fileName);
      DocumentRoot documentRoot = gpxResource.getEObject();
      GpxType gpxType = documentRoot.getGpx();
      List<TrkType> tracks = gpxType.getTrk();
      if (tracks.isEmpty()) {
        return;
      }
      TrkType track = tracks.get(0);
      List<TrksegType> segments = track.getTrkseg();
      if (segments.isEmpty()) {
        return;
      }
      TrksegType segment = segments.get(0);
      List<WptType> trackPoints = segment.getTrkpt();
      if (trackPoints.isEmpty()) {
        return;
      }
      WptType firstTrackPoint = trackPoints.get(0);
      if ((firstTrackPoint.getLat() == null)  ||  (firstTrackPoint.getLon() == null)) {
        return;
      }
      
      WGS84Coordinates coordinates = new WGS84Coordinates(firstTrackPoint.getLat().doubleValue(), firstTrackPoint.getLon().doubleValue());
      geoLocations.add(coordinates);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
  }
  
  /**
   * Guess the folder in which the documents related to a vacation are stored.
   * 
   * @param vacation the Vacation to determine the folder for.
   * @return the folder in which the documents related to <code>vacation</code> are stored
   */
  public static String getVacationFolder(Vacation vacation) {
    String vacationFolder = null;

    for (FileReference fileReference: vacation.getAllFileReferences()) {
      String filename = fileReference.getFile();
      if (filename != null) {
        File file = new File(filename);
        String vacationsFolder = file.getParentFile().getParent();
        if (VacationsRegistry.vacationsFolderName.equals(vacationsFolder)) {
          vacationFolder = file.getParent();

          LOGGER.severe("reference found to folder: " + vacationFolder);
          return vacationFolder;
        }
      }
    }

    return null;
  }

  /**
   * Guess the folder in which the pictures related to a vacation are stored.
   * 
   * @param vacation the Vacation to determine the folder for.
   * @return the folder in which the pictures related to <code>vacation</code> are stored
   */
  public static String vacationPicturesFolder(Vacation vacation) {
    Path vacationPicturesFolderPath = Paths.get(VacationsRegistry.vacationPicturesFolderName, vacation.getId());
    if (Files.isDirectory(vacationPicturesFolderPath)) {
      return vacationPicturesFolderPath.toAbsolutePath().toString();
    } else {
      return null;
    }
  }
    
  /**
   * Get a Path for the folder with photos for a specific vacation.
   * <p>
   * If the 'Pictures' attribute is set on the vacation, then this is the photos folder.
   * Otherwise, the folder is expected to be a folder with the name equal to the Id of the vacation, and being a sub folder of the
   * folder with all photos of all vacations.
   * 
   * @param vacation the vacation for which to get a Path to its photos folder.
   * @return a Path to the folder with photos for <code>vacation</code>, or null if this cannot be determined.
   */
  public static Path getVacationPhotosFolderPath(Vacation vacation) {
    String vacationPhotosFolder = vacation.getPictures().trim();
    
    if ((vacationPhotosFolder != null)  &&  !vacationPhotosFolder.isEmpty()) {
      return Paths.get(vacationPhotosFolder);
    }
    
    Path vacationsPhotosFolderPath = getVacationsPhotosFolderPath();
    
    if (vacationsPhotosFolderPath == null) {
      return null;
    }
    
    String vacationId = vacation.getId();
    
    Path vacationPhotosFolderPath = vacationsPhotosFolderPath.resolve(vacationId);
    if (Files.exists(vacationPhotosFolderPath)  &&  Files.isDirectory(vacationPhotosFolderPath)) {
      LOGGER.severe("<= " + vacationPhotosFolderPath);
      return vacationPhotosFolderPath;
    } else {
      LOGGER.severe("<= " + null);
      return null;
    }
  }
  
  /**
   * Get a Path for the folder with photos for all vacations.
   * <p>
   * The name of this folder is specified by VacationsRegistry.vacationPicturesFolderName.
   * 
   * @return a Path to the folder with photos for all vacations, or null if this cannot be determined.
   */
  public static Path getVacationsPhotosFolderPath() {
    String vacationsPhotosFolderName = VacationsRegistry.vacationPicturesFolderName;
    Path vacationsPhotosFolderPath = Paths.get(vacationsPhotosFolderName);
    if (Files.exists(vacationsPhotosFolderPath)  &&  Files.isDirectory(vacationsPhotosFolderPath)) {
      LOGGER.severe("<= " + vacationsPhotosFolderPath);
      return vacationsPhotosFolderPath;
    } else {
      LOGGER.severe("<= " + null);
      return null;
    }
  }
  
  /**
   * Get all photo folders for a vacation.
   * <p>
   * See {@link #getVacationPhotosFolderPath} for determining the Path to the main photo folder for the vacation.<br/>
   * Below this folder all folders are scanned to see if they contain pictures. If so the folder is added to the result list.
   * 
   * @return a list of all paths of all folders, under the vacation's photo folder, which contain photos.
   */
  public static List<Path> getVactionPhotosSubFoldersPaths(Vacation vacation) {
    List<Path> vacationPhotoFolderPaths = new ArrayList<>();
    
    Path vacationPhotosFolderPath = getVacationPhotosFolderPath(vacation);

    if (vacationPhotosFolderPath == null) {
      return vacationPhotoFolderPaths;
    }
    
    try {
      Files.walkFileTree(vacationPhotosFolderPath, new FileVisitor<Path>() {
        boolean containsPhoto = false;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          LOGGER.info("preVisitDirectory");
          containsPhoto = false;
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          LOGGER.info("visitFile: " + file.toString());
          if (!containsPhoto) {
            if (FileUtils.isPictureFile(file.getFileName().toString())) {
              containsPhoto = true;
            }
          }
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
          exc.printStackTrace();
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          if (containsPhoto) {
            LOGGER.info("postVisitDirectory: adding " + dir.toString());
            vacationPhotoFolderPaths.add(dir);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    for (Path path: vacationPhotoFolderPaths) {
      LOGGER.severe("path: " + path.toString());
    }
    
    return vacationPhotoFolderPaths;
  }
  
  /**
   * Get the geo location (coordinates) of a vacation element.
   * <p>
   * A value is returned for:
   * <ul>
   * <li>A <code>Location</code> with both latitude and longitude set.
   * </li>
   * </ul>
   * In all other cases <code>null</code> will be returned.
   * 
   * @param vacationElement a <code>VacationElement</code>
   * @return the geo location (coordinates) of <code>vacationElement</code>, or null if the <code>vacationElement</code> can't have a location or doesn't have its location set.
   */
  public static Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> getGeoLocation(VacationElement vacationElement) {
    LOGGER.info("=> vacationElement=" + vacationElement.toString());
    
    switch(vacationElement.eClass().getClassifierID()) {
    case VacationsPackage.DAY:
      // A day has no location.
      LOGGER.info("<= null");
      return null;
      
    case VacationsPackage.LOCATION:
      LOGGER.info("<= getGeoLocation(<Location>)");
      return getGeoLocation((Location) vacationElement);
      
    case VacationsPackage.TEXT:
      // No action; a Text has no location.
      LOGGER.info("<= null");
      return null;
      
    case VacationsPackage.PICTURE:
      LOGGER.info("<= getGeoLocation(<Picture>)");
      return getGeoLocation((Picture) vacationElement);
      
    case VacationsPackage.GPX_TRACK:
      LOGGER.info("<= getGeoLocation(<GPXTrack>)");
      return getGeoLocation((GPXTrack) vacationElement);
    }
    
    return null;
  }
  
  /**
   * Get the geo location (coordinates) of a <code>Location</code> element.
   * 
   * @param location a <code>Location</code>
   * @return the geo location (coordinates) of the <code>location</code>, or null if not both latitude and longitude are set.
   */
  public static Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> getGeoLocation(Location location) {
    LOGGER.info("=> vacationElement=" + location.toString());
    
    WGS84Coordinates coordinates = null;
    
    if (location.isSetLatitude()  &&  location.isSetLongitude()) {
      coordinates = new WGS84Coordinates(location.getLatitude(), location.getLongitude());
    }
    
    BoundingBox boundingBox = location.getBoundingbox();

    List<Boundary> boundaries = location.getBoundaries();
    if (boundaries != null) {
      if (boundaries.isEmpty()) {
        boundaries = null;
      }
    }
    
    if ((coordinates != null)  ||  (boundingBox != null)  ||  (boundaries != null)) {
      return new Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>(coordinates, boundingBox, boundaries);
    } else {
      return null;
    }
  }
  
  /**
   * Get the geo location (coordinates) of a <code>Picture</code> element.
   * 
   * @param picture a <code>Picture</code>
   * @return the geo location (coordinates) of the <code>picture</code>, or null if the file reference isn't set,
   *         or the file name in the file reference isn't set, or if the picture doesn't have a location set.
   */
  public static Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> getGeoLocation(Picture picture) {
    FileReference pictureReference = picture.getPictureReference();
    if (pictureReference == null) {
      return null;
    }
    String fileName = pictureReference.getFile();
    if (fileName == null) {
      return null;
    }
    
    return new Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>(PhotoFileMetaDataHandler.getGeoLocation(fileName), null, null);
  }
  
  /**
   * Get the geo location (coordinates) of a <code>GPXTrack</code> element.
   * 
   * @param gpxTrack a <code>GPXTrack</code>
   * @return the coordinates of the the location of the first point of the first segment of the first track, or null if this isn't available.
   */
  public static Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> getGeoLocation(GPXTrack gpxTrack) {
    FileReference trackReference = gpxTrack.getTrackReference();
    if (trackReference == null) {
      return null;
    }
    
    String fileName = trackReference.getFile();
    if ((fileName == null)  ||  fileName.isEmpty()) {
      return null;
    }

    return new Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>(GpxUtil.getStartLocation(fileName), null, null);    
  }
  
  public static Day getDay(VacationElement vacationElement) {
    if (vacationElement == null) {
      return null;
    }
    
    EObject container = vacationElement.eContainer();

    while ((container != null) && !(container instanceof Day)) {
      container = container.eContainer();
    }

    return (Day) container;
  }
  
  /**
   * Get the text to show for a picture.
   * <p>
   * If the title is set in the file reference, this is the text to show.<br/>
   * Else, if the title is set in the picture file, this is the text to show.
   * 
   * @param picture a <code>Picture</code>
   * @return the text to show for the <code>picture</code>.
   */
  public static String getPictureCaption(Picture picture) {
    String text = null;
    File file = null;
    
    if (picture.isSetPictureReference()) {
      FileReference fileReference = picture.getPictureReference();
      text = fileReference.getTitle();  // first preference; the title set in the FileReference
      
      if (text == null  ||  text.isEmpty()) {
        String fileName = fileReference.getFile();
        if (fileName != null) {
          try {
            file = new File(fileName);
            PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
            text = photoFileMetaDataHandler.getTitle();   // second preference; title set in the photo
          } catch (ImageReadException | IOException e) {
            e.printStackTrace();
          }
        }
        
//        if (text == null  ||  text.isEmpty()) {
//          if (file != null) {
//            text = file.getName();
//          }
//        }
      }
    }
    
    return text;
  }
}
