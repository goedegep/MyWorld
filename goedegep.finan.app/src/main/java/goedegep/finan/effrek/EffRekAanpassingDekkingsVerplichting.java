package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekAanpassingDekkingsVerplichting extends EffRekTransactie {
  private static final int                 BEDRAG_SIZE = 10;
  private static final PgCurrencyFormat    CF = new PgCurrencyFormat(BEDRAG_SIZE);


  public EffRekAanpassingDekkingsVerplichting(PgAccount  account) {
    super(account);
  }

  
  public short getTransactionType() {
    return TT_AANPASSING_DEKKINGSVERPLICHTING;
  }
  
  public String getDescription() {
    StringBuilder buf = new StringBuilder();

    // omschrijving en bedrag, positief bedrag is verhoging verplichting
    if (getTransactionAmount().isNegative()) {
      buf.append("bij van dekkingsrekening ");
      buf.append(CF.format(getTransactionAmount().changeSign()));
    } else {
      buf.append("af naar dekkingsrekening ");
      buf.append(CF.format(getTransactionAmount()));
    }

    return buf.toString();
  }

  public String toString() {
    return getDescription();
  }
}
