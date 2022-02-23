package goedegep.app.finan.stocksapp;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.StockPositionHistory;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.Date;
import java.util.List;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class StockBuysTable extends AppGenAbstractTable {
  public StockBuysTable(AppFrame owner, List<StockPositionHistory> buys) {
    super(owner, 700, 150);
    
    JTable table = getTable();
    table.setModel(new StockBuysTableModel(buys));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Date.class, TextBasedCellRenderer.getDateCellRendererInstance());
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer(new PgCurrencyFormat(7)));
    
    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }

  public void setBuys(List<StockPositionHistory> buys) {
    ((StockBuysTableModel) getTable().getModel()).setBuys(buys);
  }
}
