package goedegep.appgen;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class PgTableModel extends AbstractTableModel {
  
  private Object[][]   data;
  private String[]     columnNames;

  public void setData(Object[][] data) {
    this.data = data;
  }

  public void setDataAt(Object value, int row, int column) {
    data[row][column] = value;
  }

  public void setColumnNames(String[] columnNames) {
    this.columnNames = columnNames;
  }

  public int getRowCount() {
    if (data == null) {
      return 0;
    } else {
      return data.length;
    }
  }

  public int getColumnCount() {
    if (data == null) {
      return 0;
    } else {
      return data[0].length;
    }
  }

  public Object getValueAt(int row, int column) {
    return data[row][column];
  }

  public String getColumnName(int columnIndex) {
    return columnNames[columnIndex];
  }

//  public Class getColumnClass(int columnIndex) {
//    Object obj = getValueAt(0, columnIndex);
//    if (obj == null) {
//      System.out.println("ERROR: In PgTableModel.getColumnClass, value is null for row = " +
//                         0 + ", column = " + columnIndex);
//    }
//    return getValueAt(0, columnIndex).getClass();
//  }
}