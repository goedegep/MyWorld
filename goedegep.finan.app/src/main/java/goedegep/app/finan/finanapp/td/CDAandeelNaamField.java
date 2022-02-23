package goedegep.app.finan.finanapp.td;

import goedegep.app.finan.td.CD;
import goedegep.app.finan.td.TransactionEntryStatus;
import goedegep.appgen.swing.Customization;

public class CDAandeelNaamField extends CD {
  private int      columns;
  private String   toolTipText;

  public CDAandeelNaamField(String name, int columns, String toolTipText) {
    super(name, true);
    this.columns = columns;
    this.toolTipText = toolTipText;
  }
  
  public int getColumns() {
    return columns;
  }

  public String getToolTipText() {
    return toolTipText;
  }
  
  @Override
  public DCAandeelNaamField createComponent(
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCAandeelNaamField(this, (FinanTransactionEntryStatus) transactionEntryStatus, customization);
  } 
}
