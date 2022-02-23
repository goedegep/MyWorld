package goedegep.finan.stocks;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

/**
 * This class represents a DRIP (Dividend ReInvestment Plan).
 * <p>
 * A DRIP is offered by a corporation that allows investors to reinvest their cash dividends by purchasing additional shares
 * or fractional shares on the dividend payment date. Most DRIPs allow investors to buy shares commission-free and at a significant
 * discount to the current share price, and do not allow reinvestments much lower than $10. This term is sometimes abbreviated as "DRP."<br/>
 * A DRIP consist of:
 * <ul>
 * <li>
 * The number of shares (dividends) needed for one new share. In this class this is referred to as the <b>fromAmount</b>.
 * </li>
 * <li>
 * The price per share.
 * </li>
 * </ul>
 */
public class Drip {
  /**
   * Number of shares (dividends) needed for one new share
   */
  private FixedPointValue fromAmount = null;  // Number of shares (dividends) needed for one new share
  private PgCurrency  pricePerShare = null;
  
  /**
   * Get the <b>fromAmount</b>; the number of shares (dividends) needed for one new share.
   * 
   * @return the <b>fromAmount</b>
   */
  public FixedPointValue getFromAmount() {
    return fromAmount;
  }
  
  /**
   * Set the <b>fromAmount</b>.
   * 
   * @param fromAmount The number of shares (dividends) needed for one new share.
   */
  public void setFromAmount(FixedPointValue fromAmount) {
    this.fromAmount = fromAmount;
  }
  
  /**
   * Get the price to be paid per new share.
   * 
   * @return the price per share.
   */
  public PgCurrency getPricePerShare() {
    return pricePerShare;
  }
  
  /**
   * Set the price per share
   * @param pricePerShare the price to be paid per new share
   */
  public void setPricePerShare(PgCurrency pricePerShare) {
    this.pricePerShare = pricePerShare;
  }
}
