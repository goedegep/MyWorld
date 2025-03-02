package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;


/**
 * This class extends a StockPositionHistory for the case of
 * a redenomination. In case of a redenomination the history
 * is adapted, but the original history is kept in this special
 * history element.
 */
public class StockPositionHistoryRedenomination extends StockPositionHistory {
  private StockPositionHistory originalHistory;

  public StockPositionHistoryRedenomination(StockPositionHistory sph,
      int fromAmount, int toAmount) {
    super(sph.getDate());
    
    // Three possibilities for the amount:
    // 1. Is int and stays int (no remainder)
    // 2. Is int but becomes float (remainder)
    // 3. Is float and stays float
    // 2. and 3. can be combined as in both situations the calculation is in floats.
    if (sph.isIntegerAmount()  &&
        (sph.getIntAmount() * toAmount % fromAmount == 0)) {
      // 
      setAmount(sph.getIntAmount() * toAmount / fromAmount);
    } else {
      setAmount((float) sph.getAmount() * toAmount / fromAmount);
    }
    
    if (sph.getRate() != null) {
      PgCurrency originalRate = sph.getRate();
      int factor = originalRate.getFactor();
      long amount = originalRate.getAmount();
      // Verhoog het aantal decimalen, als de omzetting tot een afronding leidt.
      while (factor < 10000  &&
          amount * fromAmount % toAmount != 0) {
        factor *= 10;
        amount *= 10;
      }
      setRate(new PgCurrency(originalRate.getCurrency(), Math.round((double) amount * fromAmount / toAmount), factor));
    }
    
    setBedrag(sph.getBedrag());
    
    originalHistory = sph;
  }

  public StockPositionHistory getOriginalHistory() {
    return originalHistory;
  }
  
  public String toString() {
	  return super.toString() + " (original History:: " + originalHistory.toString() + ")";
  }
}
