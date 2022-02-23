package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekBijschrijving extends EffRekTransactie {
  public static final String               TRANSACTIE_TYPE_STRING = "bij";
  
  private static final int                 BEDRAG_SIZE = 10;
  
  protected static final PgCurrencyFormat    CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final PgCurrencyFormat    CF =  new PgCurrencyFormat(0, false, false, true);

  public EffRekBijschrijving(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_BIJSCHRIJVING;
  }

  public String getDescription() {
    return "bij " + CF2.format(getTransactionAmount());
  }

  public String toString() {
    StringBuffer      output = new StringBuffer(80);

    // transaction type
    output.append(TRANSACTIE_TYPE_STRING);

    // transaction amount
    output.append(" " + CF.format(getTransactionAmount()));

    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();

    // verhoog het tegoed met het bedrag van de storting
    effectenRekening.increaseBalance(getTransactionAmount());

    // verhoog nettoStorting
    effectenRekening.increaseNettoStorting(this.getTransactionAmount());

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
