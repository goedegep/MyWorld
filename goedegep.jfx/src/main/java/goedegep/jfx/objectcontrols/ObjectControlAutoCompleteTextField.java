package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import goedegep.jfx.controls.AutoCompleteTextField;
import goedegep.jfx.stringconverters.AnyTypeStringConverter;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tooltip;

public class ObjectControlAutoCompleteTextField<T> extends AutoCompleteTextField implements ObjectControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlAutoCompleteTextField.class.getName());
  
  private StringConverterAndChecker<T> stringConverterAndChecker = null;
  
  
  
//  /**
//   * Indication of whether the control is optional (if true) or mandatory.
//   */
//  private BooleanProperty ocOptionalProperty = new SimpleBooleanProperty(false);
  
  private boolean optional;
  
//  /**
//   * Indication of whether the control is filled-in or not.
//   */
//  private BooleanProperty ocFilledInProperty = new SimpleBooleanProperty(true);
  
  private boolean filledIn;
  
//  /**
//   * Indication of whether the control has a valid value or not.
//   */
//  private BooleanProperty ocValidProperty = new SimpleBooleanProperty(true);
  
  private boolean valid;
  
//  /**
//   * The current value.
//   */
//  private ObjectProperty<T> ocValueProperty = new SimpleObjectProperty<>();
  
  private T value;
  
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
    
  /**
   * Constructor for using an object as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   * @param dummy a dummy parameter, to have a different signature than TextFieldObjectInput(String text, double width, boolean isOptional, String toolTipText). (I know it's bad)
   */
  public ObjectControlAutoCompleteTextField(T initialValue, double width, boolean isOptional, String toolTipText, boolean dummy) {
    this((StringConverterAndChecker<T>) null, (String) null, width, isOptional, toolTipText);
    
    setText(objectToString(initialValue));
  }
  
  /**
   * Constructor for using an object as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlAutoCompleteTextField(StringConverterAndChecker<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
    this(stringConverter, (String) null, width, isOptional, toolTipText);

    setText(objectToString(initialValue));
  }

  
  /**
   * Constructor for using a text (representing an object value) as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlAutoCompleteTextField(String text, double width, boolean isOptional, String toolTipText) {
    this(null, text, width, isOptional, toolTipText);
  }
  
  /**
   * Constructor for using a text (representing an object value) as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlAutoCompleteTextField(StringConverterAndChecker<T> stringConverter, String text, double width, boolean isOptional, String toolTipText) {
    super();
    setText(text);
    
    if (stringConverter != null) {
      this.stringConverterAndChecker = stringConverter;
    } else {
      this.stringConverterAndChecker = new AnyTypeStringConverter<T>();
    }
    
    setMinWidth(width);
    
    this.optional = isOptional;
//    ocOptionalProperty.set(isOptional);
    
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    
    textProperty().addListener(new ChangeListener<String>() {
      public void changed(final ObservableValue<? extends String> observableValue, final String oldValue, final String newValue) {
        ociHandleNewUserInput();
      }
    });
    
    focusedProperty().addListener(this::handleFocusChanged);
    ociHandleNewUserInput();
  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocOptionalProperty() {
//    return ocOptionalProperty;
//  }
  
//  private void handleNewInput() {
//    LOGGER.severe("=>");
//    if (isEnteredDataValid(null)) {
//      LOGGER.severe("Data is valid");
//      ocValidProperty.set(true);
//      setStyle("-fx-text-fill: black;");
//    } else {
//      LOGGER.severe("Data NOT valid");
//      if (ocIsOptional()  &&  !ocIsFilledIn()) {
//        LOGGER.severe("Optional and not filled in");
//        ocValidProperty.set(true);
//      } else {
//        ocValidProperty.set(false);
//      }
//      setStyle("-fx-text-fill: red;");
//    }
//    
//    ocValueProperty.setValue(determineValue());
//    LOGGER.severe("objectValueProperty: " + ocValueProperty);
//    ocFilledInProperty.setValue(isFilledIn());
//    LOGGER.severe("isFilledInProperty: " + ocFilledInProperty);
//    
//    notifyListeners();
//  }
  
  private void handleFocusChanged(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
    LOGGER.info("=> newValue=" + newValue);
    if (!newValue) {
      T objectValue = stringToObject(getText());
      if (objectValue != null) {
        setText(objectToString(objectValue));
      }
    }
  }

  @Override
  public boolean ociDetermineFilledIn() {
    if (getText() == null  ||  getText().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Check that entered data is valid.
   * <p>
   * This method shall return true if the entered text represents a valid object, false otherwise.
   * This implementation always returns true. So this method has to be overwritten if this is not always the case.
   * 
   * @param buf A StringBuilder to append error message to.
   * @return true if the entered data is valid, false otherwise.
   */
  protected boolean isEnteredDataValid(StringBuilder buf) {
    boolean valid = false;
    
    try {
      return stringConverterAndChecker.fromString(getText()) != null;
    } catch (Exception e) {
      // Seems text is not valid
    }
    
    return valid;
  }

  @Override
  public T ociDetermineValue() {
    if (getText() == null) {
      return null;
    }
    
    T object = (T) stringToObject(getText());
    return object;
  }
  
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    return objectToString(ocGetValue());
  }

  @Override
  public void ocSetValue(T objectValue) {
    setText(objectToString(objectValue));
  }

//  @Override
//  public ObjectProperty<T> ocValueProperty() {
//    return ocValueProperty;
//  }
  
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
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocValidProperty() {
//    return ocValidProperty;
//  }
  
//  @Override
//  public BooleanProperty ocFilledInProperty() {
//    return ocFilledInProperty;
//  }
  
  public void setOptions(List<T> options) {
    SortedSet<String> entries = getEntries();
    
    entries.clear();
    for (T option: options) {
      entries.add(objectToString(option));
    }
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<InvalidationListener> ociGetInvalidationListeners() {
    return invalidationListeners;
  }

  @Override
  public void ociSetErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociRedrawValue() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetValue(T value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetValid(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetFilledIn(boolean filledIn) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean ocIsOptional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T ocGetValue() {
    // TODO Auto-generated method stub
    return null;
  }

}
