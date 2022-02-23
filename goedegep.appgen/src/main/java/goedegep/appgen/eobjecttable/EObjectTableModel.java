package goedegep.appgen.eobjecttable;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.appgen.swing.AppGenAbstractTableModel;

@SuppressWarnings("serial")
public class EObjectTableModel<T extends EObject> extends AppGenAbstractTableModel {
  private static final Logger         LOGGER = Logger.getLogger(EObjectTableModel.class.getName());
  
  private EObjectTableColumnDescriptor[] columnDescriptors;
  private List<T>                        objects;
  private Object[][]                     data;
  private boolean                        newItemRow = false;
  private EObjectJTable<T>                table;

  public EObjectTableModel(EObjectJTable<T> table, EObjectTableColumnDescriptor[] columnDescriptors, List<T> objects) {
    super();

    this.table = table;
    this.columnDescriptors = columnDescriptors;
    String[] columnNames = new String[columnDescriptors.length];
    Object[] longValues = new Object[columnDescriptors.length];

    for (int i = 0; i < columnDescriptors.length; i++) {
      columnNames[i] = columnDescriptors[i].getColumnName();
      longValues[i] = columnDescriptors[i].getLongValue();
    }

    setColumnNames(columnNames);
    setLongValues(longValues);
    
    setObjects(objects);
  }
  
  public void setObjects(List<T> objects) {
    this.objects = objects;

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
    if ((row == -1)  ||  (col == -1)) {
      return null;
    }
    
    if ((row >= data.length)  ||  (col >= data[row].length)) {
      return null;
    }
    
    return data[row][col];
  }

  public void setValueAt(Object aValue, int row, int column) {
    
    if (row == objects.size()) {
      // a value is added for the new item, create it.
      T eObject = table.createItem();
      objects.add(eObject);
      
      int col = 0;
      for (EObjectTableColumnDescriptor columnDescriptor: columnDescriptors) {
        // Fill in the data value.
        if (eObject.eIsSet(columnDescriptor.geteStructuralFeature())) {
          data[row][col] = eObject.eGet(columnDescriptor.geteStructuralFeature());
        } else {
          data[row][col] = null;
        }
        
        col++;
      }
    }
    
    EObjectTableColumnDescriptor columnDescriptor = columnDescriptors[column];
    EStructuralFeature eStructuralFeature = columnDescriptor.geteStructuralFeature();
    EObject eObject = (EObject) objects.get(row);
    
   LOGGER.info("=> Object = " + eObject);
    if (eObject.eGet(eStructuralFeature) instanceof String) {
      String string = (String) aValue;
      if (string == null  ||  (string.trim().length() == 0)) {
        eObject.eSet(eStructuralFeature, null);
        eObject.eUnset(eStructuralFeature);
      } else {
        eObject.eSet(eStructuralFeature, aValue);
      }
    } else if (eObject.eGet(eStructuralFeature) instanceof Enumerator  &&
        aValue instanceof String) {
      eObject.eSet(eStructuralFeature, null);
      eObject.eUnset(eStructuralFeature);
    } else {
      LOGGER.info("eStructuralFeature = " + eStructuralFeature.getName());
      LOGGER.info("aValue = " + aValue);
      LOGGER.info("isMany = " + eStructuralFeature.isMany());
      if (eStructuralFeature.isMany()) {
        if (aValue != null) {
          @SuppressWarnings("unchecked")
          List<Object> valueList = (List<Object>) eObject.eGet(eStructuralFeature);
          valueList.add(aValue);
        }
      } else {
        if (aValue != null) {
          eObject.eSet(eStructuralFeature, aValue);
        } else {
          eObject.eUnset(eStructuralFeature);
        }
      }
    }
    
    data[row][column] = getObjectColumnValue(row, column, eObject, columnDescriptor);
    
    fireTableCellUpdated(row, column);
    fireTableRowsUpdated(row, row);
  }
  
