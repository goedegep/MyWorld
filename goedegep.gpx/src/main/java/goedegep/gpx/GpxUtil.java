package goedegep.gpx;

import java.util.logging.Logger;

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;

public class GpxUtil {
  private static final Logger LOGGER = Logger.getLogger(GpxUtil.class.getName());

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

}
