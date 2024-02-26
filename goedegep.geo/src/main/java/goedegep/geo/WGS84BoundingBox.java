package goedegep.geo;

import java.util.logging.Logger;

/**
 * This class represents a bounding box, defined by the nort and south latitudes, and the east and west longitudes.
 * <p>
 * A WGS84BoundingBox is unmodifiable object.<br/>
 * There are methods to:
 * <ul>
 * <li>get the center of the bounding box</li>
 * <li>obtain an extended bounding box</li>
 * <li>check whether a point is inside a bounding box</li>
 * </ul>
 */
public class WGS84BoundingBox {
  @SuppressWarnings("unused")
private static final Logger LOGGER = Logger.getLogger(WGS84BoundingBox.class.getName());

  private final double west;
  private final double north;
  private final double east;
  private final double south;
  
  public WGS84BoundingBox(double west, double north, double east, double south) {
    this.west = west;
    this.north = north;
    this.east = east;
    this.south = south;
  }
  
  public WGS84BoundingBox(double westAndEast, double northAndSouth) {
    this(westAndEast, northAndSouth, westAndEast, northAndSouth);
  }

  public double getWest() {
    return west;
  }

  public double getNorth() {
    return north;
  }

  public double getEast() {
    return east;
  }

  public double getSouth() {
    return south;
  }
  
  public double getWidth() {
    return east - west;
  }
  
  public double getHeight() {
    return north - south;
  }

  public WGS84Coordinates getCenter() {
    return new WGS84Coordinates(south + getHeight() / 2, west + getWidth() / 2);
  }
  
  /**
   * Extend a bounding box with another bounding box.
   * 
   * @param boundingBox the bounding box to be extended, which may be null.
   * @param extendingBoundingBox the bounding box with which {@code boundingBox} is to be extended.
   * @return the extended bounding box.
   */
  public static WGS84BoundingBox extend(WGS84BoundingBox boundingBox, WGS84BoundingBox extendingBoundingBox) {
    if (boundingBox == null) {
      return extendingBoundingBox;
    } else {
      return boundingBox.extend(extendingBoundingBox);
    }
  }
  
  public static WGS84BoundingBox extend(WGS84BoundingBox boundingBox, WGS84Coordinates wgs84Coordinates) {
    if (wgs84Coordinates == null) {
      return null;
    } else {
      return extend(boundingBox, wgs84Coordinates.getLongitude(), wgs84Coordinates.getLatitude());
    }
  }
  
  public static WGS84BoundingBox extend(WGS84BoundingBox boundingBox, double longitude, double latitude) {
    if (boundingBox == null) {
      return new WGS84BoundingBox(longitude, latitude);
    } else {
      return boundingBox.extend(longitude, latitude);
    }
  }
  
  public WGS84BoundingBox extend(WGS84BoundingBox boundingBox) {
    if (boundingBox == null) {
      return this;
    }
    
    return new WGS84BoundingBox(
        Math.min(west, boundingBox.getWest()),
        Math.max(north, boundingBox.getNorth()),
        Math.max(east, boundingBox.getEast()),
        Math.min(south, boundingBox.getSouth()));
  }

  public WGS84BoundingBox extend(double longitude, double latitude) {
    WGS84BoundingBox extendedBoundingBox = new WGS84BoundingBox(
        Math.min(west, longitude),
        Math.max(north, latitude),
        Math.max(east, longitude),
        Math.min(south, latitude));
    
    return extendedBoundingBox;
  }
  
  public boolean containsPoint(WGS84Coordinates point) {
    double latitude = point.getLatitude();
    double longitude = point.getLongitude();
    
    return (latitude >= north)  &&  (latitude <= south)  &&  (longitude >= west)  &&  (longitude <= east);
  }
  
  public boolean intersects(WGS84BoundingBox boundingBox) {
    return ! (boundingBox.getWest() > getEast() || boundingBox.getEast() < getWest() || boundingBox.getNorth() < getSouth() || boundingBox.getSouth() > getNorth());
  }
  
  public String toString() {
    return String.valueOf(west) + ", " + String.valueOf(north) + ", " + String.valueOf(east) + ", " + String.valueOf(south);
  }
}
