package goedegep.finan.postbank.pbfonds;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class PbFondsStockDividend extends PbFondsTransaction {
  private static final String           STOCK_DIVIDEND_STRING = "stock dividend";
  private static final PgCurrencyFormat CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

//  int   stockDividendPercentage = -1;   // Stockdivedend percentage (maal 100)
  FixedPointValue stockDividendPercentage = null;
  FixedPointValue aantalAandelen = null;            // aantal aandelen waarover stockdividend uitgekeerd wordt

  public PbFondsStockDividend(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_STOCK_DIVIDEND;
  }

  public void setStockDividendPercentage(FixedPointValue stockDividendPercentage) {
    this.stockDividendPercentage = stockDividendPercentage;
  }

  public FixedPointValue getStockDividendPercentage() {
    return stockDividendPercentage;
  }

  public void setNumberOfShares(FixedPointValue aantalAandelen) {
    this.aantalAandelen = aantalAandelen;
  }

  public FixedPointValue getNumberOfShares() {
    return aantalAandelen;
  }

  public String getDescription() {
    return toString();
  }

  public String toString() {
    return STOCK_DIVIDEND_STRING +                                             // transaction type
           " " + FPVF.format(stockDividendPercentage) +                        // stockdividend veld
           " " + CF.format(getKoers()) +                                       // 'koers' veld
           " " + FPVF.format(aantalAandelen);        // het aantal aandelen waarover dividend uitgekeerd wordt
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbFonds        fonds = (PbFonds) this.getAccount();

    handleFirst();

    // De totale inleg verandert niet

    // Het aantal aandelen = stock dividend percentage * aantal aandelen op dividend datum
    FixedPointValue nettoAandelen = aantalAandelen.multiply(stockDividendPercentage).divide(100);

    // Verhoog het totaal aantal aandelen met de uit stock dividend verkregen aandelen
    fonds.increaseNumberOfShares(nettoAandelen);

    // registreer het vrij opneembare dividend
    PgCurrency dividend = getKoers().multiply(nettoAandelen).divide(10000);
    fonds.setVrijOpneembaarDividendValue(getBookingDate(), dividend);

    // voeg koersinformatie toe
    fonds.getFundShare().addOpeningRate(getBookingDate(), getKoers());

    handleLast();
  }
}
