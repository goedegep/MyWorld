package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * This class represents EObject tree items.
 * <p>
 * The children of an item are created when needed (i.e. upon the first call to getChildren()).<br/>
 * This class add fields which are needed for handling the items in the tree. Information that is needed for the representation of an item (by TreeCell) is
 * part of the class {@link EObjectTreeItemContent}.
 */
public class EObjectTreeItem extends TreeItem<EObjectTreeItemContent> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItem.class.getName());
  
  /**
   * Caches the result for method isLeaf().
   */
  private Boolean isLeaf = null;
  
  /**
   * If true, the children of this item still have to be created.
   */
  private boolean isFirstTimeChildren = true;

  /**
   * Constructor for specifying the EObject, EObjectTreeItemType and presentationDescriptor (so no eStructuralFeature).
   * 
   * @param eObject the EObject for this tree item (mandatory)
   * @param eObjectTreeItemType the EObjectTreeItemType for this tree item (mandatory)
   * @param presentationDescriptor a TreeNode containing the specification for the representation of this item (mandatory)
   */
  public EObjectTreeItem(EObject eObject, EObjectTreeItemType eObjectTreeItemType, EObjectTreeItemDescriptor presentationDescriptor,
      EObjectTreeView eObjectTreeView) {
    this(eObject, eObjectTreeItemType, null, presentationDescriptor, eObjectTreeView);
  }

  /**
   * Constructor for specifying all information as content of this tree item.
   * 
   * @param eObject the EObject for this tree item (mandatory)
   * @param eObjectTreeItemType the EObjectTreeItemType for this tree item (mandatory)
   * @param eStructuralFeature the EStructuralFeature for this tree item (only mandatory for the types ...)
   * @param presentationDescriptor a TreeNode containing the specification for the representation of this item (mandatory)
   */
  public EObjectTreeItem(Object object, EObjectTreeItemType eObjectTreeItemType, EStructuralFeature eStructuralFeature,
      EObjectTreeItemDescriptor presentationDescriptor, EObjectTreeView eObjectTreeView) {
    super(new EObjectTreeItemContent(object, eObjectTreeItemType, eStructuralFeature, presentationDescriptor, eObjectTreeView));
        
    if (presentationDescriptor != null) {
      setExpanded(presentationDescriptor.isExpandOnCreation());
    }
        
    LOGGER.info("<=>");
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isLeaf() {
    LOGGER.fine("=> eObjectTreeItemContent=" + getValue().toString());
    
    if (isLeaf == null) {
      // Some items, like an attribute value, are always leaves. However an EObject is also a leave, if the descriptor doesn't specify any structural feature.
      // Therefore we call getChildren(), which creates any children, and, if there are no children, the node is a leaf.
      isLeaf = getChildren().isEmpty();
    }
    
    LOGGER.fine("<= " + isLeaf);
    return isLeaf;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ObservableList<TreeItem<EObjectTreeItemContent>> getChildren() {
    LOGGER.info("=>");
    
    if (isFirstTimeChildren) {
      isFirstTimeChildren = false;
      ObservableList<TreeItem<EObjectTreeItemContent>> children = buildChildren();
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
    ObservableList<TreeItem<EObjectTreeItemContent>> children = buildChildren();
    if (children != null) {
      super.getChildren().setAll(children);
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
  private ObservableList<TreeItem<EObjectTreeItemContent>> buildChildren() {
    LOGGER.info("=> eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<EObjectTreeItemContent>> children = null;

    EObjectTreeItemContent eObjectTreeItemContent = getValue();
    EObjectTreeItemType eObjectTreeItemType = eObjectTreeItemContent.getEObjectTreeItemType();
    
    switch (eObjectTreeItemType) {
    case OBJECT:
      children = buildChildrenOfEObject();
      break;
      
    case OBJECT_LIST:
      children = buildChildrenOfEObjectList();
      break;
      
    case ATTRIBUTE_LIST:
      children = buildChildrenOfAttributeList();
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
  private ObservableList<TreeItem<EObjectTreeItemContent>> buildChildrenOfEObject() {
    LOGGER.info("=> eObjectTreeItem=" + toString());
    
    EObjectTreeItemContent eObjectTreeItemContent = getValue();
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    if (eObject == null) {
      return null;
    }
    
    ObservableList<TreeItem<EObjectTreeItemContent>> children = FXCollections.observableArrayList();
    EObjectTreeItemDescriptor presentationDescriptor = eObjectTreeItemContent.getPresentationDescriptor();
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    EObjectTreeItemClassDescriptor classDescriptor = null;
    if (presentationDescriptor instanceof EObjectTreeItemClassReferenceDescriptor) {
      EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = (EObjectTreeItemClassReferenceDescriptor) presentationDescriptor;
      EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
      EReference eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
//      EObjectTreeItemClassDescriptor eClassPresentationDescriptor = eObjectTreeDescriptor.getDescriptorForEClass((EClass) eReference.getEType());
      EObjectTreeItemClassDescriptor eClassPresentationDescriptor = getBestClassDescriptor((EClass) eReference.getEType(), eObjectTreeDescriptor);
      if (eClassPresentationDescriptor == null) {
        throw new RuntimeException("No descriptor specified for class " + eReference.getEType().getName());
      }
      // Get the best possible descriptor: if there is a child object (which may be a subclass of the type of the reference) use this class,
      // else use the type of the reference.
      if (eObject != null) {
        EObjectTreeItemClassDescriptor objectSpecificDescriptor = getBestClassDescriptor(eObject.eClass(), eObjectTreeDescriptor);
        if (objectSpecificDescriptor != null) {
          eClassPresentationDescriptor = objectSpecificDescriptor;
        }
      }
      classDescriptor = eClassPresentationDescriptor;
    } else if (presentationDescriptor instanceof EObjectTreeItemClassDescriptor) {
      classDescriptor = (EObjectTreeItemClassDescriptor) presentationDescriptor;
    } else {
      if (presentationDescriptor == null) {
        throw new RuntimeException("No presentation descriptor for item: " + eObjectTreeItemContent.toString());
      } else {
        throw new RuntimeException("Illegal descriptor type: " + presentationDescriptor.toString());
      }
    }
    
//    EObjectTreeItemType childType;
    
    for (EObjectTreeItemDescriptor descriptor: classDescriptor.getStructuralFeatureDescriptors()) {
      LOGGER.info("Handling descriptor: " + descriptor.toString());
      LOGGER.info("Descriptor class: " + descriptor.getClass().getName());
      
      EObjectTreeItem treeItem;
      switch (descriptor.getDescriptorType()) {
      case ATTRIBUTE:
        treeItem = buildAttributeChild(eObjectTreeItemContent, (EObjectTreeItemAttributeDescriptor) descriptor);
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case ATTRIBUTE_LIST:
        treeItem = buildAttributeListChild(eObjectTreeItemContent, (EObjectTreeItemAttributeListDescriptor) descriptor);
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case ATTRIBUTE_LIST_VALUE:
        throw new RuntimeException("Descriptor of type ATTRIBUTE_LIST_VALUE cannot occur as child of EObject. Descriptor is: " + descriptor.toString());
            
      case CLASS_REFERENCE:
        treeItem = buildClassReferenceChild(eObjectTreeItemContent, (EObjectTreeItemClassReferenceDescriptor) descriptor);
        if (treeItem != null) {
          children.add(treeItem);
        }
        break;
        
      case CLASS_LIST:
        treeItem = buildClassListReferenceChild(eObjectTreeItemContent, (EObjectTreeItemClassListReferenceDescriptor) descriptor);
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
   * Build a child for an EAttribute.
   * <p>
   * This applies only to 'single' attributes.<br/>
   * An attribute which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param eObjectTreeItemAttributeDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  private EObjectTreeItem buildAttributeChild(EObjectTreeItemContent eObjectTreeItemContent, EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor) {
    LOGGER.info("=> eObjectTreeItemContent" + eObjectTreeItemContent.toString());
        
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    if (eObject == null) {
      LOGGER.severe("object is null in TreeItem");
      LOGGER.severe("StructuralFeature=" +eObjectTreeItemContent.getEStructuralFeature().getName());
    }
    EAttribute eAttribute = eObjectTreeItemAttributeDescriptor.getEAttribute();
    boolean editMode = eObjectTreeItemContent.isInEditMode();
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    EObjectTreeItemType childType;

    EList<EAttribute> objectAttributes = eObject.eClass().getEAllAttributes();
    if (objectAttributes.contains(eAttribute)) {
      if (editMode  ||  eObject.eIsSet(eAttribute)) {
        Object childObject = eObject.eGet(eAttribute);
        if (eAttribute.isMany()) {
           throw new RuntimeException("Descriptor doesn't match with reference, eAttribute=" + eAttribute.toString());
        } else {
          childType = EObjectTreeItemType.ATTRIBUTE_SIMPLE;
        }
        return new EObjectTreeItem(childObject, childType, eAttribute, eObjectTreeItemAttributeDescriptor, eObjectTreeView);
      } else {
        return null;
      }
    } else {
      throw new RuntimeException("EObjectTreeItemDescriptor for non-existing attribute: " + eAttribute.getName());
    }
  }
  
  /**
   * Build a child for a list of EAttribute.
   * <p>
   * This applies to both 'many' and 'single' attributes.<br/>
   * An attribute which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param eObjectTreeItemAttributeDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  private EObjectTreeItem buildAttributeListChild(EObjectTreeItemContent eObjectTreeItemContent, EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor) {
    LOGGER.info("=>");
    
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    EAttribute eAttribute = eObjectTreeItemAttributeListDescriptor.getEAttribute();
    boolean editMode = eObjectTreeItemContent.isInEditMode();
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    EObjectTreeItemType childType;

    EList<EAttribute> objectAttributes = eObject.eClass().getEAllAttributes();
    if (objectAttributes.contains(eAttribute)) {
      if (editMode  ||  eObject.eIsSet(eAttribute)) {
        Object childObject = eObject.eGet(eAttribute);
        if (eAttribute.isMany()) {
          // This tree item will only show the attribute name, child nodes will be created for each value.
          // Therefore the value (a list) is stored in this node.
          childType = EObjectTreeItemType.ATTRIBUTE_LIST;
        } else {
          throw new RuntimeException("Descriptor doesn't match with reference. eAttribute is " + eAttribute.toString());
        }
        return new EObjectTreeItem(childObject, childType, eAttribute, eObjectTreeItemAttributeListDescriptor, eObjectTreeView);
      } else {
        return null;
      }
    } else {
      throw new RuntimeException("EObjectTreeItemDescriptor for non-existing attribute: " + eAttribute.getName());
    }
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
  private EObjectTreeItem buildClassReferenceChild(EObjectTreeItemContent eObjectTreeItemContent, EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor) {
    LOGGER.info("=>");
    
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    EReference eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
    boolean editMode = eObjectTreeItemContent.isInEditMode();
    LOGGER.info("descriptorEReference=" + eReference.getName());
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    
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
//          return new EObjectTreeItem(childObject, EObjectTreeItemType.OBJECT, eReference, eClassPresentationDescriptor, eObjectTreeView);
          return new EObjectTreeItem(childObject, EObjectTreeItemType.OBJECT, eReference, eObjectTreeItemClassReferenceDescriptor, eObjectTreeView);
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
  
  /**
   * Build a child for a Class List Reference.
   * <p>
   * A reference which isn't set, is only added in 'edit mode'.
   * 
   * @param eObjectTreeItemContent the content of the item to which this will be a child.
   * @param EObjectTreeItemClassListReferenceDescriptor the descriptor for the child.
   * @return the created child item, or null if it wasn't created.
   */
  private EObjectTreeItem buildClassListReferenceChild(EObjectTreeItemContent eObjectTreeItemContent, EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor) {
    LOGGER.info("=>");
    
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    EReference eReference = eObjectTreeItemClassListReferenceDescriptor.getEReference();
    boolean editMode = eObjectTreeItemContent.isInEditMode();
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    
    EList<EReference> objectReferences = eObject.eClass().getEAllReferences();
    if (objectReferences.contains(eReference)) {
      if (editMode  ||  eObject.eIsSet(eReference)) {
        Object childObject = eObject.eGet(eReference);
        if (eReference.isMany()) {
          return new EObjectTreeItem(childObject, EObjectTreeItemType.OBJECT_LIST, eReference, eObjectTreeItemClassListReferenceDescriptor, eObjectTreeView);
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
  
  private ObservableList<TreeItem<EObjectTreeItemContent>> buildChildrenOfEObjectList() {
    LOGGER.info("=>eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<EObjectTreeItemContent>> children = FXCollections.observableArrayList();
    
    EObjectTreeItemContent eObjectTreeItemContent = getValue();
    Object object = eObjectTreeItemContent.getObject();
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    
    // The object is a list of EObjects, each of which will be a child OBJECT.
    @SuppressWarnings("unchecked")
    List<? extends EObject> eObjects = (List<? extends EObject>) object;
    for (EObject listEObject: eObjects) {
      EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
      EObjectTreeItemDescriptor childPresentationDescriptorNode = getBestClassDescriptor(listEObject.eClass(), eObjectTreeDescriptor);
      if (childPresentationDescriptorNode == null) {
        throw new RuntimeException("No presentation descriptor found for class: " + listEObject.eClass().getName());
      }
      children.add(new EObjectTreeItem(listEObject, EObjectTreeItemType.OBJECT, null, childPresentationDescriptorNode, eObjectTreeView));
    }
    
    LOGGER.info("<=");
    if (children.isEmpty()) {
      return null;
    } else {
      return children;
    }
  }
  
  private ObservableList<TreeItem<EObjectTreeItemContent>> buildChildrenOfAttributeList() {
    LOGGER.info("=>eObjectTreeItem=" + toString());
    
    ObservableList<TreeItem<EObjectTreeItemContent>> children = FXCollections.observableArrayList();
    
    EObjectTreeItemContent eObjectTreeItemContent = getValue();
    Object object = eObjectTreeItemContent.getObject();
    EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
    EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = (EObjectTreeItemAttributeListDescriptor) eObjectTreeItemContent.getPresentationDescriptor();
    EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor = eObjectTreeItemAttributeListDescriptor.geteObjectTreeItemAttributeListValueDescriptor();
    
    // The object is a List of Objects (values).
    // Add a child for each value of the list of values of this attribute.
    @SuppressWarnings("unchecked")
    List<? extends Object> values = (List<? extends Object>) object;
    for (Object listValue: values) {
      children.add(new EObjectTreeItem(listValue, EObjectTreeItemType.ATTRIBUTE_LIST_VALUE, null, eObjectTreeItemAttributeListValueDescriptor, eObjectTreeView));
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
  
  private EObjectTreeItemClassDescriptor getBestClassDescriptor(EClass eClass, EObjectTreeDescriptor eObjectTreeDescriptor) {
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
  private EClass getSuperType(EClass eClass) {
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
    TreeItem<EObjectTreeItemContent> previousSibling = previousSibling();
    
    while(previousSibling != null) {
      childIndex++;
      previousSibling = previousSibling.previousSibling();
    }
    
    return childIndex;
  }
  
  /**
   * Get a textual representation of the value of this item.
   */
  @Override
  public String toString() {
    return getValue().toString();
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
    TreeItem<EObjectTreeItemContent> rootItem = this;
    
    while (rootItem.getParent() != null) {
      rootItem = rootItem.getParent();
    }
    
    return (EObject) rootItem.getValue().getObject();
  }

  /**
   * Find a child item of a specified item, which contains a specific EStructuralFeature.
   * 
   * @param eObjectTreeItem the item of which a child is looked for. This value may not be null.
   * @param eStructuralFeature the EStructuralFeature of the child to be found. This value may not be null.
   * @return the specified child, or null if no such child exists.
   */
  public EObjectTreeItem findChildTreeItem(EObjectTreeItem eObjectTreeItem, EStructuralFeature eStructuralFeature) {
    EObjectTreeItem result = null;
    
    for (TreeItem<EObjectTreeItemContent> child: eObjectTreeItem.getChildren()) {
      EObjectTreeItemContent content = child.getValue();
      EStructuralFeature contentEStructuralFeature = content.getEStructuralFeature();
      if ((contentEStructuralFeature != null)  &&  contentEStructuralFeature.equals(eStructuralFeature)) {
        result = (EObjectTreeItem) child;
        break;
      }
    }

    return result;
  }
}
