package goedegep.finan.postbank.pbeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.effrek.EffRekClaimRightsTransaction;

public class PbEffRekClaimRightsTransaction extends EffRekClaimRightsTransaction {
  public PbEffRekClaimRightsTransaction(PgAccount account) {
    super(account);
  }
}