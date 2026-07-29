package goedegep.jfx.editor.controls;

import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.stringconverterandchecker.LocalDateStringConverterAndChecker;

/**
 * This class provides a TextField to be used to edit a {@link LocalDate}.
 */
public class EditorControlLocalDate extends EditorControlTextField<LocalDate> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlLocalDate.class.getName());
  
  
  /**
   * Constructor using a Builder.
   * 
   * @param builder The builder with all properties set (may not be null).
   */
  protected EditorControlLocalDate(Builder builder) {
    super(builder);
  }

  
  /**
   * Builder class to set all properties for an {@code EditorControlLocalDate}.
   */
  public static class Builder extends EditorControlTextField.Builder<LocalDate> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      super(id);
      
      setStringConverter(LocalDateStringConverterAndChecker.getDefaultFormatInstance());
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
    public EditorControlLocalDate build() {
      EditorControlLocalDate editorControlLocalDate = new EditorControlLocalDate(this);
      editorControlLocalDate.performInitialization();
      
      return editorControlLocalDate;
    }
    
  }
}
