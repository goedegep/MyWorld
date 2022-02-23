package goedegep.finan.stocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;


/**
 * This class represents a stock position.
 * <p>
 * A stock position consist of:
 * <ul>
 * <li>
 * The {@link Share} to which this position applies.
 * </li>
 * <li>
 * The current number of shares in possession (which is negative in case of a short position).<br/>
 * Initial value is 0.
 * </li>
 * <li>
 * The current investment (which may be negative).<br/>
 * Initial value is null.  This is chosen, in order not to have to specify an initial currency.
 * </li>
 * <li>
 * The history of all buys and sales.<br/>
 * This is a list of {@link StockPositionHistory}. Initially an empty list.
 * </li>
 * </ul>
 * The operations on a stock position have to be performed in the right order. It is not needed and fairly impossible to handle
 * the operations in a 'random' order:
 * <ul>
 * <li>
 * Not needed<br/>
 * The operations on a stock position are normally related to stock transactions, which shall also be handled in chronological order.
 * </li>
 * <li>
 * Fairly impossible<br/>
 * Things like a redenomination, followed by an earlier buy, is very difficult to implement.
 * </li>
 * </ul>
 * 
 * Note: For the time being this is handled as a 'Share' position. 
 * Share may later be replaced by 'Stock'.
 */
public class StockPosition implements Comparable<StockPosition> {
  private static final Logger         LOGGER = Logger.getLogger(StockPosition.class.getName());
  private static final PgCurrencyFormat cf = new PgCurrencyFormat();
  
  private Share                       share;         // Identificatie
  private int                         currentAmount; // Huidig aantal
  private PgCurrency                  investment;    // Netto investering
  private List<StockPositionHistory>  history;       // lijst van aan/verkopen

  /**
   * Create a stock position.
   * 
   * @param share The stock (share) to which the position applies.
   */
  public StockPosition(Share share) {
    this.share = share;
    currentAmount = 0;
    investment = null;
    history = new LinkedList<StockPositionHistory>();
  }

  /**
   * Get the Stock (Share) to which this position applies.
   * 
   * @return The stock (share) to which the position applies.
   */
  public Share getStock () {
    return share;
  }

  /**
   * Get the current number of stocks (shares), i.e. the current
   * position.
   * 
   * @return The current number of stocks (shares).
   */
  public int getCurrentAmount() {
    return currentAmount;
  }

  /**
   * Get the current investment.
   * 
   * @return The current investment.
   */
  public PgCurrency getInvestment() {
    return investment;
  }
  
//  /**
//   * Set the current investment.
//   * Maybe change to add / subtract value.
//   * 
//   * @param investment the new investment.
//   */
//  public void setInvestment(PgCurrency investment) {
//    this.investment = investment;
//  }

  /**
   * Increase the current investment.
   * 
   * @param amount the amount with which the new investment is increased.
   */
  public void increaseInvestment(PgCurrency amount) {
    if (investment != null) {
      investment = investment.add(amount);
    } else {
      investment = amount;
    }
  }

  /**
   * Decrease the current investment.
   * 
   * @param amount the amount with which the new investment is decreased.
   */
  public void decreaseInvestment(PgCurrency amount) {
    if (investment != null) {
      investment = investment.subtract(amount);
    } else {
      investment = amount.changeSign();
    }
  }

  /**
   * Get the history of this position.
   * @return the history as a list of events.
   */
  public List<StockPositionHistory> getHistory() {
	  return history;
  }
  
  /**
   * Compare to another stock position for ordering.
   * The compare is done based on the name of the stock to which
   * the positions apply.
   * @param stockPosition The stock position to compare to.
   */
  public int compareTo(StockPosition stockPosition) {
    return getStock().getName().compareTo(stockPosition.getStock().getName());
  }
  
