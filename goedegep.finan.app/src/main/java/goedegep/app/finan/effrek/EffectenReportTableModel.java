package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDepotPeriodicReport;
import goedegep.finan.stocks.StockDividend;
import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.time.YearMonth;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class EffectenReportTableModel extends AppGenAbstractTableModel {
  private final static Logger     LOGGER = Logger.getLogger(EffectenReportTableModel.class.getName());

  // Column Identifiers
  private static final int SOORT_COLUMN = 0;
  private static final int OMSCHRIJVING_COLUMN = 1;
  private static final int AANTAL_COLUMN = 2;
  private static final int KOERS_COLUMN = 3;
  private static final int WAARDE_COLUMN = 4;
  
  private static final String[] columnNames = {
      "Soort",
      "Omschrijving",
      "Aantal",
      "Koers",
      "Waarde"
  };
  
  private static final Object[] longValues = {
      "Soort",
      "CALL TOMTOM EUR.2 OCT 2008 �9,20 of zo",
      "99999",
      PgCurrencyFormat.EURO_SYMBOL_STR + "9999,99",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99"
  };
  
  private static String[][] DEFAULT_DATA = {{null, "geen informatie beschikbaar", null, null, null}};
  private static final PgCurrencyFormat CF = new PgCurrencyFormat();
  
  private StockDepot          stockDepot = null;
  private PgCurrency          totaalWaarde;
  private int                 effectenTableIndex;
  private String[][]          data = null;
  
//  public EffectenReportTableModel(StockDepot stockDepot, Integer year, Integer quarter) {
//    super(columnNames, longValues);
//    this.stockDepot = stockDepot;
//    
//    fillData(year, quarter);
//  }
  
  public EffectenReportTableModel(EffectenReportPanelType effectenReportPanelType, StockDepot stockDepot, Object period) {
    super(columnNames, longValues);
    
    LOGGER.severe("=>");
    this.stockDepot = stockDepot;
    
    fillData(period);
    LOGGER.severe("<=");
  }

  public int getRowCount() {
    if (data == null) {
      return 0;
    } else {
      return data.length;
    }
  }

  public Object getValueAt(int row, int col) {
    return data[row][col];
  }
  
  /*
   * JTable uses this method to determine the default renderer/
   * editor for each cell.  If we didn't implement this method,
   * then the last column would contain text ("true"/"false"),
   * rather than a check box.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class getColumnClass(int col) {
    switch (col) {
    case SOORT_COLUMN:
    case OMSCHRIJVING_COLUMN:
    case AANTAL_COLUMN:
    case KOERS_COLUMN:
    case WAARDE_COLUMN:
      return String.class;

    default:
      return null;
    }
  }
  
//  public void setYear(Integer year) {
//    fillData(year, null);
//  }
//  
//  public void setQuarter(Integer year, Integer quarter) {
//    fillData(year, quarter);
//  }
  
  public void setPeriod(Object period) {
    fillData(period);
  }

  void fillData(Object period) {
    LOGGER.severe("=>");

    data = DEFAULT_DATA;

    if (period == null) {
      LOGGER.severe("period is null");
      return;
    }
    
//    if (quarter == null) {
//      quarter = 4;
//    }

    int year = -1;
    int quarter = -1;
    int month = -1;
    StockDepotPeriodicReport<? extends Comparable<?>> report = null;
    if (period instanceof Integer) {
      year = (Integer) period;
      quarter = 4;
      LOGGER.severe("period is instance of Integer. year=" + year + ", quarter=" + quarter);
      report = stockDepot.getQuarterReport(year, quarter);
    } else if (period instanceof Quarter) {
      Quarter quarterObj = (Quarter) period;
      year = quarterObj.getYear();
      quarter = quarterObj.getQuarter();
      LOGGER.severe("period is instance of Quarter. year=" + year + ", quarter=" + quarter);
      report = stockDepot.getQuarterReport(year, quarter);
    } else if (period instanceof YearMonth) {
      YearMonth monthObj = (YearMonth) period;
      year = monthObj.getYear();
      month = monthObj.getMonthValue();
      LOGGER.severe("period is instance of Month. year=" + year + ", quarter=" + quarter);
      report = stockDepot.getMonthlyReport(year, month);
    } 
    
//    StockDepotPeriodicReport<Quarter> quarterReport = stockDepot.getQuarterReport(year, quarter);
    
    if (report == null) {
      return;
    }

    totaalWaarde = null;
    effectenTableIndex = 0;

    data = new String[report.getTotalNumberOfPositions() + 2][5];  // two extra rows for empty line and the total value.

    fillSharesData(data, report);
    fillShareFractionsData(data, report);
    fillStockDividendsData(data, report, year, quarter);
    fillOptionPositionsData(data, report);

    effectenTableIndex++;  // one empty row

    data[effectenTableIndex][3] = "totale waarde";
    if (totaalWaarde != null) {
      data[effectenTableIndex][4] = CF.format(totaalWaarde);
    }
    
    fireTableDataChanged();
    LOGGER.severe("<=");
  }
  
  private void fillSharesData(String[][] data,
      StockDepotPeriodicReport<?> quarterReport) {
    Set<Share> shares        = quarterReport.getShares();
    List<Share> sortedShares = new LinkedList<Share>(shares);
    Collections.sort(sortedShares);
    
    for (Share share: sortedShares) {
      data[effectenTableIndex][0] = "aand";
      data[effectenTableIndex][1] = share.getName();
      data[effectenTableIndex][2] = String.valueOf(quarterReport.getSharePosition(share));
      effectenTableIndex++;
    }
  }

  private void fillShareFractionsData(String[][] data,
      StockDepotPeriodicReport<?> quarterReport) {
    List<Share> sortedShareFractions = new LinkedList<Share>(quarterReport.getShareFractions());
    Collections.sort(sortedShareFractions);
    
    for (Share share: sortedShareFractions) {
      data[effectenTableIndex][0] = "aand";
      data[effectenTableIndex][1] = share.getName();
      float fraction = ((float) quarterReport.getShareFractionPosition(share).doubleValue());
      data[effectenTableIndex][2] = String.valueOf(fraction);
      effectenTableIndex++;
    }
  }
  
  private void fillStockDividendsData(String[][] data,
      StockDepotPeriodicReport<?> quarterReport, Integer year, Integer quarter) {
    List<ShareDividend> sortedStockDividends = new LinkedList<ShareDividend>(quarterReport.getStockDividends());
    Collections.sort(sortedStockDividends);

    for (ShareDividend shareDividend: sortedStockDividends) {
      data[effectenTableIndex][0] = "stdiv";
      data[effectenTableIndex][1] = shareDividend.getShare().getName() + " " +
          shareDividend.getReferenceString();
      data[effectenTableIndex][2] = String.valueOf(quarterReport.getStockDividendPosition(shareDividend));
      PgCurrency rate = null;
      StockDividend stockDividend = shareDividend.getStockDividend();
      if (stockDividend != null) {
        rate = shareDividend.getStockDividend().getQuarterRate(year, quarter);
      }
      if (rate != null) {
        data[effectenTableIndex][3] = CF.format(rate);
        PgCurrency waarde = rate.multiply(quarterReport.getStockDividendPosition(shareDividend));
        data[effectenTableIndex][4] = CF.format(waarde);
        if (totaalWaarde == null) {
          totaalWaarde = waarde;
        } else {
          totaalWaarde = totaalWaarde.add(waarde);
        }
      }
      effectenTableIndex++;
    }
  }

  private void fillOptionPositionsData(String[][] data,
      StockDepotPeriodicReport<?> quarterReport) {
//    int year                 = quarterReport.getYear();
//    int quarter              = quarterReport.getQuarter().getQuarter();
//    Quarter quarter          = quarterReport.getPeriod();
    
    for (OptionSerie optionSerie : quarterReport.getOptionPositions()) {
      data[effectenTableIndex][0] = "optie";
      data[effectenTableIndex][1] = optionSerie.getOptionType() + " " +
        optionSerie.getShare().getName() + " " +
        optionSerie.getExpirationMonthText() + " " +
        optionSerie.getExpirationYear() + " " +
        CF.format(optionSerie.getUitoefeningsKoers());
      data[effectenTableIndex][2] = String.valueOf(quarterReport.getOptionPosition(optionSerie));
//      PgCurrency rate = optionSerie.getQuarterRate(quarter.getYear(), quarter.getQuarter());
//      if (rate != null) {
//        data[effectenTableIndex][3] = CF.format(rate);
//        PgCurrency waarde = rate.multiply(quarterReport.getOptionPosition(optionSerie)).multiply(100);
//        data[effectenTableIndex][4] = CF.format(waarde);
//        if (totaalWaarde == null) {
//          totaalWaarde = waarde;
//        } else {
//          totaalWaarde = totaalWaarde.add(waarde);
//        }
//      }
      effectenTableIndex++;
    }
  }
}
