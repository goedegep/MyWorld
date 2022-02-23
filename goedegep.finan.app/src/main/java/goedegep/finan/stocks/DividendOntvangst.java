package goedegep.finan.stocks;


import java.time.LocalDate;

import goedegep.util.money.PgCurrency;

public class DividendOntvangst {
  LocalDate  ontvangstDatum;
  Share      effect;
  PgCurrency brutoBedrag;
  PgCurrency dividendBelasting;
  PgCurrency provisie;

  
  public LocalDate getOntvangstDatum() {
    return ontvangstDatum;
  }


  public void setOntvangstDatum(LocalDate ontvangstDatum) {
    this.ontvangstDatum = ontvangstDatum;
  }


  public Share getEffect() {
    return effect;
  }


  public void setEffect(Share effect) {
    this.effect = effect;
  }


  public PgCurrency getBrutoBedrag() {
    return brutoBedrag;
  }


  public void setBrutoBedrag(PgCurrency brutoBedrag) {
    this.brutoBedrag = brutoBedrag;
  }


  public PgCurrency getDividendBelasting() {
    return dividendBelasting;
  }


  public void setDividendBelasting(PgCurrency dividendBelasting) {
    this.dividendBelasting = dividendBelasting;
  }


  public PgCurrency getProvisie() {
    return provisie;
  }


  public void setProvisie(PgCurrency provisie) {
    this.provisie = provisie;
  }

  public PgCurrency getNettoBedrag() {
    PgCurrency nettoBedrag = brutoBedrag;

    if (dividendBelasting != null) {
      nettoBedrag = nettoBedrag.subtract(dividendBelasting);
    }
    
    if (provisie != null) {
      nettoBedrag = nettoBedrag.subtract(provisie);
    }
    
    return nettoBedrag;
  }
}
