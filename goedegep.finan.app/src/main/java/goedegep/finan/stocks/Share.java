package goedegep.finan.stocks;

import goedegep.types.model.DateRateTuplet;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Share extends Tradable implements Comparator<Share>, Comparable<Share> {
  private static SortedMap<String, Share> shares = new TreeMap<>();  // all known shares, accessed by name.
  private static List<ShareListener>      sharesListeners = new ArrayList<ShareListener>(10);

  private String                      name;
  private Fund                        fund;
  private boolean                     obsolete = false;
  private List<ShareDividend>         dividends;
  private Map<String, ClaimEmission>  claimEmissions = new HashMap<String, ClaimEmission>();
  private Redenomination              redenominationFrom = null;
//  private LinkedList<DateRateTuplet>  openingRates = new LinkedList<DateRateTuplet>();
//  private LinkedList<DateRateTuplet>  closingRates = new LinkedList<DateRateTuplet>();
//  private LinkedList<DateRateTuplet>  anyRates     = new LinkedList<DateRateTuplet>();
//  private SortedMap<Quarter, PgCurrency> quarterRates = new TreeMap<Quarter, PgCurrency>();  // Key is "year-quarter", quarter 0 is start of year.

  public Share(String shareName, Fund fund) {
    if (shares.get(shareName) != null) {
      throw new IllegalArgumentException("shareName already exists");
    }
    name = shareName;
    this.fund = fund;
    dividends = new LinkedList<ShareDividend>();
    shares.put(shareName, this);

    notifyListenersOnSharesUpdated();
  }

  public static Share getShare(String name) {
    if (name != null) {
      return shares.get(name);
    } else {
      return null;
    }
  }
  
  public static Collection<Share> getShares() {
    return shares.values();
  }
  
  public static void resetShares() {
    shares = new TreeMap<String, Share>();
  }

  public Fund getFund() {
    return fund;
  }

  public String getName() {
    return (name);
  }

  public boolean isObsolete() {
    return obsolete;
  }

  public void setObsolete(boolean obsolete) {
    this.obsolete = obsolete;
  }

  public List<ShareDividend> getDividends() {
    return dividends;
  }

  public void setDividends(LinkedList<ShareDividend> dividends) {
    this.dividends = dividends;

    notifyListenersOnDividendAdded(this);
  }

  public void addDividend(ShareDividend dividend) {
    dividends.add(dividend);

    notifyListenersOnDividendAdded(this);
  }

  public void addDividend(ShareDividend dividend, ShareDividend beforeDividend) {
    dividends.add(dividends.indexOf(beforeDividend), dividend);

    notifyListenersOnDividendAdded(this);
  }

  /**
   * Gets the dividend for a specific dividendReference.
   *
   * @param dividendReference
   * @return The dividend, or null if no dividend exists for the specified reference.
   */
  public ShareDividend getDividend(String dividendReference) {
    //System.out.println("Getting dividend for reference: " + dividendReference);
    for (int i = 0; i < dividends.size(); i++) {
      ShareDividend dividend = (ShareDividend) dividends.get(i);
      if (dividend.getReferenceString().compareTo(dividendReference) == 0) {
        return dividend;
      }
    }

    return null;
  }

  public void addClaimEmission(ClaimEmission claimEmission) {
    claimEmissions.put(claimEmission.getId(), claimEmission);
  }
  
  public ClaimEmission getClaimEmission(String claimId) {
    return claimEmissions.get(claimId);
  }
  
  public Collection<ClaimEmission> getClaimEmissions() {
    return claimEmissions.values();
  }
  
  public Redenomination getRedenominationFrom() {
    return redenominationFrom;
  }

  public void setRedenominationFrom(Redenomination redenominationFrom) {
    if (this.redenominationFrom != null) {
      throw new RuntimeException("Trying to overwrite redenomination from.");
    }
    this.redenominationFrom = redenominationFrom;
  }
  
  public PgCurrency adaptRateForRedenomination(PgCurrency rate) {
    return rate.multiply((double) redenominationFrom.getFromAmount()/(double) redenominationFrom.getToAmount()); 
  }

  public static void addShareListener(ShareListener listener) {
    sharesListeners.add(listener);
  }

  public static void removeShareListener(ShareListener listener) {
    sharesListeners.remove(listener);
  }

//  // Opening rates
//
//  /**
//   * Add a date/rate tuple to the list of opening rates.
//   * 
//   * @param date the date for which a rate is to be added.
//   * @param rate the rate (for date) to be added.
//   */
//  public void addOpeningRate(Date date, PgCurrency rate) {
//    addRate(date, rate, openingRates);
//  }
//
//  /**
//   * Get the opening rate for a specific date.
//   * @param date the date for which the rate is requested.
//   * @return the openingsrate for the specified date, or
//   *         null if a rate for this date is not available.
//   */
//  public PgCurrency getOpeningRate(Date date) {
//    for (DateRateTuplet tuplet: openingRates) {
//      if (date.equals(tuplet.getDate())) {
//        return tuplet.getRate();
//      }
//    }
//
//    return (null);
//  }

  /**
   * Get the best opening rate for the current date. This is the
   * last known opening rate.
   * It is assumed that there are no future rates in the list, so just
   * the last item of the list is returned.
   * 
   * @return The last known opening rate.
   */
  public DateRateTuplet getBestOpeningRate() {
    DateRateTuplet tuple = super.getBestOpeningRate();
    
    if (tuple == null  &&  redenominationFrom != null) {
        tuple = redenominationFrom.getShare().getBestOpeningRate();
        if (tuple != null) {
          // Found a tuple, correct the rate.
          DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
          adaptedDateRateTuple.setDate(tuple.getDate());
          adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
          
          return adaptedDateRateTuple;
//          tuple = new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
        }
    }
    
    return tuple;
  }

  /**
   * Get the best opening rate for a specific date. This is the
   * opening rate for this date, or for the latest date before this date.
   *
   * @param date The data for which the openings rate is requested.
   * @return the opening rate on the specified date, or on the last known date.
   */
  public DateRateTuplet getBestOpeningRate(LocalDate date) {
//    DateRateTuplet  tuple = getBestRateFromList(date, openingRates);
    DateRateTuplet  tuple = super.getBestOpeningRate(date);
    
    // If no tuple found and the share is a re-denomination of another share,
    // search that share for the best opening rate.
    if (tuple == null  &&  redenominationFrom != null) {
      tuple = redenominationFrom.getShare().getBestOpeningRate(date);
      if(tuple != null) {
        // Found a tuplet, correct the rate.
        DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
        adaptedDateRateTuple.setDate(tuple.getDate());
        adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
        
        return adaptedDateRateTuple;
//        tuple = new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
      }
    }

    return (tuple);
  }

//  /**
//   * Get all opening date/rate tuples.
//   * @return all opening date/rate tuples in time order.
//   */
//  public List<DateRateTuplet> getOpeningRates() {
//    return openingRates;
//  }
//
//  /**
//   * Get all opening date/rate tuples in a specific currency.
//   * @param currency the currency in which the rates shall be returned.
//   * @return all opening date/rate tuples in time order.
//   */
//  public List<DateRateTuplet> getOpeningRates(int currency) {
//    LinkedList<DateRateTuplet> rates = new LinkedList<DateRateTuplet>();
//
//    for (DateRateTuplet tuplet: openingRates) {
//      if (tuplet.getRate().getCurrency() == currency) {
//        rates.add(tuplet);
//      } else {
//        rates.add(new DateRateTuplet(tuplet.getDate(),
//                                     tuplet.getRate().changeCurrency(currency)));
//      }
//    }
//
//    return rates;
//  }
//
//
//  /*
//   *  Closing rates
//   */
//  
//  /**
//   * Add a date/rate tuple to the list of closing rates.
//   * 
//   * @param date the date for which a rate is to be added.
//   * @param rate the rate (for date) to be added.
//   */
//  public void addClosingRate(Date date, PgCurrency rate) {
//    addRate(date, rate, closingRates);
//  }
//
//
//  /**
//   * Get the closing rate for a specific date.
//   * @param date the date for which the rate is requested.
//   * @return the closing rate for the specified date, or
//   *         null if a rate for this date is not available.
//   */
//  public PgCurrency getClosingRate(Date date) {
//    for (DateRateTuplet tuplet: closingRates) {
//      if (date.equals(tuplet.getDate())) {
//        return tuplet.getRate();
//      }
//    }
//
//    return (null);
//  }
//  
//  /**
//   * Get all closing date/rate tuples.
//   * @return all opening date/rate tuples in time order.
//   */
//  public List<DateRateTuplet> getClosingRates() {
//    return closingRates;
//  }
//
//
//  /**
//   * Get all closing date/rate tuples in a specific currency.
//   * @param currency the currency in which the rates shall be returned.
//   * @return all closing date/rate tuples in time order.
//   */
//  public List<DateRateTuplet> getClosingRates(int currency) {
//    LinkedList<DateRateTuplet> rates = new LinkedList<DateRateTuplet>();
//
//    for (DateRateTuplet tuplet: closingRates) {
//      if (tuplet.getRate().getCurrency() == currency) {
//        rates.add(tuplet);
//      } else {
//        rates.add(new DateRateTuplet(tuplet.getDate(),
//                                     tuplet.getRate().changeCurrency(currency)));
//      }
//    }
//
//    return rates;
//  }
  
  /**
   * Get the best closing rate for the current date. This is the
   * last known closing rate.
   * It is assumed that there are no future rates in the list, so just
   * the last item of the list is returned.
   * 
   * @return The last known closing rate.
   */
  public DateRateTuplet getBestClosingRate() {
    DateRateTuplet tuple = super.getBestClosingRate();

    if (tuple == null  &&  redenominationFrom != null) {
      tuple = redenominationFrom.getShare().getBestClosingRate();
      if(tuple != null) {
        // Found a tuplet, correct the rate.
        DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
        adaptedDateRateTuple.setDate(tuple.getDate());
        adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
        
        return adaptedDateRateTuple;
//        tuple = new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
      }
    }
    
    return tuple;
  }

  /**
   * Get the best closing rate for a specific date. This is the
   * closing rate for this date, or for the latest date before this date.
   *
   * @param date The data for which the closing rate is requested.
   * @return the closing rate on the specified date, or on the last known date.
   */
  public DateRateTuplet getBestClosingRate(LocalDate date) {
//    DateRateTuplet  tuple = getBestRateFromList(date, closingRates);
    if (date == null) {
      throw new RuntimeException("date is null!!");
    }

    DateRateTuplet  tuple = super.getBestClosingRate(date);
    
    // If no tuple found and the share is a re-denomination of another share,
    // search that share for the best closing rate.
    if (tuple == null  &&  redenominationFrom != null) {
      tuple = redenominationFrom.getShare().getBestClosingRate(date);
      if(tuple != null) {
        // Found a tuplet, correct the rate.
        DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
        adaptedDateRateTuple.setDate(tuple.getDate());
        adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
        
        return adaptedDateRateTuple;
//        tuple = new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
      }
    }

    return (tuple);
  }

  /*
   *  Any rates
   */
  
  /**
   * Clear all 'any' rate information.
   */
  public static void clearAllAnyRates() {
    for (Share share: shares.values()) {
      share.clearAnyRates();
    }
  }

//  /**
//   * Add a date/rate tuple to the list of 'any' rates.
//   * 
//   * @param date the date for which a rate is to be added.
//   * @param rate the rate (for date) to be added.
//   */
//  public void addAnyRate(Date date, PgCurrency rate) {
//    addRate(date, rate, anyRates);
//  }
//
//
//  /**
//   * Get the 'any' rate for a specific date.
//   * @param date the date for which the rate is requested.
//   * @return the 'any' rate for the specified date, or
//   *         null if a rate for this date is not available.
//   */
//  public PgCurrency getAnyRate(Date date) {
//    for (DateRateTuplet tuplet: anyRates) {
//      if (date.equals(tuplet.getDate())) {
//        return tuplet.getRate();
//      }
//    }
//
//    return (null);
//  }
  
  /**
   * Get the best 'any' rate for the current date. This is the
   * last known 'any' rate.
   * It is assumed that there are no future rates in the list, so just
   * the last item of the list is returned.
   * 
   * @return The last known 'any' rate.
   */
  public DateRateTuplet getBestAnyRate() {
    DateRateTuplet tuple = super.getBestAnyRate();

    if (tuple == null  &&  redenominationFrom != null) {
      tuple = redenominationFrom.getShare().getBestAnyRate();
      if(tuple != null) {
        // Found a tuplet, correct the rate.
        DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
        adaptedDateRateTuple.setDate(tuple.getDate());
        adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
        
        return adaptedDateRateTuple;
//        tuple =  new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
      }
    }

    return tuple;
  }

  /**
   * Get the best 'any' rate for a specific date. This is the
   * 'any' rate for this date, or for the latest date before this date.
   *
   * @param date The data for which the 'any' rate is requested.
   * @return the 'any' rate on the specified date, or on the last known date.
   */
  public DateRateTuplet getBestAnyRate(LocalDate date) {
//    DateRateTuplet  tuple = getBestRateFromList(date, anyRates);
    DateRateTuplet  tuple = super.getBestAnyRate(date);
    
    // If no tuple found and the share is a re-denomination of another share,
    // search that share for the best closing rate.
    if (tuple == null  &&  redenominationFrom != null) {
      tuple = redenominationFrom.getShare().getBestAnyRate(date);
      if (tuple != null) {
        // Found a tuplet, correct the rate.
        DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
        adaptedDateRateTuple.setDate(tuple.getDate());
        adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
        
        return adaptedDateRateTuple;
//        tuple = new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
      }
    }

    return (tuple);
  }

//  /**
//   * Get all 'any' date/rate tuples.
//   * @return all 'any' date/rate tuples in time order.
//   */
//  public List<DateRateTuplet> getAnyRates() {
//    return anyRates;
//  }
//
//
//  /**
//   * Get all 'any' date/rate tuples in a specific currency.
//   * @param currency the currency in which the rates shall be returned.
//   * @return all 'any' date/rate tuples in time order.
//   */
//  public List<DateRateTuplet> getAnyRates(int currency) {
//    LinkedList<DateRateTuplet> rates = new LinkedList<DateRateTuplet>();
//
//    for (DateRateTuplet tuplet: anyRates) {
//      if (tuplet.getRate().getCurrency() == currency) {
//        rates.add(tuplet);
//      } else {
//        rates.add(new DateRateTuplet(tuplet.getDate(),
//                                     tuplet.getRate().changeCurrency(currency)));
//      }
//    }
//
//    return rates;
//  }
//  
//  
//  /*
//   * Quarter rates.
//   */
//  
//  public void addQuarterRate(int year, int quarter, PgCurrency rate) {
//    if (quarter < 0  ||  quarter > 4) {
//      throw new IllegalArgumentException();
//    }
//    
//    if (quarterRates.put(new Quarter(year, quarter), rate) != null) {
//      throw new RuntimeException("Quarter rate is overwritten.");
//    }
//  }
//  
//  public PgCurrency getQuarterRate(int year, int quarter) {
//    return quarterRates.get(new Quarter(year, quarter));
//  }
//  
//  public PgCurrency getQuarterRate(Quarter quarter) {
//    return quarterRates.get(quarter);
//  }
//  
//  public Collection<Quarter> getQuarterRateKeys() {
//    return quarterRates.keySet();
//  }
//
//  public DateRateTuplet getBestQuarterRate() {
//    return getBestQuarterRate(new Date());
//  }
  
  public DateRateTuplet getBestQuarterRate(LocalDate date) {
    DateRateTuplet tuple = super.getBestQuarterRate(date);
//    DateRateTuplet tuple = null;
//    Quarter lastQuarter = null;
//    Quarter searchQuarter = Quarter.getPreviousQuarterForDate(date);
//    for (Quarter quarter: quarterRates.keySet()) {
//      if (quarter.after(searchQuarter)) {
//        break;
//      }
//      lastQuarter = quarter;
//    }
//    
//    if (lastQuarter != null) {
//      tuple = new DateRateTuplet(lastQuarter.getEndDate(), quarterRates.get(lastQuarter));
//    }

    if (tuple == null  &&  redenominationFrom != null) {
      tuple = redenominationFrom.getShare().getBestQuarterRate(date);
      if(tuple != null) {
        // Found a tuplet, correct the rate.
        DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
        adaptedDateRateTuple.setDate(tuple.getDate());
        adaptedDateRateTuple.setRate(adaptRateForRedenomination(tuple.getRate()));
        
        return adaptedDateRateTuple;
//        tuple = new DateRateTuplet(tuple.getDate(), adaptRateForRedenomination(tuple.getRate()));
      }
    }

    return tuple;
  }

  
  /*
   * Best rates.
   */
  
  /**
   * Get the best available date/rate tuple for the current date.
   * 
   * @return the best available date/rate tuple for the current date,
   *         or null if no candidate is available.
   */
  public DateRateTuplet getBestRate() {
    // This method could also be implemented as: getBestRate(new Date()).
    // However this implementation should be faster.
    
    LocalDate date = LocalDate.now();
    DateRateTuplet tuple;
    DateRateTuplet bestTuple;

    // Start with the closing rate if available.
    bestTuple = getBestClosingRate();
    if (bestTuple != null) {
      if (bestTuple.getDate().equals(date)) {
        return bestTuple;
      }
    }

    // If there is an opening rate, use that if it has a later (better) date.
    tuple = getBestOpeningRate();
    if (tuple != null) {
      if (bestTuple == null  ||  tuple.getDate().isAfter(bestTuple.getDate())) {
        bestTuple = tuple;
      }
      if (bestTuple.getDate().equals(date)) {
        return bestTuple;
      }
    }

    // If there is an 'any' rate, use that if it has a later (better) date.
    tuple = getBestAnyRate();
    if (tuple != null) {
      if (bestTuple == null  ||  tuple.getDate().isAfter(bestTuple.getDate())) {
        bestTuple = tuple;
      }
      if (bestTuple.getDate().equals(date)) {
        return bestTuple;
      }
    }
    
    // If there is a Quarter rate, use that if it has a later (better) date.
    tuple = getBestQuarterRate();
    if (tuple != null) {
      if (bestTuple == null  ||  tuple.getDate().isAfter(bestTuple.getDate())) {
        bestTuple = tuple;
      }
    }

    return bestTuple;
  }

  /**
   * Get the best available date/rate tuple for a given date.
   * 
   * @param date the date for which the best known rate is requested.
   * @return the best available date/rate tuple for the specified date,
   *         or null if no candidate is available.
   */
  public DateRateTuplet getBestRate(LocalDate date) {
    if (date == null) {
      throw new RuntimeException("date is null!!");
    }

    DateRateTuplet tuple;
    DateRateTuplet bestTuple;

    // Start with the closing rate if available.
    bestTuple = getBestClosingRate(date);
    if (bestTuple != null) {
      if (bestTuple.getDate().equals(date)) {
        return bestTuple;
      }
    }

    // If there is an opening rate, use that if it has a later (better) date.
    tuple = getBestOpeningRate(date);
    if (tuple != null) {
      if (bestTuple == null  ||  tuple.getDate().isAfter(bestTuple.getDate())) {
        bestTuple = tuple;
      }
      if (bestTuple.getDate().equals(date)) {
        return bestTuple;
      }
    }

    // If there is an 'any' rate, use that if it has a later (better) date.
    tuple = getBestAnyRate(date);
    if (tuple != null) {
      if (bestTuple == null  ||  tuple.getDate().isAfter(bestTuple.getDate())) {
        bestTuple = tuple;
      }
      if (bestTuple.getDate().equals(date)) {
        return bestTuple;
      }
    }
    
    // If there is a Quarter rate, use that if it has a later (better) date.
    tuple = getBestQuarterRate(date);
    if (tuple != null) {
      if (bestTuple == null  ||  tuple.getDate().isAfter(bestTuple.getDate())) {
        bestTuple = tuple;
      }
    }

    return bestTuple;
  }


  public int hashCode() {
    return name.hashCode();
  }
  
  private static void notifyListenersOnSharesUpdated() {
    Iterator<ShareListener> it = sharesListeners.iterator();
    while (it.hasNext()) {
      ShareListener listener = it.next();
      listener.SharesUpdated();
    }
  }

  private static void notifyListenersOnDividendAdded(Share share) {
    Iterator<ShareListener> it = sharesListeners.iterator();
    while (it.hasNext()) {
      ShareListener listener = it.next();
      listener.ShareDividendAdded(share);
    }
  }

//  /**
//   * Add a date/rate tuple to any of the rate lists.
//   * 
//   * @param date the date for which a rate is to be added.
//   * @param rate the rate (for date) to be added.
//   * @param rates the list to which the new tuplet is to be added.
//   */
//  private void addRate(Date date, PgCurrency rate, LinkedList<DateRateTuplet> rates) {
//    DateRateTuplet  currentTuplet;
//    Date            currentDate;
//    DateRateTuplet  newOpeningRate = new DateRateTuplet(date, rate);
//    boolean         inserted;
//
//    inserted = false;
//    for (int index = 0; index < rates.size()  &&  !inserted; index++) {
//      currentTuplet = rates.get(index);
//      currentDate = currentTuplet.getDate();
//      if (date.before(currentDate)) {
//        // insert before this tuplet.
//        rates.add(index, newOpeningRate);
//        inserted = true;
//      } else if (date.equals(currentDate)) {
//        // rate for this date already exists, so don't insert.
//        return;
//      }
//    }
//
//    // not inserted yet, so append at the end.
//    if (!inserted) {
//      rates.add(newOpeningRate);
//    }
//  }
//  
//  private DateRateTuplet getBestRateFromList(Date date, List<DateRateTuplet> dateRateTuples) {
//    DateRateTuplet  lastTuple = null;
//
//    // Find the last tuple that is not after the specified date.
//    for (DateRateTuplet currentTuple: dateRateTuples) {
//      if (currentTuple.getDate().after(date)) {
//        return lastTuple;
//      } else {
//        lastTuple = currentTuple;
//      }
//    }
//    
//    return null;
//  }
  
  public int compare(Share share1, Share share2) {
    return share1.getName().compareTo(share2.getName());
  }

  public int compareTo(Share arg0) {
    return getName().compareTo(((Share) arg0).getName());
  }
}