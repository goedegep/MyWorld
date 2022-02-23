package goedegep.app.finan.td;

import javax.swing.JComboBox;

import goedegep.appgen.swing.Customization;

public class CDComboBox extends CD {
  private String[] options;
  private int      columns;
  private String   toolTipText;
  
  public CDComboBox(String name, String[] options, int columns, String toolTipText) {
    super(name, true);
    this.options = options;
    this.columns = columns;
    this.toolTipText = toolTipText;
  }

  public String[] getOptions() {
    return options;
  }

  public int getColumns() {
    return columns;
  }

  public String getToolTipText() {
    return toolTipText;
  }

  @Override
  public DC<JComboBox<Object>> createComponent(
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCComboBox(this, transactionEntryStatus, customization);
  }
}
