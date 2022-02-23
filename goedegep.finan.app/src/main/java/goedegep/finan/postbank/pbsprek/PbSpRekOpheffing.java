package goedegep.finan.postbank.pbsprek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class PbSpRekOpheffing extends PbSpRekTransaction {
  private static final PgCurrencyFormat  cf =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  public PbSpRekOpheffing(PgAccount  account) {
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
    
    output.append(", huidig rentepercentage ");
    output.append(FPVF.format(this.rentePercentage));   // rentepercentage
    output.append(" %");
    
    output.append(", uw rekening is opgeheven");
    
    return output.toString();
  }

  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append("ff");
    output.append("\t");
    output.append(cf.format(getTransactionAmount()));
    output.append("\t");
    output.append(FPVF.format(getRentePercentage()));
    
    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    handleFirst();

    PbSpRek        account = (PbSpRek) this.getAccount();

    // af (naar giro)
    // verlaag saldo en investering met het opgenomen bedrag.
    account.decreaseBalance(getTransactionAmount());
    account.decreaseNettoStorting(getTransactionAmount());
    account.decreaseBalance(opnameKosten());

    handleLast();
  }

  public PgCurrency opnameKosten() {
    return new PgCurrency(getTransactionAmount().getCurrency(), 0L);
  }
}
