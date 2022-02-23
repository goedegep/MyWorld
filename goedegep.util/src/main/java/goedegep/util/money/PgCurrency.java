package goedegep.util.money;

import java.util.Comparator;
import java.util.Objects;

import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * This class represents an amount of money.
 * <p>
 * An amount of money consists of a <b>currency unit</b>, e.g. Euro, and an amount, which is a fixed point value.<br/>
 * Typically money uses 2 digits behind the comma, i.e. a resolution of 1 cent (represented by a factor of 100).
 * However in calculations and in e.g. dividend amounts often a higher accuracy is used.
 * Therefore the default <b>factor</b> is 100, but it can be set to any value.<p/>
 * This class is immutable; the are no setters and none of the methods alters any property of an object.
 */
public class PgCurrency implements Comparator<PgCurrency>, Comparable<PgCurrency> {
  // Predefined currency unit values.
  public static final int NO_CURRENCY_SPECIFIED = 0;
  public static final int GUILDER = 1;
  public static final int EURO = 2;
  
  public static final int DEFAULT_CURRENCY = EURO;
  static final double      euroToGuilderRatio = 2.20371;  // 1 euro = f 2,20371
  private static final Comparator<FixedPointValue> FPV_COMPARATOR = new FixedPointValue(0L);

  private final int currency;             // the currency unit
  private final FixedPointValue amount;   // the amount

  
  /**
   * Create a Currency value where the <b>currency unit</b> is specified and the value is specified as a FixedPointValue.
   * 
   * @param currency The <b>currency unit</b>.
   * @param value The amount as a <b>FixedPointValue</b>.
   */
  public PgCurrency (int currency, FixedPointValue value) {
    this.currency = currency;
    this.amount = value;
  }
  
  /**
   * Create a Currency value where all elements are specified.
   * For example for € 3,4567 the <b>currency unit</b> is EURO, the amount is 34567 and the <b>factor</b> is 10000.
   * 
   * @param currency The <b>currency unit</b>.
   * @param amount The amount as an integral value.
   * @param factor The <b>factor</b>, specifying the number of digits behind the comma.
   */
  public PgCurrency (int currency, long amount, int factor) {
    this(currency, new FixedPointValue(amount, factor));
  }

  
  /**
   * Create a Currency value using the default <b>factor</b> of 100.
   * 
   * @param currency The <b>currency unit</b>.
   * @param amount The amount as an integral value.
   */
  public PgCurrency (int currency, long amount) {
    this(currency, amount, 100);
  }

  /**
   * Create a Currency value using the default <b>currency unit</b> EURO and the default <b>factor</b> of 100.
   * 
   * @param amount The amount as an integral value.
   */
  public PgCurrency (long amount) {
    this(DEFAULT_CURRENCY, amount, 100);
  }

  
  /**
   * Create a Currency value using the default factor of 100 and where the amount is specified as a Double.<br/>
   * For example for € 3,45 the <b>currency unit</b> is EURO and the amount is 3.45d.<br/>
   * Use this method with care, as rounding problems may occur!
   * 
   * @param currency The <b>currency unit</b>.
   * @param amount The amount as a Double value.
   */
  public PgCurrency (int currency, Double amount) {
    this(currency, (long) (amount * 100), 100);
  }

  /**
   * Get the <b>currency unit</b>.
   * 
   * @return the <b>currency unit</b>
   */
  public int getCurrency() {
    return currency;
  }

  /**
   * Get the <b>amount</b> property.
   * 
   * @return the <b>amount</b> property
   */
  public long getAmount() {
    return amount.getValue();
  }

  /**
   * Get the <b>factor</b> property.
   * 
   * @return the <b>factor</b> property
   */
  public int getFactor() {
    return amount.getFactor();
  }

  /**
   * Create a copy of this currency.
   * 
   * @return a copy of this currency.
   */
  public PgCurrency cloneCurrency() {
    return new PgCurrency(currency, amount.cloneFixedPointValue());
  }

