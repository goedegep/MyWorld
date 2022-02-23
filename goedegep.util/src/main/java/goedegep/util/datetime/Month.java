package goedegep.util.datetime;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class is just still there for the utility method createMonthForDate.
 * If I don't use java.util.Date anymore, this class can be deleted.
 */
@Deprecated
public class Month {
  /**
   * Private constructor as this is (now) a utility class.
   */
  private Month() {
  }
  
  public static YearMonth createMonthForDate(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    
    return YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
  }
}
