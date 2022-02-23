package goedegep.finan.direktbank.direktsprek;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.QuarterlyData;
import goedegep.finan.basic.RenteAanpassing;
import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.text.TextWriter;


public class DirektSpRek extends PgAccount {
  private static String     ACCOUNT_NAME = "Direktspaarrekening";
  
  private static final DateTimeFormatter  df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  cfLarge = new PgCurrencyFormat(9);
  
  private List<RenteAanpassing>           renteAanpassingen;
  private LinkedList<YearlyData>          yearlyData;           // overzichtsgegevens per jaar, zoals ontvangen dividend
  // TODO Yearly data verder implementeren.
  private List<QuarterlyData>             quarterlyDataList = new ArrayList<QuarterlyData>();
  private QuarterlyData                   quarterlyData = null;
  private boolean                         quarterlyDataFinished;
  
  public DirektSpRek() {
    super(true, PgCurrency.GUILDER, 0L, false);
    setName(ACCOUNT_NAME);
    clear();
  }
    
// Weet nog niet of er opnamekosten zijn, en of dat hier geimplementeerd wordt.
//  public PgCurrency getOpnameKosten(PbSpRekOverschrijving transaction) {
//    return policy.getOpnameKosten(transaction);
//  }
//  
//  public PgCurrency getVrijOpneembaarBedragVoor(PbSpRekTransaction transaction) {
//    return policy.getVrijOpneembaarBedragVoor(transaction);
//  }
  
  public void clear() {
    super.clear();
    renteAanpassingen = new ArrayList<RenteAanpassing>();
    yearlyData = new LinkedList<YearlyData>();
    // TODO clear quarterly data??
    quarterlyDataFinished = false;
  }
  
  /**
   * Verkrijg de naam van de rekening.
   *
   * @return De naam van de rekening.
   */
  public static String getAccountName() {
    return ACCOUNT_NAME;
  }
  
  /*
   * Munteenheid
   */

  public int getCurrency() {
    return getBalance().getCurrency();
  }

  public void handleFirst(DirektSpRekTransaction transaction) {
//    QuarterFormat qf = new QuarterFormat();
    
    if (quarterlyData == null) { // Only the case for the first transaction.
      return;
    }
    
    Quarter quarter = quarterlyData.getQuarter();
    Quarter transactionQuarter = Quarter.getQuarterForDate(transaction.getExecutionDate());
    
    // Als deze transactie in een nieuw kwartaal is, kan
    // de kwartaal data aan de lijst toegevoegd worden.
    if (transactionQuarter.after(quarter)) {
      fillQuarterlyDataProfit();
      quarterlyDataList.add(quarterlyData);
      
      // Er kunnen tussenliggende kwartalen zijn zonder transacties.
      // Voor deze kwartalen is de informatie hetzelfde.
      Quarter nextQuarter = quarter.getNextQuarter();
      
      while (transactionQuarter.after(nextQuarter)) {
        QuarterlyData nextQuarterData = new QuarterlyData(nextQuarter);
        nextQuarterData.setBalance(quarterlyData.getBalance());
        nextQuarterData.setNettoStorting(quarterlyData.getNettoStorting());
        nextQuarterData.setProfit(new PgCurrency(quarterlyData.getBalance().getCurrency(), 0L));
        nextQuarterData.setCumulativeProfit(quarterlyData.getCumulativeProfit());
        
//        System.out.println("fill in for no transactions: " + qf.format(nextQuarter) + ", " + df.format(transaction.getExecutionDate()));
        quarterlyDataList.add(nextQuarterData);
        nextQuarter = nextQuarter.getNextQuarter();
      }
      
      quarterlyData = null;
    }
  }
  
  public void handleLast(DirektSpRekTransaction transaction) {
//    QuarterFormat qf = new QuarterFormat();
    
    if (quarterlyData == null) {
      Quarter quarter = Quarter.getQuarterForDate(transaction.getExecutionDate());
      quarterlyData = new QuarterlyData(quarter);
//      System.out.println("Initial QD created for: " + qf.format(quarter));
    }
    
    quarterlyData.setBalance(getBalance());
    quarterlyData.setNettoStorting(getNettoStorting());
  }

