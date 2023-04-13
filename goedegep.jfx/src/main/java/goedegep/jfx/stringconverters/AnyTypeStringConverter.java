package goedegep.jfx.stringconverters;

import java.util.logging.Logger;

import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for any data type.
 * <p>
 * The toString method simply returns toString() on the provided object.<br/>
 * The fromString method just return null. So actually it is only half a StringConverter.
 */
public class AnyTypeStringConverter<T extends Object> extends StringConverterAndChecker<T> {
  private static final Logger         LOGGER = Logger.getLogger(AnyTypeStringConverter.class.getName());

  @Override
  public String toString(T object) {
    if (object != null) {
      String result = object.toString();
      LOGGER.info("<= " + result);
      return result;
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
    try {
      return (T) string;
    } catch (RuntimeException e) {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(String string) {
    return true;
  }

}
