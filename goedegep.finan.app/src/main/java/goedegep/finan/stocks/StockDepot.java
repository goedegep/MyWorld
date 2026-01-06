
// For the time being this is handled as a 'Share' depot.
// Share may later be replaced by 'Stock'.
package goedegep.finan.stocks;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import goedegep.finan.basic.PgTransaction;
import goedegep.types.model.DateRateTuplet;
import goedegep.util.datetime.DateUtil;
import goedegep.util.datetime.Quarter;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

/**
 * This class represents a depot of stocks (or shares).
 *
 * <h3>aggregate depot</h3>
 * Every bank account has its own stock depot. However, for e.g. yield calculations and overviews, it's much easier to have all
 * information in a single depot. Therefore it is possible to use separate depots (child depots) and an aggregate depot (parent depot).
 * With this constuction, any operation on a child depot is also passed on to the parent.<br/>
 * To use this, you first create the aggregate (parent) depot. Then, when creating the depot for a specific account, you pass the parent depot
 * as a parameter to the constructor.
 * <p>
 * A stock depot consist of:
 * <ul>
 * <li>
 * Stock positions per {@link Share}; type {@link StockPosition}
 * </li>
 * <li>
 * Stock fraction positions per {@link Share}; type {@link StockFractionPosition}<br/>
 * A normal stock position is an integral value, but in case of a company take-over or a  redenomination there may temporarily be a fraction.
 * A fraction can be positive or negative.
 * TODO Why isn't the fraction part of the stock position? 
 * </li>
 * <li>
 * Dividend positions per {@link ShareDividend}; type {@link StockDividendPosition}.
 * </li>
 * </ul>
 *
 */
public class StockDepot {
  private final static Logger LOGGER = Logger.getLogger(StockDepot.class.getName());

  private static final SimpleDateFormat DF =  new SimpleDateFormat("dd-MM-yyyy");

  private StockDepot                                      parentDepot = null;  // If set, all changes on the depot are also applied to this parent.
  private SortedMap<Share, StockPosition>                 stockPositions = null;
  private Map<Share, StockFractionPosition>               stockFractions = null;
  private Map<ShareDividend, StockDividendPosition>       stockDividendPositions = null;
  private Map<String, OptionPosition>                     optionPositions = null;  // Key = <type>|<shareName>|<month>|<year>|<uitoefenigskoers>
  private Map<ClaimEmission, Integer>                     claimRightPositions = null;   // Maps claimEmission to number of rights.
  private Map<Share, TerugBetaling>                       terugBetalingPositions = null; // Maps shares to terugbetaling positie.
  private PgCurrency                                      optionsInvestment = null;
  
  private StockDepotPeriodicReport<Integer>               reportForCurrentYear;
  private Map<Integer, StockDepotPeriodicReport<Integer>> yearlyReports = null;
  
  private StockDepotPeriodicReport<Quarter>               reportForCurrentQuarter;
  private Map<Quarter, StockDepotPeriodicReport<Quarter>> quarterlyReports = null;
  
  private StockDepotPeriodicReport<YearMonth>             reportForCurrentMonth;
  private Map<YearMonth, StockDepotPeriodicReport<YearMonth>> monthlyReports = null;

  /*
   * Complete stock depot part.
   */
  
  public StockDepot() {
    this(null);
  }
  
  public StockDepot(StockDepot parentDepot) {
    if (parentDepot != null) {
      this.parentDepot = parentDepot;
    }
    
    clear();
  }
  
  
  /**
   * Get the current value of the complete depot (stocks, stock fractions and stockdividens).
   */
  public PgCurrency getValue() {
    return (getValue(LocalDate.now()));
  }


  /**
   * Get the value of the complete depot (stocks, stock fractions and stockdividens) at a
   * specific date.
   * 
   * @param date The date for which the value is requested. If null, the value
   *             the value for the current date is returned.
   */
  public PgCurrency getValue(LocalDate date) {
    PgCurrency totalValue;
    PgCurrency value;

    totalValue = getStockPositionsValue(date);
    if (totalValue == null) {
      // If no value can be calculated for one part, the total value cannot be calculated.
      return null;
    }
    
    value = getStockDividendPositionsValue(date);
    if (value == null) {
      // If no value can be calculated for one part, the total value cannot be calculated.
      return null;
    }
    value = value.certifyCurrency(totalValue.getCurrency());
    totalValue = totalValue.add(value);
    
    value = getStockFractionsValue(date);
    if (value == null) {
      // If no value can be calculated for one part, the total value cannot be calculated.
      return null;
    }
    value = value.certifyCurrency(totalValue.getCurrency());
    totalValue = totalValue.add(value);
    
    value = getOptionPositionsValue(date);
    if (value == null) {
      // If no value can be calculated for one part, the total value cannot be calculated.
      return null;
    }
    value = value.certifyCurrency(totalValue.getCurrency());
    totalValue = totalValue.add(value);
    
    return totalValue;
  }

  /**
   * Get the total amount of money 'invested'.
   * 
   * @return The total investment for this depot.
   */
  public PgCurrency getTotalInvestment () {
    PgCurrency      totalInvestment;
    PgCurrency      investment;
    
    totalInvestment = getStockPositionsInvestment();
    
    investment = getStockDividendPositionsInvestment();
    investment = investment.certifyCurrency(totalInvestment.getCurrency());
    totalInvestment = totalInvestment.add(investment);
    
    investment = getStockFractionsInvestment();
    investment = investment.certifyCurrency(totalInvestment.getCurrency());
    totalInvestment = totalInvestment.add(investment);
    

    return totalInvestment;
  }

