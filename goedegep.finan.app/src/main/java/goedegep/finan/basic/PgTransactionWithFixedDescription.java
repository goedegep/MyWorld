package goedegep.finan.basic;

import java.util.List;

public class PgTransactionWithFixedDescription extends PgTransaction {
  private String descriptionText;

  public PgTransactionWithFixedDescription(String descriptionText) {
    super();
    this.descriptionText = descriptionText;
  }

  @Override
  public short getTransactionType() {
    return 0;
  }

  @Override
  public String getDescription() {
    return descriptionText;
  }

  @Override
  public void handle(List<TransactionError> errors) {
    setHandled(true);
  }

}
