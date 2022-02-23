package goedegep.finan.basic;

import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;

/**
 * This class provides information for a quarter of a year for an account.
 *
 */
public class QuarterlyData {
  private Quarter quarter;
  private PgCurrency balance;
  private PgCurrency estimatedValue;
  private PgCurrency nettoStorting;
  private PgCurrency profit;
  private PgCurrency cumulativeProfit;
  
  
  public QuarterlyData(Quarter quarter) {
    this.quarter = quarter;
  }

  public PgCurrency getBalance() {
    return balance;
  }

  public void setBalance(PgCurrency balance) {
    this.balance = balance;
  }

  public PgCurrency getEstimatedValue() {
    return estimatedValue;
  }

  public void setEstimatedValue(PgCurrency estimatedValue) {
    this.estimatedValue = estimatedValue;
  }

  public Quarter getQuarter() {
    return quarter;
  }

  public PgCurrency getNettoStorting() {
    return nettoStorting;
  }

  public void setNettoStorting(PgCurrency nettoStorting) {
    this.nettoStorting = nettoStorting;
  }

  public PgCurrency getProfit() {
    return profit;
  }

  public void setProfit(PgCurrency profit) {
    this.profit = profit;
  }

  public PgCurrency getCumulativeProfit() {
    return cumulativeProfit;
  }

  public void setCumulativeProfit(PgCurrency cumulativeProfit) {
    this.cumulativeProfit = cumulativeProfit;
  }

}
