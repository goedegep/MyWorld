package goedegep.app.finan.finanapp;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.DataWithIcon;
import goedegep.appgen.TextWithIconCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.basic.SumAccount;
import goedegep.util.money.PgCurrency;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class BankTable extends AppGenAbstractTable {
  public BankTable(AppFrame owner, SumAccount sumAccount) {
    super(owner, 600, 100);
    JTable table = getTable();
    table.setModel(new BankModel(sumAccount));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());
    table.setDefaultRenderer(DataWithIcon.class, new TextWithIconCellRenderer());

    initColumnSizes(table);
    
    setViewportView(table);
  }
  
  public void updateData(boolean ignoreDataErrors) {
    ((BankModel)getTable().getModel()).updateAccountInfo(ignoreDataErrors);
    getTable().revalidate();
  }
}
