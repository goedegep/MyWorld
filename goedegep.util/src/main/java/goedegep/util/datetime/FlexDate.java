package goedegep.util.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * This class provides a flexible date representation.
 * <p>
 * The fields of a FlexDate are:
 * <ul>
 * <li>
 * day<br/>
 * The day in a month (range 1 .. 31).
 * </li>
 * <li>
 * month<br/>
 * The month in a year (range 0 .. 11).
 * </li>
 * <li>
 * year<br/>
 * The year.
 * </li>
 * </ul>
 * A FlexDate can hold:
 * <ul>
 * <li>
 * year<br/>
 * To specify only a year
 * </li>
 * <li>
 * month and year<br/>
 * To specify a month in a specific year
 * </li>
 * <li>
 * day and month and year<br/>
 * To specify a complete (normal) date.
 * </li>
 * </ul>
 */
public class FlexDate implements Cloneable, Comparable<FlexDate> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FlexDate.class.getName());

  private GregorianCalendar calendar;
  private Integer month = null;
  private Integer year = null;
  
  /**
   * Constructor to create a FlexDate where only the year is specified.
   * 
   * @param year The year for which a FlexDate is created.
   */
  public FlexDate(Integer year) {
    this(null, null, year);
  }
  
  /**
   * Constructor to create a FlexDate where the year and month are specified.
   * 
   * @param month A month within the year. Values are from 0 (january) to 11 (december). 
   * @param year The year for which a FlexDate is created.
   */
  public FlexDate(Integer month, Integer year) {
    this(null, month, year);
  }
  
  /**
   * Constructor to create a Flexdate where year, month and day are specified.
   * 
   * @param day A day within the month. Values are from 1 to 31.
   * @param month A month within the year. Values are from 0 (january) to 11 (december).
   * @param year The year for which a FlexDate is created.
   */
  public FlexDate(Integer day, Integer month, Integer year) {
    if (year == null) {
      throw new IllegalArgumentException("The argument 'year' may not be null");
    }
    
    if ((day != null)  &&  (month == null)) {
      throw new IllegalArgumentException("The argument 'month' may not be null, if the 'day' isn't null");
    }
    
    if (day != null  &&  month != null) {
      calendar = new GregorianCalendar(year, month, day);
    } else {
      this.month = month;
      this.year = year;
    }
  }
  
  /**
   * Constructor to create FlexDate from a {@link LocalDate}.
   * 
   * @param localDate the LocalDate to create a FlexDate from.
   */
  public FlexDate(LocalDate localDate) {
    this(localDate.getDayOfMonth(), localDate.getMonthValue() - 1, localDate.getYear());
  }
  
  /**
   * Check whether the 'day' is set.
   * <p>
   * If 'day' is set, then it is a complete date ('month' and 'year' are then, by definition, also set).
   * 
   * @return true if the 'day' is set, false otherwise.
   */
  public boolean isDaySet() {
    return calendar != null;
  }
  
  /**
   * Get the 'day' of this FlexDate.
   * 
   * @return the 'day' of this FlexDate, or null if the 'day' isn't set.
   */
  public Integer getDay() {
    if (calendar == null) {
      return null;
    } else {
      return calendar.get(Calendar.DAY_OF_MONTH);
    }
  }
  
  /**
   * Check whether the 'month' is set.
   * 
   * @return true if the 'month' is set, false otherwise.
   */
  public boolean isMonthSet() {
    return (month != null)  ||  (calendar != null);
  }
  
  
  /**
   * Get the 'month' of this FlexDate.
   * 
   * @return the 'month' of this FlexDate, or null is the 'month' isn't set.
   */
  public Integer getMonth() {
    if (calendar == null) {
      return month;
    } else {
      return calendar.get(Calendar.MONTH);
    }
  }
  
  
  /**
   * Get the 'year' of this FlexDate.
   * 
   * @return the 'year' of this FlexDate, which is never null.
   */
  public Integer getYear() {
    if (calendar == null) {
      return year;
    } else {
      return calendar.get(Calendar.YEAR);
    }
  }
  
  /**
   * Check whether this is a full date; year, month and day are set.
   * 
   * @return true if this is a full date.
   */
  public boolean isCompleteDate() {
    return isDaySet();
  }

  /**
   * Compare this FlexDate to another FlexDate.
   * <p>
   * Sorting is such that incomplete dates are shown first, so january 2020 comes before 23th of january 2020.
   * 
   * @param o the FlexDate to compare to.
   * @return the value 0 if the date represented by the argument is equal to this FlexDate;
   *         a valueless than 0 if this FlexDate is before the date represented by the argument;
   *         and a value greater than 0 if this FlexDate is after the date represented by the argument.
   */
  public int compareTo(FlexDate o) {
    if (calendar != null) {
      if (o.calendar != null) {
        return calendar.compareTo(o.calendar);
      } else {
        return compareFullDateToPartialDate(o);
      }
    } else {
      if (o.calendar != null) {
        return comparePartialDateToFullDate(o);
      } else {
        return comparePartialDateToPartialDate(o);
      }
    }
  }
  
  /**
   * Compare a complete FlexDate to a partial FlexDate.
   * 
   * @param o the FlexDate to compare to.
   * @return the value 0 if the date represented by the argument is equal to this FlexDate;
   *         a valueless than 0 if this FlexDate is before the date represented by the argument;
   *         and a value greater than 0 if this FlexDate is after the date represented by the argument.
   */
  private int compareFullDateToPartialDate(FlexDate o) {
    if (o.year > calendar.get(Calendar.YEAR)) {
      return -1;
    } else if (o.year == calendar.get(Calendar.YEAR)) {
      if (o.month != null) {
        if (o.month > calendar.get(Calendar.MONTH)) {
          return -1;
        } else {
          return 1;
        }
      } else {
        return 1;
      }
    } else {
      return 1;
    }
  }
  
  /**
   * Compare a partial FlexDate to a complete FlexDate.
   * 
   * @param o the FlexDate to compare to.
   * @return the value 0 if the date represented by the argument is equal to this FlexDate;
   *         a valueless than 0 if this FlexDate is before the date represented by the argument;
   *         and a value greater than 0 if this FlexDate is after the date represented by the argument.
   */
  private int comparePartialDateToFullDate(FlexDate o) {
    if (year > o.calendar.get(Calendar.YEAR)) {
      return 1;
    } else if (year == o.calendar.get(Calendar.YEAR)) {
      if (month != null) {
        if (month > o.calendar.get(Calendar.MONTH)) {
          return 1;
        } else {
          return -1;
        }
      } else {
        return -1;
      }
    } else {
      return -1;
    }
  }

  /**
   * Compare a partial FlexDate to a partial FlexDate.
   * 
   * @param o the FlexDate to compare to.
   * @return the value 0 if the date represented by the argument is equal to this FlexDate;
   *         a valueless than 0 if this FlexDate is before the date represented by the argument;
   *         and a value greater than 0 if this FlexDate is after the date represented by the argument.
   */
  private int comparePartialDateToPartialDate(FlexDate o) {
    if (year.intValue() > o.year.intValue()) {
      return 1;
    } else if (year.intValue() == o.year.intValue()) {
      if (month != null) {
        if (o.month != null) {
          if (month.intValue() > o.month.intValue()) {
            return 1;
          } else if (month.intValue() == o.month.intValue()) {
            return 0;
          } else {
            return -1;
          }
        } else {
          return -1;
        }
      } else {
        if (o.month != null) {
          return -1;
        } else {
          return 0;
        }
      }
    } else {
      return -1;
    }
  }
  
  /**
   * Get a copy of this FlexDate.
   * 
   * @return a copy of this FlexDate.
   */
  public FlexDate clone() {
    try {
      return (FlexDate) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (getClass() != object.getClass()) {
      return false;
    }
    
    FlexDate flexDate = (FlexDate) object;
    return (compareTo(flexDate) == 0);
  }

  @Override
  public int hashCode() {
      return Objects.hash(calendar, month, year);
  }
  
  /**
   * Checks whether this <code>FlexDate</code> is a specialization of a given other date.
   * <p>
   * A specialization means that this date has at least fields filled in which are filled in the other FlexDate, and that the
   * values of these fields are the same. This date may have more fields filled in (which makes it more specialized).
   * 
   * @param flexDate the <code>FlexDate</code> to compare
   * @return true if <code>flexDate</code> is a specialization of this FlexDate, false otherwise
   */
  public boolean isSpecializationOf(FlexDate flexDate) {
    // Year must always be there.
    if (getYear().intValue() != flexDate.getYear().intValue()) {
      return false;
    }
    
    if (isMonthSet()) {
      if (flexDate.isMonthSet()  &&  (month.intValue() != flexDate.getMonth().intValue())) {
        return false;
      }
    }
    
    if (isDaySet()) {
      if (flexDate.isDaySet()  &&  !calendar.equals(flexDate.calendar)) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Checks whether this <code>FlexDate</code> is a generalization of a given other date.
   * <p>
   * A generalization means that this date may have less fields filled in than the given date, but the fields
   * which are filled in must be equal.
   * 
   * @param flexDate the <code>FlexDate</code> to compare
   * @return true if <code>flexDate</code> is a generalization of this FlexDate, false otherwise
   */
  public boolean isGeneralizationOf(FlexDate flexDate) {
    // Year must always be there.
    if (year.intValue() != flexDate.getYear().intValue()) {
      return false;
    }
    
    if (isMonthSet()) {
      if (!flexDate.isMonthSet()  ||  (month.intValue() != flexDate.getMonth().intValue())) {
        return false;
      }
    }
    
    if (isDaySet()) {
      if (!flexDate.isDaySet()  ||  !calendar.equals(flexDate.calendar)) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Create a {@link LocalDateTime} for this FlexDate.
   * <p>
   * The date part is filled from this FlexDate, the hour, minutes and second are set to 0.
   * 
   * @return a <code>LocalDateTime</code> for this FlexDate, or null if this FlexDate isn't complete.
   */
  public LocalDateTime toLocalDateTime() {
    if (isCompleteDate()) {
      return LocalDateTime.of(getYear(), getMonth() + 1, getDay(), 0, 0, 0);
    } else {
      return null;
    }
  }
  
  /**
   * Create a {@link Date} for this FlexDate.
   * <p>
   * The date part is filled from this FlexDate, the hour, minutes and second are set to 0.
   * 
   * @return a <code>Date</code> for this FlexDate, or null if this FlexDate isn't complete.
   */
  public Date toDate() {
    if (isCompleteDate()) {
      return DateUtil.createDate(getDay(), getMonth() + 1, getYear());
    } else {
      return null;
    }
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    if (isDaySet()) {
      int day = getDay();
      if (day < 10) {
        buf.append('0');
      }
      buf.append(day);
      buf.append('-');
    }
    
    if (isMonthSet()) {
      int month = getMonth() + 1;
      if (month < 10) {
        buf.append('0');
      }
      buf.append(month);
      buf.append('-');
    }
    
    buf.append(getYear());
    
    return buf.toString();
  }
}
