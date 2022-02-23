package goedegep.finan.stocks;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import goedegep.types.model.DateRateTuplet;
import goedegep.types.model.TypesFactory;
import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;

/**
 * This is a base class for items that can be traded at a varying rate.
 * They have opening, closing and 'any' rates per day. The 'any' rate is
 * a rate at which was traded that day. This can be useful if no opening and
 * closing rate is available.
 */
public class Tradable {
  protected static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
  
  private LinkedList<DateRateTuplet>  openingRates = new LinkedList<DateRateTuplet>();
  private LinkedList<DateRateTuplet>  closingRates = new LinkedList<DateRateTuplet>();
  private LinkedList<DateRateTuplet>  anyRates     = new LinkedList<DateRateTuplet>();
  private SortedMap<Quarter, PgCurrency> quarterRates = new TreeMap<Quarter, PgCurrency>();  // Key is "year-quarter", quarter 0 is start of year.

  // Opening rates

  /**
   * Add a date/rate tuple to the list of opening rates.
   * 
   * @param date the date for which a rate is to be added.
   * @param rate the rate (for date) to be added.
   */
  public void addOpeningRate(LocalDate date, PgCurrency rate) {
    addRate(date, rate, openingRates);
  }

  /**
   * Get the opening rate for a specific date.
   * @param date the date for which the rate is requested.
   * @return the openingsrate for the specified date, or
   *         null if a rate for this date is not available.
   */
  public PgCurrency getOpeningRate(LocalDate date) {
    for (DateRateTuplet tuplet: openingRates) {
      if (date.equals(tuplet.getDate())) {
        return tuplet.getRate();
      }
    }

    return (null);
  }

  /**
   * Get the best opening rate for the current date. This is the
   * last known opening rate.
   * It is assumed that there are no future rates in the list, so just
   * the last item of the list is returned.
   * 
   * @return The last known opening rate.
   */
  public DateRateTuplet getBestOpeningRate() {
    if (openingRates.isEmpty()) {
      return null;
    } else {
      return (DateRateTuplet) openingRates.getLast();
    }
  }

  /**
   * Get the best opening rate for a specific date. This is the
   * opening rate for this date, or for the latest date before this date.
   *
   * @param date The data for which the openings rate is requested.
   * @return the opening rate on the specified date, or on the last known date.
   */
  public DateRateTuplet getBestOpeningRate(LocalDate date) {
    return getBestRateFromList(date, openingRates);
  }

  /**
   * Get all opening date/rate tuples.
   * @return all opening date/rate tuples in time order.
   */
  public List<DateRateTuplet> getOpeningRates() {
    return openingRates;
  }

  /**
   * Get all opening date/rate tuples in a specific currency.
   * @param currency the currency in which the rates shall be returned.
   * @return all opening date/rate tuples in time order.
   */
  public List<DateRateTuplet> getOpeningRates(int currency) {
    LinkedList<DateRateTuplet> rates = new LinkedList<DateRateTuplet>();

    for (DateRateTuplet tuplet: openingRates) {
      if (tuplet.getRate().getCurrency() == currency) {
        rates.add(tuplet);
      } else {
        DateRateTuplet dateRateTuplet = TYPES_FACTORY.createDateRateTuplet();
        dateRateTuplet.setDate(tuplet.getDate());
        dateRateTuplet.setRate(tuplet.getRate().changeCurrency(currency));
        rates.add(dateRateTuplet);
//        rates.add(new DateRateTuplet(tuplet.getDate(),
//                                     tuplet.getRate().changeCurrency(currency)));
      }
    }

    return rates;
  }


  /*
   *  Closing rates
   */
  
  /**
   * Add a date/rate tuple to the list of closing rates.
   * 
   * @param date the date for which a rate is to be added.
   * @param rate the rate (for date) to be added.
   */
  public void addClosingRate(LocalDate date, PgCurrency rate) {
    addRate(date, rate, closingRates);
  }


  /**
   * Get the closing rate for a specific date.
   * @param date the date for which the rate is requested.
   * @return the closing rate for the specified date, or
   *         null if a rate for this date is not available.
   */
  public PgCurrency getClosingRate(LocalDate date) {
    for (DateRateTuplet tuplet: closingRates) {
      if (date.equals(tuplet.getDate())) {
        return tuplet.getRate();
      }
    }

    return (null);
  }
  
