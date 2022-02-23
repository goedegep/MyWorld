package goedegep.util.unit;

import goedegep.util.datetime.ClockTime;

/**
 * This class provides utility methods and constants related to units.
 * <p>
 * Currently supported is:
 * <ul>
 * <li>
 * Kilometers to miles conversion and vice versa.
 * </li>
 * <li>
 * Speed to tempo and vice versa
 * </li>
 * </ul>
 */
public class UnitUtils {
  public static final Double     KM_TO_MILES_FACTOR = 1.609344;
  
  /**
   * Convert miles to kilometers.
   * 
   * @param miles the number of miles to be converted to kilometers
   * @return the number kilometers corresponding to {@code miles}
   */
  public static double milesToKm(double miles) {
    return miles * KM_TO_MILES_FACTOR;
  }

  /**
   * Convert kilometers to miles.
   * 
   * @param kilometers the number of kilometers to be converted to miles.
   * @return the number of miles corresponding to {@code kilometers}
   */
  public static double kmToMiles(double kilometers) {
    return kilometers / KM_TO_MILES_FACTOR;
  }
  
  /**
   * Convert speed to tempo.
   * <p>
   * Speed can be both in km/h (in which case tempo is in time per km) or mile/h (in which case tempo is in time per mile).
   * 
   * @param speed the speed to be converted
   * @return the tempo corresponding to the {@code speed}
   */
  public static ClockTime speedToTempo(double speed) {
    return new ClockTime((long) (3600 / speed + 0.5));
  }
  
  /**
   * Convert tempo to speed.
   * <p>
   * Tempo can be both in time per km (in which case speed is in km/h) or time per mile (in which case speed is in mile/h).
   * 
   * @param tempo the tempo to be converted
   * @return the speed corresponding to the {@code tempo}
   */
  public static double tempoToSpeed(ClockTime tempo) {
    return 3600 / tempo.getTimeInSeconds();
  }

}
