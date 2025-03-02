package goedegep.util.moneytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;


public class PgCurrencyTest {
  private final PgCurrency money0 = new PgCurrency(PgCurrency.EURO, 0);
  private final PgCurrency money1 = new PgCurrency(PgCurrency.EURO, 3456);
  private final PgCurrency money2 = new PgCurrency(PgCurrency.EURO, 1234);
  private final PgCurrency money3 = new PgCurrency(PgCurrency.GUILDER, 1234);
  private final PgCurrency money4 = new PgCurrency(PgCurrency.EURO, 1234, 10000);
  private final PgCurrency money5 = new PgCurrency(PgCurrency.EURO, 307400000, 100000);
  
  /**
   * This method tests the method cloneCurrency.
   * The returned object shall be a different object, but with the same properties as the original.
   */
  @Test
  public void cloneTest() {
    PgCurrency money = money1.cloneCurrency();
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount(), money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    assertTrue("Not a different object", money1 != money);
  }

  /**
   * This method tests the method add.<p/>
   * It checks whether a normal addition result is correct.
   */
  @Test
  public void addTest() {
    PgCurrency money = money1.add(money2);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 4690, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method add.<p/>
   * It checks that an <b>IllegalArgumentException</b> is thrown if the values have different <b>currency units</b>.
   */
  @Test(expected=IllegalArgumentException.class)
  public void addIncompatibleCurrenciesExceptionTest() {
    money1.add(money3);
  }

  /**
   * This method tests the method add.<p/>
   * It checks that an <b>IllegalArgumentException</b> is thrown if the values have different <b>factors</b>.
   */
  @Test(expected=IllegalArgumentException.class)
  public void addIncompatibleFactorsExceptionTest() {
    money1.add(money4);
  }

  /**
   * This method tests the method addDifferentCurrencies.<p/>
   * It checks the result of calling this method with:
   * <ul>
   * <li>
   * two <b>null</b> values; which should return <b>null</b>.
   * </li>
   * <li>
   * one <b>null</b> value and one non-null value; which should return a copy of the non-null value. This is tested both ways.
   * </li>
   * <li>
   * two values with different <b>currency units</b>; which should return the sum with the specified <b>currency unit</b>.
   * </li>
   * </ul>
   */
  @Test
  public void addDifferentCurrenciesTest() {
    PgCurrency money = PgCurrency.addDifferentCurrencies(null, null, PgCurrency.EURO);
    assertNull("Result not null", money);
    
    money = PgCurrency.addDifferentCurrencies(money1, null, PgCurrency.EURO);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount(), money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    assertTrue("Not a different object", money1 != money);
    
    money = PgCurrency.addDifferentCurrencies(null, money1, PgCurrency.EURO);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount(), money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    assertTrue("Not a different object", money1 != money);
    
    money = PgCurrency.addDifferentCurrencies(money2, money3, PgCurrency.EURO);
    assertEquals("Wrong currency", PgCurrency.EURO, money.getCurrency());
    assertEquals("Wrong amount", 1234l + (long) (1234 / 2.20371d + 0.5d), money.getAmount());  
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }


  /**
   * This method tests the method subtract.<p/>
   * It tests subtractions with a positive and a negative result.
   */

  @Test
  public void subtractTest() {
    PgCurrency money = money1.subtract(money2);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 2222, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    money = money2.subtract(money1);
    assertEquals("Wrong currency", money2.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", -2222, money.getAmount());
    assertEquals("Wrong factor", money2.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method subtract.<p/>
   * It checks that an <b>IllegalArgumentException</b> is thrown if the values have different <b>currency units</b>.
   */
  @Test(expected=IllegalArgumentException.class)
  public void subtractIncompatibleCurrenciesExceptionTest() {
    money1.subtract(money3);
  }

  /**
   * This method tests the method subtract.<p/>
   * It checks that an <b>IllegalArgumentException</b> is thrown if the values have different <b>factors</b>.
   */
  @Test(expected=IllegalArgumentException.class)
  public void subtractIncompatibleFactorsExceptionTest() {
    money1.subtract(money4);
  }

  /**
   * This method tests the method multiply, using a <b>factor</b> of type <b>long</b>.
   */
  @Test
  public void multiplyLongTest() {
    PgCurrency money = money1.multiply(789L);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 2726784, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method multiply, using a <b>factor</b> of type <b>double</b>.
   */
  @Test
  public void multiplyDoubleTest() {
    PgCurrency money = money1.multiply(1.23d);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 4251, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }
  
  /**
   * This method tests the method multiply, using a <b>factor</b> of type <b>FixedPointValue</b>.
   */
  @Test
  public void multiplyFixedPointValueTest() {
    PgCurrency money = money2.multiply(new FixedPointValue(10, 100));
    assertEquals("Wrong currency", money2.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 123, money.getAmount());
    assertEquals("Wrong factor", money2.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method divide, using a <b>factor</b> of type <b>long</b>.
   */
  @Test
  public void divideLongTest() {
    PgCurrency money = money1.divide(78L);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 44, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method divide, using a <b>factor</b> of type <b>double</b>.
   */
  @Test
  public void divideDoubleTest() {
    PgCurrency money = money5.divide(102d);
    assertEquals("Wrong currency", money5.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 3013725, money.getAmount());
    assertEquals("Wrong factor", money5.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method divide, using a <b>factor</b> of type <b>FixedPointValue</b>.
   */
  @Test
  public void divideFixedPointValueTest() {
    PgCurrency money = money1.divide(new FixedPointValue(7800, 100));
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 44, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    
    money = money1.divide(new FixedPointValue(7700, 100));
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 45, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    
    money = money1.divide(new FixedPointValue(1234, 100));
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 280, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }

  /**
   * This method tests the method divide, using a <b>factor</b> of type <b>PgCurrency</b>.
   */
  @Test
  public void dividePgCurrencyTest() {
    FixedPointValue ratio = money1.divide(money2, money1.getFactor());
    assertEquals("Wrong amount", 280, ratio.getValue());
    assertEquals("Wrong factor", money1.getFactor(), ratio.getFactor());
  }
  
  /**
   * This method tests the method divide, using a <b>factor</b> of type <b>double</b>.
   */
  @Test
  public void divideWithRoundUpDoubleTest() {
    PgCurrency money = money5.divideWithRoundUp(102d);
    assertEquals("Wrong currency", money5.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 3013726, money.getAmount());
    assertEquals("Wrong factor", money5.getFactor(), money.getFactor());
  }

  @Test
  public void changeSignTest() {
    PgCurrency money = money1.changeSign();
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", -3456, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }

  @Test
  public void certifyCurrencyTest() {
    // First test with no change required, original object shall be returned.
    PgCurrency money = money1.certifyCurrency(PgCurrency.EURO);
    assertTrue("A different object", money1 == money);
    // Next test with a change required, so a new object shall be returned.
    money = money3.certifyCurrency(PgCurrency.EURO);
    assertTrue("Not a different object", money3 != money);
    assertEquals("Wrong currency", PgCurrency.EURO, money.getCurrency());
    assertEquals("Wrong amount", 560, money.getAmount());
    assertEquals("Wrong factor", money3.getFactor(), money.getFactor());
  }

  @Test
  public void changeCurrencyTest() {
    // From Euro's to Guilders.
    PgCurrency money = money1.changeCurrency(PgCurrency.GUILDER);
    assertEquals("Wrong currency", PgCurrency.GUILDER, money.getCurrency());
    assertEquals("Wrong amount", 7616, money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
    // From Guilders to Euro's.
    money = money3.changeCurrency(PgCurrency.EURO);
    assertEquals("Wrong currency", PgCurrency.EURO, money.getCurrency());
    assertEquals("Wrong amount", 560, money.getAmount());
    assertEquals("Wrong factor", money3.getFactor(), money.getFactor());
    // From Euro's to Euro's.
    money = money1.changeCurrency(PgCurrency.EURO);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount(), money.getAmount());
    assertEquals("Wrong factor", money1.getFactor(), money.getFactor());
  }

  @Test
  public void certifyFactorTest() {
    // First test with no change required, original object shall be returned.
    PgCurrency money = money1.certifyFactor(100);
    assertTrue("A different object", money1 == money);
    // Next test with a change required, so a new object shall be returned.
    money = money1.certifyFactor(1000);
    assertTrue("Not a different object", money1 != money);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount() * 10, money.getAmount());
    assertEquals("Wrong factor", 1000, money.getFactor());
  }

  @Test
  public void certifyMininalFactorTest() {
    // First test with no change required, original object shall be returned.
    PgCurrency money = money1.certifyMinimalFactor(100);
    assertTrue("A different object", money1 == money);
    // Next test with a change required, so a new object shall be returned.
    money = money1.certifyMinimalFactor(1000);
    assertTrue("Not a different object", money1 != money);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount() * 10, money.getAmount());
    assertEquals("Wrong factor", 1000, money.getFactor());
  }

  @Test
  public void changeFactorTest() {
    PgCurrency money = money1.changeFactor(1000);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", money1.getAmount() * 10, money.getAmount());
    assertEquals("Wrong factor", 1000, money.getFactor());
    money = money1.changeFactor(10);
    assertEquals("Wrong currency", money1.getCurrency(), money.getCurrency());
    assertEquals("Wrong amount", 346, money.getAmount());
    assertEquals("Wrong factor", 10, money.getFactor());
  }
  
  @Test
  public void roundTest() {
    PgCurrency moneyRounded = new PgCurrency(PgCurrency.EURO, 3500);
    
    PgCurrency money = new PgCurrency(PgCurrency.EURO, 3450);
    assertEquals("Wrong rounding", moneyRounded, money.round());
    
    money = new PgCurrency(PgCurrency.EURO, 3549);
    assertEquals("Wrong rounding", moneyRounded, money.round());
    
    money = new PgCurrency(PgCurrency.EURO, 3500);
    assertEquals("Wrong rounding", moneyRounded, money.round());
    
    moneyRounded = new PgCurrency(PgCurrency.EURO, 35000, 1000);
    
    money = new PgCurrency(PgCurrency.EURO, 34500, 1000);
    assertEquals("Wrong rounding", moneyRounded, money.round());
    
    money = new PgCurrency(PgCurrency.EURO, 35499, 1000);
    assertEquals("Wrong rounding", moneyRounded, money.round());
    
    money = new PgCurrency(PgCurrency.EURO, 35000, 1000);
    assertEquals("Wrong rounding", moneyRounded, money.round());
  }
  
  @Test
  public void roundUpTest() {
    PgCurrency moneyRounded = new PgCurrency(PgCurrency.EURO, 3500);
    
    PgCurrency money = new PgCurrency(PgCurrency.EURO, 3450);
    assertEquals("Wrong rounding", moneyRounded, money.roundUp());
    
    money = new PgCurrency(PgCurrency.EURO, 3401);
    assertEquals("Wrong rounding", moneyRounded, money.roundUp());
    
    money = new PgCurrency(PgCurrency.EURO, 3500);
    assertEquals("Wrong rounding", moneyRounded, money.roundUp());
    
    moneyRounded = new PgCurrency(PgCurrency.EURO, 35000, 1000);
    
    money = new PgCurrency(PgCurrency.EURO, 34500, 1000);
    assertEquals("Wrong rounding", moneyRounded, money.roundUp());
    
    money = new PgCurrency(PgCurrency.EURO, 34001, 1000);
    assertEquals("Wrong rounding", moneyRounded, money.roundUp());
    
    money = new PgCurrency(PgCurrency.EURO, 35000, 1000);
    assertEquals("Wrong rounding", moneyRounded, money.roundUp());
  }

  @Test
  public void isLessThanTest() {
    assertTrue("Not seen as less than while it should", money2.isLessThan(money1));
    assertFalse("Seen as less than while it shouldn't", money1.isLessThan(money2));
  }

  @Test
  public void isGreaterThanTest() {
    assertTrue("Not seen as greater than while it should", money1.isGreaterThan(money2));
    assertFalse("Seen as greater than while it shouldn't", money2.isGreaterThan(money1));
  }

  @Test
  public void isNegativeTest() {
    PgCurrency negative = money0.subtract(money1);
    assertTrue("Not seen as negative while it should", negative.isNegative());
    assertFalse("Seen as negative while it shouldn't", money1.isNegative());
  }

  @Test
  public void isZeroTest() {
    assertTrue("Not seen as zero while it should", money0.isZero());
    assertFalse("Seen as zero while it shouldn't", money1.isZero());
  }

  @Test
  public void compareToTest() {
    PgCurrency money = money1.cloneCurrency();
    assertEquals("Not seen as equal", 0, money.compareTo(money1));
    assertEquals("Not seen as equal", 0, money1.compareTo(money));
    money = money.add(new PgCurrency(money.getCurrency(), 1));
    assertEquals("Not seen as smaller", -1, money1.compareTo(money));
    assertEquals("Not seen as bigger", 1, money.compareTo(money1));
  }

  @Test(expected=IllegalArgumentException.class)
  public void compareToIncompatibleCurrenciesExceptionTest() {
    money1.compareTo(money3);
  }

  @Test(expected=IllegalArgumentException.class)
  public void compareToIncompatibleFactorsExceptionTest() {
    money1.compareTo(money4);
  }

  @Test
  public void compareTest() {
    PgCurrency money = money1.cloneCurrency();
    assertEquals("Not seen as equal", 0, money.compare(money, money1));
    assertEquals("Not seen as equal", 0, money1.compare(money1, money));
    money = money.add(new PgCurrency(money.getCurrency(), 1));
    assertEquals("Not seen as smaller", -1, money1.compare(money1, money));
    assertEquals("Not seen as bigger", 1, money.compare(money, money1));
  }

  @Test(expected=IllegalArgumentException.class)
  public void compareIncompatibleCurrenciesExceptionTest() {
    money1.compare(money1, money3);
  }

  @Test(expected=IllegalArgumentException.class)
  public void compareIncompatibleFactorsExceptionTest() {
    money1.compare(money1, money4);
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  public void equalTest() {
    assertEquals("Not seen as equal", money1, money1.cloneCurrency());
    assertEquals("Not seen as equal", money1, money1);
    assertFalse("Seen as equal", money1.equals(new Date()));
    assertFalse("Seen as equal", money1.equals(money2));
  }  
  
  @Test
  public void minMaxTest() {
	assertEquals("Wrong minimum", money2, PgCurrency.min(money1, money2));
	assertEquals("Wrong maximum", money1, PgCurrency.max(money1, money2));
  }
}
