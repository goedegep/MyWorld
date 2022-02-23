package goedegep.finan.basic;

import java.util.LinkedList;


public class FinanAccount {
  LinkedList<FinanTransaction>      _transactions = new LinkedList<FinanTransaction>();     // alle transacties

  public FinanAccount() {
    //super(false);
  }

  public void addTransaction(FinanTransaction transaction) {
    _transactions.addLast(transaction);
  }

  public int numberOfTransactions() {
    return _transactions.size();
  }

  public FinanTransaction getTransaction(int index) {
    return (FinanTransaction) _transactions.get(index);
  }

}
