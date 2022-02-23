package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekVerwClaimRights extends EffRekTransactie {
  private static final int                  BEDRAG_SIZE = 10;
  private static final PgCurrencyFormat     CF = new PgCurrencyFormat();
  private static final PgCurrencyFormat     CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  
  private ClaimEmission   claimEmission = null;
  private int             numberOfRights = -1;  // -1 indicates invalid value.

  public EffRekVerwClaimRights(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_VERW_CLAIM_RIGHTS;
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
  
  public int getNumberOfSharesObtained() {
    return numberOfRights * claimEmission.getToAmount() / claimEmission.getFromAmount();
  }
  
  public PgCurrency getBrutoBedrag() {
    return claimEmission.getPricePerShare().multiply(getNumberOfSharesObtained()).certifyFactor(100);
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
  
  @Override
  public void handle(List<TransactionError> errors) {
    PbEffRek  effectenRekening = (PbEffRek) this.getAccount();
    
    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }
    
    PgCurrency brutoBedrag = getBrutoBedrag();
    effectenRekening.decreaseBalance(brutoBedrag);
    effectenRekening.getVerzamelDepot().claimRechtenVerwijderen(this.getExecutionDate(), claimEmission, numberOfRights);
    effectenRekening.getVerzamelDepot().effectenToevoegen(getBookingDate(), claimEmission.getShare(), getNumberOfSharesObtained(), claimEmission.getPricePerShare(), brutoBedrag);

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
  
  public String getDescription() {
    int numberOfShares = getNumberOfSharesObtained();
    PgCurrency brutoBedrag = getBrutoBedrag();
    StringBuilder buf = new StringBuilder();
    buf.append("af ").append(CF2.format(brutoBedrag));
    buf.append(" claim emissie: ").append(numberOfRights);
    if (numberOfRights == 1) {
      buf.append(" right ");
    } else {
      buf.append(" rights ");
    }
    buf.append(claimEmission.getShare().getName()).append(" ");
    buf.append(claimEmission.getId());    
    buf.append(" zijn verwisseld in ");
    buf.append(numberOfShares);
    buf.append(" aandelen ").append(claimEmission.getShare().getName());
    buf.append(", voor elke ").append(claimEmission.getFromAmount());
    buf.append(" stuks en ");
    buf.append(CF.format(claimEmission.getPricePerShare().multiply(claimEmission.getToAmount())));
    buf.append(" ontvangt u ").append(claimEmission.getToAmount());
    buf.append(" stuks, brutobedrag ").append(CF.format(brutoBedrag));
    
    return buf.toString();
  }

  @Override
  public String toString() {
    int numberOfShares = getNumberOfSharesObtained();
    PgCurrency brutoBedrag = getBrutoBedrag();
    StringBuilder buf = new StringBuilder();
    buf.append(" claim emissie: ").append(numberOfRights);
    if (numberOfRights == 1) {
      buf.append(" right ");
    } else {
      buf.append(" rights ");
    }
    buf.append(claimEmission.getShare().getName()).append(" ");
    buf.append(claimEmission.getId());    
    buf.append(" zijn verwisseld in ");
    buf.append(numberOfShares);
    buf.append(" aandelen ").append(claimEmission.getShare().getName());
    buf.append(", voor ");
    if (claimEmission.getFromAmount() == 1) {
      buf.append("elk stuk");
    } else {
      buf.append("elke ").append(claimEmission.getFromAmount());
      buf.append(" stuks");
    }
    buf.append(" en ");
    buf.append(CF.format(claimEmission.getPricePerShare().multiply(claimEmission.getToAmount())));
    buf.append(" ontvangt u ").append(claimEmission.getToAmount());
    buf.append(" stuks, brutobedrag ").append(CF.format(brutoBedrag));
    
    return buf.toString();
  }
}
