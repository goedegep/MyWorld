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
  public void addObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener);

  /**
   * Remove a previously added listener to selecting an object.
   * 
   * @param objectSelectionListener the {@link ObjectSelectionListener} to be removed.
   */
  public void removeObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener);
  
  /**
   * Gets the currently selected object.
   * 
   * @return the currently selected object, or <code>null</code> is no object is selected.
   */
  public T getSelectedObject();
}
