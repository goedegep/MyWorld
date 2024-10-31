package goedegep.util.text;

/**
 * Interface used by the LookAheadComboBoxEditor (which in turn is used by the LookAheadComboBox).
 */
public interface TextLookAhead {
  /**
   * Get the object which starts with the 'key'.
   * @param key The string with which the returned object shall start.
   * @return The first object which starts with the 'key', or null if none of the objects starts with the 'key'.
   */
  Object doLookAhead(String key);
}