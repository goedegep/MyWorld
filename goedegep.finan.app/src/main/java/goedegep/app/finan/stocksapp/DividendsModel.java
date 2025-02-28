package goedegep.app.finan.stocksapp;

import java.util.Objects;

import goedegep.appgen.MessageDialogType;
import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.DividendType;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.finan.stocks.StockDividend;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

@SuppressWarnings("serial")
public class DividendsModel extends AppGenAbstractTableModel {
  // Column Identifiers
  protected static final int NAAM_COLUMN = 0;
  protected static final int JAAR_COLUMN = 1;
  protected static final int TYPE_COLUMN = 2;
  protected static final int BEDRAG_COLUMN = 3;  
  protected static final int DIVIDENDEN_PER_AANDEEL_COLUMN = 4;  
  protected static final int KOERS_COLUMN = 5;
  protected static final int BELASTING_PERCENTAGE_COLUMN = 6;

  private static final String[] columnNames = {
      "Naam",
      "Jaar",
      "Type",
      "Bedrag",
      "Ratio",
      "Koers",
      "Bel. %"
  };

  private static final Object[] longValues = {
    "Dividend naam",
    "2008",
    "Contant",
    PgCurrencyFormat.EURO_SYMBOL_STR + " 100000,-",
    "1000/1",
    PgCurrencyFormat.EURO_SYMBOL_STR + " 99,99",
    "25"
    };
  
  private Share share;
  
  public DividendsModel() {
    super(columnNames, longValues);
  }
  
  public int getRowCount() {
    if (share != null) {
      return share.getDividends().size();
    } else {
      return 0;
    }
  }

  public boolean isCellEditable(int row, int column) {
    return true;
  }

  public void setValueAt(Object value, int row, int column) {
    ShareDividend dividend = share.getDividends().get(row);
    String s;
    
    switch (column) {
    case NAAM_COLUMN:
      s = ((String) value).trim();
      if (s.length() == 0) {  // never set the name to an empty string.
        s = null;
      }
      // There must either be a name or a year.
      if ((dividend.getYear() != null)  ||  (s != null)) {
        if (!Objects.equals(s, dividend.getName())) {
          dividend.setName(s);
          fireTableRowsUpdated(row, row);
        }
      } else {
        getTable().showMessageDialog(MessageDialogType.WARNING, "Naam niet aangepast. De naam kan alleen leeg zijn als er een jaar is ingevuld.");
      }
      break;

    case JAAR_COLUMN:
      Integer year = null;
      if (value instanceof String) {
        s = ((String) value).trim();
        year = Integer.parseInt(s);
      } else if (value instanceof Number) {
        year = ((Number) value).intValue();
      } else if (value == null) {
        // nothing to be done.
      } else {
        throw new IllegalArgumentException("Unexpected object type");
      }

      // There must either be a name or a year.
      if ((dividend.getName() != null)  ||  (year != null)) {
        if (!Objects.equals(year, dividend.getYear())) {
          dividend.setYear(year);
          fireTableRowsUpdated(row, row);
        }
      } else {
        getTable().showMessageDialog(MessageDialogType.WARNING, "Jaar niet aangepast. Het jaar kan alleen leeg zijn als er een naam is ingevuld.");
      }
      break;

    case TYPE_COLUMN:
      if (value instanceof String) {
        s = ((String) value).trim();
        DividendType type = DividendType.getDividendTypeForText(s);
        if (type != null) {
          dividend.setType(type);
          fireTableRowsUpdated(row, row);
        }
      }
      break;

    case BEDRAG_COLUMN:
      if (value instanceof PgCurrency) {
        if (value != null) {
          dividend.setAmount((PgCurrency) value);
        }
      }
      break;

    case DIVIDENDEN_PER_AANDEEL_COLUMN:
      int ratio = -1;
      if (value instanceof String) {
        s = ((String) value).trim();
        ratio = Integer.parseInt(s);
      } else if (value instanceof Number) {
        ratio = ((Number) value).intValue();
      }

      if (ratio != -1) {
        StockDividend stockDividend = dividend.getStockDividend();
        if (stockDividend == null) {
          stockDividend = new StockDividend();
          dividend.setStockDividend(stockDividend);
        }
        stockDividend.setFromAmount(ratio);
        fireTableRowsUpdated(row, row);
      }
      break;

    case KOERS_COLUMN:
      if (value instanceof PgCurrency) {
        if (value != null) {
          StockDividend stockDividend = dividend.getStockDividend();
          if (stockDividend == null) {
            stockDividend = new StockDividend();
            dividend.setStockDividend(stockDividend);
          }
          stockDividend.setKoers((PgCurrency) value);
        }
      }
      break;

    case BELASTING_PERCENTAGE_COLUMN:
      Integer taxPercentage = null;
      if (value instanceof String) {
        s = ((String) value).trim();
        taxPercentage = Integer.parseInt(s);
      } else if (value instanceof Number) {
        taxPercentage = ((Number) value).intValue();
      } else if (value == null) {
        // nothing to be done.
      } else {
        throw new IllegalArgumentException("Unexpected object type");
      }

      // There must either be a name or a year.
      if (!Objects.equals(taxPercentage, dividend.getTaxPercentage())) {
        dividend.setTaxPercentage(taxPercentage);
        fireTableRowsUpdated(row, row);
      }
      break;

    default:
      break;
    }
  }

  public Object getValueAt(int row, int col) {
    StockDividend stockDividend;
    ShareDividend dividend = share.getDividends().get(row);
    
    switch (col) {
    case NAAM_COLUMN:
      return dividend.getName();
      
    case JAAR_COLUMN:
      return dividend.getYear();
      
    case TYPE_COLUMN:
      return dividend.getType().getText();
      
    case BEDRAG_COLUMN:
      return dividend.getAmount();
      
    case DIVIDENDEN_PER_AANDEEL_COLUMN:
      stockDividend = dividend.getStockDividend();
      if (stockDividend != null) {
        return stockDividend.getFromAmount();
      } else {
        return null;
      }
      
    case KOERS_COLUMN:
      stockDividend = dividend.getStockDividend();
      if (stockDividend != null) {
        return stockDividend.getKoers();
      } else {
        return null;
      }
      
    case BELASTING_PERCENTAGE_COLUMN:
      return dividend.getTaxPercentage();
      
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
    case NAAM_COLUMN:
    case TYPE_COLUMN:
    case DIVIDENDEN_PER_AANDEEL_COLUMN:
      return String.class;

    case JAAR_COLUMN:
    case BELASTING_PERCENTAGE_COLUMN:
      return Integer.class;

    case BEDRAG_COLUMN:
    case KOERS_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }
  
  public void setShare(Share share) {
    this.share = share;
    this.fireTableDataChanged();
  }
}
