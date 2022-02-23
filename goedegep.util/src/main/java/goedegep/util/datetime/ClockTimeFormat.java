package goedegep.util.datetime;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class ClockTimeFormat extends Format {
  private static final Logger         LOGGER = Logger.getLogger(ClockTimeFormat.class.getName());
  
  private boolean useColons = false;
  private boolean alwaysPrintHours = false;
  
  public ClockTimeFormat() {
    this(false, false);
  }
  
  public ClockTimeFormat(boolean useColons, boolean alwaysPrintHours) {
    this.useColons = useColons;
    this.alwaysPrintHours = alwaysPrintHours;
  }

  @Override
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    ClockTime clockTime = (ClockTime) obj;
    
    int hours = clockTime.getHours();
    if (alwaysPrintHours  ||  (hours != 0)) {
      if (useColons  &&  hours < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(hours);
      if (useColons) {
        toAppendTo.append(":");
      } else {
        toAppendTo.append("??");
      }
    }
    
    int minutes = clockTime.getMinutes();
    if (useColons  &&  minutes < 10) {
      toAppendTo.append("0");
    }
    toAppendTo.append(minutes);
    if (useColons) {
      toAppendTo.append(":");
    } else {
      toAppendTo.append("'");
    }
    
    int seconds = clockTime.getSeconds();
    if (useColons  &&  seconds < 10) {
      toAppendTo.append("0");
    }
    toAppendTo.append(seconds);
    if (!useColons) {
      toAppendTo.append("''");
    }
    
    return toAppendTo;
  }

  public ClockTime parse(String source) {
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    
    int colonIndex = source.lastIndexOf(":");
    if (colonIndex != -1) {
      String secondsString = source.substring(colonIndex + 1);
      seconds = Integer.valueOf(secondsString);
      LOGGER.info("secondsString: " + secondsString);
      source = source.substring(0, colonIndex);
      LOGGER.info("source: " + source);
      
      colonIndex = source.lastIndexOf(":");
      if (colonIndex != -1) {
        String minutesString = source.substring(colonIndex + 1);
        minutes = Integer.valueOf(minutesString);
        LOGGER.info("minutesString: " + minutesString);
        source = source.substring(0, colonIndex);
        LOGGER.info("source: " + source);

//        hours = Integer.valueOf(source);
      } else {
        minutes = Integer.valueOf(source);
      }
    } else {
      seconds = Integer.valueOf(source);
    }
    
    
    ClockTime clockTime = new ClockTime(hours, minutes, seconds);
    
    return clockTime;
  }

  @Override
  public Object parseObject(String arg0, ParsePosition arg1) {
    // TODO Auto-generated method stub
    return null;
  }

}
