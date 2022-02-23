package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekCorrectie extends EffRekTransactie {
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat();

  private PgCurrency         saldo;   // Nieuw, gecorrigeerd saldo.
  

  public EffRekCorrectie(PgAccount  account) {
    super(account);
  }
  public short getTransactionType() {
    return TT_CORRECTIE;
  }

  public PgCurrency getSaldo() {
    return saldo;    
  }
  
  public void setSaldo(PgCurrency saldo) {
    this.saldo = saldo;    
  }
  
  @Override
  public void handle(List<TransactionError> errors) {
    PbEffRek        effectenRekening = (PbEffRek) this.getAccount();
    
    if (saldo != null) {
      effectenRekening.setBalance(saldo);
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }


  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Correctie:  ");
    if (saldo != null) {
      output.append(", nieuw saldo " + CF.format(saldo));
    }

    return output.toString();
  }

  @Override
  public String toString() {
    StringBuilder      buf = new StringBuilder();
    
    buf.append("Correctie");
    
    // saldo
    if (saldo != null) {
      buf.append(", nieuw saldo " + CF.format(saldo));
    }
    
    return buf.toString();
  }
}
