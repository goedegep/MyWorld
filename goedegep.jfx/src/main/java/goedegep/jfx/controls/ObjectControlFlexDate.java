package goedegep.jfx.controls;

import java.text.ParseException;
import java.util.logging.Logger;

import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

/**
 * This class provides a TextField to be used to edit a {@link FlexDate}.
 */
public class ObjectControlFlexDate extends TextFieldObjectInput<FlexDate> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlFlexDate.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  public ObjectControlFlexDate(FlexDate flexDate, double width, boolean isOptional, String toolTipText) {
    super(flexDate, width, isOptional, toolTipText, false);
  }
  
  public ObjectControlFlexDate(String text, double width, boolean isOptional, String toolTipText) {
    super(text, width, isOptional, toolTipText);
  }
    
  @Override
  protected boolean isEnteredDataValid(StringBuilder buf) {
    if (getText() == null) {
      return false;
    }
    
    boolean valueIsValid = true;
    
    try {
      FDF.parse(getText());
    } catch (ParseException e) {
      valueIsValid = false;
      if (buf != null) {
        buf.append(e.getMessage());
      }
    }

    return valueIsValid;
  }

  @Override
  protected FlexDate stringToObject(String valueAsString) {
    FlexDate flexDate = null;
    
    if (valueAsString != null) {
      try {
        flexDate = FDF.parse(valueAsString);
      } catch (ParseException e) {
        LOGGER.info("ParseException on value: " + valueAsString);
      }
    }
    
    return flexDate;
  }
  
  @Override
  protected String objectToString(FlexDate value) {
    return FDF.format(value);
  }
}
