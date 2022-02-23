package goedegep.app.finan.effrek;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class StockDividendsOwnedTable extends AppGenAbstractTable {
  public StockDividendsOwnedTable(AppFrame owner, StockDepot depot) {
    super(owner, 500, 100);
    JTable table = getTable();
    table.setModel(new StockDividendsOwnedTableModel(depot));
    
    // Install the right data renderers and editors.
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());
    table.setDefaultRenderer(FixedPointValue.class, TextBasedCellRenderer.getFixedPointValueCellRendererInstance());
    
    initColumnSizes(table);

    setViewportView(table);
  }
}
