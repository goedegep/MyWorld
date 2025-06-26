package goedegep.jfx.editor.controls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * This class provides a TextField to be used to edit a {@link Date}.
 */
public class EditorControlDate extends EditorControlTextField<Date> {
  private static final Logger         LOGGER = Logger.getLogger(EditorControlDate.class.getName());
  private static final DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
  
  protected EditorControlDate(EditorControlDateBuilder builder) {
    super(builder);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Date stringToObject(String valueAsString) {
    Date date = null;
    
      try {
        date = DF.parse(valueAsString);
      } catch (ParseException e) {
        LOGGER.info("ParseException on value: " + valueAsString);
      }
    
    return date;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String objectToString(Date value) {
    return DF.format(value);
  }
  
  
  /**
   * Builder for creating a {@code EditorControlDate}.
   */
  public static class EditorControlDateBuilder extends EditorControlTextField.Builder<Date> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the EditorControl (may not be null).
     */
    public EditorControlDateBuilder(String id) {
      super(id);
    }
    
    @Override
    public EditorControlDateBuilder setWidth(double width) {
      super.setWidth(width);
      
      return this;
    }
    
    @Override
    public EditorControlDateBuilder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);
      
      return this;
    }
    
    @Override
    public EditorControlDateBuilder setToolTipText(String toolTipText) {
      super.setToolTipText(toolTipText);
      
      return this;
    }
    
    @Override
    public EditorControlDateBuilder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public EditorControlDateBuilder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
      super.setErrorTextSupplier(errorTextSupplier);
      
      return this;
    }

    @Override
    public EditorControlDate build() {
      EditorControlDate editorControlDate = new EditorControlDate(this);
      editorControlDate.performInitialization();
      
      return editorControlDate;
    }
    
  }
}
