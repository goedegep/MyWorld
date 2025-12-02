package goedegep.travels.app.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.google.common.geometry.S1Angle;
import com.google.common.geometry.S2Earth;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2LatLngRect;
import com.google.common.geometry.S2Loop;
import com.google.common.geometry.S2Point;

import goedegep.geo.WGS84Coordinates;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.Triplet;
import goedegep.util.datetime.DateUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.util.string.StringUtil;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.VacationsFactory;

/**
 * This class provides functionality to import photos to a Vacation.
 * 
 */
public class PhotosImporter {
  private static final Logger LOGGER = Logger.getLogger(PhotosImporter.class.getName());
  private static final VacationsFactory VACATIONS_FACTORY = VacationsFactory.eINSTANCE;
  private static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
  
  /*
   * Information related to the vacation
   */
  
  // A list of all elements with location information  (for the second option: a match on Location)
  private Map<VacationElement, Triplet<WGS84Coordinates, BoundingBox, List<Boundary>>> geoLocations = null;
  
  /*
   * Information related to the photo being handled.
   * 
   */
  // S2LatLng for the coordinates of the photo which is being handled.
  S2LatLng photoS2LatLng = null;

  // S2Point for the coordinates of the photo which is being handled.
  private S2Point photoS2Point = null;
  
  /*
   * Information about the current vacation element
   */
  
  // The vacation element being checked.
  private VacationElement vacationElement = null;
  
  // Location information for vacationElement.
  Triplet<WGS84Coordinates, BoundingBox, List<Boundary>> geoLocation = null;
  
  /**
   * Information about the best match so far.
   */
  
  // Best match vacation element.
  private VacationElement bestMatchVacationElement = null;

  // Best match ...
//  private S2LatLng bestMatchS2LatLng = null;

  // Best match bounding box
//  private S2LatLngRect bestMatchS2LatLngRect = null;

  // Best match polygon
  private S2Loop bestMatchS2Loop = null;
  
