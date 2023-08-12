package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * This class is a TreeItem for an attribute with a list of values (many in EMF terminalogy).
 * <p>
 * The item typically only shows the attribute name or an alternative name.<br/>
 *
 * Content:
 * <ul>
 *   <li>
 *     eObjectTreeItemType: ATTRIBUTE_LIST
 *   </li>
 *   <li>
 *     value: the EList with the attribute values
 *   </li>
 *   <li>
 *    eAttribute: the EAttribute for this attribute
 *   </li>
 *   <li>
 *     descriptor: the corresponding descriptor of type EObjectTreeItemAttributeListDescriptor
 *   </li>
 * </ul>
 * There's one child for each element in the EList.
 */
public class EObjectTreeItemForAttributeList extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForAttributeList.class.getName());
  
  private EAttribute eAttribute = null;
  
  private EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = null;

  public EObjectTreeItemForAttributeList(Object object, EAttribute eAttribute,
      EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.ATTRIBUTE_LIST, eObjectTreeView);
    
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null for an EObjectTreeItemForAttributeList");
    }
    
    if (eObjectTreeItemAttributeListDescriptor == null) {
      throw new IllegalArgumentException("presentationDescriptor cannot be null for EObjectTreeItemForAttributeList. object=" +
                                         (object != null ? object.toString() : "(null)"));
    }
    
    this.eAttribute = eAttribute;
    this.eObjectTreeItemAttributeListDescriptor = eObjectTreeItemAttributeListDescriptor;
  }
  
  /**
   * {@inheritDoc}
   * The tree item is leaf if there is no list, or if the list is empty.
   */
  @Override
  boolean isItemALeafItem() {
    Object object = getValue();
    
    return object == null  ||  !(object instanceof List<?> list) || list.isEmpty();
  }
  
  /**
   * {@inheritDoc}
   * There is one {@code EObjectTreeItemForAttributeListValue} child for each value in the list.
   */
  @Override
  ObservableList<TreeItem<Object>> buildChildren() {
    LOGGER.info("=>eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();
    
    Object object = getValue();
    EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor = eObjectTreeItemAttributeListDescriptor.geteObjectTreeItemAttributeListValueDescriptor();
    
    // The object is a List of Objects (values).
    // Add a child for each value of the list of values of this attribute.
    @SuppressWarnings("unchecked")
    List<? extends Object> values = (List<? extends Object>) object;
    for (Object listValue: values) {
      children.add(new EObjectTreeItemForAttributeListValue(listValue, eObjectTreeItemAttributeListValueDescriptor, getEObjectTreeView()));
    }
    
    LOGGER.info("<=");
    if (children.isEmpty()) {
      return null;
    } else {
      return children;
    }    
  }
  
  /**
   * Create a child for a list of EAttribute.
   * <p>
   * This applies to both 'many' and 'single' attributes.<br/>
   * An attribute which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param eObjectTreeItemAttributeDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  static EObjectTreeItem createEObjectTreeItemForAttributeList(EObject value, EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor,
      EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=>");
    
    EAttribute eAttribute = eObjectTreeItemAttributeListDescriptor.getEAttribute();

    EList<EAttribute> objectAttributes = value.eClass().getEAllAttributes();
    if (objectAttributes.contains(eAttribute)) {
      if (editMode  ||  value.eIsSet(eAttribute)) {
        Object childObject = value.eGet(eAttribute);
        if (eAttribute.isMany()) {
          // This tree item will only show the attribute name, child nodes will be created for each value.
          // Therefore the value (a list) is stored in this node.
        } else {
          throw new RuntimeException("Descriptor doesn't match with reference. eAttribute is " + eAttribute.toString());
        }
        return new EObjectTreeItemForAttributeList(childObject, eAttribute, eObjectTreeItemAttributeListDescriptor, eObjectTreeView);
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
   * For an attribute list this is the eAttribute.
   */
  @Override
  public EStructuralFeature getEStructuralFeature() {
    return getEAttribute();
  }
  
  public EObjectTreeItemAttributeListDescriptor getEObjectTreeItemAttributeListDescriptor() {
    return eObjectTreeItemAttributeListDescriptor;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToEditMode() {
    // No action as all children are already present and all children are leaves.
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToViewMode() {
    // No action, all children remain present.
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("eAttribute: ").append(eAttribute != null ? eAttribute : "<null>").append(NEWLINE);
    buf.append("descriptor: ").append(eObjectTreeItemAttributeListDescriptor != null ? eObjectTreeItemAttributeListDescriptor : "<null>").append(NEWLINE);
    
    return buf.toString();
  }
}
