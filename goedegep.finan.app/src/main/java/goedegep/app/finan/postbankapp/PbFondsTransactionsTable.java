package goedegep.app.finan.postbankapp;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.DateCellEditor;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.postbank.pbfonds.PbFonds;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

@SuppressWarnings("serial")
public class PbFondsTransactionsTable extends AppGenAbstractTable {

  public PbFondsTransactionsTable(AppFrame owner, PbFonds fonds) {
    super(owner, 600, 200);

    JTable table = getTable();
    table.setModel(new PbFondsTransactionsModel(fonds));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Date.class, TextBasedCellRenderer.getDateCellRendererInstance("klik om datum te wijzigen"));
    table.setDefaultEditor(Date.class, new DateCellEditor("dd-MM-yyyy"));
    table.setDefaultRenderer(FixedPointValue.class, TextBasedCellRenderer.getFixedPointValueCellRendererInstance());
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());

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
        dummyAvtion(row, true);
      }
    });

    return popup;
  }

  private void dummyAvtion(int row, boolean before) {
    
    getTable().revalidate();
  }
}