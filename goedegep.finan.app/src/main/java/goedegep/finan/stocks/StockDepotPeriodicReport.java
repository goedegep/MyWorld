package goedegep.finan.stocks;

import goedegep.app.finan.gen.PeriodicReport;
import goedegep.finan.basic.PgTransaction;
import goedegep.util.fixedpointvalue.FixedPointValue;

import java.util.ArrayList;
//import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//public class StockDepotPeriodicReport<T extends Comparable<T>> implements Comparator<StockDepotPeriodicReport<T>> {
public class StockDepotPeriodicReport<T extends Comparable<T>> extends PeriodicReport<T> {
//  private T period = null; // Can be a year(Integer), Quarter or Month
  private HashMap<Share, Integer>         sharePositions = new HashMap<>();
  private HashMap<Share, FixedPointValue>         shareFractionPositions = new HashMap<>();
  private HashMap<ShareDividend, FixedPointValue> stockDividendPositions = new HashMap<>();
  private HashMap<OptionSerie, Integer>   optionPositions = new HashMap<>();
  private List<DividendOntvangst>         dividends = new ArrayList<>();
  private List<PgTransaction>             transactions = null;
  
  public StockDepotPeriodicReport() {
  }

//  public T getPeriod() {
//    return period;
//  }
//
//  public void setPeriod(T period) {
//    this.period = period;
//  }

  
  /*
   * Share position part.
   */
  
  public void addSharePosition(Share share, int amount) {
    sharePositions.put(share, amount);
  }

  public Set<Share> getShares() {
    return sharePositions.keySet();
  }

  public int getSharePosition(Share share) {
    return sharePositions.get(share);
  }

  
  /*
   * Share fraction part.
   */
  
  public void addShareFractionPosition(Share share, FixedPointValue amount) {
    shareFractionPositions.put(share, amount);
  }

  public Set<Share> getShareFractions() {
    return shareFractionPositions.keySet();
  }

  public FixedPointValue getShareFractionPosition(Share share) {
    return shareFractionPositions.get(share);
  }

  
  /*
   * StockDividend position part.
   */
  
  public void addStockDividendPosition(ShareDividend shareDividend, FixedPointValue amount) {
    stockDividendPositions.put(shareDividend, amount);
  }

  public Set<ShareDividend> getStockDividends() {
    return stockDividendPositions.keySet();
  }

  public FixedPointValue getStockDividendPosition(ShareDividend shareDividend) {
    return stockDividendPositions.get(shareDividend);
  }

  
  /*
   * Option position part.
   */
  
  public void addOptionPosition(OptionSerie optionSerie, int amount) {
    optionPositions.put(optionSerie, amount);
  }

  public Set<OptionSerie> getOptionPositions() {
    return optionPositions.keySet();
  }

  public int getOptionPosition(OptionSerie optionSerie) {
    return optionPositions.get(optionSerie);
  }

  
  /*
   * Dividends part
   */
  public void addDividend(DividendOntvangst dividend) {
    dividends.add(dividend);
  }
  
  public List<DividendOntvangst> getDividends() {
    return dividends;
  }

  
  /*
   * Transactions part.
   */
  
  public void addTransaction(PgTransaction transaction) {
    if (transactions == null) {
      transactions = new ArrayList<PgTransaction>();
    }
    transactions.add(transaction);
  }

  public List<PgTransaction> getTransactions() {
    return transactions;
  }

  
  /*
   * General part.
   */
  
  public int getTotalNumberOfPositions() {
    return sharePositions.size() + shareFractionPositions.size() + stockDividendPositions.size() + optionPositions.size();
  }
  
//  public int compare(StockDepotPeriodicReport<T> quarterReport1, StockDepotPeriodicReport<T> quarterReport2) {
//    return quarterReport1.period.compareTo(quarterReport2.period);
//  }
  
  
}
