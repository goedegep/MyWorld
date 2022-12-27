package goedegep.geo;

/**
 * This class provides utility methods for geo coordinates.
 * <p>
 * There are methods to:
 * <ul>
 * <li>Convert a double latitude or longitude to a degrees/minutes/seconds.</li>
 * </ul>
 *
 */
public class GeoUtil {
  
  /**
   * Directions North, South, East and West.
   */
  private static enum Direction {
      N,
      S,
      E,
      W
  }
  
  /**
   * Get a textual representation in degrees, minutes and seconds for a latitude.
   * 
   * @param latitude a double latitude value
   * @return the <code>latitude</code> in degrees, minutes and seconds.
   */
  public static String latToDegreesMinutesSecondsString(final double latitude) {
    Direction direction;
    double value;
    
    if (latitude >= 0) {
      direction = Direction.N;
      value = latitude;
    } else {
      direction = Direction.S;
      value = -latitude;
    }
    
    return direction.toString() + " " + doubleToDegreesMinutesSecondsString(value);
  }
  
  /**
   * Get a textual representation in degrees, minutes and seconds for a longitude.
   * 
   * @param longitude a double longitude value
   * @return the <code>longitude</code> in degrees, minutes and seconds.
   */
  public static String lonToDegreesMinutesSecondsString(final double longitude) {
    Direction direction;
    double value;
    
    if (longitude >= 0) {
      direction = Direction.E;
      value = longitude;
    } else {
      direction = Direction.W;
      value = -longitude;
    }
    
    return direction.toString() + " " + doubleToDegreesMinutesSecondsString(value);
  }
  
  /**
   * Get a textual representation in degrees, minutes and seconds for a double value.
   * 
   * @param value a double value.
   * @return the <code>value</code> in degrees, minutes and seconds.
   */
  private static String doubleToDegreesMinutesSecondsString(final double value) {
      final int degrees = (int) Math.floor(value);
      final double minutes = (value - degrees) * 60.0;
      final double seconds = (minutes - (int) Math.floor(minutes)) * 60.0;
      return String.format("%2d° %2d' %4.2f\"", degrees, (int) Math.floor(minutes), seconds);
  }
}
