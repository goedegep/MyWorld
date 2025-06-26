package goedegep.jfx.stringconverterandchecker;

import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

/**
 * This class is a {@link StringConverterAndChecker} for a {@link FlexDate}.
 */
public class FlexDateStringConverterAndChecker extends FormatBasedStringConverterAndChecker<FlexDate>  {
  
  private static FlexDateStringConverterAndChecker defaultFormatFlexDateStringConverterAndChecker = null;
  
  /**
   * Get the instance of the {@code FlexDateStringConverterAndChecker} for the default date format:  'dd-MM-yyyy'.
   * 
   * @return the instance of the {@code FlexDateStringConverterAndChecker} for the default date format:  'dd-MM-yyyy'.
   */
  public static FlexDateStringConverterAndChecker getDefaultFormatFlexDateStringConverterAndChecker() {
    if (defaultFormatFlexDateStringConverterAndChecker == null) {
      defaultFormatFlexDateStringConverterAndChecker = new FlexDateStringConverterAndChecker();
    }

    return defaultFormatFlexDateStringConverterAndChecker;
  }
  
  /**
   * Constructor for the default flex date format: 'dd-MM-yyyy'.
   */
  private FlexDateStringConverterAndChecker() {
    super(new FlexDateFormat());
  }
  
  /**
   * Constructor for a specific flex date format.
   * 
   * @param pattern the date pattern to use.
   */
  public FlexDateStringConverterAndChecker(FlexDateFormat flexDateFormat) {
    super(flexDateFormat);
  }
  
}
