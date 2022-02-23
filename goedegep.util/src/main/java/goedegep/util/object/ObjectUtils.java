package goedegep.util.object;

import java.util.Collection;

public class ObjectUtils {

  /**
   * Private constructor, so this class cannot be instantiated.
   */
  private ObjectUtils() {

  }

  /**
   * Create a single String from a Collection of Objects. The values from the collection are separated by a comma
   * followed by a space (', ').<br/>
   * The values of the objects are turned into Strings by calling toString() on them.
   * 
   * @param objects The Collection of Objects for which a single string is to be created.
   * @return a String consisting of all values of <code>objects</code>, separated by ', '.
   */
  public static String objectCollectionToCommaSeparatedStrings(Collection<? extends Object> objects) {
    return objectCollectionToSeparatorSeparatedStrings(objects, ", ");
  }

  /**
   * Create a single String from a Collection of Objects. The values from the collection are separated by a semicolon
   * followed by a space ('; ').<br/>
   * The values of the objects are turned into Strings by calling toString() on them.
   * 
   * @param objects The Collection of Object for which a single string is to be created.
   * @return a String consisting of all values of <code>objects</code>, separated by '; '.
   */
  public static String objectsCollectionToSemicolonSeparatedStrings(Collection<? extends Object> objects) {
    return objectCollectionToSeparatorSeparatedStrings(objects, "; ");
  }

  /**
   * Create a single String from a Collection of Objects. The values from the collection are separated by the
   * specified separator.<br/>
   * Note that no space is added after the <code>separator</code>. So if you want the values separated by a comma,
   * followed by a space, you have to specify ", " as separator.<br/>
   * The values of the objects are turned into Strings by calling toString() on them.
   * 
   * @param objects The Collection of Objects for which a single string is to be created.
   * @param separator The separator String, which will be placed between the values of the objects.
   * @return a String consisting of all values of <code>objects</code>, separated by the <code>separator</code>.
   */
  public static String objectCollectionToSeparatorSeparatedStrings(Collection<? extends Object> objects, String separator) {
    StringBuilder buf = new StringBuilder();

    boolean first = true;
    for (Object value: objects) {
      if (first) {
        first = false;
      } else {
        buf.append(separator);
      }
      buf.append(value.toString());
    }

    return buf.toString();
  }

}
