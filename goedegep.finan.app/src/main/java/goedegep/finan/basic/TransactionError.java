package goedegep.finan.basic;

public class TransactionError {
  private PgTransaction transaction;
  private String errorMessage;
  
  public PgTransaction getTransaction() {
    return transaction;
  }

  public void setTransaction(PgTransaction transaction) {
    this.transaction = transaction;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public TransactionError(PgTransaction transaction, String errorMessage) {
    super();
    this.transaction = transaction;
    this.errorMessage = errorMessage;
  }

  
}
