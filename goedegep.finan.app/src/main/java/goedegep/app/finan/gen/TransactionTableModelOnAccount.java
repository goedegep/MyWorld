package goedegep.app.finan.gen;

import java.time.LocalDate;
import java.util.HashMap;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.TransactionFilter;

@SuppressWarnings("serial")
public class TransactionTableModelOnAccount extends TransactionTableModel {
  private PgAccount  account;

  public TransactionTableModelOnAccount(PgAccount account) {
    super(true);
    this.account = account;
  }

  public void setValueAt(Object value, int row, int column) {
    LocalDate date;
    
    if (filteredTransactionsMap != null) {
      row = filteredTransactionsMap.get(row);
    }
        
    PgTransaction transaction = (PgTransaction) account.getTransaction(row);
    switch (column) {
    case RENTE_DATUM_COLUMN:
        date = (LocalDate) value;
        if ((transaction.getBookingDate() == null)  ||
            !transaction.getBookingDate().equals(date)) {
          transaction.setBookingDate(date);
        }
      break;

    case UITVOERINGS_DATUM_COLUMN:
      date = (LocalDate) value;
      if ((transaction.getExecutionDate() == null)  ||
          !transaction.getExecutionDate().equals(date)) {
        transaction.setExecutionDate(date);
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
    if (filteredTransactionsMap != null) {
      return filteredTransactionsMap.size();
    } else if (account != null) {
      return account.numberOfTransactions();
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    if (filteredTransactionsMap != null) {
      row = filteredTransactionsMap.get(row);
    }
    
    PgTransaction transaction = (PgTransaction) account.getTransaction(row + 1);
    return getValueAt(transaction, col);
  }

  protected void fillFilteredTransactionsMap() {
    filteredTransactionsMap = new HashMap<Integer, Integer>();
    int transactionsIndex = 0;
    int filteredTransactionsIndex = 0;
    
    for (PgTransaction transaction: account.getTransactions()) {
      boolean filterPassed = true;
      for (TransactionFilter filter: filters) {
        if (!filterPassed) {
          break;
        }
        filterPassed = filter.Filter(transaction);
      }
      if (filterPassed) {
        filteredTransactionsMap.put(filteredTransactionsIndex++, transactionsIndex);
      }
      transactionsIndex++;
    }
  }
}
