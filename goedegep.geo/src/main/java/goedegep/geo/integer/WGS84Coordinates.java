package goedegep.geo.integer;

/**
 * This class represents the coordinates of a location. Coordinate's latitude and longitude are
 * represented in microdegrees (decimal degrees * 10^6). A negative
 * longitude means West (of Greenwich) and a negative latitude means South
 * (of the equator).
 */
public class WGS84Coordinates {
    private int latitude;
    private int longitude;
    
    public int getLatitude() {
        return latitude;
    }
    
    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
    
    public int getLongitude() {
        return longitude;
    }
    
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
