package goedegep.finan.postbank.pbfonds;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.Share;
import goedegep.types.model.DateRateTuplet;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.util.text.TextWriter;

public class PbFonds extends PgAccount {
  private static final SimpleDateFormat   df = new SimpleDateFormat("dd-MM-yyyy");
  private static DecimalFormat            numberOfSharesFormat = new DecimalFormat("#,###.##");
  private static boolean                  numberOfSharesFormatInitialized = false;



  private LinkedList<YearlyData>          yearlyData = new LinkedList<YearlyData>(); // overzichtsgegevens per jaar, zoals ontvangen dividend
  private Fund                            fund;                                   // Het "Fund"
  private Share                           fundShare;                              // het aandeel.
  private FixedPointValue                 numberOfShares = new FixedPointValue(0, 10000);  // nr. of shares * 10000
  private PgCurrency                      vrijOpneembaarDividend = null;            // huidig vrij opneembaar dividend
  private PgCurrency                      investment = null;                        // huidige investering
  private LocalDate                       dividendVrijOpneembaarTot = null;       // datum to wanneer dividend vrij opneembaar is



  public PbFonds(PbFondsId fondsId) {
    super(false, true);
    setName(fondsId.getPbFundName());

    fund = Fund.getFund(fondsId.getFundName());
    if (fund == null) {
      throw new IllegalArgumentException("Bijbehorend fonds niet gevonden voor " + fondsId);
    }

    // het aandeel is afhankelijk van de openingsdatum (TODO nog te doen)!
    // voorlopig pakken we het eerst bekende aandeel.
    fundShare = (Share) fund.getShares().get(0);
  }
  
  public void clear() {
    super.clear();
    yearlyData = new LinkedList<YearlyData>();
    numberOfShares = new FixedPointValue(0, 10000);
    vrijOpneembaarDividend = null;
    investment = null;
    dividendVrijOpneembaarTot = null;
  }

  /*
   * Fonds aandeel en koers
   */

  public Share getFundShare() {
    return fundShare;
  }

  public DateRateTuplet getBestRate() {
    return fundShare.getBestRate();
  }

  /*
   * Aantal aandelen
   */

  // get: huidig aantal aandelen
  public FixedPointValue getNumberOfShares() {
    return numberOfShares;
  }

  // get: aantal aandelen op een bepaalde datum
  public FixedPointValue getNumberOfShares(LocalDate atDate) {
    FixedPointValue  numberOfShares = null;

    for (PgTransaction pgTransaction: this.getTransactions()) {
      PbFondsTransaction currentTransaction = (PbFondsTransaction) pgTransaction;
      LocalDate currentDate = currentTransaction.getBookingDate();

      if (currentDate.isAfter(atDate)) {
        break;
      } else {
        if (currentTransaction.isHandled()) {
          numberOfShares = currentTransaction.getNewNumberOfShares();
        }
      }
    }
    
    return numberOfShares;
  }

  // get: aantal aandelen per einde van een bepaald jaar
  public FixedPointValue getNumberOfSharesUltimoYear(int year) {
    LocalDate endOfYear = LocalDate.of(year, Month.DECEMBER, Month.DECEMBER.maxLength());

    return getNumberOfShares(endOfYear);
  }

  // verhoog het aantal aandelen
  public void increaseNumberOfShares(FixedPointValue amount) {
    numberOfShares = numberOfShares.add(amount);
  }

  // verlaag het aantal aandelen
  public void decreaseNumberOfShares(FixedPointValue amount) {
    numberOfShares = numberOfShares.subtract(amount);
  }


  /*
   * Munteenheid
   */

  void changeCurrency(int currency) {
    investment = investment.changeCurrency(currency);
    if (vrijOpneembaarDividend != null) {
      vrijOpneembaarDividend = vrijOpneembaarDividend.changeCurrency(currency);
    }
  }


  /*
   * Investering
   */

  // get: als PgCurrency
  public PgCurrency getInvestment() {
    return investment;
  }

  public void increaseInvestmentValue(PgCurrency amount) {
    if (investment == null) {
      investment = amount;
    } else {
      investment = investment.add(amount);
    }
  }

  public void decreaseInvestmentValue(PgCurrency amount) {
    investment = investment.subtract(amount);
  }

  /*
   * Dividend
   */

  public PgCurrency getVrijOpneembaarDividend() {
    return vrijOpneembaarDividend;
  }

  public void clearVrijOpneembaarDividendValue() {
    vrijOpneembaarDividend = null;
  }

  public void setVrijOpneembaarDividendValue(LocalDate date, PgCurrency amount) {
    vrijOpneembaarDividend = amount;
    dividendVrijOpneembaarTot = getDividenVrijOpneembaarTotVoorDividendDatum(date);
  }

  public void decreaseVrijOpneembaarDividendValue(PgCurrency amount) {
    // Als vrijOpneembaarDividend null is, is er geen vrij opneembaar dividend,
    // en kan het dus ook niet verlaagd worden.
    if (vrijOpneembaarDividend != null) {
      if (vrijOpneembaarDividend.isGreaterThan(amount)) {
        vrijOpneembaarDividend = vrijOpneembaarDividend.subtract(amount);
      } else {
        vrijOpneembaarDividend = new PgCurrency(vrijOpneembaarDividend.getCurrency(), 0L);
      }
    }
  }

  public LocalDate getDividendVrijOpneembaarTot() {
    return dividendVrijOpneembaarTot;
  }

