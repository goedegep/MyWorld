package goedegep.util.datetime;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.time.Duration;
import java.util.logging.Logger;

/**
 * This class is a {@link Format} for a {@link Duration}.
 * <p>
 * 
 * The formatter is only meant to be used for durations up to 24 hours.<br/>
 * Properties:
 * <ul>
 * <li>alwaysPrintHours<br/>
 * If the value of the hours is zero, these are only printed if this property is set to true.
 * Example: 11 minutes and 34 seconds if show as<br/>
 * 11:34    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if alwaysPrintHours is false<br/>
 * 00:11:34 if alwaysPrintHours is true
 * </li>
 * <li>alwaysPrintDays<br/>
 * If the value of the days is zero, these are only printed if this property is set to true.
 * </li>
 * </ul>
 * 
 */
@SuppressWarnings("serial")
public class DurationFormat extends Format {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(DurationFormat.class.getName());
  
  /**
   * If true, the hours field is printed even if the value of the hours is 0.
   */
  private boolean alwaysPrintHours = false;
  
  /**
   * If true, the days field is printed even if the value of the days is 0.
   */
  private boolean alwaysPrintDays = false;
  
  /**
   * Default constructor; <code>alwaysPrintHours</code> is false.
   */
  public DurationFormat() {
    this(false, false);
  }
  
  /**
   * Constructor
   * 
   * @param alwaysPrintHours if true, the hours field is printed even if the value of the hours is 0.
   */
  public DurationFormat(boolean alwaysPrintHours) {
    this(alwaysPrintHours, false);
  }
  
  /**
   * Constructor
   * 
   * @param alwaysPrintHours if true, the hours field is printed even if the value of the hours is 0.
   */
  public DurationFormat(boolean alwaysPrintHours, boolean alwaysPrintDays) {
    this.alwaysPrintHours = alwaysPrintHours;
    this.alwaysPrintDays = alwaysPrintDays;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    Duration duration = (Duration) obj;
    
    if (duration.isNegative()) {
      toAppendTo.append("-");
      duration = duration.negated();
    }
    long days = duration.toDays();
    if (alwaysPrintDays  ||  (days != 0)) {
      if (days < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(days);
      toAppendTo.append(":");
    }

    long hours = duration.toHoursPart();
    if (alwaysPrintHours  ||  (hours != 0)) {
      if (hours < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(hours);
      toAppendTo.append(":");
    }
    
    int minutes = duration.toMinutesPart();
    if (minutes < 10) {
      toAppendTo.append("0");
    }
    toAppendTo.append(minutes);
    toAppendTo.append(":");
    
    int seconds = duration.toSecondsPart();
    if (seconds < 10) {
      toAppendTo.append("0");
    }
    toAppendTo.append(seconds);
    
    return toAppendTo;
  }

  /**
   * Parse a <code>Duration</code> from a textual representation.
   * <p>
   * The text is supposed to have the format: [dd]:[hh]:[mm]:ss.
   * 
   * @param text the string to be parsed.
   * @return the <code>Duration</code> represented by <code>source</code>.
   */
  public Duration parse(String text) {
//    int hours = 0;
//    int minutes = 0;
//    int seconds = 0;
//    
//    int colonIndex = source.lastIndexOf(":");
//    if (colonIndex != -1) {
//      String secondsString = source.substring(colonIndex + 1);
//      seconds = Integer.valueOf(secondsString);
//      LOGGER.info("secondsString: " + secondsString);
//      source = source.substring(0, colonIndex);
//      LOGGER.info("source: " + source);
//      
//      colonIndex = source.lastIndexOf(":");
//      if (colonIndex != -1) {
//        String minutesString = source.substring(colonIndex + 1);
//        minutes = Integer.valueOf(minutesString);
//        LOGGER.info("minutesString: " + minutesString);
//        source = source.substring(0, colonIndex);
//        LOGGER.info("source: " + source);
//
//      } else {
//        minutes = Integer.valueOf(source);
//      }
//    } else {
//      seconds = Integer.valueOf(source);
//    }
//    
//    Duration duration = Duration.ofSeconds(hours * 3600 + minutes * 60 + seconds);
//    
//    return duration;
    text = text.trim();
    
    boolean isNegative = false;
    if (text.startsWith("-")) {
      isNegative = true;
      text = text.substring(1).trim();
    }
    
    String[] durationFields = text.split(":");
    int index = durationFields.length - 1;
    
    if (index < 0) {
      throw new IllegalArgumentException("Invalid text for a duration. Text is: " + text);
    }

    Duration duration = Duration.ofSeconds(Long.valueOf(durationFields[index]));
    
    index--;    
    if (index >= 0) {
      duration = duration.plusMinutes(Long.valueOf(durationFields[index]));
    }
    
    index--;    
    if (index >= 0) {
      duration = duration.plusHours(Long.valueOf(durationFields[index]));
    }
    
    index--;
    if (index >= 0) {
      duration = duration.plusDays(Long.valueOf(durationFields[index]));
    }
    
    if (isNegative) {
      duration = duration.negated();
    }
    
    return duration;
  }

  @Override
  public Object parseObject(String arg0, ParsePosition arg1) {
    // TODO Auto-generated method stub
    return null;
  }

}
