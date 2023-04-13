package goedegep.jfx.objectcontrols;

import java.text.ParseException;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
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
  public ObjectControlFlexDate(CustomizationFx customization, FlexDate flexDate, double width, boolean isOptional, String toolTipText) {
    super(customization, flexDate, width, isOptional, toolTipText);
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
}
