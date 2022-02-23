package goedegep.app.finan.stocksapp;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.StockBuySellCombo;
import goedegep.util.money.PgCurrency;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("serial")
public class StockBuySellComboTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int AANTAL_COLUMN = 0;
  private static final int DATUM_KOOP_COLUMN = 1;
  private static final int KOERS_KOOP_COLUMN = 2;
  private static final int DATUM_VERKOOP_COLUMN = 3;
  private static final int KOERS_VERKOOP_COLUMN = 4;
  private static final int WINST_COLUMN = 5;
  
  private static final Date       LONGEST_DATE = (new GregorianCalendar(9999, 12, 30)).getTime();
  private static final PgCurrency LONGEST_MONEY_AMOUNT = new PgCurrency(PgCurrency.EURO, 999999);
  
  private static final String[] columnNames = {
    "Aantal",
    "Gekocht op",
    "Gekocht tegen",
    "Verkocht op",
    "Verkocht tegen",
    "Winst"
  };

  private static final Object[] longValues = {
    999999,
    LONGEST_DATE,
    LONGEST_MONEY_AMOUNT,
    LONGEST_DATE,
    LONGEST_MONEY_AMOUNT,
    LONGEST_MONEY_AMOUNT
  };

  List<StockBuySellCombo> buySellCombos;
  
  public StockBuySellComboTableModel(List<StockBuySellCombo> buySellCombos) {
    super(columnNames, longValues);
    this.buySellCombos = buySellCombos;
  }

  public int getRowCount() {
    if (buySellCombos != null) {
      return buySellCombos.size();
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    if (buySellCombos == null) {
      return null;
    }
    
    StockBuySellCombo combo = buySellCombos.get(row);
   
    switch (col) {
    case AANTAL_COLUMN:
      if (combo.isIntegerAmount()) {
        return String.valueOf(combo.getIntAmount());          
      } else {
        return String.valueOf(combo.getAmount());
      }

    case DATUM_KOOP_COLUMN:
      return combo.getBuyDate();

    case KOERS_KOOP_COLUMN:
      return combo.getBuyRate();

    case DATUM_VERKOOP_COLUMN:
      return combo.getSellDate();

    case KOERS_VERKOOP_COLUMN:
      return combo.getSellRate();
      
    case WINST_COLUMN:
      return combo.getProfit();

    default:
      return null;
    }
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
    case AANTAL_COLUMN:
      return String.class;
      
    case DATUM_KOOP_COLUMN:
    case DATUM_VERKOOP_COLUMN:
      return Date.class;

    case KOERS_KOOP_COLUMN:
    case KOERS_VERKOOP_COLUMN:
    case WINST_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }

  public void setBuySellCombos(List<StockBuySellCombo> buySellCombos) {
    this.buySellCombos = buySellCombos;
    fireTableDataChanged();
  }
}