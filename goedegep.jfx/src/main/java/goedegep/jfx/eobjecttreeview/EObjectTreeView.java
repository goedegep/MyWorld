package goedegep.jfx.eobjecttreeview;

import java.nio.file.FileVisitResult;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.treeview.TreeItemVisitResult;
import goedegep.jfx.treeview.TreeItemVisitor;
import goedegep.jfx.treeview.TreeViewWalker;
import goedegep.util.emf.EObjectPath;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.emf.EmfUtil;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import goedegep.util.text.Indent;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

/**
 * This class provides a tree view for a hierarchy of {@link EObject}s.
 * <p>
 * <b>Descriptor for which parts are shown and how</b><br/>
 * The tree view is highly configurable via an {@link EObjectTreeDescriptor}. However this descriptor can also be completely omitted.
 * In this case an EObjectTreeDescriptor is generated, where all information is derived from the EObjects.
 * The EObjectTreeDescriptor is derived from the first eObject set, either via the constructor or via the method {@link #setEObject}.<br/><br/>
 * 
 * <b>Edit mode</b><br/>
 * The tree view is either in 'view mode' or in 'edit mode'. In 'view mode' attributes which have no value are not shown and the items cannot be changed,
 * in 'edit mode' all attributes are shown (unless the Descriptor specifies that there aren't to be shown) and the items can be changed.<br/><br/>
 * 
 * <b>ObjectSelector</b><br/>
 * The tree view implements the ObjectSelector interface. This normally provides an object of a specific type. A tree however contains items of different types.
 * This also means that some context information is required. Therefore objectSelected() provides the selected tree item (instead of the value).
 * 
 */
public class EObjectTreeView extends TreeView<Object> implements ObjectSelector<TreeItem<Object>> {
  
