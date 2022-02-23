package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;

/**
 * This class represents a dividend 'payment' on a {@link Share}.
 * <p>
 * A share dividend consists of:
 * <ul>
 * <li>
 * The <b>name</b> of the dividend, e.g. "DIV06-2" (the second dividend of a company in 2006).
 * </li>
 * <li>
 * The <b>year</b> to which the dividend applies.
 * </li>
 * <li>
 * A <b>reference</b> for this dividend. This is a generated String to identify the dividend.
 * </li>
 * <li>
 * The {@link Share} to which this dividend is related.
 * </li>
 * <li>
 * The kind of dividend, specified as a {@link DividendType}.
 * </li>
 * <li>
 * The dividend amount, for when the type is 'cash' or 'choice'.
 * </li>
 * <li>
 * A specification of the stock dividend (as type {@link StockDividend}), for when the type is 'stock dividend' or 'choice'.
 * </li>
 * <li>
 * A specification of the {@link Drip}, for when the type is 'drip'.
 * </li>
 * <li>
 * The tax percentage, which is only filled in if it is not the standard percentage.
 * </li>
 * </ul>
 *
 */
public class ShareDividend implements Comparable<ShareDividend> {
  String        name = null;          // Name of the dividend.
  Integer       year = null;          // Year to which the dividend applies.
  String        reference = null;     // Generated String to identify this dividend, see createReferenceString(name, year)
  Share         share = null;         // Share to which this dividend is related.
  DividendType  type = null;
  PgCurrency    amount = null;        // dividend bedrag, null voor type STOCK
  StockDividend stockDividend = null; // Stock Dividend
  Drip          drip = null;          // Drip
  Integer       taxPercentage = null; // Dividend tax percentage, only filled in if not standard.

  public ShareDividend() {
    this(null, null, DividendType.CASH, null, null, null);
  }

  public ShareDividend(String dividendName, Integer year, DividendType type, PgCurrency amount, Share share, StockDividend stockDividend) {
    name = dividendName;
    this.year = year;
    this.type = type;
    this.amount = amount;
    this.share = share;
    this.stockDividend = stockDividend;
    drip = null;
    updateReference();
  }

  public ShareDividend(String dividendName, Share share) {
    this(dividendName, -1, DividendType.CASH, null, share, null);
  }

  public Share getShare() {
    return share;
  }

  public void setShare(Share share) {
    this.share = share;
  }

  public String getName() {
    return name;
  }

  public void setName(String dividendName) {
    name = dividendName;
    updateReference();
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
    updateReference();
  }

  private void updateReference() {
    reference = createReferenceString(name, year);
  }

  public String getReferenceString() {
    return reference;
  }

  public DividendType getType() {
    return type;
  }

  public void setType(DividendType type) {
    this.type = type;
  }

  public StockDividend getStockDividend() {
    return stockDividend;
  }

  public void setStockDividend(StockDividend stockDividend) {
    this.stockDividend = stockDividend;
  }

  public Drip getDrip() {
    return drip;
  }

  public void setDrip(Drip drip) {
    this.drip = drip;
  }

  public PgCurrency getAmount() {
    return amount;
  }

  public void setAmount(PgCurrency amount) {
    this.amount = amount;
  }

  public Integer getTaxPercentage() {
    return taxPercentage;
  }

  public void setTaxPercentage(Integer taxPercentage) {
    this.taxPercentage = taxPercentage;
  }

  public static String createReferenceString(String name, Integer year) {
    if (name != null  && year != null) {
      return name + "_" + year;
    } else if (name != null) {
      return name;
    } else if (year != null) {
      return year.toString();
    } else {
      return null;
    }
  }
  
  public String toString() {
    return reference;
  }
  
  public boolean isValid() {
    return reference != null;
  }

  public int compareTo(ShareDividend shareDividend) {
    return getReferenceString().compareTo(shareDividend.getReferenceString());
  }
}
