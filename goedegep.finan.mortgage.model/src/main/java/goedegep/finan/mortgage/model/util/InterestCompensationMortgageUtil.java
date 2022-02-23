package goedegep.finan.mortgage.model.util;

import java.io.IOException;

import goedegep.finan.mortgage.model.InterestCompensationMortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.NewInterestRateWithCompensation;
import goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation;
import goedegep.util.text.TextWriter;

public class InterestCompensationMortgageUtil extends MortgageUtil {

  public static void dumpData(InterestCompensationMortgage interestCompensationMortgage, TextWriter textWriter) throws IOException {
    textWriter.writeString(0, "MORTGAGE", 1);
    dumpClosingData(interestCompensationMortgage, textWriter);
    dumpEvents(interestCompensationMortgage, textWriter);
  }

  public static void dumpClosingData(InterestCompensationMortgage interestCompensationMortgage, TextWriter textWriter) throws IOException {
    MortgageUtil.dumpClosingData(interestCompensationMortgage, textWriter);
    final int labelColumn = 2;
    final int valueColumn = 20;
    textWriter.writeString(labelColumn, "compensation percentage borrower: ");
    textWriter.writeString(valueColumn, (interestCompensationMortgage.isSetCompensationPercentageBorrower() ? FPVF.format(interestCompensationMortgage.getCompensationPercentageBorrower()) : "-") + " %", 1);
    textWriter.writeString(labelColumn, "percentage december payment: ");
    textWriter.writeString(valueColumn, (interestCompensationMortgage.isSetPercentageDecemberPayment() ? FPVF.format(interestCompensationMortgage.getPercentageDecemberPayment()) : "-") + " %", 1);
  }
  
  private static void dumpEvents(InterestCompensationMortgage interestCompensationMortgage, TextWriter textWriter) throws IOException {
    textWriter.writeString(0, "EVENTS", 1);
    for (MortgageEvent event: interestCompensationMortgage.getMortgageEvents()) {
      if (event instanceof PeriodicPaymentWithCompensation) {
        dumpPeriodicPaymentWithCompensation((PeriodicPaymentWithCompensation) event, textWriter);
      } else if (event instanceof NewInterestRateWithCompensation) {
        dumpNewInterestRateWithCompensation((NewInterestRateWithCompensation) event, textWriter);
      } else {
        MortgageUtil.dumpEvent(event, textWriter);
      }
    }
  }
  
  private static void dumpPeriodicPaymentWithCompensation(PeriodicPaymentWithCompensation periodicPaymentWithCompensation, TextWriter textWriter) throws IOException {
    StringBuilder buf = new StringBuilder();
    
    buf.append(", Borrower compensation: ");
    buf.append((periodicPaymentWithCompensation.isSetBorrowerCompensation() ? CF.format(periodicPaymentWithCompensation.getBorrowerCompensation()) : "-"));
    buf.append(", December payment accumulation: ");
    buf.append((periodicPaymentWithCompensation.isSetDecemberPaymentAccumulation() ? CF.format(periodicPaymentWithCompensation.getDecemberPaymentAccumulation()) : "-"));
    
    MortgageUtil.dumpPeriodicPayment(periodicPaymentWithCompensation, buf.toString(), textWriter);
  }
  
  private static void dumpNewInterestRateWithCompensation(NewInterestRateWithCompensation newInterestRateWithCompensation, TextWriter textWriter) throws IOException {
    StringBuilder buf = new StringBuilder();
    
    newInterestRateWithCompensation.isSetCompensationPercentageBorrower();
    newInterestRateWithCompensation.isSetPercentageDecemberPayment();
    buf.append(", Compensation percentage borrower: ");
    buf.append((newInterestRateWithCompensation.isSetCompensationPercentageBorrower() ? FPVF.format(newInterestRateWithCompensation.getCompensationPercentageBorrower()) : "-"));
    buf.append(", Percentage december payment: ");
    buf.append((newInterestRateWithCompensation.isSetPercentageDecemberPayment() ? CF.format(newInterestRateWithCompensation.getPercentageDecemberPayment()) : "-"));
    
    MortgageUtil.dumpNewInterestRate(newInterestRateWithCompensation, buf.toString(), textWriter);
  }
  
//  public void dumpTermijnen(TextWriter textWriter, boolean printHeader) throws IOException {
//    if (printHeader) {
//      HypotheekTermijn.dumpTableHeader(textWriter);
//    }
//    for (HypotheekEvent event: events) {
//      if (event instanceof HypotheekTermijn) {
//        HypotheekTermijn hypotheekTermijn = (HypotheekTermijn) event;
//        hypotheekTermijn.dumpAsTableRow(textWriter);
//      }
//    }
//    
//  }


}
