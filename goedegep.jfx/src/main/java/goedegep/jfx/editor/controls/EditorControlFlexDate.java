package goedegep.jfx.editor.controls;

import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.stringconverterandchecker.FlexDateStringConverterAndChecker;
import goedegep.util.datetime.FlexDate;

/**
 * This class provides a TextField to be used to edit a {@link FlexDate}.
 */
public class EditorControlFlexDate extends EditorControlTextField<FlexDate> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlFlexDate.class.getName());
  
  
  protected EditorControlFlexDate(Builder builder) {
    super(builder);
  }

  
  public static class Builder extends EditorControlTextField.Builder<FlexDate> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      super(id);
      
      setStringConverter(FlexDateStringConverterAndChecker.getDefaultFormatFlexDateStringConverterAndChecker());
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
    public EditorControlFlexDate build() {
      EditorControlFlexDate editorControlFlexDate = new EditorControlFlexDate(this);
      editorControlFlexDate.performInitialization();
      
      return editorControlFlexDate;
    }
    
  }
}
