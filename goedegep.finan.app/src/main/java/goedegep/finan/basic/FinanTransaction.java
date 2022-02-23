package goedegep.finan.basic;


public class FinanTransaction {
  Bank          bank;          // the bank to which the transaction belongs
  PgTransaction transaction;   // the transaction

  public FinanTransaction(Bank bank, PgTransaction transaction) {
    this.bank = bank;
    this.transaction = transaction;
  }

  // TODO Moet waarschijnlijk een FinanBank worden, zodat hier ook direkt het icon van verkregen kan worden. 
  public Bank getBank() {
    return bank;
  }

  public PgTransaction getTransaction() {
    return transaction;
  }
}

