package goedegep.finan.stocks;

public interface ShareListener {
  public void SharesUpdated();

  public void ShareDividendAdded(Share share);
}