package goedegep.vacations.app.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.EMFResource;
import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.gpx.model.util.GPXResourceFactoryImpl;
import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.vacations.model.ActivityLabel;
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
   */
  public static List<WGS84Coordinates> getGeoLocations(Vacation vacation) {
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
  
  public static int countNumberOfDays(Vacation vacation) {
    int numberOfDays = 0;
    
    TreeIterator<EObject> iterator = vacation.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Day) {
        Day day = (Day) eObject;
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
   * Add the geo-locations of a <code>VacationElement</code> and all its children to a list of geo-locations.
   * 
   * @param geoLocations the list to which the locations are added.
   * @param element the <code>VacationElement</code> for which the locations will be added.
   */
  private static void addGeoLocationsForVacationElement(List<WGS84Coordinates> geoLocations, VacationElement element, WGS84Coordinates[] stayedAtLocations) {
    
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

  private static void addGeoLocationForVacationElementLocation(List<WGS84Coordinates> geoLocations, Location location, WGS84Coordinates[] stayedAtLocations) {
    
    if (location.isSetLatitude()  &&  location.isSetLongitude()) {
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

  private static void addGeoLocationForVacationElementPicture(List<WGS84Coordinates> geoLocations, Picture picture) {
    WGS84Coordinates coordinates = null;
    FileReference pictureReference = picture.getPictureReference();
    if (pictureReference == null) {
      return;
    }
    String fileName = pictureReference.getFile();
    if (fileName == null) {
      return;
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
}
