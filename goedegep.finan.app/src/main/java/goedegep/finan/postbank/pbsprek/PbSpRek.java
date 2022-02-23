// Postbank spaarrekening

package goedegep.finan.postbank.pbsprek;

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


public class PbSpRek extends PgAccount {
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF_LARGE = new PgCurrencyFormat(9);
  
  private PbSpRekId                       rekID;
  private PbSpRekPolicy                   policy;
  private boolean                         hasOpnameKosten;
  private boolean                         hasVrijOpneembaarBedrag;
  private List<RenteAanpassing>           renteAanpassingen;
  private LinkedList<YearlyData>          yearlyData;           // overzichtsgegevens per jaar, zoals ontvangen dividend
  // TODO Yearly data verder implementeren.
  private ArrayList<QuarterlyData>        quarterlyDataList = new ArrayList<QuarterlyData>();
  private QuarterlyData                   quarterlyData = null;
  private boolean                         quarterlyDataFinished;
  
  public PbSpRek(PbSpRekId rekID,
      boolean hasOpnameKosten,
      boolean hasVrijOpneembaarBedrag,
      PbSpRekPolicy policy) {
    super(true, PgCurrency.GUILDER, 0L, false);
    this.rekID = rekID;
    this.policy = policy;
    this.hasOpnameKosten = hasOpnameKosten;
    this.hasVrijOpneembaarBedrag = hasVrijOpneembaarBedrag;
    setName(rekID.getName());
    clear();
  }
  
  public PbSpRekId getRekID() {
    return rekID;
  }
  
  public PbSpRekPolicy getPolicy() {
    return policy;
  }

  public boolean hasOpnameKosten() {
    return hasOpnameKosten;
  }
  
  public boolean hasVrijOpneembaarBedrag() {
    return hasVrijOpneembaarBedrag;
  }
  
  public PgCurrency getOpnameKosten(PbSpRekOverschrijving transaction) {
    return policy.getOpnameKosten(transaction);
  }
  
  public PgCurrency getVrijOpneembaarBedragVoor(PbSpRekTransaction transaction) {
    return policy.getVrijOpneembaarBedragVoor(transaction);
  }
  
  public void clear() {
    super.clear();
    renteAanpassingen = new ArrayList<RenteAanpassing>();
    yearlyData = new LinkedList<YearlyData>();
    quarterlyDataFinished = false;
  }

  /*
   * Munteenheid
   */

  public int getCurrency() {
    return getBalance().getCurrency();
  }

  public void handleFirst(PbSpRekTransaction transaction) {
//    QuarterFormat qf = new QuarterFormat();
    if (quarterlyData == null) { // Only the case for the first transaction.
      return;
    }
    
    Quarter quarter = quarterlyData.getQuarter();
    Quarter transactionQuarter = Quarter.getQuarterForDate(transaction.getExecutionDate());
    
    // Als deze transactie in een nieuw kwartaal is, kan
    // de kwartaal data aan de lijst toegevoegd worden.
    if (transactionQuarter.after(quarter)) {
//      if (getName().equals("Sterrekening")) {
//        System.out.println("Transaction is in new quarter: " + qf.format(quarterlyData.getQuarter()) + ", " + df.format(transaction.getExecutionDate()));
//        if (quarterlyData.getBalance() == null) {
//          System.out.println("Saldo is NULL");
//        }
//      }
//      if (getName().equals("Renterekening")  &&  quarterlyData.getBalance() == null) {
//        System.out.println("balance is null for date: " + df.format(transaction.getExecutionDate()));
//      }
//      if (getName().equals("Renterekening")  &&  quarterlyData.getNettoStorting() == null) {
//        System.out.println("netto storting is null for date: " + df.format(transaction.getExecutionDate()));
//      }
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
        
//        if (getName().equals("Renterekening")) {
//          System.out.println("fill in for no transactions: " + qf.format(nextQuarter) + ", " + df.format(transaction.getExecutionDate()));
//        }
        quarterlyDataList.add(nextQuarterData);
        nextQuarter = nextQuarter.getNextQuarter();
      }
      
      quarterlyData = null;
    }
  }
  
  public void handleLast(PbSpRekTransaction transaction) {
//    QuarterFormat qf = new QuarterFormat();
    
    if (quarterlyData == null) {
      Quarter quarter = Quarter.getQuarterForDate(transaction.getExecutionDate());
      quarterlyData = new QuarterlyData(quarter);
//      if (getName().equals("Sterrekening")) {
//        System.out.println("Creating new quarterlyData: " + qf.format(quarter) + ", " + df.format(transaction.getExecutionDate()));
//      }
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
    textWriter.write("POSTBANK SPAARREKENING - " + getName());
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

  private void dumpTransactions(TextWriter textWriter) throws IOException {
    textWriter.write("POSTBANK SPAARREKENING TRANSACTIONS - " + getName());
    textWriter.newLine();
    PbSpRekTransaction   transaction;
    LocalDate            date;

    for (int index = 1; index <= numberOfTransactions(); index++) {
      transaction = (PbSpRekTransaction) getTransaction(index);

      if (!transaction.isHandled()) {
        break;
      }

      date = transaction.getBookingDate();
      if (date != null) {
        textWriter.write(date.format(DTF));
      }
      textWriter.write('\t');

      textWriter.write(transaction.getDescription());
      textWriter.write('\t');

      date = transaction.getExecutionDate();
      if (date != null) {
        textWriter.write(date.format(DTF));
      }
      textWriter.write('\t');

      PgCurrency nieuwSaldo = transaction.getNieuwTegoed();
      if (nieuwSaldo != null) {
        textWriter.write(CF_LARGE.format(nieuwSaldo));
      }
      textWriter.write('\t');

      String comment = transaction.getComment();
      if (comment != null) {
        textWriter.write(comment);
      }
      textWriter.newLine();
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