  /**
   * Convert all relevant values in the depot to Euro's.
   */
  public void convertToEuros() {
    // convert all stock positions.
    for (StockPosition stockPosition: stockPositions.values()) {
      stockPosition.convertToEuros();
    }
    
    if (parentDepot != null) {
      parentDepot.convertToEuros();
    }
    
    // for the time being it is not needed to convert fractions and stock dividends.
  }
  
  public void clear() {
    if (parentDepot != null) {
      parentDepot.clear();
    }
    stockPositions = new TreeMap<Share, StockPosition>();
    stockFractions = new HashMap<Share, StockFractionPosition>();
    stockDividendPositions = new HashMap<ShareDividend, StockDividendPosition>();
    optionPositions = new HashMap<String, OptionPosition>();
    claimRightPositions = new HashMap<ClaimEmission, Integer>();
    terugBetalingPositions = new HashMap<Share, TerugBetaling>();
    optionsInvestment = null;
    reportForCurrentYear = new StockDepotPeriodicReport<Integer>();
    yearlyReports = new HashMap<Integer, StockDepotPeriodicReport<Integer>>();
    
    reportForCurrentQuarter = new StockDepotPeriodicReport<Quarter>();
    quarterlyReports = new HashMap<Quarter, StockDepotPeriodicReport<Quarter>>();
    
    reportForCurrentMonth = new StockDepotPeriodicReport<YearMonth>();
    monthlyReports = new HashMap<YearMonth, StockDepotPeriodicReport<YearMonth>>();
    
  }

  
  /*
   * StockPositions part.
   */
  
  /**
   * Get all stock positions.
   */
  public Collection<StockPosition> getStockPositions() {
    return stockPositions.values();
  }
  
  /**
   * Get active stock positions.
   */
  public List<StockPosition> getActiveStockPositions() {
    List<StockPosition> activeStockPositions = new ArrayList<StockPosition>();
    for (StockPosition sp: stockPositions.values()) {
      if (sp.getCurrentAmount() != 0) {
        activeStockPositions.add(sp);
      }
    }
    return activeStockPositions;
  }
  
  /**
   * Get total V-Model profit of all positions.
   * <p>
   * @return the sum of all 'total buy/sell profits' of all positions, or null if any V-Model status cannot be calculated.
   */
  public PgCurrency getTotalVModelProfit() {
    PgCurrency totalVModelProfit = null;
    
    for (StockPosition sp: stockPositions.values()) {
      StockVModelStatus stockVModelStatus = sp.getVModelStatus();
      if (stockVModelStatus == null) {
        return null;
      }
      
      PgCurrency profitThisPosition = stockVModelStatus.getTotalBuySellProfit();
      if (profitThisPosition != null) {
        if (totalVModelProfit == null) {
          totalVModelProfit = profitThisPosition;
        } else {
          totalVModelProfit = totalVModelProfit.certifyCurrency(profitThisPosition.getCurrency());
          totalVModelProfit = totalVModelProfit.add(profitThisPosition);
        }
      }
    }
    
    return totalVModelProfit;
  }
  
  /**
   * Get the stock position for a specific share.
   * 
   * @param share The share for which the position is to be returned.
   */
  public StockPosition getStockPosition(Share share) {
    return stockPositions.get(share);
  }
  
  /**
   * Get the alphabetically sorted list of share names for all stock positions.
   */
  public List<String> getStockPositionsShareNames() {
    List<String> shareNames = new ArrayList<String>();
    for (StockPosition stockPosition: stockPositions.values()) {
      shareNames.add(stockPosition.getStock().getName());
    }
    Collections.sort(shareNames);
    
    return shareNames;
  }
  
  /**
   * Get the number of active positions, which are the positions for which
   * the number of shares is not zero.
   */
  public int numberOfActivePostions() {
    int numberOfActivePositions = 0;
    for (StockPosition sp: stockPositions.values()) {
      if (sp.getCurrentAmount() != 0) {
        numberOfActivePositions++;
      }
    }
    
    return numberOfActivePositions;
  }

  
  /**
   * Get the value of the stock positions on a specific date.
   * 
   * @param date The date for which the value is requested. If null,
   *             the value for the current date is returned.
   */
  public PgCurrency getStockPositionsValue(LocalDate date) {
    PgCurrency value = new PgCurrency(PgCurrency.GUILDER, 0l);

    // sum over all stock positions
    for (StockPosition sp: stockPositions.values()) {
      long amount = sp.getCurrentAmount();
      Share share = sp.getStock();
      DateRateTuplet rateTuplet;
      if (date == null) {
        rateTuplet = share.getBestRate();
      } else {
        rateTuplet = share.getBestRate(date);
      }
      if (rateTuplet != null) {
        PgCurrency positionValue = rateTuplet.getRate().multiply(amount).certifyFactor(100);
        value = value.certifyCurrency(positionValue.getCurrency());
        value = value.add(positionValue);
      } else {
        // If no value is knows for one position, the value of the total cannot be calculated.
        return null;
//        throw new RuntimeException("StockDepot: getValue: geen koers voor aandeel " + share.getName() +
//            " voor " + (date != null ? DF.format(date) : "vandaag."));
      }
    }

    return value;
  }


