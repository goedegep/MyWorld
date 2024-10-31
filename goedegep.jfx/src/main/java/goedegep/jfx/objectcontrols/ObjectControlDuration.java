package goedegep.jfx.objectcontrols;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;

/**
 * This class provides a TextField to be used to edit a {@link Duration}.
 */
public class ObjectControlDuration extends ObjectControlTextField<Duration> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlDuration.class.getName());
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("mm:SSS");
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param localDate initial value.
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlDuration(CustomizationFx customization, Duration duration, double width, boolean isOptional, String toolTipText) {
    super(customization, duration, width, isOptional, toolTipText);
  }
    
  @Override
  protected Duration stringToObject(String valueAsString) {
    Duration duration = null;
    
    if (valueAsString == null) {
      return null;
    }
    
    try {
      LocalTime localTime = LocalTime.parse(valueAsString, DTF);
      duration = Duration.ofSeconds(localTime.toSecondOfDay());
    } catch (DateTimeParseException e) {  //NOPMD
      // No action
    }
    
    LOGGER.severe("<= " + duration.toSeconds());
    return duration;
  }
  
  @Override
  protected String objectToString(Duration value) {
    if (value == null) {
      return null;
    } else {
      return LocalTime.ofSecondOfDay(value.getSeconds()).format(DTF);
    }
  }

}
