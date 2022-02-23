package goedegep.appgen.swing;

import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public abstract class AppGenAbstractTableModel extends DefaultTableModel {
  private static final Logger LOGGER = Logger.getLogger(AppGenAbstractTableModel.class.getName());
  
  private String[] columnNames;
  private Object[] longValues;
  
  private AppGenAbstractTable table;  // TODO is this needed.
  
  protected AppGenAbstractTableModel() {
    this.columnNames = null;
    this.longValues = null;
    LOGGER.fine("<= default constructor");
  }
  
  protected AppGenAbstractTableModel(String[] columnNames, Object[] longValues) {
    this.columnNames = columnNames;
    this.longValues = longValues;
    LOGGER.fine("<= columnName.length=" + columnNames.length + ", longValues.length=" + longValues.length);
  }
  
  public String[] getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(String[] columnNames) {
    this.columnNames = columnNames;
  }


  public Object[] getLongValues() {
    return longValues;
  }
  
  public void setLongValues(Object[] longValues) {
    this.longValues = longValues;
  }

  public int getColumnCount() {
    if (columnNames != null) {
      LOGGER.fine("OK: columnNames.length:" + columnNames.length);
      return columnNames.length;
    } else {
      LOGGER.severe("ERROR: columnNames not set yet");
      return 0;
    }
  }

  public String getColumnName(int col) {
    return "<HTML><B>" + columnNames[col] + "</HTML>" ;
  }
  
  public void setTable(AppGenAbstractTable table) {
    this.table = table;
  }
  
  public AppGenAbstractTable getTable() {
    return table;
  }  
}
