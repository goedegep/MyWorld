package goedegep.geo;

import java.io.Serializable;
import java.util.logging.Logger;

import com.google.common.geometry.S2Earth;
import com.google.common.geometry.S2LatLng;

/**
 * This class represents the coordinates of a location. Coordinate's latitude and longitude are
 * represented in decimal degrees. A negative longitude means West (of Greenwich) and a negative latitude means South
 * (of the equator). There's also an optional elevation.
 * 
 * A 3D coordinate is an extension of a 2D coordinate. So ideally these 3D coordinates would extend {@link S2LatLng},
 * but this isn't possible as S2LatLng is final. Therefore this class uses a reference to S2LatLng for the 2D part and adds an elevation.<br/>
 * Where possible the functionality is built on top of com.google.common.geometry.
 * 
 * There are methods to:
 * <ul>
 * <li>get the distance to another point</li>
 * <li>get the slope between a point and another point</li>
 * </ul>
 */
public class WGS84Coordinates implements Serializable {
  private static final long serialVersionUID = 2270291021135307162L;

  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(WGS84BoundingBox.class.getName());

    private S2LatLng s2LatLng;
    // Elevation is optional, so it is op type Double.
    private Double elevation;
    
    /**
     * Constructor for setting latitude and longitude only.
     * 
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     */
    public WGS84Coordinates(double latitude, double longitude) {
      this(latitude, longitude, null);
    }
    
    /**
     * Constructor for location with elevation.
     * 
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param elevation The elevation of the location.
     */
    public WGS84Coordinates(double latitude, double longitude, Double elevation) {
      s2LatLng = S2LatLng.fromDegrees(latitude, longitude);
      this.elevation = elevation;
    }    
    
    /**
     * Get the S2LatLng part (the 2D part) of the coordinates.
     * 
     * @return the S2LatLng (2D) part of the coordinates.
     */
    public S2LatLng getS2LatLng() {
      return s2LatLng;
    }

    /**
     * Get the latitude of the location.
     * 
     * @return the latitude of the location.
     */
    public double getLatitude() {
      return s2LatLng.latDegrees();
    }
    
    /**
     * Get the longitude of the location.
     * 
     * @return the longitude of the location.
     */
    public double getLongitude() {
      return s2LatLng.lngDegrees();
    }
    
    /**
     * Get the elevation of the location.
     * 
     * @return the elevation of the location, or null if the elevation is not available.
     */
    public double getElevation() {
		return elevation;
	}

	/**
	 * Calculate the distance to another point in meters.
	 * 
	 * @param point the point to which the distance is to be calculated (mandatory).
	 * @return the distance from here to the {@code point}.
	 */
    public double getDistanceMeters(WGS84Coordinates point) {
      double s2distance = S2Earth.getDistanceMeters(this.s2LatLng, point.s2LatLng);
      return s2distance;
    }
	
	/**
	 * Get the difference in elevation between this point and another point.
	 * 
	 * @param point the point in relation to which the elevation difference is to be determined.
	 * @return the difference in elevation between this point and the specified <code>point</code>.
	 */
    public Double elevationDifference(final WGS84Coordinates point) {
      if ((elevation == null)  ||  (point.elevation == null)) {
        return null;
      }
      
      return point.elevation - elevation;
    }
    
    /**
     * Get the slope percentage between this point and another point.
     * 
     * @param point the point in relation to which the slope is to be determined.
     * @return the slope between this point and the specified <code>point</code>.
     */
    public Double slope(final WGS84Coordinates point) {
      Double elevationDiff = elevationDifference(point);
      if (elevationDiff == null) {
        return null;
      }

      return elevationDiff / getDistanceMeters(point) * 100.0;
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      
      buf.append(s2LatLng.latDegrees());
      buf.append(", ");
      buf.append(s2LatLng.lngDegrees());
      if (elevation != null) {
          buf.append(", ");
          buf.append(elevation);
      }
      
      return buf.toString();
    }
    
    /**
     * Get a textual representation of the coordinates, using degress, minutes and seconds notation.
     * 
     * @return a textual representation of the coordinates, using degress, minutes and seconds notation.
     */
    public String toDegreesMinutesSecondsString() {
      return GeoUtil.latToDegreesMinutesSecondsString(s2LatLng.latDegrees()) + " " + GeoUtil.lonToDegreesMinutesSecondsString(s2LatLng.lngDegrees());      
    }
}
