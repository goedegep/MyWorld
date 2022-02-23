package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockPosition;
import goedegep.types.model.DateRateTuplet;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class SharesOwnedTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int SHARE_COLUMN = 0;
  private static final int COUNT_COLUMN = 1;
  private static final int RATE_COLUMN = 2;
  
  private static final String[] columnNames = {
      "Aandeel",
      "Aantal",
      "Koers"
  };
  
  private static final Object[] longValues = {
      "Naam van een aandeel",
      9999999,
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99"
  };
  
  private StockDepot          depot = null;
  private LocalDate           statusDate = null; // Date for which rates are shown. If null, today.
  private List<StockPosition> stockPositions;
  private Object[][]          data = null;
  
  public SharesOwnedTableModel(StockDepot depot, LocalDate statusDate) {
    super(columnNames, longValues);
    this.depot = depot;
    this.statusDate = statusDate;
    
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
    case SHARE_COLUMN:
      return String.class;
      
    case COUNT_COLUMN:
      return Integer.class;

    case RATE_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }

  void fillData() {
    int nrOfPositions = depot.numberOfActivePostions() + depot.numberOfClaimRightPositions();

    if (nrOfPositions == 0) {
      data = new String[1][3];
      data[0][0] = "geen aandelen";
      data[0][1] = "";
      data[0][2] = "";
    } else {
      data = new Object[nrOfPositions][3];
      
      // create an alphabetically sorted list of the positions in the depot
      stockPositions = new ArrayList<StockPosition>(depot.getActiveStockPositions());
      Collections.sort(stockPositions);

      // fill the positionData with the Name and Amount per position
      int index = 0;
      
      for (StockPosition p: stockPositions) {
        data[index][0] = p.getStock().getName();
        data[index][1] = p.getCurrentAmount();
        if (statusDate == null) {
          data[index++][2] = p.getStock().getBestRate().getRate();
        } else {
          DateRateTuplet tuplet = p.getStock().getBestRate(statusDate);
          if (tuplet != null) {
            data[index++][2] = tuplet.getRate();
          }
        }
      }
      
      Set<ClaimEmission> claimEmissions = depot.getClaimRightPositions();
      for (ClaimEmission claimEmission : claimEmissions) {
        data[index][0] = claimEmission.getShare().getName() + " " + claimEmission.getId();
        data[index][1] = depot.getClaimRightPosition(claimEmission);
        data[index++][2] = claimEmission.getRightRate();
      }
    }
  }

  public Share getShare(int row) {
    if (stockPositions == null) {
      return null;
    }
    
    if (row < stockPositions.size()) {
      return stockPositions.get(row).getStock();
    } else {
      return null;
    }
  }
}
