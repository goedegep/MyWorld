package goedegep.jfx.objectcontrols;

import java.text.ParseException;
import java.util.logging.Logger;

import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

/**
 * This class provides a TextField to be used to edit a {@link FlexDate}.
 */
public class ObjectControlFlexDate extends ObjectControlTextField<FlexDate> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlFlexDate.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  /**
   * Constructor.
   * 
   * @param flexDate Initial date to be filled in in the TextField.
   * @param width Width of the TextField.
   * @param isOptional Indication of whether the value is an optional values.
   * @param toolTipText An optional Tooltip text.
   */
  public ObjectControlFlexDate(FlexDate flexDate, double width, boolean isOptional, String toolTipText) {
    super(flexDate, width, isOptional, toolTipText, false);
  }
  
  /**
   * Constructor.
   * 
   * @param text Textual representation for the initial date to be filled in in the TextField.
   * @param width Width of the TextField.
   * @param isOptional Indication of whether the value is an optional values.
   * @param toolTipText An optional Tooltip text.
   */
  public ObjectControlFlexDate(String text, double width, boolean isOptional, String toolTipText) {
    super(text, width, isOptional, toolTipText);
  }
    
  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String objectToString(FlexDate value) {
    return FDF.format(value);
  }
}
