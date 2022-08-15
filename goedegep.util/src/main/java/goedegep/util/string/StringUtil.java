package goedegep.util.string;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringUtil {
  
  /**
   * Private constructor, so this class cannot be instantiated.
   */
  private StringUtil() {
    
  }
  
  /**
   * Create a single String from a Collection of Strings. The values from the collection are separated by a comma
   * followed by a space (', ').<br/>
   * 
   * @param strings The Collection of Strings for which a single string is to be created.
   * @return a String consisting of all values of <code>strings</code>, separated by ', '.
   */
  public static String stringCollectionToCommaSeparatedStrings(Collection<String> strings) {
    return stringCollectionToSeparatorSeparatedStrings(strings, ", ");
  }
  
  /**
   * Create a single String from a Collection of Strings. The values from the collection are separated by a semicolon
   * followed by a space ('; ').<br/>
   * 
   * @param strings The Collection of Strings for which a single string is to be created.
   * @return a String consisting of all values of <code>strings</code>, separated by '; '.
   */
  public static String stringCollectionToSemicolonSeparatedStrings(Collection<String> strings) {
    return stringCollectionToSeparatorSeparatedStrings(strings, "; ");
  }
  
  /**
   * Create a single String from a Collection of Strings. The values from the collection are separated by the
   * specified separator.<br/>
   * Note that no space is added after the <code>separator</code>. So if you want the values separated by a comma,
   * followed by a space, you have to specify ", " as separator.
   * 
   * @param strings The Collection of Strings for which a single string is to be created.
   * @param separator The separator String, which will be placed between the values of the <code>string</code>.
   * @return a String consisting of all values of <code>strings</code>, separated by the <code>separator</code>.
   */
  public static String stringCollectionToSeparatorSeparatedStrings(Collection<String> strings, String separator) {
    if (strings == null) {
      return null;
    }
    
    StringBuilder buf = new StringBuilder();
    
    boolean first = true;
    for (String value: strings) {
      if (first) {
        first = false;
      } else {
        buf.append(separator);
      }
      buf.append(value);
    }
    
    return buf.toString();
  }
  
  /**
   * Convert a comma separated list of strings into a List of String values.<br/>
   * The String values will not contain any leading or trailing spaces (they are trimmed).
   * 
   * @param commaSeparatedValues String values separated by commas.
   * @return a List with the String values
   */
  public static List<String> commaSeparatedValuesToListOfValues(String commaSeparatedValues) {
    return separatorSeparatedValuesToListOfValues(commaSeparatedValues, ",");
  }
  
  /**
   * Convert a semicolon separated list of strings into a List of String values.<br/>
   * The String values will not contain any leading or trailing spaces (they are trimmed).
   * 
   * @param semicolonSeparatedValues String values separated by semicolons.
   * @return a List with the String values
   */
  public static List<String> semicolonSeparatedValuesToListOfValues(String semicolonSeparatedValues) {
    return separatorSeparatedValuesToListOfValues(semicolonSeparatedValues, ";");
  }
  
  /**
   * Convert a separator separated list of strings into a List of String values.<br/>
   * The String values will not contain any leading or trailing spaces (they are trimmed). Therefore, if the list is
   * separated by ', ', you just have to specify ',' for the separator.
   * 
   * @param separatorSeparatedValues String values separated by the <code>separator</code>.
   * @param separator the String which separates the value in the <code>separatorSeparatedValues</code>.
   * @return a List with the String values
   */
  public static List<String> separatorSeparatedValuesToListOfValues(String separatorSeparatedValues, String separator) {
    List<String> values = new ArrayList<>();
    
    if (separatorSeparatedValues != null) {
      for(String value: separatorSeparatedValues.split(separator)) {
        values.add(value.trim());
      }
    }
    
    return values;
  }

  
  /**
   * Create a single String from a Collection of Objects. The values, represented as String by calling toString(), from the collection are separated by a comma
   * followed by a space (', ').<br/>
   * 
   * @param objects The Collection of Objects for which a single string is to be created.
   * @return a String consisting of all values of <code>objects</code>, separated by ', '.
   */
  public static String objectCollectionToCommaSeparatedStrings(Collection<? extends Object> objects) {
    return objectCollectionToSeparatorSeparatedStrings(objects, ", ");
  }
  
  /**
   * Create a single String from a Collection of Objects. The values, represented as String by calling toString(), from the collection are separated by the
   * specified separator.<br/>
   * Note that no space is added after the <code>separator</code>. So if you want the values separated by a comma,
   * followed by a space, you have to specify ", " as separator.
   * 
   * @param objects The Collection of Objects for which a single string is to be created.
   * @param separator The separator String, which will be placed between the values of the <code>objects</code>.
   * @return a String consisting of all values of <code>objects</code>, separated by the <code>separator</code>.
   */
  public static String objectCollectionToSeparatorSeparatedStrings(Collection<? extends Object> objects, String separator) {
    StringBuilder buf = new StringBuilder();
    
    boolean first = true;
    for (Object object: objects) {
      if (first) {
        first = false;
      } else {
        buf.append(separator);
      }
      buf.append(object.toString());
    }
    
    return buf.toString();
  }

  /**
   * Remove a specific starting part from a string.
   * 
   * @param prefix the starting part to be removed.
   * @param string the string from which the prefix is to be removed.
   * @return the string without the prefix
   */
  public static String removePrefix(String prefix, String string) {
    if (string.startsWith(prefix)) {
      return string.substring(prefix.length());
    } else {
      throw new RuntimeException("'" + string + "' doesn't start with '" + prefix + "'");
    }
  }

  public static String removeQuotes(String string) {
    if (string == null) {
      return null;
    }
    
    if (string.startsWith("'")  &&  string.endsWith("'")) {
      return string.substring(1, string.length() - 1);
    }
    
    if (string.startsWith("\"")  &&  string.endsWith("\"")) {
      return string.substring(1, string.length() - 1);
    }
    
    return string;
  }

  public static String CapitalizeWords(String string) {
    StringBuilder buf = new StringBuilder();
    
    boolean first = true;
    for (String word: string.split(" ")) {
      if (first) {
        first = false;
      } else {
        buf.append(" ");
      }
      String firstChar = word.substring(0, 1);
      buf.append(firstChar.toUpperCase());
      
      String remainderOfWord = word.substring(1, word.length());
      buf.append(remainderOfWord);
    }
    
    return buf.toString();
  }
}
