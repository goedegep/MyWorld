package goedegep.jfx.eobjecttreeview;

import java.nio.ByteBuffer;
import java.util.Iterator;
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
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * This class is a TreeItem for an object.
 * <p>
 * The item only shows the header for the object.
 * For example if the object is a Person, but in the model it's an employee, you could show 'employee' (via the descriptor).<br/>
 * 
 * Content:
 * <ul>
 *   <li>
 *     eObjectTreeItemType: OBJECT
 *   </li>
 *   <li>
 *     value: the EObject
 *   </li>
 *   <li>
 *     descriptor: EObjectTreeItemClassReferenceDescriptor or EObjectTreeItemClassDescriptor
 *   </li>
 * </ul>
 * There's one child for each EStructuralFeature defined in the descriptor, which is either an EAttribute or an EReference.<br/>
 * For a single value EAttribute the child is of type ATTRIBUTE_SIMPLE.<br/>
 * For a list value EAttribute the child is of type ATTRIBUTE_LIST.<br/>
 * For an EReference to a single value EObject the child is of type OBJECT.<br/>
 * For an EReference to a list value EObject the child is of type OBJECT_LIST.<br/>
 *
 */
public class EObjectTreeItemForObject extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForObject.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The {@code EClass} for this tree item.
   * This is needed, because the reference descriptor may be null and also the value can be null. So this property can be used to get the class descriptor.
   */
  private EClass eClass;
  
  /**
   * Reference to the object in this tree item.
   * <p>
   * This reference can only be null for:
   * <ul>
   *   <li>The root item</li>
   *   <li>Objects in a list of objects</li>
   * </ul>
   */
  private EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = null;

  /**
   * Constructor.
   * 
   * @param value the value of the tree item.
   * @param eClass the {@code EClass} for {@code value} (mandatory).
   * @param eObjectTreeItemClassDescriptor the descriptor for this tree item (mandatory).
   * @param eObjectTreeView the {@code EObjectTreeView} to which this item belongs (mandatory).
   */
  public EObjectTreeItemForObject(EObject value, EClass eClass, EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor, EObjectTreeView eObjectTreeView) {

    super(value, EObjectTreeItemType.OBJECT, eObjectTreeView);
    LOGGER.info("=>");
//    if (value.eClass().getName().equals("Day")) {
//      String info = value.toString();
//      if (info.contains("null")) {
//        LOGGER.info("Stop");
//      }
//    }
    Objects.requireNonNull(eClass, "eClass may not be null");
    Objects.requireNonNull(eObjectTreeView, "eObjectTreeView may not be null");

    this.eClass = eClass;
    this.eObjectTreeItemClassReferenceDescriptor = eObjectTreeItemClassReferenceDescriptor;

    EObjectTreeItemClassDescriptor classDescriptor = eObjectTreeView.getBestClassDescriptor(eClass);
    if (classDescriptor != null) {
      if (classDescriptor.isExpandOnCreation()) {
        setExpanded(true);
      }
    } else {
      LOGGER.severe("No class descriptor for: " + (eClass != null ? eClass.getName() : "<null>"));
      throw new RuntimeException("No class descriptor for: " + (eClass != null ? eClass.getName() : "<null>"));
    }
    LOGGER.info("<=");
  }

  /**
   * {@inheritDoc}
   * 
   * An object has no structural feature.
   */
  @Override
  public EStructuralFeature getEStructuralFeature() {
    return eObjectTreeItemClassReferenceDescriptor != null ? eObjectTreeItemClassReferenceDescriptor.getEReference() : null;
  }
  
  /**
   * {@inheritDoc}
   * An EObject is a leaf if it has no structural features to be displayed.
   * This is the case if the descriptor has no features to be displayed, or if the tree is in normal mode
   * and all features to be displayed are null.
   */
  @Override
  boolean isItemALeafItem() {
    LOGGER.info("=> this=" + this);
    
    EObject value = (EObject) getValue();
//    if (value.eClass().getName().equals("Day")) {
//      String info = value.toString();
//      if (info.contains("null")) {
//        LOGGER.info("Stop");
//      }
//    }
    
    boolean isLeaf = true;

    EObjectTreeItemClassDescriptor classDescriptor = getClassDescriptor();
    if (isInEditMode()  &&  !classDescriptor.getStructuralFeatureDescriptors().isEmpty()) {
      isLeaf = false;
    } else {
      for (EObjectTreeItemDescriptor itemDescriptor: classDescriptor.getStructuralFeatureDescriptors()) {
        EStructuralFeature feature = null;
        if (itemDescriptor instanceof EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor) {
          feature =  eObjectTreeItemAttributeDescriptor.getEAttribute();
        } else if (itemDescriptor instanceof EObjectTreeItemAttributeListDescriptor eObjectTreeItemClassListReferenceDescriptor) {
          feature = eObjectTreeItemClassListReferenceDescriptor.getEAttribute();
        } else if (itemDescriptor instanceof EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor) {
          feature = eObjectTreeItemClassReferenceDescriptor.getEReference();
        } else if (itemDescriptor instanceof EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor) {
          feature = eObjectTreeItemClassListReferenceDescriptor.getEReference();
        } else {
          throw new RuntimeException("No feature for itemDescriptor: " + itemDescriptor);
        }

        //      EObject value = (EObject) getValue();
        if (value != null) {
          if (value.eIsSet(feature)) {
            isLeaf = false;
            break;
          }
        }
      }
    }

    LOGGER.info("<= " + isLeaf);
    return isLeaf;
  }
  
  /**
   * {@inheritDoc}
   * Build the children of an EObject item.
   * <p>
   * The 'object' in the EObjectTreeItemContent is by definition an EObject.<br/>
   * Children are created for structural features of the EObject. The EObjectTreeItemContent of the eObjectTreeItem provides
   * the presentation descriptor node for this object. The children of the presentation descriptor node determine which structural
   * features are added to the tree view and how. The rules are:
   * <ul>
   * <li>
   * Only structural features for which there is a presentation descriptor node, are added.
   * </li>
   * <li>
   * Structural features are added in the order of the presentation descriptor nodes.
   * </li>
   * <li>
   * Furthermore, a structural feature is only added if it has a non-null value, or when the tree is in edit mode.
   * </li>
   * </ul>
   */
  @Override
  ObservableList<TreeItem<Object>> buildChildren() {
    EObject eObject = (EObject) getValue();
    if (eObject == null) {
      return null;
    }
    
    ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();
    
    EObjectTreeItemClassDescriptor classDescriptor = getClassDescriptor();

    // Create the children as specified by the descriptor.
    for (EObjectTreeItemDescriptor descriptor: classDescriptor.getStructuralFeatureDescriptors()) {
      LOGGER.info("Handling descriptor: " + descriptor.toString());
      LOGGER.info("Descriptor class: " + descriptor.getClass().getName());
      
      EObjectTreeItem treeItem;
      switch (descriptor.getDescriptorType()) {
      case ATTRIBUTE:
        treeItem = EObjectTreeItemForAttributeSimple.createEObjectTreeItemForAttributeSimple(eObject, (EObjectTreeItemAttributeDescriptor) descriptor, getEObjectTreeView(), isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case ATTRIBUTE_LIST:
        treeItem = EObjectTreeItemForAttributeList.createEObjectTreeItemForAttributeList(eObject, (EObjectTreeItemAttributeListDescriptor) descriptor, getEObjectTreeView(), isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case ATTRIBUTE_LIST_VALUE:
        throw new RuntimeException("Descriptor of type ATTRIBUTE_LIST_VALUE cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
            
      case CLASS_REFERENCE:
        treeItem = EObjectTreeItemForObject.createChildEObjectTreeItemForSingleObjectReference(eObject, ((EObjectTreeItemClassReferenceDescriptor) descriptor).getEReference(), (EObjectTreeItemClassReferenceDescriptor) descriptor, getEObjectTreeView(), isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case CLASS_LIST:
        treeItem = EObjectTreeItemForObjectList.createEObjectTreeItemForObjectList(eObject, (EObjectTreeItemClassListReferenceDescriptor) descriptor, getEObjectTreeView(), isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;

      case CLASS:
        throw new RuntimeException("Descriptor of type CLASS cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
      }
    }
    
    LOGGER.info("<=");
    if (children.isEmpty()) {
      return null;
    } else {
      return children;
    }
  }
  
  /**
   * Build a child for a Class Reference.
   * <p>
   * A reference which isn't set, is only added in 'edit mode'.
   * 
   * @param parentEObject the {@code EObject} for which a child {@code EObjectTreeItem} is to be created.
   * @param eReference the {@code EReference} within the {@code parentEObject} identifying the child {@code EObject}.
   * @param eObjectTreeItemClassReferenceDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  static EObjectTreeItem createChildEObjectTreeItemForSingleObjectReference(EObject parentEObject, EReference eReference, EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor,
      EObjectTreeView eObjectTreeView, boolean editMode) {
    LOGGER.info("=>");
//    Objects.requireNonNull(eReference, "eReference may not be null");
    
    if (parentEObject.eClass().getName().equals("Day")) {
      LOGGER.severe("Found: " + parentEObject.toString());
      String info = parentEObject.toString();
      LOGGER.severe("Info: " + parentEObject.toString());
      if (info.contains("null")) {
        LOGGER.severe("Stop");
      }
    }
    
    EList<EReference> objectReferences = parentEObject.eClass().getEAllReferences();
    if (objectReferences.contains(eReference)) {
      if (editMode  ||  parentEObject.eIsSet(eReference)) {  // items are only added, if they are set, or if 'edit mode' is active.
        if (!eReference.isMany()) {          
          EObject childObject = (EObject) parentEObject.eGet(eReference);
          return new EObjectTreeItemForObject(childObject, (EClass) eReference.getEType(), eObjectTreeItemClassReferenceDescriptor, eObjectTreeView);
        } else {
          throw new RuntimeException("Descriptor doesn't match with reference");
        }
      } else {
        return null;
      }
    } else {
      throw new RuntimeException("Non existing Class Reference in descriptor. Reference name is \'" + (eReference != null ? eReference.getName() : "<null>") +
          "\', descriptor is: " + (eObjectTreeItemClassReferenceDescriptor != null ? eObjectTreeItemClassReferenceDescriptor.toString() : "<null>"));
    }
  }
  
  /**
   * Get the applicable {@code EReference}.
   * 
   * @param eObjectTreeItem
   * @return
   */
  protected static EReference getApplicableEReference(EObjectTreeItemForObject eObjectTreeItemForObject) {
    // if the item has a class reference descriptor, it is a single reference, which is part of the descriptor.
    if (eObjectTreeItemForObject.getEObjectTreeItemClassReferenceDescriptor() != null) {
      return eObjectTreeItemForObject.getEObjectTreeItemClassReferenceDescriptor().getEReference();
    }
    
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) eObjectTreeItemForObject.getParent();
    if (parentTreeItem == null) {
      return null; // it seems eObjectTreeItemForObject is the root, which has no reference.
    }
    
    // now the parent can only be an object list, having a reference
    EObjectTreeItemForObjectList parentEObjectTreeItemForObjectList = (EObjectTreeItemForObjectList) parentTreeItem;
    return parentEObjectTreeItemForObjectList.getEReference();
  }

  public EObjectTreeItemClassReferenceDescriptor getEObjectTreeItemClassReferenceDescriptor() {
    return eObjectTreeItemClassReferenceDescriptor;
  }
  
  /**
   * Determine which EObjectTreeItemClassDescriptor to use.
   * If there is a non null value, the class of this value is used to get the best descriptor from the tree view.
   * This gives the best descriptor in case the value is a sub type of the type for this item.
   * Else the eClass is used to get the best descriptor from the tree view.
   * 
   * @return the best {@code EObjectTreeItemClassDescriptor} for the current value or {@code eClass}.
   */
  public EObjectTreeItemClassDescriptor getClassDescriptor() {
    
    EObjectTreeItemClassDescriptor classDescriptor = null;
    
    EObject eObject = (EObject) getValue();
    if (eObject != null ) {
      classDescriptor = getEObjectTreeView().getBestClassDescriptor(eObject.eClass());
    } else {
      classDescriptor = getEObjectTreeView().getBestClassDescriptor(eClass);
    }
    
    return classDescriptor;
  }
  
  /**
   * Find a child item of a specified item, which contains a specific EStructuralFeature.
   * <p>
   * This is used to handle a changed value, where we get information that a feature has changed for an object.
   * 
   * @param eObjectTreeItem the item of which a child is looked for. This value may not be null.
   * @param eStructuralFeature the EStructuralFeature of the child to be found. This value may not be null.
   * @return the specified child, or null if no such child exists.
   */
  EObjectTreeItem findChildTreeItem(EStructuralFeature eStructuralFeature) {
    EObjectTreeItem result = null;
    
    for (TreeItem<Object> child:getChildren()) {
      EStructuralFeature contentEStructuralFeature = ((EObjectTreeItem) child).getEStructuralFeature();
      if ((contentEStructuralFeature != null)  &&  contentEStructuralFeature.equals(eStructuralFeature)) {
        result = (EObjectTreeItem) child;
        break;
      }
    }

    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * In view mode the empty children are left out of the tree. So upon switching to edit mode these empty children have to be added.
   * This is done by this method.<br/>
   * The 'object' in the EObjectTreeItemContent is by definition an EObject.<br/>
   */
  @Override
  void switchToEditMode() {
    LOGGER.info("=> eObjectTreeItem=" + toString());
    
    EObject eObject = (EObject) getValue();
    if (eObject == null) {
      return;
    }
    
    ObservableList<TreeItem<Object>> children = getChildren();
    
    EObjectTreeItemClassDescriptor classDescriptor = getClassDescriptor();

    int childIndex = 0;
    // Create the children as specified by the descriptor.
    for (EObjectTreeItemDescriptor descriptor: classDescriptor.getStructuralFeatureDescriptors()) {
      LOGGER.info("Handling descriptor: " + descriptor.toString());
      LOGGER.info("Descriptor class: " + descriptor.getClass().getName());
      
      EObjectTreeItem treeItem = null;
      switch (descriptor.getDescriptorType()) {
      case ATTRIBUTE:
        EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = (EObjectTreeItemAttributeDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemAttributeDescriptor.getEAttribute())) {
          treeItem = EObjectTreeItemForAttributeSimple.createEObjectTreeItemForAttributeSimple(eObject, eObjectTreeItemAttributeDescriptor, getEObjectTreeView(), isInEditMode());
        } else {
          childIndex++;
        }
        break;
        
      case ATTRIBUTE_LIST:
        EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = (EObjectTreeItemAttributeListDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemAttributeListDescriptor.getEAttribute())) {        
          treeItem = EObjectTreeItemForAttributeList.createEObjectTreeItemForAttributeList(eObject, eObjectTreeItemAttributeListDescriptor, getEObjectTreeView(), isInEditMode());
        } else {
          childIndex++;
        }
        break;
        
      case ATTRIBUTE_LIST_VALUE:
        throw new RuntimeException("Descriptor of type ATTRIBUTE_LIST_VALUE cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
            
      case CLASS_REFERENCE:
        EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = (EObjectTreeItemClassReferenceDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemClassReferenceDescriptor.getEReference())) {        
          treeItem = EObjectTreeItemForObject.createChildEObjectTreeItemForSingleObjectReference(eObject, null, eObjectTreeItemClassReferenceDescriptor, getEObjectTreeView(), isInEditMode());
        } else {
          childIndex++;
        }
        break;
        
      case CLASS_LIST:
        EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = (EObjectTreeItemClassListReferenceDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemClassListReferenceDescriptor.getEReference())) {        
          treeItem = EObjectTreeItemForObjectList.createEObjectTreeItemForObjectList(eObject, eObjectTreeItemClassListReferenceDescriptor, getEObjectTreeView(), isInEditMode());
        } else {
          childIndex++;
        }
        break;

      case CLASS:
        throw new RuntimeException("Descriptor of type CLASS cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
      }
      
      if (treeItem != null) {
        children.add(childIndex, treeItem);
        childIndex++;
      }
    }
    
    for (TreeItem<Object> child: getChildren()) {
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) child;
      eObjectTreeItem.setEditMode(true);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Check whether there is a child of this item for a specific feature.
   * 
   * @param feature the {@code EStructuralFeature} for which there should be a child.
   * @return true if there is a child item for {@code feature}.
   */
  private boolean doChildrenContainItemForFeature(EStructuralFeature feature) {
    for (TreeItem<Object> item: getChildren()) {
      if (feature.equals(((EObjectTreeItem) item).getEStructuralFeature())) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToViewMode() {
    EObject eObject = (EObject) getValue();
    
    Iterator<TreeItem<Object>> iterator = getChildren().iterator();
    while (iterator.hasNext()) {
      TreeItem<Object> item = iterator.next();
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) item;
      EStructuralFeature eStructuralFeature = null;
      // TODO this is same functionality as getEStructuralFeature() (apart from the exceptions thrown here), chose which one to use.
      switch (eObjectTreeItem.getEObjectTreeItemType()) {
      case OBJECT:
        eStructuralFeature = ((EObjectTreeItemForObject) eObjectTreeItem).getEStructuralFeature();
        break;
        
      case OBJECT_LIST:
        eStructuralFeature = ((EObjectTreeItemForObjectList) eObjectTreeItem).getEReference();
        break;
        
      case ATTRIBUTE_LIST:
        eStructuralFeature = ((EObjectTreeItemForAttributeList) eObjectTreeItem).getEAttribute();
        break;
        
      case ATTRIBUTE_LIST_VALUE:
        // Shall not exist here.
        throw new RuntimeException("Illegal ATTRIBUTE_LIST_VALUE child of tree item: " + toString());
        
      case ATTRIBUTE_SIMPLE:
        eStructuralFeature = ((EObjectTreeItemForAttributeSimple) eObjectTreeItem).getEAttribute();
        break;
      }
      if (!eObject.eIsSet(eStructuralFeature)) {
        iterator.remove();
      }
    }

    for (TreeItem<Object> item: getChildren()) {
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) item;
      eObjectTreeItem.setEditMode(false);
    }
  }
  
  /**
   * Handle the fact that a feature has a new value.
   * 
   * @param eStructuralFeature the changed feature
   * @param newValue the new value
   */
  public void handleAttributeValueChanged(EStructuralFeature eStructuralFeature, Object newValue) {
    LOGGER.severe("=> " + toString());
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
        LOGGER.severe("child found, going to rebuild children");
        setExpanded(true);  // hack. This way the TreeView seems to re-evaluate whether the item is a leaf.
        childEObjectTreeItem.rebuildChildren();
        break;
      }
    }
    
  }
  
  /**
   * {@inheritDoc}
   * 
   * An {@link EObjectPath} is created and serialized as bytes.
   * {@code ClipboardContent} is created with this data, with type {@link EOBJECT_PATH}.
   * A {@code Dragboard} is created for a MOVE and with the {@code ClipboardContent} as content.
   */
  @Override
  public void handleDragDetected(MouseEvent event, Node node) {
    EObject eObject = (EObject) getValue();
    EObjectPath eObjectPath = new EObjectPath(eObject);
    ByteBuffer eObjectPathAsBytes = eObjectPath.getSerializedData();

    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.put(EOBJECT_PATH, eObjectPathAsBytes);

    node.startDragAndDrop(TransferMode.MOVE).setContent(clipboardContent);

    event.consume();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TransferMode isDropPossible(DragEvent dragEvent) {  
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

        if (!eClass.isSuperTypeOf(sourceEObject.eClass())) {
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
//      EObjectTreeItemForObject sourceEObjectTreeItem = (EObjectTreeItemForObject) getEObjectTreeView().findTreeItem(eObjectPath);
//      sourceReference = getApplicableEReference(sourceEObjectTreeItem);
      sourceReference = eObjectPath.resolveToEObjectReference((EObject) getEObjectTreeView().getRoot().getValue());
      if (sourceReference == null) {  // indicates that the source is the root item
        LOGGER.severe("You cannot cut the root item.");
        dropPossible = false;
      }
    }
     
    EReference destinationReference = null;
    if (dropPossible) {
      destinationReference = getApplicableEReference(this);
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
    LOGGER.info("=> dragEvent" + EObjectTreeItem.dragEventToString(dragEvent));

    if (isDropPossible(dragEvent) == null) {
      return;
    }

    // delete the source object from its reference
    Dragboard dragboard = dragEvent.getDragboard();
    ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
    LOGGER.info("EObjectPath received!!");
    EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
    EObject sourceObject = eObjectPath.resolveToEObject((EObject) getEObjectTreeView().getRoot().getValue());
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

    // set the source object to this reference
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) getParent();
    if (eObjectTreeItemClassReferenceDescriptor != null) {
      // it is a single reference
      EObject parentEObject = (EObject) parentTreeItem.getValue();
      parentEObject.eSet(eObjectTreeItemClassReferenceDescriptor.getEReference(), sourceObject);
    } else {
      // parent tree item is an object list, insert before this tree item.
      EObjectTreeItemForObjectList eObjectTreeItemForObjectList = (EObjectTreeItemForObjectList) parentTreeItem;
      @SuppressWarnings("unchecked")
      List<EObject> list = (List<EObject>) eObjectTreeItemForObjectList.getValue();
      int index = getIndexInParentTreeItem();
      list.add(index, sourceObject);
    }

  }
  
  /**
   *{@inheritDoc}
   */
  public String getText() {
    String labelText = null;
    EObject eObject = (EObject) getValue();
    
    if (hasReferenceWithPresentationInfo()) {
      if (eObjectTreeItemClassReferenceDescriptor.getNodeTextFunction() != null) {
        labelText = eObjectTreeItemClassReferenceDescriptor.getNodeTextFunction().apply(eObject);
      }
    } else {
      EObjectTreeItemClassDescriptor classDescriptor = getClassDescriptor();
      if (classDescriptor.getNodeTextFunction() != null) {
        labelText = classDescriptor.getNodeTextFunction().apply(eObject);
      }
    }
    
    if (labelText == null  &&  eObject != null) {
      LOGGER.severe("Fall back to class name");
      String className = eObject.getClass().getSimpleName();
      labelText = className.substring(0, className.length() - 4);
    }
        
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  /**
   * Check whether the object tree item has a reference with any presentation information.
   * <p>
   * An object tree item has a reference with any presentation information,if the {@code classReferenceDescriptor} in not null
   * and this descriptor has any presentation value set (buildText, strongText, nodeIconFunction).
   * 
   * @return true if the object tree item has a reference with any presentation information, false otherwise.
   */
  boolean hasReferenceWithPresentationInfo() {
    return eObjectTreeItemClassReferenceDescriptor != null &&
      (eObjectTreeItemClassReferenceDescriptor.getNodeTextFunction() != null  ||
          eObjectTreeItemClassReferenceDescriptor.isStrongText()  ||
          eObjectTreeItemClassReferenceDescriptor.getNodeIconFunction() != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("classReferenceDescriptor: ").append(eObjectTreeItemClassReferenceDescriptor != null ? eObjectTreeItemClassReferenceDescriptor.toString() : "<null>").append(NEWLINE);
        
    return buf.toString();
  }
}
