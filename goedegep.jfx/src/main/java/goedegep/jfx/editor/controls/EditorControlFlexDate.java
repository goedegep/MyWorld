package goedegep.jfx.editor.controls;

import java.text.ParseException;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

/**
 * This class provides a TextField to be used to edit a {@link FlexDate}.
 */
public class EditorControlFlexDate extends EditorControlTextField<FlexDate> {
  private static final Logger         LOGGER = Logger.getLogger(EditorControlFlexDate.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
//  @SuppressWarnings("unchecked")
//  public static EditorControlFlexDate newInstance(CustomizationFx customization, double width, boolean isOptional, String toolTipText) {
//    EditorControlFlexDate editorControlFlexDate = new EditorControlFlexDate(customization, width, isOptional, toolTipText);
//    editorControlFlexDate.performInitialization();
//    
//    return editorControlFlexDate;
//  }
//  
//  /**
//   * Constructor.
//   * 
//   * @param width Width of the TextField.
//   * @param isOptional Indication of whether the value is an optional values.
//   * @param toolTipText An optional Tooltip text.
//   */
//  public EditorControlFlexDate(CustomizationFx customization, double width, boolean isOptional, String toolTipText) {
//    super(customization, width, isOptional, toolTipText);
//  }
  
  protected EditorControlFlexDate(FlexDateBuilder builder) {
    super(builder);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlexDate stringToObject(String valueAsString) {
    FlexDate flexDate = null;
    
      try {
        flexDate = FDF.parse(valueAsString);
      } catch (ParseException e) {
        LOGGER.info("ParseException on value: " + valueAsString);
      }
    
    return flexDate;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String objectToString(FlexDate value) {
    return FDF.format(value);
  }
  
  public static class FlexDateBuilder extends EditorControlTextField.Builder<FlexDate> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public FlexDateBuilder(String id) {
      super(id);
    }
    
    @Override
    public FlexDateBuilder setWidth(Double width) {
      super.setWidth(width);
      
      return this;
    }
    
    @Override
    public FlexDateBuilder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);
      
      return this;
    }
    
    @Override
    public FlexDateBuilder setToolTipText(String toolTipText) {
      super.setToolTipText(toolTipText);
      
      return this;
    }
    
    @Override
    public FlexDateBuilder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public FlexDateBuilder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
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