  /**
   * Get the total amount of money 'invested' in the stock positions.
   * 
   * @return The total investment for this depot.
   */
  public PgCurrency getStockPositionsInvestment() {
    PgCurrency      investment = new PgCurrency(PgCurrency.DEFAULT_CURRENCY, 0l);
    
    // Sommeer over alle posities.
    for (StockPosition stockPosition: stockPositions.values()) {
      PgCurrency positionInvestment = stockPosition.getInvestment();
      investment = investment.certifyCurrency(positionInvestment.getCurrency());
      investment = investment.add(positionInvestment);
    }
    
    return investment;
  }


  /**
   * Add shares to the depot.
   * @param date The date at which the shares were obtained.
   * @param stock The share.
   * @param amount The number of shares.
   * @param rate The rate at which the shares were obtained (without costs).
   * @param bedrag The total investment (including costs).
   */
  public void effectenToevoegen(LocalDate date, Share stock, int amount, PgCurrency rate, PgCurrency bedrag) {
    StockPosition   stockPosition = stockPositions.get(stock);
    // als het aandeel nog niet in de posities zit, voeg het toe.
    if (stockPosition == null) {
      stockPosition = addStockPosition(stock);
    }
    stockPosition.effectenToevoegen(date, amount, rate, bedrag);
    
    if (parentDepot != null) {
      parentDepot.effectenToevoegen(date, stock, amount, rate, bedrag);
    }
  }


  /**
   * Remove shares from the depot.
   * @param date The date at which the shares have been sold.
   * @param stock The share.
   * @param amount The number of shares.
   * @param rate The rate at which the shares were sold (without costs).
   * @param bedrag The total desinvestment (including costs).
   */
  public void effectenVerwijderen(LocalDate date, Share stock, int amount, PgCurrency rate, PgCurrency bedrag) {
    StockPosition   stockPosition = stockPositions.get(stock);
    if (stockPosition == null) {
      throw new IllegalArgumentException("Unknown stock" + stock.getName());
    }
    stockPosition.effectenVerwijderen(date, amount, rate, bedrag);    
    
    if (parentDepot != null) {
      parentDepot.effectenVerwijderen(date, stock, amount, rate, bedrag);
    }
  }

  
  /**
   * Redenominate a stock position.
   * @param date Date at which the redenomination takes place.
   * @param toStock New stock.
   */
  public Double redenomineer(LocalDate date, Share toStock) {
    Redenomination redenomination = toStock.getRedenominationFrom();
    Share fromStock = redenomination.getShare();
    StockPosition stockPosition = stockPositions.get(fromStock);
    if (stockPosition == null) {
      throw new IllegalArgumentException("Unknown stock" + fromStock.getName());
    }
    
    if (redenomination.getTerugBetaling() != null) {
      TerugBetaling terugBetaling = new TerugBetaling(fromStock, redenomination.getTerugBetaling(), stockPosition.getCurrentAmount(), toStock);
      terugBetalingPositions.put(fromStock, terugBetaling);
    }
    
    Double fraction = stockPosition.redenomineer(date, toStock);
    stockPositions.remove(fromStock);
    stockPositions.put(toStock, stockPosition);
    
    if (parentDepot != null) {
      parentDepot.redenomineer(date, toStock);
    }
    
    return fraction;
  }


