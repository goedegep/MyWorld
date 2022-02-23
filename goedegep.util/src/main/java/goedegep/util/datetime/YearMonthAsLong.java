package goedegep.util.datetime;

import java.time.YearMonth;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * This is a utility class for representing a {@link YearMonth} as a long.
 * <p>
 * Representing a YearMonth as a long (as the value of a {@link Number}) is mainly useful in javafx charts.<br/>
 * The long value for a YearMonth is MONTHS_IN_YEAR * yearMonth.getYear() + yearMonth.getMonthValue() - 1.
 */
public class YearMonthAsLong {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(YearMonthAsLong.class.getName());
  /**
   * The number of months in a year.
   */
  public static final long MONTHS_IN_YEAR = 12;

  /**
   * As this is a utility class, there is a private constructor.
   */
  private YearMonthAsLong() {
  }
  
  /**
   * Get the numerical representation of a YearMonth.
   * 
   * @param yearMonth the YearMonth to get the numerical representation for.
   * @return the numerical representation of <code>yearMonth</code>.
   */
  public static long yearMonthToLong(YearMonth yearMonth) {
    Objects.requireNonNull(yearMonth, "argument ‘yearMonth’ must not be null");
    
    long yearMonthValue = MONTHS_IN_YEAR * yearMonth.getYear() + yearMonth.getMonthValue() - 1;
    return yearMonthValue;
  }
  
  /**
   * Get the YearMonth for a numerical representation of a YearMonth.
   * 
   * @param yearMonthValue the numerical representation of a YearMonth.
   * @return the YearMonth for the <code>yearMonthValue</code>.
   */
  public static YearMonth longToYearMonth(long yearMonthValue) {
    
    int year = (int) (yearMonthValue / MONTHS_IN_YEAR);
    int month = (int) (yearMonthValue % MONTHS_IN_YEAR + 1);
    YearMonth yearMonth = YearMonth.of(year, month);
    
    return yearMonth;
  }
}
