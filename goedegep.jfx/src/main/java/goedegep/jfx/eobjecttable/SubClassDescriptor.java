package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;

/**
 * Descriptor for sub-classes of the class that is shown in an {@code EObjectTable}.
 * <p>
 * Sub-classes will have extra attributes to be shown or edited.
 * 
 * @param <T> The sub-class type to which this descriptor applies.
 */
public class SubClassDescriptor<T extends EObject> {
  private String displayName;
  private EObjectEditor<T> editor;

  /**
   * Constructor.
   * 
   * @param displayName 
   * @param editor
   */
  public SubClassDescriptor(String displayName, EObjectEditor<T> editor) {
    this.displayName = displayName;
    this.editor = editor;
  }

  public String getDisplayName() {
    return displayName;
  }

  public EObjectEditor<T> getEditor() {
    return editor;
  }

}