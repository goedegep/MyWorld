package goedegep.jfx.editor;

/**
 * This is the interface for a panel for editing a (child) object. Such a panel is used as a part of an {@link Editor}.
 * <p>
 * Clients registered as listener are only notified about any status changes.
 */
public interface EditPanel<T> extends EditorMultiControl<T> {
  
  
}
