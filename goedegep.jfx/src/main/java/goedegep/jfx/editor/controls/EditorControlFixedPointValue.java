package goedegep.jfx.editor.controls;

import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.stringconverterandchecker.FixedPointValueStringConverterAndChecker;
import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * This class provides a TextField to be used to edit a {@link FixedPointValue}.
 * <p>
 * <b>Valid factor range.</b><br/>
 * A valid factor range can be set by specifying a minimum and maximum factor, see {@link #setValidFactorRange}.
 * By default both minimumFactor and maximumFactor are set to null, which means no restriction.
 */
public class EditorControlFixedPointValue extends EditorControlTextField<FixedPointValue> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlFixedPointValue.class.getName());
  
  
  /**
   * Minimum factor for the FixedPointValue.
   */
  protected Integer minimumFactor = null;
  
  /**
   * Maximum factor for the FixedPointValue.
   */
  protected Integer maximumFactor = null;
  
  
  protected EditorControlFixedPointValue(Builder builder) {
    super(builder);
    
    minimumFactor = builder.minimumFactor;
    maximumFactor = builder.maximumFactor;
  }
    
  
  public static class Builder extends EditorControlTextField.Builder<FixedPointValue> {
    
    /**
     * Minimum factor for the FixedPointValue.
     */
    protected Integer minimumFactor = null;
    
    /**
     * Maximum factor for the FixedPointValue.
     */
    protected Integer maximumFactor = null;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      super(id);
      
      setStringConverter(FixedPointValueStringConverterAndChecker.getDefaultFormatFixedPointValueStringConverterAndChecker());
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
    
    /**
     * Set the valid factor range for fixed point values.
     * 
     * @param minimumFactor The minumum factor. Null means no limit.
     * @param maximumFactor The maximum factor. Null means no limit.
     */
    public Builder setValidFactorRange(Integer minimumFactor, Integer maximumFactor) {
      this.minimumFactor = minimumFactor;
      this.maximumFactor = maximumFactor;
      
      return this;
    }

    @Override
    public EditorControlFixedPointValue build() {
      EditorControlFixedPointValue editorControlCurrency = new EditorControlFixedPointValue(this);
      editorControlCurrency.performInitialization();
      
      return editorControlCurrency;
    }
  }
}
