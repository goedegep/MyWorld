package goedegep.jfx.eobjecttreeview;

/**
 * This class is a TreeItem for single item of an attribute list.
 * <p>
 * The item typically only shows the value.<br/>
 * This class has no EAttribute. Instead the attribute is obtained from the parent item.
 *
 */
public class EObjectTreeItemForAttributeListValue extends EObjectTreeItem {
  
  private EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor;

  public EObjectTreeItemForAttributeListValue(Object object, EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.ATTRIBUTE_LIST_VALUE, eObjectTreeView);
    
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null for an EObjectTreeItemForAttributeListValue");
    }
  }

  public EObjectTreeItemAttributeListValueDescriptor getEObjectTreeItemAttributeListValueDescriptor() {
    return eObjectTreeItemAttributeListValueDescriptor;
  }
  
}
