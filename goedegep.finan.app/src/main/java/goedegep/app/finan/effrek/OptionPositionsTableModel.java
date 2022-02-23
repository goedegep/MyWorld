package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.OptionPosition;
import goedegep.finan.stocks.StockDepot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class OptionPositionsTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int TYPE_COLUMN = 0;
  private static final int SERIE_COLUMN = 1;
  private static final int AANTAL_COLUMN = 2;
  
  private static final String[] columnNames = {
      "Type",
      "Optie serie",
      "Aantal"
  };
  
  private static final Object[] longValues = {
      "call",
      "mmyy",
      "99999"
  };
  
  private StockDepot          depot = null;
  private Object[][]          data = null;
  
  public OptionPositionsTableModel(StockDepot depot) {
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
    case TYPE_COLUMN:
      return String.class;
      
    case SERIE_COLUMN:
      return String.class;

    case AANTAL_COLUMN:
      return Integer.class;

    default:
      return null;
    }
  }

  void fillData() {
//    Collection<OptionPosition> optionPositions = depot.getOptionPositions();
    List<OptionPosition> positions = new ArrayList<OptionPosition>(depot.getOptionPositions());

    if (positions.size() == 0) {
      data = new String[1][3];
      data[0][0] = "geen opties";
      data[0][1] = "";
      data[0][2] = "";
    } else {
      data = new Object[positions.size()][3];

      // create an alphabetically sorted list of the positions in the depot
      Collections.sort(positions, positions.get(0));
      
      // fill the positionData with the Name and Amount per position
      int index = 0;
      for (OptionPosition optionPosition: positions) {
        data[index][0] = optionPosition.getOptionSerie().getOptionType().getText();
        data[index][1] = optionPosition.getOptionSerie().getShare().getName();
        data[index][2] = optionPosition.getPosition();
        index++;
      }
      
    }
  }
}
