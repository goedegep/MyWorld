package goedegep.jfx.stringconverters;

import goedegep.geo.WGS84Coordinates;

/**
 * This class is a {@link StringConverterAndChecker} for {@link WGS84Coordinates}.
 */
public class WGS84CoordinatesStringConverter extends StringConverterAndChecker<WGS84Coordinates> {
  
  private static WGS84CoordinatesStringConverter instance;
  private WGS84CoordinatesFormatType formatType;
  
  public WGS84CoordinatesStringConverter(WGS84CoordinatesFormatType formatType) {
    this.formatType = formatType;
  }

  /**
   * Get an instance of this WGS84CoordinatesStringConverter.
   * 
   * @return an instance of this WGS84CoordinatesStringConverter.
   */
  public static WGS84CoordinatesStringConverter getInstance() {
    if (instance == null) {
      instance = new WGS84CoordinatesStringConverter(WGS84CoordinatesFormatType.DEGREES_MINUTES_SECONDS);
    }
    
    return instance;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(String string) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(WGS84Coordinates coordinates) {
    if (coordinates != null) {
      switch (formatType) {
      case DECIMAL:
        return coordinates.getLatitude() + ", " + coordinates.getLongitude();
        
      case DEGREES_MINUTES_SECONDS:
        return latToString(coordinates.getLatitude()) + " " + lonToString(coordinates.getLongitude());
        
      default:
        throw new RuntimeException("Unknown format");
      }
    } else {
      return null;
    }
  }
  
  /**
   * Get a string representaion of a latitude.
   * 
   * @param lat a latitude value.
   * @return the string representation of {@code lat}.
   */
  public static String latToString(double lat) {
    if (lat >= 0) {
      return latlonToString(lat, Directions.N.toString());
    } else {
      return latlonToString(-lat, Directions.S.toString());
    }
  }
  
  /**
   * Get a string representaion of a longitude.
   * 
   * @param lon a longitude value.
   * @return the string representation of {@code lon}.
   */
  public static String lonToString(double lon) {
      if (lon >= 0) {
          return latlonToString(lon, Directions.E.toString());
      } else {
          return latlonToString(-lon, Directions.W.toString());
      }
  }
  
  /**
   * Get a string representation for a latitude or longitude.
   * 
   * @param latlon the latitude or longitude value.
   * @param direction a {@code Direction}
   * @return the string representation for a latitude or longitude.
   */
  private static String latlonToString(final double latlon, final String direction) {
      final int degrees = (int) Math.floor(latlon);
      final double minutes = (latlon - degrees) * 60.0;
      final double seconds = (minutes - (int) Math.floor(minutes)) * 60.0;
      return String.format("%s %2d° %2d' %4.2f\"", direction, degrees, (int) Math.floor(minutes), seconds);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public WGS84Coordinates fromString(String string) {
    if (string != null) {
      switch (formatType) {
      case DECIMAL:
        String[] latLongString = string.split(",");
        double latitude = Double.parseDouble(latLongString[0]);
        double longitude = Double.parseDouble(latLongString[1]);
        return new WGS84Coordinates(latitude, longitude);
        
      case DEGREES_MINUTES_SECONDS:
        throw new RuntimeException("Unknown format");
        
      default:
        throw new RuntimeException("Unknown format");
      }
    } else {
      return null;
    }
  }
  
  /**
   * Values for the directions North, South, East and West.
   */
  private static enum Directions {
      N,
      S,
      E,
      W
  }

}
