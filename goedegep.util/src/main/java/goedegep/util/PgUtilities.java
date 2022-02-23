package goedegep.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains a number of utility methods.
 */
public class PgUtilities {
  private static final Logger LOGGER = Logger.getLogger(PgUtilities.class.getName());
  
  static {
    LOGGER.setLevel(Level.SEVERE);
  }
  
  /**
   * Compare two objects for equality.
   * <p>
   * {@link Object#equals} cannot be called on a null object. This method allows null for both parameters.
   * 
   * @param object1 the object (or null) to be compared to object2.
   * @param object2 the object (or null) to be compared to obejct1.
   * @return 
   * <ul>
   * <li>
   * true, if both objects are null.
   * </li>
   * <li>
   * false, if one object is null and the other isn't null.
   * </li>
   * <li>
   * object1.equals(object2) if both objects aren't null.
   * </li>
   * </ul>
   */
  public static boolean equals(Object object1, Object object2) {
    if ((object1 == null)  &&  (object2 != null)  ||
        (object1 != null)  &&  (object2 == null)) {
      return false;
    }
    
    if ((object1 == null)  &&  (object2 == null)) {
      return true;
    }
    
    return object1.equals(object2);
  }

}