package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;

public abstract class EffRekMaandOverzicht extends EffRekTransactie {
  private int year = -1;
  private int month = -1;

  public EffRekMaandOverzicht(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_MAAND_OVERZICHT;
  }

  public int getJaar() {
    return year;
  }

  public void setJaar(int year) {
    this.year = year;
  }

  public int getMaand() {
    return month;
  }
  
  public void setMaand(int maand) {
    if (maand < 1  ||  maand > 12) {
      throw new IllegalArgumentException();
    }
    month = maand;
  }
  
  public boolean isValid() {
    boolean isValid = true;
    
    if (year == -1  ||  month == -1) {
      isValid = false;
    }
    
    return isValid;
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek effectenRekening = (EffRek) this.getAccount();
    effectenRekening.getVerzamelDepot().finalizeMonthlyReport(year, month);
    
    setHandled(true);
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Maandoverzicht voor de maand ");
    output.append(year).append("-").append(month);
    
    return output.toString();
  }

  @Override
  public String toString() {
    return getDescription();
  }
}