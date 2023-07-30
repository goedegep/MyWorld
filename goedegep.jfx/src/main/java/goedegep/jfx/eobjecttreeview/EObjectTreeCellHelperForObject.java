package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.emf.EmfUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a tree cell helper for an object.
 */
public class EObjectTreeCellHelperForObject extends EObjectTreeCellHelperAbstract<EObjectTreeItemForObject> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForObject.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  private EObjectTreeItemClassDescriptor itemDescriptor;
  
  private final Node objectIcon = new ImageView(new Image(EObjectTreeCellHelperForObject.class.getResourceAsStream("Class no text.png"), 36, 16, true, true));
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the {@code EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForObject(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(Object eObjectTreeItemContent) {
    super.updateItem(eObjectTreeItemContent);
    
    LOGGER.info("=> treeItem=" + treeItem);
    
    itemDescriptor = treeItem.getEObjectTreeItemClassDescriptor();
    
    if (itemDescriptor == null) {
      EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = treeItem.getEObjectTreeItemClassReferenceDescriptor();
      if (eObjectTreeItemClassReferenceDescriptor != null) {
        itemDescriptor = treeItem.getEObjectTreeView().getEObjectTreeDescriptor().getDescriptorForEClass(eObjectTreeItemClassReferenceDescriptor.getEClass());
      }
    }
    
    ContextMenu contextMenu = createContextMenu(eObjectTreeItemContent);
    eObjectTreeCell.setContextMenu(contextMenu);
    
    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(getText(eObjectTreeItemContent));
    if (itemDescriptor.isStrongText()) {
      eObjectTreeCell.setStyle("-fx-font-weight: bold;");
    }

    
    ImageView iconImageView = null;
    if (eObjectTreeItemContent != null) {
      if (itemDescriptor != null) {        
        Function<Object, Image> nodeIconFunction = itemDescriptor.getNodeIconFunction();
        if (nodeIconFunction != null) {
          Image iconImage = nodeIconFunction.apply(eObjectTreeItemContent);
          if (iconImage != null) {
            iconImageView = new ImageView(iconImage);
            iconImageView.setFitHeight(24);
            iconImageView.setPreserveRatio(true);
          }
        }
      }
    }
    if (iconImageView != null) {
      eObjectTreeCell.setGraphic(iconImageView);
    } else {
      eObjectTreeCell.setGraphic(objectIcon);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Create a context menu for this cell.
   * 
   * @return a context menu derived from the node operation descriptors, or null if no node operation descriptors are specified.
   */
  private ContextMenu createContextMenu(Object eObjectTreeItemContent) {
    LOGGER.info("=>");
    
    if (itemDescriptor == null) {
      return null;
    }
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      // node operation descriptors, so no context menu.
      return null;
    }
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    
    // Check whether this is the first item in a list. An object can only be moved up, if it is not the first one.
    boolean first = false;
    if (eObjectTreeItem.previousSibling() == null) {
      first = true;
    }
    
    // Check whether this is the last item in a list. An object can only be moved down, if it is not the last one.
    boolean last = false;
    if (eObjectTreeItem.nextSibling() == null) {
      last = true;
    }
    
    /*
     * The object type has to be determined from the reference.
     * If the reference to the object is not 'many', the itemDescriptor is an EObjectTreeItemClassReferenceDescriptor, which
     * contains the reference.
     * If the reference to this object is 'many', the itemDescriptor is an EObjectTreeItemClassDescriptor, which doesn't contain
     * the reference. In this case the reference is available in the parent node.
     * 
     * Note that the object of this item may be a sub type of the reference type. 
     */
    EReference eReference = null;
    if (itemDescriptor instanceof EObjectTreeItemClassReferenceDescriptor) {
      EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = (EObjectTreeItemClassReferenceDescriptor) itemDescriptor;
      eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
    } else {
      EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
      if (parentEObjectTreeItem != null) {
        eReference = treeItem.getEReference();
      }
    }
    
    if (eReference == null) {
      return null;
    }

    // The type is needed for the 'new' operations.
    EClass listType = eReference.getEReferenceType();
    LOGGER.info("Reference type=" + listType.getName());
    
    // If the type has subtypes, a submenu is added with entries for each (non-abstract) subtype.
    EmfPackageHelper emfPackageHelper = new EmfPackageHelper(listType.getEPackage());
    List<EClass> subTypesAll = emfPackageHelper.getSubTypes(listType);
    List<EClass> subTypes = null;
    if (subTypesAll != null) {
      subTypesAll.add(0, listType);
      
      for (EClass eClass: subTypesAll) {
        if (!eClass.isAbstract()) {
          if (subTypes == null) {
            subTypes = new ArrayList<>();
          }
          subTypes.add(eClass);
        }
      }
    }
    
    ContextMenu contextMenu = new ContextMenu();
    // add a menuItem for each node operation descriptor.
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      MenuItem menuItem;
      Menu menu;
      if ((nodeOperationDescriptor.getOperation() == TableRowOperation.NEW_OBJECT_BEFORE)  &&
          (subTypes != null)  &&  (subTypes.size() > 1)) {
        menu = createSubClassesMenu(nodeOperationDescriptor.getMenuText(), subTypes, true);
        contextMenu.getItems().add(menu);
      } else if ((nodeOperationDescriptor.getOperation() == TableRowOperation.NEW_OBJECT_AFTER)  &&
          (subTypes != null)  &&  (subTypes.size() > 1)) {
        menu = createSubClassesMenu(nodeOperationDescriptor.getMenuText(), subTypes, false);
        contextMenu.getItems().add(menu);
      } else {
        menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
        if ((eObjectTreeItemContent != null)  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.NEW_OBJECT)) {
          menuItem.setDisable(true);
        }
        if ((eObjectTreeItemContent == null)  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.DELETE_OBJECT)) {
          menuItem.setDisable(true);
        }
        
        if (first  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.MOVE_OBJECT_UP)) {
          menuItem.setDisable(true);
        }
        if (last  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.MOVE_OBJECT_DOWN)) {
          menuItem.setDisable(true);
        }
        
        if (nodeOperationDescriptor.getOperation() == TableRowOperation.EXTENDED_OPERATION) {
          ExtendedNodeOperationDescriptor extendedNodeOperationDescriptor = (ExtendedNodeOperationDescriptor) nodeOperationDescriptor;
          Predicate<EObjectTreeItem> predicate = extendedNodeOperationDescriptor.getIsMenuToBeEnabled();
          if (predicate != null) {
            if (! predicate.test(eObjectTreeItem)) {
              menuItem.setDisable(true);
            }
          }
        }
        
        contextMenu.getItems().add(menuItem);
        final TableRowOperation operation = nodeOperationDescriptor.getOperation();
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent t) {
            switch (operation) {
            case NEW_OBJECT:
              createAndSetObject();
              break;
              
            case NEW_OBJECT_BEFORE:
              createAndAddObject(listType, true);
              break;
              
            case NEW_OBJECT_AFTER:
              createAndAddObject(listType, false);
              break;
              
            case DELETE_OBJECT:
              deleteObject();
              break;
              
            case MOVE_OBJECT_UP:
              moveObject(true);
              break;
              
            case MOVE_OBJECT_DOWN:
              moveObject(false);
              break;
              
            case ATTRIBUTE_EDITOR:
              throw new IllegalArgumentException("Operatie 'Attribuut editor' is (momenteel) niet mogelijk voor een object");
              
            case OPEN:
              throw new IllegalArgumentException("Operatie 'Open' is (momenteel) niet mogelijk voor een object");
              
            case EXTENDED_OPERATION:
              ExtendedNodeOperationDescriptor extendedNodeOperationDescriptor = (ExtendedNodeOperationDescriptor) nodeOperationDescriptor;
              Consumer<EObjectTreeItem> nodeOperation = extendedNodeOperationDescriptor.getNodeOperation();
              if (nodeOperation != null) {
                nodeOperation.accept(eObjectTreeItem);
              }
              break;
            }
          }
        });
      }
    }
    
    LOGGER.info("<= contextMenu");
    return contextMenu;
  }
  
  private Menu createSubClassesMenu(String menuText, List<EClass> classes, boolean before) {
    Menu menu = new Menu(menuText);
    MenuItem menuItem;
    
    for (EClass eClass: classes) {
      menuItem = new MenuItem(eClass.getName());
      
      menuItem.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          createAndAddObject(eClass, before);
        }
        
      });
      
      menu.getItems().add(menuItem);
    }
    
    return menu;
  }
  
  private void createAndSetObject() {
    LOGGER.info("=>");
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();    
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = (EObjectTreeItemClassReferenceDescriptor) itemDescriptor;
    EReference eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
    EClass eClass = (EClass) eReference.getEType();
    
        
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject newEObject = eFactory.create(eClass);
    
    EObject parentEObject = (EObject) parentEObjectTreeItem.getValue();
    parentEObject.eSet(eReference, newEObject);
    
    parentEObjectTreeItem.rebuildChildren();

    LOGGER.info("<=");
  }
  
  private void createAndAddObject(EClass eClass, boolean before) {
    LOGGER.info("=> CREATING NEW OBJECT");
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObject eObject = (EObject) eObjectTreeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) parentEObjectTreeItem.getValue();
        
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject newEObject = eFactory.create(eClass);
    
    int index = eObjectList.indexOf(eObject);
    if (!before) {
      index++;
    }
    LOGGER.info("index=" + index);
    eObjectList.add(index, newEObject);
    
    parentEObjectTreeItem.rebuildChildren();

    LOGGER.info("<= NEW OBJECT CREATED");
  }
  
  private void moveObject(boolean up) {
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObject eObject = (EObject) eObjectTreeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) parentEObjectTreeItem.getValue();
    
    int currentIndex = eObjectList.indexOf(eObject);
    int newIndex;
    if (up) {
      newIndex = currentIndex - 1;
    } else {
      newIndex = currentIndex + 1;
    }
    eObjectList.move(newIndex, currentIndex);
    
    parentEObjectTreeItem.rebuildChildren();
  }

  /**
   * Delete the object related to this tree cell.
   */
  private void deleteObject() {
    LOGGER.severe("=>");
    
    // Get the object to be deleted.
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObject eObjectToBeDeleted = (EObject) eObjectTreeItem.getValue();  // By definition the object will be an EOBject
    
    // If the object to be deleted is referenced by a containment reference, check whether there are other references to this object. Inform the user about this.
    EReference eReferenceToObjectToBeDeleted = getEReferenceForOurObject();
    if (eReferenceToObjectToBeDeleted.isContainment()) {
      LOGGER.info("Containment");
      ResourceSet resourceSet = eObjectToBeDeleted.eResource().getResourceSet();
      Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObjectToBeDeleted, resourceSet);
      
      if (settings.size() != 0) {
        if (!canWeDeleteCrossReferences(eObjectToBeDeleted, settings)) {
          return;
        }
        
        // Delete the references
        for (EStructuralFeature.Setting setting: settings) {
          EStructuralFeature feature = setting.getEStructuralFeature();
          EObject referringObject = setting.getEObject();
          LOGGER.severe("Deleting feature: " + feature.getName() + " from " + referringObject);
          
          if (feature.isMany()) {
            List<?> list = (List<?>) referringObject.eGet(feature);
            list.remove(eObjectToBeDeleted);
          } else {
            referringObject.eUnset(feature);
          }
        }
        
      }
    }