  /*
   * JTable uses this method to determine the default renderer/
   * editor for each cell.  If we didn't implement this method,
   * then the last column would contain text ("true"/"false"),
   * rather than a check box.
   */
  public Class<?> getColumnClass(int col) {
    EObjectTableColumnDescriptor columnDescriptor = columnDescriptors[col];
    EStructuralFeature structuralFeature = columnDescriptor.geteStructuralFeature();
    if (structuralFeature instanceof EAttribute) {
      EAttribute eAttribute = (EAttribute) structuralFeature;
      if (eAttribute.isMany()) {
        LOGGER.info("<= EAttribute.Many.class = " + EList.class + ", col = " + col);
        return EList.class;
      }
      if (eAttribute.getEType().getInstanceClass() == boolean.class) {
        LOGGER.info("<= class = " + Boolean.class);
        return Boolean.class;
      } else if (eAttribute.getEType().getInstanceClass() == float.class) {
        LOGGER.info("<= class = " + Float.class);
        return Float.class;
      } else if (eAttribute.getEType().getInstanceClass() == int.class) {
        LOGGER.info("<= class = " + Integer.class);
        return Integer.class;
      }
      LOGGER.info("<= EAttribute.class = " + eAttribute.getEType().getInstanceClass() + ", col = " + col);
      return eAttribute.getEType().getInstanceClass();
    } else if (structuralFeature instanceof EReference) {
      EReference eReference = (EReference) structuralFeature;
      LOGGER.info("<= EReference.class = " + eReference.getEReferenceType().getInstanceClass() + ", col = " + col);
      return eReference.getEReferenceType().getInstanceClass();
    }
    
    LOGGER.info("<= class = " + Object.class);
    return Object.class;
  }

  public boolean isCellEditable(int row, int col) {
    EObjectTableColumnDescriptor columnDescriptor = columnDescriptors[col];
    
    LOGGER.info("isCellEditable: " + row + ", " + col + " => " + columnDescriptor.isEditable());
    return columnDescriptor.isEditable();
  }
  
  public EObjectTableColumnDescriptor[] getColumnDescriptors() {
    return columnDescriptors;
  }
 
  public Integer fillData() {
    if (objects == null) {
      data = null;
      return null;
    }

    Object[][] oldData = data;
    Integer firstChangedRowIndex = null;
    int numberOfRows = objects.size();
    if (newItemRow) {
      numberOfRows++;
    }
    data = new Object[numberOfRows][getColumnNames().length];

    int row = 0;
    for (T object: objects) {
      LOGGER.info("in objects loop" + object.toString());
      EObject eObject = getActualObject(object);
      int column = 0;
      for (EObjectTableColumnDescriptor columnDescriptor: columnDescriptors) {
        // Fill in the data value.
        data[row][column] = getObjectColumnValue(row, column, eObject, columnDescriptor);
        
        // Check whether it has changed.
        if ((firstChangedRowIndex == null)  &&  // no changed row found yet
            (oldData != null)) {                // and we had data before
          if ((row >= oldData.length)  ||       // found new row
              (oldData[row][column] != data[row][column])) {  // found changed row
            firstChangedRowIndex = row;
          }
        }
        
        column++;
      }
               
      row++;
    }
    
    if (newItemRow) {
      data[row][0] = "<nieuwe waarde>";
    }

    return firstChangedRowIndex;
  }
  
  protected EObject getActualObject(T object) {
    return (EObject) object;
  }
  
  public Object getObjectColumnValue(int row, int column, EObject eObject, EObjectTableColumnDescriptor columnDescriptor) {
    EStructuralFeature structuralFeature = columnDescriptor.geteStructuralFeature();
    if (structuralFeature != null) {
      return eObject.eGet(columnDescriptor.geteStructuralFeature());
    } else {
      return null;
    }
  }
  
  public T getRowObject(int row) {
    if (row < objects.size()) {
      return objects.get(row);
    } else {
      return null; // new item row is selected.
    }
  }
  
  public int findObject(T object) {
    return objects.indexOf(object);
  }
}