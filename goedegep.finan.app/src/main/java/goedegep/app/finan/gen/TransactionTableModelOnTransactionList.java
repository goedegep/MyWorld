package goedegep.app.finan.gen;

import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.TransactionFilter;

import java.util.HashMap;
import java.util.List;


@SuppressWarnings("serial")
public class TransactionTableModelOnTransactionList extends TransactionTableModel {
  private List<PgTransaction> transactions;
  
  public TransactionTableModelOnTransactionList(List<PgTransaction> transactions) {
    super(false);
    this.transactions = transactions;
  }

  public void setValueAt(Object value, int row, int column) {
  }

  public int getRowCount() {
    if (filteredTransactionsMap != null) {
      return filteredTransactionsMap.size();
    }
    
    if (transactions == null) {
      return 0;
    }
    
    return transactions.size();
  }

  public Object getValueAt(int row, int col) {
    if (filteredTransactionsMap != null) {
      row = filteredTransactionsMap.get(row);
    }
    
    PgTransaction transaction = transactions.get(row);
    return getValueAt(transaction, col);
  }

  protected void fillFilteredTransactionsMap() {
    filteredTransactionsMap = new HashMap<Integer, Integer>();
    int transactionsIndex = 0;
    int filteredTransactionsIndex = 0;
    
    for (PgTransaction transaction: transactions) {
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
