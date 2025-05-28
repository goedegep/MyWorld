package goedegep.jfx.stringconverterandchecker;

import java.util.logging.Logger;

import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for any data type.
 * <p>
 * The {@code toString()} method simply returns {@code toString()} on the provided object.<br/>
 * The {@code fromString()} method just returns null and {@code isvalid()} returns false. So actually it is only a partial implementation.
 */
public class AnyTypeStringConverterAndChecker<T extends Object> extends StringConverterAndChecker<T> {
  private static final Logger         LOGGER = Logger.getLogger(AnyTypeStringConverterAndChecker.class.getName());

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
    if (string.isEmpty()) {  // An empty string is handled as null value.
      return null;
    } else {

      try {
        return (T) string;
      } catch (RuntimeException e) {
        return null;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(String string) {
    return false;
  }

}
