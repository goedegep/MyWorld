package goedegep.finan.abnamrobank.aaeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.effrek.EffRekBijschrijving;

public class AAEffRekBijschrijving extends EffRekBijschrijving {

  public AAEffRekBijschrijving(PgAccount account) {
    super(account);
  }

  public boolean isExecutionDateSupported() {
    return false;
  }
}