  /**
   * Create a currency which is the sum of this currency and another currency.<p/>
   * With this method, currencies can only be added if they have the same <b>currency unit</b> and the same <b>factor</b>, otherwise
   * an <b>IllegalArgumentException</b> will be thrown.
   * 
   * @param currency the currency to be added to this currency
   * @return the sum of this currency and the specified currency
   */
  public PgCurrency add(PgCurrency currency) {
    if (this.currency != currency.currency) {
      throw new IllegalArgumentException("Incompatible currencies, can not add " +
          currency.currency + " to " + this.currency);
    }
    if (amount.getFactor() != currency.amount.getFactor()) {
      throw new IllegalArgumentException("Incompatible currency factors, can not add factor " +
          currency.amount.getFactor() + " to factor " + amount.getFactor());
    }
    
    return new PgCurrency(this.currency, amount.add(currency.amount));
  }
  
  /**
   * Create a currency which is the sum of two currencies.
   * If one of the currencies is <b>null</b>, a copy of the other currency is returned.
   * If both currencies are <b>null</b>, <b>null</b> is returned.<p/>
   * A <b>currency unit</b> has to be specified. Both currencies are converted to this unit (if needed) before they are added.<br/>
   * With this method, currencies can only be added if they have the same <b>factor</b>, otherwise an <b>IllegalArgumentException</b> will be thrown.
   * 
   * @param money1 the first currency to be summed
   * @param money2 the second currency to be summed
   * @param currency the <b>currency unit</b> of the result
   * @return
   */
  public static PgCurrency addDifferentCurrencies(PgCurrency money1, PgCurrency money2, int currency) {
    if ((money1 == null)  &&  (money2 == null)) {
      return null;
    }
    
    if (money1 == null) {
      return money2.cloneCurrency();
    }
    
    if (money2 == null) {
      return money1.cloneCurrency();
    }
    
    return money1.certifyCurrency(currency).add(money2.certifyCurrency(currency));
  }

  /**
   * Create a currency which is the result of subtracting a currency from this currency.<p/>
   * With this method, currencies can only be subtracted if they have the same <b>currency unit</b> and the same <b>factor</b>, otherwise
   * an <b>IllegalArgumentException</b> will be thrown.
   * 
   * @param currency the currency to be subtracted from this currency
   * @return the subtraction of this currency and the specified currency
   */
  public PgCurrency subtract(PgCurrency currency) {
    if (this.currency != currency.currency) {
      throw new IllegalArgumentException("Incompatible currencies, can not subtract " +
          currency.currency + " from " + this.currency);
    }
    if (amount.getFactor() != currency.amount.getFactor()) {
      throw new IllegalArgumentException("Incompatible currency factors, can not subtract factor " +
          currency.amount.getFactor() + " from factor " + amount.getFactor());
    }
    return new PgCurrency(this.currency, amount.subtract(currency.amount));
  }

  
  /**
   * Create a currency which is the result of multiplying this currency with an integral factor.
   * 
   * @param factor the value to multiply this currency with.
   * @return the multiplication of this currency with the specified factor
   */
  public PgCurrency multiply(long factor) {
    return new PgCurrency(currency, amount.multiply(factor));
  }

  /**
   * Create a currency which is the result of multiplying this currency with an double factor.
   * 
   * @param factor the value to multiply this currency with.
   * @return the multiplication of this currency with the specified factor
   */
  public PgCurrency multiply(double factor) {
    return new PgCurrency(currency, amount.multiply(factor));
  }

  /**
   * Create a currency which is the result of multiplying this currency with an FixedPointValue factor.
   * 
   * @param factor the value to multiply this currency with.
   * @return the multiplication of this currency with the specified factor
   */
  public PgCurrency multiply(FixedPointValue factor) {
    return new PgCurrency(currency, amount.multiply(factor));
  }

  
  /**
   * Create a currency which is the result of dividing this currency by an integral value.
   * The result is a rounded value.
   * 
   * @param factor the value to divide this currency by.
   * @return this currency divided by the specified factor
   */
  public PgCurrency divide(long factor) {
    return new PgCurrency(currency, amount.divide(factor));
  }

  /**
   * Create a currency which is the result of dividing this currency by a double value.
   * 
   * @param factor the value to divide this currency by.
   * @return this currency divided by the specified factor
   */
  public PgCurrency divide(double factor) {
    return new PgCurrency(currency, amount.divide(factor));
  }

