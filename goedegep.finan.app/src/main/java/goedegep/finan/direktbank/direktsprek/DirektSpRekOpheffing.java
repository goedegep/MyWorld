package goedegep.finan.direktbank.direktsprek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

public class DirektSpRekOpheffing extends DirektSpRekTransaction {
  private static final PgCurrencyFormat  cf =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  public DirektSpRekOpheffing(PgAccount account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OPHEFFING;
  }

  public String getDescription() {
    StringBuilder output = new StringBuilder();

    // bedrag.
    output.append("af ");
    output.append(cf.format(getTransactionAmount()));
    
    // rentepercentage
    if (getRentePercentage() != null) {
      output.append(", huidig rentepercentage ");
      output.append(FPVF.format(getRentePercentage()));
      output.append(" %");
    }
    
    output.append(", uw rekening is opgeheven");
    
    return output.toString();
  }

  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append("ff");
    output.append("\t");
    output.append(cf.format(getTransactionAmount()));
    if (getRentePercentage() != null) {
      output.append("\t");
      output.append(FPVF.format(getRentePercentage()));
    }
    
    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    handleFirst();

    DirektSpRek        account = (DirektSpRek) this.getAccount();

    // verlaag saldo en investering met het opgenomen bedrag.
    account.decreaseBalance(getTransactionAmount());
    account.decreaseNettoStorting(getTransactionAmount());

    handleLast();
  }
}