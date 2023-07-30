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

  /**
   * {@inheritDoc}
   */
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("classDescriptor: ").append(eObjectTreeItemClassDescriptor != null ? eObjectTreeItemClassDescriptor : "<null>").append(NEWLINE);
    
    return buf.toString();
    
  }
  

//  @Override
//  public String toString() {
//    StringBuilder buf = new StringBuilder();
//    
//    buf.append(super.toString());
//    buf.append("eReference: ").append(eReference != null ? eReference.getName() : "<null>").append(NEWLINE);
//    
////    buf.append("eObjectTreeItemType=").append(eObjectTreeItemType.name()).append(NEWLINE);
////    switch (eObjectTreeItemType) {
////    case OBJECT:
////      buf.append("Object: ");
////      if (object != null) {
////        buf.append(object.getClass().getSimpleName());
////      } else {
////        buf.append("(null)");
////      }
////      buf.append(NEWLINE);
////      buf.append("eStructuralFeature: ");
////      if (eStructuralFeature != null) {
////        buf.append(eStructuralFeature.getName());
////      } else {
////        buf.append("(null)");
////      }
////      break;
////      
////    case OBJECT_LIST:
////      buf.append(eStructuralFeature.getName());
////      break;
////      
////    case ATTRIBUTE_SIMPLE:
////      buf.append(eStructuralFeature.getName());
////      buf.append("=");
////      if (object != null) {
////        buf.append(object.toString());
////      } else {
////        buf.append("null");
////      }
////      break;
////      
////    case ATTRIBUTE_LIST:
////      buf.append(eStructuralFeature.getName());
////      break;
////      
////    case ATTRIBUTE_LIST_VALUE:
////      buf.append(object != null ? object.toString() : "(null)");
////      break;
////    }
////    buf.append(NEWLINE);
//    
////    if (presentationDescriptor != null) {
////      buf.append("presentationDescriptor=" + presentationDescriptor.toString(new Indent(4)));
////    }
//    
//    return buf.toString();
//    
//  }
}
