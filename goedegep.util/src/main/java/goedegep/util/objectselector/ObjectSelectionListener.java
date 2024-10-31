package goedegep.util.objectselector;

/**
 * This functional interface defines the method which is called by an {@link ObjectSelector} when a new object is selected.
 *
 * @param <T>
 */
@FunctionalInterface
public interface ObjectSelectionListener<T> {
  /**
   * The method which is called when a new object is selected.
   * 
   * @param object the newly selected object.
   */
  void objectSelected(Object source, T object);
}
