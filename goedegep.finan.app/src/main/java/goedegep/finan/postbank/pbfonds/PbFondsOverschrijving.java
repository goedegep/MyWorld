package goedegep.finan.postbank.pbfonds;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class PbFondsOverschrijving extends PbFondsTransaction {
  private static final String  AANKOOP_STRING = "van giro";
  private static final String  VERKOOP_STRING = "naar giro";
  private static final String  ZONDER_KOSTEN = "zonder kosten";
  
  private static final double  TRANSACTION_COSTS = 0.004;
  
  private static final PgCurrencyFormat CF =  new PgCurrencyFormat(0, false, false, true);

  // TODO i.p.v. isBijschrijving, Overschrijving splitsen in Af en Bijschrijving (zie Direktspaarrekening)
  private boolean     isBijschrijving= false;  // if true bijschrijving, else afschrijving.
  private boolean     zonderKosten = false;
  private PgCurrency  transactiekosten = null; // only valid if transaction has been handled
  private FixedPointValue aantalAandelen = null;     // only valid if transaction has been handled

  public PbFondsOverschrijving(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OVERSCHRIJVING;
  }

  public boolean isBijschrijving() {
    return isBijschrijving;
  }

  public void setBijschrijving(boolean isBijschrijving) {
    this.isBijschrijving = isBijschrijving;
  }

  public void setZonderKosten(boolean zonderKosten) {
    this.zonderKosten = zonderKosten;
  }

  public boolean getZonderKosten() {
    return zonderKosten;
  }

  public PgCurrency getTransactionCosts() {
    return transactiekosten;
  }

  public FixedPointValue getNumberOfShares() {
    return aantalAandelen;
  }

  public String getDescription() {
    return toString();
  }

  public String toString() {
    StringBuilder      output = new StringBuilder();

    // transaction type
    if (isBijschrijving) {
      output.append(AANKOOP_STRING);
    } else {
      output.append(VERKOOP_STRING);
    }
    if (zonderKosten) {
      output.append(" " + ZONDER_KOSTEN);      
    }
    
    // bedrag.
    output.append(" ");
    output.append(CF.format(getTransactionAmount()));

    // 'koers' veld.
    output.append(" tegen een koers van ");
    output.append(CF.format(getKoers()));

    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbFonds        fonds = (PbFonds) getAccount();

    this.handleFirst();

    if (isBijschrijving) {
      // aankoop (van giro)
      // verhoog de totale investering met het aankoopbedrag
      fonds.increaseInvestmentValue(getTransactionAmount());

      // transactiekosten
      if (!zonderKosten) {
        transactiekosten = getTransactionAmount().multiply(TRANSACTION_COSTS);
      } else {
        transactiekosten = new PgCurrency(getTransactionAmount().getCurrency(), 0L);
      }
      // netto aankoop = storting - 0,4 %
      PgCurrency nettoAankoopbedrag = getTransactionAmount().subtract(transactiekosten);

      // Bepaal het aantal aandelen dat gekocht wordt
      aantalAandelen = nettoAankoopbedrag.divide(getKoers(), 10000);
//      aantalAandelen = new FixedPointValue((long)(10000 * nettoAankoopbedrag.divide(getKoers())+ 0.5), 10000);
//      aantalAandelen = (long) (((double)(10000 * nettoAankoopbedrag.getAmount())) / ((double) getKoers().getAmount()) + 0.5);

      // Verhoog het totaal aantal aandelen met de gekochte aandelen
      fonds.increaseNumberOfShares(aantalAandelen);
    } else {
      // verkoop (naar giro)

      PgCurrency verkoopBedragInclKosten = null;

      // verlaag de totale investering met het verkoopbedrag
      fonds.decreaseInvestmentValue(getTransactionAmount());

      // totale verkoop is storting (eventueel verminderd met vrij opneembaar dividend) plus 0,4 % kosten
      PgCurrency vrijOpneembaarDividend = fonds.getVrijOpneembaarDividend();
      if (vrijOpneembaarDividend == null) {
        transactiekosten = getTransactionAmount().multiply(TRANSACTION_COSTS);
        verkoopBedragInclKosten = getTransactionAmount().add(transactiekosten);
      verkoopBedragInclKosten = getTransactionAmount().add(transactiekosten);
      } else if (getTransactionAmount().isGreaterThan(fonds.getVrijOpneembaarDividend())) {
        transactiekosten = getTransactionAmount().subtract(fonds.getVrijOpneembaarDividend()).multiply(TRANSACTION_COSTS);
        verkoopBedragInclKosten = getTransactionAmount().add(transactiekosten);
      } else {
        verkoopBedragInclKosten = getTransactionAmount();
      }
      fonds.decreaseVrijOpneembaarDividendValue(verkoopBedragInclKosten);

      // Bepaal het aantal aandelen dat verkocht wordt
      aantalAandelen = verkoopBedragInclKosten.divide(getKoers(), 10000);
//      aantalAandelen = new FixedPointValue((long)(10000 * verkoopBedragInclKosten.divide(getKoers())+ 0.5), 10000);
//      aantalAandelen = (long) (((double)(10000 * verkoopBedragInclKosten.getAmount())) / ((double) getKoers().getAmount()) + 0.5);

      // Verlaag het totaal aantal aandelen met de verkochte aandelen
      fonds.decreaseNumberOfShares(aantalAandelen);
    }

    // voeg koersinformatie toe
    fonds.getFundShare().addOpeningRate(getBookingDate(), getKoers());

    this.handleLast();
  }
}