package goedegep.finan.stocks;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.util.Comparator;


/**
 * This class represents a stock dividend or a DRIP position.
 * <p>
 * A dividend position consists of:
 * <ul>
 * <li>
 * The dividend, which contains the stock dividend (type {@link ShareDividend}) or the DRIP (type {@link Drip}).
 * </li>
 * <li>
 * The current amount as a {@link FixedPointValue}.
 * </li>
 * <li>
 * The net investment as a {@link PgCurrency}<br/>
 * You get stock dividends as a form a dividend, so at that point there is no investment.
 * But the bank will buy or shall stock dividends so a round number of shares can be obtained.
 * </li>
 * </ul>
 */
public class StockDividendPosition implements Comparator<StockDividendPosition> {
  private ShareDividend    dividend;      // the dividend is stored, i.s.o. the stockdividend
                                          // otherwise a relation of the stockdividend to the dividend is needed.
  private FixedPointValue  currentAmount; // Huidig aantal stockdividenden.
  private PgCurrency       investment;    // Netto investering

  /**
   * Create a <b>StockDividendPosition</b>.
   * <p>
   * Only the dividend is specified. The <b>current amount</b> and <b>investment</b> are both set to null.
   * 
   * @param dividend the dividend (which shall have the stock dividend, or the DRIP, set).
   */
  public StockDividendPosition(ShareDividend dividend) {
    if ((dividend.getStockDividend() == null)  &&  (dividend.getDrip() == null)) {
      throw new IllegalArgumentException("The dividend must have its stockDividend set.");
    }
    
    this.dividend = dividend;
    currentAmount = null;
    investment = null;
  }

  /**
   * Get the dividend.
   * 
   * @return the dividend
   */
  public ShareDividend getDividend () {
    return dividend;
  }

  /**
   * Get the current amount of stock dividends.
   * 
   * @return the current amount of stock dividends.
   */
  public FixedPointValue getCurrentAmount() {
    return currentAmount;
  }

  /**
   * Get the current investment.
   * 
   * @return the current investment
   */
  public PgCurrency getInvestment() {
    return investment;
  }
  
  @Override
  public int compare(StockDividendPosition pos1, StockDividendPosition pos2) {
    String s1 = (pos1).getDividend().getReferenceString();
    String s2 = (pos2).getDividend().getReferenceString();

    return s1.compareTo(s2);
  }


  /**
   * Add dividends to the position.
   * 
   * @param amount the number of dividends to add. This value shall not be null.
   * @param bedrag the costs of the dividends. This value can be null. This is typically used if there were no costs.
   */
  public void addDividends(FixedPointValue amount, PgCurrency bedrag) {
    if (amount == null) {
      throw new IllegalArgumentException("amount shall not be null");
    }
    
    if (bedrag != null) {
      if (investment != null) {
        investment = investment.add(bedrag);
      } else {
        investment = bedrag;
      }
    }
    if (currentAmount != null) {
      currentAmount = currentAmount.add(amount);
    } else {
      currentAmount = amount;
    }
  }

  /**
   * Remove dividends from the position.
   * 
   * @param amount the number of dividends to be removed. This value shall not be null.
   * @param bedrag the money obtained for the dividends.
   */
  public void removeDividends(FixedPointValue amount, PgCurrency bedrag) {
    if (amount == null) {
      throw new IllegalArgumentException("amount shall not be null");
    }
    
    currentAmount = currentAmount.subtract(amount);
    if (bedrag != null) {
      if (investment != null) {
        investment = investment.subtract(bedrag);
      } else {
        investment = bedrag.changeSign();
      }
    }
  }
}