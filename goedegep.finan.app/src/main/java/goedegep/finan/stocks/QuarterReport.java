package goedegep.finan.stocks;

import goedegep.util.datetime.Quarter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class QuarterReport implements Comparator<QuarterReport> {
  private Quarter                         quarter;
  private HashMap<Share, Integer>         sharePositions = new HashMap<Share, Integer>();
  private HashMap<Share, Integer>         shareFractionPositions = new HashMap<Share, Integer>();
  private HashMap<ShareDividend, Integer> stockDividendPositions = new HashMap<ShareDividend, Integer>();
  private HashMap<OptionSerie, Integer>   optionPositions = new HashMap<OptionSerie, Integer>();
  
  public QuarterReport(Quarter quarter) {  
    this.quarter = quarter;
  }

  public int getYear() {
    return quarter.getYear();
  }
  
  public Quarter getQuarter() {
    return quarter;
  }

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
  
  public void addShareFractionPosition(Share share, int amount) {
    shareFractionPositions.put(share, amount);
  }

  public Set<Share> getShareFractions() {
    return shareFractionPositions.keySet();
  }

  public int getShareFractionPosition(Share share) {
    return shareFractionPositions.get(share);
  }

  /*
   * StockDividend position part.
   */
  
  public void addStockDividendPosition(ShareDividend shareDividend, int amount) {
    stockDividendPositions.put(shareDividend, amount);
  }

  public Set<ShareDividend> getStockDividends() {
    return stockDividendPositions.keySet();
  }

  public int getStockDividendPosition(ShareDividend shareDividend) {
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
   * General part.
   */
  
  public int getTotalNumberOfPositions() {
    return sharePositions.size() + shareFractionPositions.size() + stockDividendPositions.size() + optionPositions.size();
  }
  
  public int compare(QuarterReport quarterReport1, QuarterReport quarterReport2) {
    return quarterReport1.quarter.compareTo(quarterReport2.quarter);
//    if (quarterReport1.year < quarterReport2.year) {
//      return -1;
//    } else if (quarterReport1.year > quarterReport2.year) {
//      return 1;
//    } else if (quarterReport1.quarter < quarterReport2.quarter) {
//      return -1;
//    } else if (quarterReport1.quarter > quarterReport2.quarter) {
//      return 1;
//    } else {
//      return 0;
//    }
  }
}