  /**
   * Create a currency which is the result of dividing this currency by a FixedPointValue value.
   * 
   * @param divider the value to divide this currency by.
   * @return this currency divided by the specified factor
   */
  public PgCurrency divide(FixedPointValue divider) {
    return new PgCurrency(currency, amount.divide(divider, divider.getFactor()));
  }

  /**
   * Create a currency which is the result of dividing this currency by another currency.
   * If the <b>currency unit</b> of the specified currency differs from the <b>currency unit</b> of this currency,
   * the specified currency is first converted to have the same unit as this currency.<br/>
   * With this method, currencies can only be divided if they have the same <b>factor</b>, otherwise
   * an <b>IllegalArgumentException</b> will be thrown.
   * 
   * @param currency the value to divide this currency by.
   * @return this currency divided by the specified currency
   */
  public FixedPointValue divide(PgCurrency currency, int resultFactor) {
    if (amount.getFactor() != currency.amount.getFactor()) {
      throw new IllegalArgumentException("Incompatible currency factors, can not divide factor " +
          amount.getFactor() + " by factor " + currency.amount.getFactor());
    }
    PgCurrency adaptedCurrency = currency.certifyCurrency(this.getCurrency());
    return amount.divide(adaptedCurrency.amount, resultFactor);
  }
  
  /**
   * Create a currency which is the result of dividing this currency by a double value, and where the result is rounded up (i.s.o. normal rounding).
   * 
   * @param factor the value to divide this currency by.
   * @return this currency divided by the specified factor, using round up
   */
  public PgCurrency divideWithRoundUp(double factor) {
    return new PgCurrency(currency, amount.divideWithRoundUp(factor));
  }

  /**
   * Create a currency from this currency, where the value is negated
   * 
   * @return a currency which is the same as this currency, only the sign is changed (plus to minus, or vice versa)
   */
  public PgCurrency changeSign() {
    return new PgCurrency(currency, amount.changeSign());
  }

  /**
   * Returns the object itself if it already has the specified currency,
   * else it returns an object which is converted to the specified currency.
   * 
   * @param currency the <b>currency unit</b> which the returned object shall have.
   * @return this object, if it has the required <b>currency unit</b>, or this object converted to the required <b>currency unit</b> otherwise.
   */
  public PgCurrency certifyCurrency(int currency) {
    if (this.currency == currency) {
      return this;
    } else {
      return changeCurrency(currency);
    }
  }
  
  /**
   * Convert this currency to a currency with a specific <b>currency unit</b>.
   * This method always returns a new currency object.
   * 
   * @param currency the <b>currency unit</b> which the returned object shall have.
   * @return this object converted to the required <b>currency unit</b>.
   */
  public PgCurrency changeCurrency(int currency) {
    PgCurrency  result;

    if (this.currency == currency) {
      result = cloneCurrency();
    } else if ((this.currency == GUILDER) &&  (currency == EURO)) {
      result = new PgCurrency(currency, amount.divide(euroToGuilderRatio));
    } else {
      result = new PgCurrency(currency, amount.multiply(euroToGuilderRatio));
    }

    return result;
  }
  
  /**
   * Returns the object itself if it already has the specified factor,
   * else it returns an object which is converted to the specified factor.
   * 
   * @param factor The factor to be certified.
   * @return this object, if it has the required factor, a new currency converted to the required factor otherwise.
   */
  public PgCurrency certifyFactor(int factor) {
    if (amount.getFactor() == factor) {
      return this;
    } else {
      return changeFactor(factor);
    }
  }

  /**
   * Returns the object itself if it already has a factor greater of equal to the specified minimal factor,
   * else it returns a currency which is converted to have the minimal factor.
   * 
   * @param factor The required minimal factor.
   * @return this object, if it has the required factor, a new currency converted to the required minimal factor otherwise.
   */
  public PgCurrency certifyMinimalFactor(int factor) {
    if (amount.getFactor() >= factor) {
      return this;
    } else {
      return changeFactor(factor);
    }
  }

  /**
   * Create a currency with a different factor (accuracy) than this currency.
   * 
   * @param factor the factor the new currency shall have.
   * @return a currency where the factor is changed to the specified value.
   */
  public PgCurrency changeFactor(int factor) {
    return new PgCurrency(currency, amount.changeFactor(factor));
  }
  
