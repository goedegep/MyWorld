package goedegep.app.finan.td;

import goedegep.appgen.PgFormattedTextField;
import goedegep.appgen.swing.Customization;

public class CDDateField extends CD {
  private String toolTipText;
  
  public CDDateField(String name, String toolTipText) {
    super(name, true);
    this.toolTipText = toolTipText;
  }

  public String getToolTipText() {
    return toolTipText;
  }
  
  public DC<PgFormattedTextField> createComponent(TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCDateField(this, transactionEntryStatus, customization);
  }
}
