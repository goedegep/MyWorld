package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.ClaimEmission;

public abstract class EffRekClaimRightsReceived extends EffRekTransactie {
  private ClaimEmission   claimEmission;
  private int             numberOfRights = -1;  // -1 indicates invalid value.

  public EffRekClaimRightsReceived(PgAccount  account) {
    super(account);
  }

  public short getTransactionType() {
    return TT_CLAIM_RIGHTS_RECEIVED;
  }

  public ClaimEmission getClaimEmission() {
    return claimEmission;
  }

  public void setClaimEmission(ClaimEmission claimEmission) {
    this.claimEmission = claimEmission;
  }

  public int getNumberOfRights() {
    return numberOfRights;
  }

  public void setNumberOfRights(int numberOfRights) {
    this.numberOfRights = numberOfRights;
  }

  public boolean isValid() {
    if ((claimEmission != null)  &&
        (numberOfRights != -1)) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();
    
    effectenRekening.getVerzamelDepot().claimRechtenToevoegen(this.getExecutionDate(), claimEmission, numberOfRights);

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
  
  
  public String getDescription() {
    StringBuilder buf = new StringBuilder();
    buf.append(numberOfRights);
    buf.append(" claimrechten ontvangen voor claimemissie ");
    buf.append(claimEmission.getShare().getName()).append(" ");
    buf.append(claimEmission.getId());
    
    return buf.toString();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(numberOfRights);
    buf.append(" claimrechten ontvangen voor claimemissie ");
    buf.append(claimEmission.getShare().getName()).append(" ");
    buf.append(claimEmission.getId());
    
    return buf.toString();
  }
}
