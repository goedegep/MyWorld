package goedegep.util.datetime;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

/**
 * This class represents a quarter of a year.
 *
 */
public class Quarter implements Comparable<Quarter> {
  private final static int NUMBER_OF_MONTHS_IN_QUARTER = 3;
  Month m;
  
  private int year;
  private int quarter;  // 1, 2, 3 or 4
  
  /**
   * Constructor
   * 
   * @param year the calendar year for the quarter
   * @param quarter the quarter in {@code year}, as one of the values: 1, 2, 3 or 4.
   */
  public Quarter(int year, int quarter) {
    if (quarter < 1  ||  quarter > 4) {
      throw new IllegalArgumentException();
    }
    this.year = year;
    this.quarter = quarter;
  }
  
  /**
   * Get the year of the quarter.
   * 
   * @return the year of the quarter.
   */
  public int getYear() {
    return year;
  }
  
  /**
   * Get the quarter value of the quarter.
   * 
   * @return the quarter value within the year, as one of the values: 1, 2, 3 or 4.
   */
  public int getQuarter() {
    return quarter;
  }
  
  /**
   * Get the end date of the quarter.
   * 
   * @return return the last date of the quarter.
   */
  public LocalDate getEndDate() {
    int quarterEndMonthValue = NUMBER_OF_MONTHS_IN_QUARTER * quarter;
    Month quarterEndMonth = Month.of(quarterEndMonthValue);
    int lastDayOfEndMonth = quarterEndMonth.length(false);  // leap year is actually a 'don't care' is february isn't a quarter end month.
    
    return LocalDate.of(year, quarterEndMonth, lastDayOfEndMonth);
  }
  
  /*
   * Implementation of Comparable<Quarter>
   */
  @Override
  public int compareTo(Quarter quarter) {
    if (year < quarter.year) {
      return -1;
    } else if (year > quarter.year) {
      return 1;
    } else if (this.quarter < quarter.quarter) {
      return -1;
    } else if (this.quarter > quarter.quarter) {
      return 1;
    } else {
      return 0;
    }
  }
  
  public boolean after(Quarter quarter) {
    return this.compareTo(quarter) == 1;
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
        
    Quarter aQuarter = (Quarter) object;
    if ((year == aQuarter.year)  &&
        (quarter == aQuarter.quarter)) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(year, quarter);
  }
 
  public boolean isDateAfterQuarter(LocalDate date) {
    int year = date.getYear();
    int month = date.getMonthValue();
    int quarter = month / 3 + 1;  // +1 as quarters are in the range 1 - 4.

    if ((year > this.year)  ||
        (year == this.year  &&  quarter > this.quarter)) {
      return true;
    } else {
      return false;
    }
  }
  
  public Quarter getNextQuarter() {
    int year = this.year;
    int quarter = this.quarter;
    
    quarter++;
    
    if (quarter > 4) {
      year++;
      quarter = 1;
    }
    
    return new Quarter(year, quarter);    
  }
  
  public static Quarter getQuarterForDate(LocalDate date) {
    int year = date.getYear();
    int month = date.getMonthValue() - 1;
    int quarter = month / 3 + 1;  // +1 as quarters are in the range 1 - 4.
    return new Quarter(year, quarter);
  }
  
  public static Quarter getPreviousQuarterForDate(LocalDate date) {
    int year = date.getYear();
    int month = date.getMonthValue() - 1;
    // Month first has to be decremented, because we look for the previous quarter.
    // However, it is also incremented to use months in the range 1 - 12, i.s.o. 0 - 11.
    // So nothing is done here.
    int quarter = month / 3;
    if (quarter == 0) {
      quarter = 4;
      year--;
    }
    return new Quarter(year, quarter);
  }
}
