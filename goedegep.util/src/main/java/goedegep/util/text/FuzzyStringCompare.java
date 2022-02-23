package goedegep.util.text;

import java.util.logging.Logger;

public class FuzzyStringCompare {
  private static final Logger     LOGGER = Logger.getLogger(FuzzyStringCompare.class.getName());

  public static boolean fuzzyStringCompare(String s1, String s2) {
    if (s1.equalsIgnoreCase(s2)) {
      return true;
    }
    
    String fuzzy1 = replaceAmpersandInString(s1);
    String fuzzy2 = replaceAmpersandInString(s2);
    if (fuzzy1.equalsIgnoreCase(fuzzy2)) {
      LOGGER.fine("Fuzzy match found in comparing: " + s1 + " with " + s2);
      return true;
    }
    
    fuzzy1 = removePunctuationChars(s1);
    fuzzy2 = removePunctuationChars(s2);
    if (fuzzy1.equalsIgnoreCase(fuzzy2)) {
      LOGGER.fine("Fuzzy match found in comparing: " + s1 + " with " + s2);
      return true;
    }
    
    return false;
  }

  private static String removePunctuationChars(String s) {
    if (s.contentEquals("\"Heroes\"")){
      LOGGER.severe("=> s=" + s);
    }
    char[] chars = s.toCharArray();
    StringBuilder buf = new StringBuilder();
    for (char c: chars) {
      if (!(c == '\"')) {
        buf.append(c);
      }
    }

    if (s.contentEquals("\"Heroes\"")){
      LOGGER.severe("<= " + buf.toString());
    }
    return buf.toString();
  }

  private static String replaceAmpersandInString(String s1) {
    return s1.replaceAll("&", "and");
  }
 }
