package goedegep.finan.lynx.lynxeffrek;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import goedegep.app.finan.registry.FinanRegistry;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.effrek.EffRek;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.lynx.LynxBasic;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockBuySellCombo;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockPosition;
import goedegep.finan.stocks.StockPositionHistory;
import goedegep.finan.stocks.StockVModelStatus;
import goedegep.util.file.FileUtils;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.text.TextWriter;

public class LynxEffRek extends EffRek {
  private final static Logger            LOGGER = Logger.getLogger(LynxEffRek.class.getName()); 
  private static final DateTimeFormatter  DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  private static final PgCurrencyFormat  CF_LARGE = new PgCurrencyFormat(9);
//  private static final QuarterFormat     qf = new QuarterFormat();
  

  public LynxEffRek(StockDepot parentDepot) {
    super(parentDepot, LynxBasic.EFFECTEN_REKENING_STRING, PgCurrency.EURO);
  }

  public String getStatusAsString() {
    PgCurrency        balance = this.getBalance();
    PgCurrency        value   = this.getVerzamelDepot().getValue();
    if (value.getCurrency() != balance.getCurrency()) {
        value = value.changeCurrency(balance.getCurrency());
    }
    PgCurrency        nettoStorting = getNettoStorting();
    if (nettoStorting.getCurrency() != balance.getCurrency()) {
        nettoStorting = nettoStorting.changeCurrency(balance.getCurrency());
    }
    PgCurrency        profit = balance.add(value).subtract(nettoStorting);

    return "saldo: " + CF.format(balance) +
           " + depotwaarde: " + CF.format(value) +
           " - netto storting: " + CF.format(nettoStorting) +
           " = winst: " + CF.format(profit);
  }

