package goedegep.finan.postbank.pbsprek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;


public class PbSpRekGeschenkInleg extends PbSpRekTransaction {
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  public PbSpRekGeschenkInleg(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_GESCHENK_INLEG;
  }

  public String getDescription() {
    StringBuilder output = new StringBuilder();
    
    output.append("bij ");
    output.append(CF.format(getTransactionAmount()));       // bedrag
    output.append(" geschenkinleg");

    output.append(", huidig rentepercentage ");
    output.append(FPVF.format(this.rentePercentage));   // rentepercentage
    output.append(" %");
    
    return output.toString();
  }

  public String toString() {
    return "Geschenkinleg " + CF.format(getTransactionAmount()) +                         // bedrag
           " " + FPVF.format(this.rentePercentage); // 'rentepercentage' veld
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbSpRek        account = (PbSpRek) this.getAccount();

    handleFirst();

    // verhoog saldo met de geschenkinleg, de investering blijft gelijk.
    account.increaseBalance(getTransactionAmount());

    handleLast();
  }
}