  /**
   * Get all closing date/rate tuples.
   * @return all opening date/rate tuples in time order.
   */
  public List<DateRateTuplet> getClosingRates() {
    return closingRates;
  }


  /**
   * Get all closing date/rate tuples in a specific currency.
   * @param currency the currency in which the rates shall be returned.
   * @return all closing date/rate tuples in time order.
   */
  public List<DateRateTuplet> getClosingRates(int currency) {
    LinkedList<DateRateTuplet> rates = new LinkedList<DateRateTuplet>();

    for (DateRateTuplet tuplet: closingRates) {
      if (tuplet.getRate().getCurrency() == currency) {
        rates.add(tuplet);
      } else {
        DateRateTuplet dateRateTuplet = TYPES_FACTORY.createDateRateTuplet();
        dateRateTuplet.setDate(tuplet.getDate());
        dateRateTuplet.setRate(tuplet.getRate().changeCurrency(currency));
        rates.add(dateRateTuplet);
//        rates.add(new DateRateTuplet(tuplet.getDate(),
//                                     tuplet.getRate().changeCurrency(currency)));
      }
    }

    return rates;
  }
  
  /**
   * Get the best closing rate for the current date. This is the
   * last known closing rate.
   * It is assumed that there are no future rates in the list, so just
   * the last item of the list is returned.
   * 
   * @return The last known closing rate.
   */
  public DateRateTuplet getBestClosingRate() {
    if (closingRates.isEmpty()) {
      return null;
    } else {
      return (DateRateTuplet) closingRates.getLast();
    }
  }

  /**
   * Get the best closing rate for a specific date. This is the
   * closing rate for this date, or for the latest date before this date.
   *
   * @param date The data for which the closing rate is requested.
   * @return the closing rate on the specified date, or on the last known date.
   */
  public DateRateTuplet getBestClosingRate(LocalDate date) {
    if (date == null) {
      throw new RuntimeException("date is null!!");
    }
    
    return getBestRateFromList(date, closingRates);
  }

  /*
   *  Any rates
   */
  
  /**
   * Clear all 'any' rate information.
   */
  public void clearAnyRates() {
    anyRates = new LinkedList<DateRateTuplet>();
  }

  /**
   * Add a date/rate tuple to the list of 'any' rates.
   * 
   * @param date the date for which a rate is to be added.
   * @param rate the rate (for date) to be added.
   */
  public void addAnyRate(LocalDate date, PgCurrency rate) {
    addRate(date, rate, anyRates);
  }


  /**
   * Get the 'any' rate for a specific date.
   * @param date the date for which the rate is requested.
   * @return the 'any' rate for the specified date, or
   *         null if a rate for this date is not available.
   */
  public PgCurrency getAnyRate(LocalDate date) {
    for (DateRateTuplet tuplet: anyRates) {
      if (date.equals(tuplet.getDate())) {
        return tuplet.getRate();
      }
    }

    return (null);
  }
  
  /**
   * Get the best 'any' rate for the current date. This is the
   * last known 'any' rate.
   * It is assumed that there are no future rates in the list, so just
   * the last item of the list is returned.
   * 
   * @return The last known 'any' rate.
   */
  public DateRateTuplet getBestAnyRate() {
    if (anyRates.isEmpty()) {
      return null;
    } else {
      return anyRates.getLast();
    }
  }

  /**
   * Get the best 'any' rate for a specific date. This is the
   * 'any' rate for this date, or for the latest date before this date.
   *
   * @param date The data for which the 'any' rate is requested.
   * @return the 'any' rate on the specified date, or on the last known date.
   */
  public DateRateTuplet getBestAnyRate(LocalDate date) {
    return getBestRateFromList(date, anyRates);
  }

  /**
   * Get all 'any' date/rate tuples.
   * @return all 'any' date/rate tuples in time order.
   */
  public List<DateRateTuplet> getAnyRates() {
    return anyRates;
  }


