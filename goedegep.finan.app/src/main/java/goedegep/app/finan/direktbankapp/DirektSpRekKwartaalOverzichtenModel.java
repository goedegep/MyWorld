package goedegep.app.finan.direktbankapp;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.basic.QuarterlyData;
import goedegep.finan.direktbank.direktsprek.DirektSpRek;
import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;

import java.util.List;

@SuppressWarnings("serial")
public class DirektSpRekKwartaalOverzichtenModel extends AppGenAbstractTableModel {
  private static final int                      KWARTAAL_COLUMN = 0;
  private static final int                      SALDO_COLUMN = 1;
  private static final int                      WINST_COLUMN = 2;
  private static final int                      CUM_WINST_COLUMN = 3;
  
  private static final String[] columnNames = {
    "Kwartaal",
    "Saldo",
    "Kwartaal winst",
    "Cumulatieve winst"
  };

  private static final Object[] longValues = {
      "2010 IV",      // periode (kwartaal)
      "e 100000,00",  // saldo
      "e 10000,00",   // kwartaalwinst
      "e 100000,00"}; // cumulatieve winst
 
  private List<QuarterlyData> quarterlyDataList;
  
  public DirektSpRekKwartaalOverzichtenModel(DirektSpRek account) {
    super(columnNames, longValues);
    quarterlyDataList = account.getQuarterlyData();
  }

  public int getRowCount() {
    return quarterlyDataList.size();
  }

  public Object getValueAt(int row, int col) {
    QuarterlyData quarterlyData = quarterlyDataList.get(row);
    switch (col) {
    case KWARTAAL_COLUMN:
      return quarterlyData.getQuarter();

    case SALDO_COLUMN:
      return quarterlyData.getBalance();

    case WINST_COLUMN:
      return quarterlyData.getProfit();

    case CUM_WINST_COLUMN:
      return quarterlyData.getCumulativeProfit();

    default:
      return null;
    }
  }
  
  /*
   * JTable uses this method to determine the default renderer/
   * editor for each cell.  If we didn't implement this method,
   * then the last column would contain text ("true"/"false"),
   * rather than a check box.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class getColumnClass(int col) {
    switch (col) {
    case KWARTAAL_COLUMN:
      return Quarter.class;

    case SALDO_COLUMN:
      return PgCurrency.class;

    case WINST_COLUMN:
      return PgCurrency.class;

    case CUM_WINST_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }
}