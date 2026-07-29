package goedegep.util.text;

import java.util.logging.Logger;

public class FuzzyStringCompare {
  private static final Logger     LOGGER = Logger.getLogger(FuzzyStringCompare.class.getName());

  /**
   * Perform a fuzzy String comparison.
   * <p>
   * If one of the strings is {@code null} and the other isn't, the result is {@code false}.<br/>
   * If both strings are {@code null} the result is {@code true}.<br/>
   * For an actual comparison:
   * <ul>
   * <li>The case is ignored</li>
   * </ul>
   * 
   * @param s1 A {@code String} to compare to {@code s2}.
   * @param s2 A {@code String} to compare to {@code s1}.
   * @return {@code true} if the strings are 'equal', {@code false} otherwise.
   */
  public static boolean fuzzyStringCompare(String s1, String s2) {
    if (s1 == null  &&  s2 != null) {
      return false;
    }
    
    if (s1 != null  &&  s2 == null) {
      return false;
    }
    
    if (s1 == null  &&  s2 != null) {
      return false;
    }
    
    String fuzzy1 = s1.toLowerCase();
    String fuzzy2 = s2.toLowerCase();
    
    if (s1.equalsIgnoreCase(s2)) {
      return true;
    }
    
    fuzzy1 = replaceAmpersandInString(s1);
    fuzzy2 = replaceAmpersandInString(s2);
    fuzzy1 = removePunctuationChars(s1);
    fuzzy2 = removePunctuationChars(s2);
    
    return fuzzy1.equals(fuzzy2);
  }

  /**
   * Remove punctuation characters from a {@code String}.
   * <p>
   * The following characters are removed: '"'.
   * 
   * @param s a {@code String}.
   * @return the string {@code s} from which punctuation characters are removed.
   */
  private static String removePunctuationChars(String s) {
    char[] chars = s.toCharArray();
    StringBuilder buf = new StringBuilder();
    
    for (char c: chars) {
      if (!(c == '\"')) {
        buf.append(c);
      }
    }

    return buf.toString();
  }

  /**
   * Replace an '&' by 'and'. The strings 'one & two' and 'one and two' are considered to be equal.
   * 
   * @param s1 A {@code String} in which the ampersand is to be replaced.
   * @return {@code s1} in which and ampersand is replaced.
   */
  private static String replaceAmpersandInString(String s1) {
    return s1.replaceAll("&", "and");
  }
 }
