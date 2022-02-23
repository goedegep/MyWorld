package goedegep.app.finan.finanapp;

import goedegep.appgen.DataWithIcon;
import goedegep.appgen.DateCellEditor;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.TextWithIconCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.stocks.CompanyPool;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class FinanTransactionsTable extends AppGenAbstractTable {
  private SumAccount  sumAccount;
  private CompanyPool companyPool;

  public FinanTransactionsTable(AppFrame owner, CompanyPool companyPool, SumAccount sumAccount) {
    super(owner, 800, 200);
    this.companyPool = companyPool;
    this.sumAccount = sumAccount;

    JTable table = getTable();
    table.setModel(new FinanTransactionsModel(sumAccount));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Date.class, TextBasedCellRenderer.getDateCellRendererInstance("klik om datum te wijzigen"));
    table.setDefaultEditor(Date.class, new DateCellEditor("dd-MM-yyyy"));
    table.setDefaultRenderer(DataWithIcon.class, new TextWithIconCellRenderer());
    
    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }
  
  public void revalidate() {
	if (getTable() != null) {
	  FinanTransactionsModel model = (FinanTransactionsModel)getTable().getModel();
	  int lastRowIndex = model.getRowCount() - 1;
      Rectangle bounds = getTable().getCellRect(lastRowIndex,0,true);
      getViewport().setViewPosition(bounds.getLocation());
      getTable().revalidate();
	}
  }
    
  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    JMenuItem       menuItem;
    JPopupMenu      popup = null;
    final int       row = getTable().rowAtPoint(new Point(x, y));

    popup = new JPopupMenu();

    MenuFactory.addMenuItem(popup, "Nieuwe transactie hiervoor", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        CreateNewTransaction(row, true);
      }
    });

    MenuFactory.addMenuItem(popup, "Nieuwe transactie hierna", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        CreateNewTransaction(row, false);
      }
    });

    MenuFactory.addMenuItem(popup, "Transactie verwijderen", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        removeTransaction(row);
      }
    });

    menuItem = MenuFactory.addMenuItem(popup, "transactie omhoog verplaatsen", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        moveTransaction(row, true);
      }
    });
    if (row == 0) {
      menuItem.setEnabled(false);
    }

    menuItem = MenuFactory.addMenuItem(popup, "transactie omlaag verplaatsen", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        moveTransaction(row, false);
      }
    });
    if (row == getTable().getRowCount() - 1) {
      menuItem.setEnabled(false);
    }

    return popup;
  }

  
  private void CreateNewTransaction(int row, boolean before) {
    TransactionDialog dlg = new TransactionDialog((AppFrame) getOwner(), companyPool, sumAccount, sumAccount.getTransaction(row), before);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);
    
    getTable().revalidate();
  }

  private void moveTransaction(int row, boolean up) {
    System.out.println("FinanMainWindow: moveTransaction: row = " + row);
    System.out.println("FinanMainWindow: moveTransaction: transaction = " +
                       sumAccount.getTransaction(row).toString());
    sumAccount.moveTransaction(sumAccount.getTransaction(row), up);
    
    FinanTransactionsModel model = (FinanTransactionsModel)getTable().getModel();
    if (up) {
      model.fireTableRowsUpdated(row - 1, row);
    } else {
      model.fireTableRowsUpdated(row, row + 1);
    }
  }

  private void removeTransaction(int row) {
    System.out.println("FinanMainWindow: removeTransaction: row = " + row);
    sumAccount.removeTransaction(row);
    FinanTransactionsModel model = (FinanTransactionsModel)getTable().getModel();
    model.fireTableDataChanged();
  }
}
