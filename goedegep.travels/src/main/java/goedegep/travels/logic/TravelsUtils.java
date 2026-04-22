package goedegep.travels.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.poi.app.LocationCategory;
import goedegep.travels.model.Boundary;
import goedegep.travels.model.BoundingBox;
import goedegep.travels.model.Day;
import goedegep.travels.model.DayTrip;
import goedegep.travels.model.Document;
import goedegep.travels.model.GPXTrack;
import goedegep.travels.model.Location;
import goedegep.travels.model.Picture;
import goedegep.travels.model.Travel;
import goedegep.travels.model.Vacation;
import goedegep.travels.model.VacationElement;
import goedegep.travels.model.Travels;
import goedegep.travels.model.TravelsPackage;
import goedegep.types.model.FileReference;
import goedegep.util.Triplet;
import goedegep.util.datetime.FlexDate;
import goedegep.util.emf.EMFResource;
import goedegep.util.file.FileUtils;
import goedegep.util.img.ImageUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;

/**
 * This class provides utility methods for the Travels application.
 */
public class TravelsUtils {
  private static final Logger LOGGER = Logger.getLogger(TravelsUtils.class.getName());
  private static DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
  private static TravelsRegistry travelsRegistry = TravelsRegistry.getInstance();
  
  /**
   * Get the {@code Travel} to which an object belongs.
   * 
   * @param object the {@code Object} for which the {@code Travel} is to be obtained.
   * @return The {@code Travel} to which the {@code object} belongs, or null if the object is not part of a travel.
   */
  public static Travel getTravelForObject(Object object) {
    if (object == null) {
      return null;
    }
    
    if (object instanceof EObject eObject) {
      while ((eObject != null)  &&  !(eObject instanceof Travel)) {
        eObject = eObject.eContainer();
      }
      
      if (eObject instanceof Travel travel) {
        return travel;
      }
    }
        
    return null;
  }
  
  /**
   * Get the type of map image that can be used for an object.
   * <p>
   * The type is determined by the class of the object, or by the class of its container.
   * 
   * @param eObject the {@code Object} for which the map image type is to be obtained.
   * @return The {@code MapImageType} for the {@code object}, or null if no type can be determined.
   */
  public static MapImageType getMapImageType(EObject eObject) {
    while (eObject != null) {
      switch (eObject) {
      case Day _ -> {return MapImageType.DAY;}
      case DayTrip _ -> {return MapImageType.TRAVEL;}
      case GPXTrack _ -> {return MapImageType.LOCATION;}
      case Location _ -> {return MapImageType.LOCATION;}
      case Picture _ -> {return MapImageType.LOCATION;}
      case Travel _ -> {return MapImageType.TRAVEL;}
      default -> {}
      }
      
      eObject = eObject.eContainer();
    }
    
    return null;
  }
  
  /**
   * Get a list of lines connecting the locations of a travel.
   * <p>
   * For point locations, a line connects the points.<br/>
   * For a GPX track, the line ends at the start of the track and a new line starts at the end of the track.<br/>
   * At the end of a day, if there is a stayed at location for that day, the line goes back to that stayed at location.
   * 
   * @param travel the {@code Travel} for which the locations are to be collected.
   * @return a list of polylines (a list of code WGS84Coordinates) of all locations of {@code travel}.
   * @throws FileNotFoundException if a file, referenced by an element, doesn't exist.
   */
  public static List<List<WGS84Coordinates>> getLocationConnectingLines(Travel travel) throws FileNotFoundException {
    // The list of polylines
    List<List<WGS84Coordinates>> locationConnectingLines = new ArrayList<>();
    
    /*
     * The current polyline.
     * A polyline often goes across travel elements. Therefore the method addGeoLocationsForVacationElement() gets the current polyline as a parameter.
     * If a polyline ends addGeoLocationsForVacationElement() creates a new line and adds it to the locationConnectingLines.
     * The last line may be empty, so we have to remove it in that case.
     */
    List<WGS84Coordinates> locationsConnectingLine = new ArrayList<>();
        
    locationConnectingLines.add(locationsConnectingLine);
    
    for (VacationElement element: travel.getElements()) {
      locationsConnectingLine = addGeoLocationsForVacationElement(locationConnectingLines, locationsConnectingLine, element);
    }
    
    if (locationConnectingLines.getLast().size() < 2) {
      locationConnectingLines.removeLast();
    }
    
    
    return locationConnectingLines;
  }

  
  /**
   * Get a list of lines connecting the locations of a day trip.
   * <p>
   * For point locations, a line connects the points.<br/>
   * For a GPX track, the line ends at the start of the track and a new line starts at the end of the track.<br/>
   * At the end of a day, if there is a stayed at location for that day, the line goes back to that stayed at location.
   * 
   * @param dayTrip the {@code DayTrip} for which the locations are to be collected.
   * @return a list of polylines (a list of code WGS84Coordinates) of all locations of {@code dayTrip}.
   * @throws FileNotFoundException if a file, referenced by an element, doesn't exist.
   */
  public static List<List<WGS84Coordinates>> getLocationConnectingLines(DayTrip dayTrip) throws FileNotFoundException {
    // The list of polylines
    List<List<WGS84Coordinates>> locationConnectingLines = new ArrayList<>();
    
    /*
     * The current polyline.
     * A polyline often goes across vacation elements. Therefore the method addGeoLocationsForVacationElement() gets the current polyline as a parameter.
     * If a polyline ends addGeoLocationsForVacationElement() creates a new line and adds it to the locationConnectingLines.
     * The last line may be empty, so we have to remove that in that case.
     */
    List<WGS84Coordinates> locationsConnectingLine = new ArrayList<>();
        
    locationConnectingLines.add(locationsConnectingLine);
    
    for (VacationElement element: dayTrip.getElements()) {
      locationsConnectingLine = addGeoLocationsForVacationElement(locationConnectingLines, locationsConnectingLine, element);
    }
    
    if (locationConnectingLines.getLast().size() < 2) {
      locationConnectingLines.removeLast();
    }
    
    
    return locationConnectingLines;
  }
  
