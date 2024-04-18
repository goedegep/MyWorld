package goedegep.jfx.objectcontrols;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;


/**
 * This class provides a TextField to be used to edit a {@link LocalDate}.
 */
public class ObjectControlLocalDate extends ObjectControlTextField<LocalDate> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlLocalDate.class.getName());
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("d-M-yyyy");
  private static final DateTimeFormatter DTF2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  
  /**
   * Constructor.
   * 
   * @param localDate initial value.
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlLocalDate(CustomizationFx customization, LocalDate localDate, double width, boolean isOptional, String toolTipText) {
    super(customization, localDate, width, isOptional, toolTipText);
  }
    
  @Override
  protected LocalDate stringToObject(String valueAsString) {
    LocalDate localDate = null;
    
    if (valueAsString == null) {
      return null;
    }
    
    try {
      localDate = LocalDate.parse(valueAsString, DTF);
    } catch (DateTimeParseException e) {
      // No action
    }
    
    return localDate;
  }
  
  @Override
  protected String objectToString(LocalDate value) {
    if (value == null) {
      return null;
    } else {
      return DTF2.format(value);
    }
  }
}
