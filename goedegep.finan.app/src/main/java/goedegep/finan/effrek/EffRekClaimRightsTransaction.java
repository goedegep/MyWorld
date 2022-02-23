package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekClaimRightsTransaction extends EffRekTransactie {
  public static final String            AANKOOP_STRING = "aank";
  public static final String            VERKOOP_STRING = "verk";
  
  private static final int              BEDRAG_SIZE = 10;
  private static final PgCurrencyFormat CF =  new PgCurrencyFormat();
  private static final PgCurrencyFormat CF2 = new PgCurrencyFormat(BEDRAG_SIZE);

  private boolean         buy;             // rights bought if true, otherwise sell
  private ClaimEmission   claimEmission;
  private int             numberOfRights = -1;  // -1 indicates invalid value.

  
  public EffRekClaimRightsTransaction(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_CLAIM_RIGHTS_TRANSACTION;
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

  public boolean isBuy() {
    return buy;
  }

  public void setBuy(boolean buy) {
    this.buy = buy;
  }
  
  public boolean isValid() {
    if ((getBookingDate() != null) &&
        (claimEmission != null)  &&
        (numberOfRights != -1)  &&
        (getExecutionDate() != null)) {
      return true;
    } else {
      return false;
    }
  }
  
  public PgCurrency getBrutoBedrag() {
    return claimEmission.getRightRate().multiply(numberOfRights);
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();
    
    if (buy) {
      effectenRekening.getVerzamelDepot().claimRechtenToevoegen(this.getExecutionDate(), claimEmission, numberOfRights);      
      effectenRekening.decreaseBalance(getBrutoBedrag());
    } else {
      effectenRekening.getVerzamelDepot().claimRechtenVerwijderen(this.getExecutionDate(), claimEmission, numberOfRights);      
      effectenRekening.increaseBalance(getBrutoBedrag());
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
  
  public String getDescription() {
    PgCurrency brutoBedrag = getBrutoBedrag();
    StringBuilder buf = new StringBuilder();
    if (buy) {
      buf.append("af ");      
    } else {
      buf.append("bij ");
    }
    buf.append(CF2.format(brutoBedrag));
    
    if (buy) {
      buf.append(" aankoop ");
    } else {
      buf.append(" verkoop ");
    }
    
    buf.append(" claim rechten: ");
    buf.append(claimEmission.getShare().getName()).append(" ");
    buf.append(claimEmission.getId());    
    buf.append(", ").append(numberOfRights);
    if (numberOfRights == 1) {
      buf.append(" right ");
    } else {
      buf.append(" rights ");
    }
    buf.append("koers: ").append(CF.format(claimEmission.getRightRate()));
    buf.append(", brutobedrag af ").append(CF.format(brutoBedrag));
    
    return buf.toString();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    if (buy) {
      buf.append(" aankoop ");
    } else {
      buf.append(" verkoop ");
    }
    
    buf.append(" claim rechten: ");
    buf.append(claimEmission.getShare().getName()).append(" ");
    buf.append(claimEmission.getId());    
    buf.append(", ").append(numberOfRights);
    if (numberOfRights == 1) {
      buf.append(" right ");
    } else {
      buf.append(" rights ");
    }
    buf.append("koers: ").append(CF.format(claimEmission.getRightRate()));
    
    return buf.toString();
  }
}
