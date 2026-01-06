//Title:        Financien
//Version:
//Copyright:    Copyright (c) 2001
//Author:       Peter Goedegebure
//Company:      Solvation
//Description:  Postbank effectenrekening
package goedegep.finan.abnamrobank.aaeffrek;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import goedegep.finan.abnamrobank.AbnAmroBankBasic;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.effrek.EffRek;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.stocks.DividendOntvangst;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockBuySellCombo;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDepotPeriodicReport;
import goedegep.finan.stocks.StockPosition;
import goedegep.finan.stocks.StockPositionHistory;
import goedegep.finan.stocks.StockVModelStatus;
import goedegep.util.datetime.DateUtil;
import goedegep.util.datetime.Quarter;
import goedegep.util.datetime.QuarterFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.text.TextWriter;

public class AAEffRek extends EffRek {
  private static final SimpleDateFormat  df = new SimpleDateFormat("dd-MM-yyyy");
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat();
  private static final PgCurrencyFormat  cfLarge = new PgCurrencyFormat(9);
  private static final QuarterFormat     qf = new QuarterFormat();
  
  private PgAccount  dekkingsRekening;

  public AAEffRek(StockDepot parentDepot, PgAccount dekkingsRekening) {
    super(parentDepot, AbnAmroBankBasic.EFFECTEN_REKENING_STRING, PgCurrency.GUILDER);

    this.dekkingsRekening = dekkingsRekening;
  }
    
  public PgAccount getDekkingsRekening() {
    return dekkingsRekening;
  }

  public String getStatusAsString() {
    PgCurrency        balance = getBalance();
    PgCurrency        value   = getVerzamelDepot().getValue();
    if (value.getCurrency() != balance.getCurrency()) {
        value = value.changeCurrency(balance.getCurrency());
    }
    PgCurrency        nettoStorting = getNettoStorting();
    if (nettoStorting.getCurrency() != balance.getCurrency()) {
        nettoStorting = nettoStorting.changeCurrency(balance.getCurrency());
    }
    PgCurrency        profit = balance.add(value).subtract(nettoStorting);

    return "saldo: " + cf.format(balance) +
           " + depotwaarde: " + cf.format(value) +
           " - netto storting: " + cf.format(nettoStorting) +
           " = winst: " + cf.format(profit);
  }

  public String toString(PgTransaction transaction) {
    return "Not implemented";
  }

