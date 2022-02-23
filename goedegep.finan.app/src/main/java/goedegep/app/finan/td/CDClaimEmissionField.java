package goedegep.app.finan.td;

import javax.swing.JComboBox;

import goedegep.appgen.swing.Customization;

public class CDClaimEmissionField extends CD {
  private int      columns;
  private String   toolTipText;
  
  public CDClaimEmissionField(String name, int columns, String toolTipText,
      String shareFieldName) {
    super(name, true, shareFieldName);
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
  public DC<JComboBox<String>> createComponent(TransactionEntryStatus transactionEntryStatus,
      Customization customization) {
    return new DCClaimEmissionField(this, transactionEntryStatus, customization);
  }

}
