package goedegep.util.datetime;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;


/**
 * This class is a {@link Format} for a {@link FlexDate}.
 * <p>
 * Properties:
 * <ul>
 * <li>alwaysPrint2DigitDayAndMonth<br/>
 * If true the day and month are always printed with 2 digits.
 * </li>
 * <li>reversedDate<br/>
 * If true the date is printed in reversed order.
 * </li>
 * </ul>
 * 
 */
@SuppressWarnings("serial")
public class FlexDateFormat extends Format {
  private boolean alwaysPrint2DigitDayAndMonth = true;
  private boolean reversedDate = false;
  
  /**
   * Constructor where <code>alwaysPrint2DigitDayAndMonth</code> is set to true and <code>reversedDate</code> is set to false.
   */
  public FlexDateFormat() {
    this(true);
  }
  
  public FlexDateFormat(boolean alwaysPrint2DigitDayAndMonth) {
    this(alwaysPrint2DigitDayAndMonth, false);
  }
  
  public FlexDateFormat(boolean alwaysPrint2DigitDayAndMonth, boolean reversedDate) {
    this.alwaysPrint2DigitDayAndMonth = alwaysPrint2DigitDayAndMonth;
    this.reversedDate = reversedDate;
  }
  
  @Override
  public FlexDate parseObject(String dateString) throws ParseException {
    return parse(dateString);
  }
  
  public FlexDate parse(String dateString) throws ParseException {
    if (reversedDate) {
      return parseReversed(dateString);
    } else {
      return parseNormal(dateString);
    }
  }

  private FlexDate parseNormal(String dateString) throws ParseException {
    
    // scan backwards, as year is always there
    int index = dateString.length() - 1;
    char    c;
    int factor;

    // parse 4 digits year
    int year = 0;
    factor = 1;
    for (int i = 1; i <= 4; i++) {
      if (index < 0) {
        throw new ParseException("Te weinig cijfer in jaartal in datum tekst: " + dateString, 0);
      }
      
      c = dateString.charAt(index--);
      if (!Character.isDigit(c)) {
        throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index + 2) + " in datum tekst: " + dateString, 0);
      }
      
