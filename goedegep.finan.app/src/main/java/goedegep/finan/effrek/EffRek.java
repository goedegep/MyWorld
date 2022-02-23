package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.money.PgCurrency;

public abstract class EffRek extends PgAccount {
  private StockDepot verzamelDepot; // Verzameldepot
  
  public EffRek(StockDepot parentDepot, String name, int currency) {
    super(true, currency, 0L, true);

    setName(name);
     verzamelDepot = new StockDepot(parentDepot);
  }
  
  public StockDepot getVerzamelDepot() {
    return verzamelDepot;
  }

  public void clear() {
    super.clear();
    if (verzamelDepot != null) {
      verzamelDepot.clear();
    }
  }
  
  public PgCurrency getEstimatedValue() {
    return getVerzamelDepot().getValue();
  }
}
