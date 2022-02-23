package goedegep.finan.postbank.pbeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.effrek.EffRekBijschrijving;

public class PbEffRekBijschrijving extends EffRekBijschrijving {

  public PbEffRekBijschrijving(PgAccount account) {
    super(account);
  }
  
  public String getDescription() {
    return "bij " + CF2.format(getTransactionAmount()) + " van girorekening";
  }
}
