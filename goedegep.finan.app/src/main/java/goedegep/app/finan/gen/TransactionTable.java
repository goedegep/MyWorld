package goedegep.app.finan.gen;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.DateCellEditor;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.TransactionFilter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class TransactionTable extends AppGenAbstractTable {
  
  public TransactionTable(AppFrame owner, List<PgTransaction> transactions) {
    this(owner);
    
    getTable().setModel(new TransactionTableModelOnTransactionList(transactions));
    
    initTable();
  }
  
  public TransactionTable(AppFrame owner, PgAccount account) {
    this(owner);
    
    getTable().setModel(new TransactionTableModelOnAccount(account));
    
    initTable();
  }
  
  public void setTransactions(List<PgTransaction> transactions) {
    getTable().setModel(new TransactionTableModelOnTransactionList(transactions));
  }
  
  private TransactionTable(AppFrame owner) {
    super(owner, 600, 200);
    
  }
  
  private void initTable() {
    JTable table = getTable();

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Date.class, TextBasedCellRenderer.getDateCellRendererInstance("klik om datum te wijzigen"));
    table.setDefaultEditor(Date.class, new DateCellEditor("dd-MM-yyyy"));
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer(new PgCurrencyFormat(9)));
    
    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }
  
  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    JPopupMenu      popup = null;
    final int       row = getTable().rowAtPoint(new Point(x, y));

    popup = new JPopupMenu();

    MenuFactory.addMenuItem(popup, "Dummy", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        dummyAction(row, true);
      }
    });

    return popup;
  }

  private void dummyAction(int row, boolean before) {
    
    getTable().revalidate();
  }

  public void setFilters(List<TransactionFilter> filters) {
    TransactionTableModel transactionTableModel = (TransactionTableModel) getTable().getModel();
    transactionTableModel.setFilters(filters);
    getTable().revalidate();
  }
}
