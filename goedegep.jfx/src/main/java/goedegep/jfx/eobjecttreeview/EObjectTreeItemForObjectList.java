package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * This class is a TreeItem for a list of objects.
 * <p>
 * The item typically only shows the name of the objects or an alternative name.
 * For example if the objects are Persons, but in the model they are employee, you could show 'employees' (via the descriptor).
 *
 */
public class EObjectTreeItemForObjectList extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForObjectList.class.getName());
  
  /**
   * TODO add description
   */
  private EReference eReference = null;
  
  private EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = null;

  /**
   * Constructor.
   * 
   * @param object
   * @param eStructuralFeature
   * @param presentationDescriptor
   * @param eObjectTreeView
   */
  public EObjectTreeItemForObjectList(Object object, EReference eReference,
      EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.OBJECT_LIST, eObjectTreeView);
    
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null for an EObjectTreeItemForObjectList");
    }
    
    if (eObjectTreeItemClassListReferenceDescriptor == null) {
      throw new IllegalArgumentException("presentationDescriptor cannot be null for EObjectTreeItemForObjectList. object=" +
                                         (object != null ? object.toString() : "(null)"));
    }
    
    this.eReference = eReference;
    this.eObjectTreeItemClassListReferenceDescriptor = eObjectTreeItemClassListReferenceDescriptor;
  }
  
  /**
   * Build a child for a Class List Reference.
   * <p>
   * A reference which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param EObjectTreeItemClassListReferenceDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  static EObjectTreeItem createEObjectTreeItemForObjectList(EObjectTreeItemContent eObjectTreeItemContent, EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor,
      EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=>");
    
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    EReference eReference = eObjectTreeItemClassListReferenceDescriptor.getEReference();
    
    EList<EReference> objectReferences = eObject.eClass().getEAllReferences();
    if (objectReferences.contains(eReference)) {
      if (editMode  ||  eObject.eIsSet(eReference)) {
        Object childObject = eObject.eGet(eReference);
        if (eReference.isMany()) {
          return new EObjectTreeItemForObjectList(childObject, eReference, eObjectTreeItemClassListReferenceDescriptor, eObjectTreeView);
        } else {
          throw new RuntimeException("Descriptor doesn't match with reference (descriptor is for many, but reference isn't). Descriptor=" + eObjectTreeItemClassListReferenceDescriptor.toString());
        }
      } else {
        return null;
      }
    } else {
      throw new RuntimeException("EObjectTreeItemClassListReferenceDescriptor for non-existing reference: " + eReference.getName());
    }
  }

  public EReference getEReference() {
    return eReference;
  }

  public EObjectTreeItemClassListReferenceDescriptor getEObjectTreeItemClassListReferenceDescriptor() {
    return eObjectTreeItemClassListReferenceDescriptor;
  }
  
}