  public void terugBetalingsRechtenToevoegen(Share share,
      PgCurrency betalingPerEffect, int aantalRechten) {
    TerugBetaling terugBetaling = new TerugBetaling(share, betalingPerEffect, aantalRechten);
    terugBetalingPositions.put(share, terugBetaling);
    
    if (parentDepot != null) {
      parentDepot.terugBetalingsRechtenToevoegen(share, betalingPerEffect, aantalRechten);
    }
  }
  
  
  /**
   * Verwijder een terugbetalings positie.
   * 
   * @return het aantal posities dat verwijderd is.
   */
  public TerugBetaling terugBetalingsRechtenVerwijderen(Share share) {
    TerugBetaling terugBetaling = terugBetalingPositions.remove(share);
    
    if (terugBetaling == null) {
      throw new IllegalArgumentException("No terugbetaling position for " + share.getName());
    }
    
    Share stockPositionShare = terugBetaling.getStockPositionShare();
    if (stockPositionShare == null) {
      stockPositionShare = share;
    }
    StockPosition stockPosition = stockPositions.get(stockPositionShare);
    
    PgCurrency bedrag = terugBetaling.getBetalingPerAandeel().multiply(terugBetaling.getAantalRechten());
    stockPosition.decreaseInvestment(bedrag);
    // TODO bedrag wordt nog niet meegenomen in de winst in V-model.
    
    if (parentDepot != null) {
      parentDepot.terugBetalingsRechtenVerwijderen(share);
    }
    
    return terugBetaling;
  }

  
  /**
   * Perform a take-over of a company.
   * 
   * @param date Date at which the take-over is performed.
   * @param vanEffect Share of the company that is taken over.
   * @param naarEffect New share.
   * @param vanAantal From amount; each <code>vanAantal</code> <code>vanEffect</code> shares is replaced by <code>naarAantal</code>
   *                  <code>naarEffect</code> shares.
   * @param naarAantal To amount.
   */
  public void overname(LocalDate date, Share vanEffect, Share naarEffect, int vanAantal, int naarAantal) {
    LOGGER.fine("=> date = " + DF.format(DateUtil.localDateToDate(date)) + ", vanEffect = " + vanEffect.getName() +
                ", naarEffect = " + naarEffect.getName() + ", vanAantal = " + vanAantal + ", naarAantal = " + naarAantal);
    StockPosition fromStockPosition = stockPositions.get(vanEffect);
    if (fromStockPosition == null) {
      throw new IllegalArgumentException("Over te nemen aandeel niet in depot.");
    }
    
    StockPosition toStockPosition = stockPositions.get(naarEffect);
    // als het aandeel nog niet in de posities zit, voeg het toe.
    if (toStockPosition == null) {
      toStockPosition = addStockPosition(naarEffect);
    }
    
    LOGGER.fine("currentAmount = " + fromStockPosition.getCurrentAmount());
    FixedPointValue fromPositionAmount = new FixedPointValue(fromStockPosition.getCurrentAmount(), 10000);
    FixedPointValue numberOfShares = fromPositionAmount.multiply(naarAantal).divide(vanAantal);
    // The investment stays the same.
    PgCurrency investment = fromStockPosition.getInvestment();
    PgCurrency newRate = investment.divide(numberOfShares);
    int wholeNumberOfShares = (int) numberOfShares.getIntegerPart();
    PgCurrency wholeNumberOfSharesInvestment;
    if (numberOfShares.hasFraction()) {
      wholeNumberOfSharesInvestment = newRate.multiply(wholeNumberOfShares);
      stockFractions.put(naarEffect, new StockFractionPosition(naarEffect, numberOfShares.getFraction(), investment.subtract(wholeNumberOfSharesInvestment.certifyFactor(100))));
    } else {
      wholeNumberOfSharesInvestment = investment;
    }
    toStockPosition.effectenToevoegen(date, wholeNumberOfShares, newRate, investment);
    
    fromStockPosition.effectenVerwijderen(date, fromStockPosition.getCurrentAmount(), null, investment);
    
    if (parentDepot != null) {
      parentDepot.overname(date, vanEffect, naarEffect, vanAantal, naarAantal);
    }
  }

  /**
   * Add a new stock position to the stock positions.
   * @param stock The share.
   * @return The newly added stock position.
   */
  private StockPosition addStockPosition (Share stock) {
    LOGGER.fine("=> stock=" + stock.getName());
    StockPosition stockPosition = new StockPosition(stock);
    stockPositions.put(stock, stockPosition);
    return stockPosition;
  }

  
  
  /*
   * StockDividend part
   */
  
  /**
   * Get the number of stockdividend positions.
   */
  public int numberOfStockDividendPositions() {
    return stockDividendPositions.size();
  }

  /**
   * Get the stockdividend positions.
   * 
   * @return The stockdividend positions.
   */
  public Collection<StockDividendPosition> getStockDividendPositions() {
    return stockDividendPositions.values();
  }

  
  /**
   * Get an iterator over the stockdividend positions.
   * 
   * @return An iterator over the stockdividend positions.
   */
  public Iterator<StockDividendPosition> stockDividendIterator() {
    return stockDividendPositions.values().iterator();
  }


  /**
   * Get the value of the stockdividens at a specific date.
   * 
   * @param date The date for which the value is requested. If null, the value
   *             the value for the current date is returned.
   */
  public PgCurrency getStockDividendPositionsValue(LocalDate date) {
    PgCurrency value = new PgCurrency(PgCurrency.GUILDER, 0l);

    // sum over all stockdividend positions
    for (StockDividendPosition sp: getStockDividendPositions()) {
      FixedPointValue amount = sp.getCurrentAmount();
      ShareDividend dividend = sp.getDividend();
      if (dividend.getType() != DividendType.DRIP) {
        PgCurrency positionValue = dividend.getStockDividend().getKoers();
        if (positionValue != null) {
          positionValue = positionValue.multiply(amount).certifyFactor(100);
          value = value.certifyCurrency(positionValue.getCurrency());
          value = value.add(positionValue);
        } else {
          System.out.println("StockDepot: getValue: geen koers voor StockDividend " + sp.getDividend().getReferenceString() +
              " voor " + (date != null ? DF.format(date) : "vandaag"));
        }
      }
    }
    return value;
  }

  /**
   * Get the total amount of money 'invested' in the stockdividend positions.
   * 
   * @return The total investment for this depot.
   */
  public PgCurrency getStockDividendPositionsInvestment() {
    PgCurrency      investment = new PgCurrency(PgCurrency.DEFAULT_CURRENCY, 0l);
    
    // Sommeer over alle posities.
    for (StockDividendPosition stockDividenPosition: getStockDividendPositions()) {
      PgCurrency positionInvestment = stockDividenPosition.getInvestment();
      if (positionInvestment != null) {
        investment = investment.certifyCurrency(positionInvestment.getCurrency());
        investment = investment.add(positionInvestment);
      }
    }
    
    return investment;
  }

