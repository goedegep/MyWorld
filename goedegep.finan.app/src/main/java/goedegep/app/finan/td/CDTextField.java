package goedegep.app.finan.td;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;

public class CDTextField extends CD {
  private int    columns;
  private String toolTipText;
  
  public CDTextField(String name, int columns, String toolTipText) {
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
  
  public DC<JTextField> createComponent(TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCTextField(this, transactionEntryStatus, customization);
  }
}
