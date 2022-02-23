package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;

public class Redenomination {
  private Share share = null;
  private int fromAmount = 1;  // 1 is default value.
  private int toAmount = 1;    // 1 is default value.
  private PgCurrency koers = null; // Koers waartegen fractie verrekend wordt.
  private PgCurrency terugBetaling = null; // Eventuele terugbetaling per oorspronkelijk aandeel
  
  public Share getShare() {
    return share;
  }
  
  public void setShare(Share share) {
    this.share = share;
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

  public PgCurrency getTerugBetaling() {
    return terugBetaling;
  }

  public void setTerugBetaling(PgCurrency terugBetaling) {
    this.terugBetaling = terugBetaling;
  }
}