  public FixedPointValue getStockDividendAmount(ShareDividend dividend) {
    StockDividendPosition   stockDividendPosition = stockDividendPositions.get(dividend);

    if (stockDividendPosition == null) {
      return null;
    } else {
      return stockDividendPosition.getCurrentAmount();
    }
  }

  
  /**
   * Add stockdividends to the depot.
   * 
   * @param date The date at which they are obtained.
   * @param dividend The (stock)dividend.
   * @param amount The number of stockdividends.
   * @param bedrag The total costs (the investment).
   */
  public void stockDividendenToevoegen(LocalDate date, ShareDividend dividend, FixedPointValue amount, PgCurrency bedrag) {
    StockDividendPosition   stockDividendPosition = stockDividendPositions.get(dividend);

    // als het dividend nog niet bestaat, voeg het dan toe.
    if (stockDividendPosition == null) {
      stockDividendPosition = addStockDividendPosition(dividend);
    }

    stockDividendPosition.addDividends(amount, bedrag);
    
    if (parentDepot != null) {
      parentDepot.stockDividendenToevoegen(date, dividend, amount, bedrag);
    }
  }

  
  /**
   * Remove stockdividends from the depot.
   * 
   * @param date The date at which they are sold or exchanged for shares.
   * @param dividend The (stock)dividend.
   * @param amount The amount of stockdividends.
   * @param bedrag The total amount of money obtained (the disinvestment).
   */
  public void stockDividendenVerwijderen(LocalDate date, ShareDividend dividend, FixedPointValue amount, PgCurrency bedrag) {
    StockDividendPosition   stockDividendPosition = stockDividendPositions.get(dividend);

    if (stockDividendPosition == null) {
       throw new IllegalArgumentException("Stockdividend niet in depot: " + dividend.getReferenceString());
    }

    stockDividendPosition.removeDividends(amount, bedrag);
    
    if (stockDividendPosition.getCurrentAmount() == null) {
      removeStockDividendPosition(dividend);
    }
    
    if (parentDepot != null) {
      parentDepot.stockDividendenVerwijderen(date, dividend, amount, bedrag);
    }
  }

  
  /**
   * Create a new stockdividend position and add it to the depot.
   * 
   * @param dividend The (stock)dividend for which a position is added.
   * @return The newly added position.
   */
  private StockDividendPosition addStockDividendPosition(ShareDividend dividend) {
    StockDividendPosition   stockDividendPosition = new StockDividendPosition(dividend);
    stockDividendPositions.put(dividend, stockDividendPosition);

    return stockDividendPosition;
  }
  
  /**
   * Remove a stockposition from the depot.
   * 
   * @param stockDividendPosition The position to be removed.
   */
  private void removeStockDividendPosition(ShareDividend dividend) {
    stockDividendPositions.remove(dividend);
  }

  
  
  /*
   * Stock fractions part.
   */
  
  /**
   * Get the stock fraction positions.
   */
  public Collection<StockFractionPosition> getStockFractionPostions() {
    return stockFractions.values();
  }
  
  /**
   * Remove a stock fraction.
   * @param share The share for which the fraction is to be removed.
   * @param fraction The share fraction.
   */
  public void removeStockFraction(Share share, FixedPointValue fraction) {
    StockFractionPosition stockFractionPosition = stockFractions.get(share);
    if (stockFractionPosition == null) {
      throw new IllegalArgumentException("Fraction not in depot.");
    }
    if (stockFractionPosition.getFraction().equals(fraction)) {
      throw new IllegalArgumentException("Wrong fraction size.");
    }
    stockFractions.remove(share);
    
    if (parentDepot != null) {
      parentDepot.removeStockFraction(share, fraction);
    }
  }
  
  /**
   * Get the value of the stock fractions on a specific date.
   * 
   * @param date The date for which the value is requested. If null,
   *             the value for the current date is returned.
   */
  public PgCurrency getStockFractionsValue(LocalDate date) {
    PgCurrency value = new PgCurrency(PgCurrency.GUILDER, 0l);
    if (date == null) {
      date = LocalDate.now();
    }

    // sum over all stock positions
    for (StockFractionPosition stockFractionPosition: stockFractions.values()) {
      FixedPointValue fraction = stockFractionPosition.getFraction();
      Share share = stockFractionPosition.getStock();
      DateRateTuplet rateTuplet = share.getBestRate(date);
      if (rateTuplet != null) {
        PgCurrency positionValue = rateTuplet.getRate().multiply(fraction);
        if (value.getCurrency() != positionValue.getCurrency()) {
          value = value.changeCurrency(positionValue.getCurrency());
        }
        value = value.add(positionValue);
      } else {
        System.out.println("StockDepot: getValue: geen koers voor aandeel " + share.getName() +
            " voor " + DF.format(date));
      }
    }

    return value;
  }
  

  /**
   * Get the total amount of money 'invested' in the stock fractions.
   * 
   * @return The total investment for this depot.
   */
  public PgCurrency getStockFractionsInvestment() {
    PgCurrency      investment = new PgCurrency(PgCurrency.DEFAULT_CURRENCY, 0l);
    
    // Sommeer over alle posities.
    for (StockFractionPosition sfp: stockFractions.values()) {
      PgCurrency positionInvestment = sfp.getInvestment();
      if (positionInvestment != null) {
        investment = investment.certifyCurrency(positionInvestment.getCurrency());
        investment = investment.add(positionInvestment);
      }
    }
    
    return investment;
  }

  
  /*
   * Yearly reports part.
   */
  
