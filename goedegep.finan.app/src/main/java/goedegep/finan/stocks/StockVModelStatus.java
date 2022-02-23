package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.List;

public class StockVModelStatus {
  private static PgCurrencyFormat     cf = new PgCurrencyFormat();
  
  private PgCurrency                  totalBuySellProfit;
  private List<StockBuySellCombo>     buySellCombos;
  private List<StockPositionHistory>  buys;

  public StockVModelStatus(PgCurrency totalBuySellProfit,
      List<StockBuySellCombo> buySellCombos,
      List<StockPositionHistory>  buys) {
    this.totalBuySellProfit = totalBuySellProfit;
    this.buySellCombos = buySellCombos;
    this.buys = buys;
  }

  public PgCurrency getTotalBuySellProfit() {
    return totalBuySellProfit;
  }

  public List<StockBuySellCombo> getBuySellCombos() {
    return buySellCombos;
  }

  public List<StockPositionHistory> getBuys() {
    return buys;
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Total buy/sell profit: " + cf.format(totalBuySellProfit));
    
    return buf.toString();
  }
}
