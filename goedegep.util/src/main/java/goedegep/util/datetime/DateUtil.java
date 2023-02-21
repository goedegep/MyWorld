package goedegep.util.datetime;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * This is a utility class with date (Date, LocalDate, FlexDate) related methods.
 *
 */
public final class DateUtil {
  private static final Logger LOGGER = Logger.getLogger(DateUtil.class.getName());
  
  /**
   * A private constructor as this is a utility class.
   */
  private DateUtil() {
  }
  
  /**
   * 'Temporary' conversion function.
   * @param localDate the LocalDate to be translated to a Date.
   * @return a Date representation of the localDate.
   */
  public static Date localDateToDate(LocalDate localDate) {
    return createDate(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
  }
  
  /**
   * 'Temporary'conversion function.
   * 
   * @param date the Date to be translated to a LocalDate.
   * @return a LocalDate representation of the date.
   */
  public static LocalDate dateToLocalDate(Date date) {
    LOGGER.info("=> date=" + getDayOfMonth(date) + "-" + getDateMonth(date) + "-" + getDateYear(date));
    return LocalDate.of(getDateYear(date), getDateMonth(date), getDayOfMonth(date));
  }
  
  /**
   * Convert a {@code FlexDate} to a {@code LocalDate}.
   * 
   * @param flexDate the {@code FlexDate} to be converted. All three fields (year, month and day) of this {@code flexDate} have to be set.
   * @return a {@code LocalDate} corresponding to the {@code flexDate}.
   */
  public static LocalDate flexDateToLocalDate(FlexDate flexDate) {
    return LocalDate.of(flexDate.getYear(), flexDate.getMonth() + 1, flexDate.getDay());
  }
  
  /**
   * Create a Date.
   * @param day day of the month, range 1 .. 31
   * @param month month, range 1 .. 12
   * @param year the year
   * @return
   */
  public static Date createDate(int day, int month, int year) {
    return (new GregorianCalendar(year, month - 1, day)).getTime();
  }
  
  /**
   * Get the year value of a <code>Date</code>
   * @param date the <code>Date</code>
   * @return the year value of <code>date</code>
   */
  public static int getDateYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.YEAR);
  }
  
  /**
   * Get the month value of a <code>Date</code>
   * @param date the <code>Date</code>
   * @return the month value of <code>date</code>
   */
  public static int getDateMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.MONTH) + 1;
  }
  
  /**
   * Get the day of the month value of a <code>Date</code>
   * @param date the <code>Date</code>
   * @return the day of the month value of <code>date</code>
   */
  public static int getDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
  }
  
  /**
   * Create a copy of a <code>Date</code>, but where the time on that date is zero.
   * @param date the <code>Date</code> to make a 'copy' of .
   * @returna a copy of <code>date</code>, but where the time on that date is zero.
   */
  public static Date createDateWithoutTimeFromDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    
    return createDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
  }
  
  /**
   * Get the number of full years in a specific period.
   * 
   * @param periodStart the first <code>Date</code> of the period.
   * @param periodEnd the last <code>Date</code> of the period.
   * @return the number of full years in the period from <code>periodStart</code> until (including) <code>periodEnd</code>.
   */
  public static int fullYearsInPeriod(Date periodStart, Date periodEnd) {
    return fullYearsInPeriod(dateToLocalDate(periodStart), dateToLocalDate(periodEnd));
  }
  
  /**
   * Get the number of full years in a specific period.
   * 
   * @param periodStart the first <code>LocalDate</code> of the period.
   * @param periodEnd the last <code>LocalDate</code> of the period.
   * @return the number of full years in the period from <code>periodStart</code> until (including) <code>periodEnd</code>.
   */
  public static int fullYearsInPeriod(LocalDate periodStart, LocalDate periodEnd) {
    int years = periodStart.getYear() - periodEnd.getYear();
    LOGGER.fine("years = " + years);
    
    if (periodStart.plusYears(years).isAfter(periodEnd)) {
      LOGGER.fine("decrementing years.");
      years--;
    }
    
    return years;
  }

  /**
   * Get the year fraction of a specific period.
   * 
   * @param periodStart the first <code>LocalDate</code> of the period.
   * @param periodEnd the last <code>LocalDate</code> of the period.
   * @return the fraction of a year, as a value between 0 and 1, for the specified period.
   */
  public static double getYearFraction(LocalDate periodStart, LocalDate periodEnd) {    
    double days;
    int startDay = periodStart.getDayOfYear();
    int endDay = periodEnd.getDayOfYear();
    LOGGER.fine("startDay = " + startDay + ", endDay = " + endDay);
    if (startDay <= endDay) {
      days = endDay - startDay;
    } else {
      days = endDay + (365 - startDay);
    }
    
    double fraction = days / 365;
    LOGGER.fine("fraction = " + fraction);
    return fraction;
  }
  
  /**
   * Get the remaining fraction of a month.
   * 
   * @param startDate the start date of the fraction.
   * @return the fraction of the month, as a value between 0 and 1.
   */
  public static double getRemainingMonthFraction(Date startDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);
    double daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    double dayInMonth = calendar.get(Calendar.DAY_OF_MONTH);
    double fraction = (daysInMonth - dayInMonth + 1) / daysInMonth;
    
    LOGGER.fine("<= " + fraction);
    
    return fraction;
  }
}
