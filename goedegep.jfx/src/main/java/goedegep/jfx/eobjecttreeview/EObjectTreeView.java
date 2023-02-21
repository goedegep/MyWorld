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
 * The tree view is either in 'normal mode' or in 'edit mode'. In 'normal mode' attributes which have no value are not shown and the items cannot be changed,
 * in 'edit mode' all attributes are shown (unless the Descriptor specifies that there aren't to be shown) and the items can be changed.<br/><br/>
 * 
 * <b>ObjectSelector</b><br/>
 * The tree view implements the ObjectSelector interface. This normally provides an object of a specific type. A tree however contains items of different types.
 * This also means that some context information is required. Therefore objectSelected() provides the selected tree item (instead of the value).
 * 
 */
public class EObjectTreeView extends TreeView<EObjectTreeItemContent> implements ObjectSelector<TreeItem<EObjectTreeItemContent>> {
  
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
   * How the tree is rendered is specified by a EObjectTreeDescriptor. How a cell is rendered is specified by a (sub-type of ) EObjectTreeItemDescriptor.
   * Therefore the EObjectTreeDescriptor contains a tree with nodes of type TreeNode<EObjectTreeItemDescriptor>.
   */
  
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeView.class.getName());
  protected static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The EObject presented by the tree.
   */
  private EObject eObject = null;
  
  /**
   * The description of what is shown in the tree and how.
   */
  private EObjectTreeDescriptor eObjectTreeDescriptor;  // describes what is shown in the tree, and how.
  
  /**
   * The EClass related to the root EObject.
   */
  private EClass eClass = null;
  
  
  private EContentAdapter eContentAdapter = null;
  
  /**
   * TODO
   */
  private EmfPackageHelper emfPackageHelper = null;
  
  /**
   * ObjectSelector listeners to the selected object in the tree.
   */
  private List<ObjectSelectionListener<TreeItem<EObjectTreeItemContent>>> objectSelectionListeners = new ArrayList<>();
  
  /**
   * Function to check whether a drop is possible on an item.
   */
  private BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction;
  
  /**
   * Function to handle the drop.
   */
  private BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction;
  
  
  /**
   * Constructor for a default tree view (no EObjectTreeDescriptor specified).
   * <p>
   * All information of the eObject is shown. Labels are derived from the related EClass.
   * 
   * @param eObject the EObject (hierarchy) to be shown in the tree. (optional)
   * @param editMode if true, the tree is shown in 'edit mode', otherwise the tree is shown in 'normal mode'.
   */
  public EObjectTreeView(EObject eObject, boolean editMode) {
    this(eObject, null, editMode);
  }
  
  /**
   * Constructor for a tree view where a descriptor can be specified.
   * 
   * @param eObject the EObject (hierarchy) to be shown in the tree. (mandatory)
   * @param eObjectTreeDescriptor an optional descriptor for what is show and how.
   * @param editMode if true, the tree is shown in 'edit mode', otherwise the tree is shown in 'normal mode'.
   */
  public EObjectTreeView(EObject eObject, EObjectTreeDescriptor eObjectTreeDescriptor, boolean editMode) {
    super();
    
    this.eObject = eObject;

    this.eObjectTreeDescriptor = eObjectTreeDescriptor;
    
    setEditable(editMode);
    
    setCellFactory(treeView -> new EObjectTreeCell());
    
    // React to changes in the selected item, to notify ObjectSelection listeners.
    getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

      @Override
      public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (newValue != null) {
          @SuppressWarnings("unchecked")
          TreeItem<EObjectTreeItemContent> selectedItem = (TreeItem<EObjectTreeItemContent>) newValue;
          LOGGER.info("Selected Node: " + selectedItem.getValue().getObject());
          notifySelectedObjectListeners(selectedItem);
        } else {
          LOGGER.info("Selected Node: (null)");
          notifySelectedObjectListeners(null);
        }
      }

    });
    
    if (eObject != null) {
      handleNewEObject(eObject);
    }
  }
  
  public void setIsDropPossibleFunction(BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction) {
    this.isDropPossibleFunction = isDropPossibleFunction;
  }
  
  public BiPredicate<EObjectTreeItem, Dragboard> getIsDropPossibleFunction() {
    return isDropPossibleFunction;
  }
  
  public void setHandleDropFunction(BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction) {
    this.handleDropFunction = handleDropFunction;
  }
  
  public BiPredicate<EObjectTreeItem, Dragboard> getHandleDropFunction() {
    return handleDropFunction;
  }
  
  /**
   * Set the mode to 'normal mode' or 'edit mode'.
   * 
   * @param editMode if true, the mode is set to 'edit mode', else the mode is set to 'normal mode'.
   */
  public void setEditMode(boolean editMode) {
    boolean currentEditable = isEditable();
    if (currentEditable != editMode) {
      LOGGER.info("Changing mode");
      setEditable(editMode);
      setEObject(eObject);
    }
  }
  
  /**
   * Set the eObject to be shown in the tree view.
   * 
   * @param eObject the eObject to be shown in the tree view. If null, the tree view will be cleared.
   */
  public void setEObject(EObject eObject) {
    if (this.eObject != null  &&  eContentAdapter != null) {
      this.eObject.eAdapters().remove(eContentAdapter);
    }
    this.eObject = eObject;
    
    if (eObject == null) {
      setRoot(null);
    } else {
      handleNewEObject(eObject);
    }
  }
  
  /**
   * Get the EClass of the (first) eObject shown in the tree.
   * <p>
   * The EClass is derived from the first eObject set, either via the constructor or via the method {@link #setEObject}. 
   * 
   * @return the EClass of the (first) eObject shown in the tree.
   */
  public EClass getEClass() {
    return eClass;
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
  
  public EmfPackageHelper getEMFPackageHelper() {
    return emfPackageHelper;
  }
  
  public static Image getListIcon() {
    return new Image(EObjectTreeCellHelperForObjectList.class.getResourceAsStream("Three dots.png"), 36, 18, true, true);
  }
  
  /**
   * Handle the fact that a new EObject is to be shown in the tree view.
   * 
   * @param eObject the new EObject to be shown in the tree. (mandatory)
   */
  private void handleNewEObject(EObject eObject) {
    if (eObjectTreeDescriptor == null) {
      eObjectTreeDescriptor = createDefaultEObjectTreeDescriptor(eObject);
    }
    
    if (eClass == null) {
      eClass = eObject.eClass();
      emfPackageHelper = new EmfPackageHelper(eClass.getEPackage());
    }
    
    EObjectTreeItem rootItem;
    rootItem = new EObjectTreeItem(eObject, EObjectTreeItemType.OBJECT, eObjectTreeDescriptor.getDescriptorForEClass(eClass), this);
    setRoot(rootItem);
    
    installHandlingOfEObjectChanges();
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
   * Create an EObjectTreeItemClassDescriptor for an EClass and add it to the EObjectTreeDescriptor.
   * 
   * @param eObjectTreeDescriptor the EObjectTreeDescriptor to which the new EObjectTreeItemClassDescriptor is to be added.
   * @param eClass the EClass for which a descriptor is to be created.
   * @param emfPackageHelper an EmfPackageHelper for the package in which the <code>eClass</code> is located.
   */
  private void addClassDescriptor(EObjectTreeDescriptor eObjectTreeDescriptor, EClass eClass, EmfPackageHelper emfPackageHelper) {
    LOGGER.info("=> eClass=" + eClass.getName());
    
    if (eObjectTreeDescriptor.getDescriptorForEClass(eClass) != null) {
      LOGGER.fine("<= existing class");
      return;
    }
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, null, true, nodeOperationDescriptors);
    
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
   * Install the handling of changes within the eObject.
   */
  private void installHandlingOfEObjectChanges() {
    eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        LOGGER.info("TreeView change detected: " + notification.toString());
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }
        
        /*
         * The reported change is on the EObject, so we first have to find the tree item with that object.
         */
        
        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
        if (feature == null) {
          throw new RuntimeException("feature is null, this is not supported yet");
        } else if (feature instanceof EStructuralFeature) {
          EStructuralFeature eStructuralFeature = (EStructuralFeature) feature;
          LOGGER.fine("eStructuralFeature: " + eStructuralFeature);
          EObjectPath eObjectPath = new EObjectPath(notifierEObject);
          EObjectTreeItem rootItem = (EObjectTreeItem) getRoot();
          EObjectTreeItem changedContainingTreeItem = findTreeItem(eObjectPath);
          changedContainingTreeItem.setExpanded(true);
          changedContainingTreeItem.rebuildChildren();          
          EObjectTreeItem changedTreeItem = rootItem.findChildTreeItem(changedContainingTreeItem, eStructuralFeature);
          if (changedTreeItem != null) {
            if (notification.getEventType() == Notification.ADD) {
              changedTreeItem = (EObjectTreeItem) changedTreeItem.getChildren().get(notification.getPosition());
            }
            LOGGER.info("Going to SELECT node: " + changedTreeItem.toString());
            changedTreeItem.setExpanded(true);
            getSelectionModel().select(changedTreeItem);
          } else {
            LOGGER.fine("Changed tree item not found: changedContainingTreeItem=" + changedContainingTreeItem.toString() + ", eStructuralFeature=" + eStructuralFeature);
          }
        } else {
          throw new RuntimeException("feature not supported: " + feature.toString());
        }
      }

    };
