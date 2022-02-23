package goedegep.app.finan.postbankapp;

import java.time.LocalDate;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.postbank.pbfonds.PbFonds;
import goedegep.finan.postbank.pbfonds.PbFondsTransaction;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

@SuppressWarnings("serial")
public class PbFondsTransactionsModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int DATUM_COLUMN = 0;
  private static final int OMSCHRIJVING_COLUMN = 1;
  private static final int NIEUW_TEGOED_COLUMN = 2;
  private static final int WAARDE_COLUMN = 3;
  private static final int OPMERKINGEN_COLUMN = 4;
  
  private static final String[] columnNames = {
      "Datum",
      "Omschrijving",
      "Nieuw Tegoed",
      "Waarde"
  };
  
  private static final Object[] longValues = {
      "10-11-1989",
      "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmno",
      "999.999,9999",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99"};
  
  private PbFonds                   fonds;
  
  public PbFondsTransactionsModel(PbFonds fonds) {
    super(columnNames, longValues);
    this.fonds = fonds;
  }
  
  public boolean isCellEditable(int row, int column) {
    switch (column) {
    case DATUM_COLUMN:
      return true;
      
    case OMSCHRIJVING_COLUMN:
      // TODO omschrijving editable maken.
      return false;
      
    case NIEUW_TEGOED_COLUMN:
      return false;
      
    case WAARDE_COLUMN:
      return false;
      
    case OPMERKINGEN_COLUMN:
      return true;
      
    default:
      return false;
    }
  }

  public void setValueAt(Object value, int row, int column) {
    LocalDate date;
    
    PbFondsTransaction transaction = (PbFondsTransaction) fonds.getTransaction(row);
    switch (column) {
    case DATUM_COLUMN:
        date = (LocalDate) value;
        if ((transaction.getBookingDate() == null)  ||
            !transaction.getBookingDate().equals(date)) {
          transaction.setBookingDate(date);
        }
      break;

    case OPMERKINGEN_COLUMN:
      String string = (String) value;
      if ((transaction.getComment() == null)  ||
          !transaction.getComment().equals(string)) {
        transaction.setComment(string);
      }
      break;
    }
    fireTableCellUpdated(row, column);
  }

  public int getRowCount() {
    if (fonds != null) {
      return fonds.numberOfTransactions();
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    PbFondsTransaction transaction = (PbFondsTransaction) fonds.getTransaction(row + 1);
    switch (col) {
    case DATUM_COLUMN:
      return transaction.getBookingDate();

    case OMSCHRIJVING_COLUMN:
      return transaction.getDescription();

    case NIEUW_TEGOED_COLUMN:
      if (transaction.isHandled()) {
        return transaction.getNewNumberOfShares();
      } else {
        return null;
      }

    case WAARDE_COLUMN:
      if (transaction.isHandled()) {
        return transaction.getKoers().multiply(transaction.getNewNumberOfShares());
      } else {
        return null;
      }

    case OPMERKINGEN_COLUMN:
      String comment = transaction.getComment();
      if (comment != null) {
        return comment;
      } else {
        return "";
      }

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
    case DATUM_COLUMN:
      return LocalDate.class;

    case OMSCHRIJVING_COLUMN:
      return String.class;

    case NIEUW_TEGOED_COLUMN:
      return FixedPointValue.class;

    case WAARDE_COLUMN:
      return PgCurrency.class;

    case OPMERKINGEN_COLUMN:
      return String.class;

    default:
      return null;
    }
  }
}