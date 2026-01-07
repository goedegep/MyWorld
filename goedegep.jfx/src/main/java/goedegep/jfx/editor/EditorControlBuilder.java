package goedegep.jfx.editor;

import java.util.Objects;
import java.util.function.Supplier;

import goedegep.jfx.CustomizationFx;


/**
 * This class is the base class for the builders of editor controls.
 */
public abstract class EditorControlBuilder {
  
  /**
   * The unique id of the ObjectControl (mandatory).
   */
  public String id;
  
  /**
   * The GUI customization (optional).
   * <p>
   * If not set an instance of the {@link DefaultCustomizationFx} is used.
   */
  public CustomizationFx customization;
  
  
  /**
   * Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
   */
  public boolean optional;
  
  /**
   * Base text for the label.
   */
  public String labelBaseText;
  
  /**
   * Width of the text field.
   */
  public Double width;
  
  /**
   * ToolTip text for the control (optional).
   */
  public String toolTipText;
  
  /**
   * Error text supplier. A method that provides an error text.
   */
  public Supplier<String> errorTextSupplier;
  
  /**
   * Constructor with mandatory arguments.
   * 
   * @param id The unique id of the ObjectControl (may not be null).
   */
  public EditorControlBuilder(String id) {
    Objects.requireNonNull(id, "The id may not be null");
    
    this.id = id;
  }
  
  /**
   * Set the GUI customization.
   * 
   * @param customization The GUI customization
   * @return this
   */
  public EditorControlBuilder setCustomization(CustomizationFx customization) {
    this.customization = customization;
    
    return this;
  }
  
  /**
   * Set the optional indication.
   * 
   * @param optional Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
   * @return this
   */
  public EditorControlBuilder setOptional(boolean optional) {
    this.optional = optional;
    
    return this;
  }
  
  /**
   * Set the base text for the label.
   * 
   * @param labelBaseText  the base text for the label.
   * @return this
   */
  public EditorControlBuilder setLabelBaseText(String labelBaseText) {
    this.labelBaseText = labelBaseText;
    
    return this;
  }
  
  /**
   * Set the width of the text field.
   * 
   * @param width  the width of the text field.
   * @return this
   */
  public EditorControlBuilder setWidth(Double width) {
    this.width = width;
    
    return this;
  }
  
  /**
   * Set the ToolTip text.
   * 
   * @param toolTipText the ToolTip text
   * @return this
   */
  public EditorControlBuilder setToolTipText(String toolTipText) {
    this.toolTipText = toolTipText;
    
    return this;
  }
  
  /**
   * Set the method that provides an error text.
   * 
   * @param errorTextSupplier the method that provides an error text.
   * @return this
   */
  public EditorControlBuilder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
    this.errorTextSupplier = errorTextSupplier;
    
    return this;
  }
  
}
