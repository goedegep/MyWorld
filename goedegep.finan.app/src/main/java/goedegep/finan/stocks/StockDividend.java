package goedegep.finan.stocks;

import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains all information on a stock dividend.
 * - The number of dividends to be exchanged for one share.
 * - The rate at which the stock dividend transactions are executed.
 * 
 * @author Peter
 */

public class StockDividend {
  // Exchange ratio: for every 'fromAmount' stockDividends, you receive 'toAmount' shares.
  private int                      fromAmount;          // -1 indicates not set.
  private int                      toAmount;            // -1 indicates not set.
  private PgCurrency               koers;               // koers waartegen gehandeld wordt
  private Map<Quarter, PgCurrency>  quarterRates = new HashMap<Quarter, PgCurrency>();  // Key is "year-quarter", quarter 0 is start of year.

  public StockDividend() {
    this(-1, -1, null);
  }

  public StockDividend(int fromAmount, int toAmount, PgCurrency koers) {
    this.fromAmount = fromAmount;
    this.toAmount = toAmount;
    this.koers = koers;
  }

  public int getFromAmount() {
    return fromAmount;
  }

  public void setFromAmount(int fromAmount) {
    this.fromAmount = fromAmount;
  }

  public int getToAmount() {
    return toAmount;
  }

  public void setToAmount(int toAmount) {
    this.toAmount = toAmount;
  }

  public PgCurrency getKoers() {
    return koers;
  }

  public void setKoers(PgCurrency koers) {
    this.koers = koers;
  }

  public void addQuarterRate(int year, int quarter, PgCurrency rate) {
    if (quarter < 0  ||  quarter > 4) {
      throw new IllegalArgumentException();
    }
    if (quarterRates.put(new Quarter(year, quarter), rate) != null) {
      throw new RuntimeException("Quarter rate is overwritten.");
    }
  }
  
  public PgCurrency getQuarterRate(int year, int quarter) {
    return quarterRates.get(new Quarter(year, quarter));
  }
  
  public Collection<Quarter> getQuarterRateKeys() {
    return quarterRates.keySet();
  }
}