package goedegep.finan.postbank.pbsprek;

import goedegep.util.money.PgCurrency;

public abstract class PbSpRekPolicy {
  public PgCurrency getOpnameKosten(PbSpRekOverschrijving transaction) {
    return null;
  }
  
  public PgCurrency getVrijOpneembaarBedragVoor(PbSpRekTransaction transaction) {
    return null;
  }
  
  public PgCurrency getVrijOpneembaarBedragNa(PbSpRekTransaction transaction) {
    return null;
  }
}
