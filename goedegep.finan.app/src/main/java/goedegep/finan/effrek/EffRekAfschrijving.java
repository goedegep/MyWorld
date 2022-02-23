package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekAfschrijving extends EffRekTransactie {
  public static final String               TRANSACTIE_TYPE_STRING = "af";
  
  private static final int                 BEDRAG_SIZE = 10;
  
  protected static final PgCurrencyFormat    CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final PgCurrencyFormat    CF =  new PgCurrencyFormat(0, false, false, true);


  public EffRekAfschrijving(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_AFSCHRIJVING;
  }
  
  public String getDescription() {
    String            output;

    // omschrijving en bedrag
    output = "af  " + CF2.format(getTransactionAmount());

    return output;
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

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    // verlaag het tegoed met het bedrag van de opname
    effectenRekening.decreaseBalance(getTransactionAmount());

    // verlaag nettoStorting
    effectenRekening.decreaseNettoStorting(this.getTransactionAmount());

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public boolean isExecutionDateSupported() {
    return false;
  }
}
