package goedegep.jfx.editor.controls;

import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.money.PgCurrency;

/**
 * This class provides a TextField to be used to edit a {@link PgCurrency}.
 */
public class EditorControlInteger extends EditorControlTextField<Integer> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlInteger.class.getName());
  
  // TODO check on valid range.
//
//  if ((minimumValue != null)  &&
//      (value < minimumValue)) {
//    valueIsValid = false;
//    errorText = "value too low";
//  } else if ((maximumValue != null)  &&
//      (value > maximumValue)) {
//    valueIsValid = false;
//    errorText = "value too high";
//  }

  /**
   * Minimum value.
   */
  protected Integer minimumValue = null;

  /**
   * Maximum value.
   */
  protected Integer maximumValue = null;


  protected EditorControlInteger(IntegerBuilder builder) {
    super(builder);
  }  
  
  public static class IntegerBuilder extends EditorControlTextField.Builder<Integer> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public IntegerBuilder(String id) {
      super(id);
    }
    
    @Override
    public IntegerBuilder setCustomization(CustomizationFx customization) {
      super.setCustomization(customization);
      
      return this;
    }
    
    @Override
    public IntegerBuilder setWidth(Double width) {
      super.setWidth(width);
      
      return this;
    }
    
    @Override
    public IntegerBuilder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);
      
      return this;
    }
    
    @Override
    public IntegerBuilder setToolTipText(String toolTipText) {
      super.setToolTipText(toolTipText);
      
      return this;
    }
    
    @Override
    public IntegerBuilder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public IntegerBuilder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
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
