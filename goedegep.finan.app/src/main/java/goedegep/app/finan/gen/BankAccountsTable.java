package goedegep.app.finan.gen;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.basic.Bank;
import goedegep.util.money.PgCurrency;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class BankAccountsTable extends AppGenAbstractTable {
  public BankAccountsTable(AppFrame owner, Bank bank, int munteenheid) {
    super(owner, 400, 200);

    JTable table = getTable();
    table.setModel(new BankAccountsTableModel(bank, munteenheid));
 
    // Install the right data renderers and editors.
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());

    initColumnSizes(table);

    setViewportView(table);
  }

  public void setMunteenheid(int munteenheid) {
    ((BankAccountsTableModel)getTable().getModel()).setMunteenheid(munteenheid);
  }
}
