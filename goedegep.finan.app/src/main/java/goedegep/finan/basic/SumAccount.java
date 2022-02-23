package goedegep.finan.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.app.finan.gen.FinanBank;

/**
 * Sum account. This is a special account to:
 * - hold all transactions of all accounts (to make it possible to have all
 *   transactions in the right order).
 * - have a list of all banks.
 * TODO Move to goedegep.app.finan.gen (as it uses FinanBank now).
 */
public class SumAccount {
  private static final Logger         LOGGER = Logger.getLogger(SumAccount.class.getName());
  
  
  private List<FinanBank>               banks             = new LinkedList<FinanBank>();             // alle banken waar rekeningen bij zijn.
  private LinkedList<FinanTransaction>  finanTransactions = new LinkedList<FinanTransaction>(); // alle transacties

  /**
   * Listeners to changes on this sum account.
   */
  private ArrayList<SumAccountListener> accountListeners = new ArrayList<SumAccountListener>();

  public SumAccount() {
  }

  public int getNrOfBanks() {
    return banks.size();
  }
  
  public List<FinanBank> getBanks() {
    return banks;
  }

  public void addBank(FinanBank bank) {
    banks.add(bank);
  }

  public FinanBank getBankForBankName(String bankName) {
    for (FinanBank finanBank: banks) {
      if (finanBank.getBank().getName().equals(bankName)) {
        return finanBank;
      }
    }

    return null;
  }
  
  public void addTransaction(FinanTransaction transaction) {
    finanTransactions.addLast(transaction);
    PgTransaction t = transaction.getTransaction();
    t.getAccount().addTransaction(t);
    notifyListenersOnTransactionAdded(transaction);
    //System.out.println("Transactie nr. " + numberOfTransactions() + " toegevoegd.");
  }

  /**
   * Add a transaction before or after a specific other transaction.
   * The transaction is added to this SumAccount and to the account to which the
   * transaction belongs.
   * 1 a
   * 2 a
   * 3 b
   * 4 a
   * 5 c
   * 6 c
   * 7 c
   * 8 b
   * 9 a
   * 
   * @param transaction the transaction to be added.
   * @param insertLocation the transaction before or after which the new transaction is to be added.
   * @param before if true, the transaction is added before the insertLocation, else it is added after it.
   */
  public void addTransaction(FinanTransaction transaction, FinanTransaction insertLocation, boolean before) {
    // Add to the account first. If you first add to the sum account,
    // then when adding to the account has to find a suitable location,
    // it may find the new transaction itself.
    addTransactionToAccount(transaction, insertLocation, before);
    addTransactionToSummAccount(transaction, insertLocation, before);

    notifyListenersOnTransactionAdded(transaction);
  }
  
  /**
   * Add a transaction to this SumAccount, before or after a specific other transaction.
   * 
   * @param transaction the transaction to be added.
   * @param insertLocation the transaction before or after which the new transaction is to be added.
   * @param before if true, the transaction is added before the insertLocation, else it is added after it.
   */
  private void addTransactionToSummAccount(FinanTransaction transaction, FinanTransaction insertLocation, boolean before) {
    int insertIndex = finanTransactions.indexOf(insertLocation);
    if (insertIndex == -1) {
      throw(new IllegalArgumentException());
    }

    if (before) {
      // insert before the specified location.
      finanTransactions.add(insertIndex, transaction);
    } else {
      // there is no 'insert after', so try to insert before the next transaction.
      insertIndex++;
      if (insertIndex >= finanTransactions.size()) {
        // there is no next, so append to the end.
        finanTransactions.addLast(transaction);
      } else {
        finanTransactions.add(insertIndex, transaction);
      }
    }
  }
  
  /**
   * Add a transaction to the account to which the transaction belongs, before or after a specific other transaction.
   * This specific other transaction can be any transaction on the Sum Account, which is most often a transaction
   * on another account than the account of the transaction to be added. This means that for the account on which the
   * transaction is to be added, first the correct insert location has to be determined.
   * Suppose we have to following list of transactions (where the letter indicates an account name).
   * 1 a
   * 2 a
   * 3 b
   * 4 a
   * 5 c
   * 6 c
   * 7 c
   * 8 b
   * 9 a
   * 
   * If the transaction is for account c, and the insert location is after 5, it can just be inserted after 5.
   * If the transaction is for account c, and the insert location is after 2, it has to be inserted before 5.
   * If the transaction is for account c, and the insert location is after 8, it has to be inserted after 7.
   * 
   * So the following algorithm has to be used:
   * If the insert location is for the right account, perform a regular insert.
   * Else
   *     If 'before' is specified, find a transaction for the right account backwards. If this is found, insert after it,
   *     else insert at the beginning of the list.
   *     If 'after' is specified, find a transaction for the right account forwards. If this is found, insert before it,
   *     else append to the end of the list.
   *     
   * @param finanTransaction the transaction to be added.
   * @param insertLocation the transaction before or after which the new transaction is to be added.
   * @param before if true, the transaction is added before the insertLocation, else it is added after it.
   */
  private void addTransactionToAccount(FinanTransaction finanTransaction, FinanTransaction insertLocation, boolean before) {
    PgTransaction transaction = finanTransaction.getTransaction();
    PgAccount account = transaction.getAccount();
    PgTransaction accountInsertLocation = insertLocation.getTransaction();
    
    if (accountInsertLocation.getAccount().equals(account)) {
      account.addTransaction(transaction, accountInsertLocation, before);
    } else {
      if (before) {
        insertLocation = getFirstNextTransactionForAccountBackwards(insertLocation, account);
        if (insertLocation != null) {
          accountInsertLocation = insertLocation.getTransaction();
          account.addTransaction(transaction, accountInsertLocation, false);
        } else {
          account.addFirstTransaction(transaction);
        }
      } else {
        insertLocation = getFirstNextTransactionForAccountForwards(insertLocation, account);
        if (insertLocation != null) {
          accountInsertLocation = insertLocation.getTransaction();
          account.addTransaction(transaction, accountInsertLocation, true);
        } else {
          account.addTransaction(transaction);
        }
      }
    }
  }