  // Best distance
  private Double bestDistanceMatch = null;
  
  
  /**
   * Import photos to a vacation.
   * <p>
   * A photo is only added if it doesn't exist in the vacation yet (based on its filename).
   * This makes it possible to start with adding some photos manually and later perform an import.<br/>
   * The following order is used to find the item of the vacation where the photo is added:
   * <ol>
   * <li>
   * A match on Location and Day<br/>
   * If there is a Day for which the date matches with the time the photo is taken, and that Day has a child Location which matches with the coordinates of the photo,
   * then the photo is added as last element of that Location.
   * </li>
   * <li>
   * A match on Location<br/>
   * If there is a Location which matches with the coordinates of the photo, then the photo is added as last element of that Location.
   * </li>
   * <li>
   * A match on Day<br/>
   * If there is a Day for which the date matches with the time the photo is taken, then the photo is added as last element of that Day.
   * </li>
   * <li>
   * No match on day and/or location<br/>
   * If there is no match with a Day and/or a Location , then the photo is added as last element of the <code>vacation</code>.
   * </li>
   * </ol>
   * 
   * @param vacation the vacation to which photos are added.
   */
  public List<PhotoImportResult> importPhotos(Vacation vacation) {
    // Information that is returned.
    List<PhotoImportResult> photoImportResults = new ArrayList<>();
    
    // get a list of all filenames of the photos which are already part of the vacation.
    Map<String, VacationElement> photoFileNameToParentElementMap = generatePhotoFileNameToParentElementMap(vacation);
    Set<String> existingPhotosFileNames = photoFileNameToParentElementMap.keySet();
    for (String photoFilename: existingPhotosFileNames) {
      LOGGER.severe("Existing photo filename: " + photoFilename);
    }
    
    // get a list of all days (for the third option, and to create the map from days to elements of that day with location information)
    List<Day> vacationDays = VacationsUtils.getVacationDays(vacation);
    
    // get the map of all elements with location information with their location information  (for the second option)
    geoLocations = VacationsUtils.getVacationGeoLocations(vacation);
    for (VacationElement element: geoLocations.keySet()) {
      LOGGER.severe("Element with location information: " + element);
    }
    
    // create a map from days to the elements of that day with location information (for the first option)
    Map<Day, List<VacationElement>> dayElementsMap = new HashMap<>();
    
    for (Day day: vacationDays) {
      List<VacationElement> geoElements = new ArrayList<>();
      for (VacationElement vacationElement: geoLocations.keySet()) {
        Day elementDay = VacationsUtils.getAncestorOfType(vacationElement, Day.class);
        if (day.equals(elementDay)) {
          geoElements.add(vacationElement);
        }
      }
      dayElementsMap.put(day, geoElements);
    }
    
    // Get all photo folders to handle
    List<Path> vacationPhotoFolderPaths = VacationsUtils.getVactionPhotosSubFoldersPaths(vacation);
    for (Path path: vacationPhotoFolderPaths) {
      LOGGER.severe("Vacation folder path: " + path);
    }
          
    // List of folders to skip
    List<String> skipFoldernames = StringUtil.commaSeparatedValuesToListOfValues("weg,Originals");
    
    for (Path photosPath: vacationPhotoFolderPaths)  {
      LOGGER.severe("Handling photo folder: " + photosPath.toString());
      
      // Skip folders to skip.
      String foldername = photosPath.toFile().getName();
      if (skipFoldernames.contains(foldername)) {
        photoImportResults.add(new PhotoImportResult(null, PhotoImportResultType.SKIP_FOLDER_SKIPPED, foldername));
        LOGGER.severe("Skipping folder to skip: " + photosPath);
        continue;
      }
      
      // Handle all files in a photo folder
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(photosPath)) {
        for (Path checkFile: stream) {
          LOGGER.severe("Handling file/folder: " + checkFile);
          
          PhotoImportResultType photoImportResultType = null;
          
          boolean photoAlreadyPartOfVacation = false;
          
          // skip directories
          if (Files.isDirectory(checkFile)) {
            LOGGER.severe("Skipping a folder: " + checkFile);
            continue;
          }
          
          File file = new File(checkFile.toAbsolutePath().toString());
          String filename = file.getAbsolutePath();
          
          // Skip photos which are already part of the vacation.
          if (existingPhotosFileNames.contains(filename)) {
            LOGGER.severe("Skipping photo which is already part of the vacation: " + filename);
            photoAlreadyPartOfVacation = true;  // continue to check that the same match will be found.
          }
          
          // Skip non jpeg files.
          if (!FileUtils.isJpegFile(checkFile)) {
            photoImportResults.add(new PhotoImportResult(filename, PhotoImportResultType.NON_JPEG_FILE_SKIPPED, filename));
            LOGGER.severe("Skipping a non jpeg file: " + checkFile);
            continue;
          }

          // Get the coordinates of the photo and the date/time it was taken.
          // Skip the photo if it doesn't have coordinates.
          // TODO add option to add these files at the top level of the vacation, so they can be manually moved to the right element.
          WGS84Coordinates photoCoordinates = null;
          LocalDateTime photoTakenDateTime = null;
          try {
            PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
            photoCoordinates = photoFileMetaDataHandler.getGeoLocation();
            photoTakenDateTime = photoFileMetaDataHandler.getCreationDateTime();
          } catch (ImageReadException | IOException e) {
            e.printStackTrace();
          }
          
          if (photoCoordinates == null) {
            photoImportResults.add(new PhotoImportResult(filename, PhotoImportResultType.PHOTO_WITHOUT_COORDINATES_SKIPPED, filename));
            LOGGER.severe("Skipping a file without coordinates: " + filename);
            continue;
          }
          
          LOGGER.severe("Photo coordinates: " + photoCoordinates.toString());
          LOGGER.severe("Photo taken date/time: " + (photoTakenDateTime != null ? photoTakenDateTime.toString() : "<not available>"));
          
          photoS2LatLng = S2LatLng.fromDegrees(photoCoordinates.getLatitude(), photoCoordinates.getLongitude());
          photoS2Point = photoS2LatLng.toPoint();
          
          bestMatchVacationElement = null;
          bestMatchS2Loop = null;
          bestDistanceMatch = null;

          
          // If the time that the photo was taken is available, find matching day(s) and for that day the best matching location.
          if (photoTakenDateTime != null) {
            long photoTakenEpochDay = photoTakenDateTime.toLocalDate().toEpochDay();
            
            for (Day day: dayElementsMap.keySet()) {
              if (isEpochDayMatchingForDayElement(photoTakenEpochDay, day)) {

                for (VacationElement aVacationElement: dayElementsMap.get(day)) {
                  vacationElement = aVacationElement;
                  if (handlePossibleBestMatch()) {
                    photoImportResultType = PhotoImportResultType.ADDED_TO_DAY_PLUS_LOCATION;
                  }
                }

                if (bestMatchVacationElement == null) {
                  LOGGER.severe("No match found for this day");
                }
              }
            }
          }
          
          // Second option: add photo to matching location.
          if (bestMatchVacationElement == null) {
            for (VacationElement aVacationElement: geoLocations.keySet()) {
              vacationElement = aVacationElement;
              if (handlePossibleBestMatch()) {
                photoImportResultType = PhotoImportResultType.ADDED_TO_LOCATION;
              }
            }
          }
          
          // Third option: match on Day.
          if (bestMatchVacationElement == null) {
            if (photoTakenDateTime != null) {
              long photoTakenEpochDay = photoTakenDateTime.toLocalDate().toEpochDay();
              
              for (Day day: dayElementsMap.keySet()) {
                if (isEpochDayMatchingForDayElement(photoTakenEpochDay, day)) {
                  bestMatchVacationElement = day;
                  photoImportResultType = PhotoImportResultType.ADDED_TO_DAY;
                }
              }
            }
          }
          
          if (photoAlreadyPartOfVacation) {
            VacationElement currentVacationElement = photoFileNameToParentElementMap.get(filename);
            VacationElement newVacationElement = null;
            if (!currentVacationElement.equals(bestMatchVacationElement)) {
              newVacationElement = bestMatchVacationElement;
            }
            photoImportResults.add(new PhotoImportResult(filename, PhotoImportResultType.EXISTING_PHOTO_SKIPPED, currentVacationElement, filename, newVacationElement));
          } else {

            Picture vacationElementPicture = VACATIONS_FACTORY.createPicture();
            FileReference pictureReference = TYPES_FACTORY.createFileReference();
            pictureReference.setFile(file.getAbsolutePath());
            vacationElementPicture.setPictureReference(pictureReference);

            if (bestMatchVacationElement != null) {
              LOGGER.severe("Going to add photo to: " + bestMatchVacationElement.toString());
              bestMatchVacationElement.getChildren().add(vacationElementPicture);
              photoImportResults.add(new PhotoImportResult(filename, photoImportResultType, bestMatchVacationElement, null));
            } else {
              LOGGER.severe("Going to add photo at vacation level");
              vacation.getElements().add(vacationElementPicture);
              photoImportResults.add(new PhotoImportResult(filename, PhotoImportResultType.ADDED_TO_VACATION, null, null));
            }
          }
        }
      } catch (IOException | DirectoryIteratorException x) {
        LOGGER.severe("Problem in finding photos folder: " + x.getMessage());
      }
    }
    