  /*
   * A TreeView consists of items of type TreeItem.
   * The items in the tree have to contain all relevant information. In this case:
   * - The value of the item
   * - Presentation information
   * This information is stored in the objects of the type EObjectTreeItemContent. Therefore the type EObjectTreeItem (for the items in the tree)
   * is of type TreeItem<EObjectTreeItemContent>.
   * 
   * The structure of the tree is as follows.
   * The root node is an OBJECT node for the EObject to be shown.
   * An OBJECT node has the following content:
   * eObjectTreeItemType = OBJECT
   * object = the eObject
   * eStructuralFeature = null
   * presentationDescriptorTreeNode = the corresponding descriptor
   * An OBJECT node has one child for each EStructuralFeature of the eObject, which is either an EAttribute or an EReference.
   *   
   * 
   * A TreeCell is used to render an item in a TreeView.</br>
   * Although the rendering largely depends on the type of the item, there is only a single type of TreeCell,
   * which is created by a factory set via setCellFactory(). Here the TreeCell is type EObjectTreeCell which itself is a TreeCell<EObjectTreeItemContent>.
   * Therefore the actual work is done by type dependend helpers. These helpers all implement the EObjectTreeCellHelper interface.<br/>
   * The common part is implemented by EObjectTreeCellHelperAbstract, which is a generic type with a subtype of EObjectTreeItemDescriptor as parameter.
   * For each type defined by EObjectTreeItemType there is a subtype of EObjectTreeCellHelperAbstract.
   * A TreeCell instance is also reused to render different items. Each time the TreeCell instance is assigned to an item, the method updateItem is called.
   * Therefore this method creates the right helper for the item type and then delegates the work to the helper by calling updateItem on the helper.<br/>
   * 
   */
  
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeView.class.getName());
  protected static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The description of what is shown in the tree and how.
   */
  private EObjectTreeDescriptor eObjectTreeDescriptor;
  
  /**
   * Content adapter to react to changes in the root EObject hierarchy.
   */
  private EContentAdapter eContentAdapter = null;
  
  /**
   * ObjectSelector listeners to the selected object in the tree.
   */
  private List<ObjectSelectionListener<TreeItem<Object>>> objectSelectionListeners = new ArrayList<>();
  
  /**
   * Function to check whether a drop is possible on an item.
   */
  private BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction;
  
  /**
   * Function to handle the drop.
   */
  private BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction;
  
  /**
   * Indicates that a notification has to be ignored, because it change was done withing the tree view itself
   */
  boolean ignoreNotification = false;
  
  
  /**
   * Constructor for a default tree view (no EObjectTreeDescriptor specified).
   * <p>
   * All information of the eObject is shown. Labels are derived from the related EClass.
   * 
   * @param eObject the EObject (hierarchy) to be shown in the tree. (optional)
   * @param editMode if true, the tree is shown in 'edit mode', otherwise the tree is shown in 'view mode'.
   */
  public EObjectTreeView(EObject eObject, boolean editMode) {
    this(eObject, null, editMode);
  }
  
  /**
   * Constructor for a tree view where a descriptor can be specified.
   * 
   * @param eObject the EObject (hierarchy) to be shown in the tree. (mandatory)
   * @param eObjectTreeDescriptor an optional descriptor for what is show and how.
   * @param editMode if true, the tree is shown in 'edit mode', otherwise the tree is shown in 'view mode'.
   */
  public EObjectTreeView(EObject eObject, EObjectTreeDescriptor eObjectTreeDescriptor, boolean editMode) {
    super();
    
    this.eObjectTreeDescriptor = eObjectTreeDescriptor;
    
    setEditable(editMode);
    
    setCellFactory(treeView -> new EObjectTreeCell());
    
    // React to changes in the selected item, to notify ObjectSelection listeners.
    getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

      @Override
      public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (newValue != null) {
          @SuppressWarnings("unchecked")
          TreeItem<Object> selectedItem = (TreeItem<Object>) newValue;
          LOGGER.info("Selected Node: " + selectedItem.getValue());
          notifySelectedObjectListeners(selectedItem);
        } else {
          LOGGER.info("Selected Node: (null)");
          notifySelectedObjectListeners(null);
        }
      }

    });
    
    eContentAdapter = createEContentAdapter();
    
    setEObject(eObject);
  }
  
  /**
   * Set the isDropPossible function.
   * 
   * @param isDropPossibleFunction the new isDropPossible function.
   */
  public void setIsDropPossibleFunction(BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction) {
    this.isDropPossibleFunction = isDropPossibleFunction;
  }
  
  /**
   * Get the isDropPossible function.
   * 
   * @return the isDropPossible function.
   */
  public BiPredicate<EObjectTreeItem, Dragboard> getIsDropPossibleFunction() {
    return isDropPossibleFunction;
  }
  
  /**
   * Set the handleDrop function.
   * 
   * @param handleDropFunction the new handleDrop function.
   */
  public void setHandleDropFunction(BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction) {
    this.handleDropFunction = handleDropFunction;
  }
  
  /**
   * Get the handleDrop function.
   * 
   * @return the handleDrop function.
   */
  public BiPredicate<EObjectTreeItem, Dragboard> getHandleDropFunction() {
    return handleDropFunction;
  }
  
  /**
   * Set the mode to 'view mode' or 'edit mode'.
   * 
   * @param editMode if true, the mode is set to 'edit mode', else the mode is set to 'view mode'.
   */
  public void setEditMode(boolean editMode) {
    LOGGER.info("=> editMode=" + editMode);
    
    setEditable(editMode);
    EObjectTreeItem root = (EObjectTreeItem) getRoot();
    root.setEditMode(editMode);
  }
  
  /**
   * Get the EObject of the root item.
   * 
   * @return the EObject of the root item.
   */
  private EObject getRootEObject() {
    EObject rootEObject = null;
    
    TreeItem<Object> rootItem = getRoot();
    if (rootItem != null) {
      rootEObject = (EObject) rootItem.getValue();
    }
    
    return rootEObject;
  }

  /**
   * Get the presentation descriptor.
   * <p>
   * The presentation is either:
   * <ul>
   * <li>
   * the presentation descriptor passed as parameter to the constructor.<br/>
   * This is the case if a presentation descriptor was passed to the constructor.
   * </li>
   * <li>
   * the generated presentation descriptor.<br/>
   * This is the case if the constructor was called without a presentation descriptor, and
   * an eObject has been set (from which the descriptor was derived).
   * </li>
   * <li>
   * null<br/>
   * This is the case if the constructor was called without a presentation descriptor, and
   * no eObject has been set yet.
   * </li>
   * </ul>
   * 
   * @return the presentation descriptor.
   */
  public EObjectTreeDescriptor getEObjectTreeDescriptor() {
    return eObjectTreeDescriptor;
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
  EObjectTreeItemClassDescriptor getBestClassDescriptor(EClass eClass) {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = null;

    do {
      eObjectTreeItemClassDescriptor = eObjectTreeDescriptor.getDescriptorForEClass(eClass);
    } while ((eObjectTreeItemClassDescriptor == null)  &&  ((eClass = EmfUtil.getSuperType(eClass)) != null));

    return eObjectTreeItemClassDescriptor;
  }
  
  public static Image getListIcon() {
    return new Image(EObjectTreeCellHelperForObjectList.class.getResourceAsStream("Three dots.png"), 36, 18, true, true);
  }
  
  /**
   * Set a new EObject to be shown in the tree view.
   * <p>
   * If there is no tree descriptor specified, the default descriptor is created.
   * 
   * @param eObject the new EObject to be shown in the tree. If null, the tree view will be cleared.
   */
  public void setEObject(EObject eObject) {
    if ((eObject != null)  &&  (eObject.eResource() == null)) {
      throw new IllegalArgumentException("The EObject has to be part of a Resource");
    }

    // Create default descriptor if no descriptor is specified.
    if (eObjectTreeDescriptor == null  &&  eObject != null) {
      eObjectTreeDescriptor = createDefaultEObjectTreeDescriptor(eObject);
    }
    
    EObject currentRootEObject = getRootEObject();
    if (currentRootEObject != null) {
      currentRootEObject.eAdapters().remove(eContentAdapter);
    }
    
    if (eObject != null) {
      EObjectTreeItem rootItem = new EObjectTreeItemForObject(eObject, eObject.eClass(), null, this);
      setRoot(rootItem);
      eObject.eAdapters().add(eContentAdapter);;
    } else {
      setRoot(null);
    }
    
  }
  
  /**
   * Create a default EObjectTreeDescriptor for an EObject.
   * <p>
   * For the EClass of the provided EObject a descriptor is generated.
   * For each class in the class hierarchy of the EClass an EObjectTreeItemClassDescriptor is created and added to the descriptor.
   * 
   * @param eObject the EObject for which a descriptor will be generated.
   * @return the generated presentation descriptor.
   */
  private EObjectTreeDescriptor createDefaultEObjectTreeDescriptor(EObject eObject) {
    LOGGER.info("=>");
    
    EClass eClass = eObject.eClass();
    LOGGER.info("eClass=" + eClass.getName());
    EmfPackageHelper emfPackageHelper = new EmfPackageHelper(eClass.getEPackage());

    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    
    addClassDescriptor(eObjectTreeDescriptor, eClass, emfPackageHelper);
    
    LOGGER.info("<= " + NEWLINE + eObjectTreeDescriptor.toString());
    return eObjectTreeDescriptor;
  }
  
  /**
   * Create an EObjectTreeItemClassDescriptor for an EClass (and all references EClasses) and add it to the EObjectTreeDescriptor.
   * 
   * @param eObjectTreeDescriptor the EObjectTreeDescriptor to which the new EObjectTreeItemClassDescriptor is to be added.
   * @param eClass the EClass for which a descriptor is to be created.
   * @param emfPackageHelper an EmfPackageHelper for the package in which the <code>eClass</code> is located.
   */
  private void addClassDescriptor(EObjectTreeDescriptor eObjectTreeDescriptor, EClass eClass, EmfPackageHelper emfPackageHelper) {
    LOGGER.info("=> eClass=" + eClass.getName());
    
    // Simply return if a descriptor for this class already exists
    if (eObjectTreeDescriptor.getDescriptorForEClass(eClass) != null) {
      LOGGER.fine("<= existing class");
      return;
    }
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, null, false, nodeOperationDescriptors);
    
    // Add information for the attributes and references
    for (EStructuralFeature structuralFeature: eClass.getEAllStructuralFeatures()) {
      if (structuralFeature instanceof EAttribute eAttribute) {
        LOGGER.info("Handling attribute: " + eAttribute.getName());
        if (eAttribute.isMany()) {
          EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(eAttribute,
              eAttribute.getName(), true, null, null);
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);
        } else {
          EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor((EAttribute) structuralFeature);
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
        }
      } else if (structuralFeature instanceof EReference) {
        EReference eReference = (EReference) structuralFeature;
        LOGGER.fine("Handling reference: " + eReference.getName());
        EClass referenceClass = eReference.getEReferenceType();
        if (eReference.isMany()) {
          EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(eReference);
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);          
        } else {
          EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor =
              new EObjectTreeItemClassReferenceDescriptor(eReference, referenceClass, (eObject) -> eReference.getName(), false, nodeOperationDescriptors);
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
        }
      } else {
        throw new RuntimeException("Unknown feature type: " + structuralFeature.toString());
      }
    }
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    
    // Add an EObjectTreeItemClassDescriptor for referenced classes.
    for (EStructuralFeature structuralFeature: eClass.getEAllStructuralFeatures()) {
      if (structuralFeature instanceof EReference) {
        EReference eReference = (EReference) structuralFeature;
        LOGGER.fine("Handling reference: " + eReference.getName());
        EClass referenceClass = eReference.getEReferenceType();
        addClassDescriptor(eObjectTreeDescriptor, referenceClass, emfPackageHelper);
      }
    }
    
    // Add descriptors for any sub class of this class.
    try {
      for (EClass subType : emfPackageHelper.getSubTypes(eClass)) {
        addClassDescriptor(eObjectTreeDescriptor, subType, emfPackageHelper);
      }
    } catch (IllegalArgumentException e) {
      // Ignore 'is not part of package' exceptions. TODO create emfPackageHelper for these.
    }

    LOGGER.fine("<=");
  }
    
  /**
   * Create the content adapter for handling the changes within the eObject.
   */
  private EContentAdapter createEContentAdapter() {
    return new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        
        if (ignoreNotification) {
          return;
        }
        
        if (notification.isTouch()) {
          LOGGER.severe("Ignoring touch notification");
          // no action needed
          return;
        }
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          LOGGER.severe("Ignoring removing adapter");
          // for now no action
          return;
        }
        
        LOGGER.severe("TreeView change detected: " + EmfUtil.printNotification(notification, false));
        
        
        // If something is added, we have to add a node for this.
        // If something is removed, we have to remove the related nodes.
        
        /*
         * The reported change is on the EObject, so we first have to find the tree item with that object.
         */
        
        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
        if (feature == null) {
          throw new RuntimeException("feature is null, this is not supported yet");
        } else if (feature instanceof EReference eReference) {
          LOGGER.severe("eReference: " + eReference);
          EObjectPath eObjectPath = new EObjectPath(notifierEObject);
          EObjectTreeItem changedContainingTreeItem = findTreeItem(eObjectPath);
          LOGGER.info("changedContainingTreeItem: " + changedContainingTreeItem);
//          changedContainingTreeItem.setExpanded(true);
//          changedContainingTreeItem.rebuildChildren();
          
          if (eReference.isMany()) {
            EObjectTreeItem changedTreeItem = (EObjectTreeItemForObjectList) changedContainingTreeItem.findChildTreeItem(eReference);
            LOGGER.severe("changedTreeItem: " + changedTreeItem);

            if (notification.getEventType() == Notification.ADD) {
              // an element is added to a list of objects
              ((EObjectTreeItemForObjectList) changedTreeItem).addObjectListChild(notification.getPosition());
            } else if (notification.getEventType() == Notification.REMOVE) {
              // an element is removed from a list of objects
              ((EObjectTreeItemForObjectList) changedTreeItem).removeObjectListChild(notification.getPosition());
            } else if (notification.getEventType() == Notification.SET) {
              // the value of an attribute has changed
              ((EObjectTreeItemForObject) changedTreeItem).handleAttributeValueChanged(eReference, notification.getNewValue());
            }
          }
        } else if (feature instanceof EAttribute eAttribute) {
          if (eAttribute.isMany()) {
//            throw new RuntimeException("eAttribute not supported: " + eAttribute.toString());
          } else {
            EObjectPath eObjectPath = new EObjectPath(notifierEObject);
            EObjectTreeItem changedContainingTreeItem = findTreeItem(eObjectPath);
            EObjectTreeItem changedTreeItem = (EObjectTreeItemForAttributeSimple) changedContainingTreeItem.findChildTreeItem(eAttribute);
            LOGGER.severe("changedTreeItem: " + changedTreeItem);
            if (notification.getEventType() == Notification.SET) {
              // the value of an attribute has changed
              ((EObjectTreeItemForAttributeSimple) changedTreeItem).handleAttributeValueChanged(eAttribute, notification.getNewValue());
            } else {
              throw new RuntimeException("EventType: " + notification.getEventType() + " , for eAttribute not supported: " + eAttribute.toString());
            }
          }
        } else {
          throw new RuntimeException("feature not supported: " + feature.toString());
        }
      }

    };
  }
  
  public EObjectTreeItem findTreeItem(Object object) {
    return (EObjectTreeItem) findTreeItem(getRoot(), object);
  }
  
  public TreeItem<Object> findTreeItem(TreeItem<Object> treeItem, Object object) {
    Object treeItemContent = treeItem.getValue();
    if (treeItemContent == object) {
      return treeItem;
    }
    
    for (TreeItem<Object> child: treeItem.getChildren()) {
      TreeItem<Object> result = findTreeItem(child, object);
      if (result != null) {
        return result;
      }
    }
    
    return null;
  }

  /**
   * Provide a String representation of the tree view.
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(2);
    EObjectTreeCell eObjectTreeCell = new EObjectTreeCell();
    
    TreeViewWalker.visit(this, new TreeItemVisitor<Object>() {

      @Override
      public TreeItemVisitResult preVisitChildren(TreeItem<Object> treeItem) {
        indent.increment();
        
        return null;
      }

      @Override
      public TreeItemVisitResult visitTreeItem(TreeItem<Object> treeItem) {
        buf.append(indent.toString());
        eObjectTreeCell.updateItem(treeItem.getValue(), false);
        buf.append(eObjectTreeCell.getCellText());
        buf.append(NEWLINE);
        
        return null;
      }

      @Override
      public FileVisitResult postVisitChildren(TreeItem<Object> treeItem) {
        indent.decrement();
        
        return null;
      }

    });
    
    return buf.toString();
  }
  
  
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<TreeItem<Object>> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
  }

  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<TreeItem<Object>> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }

  @Override
  public EObjectTreeItem getSelectedObject() {
    EObjectTreeItem selectedItem = (EObjectTreeItem) getSelectionModel().selectedItemProperty().get();
    if (selectedItem != null) {
      Object eObjectTreeItemContent = selectedItem.getValue();
      
      if (eObjectTreeItemContent != null) {
        LOGGER.info("Selected item content: " + eObjectTreeItemContent);
      } else {
        LOGGER.severe("Selected item has no value");
      }
    } else {
      LOGGER.info("No EObjectTreeItem selected");
    }
    return selectedItem;
  }

  private void notifySelectedObjectListeners(TreeItem<Object> selectedItem) {
    for (ObjectSelectionListener<TreeItem<Object>> objectSelectionListener: objectSelectionListeners) {
      objectSelectionListener.objectSelected(this, selectedItem);
    }
  }

  public EObjectTreeItem findTreeItem(EObjectPath eObjectPath) {
    LOGGER.info("=> eObjectPath=" + NEWLINE + eObjectPath.getPathXTree().toString());
    
    EObjectTreeItemResolverVisitor eObjectResolverVisitor = new EObjectTreeItemResolverVisitor((EObjectTreeItem) getRoot());
    eObjectPath.getPathXTree().traverse(eObjectResolverVisitor);
    
    EObjectTreeItem result = eObjectResolverVisitor.getEObjectTreeItem();
    LOGGER.info("<= " + result);
    return result;
  }
}


/**
 * This class is a {@link XTreeNodeVisitor} used to find the EObjectTreeItem that contains the object identified
 * by an EObjectPath.
 */
