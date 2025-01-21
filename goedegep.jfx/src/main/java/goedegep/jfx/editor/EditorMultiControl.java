package goedegep.jfx.editor;

/**
 * This is an interface for a collection of controls for handling a single object.
 * <p>
 * An implementation will provide separate methods to obtain the different controls and show them in the GUI (typically an {@code EditPanel}).
 */
public interface EditorMultiControl<T> extends EditorComponent<T>  {
  EditorComponent<?> determineValidity();

  
  /**
   * Get the first invalid {@code EditorComponent}.
   * <p>
   * An editor will usually not list all errors at once. With this method the editor can inform the user about the first invalid control.
   * 
   * @return The first invalid control, or {@code null} if all controls are valid.
   */
  EditorComponent<?> getFirstInvalidControl();
  
  /**
   * Start editing a new object.
   */
  void newObject();
  
  /**
   * Start editing an object.
   * 
   * @param object the object.
   */
  void setObject(T object);
  
  /**
   * Update the object according to the values entered by the user.
   * 
   * @return The updated object
   * @throws EditorException in case the object cannot be updated, typically when the value of a control cannot be set as value of an attribute of the object.
   */
  T accept() throws EditorException;
}
