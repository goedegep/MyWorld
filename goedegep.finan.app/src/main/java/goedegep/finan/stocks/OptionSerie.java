package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OptionSerie extends Tradable implements Comparator<OptionSerie> {
  private static final PgCurrencyFormat    CF =  new PgCurrencyFormat();
  
  private static final String[]            MONTH_NAMES = {
    "JAN", "FEB", "MAR", "APR", "MEI", "JUN",
    "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
  
  private static Map<String, OptionSerie> optionSeries = new HashMap<String, OptionSerie>();  // all known option series
  private OptionType               optionType;
  private Share                    share;
  private int                      expirationMonth;
  private int                      expirationYear;
  private PgCurrency               uitoefeningsKoers;
//  private Map<Quarter, PgCurrency> quarterRates = new HashMap<Quarter, PgCurrency>();  // Key is "year-quarter", quarter 0 is start of year.
  
  public OptionSerie(OptionType optionType, Share share, int expirationMonth,
      int expirationYear, PgCurrency uitoefeningsKoers) {
    this.optionType = optionType;
    this.share = share;
    this.expirationMonth = expirationMonth;
    this.expirationYear = expirationYear;
    this.uitoefeningsKoers = uitoefeningsKoers;
    
    optionSeries.put(createOptionSeriesKey(optionType, share, expirationMonth, expirationYear, uitoefeningsKoers), this);
  }


  /**
   * Create a key for option position, being <type>'|'<shareName>'|'<month>'|'<year>'|'<uitoefenigskoers>.
   * 
   * @param optionType The option type (call or put).
   * @param share The share to which the option applies.
   * @param expirationMonth The expiration month of the option series.
   * @param expirationYear The expiration year of the option series.
   * @param uitoefeningsKoers The rate of the option series.
   */
  public static String createOptionSeriesKey(OptionType optionType, Share share, int expirationMonth, int expirationYear, PgCurrency uitoefeningsKoers) {
    StringBuilder buf = new StringBuilder();
    buf.append(optionType).append('|').append(share.getName());
    buf.append('|').append(String.valueOf(expirationMonth));
    buf.append('|').append(String.valueOf(expirationYear));
    buf.append('|').append(String.valueOf(uitoefeningsKoers.getCurrency()));
    buf.append('|').append(String.valueOf(uitoefeningsKoers.getAmount()));
    return  buf.toString();
  }
  
  public static String createOptionSeriesKey(OptionSerie optionSerie) {
    return  createOptionSeriesKey(optionSerie.optionType, optionSerie.share,
        optionSerie.expirationMonth, optionSerie.expirationYear,
        optionSerie.uitoefeningsKoers);
  }
  
  public static Collection<OptionSerie> getOptionSeries() {
    return optionSeries.values();  
  }
  
  public static OptionSerie getOptionSerie(String name) {
    for (OptionSerie optionSerie: getOptionSeries()) {
      if (optionSerie.toString().equals(name)) {
        return optionSerie;
      }
    }
    
    return null;
  }
  
  public static OptionSerie getOptionSerie(OptionType optionType, Share share, int expirationMonth,
      int expirationYear, PgCurrency uitoefeningsKoers) {
    return optionSeries.get(createOptionSeriesKey(optionType, share, expirationMonth, expirationYear, uitoefeningsKoers));
  }
  
  public OptionType getOptionType() {
    return optionType;
  }

  public Share getShare() {
    return share;
  }

  public int getExpirationMonth() {
    return expirationMonth;
  }

  public String getExpirationMonthText() {
    return MONTH_NAMES[expirationMonth - 1];
  }

  public int getExpirationYear() {
    return expirationYear;
  }

  public PgCurrency getUitoefeningsKoers() {
    return uitoefeningsKoers;
  }
  
  public String toString() {
    StringBuilder      output = new StringBuilder();

    output.append(optionType.getText());
    output.append(" ").append(share.getName());
    output.append(" ").append(MONTH_NAMES[expirationMonth - 1]);
    output.append(" ").append(expirationYear);
    output.append(" ").append(CF.format(uitoefeningsKoers));
    
    return output.toString();
  }
   
//  public void addQuarterRate(int year, int quarter, PgCurrency rate) {
//    if (quarterRates.put(new Quarter(year, quarter), rate) != null) {
//      throw new RuntimeException("Quarter rate is overwritten.");
//    }
//  }
//  
//  public PgCurrency getQuarterRate(int year, int quarter) {
//    return quarterRates.get(new Quarter(year, quarter));
//  }
//  
//  public Collection<Quarter> getQuarterRateKeys() {
//    return quarterRates.keySet();
//  }
  
  public int compare(OptionSerie o1, OptionSerie o2) {
    int result;
    result = o1.share.compare(o1.share, o2.share);
    if (result != 0) {
      return result;
    }
    if (o1.expirationMonth < o2.expirationMonth) {
      return -1;
    }
    if (o1.expirationMonth > o2.expirationMonth) {
      return 1;
    }
    if (o1.expirationYear < o2.expirationYear) {
      return -1;
    }
    if (o1.expirationYear > o2.expirationYear) {
      return 1;
    }
    result = o1.optionType.compare(o1.optionType, o2.optionType);
    if (result != 0) {
      return result;
    }
    return o1.uitoefeningsKoers.compareTo(o2.uitoefeningsKoers);
  }
}