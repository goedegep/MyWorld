package goedegep.app.finan.stocksapp;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.StockPositionHistory;
import goedegep.util.money.PgCurrency;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("serial")
public class StockBuysTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int DATUM_KOOP_COLUMN = 0;
  private static final int AANTAL_COLUMN = 1;
  private static final int KOERS_KOOP_COLUMN = 2;
  private static final int PRIJS_COLUMN = 3;
  private static final int KOERS_VERKOOP_ADVIES_COLUMN = 4;
  
  private static final Date       LONGEST_DATE = (new GregorianCalendar(9999, 12, 30)).getTime();
  private static final PgCurrency LONGEST_MONEY_AMOUNT = new PgCurrency(PgCurrency.EURO, 999999);
  
  private static final String[] columnNames = {
    "Gekocht op",
    "Aantal",
    "Gekocht tegen",
    "Prijs",
    "Verkoop tegen (3%)"
  };

  private static final Object[] longValues = {
    LONGEST_DATE,
    999999,
    LONGEST_MONEY_AMOUNT,
    LONGEST_MONEY_AMOUNT,
    LONGEST_MONEY_AMOUNT
  };

  List<StockPositionHistory> buys;
  
  public StockBuysTableModel(List<StockPositionHistory> buys) {
    super(columnNames, longValues);
    this.buys = buys;
  }

  public int getRowCount() {
    if (buys != null) {
      return buys.size();
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    if (buys == null) {
      return null;
    }
    
    StockPositionHistory history = buys.get(row);
   
    switch (col) {
    case DATUM_KOOP_COLUMN:
      return history.getDate();

    case AANTAL_COLUMN:
      if (history.isIntegerAmount()) {
        return String.valueOf(history.getIntAmount());          
      } else {
        return String.valueOf(history.getAmount());
      }

    case KOERS_KOOP_COLUMN:
      return history.getRate();

    case PRIJS_COLUMN:
      if (history.getBedrag() != null  &&  history.getAmount() != 0) {
        if (history.isIntegerAmount()) {
          return history.getBedrag().divide(history.getIntAmount());
        } else {
          return history.getBedrag().changeFactor(10000).divide(history.getAmount());
        }
      } else {
        return null;
      }

    case KOERS_VERKOOP_ADVIES_COLUMN:
      return history.getRate().multiply(1.03);

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
    case DATUM_KOOP_COLUMN:
      return Date.class;
      
    case AANTAL_COLUMN:
      return String.class;

    case KOERS_KOOP_COLUMN:
    case PRIJS_COLUMN:
    case KOERS_VERKOOP_ADVIES_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }

  public void setBuys(List<StockPositionHistory> buys) {
    this.buys = buys;
    fireTableDataChanged();
  }
}
