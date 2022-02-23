package goedegep.finan.postbank.pbsprek;

import java.time.LocalDate;
import java.time.Month;

import goedegep.util.money.PgCurrency;

public class KapitaalRekeningPolicy extends PbSpRekPolicy {
  static final PgCurrency vrijPerMaand = new PgCurrency(PgCurrency.GUILDER, 2500000L);
  static final PgCurrency vrijPerMaandJul96 = new PgCurrency(PgCurrency.GUILDER, 5000000L);
  static final double     transactionCosts = 0.01;

  @Override
  public PgCurrency getOpnameKosten(PbSpRekOverschrijving transaction) {
    PgCurrency  bedrag;
    PgCurrency  vrij;
    PgCurrency  kosten;

    LocalDate        july96 = LocalDate.of(1996, Month.JULY, 1);

    bedrag = transaction.getTransactionAmount();
    if (transaction.getBookingDate().isBefore(july96)) {
      vrij = vrijPerMaand;
    } else {
      vrij = vrijPerMaandJul96;
    }

    if (vrij.isLessThan(bedrag)) {
      PgCurrency bedragWaaroverKostenBerekendWorden = bedrag.subtract(vrij);
      kosten = bedragWaaroverKostenBerekendWorden.multiply(transactionCosts);
    } else {
      kosten = new PgCurrency(bedrag.getCurrency(), 0L);
    }

    return kosten;
  }

}
