package goedegep.jfx.stringconverterandchecker;

import javafx.util.converter.IntegerStringConverter;

/**
 * This class is a {@link StringConverterAndChecker} for the Integer type.
 */
public class IntegerStringConverterAndChecker extends StringConverterAndChecker<Integer> {
  private static IntegerStringConverter integerStringConverter = new IntegerStringConverter();
  
  private static IntegerStringConverterAndChecker instance = null;
  
  /**
   * Get the instance of the {@code IntegerStringConverterAndChecker}.
   * 
   * @return the instance of the {@code IntegerStringConverterAndChecker}.
   */
  public static IntegerStringConverterAndChecker getInstance() {
    if (instance == null) {
      instance = new IntegerStringConverterAndChecker();
    }
    
    return instance;
  }
  
  /**
   * Private constructor.
   */
  private IntegerStringConverterAndChecker() {
  }

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
