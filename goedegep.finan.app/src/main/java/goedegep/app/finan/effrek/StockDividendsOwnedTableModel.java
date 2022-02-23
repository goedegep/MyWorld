package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.DividendType;
import goedegep.finan.stocks.ShareDividend;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDividendPosition;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class StockDividendsOwnedTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int STOCK_DIVIDEND_COLUMN = 0;
  private static final int COUNT_COLUMN = 1;
  private static final int RATE_COLUMN = 2;
  
  private static final String[] columnNames = {
      "Stock dividend",
      "Aantal",
      "Koers"
  };
  
  private static final Object[] longValues = {
      "Naam van een stock dividend",
      "9999999",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99"
  };
  
  private StockDepot          depot = null;
  private Object[][]          data = null;
  
  public StockDividendsOwnedTableModel(StockDepot depot) {
    super(columnNames, longValues);
    this.depot = depot;
    
    fillData();
  }

  public int getRowCount() {
    if (data != null) {
      return data.length;
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    return data[row][col];
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
    case STOCK_DIVIDEND_COLUMN:
      return String.class;
      
    case COUNT_COLUMN:
      return FixedPointValue.class;

    case RATE_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }

  void fillData() {
    int nrOfPositions = depot.numberOfStockDividendPositions();

    if (nrOfPositions == 0) {
      data = new String[1][3];
      data[0][0] = "geen stock dividenden";
    } else {
      data = new Object[nrOfPositions][3];

      // first create an alphabetically sorted list of the positions in the depot
      List<StockDividendPosition> positions = new LinkedList<StockDividendPosition>(depot.getStockDividendPositions());
      Collections.sort(positions, (StockDividendPosition) positions.get(0));

      // fill the positionData with the Name, Amount and Rate per position
      int index = 0;
      for (StockDividendPosition position: positions) {
        ShareDividend dividend = position.getDividend();
        data[index][0] = dividend.getReferenceString();
        data[index][1] = position.getCurrentAmount();
        if (dividend.getType() != DividendType.DRIP) {
          data[index][2] = dividend.getStockDividend().getKoers();
        }
        index++;
      }
    }
  }
}
