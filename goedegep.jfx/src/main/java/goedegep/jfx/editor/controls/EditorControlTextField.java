package goedegep.jfx.editor.controls;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.appgen.swing.DefaultCustomization;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
import goedegep.jfx.editor.controls.EditorControlString.Builder;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.stringconverters.AnyTypeStringConverter;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * This class forms the basis for classes implementing the {@link ObjectControl} interface, using a TextField control.
 * <p>
 * The initial {@code text} of a {@code TextField} is an empty string. Also, when the user clears the field, the {@code text} is an empty string.<br/>
 * This empty string is handled as 'not filled in' and the value in this case is {@code null}.
 * 
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
public class EditorControlTextField<T> extends EditorControlTemplate<T> {
  private static final Logger LOGGER = Logger.getLogger(EditorControlTextField.class.getName());
  
  private StringConverter<T> stringConverter = null;
  
  private double width;
  
  private String toolTipText;
  
  private TextField textField = null;
  
  private boolean ignoreChanges = false;
  
//  public static <U> EditorControlTextField<U> newInstance(CustomizationFx customization, double width, boolean isOptional, String toolTipText) {
//    EditorControlTextField<U> editorControlTextField = new EditorControlTextField<U>(customization, width, isOptional, toolTipText);
//    editorControlTextField.performInitialization();
//    
//    return editorControlTextField;
//  }
//    
//  /**
//   * Constructor for using an object as initial value.
//   * 
//   * @param initialValue Initial value to set the text to (may be null).
//   * @param width The width of the TextField
//   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
//   * @param toolTipText An optional ToolTip text.
//   */
//  public EditorControlTextField(CustomizationFx customization, double width, boolean isOptional, String toolTipText) {
//    this(customization, null, width, isOptional, toolTipText);
//  }

  /**
   * Constructor using builder.
   * 
   * @param builder The {@code Builder} providing all settings.
   */
  protected EditorControlTextField(Builder<T> builder) {
    super(builder.customization != null ? builder.customization : DefaultCustomizationFx.getInstance(), builder.optional);
    
    toolTipText = builder.toolTipText;
    width = builder.width;
    setLabelBaseText(builder.labelBaseText);
    setId(builder.id);
    setErrorTextSupplier(builder.errorTextSupplier);
    
    if (builder.stringConverter != null) {
      stringConverter = builder.stringConverter;
    } else {
      stringConverter = new AnyTypeStringConverter<T>();
    }
  }
  
//  /**
//   * Constructor for using an object as initial value.
//   * 
//   * @param stringConverter a StringConverterAndChecker for conversion between object of type T and String.
//   * @param initialValue Initial value to set the text to (may be null).
//   * @param width The width of the TextField
//   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
//   * @param toolTipText An optional ToolTip text.
//   */
//  public EditorControlTextField(CustomizationFx customization, StringConverter<T> stringConverter, double width, boolean isOptional, String toolTipText) {
//    super(customization, isOptional);
//    
//    this.width = width;
//    this.toolTipText = toolTipText;
//    
//    if (stringConverter != null) {
//      this.stringConverter = stringConverter;
//    } else {
//      this.stringConverter = new AnyTypeStringConverter<T>();
//    }
//
//    // The initial value of the textField is an empty string. Again setting it to an empty string doesn't trigger the listener.
//    // So if the initial value isn't null, set the value (triggering the listener, leading to a call to handleNewUserInput().
//    // Else, just call handleNewUserInput().
//  }
  
  public void createControls() {
    textField = customization.getComponentFactoryFx().createTextField(width, toolTipText);
    
    textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!ignoreChanges) {
        handleNewUserInput(textField);
      }
    });
    
    textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!newValue)
        redrawValue();
    });
    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TextField getControl() {
    return textField;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean determineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
        
    if (textField.getText() == null) {
      LOGGER.severe("text is null for: " + getId());
    }
    return !textField.getText().isEmpty();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public T determineValue(Object source) {
    T value = stringToObject(textField.getText().trim());
    
    return value;
  }
  
  /**
   * {@inheritDoc}
   * If a non valid value is entered, set the text to red, else black.
   */
  @Override
  public void setErrorFeedback(boolean valid) {
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
  public void redrawValue() {
//    T value = ocValueProperty.get();
    if (getValue() != null) {
      String text = objectToString(getValue());
      if (text == null) {
        text = "";
      }
      textField.setText(objectToString(getValue()));
    } else {
      textField.setText("");
    }
  }

  @Override
  protected void updateNonSourceControls(Object source) {
    ignoreChanges = true;
    
    if (source == null) {
      String text = null;
      T value = getValue();
      
      if (value != null) {
        text = objectToString(value);
      }
      
      if (text == null) {
        text = "";
      }
      textField.setText(text);
    }
    
    ignoreChanges = false;
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueAsFormattedText()  {
    String text = objectToString(getValue());
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
    buf.append(", id=").append(getId() != null ? getId() : "<null>");
    buf.append(", value=").append(getValue() != null ? getValue() : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? referenceValue : "<null>");
    
    return buf.toString();
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    // TODO Auto-generated method stub
    
  }
 
  
  /**
   * Builder class to set all properties for an {@code EditorControlString}.
   */
  public static class Builder<T> {
    
    /**
     * The unique id of the ObjectControl (mandatory).
     */
    String id;
    
    /**
     * The GUI customization (optional).
     * <p>
     * If not set an instance of the {@link DefaultCustomization} is used.
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
    private Double width;
    
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
    private StringConverter<T> stringConverter = null;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      Objects.requireNonNull(id, "The id may not be null");
      
      this.id = id;
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
    public Builder<T> setWidth(Double width) {
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
    public Builder<T> setStringConverter(StringConverter<T> stringConverter) {
      this.stringConverter = stringConverter;
      
      return this;
    }
    
    /**
     * Create the {@code EditorControlString}.
     * 
     * @return the {@code EditorControlString}.
     */
    public EditorControlTextField<T> build() {
      EditorControlTextField<T> editorControlTextField = new EditorControlTextField<T>(this);
      editorControlTextField.performInitialization();
      
      return editorControlTextField;
    }
    
  }
}
