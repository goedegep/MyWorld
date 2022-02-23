package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * This classs contains all information on a single change of a stock position.
 * <p>
 * The following information is stored:<ul>
 * <li> the date at which the change took place.</li>
 * <li> the amount; the number of stocks added or removed.</li>
 * <li> the rate at which the stocks were obtained or sold.</li>
 * <li> the total sum (bedrag) at which the stocks were obtained or sold.</li>
 * </ul>
 * The amount is normally an integer value, as you buy/sell whole stocks. However
 * in case of an adapted history because of a redenomination, the amount can be
 * a floating point value.
 */
public class StockPositionHistory {
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  private static final SimpleDateFormat  DF =  new SimpleDateFormat("dd-MM-yyyy");
  
  private LocalDate   date;           // datum
  private boolean     integerAmount;  // Indicates whether amount is actually an int or a float.
  private float       amount;         // aantal aandelen: positief is koop, negatief verkoop
  private PgCurrency  rate;           // aan- of verkoop koers
  private PgCurrency  bedrag;         // totaalbedrag van de transactie


  public StockPositionHistory(LocalDate date) {
    this(date, 0, null, null);
  }
  
  public StockPositionHistory(LocalDate date, float amount, PgCurrency rate, PgCurrency bedrag) {
    this.integerAmount = false;
    this.date = date;
    this.amount = amount;
    this.rate = rate;
    this.bedrag = bedrag;
  }

  public StockPositionHistory(LocalDate date, int amount, PgCurrency rate, PgCurrency bedrag) {
    this.integerAmount = true;
    this.date = date;
    this.amount = amount;
    this.rate = rate;
    this.bedrag = bedrag;
  }

  public LocalDate getDate() {
    return date;
  }

  public boolean isIntegerAmount() {
    return integerAmount;
  }

  public float getAmount() {
    return amount;
  }
  
  public int getIntAmount() {
    return (int) Math.round(amount);
  }

  public void setAmount(int amount) {
    this.amount = amount;
    integerAmount = true;
  }
  
  public void setAmount(float amount) {
    this.amount = amount;
    integerAmount = false;
  }
  
  public PgCurrency getRate() {
    return rate;
  }
  
  public void setRate(PgCurrency rate) {
    this.rate = rate;
  }

  public PgCurrency getBedrag() {
    return bedrag;
  }

  public void setBedrag(PgCurrency bedrag) {
    this.bedrag = bedrag;
  }

  public String toString() {
	  StringBuilder buf = new StringBuilder();
	  
	  buf.append("Datum: " + DF.format(date));
	  if (integerAmount) {
	    buf.append(", Aantal (int): " + getIntAmount());
	  } else {
        buf.append(", Aantal (float): " + getAmount());	    
	  }
      buf.append(", Koers: ");
      if (rate != null) {
        buf.append(CF.format(rate));
      } else {
        buf.append("-");
      }
      
      buf.append(", Bedrag: ");
      if (bedrag != null) {
        buf.append(CF.format(bedrag));
      } else {
        buf.append("-");
      }

	  return buf.toString();
  }
}
