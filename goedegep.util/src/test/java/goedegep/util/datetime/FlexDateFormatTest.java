package goedegep.util.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;

public class FlexDateFormatTest {
  
  @Test
  public void parseYearOnlyTest() throws ParseException {
    FlexDateFormat flexDataFormat = new FlexDateFormat();
    FlexDate flexDate = flexDataFormat.parse("2011");
    assertFalse("dag ten onrechte ingevuld", flexDate.isDaySet());
    assertNull("dag ten onrechte ingevuld", flexDate.getDay());
    assertFalse("maand ten onrechte ingevuld", flexDate.isMonthSet());
    assertNull("maand ten onrechte ingevuld", flexDate.getMonth());
    assertEquals("verkeerd jaar", Integer.valueOf(2011), flexDate.getYear());
  }
  
  @Test
  public void parseMonthAndYearOnlyTest() throws ParseException {
    FlexDateFormat flexDataFormat = new FlexDateFormat();
    FlexDate flexDate = flexDataFormat.parse("09-2011");
    assertFalse("dag ten onrechte ingevuld", flexDate.isDaySet());
    assertNull("dag ten onrechte ingevuld", flexDate.getDay());
    assertTrue("maand niet ingevuld", flexDate.isMonthSet());
    assertEquals("verkeerde maand", Integer.valueOf(8), flexDate.getMonth());
    assertEquals("verkeerd jaar", Integer.valueOf(2011), flexDate.getYear());
    flexDate = flexDataFormat.parse("9-2011");
    assertFalse("dag ten onrechte ingevuld", flexDate.isDaySet());
    assertNull("dag ten onrechte ingevuld", flexDate.getDay());
    assertTrue("maand niet ingevuld", flexDate.isMonthSet());
    assertEquals("verkeerde maand", Integer.valueOf(8), flexDate.getMonth());
    assertEquals("verkeerd jaar", Integer.valueOf(2011), flexDate.getYear());
  }
  
  @Test
  public void parseFullDateTest() throws ParseException {
    FlexDateFormat flexDataFormat = new FlexDateFormat();
    FlexDate flexDate = flexDataFormat.parse("23-12-2011");
    assertTrue("dag niet ingevuld", flexDate.isDaySet());
    assertEquals("verkeerde dag", Integer.valueOf(23), flexDate.getDay());
    assertTrue("maand niet ingevuld", flexDate.isMonthSet());
    assertEquals("verkeerde maand", Integer.valueOf(11), flexDate.getMonth());
    assertEquals("verkeerd jaar", Integer.valueOf(2011), flexDate.getYear());
    flexDate = flexDataFormat.parse("01-9-2011");
    assertTrue("dag niet ingevuld", flexDate.isDaySet());
    assertEquals("verkeerde dag", Integer.valueOf(1), flexDate.getDay());
    assertTrue("maand niet ingevuld", flexDate.isMonthSet());
    assertEquals("verkeerde maand", Integer.valueOf(8), flexDate.getMonth());
    assertEquals("verkeerd jaar", Integer.valueOf(2011), flexDate.getYear());
  }
  
  @Test
  public void formatTest() {
    FlexDateFormat flexDataFormat = new FlexDateFormat();
    assertEquals("verkeerd formaat", "23-09-2011", flexDataFormat.format(new FlexDate(23, 8, 2011)));
    flexDataFormat = new FlexDateFormat(false);
    assertEquals("verkeerd formaat", "23-9-2011", flexDataFormat.format(new FlexDate(23, 8, 2011)));
  }
}
