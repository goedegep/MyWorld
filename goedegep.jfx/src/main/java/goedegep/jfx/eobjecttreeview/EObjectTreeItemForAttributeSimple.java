package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * This class is a TreeItem for a simple attribute.
 * <p>
 * The item typically shows attribute name and value;
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
   * Build a child for an EAttribute.
   * <p>
   * This applies only to 'single' attributes.<br/>
   * An attribute which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param eObjectTreeItemAttributeDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  static EObjectTreeItem createEObjectTreeItemForAttributeSimple(EObjectTreeItemContent eObjectTreeItemContent, EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor, EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=> eObjectTreeItemContent" + eObjectTreeItemContent.toString());
        
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    if (eObject == null) {
      LOGGER.severe("object is null in TreeItem");
//      LOGGER.severe("StructuralFeature=" + eObjectTreeItemContent.getEStructuralFeature().getName());
    }
    EAttribute eAttribute = eObjectTreeItemAttributeDescriptor.getEAttribute();
//    boolean editMode = isInEditMode();

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

  public EAttribute getEAttribute() {
    return eAttribute;
  }

  public EObjectTreeItemAttributeDescriptor getEObjectTreeItemAttributeDescriptor() {
    return eObjectTreeItemAttributeDescriptor;
  }

}
