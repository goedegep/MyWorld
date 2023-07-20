package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.text.Indent;

/**
 * This class contains all data needed to be stored in a tree item:
 * <ul>
 * <li>
 * eObjectTreeItemType<br/>
 * Specifies the type of information stored in this item. See {@link EObjectTreeItemType} for details.
 * </li>
 * <li>
 * object<br/>
 * The actual value. In this case an EObject or an attribute value, .....<br/>
 * TODO when is this filled in.
 * </li>
 * <li>
 * eStructuralFeature<br/>
 * To link a feature of the EObject to this item.<br/>
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
   * The type of information stored in the item. This value determines what is stored in the other fields.
   */
  private EObjectTreeItemType eObjectTreeItemType = null;
  
  /**
   * The value of this node. There are also nodes with attribute values, so this has to be an Object (and cannot be an EObject).
   */
  private Object object = null;
  
  /**
   * The EStructuralFeature represented by this item.
   */
  private EStructuralFeature eStructuralFeature = null;
  
  /**
   * The presentation information for this item.
   */
  private EObjectTreeItemDescriptor presentationDescriptor;
  
  /**
   * A reference back to the tree view to which the item with this content belongs.
   */
  private EObjectTreeView eObjectTreeView;
  
  /**
   * Constructor for items that don't need an eStructuralFeature.
   * 
   * @param object the value for this node. (mandatory)
   * @param eObjectTreeItemType the node type. (mandatory)
   * @param presentationDescriptorTreeNode a TreeNode with a specification for the presentation of the value of this node. (mandatory)
   */
  public EObjectTreeItemContent(EObject eObject, EObjectTreeItemType eObjectTreeItemType, EObjectTreeItemDescriptor presentationDescriptorTreeNode,
      EObjectTreeView eObjectTreeView) {
    this(eObject, eObjectTreeItemType, null, presentationDescriptorTreeNode, eObjectTreeView);
  }
  
  /**
   * Constructor where all attributes are specified.
   * 
   * @param object the value for this node. (mandatory)
   * @param eObjectTreeItemType the node type. (mandatory)
   * @param eStructuralFeature the structural feature for this node. (only mandatory for the types ...)
   * @param presentationDescriptor a TreeNode with a specification for the presentation of the value of this node. (mandatory)
   */
  public EObjectTreeItemContent(Object object, EObjectTreeItemType eObjectTreeItemType, EStructuralFeature eStructuralFeature,
      EObjectTreeItemDescriptor presentationDescriptor, EObjectTreeView eObjectTreeView) {
    if (eObjectTreeItemType == null) {
      throw new IllegalArgumentException("eObjectTreeItemType cannot be null");
    }
    if ((object == null)  &&
        (eObjectTreeItemType == EObjectTreeItemType.ATTRIBUTE_LIST)  &&
        (eObjectTreeItemType == EObjectTreeItemType.ATTRIBUTE_LIST_VALUE)  &&
        (eObjectTreeItemType == EObjectTreeItemType.OBJECT)  &&
        (eObjectTreeItemType == EObjectTreeItemType.OBJECT_LIST)) {
      throw new IllegalArgumentException("object cannot be null for eObjectTreeItemType=" + eObjectTreeItemType.name());
    }
    if ((presentationDescriptor == null)  &&  (eObjectTreeItemType != EObjectTreeItemType.ATTRIBUTE_LIST_VALUE)  &&
        (eObjectTreeItemType != EObjectTreeItemType.OBJECT)) {
      throw new IllegalArgumentException("presentationDescriptor cannot be null. object=" +
                                         (object != null ? object.toString() : "(null)") +
                                         ", eObjectTreeItemType=" + eObjectTreeItemType.name());
    }
    this.object = object;
    this.eObjectTreeItemType = eObjectTreeItemType;
    this.eStructuralFeature = eStructuralFeature;
    this.presentationDescriptor = presentationDescriptor;
    this.eObjectTreeView = eObjectTreeView;
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
  
  /**
   * Get the EObjectTreeItemType of this item.
   * 
   * @return the EObjectTreeItemType of this item.
   */
  public EObjectTreeItemType getEObjectTreeItemType() {
    return eObjectTreeItemType;
  }

  /**
   * Get the EStructuralFeature of this item.
   * 
   * @return the EStructuralFeature of this item.
   */
  public EStructuralFeature getEStructuralFeature() {
    return eStructuralFeature;
  }
  
  /**
   * Get the presentation information of this item.
   * 
   * @return the presentation information of this item.
   */
  public EObjectTreeItemDescriptor getPresentationDescriptor() {
    return presentationDescriptor;
  }
  
  /**
   * Get the related EObjectTreeView.
   * 
   * @return the tree view to which the item with this content belongs.
   */
  public EObjectTreeView geteObjectTreeView() {
    return eObjectTreeView;
  }

  /**
   * Check whether this item is in edit mode or not.
   * 
   * @return true if the tree is in edit mode, false otherwise.
   */
  public boolean isInEditMode() {
    return eObjectTreeView.isEditable();
  }

  @Override public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("EObjectTreeItemContent: ").append(NEWLINE);
    
    buf.append("eObjectTreeItemType=").append(eObjectTreeItemType.name()).append(NEWLINE);
    switch (eObjectTreeItemType) {
    case OBJECT:
      buf.append("Object: ");
      if (object != null) {
        buf.append(object.getClass().getSimpleName());
      } else {
        buf.append("(null)");
      }
      buf.append(NEWLINE);
      buf.append("eStructuralFeature: ");
      if (eStructuralFeature != null) {
        buf.append(eStructuralFeature.getName());
      } else {
        buf.append("(null)");
      }
      break;
      
    case OBJECT_LIST:
      buf.append(eStructuralFeature.getName());
      break;
      
    case ATTRIBUTE_SIMPLE:
      buf.append(eStructuralFeature.getName());
      buf.append("=");
      if (object != null) {
        buf.append(object.toString());
      } else {
        buf.append("null");
      }
      break;
      
    case ATTRIBUTE_LIST:
      buf.append(eStructuralFeature.getName());
      break;
      
    case ATTRIBUTE_LIST_VALUE:
      buf.append(object != null ? object.toString() : "(null)");
      break;
    }
    buf.append(NEWLINE);
    
    if (presentationDescriptor != null) {
      buf.append("presentationDescriptor=" + presentationDescriptor.toString(new Indent(4)));
    }
    
    return buf.toString();
  }
  
}
