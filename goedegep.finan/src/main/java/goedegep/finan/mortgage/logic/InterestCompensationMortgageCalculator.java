package goedegep.finan.mortgage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import goedegep.finan.mortgage.model.CompensationPayment;
import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.InterestCompensationMortgage;
import goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.finan.mortgage.model.NewInterestRateWithCompensation;
import goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

public class InterestCompensationMortgageCalculator extends MortgageCalculator {
  private final static Logger LOGGER = Logger.getLogger(InterestCompensationMortgageCalculator.class.getName());

  /*
   * Status informatie die gebruikt wordt tijdens het afhandelen van de events.
   */
  protected FixedPointValue huidigPercentageCompensatieNemer;          // percentage, in centi-procenten, voor de compensatie bij afhandeling van event.
  protected FixedPointValue huidigPercentageDecemberUitkering;         // percentage, in centi-procenten, voor de opbouw van de december uitkering bij afhandeling van event.
  protected PgCurrency compensatieNemer;
  protected PgCurrency opbouwDecemberUitkering;
  
  public InterestCompensationMortgageCalculator(InterestCompensationMortgage interestCompensationMortgage) {
    super(interestCompensationMortgage);
  }
  
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
    PeriodicPaymentWithCompensation periodicPayment = FACTORY.createPeriodicPaymentWithCompensation();
    fillPeriodicPaymentWithCompensation(periodicPayment, true);
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
      periodicPayment = FACTORY.createPeriodicPaymentWithCompensation();
      fillPeriodicPaymentWithCompensation(periodicPayment, false);
      
      incrementNextPaymentDate();

      calculatedMortgageEvents.add(periodicPayment);
      