  /**
   * Get all geo-locations of a day.
   * 
   * @param day the {@code Day} for which the locations are to be collected.
   * @return a list of coordinates of all locations of the {@code day}.
   * @throws FileNotFoundException if a file, referenced by an element, doesn't exist.
   */
  public static List<List<WGS84Coordinates>> getLocationConnectingLines(Day day) throws FileNotFoundException {
    // The list of polylines
    List<List<WGS84Coordinates>> locationConnectingLines = new ArrayList<>();
    
    /*
     * The current polyline.
     * A polyline often goes across vacation elements. Therefore the method addGeoLocationsForVacationElement() gets the current polyline as a parameter.
     * If a polyline ends addGeoLocationsForVacationElement() creates a new line and adds it to the locationConnectingLines.
     * The last line may be empty, so we have to remove that in that case.
     */
    List<WGS84Coordinates> locationsConnectingLine = new ArrayList<>();
    
    locationConnectingLines.add(locationsConnectingLine);
    
    for (VacationElement element: day.getChildren()) {
      locationsConnectingLine = addGeoLocationsForVacationElement(locationConnectingLines, locationsConnectingLine, element);
    }
    
    if (locationConnectingLines.getLast().size() < 2) {
      locationConnectingLines.removeLast();
    }
    
    return locationConnectingLines;
  }
  
  @SuppressWarnings("unused")
  private static void printLocations(List<List<WGS84Coordinates>> locationConnectingLines, String title) {
    LOGGER.severe(title);
    for (List<WGS84Coordinates> list: locationConnectingLines) {
      for (WGS84Coordinates coordinates: list) {
        if (coordinates == null) {
          LOGGER.severe("null");
        } else {
          LOGGER.severe(coordinates.toString());
        }
      }
      LOGGER.severe("Line ended");
    }
    
  }
  
