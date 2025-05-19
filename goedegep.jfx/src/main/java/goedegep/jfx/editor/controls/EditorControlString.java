package goedegep.jfx.editor.controls;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.appgen.swing.DefaultCustomization;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
import javafx.scene.control.TextField;

public class EditorControlString extends EditorControlTemplate<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlString.class.getName());
  
  /**
   * The control.  
   */
  private TextField textField = null;
  
  private Double width;
  private String toolTipText;
  
//  /**
//   * Get an {@code EditorControlString} instance.
//   * 
//   * @param customization The GUI customization.
//   * @param initialValue the initial value.
//   * @param width width of the TextField.
//   * @param optional indication of whether the value is optional or not.
//   * @param toolTipText an optional tooltip text.
//   * @return a new {@code EditorControlString} instance.
//   */
//  public static EditorControlString newInstance(CustomizationFx customization, double width, boolean optional, String toolTipText) {
//    EditorControlString objectControlString = new EditorControlString(customization, width, optional, toolTipText);
//    objectControlString.performInitialization();
//    
//    return objectControlString;
//  }
      
//  /**
//   * Constructor.
//   * 
//   * @param customization The GUI customization.
//   * @param width width of the TextField.
//   * @param optional indication of whether the value is optional or not.
//   * @param toolTipText an optional tooltip text.
//   */
//  private EditorControlString(CustomizationFx customization, double width, boolean optional, String toolTipText) {
//    super(customization, optional);
//    
//    this.width = width;
//    this.toolTipText = toolTipText;
//  }
  
  /**
   * Constructor using a Builder.
   * 
   * @param builder The {@code Builder} providing all settings.
   */
  private EditorControlString(Builder builder) {
    super(builder.customization != null ? builder.customization : DefaultCustomizationFx.getInstance(), builder.optional);
    
    toolTipText = builder.toolTipText;
    width = builder.width;
    setLabelBaseText(builder.labelBaseText);
    setId(builder.id);
    setErrorTextSupplier(builder.errorTextSupplier);
  }

  @Override
  public void createControls() {
    textField = customization.getComponentFactoryFx().createTextField(width, toolTipText);

    textField.textProperty().addListener((_, _, _) -> {
      handleNewUserInput(textField);
    });
        
    setComparator((o1, o2) -> {
      // For Strings we handle an empty String as equal to null.
      if (o1 != null  &&  o1.isEmpty()) {
        o1 = null;
      }
      
      if (o2 != null  &&  o2.isEmpty()) {
        o2 = null;
      }
      
      int result = Objects.equals(o1, o2) ? 0 : -1;
      return result;
    });
  }
  
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
    
    return textField.getText() != null  &&  !textField.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String determineValue(Object source) {
    String text = textField.getText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }
  
  /**
   * {@inheritDoc}
   * A value is never invalid, so no action.
   */
  @Override
  public void setErrorFeedback(boolean valid) {
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so no action.
   */
  @Override
  public void redrawValue() {
  }

  @Override
  protected void updateNonSourceControls(Object source) {
    if (source == null) {
      textField.setText(value);
    }
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so just return the text.
   */
  @Override
  public String getValueAsFormattedText()  {
    return getValue();
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("ObjectControl type=String");
    buf.append(", id=").append(getId() != null ? getId() : "<null>");
    buf.append(", value=").append(value != null ? value : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? "\"" + referenceValue + "\"" : "<null>");
    
    return buf.toString();
  }

  @Override
  public void fillControlsWithDefaultValues() {
    textField.setText("");
  }
 
  
  /**
   * Builder class to set all properties for an {@code EditorControlString}.
   */
  public static class Builder {
    
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
    public Builder setCustomization(CustomizationFx customization) {
      this.customization = customization;
      
      return this;
    }
    
    /**
     * Set the optional indication.
     * 
     * @param optional Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
     * @return this
     */
    public Builder setOptional(boolean optional) {
      this.optional = optional;
      
      return this;
    }
    
    /**
     * Set the base text for the label.
     * 
     * @param labelBaseText  the base text for the label.
     * @return this
     */
    public Builder setLabelBaseText(String labelBaseText) {
      this.labelBaseText = labelBaseText;
      
      return this;
    }
    
    /**
     * Set the width of the text field.
     * 
     * @param width  the width of the text field.
     * @return this
     */
    public Builder setWidth(Double width) {
      this.width = width;
      
      return this;
    }
    
    /**
     * Set the ToolTip text.
     * 
     * @param toolTipText the ToolTip text
     * @return this
     */
    public Builder setToolTipText(String toolTipText) {
      this.toolTipText = toolTipText;
      
      return this;
    }
    
    /**
     * Set the method that provides an error text.
     * 
     * @param errorTextSupplier the method that provides an error text.
     * @return this
     */
    public Builder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
      this.errorTextSupplier = errorTextSupplier;
      
      return this;
    }
    
    /**
     * Create the {@code EditorControlString}.
     * 
     * @return the {@code EditorControlString}.
     */
    public EditorControlString build() {
      EditorControlString editorControlString = new EditorControlString(this);
      editorControlString.performInitialization();
      
      return editorControlString;
    }
    
  }
}
