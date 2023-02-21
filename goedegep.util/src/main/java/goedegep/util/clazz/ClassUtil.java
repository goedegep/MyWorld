package goedegep.util.clazz;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * This class provides utility methods for classes.
 */
public class ClassUtil {
  private static final Logger LOGGER = Logger.getLogger(ClassUtil.class.getName());
  private static String NEWLINE = System.getProperty("line.separator");
  
  /**
   * Private constructor, so this class cannot be instantiated.
   */
  private ClassUtil() {
    
  }
  
  /**
   * A static version of toString. It lists the values of all fields.
   * 
   * @return The values of all fields as a String.
   */
  public static String staticFieldsToString(Class<?> aClass) {
    LOGGER.info("=>");
    
    StringBuffer sb = new StringBuffer();
    sb.append("Static fields of class: ").append(aClass.getName()).append(NEWLINE);
    for (Field field: aClass.getFields()) {
      try {
        sb.append(field.getName());
        sb.append(": ");
        sb.append(field.get(null));
        sb.append(NEWLINE);
      } catch (IllegalArgumentException e) {
        // This should never happen, print stack and exit.
        e.printStackTrace();
        System.exit(-1);
      } catch (IllegalAccessException e) {
        // This should never happen, print stack and exit.
        e.printStackTrace();
        System.exit(-1);
      }
    }
        
    LOGGER.info("<= " + sb.toString());
    return sb.toString();
  }
}
