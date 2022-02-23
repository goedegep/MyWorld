package goedegep.app.finan.postbankapp;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.QuarterCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.postbank.pbsprek.PbSpRek;
import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class PbSpRekKwartaalOverzichtenTable extends AppGenAbstractTable {
  public PbSpRekKwartaalOverzichtenTable(AppFrame owner, PbSpRek account) {
    super(owner, 600, 600);
    
    JTable table = getTable();
    table.setModel(new PbSpRekKwartaalOverzichtenModel(account));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Quarter.class, new QuarterCellRenderer());
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer(new PgCurrencyFormat(9)));
    
    initColumnSizes(table);

    setViewportView(table);
  }
}
