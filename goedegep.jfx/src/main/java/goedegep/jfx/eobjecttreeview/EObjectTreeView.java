package goedegep.jfx.eobjecttreeview;

import java.nio.file.FileVisitResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.configuration.model.Look;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxUtil;
import goedegep.jfx.treeview.TreeItemVisitResult;
import goedegep.jfx.treeview.TreeItemVisitor;
import goedegep.jfx.treeview.TreeViewWalker;
import goedegep.util.emf.EObjectPath;
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
import javafx.scene.paint.Color;

/**
 * This class provides a tree view for a hierarchy of {@link EObject}s.
 * <p>
 * <b>Descriptor for which parts are shown and how</b><br/>
 * The tree view is highly configurable via an {@link EObjectTreeDescriptor}. However this descriptor can also be completely omitted.
 * In this case an EObjectTreeDescriptor is generated, where all information is derived from the EObjects.
 * 
 * <h3>GUI customization</h3>
 * By default the customization is obtained via {@link DefaultCustomizationFx#getInstance}. But you can set the customization by specifying a {@link CustomizationFx}.
 * See {@link #setCustomization}.
 * 
 * <h3>Edit mode</h3>
 * The tree view is either in 'view mode' or in 'edit mode'. In 'view mode' attributes which have no value are not shown and the items cannot be changed,
 * in 'edit mode' all attributes are shown (unless the Descriptor specifies that there aren't to be shown) and the items can be changed.<br/><br/>
 * By defaullt the tree view view is in 'edit mode'. See {@link #setEditMode(boolean)} to change this.
 * 
 * <h3>ObjectSelector</h3>
 * The tree view implements the ObjectSelector interface. This normally provides an object of a specific type. A tree however contains items of different types.
 * This also means that some context information is required. Therefore objectSelected() provides the selected tree item (instead of the value).
 * 
 * <h3>Drag and Drop support</h3>
 * Via Drag and Drop EObjects can be moved within the tree. An EObject can of course only be moved to a location which supports the type of the item to be moved.
 * The move is a real move, meaning that the object is really moved (instead of creating a clone of the object and then deleting the object).
 * Apart from this standard functionality, it is possible to set functions to check whether a drop is possible and to handle the drop.
 */
public class EObjectTreeView extends TreeView<Object> implements ObjectSelector<TreeItem<Object>> {
  
  /*
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
   * which is created by a factory set via setCellFactory(). Here the TreeCell is type EObjectTreeCell which itself is a TreeCell<Object>.
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
   * GUI customization
   */
  @SuppressWarnings("unused")
  private CustomizationFx customization;

  /**
   * EObjectTreeItemClassDescriptor's per EClass.
   */
  private Map<EClass, EObjectTreeItemClassDescriptor> eClassToClassDescriptorMap = new HashMap<>();
  
  /**
   * EEnumEditorDescriptor's per EEnum.
   */
  private Map<Object, EEnumEditorDescriptor<?>> eEnumToEEnumEditorDescriptorMap = new HashMap<>();
  
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
   * Indicate that a selection was set from outside, so we don't notify listeners.
   */
  boolean dontNotifyListeners = false;
  
  /**
   * Constructor
   */
  public EObjectTreeView() {
    setCustomization(DefaultCustomizationFx.getInstance());
    
    setEditable(true);
    
    setCellFactory(treeView -> new EObjectTreeCell());
    
    // React to changes in the selected item, to notify ObjectSelection listeners.
    getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

      @Override
      public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (dontNotifyListeners) {
          return;
        }
        
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
  }  
  
  /**
   * Set the GUI customization
   * 
   * @param customization the GUI customization
   * @return this
   */
  public EObjectTreeView setCustomization(CustomizationFx customization) {
    this.customization = customization;
    
    Look look = customization.getLook();
    
    if (look != null) {
      Color backgroundColor = look.getBackgroundColor();
      
      if (backgroundColor != null) {
        String colorCssString = JfxUtil.colorToCssString(backgroundColor);
        setStyle("-fx-base: " + colorCssString + ";");
      }
    }
    
    return this;
  }
  
  
  /**
   * Add an EClass descriptor.
   * 
   * @param eClass the EClass for which a descriptor is added.
   * @param eObjectTreeItemClassDescriptor the descriptor for the <code>eClass</code>.
   * @return this
   */
  public EObjectTreeView addEClassDescriptor(EClass eClass, EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor) {
    eClassToClassDescriptorMap.put(eClass, eObjectTreeItemClassDescriptor);
    
    return this;
  }
  
