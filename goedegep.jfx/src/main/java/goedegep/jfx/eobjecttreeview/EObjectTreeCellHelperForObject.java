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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.util.emf.EmfPackageHelper;
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

public class EObjectTreeCellHelperForObject extends EObjectTreeCellHelperAbstract<EObjectTreeItemClassDescriptor>  {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForObject.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  private final Node objectIcon = new ImageView(new Image(EObjectTreeCellHelperForObject.class.getResourceAsStream("Class no text.png"), 36, 16, true, true));
  
  public EObjectTreeCellHelperForObject(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));
    
    super.updateItem(eObjectTreeItemContent);
    
    ContextMenu contextMenu = createContextMenu(eObjectTreeItemContent);
    eObjectTreeCell.setContextMenu(contextMenu);
    
    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(getText(eObjectTreeItemContent));
    
    ImageView iconImageView = null;
    EObjectTreeItemClassDescriptor ebjectTreeItemClassDescriptor = itemDescriptor;
    if (itemDescriptor instanceof EObjectTreeItemClassReferenceDescriptor) {
      EClass eClass = itemDescriptor.getEClass();
      ebjectTreeItemClassDescriptor = eObjectTreeCell.getItem().geteObjectTreeView().getEObjectTreeDescriptor().getDescriptorForEClass(eClass);
    }
    if (eObjectTreeItemContent != null) {
      if (ebjectTreeItemClassDescriptor != null) {        
        Function<Object, Image> nodeIconFunction = ebjectTreeItemClassDescriptor.getNodeIconFunction();
        if (nodeIconFunction != null) {
          Image iconImage = nodeIconFunction.apply(eObjectTreeItemContent.getObject());
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
  private ContextMenu createContextMenu(EObjectTreeItemContent eObjectTreeItemContent) {
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
        EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
        EStructuralFeature eStructuralFeature = eObjectParentTreeItemContent.getEStructuralFeature();
        eReference = (EReference) eStructuralFeature;
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
        if ((eObjectTreeItemContent.getObject() != null)  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.NEW_OBJECT)) {
          menuItem.setDisable(true);
        }
        if ((eObjectTreeItemContent.getObject() == null)  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.DELETE_OBJECT)) {
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
    
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = (EObjectTreeItemClassReferenceDescriptor) itemDescriptor;
    EReference eReference = eObjectTreeItemClassReferenceDescriptor.getEReference();
    EClass eClass = (EClass) eReference.getEType();
    
        
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject newEObject = eFactory.create(eClass);
    
    EObject parentEObject = (EObject) eObjectParentTreeItemContent.getObject();
    parentEObject.eSet(eReference, newEObject);
    
    parentEObjectTreeItem.rebuildChildren();

    LOGGER.info("<=");
  }
  
  private void createAndAddObject(EClass eClass, boolean before) {
    LOGGER.info("=> CREATING NEW OBJECT");
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) eObjectParentTreeItemContent.getObject();
        
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
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) eObjectParentTreeItemContent.getObject();
    
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
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    
    ResourceSet resourceSet = null;
    Resource resource = eObject.eResource();
    if (resource != null) {
      resourceSet = resource.getResourceSet();
    }
    
    // TODO handle cross references when there is no resource set.
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObject, resourceSet);
    if (settings.size() != 0) {
      StringBuffer buf = new StringBuffer();
      buf.append("There are ");
      buf.append(settings.size());
      buf.append(" references to this object.");
      buf.append(NEW_LINE);

      for (EStructuralFeature.Setting setting: settings) {
        EStructuralFeature feature = setting.getEStructuralFeature();
        String inOrAs = " as ";
        if (feature.isMany()) {
          inOrAs = " in ";
        }
        EObject referringObject = setting.getEObject();
        EObject container = referringObject.eContainer();
        buf.append(container.getClass().getName());
        buf.append(" in ");
        buf.append(referringObject.toString());
        buf.append(inOrAs);
        buf.append(setting.getEStructuralFeature().getName());
        buf.append(NEW_LINE);
      }
      
      CustomizationFx customization = DefaultCustomizationFx.getInstance();
      ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
      Alert alert = componentFactory.createOkCancelConfirmationDialog("How to continue?", buf.toString(), "What do you want?");

      ButtonType buttonContinue = new ButtonType("Continue");
      alert.getButtonTypes().remove(ButtonType.OK);
      alert.getButtonTypes().add(buttonContinue);

      Optional<ButtonType> result = alert.showAndWait();
      
      if (result.get() == ButtonType.CANCEL) {
        return;
      }
    }
    
    for (EStructuralFeature.Setting setting: settings) {
      EStructuralFeature feature = setting.getEStructuralFeature();
      EObject referringObject = setting.getEObject();
      
      if (feature.isMany()) {
        List<?> list = (List<?>) referringObject.eGet(feature);
        list.remove(eObject);
      } else {
        referringObject.eUnset(feature);
      }
    }
        
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    Object parentObject = eObjectParentTreeItemContent.getObject();
    
    if (parentObject instanceof EList) {
      @SuppressWarnings("unchecked")
      EList<EObject> eObjectList = (EList<EObject>) eObjectParentTreeItemContent.getObject();
      boolean removed = eObjectList.remove(eObject);
      LOGGER.info("Object removed=" + removed);
    } else if (parentObject instanceof EObject parentEObject) {
      for (EReference eReference: parentEObject.eClass().getEAllReferences()) {
        if (parentEObject.eGet(eReference).equals(eObject)) {
          LOGGER.severe("found reference");
          parentEObject.eSet(eReference, null);
        }
      }
    } else {
      throw new RuntimeException("Type of parent not supported: type=" + parentObject.getClass().getName());
    }
    
    EList<EReference> references = eObject.eClass().getEAllReferences();
    for (EReference reference: references) {
      Object object = eObject.eGet(reference);
      if (object instanceof List) {
        List<?> list = (List<?>) object;
        if (list.isEmpty()) {
          continue;
        }
      }
      LOGGER.severe("Reference = " + reference.getName());
      eObject.eUnset(reference);
    }
    
    parentEObjectTreeItem.rebuildChildren();
  }
  
  private String getText(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=>");
    
    String labelText = null;
    EObject eObject = (EObject) eObjectTreeItemContent.getObject();
    
    if ((itemDescriptor != null)  &&  (itemDescriptor.getBuildText() != null)) {
      labelText = itemDescriptor.getBuildText().apply(eObject);
    } else if (eObject != null) {
      labelText = eObject.getClass().getSimpleName() + ":";
    }
        
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  @Override
  public String getText() {
    return eObjectTreeCell.getText();
  }
}
