package goedegep.finan.abnamrobank.aaeffrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.effrek.EffRekAanpassingDekkingsVerplichting;

public class AAEffRekAanpassingDekkingsVerplichting extends EffRekAanpassingDekkingsVerplichting {
  public AAEffRekAanpassingDekkingsVerplichting(PgAccount account) {
    super(account);
  }

  @Override
  public void handle(List<TransactionError> errors) {
    AAEffRek  effectenRekening = (AAEffRek) this.getAccount();
    
    effectenRekening.getDekkingsRekening().increaseBalance(getTransactionAmount());

    effectenRekening.decreaseBalance(getTransactionAmount());

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