//    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
//    EObjectTreeItemContent parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
//    LOGGER.severe("parentEObjectTreeItemContent: " + parentEObjectTreeItemContent.toString());
//    EStructuralFeature parentFeature = parentEObjectTreeItemContent.getEStructuralFeature();
//    LOGGER.severe("parentFeature:" + (parentFeature != null ? parentFeature.getName() : "(null)"));
//    if (parentFeature != null) {
//      if (parentFeature instanceof EReference reference) {
//        if (reference.isContainment()) {
//          // only now check
//          ResourceSet resourceSet = null;
//          Resource resource = eObjectToBeDeleted.eResource();
//          if (resource != null) {
//            resourceSet = resource.getResourceSet();
//          } else {
//            resourceSet = EMFResourceSet.getResourceSet();
//          }
//          
//          Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObjectToBeDeleted, resourceSet);
          
//          if (settings.size() != 0) {
//            StringBuffer buf = new StringBuffer();
//            buf.append("You are deleting a ");
//            buf.append(eObjectToBeDeleted.getClass().getName()).append(". ");
//            if (settings.size() == 1) {
//              buf.append("There is a reference to this object.");
//            } else {
//              buf.append("There are ");
//              buf.append(settings.size());
//              buf.append(" references to this object.");
//            }
//            buf.append(NEW_LINE);
//
//            for (EStructuralFeature.Setting setting: settings) {
//              EStructuralFeature feature = setting.getEStructuralFeature();
//              EObject referringObject = setting.getEObject();
//              
//              buf.append(feature.getName());
//              buf.append(" in ");
//              buf.append(referringObject.eClass().getName());
//              buf.append(NEW_LINE);
//            }
//            buf.append("If you continue, these references will be cleared!");
//            
//            CustomizationFx customization = DefaultCustomizationFx.getInstance();
//            ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
//            Alert alert = componentFactory.createOkCancelConfirmationDialog("How to continue?", buf.toString(), "What do you want?");
//
//            ButtonType buttonContinue = new ButtonType("Continue");
//            alert.getButtonTypes().remove(ButtonType.OK);
//            alert.getButtonTypes().add(buttonContinue);
//
//            Optional<ButtonType> result = alert.showAndWait();
//            
//            if (result.get() == ButtonType.CANCEL) {
//              return;
//            }
//          }
          
