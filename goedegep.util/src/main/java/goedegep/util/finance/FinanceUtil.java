package goedegep.util.finance;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.logging.Logger;

/**
 * This is a utility class with methods for financial calculations.
 */
public final class FinanceUtil {
  private static final Logger LOGGER = Logger.getLogger(FinanceUtil.class.getName());
  private static final PgCurrencyFormat PCF = new PgCurrencyFormat();
  
  private FinanceUtil() {
  }
  
  /**
   * Calculate the Return on Investment, given an investment, and a final value after a given number of years.
   * 
   * @param investment The initial investment.
   * @param finalAmount The value at the end of the period.
   * @param years The period in years.
   * @return The Return on Investment as a percentage, using a FixedPointValue with with a factor of 100.
   */
  public static FixedPointValue returnOnInvestment(PgCurrency investment, PgCurrency finalAmount, int years) {
    LOGGER.fine("=> investment=" + PCF.format(investment) + ", finalAmoun=" + PCF.format(finalAmount) + ", years=" + years);
    double returnFactor = finalAmount.getDoubleValue() / investment.getDoubleValue();
//    double returnFactor = finalAmount.divide(investment).doubleValue();
    double yearsAsDouble = years;
    
    double returnOnInvestmentAsDouble = Math.pow(returnFactor, 1/yearsAsDouble) - 1;

    returnOnInvestmentAsDouble = 100 * 100 * returnOnInvestmentAsDouble;  // For % and for FixedPointValue factor.
    
    return new FixedPointValue((long) returnOnInvestmentAsDouble);
  }
}
