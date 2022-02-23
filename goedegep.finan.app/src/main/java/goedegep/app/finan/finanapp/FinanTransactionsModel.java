package goedegep.app.finan.finanapp;

import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.DataWithIcon;
import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

@SuppressWarnings("serial")
public class FinanTransactionsModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int                      REKENING_COLUMN = 0;
  private static final int                      RENTE_DATUM_COLUMN = 1;
  private static final int                      TRANSACTIE_COLUMN = 2;
  private static final int                      UITV_DATUM_COLUMN = 3;
  private static final int                      OPMERKINGEN_COLUMN = 4;
  
  private SumAccount      sumAccount = null;
  
  private static final String[] columnNames = {
      "Rekening",
      "Rente Datum",
      "Transactie",
      "Uitv. Datum",
      "Opmerkingen"
  };
  
  private static final Object[] longValues = {
      new DataWithIcon("Postbank Beleggingsfonds", null),
      LocalDate.now(),
      "this is long description, this is long description, this is long description, this is long description",
      LocalDate.now(),
      "Dit is de commentaar lengte"};
  
  private Map<String, Icon> bankIcons = new HashMap<String, Icon>(); 
  
  public FinanTransactionsModel(SumAccount sumAccount) {
    super(columnNames, longValues);
    this.sumAccount = sumAccount;
    
    for (FinanBank finanBank: sumAccount.getBanks()) {
      finanBank.getBankLogoIcon();
      bankIcons.put(finanBank.getBank().getName(), finanBank.getBankLogoIcon());
    }
  }

  public boolean isCellEditable(int row, int column) {
    PgTransaction transaction;
    
    switch (column) {
    // TODO Make the transaction column editable.
    
    case RENTE_DATUM_COLUMN:
      return true;
      
    case UITV_DATUM_COLUMN:
      transaction = getTransaction(row);
      if (transaction.isExecutionDateSupported()) {
        return true;
      } else {
        return false;
      }
      
    case OPMERKINGEN_COLUMN:
      return true;
      
    default:
      return false;
    }
  }

  public void setValueAt(Object value, int row, int column) {
    LocalDate date;
    
    FinanTransaction finanTransaction = sumAccount.getTransaction(row);
    PgTransaction transaction = finanTransaction.getTransaction();
    switch (column) {
    case RENTE_DATUM_COLUMN:
        date = (LocalDate) value;
        if ((transaction.getBookingDate() == null)  ||
            !transaction.getBookingDate().equals(date)) {
          transaction.setBookingDate(date);
          sumAccount.transactionUpdated(finanTransaction);
        }
      break;

    case UITV_DATUM_COLUMN:
      date = (LocalDate) value;
      if ((transaction.getExecutionDate() == null)  ||
          !transaction.getExecutionDate().equals(date)) {
        transaction.setExecutionDate(date);
        sumAccount.transactionUpdated(finanTransaction);
      }
      break;
      
    case OPMERKINGEN_COLUMN:
      String string = (String) value;
      if ((transaction.getComment() == null)  ||
          !transaction.getComment().equals(string)) {
        transaction.setComment(string);
        sumAccount.transactionUpdated(finanTransaction);
      }
      break;
    }
    fireTableCellUpdated(row, column);
  }

  public int getRowCount() {
    if (sumAccount != null) {
      return sumAccount.numberOfTransactions();
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    FinanTransaction finanTransaction = (FinanTransaction) sumAccount.getTransaction(row);
    PgTransaction currentTransaction = finanTransaction.getTransaction();
    Bank bank = finanTransaction.getBank();
    if (bank == null) {
      System.out.println("ERROR: No bank for transaction nr. " + row);
      return "No bank";
    } else {
      switch (col) {
      case REKENING_COLUMN:
        return new DataWithIcon(currentTransaction.getAccount().getName(), bankIcons.get(bank.getName()));

      case RENTE_DATUM_COLUMN:
        return currentTransaction.getBookingDate();

      case TRANSACTIE_COLUMN:
        return currentTransaction.getDescription();

      case UITV_DATUM_COLUMN:
        return currentTransaction.getExecutionDate();

      case OPMERKINGEN_COLUMN:
        String comment = currentTransaction.getComment();
        if (comment != null) {
          return comment;
        } else {
          return "";
        }

      default:
        return null;
      }
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
    case REKENING_COLUMN:
      return DataWithIcon.class;

    case RENTE_DATUM_COLUMN:
      return LocalDate.class;

    case TRANSACTIE_COLUMN:
      return String.class;

    case UITV_DATUM_COLUMN:
      return LocalDate.class;

    case OPMERKINGEN_COLUMN:
      return String.class;

    default:
      return null;
    }
  }
  
  private PgTransaction getTransaction(int row) {
    FinanTransaction finanTransaction = (FinanTransaction) sumAccount.getTransaction(row);
    return finanTransaction.getTransaction();    
  }
}