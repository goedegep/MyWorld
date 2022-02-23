package goedegep.appgen.swing;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("serial")
public abstract class ObjectTableModelPreFilled<T> extends AppGenAbstractTableModel {
  private ObjectTableColumnDescriptor[] columnDescriptors;
  private List<T> objects;
  private Object[][] data;

  protected ObjectTableModelPreFilled(ObjectTableColumnDescriptor[] columnDescriptors, List<T> objects) {
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
        
    fillData();
  }

  protected ObjectTableModelPreFilled(String[] columnNames, Object[] longValues, List<T> objects) {
    super();
    
    setColumnNames(columnNames);
    setLongValues(longValues);

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
  
  public Integer fillData() {
    if (objects == null) {
      data = null;
      return 0;
    }
    
    Object[][] oldData = data;
    Integer firstChangedRowIndex = null;
    data = new Object[objects.size()][getColumnNames().length];

    int row = 0;
    for (T object: objects) {
      EObject eObject = (EObject) object;
      int column = 0;
      for (ObjectTableColumnDescriptor columnDescriptor: columnDescriptors) {
        // Fill in the data value.
        EStructuralFeature feature = eObject.eClass().getEStructuralFeature(columnDescriptor.getFeatureName());
        data[row][column] = eObject.eGet(feature);
        
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
   
//      
//      List<PhoneNumber> phoneNumbers = employee.getPhoneNumbers();
//      RolodexFactory rolodexFactory = RolodexFactory.eINSTANCE;
//      PhoneNumberList phoneNumberList = rolodexFactory.createPhoneNumberList();
//      List<PhoneNumber> nl = phoneNumberList.getPhoneNumbers();
//      for (PhoneNumber phoneNumber: phoneNumbers) {
//        nl.add(phoneNumber);
//      }
//      data[index][PHONE_NUMBERS_COLUMN] = phoneNumberList.toString();

//      if ((firstChangedRowIndex == null)  &&  (oldData != null)) {
//        if ((index >= oldData.length)  ||
//            (oldData[index][0] != data[index][0])  ||  (oldData[index][1] != data[index][1])  ||  (oldData[index][2] != data[index][2])  ||  
//            (oldData[index][3] != data[index][3])  ||  (oldData[index][4] != data[index][4])) {
//          firstChangedRowIndex = index;
//        }
//      }
            
      row++;
    }

    return firstChangedRowIndex;
  }
  
  public abstract T getRowObject(int row);
}