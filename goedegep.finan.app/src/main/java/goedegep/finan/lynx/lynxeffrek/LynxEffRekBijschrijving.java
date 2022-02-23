package goedegep.finan.lynx.lynxeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.effrek.EffRekBijschrijving;

public class LynxEffRekBijschrijving extends EffRekBijschrijving {
  
  public LynxEffRekBijschrijving(PgAccount account) {
    super(account);
  }

  public boolean isExecutionDateSupported() {
    return false;
  }
  
  public boolean isSameTransaction(PgTransaction transaction) {
    return false;
  }
}