package goedegep.jfx.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

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

public class AutoCompleteTextFieldObjectInput<T> extends AutoCompleteTextField implements ObjectControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(AutoCompleteTextFieldObjectInput.class.getName());
  
  private StringConverterAndChecker<T> stringConverterAndChecker = null;
  
  private BooleanProperty isValidProperty = new SimpleBooleanProperty(true);
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(true);
  private ObjectProperty<T> objectValueProperty = new SimpleObjectProperty<>();
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  /**
   * Indicates whether the control is optional (if true) or mandatory.
   */
  private boolean isOptional;
  
    
  /**
   * Constructor for using an object as initial value.
   * 
   * @param initialValue Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   * @param dummy a dummy parameter, to have a different signature than TextFieldObjectInput(String text, double width, boolean isOptional, String toolTipText). (I know it's bad)
   */
  public AutoCompleteTextFieldObjectInput(T initialValue, double width, boolean isOptional, String toolTipText, boolean dummy) {
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
  public AutoCompleteTextFieldObjectInput(StringConverterAndChecker<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
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
  public AutoCompleteTextFieldObjectInput(String text, double width, boolean isOptional, String toolTipText) {
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
  public AutoCompleteTextFieldObjectInput(StringConverterAndChecker<T> stringConverter, String text, double width, boolean isOptional, String toolTipText) {
    super();
    setText(text);
    
    if (stringConverter != null) {
      this.stringConverterAndChecker = stringConverter;
    } else {
      this.stringConverterAndChecker = new AnyTypeStringConverter<T>();
    }
    
    setMinWidth(width);
    
    this.isOptional = isOptional;
    
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    
    textProperty().addListener(new ChangeListener<String>() {
      public void changed(final ObservableValue<? extends String> observableValue, final String oldValue, final String newValue) {
        handleNewInput();
      }
    });
    
    focusedProperty().addListener(this::handleFocusChanged);
    handleNewInput();
  }
  
  private void handleNewInput() {
    LOGGER.severe("=>");
    if (isEnteredDataValid(null)) {
      LOGGER.severe("Data is valid");
      isValidProperty.set(true);
      setStyle("-fx-text-fill: black;");
    } else {
      LOGGER.severe("Data NOT valid");
      if (isOptional()  &&  !getIsFilledIn()) {
        LOGGER.severe("Optional and not filled in");
        isValidProperty.set(true);
      } else {
        isValidProperty.set(false);
      }
      setStyle("-fx-text-fill: red;");
    }
    
    objectValueProperty.setValue(getObjectValue());
    LOGGER.severe("objectValueProperty: " + objectValueProperty);
    isFilledInProperty.setValue(getIsFilledIn());
    LOGGER.severe("isFilledInProperty: " + isFilledInProperty);
    
    notifyListeners();
  }
  
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
  public boolean isOptional() {
    return isOptional;
  }

  @Override
  public boolean getIsFilledIn() {
    if (getText() == null  ||  getText().isEmpty()) {
      return false;
    } else {
      return (getText().length() != 0);
    }
  }

  @Override
  public boolean getIsValid(StringBuilder buf) {
    return isValidProperty.get();
    
//    if (isOptional()  &&  !isFilledIn()) {
//      return true;
//    }
//        
//    return isEnteredDataValid(buf);
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
  public T getObjectValue() {
    if (getText() == null) {
      return null;
    }
    
    T object = (T) stringToObject(getText());
    return object;
  }
  
  public String getObjectValueAsFormattedText()  {
    return objectToString(getObjectValue());
  }

  @Override
  public void setObjectValue(T objectValue) {
    setText(objectToString(objectValue));
  }

  @Override
  public ObjectProperty<T> objectValue() {
    return objectValueProperty;
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
   * {@inheritDoc}
   */
  @Override
  public BooleanProperty isValid() {
    return isValidProperty;
  }
  
  @Override
  public BooleanProperty isFilledIn() {
    return isFilledInProperty;
  }

  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);    
  }
  
  public void setOptions(List<T> options) {
    SortedSet<String> entries = getEntries();
    
    entries.clear();
    for (T option: options) {
      entries.add(objectToString(option));
    }
  }
  
  /**
   * Notify the <code>invalidationListeners</code> that something has changed.
   */
  private void notifyListeners() {
    LOGGER.severe("=>");
    for (InvalidationListener invalidationListener: invalidationListeners) {
      LOGGER.severe("Notifying listener: " + invalidationListener);
      invalidationListener.invalidated(this);
    }
  }

}
