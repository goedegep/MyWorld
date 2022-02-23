package goedegep.app.finan.td;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;

public class CDCurrencyField extends CD {
  private int    columns;
  private String toolTipText;

  public CDCurrencyField(String name, int columns, String toolTipText) {
    this(name, columns, toolTipText, false);
  }

  public CDCurrencyField(String name, int columns, String toolTipText, boolean optional) {
    super(name, true, optional);
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
  public DC<JTextField> createComponent(
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCCurrencyField(this, transactionEntryStatus, customization);
  }
}
