package goedegep.jfx.editor.controls;

import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;

/**
 * This class provides a TextField to be used to edit an {@link Integer}.
 */
public class EditorControlInteger extends EditorControlTextField<Integer> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlInteger.class.getName());
  

  /**
   * Minimum value.
   */
  protected Integer minimumValue = null;

  /**
   * Maximum value.
   */
  protected Integer maximumValue = null;


  protected EditorControlInteger(Builder builder) {
    super(builder);
  }
  
  @Override
  public Integer stringToObject(String string) {
    Integer value = null;
    
    if (string != null && !string.isEmpty()) {
      try {
        value = Integer.valueOf(string);
      } catch (NumberFormatException e) {
        // ignore, value will be null and thus illegal.
      }
    }
    
    if ((minimumValue != null)  &&
        (value < minimumValue)) {
      errorText = "value too low";
      return null;
    } else if ((maximumValue != null)  &&
        (value > maximumValue)) {
      errorText = "value too high";
      return null;
    }
    
    return value;
  }
  
  /**
   * Builder class to set all properties for an {@code EditorControlInteger}.
   */
  public static class Builder extends EditorControlTextField.Builder<Integer> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      super(id);
    }
    
    @Override
    public Builder setCustomization(CustomizationFx customization) {
      super.setCustomization(customization);
      
      return this;
    }
    
    @Override
    public Builder setWidth(double width) {
      super.setWidth(width);
      
      return this;
    }
    
    @Override
    public Builder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);
      
      return this;
    }
    
    @Override
    public Builder setToolTipText(String toolTipText) {
      super.setToolTipText(toolTipText);
      
      return this;
    }
    
    @Override
    public Builder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public Builder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
      super.setErrorTextSupplier(errorTextSupplier);
      
      return this;
    }

    @Override
    public EditorControlInteger build() {
      EditorControlInteger editorControlInteger = new EditorControlInteger(this);
      editorControlInteger.performInitialization();
      
      return editorControlInteger;
    }
  }
}
