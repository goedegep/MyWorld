package goedegep.app.finan.td;

import goedegep.appgen.swing.Customization;
import goedegep.finan.stocks.ShareDividend;

import javax.swing.JComboBox;

public class CDShareDividendField extends CD {
  private int      columns;
  private String   toolTipText;
  
  public CDShareDividendField(String name, int columns, String toolTipText,
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
  public DC<JComboBox<ShareDividend>> createComponent(TransactionEntryStatus transactionEntryStatus,
      Customization customization) {
    return new DCShareDividendField(this, transactionEntryStatus, customization);
  }
}