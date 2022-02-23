package goedegep.finan.postbank.pbfonds;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.money.PgCurrencyFormat;

import java.util.List;

public class PbFondsJaaroverzicht extends PbFondsTransaction {
  private static final String           JAAROVERZICHT_STRING = "jaaroverzicht";
  private static final PgCurrencyFormat CF =  new PgCurrencyFormat(0, false, false, true);

  public PbFondsJaaroverzicht(PgAccount account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_JAAROVERZICHT;
  }

  public int getYear() {
    return getBookingDate().getYear();
  }

  public String getDescription() {
    return toString();
  }

  public String toString() {
    return JAAROVERZICHT_STRING +        // transaction type
           " " + CF.format(getKoers());          // 'koers' veld.
  }

  @Override
  public void handle(List<TransactionError> errors) {
    handleFirst();
    
    PbFonds        fonds = (PbFonds) this.getAccount();
    fonds.addYearlyData(getYear() - 1);
    
    handleLast();
  }
}
