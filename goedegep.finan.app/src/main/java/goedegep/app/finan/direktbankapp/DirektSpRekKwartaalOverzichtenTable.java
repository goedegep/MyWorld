package goedegep.app.finan.direktbankapp;

import goedegep.appgen.CurrencyCellRenderer;
import goedegep.appgen.QuarterCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.finan.direktbank.direktsprek.DirektSpRek;
import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class DirektSpRekKwartaalOverzichtenTable extends AppGenAbstractTable {
  
  public DirektSpRekKwartaalOverzichtenTable(AppFrame owner, DirektSpRek account) {
    super(owner, 600, 600);
    
    JTable table = getTable();
    table.setModel(new DirektSpRekKwartaalOverzichtenModel(account));

    // Install the right data renderers and editors.
    table.setDefaultRenderer(Quarter.class, new QuarterCellRenderer());
    table.setDefaultRenderer(PgCurrency.class, new CurrencyCellRenderer());
    
    initColumnSizes(table);

    setViewportView(table);
  }
}