  /**
   * Get all geo-locations of a day.
   * 
   * @param day the {@code Day} for which the locations are to be collected.
   * @return a list of coordinates of all locations of {@code day}.
   * @throws FileNotFoundException In case a file, which is referenced by an element, doesn't exist
   */
  public static List<WGS84Coordinates> getGeoLocations(Day day) throws FileNotFoundException {
    List<WGS84Coordinates> geoLocations = new ArrayList<>();
    int numberOfDays = 0;
    Travel travel = day.getTravel();
    if (travel != null) {
      numberOfDays = getNumberOfDays(travel);
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
   * Get the number of days of a {@code Travel}.
   * <p>
   * If the (start) date and end date are set, and both are complete dates, then these dates are used to calculate the duration.
   * Otherwise the number of days is the sum of all days of all Day elements of the travel (calculated by calling {@link #countNumberOfDays}.
   * 
   * @param travel a {@code Travel}
   * @return the number of days of the {@code travel}.
   */
  public static int getNumberOfDays(Travel travel) {
    int numberOfDays = 0;
    
    if (travel.isSetDate()  &&  travel.isSetEndDate()) {
      FlexDate flexDate = travel.getDate();
      LocalDateTime startDate = flexDate.toLocalDateTime();
      
      flexDate = travel.getEndDate();
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
      numberOfDays = countNumberOfDays(travel);
    }
    
    return numberOfDays;
  }
  
  /**
   * Count the number of days of a {@code Travel}.
   * <p>
   * The number of days is the sum of all days of all Day elements of the travel.
   * This method only gives the correct value if Day elements are consistently used for the travel.
   * 
   * @param travel a {@code Travel}
   * @return the number of days of the {@code travel}
   */
  public static int countNumberOfDays(Travel travel) {
    int numberOfDays = 0;
    
    TreeIterator<EObject> iterator = travel.eAllContents();
    
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
   * Get a list of all the {@code Day} elements of a {@code Travel}.
   * 
   * @param travel a {@code Travel}
   * @return a list of all the {@code Day} elements of the {@code travel}.
   */
  public static List<Day> getTravelDays(Travel travel) {
    List<Day> days = new ArrayList<>();
    
    TreeIterator<EObject> iterator = travel.eAllContents();
    
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
   * @param travel a <code>Travel</code>
   * @return a mapping of VacationElements to their geo locations.
   */
  public static Map<VacationElement, Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>> getTravelGeoLocations(Travel travel) {
    Map<VacationElement, Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>> geoLocations = new HashMap<>();
    
    TreeIterator<EObject> iterator = travel.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof VacationElement vacationElement) {
        LOGGER.info("Handling vacation element: " + vacationElement.toString());
        Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> elementGeoLocations = getGeoLocation(vacationElement);
        if (elementGeoLocations != null) {
          geoLocations.put(vacationElement, elementGeoLocations);
        }
      }
    }
    
    return geoLocations;
  }
  
  /**
   * Get the total travel distance of a day.
   * 
   * @param day the <code>Day</code> for which the travel distance is to be calculated.
   * @return the total travel distance of the {@code day}.
   */
  public static Double getDayTravelDisctance(Day day) {
    GPXFactory gpxFactory = GPXFactory.eINSTANCE;
    GpxType gpxType = gpxFactory.createGpxType();
    List<TrkType> tracks = gpxType.getTrk();
    TrkType track = gpxFactory.createTrkType();
    tracks.add(track);
    List<TrksegType> segments = track.getTrkseg();
    TrksegType segment = gpxFactory.createTrksegType();
    segments.add(segment);
    List<WptType> trackPoints = segment.getTrkpt();
    
    // If there is a previous day with a stayed at location, add that as first point.
    // Else if there is previous day without a stayed at location, add the last location of that day as first point.
    Day previousDay = getPreviousDay(day);
    if (previousDay != null) {
      Location stayedAtLocation = getStayedAtLocation(previousDay);
      if (stayedAtLocation != null  &&  stayedAtLocation.getLatitude() != null  &&  stayedAtLocation.getLongitude() != null) {
        WptType trackPoint = gpxFactory.createWptType();
        trackPoint.setLat(new BigDecimal(stayedAtLocation.getLatitude()));
        trackPoint.setLon(new BigDecimal(stayedAtLocation.getLongitude()));
        trackPoints.add(trackPoint);        
      } else {
        try {
          List<WGS84Coordinates> previousDayGeoLocations = getGeoLocations(previousDay);
          if (!previousDayGeoLocations.isEmpty()) {
            WGS84Coordinates lastCoordinates = previousDayGeoLocations.get(previousDayGeoLocations.size() - 1);
            WptType trackPoint = gpxFactory.createWptType();
            trackPoint.setLat(new BigDecimal(lastCoordinates.getLatitude()));
            trackPoint.setLon(new BigDecimal(lastCoordinates.getLongitude()));
            trackPoints.add(trackPoint);        
          } else {
          }
        } catch (FileNotFoundException e) {
          // ignore exceptions
        }
      }
    }
    
    try {
      List<WGS84Coordinates> geoLocations = getGeoLocations(day);
      for (WGS84Coordinates coordinates: geoLocations) {
        WptType trackPoint = gpxFactory.createWptType();
        trackPoint.setLat(new BigDecimal(coordinates.getLatitude()));
        trackPoint.setLon(new BigDecimal(coordinates.getLongitude()));
        trackPoints.add(trackPoint);        
      }
      return segment.getLength() / 1000.0; // in km
    } catch (FileNotFoundException e) {
      // ignore exceptions
    }
    
    return null;
  }

  /**
   * Add the geo-locations of a {@code VacationElement} and all its children to a list of geo-locations.
   * 
   * @param geoLocations the list to which the locations are added.
   * @param element the {@code VacationElement} for which the locations will be added.
   * @param stayedAtLocations optional array of stayed at locations
   * @throws FileNotFoundException in case the file specified by the pictureReference of a Picture element doesn't exist.
   */
  private static void addGeoLocationsForVacationElement(List<WGS84Coordinates> geoLocations, VacationElement element, WGS84Coordinates[] stayedAtLocations) throws FileNotFoundException {
    
    switch(element.eClass().getClassifierID()) {
    case TravelsPackage.DAY:
      // A day has no location. Stayed at locations are handled after handling the children.
      break;
      
    case TravelsPackage.LOCATION:
      addGeoLocationForVacationElementLocation(geoLocations, (Location) element);
      break;
      
    case TravelsPackage.TEXT:
      // No action; a Text has no location.
      break;
      
    case TravelsPackage.PICTURE:
      addGeoLocationForVacationElementPicture(geoLocations, (Picture) element);
      break;
      
    case TravelsPackage.GPX_TRACK:
      addGeoLocationForVacationElementGPXTrack(geoLocations, (GPXTrack) element);
      break;
    }
    
    for (VacationElement childElement: element.getChildren()) {
      addGeoLocationsForVacationElement(geoLocations, childElement, stayedAtLocations);
    }
    
    if (element.eClass().getClassifierID() == TravelsPackage.DAY) {
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
   * Add the geo-locations of a {@code VacationElement} and all its children to a list of geo-locations.
   * <p>
   * If the {@code element} is a day, then at the end of the day the line is drawn back to the stayed at location (if available).
   * 
   * @param locationsConnectingLines the list of polylines to which any new line has to be added.
   * @param geoLocations the current polyline to which the locations are added.
   * @param element the {@code VacationElement} for which the locations will be added.
   * @param stayedAtLocations optional array of stayed at locations
   * @throws FileNotFoundException in case the file specified by the pictureReference of a Picture element doesn't exist.
   */
  private static List<WGS84Coordinates> addGeoLocationsForVacationElement(List<List<WGS84Coordinates>> locationsConnectingLines, List<WGS84Coordinates> geoLocations, VacationElement element) throws FileNotFoundException {
    
    switch(element.eClass().getClassifierID()) {
    case TravelsPackage.DAY:
      // A day has no location. Stayed at locations are handled after handling the children.
      break;
      
    case TravelsPackage.LOCATION:
      addGeoLocationForVacationElementLocation(geoLocations, (Location) element);
      break;
      
    case TravelsPackage.TEXT:
    case TravelsPackage.DOCUMENT:
      // No action; a Text or Document has no location.
      break;
      
    case TravelsPackage.PICTURE:
      addGeoLocationForVacationElementPicture(geoLocations, (Picture) element);
      break;
      
    case TravelsPackage.GPX_TRACK:
      geoLocations = addGeoLocationForVacationElementGPXTrack(locationsConnectingLines, geoLocations, (GPXTrack) element);
      break;
    }
    
    for (VacationElement childElement: element.getChildren()) {
      geoLocations = addGeoLocationsForVacationElement(locationsConnectingLines, geoLocations, childElement);
    }
    
    if (element.eClass().getClassifierID() == TravelsPackage.DAY) {
      Day day = (Day) element;
      Location stayedAtLocation = getStayedAtLocation(day);
      if (stayedAtLocation != null  &&  stayedAtLocation.getLatitude() != null  &&  stayedAtLocation.getLongitude() != null) {
        WGS84Coordinates coordinates = new WGS84Coordinates(stayedAtLocation.getLatitude(), stayedAtLocation.getLongitude());
        geoLocations.add(coordinates);
        LOGGER.info("Found stayed at");
      } else {
        LOGGER.info("No stayed at for day: " + day);
      }
    }
    
    return geoLocations;
  }

  /**
   * Get the stayed at location for a specific day.
   * 
   * @param day the {@code Day} for which the stayed at location is requested.
   * @return the stayed at {@code Location} for the {@code day}, or null if there is none.
   */
  private static Location getStayedAtLocation(Day day) {

    // Try to find a stayed at location in the current day, or in any of the previous days (with a long enough duration).
    int nrOfDaysStayed = 1;
    Day currentDay = day;
    while (currentDay != null) {
      Location location = findStayedAtLocationElement(currentDay);

      if (location != null  &&  location.getDuration() != null  &&  location.getDuration() >= nrOfDaysStayed) { 
        return location;
      } else {
        nrOfDaysStayed++;
        currentDay = getPreviousDay(currentDay);
      }
    }

    Location location = getTopLevelStayedAtLocationElement(day.getTravel());
    if (location != null  &&  location.getDuration() != null  &&  location.getDuration() >= day.getDayNr()) {
      return location;
    }

    return null;
  }

  private static Location getTopLevelStayedAtLocationElement(Travel travel) {
    
    for (VacationElement vacationElement: travel.getElements()) {
      if (vacationElement instanceof Location location  &&  location.isStayedAtThisLocation()) {
        return location;
      }
    }
    
    return null;
  }

  /**
   * Get the previous day of a {@code Day}.
   * 
   * @param day the day for which the previous day is requested.
   * @return the previous {@code Day} of {@code day}, or null if this doesn't exist.
   */
  private static Day getPreviousDay(Day day) {
    Travel travel = day.getTravel();
    
    Day previousDay = null;
    
    for (VacationElement vacationElement: travel.getElements()) {
      if (vacationElement instanceof Day aDay) {
        if (aDay == day) {
          return previousDay;
        } else {
          previousDay = aDay;
        }
      }
    }
    
    return null;
  }

  /**
   * Find the first stayed at {@code Location} element in a {@code Day}.
   * 
   * @param day the {@code Day} in which the stayed at location is to be found.
   * @return the first stayed at {@code Location} element in the {@code day}, or null if this doesn't exist.
   */
  private static Location findStayedAtLocationElement(Day day) {
    TreeIterator<EObject> iterator = day.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Location location  &&  location.isStayedAtThisLocation()) {
        return location;
      }
    }
    
    return null;
  }

  /**
   * Add the geo-location of a {@code Location} to a list of geo-locations or to an array of stayed at locations.
   * <p>
   * The location can only be added if both latitude and longitude of the {@code Location} are set.<br/>
   * If the location is a 'stayed at' location, it is added to the stayed at locations for the corresponding days.
   * Otherwise it is added to the geoLocations.
   * 
   * @param geoLocations a list of coordinates to which the coordinates of the {@code location} are added if this {@code location} isn't a 'stayed at' location.
   * @param location a {@code Location}
   * @param stayedAtLocations optional array of 'stayed at' locations. If the {@code location} is a 'stayed at' location, the coordinates of the {@code location} are set
   *        in the elements corresponding to the days you stayed at this location.
   */
  private static void addGeoLocationForVacationElementLocation(List<WGS84Coordinates> geoLocations, Location location) {
//    if (elementIsChildOfGpxTrackOrLocation(location)) {
//      // These elements are not added as they are supposed to be on or close to the line.
//      return;
//    }
    
    if (!location.isReferenceOnly()  &&                                                   // Reference only locations are not part of the lines
        !(location.isStayedAtThisLocation()  &&  elementIsChildOfTravel(location))  &&  // If a 'stayed at' location exists at travel level, it is not added here.
        location.isSetLatitude()  &&  location.isSetLongitude()) {                        // And of course it must have coordinates.
      WGS84Coordinates coordinates = new WGS84Coordinates(location.getLatitude(), location.getLongitude());      
      geoLocations.add(coordinates);
    }
  }

  /**
   * Add the geo-location of a picture to a list of geo-locations.
   * <p>
   * The coordinates can only be added if the picture element has a valid reference to a picture file which has coordinates set.
   * 
   * @param geoLocations a list of coordinates to which the coordinates of the {@code picture} are added.
   * @param picture a {@code Picture} element.
   * @throws FileNotFoundException in case the file specified by the pictureReference doesn't exist.
   */
  private static void addGeoLocationForVacationElementPicture(List<WGS84Coordinates> geoLocations, Picture picture) throws FileNotFoundException {
    if (elementIsChildOfGpxTrackOrLocation(picture)) {
      return;
    }
    
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
      throw new FileNotFoundException(fileName);
    }
    
    coordinates = ImageUtils.getGeoLocation(fileName);

    if (coordinates != null) {      
      geoLocations.add(coordinates);
    }
  }
  
  /**
   * Check whether a {@code VacationElement} is part of a {@code Travel}.
   * 
   * @param element the {@code VacationElement} to check.
   * @return true if {@code element} is part of a {@code Travel}, false otherwise.
   */
  private static boolean elementIsChildOfTravel(VacationElement element) {
    EObject containingObject = element.eContainer();
    
    if (containingObject instanceof Travel) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Check whether a {@code VacationElement} is part of a {@code GPXTrack} or {@code Location}.
   * 
   * @param element the {@code VacationElement} to check.
   * @return true if {@code element} is part of a @code GPXTrack} or {@code Location}, false otherwise.
   */
  private static boolean elementIsChildOfGpxTrackOrLocation(VacationElement element) {
    EObject containingObject = element.eContainer();
    
    if (containingObject instanceof GPXTrack  ||  containingObject instanceof Location) {
      return true;
    } else {
      return false;
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

    EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
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
    } catch (IOException e) {
      e.printStackTrace();
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
  private static List<WGS84Coordinates> addGeoLocationForVacationElementGPXTrack(List<List<WGS84Coordinates>> locationsConnectingLines, List<WGS84Coordinates> geoLocations, GPXTrack gpxTrack) {
    FileReference trackReference = gpxTrack.getTrackReference();
    if (trackReference == null) {
      return geoLocations;
    }
    
    String fileName = trackReference.getFile();
    if ((fileName == null)  ||  fileName.isEmpty()) {
      return geoLocations;
    }    

    EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
    try {
      gpxResource.load(fileName);
      DocumentRoot documentRoot = gpxResource.getEObject();
      
      WGS84Coordinates endCoordinates = GpxUtil.getEndLocation(documentRoot.getGpx());
      if (endCoordinates == null) {
        return geoLocations;
      }
      WGS84Coordinates startCoordinates = GpxUtil.getStartLocation(documentRoot.getGpx());
      if (startCoordinates == null) {  // This can happen if the first waypoint is missing a latitude or longitude
        return geoLocations;
      }
      
      if (geoLocations.isEmpty()) {
        geoLocations.add(endCoordinates);
      } else {
        geoLocations.add(startCoordinates);
        
        geoLocations = new ArrayList<>();
        geoLocations.add(endCoordinates);
        locationsConnectingLines.add(geoLocations);
      }
    } catch (IOException e) {
      LOGGER.severe("Error loading GPX file: " + fileName);
    }
    
    return geoLocations;
  }
  
  /**
   * Guess the folder in which the files related to a travel are stored.
   * <p>
   * If the travel has one or more file references, the folder is taken from the first reference that refers to a file in a folder that is a sub folder of the folder where all travels of that category are stored.
   * The folder where all travels of a category are stored is category specific:
   * <ul>
   * <li>Vacation - specified by VacationsRegistry.vacationsFolderName</li>
   * <li>DayTrip - specified by 
   * </ul>
   * If the folder can't be derived from the file references, the default is used. This is a subfolder, with the name of the travel id, of the folder where all travels of that category are stored
   * 
   * @param travel the Travel to determine the folder for.
   * @return the folder in which the files related to {@code travel} are stored
   */
  public static String getTravelFilesFolder(Travel travel) {
    String travelFilesFolder = null;

    for (FileReference fileReference: travel.getAllFileReferences()) {
      String filename = fileReference.getFile();
      if (filename != null) {
        File file = new File(filename);
        String parentFolder = file.getParentFile().getParent();
        if (travel instanceof Vacation) {
          if (travelsRegistry.getVacationsFolderName().equals(parentFolder)) {
            travelFilesFolder = file.getParent();
            break;
          }
        } else if (travel instanceof DayTrip) {
          if ("D:\\Database\\Dagtochten".equals(parentFolder)) {
            travelFilesFolder = file.getParent();
            break;
          }
        }
      }
    }
    
    if (travelFilesFolder == null) {
      String baseFolder = null;
      if (travel instanceof Vacation) {
        baseFolder = travelsRegistry.getVacationsFolderName();
      } else if (travel instanceof DayTrip) {
        baseFolder = "D:\\Database\\Dagtochten";
      }
      travelFilesFolder = baseFolder + "\\" + travel.getId();
    }

    return travelFilesFolder;
  }
  
  /**
   * Guess the folder in which the documents related to a dayTrip are stored.
   * 
   * @param dayTrip the DayTrop to determine the folder for.
   * @return the folder in which the documents related to {@code dayTrip} are stored
   */
  public static String getDayTripFolder(DayTrip dayTrip) {
//    String vacationFolder = null;

//    for (FileReference fileReference: dayTrip.getAllFileReferences()) {
//      String filename = fileReference.getFile();
//      if (filename != null) {
//        File file = new File(filename);
//        String vacationsFolder = file.getParentFile().getParent();
//        if (VacationsRegistry.vacationsFolderName.equals(vacationsFolder)) {
//          vacationFolder = file.getParent();
//
//          return vacationFolder;
//        }
//      }
//    }

    return null;
  }

//  /**
//   * Guess the folder in which the pictures related to a vacation are stored.
//   * 
//   * @param vacation the Vacation to determine the folder for.
//   * @return the folder in which the pictures related to {@code vacation} are stored
//   */
//  public static String vacationPicturesFolder(Vacation vacation) {
//    Path vacationPicturesFolderPath = Paths.get(VacationsRegistry.vacationPicturesFolderName, vacation.getId());
//    if (Files.isDirectory(vacationPicturesFolderPath)) {
//      return vacationPicturesFolderPath.toAbsolutePath().toString();
//    } else {
//      return null;
//    }
//  }
    
  /**
   * Get a Path for the folder with photos for a specific vacation.
   * <p>
   * If the 'Pictures' attribute is set on the travel, then this is the photos folder.
   * Otherwise, the folder is expected to be a folder with the name equal to the Id of the travel, and being a sub folder of the
   * folder with all photos of all travels.
   * 
   * @param travel the vacation for which to get a Path to its photos folder.
   * @return a Path to the folder with photos for {@code travel}, or null if this cannot be determined.
   */
  public static Path getVacationPhotosFolderPath(Travel travel) {
    String vacationPhotosFolder = travel.getPictures();
    
    if (vacationPhotosFolder != null) {
      vacationPhotosFolder = vacationPhotosFolder.trim();
      if (!vacationPhotosFolder.isEmpty()) {
        return Paths.get(vacationPhotosFolder);
      }
    }    
    
    /*
     *  'Pictures' attribute not set (at least not to a sensible value). Try to create the default folder name.
     *  For this the following is needed:
     *  - a valid Path to the main folder for vacation photos.
     *  - a valid vacation date
     *  - a valid vacation title
     */
    
    Path vacationPhotosFolderPath = getVacationPhotosFolderPathByConvention(travel);
    if (vacationPhotosFolderPath != null  &&  Files.exists(vacationPhotosFolderPath)  &&  Files.isDirectory(vacationPhotosFolderPath)) {
      return vacationPhotosFolderPath;
    } else {
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
    String vacationsPhotosFolderName = travelsRegistry.getVacationPicturesFolderName();
    Path vacationsPhotosFolderPath = Paths.get(vacationsPhotosFolderName);
    if (Files.exists(vacationsPhotosFolderPath)  &&  Files.isDirectory(vacationsPhotosFolderPath)) {
      return vacationsPhotosFolderPath;
    } else {
      return null;
    }
  }
  
  /**
   * Get the vacation photo folder path by convention.
   * <p>
   * By convention:
   * <ul>
   * <li>
   * the vacation folder is a subfolder of the folder with photos for all vacations (see {@link #getVacationsPhotosFolderPath}
   * </li>
   * <li>
   * the name of the subfolder is the vacation id, which means that it can only be determined if the vacation has a date and title.
   * </li>
   * </ul>
   * 
   * @param travel the {@Vacation} to get the path for.
   * @return the photo folder path by convention for {@code vacation}, or null if this cannot be determined.
   */
  public static Path getVacationPhotosFolderPathByConvention(Travel travel) {
    Path vacationsPhotosFolderPath = getVacationsPhotosFolderPath();
    
    if (vacationsPhotosFolderPath == null) {
      return null;
    }
    
    if (travel.getDate() == null) {
      return null;
    }
    
    if (travel.getTitle() == null  ||  travel.getTitle().isEmpty()) {
      return null;
    }
    
    String vacationId = travel.getId();
    
    Path vacationPhotosFolderPath = vacationsPhotosFolderPath.resolve(vacationId);
    
    return vacationPhotosFolderPath;
  }
  
  /**
   * Get all photo folders for a travel.
   * <p>
   * See {@link #getVacationPhotosFolderPath} for determining the Path to the main photo folder for the vacation.<br/>
   * Below this folder all folders are scanned to see if they contain pictures. If so the folder is added to the result list.
   * 
   * @return a list of all paths of all folders, under the vacation's photo folder, which contain photos.
   */
  public static List<Path> getVactionPhotosSubFoldersPaths(Travel vacation) {
     List<String> skippedFolderNames = new ArrayList<>();
     // TODO get skipp folder names from user settings
    skippedFolderNames.add("Originals");
    skippedFolderNames.add("weg");
    
    List<Path> vacationPhotoFolderPaths = new ArrayList<>();
    
    Path vacationPhotosFolderPath = getVacationPhotosFolderPath(vacation);

    if (vacationPhotosFolderPath == null  ||  !Files.exists(vacationPhotosFolderPath)) {
      return vacationPhotoFolderPaths;
    }
    
    try {
      Files.walkFileTree(vacationPhotosFolderPath, new FileVisitor<Path>() {
        boolean containsPhoto = false;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          LOGGER.info("preVisitDirectory");
          if (skippedFolderNames.contains(dir.getFileName().toString())) {
            return FileVisitResult.SKIP_SUBTREE;
          } else {
            containsPhoto = false;
            return FileVisitResult.CONTINUE;
          }
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
        
    return vacationPhotoFolderPaths;
  }
  
  /**
   * Get the geo location (coordinates) of a vacation element.
   * <p>
   * A value is returned for:
   * <ul>
   * <li>A <{@code Location} with both latitude and longitude set.
   * </li>
   * </ul>
   * In all other cases {@code null} will be returned.
   * 
   * @param vacationElement a {@code VacationElement}
   * @return the geo location (coordinates) of {@code vacationElement}, or null if the {@code vacationElement} can't have a location or doesn't have its location set.
   */
  public static Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> getGeoLocation(VacationElement vacationElement) {
    LOGGER.info("=> vacationElement=" + vacationElement.toString());
    
    switch(vacationElement.eClass().getClassifierID()) {
    case TravelsPackage.DAY:
      // A day has no location.
      LOGGER.info("<= null");
      return null;
      
    case TravelsPackage.LOCATION:
      LOGGER.info("<= getGeoLocation(<Location>)");
      return getGeoLocation((Location) vacationElement);
      
    case TravelsPackage.TEXT:
      // No action; a Text has no location.
      LOGGER.info("<= null");
      return null;
      
    case TravelsPackage.PICTURE:
      LOGGER.info("<= getGeoLocation(<Picture>)");
      return getGeoLocation((Picture) vacationElement);
      
    case TravelsPackage.GPX_TRACK:
      LOGGER.info("<= getGeoLocation(<GPXTrack>)");
      return getGeoLocation((GPXTrack) vacationElement);
    }
    
    return null;
  }
  
  /**
   * Get the geo location (coordinates) of a {@code Location} element.
   * 
   * @param location a {@code Location}
   * @return the geo location (coordinates) of the {@code location}, or null if not both latitude and longitude are set.
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
   * Get the geo location (coordinates) of a {@code Picture} element.
   * 
   * @param picture a {@code Picture}
   * @return the geo location (coordinates) of the {@code picture}, or null if the file reference isn't set,
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
    
    return new Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>(ImageUtils.getGeoLocation(fileName), null, null);
  }
  
  /**
   * Get the geo location (coordinates) of a {@code GPXTrack} element.
   * 
   * @param gpxTrack a {@code GPXTrack}
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
  
//  public static Day getDay(VacationElement vacationElement) {
//    if (vacationElement == null) {
//      return null;
//    }
//    
//    EObject container = vacationElement.eContainer();
//
//    while ((container != null) && !(container instanceof Day)) {
//      container = container.eContainer();
//    }
//
//    return (Day) container;
//  }
  
  /**
   * Get the first ancestor (including the {@code eObject} itself) of a specific type of an EObject.
   * 
   * @param eObject the EObject for which to get the ancestor.
   * @param clazz the class of the ancestor to get.
   * @return the first ancestor (including the {@code eObject} itself) of (including the {@code eObject itself) which is an instance of {@code clazz}, or null if no such ancestor exists.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAncestorOfType(EObject eObject, Class<T> clazz) {
    if (eObject == null) {
      return null;
    }
    
    EObject container = eObject;

    while ((container != null) && !clazz.isInstance(container)) {
      container = container.eContainer();
    }

    return (T) container;
  }
  
  /**
   * Get the text to show for a picture.
   * <p>
   * If the title is set in the file reference, this is the text to show.<br/>
   * Else, if the title is set in the picture file, this is the text to show.
   * 
   * @param picture a {@code Picture}
   * @return the text to show for the {@code picture}.
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
  
  /**
   * Get the number of points in the boundaries of a location.
   * 
   * @param location the {@code Location} for which to get the number of boundary points.
   * @return the number of points in the boundaries of {@code location}.
   */
  public static int getNumberOfPointsInLocationBoundary(Location location) {
    return location.getBoundaries().stream()
    .mapToInt(boundary -> boundary.getPoints().size())
    .sum();
  }
  
  /**
   * Get a textual 'absolute' path to an {@code EObject}.
   * 
   * @param eObject the {@code EObject} to get a path to.
   * @return a textual path to {@code eObject}.
   */
  public static String getTextPathToEObject(EObject eObject) {
    List<String> items = new ArrayList<>();
    
    items.add(getShortTextForEObject(eObject));
    
    EObject container = eObject.eContainer();
    while (container != null) {
      items.add(getShortTextForEObject(container));
      container = container.eContainer();
    }
    
    StringBuilder buf = new StringBuilder();
    
    boolean first = true;
    while (!items.isEmpty()) {
      if (first) {
        first = false;
      } else {
        buf.append(":");
      }
      buf.append(items.removeLast());
    }
    
    return buf.toString();
  }
  
  /**
   * Get a textual 'relative' path to an {@code EObject}.
   * 
   * @param eObject the {@code EObject} to get a path to.
   * @return a textual path to {@code eObject}.
   */
  public static String getTextPathToEObject(EObject eObject, EObject prefix) {
    List<String> items = new ArrayList<>();
    
    items.add(getShortTextForEObject(eObject));
    
    EObject container = eObject.eContainer();
    while (container != null  &&  container != prefix) {
      items.add(getShortTextForEObject(container));
      container = container.eContainer();
    }
    
    StringBuilder buf = new StringBuilder();
    
    boolean first = true;
    while (!items.isEmpty()) {
      if (first) {
        first = false;
      } else {
        buf.append(":");
      }
      buf.append(items.removeLast());
    }
    
    return buf.toString();
  }

  private static String getShortTextForEObject(EObject eObject) {
    String text;
    
   text = switch (eObject) {
    case Day day -> getShortTextForDay(day);
    case DayTrip dayTrip -> getShortTextForDayTrip(dayTrip);
    case Document _ -> "Document";
    case GPXTrack gpxTrack -> getShortTextForGPXTrack(gpxTrack);
    case FileReference _ -> "File Reference";
    case Location location -> getShortTextForLocation(location);
    case Vacation vacation -> getShortTextForTravel(vacation);
    case Travel travel -> getShortTextForTravel(travel);
    case Travels _ -> "Vacations";
    default -> "?";
    };
   
    return text;
  }

  private static String getShortTextForTravel(Travel travel) {
    String shortText = null;
    shortText = travel.getId();
    
    return shortText;
  }

  private static String getShortTextForDay(Day day) {
    String shortText = null;
    shortText = day.getTitle();
    
    if (shortText == null) {
      Date date = day.getDate();
      if (date != null) {
        shortText = DF.format(date);
      }
    }
    
    if (shortText == null) {
      shortText = String.valueOf(day.getDayNr());
    }
    
    return "Day " + shortText;
  }

  private static String getShortTextForDayTrip(DayTrip dayTrip) {
    String shortText = null;
    shortText = dayTrip.getId();
    
    return shortText;
  }

  private static String getShortTextForGPXTrack(GPXTrack gpxTrack) {
    String shortText = null;
    
    FileReference fileReference = gpxTrack.getTrackReference();
    if (fileReference != null) {
      shortText = fileReference.getTitle();
    }
    
    if (shortText == null) {
      shortText = "GPXTrack";
    }
    
    return shortText;
  }
  
  private static String getShortTextForLocation(Location location) {
    String shortText = null;
    shortText = location.getName();
    
    if (shortText == null) {
      LocationCategory locationCategory = location.getLocationCategory();
      if (locationCategory != null) {
        shortText = LocationCategory.getDisplayName(locationCategory);
      }
    }
    
    if (shortText == null) {
      shortText = location.getCity();
    }
    
    return shortText;
  }
  
  public static boolean doesTravelHavePictures(Travel travel) {
    TreeIterator<EObject> iterator = travel.eAllContents();
    Path vacationPhotosFolderPath = getVacationsPhotosFolderPath();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String fileName = fileReference.getFile();
          
          if (fileName != null) {
            Path picturePath = Paths.get(fileName);
            if (FileUtils.isSubPathOfPath(picturePath, vacationPhotosFolderPath)) {
              return true;
            }
          }
        }
      }
    }
    
    return false;
  }

  public static List<String> getShowFileNames(Travel travel) {
    List<String> showFileNames = new ArrayList<>();
    
    TreeIterator<EObject> iterator = travel.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String fileName = fileReference.getFile();
          
          if (fileName != null) {
            showFileNames.add(fileName);
          }
        }
      }
    }
    
    return showFileNames;
  }
}
