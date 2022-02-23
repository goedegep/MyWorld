package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.StockDepot;

import java.util.logging.Logger;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class EffectenReportTable extends AppGenAbstractTable {
  private final static Logger     LOGGER = Logger.getLogger(EffectenReportTable.class.getName());

  
//  public EffectenReportTable(AppFrame owner, StockDepot depot, Integer year) {
//    this(owner, depot, year, null);
//  }
  
//  public EffectenReportTable(AppFrame owner, StockDepot depot, Integer year, Integer quarter) {
//    super(owner, 750, 300);
//    
//    JTable table = getTable();
//    table.setModel(new EffectenReportTableModel(depot, year, quarter));
//
//    // This table doesn't use renderers per class as e.g. 'totale waarde'
//    // is in the 'Koers' column.
//    
//    initColumnSizes(table);
//
//    setViewportView(table);
//  }
  
  public EffectenReportTable(AppFrame owner, EffectenReportPanelType effectenReportPanelType, StockDepot depot, Object period) {
    super(owner, 750, 300);
    LOGGER.severe("=>");
    
    JTable table = getTable();
    table.setModel(new EffectenReportTableModel(effectenReportPanelType, depot, period));

    // This table doesn't use renderers per class as e.g. 'totale waarde'
    // is in the 'Koers' column.
    
    initColumnSizes(table);

    setViewportView(table);
    LOGGER.severe("<=");
  }

//  public void setYear(Integer year) {
//    EffectenReportTableModel tableModel = (EffectenReportTableModel) getTable().getModel();
//    tableModel.setYear(year);
//  }
//
//  public void setQuarter(Integer year, Integer quarter) {
//    EffectenReportTableModel tableModel = (EffectenReportTableModel) getTable().getModel();
//    tableModel.setQuarter(year, quarter);
//  }

  public void setPeriod(Object period) {
    EffectenReportTableModel tableModel = (EffectenReportTableModel) getTable().getModel();
    tableModel.setPeriod(period);
  }
}
