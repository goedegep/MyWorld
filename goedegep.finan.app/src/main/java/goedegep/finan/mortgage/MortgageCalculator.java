package goedegep.finan.mortgage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgageFactory;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.finan.mortgage.model.NewInterestRate;
import goedegep.finan.mortgage.model.PeriodicPayment;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class MortgageCalculator {
  private final static Logger LOGGER = Logger.getLogger(MortgageCalculator.class.getName());
  protected static final PgCurrencyFormat CF = new PgCurrencyFormat();
  protected final static MortgageFactory FACTORY = MortgageFactory.eINSTANCE;

  /**
   * The mortgage to which the calculations applies.
   */
  protected Mortgage mortgage;
  
  /**
   * The interest rate which applies at a specific moment in the calculation.
   */
  protected FixedPointValue currentInterestRate;
  
  /**
   * A calendar used to calculate the next payment date.
   */
  protected GregorianCalendar nextPaymentDateCalendar = new GregorianCalendar();
  
  /**
   * The amount of money to be payed at next periodic payment.
   */
  protected PgCurrency periodicPaymentAmount;
  
  /**
   * The part of the first month which is applicable for the first payment.
   */
  protected double firstMonthFraction;
  
  /**
   * The amount of money to be payed at the first periodic payment, which is normally about a part of a month.
   */
  protected PgCurrency firstPaymentAmount;
  
  /**
   * The amount of interest payed over the past period.
   */
  protected PgCurrency interestPastPeriod;
  
  /**
   * The repayment in the past period.
   */
  protected PgCurrency repaymentPastPeriod;
  
  /**
   * The current balance, the part of the principal still to be paid.
   */
  protected PgCurrency currentBalance;
  
  /**
   * The number of PeriodicPayments still to be done.
   */
  protected int numberOfPeriodsRemaining;
  
  /**
   * In the mortgage only events are stored which cannot be generated (all events except the periodic payments).
   * The other events are generated during the calculation (see {@link handleAndGenerateEvents}).
   * <p>
   * This is the complete list of events, with all information filled in.
   */
  protected List<MortgageEvent> calculatedMortgageEvents = null;
  
  
  /**
   * Constructor
   * 
   * @param mortgage The <code>Mortgage</code> to which the calculations applies.
   */
  public MortgageCalculator(Mortgage mortgage) {
    this.mortgage = mortgage;
  }

  /**
   * Get the <code>Mortgage</code> to which the calculations applies.
   * 
   * @return The <code>Mortgage</code> to which the calculations applies.
   */
  public Mortgage getMortgage() {
    return mortgage;
  }
  
  /**
   * Get the complete and calculated list of events.
   * <p>
   * This list is only available after a call to {@link handleAndGenerateEvents}.
   * @return
   */
  public List<MortgageEvent> getCalculatedMortgageEvents() {
    return calculatedMortgageEvents;
  }

  /**
   * Get the year in which the mortgage starts, which is the year of the start date.
   * 
   * @return the year in which the mortgage starts.
   */
  public int getStartYear() {
    if (mortgage.isSetStartingDate()) {
      return DateUtil.getDateYear(mortgage.getStartingDate());
    } else {
      return -1;
    }
  }
  
  /**
   * Get the end date of the mortgage, which is the start date plus the number of months of the duration.
   * 
   * @return the date at which the mortgage ends.
   */
  public Date getEndDate() {
    Date endDate = null;

    if (mortgage.isSetStartingDate()  &&  mortgage.isSetDuration()) {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(mortgage.getStartingDate());
      calendar.add(Calendar.MONTH, mortgage.getDuration());
      endDate = calendar.getTime();
    }

    return endDate;
  }
  
  /**
   * Get the actual end date of the mortgage, which is either:
   * <ul>
   * <li>
   * The end date (see {@link getEndDate}
   * </li>
   * <li>
   * Or the date of the <code>FinalPayment</code> event, if it exists.
   * </li>
   * </ul>
   * @return the actual end date of the mortgage.
   */
  public Date getActualEndDate() {
    Date actualEndDate = getEndDate();
    
    List<MortgageEvent> mortgageEvents = mortgage.getMortgageEvents();
    if (!mortgageEvents.isEmpty()) {
      MortgageEvent mortgageEvent = mortgageEvents.get(mortgageEvents.size() - 1);
      if (mortgageEvent instanceof FinalPayment) {
        if (mortgageEvent.isSetDate()) {
          actualEndDate = mortgageEvent.getDate();
        }
      }
    }
    
    return actualEndDate;
  }
  
  /**
   * Get the year in which the mortgage ends.
   * <p>
   * This is the year of the end date, which is the start date plus the number of months of the duration.
   * 
   * @return the year in which the mortgage ends.
   */
  public int getEndYear() {
    Date endDate = getEndDate();

    if (endDate != null) {
      return DateUtil.getDateYear(endDate);
    } else {
      return -1;
    }
  }
  
  /**
   * Get the actual end year of the mortgage, which is the year of the actual end date (see {@link getActualEndDate}.
   * 
   * @return the actual end year of the mortgage.
   * 
   */
  public int getActualEndYear() {
    Date actualEndDate = getActualEndDate();
    
    return DateUtil.getDateYear(actualEndDate);
  }
  
  /**
   * Get an Integer list, where the first element contains the starting year of the mortgage, each next element contains the next year, while the last element contains the actual end year of the mortgage.
   * 
   * @return the duration in years.
   */
  public List<Integer> getActualDurationYearsList() {
    List<Integer> durationYears = new ArrayList<>();
    int startYear = getStartYear();
    int endYear = getActualEndYear();
    
    for (int year = startYear; year <= endYear; year++) {
      durationYears.add(year);
    }
    
    return durationYears;
  }

  /**
   * Get the current debt.
   * <p>
   * This value is only available after a call to {@link handleAndGenerateEvents}.
   * 
   * @return the current debt, or null if this value cannot be calculated.
   */
  public PgCurrency getCurrentDebt() {
    return getDebtAtDate(new Date(), false);
  }

  /**
   * Get the debt at a specific date.
   * <p>
   * This value is only available after a call to {@link handleAndGenerateEvents}.
   * 
   * @param date the Date for which the debt is requested.
   * @return the debt at the specified date, or null if this value cannot be calculated.
   */
  public PgCurrency getDebtAtDate(Date date, boolean justBefore) {
    PgCurrency debtAtDate = mortgage.getPrincipal();
    
    if (calculatedMortgageEvents != null) {
      for (MortgageEvent event: calculatedMortgageEvents) {
        if (event.isSetDate()  &&  event.getDate().after(date)) {
          LOGGER.severe("break; not justBefore");
          break;
        }
        if (justBefore  &&  event.isSetDate()  &&  !date.after(event.getDate())) {
          LOGGER.severe("break; justBefore");
          break;
        }
        if (event instanceof PeriodicPayment) {
          PeriodicPayment periodicPayment = (PeriodicPayment) event;
          if (periodicPayment.isSetBalanceAfterPayment()) {
            debtAtDate = periodicPayment.getBalanceAfterPayment();
          }
        } else if (event instanceof FinalPayment) {
          debtAtDate = new PgCurrency(0l);
        }
      }
    }
    
    return debtAtDate;
  }

  /**
   * Handle and Generate mortgage events.
   * <p>
   * The monthly payments are being generated and mixed with the 'fixed' events like interest rate changes.
   */
  public void handleAndGenerateEvents() {
    LOGGER.info("=>");
    
    if (!mortgageIsValid()) {
      LOGGER.severe("Invalid mortgage");
      return;
    }
    
    List<MortgageEvent> mortgageEvents = mortgage.getMortgageEvents();
    calculatedMortgageEvents = new ArrayList<>();
    
    periodicPaymentAmount = MortgageCalculator.calculateAnnuity(mortgage.getPrincipal(), mortgage.getInterestRate().doubleValue() / 12, mortgage.getDuration());
    currentInterestRate = mortgage.getInterestRate();
    nextPaymentDateCalendar.setTime(mortgage.getFirstPaymentDate());

    /*
     * Handle the first, partial, month.
     * The periodic payments are for full months. However the first month is an exception, this goes from the starting date till the
     * end of the month.
     */
    calculateFirstMonth();
    PeriodicPayment periodicPayment = FACTORY.createPeriodicPayment();
    fillPeriodicPayment(periodicPayment, true);    
    calculatedMortgageEvents.add(periodicPayment);
    
    incrementNextPaymentDate();
    
    numberOfPeriodsRemaining = mortgage.getDuration();
    periodicPaymentAmount = MortgageCalculator.calculateAnnuity(currentBalance, currentInterestRate.doubleValue() / 12, numberOfPeriodsRemaining);
    
    ListIterator<MortgageEvent> currentEventsIterator = mortgageEvents.listIterator();
    MortgageEvent nextEvent = null;
    if (currentEventsIterator.hasNext()) {
      nextEvent = currentEventsIterator.next();
    }

    while (currentBalance.getAmount() >= 0) {
      if ((nextEvent != null)  && getNextPaymentDate().after(nextEvent.getDate())) {
        LOGGER.info("Handling next event: " + nextEvent.toString());
        
        handleEvent(nextEvent);
        
        calculatedMortgageEvents.add(nextEvent);
        
        if (nextEvent instanceof FinalPayment) {
          break;
        }
        
        if (currentEventsIterator.hasNext()) {
          nextEvent = currentEventsIterator.next();
        } else {
          nextEvent = null;
        }
      }

      calculateMonth();
      periodicPayment = FACTORY.createPeriodicPayment();
      fillPeriodicPayment(periodicPayment, false);
      
      incrementNextPaymentDate();

      calculatedMortgageEvents.add(periodicPayment);
      
      numberOfPeriodsRemaining--;
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Check whether a mortgage is valid, so it can be calculated.
   * @return
   */
  public boolean mortgageIsValid() {
    if (!mortgage.isSetFirstPaymentDate()) {
      LOGGER.severe("FirstPaymentDate not set");
      return false;
    }
    
    return true;
  }
  
  /**
   * Handle a mortgage event.
   * <p>
   * The handling is delegated to one of the methods to handle the specific events.
   * 
   * @param mortgageEvent the <code>MortgageEvent</code> to be handled.
   */
  protected void handleEvent(MortgageEvent mortgageEvent) {
    LOGGER.info("=> " + mortgageEvent);
    if (mortgageEvent instanceof PeriodicPayment) {
      handleEvent((PeriodicPayment) mortgageEvent);
    } else if (mortgageEvent instanceof FinalPayment) {
      handleEvent((FinalPayment) mortgageEvent);
    } else if (mortgageEvent instanceof NewInterestRate) {
      handleEvent((NewInterestRate) mortgageEvent);
    }
  }
  
  /**
   * Handle a <code>PeriodicPayment</code> mortgage event.
   * 
   * @param periodicPayment the <code>PeriodicPayment</code> to be handled.
   */
  private void handleEvent(PeriodicPayment periodicPayment) {
    LOGGER.info("=> " + periodicPayment);
    
    periodicPayment.setBalanceAfterPayment(currentBalance);
    periodicPayment.setNewInterestRate(currentInterestRate);
  }
  
  private void handleEvent(FinalPayment finalPayment) {
    LOGGER.info("=> " + finalPayment);
    currentBalance = new PgCurrency(0);
  }
  
  private void handleEvent(NewInterestRate newInterestRate) {
    LOGGER.info("=> " + newInterestRate);

    currentInterestRate = newInterestRate.getNewInterestRate();
    periodicPaymentAmount = calculateAnnuity(currentBalance, currentInterestRate.doubleValue() / 12, numberOfPeriodsRemaining);

    newInterestRate.setNewInterestRate(currentInterestRate);
  }

  /**
   * Get the next payment date.
   * 
   * @return The next payment date.
   */
  protected Date getNextPaymentDate() {
    return nextPaymentDateCalendar.getTime();
  }

  /**
   * Increment the next payment date, i.e. add one month to the next payment date.
   */
  public void incrementNextPaymentDate() {
    nextPaymentDateCalendar.add(Calendar.MONTH, 1);
  }
  
  /**
   * Fill the information of a <code>PeriodicPayment</code>.
   * 
   * @param periodicPayment the <code>PeriodicPayment</code> to be filled.
   * @param isFirstPayment if true, this is the first (partial month) payment.
   */
  protected void fillPeriodicPayment(PeriodicPayment periodicPayment, boolean isFirstPayment) {
    periodicPayment.setDate(getNextPaymentDate());
    if (isFirstPayment) {
      periodicPayment.setPayment(firstPaymentAmount);
    } else {
      periodicPayment.setPayment(periodicPaymentAmount);
    }
    periodicPayment.setInterest(interestPastPeriod);
    periodicPayment.setBalanceAfterPayment(currentBalance);
  }

  /**
   * Perform the calculations for the first (partial) month.
   * <p>
   * The first month is normally a part of a month; from the closing date till the end of the month.
   * For the first month, this method calculates:<br/>
   * firstPaymentAmount<br/>
   * interestPastPeriod<br/>
   * repaymentPastPeriod<br/>
   * currentBalance
   */
  protected void calculateFirstMonth() {
    firstMonthFraction = DateUtil.getRemainingMonthFraction(mortgage.getStartingDate());
    firstPaymentAmount = periodicPaymentAmount.multiply(firstMonthFraction);
    interestPastPeriod = mortgage.getPrincipal().multiply(mortgage.getInterestRate().doubleValue() * firstMonthFraction / 1200d);
    repaymentPastPeriod = firstPaymentAmount.subtract(interestPastPeriod);
    currentBalance = mortgage.getPrincipal().subtract(repaymentPastPeriod);
  }

  /**
   * Perform the calculations for a (normal) full month.
   * <p>
   * This method calculates:<br/>
   * interestPastPeriod<br/>
   * repaymentPastPeriod<br/>
   * currentBalance
   */
  protected void calculateMonth() {
    interestPastPeriod = currentBalance.multiply(currentInterestRate).divide(1200);
    repaymentPastPeriod = periodicPaymentAmount.subtract(interestPastPeriod);
    currentBalance = currentBalance.subtract(repaymentPastPeriod);
  }

  /**
   * Calculate an annuity.
   * 
   * @param principal The amount of the loan.
   * @param interestPercentageForAPeriod The interest rate (in percent) for one period. If the period is a month, this is the yearly interest divided by 12.
   * @param numberOfPeriods The number of payment periods.
   * @return The annuity amount for the given parameters.
   */
  public static PgCurrency calculateAnnuity(PgCurrency principal, double interestPercentageForAPeriod, int numberOfPeriods) {
    LOGGER.fine("=> principal=" + CF.format(principal) + ", interestRateForAPeriod=" + interestPercentageForAPeriod + ", numberOfPeriods=" + numberOfPeriods);
    double interestRateForAPeriod = interestPercentageForAPeriod / 100;
    LOGGER.fine("interestRateForAPeriod=" + interestRateForAPeriod);
    double onePlusinterestRateForAPeriod = 1 + interestRateForAPeriod;
    LOGGER.fine("onePlusinterestRateForAPeriod=" + onePlusinterestRateForAPeriod);
    double power = Math.pow(onePlusinterestRateForAPeriod, (double) -numberOfPeriods);
    LOGGER.fine("power=" + power);
    double powerMinusOne = 1 - power;
    LOGGER.fine("powerMinusOne=" + powerMinusOne);
    PgCurrency annuityAmount = principal.multiply(interestRateForAPeriod).divideWithRoundUp(powerMinusOne);
    
    LOGGER.fine("<= " + CF.format(annuityAmount));

    return annuityAmount;
  }
  
  /**
   * Get the repayment for a periodic payment.
   * <p>
   * This is the payment minus the interest to be paid.
   * 
   * @param periodicPayment The <code>PeriodecPayment</code> for which the repayment is to be calculated.
   * @return The repayment for the <code>periodicPayment</code>.
   */
  public static PgCurrency getRepayment(PeriodicPayment periodicPayment) {
    if (periodicPayment.isSetPayment()  &&  periodicPayment.isSetInterest()) {
      return periodicPayment.getPayment().subtract(periodicPayment.getInterest());
    } else {
      return null;
    }
  }
  
  /**
   * Create the mortgage yearly information for a specific year. This is calculated as follows:
 * <ul>
 * <li>
 * debt at the beginning of the year<br/>
 * This is the <code>balanceAfterPayment</code> of the last <code>PeriodicPayment</code> before the specified year.
 * </li>
 * <li>
 * debt at the end of the year<br/>
 * This is the <code>balanceAfterPayment</code> of the last <code>PeriodicPayment</code> of the specified year,
 * or, if there aren't any payments in this year, the debt at the beginning of the year.
 * </li>
 * <li>
 * interest paid during the year<br/>
 * The sum of all interest payments of all <code>PeriodicPayment</code>s of the year.
 * </li>
 * <li>
 * total repayment in the year<br/>
 * The sum of all repayments of all <code>PeriodicPayment</code>s of the year.
 * </li>
 * </ul>
   * 
   * @param year the year for which the information is requested.
   * @return the yearly overview for the specified <code>year</year>.
   */
  public MortgageYearlyOverview getYearlyOverview(int year) {
    PgCurrency debtAtTheBeginningOfTheYear = null;  // Is the debt at the end of the previous year.
    PgCurrency debtAtTheEndOfTheYear = null;
    PgCurrency paidInterest = null;
    PgCurrency repayment = null;
    
    if (calculatedMortgageEvents == null) {
      return null;
    }
    
    if (year > getStartYear()) {
      debtAtTheBeginningOfTheYear = mortgage.getPrincipal();
    }
    
    if (year >= getStartYear()) {
      debtAtTheEndOfTheYear = mortgage.getPrincipal();
    }
    
    EventIterationStatus eventIterationStatus = EventIterationStatus.BEFORE_YEAR;
    
    for (MortgageEvent mortgageEvent: calculatedMortgageEvents) {
      Date eventDate = mortgageEvent.getDate();
      int eventYear = DateUtil.getDateYear(eventDate);
      if (eventYear == year) {
        eventIterationStatus = EventIterationStatus.IN_YEAR;
      } else if (eventYear > year) {
        break;
      }
      
      if (mortgageEvent instanceof PeriodicPayment) {
        PeriodicPayment periodicPayment = (PeriodicPayment) mortgageEvent;
        
        switch (eventIterationStatus) {
        case BEFORE_YEAR:
          debtAtTheBeginningOfTheYear = periodicPayment.getBalanceAfterPayment();
          debtAtTheEndOfTheYear = periodicPayment.getBalanceAfterPayment();  // for the case that there are no payments in the 'year'.
          break;
          
        case IN_YEAR:
          debtAtTheEndOfTheYear = periodicPayment.getBalanceAfterPayment();
          if (periodicPayment.isSetInterest()) {
            if (paidInterest == null) {
              paidInterest = periodicPayment.getInterest();
            } else {
              paidInterest = paidInterest.add(periodicPayment.getInterest());
            }
          }
          PgCurrency repaymentThisPeriod = getRepayment(periodicPayment);
          if (repaymentThisPeriod != null) {
            if (repayment == null) {
              repayment = repaymentThisPeriod;
            } else {
              repayment = repayment.add(repaymentThisPeriod);
            }
          }
          break;
          
        case AFTER_YEAR:
          break;
        }
      } else if (mortgageEvent instanceof FinalPayment) {
        switch (eventIterationStatus) {
        case BEFORE_YEAR:
        case IN_YEAR:
          debtAtTheEndOfTheYear = new PgCurrency(0l);
          break;
          
        case AFTER_YEAR:
          break;
        }        
      }
    }
    
    MortgageYearlyOverview yearlyOverview = FACTORY.createMortgageYearlyOverview();
    yearlyOverview.setYear(year);
    yearlyOverview.setDebtAtBeginningOfYear(debtAtTheBeginningOfTheYear);
    yearlyOverview.setDebtAtEndOfYear(debtAtTheEndOfTheYear);
    yearlyOverview.setInterestPaid(paidInterest);
    yearlyOverview.setRepayment(repayment);
    
    return yearlyOverview;
  }
  
  /**
   * States for iterating over the mortgage events.
   */
  protected enum EventIterationStatus {
    /**
     * State for events that are from before the applicable year.
     */
    BEFORE_YEAR,
    
    /**
     * State for events that are in the applicable year.
     */
    IN_YEAR,
    
    /**
     * State for events that are from after the applicable year.
     */
    AFTER_YEAR
  }


}
