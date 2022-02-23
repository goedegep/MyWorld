package goedegep.jfx;

import java.time.format.DateTimeFormatter;

import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

/**
 * This factory class provides singleton instances of the most common formatters.
 */
public class FormattersFactory {
  private static DateTimeFormatter dateDateTimeFormatterInstance = null;
  private static DateTimeFormatter yearMonthDateTimeFormatterInstance = null;
  private static PgCurrencyFormat pgCurrencyFormat = null;
  private static FixedPointValueFormat fixedPointValueFormat = null;
  
  /**
   * Private constructor as you shouldn't instantiate this class.
   */
  private FormattersFactory() {
  }

  /**
   * Get the instance of the {@link DateTimeFormatter} for a complete date.
   * 
   * @return the instance of the DateTimeFormatter.
   */
  public static DateTimeFormatter getDateDateTimeFormatterInstance() {
    if (dateDateTimeFormatterInstance == null) {
      dateDateTimeFormatterInstance = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
    
    return yearMonthDateTimeFormatterInstance;
  }

  /**
   * Get the instance of the {@link DateTimeFormatter} for a YearMonth.
   * 
   * @return the instance of the DateTimeFormatter.
   */
  public static DateTimeFormatter getYearMonthDateTimeFormatterInstance() {
    if (yearMonthDateTimeFormatterInstance == null) {
      yearMonthDateTimeFormatterInstance = DateTimeFormatter.ofPattern("MM-yyyy");
    }
    
    return yearMonthDateTimeFormatterInstance;
  }
  
  /**
   * Get the instance of the {@link PgCurrencyFormatter}.
   * 
   * @return the instance of the {@link PgCurrencyFormatter}.
   */
  public static PgCurrencyFormat getPgCurrencyFormat() {
    if (pgCurrencyFormat == null) {
      pgCurrencyFormat = new PgCurrencyFormat();
    }
    
    return pgCurrencyFormat;
  }
  
  /**
   * Get the instance of the FixedPointValueFormat.
   * 
   * @return the instance of the FixedPointValueFormat.
   */
  public static FixedPointValueFormat getFixedPointValueFormat() {
    if (fixedPointValueFormat == null) {
      fixedPointValueFormat = new FixedPointValueFormat();
    }
    
    return fixedPointValueFormat;
  }
}