  /**
   * Get all 'any' date/rate tuples in a specific currency.
   * @param currency the currency in which the rates shall be returned.
   * @return all 'any' date/rate tuples in time order.
   */
  public List<DateRateTuplet> getAnyRates(int currency) {
    LinkedList<DateRateTuplet> rates = new LinkedList<DateRateTuplet>();

    for (DateRateTuplet tuplet: anyRates) {
      if (tuplet.getRate().getCurrency() == currency) {
        rates.add(tuplet);
      } else {
        DateRateTuplet dateRateTuplet = TYPES_FACTORY.createDateRateTuplet();
        dateRateTuplet.setDate(tuplet.getDate());
        dateRateTuplet.setRate(tuplet.getRate().changeCurrency(currency));
        rates.add(dateRateTuplet);
//        rates.add(new DateRateTuplet(tuplet.getDate(),
//                                     tuplet.getRate().changeCurrency(currency)));
      }
    }

    return rates;
  }
  
  
  /*
   * Quarter rates.
   */
  
  public void addQuarterRate(int year, int quarter, PgCurrency rate) {
    if (quarter < 0  ||  quarter > 4) {
      throw new IllegalArgumentException();
    }
    
    if (quarterRates.put(new Quarter(year, quarter), rate) != null) {
      throw new RuntimeException("Quarter rate is overwritten.");
    }
  }
  
  public PgCurrency getQuarterRate(int year, int quarter) {
    return quarterRates.get(new Quarter(year, quarter));
  }
  
  public PgCurrency getQuarterRate(Quarter quarter) {
    return quarterRates.get(quarter);
  }
  
  public Collection<Quarter> getQuarterRateKeys() {
    return quarterRates.keySet();
  }

  public DateRateTuplet getBestQuarterRate() {
    return getBestQuarterRate(LocalDate.now());
  }
  
  public DateRateTuplet getBestQuarterRate(LocalDate date) {
    DateRateTuplet dateRateTuplet = null;
    Quarter lastQuarter = null;
    Quarter searchQuarter = Quarter.getPreviousQuarterForDate(date);
    for (Quarter quarter: quarterRates.keySet()) {
      if (quarter.after(searchQuarter)) {
        break;
      }
      lastQuarter = quarter;
    }
    
    if (lastQuarter != null) {
      dateRateTuplet = TYPES_FACTORY.createDateRateTuplet();
      dateRateTuplet.setDate(lastQuarter.getEndDate());
      dateRateTuplet.setRate(quarterRates.get(lastQuarter));
//      dateRateTuplet = new DateRateTuplet(lastQuarter.getEndDate(), quarterRates.get(lastQuarter));
    }

    return dateRateTuplet;
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
  
  /**
   * Add a date/rate tuple to any of the rate lists.
   * 
   * @param date the date for which a rate is to be added.
   * @param rate the rate (for date) to be added.
   * @param rates the list to which the new tuplet is to be added.
   */
  private void addRate(LocalDate date, PgCurrency rate, LinkedList<DateRateTuplet> rates) {
    DateRateTuplet currentTuplet;
    LocalDate currentDate;
//    DateRateTuplet  newOpeningRate = new DateRateTuplet(date, rate);
    DateRateTuplet newOpeningRate = TYPES_FACTORY.createDateRateTuplet();
    newOpeningRate.setDate(date);
    newOpeningRate.setRate(rate);    
    boolean         inserted;

    inserted = false;
    for (int index = 0; index < rates.size()  &&  !inserted; index++) {
      currentTuplet = rates.get(index);
      currentDate = currentTuplet.getDate();
      if (date.isBefore(currentDate)) {
        // insert before this tuplet.
        rates.add(index, newOpeningRate);
        inserted = true;
      } else if (date.equals(currentDate)) {
        // rate for this date already exists, so don't insert.
        return;
      }
    }

    // not inserted yet, so append at the end.
    if (!inserted) {
      rates.add(newOpeningRate);
    }
  }
  
  private DateRateTuplet getBestRateFromList(LocalDate date, List<DateRateTuplet> dateRateTuples) {
    if (date == null) {
      throw new RuntimeException("date is null!!");
    }
    DateRateTuplet  lastTuple = null;

    // Find the last tuple that is not after the specified date.
    for (DateRateTuplet currentTuple: dateRateTuples) {
      if (currentTuple.getDate() == null) {
        throw new RuntimeException("date of DateRateTuplet is null!!");
      }
      if (currentTuple.getDate().isAfter(date)) {
        return lastTuple;
      } else {
        lastTuple = currentTuple;
      }
    }
    
    return lastTuple;
  }
}
