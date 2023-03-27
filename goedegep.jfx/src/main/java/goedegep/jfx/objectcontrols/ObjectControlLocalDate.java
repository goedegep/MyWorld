package goedegep.jfx.objectcontrols;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import goedegep.jfx.stringconverters.FormatBasedStringConverterAndChecker;


/**
 * This class provides a TextField to be used to edit a {@link LocalDate}.
 */
public class ObjectControlLocalDate extends ObjectControlTextField<LocalDate> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlLocalDate.class.getName());
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  
  /**
   * Constructor.
   * 
   * @param localDate initial value.
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlLocalDate(LocalDate localDate, double width, boolean isOptional, String toolTipText) {
    super(localDate, width, isOptional, toolTipText);
  }
    
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isDataValid() {
//    if (getText() == null) {
//      return true;
//    }
//    
//    try {
//      @SuppressWarnings("unused")
//      LocalDate localDate = LocalDate.parse(getText(), DTF);
//      return true;
//    } catch (Exception e) {
//      return false;
//    }
//  }

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