  /**
   * Voeg effecten aan de positie toe.
   * In principe geldt: bedrag - (amount * rate) = kosten.
   * De volgende acties vinden plaats:<ul>
   * <li>'current amount'wordt verhoogt met amount.
   * <li>Indien het bedrag niet null is, wordt de investering met dit
   * bedrag verhoogt (of op dit bedrag geinitialiseerd).
   * <li>Er wordt een history element, met alle parameters,
   *  achter aan de history lijst toegevoegd.
   * </ul>
   * Operaties op een stock position moeten dus in de juiste volgorde
   * uitgevoerd worden.
   * 
   * @param date Datum waarop de effecten toegevoegd zijn.
   * @param amount Het aantal effecten.
   * @param rate De koers waartegen de effecten verkregen zijn, of null
   * indien ze gratis waren (zoals in het geval van bonus aandelen).
   * @param bedrag Het totale bedrag waartegen de effecten verkregen
   *  zijn, of null indien ze gratis waren (zoals in het geval van bonus aandelen).
   */
  public void effectenToevoegen(LocalDate date, int amount, PgCurrency rate, PgCurrency bedrag) {
    currentAmount += amount;
    if (bedrag != null) {
      if (investment != null) {
        investment = investment.add(bedrag);
      } else {
        investment = bedrag;
      }
    }
    StockPositionHistory hist = new StockPositionHistory(date, amount, rate, bedrag);
    history.add(hist);
  }

  /**
   * Verwijder effecten uit de positie. a*r - k = b
   * In principe geldt: bedrag = (amount * rate) - kosten.
   * De volgende acties vinden plaats:<ul>
   * <li>'current amount'wordt verlaagt met amount.
   * <li>De investering met het bedrag verlaagt (of op min het bedrag
   * geinitialiseerd).
   * <li>Er wordt een history element, met alle parameters,
   *  achter aan de history lijst toegevoegd.
   * </ul>
   * Operaties op een stock position moeten dus in de juiste volgorde
   * uitgevoerd worden.
   * 
   * @param date Datum waarop de effecten verwijderd zijn.
   * @param amount Het aantal effecten.
   * @param rate De koers waartegen de effecten verkocht zijn.
   * @param bedrag Het totale bedrag waartegen de effecten verkocht zijn.
   */
  public void effectenVerwijderen(LocalDate date, int amount, PgCurrency rate, PgCurrency bedrag) {
    currentAmount -= amount;
    
    if (investment != null) {
      investment = investment.subtract(bedrag);
    } else {
      investment = bedrag.changeSign();
    }
    
    StockPositionHistory hist = new StockPositionHistory(date, -amount, rate, bedrag.changeSign());
    history.add(hist);
  }

  /**
   * Redenomineer de aandelen van een positie.
   * The following actions take place:<ul>
   * <li>the share is set to the toStock
   * <li>the current amount is adapted (possibly in combination with
   * returning a fraction.
   * <li>all entries in the history are adapted.
   * </ul>
   * @param date Currently not used.
   * @param toStock The new stock (with the new value).
   * @return null als er geen fractie is, anders de fractie.
   */
  public Double redenomineer(LocalDate date, Share toStock) {
    Double fraction = null;
    Redenomination redenomination = toStock.getRedenominationFrom();
    int fromAmount = redenomination.getFromAmount();
    int toAmount = redenomination.getToAmount();
    if (currentAmount * toAmount % fromAmount == 0) {
      // There's no remainder, so no fraction. And it's safe to do
      // an integer calculation for the new amount.
      currentAmount = currentAmount * toAmount / fromAmount;
    } else {
      // There will be a fraction, so use floating point calculation.
      // Use rounding for the new amount.
      Double realAmount = (double) currentAmount * toAmount / fromAmount;
      currentAmount = (int) Math.round(realAmount);
      fraction = realAmount - currentAmount;
    }
    
    share = toStock;
    
    // Create a new history list.
    // For each item in the old list, there's an entry in the
    // new list. These items have an adapted content, plus a
    // reference to the original entry.
    ArrayList<StockPositionHistory> adaptedHistory =
      new ArrayList<StockPositionHistory>();
    for (StockPositionHistory sph: history) {
      adaptedHistory.add(new StockPositionHistoryRedenomination(sph, fromAmount, toAmount));
    }
    history = adaptedHistory;

    return fraction;
  }

