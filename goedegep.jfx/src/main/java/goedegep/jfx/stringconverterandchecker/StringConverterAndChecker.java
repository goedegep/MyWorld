package goedegep.jfx.stringconverterandchecker;

import javafx.util.StringConverter;

/**
 * This class extends the {@link StringConverter} class with a method to check whether the string is valid.
 * <p>
 * The method fromString() just returns null if the string cannot be parsed. But null is also returned if the string
 * is null or empty. So to check whether the string is valid, you would have to check whether the string is either null or empty,
 * or that fromString() return a non-null value. The method isValid() in this class combines these two steps.
 *
 * @param <T>
 */
public abstract class StringConverterAndChecker<T> extends StringConverter<T> {

  /**
  * Checks whether the string provided can be converted into an object defined by the specific converter.
  *
  * @param string the {@code String} to check
  * @return true if the {@code string} is null, or if it can be converted to an object defined by the specific converter.
  */
  public abstract boolean isValid(String string);

}