      numberOfPeriodsRemaining--;
    }
    
    LOGGER.info("<=");
  }

  @Override
  protected void calculateFirstMonth() {
    super.calculateFirstMonth();
    
    huidigPercentageCompensatieNemer = ((InterestCompensationMortgage) mortgage).getCompensationPercentageBorrower();
    huidigPercentageDecemberUitkering = ((InterestCompensationMortgage) mortgage).getPercentageDecemberPayment();
    
    compensatieNemer = mortgage.getPrincipal().multiply(huidigPercentageCompensatieNemer.doubleValue() * firstMonthFraction / 1200d);
    opbouwDecemberUitkering = mortgage.getPrincipal().multiply(huidigPercentageDecemberUitkering.doubleValue() * firstMonthFraction / 1200d);
  }

  @Override
  protected void calculateMonth() {
    compensatieNemer = currentBalance.multiply(huidigPercentageCompensatieNemer.doubleValue() / 1200d);
    opbouwDecemberUitkering = currentBalance.multiply(huidigPercentageDecemberUitkering.doubleValue() / 1200d);
    
    super.calculateMonth();
  }
  
  protected void fillPeriodicPaymentWithCompensation(PeriodicPaymentWithCompensation periodicPayment, boolean isFirstPayment) {
    fillPeriodicPayment(periodicPayment, isFirstPayment);
    
    periodicPayment.setBorrowerCompensation(compensatieNemer);
    periodicPayment.setDecemberPaymentAccumulation(opbouwDecemberUitkering);
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
    if (mortgageEvent instanceof PeriodicPaymentWithCompensation) {
      handleEvent((PeriodicPaymentWithCompensation) mortgageEvent);
    } else if (mortgageEvent instanceof NewInterestRateWithCompensation) {
      handleEvent((NewInterestRateWithCompensation) mortgageEvent);
    } else {
      super.handleEvent(mortgageEvent);
    }
    
  }
  
  private void handleEvent(PeriodicPaymentWithCompensation periodicPaymentWithCompensation) {
    LOGGER.info("=> " + periodicPaymentWithCompensation);
    
    periodicPaymentWithCompensation.setNewInterestRate(currentInterestRate);
  }
  
  private void handleEvent(NewInterestRateWithCompensation newInterestRateWithCompensation) {
    LOGGER.info("=> " + newInterestRateWithCompensation);
    
    if (newInterestRateWithCompensation.isSetCompensationPercentageBorrower()) {
      huidigPercentageCompensatieNemer = newInterestRateWithCompensation.getCompensationPercentageBorrower();
    }
    
    if (newInterestRateWithCompensation.isSetPercentageDecemberPayment()) {
      huidigPercentageDecemberUitkering = newInterestRateWithCompensation.getPercentageDecemberPayment();
    }
    
    newInterestRateWithCompensation.setNewInterestRate(currentInterestRate);
  }

  public List<CompensationPayment> getCompensationPaymentsForYear(int jaar, boolean createIfNotExisting) {
    EList<CompensationPayment> compensatieBetalingenInJaar = ((InterestCompensationMortgage) mortgage).getCompensationPaymentsPerYear().get((Integer) jaar);

    
    if ((compensatieBetalingenInJaar == null) && createIfNotExisting) {
      compensatieBetalingenInJaar = new BasicEList<CompensationPayment>();
      ((InterestCompensationMortgage) mortgage).getCompensationPaymentsPerYear().put((Integer) jaar, compensatieBetalingenInJaar);
    }
    
    return compensatieBetalingenInJaar;
  }
    
  public InterestCompensationMortgageYearlyOverview getYearlyOverview(int year) {
    LOGGER.info("=>" + year);
    MortgageYearlyOverview mortgageYearlyOverview = super.getYearlyOverview(year);
    
    PgCurrency compensationBorrower = null;
    PgCurrency decemberPayment = null;
        
    EventIterationStatus eventIterationStatus = EventIterationStatus.BEFORE_YEAR;
    
    for (MortgageEvent mortgageEvent: calculatedMortgageEvents) {
      LOGGER.info("mortgageEvent: " + mortgageEvent.toString());
      Date eventDate = mortgageEvent.getDate();
      int eventYear = DateUtil.getDateYear(eventDate);
      if (eventYear == year) {
        eventIterationStatus = EventIterationStatus.IN_YEAR;
      } else if (eventYear > year) {
        break;
      }
      
      if (mortgageEvent instanceof PeriodicPaymentWithCompensation) {
        PeriodicPaymentWithCompensation periodicPaymentWithCompensation = (PeriodicPaymentWithCompensation) mortgageEvent;
        
        switch (eventIterationStatus) {
        case BEFORE_YEAR:
          // No action
          break;
          
        case IN_YEAR:
          if (periodicPaymentWithCompensation.isSetBorrowerCompensation()) {
            if (compensationBorrower == null) {
              compensationBorrower = periodicPaymentWithCompensation.getBorrowerCompensation();
            } else {
              compensationBorrower = compensationBorrower.add(periodicPaymentWithCompensation.getBorrowerCompensation());
            }
          } else {
            LOGGER.severe("BorrowerCompensation not set");
          }
          if (periodicPaymentWithCompensation.isSetDecemberPaymentAccumulation()) {
            if (decemberPayment == null) {
              decemberPayment = periodicPaymentWithCompensation.getDecemberPaymentAccumulation();
            } else {
              decemberPayment = decemberPayment.add(periodicPaymentWithCompensation.getDecemberPaymentAccumulation());
            }
          } else {
            LOGGER.severe("DecemberPaymentAccumulation not set");
          }
          break;
          
        case AFTER_YEAR:
          break;
        }
      }
    }
    
    PgCurrency compensationPaid = new PgCurrency(0);
    List<CompensationPayment> compensationPaymentsForYear = getCompensationPaymentsForYear(year, false);
    if (compensationPaymentsForYear != null) {
      for (CompensationPayment compensationPayment: compensationPaymentsForYear) {
        compensationPaid = compensationPaid.add(compensationPayment.getAmount());
      }
    }
    
    InterestCompensationMortgageYearlyOverview interestCompensationMortgageYearlyOverview = FACTORY.createInterestCompensationMortgageYearlyOverview();
    interestCompensationMortgageYearlyOverview.setYear(mortgageYearlyOverview.getYear());
    interestCompensationMortgageYearlyOverview.setDebtAtBeginningOfYear(mortgageYearlyOverview.getDebtAtBeginningOfYear());
    interestCompensationMortgageYearlyOverview.setDebtAtEndOfYear(mortgageYearlyOverview.getDebtAtEndOfYear());
    interestCompensationMortgageYearlyOverview.setInterestPaid(mortgageYearlyOverview.getInterestPaid());
    interestCompensationMortgageYearlyOverview.setRepayment(mortgageYearlyOverview.getRepayment());
    
    interestCompensationMortgageYearlyOverview.setCompensationBorrower(compensationBorrower);
    LOGGER.info("CompensationBorrower: " + (compensationBorrower != null ? compensationBorrower.toString() : "(null)"));
    interestCompensationMortgageYearlyOverview.setDecemberPayment(decemberPayment);
    LOGGER.info("CompensationPayment: " + (compensationPaid != null ? compensationPaid.toString() : "(null)"));
    interestCompensationMortgageYearlyOverview.setCompensationPayment(compensationPaid);
    
    LOGGER.info("<=");
    return interestCompensationMortgageYearlyOverview;
  }
}
