package goedegep.appgen.swing;

import java.util.List;

@SuppressWarnings("serial")
public abstract class ObjectTableModel<T> extends AppGenAbstractTableModel {
  private ObjectTableColumnDescriptor[] columnDescriptors;
  private List<T> objects;

  protected ObjectTableModel(ObjectTableColumnDescriptor[] columnDescriptors, List<T> objects) {
    super();
    
    this.columnDescriptors = columnDescriptors;
    String[] columnNames = new String[columnDescriptors.length];
    Object[] longValues = new Object[columnDescriptors.length];
    
    for (int i = 0; i < columnDescriptors.length; i++) {
      columnNames[i] = columnDescriptors[i].getColumnName();
      longValues[i] = columnDescriptors[i].getLongValue();
    }
    
    setColumnNames(columnNames);
    setLongValues(longValues);

    this.objects = objects;
  }

  protected ObjectTableModel(String[] columnNames, Object[] longValues, List<T> objects) {
    super();
    
    setColumnNames(columnNames);
    setLongValues(longValues);

    this.objects = objects;
  }
  
  public int getRowCount() {
    return objects.size();
//    if (data != null) {
//      return data.length;
//    } else {
//      return 0;
//    }
  }

  public abstract Object getValueAt(int row, int col);
//  {
//    return data[row][col];
//  }
  
  /*
   * JTable uses this method to determine the default renderer/
   * editor for each cell.  If we didn't implement this method,
   * then the last column would contain text ("true"/"false"),
   * rather than a check box.
   */
  public Class<?> getColumnClass(int col) {
    ObjectTableColumnDescriptor columnDescriptor = columnDescriptors[col];
    return columnDescriptor.getFeatureClass();
//    EStructuralFeature feature = eObject.eClass().getEStructuralFeature(columnDescriptor.getFeatureName());
    
//    switch (col) {
//    case FIRSTNAME_COLUMN:
//    case INFIX_COLUMN:
//    case SURNAME_COLUMN:
//    case INSTITUTION_NAME_COLUMN:
//    case PHONE_NUMBERS_COLUMN:
//      return String.class;

//    default:
//      return null;
//    }
  }
   
  public abstract T getRowObject(int row);
}
