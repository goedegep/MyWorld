package goedegep.jfx.stringconverterandchecker;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;

/**
 * This class is a {@link StringConverterAndChecker} for a {@link FixedPointValue}.
 */
public class FixedPointValueStringConverterAndChecker extends FormatBasedStringConverterAndChecker<FixedPointValue> {
  
  private static FixedPointValueStringConverterAndChecker defaultFormatFixedPointValueStringConverterAndChecker;
  
  /**
   * Get the instance of the {@code FixedPointValueStringConverter} for the default format.
   * 
   * @return the instance of the {@code FixedPointValueStringConverter} for the default format.
   */
  public static FixedPointValueStringConverterAndChecker getDefaultFormatFixedPointValueStringConverterAndChecker() {
    if (defaultFormatFixedPointValueStringConverterAndChecker == null) {
      defaultFormatFixedPointValueStringConverterAndChecker = new FixedPointValueStringConverterAndChecker();
    }
    
    return defaultFormatFixedPointValueStringConverterAndChecker;
  }
  
  /**
   * Constructor for the default {@code FixedPointValueFormat}.
   */
  private FixedPointValueStringConverterAndChecker() {
    super(new FixedPointValueFormat());
  }
  
  /**
   * Constructor for a specific {@code FixedPointValueFormat}.
   * 
   * @param fixedPointValueFormat the {@code FixedPointValueFormat} to use.
   */
  private FixedPointValueStringConverterAndChecker(FixedPointValueFormat fixedPointValueFormat) {
    super(fixedPointValueFormat);
  }
}


