package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;

public class TerugBetaling {
  private Share      share;
  private PgCurrency betalingPerAandeel;
  private Integer    aantalRechten;
  private Share      stockPositionShare;  // In het geval van een redenominatie, wordt de terugbetaling verwerkt bij de nieuwe positie.

  public TerugBetaling(Share share, PgCurrency betalingPerAandeel, Integer aantalRechten) {
    this(share, betalingPerAandeel, aantalRechten, null);
  }
  
  public TerugBetaling(Share share, PgCurrency betalingPerAandeel, Integer aantalRechten, Share stockPositionShare) {
    super();
    this.share = share;
    this.betalingPerAandeel = betalingPerAandeel;
    this.aantalRechten = aantalRechten;
    this.stockPositionShare = stockPositionShare;
  }
  
  public Share getShare() {
    return share;
  }

  public PgCurrency getBetalingPerAandeel() {
    return betalingPerAandeel;
  }

  public Integer getAantalRechten() {
    return aantalRechten;
  }

  public Share getStockPositionShare() {
    return stockPositionShare;
  }
}
