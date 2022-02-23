package goedegep.jfx.eobjecttable.objectstringconverters;

import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * This class is a {@link StringConverter} for any data type.
 * <p>
 * The toString method simply returns toString() on the provided object.<br/>
 * The fromString method just return null. So actually it is only half a StringConverter.
 */
public class IntegerObjectStringConverter extends StringConverter<Object> {
  private static IntegerStringConverter integerStringConverter = new IntegerStringConverter();

  @Override
  public String toString(Object object) {
    if (object != null) {
      return integerStringConverter.toString((Integer) object);
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String string) {
    return integerStringConverter.fromString(string);
  }

}
