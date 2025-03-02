package goedegep.util.moneytest;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;


public class PgCurrencyFormatTest {

  @Test
  public void parseTest() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    PgCurrency currency;
    
    currency = cf.parse("678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, 67800), currency);
    
    currency = cf.parse("-678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, -67800), currency);
    
    currency = cf.parse("- 678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, -67800), currency);
    
    currency = cf.parse("e678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, 67800), currency);
    
    currency = cf.parse("f678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.GUILDER, 67800), currency);
    
    currency = cf.parse("e 678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, 67800), currency);
    
    currency = cf.parse("f 678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.GUILDER, 67800), currency);
    
    currency = cf.parse("678,12");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, 67812), currency);
    
    currency = cf.parse("678,1234");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, 6781234, 10000), currency);
    
    currency = cf.parse("- e 678,12");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, -67812), currency);
    
    currency = cf.parse("e -678,34");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, -67834), currency);
    
    currency = cf.parse("€678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.EURO, 67800), currency);
    
    currency = cf.parse("ƒ678");
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.GUILDER, 67800), currency);
    
    currency = cf.parse("ƒ7,5", 100);
    assertEquals("Wrong currency", new PgCurrency(PgCurrency.GUILDER, 750), currency);
  }
  
  @Test(expected=ParseException.class)
  public void parseTwoSignsExceptionTest() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("-e -678,34");
  }
  
  @Test(expected=ParseException.class)
  public void parseEuroGuilderExceptionTest() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("ef -678,34");
  }
  
  @Test(expected=ParseException.class)
  public void parseGuilderEuroExceptionTest() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("fe -678,34");
  }
  
  @Test(expected=ParseException.class)
  public void parseIllegalCharacterExceptionTest1() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("g -678,34");
  }
  
  @Test(expected=ParseException.class)
  public void parseIllegalCharacterExceptionTest2() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("e -67x,34");
  }
  
  @Test(expected=ParseException.class)
  public void parseNoDigitsBeforeCommaExceptionTest() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("e ,34");
  }
  
  @Test(expected=ParseException.class)
  public void parseNoDigitsExceptionTest() throws ParseException {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.parse("e ");
  }

  @Test
  public void formatTest() {
    PgCurrencyFormat cfDefault = new PgCurrencyFormat();
    PgCurrencyFormat cfMinimumLength = new PgCurrencyFormat(10);
    PgCurrencyFormat cfMinimumLengthAlwaysSign = new PgCurrencyFormat(11, true, true);
    PgCurrencyFormat cfMinimumLengthAlwaysSignPosition = new PgCurrencyFormat(11, false, true);
    PgCurrencyFormat cfAscii = new PgCurrencyFormat(0, false, false, true);
    Object           positiveAmount = new PgCurrency(PgCurrency.EURO, 123456);
    PgCurrency       negativeAmount = new PgCurrency(PgCurrency.EURO, -12345);
    PgCurrency       guilderAmount = new PgCurrency(PgCurrency.GUILDER, 2345678);
    PgCurrency       factor1Amount = new PgCurrency(PgCurrency.GUILDER, 2345678, 1);
    PgCurrency       factor100000Amount = new PgCurrency(PgCurrency.EURO, 2345678, 100000);
    
    // For null and no explicit length an empty string is expected.
    assertEquals("Wrong output string", "", cfDefault.format(null));    
    assertEquals("Wrong output string", "----------", cfMinimumLength.format(null));
    
    assertEquals("Wrong output string", "€1234,56", cfDefault.format(positiveAmount));
    assertEquals("Wrong output string", "€  1234,56", cfMinimumLength.format(positiveAmount));
    assertEquals("Wrong output string", "+€  1234,56", cfMinimumLengthAlwaysSign.format(positiveAmount));
    assertEquals("Wrong output string", " €  1234,56", cfMinimumLengthAlwaysSignPosition.format(positiveAmount));
    assertEquals("Wrong output string", "e1234,56", cfAscii.format(positiveAmount));
    
    assertEquals("Wrong output string", "-€123,45", cfDefault.format(negativeAmount));
    assertEquals("Wrong output string", "-€  123,45", cfMinimumLength.format(negativeAmount));
    assertEquals("Wrong output string", "-€   123,45", cfMinimumLengthAlwaysSign.format(negativeAmount));
    assertEquals("Wrong output string", "-€   123,45", cfMinimumLengthAlwaysSignPosition.format(negativeAmount));
    assertEquals("Wrong output string", "-e123,45", cfAscii.format(negativeAmount));
    
    assertEquals("Wrong output string", "ƒ23456,78", cfDefault.format(guilderAmount));
    assertEquals("Wrong output string", "ƒ 23456,78", cfMinimumLength.format(guilderAmount));
    assertEquals("Wrong output string", "+ƒ 23456,78", cfMinimumLengthAlwaysSign.format(guilderAmount));
    assertEquals("Wrong output string", " ƒ 23456,78", cfMinimumLengthAlwaysSignPosition.format(guilderAmount));
    assertEquals("Wrong output string", "f23456,78", cfAscii.format(guilderAmount));
    
    assertEquals("Wrong output string", "ƒ2345678", cfDefault.format(factor1Amount));
    assertEquals("Wrong output string", "ƒ  2345678", cfMinimumLength.format(factor1Amount));
    assertEquals("Wrong output string", "+ƒ  2345678", cfMinimumLengthAlwaysSign.format(factor1Amount));
    assertEquals("Wrong output string", " ƒ  2345678", cfMinimumLengthAlwaysSignPosition.format(factor1Amount));
    assertEquals("Wrong output string", "f2345678", cfAscii.format(factor1Amount));
    
    assertEquals("Wrong output string", "€23,45678", cfDefault.format(factor100000Amount));
    assertEquals("Wrong output string", "€ 23,45678", cfMinimumLength.format(factor100000Amount));
    assertEquals("Wrong output string", "+€ 23,45678", cfMinimumLengthAlwaysSign.format(factor100000Amount));
    assertEquals("Wrong output string", " € 23,45678", cfMinimumLengthAlwaysSignPosition.format(factor100000Amount));
    assertEquals("Wrong output string", "e23,45678", cfAscii.format(factor100000Amount));
  }

  @Test(expected=IllegalArgumentException.class)
  public void formatIllegalCurrencyExceptionTest() {
    PgCurrencyFormat cf = new PgCurrencyFormat();
    cf.format(new PgCurrency(88, 123));
  }
}
