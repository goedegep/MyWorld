package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.List;

public abstract class EffRekBewaarloon extends EffRekTransactie {
  private static final String BEWAARLOON_STRING  = "bewaarloon";

  private static final int               BEDRAG_SIZE = 10;
  
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  private static final PgCurrencyFormat  CF2 = new PgCurrencyFormat(BEDRAG_SIZE);

  public EffRekBewaarloon(PgAccount  account) {
    super(account);
  }

  public short getTransactionType() {
    return TT_BEWAARLOON;
  }
  
  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    PgCurrency total = getTransactionAmount();
    output.append("af  " + CF2.format(total));

    int year = getBookingDate().getYear() - 1;

    output.append(" bewaarloon ultimo " + year);
    double btwPercentage = getBTWPercentage(year);
    long bewaarloonAmount = (long) ((double) total.getAmount() / (1 + btwPercentage/100) + 0.5);
    PgCurrency bewaarloon = new PgCurrency(total.getCurrency(), bewaarloonAmount);
    long btwAmount = total.getAmount() - bewaarloonAmount;
    PgCurrency btw = new PgCurrency(total.getCurrency(), btwAmount);
    output.append(" af " + CF.format(bewaarloon));
    output.append(" btw " + btwPercentage + "% af " + CF.format(btw));

    return output.toString();
  }
  
  public String toString() {
    StringBuffer      output = new StringBuffer(80);

    // transaction type
    output.append(BEWAARLOON_STRING);

    // transaction amount
    output.append("\t" + CF.format(getTransactionAmount()));


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

    // verminder het tegoed met het bewaarloon
    effectenRekening.decreaseBalance(getTransactionAmount());

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public double getBTWPercentage(int year) {
    double percentage = 0.0D;

    if (year < 2000) {
      percentage = 17.50D;
    } else {
      percentage = 19.00D;
    }

    return percentage;
  }
}
