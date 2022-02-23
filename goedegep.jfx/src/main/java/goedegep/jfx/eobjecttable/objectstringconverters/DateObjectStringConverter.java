package goedegep.jfx.eobjecttable.objectstringconverters;

import java.util.Date;

import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

/**
 * This class is a {@link StringConverter} for a Date.
 */
public class DateObjectStringConverter extends StringConverter<Object> {
  private static DateStringConverter dateStringConverter = new DateStringConverter("dd-MM-yyyy");

  @Override
  public String toString(Object object) {
    if (object != null) {
      return dateStringConverter.toString((Date) object);
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String string) {
    return dateStringConverter.fromString(string);
  }

}
