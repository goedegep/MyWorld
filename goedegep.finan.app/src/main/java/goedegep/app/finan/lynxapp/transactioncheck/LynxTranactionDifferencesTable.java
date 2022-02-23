package goedegep.app.finan.lynxapp.transactioncheck;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.lynx.lynxeffrek.LynxEffRek;
import goedegep.finan.lynx.lynxeffrek.LynxMonthlyActivityStatements;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;

@SuppressWarnings("serial")
public class LynxTranactionDifferencesTable extends AppGenAbstractTable {
  private LynxTranactionDifferencesTableModel lynxTranactionDifferencesTableModel;
  
  public LynxTranactionDifferencesTable(AppFrame owner, LynxEffRek effectenRekening, LynxToFinanShareIdList lynxToFinanShareIdList, LynxMonthlyActivityStatements lynxMonthlyActivityStatements) {
    super(owner, 1350, 500);
    
    lynxTranactionDifferencesTableModel = new LynxTranactionDifferencesTableModel(effectenRekening, lynxToFinanShareIdList, lynxMonthlyActivityStatements);
    JTable table = getTable();
    table.setModel(lynxTranactionDifferencesTableModel);

    table.setDefaultRenderer(LynxTransactionCheckResultInfo.class, new LynxTransactionDifferencesTableCellRenderer());
    
    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }

  public void setMonth(YearMonth month) {
    lynxTranactionDifferencesTableModel.setMonth(month);
  }


  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    JPopupMenu      popup = null;
    final int       row = getTable().rowAtPoint(new Point(x, y));
//    final int       column = getTable().columnAtPoint(new Point(x, y));

    popup = new JPopupMenu();

    if (row != -1) {
      MenuFactory.addMenuItem(popup, "Voeg toe aan Finan", new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          openObject(row);
        }
      });

    }

    return popup;
  }

  /**
   * TODO change to adding to Finan
   */
  public void openObject(int row) {
//    T object = objects.get(row);
//
//    openObject(object);
  }
}
