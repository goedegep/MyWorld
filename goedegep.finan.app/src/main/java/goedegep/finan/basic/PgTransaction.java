package goedegep.finan.basic;

import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import java.util.List;

public abstract class PgTransaction {
  private LocalDate  bookingDate = null;       // boekingsdatum (rentedatum)
  private LocalDate  executionDate = null;     // Uitvoeringsdatum
  private PgCurrency transactionAmount = null; // bedrag
  private PgAccount  account = null;           // rekening waarop de transactie van toepassing is
  private String     comment = null;           // Commentaar.
  private boolean    handled = false;          // true als de transactie verwerkt is
  private PgCurrency nieuwTegoed = null;       // only valid if transaction is handled
  
  List<TransactionError> e;

  public PgTransaction() {
  }

  public PgTransaction(PgAccount account) {
    this(account, null, null, null);
  }

  public PgTransaction(PgAccount account, LocalDate bookingDate) {
    this(account, null, bookingDate, null);
  }

  public PgTransaction(PgAccount account, LocalDate bookingDate, PgCurrency transactionAmount) {
    this(account, null, bookingDate, transactionAmount);
  }

  public PgTransaction(PgAccount account, LocalDate executionDate, LocalDate bookingDate, PgCurrency transactionAmount) {
    this.account = account;
    this.executionDate = executionDate;
    this.bookingDate = bookingDate;
    this.transactionAmount = transactionAmount;
  }

  /**
   * The transaction type shall be a unique per account.
   * @return A type id, which is unique per account.
   */
  public abstract short getTransactionType();

  public LocalDate getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(LocalDate bookingDate) {
    this.bookingDate = bookingDate;
  }

  public LocalDate getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(LocalDate executionDate) {
    this.executionDate = executionDate;
  }

  public boolean isExecutionDateSupported() {
    return true;
  }

  public PgCurrency getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(PgCurrency transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public PgAccount getAccount() {
    return account;
  }

  public void setAccount(PgAccount account) {
    this.account = account;
  }

  public abstract String getDescription();

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public PgCurrency getNieuwTegoed() {
    return nieuwTegoed;
  }

  public void setNieuwTegoed(PgCurrency nieuwTegoed) {
    this.nieuwTegoed = nieuwTegoed;
  }

  public boolean isValid() {
    return true;      
  }

  public abstract void handle(List<TransactionError> errors);

  public boolean isHandled() {
    return handled;
  }

  public void setHandled(boolean handled) {
    this.handled = handled;
  }
  
  public boolean isSameTransaction(PgTransaction transaction) {
    throw new UnsupportedOperationException("isSameTransaction not implemented for class: " + getClass().getName());
  }

}