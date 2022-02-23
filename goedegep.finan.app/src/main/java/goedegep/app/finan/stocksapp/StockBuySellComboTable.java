package goedegep.app.finan.stocksapp;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.StockBuySellCombo;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.Date;
import java.util.List;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class StockBuySellComboTable extends AppGenAbstractTable {
  public StockBuySellComboTable(AppFrame owner, List<StockBuySellCombo> buySellCombos) {
    super(owner, 700, 250);
    
    JTable table = getTable();
    table.setModel(new StockBuySellComboTableModel(buySellCombos));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Date.class, TextBasedCellRenderer.getDateCellRendererInstance());
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer(new PgCurrencyFormat(7)));
    
    initColumnSizes(table);

    setViewportView(table);
    
    installPopupMenu(table);
  }

  public void setBuySellCombos(List<StockBuySellCombo> buySellCombos) {
    ((StockBuySellComboTableModel) getTable().getModel()).setBuySellCombos(buySellCombos);
  }
}