package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.stringconverters.AnyTypeStringConverter;
import javafx.scene.control.TextField;
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
public class ObjectControlTextField<T> extends ObjectControlAbstract<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlTextField.class.getName());
  
  
  
  private StringConverter<T> stringConverter = null;
  
  private TextField textField = null;
  
    
  /**
   * Constructor for using an object as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlTextField(CustomizationFx customization, T initialValue, double width, boolean isOptional, String toolTipText) {
    this(customization, null, initialValue, width, isOptional, toolTipText);
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
  public ObjectControlTextField(CustomizationFx customization, StringConverter<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
    super(isOptional);
    
    textField = customization.getComponentFactoryFx().createTextField(width, toolTipText);
    
    if (stringConverter != null) {
      this.stringConverter = stringConverter;
    } else {
      this.stringConverter = new AnyTypeStringConverter<T>();
    }
    
    textField.textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput(textField));
    
    textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!newValue)
        ociRedrawValue();
    });

    // The initial value of the textField is null. Again setting it to null doesn't trigger the listener.
    // So if the initial value isn't null, set the value (triggering the listener, leading to a call to ociHandleNewUserInput().
    // Else, just call ociHandleNewUserInput().
//    if (initialValue != null) {
      ocSetValue(initialValue);
//    } else {
//      ociHandleNewUserInput();
//    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TextField ocGetControl() {
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(T objectValue) {
    String text = objectToString(objectValue);
    referenceValue = stringToObject(text);
    textField.setText(text);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return textField.getText() != null  &&  !textField.getText().isEmpty();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public T ociDetermineValue(Object source) {
    T value = stringToObject(textField.getText().trim());
    
    return value;
  }
  
  /**
   * {@inheritDoc}
   * If a non valid value is entered, set the text to red, else black.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
    if (valid) {
      textField.setStyle("-fx-text-fill: black;");
    } else {
      textField.setStyle("-fx-text-fill: red;");
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void ociRedrawValue() {
//    T value = ocValueProperty.get();
    if (value != null) {
      textField.setText(objectToString(value));
    } else {
      textField.setText(null);
    }
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    String text = objectToString(ocGetValue());
    return text;
  }
  
  /**
   * Create an object based on its String representation.
   * <p>
   * It is mandatory to overwrite this method.
   * 
   * @param valueAsString The String representation of an object.
   */
  protected T stringToObject(String valueAsString) {
    return (T) stringConverter.fromString(valueAsString);
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
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("ObjectControl type=TextField");
    buf.append(", id=").append(ocGetId() != null ? ocGetId() : "<null>");
    buf.append(", value=").append(value != null ? value : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? referenceValue : "<null>");
    
    return buf.toString();
  }
}
