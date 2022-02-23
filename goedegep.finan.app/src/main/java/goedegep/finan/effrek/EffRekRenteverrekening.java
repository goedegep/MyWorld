package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class EffRekRenteverrekening extends EffRekTransactie {
  private static final String            RENTE_VERREKENING_STRING = "renteverrekening";
  private static final int               BEDRAG_SIZE = 10;
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat();
  private static final PgCurrencyFormat  CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final DateTimeFormatter  DF =  DateTimeFormatter.ofPattern("dd-MM-yyyy");

  private LocalDate    vanDatum;      // begin van de periode waarover verrekening plaatsvindt
  private LocalDate    totDatum;      // einde van de periode waarover verrekening plaatsvindt
  private PgCurrency   creditRente;   // te ontvangen rente
  private PgCurrency   debetRente;    // te betalen rente

  public EffRekRenteverrekening(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_RENTEVERREKENING;
  }

  public void setVanDatum(LocalDate vanDatum) {
    this.vanDatum = vanDatum;
  }

  public LocalDate getVanDatum() {
    return vanDatum;
  }

  public void setTotDatum(LocalDate totDatum) {
    this.totDatum = totDatum;
  }

  public LocalDate getTotDatum() {
    return totDatum;
  }

  public void setCreditRente(PgCurrency creditRente) {
    this.creditRente = creditRente;
  }

  public PgCurrency getCreditRente() {
    return creditRente;
  }

  public void setDebetRente(PgCurrency debetRente) {
    this.debetRente = debetRente;
  }

  public PgCurrency getDebetRente() {
    return debetRente;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    PgCurrency creditRente = getCreditRente();
    PgCurrency debetRente = getDebetRente();
    PgCurrency netto = null;

    if (creditRente != null) {
      netto = creditRente;
    }

    if (netto == null) {
      netto = debetRente;
    } else if (debetRente != null) {
      netto = creditRente.subtract(debetRente);
    }

    if (netto != null) {
      if (netto.isNegative()) {
        output.append("af  " + CF2.format(netto.changeSign()));
      }
      else {
        output.append("bij " + CF2.format(netto));
      }
    }

    output.append(" renteverrekening " + vanDatum.format(DF) + " t/m " + totDatum.format(DF));

    if (creditRente != null && creditRente.getAmount() != 0L) {
      output.append(" creditrente: bij " + CF.format(creditRente));
    }

    if (debetRente != null && debetRente.getAmount() != 0L) {
      output.append(" debetrente: af " + CF.format(debetRente));
    }

    return output.toString();
  }

  public String toString() {
    return RENTE_VERREKENING_STRING +           // transaction type
           "\t" + DF.format(vanDatum) +            // periode en bedragen
           "\t" + DF.format(totDatum) +
           "\t" + CF.format(creditRente) +
           "\t" + CF.format(debetRente);
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    // verhoog het tegoed met de creditrente
    if (creditRente != null) {
      effectenRekening.increaseBalance(creditRente);
    }
    // verminder het tegoed met de debetrente
    if (debetRente != null) {
      effectenRekening.decreaseBalance(debetRente);
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