  /**
   * Finalize the current yearly report.
   * 
   * @param year The year for which this report is.
   */
  public void finalizeYearlyReport(int year) {
    reportForCurrentYear.setPeriod(year);

    if (yearlyReports.put(year, reportForCurrentYear) != null) {
      throw new RuntimeException("Onverwacht overschrijven van een jaarrapport.");
    }

    reportForCurrentYear = new StockDepotPeriodicReport<Integer>();
    
    if (parentDepot != null) {
      parentDepot.finalizeYearlyReport(year);
    }
  }

  
  /**
   * Get the tax report of a specific year.
   * 
   * @param year The year for which the tax report is requested.
   * @return The tax report for the specified year, or null if this report
   *         isn't available.
   */
  public StockDepotPeriodicReport<Integer> getTaxReport(int year) {
    return yearlyReports.get(year);
  }
  
  /**
   * Get a list of all tax reports, in chronological order.
   * 
   * @return A list of all tax reports in chronological order.
   */
  public List<StockDepotPeriodicReport<Integer>> getTaxReports() {
    LinkedList<StockDepotPeriodicReport<Integer>> yearlyReportsList = new LinkedList<StockDepotPeriodicReport<Integer>>(yearlyReports.values());
    if (yearlyReports.size() != 0) {
      StockDepotPeriodicReport<Integer> comparator = yearlyReportsList.get(0);
      Collections.sort(yearlyReportsList, comparator);
    }
    return yearlyReportsList;    
  }
  
  
  /**
   * Add a dividend to the current tax report.
   */
  public void addDividendToTaxReport(DividendOntvangst dividend) {
    reportForCurrentYear.addDividend(dividend);
    
    if (parentDepot != null) {
      parentDepot.addDividendToTaxReport(dividend);
    }
  }
 

  /*
   * Monthly reports part
   */

  /**
   * Get a list of all monthly reports in chronological order.
   * 
   * @return A list of all monthly reports in chronological order.
   */
  public List<StockDepotPeriodicReport<YearMonth>> getMonthlyReports() {
    LinkedList<StockDepotPeriodicReport<YearMonth>> monthlyReportList = new LinkedList<>(monthlyReports.values());
    if (monthlyReportList.size() != 0) {
      StockDepotPeriodicReport<YearMonth> comparator = monthlyReportList.get(0);
      Collections.sort(monthlyReportList, comparator);
    }
    
    return monthlyReportList;
  }
  
  /**
   * Get the years, in chronological order, for which at least one monthly report exists.
   * 
   * @return The list of years.
   */
  public Integer[] getMonthlyReportYears() {
    Set<Integer> years = new HashSet<Integer>();
    for (StockDepotPeriodicReport<YearMonth> report: monthlyReports.values()) {
      YearMonth month = report.getPeriod();
      Integer year = month.getYear();
      years.add(year);
    }
    
    return years.toArray(new Integer[years.size()]);
  }

  /**
   * Get the months, in chronological order, for which at there is a monthly report in a specific year.
   * @param year the year for which the list of months is requested.
   * @return the list of months.
   */
  public Integer[] getMonthlyReportMonths(Integer year) {
    Set<Integer> months = new HashSet<Integer>();
    for (StockDepotPeriodicReport<YearMonth> report: monthlyReports.values()) {
      YearMonth month = report.getPeriod();
      if (month.getYear() == year) {
        months.add(month.getMonthValue());
      }
    }
    
    return months.toArray(new Integer[months.size()]);
  }
  
  /**
   * Get a specific monthly report.
   * 
   * @param year The year for which the report is requested.
   * @param month The month (1 - 12) for which the report is requested.
   * @return The monthly report for the specified month of the specified year, or
   *         null is this is not available.
   */
  public StockDepotPeriodicReport<YearMonth> getMonthlyReport(int year, int month) {
    return monthlyReports.get(YearMonth.of(year, month));
  }
  
  public void addTransactionToMonthlyReport(PgTransaction transaction) {
    reportForCurrentMonth.addTransaction(transaction);
  }
  
  public void finalizeMonthlyReport(int year, int month) {
    YearMonth monthObj = YearMonth.of(year, month);
    reportForCurrentMonth.setPeriod(monthObj);

    for (StockPosition sp: stockPositions.values()) {
      if (sp.getCurrentAmount() > 0) {
        reportForCurrentMonth.addSharePosition(sp.getStock(), sp.getCurrentAmount());
      }
    }

    for (StockFractionPosition sfp: stockFractions.values()) {
      reportForCurrentMonth.addShareFractionPosition(sfp.getStock(), sfp.getFraction());
    }

    for (StockDividendPosition sdp: stockDividendPositions.values()) {
      reportForCurrentMonth.addStockDividendPosition(sdp.getDividend(), sdp.getCurrentAmount());
    }

    for (OptionPosition op: optionPositions.values()) {
      reportForCurrentMonth.addOptionPosition(op.getOptionSerie(), op.getPosition());
    }

    if (monthlyReports.put(monthObj, reportForCurrentMonth) != null) {
      throw new RuntimeException("Onverwacht overschrijven van een maandrapport.");
    }

    if (parentDepot != null) {
      parentDepot.finalizeMonthlyReport(year, month);
    }

    reportForCurrentMonth = new StockDepotPeriodicReport<YearMonth>();
  }
  

  /*
   * Quarter reports part.
   */
  
