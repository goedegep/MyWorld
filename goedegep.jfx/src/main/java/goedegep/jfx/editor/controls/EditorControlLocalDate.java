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
  
  
  protected EditorControlLocalDate(LocalDateBuilder builder) {
    super(builder);
  }

  
  public static class LocalDateBuilder extends EditorControlTextField.Builder<LocalDate> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public LocalDateBuilder(String id) {
      super(id);
      
      setStringConverter(LocalDateStringConverterAndChecker.getDefaultFormatInstance());
    }
    
    @Override
    public LocalDateBuilder setCustomization(CustomizationFx customization) {
      super.setCustomization(customization);
      
      return this;
    }
    
    @Override
    public LocalDateBuilder setWidth(double width) {
      super.setWidth(width);
      
      return this;
    }
    
    @Override
    public LocalDateBuilder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);
      
      return this;
    }
    
    @Override
    public LocalDateBuilder setToolTipText(String toolTipText) {
      super.setToolTipText(toolTipText);
      
      return this;
    }
    
    @Override
    public LocalDateBuilder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public LocalDateBuilder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
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
