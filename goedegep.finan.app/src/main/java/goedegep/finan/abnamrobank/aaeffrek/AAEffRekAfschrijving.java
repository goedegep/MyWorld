package goedegep.finan.abnamrobank.aaeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.effrek.EffRekAfschrijving;

public class AAEffRekAfschrijving extends EffRekAfschrijving {

  public AAEffRekAfschrijving(PgAccount account) {
    super(account);
  }

  public boolean isExecutionDateSupported() {
    return true;
  }
}