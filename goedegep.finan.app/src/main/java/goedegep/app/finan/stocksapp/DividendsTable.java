package goedegep.app.finan.stocksapp;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.stocks.DividendType;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.money.PgCurrency;


@SuppressWarnings("serial")
public class DividendsTable extends AppGenAbstractTable {
  private Share       share;

  public DividendsTable(AppFrame owner) {
    super(owner, 720, 350);

    AppGenAbstractTableModel tableModel = new DividendsModel();
    JTable table = getTable();
    table.setModel(tableModel);
    tableModel.setTable(this);

    // Install the right data renderers and editors.
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());
    table.setDefaultEditor(PgCurrency.class, new CurrencyCellEditor());

    ComponentFactory componentFactory = owner.getTheComponentFactory();
   LookAheadComboBox<String> dividendTypeComboBox = componentFactory.createLookAheadComboBox(DividendType.getTextsAsArray(), true, 12, 20, "Kies dividend type");
//        new JComboBox<>(DividendType.getTextsAsArray());
    dividendTypeComboBox.setEditable(true);
    DefaultCellEditor dividendTypeEditor = new DefaultCellEditor(dividendTypeComboBox);
    TableColumnModel columnModel = table.getColumnModel();
    columnModel.getColumn(DividendsModel.TYPE_COLUMN).setCellEditor(dividendTypeEditor);

    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }
  
  public void setShare(Share share) {
    this.share = share;
    ((DividendsModel) getTable().getModel()).setShare(share);
    getTable().revalidate();
  }
  
  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    DividendsModel dividendsModel =(DividendsModel) getTable().getModel();
    if (dividendsModel.getRowCount() == 0) {
      return createEmptyTablePopup();
    } else {
      return createNonEmptyTablePopup(x, y);
    }
  }
  
  private JPopupMenu createNonEmptyTablePopup(final int x, final int y) {
    JPopupMenu      popup = null;
    
    popup = new JPopupMenu();

    MenuFactory.addMenuItem(popup, "Nieuw dividend hiervoor", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        CreateNewDividend(getTable().rowAtPoint(new Point(x, y)), true);
      }
    });

    MenuFactory.addMenuItem(popup, "Nieuw dividend hierna", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        CreateNewDividend(getTable().rowAtPoint(new Point(x, y)), false);
      }
    });

    MenuFactory.addMenuItem(popup, "Dividend verwijderen", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Verwijderen");
      }
    });

    return popup;
  }
  
  private JPopupMenu createEmptyTablePopup() {
    JPopupMenu      popup = null;

    popup = new JPopupMenu();

    MenuFactory.addMenuItem(popup, "Nieuw dividend", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        CreateNewDividend(-1, true);
      }
    });

    return popup;
  }

  private void CreateNewDividend(int row, boolean before) {
    ShareDividend dividend = new ShareDividend();
    dividend.setShare(share);
    dividend.setName("naamloos");
    if (row == -1) {
      share.addDividend(dividend);
    } else {
      if (before) {
        share.addDividend(dividend, share.getDividends().get(row));
      } else {
        if ((row + 1) < share.getDividends().size()) {
          share.addDividend(dividend, share.getDividends().get(row + 1));
        } else {
          share.addDividend(dividend);
        }
      }
    }

    int newRow = getRowForDividend(dividend);
    ((DividendsModel)getTable().getModel()).fireTableRowsInserted(newRow, newRow);
    getTable().setRowSelectionInterval(newRow, newRow);
  }

  private int getRowForDividend(ShareDividend shareDividend) {
    int index = 0;

    for (ShareDividend currentShareDividend: share.getDividends()) {
      if (currentShareDividend == shareDividend) {
        return index;
      }

      index++;
    }

    return -1;
  }
}
