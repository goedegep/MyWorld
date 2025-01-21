package goedegep.jfx.editor;

/**
 * This is the interface for an editor control.
 * 
 * @param <T> The type handled by the control.
 */
public interface EditorControl<T> extends EditorComponent<T> {
  
  /**
   * Set the value of the control.
   * 
   * @param value the value of the control.
   */
  void setObject(final T value);

  /**
   * Get the value of the control.
   * 
   * @return the value of the control.
   */
  T getValue();
}
