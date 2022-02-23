package goedegep.finan.basic;

/**
 * <p>@author Peter Goedegebure</p>
 *
 * <p>@version 0.1</p>
 *
 * <p> </p>
 *
 * <p> </p> not attributable
 */
public interface SumAccountListener {
  public void transactionAdded(FinanTransaction transaction);
  public void transactionRemoved(FinanTransaction transaction);
}
