package goedegep.finan.basic;

public interface TransactionFilter {
  /**
   * Transaction filter.
   * @param transaction
   * @return true if the transaction passed the filter, false otherwise.
   */
  public boolean Filter(PgTransaction transaction);
}
