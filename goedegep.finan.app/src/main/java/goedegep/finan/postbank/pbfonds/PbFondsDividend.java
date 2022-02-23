package goedegep.finan.postbank.pbfonds;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class PbFondsDividend extends PbFondsTransaction {
  private static final String           DIVIDEND_STRING = "dividend";
  private static final PgCurrencyFormat CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  private PgCurrency   dividendAmount = null;  // Dividend
  private FixedPointValue  aantalAandelen = null;    // aantal aandelen waarover dividend uitgekeerd wordt

  public PbFondsDividend(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_DIVIDEND;
  }

  public void setDividendAmount(PgCurrency dividendAmount) {
    this.dividendAmount = dividendAmount;
  }

  public PgCurrency getDividendAmount() {
    return dividendAmount;
  }

  public PgCurrency getBrutoDividend() {
    // bruto dividend = dividend per aandeel * aantal aandelen op dividend datum
    return dividendAmount.multiply(aantalAandelen).divide(10000);
  }

  public static PgCurrency getDividendBelasting(PgCurrency brutoDividend) {
    // dividend belasting is 25% over het bruto dividend
    return brutoDividend.multiply(0.25);
  }

  public void setNumberOfShares(FixedPointValue aantalAandelen) {
    this.aantalAandelen = aantalAandelen;
  }

  public FixedPointValue getNumberOfShares() {
    return aantalAandelen;
  }

  public FixedPointValue getNettoAandelen(PgCurrency nettoAankoopbedrag) {
    return nettoAankoopbedrag.divide(getKoers(), 10000);
//    return new FixedPointValue((long)(10000 * nettoAankoopbedrag.divide(getKoers())+ 0.5), 10000);

//    return (long) (((double)(10000 * nettoAankoopbedrag.getAmount())) / ((double) getKoers().getAmount()) + 0.5);
  }

  public String getDescription() {
    StringBuilder      outputBuffer = new StringBuilder();

    // transaction type
    outputBuffer.append(DIVIDEND_STRING);

    // dividend veld.
    outputBuffer.append(" " + CF.format(dividendAmount));

    // 'koers' veld.
    outputBuffer.append(" " + CF.format(getKoers()));

    // het aantal aandelen waarover dividend uitgekeerd wordt.
    outputBuffer.append(" " + FPVF.format(aantalAandelen));

    return outputBuffer.toString();
  }

  public String toString() {
    StringBuilder      outputBuffer = new StringBuilder();

    // valutadatum
    //outputBuffer.append(_df.format(getBookingDate()));

    // transaction type
    outputBuffer.append(DIVIDEND_STRING);

    // dividend veld.
    outputBuffer.append(" " + CF.format(dividendAmount));

    // 'koers' veld.
    outputBuffer.append(" " + CF.format(getKoers()));

    // het aantal aandelen waarover dividend uitgekeerd wordt.
    outputBuffer.append(" " + FPVF.format(aantalAandelen));

    return outputBuffer.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbFonds        fonds = (PbFonds) this.getAccount();

    handleFirst();

    // De totale investering verandert niet

    PgCurrency brutoDividend = getBrutoDividend();
    PgCurrency dividendBelasting = getDividendBelasting(brutoDividend);
    // netto aankoop = brutoDividend - dividendBelasting
    PgCurrency nettoAankoopbedrag = brutoDividend.subtract(dividendBelasting);

    // Bepaal het aantal aandelen dat gekocht wordt
    FixedPointValue nettoAandelen = getNettoAandelen(nettoAankoopbedrag);

    // Verhoog het totaal aantal aandelen met de gekochte aandelen
    fonds.increaseNumberOfShares(nettoAandelen);

    // registreer het vrij opneembare dividend
    fonds.setVrijOpneembaarDividendValue(getBookingDate(), nettoAankoopbedrag);

    // registreer het dividend bij de jaar-totalen.
    int year = getBookingDate().getYear();
    fonds.addDividendForYear(year, brutoDividend);

    // voeg koersinformatie toe
    fonds.getFundShare().addOpeningRate(getBookingDate(), getKoers());

    handleLast();
  }
}