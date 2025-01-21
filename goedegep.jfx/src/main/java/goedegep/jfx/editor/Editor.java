package goedegep.jfx.editor;

import javafx.beans.Observable;

/**
 * This is the (small) interface for an editor.
 * <h4>Rules for implementing an {@code Editor}</h4>
 * <ul>
 * <li>
 * Upon creation the editor shall be in the NEW mode.
 * </li>
 * <li>
 * Upon creation the editor doesn't show. For this the client has to call the {@code show()} method.
 * </li>
 * </ul>
 * <h4>Adviced way to implement an {@code Editor}</h4>
 * <ul>
 * <li>
 * Extend the {@code EditorTemplate} class.
 * </li>
 * <li>
 * Have a {@code newInstance()} method to create an instance of the editor.<br/>
 * The first parameter is a {@code customizationFx} object.</br>
 * The second parameter is typically the method to add a new object or a service which has such a method. This is needed for the {@code addObjectMethod()} parameter of the constructor of the {@code EditorTemplate} class.<br/>
 * This method calls the constructor and then the {@code performInitialization()} method of the {@code EditorTemplate} class.
 * </li>
 * <li>
 * Make the constructor {@code private}.
 * </li>
 * </ul>
 * <h4>Using an editor</h4>
 * <ul>
 * <li>
 * Create an instance of the editor by calling the {@code newInstance()} method.
 * </li>
 * <li>
 * Set the object to be edited<br/>
 * Upon creation, the editor is in the 'NEW' mode. So if you are going to edit an object you have to set the object by calling {@code setObject()}.
 * </li>
 * <li>
 * If you want to react to the current status of the editor, add a listener by calling {@code addListener()}.
 * </li>
 * <li>
 * Show the editor by calling {@code show()}.
 * </li>
 * </ul>
 * 
 * @param T the data type to edit with the editor.
 */
public interface Editor<T> extends Observable {

  /**
   * Fill the editor with an object.
   * <p>
   * The editor doesn't make any changes to the object until the user presses the 'update' button.
   * 
   * @param object the value to set in the editor.
   */
  void setObject(T object);

  /**
   * Show the editor.
   */
  void show();

  /**
   * Remove all invalidation listeners.
   * <p>
   * {@link Observable} has a removeListener method. This is a convenience method to remove all listeners at once, e.g. when the editor is deleted.
   */
  void removeListeners();
}