  /**
   * Get a list of all quarter reports in chronological order.
   * 
   * @return A list of all quarter reports in chronological order.
   */
  public List<StockDepotPeriodicReport<Quarter>> getQuarterReports() {
    LinkedList<StockDepotPeriodicReport<Quarter>> quarterReportList = new LinkedList<StockDepotPeriodicReport<Quarter>>(quarterlyReports.values());
    if (quarterReportList.size() != 0) {
      StockDepotPeriodicReport<Quarter> comparator = quarterReportList.get(0);
      Collections.sort(quarterReportList, comparator);
    }
    return quarterReportList;    
  }

  
  /**
   * Get the years, in chronological order, for which at least one quarterly report exists.
   * 
   * @return The list of years.
   */
  public Integer[] getQuarterlyReportYears() {
    Set<Integer> years = new HashSet<Integer>();
    for (StockDepotPeriodicReport<Quarter> report: quarterlyReports.values()) {
      Quarter quarter = report.getPeriod();
      Integer year = quarter.getYear();
      years.add(year);
    }
    
    return years.toArray(new Integer[years.size()]);
  }
  
  
  /**
   * Get a specific quarter report.
   * 
   * @param year The year for which the report is requested.
   * @param quarter The quarter (1 - 4) for which the report is requested.
   * @return The quarter report for the specified quarter of the specified year, or
   *         null is this is not available.
   */
  public StockDepotPeriodicReport<Quarter> getQuarterReport(int year, int quarter) {
    return quarterlyReports.get(new Quarter(year, quarter));
  }
  
  
  /**
   * Generate a Quarter report and add it to the depot.
   * 
   * @param year The year to which the repost applies.
   * @param quarter The quarter to which the report applies.
   */
  public void generateQuarterReport(int year, int quarter) {
    Quarter quarterObj = new Quarter(year, quarter);
    reportForCurrentQuarter.setPeriod(quarterObj);
    
    for (StockPosition sp: stockPositions.values()) {
      if (sp.getCurrentAmount() > 0) {
        reportForCurrentQuarter.addSharePosition(sp.getStock(), sp.getCurrentAmount());
      }
    }
    
    for (StockFractionPosition sfp: stockFractions.values()) {
      reportForCurrentQuarter.addShareFractionPosition(sfp.getStock(), sfp.getFraction());
    }
    
    for (StockDividendPosition sdp: stockDividendPositions.values()) {
      reportForCurrentQuarter.addStockDividendPosition(sdp.getDividend(), sdp.getCurrentAmount());
    }
    
    for (OptionPosition op: optionPositions.values()) {
      reportForCurrentQuarter.addOptionPosition(op.getOptionSerie(), op.getPosition());
    }
    
    if (quarterlyReports.put(quarterObj, reportForCurrentQuarter) != null) {
      throw new RuntimeException("Onverwacht overschrijven van een kwartaalrapport.");
    }
    
    if (parentDepot != null) {
      parentDepot.generateQuarterReport(year, quarter);
    }
    
    reportForCurrentQuarter = new StockDepotPeriodicReport<Quarter>();
        
  }


  /*
   * Options part
   */

  public Collection<OptionPosition> getOptionPositions() {
    return optionPositions.values();
  }
  
  public PgCurrency getOptionsInvestment() {
    return optionsInvestment;
  }
  
  public void optiesToevoegen(LocalDate date, OptionSerie optionSerie, int aantalContracten, PgCurrency rate, PgCurrency bedrag) {
    // Generate key.
    String key = OptionSerie.createOptionSeriesKey(optionSerie);
    
    // Get the current position.
    OptionPosition optionPosition = optionPositions.get(key);
    
    // If it doesn't exist, add it.
    if (optionPosition == null) {
      optionPosition = new OptionPosition(optionSerie);
      optionPositions.put(key, optionPosition);
    }
        
    // Add new amount to position.
    optionPosition.addToPosition(aantalContracten);
        
    // Add 'bedrag' from investment.
    increaseOptionsInvestment(bedrag);
    
    if (parentDepot != null) {
      parentDepot.optiesToevoegen(date, optionSerie, aantalContracten, rate, bedrag);
    }
  }
  
  public void optiesVerwijderen(LocalDate date, OptionSerie optionSerie, int aantalContracten, PgCurrency rate, PgCurrency bedrag, boolean allowNegativePosition) {
    // Generate key.
    String key = OptionSerie.createOptionSeriesKey(optionSerie);
    
    // Get the current position.
    OptionPosition optionPosition = optionPositions.get(key);
    
    // Throw an exception if it doesn't exist.
    if (optionPosition == null) {
      if (allowNegativePosition) {
        optionPosition = new OptionPosition(optionSerie);
        optionPositions.put(key, optionPosition);
      } else {
        throw new IllegalArgumentException("Opties niet in depot" + key);
      }
    }
        
    // Subtract amount from position.
    optionPosition.subtractFromPosition(aantalContracten, allowNegativePosition);
    
    // Remove the position if the number of contracts is now 0.
    if (optionPosition.getPosition() == 0) {
      optionPositions.remove(key);
    }
        
    // Subtract 'bedrag' from investment.
    decreaseOptionsInvestment(bedrag);
    
    if (parentDepot != null) {
      parentDepot.optiesVerwijderen(date, optionSerie, aantalContracten, rate, bedrag, allowNegativePosition);
    }
  }
  
