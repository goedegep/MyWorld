package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.Share;

public abstract class EffRekOvername extends EffRekTransactie {
  private Share vanEffect = null;         // 'van' effect
  private Share naarEffect = null;        // 'naar' effect
  private int   vanAantal = -1;           // 'van' aantal effecten
  private int   naarAantal = -1;          // 'naar' aantal effecten

  public EffRekOvername(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OVERNAME;
  }
  
  public void setVanEffect(Share vanEffect) {
    this.vanEffect = vanEffect;
  }

  public Share getVanEffect() {
    return vanEffect;
  }

  public void setVanAantal(int vanAantal) {
    this.vanAantal = vanAantal;
  }

  public int getVanAantal() {
    return vanAantal;
  }

  public void setNaarEffect(Share naarEffect) {
    this.naarEffect = naarEffect;
  }

  public Share getNaarEffect() {
    return naarEffect;
  }

  public void setNaarAantal(int naarAantal) {
    this.naarAantal = naarAantal;
  }

  public int getNaarAantal() {
    return naarAantal;
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek        effectenRekening = (EffRek) getAccount();
    
    effectenRekening.getVerzamelDepot().overname(getBookingDate(), vanEffect, naarEffect, vanAantal, naarAantal);
    
    setHandled(true);
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Overname  ");

    if (getVanAantal() == 1) {
      output.append("ieder aandeel ");
    } else {
      output.append("iedere " + getVanAantal() + " aandelen ");
    }

    output.append(getVanEffect().getName());

    if (getVanAantal() == 1) {
      output.append(" is ");
    } else {
      output.append(" zijn ");
    }

    output.append("verwisseld in " + getNaarAantal());

    if (getNaarAantal() == 1) {
      output.append(" aandeel ");
    } else {
      output.append(" aandelen ");
    }

    //output.append(PbEffRekTransaction.effectIDToEffectString(transaction.getNaarEffect()));
    output.append(getNaarEffect().getName());

    return output.toString();
  }
  
  @Override
  public String toString() {
    StringBuilder      output = new StringBuilder();

    output.append("Overname:  ");

    if (getVanAantal() == 1) {
      output.append("1 aandeel ");
    } else {
      output.append(getVanAantal() + " aandelen ");
    }

    output.append(getVanEffect().getName());

    if (getVanAantal() == 1) {
      output.append(" is ");
    } else {
      output.append(" zijn ");
    }

    output.append("verwisseld in " + getNaarAantal());

    if (getNaarAantal() == 1) {
      output.append(" aandeel ");
    } else {
      output.append(" aandelen ");
    }

    output.append(getNaarEffect().getName());

    return output.toString();
  }

  public boolean isValid() {
    return ((vanEffect != null)  &&
            (naarEffect != null)  &&
            (vanAantal != -1)  &&
            (naarAantal != -1));
  }
}
