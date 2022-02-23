package goedegep.util.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class QuarterTest {
  Quarter FIRST_2008 = new Quarter(2008, 1);
  Quarter SECOND_2008 = new Quarter(2008, 2);
  Quarter SECOND_2008_2 = new Quarter(2008, 2);
  Quarter THIRD_2008 = new Quarter(2008, 3);
  Quarter FOURTH_2008 = new Quarter(2008, 4);
  Quarter FIRST_2009 = new Quarter(2009, 1);
  
  @Test(expected=IllegalArgumentException.class)
  public void constructorExceptionTest0() {
    new Quarter(2008, -1);
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void constructorExceptionTest5() {
    new Quarter(2008, 5);
  }
  
  @Test
  public void getEndDateTest() {
    for (EndDate endDate: EndDate.values()) {
      LocalDate quarterEndDate = endDate.getQuarter().getEndDate();
      assertEquals("Wrong day of month in end date", endDate.getEndDay(), quarterEndDate.getDayOfMonth());
      assertEquals("Wrong end month in end date", endDate.getEndMonth(), quarterEndDate.getMonth());
      assertEquals("Wrong end year in end date", endDate.getEndYear(), quarterEndDate.getYear());
    }
  }
  
  @Test
  public void compareToTest() {
    // First test compare with itself.
    assertEquals("Wrong compare result", 0, FIRST_2008.compareTo(FIRST_2008));

    assertEquals("Wrong compare result", 0, SECOND_2008.compareTo(SECOND_2008_2));
    assertEquals("Wrong compare result", -1, FIRST_2008.compareTo(SECOND_2008));
    assertEquals("Wrong compare result", 1, SECOND_2008.compareTo(FIRST_2008));
    assertEquals("Wrong compare result", 1, FIRST_2009.compareTo(FOURTH_2008));
    assertEquals("Wrong compare result", -1, FIRST_2008.compareTo(FIRST_2009));
  }
  
  @Test
  public void afterTest() {
    // First test compare with itself.
    assertFalse("Wrong compare result", FIRST_2008.after(FIRST_2008));

    assertFalse("Wrong compare result", SECOND_2008.after(SECOND_2008_2));
    assertFalse("Wrong compare result", FIRST_2008.after(SECOND_2008));
    assertTrue("Wrong compare result", SECOND_2008.after(FIRST_2008));
    assertTrue("Wrong compare result", FIRST_2009.after(FOURTH_2008));
    assertFalse("Wrong compare result", FIRST_2008.after(FIRST_2009));
  }
  
  @Test
  public void getPreviousQuarterForDateTest() {
    for (KnownQuarter knownQuarter: KnownQuarter.values()) {
      LocalDate date = LocalDate.of(knownQuarter.getYear(), knownQuarter.getMonth(), knownQuarter.getDay());
      Quarter quarter = Quarter.getPreviousQuarterForDate(date);
      assertEquals("Wrong year", knownQuarter.getPreviousQuarter().getYear(), quarter.getYear());
      assertEquals("Wrong quarter", knownQuarter.getPreviousQuarter().getQuarter(), quarter.getQuarter());
    }
  }

  private enum KnownQuarter {
    KQ01(2, Month.JANUARY, 2009, new Quarter(2008, 4)),
    KQ02(28, Month.FEBRUARY, 1980, new Quarter(1979, 4)),
    KQ03(1, Month.MARCH, 2002, new Quarter(2001, 4)),
    KQ04(14, Month.APRIL, 2002, new Quarter(2002, 1)),
    KQ05(31, Month.MAY, 2040, new Quarter(2040, 1)),
    KQ06(20, Month.JUNE, 2078, new Quarter(2078, 1)),
    KQ07(7, Month.JULY, 2008, new Quarter(2008, 2)),
    KQ08(27, Month.AUGUST, 2009, new Quarter(2009, 2)),
    KQ09(5, Month.SEPTEMBER, 2009, new Quarter(2009, 2)),
    KQ10(5, Month.OCTOBER, 2009, new Quarter(2009, 3)),
    KQ11(3, Month.NOVEMBER, 2009, new Quarter(2009, 3)),
    KQ12(30, Month.DECEMBER, 2009, new Quarter(2009, 3));
    
    private int day;
    private Month month;
    private int year;
    private Quarter previousQuarter;
    private KnownQuarter(int day, Month month, int year, Quarter previousQuarter) {
      this.day = day;
      this.month = month;
      this.year = year;
      this.previousQuarter = previousQuarter;
    }
    public int getDay() {
      return day;
    }
    public Month getMonth() {
      return month;
    }
    public int getYear() {
      return year;
    }
    public Quarter getPreviousQuarter() {
      return previousQuarter;
    }    
  }
  
  private enum EndDate {
    FIRST(new Quarter(2008, 1), 31, Month.MARCH, 2008),
    SECOND(new Quarter(1980, 2), 30, Month.JUNE, 1980),
    THIRD(new Quarter(2008, 3), 30, Month.SEPTEMBER, 2008),
    FOURTH(new Quarter(2008, 4), 31, Month.DECEMBER, 2008);
    
    private Quarter quarter;
    private int endDay;
    private Month endMonth;
    private int endYear;
    
    private EndDate(Quarter quarter, int endDay, Month endMonth, int endYear) {
      this.quarter = quarter;
      this.endDay = endDay;
      this.endMonth = endMonth;
      this.endYear = endYear;
    }
    public Quarter getQuarter() {
      return quarter;
    }
    public int getEndDay() {
      return endDay;
    }
    public Month getEndMonth() {
      return endMonth;
    }
    public int getEndYear() {
      return endYear;
    }    
  }
}