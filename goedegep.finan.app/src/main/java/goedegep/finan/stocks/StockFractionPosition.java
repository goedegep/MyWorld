package goedegep.finan.stocks;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;


/**
 * This class represents a stock fraction position.
 * <p>
 * A stock fraction position consist of:
 * <ul>
 * <li>
 * The {@link Share} to which this position applies.
 * </li>
 * <li>
 * The current fraction of a share (which can be positive or negative).
 * </li>
 * <li>
 * The current investment (which may be positive or negative).<br/>
 * </li>
 * </ul>
 * 
 * Note: For the time being this is handled as a 'Share' fraction position. 
 * Share may later be replaced by 'Stock'.
 */

public class StockFractionPosition {
  private Share           stock;       // Identificatie
  private FixedPointValue fraction;    // Huidig aantal in 4 decimalen.
  private PgCurrency      investment;  // Investering

  public StockFractionPosition(Share stock, FixedPointValue fraction, PgCurrency investment) {
    this.stock = stock;
    this.fraction = fraction;
    this.investment = investment;
  }

  public Share getStock() {
    return stock;
  }

  public FixedPointValue getFraction() {
    return fraction;
  }

  public PgCurrency getInvestment() {
    return investment;
  }
}
