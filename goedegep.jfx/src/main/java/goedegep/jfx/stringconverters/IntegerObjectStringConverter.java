package goedegep.jfx.stringconverters;

import javafx.util.converter.IntegerStringConverter;

/**
 * This class is a {@link StringConverterAndChecker} for Integer type.
 */
public class IntegerObjectStringConverter extends StringConverterAndChecker<Integer> {
  private static IntegerStringConverter integerStringConverter = new IntegerStringConverter();

  @Override
  public String toString(Integer object) {
    if (object != null) {
      return integerStringConverter.toString(object);
    } else {
      return null;
    }
  }

  @Override
  public Integer fromString(String string) {
    Integer value = null;
    
    try {
    value = integerStringConverter.fromString(string);
    } catch (NumberFormatException e) { //NOPMD
      // no action.
    }
    
    return value;
  }

  @Override
  public boolean isValid(String string) {
    if (string == null) {
      return true;
    } else {
      try {
        fromString(string);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  }

}
