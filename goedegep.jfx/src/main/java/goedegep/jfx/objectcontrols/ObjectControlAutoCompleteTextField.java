package goedegep.jfx.objectcontrols;

import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextField;
import goedegep.jfx.stringconverters.AnyTypeStringConverter;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tooltip;

public class ObjectControlAutoCompleteTextField<T> extends ObjectControlAbstract<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlAutoCompleteTextField.class.getName());
  
  private AutoCompleteTextField autoCompleteTextField = null;
  private StringConverterAndChecker<T> stringConverterAndChecker = null;
    
  /**
   * Constructor for using an object as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlAutoCompleteTextField(CustomizationFx customization, T initialValue, double width, boolean isOptional, String toolTipText) {
    this(customization, (StringConverterAndChecker<T>) null, null, width, isOptional, toolTipText);
  }
    
  /**
   * Constructor for using an object as initial value and setting the StringConverterAndChecker.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlAutoCompleteTextField(CustomizationFx customization, StringConverterAndChecker<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
    super(isOptional);
    
    if (stringConverter != null) {
      this.stringConverterAndChecker = stringConverter;
    } else {
      this.stringConverterAndChecker = new AnyTypeStringConverter<T>();
    }
    
    autoCompleteTextField = customization.getComponentFactoryFx().createAutoCompleteTextField();
    
    autoCompleteTextField.setMinWidth(width);
        
    if (toolTipText != null) {
      autoCompleteTextField.setTooltip(new Tooltip(toolTipText));
    }
    
    autoCompleteTextField.textProperty().addListener((o) -> ociHandleNewUserInput());
    
    autoCompleteTextField.focusedProperty().addListener(this::handleFocusChanged);
    autoCompleteTextField.setText(objectToString(initialValue));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public AutoCompleteTextField ocGetControl() {
    return autoCompleteTextField;
  }

  /**
   * On focus lost, redraw the text.
   * 
   * @param observableValue focus value.
   * @param oldValue old focus value
   * @param newValue new focus value
   */
  private void handleFocusChanged(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
    LOGGER.info("=> newValue=" + newValue);
    if (!newValue) {
      ociRedrawValue();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    if (autoCompleteTextField.getText() == null  ||  autoCompleteTextField.getText().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public T ociDetermineValue() {
    if (autoCompleteTextField.getText() == null) {
      return null;
    }
    
    T object = (T) stringToObject(autoCompleteTextField.getText());
    return object;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    return objectToString(ocGetValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(T objectValue) {
    referenceValue = objectValue;
    autoCompleteTextField.setText(objectToString(objectValue));
  }

  /**
   * Create an object based on its String representation.
   * <p>
   * It is mandatory to overwrite this method.
   * 
   * @param valueAsString The String representation of an object.
   */
  protected T stringToObject(String valueAsString) {
    if (stringConverterAndChecker.isValid(valueAsString)) {
      return (T) stringConverterAndChecker.fromString(valueAsString);
    } else {
      return null;
    }
  }
  
  /**
   * Convert an object value to its String representation
   * <p>
   * It is mandatory to overwrite this method.
   * 
   * @param value The object to be converted to its String representation.
   */
  protected String objectToString(T value) {
    return stringConverterAndChecker.toString(value);
  }
  
  /**
   * Set the possible values.
   * 
   * @param options the possible values.
   */
  public void setOptions(List<T> options) {
    SortedSet<String> entries = autoCompleteTextField.getEntries();
    
    entries.clear();
    for (T option: options) {
      entries.add(objectToString(option));
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
    if (valid) {
      autoCompleteTextField.setStyle("-fx-text-fill: black;");
    } else {
      autoCompleteTextField.setStyle("-fx-text-fill: red;");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ociRedrawValue() {
    autoCompleteTextField.setText(ocGetObjectValueAsFormattedText());    
  }

}
