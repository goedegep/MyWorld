package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.StockDepot;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class OptionPositionsTable extends AppGenAbstractTable {
  public OptionPositionsTable(AppFrame owner, StockDepot depot) {
    super(owner, 500, 100);
    JTable table = getTable();
    
    table.setModel(new OptionPositionsTableModel(depot));
    
    // No renderers to be installed.
    
    initColumnSizes(table);

    setViewportView(table);
  }
}
