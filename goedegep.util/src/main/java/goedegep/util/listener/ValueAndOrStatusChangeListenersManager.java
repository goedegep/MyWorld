package goedegep.util.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a class that maintains a collection of {@link ValueAndOrStatusChangeListener}s.
 *  
 * @param <T> the value type.
 */
public class ValueAndOrStatusChangeListenersManager {
  
  /**
   * List to hold references to the listeners.
   */
  private final List<ValueAndOrStatusChangeListener> listeners = new ArrayList<>();

  /**
   * Method to add a listener.
   * 
   * @param listener the listener to be added.
   */
  public void addValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener listener) {
    listeners.add(listener);
  }

  /**
   * Method to remove a listener.
   * 
   * @param listener the listener to be removed.
   */
  public void removeValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener listener) {
    Iterator<ValueAndOrStatusChangeListener> iterator = listeners.iterator();
    while (iterator.hasNext()) {
      ValueAndOrStatusChangeListener l = iterator.next();
      if (l == null || l.equals(listener)) {
        iterator.remove();
      }
    }
  }
  
  /**
   * Remove all listeners.
   */
  public void removeValueAndOrStatusChangeListeners() {
    listeners.clear();
  }

  /**
   * Method to notify all listeners
   * @param newValue
   */
  public void notifyListeners(boolean valueChanged, boolean statusChanged) {    
    Iterator<ValueAndOrStatusChangeListener> iterator = listeners.iterator();
    while (iterator.hasNext()) {
      ValueAndOrStatusChangeListener listener = iterator.next();
      if (listener != null) {
        listener.valueAndOrStatusChanged(valueChanged, statusChanged);
      } else {
        iterator.remove();
      }
    }
  }
  
}
