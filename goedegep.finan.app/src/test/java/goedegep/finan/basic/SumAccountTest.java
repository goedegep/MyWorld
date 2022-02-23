package goedegep.finan.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import goedegep.app.finan.gen.FinanBank;
import goedegep.util.text.TextWriter;

public class SumAccountTest {
  private static final String[] TRANSACTIONS_ACCOUNT_X = {
    "1", "2", "5", "7", "12"
  };

  private static final String[] TRANSACTIONS_ACCOUNT_Y = {
    "3", "8", "9", "10"
  };

  private static final String[] TRANSACTIONS_ACCOUNT_Z = {
    "4", "6", "11"
  };
  
  // Following data is initialized by initializeData()'.
  SumAccount sumAccount;
  Bank funnyBank;
  Bank solidBank;
  PgAccount accountX;
  PgAccount accountY;
  PgAccount accountZ;
  List<PgTransaction> transactionsAccountX;
  List<PgTransaction> transactionsAccountY;
  List<PgTransaction> transactionsAccountZ;
  List<FinanTransaction> transactions;

  
  @Test
  public void testBanks() {
    SumAccount sumAccount = new SumAccount();
    
    // New account shall have no banks.
    assertEquals("Verkeerd aantal banken", 0, sumAccount.getNrOfBanks());
    
    // A bank shall not be found.
    assertNull("Bank gevonden, terwijl dat niet kan", sumAccount.getBankForBankName("Bank"));
    
    Bank funnyBank = new FunnyBank();
    Bank solidBank = new SolidBank();
    
    sumAccount.addBank(new FinanBank(funnyBank, null, null));
    sumAccount.addBank(new FinanBank(solidBank, null, null));
    
    assertEquals("Verkeerd aantal banken", 2, sumAccount.getNrOfBanks());
    assertEquals("Verkeerde bank gevonden", solidBank.getName(), sumAccount.getBankForBankName(solidBank.getName()).getBank().getName());
    assertEquals("Verkeerde bank gevonden", funnyBank.getName(), sumAccount.getBankForBankName(funnyBank.getName()).getBank().getName());
  }
  
  /**
   * In this test the initially created transactions are checked.
   * 
   * Initial content of accountX:
   * comment
   *  1
   *  2
   *  5
   *  7
   * 12
   * 
   * Initial content of accountY:
   * comment
   *  3
   *  8
   *  9
   * 10
   * 
   * Initial content of accountY:
   * comment  account
   *  4
   *  6
   * 11
   * 
   * Initial content of sum account has to be:
   * comment  account
   *  1        X
   *  2        X
   *  3        Y
   *  4        Z
   *  5        X
   *  6        Z
   *  7        X
   *  8        Y
   *  9        Y
   * 10        Y
   * 11        Z
   * 12        X
   */
  @Test
  public void testAddingTransactions() {
    initializeData();
    
    // Check transactions.
    checkAccountTransactions(accountX, transactionsAccountX);
    checkAccountTransactions(accountY, transactionsAccountY);
    checkAccountTransactions(accountZ, transactionsAccountZ);
    checkSumAccountTransactions(sumAccount, transactions);
  }
  
