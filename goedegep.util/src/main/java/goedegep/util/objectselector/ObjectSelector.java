package goedegep.util.objectselector;

/**
 * An interface which may be implemented by classes which can select an object.
 * <p>
 * For example: If a table shows objects as rows of the table, selecting a row of the table is also selecting an object.
 *  
 * @param <T> the object type
 */
public interface ObjectSelector<T> {

  /**
   * Add a listener to selecting an object.
   * 
   * @param objectSelectionListener the {@link ObjectSelectionListener} to be added.
   */
  void addObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener);

  /**
   * Remove a previously added listener to selecting an object.
   * 
   * @param objectSelectionListener the {@link ObjectSelectionListener} to be removed.
   */
  void removeObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener);
  
  /**
   * Gets the currently selected object.
   * 
   * @return the currently selected object, or <code>null</code> is no object is selected.
   */
  T getSelectedObject();
  
  /**
   * Set the currently selected object.
   * 
   * @param object The object that shall be selected.
   * @return true if the object could be selected, false otherwise.
   */
  default boolean selectObject(T object) {
    throw new UnsupportedOperationException("selectObject is not implemented.");
  }
}