  /**
   * Get the value of the option positions on a specific date.
   * 
   * @param date The date for which the value is requested. If null,
   *             the value for the current date is returned.
   */
  public PgCurrency getOptionPositionsValue(LocalDate date) {
    PgCurrency value = new PgCurrency(PgCurrency.GUILDER, 0l);

    // sum over all option positions
    for (OptionPosition op: optionPositions.values()) {
      long amount = op.getPosition();
      OptionSerie optionSerie = op.getOptionSerie();
      DateRateTuplet rateTuplet;
      if (date == null) {
        rateTuplet = optionSerie.getBestRate();
      } else {
        rateTuplet = optionSerie.getBestRate(date);
      }
      if (rateTuplet != null) {
        PgCurrency positionValue = rateTuplet.getRate().multiply(amount).certifyFactor(100);
        value = value.certifyCurrency(positionValue.getCurrency());
        value = value.add(positionValue);
      } else {
        throw new RuntimeException("StockDepot: getValue: geen koers voor optie serie " + optionSerie +
            " voor " + (date != null ? DF.format(date) : "vandaag."));
      }
    }

    return value;
  }
  
  public void optieExpiratie(int expirationMonth, int expirationYear) {
    // Iterate over all positions, if the position matches the expiration date
    // add it to a list of entries to be removed.
    // Then remove the entries. We can not directly remove them, because this
    // would give problems with the iterator.
    List<String> keysToBeRemoved = new LinkedList<String>();
    for (String key: optionPositions.keySet()) {
      OptionPosition optionPosition = optionPositions.get(key);
      OptionSerie optionSerie = optionPosition.getOptionSerie();
      if ((optionSerie.getExpirationMonth() == expirationMonth)  &&
          (optionSerie.getExpirationYear() == expirationYear)) {
        keysToBeRemoved.add(key);
      }
    }
    
    for (String key: keysToBeRemoved) {
      optionPositions.remove(key);   
    }
    
    if (parentDepot != null) {
      parentDepot.optieExpiratie(expirationMonth, expirationYear);
    }
  }
  
  private void increaseOptionsInvestment(PgCurrency bedrag) {
    if (bedrag != null) {
      if (optionsInvestment != null) {
        if ((optionsInvestment.getCurrency() == PgCurrency.GUILDER)  &&
            (bedrag.getCurrency() == PgCurrency.EURO)) {
          optionsInvestment = optionsInvestment.changeCurrency(PgCurrency.EURO);
        }
        optionsInvestment = optionsInvestment.add(bedrag.certifyCurrency(optionsInvestment.getCurrency()));
      } else {
        optionsInvestment = bedrag;
      }
    }
  }
  
  private void decreaseOptionsInvestment(PgCurrency bedrag) {
    if (bedrag != null) {
      if (optionsInvestment != null) {
        if ((optionsInvestment.getCurrency() == PgCurrency.GUILDER)  &&
            (bedrag.getCurrency() == PgCurrency.EURO)) {
          optionsInvestment = optionsInvestment.changeCurrency(PgCurrency.EURO);
        }
        optionsInvestment = optionsInvestment.subtract(bedrag.certifyCurrency(optionsInvestment.getCurrency()));
      } else {
        optionsInvestment = bedrag.changeSign();
      }
    }
  }

  
  /*
   * Claim rights part.
   */
  public int numberOfClaimRightPositions() {
    return claimRightPositions.size();
  }
  
  public Set<ClaimEmission> getClaimRightPositions() {
    return claimRightPositions.keySet();
  }
  
  public int getClaimRightPosition(ClaimEmission claimEmission) {
    Integer position = claimRightPositions.get(claimEmission);
    if (position != null) {
      return position;
    } else {
      return 0;
    }
  }
  
  public void claimRechtenToevoegen(LocalDate date, ClaimEmission claimEmission, int numberOfRights) {
    Integer currentPosition = claimRightPositions.get(claimEmission);
    int newPosition;
    
    if (currentPosition != null) {
      newPosition = currentPosition + numberOfRights;
    } else {
      newPosition = numberOfRights;
    }
    
    if (newPosition != 0) {
      claimRightPositions.put(claimEmission, newPosition);
    } else {
      if (currentPosition != null) {
        claimRightPositions.remove(claimEmission);
      }
    }
    
    if (parentDepot != null) {
      parentDepot.claimRechtenToevoegen(date, claimEmission, numberOfRights);
    }
  }

  public void claimRechtenVerwijderen(LocalDate date, ClaimEmission claimEmission, int numberOfRights) {
    // get the current position (throw an exception if it doesn't exist)
    Integer currentPosition = claimRightPositions.get(claimEmission);
    if (currentPosition == null) {
      throw new IllegalArgumentException("Poging emissie rechten te verwijderen die niet bestaan: " +
          claimEmission.getShare().getName() + " " +
          claimEmission.getId());
    }
    
    // if the new postion is 0, remove the position, else update it.
    if (currentPosition == numberOfRights) {
      claimRightPositions.remove(claimEmission);
    } else {
      claimRightPositions.put(claimEmission, currentPosition - numberOfRights);
    }
    
    if (parentDepot != null) {
      parentDepot.claimRechtenVerwijderen(date, claimEmission, numberOfRights);
    }
  }
}