  /**
   * In this test a transaction is added after a transaction for the
   * same account.
   * 
   * New transaction:
   * comment  account  bank
   * 13        X       funnyBank
   * 
   * Content of accountX:
   * Before:                          After:
   * comment                          Comment
   *  1                                 1
   *  2                                 2
   *  5                                 5
   *  7     => new after this           7
   * 12                                13
   *                                   12
   *                                   
   * Content of accountY (no change):
   * comment
   *  3
   *  8
   *  9
   * 10
   * 
   * Content of accountZ (no change):
   * comment  account
   *  4
   *  6
   * 11
   * 
   * Content of sum account:
   * Before:                          After:
   * comment  account                   comment  account
   *  1        X                         1        X
   *  2        X                         2        X
   *  3        Y                         3        Y
   *  4        Z                         4        Z
   *  5        X                         5        X
   *  6        Z                         6        Z
   *  7        X    => new after this    7        X
   *  8        Y                        13        X
   *  9        Y                         8        Y
   * 10        Y                         9        Y
   * 11        Z                        10        Y
   * 12        X                        11        Z
   *                                    12        X
   * As the transaction is added after a transaction for the same account,
   * it should appear directly after this transaction.
   */
  @Test
  public void testAddingTransactionAfterTransactionForSameAccount() {
    initializeData();
        
    // New transaction.
    PgTransaction newTransaction = new TransactionA(accountX, "13");
    FinanTransaction newFinanTransaction = new FinanTransaction(funnyBank, newTransaction);

    List<PgTransaction> transactionsAccountXAfterUpdate = new ArrayList<PgTransaction>();
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(0));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(1));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(2));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(3));
    transactionsAccountXAfterUpdate.add(newTransaction);
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(4));
    
    List<FinanTransaction> transactionsAfterUpdate = new ArrayList<FinanTransaction>();
    transactionsAfterUpdate.add(transactions.get(0));
    transactionsAfterUpdate.add(transactions.get(1));
    transactionsAfterUpdate.add(transactions.get(2));
    transactionsAfterUpdate.add(transactions.get(3));
    transactionsAfterUpdate.add(transactions.get(4));
    transactionsAfterUpdate.add(transactions.get(5));
    transactionsAfterUpdate.add(transactions.get(6));
    transactionsAfterUpdate.add(newFinanTransaction);
    transactionsAfterUpdate.add(transactions.get(7));
    transactionsAfterUpdate.add(transactions.get(8));
    transactionsAfterUpdate.add(transactions.get(9));
    transactionsAfterUpdate.add(transactions.get(10));
    transactionsAfterUpdate.add(transactions.get(11));
    
    // Insert and check
    sumAccount.addTransaction(newFinanTransaction, transactions.get(6), false);
    
    checkAccountTransactions(accountX, transactionsAccountXAfterUpdate);
    checkAccountTransactions(accountY, transactionsAccountY);
    checkAccountTransactions(accountZ, transactionsAccountZ);
    checkSumAccountTransactions(sumAccount, transactionsAfterUpdate);
  }
  
  
  /**
   * In this test a transaction is added before a transaction for the
   * same account.
   * 
   * New transaction:
   * comment  account  bank
   * 13        X       funnyBank
   * 
   * Content of accountX:
   * Before:                          After:
   * comment                          Comment
   *  1                                 1
   *  2                                 2
   *  5                                 5
   *  7     => new before this         13
   * 12                                 7
   *                                   12
   *                                   
   * Content of accountY (no change):
   * comment
   *  3
   *  8
   *  9
   * 10
   * 
   * Content of accountZ (no change):
   * comment  account
   *  4
   *  6
   * 11
   * 
   * Content of sum account:
   * Before:                          After:
   * comment  account                   comment  account
   *  1        X                         1        X
   *  2        X                         2        X
   *  3        Y                         3        Y
   *  4        Z                         4        Z
   *  5        X                         5        X
   *  6        Z                         6        Z
   *  7        X   => new before this   13        X
   *  8        Y                         7        X
   *  9        Y                         8        Y
   * 10        Y                         9        Y
   * 11        Z                        10        Y
   * 12        X                        11        Z
   *                                    12        X
   * As the transaction is added before a transaction for the same account,
   * it should appear directly before this transaction.
   */
  @Test
  public void testAddingTransactionBeforeTransactionForSameAccount() {
    initializeData();
        
    // New transaction.
    PgTransaction newTransaction = new TransactionA(accountX, "13");
    FinanTransaction newFinanTransaction = new FinanTransaction(funnyBank, newTransaction);

    List<PgTransaction> transactionsAccountXAfterUpdate = new ArrayList<PgTransaction>();
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(0));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(1));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(2));
    transactionsAccountXAfterUpdate.add(newTransaction);
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(3));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(4));
    
    List<FinanTransaction> transactionsAfterUpdate = new ArrayList<FinanTransaction>();
    transactionsAfterUpdate.add(transactions.get(0));
    transactionsAfterUpdate.add(transactions.get(1));
    transactionsAfterUpdate.add(transactions.get(2));
    transactionsAfterUpdate.add(transactions.get(3));
    transactionsAfterUpdate.add(transactions.get(4));
    transactionsAfterUpdate.add(transactions.get(5));
    transactionsAfterUpdate.add(newFinanTransaction);
    transactionsAfterUpdate.add(transactions.get(6));
    transactionsAfterUpdate.add(transactions.get(7));
    transactionsAfterUpdate.add(transactions.get(8));
    transactionsAfterUpdate.add(transactions.get(9));
    transactionsAfterUpdate.add(transactions.get(10));
    transactionsAfterUpdate.add(transactions.get(11));
    
    // Insert and check
    sumAccount.addTransaction(newFinanTransaction, transactions.get(6), true);
    
    checkAccountTransactions(accountX, transactionsAccountXAfterUpdate);
    checkAccountTransactions(accountY, transactionsAccountY);
    checkAccountTransactions(accountZ, transactionsAccountZ);    
    checkSumAccountTransactions(sumAccount, transactionsAfterUpdate);
  }
  
  /**
   * In this test a transaction is added after a transaction for a
   * different account.
   * 
   * New transaction:
   * comment  account  bank
   * 13        X       funnyBank
   * 
   * Content of accountX:
   * Before:                          After:
   * comment                          Comment
   *  1                                 1
   *  2                                 2
   *  5     => new before this         13
   *  7                                 5
   * 12                                 7
   *                                   12
   *                                   
   * Content of accountY (no change):
   * comment
   *  3
   *  8
   *  9
   * 10
   * 
   * Content of accountZ (no change):
   * comment  account
   *  4
   *  6
   * 11
   * 
   * Content of sum account:
   * Before:                                After:
   * comment  account                       comment  account
   *  1        X                             1        X
   *  2        X                             2        X
   *  3        Y   => new after this         3        Y
   *  4        Z                            13        X
   *  5        X   before this on accountX   4        Z
   *  6        Z                             5        X
   *  7        X                             6        Z
   *  8        Y                             7        X
   *  9        Y                             8        Y
   * 10        Y                             9        Y
   * 11        Z                            10        Y
   * 12        X                            11        Z
   *                                        12        X
   * As the transaction is added after a transaction for a different account,
   * first a transaction is searched forwards for accountX ("5") and the new
   * transaction is inserted before this one.
   */
  @Test
  public void testAddingTransactionAfterTransactionForOtherAccount() {
    initializeData();
        
    // New transaction.
    PgTransaction newTransaction = new TransactionA(accountX, "13");
    FinanTransaction newFinanTransaction = new FinanTransaction(funnyBank, newTransaction);

    List<PgTransaction> transactionsAccountXAfterUpdate = new ArrayList<PgTransaction>();
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(0));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(1));
    transactionsAccountXAfterUpdate.add(newTransaction);
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(2));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(3));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(4));
    
    List<FinanTransaction> transactionsAfterUpdate = new ArrayList<FinanTransaction>();
    transactionsAfterUpdate.add(transactions.get(0));
    transactionsAfterUpdate.add(transactions.get(1));
    transactionsAfterUpdate.add(transactions.get(2));
    transactionsAfterUpdate.add(newFinanTransaction);
    transactionsAfterUpdate.add(transactions.get(3));
    transactionsAfterUpdate.add(transactions.get(4));
    transactionsAfterUpdate.add(transactions.get(5));
    transactionsAfterUpdate.add(transactions.get(6));
    transactionsAfterUpdate.add(transactions.get(7));
    transactionsAfterUpdate.add(transactions.get(8));
    transactionsAfterUpdate.add(transactions.get(9));
    transactionsAfterUpdate.add(transactions.get(10));
    transactionsAfterUpdate.add(transactions.get(11));
    
    // Insert and check
    sumAccount.addTransaction(newFinanTransaction, transactions.get(2), false);
    
    checkAccountTransactions(accountX, transactionsAccountXAfterUpdate);
    checkAccountTransactions(accountY, transactionsAccountY);
    checkAccountTransactions(accountZ, transactionsAccountZ);
    checkSumAccountTransactions(sumAccount, transactionsAfterUpdate);
  }
  
  /**
   * In this test a transaction is added before a transaction for a
   * different account.
   * 
   * New transaction:
   * comment  account  bank
   * 13        X       funnyBank
   * 
   * Content of accountX:
   * Before:                          After:
   * comment                          Comment
   *  1                                 1
   *  2     => new after this           2
   *  5                                13
   *  7                                 5
   * 12                                 7
   *                                   12
   *                                   
   * Content of accountY (no change):
   * comment
   *  3
   *  8
   *  9
   * 10
   * 
   * Content of accountY (no change):
   * comment  account
   *  4
   *  6
   * 11
   * 
   * Content of sum account:
   * Before:                                After:
   * comment  account                       comment  account
   *  1        X                             1        X
   *  2        X   after this on accountX    2        X
   *  3        Y                             3        Y
   *  4        Z   => new before this       13        X
   *  5        X                             4        Z
   *  6        Z                             5        X
   *  7        X                             6        Z
   *  8        Y                             7        X
   *  9        Y                             8        Y
   * 10        Y                             9        Y
   * 11        Z                            10        Y
   * 12        X                            11        Z
   *                                        12        X
   * As the transaction is added before a transaction for a different account,
   * first a transaction is searched backwards for accountX ("2") and the new
   * transaction is inserted after this one.
   */
  @Test
  public void testAddingTransactionBeforeTransactionForOtherAccount() {
    initializeData();
        
    // New transaction.
    PgTransaction newTransaction = new TransactionA(accountX, "13");
    FinanTransaction newFinanTransaction = new FinanTransaction(funnyBank, newTransaction);

    List<PgTransaction> transactionsAccountXAfterUpdate = new ArrayList<PgTransaction>();
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(0));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(1));
    transactionsAccountXAfterUpdate.add(newTransaction);
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(2));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(3));
    transactionsAccountXAfterUpdate.add(transactionsAccountX.get(4));
    
    List<FinanTransaction> transactionsAfterUpdate = new ArrayList<FinanTransaction>();
    transactionsAfterUpdate.add(transactions.get(0));
    transactionsAfterUpdate.add(transactions.get(1));
    transactionsAfterUpdate.add(transactions.get(2));
    transactionsAfterUpdate.add(newFinanTransaction);
    transactionsAfterUpdate.add(transactions.get(3));
    transactionsAfterUpdate.add(transactions.get(4));
    transactionsAfterUpdate.add(transactions.get(5));
    transactionsAfterUpdate.add(transactions.get(6));
    transactionsAfterUpdate.add(transactions.get(7));
    transactionsAfterUpdate.add(transactions.get(8));
    transactionsAfterUpdate.add(transactions.get(9));
    transactionsAfterUpdate.add(transactions.get(10));
    transactionsAfterUpdate.add(transactions.get(11));
    
    // Insert and check
    sumAccount.addTransaction(newFinanTransaction, transactions.get(3), true);
    
    checkAccountTransactions(accountX, transactionsAccountXAfterUpdate);
    checkAccountTransactions(accountY, transactionsAccountY);
    checkAccountTransactions(accountZ, transactionsAccountZ);
    checkSumAccountTransactions(sumAccount, transactionsAfterUpdate);
  }
            
  
  /**
   * In this test a transaction is added after a transaction for a
   * different account. 
   * 
   * New transaction:
   * comment  account  bank
   * 13        Y       funnyBank
   * 
   * Content of accountX (no change):
   * comment
   *  1
   *  2
   *  5
   *  7
   * 12
   *                                   
   * Content of accountY:
   * Before:                          After:
   * comment                          Comment
   *  3                                 3
   *  8                                 8
   *  9                                 9
   * 10     => new before last         10
   *                                   13
   * 
   * Content of accountZ (no change):
   * comment  account
   *  4
   *  6
   * 11
   * 
   * Content of sum account:
   * Before:                                After:
   * comment  account                       comment  account
   *  1        X                             1        X
   *  2        X                             2        X
   *  3        Y                             3        Y
   *  4        Z                             4        Z
   *  5        X                             5        X
   *  6        Z                             6        Z
   *  7        X                             7        X
   *  8        Y                             8        Y
   *  9        Y                             9        Y
   * 10        Y   after last on accountY   10        Y
   * 11        Z   => new after this        11        Z
   * 12        X                            13        Y
   *                                        12        X
   * As the transaction is added after a transaction for a different account,
   * first a transaction is searched forwards for accountY. In this case
   * this doesn't exist, so the transaction is added at the end.
   */
  @Test
  public void testAddingTransactionAfterTransactionForOtherAccount2() {
    initializeData();
        
    // New transaction.
    PgTransaction newTransaction = new TransactionA(accountY, "13");
    FinanTransaction newFinanTransaction = new FinanTransaction(funnyBank, newTransaction);

    List<PgTransaction> transactionsAccountYAfterUpdate = new ArrayList<PgTransaction>();
    transactionsAccountYAfterUpdate.add(transactionsAccountY.get(0));
    transactionsAccountYAfterUpdate.add(transactionsAccountY.get(1));
    transactionsAccountYAfterUpdate.add(transactionsAccountY.get(2));
    transactionsAccountYAfterUpdate.add(transactionsAccountY.get(3));
    transactionsAccountYAfterUpdate.add(newTransaction);
    
    List<FinanTransaction> transactionsAfterUpdate = new ArrayList<FinanTransaction>();
    transactionsAfterUpdate.add(transactions.get(0));
    transactionsAfterUpdate.add(transactions.get(1));
    transactionsAfterUpdate.add(transactions.get(2));
    transactionsAfterUpdate.add(transactions.get(3));
    transactionsAfterUpdate.add(transactions.get(4));
    transactionsAfterUpdate.add(transactions.get(5));
    transactionsAfterUpdate.add(transactions.get(6));
    transactionsAfterUpdate.add(transactions.get(7));
    transactionsAfterUpdate.add(transactions.get(8));
    transactionsAfterUpdate.add(transactions.get(9));
    transactionsAfterUpdate.add(transactions.get(10));
    transactionsAfterUpdate.add(newFinanTransaction);
    transactionsAfterUpdate.add(transactions.get(11));
    
    // Insert and check
    sumAccount.addTransaction(newFinanTransaction, transactions.get(10), false);
    
    checkAccountTransactions(accountX, transactionsAccountX);
    checkAccountTransactions(accountY, transactionsAccountYAfterUpdate);
    checkAccountTransactions(accountZ, transactionsAccountZ);
    checkSumAccountTransactions(sumAccount, transactionsAfterUpdate);
  }
  
  /**
   * In this test a transaction is added before a transaction for a
   * different account. In this case it has to be inserted at the beginning
   * of the account.
   * 
   * New transaction:
   * comment  account  bank
   * 13        Z       funnyBank
   * 
   * Content of accountX (no change):
   * comment
   *  1
   *  2
   *  5
   *  7
   * 12
   *                                   
   * Content of accountY (no change):
   * comment
   *  3
   *  8
   *  9
   * 10
   * 
   * Content of accountZ (no change):
   * Before:                          After:
   * comment                          Comment
   *  4     => new before this         13
   *  6                                 4
   * 11                                 6
   *                                   11
   *                                   
   * Content of sum account:
   * Before:                                After:
   * comment  account                       comment  account
   *  1        X                             1        X
   *  2        X                             2        X
   *  3        Y   => new before this       13        Z
   *  4        Z   before first on accountZ  3        Y
   *  5        X                             4        Z
   *  6        Z                             5        X
   *  7        X                             6        Z
   *  8        Y                             7        X
   *  9        Y                             8        Y
   * 10        Y                             9        Y
   * 11        Z                            10        Y
   * 12        X                            11        Z
   *                                        12        X
   * As the transaction is added before a transaction for a different account,
   * first a transaction if found backwards for accountX ("5") and the new
   * transaction is inserted after this one.
   */
  @Test
  public void testAddingTransactionBeforeTransactionForOtherAccount2() {
    initializeData();
        
    // New transaction.
    PgTransaction newTransaction = new TransactionA(accountZ, "13");
    FinanTransaction newFinanTransaction = new FinanTransaction(funnyBank, newTransaction);

    List<PgTransaction> transactionsAccountZAfterUpdate = new ArrayList<PgTransaction>();
    transactionsAccountZAfterUpdate.add(newTransaction);
    transactionsAccountZAfterUpdate.add(transactionsAccountZ.get(0));
    transactionsAccountZAfterUpdate.add(transactionsAccountZ.get(1));
    transactionsAccountZAfterUpdate.add(transactionsAccountZ.get(2));
    
    List<FinanTransaction> transactionsAfterUpdate = new ArrayList<FinanTransaction>();
    transactionsAfterUpdate.add(transactions.get(0));
    transactionsAfterUpdate.add(transactions.get(1));
    transactionsAfterUpdate.add(newFinanTransaction);
    transactionsAfterUpdate.add(transactions.get(2));
    transactionsAfterUpdate.add(transactions.get(3));
    transactionsAfterUpdate.add(transactions.get(4));
    transactionsAfterUpdate.add(transactions.get(5));
    transactionsAfterUpdate.add(transactions.get(6));
    transactionsAfterUpdate.add(transactions.get(7));
    transactionsAfterUpdate.add(transactions.get(8));
    transactionsAfterUpdate.add(transactions.get(9));
    transactionsAfterUpdate.add(transactions.get(10));
    transactionsAfterUpdate.add(transactions.get(11));
    
    // Insert and check
    sumAccount.addTransaction(newFinanTransaction, transactions.get(2), true);
    
    checkAccountTransactions(accountX, transactionsAccountX);
    checkAccountTransactions(accountY, transactionsAccountY);
    checkAccountTransactions(accountZ, transactionsAccountZAfterUpdate);
    checkSumAccountTransactions(sumAccount, transactionsAfterUpdate);
  }
            
  private void initializeData() {
    sumAccount = new SumAccount();
 
    funnyBank = new FunnyBank();
    solidBank = new SolidBank();

    accountX = new AccountX();
    accountY = new AccountY();
    accountZ = new AccountZ();
    
    transactionsAccountX = new ArrayList<PgTransaction>();
    for (String comment: TRANSACTIONS_ACCOUNT_X) {
      transactionsAccountX.add(new TransactionB(accountX, comment));
    }
    
    transactionsAccountY = new ArrayList<PgTransaction>();
    for (String comment: TRANSACTIONS_ACCOUNT_Y) {
      transactionsAccountY.add(new TransactionB(accountY, comment));
    }
    
    transactionsAccountZ = new ArrayList<PgTransaction>();
    for (String comment: TRANSACTIONS_ACCOUNT_Z) {
      transactionsAccountZ.add(new TransactionB(accountZ, comment));
    }
    
    transactions = new ArrayList<FinanTransaction>();
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountX.get(0)));
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountX.get(1)));
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountY.get(0)));
    transactions.add(new FinanTransaction(solidBank, transactionsAccountZ.get(0)));
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountX.get(2)));
    transactions.add(new FinanTransaction(solidBank, transactionsAccountZ.get(1)));
    transactions.add(new FinanTransaction(solidBank, transactionsAccountX.get(3)));
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountY.get(1)));
    transactions.add(new FinanTransaction(solidBank, transactionsAccountY.get(2)));
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountY.get(3)));
    transactions.add(new FinanTransaction(funnyBank, transactionsAccountZ.get(2)));
    transactions.add(new FinanTransaction(solidBank, transactionsAccountX.get(4)));

    // Add initial list of transactions and check.
    for (FinanTransaction transaction: transactions) {
      sumAccount.addTransaction(transaction);
    }
  }
  
  private void checkSumAccountTransactions(SumAccount sumAccount, List<FinanTransaction> transactions) {
    int i = 0;
    for (FinanTransaction transaction: sumAccount.getTransactions()) {
      assertEquals("Verkeerd commentaar", transactions.get(i).getTransaction().getComment(), transaction.getTransaction().getComment());
      assertEquals("Verkeerd account", transactions.get(i).getTransaction().getAccount().getName(), transaction.getTransaction().getAccount().getName());
      i++;
    }
  }
  
  private void checkAccountTransactions(PgAccount account, List<PgTransaction> transactions) {
    int i = 0;
    for (PgTransaction transaction: account.getTransactions()) {
      assertEquals("Verkeerd commentaar", transactions.get(i).getComment(), transaction.getComment());
      assertEquals("Verkeerd account", transactions.get(i).getAccount().getName(), transaction.getAccount().getName());
      i++;
    }
  }
  
  private class FunnyBank extends Bank {
    FunnyBank() {
      setName("FunnyBank");
    }
    
    @Override
    public void dumpData(TextWriter textWriter) throws IOException {
    }

    public List<PgAccount> getAccounts() {
      return null;
    }

    public PgAccount openAccount(String accountName) {
      return null;
    }
  }
  
  private class SolidBank extends Bank {
    SolidBank() {
      setName("SolidBank");
    }
    
    @Override
    public void dumpData(TextWriter textWriter) throws IOException {
    }

    public List<PgAccount> getAccounts() {
      return null;
    }

    public PgAccount openAccount(String accountName) {
      return null;
    }
  }
  
  private class AccountX extends PgAccount {
    AccountX() {
      super(false, false);
      setName("X");
    }
    
    public String toString(PgTransaction transaction) {
      return null;
    }

    @Override
    public void dumpData(TextWriter out) throws IOException {
    }
  }
  
  private class AccountY extends PgAccount {
    AccountY() {
      super(false, false);
      setName("Y");
    }
    
    public String toString(PgTransaction transaction) {
      return null;
    }

    @Override
    public void dumpData(TextWriter out) throws IOException {
    }
  }
  
  private class AccountZ extends PgAccount {
    AccountZ() {
      super(false, false);
      setName("Z");
    }
    
    public String toString(PgTransaction transaction) {
      return null;
    }

    @Override
    public void dumpData(TextWriter out) throws IOException {
    }
  }
  
  private class TransactionA extends PgTransaction {
    TransactionA(PgAccount account, String comment) {
      super(account);
      setComment(comment);
    }
    
    public String getDescription() {
      return null;
    }

    public void handle(List<TransactionError> errors) {
    }

     public short getTransactionType() {
      return 0;
    }
  }
  
  private class TransactionB extends PgTransaction {
    TransactionB(PgAccount account, String comment) {
      super(account);
      setComment(comment);
    }
    
    public String getDescription() {
      return null;
    }

    public void handle(List<TransactionError> errors) {
    }

    public short getTransactionType() {
     return 1;
   }
  }
}
