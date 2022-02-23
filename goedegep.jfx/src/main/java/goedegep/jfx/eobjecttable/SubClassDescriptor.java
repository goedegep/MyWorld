package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;

public class SubClassDescriptor<T extends EObject> {
  private String displayName;
  private EObjectEditor<T> editor;

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