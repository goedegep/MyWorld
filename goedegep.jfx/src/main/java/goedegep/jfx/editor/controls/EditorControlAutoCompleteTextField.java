package goedegep.jfx.editor.controls;

import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextField;
import goedegep.jfx.editor.EditorControlTemplate;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

/**
 * This class is an {@link EditorControl} based on an autocomplete text field.
 * 
 * @param <T> The data type being edited.
 */
public class EditorControlAutoCompleteTextField<T> extends EditorControlTemplate<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlAutoCompleteTextField.class.getName());
  
  /**
   * Requested width of the text field.
   */
  private double width;
  
  /**
   * The toolTip text for the {@code comboBox}.
   */
  private String toolTipText = null;
  
  
  /**
   * The actual GUI control.
   */
  private AutoCompleteTextField autoCompleteTextField = null;
  
  /**
   * The converter to convert between an object and its textual representation.
   */
  private StringConverterAndChecker<T> stringConverterAndChecker = null;
    

  
  /**
   * Factory method to create an {@code EditorControlAutoCompleteTextField}.
   * 
   * @param customization the GUI customization
   * @param stringConverterAndChecker the converter to convert between an object and its textual representation.
   * @param width of the control.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   * @return a new {@code EditorControlEnumComboBox} instance
   */
  public static <U extends Enum<U>> EditorControlAutoCompleteTextField<U> newInstance(CustomizationFx customization, StringConverterAndChecker<U> stringConverterAndChecker, double width, boolean isOptional, String toolTipText) {
    EditorControlAutoCompleteTextField<U> editorControlAutoCompleteTextField = new EditorControlAutoCompleteTextField<U>(customization, stringConverterAndChecker, width, isOptional, toolTipText);
    editorControlAutoCompleteTextField.performInitialization();
    
    return editorControlAutoCompleteTextField;
  }
    
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param stringConverterAndChecker the converter to convert between an object and its textual representation.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  public EditorControlAutoCompleteTextField(CustomizationFx customization, StringConverterAndChecker<T> stringConverterAndChecker, double width, boolean isOptional, String toolTipText) {
    super(customization, isOptional);
    
    this.stringConverterAndChecker = stringConverterAndChecker;
    this.width = width;
    this.toolTipText = toolTipText;
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
      entries.add(stringConverterAndChecker.toString(option));
    }
  }

  @Override
  public void createControls() {
    autoCompleteTextField = customization.getComponentFactoryFx().createAutoCompleteTextField();
    
    autoCompleteTextField.setMinWidth(width);
        
    if (toolTipText != null) {
      autoCompleteTextField.setTooltip(new Tooltip(toolTipText));
    }
    
    autoCompleteTextField.textProperty().addListener((o) -> handleNewUserInput(autoCompleteTextField));
    
    autoCompleteTextField.focusedProperty().addListener(this::handleFocusChanged);
  }

  @Override
  public Node getControl() {
    return autoCompleteTextField;
  }

  @Override
  public String getValueAsFormattedText() {
    return stringConverterAndChecker.toString(value);
  }

  @Override
  protected void fillControlsWithDefaultValues() {
  }

  @Override
  protected boolean determineFilledIn(Object source) {
    // as there is only one control, we can ignore the source parameter
    
    if (autoCompleteTextField.getText() == null  ||  autoCompleteTextField.getText().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  protected T determineValue(Object source) {
    if (autoCompleteTextField.getText() == null) {
      return null;
    }
    
    T object = (T) stringToObject(autoCompleteTextField.getText());
    return object;
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

  @Override
  protected void updateNonSourceControls(Object source) {
    if (source != autoCompleteTextField) {
      autoCompleteTextField.setText(stringConverterAndChecker.toString(getValue()));
    }
  }

  @Override
  protected void redrawValue() {
    autoCompleteTextField.setText(getValueAsFormattedText());    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    if (valid) {
      autoCompleteTextField.setStyle("-fx-text-fill: black;");
    } else {
      autoCompleteTextField.setStyle("-fx-text-fill: red;");
    }
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
      redrawValue();
    }
  }

}