//    eObject.eAdapters().add(eContentAdapter);    
  }
  
  public EObjectTreeItem findTreeItem(Object object) {
    return (EObjectTreeItem) findTreeItem(getRoot(), object);
  }
  
  public TreeItem<EObjectTreeItemContent> findTreeItem(TreeItem<EObjectTreeItemContent> treeItem, Object object) {
    EObjectTreeItemContent treeItemContent = treeItem.getValue();
    Object treeItemContentObject = treeItemContent.getObject();
    if (treeItemContentObject == object) {
      return treeItem;
    }
    
    for (TreeItem<EObjectTreeItemContent> child: treeItem.getChildren()) {
      TreeItem<EObjectTreeItemContent> result = findTreeItem(child, object);
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
    
    TreeViewWalker.visit(this, new TreeItemVisitor<EObjectTreeItemContent>() {

      @Override
      public TreeItemVisitResult preVisitChildren(TreeItem<EObjectTreeItemContent> treeItem) {
        indent.increment();
        
        return null;
      }

      @Override
      public TreeItemVisitResult visitTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
        buf.append(indent.toString());
        eObjectTreeCell.updateItem(treeItem.getValue(), false);
        buf.append(eObjectTreeCell.getCellText());
        buf.append(NEWLINE);
        
        return null;
      }

      @Override
      public FileVisitResult postVisitChildren(TreeItem<EObjectTreeItemContent> treeItem) {
        indent.decrement();
        
        return null;
      }

    });
    
    return buf.toString();
  }
  
  
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<TreeItem<EObjectTreeItemContent>> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
  }

  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<TreeItem<EObjectTreeItemContent>> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }

  @Override
  public EObjectTreeItem getSelectedObject() {
    EObjectTreeItem selectedItem = (EObjectTreeItem) getSelectionModel().selectedItemProperty().get();
    if (selectedItem != null) {
      EObjectTreeItemContent eObjectTreeItemContent = selectedItem.getValue();
      
      if (eObjectTreeItemContent != null) {
        LOGGER.info("Selected item content: " + eObjectTreeItemContent.getObject());
      } else {
        LOGGER.severe("Selected item has no value");
      }
    } else {
      LOGGER.info("No EObjectTreeItem selected");
    }
    return selectedItem;
  }

  private void notifySelectedObjectListeners(TreeItem<EObjectTreeItemContent> selectedItem) {
    for (ObjectSelectionListener<TreeItem<EObjectTreeItemContent>> objectSelectionListener: objectSelectionListeners) {
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
      
      for (TreeItem<EObjectTreeItemContent> treeItem: eObjectTreeItem.getChildren()) {
        EObjectTreeItemContent content = treeItem.getValue();
        LOGGER.fine("content=" + content.toString());
        EStructuralFeature eStructuralFeature = content.getEStructuralFeature();
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
      EObjectTreeItemContent content = eObjectTreeItem.getValue();
      Object listObject = content.getObject(); 
      
      if (!(listObject instanceof EList)) {
        throw new RuntimeException("Wrong object in hierarchy; EList expected, but is " + listObject);
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
