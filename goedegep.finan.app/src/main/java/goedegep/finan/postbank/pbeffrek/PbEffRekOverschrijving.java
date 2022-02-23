package goedegep.finan.postbank.pbeffrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class PbEffRekOverschrijving extends PbEffRekTransaction {
  public static final String               VAN_GIRO_STRING = "van giro";
  public static final String               NAAR_GIRO_STRING = "naar giro";
  
  private static final int                 BEDRAG_SIZE = 10;
  
  private static final PgCurrencyFormat    CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final PgCurrencyFormat    CF =  new PgCurrencyFormat(0, false, false, true);

  private boolean   vanGiro;   // true for 'VAN GIRO', else 'NAAR GIRO'

  public PbEffRekOverschrijving(PgAccount account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OVERSCHRIJVING;
  }

  public void setVanGiro(boolean vanGiro) {
    this.vanGiro = vanGiro;
  }

  public boolean vanGiro() {
    if (vanGiro) {
      return true;
    } else {
      return false;
    }
  }

  public boolean naarGiro() {
    if (!vanGiro) {
      return true;
    } else {
      return false;
    }
  }
  
  public String getDescription() {
    String            output;

    // omschrijving en bedrag
    if (vanGiro()) {
      output = "bij " + CF2.format(getTransactionAmount()) + " van girorekening";
    } else {
      output = "af  " + CF2.format(getTransactionAmount()) + " naar girorekening";
    }

    return output;
  }


  public String toString() {
    StringBuffer      output = new StringBuffer(80);

    // transaction type
    if (vanGiro) {
      output.append(VAN_GIRO_STRING);
    } else {
      output.append(NAAR_GIRO_STRING);
    }

    // transaction amount
    output.append(" " + CF.format(getTransactionAmount()));

    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbEffRek  effectenRekening = (PbEffRek) this.getAccount();

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    if (vanGiro) {
      // verhoog het tegoed met het bedrag van de storting
      effectenRekening.increaseBalance(getTransactionAmount());

      // verhoog nettoStorting
      effectenRekening.increaseNettoStorting(this.getTransactionAmount());
    } else {
      // verlaag het tegoed met het bedrag van de opname
      effectenRekening.decreaseBalance(getTransactionAmount());

      // verlaag nettoStorting
      effectenRekening.decreaseNettoStorting(this.getTransactionAmount());
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public boolean isExecutionDateSupported() {
    return false;
  }
}