  public String toString(PgTransaction transaction) {
    return getName();
  }

  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("LYNX EFFECTENREKENING");
    textWriter.newLine();
    dumpTransactions(textWriter);
//    dumpKwartaalOverzichten(out);
//    dumpFiscaleJaarOverzichten(out);
    dumpVModelStatussen(textWriter);
  }

  public List<LynxEffRekPeriodicReport<YearMonth>> getLynxMonthlyReports() {
    String dataDirectory = FinanRegistry.dataDirectory;
    Path path = Paths.get(dataDirectory, "lynx", "maandoverzichten");
    LOGGER.info("path: " + path.toString());

    ReportFileVisitor fileVisitor = new ReportFileVisitor();
    try {
      Files.walkFileTree(path, fileVisitor);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  private void dumpTransactions(BufferedWriter out) throws IOException {
    out.write("LYNX EFFECTENREKENING TRANSACTIONS");
    out.newLine();
    EffRekTransactie transaction;
    LocalDate        date;

    for (int index = 1; index <= numberOfTransactions(); index++) {
      transaction = (EffRekTransactie) getTransaction(index);

      if (!transaction.isHandled()) {
        break;
      }

      date = transaction.getBookingDate();
      if (date != null) {
        out.write(date.format(DTF));
      }
      out.write('\t');

      out.write(transaction.getDescription());
      out.write('\t');

      date = transaction.getExecutionDate();
      if (date != null) {
        out.write(date.format(DTF));
      }
      out.write('\t');

      PgCurrency nieuwSaldo = transaction.getNieuwTegoed();
      if (nieuwSaldo != null) {
        out.write(CF_LARGE.format(nieuwSaldo));
      }
      out.write('\t');

      String comment = transaction.getComment();
      if (comment != null) {
        out.write(comment);
      }
      out.newLine();
    }
  }
  
//  private void dumpKwartaalOverzichten(BufferedWriter out) throws IOException {
//    out.write("LYNX EFFECTENREKENING KWARTAALOVERZICHTEN");
//    out.newLine();
//    List<QuarterReport> quarterReports = verzamelDepot.getQuarterReports();
//
//    for (QuarterReport quarterReport: quarterReports) {
//      dumpKwartaalOverzicht(quarterReport, out);
//    }
//  }

//  private void dumpKwartaalOverzicht(QuarterReport quarterReport, BufferedWriter out) throws IOException {
//    Quarter quarter = quarterReport.getQuarter();
//    out.write("LYNX EFFECTENREKENING KWARTAALOVERZICHT " + qf.format(quarter));
//    out.newLine();
//    
//    PgCurrency totaalWaarde = null;
//
//    Set<Share> shares = quarterReport.getShares();
//    List<Share> sortedShares = new LinkedList<Share>(shares);
//    if (sortedShares.size() != 0) {
//      Share aShare = sortedShares.get(0);
//      Collections.sort(sortedShares, aShare);
//    }
//
//    for (Share share: sortedShares) {
//      out.write("aand");
//      out.write('\t');
//      out.write(share.getName());
//      out.write('\t');
//      out.write(String.valueOf(quarterReport.getSharePosition(share)));
//      out.write('\t');
//      PgCurrency rate = share.getQuarterRate(quarter);
//      if (rate != null) {
//        out.write(cfLarge.format(rate));
//        out.write('\t');
//        PgCurrency waarde = rate.multiply(quarterReport.getSharePosition(share)).certifyFactor(100);
//        out.write(cfLarge.format(waarde));
//        out.write('\t');
//        if (totaalWaarde == null) {
//          totaalWaarde = waarde;
//        } else {
//          totaalWaarde = totaalWaarde.add(waarde);
//        }
//      }
//      out.newLine();
//    }
//
//    Set<Share> shareFractions = quarterReport.getShareFractions();
//    List<Share> sortedShareFractions = new LinkedList<Share>(shareFractions);
//    if (sortedShareFractions.size() != 0) {
//      Share aShareFraction = sortedShareFractions.get(0);
//      Collections.sort(sortedShareFractions, aShareFraction);
//    }
//
//    for (Share share: sortedShareFractions) {
//      out.write("aand fractie");
//      out.write('\t');
//      out.write(share.getName());
//      out.write('\t');
//      float fraction = ((float) quarterReport.getShareFractionPosition(share)) / 10000;
//      out.write(String.valueOf(fraction));
//      out.write('\t');
//      PgCurrency rate = share.getQuarterRate(quarter);
//      if (rate != null) {
//        out.write(cfLarge.format(rate));
//        out.write('\t');
//        PgCurrency waarde = rate.multiply(fraction);
//        out.write(cfLarge.format(waarde));
//        out.write('\t');
//        if (totaalWaarde == null) {
//          totaalWaarde = waarde;
//        } else {
//          totaalWaarde = totaalWaarde.add(waarde);
//        }
//      }
//      out.newLine();
//    }
//
//    out.write("totale waarde: ");
//    if (totaalWaarde != null) {
//      out.write(cfLarge.format(totaalWaarde));
//    }      
//    out.newLine();
//  }

//  private void dumpFiscaleJaarOverzichten(BufferedWriter out) throws IOException {
//    out.write("LYNX EFFECTENREKENING FISCALE JAAROVERZICHTEN");
//    out.newLine();
//    List<TaxReport> taxReports = verzamelDepot.getTaxReports();
//
//    for (TaxReport taxReport: taxReports) {
//      dumpFiscaalJaarOverzicht(taxReport, out);
//    }
//  }

//  private void dumpFiscaalJaarOverzicht(TaxReport taxReport, BufferedWriter out) throws IOException {
//    int year = taxReport.getYear();
//    out.write("LYNX EFFECTENREKENING FISCAAL JAAROVERZICHT " + year);
//    out.newLine();
//    
//    List<PbEffRekDividend> dividends = taxReport.getDividends();
//
//    PgCurrency totaalBrutoBedrag = null;
//    PgCurrency totaalDividendBelasting = null;
//    PgCurrency totaalProvisie = null;
//    PgCurrency totaalNettoBedrag = null;      
//    for (PbEffRekDividend dividend: dividends) {
//      out.write(df.format(dividend.getExecutionDate()));
//      out.write('\t');
//      out.write("aand " + dividend.getEffect().getName());
//      out.write('\t');
//      PgCurrency brutoBedrag = dividend.getBrutoBedrag();
//      out.write(cfLarge.format(brutoBedrag));
//      out.write('\t');
//      if (totaalBrutoBedrag == null) {
//        totaalBrutoBedrag = brutoBedrag;
//      } else {
//        totaalBrutoBedrag = totaalBrutoBedrag.add(brutoBedrag);
//      }
//      PgCurrency dividendBelasting = dividend.dividendBelasting();
//      out.write(cfLarge.format(dividendBelasting));
//      out.write('\t');
//      if (totaalDividendBelasting == null) {
//        totaalDividendBelasting = dividendBelasting;
//      } else {
//        totaalDividendBelasting = totaalDividendBelasting.add(dividendBelasting);
//      }
//      PgCurrency provisie = dividend.dividendOfCouponProvisie(brutoBedrag);
//      out.write(cfLarge.format(provisie));
//      out.write('\t');
//      if (totaalProvisie == null) {
//        totaalProvisie = provisie;
//      } else {
//        totaalProvisie = totaalProvisie.add(provisie);
//      }
//      PgCurrency nettoBedrag = brutoBedrag.subtract(dividendBelasting).subtract(provisie);
//      out.write(cfLarge.format(nettoBedrag));
//      out.write('\t');
//      if (totaalNettoBedrag == null) {
//        totaalNettoBedrag = nettoBedrag;
//      } else {
//        totaalNettoBedrag = totaalNettoBedrag.add(nettoBedrag);
//      }
//      
//      out.newLine();
//    }
//
//    out.write("totaal bruto bedrag: ");
//    if (totaalBrutoBedrag != null) {
//      out.write(cfLarge.format(totaalBrutoBedrag));
//    }
//    out.write('\t');
//    out.write("totaal dividendbelasting: ");
//    if (totaalDividendBelasting != null) {
//      out.write(cfLarge.format(totaalDividendBelasting));
//    }      
//    out.write('\t');
//    out.write("totaal provisie: ");
//    if (totaalProvisie != null) {
//      out.write(cfLarge.format(totaalProvisie));
//    }      
//    out.write('\t');
//    out.write("totaal netto bedrag: ");
//    if (totaalNettoBedrag != null) {
//      out.write(cfLarge.format(totaalNettoBedrag));
//    }      
//    out.newLine();
//  }
  
  private void dumpVModelStatussen(BufferedWriter out) throws IOException {
    out.write("LYNX EFFECTENREKENING VMODEL STATUSSEN");
    out.newLine();

    for (StockPosition stockPosition: getVerzamelDepot().getStockPositions()) {   
      dumpVModelStatus(out, stockPosition);
    }
  }
  
  private void dumpVModelStatus(BufferedWriter out, StockPosition stockPosition) throws IOException {
    int amount = stockPosition.getCurrentAmount();
    PgCurrency investment = stockPosition.getInvestment();
    
    out.write("LYNX EFFECTENREKENING VMODEL STATUS: ");    
    Share share = stockPosition.getStock();
    out.write(share.getName());
    out.newLine();
    
    out.write("aantal aandelen: ");
    out.write(String.valueOf(amount));
    out.newLine();
    
    out.write("Investering: ");
    out.write(CF.format(investment));
    out.newLine();
    
    if (amount > 0) {
      out.write("Gemiddelde koers: ");
      out.write(CF.format(investment.divide(amount)));
      out.newLine();      
    }
    
    StockVModelStatus status = stockPosition.getVModelStatus();
    out.write("Totale winst: ");
    if (status != null) {
      out.write(CF.format(status.getTotalBuySellProfit()));
      out.newLine();
      
      dumpVModelStatusBuySellComboTable(out, status.getBuySellCombos());
      dumpVModelStatusBuysTable(out, status.getBuys());
      
      PgCurrency lastBuyRate = null;
      if (status.getBuys().size() != 0) {
        lastBuyRate = status.getBuys().get(status.getBuys().size() - 1).getRate();
      } else {
        lastBuyRate = status.getBuySellCombos().get(status.getBuySellCombos().size() - 1).getBuyRate();
      }
      if (lastBuyRate != null) {
        out.write("Bijkopen 3%: ");
        out.write(CF.format(lastBuyRate.multiply(0.97)));
        out.newLine();
        out.write("Bijkopen 2%: ");
        out.write(CF.format(lastBuyRate.multiply(0.98)));
        out.newLine();
      }
    } else {
      out.write("<berekening mislukt>");
    }
  }
  
  private void dumpVModelStatusBuySellComboTable(BufferedWriter out, List<StockBuySellCombo> buySellCombos) throws IOException {
    out.write("Aantal:  Gekocht op:  Gekocht tegen:  Verkocht op:  Verkocht tegen:  Winst:");
    out.newLine();
    
    if (buySellCombos != null  &&  buySellCombos.size() != 0) {      
      
      for (StockBuySellCombo combo: buySellCombos) {
        if (combo.isIntegerAmount()) {
          out.write(String.valueOf(combo.getIntAmount()));
        } else {
          out.write(String.valueOf(combo.getAmount()));
        }
        out.write("      ");
        out.write(combo.getBuyDate().format(DTF));
        out.write("   ");
        out.write(CF.format(combo.getBuyRate()));
        out.write("       ");
        out.write(combo.getSellDate().format(DTF));
        out.write("     ");
        out.write(CF.format(combo.getSellRate()));
        out.write("           ");
        out.write(CF.format(combo.getProfit()));
        out.newLine();
      } 
    }
  }
  
  private void dumpVModelStatusBuysTable(BufferedWriter out, List<StockPositionHistory> buys) throws IOException {   
    out.write("Gekocht op:  Aantal:  Gekocht tegen:  Prijs:  Verkoop tegen (3%):");
    out.newLine();
    
    if (buys != null  &&  buys.size() != 0) {      
      
      for (StockPositionHistory history: buys) {
        out.write(history.getDate().format(DTF));
        out.write("   ");
        if (history.isIntegerAmount()) {
          out.write(String.valueOf(history.getIntAmount()));
        } else {
          out.write(String.valueOf(history.getAmount()));
        }
        out.write("      ");
        out.write(CF.format(history.getRate()));
        out.write("          ");
        if (history.getBedrag() != null  &&  history.getAmount() != 0) {
          out.write(CF.format(history.getBedrag().divide(history.getAmount())));
        } else {
          out.write("-----");
        }
        out.write("    ");
        out.write(CF.format(history.getRate().multiply(1.03)));
        out.newLine();
      }      
    }
  }
  
//  class RVisitor implements FileVisitor {
//
//    @Override
//    public FileVisitResult preVisitDirectory(Object dir,
//        BasicFileAttributes attrs) throws IOException {
//      // TODO Auto-generated method stub
//      return null;
//    }
//
//    @Override
//    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs)
//        throws IOException {
//      // TODO Auto-generated method stub
//      return null;
//    }
//
//    @Override
//    public FileVisitResult visitFileFailed(Object file, IOException exc)
//        throws IOException {
//      // TODO Auto-generated method stub
//      return null;
//    }
//
//    @Override
//    public FileVisitResult postVisitDirectory(Object dir, IOException exc)
//        throws IOException {
//      // TODO Auto-generated method stub
//      return null;
//    }
//    
//  }
  
  private class ReportFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
        throws IOException {
      LOGGER.info("Handling file: " + file.toString());
      String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(file);
      LOGGER.info("filename: " + fileNameAndExtension[0] + ", extension: " + fileNameAndExtension[1]);
      if (!fileNameAndExtension[1].equals("ofx")) {
        LOGGER.info("Verkeerde extensie, bestand wordt overgeslagen");
        return CONTINUE;
      }
      
      String[] fileNameParts = fileNameAndExtension[0].split("_");
      if (fileNameParts.length < 1) {
        LOGGER.info("Account nummer ontbreekt");
        return CONTINUE;
      }
      
      String accountNumber = fileNameParts[0];
      if (!accountNumber.equals("U874486")) {
        LOGGER.info("Verkeerd account nummer");
        return CONTINUE;
      }
      
      if (fileNameParts.length < 2) {
        LOGGER.info("Eerste jaar/maand deel ontbreekt");
        return CONTINUE;
      }
      String yearMonth1 = fileNameParts[1];
      
      
      if (fileNameParts.length < 3) {
        LOGGER.info("Tweede jaar/maand deel ontbreekt");
        return CONTINUE;
      }
      String yearMonth2 = fileNameParts[2];
            
      if (!yearMonth2.equals(yearMonth1)) {
        LOGGER.info("Jaar/maand delen zijn niet gelijk");
        return CONTINUE;
      }
      
      if (!yearMonth1.matches("\\d\\d\\d\\d\\d\\d")) {
        LOGGER.info("Jaar/maand delen zijn niet van het juiste formaat");
        return CONTINUE;
      }
      
      String yearString = yearMonth1.substring(0, 4);
      LOGGER.info("jaar = " + yearString);
//      int year = Integer.valueOf(yearString);
      
      String monthString = yearMonth1.substring(4, 6);
      LOGGER.info("maand = " + monthString);
//      int monthValue = Integer.valueOf(monthString);
      
//      Month month = new Month(monthValue, year);   // TODO hier verder implementeren.
      
      return CONTINUE;
    }
    
  }
}