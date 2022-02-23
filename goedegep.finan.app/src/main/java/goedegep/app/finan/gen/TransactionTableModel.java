package goedegep.app.finan.gen;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.TransactionFilter;
import goedegep.util.money.PgCurrency;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public abstract class TransactionTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  protected static final int RENTE_DATUM_COLUMN = 0;
  protected static final int OMSCHRIJVING_COLUMN = 1;
  protected static final int UITVOERINGS_DATUM_COLUMN = 2;
  protected static final int SALDO_COLUMN = 3;
  protected static final int OPMERKINGEN_COLUMN = 4;
  
  private static final String[] columnNames = {
      "Rentedatum",
      "Omschrijving",
      "Uitv.datum",
      "Saldo",
      "Opmerkingen"
  };
  
  private static final Object[] longValues = {
      new GregorianCalendar(1989, 12, 31).getTime(),
      "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnoaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      new GregorianCalendar(1989, 12, 31).getTime(),
      new PgCurrency(PgCurrency.EURO, 99999999),
      "Dit is het commentaar"};

  private boolean editable = false;
  protected Map<Integer, Integer> filteredTransactionsMap = null;
  protected List<TransactionFilter> filters = null;

  public TransactionTableModel(boolean editable) {
    super(columnNames, longValues);
    
    this.editable = editable;
  }

  public boolean isCellEditable(int row, int column) {
    if (!editable) {
      return false;
    }
    
    switch (column) {
    case RENTE_DATUM_COLUMN:
      return true;
      
    case OMSCHRIJVING_COLUMN:
      // TODO omschrijving editable maken.
      return false;
      
    case UITVOERINGS_DATUM_COLUMN:
      return true;
      
    case SALDO_COLUMN:
      return false;
      
    case OPMERKINGEN_COLUMN:
      return true;
      
    default:
      return false;
    }
  }
  
  protected Object getValueAt(PgTransaction transaction, int col) {
    switch (col) {
    case RENTE_DATUM_COLUMN:
      return transaction.getBookingDate();

    case OMSCHRIJVING_COLUMN:
      return transaction.getDescription();

    case UITVOERINGS_DATUM_COLUMN:
      return transaction.getExecutionDate();

    case SALDO_COLUMN:
      if (transaction.isHandled()) {
        return transaction.getNieuwTegoed();
      } else {
        return null;
      }

    case OPMERKINGEN_COLUMN:
      return transaction.getComment();

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
  public Class<?> getColumnClass(int col) {
    switch (col) {
    case RENTE_DATUM_COLUMN:
      return Date.class;
      
    case OMSCHRIJVING_COLUMN:
      return String.class;

    case UITVOERINGS_DATUM_COLUMN:
      return Date.class;

    case SALDO_COLUMN:
      return PgCurrency.class;

    case OPMERKINGEN_COLUMN:
      return String.class;

    default:
      return null;
    }
  }

  public void setFilters(List<TransactionFilter> filters) {
    this.filters = filters;
    
    if (filters != null) {
      fillFilteredTransactionsMap();
    } else {
      filteredTransactionsMap = null;
    }
  }
  
  protected abstract void fillFilteredTransactionsMap();
}
