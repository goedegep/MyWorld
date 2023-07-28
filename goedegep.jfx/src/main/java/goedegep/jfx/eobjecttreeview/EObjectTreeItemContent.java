package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

/**
 * This class contains all data needed to be stored in a tree item:
 * <ul>
 * <li>
 * object<br/>
 * The actual value. In this case an EObject or an attribute value, .....<br/>
 * TODO when is this filled in.
 * </li>
 * <li>
 * presentationDescriptor<br/>
 * This reference to a {@link EObjectTreeItemDescriptor} provides the presentation information.
 * </li>
 * </ul>
 * 
 * <b>Note:</b> The method updateItem in TreeCell gets the content of an item (and not the TreeItem). Therefore information needed by updateItem has to be part of the content
 * (this class).
 */
public class EObjectTreeItemContent {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemContent.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The value of this node. There are also nodes with attribute values, so this has to be an Object (and cannot be an EObject).
   */
  private Object object = null;
  
//  /**
//   * The presentation information for this item.
//   */
//  private EObjectTreeItemDescriptor presentationDescriptor;
    
  /**
   * Constructor.
   * 
   * @param object the value for this node. (mandatory)
   * @param presentationDescriptor a TreeNode with a specification for the presentation of the value of this node. (mandatory)
   */
  public EObjectTreeItemContent(Object object) {
    this.object = object;
//    this.presentationDescriptor = presentationDescriptor;
    LOGGER.info("<=> " + toString());
  }

  /**
   * Get the object of this item.
   * 
   * @return the object of this item.
   */
  public Object getObject() {
    return object;
  }

  /**
   * Set the object of this item.
   * 
   * @param object the new value for the object of this item.
   */
  public void setObject(Object object) {
    // Object is set to null when the object of a tree item is deleted.
//    if (object == null) {
//      throw new IllegalArgumentException("object cannot be null");
//    }
    this.object = object;
  }

//  /**
//   * Get the presentation information of this item.
//   * 
//   * @return the presentation information of this item.
//   */
//  public EObjectTreeItemDescriptor getPresentationDescriptor() {
//    return presentationDescriptor;
//  }  

  @Override public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("EObjectTreeItemContent: ").append(NEWLINE);
    
//    buf.append("eObjectTreeItemType=").append(eObjectTreeItemType.name()).append(NEWLINE);
//    switch (eObjectTreeItemType) {
//    case OBJECT:
//      buf.append("Object: ");
//      if (object != null) {
//        buf.append(object.getClass().getSimpleName());
//      } else {
//        buf.append("(null)");
//      }
//      buf.append(NEWLINE);
//      buf.append("eStructuralFeature: ");
//      if (eStructuralFeature != null) {
//        buf.append(eStructuralFeature.getName());
//      } else {
//        buf.append("(null)");
//      }
//      break;
//      
//    case OBJECT_LIST:
//      buf.append(eStructuralFeature.getName());
//      break;
//      
//    case ATTRIBUTE_SIMPLE:
//      buf.append(eStructuralFeature.getName());
//      buf.append("=");
//      if (object != null) {
//        buf.append(object.toString());
//      } else {
//        buf.append("null");
//      }
//      break;
//      
//    case ATTRIBUTE_LIST:
//      buf.append(eStructuralFeature.getName());
//      break;
//      
//    case ATTRIBUTE_LIST_VALUE:
//      buf.append(object != null ? object.toString() : "(null)");
//      break;
//    }
//    buf.append(NEWLINE);
    
//    if (presentationDescriptor != null) {
//      buf.append("presentationDescriptor=" + presentationDescriptor.toString(new Indent(4)));
//    }
    
    return buf.toString();
  }
  
}
