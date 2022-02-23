package goedegep.app.finan.effrek;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.NumberCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.money.PgCurrency;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class SharesOwnedTable extends AppGenAbstractTable {
  public SharesOwnedTable(AppFrame owner, StockDepot depot, LocalDate statusDate) {
    super(owner, 500, 400);
    
    JTable table = getTable();
    
    table.setModel(new SharesOwnedTableModel(depot, statusDate));

    // Install the right data renderers and editors.
    NumberCellRenderer numberCellRenderer = new NumberCellRenderer(8, false, true);
    table.setDefaultRenderer(Integer.class, numberCellRenderer);
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());
    
    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }
  
  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    JPopupMenu      popup = null;
    final int       row = getTable().rowAtPoint(new Point(x, y));
    SharesOwnedTableModel tableModel = (SharesOwnedTableModel) getTable().getModel();
    final Share           share = tableModel.getShare(row);

    popup = new JPopupMenu();

    if (share != null) {
      MenuFactory.addMenuItem(popup, "V Model", new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          ((EffRekWindow) getOwner()).showVModelWindow(share);
        }
      });
    }

    return popup;
  }
}
