package goedegep.invandprop.app;

import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;

public class SelectionAdapter<T> implements ObjectSelectionListener<T> {
  
  public SelectionAdapter(ObjectSelector<T> objectSource) {
    objectSource.addObjectSelectionListener(this);
  }

  public void objectSelected(T object) {
    // TODO Auto-generated method stub
    
  }
  
}