  /**
   * Create a currency which is a rounded value (no digits behind the comma) of this currency.
   * 
   * @return a rounded currency
   */
  public PgCurrency round() {
    return this.changeFactor(1).changeFactor(amount.getFactor());
  }
  
  /**
   * Create a currency which is a rounded to a specific number of digits behind the comma.
   * 
   * @param factor a representation of the number of digits to round to
   * @return a currency, rounded to a specific number of digits behind the comma.
   */
  public PgCurrency round(int factor) {
    return this.changeFactor(factor).changeFactor(amount.getFactor());
  }
  
  /**
   * Create a currency, of which value is the value of this currency, but rounded up.
   * 
   * @return a currency of which the value is rounded up.
   */
  public PgCurrency roundUp() {
    return new PgCurrency(currency, amount.roundUp());
  }
  
  /**
   * Check whether this currency is less than another currency.
   * 
   * @param currency the currency to compare to
   * @return true, if this currency is less than the specified currency, false otherwise
   */
  public boolean isLessThan(PgCurrency currency) {
    return compareTo(currency) == -1;
  }

  /**
   * Check whether this currency is greater than another currency.
   * 
   * @param currency the currency to compare to
   * @return true, if this currency is greater than the specified currency, false otherwise
   */
  public boolean isGreaterThan(PgCurrency currency) {
    return compareTo(currency) == 1;
  }

  /**
   * Check whether the amount of this currency is less than zero.
   * 
   * @return true, if the amount of this currency is negative, false otherwise.
   */
  public boolean isNegative() {
    return amount.isNegative();
  }

  /**
   * Check whether the amount of this currency is 0.
   * 
   * @return true, if the amount of this currency is zero, false otherwise.
   */
  public boolean isZero() {
    return amount.isZero();
  }
  
  /**
   * Compare this currency to another currency.<br/>
   * With this method, currencies can only be compared if they have the same <b>currency unit</b> and the same <b>factor</b>, otherwise
   * an <b>IllegalArgumentException</b> will be thrown.
   * 
   * @param currency the value to compare this currency to
   * @return -1, 0, or 1 if this currency is less than, equal to, or greater than the specified currency
   */
  public int compareTo(PgCurrency currency) {
    return compare(this, currency);
  }
  
  /**
   * Compare two currencies.<br/>
   * With this method, currencies can only be compared if they have the same <b>currency unit</b> and the same <b>factor</b>, otherwise
   * an <b>IllegalArgumentException</b> will be thrown.
   * 
   * @param money1 the first value to compare
   * @param money2 the second value to compare
   * @return -1, 0, or 1 if money1 is less than, equal to, or greater than money2
   */
  public int compare(PgCurrency money1, PgCurrency money2) {
    if ((money1.currency != money2.currency)  ||
        (money1.amount.getFactor() != money2.amount.getFactor())) {
      throw new IllegalArgumentException("Imcompatible currencies");
    }
    return FPV_COMPARATOR.compare(money1.amount, money2.amount);
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
    
    PgCurrency money = (PgCurrency) object;
    if ((currency == money.currency)  &&
        amount.equals(money.amount)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
      return Objects.hash(currency, amount);
  }
  
  @Override
  public String toString() {
    return ("amount = " + amount.getValue() + ", currency = " + currency +
        ", factor = " + amount.getFactor());
  }
  
  /**
   * Get the highest value currency of two currencies.
   * 
   * @param a the first currency
   * @param b the second currency
   * @return the highest value of a and b
   */
  public static PgCurrency max(PgCurrency a, PgCurrency b) {
	if (a.isGreaterThan(b)) {
		return a;
	} else {
		return b;
	}
  }
  
  /**
   * Get the lowest value currency of two currencies.
   * 
   * @param a the first currency
   * @param b the second currency
   * @return the highest value of a and b
   */
  public static PgCurrency min(PgCurrency a, PgCurrency b) {
	if (a.isGreaterThan(b)) {
		return b;
	} else {
		return a;
	}
  }

  /**
   * Get the amount as a double.
   * 
   * @return the amount as a double value.
   */
  public double getDoubleValue() {
    return amount.doubleValue();
  }
}