package goedegep.gpx;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Logger;

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.gpx.model.util.GPXResourceFactoryImpl;
import goedegep.util.emf.EMFResource;

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
    EMFResource<DocumentRoot> gpxResource = new EMFResource<>(GPXPackage.eINSTANCE, () -> GPXFactory.eINSTANCE.createDocumentRoot(), false);
    gpxResource.addResourceFactoryForFileExtension("gpx", new GPXResourceFactoryImpl());
    
    return gpxResource;
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
      return GpxUtil.getStartLocation(documentRoot.getGpx());
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
    LOGGER.info("=> " + boundingBox.toString());
    
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
    
    return new WGS84Coordinates(firstTrackPoint.getLat().doubleValue(), firstTrackPoint.getLon().doubleValue());
  }

}