  public void addDividendForYear(int year, PgCurrency dividend) {
    // see whether there is already an entry for 'year'
    YearlyData  currentData = null;

    boolean found = false;
    boolean passed = false;
    for (int index = 0; index < yearlyData.size() && !found  && !passed; index++) {
      currentData = (YearlyData) yearlyData.get(index);
      if (currentData._year == year) {
	      found = true;
	    } else if (currentData._year > year) {
        passed = true;
	    }
    }

    if (!found) {
      currentData = addYearlyData(year);
    }


    if (currentData._belastbaarDividend.getCurrency() != dividend.getCurrency()) {
      throw new IllegalArgumentException("Ongeldige munteenheid.");
    } else {
      currentData._belastbaarDividend = currentData._belastbaarDividend.add(dividend);
    }

  }

  public PgCurrency getBelastbaarDividendForYear(int year) {
    YearlyData  currentData = null;

    boolean found = false;
    for (int index = 0; index < yearlyData.size() && !found; index++) {
      currentData = (YearlyData) yearlyData.get(index);
      if (currentData._year == year) {
	      found = true;
	    }
    }

    if (found) {
      return currentData._belastbaarDividend;
    } else {
      return null;
    }
  }

  public PgCurrency getIngehoudenDividendBelastingForYear(int year) {
    PgCurrency brutoDividend = getBelastbaarDividendForYear(year);

    return PbFondsDividend.getDividendBelasting(brutoDividend);
  }

  /*
   * Waarde
   */

  public PgCurrency getEstimatedValue() {
    // estimated value is 'current number of shares' * 'best rate for fund share'
    DateRateTuplet  dr = fundShare.getBestRate();

    if (dr == null) {
      return null;
    } else {
      return dr.getRate().multiply(numberOfShares).divide(10000);
    }
  }

//  public PgCurrency getEstimatedValue(Date date) {
//    // estimated value is 'number of shares at date' * 'best rate for fund share at date'
//    return null;
//  }

  /*
   * Jaartotalen
   */

  public YearlyData addYearlyData(int year) {
    YearlyData  currentData = null;
    YearlyData  newData = new YearlyData(year);

    boolean inserted = false;
    boolean existing = false;
    for (int index = 0; index < yearlyData.size() && !inserted  && !existing; index++) {
      currentData = (YearlyData) yearlyData.get(index);
      if (currentData._year == year) {
        existing = true;
      } else if (currentData._year > year) {
        yearlyData.add(index, newData);
        inserted = true;
	    }
    }

    if (!inserted  && !existing) {
      yearlyData.addLast(newData);
    }

    return newData;
  }

  /*
   * Class methods
   */

  public static String numberOfSharesToString(long numberOfShares, int minimumIntegerDigits) {
    if (!numberOfSharesFormatInitialized) {
      numberOfSharesFormat.setMinimumFractionDigits(4);
      numberOfSharesFormatInitialized = true;
    }

    numberOfSharesFormat.setMinimumIntegerDigits(minimumIntegerDigits);

    return numberOfSharesFormat.format((double) numberOfShares/10000);
  }

  public static boolean isPostbankFonds(Share effect) {
    if (effect.getFund().getCompany().getName().compareTo("Postbank") == 0) {
      return true;
    } else {
      return false;
    }
  }

  /*
   * Dividend
   */

  public static LocalDate getDividenVrijOpneembaarTotVoorDividendDatum(LocalDate date) {
    int year = date.getYear();
    LocalDate vrijOpneembaarTotDate = date.plusMonths(1);
    if (year > 1990) {
      vrijOpneembaarTotDate = vrijOpneembaarTotDate.minusDays(1);
    }
    
    return vrijOpneembaarTotDate;
  }

  public String toString(PgTransaction transaction) {
    return "Not implemented";
  }

  public String toXmlString(PgTransaction transaction, String nameSpace) {
    return "Not implemented";
  }


  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("POSTBANK FONDS - " + getName());
    textWriter.newLine();
    dumpTransactions(textWriter);
  }

  private void dumpTransactions(TextWriter textWriter) throws IOException {
    textWriter.write("POSTBANK FONDS TRANSACTIONS - " + getName());
    textWriter.newLine();
    PbFondsTransaction   transaction;
    LocalDate date;

    for (int index = 1; index <= numberOfTransactions(); index++) {
      transaction = (PbFondsTransaction) getTransaction(index);

      if (!transaction.isHandled()) {
        break;
      }

      date = transaction.getBookingDate();
      if (date != null) {
        textWriter.write(df.format(DateUtil.localDateToDate(date)));
      }
      textWriter.write('\t');

      textWriter.write(transaction.getDescription());
      textWriter.write('\t');

      date = transaction.getExecutionDate();
      if (date != null) {
        textWriter.write(df.format(DateUtil.localDateToDate(date)));
      }
      textWriter.write('\t');

      String comment = transaction.getComment();
      if (comment != null) {
        textWriter.write(comment);
      }
      textWriter.newLine();
    }
  }

  public class YearlyData {
    int         _year;
    PgCurrency  _belastbaarDividend;

    YearlyData(int year) {
      _year = year;

      if (year >= 1999) {
        _belastbaarDividend = new PgCurrency(PgCurrency.EURO, 0L);
      } else {
        _belastbaarDividend = new PgCurrency(PgCurrency.GUILDER, 0L);
      }
    }
  }
}
