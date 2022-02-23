package goedegep.finan.stocks;

public interface FundListener {
  public void FundsUpdated();

  public void FundShareAdded(Fund fund);
}