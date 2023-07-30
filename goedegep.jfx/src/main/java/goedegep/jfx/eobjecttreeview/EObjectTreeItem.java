package goedegep.jfx.eobjecttreeview;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

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
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * This class represents the common part of EObject tree items.
 * <p>
 * There is a subclass (EObjectTreeItemFor<type>) for each EObjectTreeItemType.</br>
 * The children of an item are created when needed (i.e. upon the first call to getChildren()).<br/>
 * This class add fields which are needed for handling the items in the tree.
 */
public abstract class EObjectTreeItem extends TreeItem<Object> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItem.class.getName());
  static final String NEWLINE = System.getProperty("line.separator");
  protected static final DataFormat EOBJECT_PATH = new DataFormat("EObjectPath");
  
  /**
   * Caches the result for method isLeaf().
   */
  private Boolean isLeaf = null;
  
  /**
   * If true, the children of this item still have to be created.
   */
  private boolean isFirstTimeChildren = true;
  
  /**
   * Reference to the tree view of which this item is part.
   */
  private EObjectTreeView eObjectTreeView = null;
  
  /**
   * The type of information stored in the item. This value determines what is stored in the other fields.
   */
  private EObjectTreeItemType eObjectTreeItemType = null;

//  /**
//   * Constructor for specifying the EObject, EObjectTreeItemType and presentationDescriptor (so no eStructuralFeature).
//   * 
//   * @param eObject the EObject for this tree item (mandatory)
//   * @param eObjectTreeItemType the EObjectTreeItemType for this tree item (mandatory)
//   * @param presentationDescriptor a TreeNode containing the specification for the representation of this item (mandatory)
//   */
//  public EObjectTreeItem(EObject eObject, EObjectTreeItemType eObjectTreeItemType, EObjectTreeItemDescriptor presentationDescriptor,
//      EObjectTreeView eObjectTreeView) {
//    this(eObject, eObjectTreeItemType, presentationDescriptor, eObjectTreeView);
//  }

  /**
   * Constructor for specifying all information as content of this tree item.
   * 
   * @param Object the value for this tree item (mandatory)
   * @param eObjectTreeItemType the EObjectTreeItemType for this tree item (mandatory)
   * @param eStructuralFeature the EStructuralFeature for this tree item (only mandatory for the types ...)
   * @param presentationDescriptor a TreeNode containing the specification for the representation of this item (mandatory)
   */
  public EObjectTreeItem(Object value, EObjectTreeItemType eObjectTreeItemType,
      EObjectTreeView eObjectTreeView) {
    super(value);
    
    this.eObjectTreeItemType = eObjectTreeItemType;
    this.eObjectTreeView = eObjectTreeView;
        
//    if (presentationDescriptor != null) {
//      setExpanded(presentationDescriptor.isExpandOnCreation());
//    }
        
    LOGGER.info("<=>");
  }
  

  /**
   * Get the reference to the tree view of which this item is part.
   * @return the reference to the tree view of which this item is part.
   */
  public EObjectTreeView getEObjectTreeView() {
    return eObjectTreeView;
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
   * @inheritDoc
   */
  @Override
  public boolean isLeaf() {
    LOGGER.fine("=> eObjectTreeItemContent=" + getValue().toString());
    
    if (isLeaf == null) {
      switch (eObjectTreeItemType) {
      case ATTRIBUTE_LIST_VALUE:
      case ATTRIBUTE_SIMPLE:
        isLeaf = true;
        break;
        
      case OBJECT:
        EObjectTreeItemClassDescriptor classDescriptor = ((EObjectTreeItemForObject) this).getClassDescriptor();
        if (classDescriptor != null) {
          if (classDescriptor.getStructuralFeatureDescriptors().isEmpty()) {
            isLeaf = true;
          } else {
            isLeaf = false;
          }
        } else {
          LOGGER.severe("No classDescriptor for EObjectTreeItem: " + this.toString());
          isLeaf = true;
        }
        break;
        
      case OBJECT_IN_LIST:
        EObjectTreeItemClassDescriptor classInListDescriptor = ((EObjectTreeItemForObjectInList) this).getEObjectTreeItemClassDescriptor();
        if (classInListDescriptor != null) {
          if (classInListDescriptor.getStructuralFeatureDescriptors().isEmpty()) {
            isLeaf = true;
          } else {
            isLeaf = false;
          }
        } else {
          LOGGER.severe("No classDescriptor for EObjectTreeItem: " + this.toString());
          isLeaf = true;
        }
        break;
        
      case ATTRIBUTE_LIST:
      case OBJECT_LIST:
        Object object = getValue();
        isLeaf = true;
        if (object != null) {
          if (object instanceof List<?> list) {
            if (!list.isEmpty()) {
              isLeaf = false;
            }
          }
        }
        break;
      }
    }
    
    LOGGER.info("<= " + isLeaf);
    return isLeaf;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ObservableList<TreeItem<Object>> getChildren() {
    LOGGER.info("=> " + toString());
    
    if (isFirstTimeChildren) {
      isFirstTimeChildren = false;
      ObservableList<TreeItem<Object>> children = buildChildren();
      if (children != null) {
        super.getChildren().setAll(children);
      }
    }
    
    LOGGER.info("<= via super.getChildren()");
    return super.getChildren();
  }
  
  /**
   * Rebuild the children of this node.
   * <p>
   * This method can be called after changes to this node.
   */
  public void rebuildChildren() {
    super.getChildren().clear();
    isLeaf = null;
    ObservableList<TreeItem<Object>> children = buildChildren();
    if (children != null) {
      super.getChildren().addAll(children);
    }
    
  }
  
  /**
   * Create the list of children for a tree item.
   * <p>
   * The items to be shown are defined by the presentation descriptors.<br/>
   * A tree item is one of the following:
   * <ul>
   *   <li>
   *     type OBJECT - for an EObject<br/>
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: OBJECT
   *       </li>
   *       <li>
   *         object: the eObject
   *       </li>
   *       <li>
   *         eStructuralFeature: null
   *       </li>
   *       <li>
   *         presentationDescriptorTreeNode: the corresponding descriptor of type EObjectTreeItemClassDescriptor
   *       </li>
   *     </ul>
   *     There's one child for each EStructuralFeature of this EObject, which is either an EAttribute or an EReference.<br/>
   *     For a single value EAttribute the child is of type ATTRIBUTE_SIMPLE.<br/>
   *     For a list value EAttribute the child is of type ATTRIBUTE_LIST.<br/>
   *     For an EReference to a single value EObject the child is of type OBJECT.<br/>
   *     For an EReference to a list value EObject the child is of type OBJECT_LIST.<br/>
   *   </li>
   *   <li>
   *     type ATTRIBUTE_SIMPLE - for a single value EAttribute (i.e. not a list).
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: ATTRIBUTE_SIMPLE
   *       </li>
   *       <li>
   *         object: the attribute value
   *       </li>
   *       <li>
   *         eStructuralFeature: the EAttribute for this attribute
   *       </li>
   *       <li>
   *         presentationDescriptorTreeNode: the corresponding descriptor of type EObjectTreeItemAttributeDescriptor
   *       </li>
   *     </ul>
   *     This is a leaf node.
   *   </li>
   *   <li>
   *     type ATTRIBUTE_LIST - for a list type EAttribute.
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: ATTRIBUTE_LIST
   *       </li>
   *       <li>
   *         object: the EList with the attribute values
   *       </li>
   *       <li>
   *         eStructuralFeature: the EAttribute for this attribute
   *       </li>
   *       <li>
   *         presentationDescriptorTreeNode: the corresponding descriptor of type EObjectTreeItemAttributeDescriptor
   *       </li>
   *     </ul>
   *     There's one child for each element in the EList.
   *   </li>
   *   <li>
   *     type OBJECT_LIST - for a list of EObjects
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: OBJECT_LIST
   *       </li>
   *       <li>
   *         object: the EList with the eObjects
   *       </li>
   *       <li>
   *         eStructuralFeature: the EReference for this list of EObjects
   *       </li>
   *       <li>
   *         presentationDescriptor: the corresponding descriptor of type EObjectTreeItemClassListReferenceDescriptor
   *       </li>
   *     </ul>
   *     There's one child for each element in the EList.
   *   </li>
   * </ul>
   * 
   * @param eObjectTreeItem the EObjectTreeItem for which the children are to be created.
   * @return the list of children for eObjectTreeItem, or null if there are no children.
   */
  private ObservableList<TreeItem<Object>> buildChildren() {
    LOGGER.info("=> eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<Object>> children = null;

    switch (eObjectTreeItemType) {
    case OBJECT:
    case OBJECT_IN_LIST:
      children = buildChildrenOfEObject();
      break;
      
    case OBJECT_LIST:
      children = buildChildrenOfEObjectList();
      break;
      
    case ATTRIBUTE_LIST:
      children = ((EObjectTreeItemForAttributeList) this).buildChildrenOfAttributeList();
      break;
      
    case ATTRIBUTE_LIST_VALUE:
      // No action, a list value has no children.
      break;
      
    case ATTRIBUTE_SIMPLE:
      // No action, an simple attribute value has no children.
      break;
    }
    
    LOGGER.info("<=");
    return children;
  }
  
  /**
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
  private ObservableList<TreeItem<Object>> buildChildrenOfEObject() {
    LOGGER.info("=> eObjectTreeItem=" + toString());
    
    EObject eObject = (EObject) getValue();
    if (eObject == null) {
      return null;
    }
    
    ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();
    
    EObjectTreeItemClassDescriptor classDescriptor;
    if (this instanceof EObjectTreeItemForObject eObjectTreeItemForObject) {
      classDescriptor = eObjectTreeItemForObject.getClassDescriptor();
    } else {
      classDescriptor = ((EObjectTreeItemForObjectInList) this).getEObjectTreeItemClassDescriptor();
    }

    // Create the children as specified by the descriptor.
    for (EObjectTreeItemDescriptor descriptor: classDescriptor.getStructuralFeatureDescriptors()) {
      LOGGER.info("Handling descriptor: " + descriptor.toString());
      LOGGER.info("Descriptor class: " + descriptor.getClass().getName());
      
      EObjectTreeItem treeItem;
      switch (descriptor.getDescriptorType()) {
      case ATTRIBUTE:
        treeItem = EObjectTreeItemForAttributeSimple.createEObjectTreeItemForAttributeSimple(eObject, (EObjectTreeItemAttributeDescriptor) descriptor, eObjectTreeView, isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case ATTRIBUTE_LIST:
        treeItem = EObjectTreeItemForAttributeList.createEObjectTreeItemForAttributeList(eObject, (EObjectTreeItemAttributeListDescriptor) descriptor, eObjectTreeView, isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case ATTRIBUTE_LIST_VALUE:
        throw new RuntimeException("Descriptor of type ATTRIBUTE_LIST_VALUE cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
            
      case CLASS_REFERENCE:
        treeItem = EObjectTreeItemForObject.createEObjectTreeItemForObject(eObject, (EObjectTreeItemClassReferenceDescriptor) descriptor, eObjectTreeView, isInEditMode());
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case CLASS_LIST:
        treeItem = EObjectTreeItemForObjectList.createEObjectTreeItemForObjectList(eObject, (EObjectTreeItemClassListReferenceDescriptor) descriptor, eObjectTreeView, isInEditMode());
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
  
  private ObservableList<TreeItem<Object>> buildChildrenOfEObjectList() {
    LOGGER.info("=>eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();
    
    Object object = getValue();
    
    // The object is a list of EObjects, each of which will be a child OBJECT.
    @SuppressWarnings("unchecked")
    List<? extends EObject> eObjects = (List<? extends EObject>) object;
    for (EObject listEObject: eObjects) {
      EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
      EObjectTreeItemClassDescriptor childPresentationDescriptorNode = getBestClassDescriptor(listEObject.eClass(), eObjectTreeDescriptor);
      if (childPresentationDescriptorNode == null) {
        throw new RuntimeException("No presentation descriptor found for class: " + listEObject.eClass().getName());
      }
      EObjectTreeItem child = new EObjectTreeItemForObjectInList(listEObject, childPresentationDescriptorNode, eObjectTreeView);
        child.setExpanded(childPresentationDescriptorNode.isExpandOnCreation());
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
   * Find the right presentation descriptor for an eObject.
   * <p>
   * A list of eObjects is of a specific EClass, but the elements may of course also be of a sub type of this EClass.
   * If so, the sub type may have extra attributes to be shown. Therefore there may be several presentation descriptors for the EClass
   * and its expected sub types.<br/>
   * This method first looks for a presentation descriptor for the class of the <code>eObject</code>, if not found it tries to find one
   * for the super type of this class. This continues until a descriptor is found, or until there's no more super type.
   *  
   * @param presentationDescriptorNode the TreeNode whose children contain the available presentation descriptors.
   * @param eObject the EObject for which a presentation descriptor is to be found
   * @return the best presentation descriptor for the <code>eObject</code>.
   * @throw RuntimeException if an illegal descriptor type is encountered, or if no descriptor is found.
   */
//  private TreeNode<EObjectTreeItemDescriptor> findChildNode(TreeNode<EObjectTreeItemDescriptor> presentationDescriptorNode, EObject eObject) {
//    EClass eObjectEClass = eObject.eClass();
//    LOGGER.info("=> className=" + eObjectEClass.getInstanceClassName());
//    
//    TreeNode<EObjectTreeItemDescriptor> descriptorNode = null;
//    
//    do {
//      LOGGER.info("In loop, eObjectEClass=" + eObjectEClass.getInstanceClassName());
//      for (TreeNode<EObjectTreeItemDescriptor> childNode: presentationDescriptorNode.getChildren()) {
//        EObjectTreeItemDescriptor childNodeContent = childNode.getContent();
//        if (childNodeContent instanceof EObjectTreeItemClassDescriptor) {
//          EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = (EObjectTreeItemClassDescriptor) childNodeContent;
//          EClass descriptorEClass = eObjectTreeItemClassDescriptor.getEClass();
//          LOGGER.info("Checking descriptor for class: " + descriptorEClass.getInstanceClassName());
//          if (descriptorEClass.equals(eObjectEClass)) {
//            descriptorNode = childNode;
//            break;
//          }
//        } else {
//          throw new RuntimeException("Illegal descriptor type encountered: " + childNodeContent.toString());
//        }
//      }
//      
//      if (descriptorNode == null) {
//        eObjectEClass = getSuperType(eObjectEClass);
//      }
//    } while (descriptorNode == null  &&  eObjectEClass != null);
//    
////    if (descriptorNode == null) {
////      throw new RuntimeException("No descriptor node found for class: " + eObject.getClass().getName());
////    }
//    
//    LOGGER.info("<= " + (descriptorNode != null ? descriptorNode.toString() : "<null>"));
//    return descriptorNode;
//  }
  
  static EObjectTreeItemClassDescriptor getBestClassDescriptor(EClass eClass, EObjectTreeDescriptor eObjectTreeDescriptor) {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = null;
    
    do {
      eObjectTreeItemClassDescriptor = eObjectTreeDescriptor.getDescriptorForEClass(eClass);
    } while ((eObjectTreeItemClassDescriptor == null)  &&  ((eClass = getSuperType(eClass)) != null));
    
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Get a super type of an EClass.
   * <p>
   * 
   * @param eClass the EClass for which the super type is requested.
   * @return 
   */
  static EClass getSuperType(EClass eClass) {
    LOGGER.info("=> eClass=" + eClass.getInstanceTypeName());
    LOGGER.fine("=> eClass=" + eClass.getInstanceClass().getName());
    EClass superType = null;

    List<EClass> superTypes = eClass.getESuperTypes();
    if (superTypes.size() > 0) {
      superType = superTypes.get(0);
      LOGGER.fine("superType=" + superType.getInstanceClassName() + ",  " + superType.getInstanceClass().getName());
    }

    LOGGER.info("<= " + (superType != null ? superType.getInstanceTypeName() : "<null>"));
    return superType;
  }

  /**
   * Get the index of this child within the list of children of the parent.
   * 
   * @return the index of this child.
   */
  public int getChildIndex() {
    int childIndex = 0;
    
    // move back through the 'list' via previousSibling() and count the
    // number of steps.
    TreeItem<Object> previousSibling = previousSibling();
    
    while(previousSibling != null) {
      childIndex++;
      previousSibling = previousSibling.previousSibling();
    }
    
    return childIndex;
  }
  
  /*
   * Extra methods from here
   */
  
  /**
   * Get the top level EObject of the tree, which is the Object of the content of the root node.
   * 
   * @return the top level EObject of the tree.
   */
  EObject getRootEObject() {
    TreeItem<Object> rootItem = this;
    
    while (rootItem.getParent() != null) {
      rootItem = rootItem.getParent();
    }
    
    return (EObject) rootItem.getValue();
  }

  /**
   * Find a child item of a specified item, which contains a specific EStructuralFeature.
   * 
   * @param eObjectTreeItem the item of which a child is looked for. This value may not be null.
   * @param eStructuralFeature the EStructuralFeature of the child to be found. This value may not be null.
   * @return the specified child, or null if no such child exists.
   */
  public static EObjectTreeItem findChildTreeItem(EObjectTreeItem eObjectTreeItem, EStructuralFeature eStructuralFeature) {
    EObjectTreeItem result = null;
    
    for (TreeItem<Object> child: eObjectTreeItem.getChildren()) {
      EStructuralFeature contentEStructuralFeature = getEStructuralFeature(child);
      if ((contentEStructuralFeature != null)  &&  contentEStructuralFeature.equals(eStructuralFeature)) {
        result = (EObjectTreeItem) child;
        break;
      }
    }

    return result;
  }
  
  public static EStructuralFeature getEStructuralFeature(TreeItem<Object> treeItem) {
    EStructuralFeature eStructuralFeature = null;
    
    if (treeItem instanceof EObjectTreeItemForAttributeList eObjectTreeItemForAttributeList) {
      eStructuralFeature = eObjectTreeItemForAttributeList.getEAttribute();
    } else if (treeItem instanceof EObjectTreeItemForAttributeListValue) {
      // no action, this type has no structural feature
    } else if (treeItem instanceof EObjectTreeItemForAttributeSimple eObjectTreeItemForAttributeSimple) {
      eStructuralFeature = eObjectTreeItemForAttributeSimple.getEAttribute();
    } else if (treeItem instanceof EObjectTreeItemForObject eObjectTreeItemForObject) {
      eStructuralFeature = eObjectTreeItemForObject.getEReference();
    } else if (treeItem instanceof EObjectTreeItemForObjectInList) {
      // no action, this type has no structural feature
    } else if (treeItem instanceof EObjectTreeItemForObjectList eObjectTreeItemForObjectList) {
      eStructuralFeature = eObjectTreeItemForObjectList.getEReference();
    }
    
    return eStructuralFeature;
  }

  /**
   * Change the edit mode.
   * 
   * @param editMode
   */
  public void setEditMode(boolean editMode) {
    // If the children of this item aren't created yet, nothing has to be done.
    if (isFirstTimeChildren) {
      return;
    }
    
    if (editMode) {
      switchToEditMode();
    } else {
      switchToViewMode();
    }
  }
  
  /**
   * Switch to edit mode.
   * <p>
   * Add the 'empty' value nodes, which aren't shown in view mode.
   */
  private void switchToEditMode() {
    LOGGER.info("=>");

    switch (eObjectTreeItemType) {
    case OBJECT:
    case OBJECT_IN_LIST:
      addEmptyChildrenOfEObject();
      break;
      
    case OBJECT_LIST:
      // No action as all children are already present
      break;
      
    case ATTRIBUTE_LIST:
      // No action as all children are already present
      break;
      
    case ATTRIBUTE_LIST_VALUE:
      // No action, a list value has no children.
      break;
      
    case ATTRIBUTE_SIMPLE:
      // No action, an simple attribute value has no children.
      break;
    }
    
    for (TreeItem<Object> child: getChildren()) {
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) child;
      eObjectTreeItem.setEditMode(true);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Add the empty children of an EObject item.
   * <p>
   * In view mode the empty children are left out of the tree. So upon switching to edit mode these empty children have to be added.
   * This is done by this method.<br/>
   * The 'object' in the EObjectTreeItemContent is by definition an EObject.<br/>
   */
  private void addEmptyChildrenOfEObject() {
    LOGGER.info("=> eObjectTreeItem=" + toString());
    
    EObject eObject = (EObject) getValue();
    if (eObject == null) {
      return;
    }
    
    ObservableList<TreeItem<Object>> children = getChildren();
    
    EObjectTreeItemClassDescriptor classDescriptor = ((EObjectTreeItemForObject) this).getClassDescriptor();

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
          treeItem = EObjectTreeItemForAttributeSimple.createEObjectTreeItemForAttributeSimple(eObject, eObjectTreeItemAttributeDescriptor, eObjectTreeView, isInEditMode());
        } else {
          childIndex++;
        }
        break;
        
      case ATTRIBUTE_LIST:
        EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = (EObjectTreeItemAttributeListDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemAttributeListDescriptor.getEAttribute())) {        
          treeItem = EObjectTreeItemForAttributeList.createEObjectTreeItemForAttributeList(eObject, eObjectTreeItemAttributeListDescriptor, eObjectTreeView, isInEditMode());
        } else {
          childIndex++;
        }
        break;
        
      case ATTRIBUTE_LIST_VALUE:
        throw new RuntimeException("Descriptor of type ATTRIBUTE_LIST_VALUE cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
            
      case CLASS_REFERENCE:
        EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = (EObjectTreeItemClassReferenceDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemClassReferenceDescriptor.getEReference())) {        
          treeItem = EObjectTreeItemForObject.createEObjectTreeItemForObject(eObject, eObjectTreeItemClassReferenceDescriptor, eObjectTreeView, isInEditMode());
        } else {
          childIndex++;
        }
        break;
        
      case CLASS_LIST:
        EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = (EObjectTreeItemClassListReferenceDescriptor) descriptor;
        if (!doChildrenContainItemForFeature(eObjectTreeItemClassListReferenceDescriptor.getEReference())) {        
          treeItem = EObjectTreeItemForObjectList.createEObjectTreeItemForObjectList(eObject, eObjectTreeItemClassListReferenceDescriptor, eObjectTreeView, isInEditMode());
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
      if (feature.equals(getEStructuralFeature(item))) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Switch to view mode.
   * <p>
   * The 'empty' nodes are removed.
   */
  private void switchToViewMode() {
    LOGGER.severe("=>");

    switch (eObjectTreeItemType) {
    case OBJECT:
    case OBJECT_IN_LIST:
      removeChildrenOfEObject();
      break;
      
    case OBJECT_LIST:
      // No action as all children are already present
      break;
      
    case ATTRIBUTE_LIST:
      // No action as all children are already present
      break;
      
    case ATTRIBUTE_LIST_VALUE:
      // No action, a list value has no children.
      break;
      
    case ATTRIBUTE_SIMPLE:
      // No action, an simple attribute value has no children.
      break;
    }
    
    for (TreeItem<Object> item: getChildren()) {
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) item;
      eObjectTreeItem.setEditMode(false);
    }
    
    LOGGER.severe("<=");
    
  }
  
  /**
   * Check whether this item is in edit mode or not.
   * 
   * @return true if the tree is in edit mode, false otherwise.
   */
  public boolean isInEditMode() {
    return eObjectTreeView.isEditable();
  }

  private void removeChildrenOfEObject() {
    EObject eObject = (EObject) getValue();
    
    Iterator<TreeItem<Object>> iterator = getChildren().iterator();
    while (iterator.hasNext()) {
      TreeItem<Object> item = iterator.next();
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) item;
      EStructuralFeature eStructuralFeature = null;
      // TODO this is same functionality as getEStructuralFeature() (apart from the exceptions thrown here), chose which one to use.
      switch (eObjectTreeItem.getEObjectTreeItemType()) {
      case OBJECT:
        eStructuralFeature = ((EObjectTreeItemForObject) eObjectTreeItem).getEReference();
        break;
        
      case OBJECT_IN_LIST:
        // Shall not exist here.
        throw new RuntimeException("Illegal OBJECT_IN_LIST child of tree item: " + toString());
        
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
  }

  /**
   * Add a child to an object list item.
   * <p>
   * If the children haven't been built yet, nothing is done, because the right children will be built when needed.
   * 
   * @param position the position in the list of objects and in the child items.
   */
  public void addObjectListChild(int position) {
    LOGGER.severe("=>");
    
    isLeaf = false;
    
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
    EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
    EObjectTreeItemClassDescriptor childPresentationDescriptorNode = getBestClassDescriptor(listEObject.eClass(), eObjectTreeDescriptor);
    if (childPresentationDescriptorNode == null) {
      throw new RuntimeException("No presentation descriptor found for class: " + listEObject.eClass().getName());
    }
    EObjectTreeItem child = new EObjectTreeItemForObjectInList(listEObject, childPresentationDescriptorNode, eObjectTreeView);
    if (children.size() < position + 1) {
      children.add(child);
    } else {
      children.add(position, child);
    }
    
    this.setExpanded(true);
    eObjectTreeView.getSelectionModel().select(child);
            
    LOGGER.severe("<=");
  }

  /**
   * Remove a child from an object list item.
   * <p>
   * If the children haven't been built yet, nothing is done, because the right children will be built when needed.
   * 
   * @param position the position in the list of objects and in the child items.
   */
  public void removeObjectListChild(int position) {
    LOGGER.severe("=>");
    
    if (isFirstTimeChildren) {
      // The children haven't been built yet, so we don't have to add anything.
      LOGGER.severe("Children haven't been built yet, so no action");
      return;
    }
    
    ObservableList<TreeItem<Object>> children = getChildren();
    children.remove(position);
        
    LOGGER.severe("<=");
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
      if (eStructuralFeature.equals(getEStructuralFeature(childEObjectTreeItem))) {
        LOGGER.severe("child found, going to rebuild children");
        setExpanded(true);  // hack. This way the TreeView seems to re-evaluate whether the item is a leaf.
        childEObjectTreeItem.rebuildChildren();
        break;
      }
    }
    
  }

  /**
   * Handle the starting of a drag event.
   * <p>
   * Only dragging of an EObject is supported.<br/>
   * If the item is an EObject, an {@link EObjectPath} is created and serialized as bytes.
   * {@code ClipboardContent} is created with this data, with type {@link EOBJECT_PATH}.
   * A {@code Dragboard} is created for a MOVE and with the {@code ClipboardContent} as content.
   * 
   * @param event the {@code MouseEvent} which detected the drag start.
   */
  public void handleDragDetected(MouseEvent event, Node node) {
    Object object = getValue();
    if (object instanceof EObject eObject) {
      EObjectPath eObjectPath = new EObjectPath(eObject);
      ByteBuffer eObjectPathAsBytes = eObjectPath.getSerializedData();
      
      ClipboardContent clipboardContent = new ClipboardContent();
      clipboardContent.put(EOBJECT_PATH, eObjectPathAsBytes);
      
      node.startDragAndDrop(TransferMode.MOVE).setContent(clipboardContent);
    }

    event.consume();
  }

  /**
   * Check whether the information of the dragEvent can be dropped on this tree item and if so return the TransferMode.
   * <p>
   * If the dragboard content is an EOBJECT_PATH, the isDropPossible() of the helper is called. If this has a positive reply, the TransferMode is MOVE.</br>
   * If the dragboard content isn't an EOBJECT_PATH, or a drop isn't possible, the isDropPossibleFunction (when set on the TreeView) is called.
   * 
   * @param dragEvent the Drag and Drop event.
   * @return true if the {@code dragEvent} can be handled.
   */
  public TransferMode isDropPossible(DragEvent dragEvent) {

//    Dragboard dragboard = dragEvent.getDragboard();
//
//    if (dragboard.hasContent(EOBJECT_PATH)) {
//      EObject sourceEObject = getSourceObject(dragboard);
//      if (sourceEObject != null  &&
//          treeCellHelper.isDropPossible(sourceEObject, (EObjectTreeItem)  getTreeItem())) {
//        return TransferMode.MOVE;        
//      }
//    }
//        
//    BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction = ((EObjectTreeView) getTreeView()).getIsDropPossibleFunction();
//    if (isDropPossibleFunction != null) {
//      if (isDropPossibleFunction.test((EObjectTreeItem) getTreeItem(), dragboard)) {
//        return TransferMode.COPY;
//      }
//    }
    
    return null;
  }

  public void handleDragDropped(DragEvent dragEvent) {
  }
  
  protected EReference getApplicableEReference(EObjectTreeItem eObjectTreeItem) {
    if (eObjectTreeItem instanceof EObjectTreeItemForObject eObjectTreeItemForObject) {
      return eObjectTreeItemForObject.getEReference();
    } else if (eObjectTreeItem instanceof EObjectTreeItemForObjectInList eObjectTreeItemForObjectInList) {
      // in this case the reference is in the parent, which is a EObjectTreeItemForObjectList
      EObjectTreeItemForObjectList eObjectTreeItemForObjectList = (EObjectTreeItemForObjectList) eObjectTreeItemForObjectInList.getParent();
      return eObjectTreeItemForObjectList.getEReference();
    } else {
      return null;
    }
  }

  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(NEWLINE);
    buf.append("isFirstTimeChildren: ").append(isFirstTimeChildren).append(NEWLINE);
    buf.append("isLeaf: ").append(isLeaf).append(NEWLINE);
    buf.append("value: ").append(getValue().toString()).append(NEWLINE);
    buf.append("type: ").append(eObjectTreeItemType.name()).append(NEWLINE);
    
    return buf.toString();
  }
}