  // TODO handle move in a central (this) location.
  public void moveTransaction(FinanTransaction transaction, boolean up) {
    boolean  before;
    int      index = 0;
    index = finanTransactions.indexOf(transaction);
    System.out.println("SumAccount:moveTransaction: index = " + index);

    finanTransactions.remove(index);
    if (up) {
      index--;
      before = true;
    } else {
      before = false;
    }
    FinanTransaction insertLocation = (FinanTransaction) finanTransactions.get(index);
    addTransaction(transaction, insertLocation, before);
  }

  // TODO handle remove in a central (this) location.
  public void removeTransaction(int index) {
    FinanTransaction finanTransaction = finanTransactions.get(index);
    finanTransactions.remove(index);
    notifyListenersOnTransactionRemoved(finanTransaction);
  }
  
  public void transactionUpdated(FinanTransaction finanTransaction) {
    notifyListenersOnTransactionRemoved(finanTransaction);
  }
  
  public int numberOfTransactions() {
    return finanTransactions.size();
  }

  public FinanTransaction getTransaction(int index) {
    return finanTransactions.get(index);
  }
  
  public int getTransactionIndex(FinanTransaction finanTransaction) {
    return finanTransactions.indexOf(finanTransaction);
  }
  
  public List<FinanTransaction> getTransactions() {
    return finanTransactions;
  }
  
  public List<TransactionError> handleTransactions() {
    List<TransactionError> errors = new ArrayList<>();
    
    for (FinanTransaction finanTransaction: finanTransactions) {
      LOGGER.fine("Going to handle transaction: (type=" + finanTransaction.getTransaction().getClass().getName() + ") " + finanTransaction.getTransaction().toString());
      finanTransaction.getTransaction().handle(errors);
    }
    
    return errors;
  }
  
  public void unhandleTransactions() {
    for (FinanTransaction finanTransaction: finanTransactions) {
      finanTransaction.getTransaction().setHandled(false);
    }
  }

  public FinanTransaction getFirstNextTransactionForAccountBackwards(FinanTransaction startTransaction, PgAccount account) {    
//    int index = finanTransactions.indexOf(startTransaction);
    
    for (int index = finanTransactions.indexOf(startTransaction); index >= 0; index--) {
      FinanTransaction finanTransaction = finanTransactions.get(index);
      if (finanTransaction.getTransaction().getAccount().equals(account)) {
//      System.out.println("Account found: " + finanTransaction.getTransaction().toString());
        return finanTransaction;
      } else {
//      System.out.println("Searching: " + finanTransaction.getTransaction().toString());
      }
    }

    return null;
  }

  public FinanTransaction getFirstNextTransactionForAccountForwards(FinanTransaction startTransaction, PgAccount account) {    
    int index = finanTransactions.indexOf(startTransaction);
    Iterator<FinanTransaction> it = finanTransactions.listIterator(index);
    
    while (it.hasNext()) {
      FinanTransaction finanTransaction = it.next();

      if (finanTransaction.getTransaction().getAccount().equals(account)) {
//      System.out.println("Account found: " + t.getTransaction().toString());
        return finanTransaction;
      } else {
//      System.out.println("Searching: " + t.getTransaction().toString());
      }
    }

    return null;
  }

  public void addAccountListener(SumAccountListener listener) {
    accountListeners.add(listener);
  }

  public void removeAccountListener(SumAccountListener listener) {
    accountListeners.remove(listener);
  }

  private void notifyListenersOnTransactionAdded(FinanTransaction transaction) {
    Iterator<SumAccountListener> it = accountListeners.iterator();
    while (it.hasNext()) {
      SumAccountListener listener = it.next();
      listener.transactionAdded(transaction);
    }
  }

  private void notifyListenersOnTransactionRemoved(FinanTransaction transaction) {
    Iterator<SumAccountListener> it = accountListeners.iterator();
    while (it.hasNext()) {
      SumAccountListener listener = it.next();
      listener.transactionRemoved(transaction);
    }
  }
}
