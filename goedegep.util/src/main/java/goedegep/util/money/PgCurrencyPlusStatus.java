package goedegep.util.money;


/**
 * This class consists of money (a {@link PgCurrency}) combined with its {@link Status}.
 */
public class PgCurrencyPlusStatus implements Comparable<PgCurrencyPlusStatus> {
  private final static PgCurrencyFormat CF = new PgCurrencyFormat();
  
  /**
   * This enum provides the values for the {@link status}.
   * The constants are defined such that a higher value indicates a higher reliability.
   */
  public enum Status {
    /**
     * The value is not available, or incorrect.
     */
    ERROR,
    /**
     * The value is available but not reliable (typically a value to be checked).
     */
    SUSPICIOUS,
    /**
     * The value is available and reliable.
     */
    OK
  }
  
  
  /**
   * The Money value, which may be null
   */
  private PgCurrency money;
  
  /**
   * The status provides information on the reliability of the <code>money</code> value.
   */
  private Status status;

  /**
   * Constructor where only the money value is specified; the status is set to OK.
   * 
   * @param money the value.
   */
  public PgCurrencyPlusStatus(PgCurrency money) {
    this(money, Status.OK);
  }

  /**
   * Constructor where both the money value and status are specified.
   * 
   * @param money the value.
   * @param status the reliability of the value.
   */
  public PgCurrencyPlusStatus(PgCurrency money, Status status) {
    this.money = money;
    this.status = status;
  }

  /**
   * Get the money value.
   * 
   * @return the money value.
   */
  public PgCurrency getMoney() {
    return money;
  }

  /**
   * Set the money value.
   * 
   * @param money the new money value.
   */
  public void setMoney(PgCurrency money) {
    this.money = money;
  }
  
  /**
   * Get the status of the money value.
   * 
   * @return the status of the money value.
   */
  public Status getStatus() {
    return status;
  }
  
  /**
   * Set the status of the money value.
   * 
   * @param status the new status of the money value.
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Sorting is first on value and then on status.
   */
  public int compareTo(PgCurrencyPlusStatus o) {
    if (money != null) {
      if (o.money != null) {
        int moneyCompareResult = money.compareTo(o.money);
        if (moneyCompareResult == 0) {
          return compareStatus(o);
        } else {
          return moneyCompareResult;
        }
      } else {
        return 1;
      }
    } else {
      if (o.money != null) {
        return -1;
      } else {
        return compareStatus(o);
      }
    }
  }
  
  /**
   * Compare this objects status to another's status.
   * 
   * @param moneyPlusStatus te object to compare to.
   * @return a negative integer, zero, or a positive integer as this object's status is
   *         less than, equal to, or higher than the specified object's status.
   */
  public int compareStatus(PgCurrencyPlusStatus moneyPlusStatus) {
    return status.compareTo(moneyPlusStatus.status);
   }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    if (money != null) {
      buf.append(CF.format(money));
    }
    
    buf.append(" (");
    buf.append(status.toString());
    buf.append(")");
    
    return buf.toString();
  }
}