  /*
   * Jaartotalen
   */

  public YearlyData addYearlyData(int year) {
    YearlyData  currentData = null;
    YearlyData  newData = new YearlyData(year);

    boolean inserted = false;
    boolean existing = false;
    for (int index = 0; index < yearlyData.size() && !inserted  && !existing; index++) {
      currentData = (YearlyData) yearlyData.get(index);
      if (currentData.year == year) {
        existing = true;
      } else if (currentData.year > year) {
        yearlyData.add(index, newData);
        inserted = true;
        }
    }

    if (!inserted  && !existing) {
      yearlyData.addLast(newData);
    }

    return newData;
  }
  
  public void addRenteAanpassing(RenteAanpassing renteAanpassing) {
    renteAanpassingen.add(renteAanpassing);
  }

  public String toString(PgTransaction transaction) {
    return "Not implemented";
  }

  public String toXmlString(PgTransaction transaction, String nameSpace) {
    return "Not implemented";
  }

  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("DIREKTSPAARREKENING - " + getName());
    textWriter.newLine();
    dumpTransactions(textWriter);
  }
  
  @Override
  public List<QuarterlyData> getQuarterlyData() {
    if (!quarterlyDataFinished) {
      if (quarterlyData != null) {
        fillQuarterlyDataProfit();
        quarterlyDataList.add(quarterlyData);
        quarterlyDataFinished = true;
      }
    }
    
    return quarterlyDataList;
  }

  private void dumpTransactions(BufferedWriter out) throws IOException {
    out.write("DIREKTSPAARREKENING TRANSACTIONS - " + getName());
    out.newLine();
    DirektSpRekTransaction transaction;
    LocalDate date;

    for (int index = 1; index <= numberOfTransactions(); index++) {
      transaction = (DirektSpRekTransaction) getTransaction(index);

      if (!transaction.isHandled()) {
        break;
      }

      date = transaction.getBookingDate();
      if (date != null) {
        out.write(df.format(date));
      }
      out.write('\t');

      out.write(transaction.getDescription());
      out.write('\t');

      date = transaction.getExecutionDate();
      if (date != null) {
        out.write(df.format(date));
      }
      out.write('\t');

      PgCurrency nieuwSaldo = transaction.getNieuwTegoed();
      if (nieuwSaldo != null) {
        out.write(cfLarge.format(nieuwSaldo));
      }
      out.write('\t');

      String comment = transaction.getComment();
      if (comment != null) {
        out.write(comment);
      }
      out.newLine();
    }
  }
  
  private void fillQuarterlyDataProfit() {
    if ((quarterlyData.getBalance() != null)  &&  (quarterlyData.getNettoStorting() != null)) {
      quarterlyData.setCumulativeProfit(quarterlyData.getBalance().subtract(quarterlyData.getNettoStorting()));
      if (quarterlyDataList.size() > 0) {
        QuarterlyData previousQuarterData = quarterlyDataList.get(quarterlyDataList.size() - 1);
        if (previousQuarterData.getCumulativeProfit() != null) {
          quarterlyData.setProfit(quarterlyData.getCumulativeProfit().subtract(previousQuarterData.getCumulativeProfit()));
        } else {
          quarterlyData.setProfit(quarterlyData.getCumulativeProfit());
        }
      } else {
        quarterlyData.setProfit(quarterlyData.getCumulativeProfit());
      }
    }
  }

  /*
   * Private Classes
   */

  public class YearlyData {
    int         year;
    PgCurrency  rente;

    YearlyData(int year) {
      this.year = year;

      if (year >= 2002) {
        rente = new PgCurrency(PgCurrency.EURO, 0L);
      } else {
        rente = new PgCurrency(PgCurrency.GUILDER, 0L);
      }
    }
  }
}