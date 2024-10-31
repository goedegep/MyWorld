package goedegep.jfx.eobjecttable.objectstringconverters;

import java.text.Format;
import java.text.ParseException;

import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} based on a {@link Format}.
 * <p>
 * The toString method simply returns toString() on the provided object.<br/>
 * The fromString method just return null. So actually it is only half a StringConverter.
 */
public class FormatBasedObjectStringConverter extends StringConverter<Object> {
  private Format format;
  
  public FormatBasedObjectStringConverter(Format format) {
    this.format = format;
  }

  @Override
  public String toString(Object object) {
    if (object != null) {
      return format.format(object);
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String string) {
    try {
      // If the specified value is null or zero-length, return null
      if (string == null) {
          return null;
      }

      string = string.trim();

      if (string.isEmpty()) {
          return null;
      }

      // Perform the requested parsing
      return format.parseObject(string);
  } catch (ParseException ex) {
      throw new RuntimeException(ex);
  }
    
//    T result = null;
//    
//    try {
//      result = (T) format.parseObject(string);
//    } catch (ParseException e) {
//      // No action, just return null.
//    }
//    
//    return result;
  }

}
