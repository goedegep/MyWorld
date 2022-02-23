package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;

public abstract class EffRekBelastingOverzicht extends EffRekTransactie {
  private int year = -1;

  public EffRekBelastingOverzicht(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_BELASTING_OVERZICHT;
  }

  public int getJaar() {
    return year;
  }

  public void setJaar(int year) {
    this.year = year;
  }

  public boolean isValid() {
    boolean isValid = true;
    
    if (year == -1) {
      isValid = false;
    }
    
    return isValid;
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek effectenRekening = (EffRek) this.getAccount();
    effectenRekening.getVerzamelDepot().finalizeYearlyReport(year);
    
    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Belasting overzicht voor het jaar ");
    output.append(year);
    
    return output.toString();
  }

  @Override
  public String toString() {
    return getDescription();
  }
}
