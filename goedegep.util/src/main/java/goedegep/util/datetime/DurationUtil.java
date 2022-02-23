package goedegep.util.datetime;

import java.time.Duration;

public class DurationUtil {
  
  public static String durationToString(Duration duration, boolean showDays) {
    String minusSign = "";
    if (duration.isNegative()) {
      minusSign = "-";
      duration = duration.negated();
    }
    long days = duration.toDays();
    if (days != 0  &&  !showDays) {
      throw new IllegalArgumentException("showDays is false, while days is not 0 (days = " + days);
    }
    duration = duration.minusDays(days);
    long hours = duration.toHours();
    duration = duration.minusHours(hours);
    long minutes = duration.toMinutes();
    duration = duration.minusMinutes(minutes);
    long seconds = duration.getSeconds() ;
    return minusSign + hours + ":" + minutes + ":" + seconds;
  }

  /**
   * Parse a Duration from a text.
   * <p>
   * The text is supposed to have the format: [dd]:[hh]:[mm]:ss.
   * 
   * @param text the string to be parsed
   * @return the Duration parsed from the {@code text}.
   */
  public static Duration durationFromString(String text) {
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
  
}
