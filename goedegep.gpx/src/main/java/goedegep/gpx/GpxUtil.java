package goedegep.gpx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.resource.ResourceSet;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.gpx.model.util.GPXResourceFactoryImpl;
import goedegep.gpx10.model.GPX10Factory;
import goedegep.gpx10.model.GPX10Package;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EMFResourceSet;

/**
 * This class provides utility methods for GPX data.
 * <p>
 * The methods operate at different levels.
 * If you only need one piece of information from a GPX file, you can use a method which takes a file as argument. In this case the file will be read and the requested
 * information is retrieved from the contents of the file.
 * If you need more information, read the file and use the methods that operate on the type of goedegep.gpx.model.
 */
public class GpxUtil {
  private static final Logger LOGGER = Logger.getLogger(GpxUtil.class.getName());
  
  /**
   * Create an {@link EMFResource} for a GPX file.
   * 
   * @return an {@link EMFResource} for a GPX file.
   */
  public static EMFResource<DocumentRoot> createEMFResource() {
    ResourceSet resourceSet = EMFResourceSet.getResourceSet();
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
        "gpx", new GPXResourceFactoryImpl());
    
    EMFResource<DocumentRoot> gpxResource = new EMFResource<>(GPXPackage.eINSTANCE, GpxUtil::createBasicDocumentRoot, ".gpx", false);
    
