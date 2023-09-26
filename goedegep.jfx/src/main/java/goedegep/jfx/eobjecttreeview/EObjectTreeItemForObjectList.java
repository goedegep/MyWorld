package goedegep.jfx.eobjecttreeview;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.emf.EObjectPath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * This class is a TreeItem for a list of objects (a many reference in EMF terms).
 * <p>
 * The item typically only shows a header for the object (e.g. the name of the objects).
 * For example if the objects are Persons, but in the model they are employees, you could show 'employees' (via the descriptor).<br/>
 * <p>
 * Properties:
 * <ul>
 *   <li>
 *     value (set in javafx.scene.control.treeitem): the list ({@code EList}) of objects.
 *   </li>
 *   <li>
 *     eObjectTreeItemType (set in EObjectTreeItem): OBJECT_LIST
 *   </li>
 *   <li>
 *     eObjectTreeView (set in EObjectTreeItem): a reference to the tree view to which this item belongs.
 *   </li>
 *   <li>
 *     eReference: the EReference to the {@code value}.
 *   </li>
 *   <li>
 *     descriptor: the corresponding descriptor of type EObjectTreeItemClassListReferenceDescriptor
 *   </li>
 * </ul>
 * There's one child for each element in the EList.
 */
public class EObjectTreeItemForObjectList extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForObjectList.class.getName());
  
  /**
   * The EReference to the {@code value}.
   */
  private EReference eReference = null;
  
  private EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = null;

  /**
   * Constructor.
   * 
   * @param value the list ({@code EList}) of objects (mandatory).
   * @param eReference the EReference to {@code value} (mandatory).
   * @param eObjectTreeItemClassListReferenceDescriptor the descriptor for {@code value} (mandatory).
   * @param eObjectTreeView a reference to the tree view to which this item belongs (mandatory).
   */
  public EObjectTreeItemForObjectList(Object value, EReference eReference,
      EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(value, EObjectTreeItemType.OBJECT_LIST, eObjectTreeView);
    
    Objects.requireNonNull(value, "The value may not be null");
    if (!(value instanceof EList)) {
      throw new IllegalArgumentException("value has to be of type EList, type=" + value.getClass().getName());
    }
    Objects.requireNonNull(eReference, "The eReference may not be null, value=" + value.toString());
    Objects.requireNonNull(eObjectTreeItemClassListReferenceDescriptor, "The eObjectTreeItemClassListReferenceDescriptor may not be null, value=" + value.toString());
    Objects.requireNonNull(eObjectTreeView, "The eObjectTreeView may not be null, value=" + value.toString());
    
    this.eReference = eReference;
    this.eObjectTreeItemClassListReferenceDescriptor = eObjectTreeItemClassListReferenceDescriptor;
  }
  
  /**
   * Get the real (casted) value.
   * 
   * @return the real (casted) value.
   */
  EList<?> getRealValue() {
    return ((EList<?>) getValue());
  }
  
  /**
   * {@inheritDoc}
   * The tree item is leaf if the list is empty.
   */
  @Override
  boolean isItemALeafItem() {
    return getRealValue().isEmpty();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  ObservableList<TreeItem<Object>> buildChildren() {
    LOGGER.info("=>eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();
    
    Object object = getValue();
    
    // The object is a list of EObjects, each of which will be a child OBJECT.
    @SuppressWarnings("unchecked")
    List<? extends EObject> eObjects = (List<? extends EObject>) object;
    for (EObject listEObject: eObjects) {
      EObjectTreeItemClassDescriptor childPresentationDescriptorNode = getEObjectTreeView().getBestClassDescriptor(listEObject.eClass());
      if (childPresentationDescriptorNode == null) {
        throw new RuntimeException("No presentation descriptor found for class: " + listEObject.eClass().getName());
      }
      // Use the eClass from the reference, the value may be a sub type of this.
      EObjectTreeItem child = new EObjectTreeItemForObject(listEObject, (EClass) eReference.getEType(), null, getEObjectTreeView());
      children.add(child);
    }
    
    LOGGER.info("<=");
    if (children.isEmpty()) {
      return null;
    } else {
      return children;
    }
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
  static EObjectTreeItem createEObjectTreeItemForObjectList(Object eObjectTreeItemContent, EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor,
      EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=>");
    
    EObject eObject = (EObject) eObjectTreeItemContent;
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

  /**
   * Get the {@code EReference} of this item.
   * 
   * @return the {@code EReference} of this item.
   */
  public EReference getEReference() {
    return eReference;
  }

  /**
   * {@inheritDoc}
   * 
   * For an object list this is the eReference.
   */
  @Override
  public EStructuralFeature getEStructuralFeature() {
    return getEReference();
  }

  public EObjectTreeItemClassListReferenceDescriptor getEObjectTreeItemClassListReferenceDescriptor() {
    return eObjectTreeItemClassListReferenceDescriptor;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToEditMode() {
    // No action as all children are already present
    
    for (TreeItem<Object> child: getChildren()) {
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) child;
      eObjectTreeItem.setEditMode(true);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToViewMode() {
    // No action on this item, all children remain present.

    // But update our children.
    for (TreeItem<Object> item: getChildren()) {
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) item;
      eObjectTreeItem.setEditMode(false);
    }
  }

  /**
   * Add a child to an object list item.
   * <p>
   * If the children haven't been built yet, nothing is done, because the right children will be built when needed.
   * 
   * @param position the position in the list of objects and in the child items.
   */
  public void addObjectListChild(int position) {
    LOGGER.info("=>");
    
    boolean currentIsLeaf = isLeaf();
    
    isLeaf = null;
    
    if (isFirstTimeChildren) {
      // The children haven't been built yet, so we don't have to add anything.
      LOGGER.severe("Children haven't been built yet, so no action");
      return;
    }
    
    ObservableList<TreeItem<Object>> children = super.getChildren();
        
    Object object = getValue();
    
    // The object is a list of EObjects, each of which will be a child OBJECT.
    @SuppressWarnings("unchecked")
    List<? extends EObject> eObjects = (List<? extends EObject>) object;
    EObject listEObject = eObjects.get(position);
    EObjectTreeItemClassDescriptor childPresentationDescriptorNode = getEObjectTreeView().getBestClassDescriptor(listEObject.eClass());
    if (childPresentationDescriptorNode == null) {
      throw new RuntimeException("No presentation descriptor found for class: " + listEObject.eClass().getName());
    }
    EObjectTreeItem child = new EObjectTreeItemForObject(listEObject, (EClass) eReference.getEType(), null, getEObjectTreeView());
    if (children.size() < position + 1) {
      children.add(child);
    } else {
      children.add(position, child);
    }
    
    // hack. This way the TreeView seems to re-evaluate whether the item is a leaf.
    if (currentIsLeaf) {
      boolean expanded = isExpanded();
      setExpanded(!expanded);
      setExpanded(expanded);
    }
    
    this.setExpanded(true);
    getEObjectTreeView().getSelectionModel().select(child);
            
    LOGGER.info("<=");
  }

  /**
   * Remove a child from an object list item.
   * <p>
   * If the children haven't been built yet, nothing is done, because the right children will be built when needed.
   * 
   * @param position the position in the list of objects and in the child items.
   */
  public void removeObjectListChild(int position) {
    LOGGER.info("=>");
    
    if (isFirstTimeChildren) {
      // The children haven't been built yet, so we don't have to add anything.
      LOGGER.severe("Children haven't been built yet, so no action");
      return;
    }
    
    ObservableList<TreeItem<Object>> children = getChildren();
    children.remove(position);
        
    LOGGER.info("<=");
  }

  
  /*
   * Start of Drag and Drop handling.
   */
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TransferMode isDropPossible(DragEvent dragEvent) {
    LOGGER.severe("=> dragEvent=" + EObjectTreeItem.dragEventToString(dragEvent));
    
    boolean dropPossible = true;
    
    Dragboard dragboard = dragEvent.getDragboard();
    
    // Only content of type EOBJECT_PATH is supported by default.
    // Note that if the content type is EOBJECT_PATH, the source is by definition an EObject (so this is not separately checked).
    if (!dragboard.hasContent(EOBJECT_PATH)) {
      dropPossible = false;
    }
    
    // The eClass of this item must be a supertype of the source eClass
    if (dropPossible) {
      Object sourceObject = dragEvent.getGestureSource();
      if (sourceObject instanceof EObjectTreeCell eObjectTreeCell) {
        EObject sourceEObject = (EObject) eObjectTreeCell.getItem();

        EClass thisEClass = (EClass) eReference.getEType();
        if (!thisEClass.isSuperTypeOf(sourceEObject.eClass())) {
          dropPossible = false;
        }
      } else {
        dropPossible = false;
      }
    }
    
    // We don't allow a cut and paste from a containment reference to a non-containment reference.
    EReference sourceReference = null;
    if (dropPossible) {
      ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
      LOGGER.info("EObjectPath received!!");
      EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
      sourceReference = eObjectPath.resolveToEObjectReference((EObject) getEObjectTreeView().getRoot().getValue());
      if (sourceReference == null) {  // indicates that the source is the root item
        LOGGER.severe("You cannot cut the root item.");
        dropPossible = false;
      }
    }
     
    EReference destinationReference = null;
    if (dropPossible) {
      destinationReference = eObjectTreeItemClassListReferenceDescriptor.getEReference();
      if (destinationReference == null) {  // indicates that the destination is the root item
        LOGGER.severe("You cannot drop on the root item.");
        dropPossible = false;
      }
    }
      
    if (dropPossible) {
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
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleDragDropped(DragEvent dragEvent) {
    LOGGER.severe("=> dragEvent" + EObjectTreeItem.dragEventToString(dragEvent));

    if (isDropPossible(dragEvent) == null) {
      return;
    }

    // delete the source object from its reference
    Dragboard dragboard = dragEvent.getDragboard();
    
    if (dragboard.hasContent(EOBJECT_PATH)) {
      LOGGER.info("EObjectPath received!!");
      // Get the source object
      ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
      EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
      EObject sourceObject = eObjectPath.resolveToEObject((EObject) getEObjectTreeView().getRoot().getValue());
      
      // Get the reference and 'parent' to remove the source object from.
      EReference eReference = eObjectPath.resolveToEObjectReference((EObject) getEObjectTreeView().getRoot().getValue());
      EObject sourceParentEObject = eObjectPath.resolveToEObjectParent((EObject) getEObjectTreeView().getRoot().getValue());
      if (eReference.isMany()) {
        @SuppressWarnings("unchecked")
        List<EObject> list = (List<EObject>) sourceParentEObject.eGet(eReference);
        int index = eObjectPath.resolveToEObjectReferenceIndex((EObject) getEObjectTreeView().getRoot().getValue());
        list.remove(index);
      } else {
        sourceParentEObject.eSet(eReference, null);
      }

      // add the source object to the end of this items list.
      @SuppressWarnings("unchecked")
      List<EObject> list = (List<EObject>) getValue();
      list.add(sourceObject);
    } else {
      BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction = getEObjectTreeView().getHandleDropFunction();
      if (handleDropFunction != null) {
        if (handleDropFunction.test(this, dragboard)) {
        }
      }
    }
  }
  
  /*
   * End of Drag and Drop handling
   */
 
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("eReference: ").append(eReference != null ? eReference : "<null>").append(NEWLINE);
    buf.append("dscriptor: ").append(eObjectTreeItemClassListReferenceDescriptor != null ? eObjectTreeItemClassListReferenceDescriptor : "<null>").append(NEWLINE);
    
    return buf.toString();
  }
}
