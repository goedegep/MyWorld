package goedegep.finan.postbank.pbsprek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.RenteAanpassing;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;

public class PbSpRekRenteAanpassing extends PbSpRekTransaction {
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  public PbSpRekRenteAanpassing(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_RENTE_AANPASSING;
  }

  public String getDescription() {
    StringBuilder output = new StringBuilder();
    
    output.append("Rente aanpassing ");

    output.append(", nieuw rentepercentage ");
    output.append(FPVF.format(this.rentePercentage));   // rentepercentage
    output.append(" %");
    
    return output.toString();
  }

  public String toString() {
    return "Rente aanpassing  " + FPVF.format(this.rentePercentage); // 'rentepercentage' veld.
  }

  @Override
  public void handle(List<TransactionError> errors) {
    handleFirst();

    PbSpRek        account = (PbSpRek) this.getAccount();
    
    RenteAanpassing renteAanpassing = new RenteAanpassing(getExecutionDate(), getRentePercentage());
    account.addRenteAanpassing(renteAanpassing);
    
    handleLast();
  }
}
