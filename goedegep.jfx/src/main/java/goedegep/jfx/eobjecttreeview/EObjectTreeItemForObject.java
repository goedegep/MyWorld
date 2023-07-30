package goedegep.jfx.eobjecttreeview;

import java.nio.ByteBuffer;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import goedegep.util.emf.EObjectPath;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * This class is a TreeItem for an object.
 * <p>
 * The item typically only shows the name of the object or an alternative name.
 * For example if the object is a Person, but in the model it's an employee, you could show 'employee' (via the descriptor).
 *
 */
public class EObjectTreeItemForObject extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForObject.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * TODO Needed? Description
   */
  private EReference eReference = null;
  
  private EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = null;
  private EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = null;

  /**
   * Constructor.
   * 
   * @param object
   * @param eReference
   * @param presentationDescriptor
   * @param eObjectTreeView
   */
  public EObjectTreeItemForObject(Object object, EReference eReference,
      EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.OBJECT, eObjectTreeView);
    
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null for EObjectTreeItemForObject");
    }
    
    this.eReference = eReference;
    this.eObjectTreeItemClassReferenceDescriptor = eObjectTreeItemClassReferenceDescriptor;
  }

  /**
   * Constructor.
   * 
   * @param object
   * @param eReference
   * @param presentationDescriptor
   * @param eObjectTreeView
   */
  public EObjectTreeItemForObject(Object object, EReference eReference,
      EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.OBJECT, eObjectTreeView);
    
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null for EObjectTreeItemForObject");
    }
    
    this.eReference = eReference;
    this.eObjectTreeItemClassDescriptor = eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Build a child for a Class Reference.
   * <p>
   * A reference which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param eObjectTreeItemClassReferenceDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  static EObjectTreeItem createEObjectTreeItemForObject(Object eObjectTreeItemContent, EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor,
      EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=>");
    
    EObject eObject = (EObject) eObjectTreeItemContent;
    EReference eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
    LOGGER.info("descriptorEReference=" + eReference.getName());
    
    EList<EReference> objectReferences = eObject.eClass().getEAllReferences();
    if (objectReferences.contains(eReference)) {
      if (editMode  ||  eObject.eIsSet(eReference)) {  // items are only added, if they are set, or if 'edit mode' is active.
        if (!eReference.isMany()) {
          EObject childObject = (EObject) eObject.eGet(eReference);
          EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
          EObjectTreeItemDescriptor eClassPresentationDescriptor = getBestClassDescriptor((EClass) eReference.getEType(), eObjectTreeDescriptor);
          if (eClassPresentationDescriptor == null) {
            throw new RuntimeException("No descriptor specified for class " + eReference.getEType().getName());
          }
          // Get the best possible descriptor: if there is a child object (which may be a subclass of the type of the reference) use this class,
          // else use the type of the reference.
          if (childObject != null) {
            EObjectTreeItemDescriptor objectSpecificDescriptor = getBestClassDescriptor(childObject.eClass(), eObjectTreeDescriptor);
            if (objectSpecificDescriptor != null) {
              eClassPresentationDescriptor = objectSpecificDescriptor;
            }
          }
          return new EObjectTreeItemForObject(childObject, eReference, eObjectTreeItemClassReferenceDescriptor, eObjectTreeView);
        } else {
          throw new RuntimeException("Descriptor doesn't match with reference");
        }
      } else {
        return null;
      }
    } else {
      throw new RuntimeException("Non existing Class Reference in descriptor. Reference name is \'" + eReference.getName() +
          "\', descriptor is: " + eObjectTreeItemClassReferenceDescriptor.toString());
    }
  }

  public EReference getEReference() {
    return eReference;
  }
  
  public EObjectTreeItemClassReferenceDescriptor getEObjectTreeItemClassReferenceDescriptor() {
    return eObjectTreeItemClassReferenceDescriptor;
  }

  public EObjectTreeItemClassDescriptor getEObjectTreeItemClassDescriptor() {
    return eObjectTreeItemClassDescriptor;
  }
  
  
  /**
   *  Determine which EObjectTreeItemClassDescriptor to use.
   * If the presentation descriptor is for a reference, then a descriptor is searched for the class of this reference, or for the actual subtype of this class.
   * if the presentation descriptor is for a class, then this is used.
   */
  EObjectTreeItemClassDescriptor getClassDescriptor() {
    Object eObjectTreeItemContent = getValue();
    EObjectTreeItemClassDescriptor classDescriptor = null;
    if (eObjectTreeItemClassReferenceDescriptor != null) {
      EObjectTreeDescriptor eObjectTreeDescriptor = getEObjectTreeView().getEObjectTreeDescriptor();
      EReference eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
      EObjectTreeItemClassDescriptor eClassPresentationDescriptor = getBestClassDescriptor((EClass) eReference.getEType(), eObjectTreeDescriptor);
      if (eClassPresentationDescriptor == null) {
        throw new RuntimeException("No descriptor specified for class " + eReference.getEType().getName());
      }
      // Get the best possible descriptor: if there is a child object (which may be a subclass of the type of the reference) use this class,
      // else use the type of the reference.
      EObject eObject = (EObject) eObjectTreeItemContent;
      if (eObject == null) {
        return null;
      }
      EObjectTreeItemClassDescriptor objectSpecificDescriptor = getBestClassDescriptor(eObject.eClass(), eObjectTreeDescriptor);
      if (objectSpecificDescriptor != null) {
        eClassPresentationDescriptor = objectSpecificDescriptor;
      }
      classDescriptor = eClassPresentationDescriptor;
    } else if (eObjectTreeItemClassDescriptor != null) {
      classDescriptor = eObjectTreeItemClassDescriptor;
    } else {
      throw new RuntimeException("No presentation descriptor for item: " + eObjectTreeItemContent.toString());
    }
    
    return classDescriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
//  public boolean isDropPossible(EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {  
  public TransferMode isDropPossible(DragEvent dragEvent) {  
    LOGGER.severe("=> dragEvent=" + dragEvent.toString());
    
    boolean dropPossible = true;
    
    Dragboard dragboard = dragEvent.getDragboard();
    
    // Only content of type EOBJECT_PATH is supported by default.
    // Note that if the content type is EOBJECT_PATH, the source is by definition an EObject (so this is not separately checked.
    if (!dragboard.hasContent(EOBJECT_PATH)) {
      dropPossible = false;
    }
    
    // The eClass of this item must be a supertype of the source eClass
    if (dropPossible) {
      EClass eClass = eObjectTreeItemClassDescriptor.getEClass();
      
      Object sourceObject = dragEvent.getSource();
      EObject sourceEObject = (EObject) sourceObject;

      if (!eClass.isSuperTypeOf(sourceEObject.eClass())) {
        dropPossible = false;
      }
    }
    
    // We don't allow a cut and paste from a containment reference to a non-containment reference.
    if (dropPossible) {
      ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
      LOGGER.info("EObjectPath received!!");
      EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
      EObjectTreeItem sourceEObjectTreeItem = getEObjectTreeView().findTreeItem(eObjectPath);
      EReference sourceReference = getApplicableEReference(sourceEObjectTreeItem);
        
      EReference destinationReference = getEReference();

      if (sourceReference.isContainment()  &&  !destinationReference.isContainment()) {
        LOGGER.severe("Cut and paste from a containment reference to a non-containment reference is not allowed");
        dropPossible = false;
      }
    }
    
    if (dropPossible) {
      return TransferMode.MOVE;
    } else {
      BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction = getEObjectTreeView().getIsDropPossibleFunction();
      if (isDropPossibleFunction != null) {
        if (isDropPossibleFunction.test(this, dragboard)) {
          return TransferMode.COPY;
        }
      }
    }
    
    return null;
//    // Only EObjects can be dropped
//    Object sourceObject = dragEvent.getSource();
//    if (!(sourceObject instanceof EObject)) {
//      return null;
//    }
    
    
     
//    EReference eReference = eObjectTreeItemForObject.getEReference();
//    if (eReference != null) {
//      LOGGER.severe("Object");
//    } else {
//      LOGGER.severe("Object list");
//    }
    
//    // parent will be a reference.
//    EObjectTreeItemForObjectList eObjectParentTreeItem = (EObjectTreeItemForObjectList)  thisEObjectTreeItem.getParent();
//    EReference parentEReference = eObjectParentTreeItem.getEReference();
//    if (parentEReference != null) {
//      LOGGER.info("parentEReference=" + parentEReference.toString());
//      EClass contentReferenceType = parentEReference.getEReferenceType();
//      if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {
//        return true;
//      }
//    }
    
//    return false;
    

//    if (dragboard.hasContent(EOBJECT_PATH)) {
//      EObject sourceEObject = getSourceObject(dragboard);
//      if (sourceEObject != null  &&
//          treeCellHelper.isDropPossible(sourceEObject, (EObjectTreeItem)  getTreeItem())) {
//        return TransferMode.MOVE;        
//      }
//    }
//        
//
//    return true;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleDragDropped(DragEvent dragEvent) {
    LOGGER.severe("=> dragEvent" + dragEvent.toString());
    
    if (isDropPossible(dragEvent) == null) {
      return;
    }
         
    if (eReference != null) {
      LOGGER.severe("Object");
      
      // delete the source object from its reference
      
      // set the source object to this reference
      getParent();
    }
    
  }  

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("eReference: ").append(eReference != null ? eReference : "<null>").append(NEWLINE);
    buf.append("classDescriptor: ").append(eObjectTreeItemClassDescriptor != null ? eObjectTreeItemClassDescriptor : "<null>").append(NEWLINE);
    buf.append("classReferenceDescriptor: ").append(eObjectTreeItemClassReferenceDescriptor != null ? eObjectTreeItemClassDescriptor : "<null>").append(NEWLINE);
        
    return buf.toString();
  }
}
