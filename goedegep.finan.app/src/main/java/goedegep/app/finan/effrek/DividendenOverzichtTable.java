package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.StockDepot;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class DividendenOverzichtTable extends AppGenAbstractTable {
//  private JTable      table;
  
  public DividendenOverzichtTable(AppFrame owner, StockDepot depot, Integer year) {
    super(owner, 750, 200);
    JTable table = getTable();
    table.setModel(new DividendenOverzichtTableModel(depot, year));

    // This table doesn't use renderers per class as e.g. 'totale waarde'
    // is in the 'Koers' column.
    
    initColumnSizes(table);

    setViewportView(table);
  }

  public void setYear(Integer year) {
    DividendenOverzichtTableModel tableModel = (DividendenOverzichtTableModel) getTable().getModel();
    tableModel.setYear(year);
  }
}
