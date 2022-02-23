package goedegep.finan.mortgage.app.guifx;

import java.text.SimpleDateFormat;

import goedegep.finan.mortgage.MortgageCalculator;
import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.NewInterestRate;
import goedegep.finan.mortgage.model.NewInterestRateWithCompensation;
import goedegep.finan.mortgage.model.PeriodicPayment;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

public class MortgageEventDescriptions {
  private static final SimpleDateFormat  DF = new SimpleDateFormat("dd-MM-yyyy");
  protected static final PgCurrencyFormat  CF = new PgCurrencyFormat(8);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  public static String getMortgageEventDescription(MortgageEvent mortgageEvent) {
    if (mortgageEvent instanceof PeriodicPayment) {
      return getMortgageEventDescription((PeriodicPayment) mortgageEvent);
    } else if (mortgageEvent instanceof FinalPayment) {
      return getMortgageEventDescription((FinalPayment) mortgageEvent);
    } else if (mortgageEvent instanceof NewInterestRateWithCompensation) {
      return getMortgageEventDescription((NewInterestRateWithCompensation) mortgageEvent);
    } else if (mortgageEvent instanceof NewInterestRate) {
      return getMortgageEventDescription((NewInterestRate) mortgageEvent);
    } else {
      return null;
    }
  }
  
  public static String getMortgageEventDescription(PeriodicPayment periodicPayment) {
    StringBuilder buf = new StringBuilder();
    buf.append("Periodic payment: payment: " + CF.format(periodicPayment.getPayment()));
    buf.append(", interest: " + CF.format(periodicPayment.getInterest()));
    buf.append(", repayment: " + CF.format(MortgageCalculator.getRepayment(periodicPayment)));
    if (periodicPayment.isSetNewInterestRate()) {
      buf.append(", interest percentage = " + FPVF.format(periodicPayment.getNewInterestRate()));
    }
    
    return buf.toString();
  }
  
  public static String getMortgageEventDescription(FinalPayment finalPayment) {
    return "Final payment";
  }
  
  public static String getMortgageEventDescription(NewInterestRate newInterestRate) {
    StringBuilder buf = new StringBuilder();
    buf.append("New interest rate: new interest rate ");
    buf.append(FPVF.format(newInterestRate.getNewInterestRate()));
    buf.append(" %");
    if (newInterestRate.isSetFixedInterestPeriod()) {
      buf.append(", for ");
      int fixedInterestPeriod = newInterestRate.getFixedInterestPeriod();
      if (fixedInterestPeriod % 12 == 0) {
        buf.append(Integer.toString(fixedInterestPeriod / 12));
        buf.append(" year ");
      } else {
        buf.append(Integer.toString(fixedInterestPeriod));      
        buf.append(" months ");
      }
    }
    buf.append(", starting on ");
    if (newInterestRate.isSetStartingDate()) {
      buf.append(DF.format(newInterestRate.getStartingDate()));
    } else {
      buf.append("--");
    }
    
    return buf.toString();
  }
  
  public static String getMortgageEventDescription(NewInterestRateWithCompensation newInterestRateWithCompensation) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(getMortgageEventDescription((NewInterestRate) newInterestRateWithCompensation));
    if (newInterestRateWithCompensation.isSetCompensationPercentageBorrower()) {
      buf.append(", new compensationpercentage ");
      buf.append(FPVF.format(newInterestRateWithCompensation.getCompensationPercentageBorrower()));
      buf.append(" %");
    }
    if (newInterestRateWithCompensation.isSetPercentageDecemberPayment()) {
      buf.append(", new percentage decemberpayment ");
      buf.append(FPVF.format(newInterestRateWithCompensation.getPercentageDecemberPayment()));
      buf.append(" %");
    }
    
    return buf.toString();
  }
}
