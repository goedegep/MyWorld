package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.DividendOntvangst;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDepotPeriodicReport;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.text.SimpleDateFormat;
import java.util.List;

@SuppressWarnings("serial")
public class DividendenOverzichtTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int DATUM_COLUMN = 0;
  private static final int OMSCHRIJVING_COLUMN = 1;
  private static final int BRUTO_BEDRAG_COLUMN = 2;
  private static final int DIV_BELASTING_COLUMN = 3;
  private static final int PROVISIE_COLUMN = 4;
  private static final int NETTO_BEDRAG_COLUMN = 5;
  
  private static final String[] columnNames = {
      "Datum",
      "Omschrijving",
      "Bruto-bedrag",
      "Div. belasting",
      "Provisie",
      "Netto-bedrag"
  };
  
  private static final Object[] longValues = {
      "99-99-9999",
      "Dividend omschrijving",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99",
      PgCurrencyFormat.EURO_SYMBOL_STR + "9999,99",
      PgCurrencyFormat.EURO_SYMBOL_STR + "9999,99",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99"
  };
  
  private static String[][] DEFAULT_DATA = {{null, "geen informatie beschikbaar", null, null, null, null}};
  private static final SimpleDateFormat  DF =  new SimpleDateFormat("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  
  private StockDepot          stockDepot = null;
  private String[][]          data = null;
  
  public DividendenOverzichtTableModel(StockDepot stockDepot, Integer year) {
    super(columnNames, longValues);
    this.stockDepot = stockDepot;
    
    fillData(year);
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
    case DATUM_COLUMN:
    case OMSCHRIJVING_COLUMN:
    case BRUTO_BEDRAG_COLUMN:
    case DIV_BELASTING_COLUMN:
    case PROVISIE_COLUMN:
    case NETTO_BEDRAG_COLUMN:
      return String.class;

    default:
      return null;
    }
  }
  
  public void setYear(Integer year) {
    fillData(year);
  }

  void fillData(Integer year) {
    data = DEFAULT_DATA;

    if (year == null) {
      return;
    }

    StockDepotPeriodicReport<Integer> taxReport = stockDepot.getTaxReport(year);
//    TaxReport taxReport = stockDepot.getTaxReport(year);

    if (taxReport == null) {
      return;
    }

    List<DividendOntvangst> dividends = taxReport.getDividends();
    data = new String[dividends.size() + 2][6];    // two extra row for empty line and the total values.

    int index = 0;
    PgCurrency totaalBrutoBedrag = null;
    PgCurrency totaalDividendBelasting = null;
    PgCurrency totaalProvisie = null;
    PgCurrency totaalNettoBedrag = null;      
    for (DividendOntvangst dividend: dividends) {
      data[index][0] = DF.format(dividend.getOntvangstDatum());
      data[index][1] = "aand " + dividend.getEffect().getName();
      PgCurrency brutoBedrag = dividend.getBrutoBedrag();
      data[index][2] = CF.format(brutoBedrag);
      if (totaalBrutoBedrag == null) {
        totaalBrutoBedrag = brutoBedrag;
      } else {
        totaalBrutoBedrag = totaalBrutoBedrag.add(brutoBedrag);
      }
      PgCurrency dividendBelasting = dividend.getDividendBelasting();
      data[index][3] = CF.format(dividendBelasting);
      if (totaalDividendBelasting == null) {
        totaalDividendBelasting = dividendBelasting;
      } else {
        totaalDividendBelasting = totaalDividendBelasting.add(dividendBelasting);
      }
      PgCurrency provisie = dividend.getProvisie();
      data[index][4] = CF.format(provisie);
      if (totaalProvisie == null) {
        totaalProvisie = provisie;
      } else {
        totaalProvisie = totaalProvisie.add(provisie);
      }
      PgCurrency nettoBedrag = brutoBedrag.subtract(dividendBelasting).subtract(provisie);
      data[index][5] = CF.format(nettoBedrag);
      if (totaalNettoBedrag == null) {
        totaalNettoBedrag = nettoBedrag;
      } else {
        totaalNettoBedrag = totaalNettoBedrag.add(nettoBedrag);
      }
      index++;
    }

    index++;  // Skip one row.

    data[index][1] = "totale waarde";
    if (totaalBrutoBedrag != null) {
      data[index][2] = CF.format(totaalBrutoBedrag);
    }      
    if (totaalDividendBelasting != null) {
      data[index][3] = CF.format(totaalDividendBelasting);
    }      
    if (totaalProvisie != null) {
      data[index][4] = CF.format(totaalProvisie);
    }      
    if (totaalNettoBedrag != null) {
      data[index][5] = CF.format(totaalNettoBedrag);
    }      

    fireTableDataChanged();
  }
}
