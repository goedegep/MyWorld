package goedegep.finan.postbank.pbeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.effrek.EffRekAfschrijving;

public class PbEffRekAfschrijving extends EffRekAfschrijving {

  public PbEffRekAfschrijving(PgAccount account) {
    super(account);
  }
  
  public String getDescription() {
    return "af  " + CF2.format(getTransactionAmount()) + " naar girorekening";
  }
}
