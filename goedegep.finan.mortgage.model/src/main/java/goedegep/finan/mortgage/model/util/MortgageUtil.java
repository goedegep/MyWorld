package goedegep.finan.mortgage.model.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.NewInterestRate;
import goedegep.finan.mortgage.model.PeriodicPayment;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.text.TextWriter;

public class MortgageUtil {
  protected static final SimpleDateFormat  DF = new SimpleDateFormat("dd-MM-yyyy");
  protected static final PgCurrencyFormat CF = new PgCurrencyFormat();
  protected static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  public static void dumpData(Mortgage mortgage, TextWriter textWriter) throws IOException {
    textWriter.writeString(0, "MORTGAGE", 1);
    dumpClosingData(mortgage, textWriter);
    dumpEvents(mortgage, textWriter);
  }

  public static void dumpClosingData(Mortgage mortgage, TextWriter textWriter) throws IOException {
    final int labelColumn = 2;
    final int valueColumn = 20;
    textWriter.writeString(0, "CLOSINGDATA", 1);
    textWriter.writeString(labelColumn, "lender: ");
    textWriter.writeString(valueColumn, mortgage.getLender(), 1);
    textWriter.writeString(labelColumn, "mortgage name: ");
    textWriter.writeString(valueColumn, mortgage.getMortgageName(), 1);
    textWriter.writeString(labelColumn, "mortgage number: ");
    textWriter.writeString(valueColumn, mortgage.isSetMortgageNumber() ? mortgage.getMortgageNumber() : "----", 1);
    textWriter.writeString(labelColumn, "mortgage type: ");
    textWriter.writeString(valueColumn, mortgage.isSetMortgageType() ? mortgage.getMortgageType().getName() : "----", 1);
    textWriter.writeString(labelColumn, "starting date: ");
    textWriter.writeString(valueColumn, DF.format(mortgage.getStartingDate()), 1);
    textWriter.writeString(labelColumn, "duration: ");
    textWriter.writeString(valueColumn, String.valueOf(mortgage.getDuration()) + " months", 1);
    textWriter.writeString(labelColumn, "principal: ");
    textWriter.writeString(valueColumn, CF.format(mortgage.getPrincipal()), 1);
    textWriter.writeString(labelColumn, "interestRate: ");
    textWriter.writeString(valueColumn, FPVF.format(mortgage.getInterestRate()) + " %", 1);
    textWriter.writeString(labelColumn, "fixedInterestPeriod: ");
    textWriter.writeString(valueColumn, String.valueOf(mortgage.getFixedInterestPeriod()), 1);
    textWriter.writeString(labelColumn, "monthly payment: ");
    PgCurrency monthlyPayment = null;
    if (!mortgage.getMortgageEvents().isEmpty()) {
      MortgageEvent mortgageEvent = mortgage.getMortgageEvents().get(0);
      if (mortgageEvent instanceof PeriodicPayment) {
        PeriodicPayment periodicPayment = (PeriodicPayment) mortgageEvent;
        monthlyPayment = periodicPayment.getPayment();
      }
    }
    textWriter.writeString(valueColumn, CF.format(monthlyPayment), 1);
  }
  
  public static void dumpEvents(Mortgage mortgage, TextWriter textWriter) throws IOException {
    textWriter.writeString(0, "EVENTS", 1);
    for (MortgageEvent event: mortgage.getMortgageEvents()) {
      if (event instanceof PeriodicPayment) {
        dumpPeriodicPayment((PeriodicPayment) event, null, textWriter);
      } else if (event instanceof NewInterestRate) {
        dumpNewInterestRate((NewInterestRate) event, null, textWriter);
      } else if (event instanceof FinalPayment) {
        dumpFinalPayment((FinalPayment) event, textWriter);
      }
    }
  }
  
  public static void dumpEvent(MortgageEvent event, TextWriter textWriter) throws IOException {
    if (event instanceof PeriodicPayment) {
      dumpPeriodicPayment((PeriodicPayment) event, null, textWriter);
    } else if (event instanceof NewInterestRate) {
      dumpNewInterestRate((NewInterestRate) event, null, textWriter);
    } else if (event instanceof FinalPayment) {
      dumpFinalPayment((FinalPayment) event, textWriter);
    }
  }
  
  public static void dumpPeriodicPayment(PeriodicPayment periodicPayment, String extension, TextWriter textWriter) throws IOException {
    StringBuilder buf = new StringBuilder();
    buf.append("Periodic payment: " + CF.format(periodicPayment.getPayment()));
    buf.append(", interest: " + CF.format(periodicPayment.getInterest()));
    if (periodicPayment.isSetInterestRate()) {
      buf.append(", interest rate = " + FPVF.format(periodicPayment.getInterestRate()));
    }
    if (extension != null) {
      buf.append(extension);
    }
    textWriter.writeString(0, buf.toString(), 1);    
  }
  
  public static void dumpNewInterestRate(NewInterestRate newInterestRate, String extension, TextWriter textWriter) throws IOException {
    StringBuilder buf = new StringBuilder();
    buf.append("New interest rate: new interest rate ");
    buf.append(FPVF.format(newInterestRate.getNewInterestRate()));
    buf.append(" %");
    if (newInterestRate.isSetFixedInterestPeriod()) {
      buf.append(", for ");
      int fixedInterestPeriod = newInterestRate.getFixedInterestPeriod();
      if (fixedInterestPeriod % 12 == 0) {
        buf.append(Integer.toString(fixedInterestPeriod / 12));
        buf.append(" years ");
      } else {
        buf.append(Integer.toString(fixedInterestPeriod));      
        buf.append(" months ");
      }
    }
    buf.append(", starting from ");
    buf.append(DF.format(newInterestRate.getStartingDate()));
    if (extension != null) {
      buf.append(extension);
    }
    textWriter.writeString(0, buf.toString(), 1);    
  }
  
  public static void dumpFinalPayment(FinalPayment finalPayment, TextWriter textWriter) throws IOException {
    textWriter.writeString(2, "final payment: ");
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
