package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;

public abstract class EffRekKwartaalOverzicht extends EffRekTransactie {
  private int year = -1;
  private int quarter = -1;

  public EffRekKwartaalOverzicht(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_KWARTAAL_OVERZICHT;
  }

  public int getJaar() {
    return year;
  }

  public void setJaar(int year) {
    this.year = year;
  }

  public int getKwartaal() {
    return quarter;
  }
  
  public void setKwartaal(int kwartaal) {
    if (kwartaal < 1  ||  kwartaal > 4) {
      throw new IllegalArgumentException();
    }
    quarter = kwartaal;
  }
  
  public boolean isValid() {
    boolean isValid = true;
    
    if (year == -1  ||  quarter == -1) {
      isValid = false;
    }
    
    return isValid;
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek effectenRekening = (EffRek) this.getAccount();
    effectenRekening.getVerzamelDepot().generateQuarterReport(year, quarter);
    
    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Kwartaaloverzicht voor het kwartaal ");
    output.append(year).append("-").append(quarter);
    
    return output.toString();
  }

  @Override
  public String toString() {
    return getDescription();
  }
}