  /**
   * Add an EEnum editor descriptor.
   */
  public EObjectTreeView addEEnumEditorDescriptor(EEnum eEnum, EEnumEditorDescriptor<?> eEnumEditorDescriptor) {
    eEnumToEEnumEditorDescriptorMap.put(eEnum, eEnumEditorDescriptor);
    
    return this;
  }
  
  /**
   * Set the isDropPossible function.
   * 
   * @param isDropPossibleFunction the new isDropPossible function.
   * @return this
   */
  public EObjectTreeView setIsDropPossibleFunction(BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction) {
    this.isDropPossibleFunction = isDropPossibleFunction;
    
    return this;
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
   * @return this
   */
  public EObjectTreeView setHandleDropFunction(BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction) {
    this.handleDropFunction = handleDropFunction;
    
    return this;
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
  public EObjectTreeView setEditMode(boolean editMode) {
    LOGGER.info("=> editMode=" + editMode);
    
    setEditable(editMode);
    EObjectTreeItem root = (EObjectTreeItem) getRoot();
    if (root != null) {
      root.setEditMode(editMode);
    }
    
    return this;
  }
  
  /**
   * Set the text to search for in the TreeView.
   * <p>
   * The search is actually on the data structure represented by the tree view, because the TreeItems are only constructed when needed to be shown.
   * 
   * @param searchText
   */
  public void setSearchText(String searchText) {
    LOGGER.severe("=> searchText=" + searchText);
    
    if (searchText == null) {
      LOGGER.severe("No search text");
      return;
    }
    
    searchText = searchText.toLowerCase();
    
    TreeItem<Object> rootItem = getRoot();
    
    if (rootItem == null) {
      LOGGER.severe("No root");
      return;
    }
    
    Object value = rootItem.getValue();
    if (value == null) {
      LOGGER.severe("Root item has no value");
      return;
    }
    
    if (value instanceof EObject startEObject) {  // This should always be the case
      TreeIterator<EObject> vacationIterator = startEObject.eAllContents();
      while (vacationIterator.hasNext()) {
        EObject eObject = vacationIterator.next();
        String eObjectText = eObject.toString().toLowerCase();
        if (eObjectText.contains(searchText)) {
          LOGGER.severe("Text appears in: " + eObject);
          EObjectTreeItem eObjectTreeItem =  findTreeItem(eObject);
          if (eObjectTreeItem != null) {
            LOGGER.severe("Found tree item: " + eObjectTreeItem);
            getSelectionModel().select(eObjectTreeItem);
            scrollTo(getSelectionModel().getSelectedIndex());
          } else {
            LOGGER.severe("No tree item found");
          }
        }
      }
    }
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
//  public EObjectTreeDescriptor getEObjectTreeDescriptor() {
//    return eObjectTreeDescriptor;
//  }
  
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
      eObjectTreeItemClassDescriptor = getDescriptorForEClass(eClass);
    } while ((eObjectTreeItemClassDescriptor == null)  &&  ((eClass = EmfUtil.getSuperType(eClass)) != null));

    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Get the descriptor for an {@code EClass}.
   * 
   * @param eClass the {@code EClass} for which a descriptor is requested.
   * @return the {@code EObjectTreeItemClassDescriptor} for the {@code eClass}, or null if there is no descriptor for the {@code eClass}.
   */
  public EObjectTreeItemClassDescriptor getDescriptorForEClass(EClass eClass) {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = eClassToClassDescriptorMap.get(eClass);
    if (eObjectTreeItemClassDescriptor == null) {
      eObjectTreeItemClassDescriptor = createEClassDescriptor(eClass);
      eClassToClassDescriptorMap.put(eClass, eObjectTreeItemClassDescriptor);
    }
    return eObjectTreeItemClassDescriptor;
  }
  
  public EObjectTreeItemAttributeDescriptor getEObjectTreeItemAttributeDescriptor(EClass eClass, EAttribute eAttribute) {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = getDescriptorForEClass(eClass);
    for (EObjectTreeItemDescriptor eObjectTreeItemDescriptor: eObjectTreeItemClassDescriptor.getStructuralFeatureDescriptors()) {
      if (eObjectTreeItemDescriptor instanceof EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor  &&  eObjectTreeItemAttributeDescriptor.getEAttribute().equals(eAttribute)) {
        return eObjectTreeItemAttributeDescriptor;
      }
    }
    
    throw new RuntimeException("No EObjectTreeItemAttributeDescriptor found for eClass: " + eClass.getName() + ", eAttribute: " + eAttribute.getName());
  }
  
  /**
   * Get the descriptor for a specific {@code Enumerator}.
   * 
   * @param eEnum the <code>EEnum</code> to get the descriptor for.
   * @return the <code>EEnumEditorDescriptor</code> for <code>eEnum</code>, or null if it is not set.
   */
  public <E> EEnumEditorDescriptor<E> getEEnumEditorDescriptorForEEnum(E enumerator) {
    @SuppressWarnings("unchecked")
    EEnumEditorDescriptor<E> eEnumEditorDescriptor = (EEnumEditorDescriptor<E>) eEnumToEEnumEditorDescriptorMap.get(enumerator);
    if (eEnumEditorDescriptor == null) {
      eEnumEditorDescriptor = createEEnumEditorDescriptor(enumerator);
      eEnumToEEnumEditorDescriptorMap.put(enumerator, eEnumEditorDescriptor);
    }
    return eEnumEditorDescriptor;
  }
  
  /**
   * Create an {@code EEnumEditorDescriptor} for an {@code Enumerator}.
   * <p>
   * The string values are obtained by calling {@code toString()} on the literals.
   * 
   * @param <E> The {@code Enumerator} type.
   * @param enumerator the {@code Enumerator}
   * @return the created {@code EEnumEditorDescriptor}.
   */
  private <E> EEnumEditorDescriptor<E> createEEnumEditorDescriptor (E enumerator) {
    EEnumEditorDescriptor<E> eEnumEditorDescriptor = new EEnumEditorDescriptor<>(true);
    
        
    for (Object value: enumerator.getClass().getEnumConstants()) {
      eEnumEditorDescriptor.addDisplayNameForEEnum(value, value.toString());
    }
            
    return eEnumEditorDescriptor;
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
  public EObjectTreeView setEObject(EObject eObject) {
    EObject currentRootEObject = getRootEObject();
    if (currentRootEObject != null) {
      currentRootEObject.eAdapters().remove(eContentAdapter);
    }
    
    if (eObject != null) {
      EObjectTreeItem rootItem = new EObjectTreeItemForObject(eObject, eObject.eClass(), null, this);
      setRoot(rootItem);
      eObject.eAdapters().add(eContentAdapter);
    } else {
      setRoot(null);
    }
    
    return this;
  }
    
  /**
   * Create an EObjectTreeItemClassDescriptor for an EClass.
   * 
   * @param eClass the EClass for which a descriptor is to be created.
   */
  private EObjectTreeItemClassDescriptor createEClassDescriptor(EClass eClass) {
    LOGGER.info("=> eClass=" + eClass.getName());
    
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete", null));
    
    // Add information for the attributes and references
    for (EStructuralFeature structuralFeature: eClass.getEAllStructuralFeatures()) {
      if (structuralFeature instanceof EAttribute eAttribute) {
        LOGGER.info("Handling attribute: " + eAttribute.getName());
        if (eAttribute.isMany()) {
          EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(eAttribute);
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);
        } else {
          EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor((EAttribute) structuralFeature);
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
        }
      } else if (structuralFeature instanceof EReference eReference) {
        LOGGER.fine("Handling reference: " + eReference.getName());
        if (eReference.isMany()) {
          EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
          if (eReference.isContainment()) {
            eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(eReference);
          } else {
            eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(eReference, eClass);
          }
          eObjectTreeItemClassListReferenceDescriptor.setLabelText(eReference.getName());
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);          
        } else {
          EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor;
          if (eReference.isContainment()) {
            eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(eReference);
          } else {
            eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(eReference, eClass);
          }
          eObjectTreeItemClassReferenceDescriptor.addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete", null));
          eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
        }
      } else {
        throw new RuntimeException("Unknown feature type: " + structuralFeature.toString());
      }
    }
    
//    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    

    LOGGER.fine("<=");
    return eObjectTreeItemClassDescriptor;
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
      public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        
        if (ignoreNotification) {
          return;
        }
        
        if (notification.isTouch()) {
          // no action needed
          return;
        }
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          LOGGER.severe("Ignoring removing adapter");
          // for now no action
          return;
        }
        
        LOGGER.info("TreeView change detected: " + EmfUtil.printNotification(notification, false));
        
        
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
          EObjectPath eObjectPath = new EObjectPath(notifierEObject);
          EObjectTreeItem changedContainingTreeItem = findTreeItem(eObjectPath);
          LOGGER.info("changedContainingTreeItem: " + changedContainingTreeItem);
//          changedContainingTreeItem.setExpanded(true);
//          changedContainingTreeItem.rebuildChildren();
          
          if (eReference.isMany()) {
            EObjectTreeItem changedTreeItem = (EObjectTreeItemForObjectList) changedContainingTreeItem.findChildTreeItem(eReference);

            if (notification.getEventType() == Notification.ADD) {
              // an element is added to a list of objects
              if (changedTreeItem == null) {
                LOGGER.severe("changedTreeItem is null");
                LOGGER.severe("notification: " + EmfUtil.printNotification(notification));
              } else {
                ((EObjectTreeItemForObjectList) changedTreeItem).addObjectListChild(notification.getPosition());
              }
            } else if (notification.getEventType() == Notification.REMOVE) {
              // an element is removed from a list of objects
              ((EObjectTreeItemForObjectList) changedTreeItem).removeObjectListChild(notification.getPosition());
            } else if (notification.getEventType() == Notification.SET) {
              // the value of an attribute has changed
              ((EObjectTreeItemForObject) changedTreeItem).handleAttributeValueChanged(eReference, notification.getNewValue());
            }
          } else {
            EObjectTreeItem changedTreeItem = (EObjectTreeItemForObject) changedContainingTreeItem.findChildTreeItem(eReference);
            if (notification.getEventType() == Notification.SET) {
              // the referred value has changed
              ((EObjectTreeItemForObject) changedTreeItem).handleAttributeValueChanged(eReference, notification.getNewValue());
            } else {
              throw new RuntimeException("Notification event type '" + notification.getEventType() + "' not implemented for single reference");
            }
            
          }
        } else if (feature instanceof EAttribute eAttribute) {
          if (eAttribute.isMany()) {
            EObjectPath eObjectPath = new EObjectPath(notifierEObject);
            EObjectTreeItem changedContainingTreeItem = findTreeItem(eObjectPath);
            EObjectTreeItem changedTreeItem = (EObjectTreeItemForAttributeList) changedContainingTreeItem.findChildTreeItem(eAttribute);

            if (notification.getEventType() == Notification.ADD) {
              // an element is added to a list of values
              ((EObjectTreeItemForAttributeList) changedTreeItem).addAttributeListChild(notification.getPosition());
            } else if (notification.getEventType() == Notification.ADD_MANY) {
              // elements are added to a list of values
             
              ((EObjectTreeItemForAttributeList) changedTreeItem).rebuildChildren();
            } else if (notification.getEventType() == Notification.REMOVE) {
              // an element is removed from a list of values
              ((EObjectTreeItemForAttributeList) changedTreeItem).removeAttributeListChild(notification.getPosition());
            } else if (notification.getEventType() == Notification.REMOVE_MANY) {
              // elements are removed from a list of values
              LOGGER.severe("notification: " + notification);
              ((EObjectTreeItemForAttributeList) changedTreeItem).removeAttributeListChildren();
            } else if (notification.getEventType() == Notification.SET) {
              // the value has changed
              EObjectTreeItemForAttributeList listItem = (EObjectTreeItemForAttributeList) changedTreeItem;
              EObjectTreeItemForAttributeListValue changedChild = (EObjectTreeItemForAttributeListValue) listItem.getChildren().get(notification.getPosition());
              changedChild.handleValueChanged(eAttribute, notification.getNewValue());
            } else {
              throw new RuntimeException("eAttribute '" + eAttribute.toString() + "' not supported for notification '" + notification.toString() + "'");
            }
          } else {
            EObjectPath eObjectPath = new EObjectPath(notifierEObject);
            EObjectTreeItem changedContainingTreeItem = findTreeItem(eObjectPath);
            EObjectTreeItem changedTreeItem = (EObjectTreeItemForAttributeSimple) changedContainingTreeItem.findChildTreeItem(eAttribute);
            LOGGER.info("changedTreeItem: " + changedTreeItem);
            if (notification.getEventType() == Notification.SET) {
              // the value of an attribute has changed
              if (changedTreeItem == null) {
                LOGGER.severe("changedTreeItem is null!!!");
              }
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

  @Override
  public boolean selectObject(TreeItem<Object> treeItem) {
    dontNotifyListeners = true;
    
    LOGGER.info("treeItem=" + treeItem);
    getSelectionModel().select(treeItem);
//    this.scrollTo(getSelectionModel().getSelectedIndex());
    
    dontNotifyListeners = false;
    
    return true;  // TODO determine real value
  }

  public void selectObjectForObject(Object object) {
    LOGGER.severe("object=" + object);
    EObjectTreeItem treeItem = findTreeItem(object);
    selectObject(treeItem);
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
