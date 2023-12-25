package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * This class is a TreeItem for a simple attribute.
 * <p>
 * The item typically shows attribute name and value;<br/>
 * 
 * Content:
 * <ul>
 *   <li>
 *     eObjectTreeItemType: ATTRIBUTE_SIMPLE
 *   </li>
 *   <li>
 *     vakye: the attribute value
 *   </li>
 *   <li>
 *     eAttribute: the EAttribute for this attribute
 *   </li>
 *   <li>
 *     descriptor: the corresponding descriptor of type EObjectTreeItemAttributeDescriptor
 *   </li>
 * </ul>
 * This is a leaf node.
 * 
 *
 */
public class EObjectTreeItemForAttributeSimple extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForAttributeSimple.class.getName());
  
  private EAttribute eAttribute = null;
  
  private EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = null;

  /**
   * Constructor.
   * 
   * @param object
   * @param eStructuralFeature
   * @param presentationDescriptor
   * @param eObjectTreeView
   */
  public EObjectTreeItemForAttributeSimple(Object object, EAttribute eAttribute,
      EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.ATTRIBUTE_SIMPLE, eObjectTreeView);
    
    if (eObjectTreeItemAttributeDescriptor == null) {
      throw new IllegalArgumentException("presentationDescriptor cannot be null for EObjectTreeItemForAttributeSimple. object=" +
                                         (object != null ? object.toString() : "(null)"));
    }
    
    this.eAttribute = eAttribute;
    this.eObjectTreeItemAttributeDescriptor = eObjectTreeItemAttributeDescriptor;
  }
  
  /**
   * {@inheritDoc}
   * A simple attribute is always a leaf.
   */
  @Override
  boolean isItemALeafItem() {
    return true;
  }
  
  /**
   * {@inheritDoc}
   * This item is a leaf, it doesn't have any children.
   */
  @Override
  ObservableList<TreeItem<Object>> buildChildren() {
    return null;
  }  
  
  /**
   * Build a child for an EAttribute.
   * <p>
   * This applies only to 'single' attributes.<br/>
   * An attribute which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param eObjectTreeItemAttributeDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  static EObjectTreeItem createEObjectTreeItemForAttributeSimple(Object eObjectTreeItemContent, EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor, EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=> eObjectTreeItemContent" + eObjectTreeItemContent.toString());
        
    EObject eObject = (EObject) eObjectTreeItemContent;
    if (eObject == null) {
      LOGGER.severe("object is null in TreeItem");
    }
    EAttribute eAttribute = eObjectTreeItemAttributeDescriptor.getEAttribute();

    EList<EAttribute> objectAttributes = eObject.eClass().getEAllAttributes();
    if (objectAttributes.contains(eAttribute)) {
      if (editMode  ||  eObject.eIsSet(eAttribute)) {
        Object childObject = eObject.eGet(eAttribute);
        if (eAttribute.isMany()) {
           throw new RuntimeException("Descriptor doesn't match with reference, eAttribute=" + eAttribute.toString());
        }
        return new EObjectTreeItemForAttributeSimple(childObject, eAttribute, eObjectTreeItemAttributeDescriptor, eObjectTreeView);
      } else {
        return null;
      }
    } else {
      throw new RuntimeException("EObjectTreeItemDescriptor for non-existing attribute: " + eAttribute.getName());
    }
  }

  /**
   * Get the {@code EAttribute} of this item.
   * 
   * @return the {@code EAttribute} of this item.
   */
  public EAttribute getEAttribute() {
    return eAttribute;
  }

  /**
   * {@inheritDoc}
   * 
   * For a simple attribute this is the eAttribute.
   */
  @Override
  public EStructuralFeature getEStructuralFeature() {
    return getEAttribute();
  }

  public EObjectTreeItemAttributeDescriptor getEObjectTreeItemAttributeDescriptor() {
    return eObjectTreeItemAttributeDescriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void switchToEditMode() {
    // No action, a simple attribute value has no children.
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToViewMode() {
    // No action, a simple attribute value has no children.
  }
  
  /**
   * Handle the fact that an attribute has a new value.
   * 
   * @param eStructuralFeature the changed feature
   * @param newValue the new value
   */
  public void handleAttributeValueChanged(EStructuralFeature eStructuralFeature, Object newValue) {
    LOGGER.info("=> " + toString());
    setValue(newValue);
    
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) getParent();
    
    if (parentTreeItem.isFirstTimeChildren) {
      // The children haven't been built yet, so we don't have to add anything.
      LOGGER.severe("Children haven't been built yet, so no action");
      return;
    }
    
    for (TreeItem<Object> child: parentTreeItem.getChildren()) {
      EObjectTreeItem childEObjectTreeItem = (EObjectTreeItem) child;
      if (eStructuralFeature.equals(childEObjectTreeItem.getEStructuralFeature())) {
        LOGGER.info("child found, going to rebuild children");
        setExpanded(true);  // hack. This way the TreeView seems to re-evaluate whether the item is a leaf.
        childEObjectTreeItem.rebuildChildren();
        break;
      }
    }
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("eAttribute: ").append(eAttribute != null ? eAttribute : "<null>").append(NEWLINE);
    buf.append("descriptor: ").append(eObjectTreeItemAttributeDescriptor != null ? eObjectTreeItemAttributeDescriptor : "<null>").append(NEWLINE);
    
    return buf.toString();
  }
}