//          for (EStructuralFeature.Setting setting: settings) {
//            EStructuralFeature feature = setting.getEStructuralFeature();
//            EObject referringObject = setting.getEObject();
//            
//            if (feature.isMany()) {
//              List<?> list = (List<?>) referringObject.eGet(feature);
//              list.remove(eObjectToBeDeleted);
//            } else {
//              referringObject.eUnset(feature);
//            }
//          }
//        }
//      }
//    }
        
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    Object parentObject = parentEObjectTreeItem.getValue();

    if (parentObject instanceof EList) {
      @SuppressWarnings("unchecked")
      EList<EObject> eObjectList = (EList<EObject>) parentObject;
      boolean removed = eObjectList.remove(eObjectToBeDeleted);
      LOGGER.severe("Object removed=" + removed);
    } else if (parentObject instanceof EObject parentEObject) {
      for (EReference eReference: parentEObject.eClass().getEAllReferences()) {
        if (parentEObject.eGet(eReference).equals(eObjectToBeDeleted)) {
          LOGGER.info("found reference");
          LOGGER.severe(EmfUtil.eObjectToString(parentEObject));
          parentEObject.eSet(eReference, null);
          LOGGER.severe(EmfUtil.eObjectToString(parentEObject));
          break;
        }
      }
    } else {
      throw new RuntimeException("Type of parent not supported: type=" + parentObject.getClass().getName());
    }

