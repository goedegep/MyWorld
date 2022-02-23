package goedegep.app.finan.stocksapp;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.RateType;
import goedegep.util.money.PgCurrency;

import java.util.Date;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class OptionRatesTable extends AppGenAbstractTable {
  public OptionRatesTable(AppFrame owner) {
    super(owner, 500, 400);
    JTable table = getTable();
    table.setModel(new OptionRatesModel());

    // Install the right data renderers.
    table.setDefaultRenderer(Date.class, TextBasedCellRenderer.getDateCellRendererInstance());
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());

    initColumnSizes(table);
    
    setViewportView(table);
  }

  public void setParameters(OptionSerie optionSerie, RateType rateType) {
    ((OptionRatesModel)getTable().getModel()).setParameters(optionSerie, rateType);
    getTable().revalidate();
    getTable().repaint();
  }
}
