package goedegep.jfx.eobjecttreeview;

/**
 * This class is a TreeItem for an object.
 * <p>
 * The item typically only shows the name of the object or an alternative name.
 * For example if the object is a Person, but in the model it's an employee, you could show 'employee' (via the descriptor).
 *
 */
public class EObjectTreeItemForObjectInList extends EObjectTreeItem {
  
  private EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor;
  
  public EObjectTreeItemForObjectInList(Object object,
      EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.OBJECT_IN_LIST, eObjectTreeView);
    
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null for EObjectTreeItemForObject");
    }
    
    this.eObjectTreeItemClassDescriptor = eObjectTreeItemClassDescriptor;
  }
  
  public EObjectTreeItemClassDescriptor getEObjectTreeItemClassDescriptor() {
    return eObjectTreeItemClassDescriptor;
  }

  public String toString() {
    return super.toString();
  }
  
}
