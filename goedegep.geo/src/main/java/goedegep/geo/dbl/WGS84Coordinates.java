package goedegep.geo.dbl;

/**
 * This class represents the coordinates of a location. Coordinate's latitude and longitude are
 * represented in decimal degrees. A negative longitude means West (of Greenwich) and a negative latitude means South
 * (of the equator). There's also an optional elevation.
 */
public class WGS84Coordinates {
    // spherical earth
    public static final double EARTH_AVERAGE_RADIUS = 6372795.477598; //6371030.0;

    // Latitude and longitude are mandatory, so can be of type double.
    // Elevation is optional, so it is op type Double.
    private double latitude;
    private double longitude;
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
      this.latitude = latitude;
      this.longitude = longitude;
      this.elevation = elevation;
    }
    
    /**
     * Get the latitude of the location.
     * 
     * @return the latitude of the location.
     */
    public double getLatitude() {
        return latitude;
    }
    
    /**
     * Set the latitude of the location.
     * @param latitude the latitude of the location.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * Get the longitude of the location.
     * 
     * @return the longitude of the location.
     */
    public double getLongitude() {
        return longitude;
    }
    
    /**
     * Set the longitude of the location.
     * 
     * @param longitude the longitude of the location.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
     * Set the elevation of the location.
     * 
     * @param elevation the elevation of the location. Null can be used to indicate that the information is not available.
     */
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	/**
	 * Calculate the distance to another point in meters.
	 * 
	 * @param point the point to which the distance is to be calculated (mandatory).
	 * @return the distance from here to the {@code point}.
	 */
	public double getDistance(WGS84Coordinates point) {        
        final double lat1 = Math.toRadians(getLatitude());
        final double lat2 = Math.toRadians(point.getLatitude());
        final double lon1 = Math.toRadians(getLongitude());
        final double lon2 = Math.toRadians(point.getLongitude());
        
        final double lat21 = lat2 - lat1;
        final double lon21 = lon2 - lon1;
        final double a =
                Math.sin(lat21/2.0) * Math.sin(lat21/2.0)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(lon21/2.0) * Math.sin(lon21/2.0);
        return 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a)) * (EARTH_AVERAGE_RADIUS + (getElevation() + point.getElevation())/2.0);
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      
      buf.append(latitude);
      buf.append(", ");
      buf.append(longitude);
      if (elevation != null) {
          buf.append(", ");
          buf.append(elevation);
      }
      
      return buf.toString();
    }
}