  public String toString() {
    Integer amount = Integer.valueOf(currentAmount);
    String avaragePriceString;

    if (currentAmount != 0) {
      PgCurrency    avaragePrice = investment.divide(currentAmount);
      avaragePriceString = cf.format(avaragePrice);
    } else {
      avaragePriceString = "-";
    }

    return amount.toString()  + "\t" +
           share.getName() + "\t" +
           cf.format(investment) + "\t" + avaragePriceString;
  }


  /**
   * Get the V Model status.
   * Each sell is related to the last buy (hence the V in the name).
   * The returned status contains:<ul>
   * <li>The total profit (a negative value indicates loss).
   * <li>A list of buy - sell combinations, in chronological order of the sells.
   * <li>A list of remaining buys. The last item in the list can be a buy, which is
   * partly sold.
   * </ul>
   * @return The V Model status.
   */
  public StockVModelStatus getVModelStatus() {
    // Description of the algorithm.
    // Make two lists. the first are net buys (buys that have no related sell yet),
    // the second is profit/loss on buy sell combinations (the result list).
    // Start by creating empty lists.
    // For each history item:
    // - If it's a buy, add it to the end of buy list.
    // - If it's a sell: delete related buys from the
    //   end of the buy list and add an item to the result list.
    LinkedList<StockPositionHistory>  buys = new LinkedList<StockPositionHistory>();  // net buy list (bought and not sold yet)
    LinkedList<StockBuySellCombo>     result = new LinkedList<StockBuySellCombo>();   // result per sell.

    // Start with integer calculations, switch to floats if the entry that's being handled uses floats.
    boolean integerAmount = true;
    for (StockPositionHistory hist: history) {
      if (hist.getAmount() > 0) {
        // buy
        buys.addLast(hist);
      } else {
        // sell
        try {
        integerAmount = handleVModelSell(integerAmount, hist, buys, result);
        } catch (IllegalArgumentException e) {
          LOGGER.severe("Kan geen V Model status berekenen: " +
              e.getMessage());
          return null;
//          throw new IllegalArgumentException("Kan geen V Model status berekenen: " +
//              e.getMessage()); // TODO Zorg dat informatie correct is.
        }
      }
    }

    StockBuySellCombo combo;
    PgCurrency        totalProfit = new PgCurrency(PgCurrency.EURO, 0L);

    for (int index = 0; index < result.size(); index++) {
      combo = result.get(index);

      if (combo.getProfit() != null) {
        totalProfit = totalProfit.add(combo.getProfit());
      }
    }

    return new StockVModelStatus(totalProfit, result, buys);
  }
  
  
  private boolean handleVModelSell(boolean integerAmount, StockPositionHistory sell,
      LinkedList<StockPositionHistory>  buys, LinkedList<StockBuySellCombo> result) {
    float delta = 0.00015f;
    
    // There are two reasons for switching to float calculations:
    // - The sell amount is a float (checked here)
    // - A buy that is being handled has a float amount (checked below)
    if (integerAmount  &&  !sell.isIntegerAmount()) {
      integerAmount = false;
    }
    
    float sellAmount = -sell.getAmount();
    if (sellAmount < delta) {
      LOGGER.severe("In berekening VModel is 'amount' 0: share = " + share.getName());
      return integerAmount;
    }
    
    // begin resultaat met netto verkoop bedrag, werk altijd in Euro's.
    PgCurrency resultMoney = sell.getBedrag().changeSign().certifyCurrency(PgCurrency.EURO);
    int currencyFactor = 100;  // start with normal value, increase when needed.
    PgCurrency buyRateMoney = new PgCurrency(PgCurrency.EURO, 0L, currencyFactor);

    StockPositionHistory lastBuy = null;
    while (sellAmount > 0  &&  buys.size() > 0) {
      // pak steeds de laatste aankoop
      lastBuy = buys.removeLast();
      
      if (integerAmount  &&  !lastBuy.isIntegerAmount()) {
        integerAmount = false;
      }
      
      PgCurrency buyMoney;
      if (lastBuy.getBedrag() != null) {
        buyMoney = lastBuy.getBedrag().certifyCurrency(PgCurrency.EURO);
      } else {
        buyMoney = new PgCurrency(PgCurrency.EURO, 0L);
      }
      PgCurrency buyRate;
      if (lastBuy.getRate() != null) {
        buyRate = lastBuy.getRate().certifyCurrency(PgCurrency.EURO);
      } else {
        buyRate = new PgCurrency(PgCurrency.EURO, 0L);
      }
      float lastBuyAmount = lastBuy.getAmount();
      if (buyRate.getFactor() > currencyFactor) {
        currencyFactor = buyRate.getFactor();
        buyRateMoney = buyRateMoney.changeFactor(currencyFactor);
      }

      if (Math.abs(sellAmount - lastBuyAmount) < delta) {
        // last buy exactly fits the remainder of the sell.
        sellAmount = 0;
        resultMoney = resultMoney.subtract(buyMoney);
         buyRateMoney = buyRateMoney.add(buyRate.certifyFactor(currencyFactor).multiply(lastBuyAmount));
      } else if (sellAmount < lastBuyAmount) {
        // the last buy is bigger than the sell, so the remaining part is added back to the buys list.
        
        // Het deel van het bedrag dat bij deze verkoop gerekend moet worden.
        PgCurrency partMoney = null;
        if (integerAmount) {
          partMoney = buyMoney.multiply((long) sellAmount).divide(lastBuyAmount);
        } else {
          partMoney = buyMoney.multiply((double) sellAmount).divide(lastBuyAmount);
        }
        resultMoney = resultMoney.subtract(partMoney);
        buyRateMoney = buyRateMoney.add(buyRate.certifyFactor(currencyFactor).multiply(sellAmount));
        StockPositionHistory buyRemainder = null;
        if (integerAmount) {
          buyRemainder = new StockPositionHistory(lastBuy.getDate(), (int) (lastBuyAmount - sellAmount), lastBuy.getRate(),
              buyMoney.subtract(partMoney));
        } else {
          buyRemainder = new StockPositionHistory(lastBuy.getDate(), lastBuyAmount - sellAmount, lastBuy.getRate(),
              buyMoney.subtract(partMoney));
        }
        buys.addLast(buyRemainder);
        sellAmount = 0;
      } else {
        // the last buy is smaller than the sell.
        sellAmount -= lastBuyAmount;
        resultMoney = resultMoney.subtract(buyMoney);
        buyRateMoney = buyRateMoney.add(buyRate.certifyFactor(currencyFactor).multiply(lastBuyAmount));
      }
    }
    
    if (sellAmount > 0) {
      throw new IllegalArgumentException("Meer verkopen dan aankopen.");
    }
    
    StockBuySellCombo resultCombo = null;
    if (integerAmount) {
      try {
      resultCombo = new StockBuySellCombo(-sell.getIntAmount(), lastBuy.getDate(),
          buyRateMoney.divide(-sell.getAmount()), sell.getDate(), sell.getRate(), resultMoney);
      } catch (NullPointerException e) {
//        LOGGER.severe("sell=" + sell.toString() + ", lastBuy=" + lastBuy.toString() + ", resultMoney=" + cf.format(resultMoney));
        LOGGER.severe("sell=" + sell + ", lastBuy=" + lastBuy + ", resultMoney=" + resultMoney);
      }
    } else {
      resultCombo = new StockBuySellCombo(-sell.getAmount(), lastBuy.getDate(),
          buyRateMoney.divide(-sell.getAmount()), sell.getDate(), sell.getRate(), resultMoney);
    }
    result.addLast(resultCombo);
    
    return integerAmount;
  }

  
  public void convertToEuros() {
    investment = investment.changeCurrency(PgCurrency.EURO);
  }
}
