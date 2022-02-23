package goedegep.finan.direktbank.direktsprek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

public class DirektSpRekBijschrijving extends DirektSpRekTransaction {
  private static final String            TRANSACTION_TYPE = "bij";
  private static final PgCurrencyFormat      CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  public DirektSpRekBijschrijving(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_BIJSCHRIJVING;
  }

  public String getDescription() {
    StringBuilder output = new StringBuilder();
    
    output.append(TRANSACTION_TYPE);
    output.append(" ");
    output.append(CF.format(getTransactionAmount()));
    
    output.append(", huidig rentepercentage ");
    output.append(FPVF.format(getRentePercentage()));
    output.append(" %");
    
    return output.toString();
  }

  public String toString() {
    return getDescription();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    handleFirst();

    DirektSpRek account = (DirektSpRek) getAccount();

    // verhoog saldo en investering met het ingelegde bedrag.
    account.increaseBalance(getTransactionAmount());
    account.increaseNettoStorting(getTransactionAmount());

    handleLast();
  }
}
