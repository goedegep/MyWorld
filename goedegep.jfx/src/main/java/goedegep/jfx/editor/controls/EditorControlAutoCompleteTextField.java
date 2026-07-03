package goedegep.jfx.editor.controls;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextField;
import goedegep.jfx.editor.EditorControlTemplate;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.stringconverterandchecker.AnyTypeStringConverterAndChecker;
import goedegep.jfx.stringconverterandchecker.StringConverterAndChecker;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

/**
 * This class is an {@link EditorControl} based on an autocomplete text field.
 * 
 * @param <T> The data type being edited.
 */
public class EditorControlAutoCompleteTextField<T> extends EditorControlTemplate<T> {
  @SuppressWarnings("unused")
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
   * Constructor using builder.
   * 
   * @param builder The {@code Builder} providing all settings.
   */
  protected EditorControlAutoCompleteTextField(Builder<T> builder) {
    super(builder.customization != null ? builder.customization : DefaultCustomizationFx.getInstance(), builder.id, builder.optional);
    
    toolTipText = builder.toolTipText;
    width = builder.width;
    setLabelBaseText(builder.labelBaseText);
    setErrorTextSupplier(builder.errorTextSupplier);
    
    if (builder.stringConverterAndChecker != null) {
      stringConverterAndChecker = builder.stringConverterAndChecker;
    } else {
      stringConverterAndChecker = new AnyTypeStringConverterAndChecker<T>();
    }
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
    
    autoCompleteTextField.textProperty().addListener((_) -> handleNewUserInput(autoCompleteTextField));
    
    registerControls(autoCompleteTextField);
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
   * Builder class to set all properties for an {@code EditorControlAutoCompleteTextField}.
   */
  public static class Builder<T> {
    
    /**
     * The unique id of the ObjectControl (mandatory).
     */
    String id;
    
    /**
     * The GUI customization (optional).
     * <p>
     * If not set an instance of the {@link DefaultCustomizationFx} is used.
     */
    private CustomizationFx customization;
    
    
    /**
     * Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
     */
    private boolean optional;
    
    /**
     * Base text for the label.
     */
    private String labelBaseText;
    
    /**
     * Width of the text field.
     */
    private double width;
    
    /**
     * ToolTip text for the control (optional).
     */
    private String toolTipText;
    
    /**
     * Error text supplier. A method that provides an error text.
     */
    protected Supplier<String> errorTextSupplier;
    
    /**
     * A StringConverterAndChecker for conversion between object of type T and String.
     */
    private StringConverterAndChecker<T> stringConverterAndChecker = null;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      Objects.requireNonNull(id, "The id may not be null");
      
      this.id = id;
      width = -1;
    }
    
    /**
     * Set the GUI customization.
     * 
     * @param customization The GUI customization
     * @return this
     */
    public Builder<T> setCustomization(CustomizationFx customization) {
      this.customization = customization;
      
      return this;
    }
    
    /**
     * Set the optional indication.
     * 
     * @param optional Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
     * @return this
     */
    public Builder<T> setOptional(boolean optional) {
      this.optional = optional;
      
      return this;
    }
    
    /**
     * Set the base text for the label.
     * 
     * @param labelBaseText  the base text for the label.
     * @return this
     */
    public Builder<T> setLabelBaseText(String labelBaseText) {
      this.labelBaseText = labelBaseText;
      
      return this;
    }
    
    /**
     * Set the width of the text field.
     * 
     * @param width  the width of the text field.
     * @return this
     */
    public Builder<T> setWidth(double width) {
      this.width = width;
      
      return this;
    }
    
    /**
     * Set the ToolTip text.
     * 
     * @param toolTipText the ToolTip text
     * @return this
     */
    public Builder<T> setToolTipText(String toolTipText) {
      this.toolTipText = toolTipText;
      
      return this;
    }
    
    /**
     * Set the method that provides an error text.
     * 
     * @param errorTextSupplier the method that provides an error text.
     * @return this
     */
    public Builder<T> setErrorTextSupplier(Supplier<String> errorTextSupplier) {
      this.errorTextSupplier = errorTextSupplier;
      
      return this;
    }
    
    /**
     * Set the String converter.
     * 
     * @param stringConverter A StringConverterAndChecker for conversion between object of type T and String.
     * @return this
     */
    public Builder<T> setStringConverterAndChecker(StringConverterAndChecker<T> stringConverterAndChecker) {
      this.stringConverterAndChecker = stringConverterAndChecker;
      
      return this;
    }
    
    /**
     * Create the {@code EditorControlString}.
     * 
     * @return the {@code EditorControlString}.
     */
    public EditorControlAutoCompleteTextField<T> build() {
      EditorControlAutoCompleteTextField<T> editorControlAutoCompleteTextField = new EditorControlAutoCompleteTextField<T>(this);
      editorControlAutoCompleteTextField.performInitialization();
      
      return editorControlAutoCompleteTextField;
    }
    
  }
}
