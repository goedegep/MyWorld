package goedegep.appgen.swing;


import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class provides the basis for a table where each row shows the attributes
 * of an object. This means that selecting a row in the table, implies selecting
 * an object. Therefore this table implements the ObjectSelector interface.
 * @author Peter
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public class ObjectTable<T> extends AppGenAbstractTable implements ObjectSelector<T> {
  private List<ObjectSelectionListener<T>> objectSelectionListeners = new ArrayList<ObjectSelectionListener<T>>();

  public ObjectTable(AppFrame owner, int width, int height,
      ObjectTableDescriptor objectTableDescriptor, List<T> objects) {
    super(owner, width, height);
    
    initObjectSelectionListening();
  }
  
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
  }
  
  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }
  
  private void initObjectSelectionListening() {
    getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          handleValueChanged();
        }
      }
    });
  }
  
  private void handleValueChanged() {
    T selectedObject = getSelectedObject();
    
    for (ObjectSelectionListener<T> objectSelectionListener: objectSelectionListeners) {
      objectSelectionListener.objectSelected(selectedObject);
    }
  }

  /**
   * Get the first selected object (row) in the table.
   * 
   * @return the first selected object.
   */
  public T getSelectedObject() {
    JTable table = getTable();
    
    int[] selectedViewRows = table.getSelectedRows();
    
    if (selectedViewRows.length > 0) {
      int selectedModelRow = table.convertRowIndexToModel(selectedViewRows[0]);
      @SuppressWarnings("unchecked")
      T rowObject = ((ObjectTableModel<T>) table.getModel()).getRowObject(selectedModelRow);
      return rowObject;
    } else {
      return null;
    }
  }
}