    return photoImportResults;
  }

  /**
   * Check for a location match on a VacationElement and if so whether it is a better match.
   * 
   * @param bestMatchInfo
   * @param vacationElement
   * @return true if a better match was found
   */
  private boolean handlePossibleBestMatch() {
    LOGGER.severe("Handling VacationElement: " + vacationElement.toString());
    
    if (vacationElement instanceof Picture) {
      LOGGER.severe("Skipping picture (we don't add pictures to pictures)");
      return false;
    }
    
    geoLocation = geoLocations.get(vacationElement);
    
    /*
     * Get the distances of all matches and select the best one.
     * A match in a region (Boundary or BoundingBox) is preferred above a match at a certain distance from the coordinates.
     * If a region is contained within another region, this contained region if preferred.
     */

    // Check on Boundaries using polygons
    if (handlePossibleBestBoundaryMatch()) {
      return true;
    }

    // Check on bounding box.
    if (handlePossibleBestBoundingBoxMatch()) {
      return true;
    }

    // Check on coordinates
    if (handlePossibleBestCoordinatesMatch()) {
      return true;
    }
    
    return false;
  }
  
  /**
   * Check for a location match on the boundaries of a VacationElement and if so whether it is a better match.
   * 
   * @param vacationElement
   */
  private boolean handlePossibleBestBoundaryMatch() {
    List<Boundary> boundaries = geoLocation.getObject3();

    if (boundaries == null) {
      return false;  // no boundaries, so no match possible.
    }

    LOGGER.severe("VacationElement has boundaries.");
    for (Boundary boundary: boundaries) {
      LOGGER.severe("Handling boundary.");
      // Create a polygon (here an S2Loop) from the boundary.
      List<S2Point> points = new ArrayList<>();
      for (WGS84Coordinates boundaryPoint: boundary.getPoints()) {
        S2Point loopPoint = S2LatLng.fromDegrees(boundaryPoint.getLatitude(), boundaryPoint.getLongitude()).toPoint();
        points.add(loopPoint);
      }
      LOGGER.info("Number of points: " + points.size());
      S2Loop loop = new S2Loop(points);
      
      if (loop.contains(photoS2Point)) {
        LOGGER.severe("Match found on Boundary for vacationElement: " + vacationElement.toString());
        return handleNewPolygonMatch(loop);
      } else {
        // Check whether the photo is near the boundary box
        double distance = getEarthDistanceFromS2Loop(loop, photoS2LatLng);
        LOGGER.severe("Distance to boundary box = " + distance);
        
        if (distance < 80.0) {
          return handleNewDistanceMatch(distance);
        }
      }
    }
    
    return false;
  }
  
  private boolean handlePossibleBestBoundingBoxMatch() {
    BoundingBox boundingBox = geoLocation.getObject2();
    
    if (boundingBox == null) {
      return false;
    }
    
    if (geoLocation.getObject3() != null) {
      return false;  // if the element also has boundaries, don't check on the bounding box, as it is assumed to be a bounding box around the boundaries.
    }
    
    
    S2LatLngRect s2BoundingBox = boundingBoxToS2LatLngRect(boundingBox);

    // First check on whether the photo is inside the bounding box
    if (s2BoundingBox.contains(photoS2LatLng)) {
      LOGGER.severe("Match on location bounding box: " + vacationElement.toString());
      return handleNewPolygonMatch(s2LatLngRectToS2Loop(s2BoundingBox));
    } else {
      // Check whether the photo is near the bounding box
      double distance = getEarthDistanceFromS2LatLngRect(s2BoundingBox, photoS2LatLng);
      LOGGER.severe("Distance to bounding box = " + distance);
      
      if (distance < 80.0) {
        return handleNewDistanceMatch(distance);
      }
    }
    
    return false;
  }
  
  private boolean handlePossibleBestCoordinatesMatch() {
    WGS84Coordinates coordinates = geoLocation.getObject1();
    if (coordinates == null) {
      return false;
    }
        
    if ((geoLocation.getObject2() != null)  ||  (geoLocation.getObject3() != null)) {
      return false;  // if the element also has boundaries or a bounding box, don't check on the coordinates, as it is assumed to be a boundary or bounding box around the coordinates.
    }
    
    S2LatLng coordinatesS2LatLng = S2LatLng.fromDegrees(coordinates.getLatitude(), coordinates.getLongitude());
//    Double distance = photoS2LatLng.getEarthDistance(coordinatesS2LatLng);
    Double distance = S2Earth.getDistanceMeters(photoS2LatLng, coordinatesS2LatLng);
    LOGGER.info("Distance to coordinates = " + distance);
    if (distance < 80.0) {
      return handleNewDistanceMatch(distance);
    }
    
    return false;
  }
  
  private boolean handleNewPolygonMatch(S2Loop loop) {
    boolean result = false;

    if (bestMatchVacationElement == null) {
      bestMatchVacationElement = vacationElement;
      bestMatchS2Loop = loop;
      result = true;
    } else {
      if (bestMatchS2Loop != null) {
        if (loop.contains(bestMatchS2Loop)) {
          LOGGER.severe("Found match on larger polygon; no action");
        } else if (bestMatchS2Loop.contains(loop)) {
          LOGGER.severe("Found match on smaller polygon; set this as best match");
          bestMatchVacationElement = vacationElement;
          bestMatchS2Loop = loop;
          result = true;
        } else {
          LOGGER.severe("TODO: One polygon not inside the other");
        }
      } else {
        LOGGER.severe("TODO: relate best distance to this box");
      }
    }
    
    return result;
  }
  
  private boolean handleNewDistanceMatch(double distance) {
    boolean result = false;
    
    if (bestMatchVacationElement == null) {
      bestMatchVacationElement = vacationElement;
      bestDistanceMatch = distance;
      result = true;
    } else {
      if (isBetterMatch(distance)) {
        bestMatchVacationElement = vacationElement;
        bestDistanceMatch = distance;
        bestMatchS2Loop = null;
        result = true;
      }
    }
    
    return result;
  }
  
  private boolean isBetterMatch(double distance) {
    if ((bestDistanceMatch != null)) {
      if (distance < bestDistanceMatch) {
        return true;
      }
    } else {
      // bestMatchS2Loop shall not be null
      double loopDistance = distanceForLoop(bestMatchS2Loop);
      if (distance < loopDistance) {
        return true;
      }
    }
    
    return false;
  }
  
  private double distanceForLoop(S2Loop s2Loop) {
    S2LatLngRect rect = s2Loop.getRectBound();
    double surface = getS2LatLngRectSurface(rect);
    double distance = Math.sqrt(surface);
    LOGGER.severe("distance: " + distance);
    return distance;
  }
  
  private static boolean isEpochDayMatchingForDayElement(long epochDay, Day day) {
    Date dayDate = day.getDate();
    if (dayDate != null) {
      long elementEpochDay = DateUtil.dateToLocalDate(dayDate).toEpochDay();
      Integer elementDays = day.getDays();
      if (elementDays == null) {
        elementDays = 1;
      }
      if (epochDay >= elementEpochDay  && epochDay <= elementEpochDay + elementDays - 1) {
        LOGGER.severe("Match on day: " + day.toString());
        return true;
      }
    }
    
    return false;
  }
    
  /**
   * Get a map with photo filenames to the parent element of the related Picture element.
   * There will be an entry for each Picture in the specified vacation.
   * 
   * @param vacation a {@code Vacation}
   * @return the generated photo filename to parent element map.
   */
  public static Map<String, VacationElement> generatePhotoFileNameToParentElementMap(Vacation vacation) {
    Map<String, VacationElement> photoFileNameToParentElementMap = new HashMap<>();
    
    TreeIterator<EObject> iterator = vacation.eAllContents();
    
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (eObject instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String filename = fileReference.getFile();
          if (filename != null) {
            EObject container = picture.eContainer();
            if (container instanceof VacationElement vacationElement) {
              photoFileNameToParentElementMap.put(filename, vacationElement);
            }
          }
        }
      }
    }
    
    return photoFileNameToParentElementMap;
  }
  
  /**
   * Create an S2LatLngRect for a BoundingBox.
   * 
   * @param boundingBox the BoundingBox
   * @return an S2LatLngRect for <code>boundingBox</code>.
   */
  private S2LatLngRect boundingBoxToS2LatLngRect(BoundingBox boundingBox) {
    S2LatLng lo = S2LatLng.fromDegrees(boundingBox.getSouth(), boundingBox.getWest());
    S2LatLng hi = S2LatLng.fromDegrees(boundingBox.getNorth(), boundingBox.getEast());
    S2LatLngRect s2BoundingBox = new S2LatLngRect(lo, hi);
    
    return s2BoundingBox;
  }

  /*
   * Possible methods for geo library utils
   */
  
  private double getS2LatLngRectSurface(S2LatLngRect s2LatLngRect) {
    S2LatLng size = s2LatLngRect.getSize();
    double height = size.lat().radians() * 6371010;
    LOGGER.severe("height: " + height);
    double width = size.lng().radians() * 6371010;
    LOGGER.severe("width: " + width);
    double surface = height * width;
    return surface;
  }
  
  /**
   * Create an S2Loop for an S2LatLngRect.
   * 
   * @param s2LatLngRect the rectangle
   * @return an S2Loop for an <code>s2LatLngRect</code>.
   */
  private S2Loop s2LatLngRectToS2Loop(S2LatLngRect s2LatLngRect) {
    List<S2Point> points = new ArrayList<>();
    S2LatLng hi = s2LatLngRect.hi();  // latHi(), lngHi()
    S2LatLng lo = s2LatLngRect.lo();  // latLo(), lngLo()
    S2Point bottomLeft = S2LatLng.fromRadians(lo.latRadians(), lo.lngRadians()).toPoint();
    points.add(bottomLeft);
    S2Point bottomRight = S2LatLng.fromRadians(lo.latRadians(), hi.lngRadians()).toPoint();
    points.add(bottomRight);
    S2Point topRight = S2LatLng.fromRadians(hi.latRadians(), hi.lngRadians()).toPoint();
    points.add(topRight);
    S2Point topLeft = S2LatLng.fromRadians(hi.latRadians(), lo.lngRadians()).toPoint();
    points.add(topLeft);
    
    S2Loop loop = new S2Loop(points);
    return loop;
  }
  
  private double getEarthDistanceFromS2LatLngRect(S2LatLngRect s2LatLngRect, S2LatLng s2LatLng) {
    S1Angle distanceS1Angle = s2LatLngRect.getDistance(s2LatLng);
    double distance = 6371010 * distanceS1Angle.radians();  // radius of the earth in meters
    
    return distance;
  }
  
  private double getEarthDistanceFromS2Loop(S2Loop s2Loop, S2LatLng s2LatLng) {
    S2Point s2Point = s2LatLng.toPoint();
    S1Angle distanceS1Angle = s2Loop.getDistance(s2Point);
    double distance = 6371010 * distanceS1Angle.radians();  // radius of the earth in meters
    
    return distance;
  }
}