    return gpxResource;
  }

  /**
   * Create an {@link EMFResource} for a GPX file.
   * 
   * @return an {@link EMFResource} for a GPX file.
   */
  public static EMFResource<goedegep.gpx10.model.DocumentRoot> createGPX10EMFResource() {
    ResourceSet resourceSet = EMFResourceSet.getResourceSet();
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
        "gpx", new GPXResourceFactoryImpl());
    
    EMFResource<goedegep.gpx10.model.DocumentRoot> gpx10Resource = new EMFResource<>(GPX10Package.eINSTANCE, () -> GPX10Factory.eINSTANCE.createDocumentRoot(), ".gpx", false);
    
    return gpx10Resource;
  }
  
  public static DocumentRoot createBasicDocumentRoot() {
    GPXFactory gpxFactory = GPXFactory.eINSTANCE;
    
    DocumentRoot documentRoot = gpxFactory.createDocumentRoot();
    
    GpxType gpxType = gpxFactory.createGpxType();
    gpxType.setVersion("1.1");
    documentRoot.setGpx(gpxType);
    
    EMap<String, String> xmlNsPrefixMap = documentRoot.getXMLNSPrefixMap();
    xmlNsPrefixMap.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    xmlNsPrefixMap.put("", "http://www.topografix.com/GPX/1/1");
    
    EMap<String, String> xsiSchemaLocationMap = documentRoot.getXSISchemaLocation();
    xsiSchemaLocationMap.put("http://www.topografix.com/GPX/1/1", "http://www.topografix.com/GPX/1/1/gpx.xsd");
    
    return documentRoot;
  }
  
  
  /**
   * Get the start location from a GPX file.
   *  
   * @param fileName the file name (full path) of a GPX file.
   * @return the coordinates of the the location of the first point of the first segment of the first track in the file,
   *         or null if this isn't available.
   */
  public static WGS84Coordinates getStartLocation(String fileName) {
    EMFResource<DocumentRoot> gpxResource = createEMFResource();
    
    try {
      gpxResource.load(fileName);
      DocumentRoot documentRoot = gpxResource.getEObject();
      return getStartLocation(documentRoot.getGpx());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Calculate the bounding box of a GPX data set.
   * <p>
   * The bounding box will be a box around all waypoint, routes and tracks.
   * 
   * @param gpxType a {@code GpxType}
   * @return the bounding box of the {@code gpxType}.
   */
  public static WGS84BoundingBox calculateBoundingBox(GpxType gpxType) {
    LOGGER.info("=>");

    WGS84BoundingBox boundingBox = null;

    for (WptType waypoint : gpxType.getWpt()) {
      LOGGER.info("Handling waypoint");
      if (boundingBox == null) {
        boundingBox = new WGS84BoundingBox(waypoint.getLon().doubleValue(), waypoint.getLat().doubleValue());
      } else {
        boundingBox = boundingBox.extend(waypoint.getLon().doubleValue(), waypoint.getLat().doubleValue());
      }
    }

    for (RteType route : gpxType.getRte()) {
      WGS84BoundingBox routeBoundingBox = calculateBoundingBox(route);

      if (routeBoundingBox == null) {
        boundingBox = routeBoundingBox;
      } else {
        boundingBox = boundingBox.extend(routeBoundingBox);
      }
    }

    for (TrkType track : gpxType.getTrk()) {
      WGS84BoundingBox trackBoundingBox = calculateBoundingBox(track);
      if (boundingBox == null) {
        boundingBox = trackBoundingBox;
      } else {
        boundingBox = boundingBox.extend(trackBoundingBox);
      }
    }

    LOGGER.info("=> " + boundingBox.toString());
    return boundingBox;
  }

  /**
   * Calculate the bounding box of a route.
   * 
   * @param route a {@code RteType}
   * @return the bounding box of the {@code route}.
   */
  public static WGS84BoundingBox calculateBoundingBox(RteType route) {
    LOGGER.info("=>");
    
    WGS84BoundingBox boundingBox = null;

    for (WptType routePoint : route.getRtept()) {
      if (boundingBox == null) {
        boundingBox = new WGS84BoundingBox(routePoint.getLon().doubleValue(), routePoint.getLat().doubleValue());
      } else {
        boundingBox = boundingBox.extend(routePoint.getLon().doubleValue(), routePoint.getLat().doubleValue());
      }
    }
    
    LOGGER.severe("=> " + boundingBox.toString());
    return boundingBox;
  }


  /**
   * Calculate the bounding box of a track.
   * 
   * @param track a {@code TrkType}
   * @return the bounding box of {@code track}.
   */
  public static WGS84BoundingBox calculateBoundingBox(TrkType track) {
    LOGGER.info("=>");

    WGS84BoundingBox boundingBox = null;

    for (TrksegType trackSegment: track.getTrkseg()) {
      WGS84BoundingBox trackSegmentBoundingBox = calculateBoundingBox(trackSegment);

      if (boundingBox == null) {
        boundingBox = trackSegmentBoundingBox;
      } else {
        boundingBox = boundingBox.extend(trackSegmentBoundingBox);
      }
    }

    LOGGER.info("=> " + boundingBox.toString());
    return boundingBox;
  }

  /**
   * Calculate the bounding box of a track segment.
   * 
   * @param trackSegment a {@code TrksegType}
   * @return the bounding box of {@code trackSegment}
   */
  public static WGS84BoundingBox calculateBoundingBox(TrksegType trackSegment) {
    LOGGER.info("=>");
    
    WGS84BoundingBox boundingBox = null;

    for (WptType trackPoint : trackSegment.getTrkpt()) {
      if (boundingBox == null) {
        boundingBox = new WGS84BoundingBox(trackPoint.getLon().doubleValue(), trackPoint.getLat().doubleValue());
      } else {
        boundingBox = boundingBox.extend(trackPoint.getLon().doubleValue(), trackPoint.getLat().doubleValue());
      }
    }
    LOGGER.info("=> " + (boundingBox != null ? boundingBox.toString() : "<null>"));
    
    return boundingBox;
  }
  
  /**
   * Get the start location of a <code>GpxType</code>.
   *  
   * @param gpxType a <code>GpxType</code>.
   * @return the coordinates of the the location of the first point of the first segment of the first track of the <code>gpxType</code>,
   *         or null if this isn't available.
   */
  public static WGS84Coordinates getStartLocation(GpxType gpxType) {
    List<TrkType> tracks = gpxType.getTrk();
    if (tracks.isEmpty()) {
      return null;
    }
    TrkType track = tracks.get(0);
    List<TrksegType> segments = track.getTrkseg();
    if (segments.isEmpty()) {
      return null;
    }
    TrksegType segment = segments.get(0);
    List<WptType> trackPoints = segment.getTrkpt();
    if (trackPoints.isEmpty()) {
      return null;
    }
    WptType firstTrackPoint = trackPoints.get(0);
    if ((firstTrackPoint.getLat() == null)  ||  (firstTrackPoint.getLon() == null)) {
      return null;
    }
    
    return new WGS84Coordinates(firstTrackPoint.getLat().doubleValue(), firstTrackPoint.getLon().doubleValue(), firstTrackPoint.getEle().doubleValue());
  }

  /**
   * Remove points which deviate too much from the rest.
   * <p>
   * A point is too far away when:<ol>
   * <li>further away from previous than maxDistance AND</li>
   * <li>previous and next closer than maxDistance.
   * </ol>
   * This way a gap in waypoints isn't counted as one point too far away.
   * Note: this method hasn't been used/tested yet.
   * 
   * @param waypointList The list too filter
   * @param maxDistance the maximum distance a point may deviate
   * @return 
   */
  public static boolean[] removeSingleTooFarAway(List<WptType> waypointList, double maxDistance) {
    final boolean[] keep = new boolean[waypointList.size()];

    if (waypointList.isEmpty()) {
      // nothing to do
      return keep;
    } else if (waypointList.size() < 3) {
      // need at least 3 points for algorithm to work
      for (int index = 0; index < waypointList.size(); index++) {
        keep[index] = true;
      }            
      return keep;
    }

    WptType checkPt;
    WGS84Coordinates checkPtCoordinates;
    
    WptType prevPt;
    WGS84Coordinates prevPtCoordinates;
    
    WptType prevPrevPt;
    WGS84Coordinates prevPrevPtCoordinates;

    WptType nextPt;
    WGS84Coordinates nextPtCoordinates;
    
    WptType nextNextPt;
    WGS84Coordinates nextNextPtCoordinates;
    
    double distance1;
    double distance2;

    int startIndex = 0;
    int endIndex = waypointList.size() - 1;

    // first point is tricky, since we don't have a prev point, so use next and next-next in that case
    // so go forward from start till we have a valid point
    while(startIndex < endIndex - 2) {
      checkPt = waypointList.get(startIndex);
      checkPtCoordinates = waypointLatLonToWGS84Coordinates(checkPt);
      
      nextPt = waypointList.get(startIndex+1);
      nextPtCoordinates = waypointLatLonToWGS84Coordinates(nextPt);
      
      nextNextPt = waypointList.get(startIndex+2);
      nextNextPtCoordinates = waypointLatLonToWGS84Coordinates(nextNextPt);
      
      distance1 = checkPtCoordinates.getDistanceMeters(nextPtCoordinates);
      distance2 = nextPtCoordinates.getDistanceMeters(nextNextPtCoordinates);
      if ((distance1 > maxDistance) && (distance2 <= maxDistance)) {
        // startIndex point is garbage, take next one
        keep[startIndex] = false;
        startIndex++;
      } else {
        keep[startIndex] = true;
        break;
      }
    }

    // last point is tricky, since we don't have a next point, so use prev and prev-prev in that case
    // so go backward from end til we have a valid point
    while(endIndex > startIndex+2) {
      checkPt = waypointList.get(endIndex);
      checkPtCoordinates = waypointLatLonToWGS84Coordinates(checkPt);

      prevPt = waypointList.get(endIndex-1);
      prevPtCoordinates = waypointLatLonToWGS84Coordinates(prevPt);

      prevPrevPt = waypointList.get(endIndex-2);
      prevPrevPtCoordinates = waypointLatLonToWGS84Coordinates(prevPrevPt);

      distance1 = checkPtCoordinates.getDistanceMeters(prevPtCoordinates);
      distance2 = prevPtCoordinates.getDistanceMeters(prevPrevPtCoordinates);
      if ((distance1 > maxDistance) && (distance2 <= maxDistance)) {
        // endIndex point is garbage, take prev one
        keep[endIndex] = false;
        endIndex--;
      } else {
        keep[endIndex] = true;
        break;
      }
    }

    // anything left todo? we need 3 remaining points!
    if (startIndex > endIndex - 2) {
      for (int index = startIndex + 1; index < endIndex; index++) {
        keep[index] = true;
      }
      return keep;
    }

    for (int index = startIndex+1; index < endIndex; index++) {
      checkPt = waypointList.get(index);
      checkPtCoordinates = waypointLatLonToWGS84Coordinates(checkPt);
      
      prevPt = waypointList.get(index-1);
      prevPtCoordinates = waypointLatLonToWGS84Coordinates(prevPt);

      nextPt = waypointList.get(index+1);
      nextPtCoordinates = waypointLatLonToWGS84Coordinates(nextPt);

      distance1 = checkPtCoordinates.getDistanceMeters(prevPtCoordinates);
      distance2 = prevPtCoordinates.getDistanceMeters(nextPtCoordinates);
      if ((distance1 > maxDistance) && (distance2 <= maxDistance)) {
        // this point is garbage
        keep[index] = false;
      } else {
        keep[index] = true;
      }
    }

    return keep;
  }
  
  /**
   * Create WGS84Coordinates for the lat/lon of a WptType.
   * 
   * @param waypoint a <code>WptType</code>
   * @return WGS84Coordinates for the lat/lon of the <code>waypoint</code>.
   */
  public static WGS84Coordinates waypointLatLonToWGS84Coordinates(WptType waypoint) {
    return new WGS84Coordinates(waypoint.getLat().doubleValue(), waypoint.getLon().doubleValue(), waypoint.getEle().doubleValue());
  }
  
  
  /**
   * Get the difference in elevation between two waypoints.
   * 
   * @param waypoint1 a waypoint
   * @param waypoint2 another waypoint
   * @return the difference in elevation between <code>waypoint1</code> and <code>waypoint2</code>, or null if at least one of the waypoints has no elevation.
   *         If <code>waypoint2</code> is higher than <code>waypoint1</code> the difference is positive.
   */
  public static Double elevationDiff(final WptType waypoint1, final WptType waypoint2) {
    WGS84Coordinates coordinates1 = waypointLatLonToWGS84Coordinates(waypoint1);
    WGS84Coordinates coordinates2 = waypointLatLonToWGS84Coordinates(waypoint2);
    
    return coordinates1.elevationDifference(coordinates2);
  }
  
  /**
   * Get the slope percentage between two waypoints.
   * 
   * @param waypoint1 a waypoint
   * @param waypoint2 another waypoint
   * @return the slope between <code>waypoint1</code> and <code>waypoint2</code>, or null if at least one of the waypoints has no elevation.
   *         If <code>waypoint2</code> is higher than <code>waypoint1</code> the slope is positive.
   */
  public static double slope(final WptType waypoint1, final WptType waypoint2) {
    WGS84Coordinates coordinates1 = waypointLatLonToWGS84Coordinates(waypoint1);
    WGS84Coordinates coordinates2 = waypointLatLonToWGS84Coordinates(waypoint2);
    
    return coordinates1.slope(coordinates2);
  }
  
  /**
   * Get the distance between two waypoints.
   * 
   * @param waypoint1 a waypoint
   * @param waypoint2 another waypoint
   * @return the distance between <code>waypoint1</code> and <code>waypoint2</code>.
   */
  public static double distance(final WptType waypoint1, final WptType waypoint2) {
    WGS84Coordinates coordinates1 = waypointLatLonToWGS84Coordinates(waypoint1);
    WGS84Coordinates coordinates2 = waypointLatLonToWGS84Coordinates(waypoint2);
    
    return coordinates1.getDistanceMeters(coordinates2);
  }
  
  /**
   * Get the duration between two waypoints.
   * 
   * @param waypoint1 a waypoint
   * @param waypoint2 another waypoint
   * @return the time between <code>waypoint1</code> and <code>waypoint2</code>, or null if at least one of the waypoints has no time.
   */
  public static Integer duration(final WptType waypoint1, final WptType waypoint2) {

    if (waypoint1.getTime() != null && waypoint2.getTime() != null) {
      return waypoint1.getTime().getMillisecond() - waypoint2.getTime().getMillisecond();
    } else {
      return null;
    }
  }
  
  /**
   * Get the speed (in km/h) between two waypoints.
   * 
   * @param waypoint1 a waypoint
   * @param waypoint2 another waypoint
   * @return the speed between <code>waypoint1</code> and <code>waypoint2</code>, or null if at least one of the waypoints has no time.
   */
  public static double speed(final WptType waypoint1, final WptType waypoint2) {
    final Integer diffMilliSeconds = duration(waypoint1, waypoint2);
    final double diffMeters = distance(waypoint1, waypoint2);
    
    return diffMeters / diffMilliSeconds * 3600;
  }
  
  /**
   * Get the GPX version from a GPX file.
   * <p>
   * The versions recognized are 1.0 and 1.1. In all other cases {@code GpxVersion.NO_GPX} is returned.
   * 
   * @param filename the name of the file for which the GPX version is to be determined.
   * @return the {@code GpxVersion} of the file identified by the {@code filename}.
   */
  public static GpxVersion getGPXFileVersion(File file) throws FileNotFoundException {
    GpxVersion gpxVersion = null;

    try (FileReader fileReader = new FileReader(file)) {
      BufferedReader reader = new BufferedReader(fileReader);
      String line = reader.readLine();
      line = reader.readLine();
      if (line.contains("version=\"1.0\"")) {
        gpxVersion = GpxVersion.VERSION_1_0;
      } else if (line.contains("version=\"1.1\"")) {
        gpxVersion = GpxVersion.VERSION_1_1;
      } else {
        gpxVersion = GpxVersion.NO_GPX;
      }
    } catch (IOException e) {
      throw new RuntimeException("IOException message is " + e.getLocalizedMessage());
    }

    return gpxVersion;
  }
}
