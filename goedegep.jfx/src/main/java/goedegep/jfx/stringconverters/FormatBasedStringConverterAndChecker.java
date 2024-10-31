package goedegep.jfx.stringconverters;

import java.text.Format;
import java.text.ParseException;

/**
 * This class is a {@link StringConverterAndChecker} based on a {@link Format}.
 */
public class FormatBasedStringConverterAndChecker<T extends Object> extends StringConverterAndChecker<T> {
  private Format format;
  
  /**
   * Constructor
   * 
   * @param format the <code>Format</code> to perform the conversions.
   */
  public FormatBasedStringConverterAndChecker(Format format) {
    this.format = format;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(T object) {
    if (object != null) {
      return format.format(object);
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public T fromString(String string) {
    // If the specified value is null or zero-length, return null
    if (string == null) {
      return null;
    }

    string = string.trim();

    if (string.isEmpty()) {
      return null;
    }
    
    try {
      // Perform the requested parsing
      return (T) format.parseObject(string);
    } catch (ParseException ex) { //NOPMD
      // No action
    }

    return null;
  }

  @Override
  public boolean isValid(String string) {
    if (string == null  ||  string.isEmpty()) {
      return true;
    }

    try {
      format.parseObject(string);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }
  
}
