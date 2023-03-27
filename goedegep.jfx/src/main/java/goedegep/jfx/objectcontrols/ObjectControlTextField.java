package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.stringconverters.AnyTypeStringConverter;
import javafx.beans.InvalidationListener;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;

/**
 * This class forms the basis for classes implementing the {@link ObjectControl} interface, using a TextField control.
 * <p>
 * Information about extending this class:
 * <ul>
 * <li>
 * <b>Object to String conversion</b><br/>
 * To convert an object to String and vice versa there are the following options:
 * <ul>
 * <li>
 * Provide a StringConverter via the constructor.
 * </li>
 * <li>
 * Overwrite the methods {@link #objectToString} and {@link #stringToObject}.</br>
 * In this case any StringConverter is ignored.
 * </li>
 * </ul>
 * </li>
 * </ul>
 *
 * @param <T> The object type represented by this control.
 */
public class ObjectControlTextField<T> extends TextField implements ObjectControl<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlTextField.class.getName());
  
  
  /**
   * Indication of whether the control is optional (if true) or mandatory.
   */
  private boolean optional;
  
  /**
   * Indication of whether the control is filled-in or not.
   */
  private boolean filledIn;
  
  /**
   * Indication of whether the control has a valid value or not.
   */
  private boolean valid = true;
  
  /**
   * The current value.
   */
  private T value;
  
  private StringConverter<T> stringConverter = null;
  
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  /**
   * Error information text
   */
  protected String errorText = null;
  
    
  /**
   * Constructor for using an object as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlTextField(T initialValue, double width, boolean isOptional, String toolTipText) {
    this(null, initialValue, width, isOptional, toolTipText);
  }
  
  /**
   * Constructor for using an object as initial value.
   * 
   * @param stringConverter a StringConverterAndChecker for conversion between object of type T and String.
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlTextField(StringConverter<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
    if (stringConverter != null) {
      this.stringConverter = stringConverter;
    } else {
      this.stringConverter = new AnyTypeStringConverter<T>();
    }
    
    setMinWidth(width);
    
    optional = isOptional;
    
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    
    textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput());
    
    focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      if (newValue)
        ociRedrawValue();
    });

    setText(objectToString(initialValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(T objectValue) {
    String text = objectToString(objectValue);
    setText(text);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return getText() != null  &&  !getText().isEmpty();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public T ociDetermineValue() {
    T value = stringToObject(getText().trim());
    
    return value;
  }
  
  /**
   * {@inheritDoc}
   * If a non valid value is entered, set the text to red, else black.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
    if (valid) {
      setStyle("-fx-text-fill: black;");
    } else {
      setStyle("-fx-text-fill: red;");
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void ociRedrawValue() {
//    T value = ocValueProperty.get();
    if (value != null) {
      setText(objectToString(value));
    } else {
      setText(null);
    }
  }
  
//  /**
//   * Check that entered data is valid.
//   * <p>
//   * This method shall return true if the entered text represents a valid object, false otherwise.
//   * This implementation always returns true. So this method has to be overwritten if this is not always the case.
//   * 
//   * @return true if the entered data is valid, false otherwise.
//   */
//  protected boolean isEnteredDataValid() {
//    boolean valid = false;
//    
//    try {
//      stringConverterAndChecker.fromString(getText());
//      valid = true;
//    } catch (Exception e) {
//      // Seems text is not valid
//    }
//    
//    return valid;
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    String text = objectToString(ocGetValue());
    return text;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<InvalidationListener> ociGetInvalidationListeners() {
    return invalidationListeners;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetErrorText() {
      return errorText;
  }
  
  /**
   * Create an object based on its String representation.
   * <p>
   * It is mandatory to overwrite this method.
   * 
   * @param valueAsString The String representation of an object.
   */
  protected T stringToObject(String valueAsString) {
//    if (stringConverterAndChecker.isValid(valueAsString)) {
      return (T) stringConverter.fromString(valueAsString);
//    } else {
//      return null;
//    }
  }
  
  /**
   * Convert an object value to its String representation
   * <p>
   * It is mandatory to overwrite this method.
   * 
   * @param value The object to be converted to its String representation.
   */
  protected String objectToString(T value) {
    return stringConverter.toString(value);
  }

  @Override
  public boolean ocIsOptional() {
    return optional;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ocIsFilledIn() {
    return filledIn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ocIsValid() {
    return valid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T ocGetValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ociSetValue(T value) {
    setText(objectToString(value));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ociSetValid(boolean valid) {
    this.valid = valid;    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ociSetFilledIn(boolean filledIn) {
    this.filledIn = filledIn;
    
  }
}
