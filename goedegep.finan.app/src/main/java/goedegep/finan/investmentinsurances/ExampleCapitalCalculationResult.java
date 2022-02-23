package goedegep.finan.investmentinsurances;

import goedegep.util.money.PgCurrency;

/**
 * This class holds the result of an example capital calculation.
 */
public class ExampleCapitalCalculationResult {
  public PgCurrency[] calculatedExampleCapitals = null;
  public boolean defaultTotalExpenseRatioUsed = false;
  public boolean defaultPercentageCostsUsed = false;
  public boolean defaultExpectedAnnualCostsIncreaseUsed = false;
  
  /**
   * Check whether at least one estimation is used.
   * <p>
   * If a default value is used (see defaultTotalExpenseRatioUsed, defaultPercentageCostsUsed and defaultExpectedAnnualCostsIncreaseUsed), this is seen as an estimation.
   * 
   * @return true if at least one estimation is used, false otherwise.
   */
  public boolean isOneOrMoreEstimationsUsed() {
    return numberOfEstimationsUsed() > 0;
  }
  
  /**
   * Get the number of estimations used.
   * <p>
   * If a default value is used (see defaultTotalExpenseRatioUsed, defaultPercentageCostsUsed and defaultExpectedAnnualCostsIncreaseUsed), this is seen as an estimation.
   * 
   * @return the number of estimations used.
   */
  public int numberOfEstimationsUsed() {
    int numberOfEstimationsUsed = 0;
    
    if (defaultTotalExpenseRatioUsed) {
      numberOfEstimationsUsed++;
    }
    
    if (defaultPercentageCostsUsed) {
      numberOfEstimationsUsed++;
    }
    
    if (defaultExpectedAnnualCostsIncreaseUsed) {
      numberOfEstimationsUsed++;
    }
    
    return numberOfEstimationsUsed;
  }
  
  /**
   * Check whether the calculation was successful or not.
   * 
   * @return true if the calculation was successful, false otherwise.
   */
  public boolean isCalculationOk() {
    return calculatedExampleCapitals != null;
  }
}