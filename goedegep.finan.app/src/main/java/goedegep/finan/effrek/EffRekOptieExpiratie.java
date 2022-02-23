package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.postbank.pbeffrek.PbEffRek;

public abstract class EffRekOptieExpiratie extends EffRekTransactie {
  public static final int                  EXPIRATION_MONTH_INVALID = -1;
  public static final int                  EXPIRATION_YEAR_INVALID = -1;
  private static final String[]            MONTH_NAMES = {"JAN", "FEB", "MAR", "APR", "MEI", "JUN",
                                                          "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

  private int expirationMonth;
  private int expirationYear;

  public EffRekOptieExpiratie(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OPTIE_EXPIRATIE;
  }

  public int getExpirationMonth() {
    return expirationMonth;
  }

  public void setExpirationMonth(int expirationMonth) {
    this.expirationMonth = expirationMonth;
  }

  public int getExpirationYear() {
    return expirationYear;
  }

  public void setExpirationYear(int expirationYear) {
    this.expirationYear = expirationYear;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("expiratie opties: ");
    output.append(MONTH_NAMES[expirationMonth - 1]);
    output.append(" ").append(expirationYear);

    return output.toString();
  }
  
  public String toString() {
    return getDescription();
  }
  
  public boolean isValid() {
    if ((expirationMonth != EXPIRATION_MONTH_INVALID)  &&
        (expirationYear != EXPIRATION_YEAR_INVALID)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbEffRek        effectenRekening = (PbEffRek) getAccount();
    effectenRekening.getVerzamelDepot().optieExpiratie(expirationMonth, expirationYear);

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
