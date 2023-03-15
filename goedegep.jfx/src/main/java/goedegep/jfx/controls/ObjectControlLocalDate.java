package goedegep.jfx.controls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;


/**
 * This class provides a TextField to be used to edit a {@link LocalDate}.
 */
public class ObjectControlLocalDate extends TextFieldObjectControl<LocalDate> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlLocalDate.class.getName());
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  
  
  public ObjectControlLocalDate(LocalDate localDate, double width, boolean isOptional, String toolTipText) {
    super(localDate, width, isOptional, toolTipText, false);
  }
  
  public ObjectControlLocalDate(String text, double width, boolean isOptional, String toolTipText) {
    super(text, width, isOptional, toolTipText);
  }
    
  @Override
  protected boolean isEnteredDataValid(StringBuilder buf) {
    if (getText() == null) {
      return false;
    }
    
    try {
      @SuppressWarnings("unused")
      LocalDate localDate = LocalDate.parse(getText(), DTF);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  protected LocalDate stringToObject(String valueAsString) {
    return LocalDate.parse(valueAsString, DTF);
  }
  
  @Override
  protected String objectToString(LocalDate value) {
    if (value == null) {
      return null;
    } else {
      return DTF.format(value);
    }
  }
}
