package goedegep.jfx.stringconverterandchecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is a {@link StringConverterAndChecker} for a {@link Date}.
 */
public class DateStringConverterAndChecker extends FormatBasedStringConverterAndChecker<Date>  {
  
  private static DateStringConverterAndChecker defaultFormatDateStringConverterAndChecker = null;
  
  /**
   * Get the instance of the {@code DateStringConverterAndChecker} for the default date format:  'dd-MM-yyyy'.
   * 
   * @return the instance of the {@code DateStringConverterAndChecker} for the default date format:  'dd-MM-yyyy'.
   */
  public static DateStringConverterAndChecker getDefaultFormatDateStringConverterAndChecker() {
    if (defaultFormatDateStringConverterAndChecker == null) {
      defaultFormatDateStringConverterAndChecker = new DateStringConverterAndChecker();
    }
    
    return defaultFormatDateStringConverterAndChecker;
  }
  
  /**
   * Constructor for the default date format: 'dd-MM-yyyy'.
   */
  private DateStringConverterAndChecker() {
    super(new SimpleDateFormat("dd-MM-yyyy"));
  }
  
  /**
   * Constructor for a specific date pattern.
   * 
   * @param pattern the date pattern to use.
   */
  public DateStringConverterAndChecker(String pattern) {
    super(new SimpleDateFormat(pattern));
  }
  
  /**
   * Constructor for a specific {@code DateFormat}.
   * 
   * @param dateFormat the {@code DateFormat} to use.
   */
  public DateStringConverterAndChecker(DateFormat dateFormat) {
    super(dateFormat);
  }

}