//    EList<EReference> references = eObjectToBeDeleted.eClass().getEAllReferences();
//    for (EReference reference: references) {
//      Object object = eObjectToBeDeleted.eGet(reference);
//      if (object instanceof List) {
//        List<?> list = (List<?>) object;
//        if (list.isEmpty()) {
//          continue;
//        }
//      }
//      LOGGER.severe("Reference = " + reference.getName());
//      eObjectToBeDeleted.eUnset(reference);
//    }
    
//    parentEObjectTreeItem.rebuildChildren();
  }
  
  /**
   * Get the EReference referring to our object.
   * 
   * @return the EReference referring to our object.
   */
  private EReference getEReferenceForOurObject() {
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItemType eObjectTreeItemType = eObjectTreeItem.getEObjectTreeItemType();
    
    EReference eReference = null;
    if (eObjectTreeItemType == EObjectTreeItemType.OBJECT) {
      // the reference is in this object
      eReference = treeItem.getEReference();
//    } else if (eObjectTreeItemType == EObjectTreeItemType.OBJECT_LIST) {
//      // the reference is in the parent item
//      EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
//      EObjectTreeItemContent parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
//      LOGGER.severe("parentEObjectTreeItemContent: " + parentEObjectTreeItemContent.toString());
//      eReference = (EReference) parentEObjectTreeItemContent.getEStructuralFeature();
    } else {
      throw new RuntimeException("Illegal EObjectTreeItemType for tree item. Tree Item = " + eObjectTreeItem + ", EObjectTreeItemType = " + eObjectTreeItemType);
    }
    
    if (eReference == null) {
      // the reference is in the parent item
      EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
      LOGGER.severe("parentEObjectTreeItem: " + parentEObjectTreeItem.toString());
      throw new RuntimeException("TODO handle reference from parent");
//      eReference = (EReference) parentEObjectTreeItemContent.getEStructuralFeature();
    }
    
    return eReference;
  }
  
  /**
   * Show a dialog to the user with information about the to be deleted references and wait for the users choice.
   * 
   * @param eObjectToBeDeleted the object to be deleted and for which the references will be shown.
   * @param settings information about references to {@code eObjectToBeDeleted}.
   * @return true if the user has excepted that the references will be deleted, false otherwise.
   */
  private boolean canWeDeleteCrossReferences(EObject eObjectToBeDeleted, Collection<EStructuralFeature.Setting> settings) {
    StringBuffer buf = new StringBuffer();
    buf.append("You are deleting a ");
    buf.append(eObjectToBeDeleted.getClass().getName()).append(". ");
    if (settings.size() == 1) {
      buf.append("There is a reference to this object:");
    } else {
      buf.append("There are ");
      buf.append(settings.size());
      buf.append(" references to this object:");
    }
    buf.append(NEW_LINE);

    for (EStructuralFeature.Setting setting: settings) {
      EStructuralFeature feature = setting.getEStructuralFeature();
      EObject referringObject = setting.getEObject();
      
      buf.append(feature.getName());
      buf.append(" in ");
      buf.append(referringObject.eClass().getName());
      buf.append(NEW_LINE);
    }
    buf.append("If you continue, these references will be cleared!");
    
    CustomizationFx customization = DefaultCustomizationFx.getInstance();
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    Alert alert = componentFactory.createOkCancelConfirmationDialog("How to continue?", buf.toString(), "What do you want?");

    ButtonType buttonContinue = new ButtonType("Continue");
    alert.getButtonTypes().remove(ButtonType.OK);
    alert.getButtonTypes().add(buttonContinue);

    Optional<ButtonType> result = alert.showAndWait();
    
    LOGGER.severe("Answer: " + result);
    if (result.isPresent()  &&  "Continue".equals(result.get().getText())) {
      return true;
    } else {
      return false;
    }
  }
  
  private String getText(Object eObjectTreeItemContent) {
    LOGGER.info("=>");
    
    String labelText = null;
    EObject eObject = (EObject) eObjectTreeItemContent;
    
    if ((itemDescriptor != null)  &&  (itemDescriptor.getBuildText() != null)) {
      labelText = itemDescriptor.getBuildText().apply(eObject);
    } else if (eObject != null) {
      String className = eObject.getClass().getSimpleName();
      labelText = className.substring(0, className.length() - 4);
    }
        
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  @Override
  public String getText() {
    return eObjectTreeCell.getText();
  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isDropPossible(EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {  
//    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) thisEObjectTreeItem;
//    LOGGER.severe("=> thisEObjectTreeItem=" + thisEObjectTreeItem.toString());
//    
//    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = (EObjectTreeItemClassDescriptor) eObjectTreeItemForObject.getValue().getPresentationDescriptor();
//    EClass eClass = eObjectTreeItemClassDescriptor.getEClass();
//    
////    EReference eReference = eObjectTreeItemForObject.getEReference();
////    if (eReference != null) {
////      LOGGER.severe("Object");
////    } else {
////      LOGGER.severe("Object list");
////    }
//    
////    // parent will be a reference.
////    EObjectTreeItemForObjectList eObjectParentTreeItem = (EObjectTreeItemForObjectList)  thisEObjectTreeItem.getParent();
////    EReference parentEReference = eObjectParentTreeItem.getEReference();
////    if (parentEReference != null) {
////      LOGGER.info("parentEReference=" + parentEReference.toString());
////      EClass contentReferenceType = parentEReference.getEReferenceType();
////      if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {
////        return true;
////      }
////    }
//    
////    return false;
//    
//    if (!eClass.isSuperTypeOf(sourceEObject.eClass())) {
//      return false;
//    }
//    
//    // We don't allow a cut and paste from a containment reference to a non-containment reference.
//    EReference sourceReference = null;
//    EReference destinationReference = null;
//    
//    if (sourceReference.isContainment()  &&  !destinationReference.isContainment()) {
//      LOGGER.severe("Cut and paste from a containment reference to a non-containment reference is not allowed");
//      return false;
//    }
//
//    return true;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public void handleDragDropped(DragEvent dragEvent, EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {
//    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) thisEObjectTreeItem;
//    LOGGER.severe("=> thisEObjectTreeItem=" + thisEObjectTreeItem.toString());
//    
//    if (!isDropPossible(sourceEObject, thisEObjectTreeItem)) {
//      return;
//    }
//    
//     
//    /*
//     * If this item has its reference set, it is a single reference. In this case the value will be replaced.
//     * Else the parent contains a many reference and the object will be inserted before the object of this item.
//     */
//    
//    EReference eReference = eObjectTreeItemForObject.getEReference();
//    if (eReference != null) {
//      LOGGER.severe("Object");
//    } else {
//      LOGGER.severe("Object list");
//      EObjectTreeItemForObjectList eObjectParentTreeItem = (EObjectTreeItemForObjectList)  thisEObjectTreeItem.getParent();
//      EObjectTreeItemContent eObjectParentTreeItemContent = eObjectParentTreeItem.getValue();
////      EStructuralFeature parentStructuralFeature = eObjectParentTreeItemContent.getEStructuralFeature();
//      EReference parentEReference = eObjectParentTreeItem.getEReference();
//      LOGGER.severe("parentEReference=" + parentEReference.toString());
//      EClass contentReferenceType = parentEReference.getEReferenceType();
//
//      // It is a list, so insert before this item.
//      LOGGER.info("Item is part of a list; insert before");
//      @SuppressWarnings("unchecked")
//      EList<Object> contentEList = (EList<Object>) eObjectParentTreeItemContent.getObject();
//      LOGGER.info("contentEList=" + contentEList.toString());
//      EObjectTreeItemContent thisEObjectTreeItemContent = thisEObjectTreeItem.getValue();
//      Object thisObject = thisEObjectTreeItemContent.getObject();
//      LOGGER.info("contentObject=" + thisObject.toString());
//
//      // cut the source object
//      contentEList.remove(sourceEObject);
//
//      // rebuild the children of the parent of the source, if it is not our parent.
//      EObjectTreeCell sourceParentCell = (EObjectTreeCell) dragEvent.getSource();
//      EObjectTreeItem sourceParent = (EObjectTreeItem) sourceParentCell.getTreeItem();
//      if (sourceParent != eObjectParentTreeItem) {
//        sourceParent.rebuildChildren();
//      }
//
//      int contentObjectIndex = contentEList.indexOf(thisObject);
//      LOGGER.info("contentObjectIndex=" + contentObjectIndex);
//      if (contentObjectIndex != -1) {
//        contentEList.add(contentEList.indexOf(thisObject), sourceEObject);
//        eObjectParentTreeItem.rebuildChildren();
//      }
//    }
//    
//  }
}