class EObjectTreeItemResolverVisitor implements XTreeNodeVisitor {
  private final static Logger LOGGER = Logger.getLogger(EObjectTreeItemResolverVisitor.class.getName());

  boolean handlingReferenceName = true;  // false for handling index in list for reference of type many.
  EReference reference = null;
  EObjectTreeItem eObjectTreeItem = null;

  /**
   * Constructor.
   * 
   * @param rootItem the root of the EObjectTree.
   */
  public EObjectTreeItemResolverVisitor(EObjectTreeItem rootItem) {
    eObjectTreeItem = rootItem;
  }

  /**
   * Get the EObjectTreeItem
   * <p>
   * This value is set after traversing the tree, and if the tree item is found.
   * @return
   */
  public EObjectTreeItem getEObjectTreeItem() {
    return eObjectTreeItem;
  }

  @Override
  public XTreeNodeVisitResult preVisitChildren() {
    if (handlingReferenceName) {
      throw new RuntimeException("Unexpected child node");
    }
    return XTreeNodeVisitResult.CONTINUE;
  }

  @Override
  public XTreeNodeVisitResult visitTreeItem(XNodeDataType dataType, Object value) {
    if (handlingReferenceName) {
      // reference name expected
      if (dataType != XNodeDataType.STRING) {
        throw new RuntimeException("Wrong dataType; type STRING expected (for reference name), but is " + dataType.name());
      }
      String referenceName = (String) value;
      LOGGER.fine("reference name: " + referenceName);
      
      for (TreeItem<Object> treeItem: eObjectTreeItem.getChildren()) {
        Object content = treeItem.getValue();
        LOGGER.fine("content=" + (content != null ? content.toString() : "<null>"));
        EStructuralFeature eStructuralFeature = ((EObjectTreeItem)treeItem).getEStructuralFeature();
        if (eStructuralFeature == null) {
          throw new RuntimeException("eStructuralFeature is null for treeItem: " + treeItem.toString());
        }
        if (eStructuralFeature.getName().equals(referenceName)) {
          reference = (EReference) eStructuralFeature;
          eObjectTreeItem = (EObjectTreeItem) treeItem;
          break;
        }
      }
      if (reference == null) {
        // This may happen, as the EObjectTreeView doesn't have to show the complete EObject hierarchy.
        return XTreeNodeVisitResult.TERMINATE;
      }
      LOGGER.fine("reference: " + reference.getName());

      if (reference.isMany()) {
        LOGGER.fine("is many");
        handlingReferenceName = false;
      } else {
        LOGGER.fine("is NOT many");
      }
      LOGGER.fine("eObjectTreeItem=" + eObjectTreeItem.toString());
    } else {
      // List index expected
      if (dataType != XNodeDataType.INTEGER) {
        throw new RuntimeException("Wrong dataType; type INTEGER expected (for reference name), but is " + dataType.name());
      }
//      Object listObject = eObject.eGet(reference);
      Object content = eObjectTreeItem.getValue();
//      Object listObject = content.getObject(); 
      
      if (!(content instanceof EList)) {
        throw new RuntimeException("Wrong object in hierarchy; EList expected, but is " + content);
      }
      int index = (int) value;
      if (eObjectTreeItem.getChildren().size() < index + 1) {
        throw new RuntimeException("Index too high for number of elements, index= " + index + ", nr of elements=" + eObjectTreeItem.getChildren().size());
      }
      eObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getChildren().get(index);

      handlingReferenceName = true;
    }
    return XTreeNodeVisitResult.CONTINUE;
  }

  @Override
  public XTreeNodeVisitResult postVisitChildren() {
    if (!handlingReferenceName) {
      throw new RuntimeException("Unexpected move to parent");
    }
    return XTreeNodeVisitResult.CONTINUE;
  }
}
