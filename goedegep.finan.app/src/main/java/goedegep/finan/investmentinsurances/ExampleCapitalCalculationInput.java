package goedegep.finan.investmentinsurances;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

/**
 * This class provides the input values for an example capital calculation.
 */
public class ExampleCapitalCalculationInput {
  /**
   * Current value
   */
  public PgCurrency startingValue;
  
  /**
   * Return on investment
   */
  public FixedPointValue returnOnInvestment;
  
  /**
   * Total Investment Fund costs
   */
  public FixedPointValue totalExpenseRatio;
}
