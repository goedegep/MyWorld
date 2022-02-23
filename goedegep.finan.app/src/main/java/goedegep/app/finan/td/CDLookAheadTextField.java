package goedegep.app.finan.td;

import goedegep.appgen.LookAheadTextField;
import goedegep.appgen.swing.Customization;

public class CDLookAheadTextField extends CD {
  private String[] options;
  private int      columns;
  private String   toolTipText;
  
  public CDLookAheadTextField(String name, String[] options, int columns, String toolTipText) {
    super(name, true);
    this.options = options;
    this.columns = columns;
    this.toolTipText = toolTipText;
  }
  
  @Override
  public DC<LookAheadTextField> createComponent(TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCLookAheadTextField(this, transactionEntryStatus, customization);
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
}