  public String toXmlString(PgTransaction transaction, String nameSpace) {
    return "Not implemented";
  }

  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("ABN AMRO EFFECTENREKENING");
    textWriter.newLine();
    dumpTransactions(textWriter);
    dumpKwartaalOverzichten(textWriter);
    dumpFiscaleJaarOverzichten(textWriter);
    dumpVModelStatussen(textWriter);
  }

  private void dumpTransactions(TextWriter textWriter) throws IOException {
    textWriter.write("ABN AMRO EFFECTENREKENING TRANSACTIONS");
    textWriter.newLine();
    EffRekTransactie   transaction;
    LocalDate          date;

    for (int index = 1; index <= numberOfTransactions(); index++) {
      transaction = (EffRekTransactie) getTransaction(index);

      if (!transaction.isHandled()) {
        break;
      }

      date = transaction.getBookingDate();
      if (date != null) {
        textWriter.write(df.format(DateUtil.localDateToDate(date)));
      }
      textWriter.write('\t');

      textWriter.write(transaction.getDescription());
      textWriter.write('\t');

      date = transaction.getExecutionDate();
      if (date != null) {
        textWriter.write(df.format(DateUtil.localDateToDate(date)));
      }
      textWriter.write('\t');

      PgCurrency nieuwSaldo = transaction.getNieuwTegoed();
      if (nieuwSaldo != null) {
        textWriter.write(cfLarge.format(nieuwSaldo));
      }
      textWriter.write('\t');

      String comment = transaction.getComment();
      if (comment != null) {
        textWriter.write(comment);
      }
      textWriter.newLine();
    }
  }
  
  private void dumpKwartaalOverzichten(TextWriter textWriter) throws IOException {
    textWriter.write("ABN AMRO EFFECTENREKENING KWARTAALOVERZICHTEN");
    textWriter.newLine();
    
    List<StockDepotPeriodicReport<Quarter>> quarterReports = getVerzamelDepot().getQuarterReports();

    for (StockDepotPeriodicReport<Quarter> quarterReport: quarterReports) {
      dumpKwartaalOverzicht(quarterReport, textWriter);
    }
    
//    List<QuarterReport> quarterReports = getVerzamelDepot().getQuarterReports();
//
//    for (QuarterReport quarterReport: quarterReports) {
//      dumpKwartaalOverzicht(quarterReport, out);
//    }
  }

  private void dumpKwartaalOverzicht(StockDepotPeriodicReport<Quarter> quarterReport, TextWriter textWriter) throws IOException {
    Quarter quarter = quarterReport.getPeriod();
    textWriter.write("ABN AMRO EFFECTENREKENING KWARTAALOVERZICHT " + qf.format(quarter));
    textWriter.newLine();
    
    PgCurrency totaalWaarde = null;

    Set<Share> shares = quarterReport.getShares();
    List<Share> sortedShares = new LinkedList<Share>(shares);
    if (sortedShares.size() != 0) {
      Share aShare = sortedShares.get(0);
      Collections.sort(sortedShares, aShare);
    }

    for (Share share: sortedShares) {
      textWriter.write("aand");
      textWriter.write('\t');
      textWriter.write(share.getName());
      textWriter.write('\t');
      textWriter.write(String.valueOf(quarterReport.getSharePosition(share)));
      textWriter.write('\t');
      PgCurrency rate = share.getQuarterRate(quarter);
      if (rate != null) {
        textWriter.write(cfLarge.format(rate));
        textWriter.write('\t');
        PgCurrency waarde = rate.multiply(quarterReport.getSharePosition(share)).certifyFactor(100);
        textWriter.write(cfLarge.format(waarde));
        textWriter.write('\t');
        if (totaalWaarde == null) {
          totaalWaarde = waarde;
        } else {
          totaalWaarde = totaalWaarde.add(waarde);
        }
      }
      textWriter.newLine();
    }

    Set<Share> shareFractions = quarterReport.getShareFractions();
    List<Share> sortedShareFractions = new LinkedList<Share>(shareFractions);
    if (sortedShareFractions.size() != 0) {
      Share aShareFraction = sortedShareFractions.get(0);
      Collections.sort(sortedShareFractions, aShareFraction);
    }

    for (Share share: sortedShareFractions) {
      textWriter.write("aand fractie");
      textWriter.write('\t');
      textWriter.write(share.getName());
      textWriter.write('\t');
      float fraction = ((float) quarterReport.getShareFractionPosition(share).doubleValue());
      textWriter.write(String.valueOf(fraction));
      textWriter.write('\t');
      PgCurrency rate = share.getQuarterRate(quarter);
      if (rate != null) {
        textWriter.write(cfLarge.format(rate));
        textWriter.write('\t');
        PgCurrency waarde = rate.multiply(fraction);
        textWriter.write(cfLarge.format(waarde));
        textWriter.write('\t');
        if (totaalWaarde == null) {
          totaalWaarde = waarde;
        } else {
          totaalWaarde = totaalWaarde.add(waarde);
        }
      }
      textWriter.newLine();
    }

    textWriter.write("totale waarde: ");
    if (totaalWaarde != null) {
      textWriter.write(cfLarge.format(totaalWaarde));
    }      
    textWriter.newLine();
  }

  private void dumpFiscaleJaarOverzichten(TextWriter textWriter) throws IOException {
    textWriter.write("ABN AMRO EFFECTENREKENING FISCALE JAAROVERZICHTEN");
    textWriter.newLine();
    
    List<StockDepotPeriodicReport<Integer>> taxReports = getVerzamelDepot().getTaxReports();

    for (StockDepotPeriodicReport<Integer> taxReport: taxReports) {
      dumpFiscaalJaarOverzicht(taxReport, textWriter);
    }
    
//    List<TaxReport> taxReports = getVerzamelDepot().getTaxReports();
//
//    for (TaxReport taxReport: taxReports) {
//      dumpFiscaalJaarOverzicht(taxReport, out);
//    }
  }

  private void dumpFiscaalJaarOverzicht(StockDepotPeriodicReport<Integer> taxReport, TextWriter textWriter) throws IOException {
    int year = taxReport.getPeriod();
    textWriter.write("ABN AMRO EFFECTENREKENING FISCAAL JAAROVERZICHT " + year);
    textWriter.newLine();
    
    List<DividendOntvangst> dividends = taxReport.getDividends();

    PgCurrency totaalBrutoBedrag = null;
    PgCurrency totaalDividendBelasting = null;
    PgCurrency totaalProvisie = null;
    PgCurrency totaalNettoBedrag = null;      
    for (DividendOntvangst dividend: dividends) {
      textWriter.write(df.format(dividend.getOntvangstDatum()));
      textWriter.write('\t');
      textWriter.write("aand " + dividend.getEffect().getName());
      textWriter.write('\t');
      PgCurrency brutoBedrag = dividend.getBrutoBedrag();
      textWriter.write(cfLarge.format(brutoBedrag));
      textWriter.write('\t');
      if (totaalBrutoBedrag == null) {
        totaalBrutoBedrag = brutoBedrag;
      } else {
        totaalBrutoBedrag = totaalBrutoBedrag.add(brutoBedrag);
      }
      PgCurrency dividendBelasting = dividend.getDividendBelasting();
      textWriter.write(cfLarge.format(dividendBelasting));
      textWriter.write('\t');
      if (totaalDividendBelasting == null) {
        totaalDividendBelasting = dividendBelasting;
      } else {
        totaalDividendBelasting = totaalDividendBelasting.add(dividendBelasting);
      }
      PgCurrency provisie = dividend.getProvisie();
      textWriter.write(cfLarge.format(provisie));
      textWriter.write('\t');
      if (totaalProvisie == null) {
        totaalProvisie = provisie;
      } else {
        totaalProvisie = totaalProvisie.add(provisie);
      }
      PgCurrency nettoBedrag = brutoBedrag.subtract(dividendBelasting).subtract(provisie);
      textWriter.write(cfLarge.format(nettoBedrag));
      textWriter.write('\t');
      if (totaalNettoBedrag == null) {
        totaalNettoBedrag = nettoBedrag;
      } else {
        totaalNettoBedrag = totaalNettoBedrag.add(nettoBedrag);
      }
      
      textWriter.newLine();
    }

    textWriter.write("totaal bruto bedrag: ");
    if (totaalBrutoBedrag != null) {
      textWriter.write(cfLarge.format(totaalBrutoBedrag));
    }
    textWriter.write('\t');
    textWriter.write("totaal dividendbelasting: ");
    if (totaalDividendBelasting != null) {
      textWriter.write(cfLarge.format(totaalDividendBelasting));
    }      
    textWriter.write('\t');
    textWriter.write("totaal provisie: ");
    if (totaalProvisie != null) {
      textWriter.write(cfLarge.format(totaalProvisie));
    }      
    textWriter.write('\t');
    textWriter.write("totaal netto bedrag: ");
    if (totaalNettoBedrag != null) {
      textWriter.write(cfLarge.format(totaalNettoBedrag));
    }      
    textWriter.newLine();
  }
  
  private void dumpVModelStatussen(TextWriter textWriter) throws IOException {
    textWriter.write("ABN AMRO EFFECTENREKENING VMODEL STATUSSEN");
    textWriter.newLine();

    for (StockPosition stockPosition: getVerzamelDepot().getStockPositions()) {   
      dumpVModelStatus(textWriter, stockPosition);
    }
  }
  
  private void dumpVModelStatus(TextWriter textWriter, StockPosition stockPosition) throws IOException {
    int amount = stockPosition.getCurrentAmount();
    PgCurrency investment = stockPosition.getInvestment();
    
    textWriter.write("ABN AMRO EFFECTENREKENING VMODEL STATUS: ");    
    Share share = stockPosition.getStock();
    textWriter.write(share.getName());
    textWriter.newLine();
    
    textWriter.write("aantal aandelen: ");
    textWriter.write(String.valueOf(amount));
    textWriter.newLine();
    
    textWriter.write("Investering: ");
    textWriter.write(cf.format(investment));
    textWriter.newLine();
    
    if (amount > 0) {
      textWriter.write("Gemiddelde koers: ");
      textWriter.write(cf.format(investment.divide(amount)));
      textWriter.newLine();      
    }
    
    StockVModelStatus status = stockPosition.getVModelStatus();
    textWriter.write("Totale winst: ");
    textWriter.write(cf.format(status.getTotalBuySellProfit()));
    textWriter.newLine();
    
    dumpVModelStatusBuySellComboTable(textWriter, status.getBuySellCombos());
    dumpVModelStatusBuysTable(textWriter, status.getBuys());
    
    PgCurrency lastBuyRate = null;
    if (status.getBuys().size() != 0) {
      lastBuyRate = status.getBuys().get(status.getBuys().size() - 1).getRate();
    } else {
      lastBuyRate = status.getBuySellCombos().get(status.getBuySellCombos().size() - 1).getBuyRate();
    }
    if (lastBuyRate != null) {
      textWriter.write("Bijkopen 3%: ");
      textWriter.write(cf.format(lastBuyRate.multiply(0.97)));
      textWriter.newLine();
      textWriter.write("Bijkopen 2%: ");
      textWriter.write(cf.format(lastBuyRate.multiply(0.98)));
      textWriter.newLine();
    }
  }
  
  private void dumpVModelStatusBuySellComboTable(TextWriter textWriter, List<StockBuySellCombo> buySellCombos) throws IOException {
    textWriter.write("Aantal:  Gekocht op:  Gekocht tegen:  Verkocht op:  Verkocht tegen:  Winst:");
    textWriter.newLine();
    
    if (buySellCombos != null  &&  buySellCombos.size() != 0) {      
      
      for (StockBuySellCombo combo: buySellCombos) {
        if (combo.isIntegerAmount()) {
          textWriter.write(String.valueOf(combo.getIntAmount()));
        } else {
          textWriter.write(String.valueOf(combo.getAmount()));
        }
        textWriter.write("      ");
        textWriter.write(df.format(combo.getBuyDate()));
        textWriter.write("   ");
        textWriter.write(cf.format(combo.getBuyRate()));
        textWriter.write("       ");
        textWriter.write(df.format(combo.getSellDate()));
        textWriter.write("     ");
        textWriter.write(cf.format(combo.getSellRate()));
        textWriter.write("           ");
        textWriter.write(cf.format(combo.getProfit()));
        textWriter.newLine();
      } 
    }
  }
  
  private void dumpVModelStatusBuysTable(TextWriter textWriter, List<StockPositionHistory> buys) throws IOException {   
    textWriter.write("Gekocht op:  Aantal:  Gekocht tegen:  Prijs:  Verkoop tegen (3%):");
    textWriter.newLine();
    
    if (buys != null  &&  buys.size() != 0) {      
      
      for (StockPositionHistory history: buys) {
        textWriter.write(df.format(DateUtil.localDateToDate(history.getDate())));
        textWriter.write("   ");
        if (history.isIntegerAmount()) {
          textWriter.write(String.valueOf(history.getIntAmount()));
        } else {
          textWriter.write(String.valueOf(history.getAmount()));
        }
        textWriter.write("      ");
        textWriter.write(cf.format(history.getRate()));
        textWriter.write("          ");
        if (history.getBedrag() != null  &&  history.getAmount() != 0) {
          textWriter.write(cf.format(history.getBedrag().divide(history.getAmount())));
        } else {
          textWriter.write("-----");
        }
        textWriter.write("    ");
        textWriter.write(cf.format(history.getRate().multiply(1.03)));
        textWriter.newLine();
      }      
    }
  }
}