      year += factor * Character.getNumericValue(c);
      factor *= 10;
    }
    
    if (index == -1) {
      return new FlexDate(year);
    }
    
    c = dateString.charAt(index--);
    if (c != '-') {
      throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index + 2) + " in datum tekst: " + dateString, 0);
    }
    
    // parse 1 or 2 digits month
    int month = 0;
    factor = 1;
    for (int i = 1; i <= 2; i++) {
      if (index < 0) {
        if (i == 1) {
          throw new ParseException("Te weinig cijfers in maand in datum tekst: " + dateString, 0);
        } else {
          break;
        }
      }
      
      c = dateString.charAt(index--);
      if (i == 2  &&  c == '-') {
        index++;
        break;
      }
      if (!Character.isDigit(c)) {
        throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index + 2) + " in datum tekst: " + dateString, 0);
      }
      
      month += factor * Character.getNumericValue(c);
      factor *= 10;
    }
    
    if (index == -1) {
      return new FlexDate(month - 1, year);
    }
    
    c = dateString.charAt(index--);
    if (c != '-') {
      throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index + 2) + " in datum tekst: " + dateString, 0);
    }
    
    // parse 2 digits day
    int day = 0;
    factor = 1;
    for (int i = 1; i <= 2; i++) {
      if (index < 0) {
        if (i == 1) {
          throw new ParseException("Te weinig cijfers in dag in datum tekst: " + dateString, 0);
        } else {
          break;
        }
      }
      
      c = dateString.charAt(index--);
      if (!Character.isDigit(c)) {
        throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index + 2) + " in datum tekst: " + dateString, 0);
      }
      
      day += factor * Character.getNumericValue(c);
      factor *= 10;
    }
    
    if (index == -1) {
      return new FlexDate(day, month - 1, year);
    } else {
      throw new ParseException("Te veel tekens in datum tekst: " + dateString, 0);
    }
  }

  private FlexDate parseReversed(String dateString) throws ParseException {
    
    // scan forward, as year is always there
    int index = 0;
    char    c;

    // parse 4 digits year
    int year = 0;
    for (int i = 1; i <= 4; i++) {
      if (index >= dateString.length()) {
        throw new ParseException("Te weinig cijfer in jaartal in datum tekst: " + dateString, 0);
      }
      
      c = dateString.charAt(index++);
      if (!Character.isDigit(c)) {
        throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index - 1) + " in datum tekst: " + dateString, 0);
      }
      
      year = 10 * year + Character.getNumericValue(c);
    }
    
    if (index == dateString.length()) {
      return new FlexDate(year);
    }
    
    c = dateString.charAt(index++);
    if (c != '-') {
      throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index - 1) + " in datum tekst: " + dateString, 0);
    }
    
    // parse 1 or 2 digits month
    int month = 0;
    for (int i = 1; i <= 2; i++) {
      if (index >= dateString.length()) {
        if (i == 1) {
          throw new ParseException("Te weinig cijfers in maand in datum tekst: " + dateString, 0);
        } else {
          break;
        }
      }
      
      c = dateString.charAt(index++);
      if (i == 2  &&  c == '-') {
        index++;
        break;
      }
      if (!Character.isDigit(c)) {
        throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index - 1) + " in datum tekst: " + dateString, 0);
      }
      
      month = 10 * month + Character.getNumericValue(c);
    }
    
    if (index == dateString.length()) {
      return new FlexDate(month - 1, year);
    }
    
    c = dateString.charAt(index++);
    if (c != '-') {
      throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index - 1) + " in datum tekst: " + dateString, 0);
    }
    
    // parse 2 digits day
    int day = 0;
    for (int i = 1; i <= 2; i++) {
      if (index >= dateString.length()) {
        if (i == 1) {
          throw new ParseException("Te weinig cijfers in dag in datum tekst: " + dateString, 0);
        } else {
          break;
        }
      }
      
      c = dateString.charAt(index++);
      if (!Character.isDigit(c)) {
        throw new ParseException("Ongeldig teken '" + c + "' op positie " + (index - 1) + " in datum tekst: " + dateString, 0);
      }
      
      day = 10 * day + Character.getNumericValue(c);
    }
    
    if (index == dateString.length()) {
      return new FlexDate(day, month - 1, year);
    } else {
      throw new ParseException("Te veel tekens in datum tekst: " + dateString, 0);
    }
  }

  @Override
  public StringBuffer format(Object object, StringBuffer buf, FieldPosition arg2) {
    
    if (reversedDate) {
      return formatReversed(object, buf);
    } else {
      return formatNormal(object, buf);
    }
  }

  private StringBuffer formatNormal(Object object, StringBuffer buf) {
    
    FlexDate flexDate = (FlexDate) object;
    
    if (flexDate == null) {
      return buf;
    }
    
    if (flexDate.isDaySet()) {
      int day = flexDate.getDay();
      if (alwaysPrint2DigitDayAndMonth  &&  (day < 10)) {
        buf.append('0');
      }
      buf.append(day);
      buf.append('-');
    }
    
    if (flexDate.isMonthSet()) {
      int month = flexDate.getMonth() + 1;
      if (alwaysPrint2DigitDayAndMonth  &&  (month < 10)) {
        buf.append('0');
      }
      buf.append(month);
      buf.append('-');
    }
    
    buf.append(flexDate.getYear());
    
    return buf;
  }

  private StringBuffer formatReversed(Object object, StringBuffer buf) {
    
    FlexDate flexDate = (FlexDate) object;
    
    if (flexDate == null) {
      return buf;
    }
    
    buf.append(flexDate.getYear());

    if (flexDate.isMonthSet()) {
      buf.append('-');
      int month = flexDate.getMonth() + 1;
      if (alwaysPrint2DigitDayAndMonth  &&  (month < 10)) {
        buf.append('0');
      }
      buf.append(month);
    }
    
    if (flexDate.isDaySet()) {
      buf.append('-');
      int day = flexDate.getDay();
      if (alwaysPrint2DigitDayAndMonth  &&  (day < 10)) {
        buf.append('0');
      }
      buf.append(day);
    }
    
    return buf;
  }

  @Override
  public Object parseObject(String arg0, ParsePosition arg1) {
    // TODO Auto-generated method stub
    return null;
  }
}
