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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a tree cell helper for an object (showing the header for the object).
 * <p>
 * If the tree item contains a reference to this object, with any presentation information, this is used to render the header.
 * Otherwise the presentation information from the related class is used.
 */
public class EObjectTreeCellHelperForObject extends EObjectTreeCellHelperTemplate<EObjectTreeItemForObject, EObjectTreeItemClassReferenceDescriptor, ImageView> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForObject.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  private static final Image DEFAULT_GRAPHIC_IMAGE = new Image(EObjectTreeCellHelperForObject.class.getResourceAsStream("Class no text.png"), 36, 16, true, true);
  
  /**
   * Class descriptor, obtained from the tree view and used if there is no reference with presentation information.
   */
  private EObjectTreeItemClassDescriptor classDescriptor;
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the {@code EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForObject(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  /**
   * {@inheritDoc}
   * The graphic is an {@code ImageView}.<br/>
   */
  @Override
  protected void createGraphic() {
    LOGGER.info("=>");
    
    graphic = new ImageView();
    graphic.setFitHeight(24);
    graphic.setPreserveRatio(true);
    
    LOGGER.info("<=");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setEObjectTreeItemDescriptor() {
    itemDescriptor = treeItem.getEObjectTreeItemClassReferenceDescriptor();
    
    if (!hasReferenceWithPresentationInfo()) {
      classDescriptor = treeItem.getClassDescriptor();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextMenu createContextMenu(Object eObjectTreeItemContent) {
    LOGGER.info("=>");
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor != null ? itemDescriptor.getNodeOperationDescriptors() : classDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      // node operation descriptors, so no context menu.
      return null;
    }
    
    EObjectTreeItemForObject eObjectTreeItem = (EObjectTreeItemForObject) eObjectTreeCell.getTreeItem();
    
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
    EReference eReference = EObjectTreeItemForObject.getApplicableEReference(eObjectTreeItem);
    
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
              createObjectAndSetReferenceToIt();
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
  
  /**
   * Create a menu for subclasses.
   * 
   * @param menuText the menu text
   * @param classes the subclasses
   * @param before 'before' (if set) or 'after' indication
   * @return a {@code Menu} for the {@classes}.
   */
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

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public void updateContent(Object value) {
    LOGGER.info("=> treeItem=" + treeItem);
    
    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(buildText(value));
    boolean isStrongText = hasReferenceWithPresentationInfo() ? itemDescriptor.isStrongText() : classDescriptor.isStrongText();
    if (isStrongText) {
      eObjectTreeCell.setStyle("-fx-font-weight: bold;");
    } else {
      eObjectTreeCell.setStyle(null);
    }
    
    
    Image iconImage = null;
    Function<Object, Image> nodeIconFunction = hasReferenceWithPresentationInfo() ? itemDescriptor.getNodeIconFunction() : classDescriptor.getNodeIconFunction();
    if (nodeIconFunction != null) {
      iconImage = nodeIconFunction.apply(value);
    }
    
    graphic.setImage(iconImage != null ? iconImage : DEFAULT_GRAPHIC_IMAGE);
    
    LOGGER.info("<=");
  }
  
  /**
   * Create a new object (of the type of the {@code itemDescriptor} and set the reference to this object.
   */
  private void createObjectAndSetReferenceToIt() {
    LOGGER.severe("=>");
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) treeItem.getParent();
    
    EClass eClass = itemDescriptor.getEClass();
        
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject newEObject = eFactory.create(eClass);
    
    EObject parentEObject = (EObject) parentEObjectTreeItem.getValue();
    EReference eReference2 = (EReference) treeItem.getEStructuralFeature();
    parentEObject.eSet(eReference2, newEObject);

    LOGGER.info("<=");
  }
  
  /** Create a new object of a specific subclass and add it to the many reference.
   * 
   * @param subClass The Class of the object to be created. The Class must be compatible with the type of the reference.
   * @param before if {@code true} the new object is inserted before this object, else after this object.
   */
  private void createAndAddObject(EClass subClass, boolean before) {
    LOGGER.info("=>");
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) treeItem.getParent();
    
    EObject eObject = (EObject) treeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) parentEObjectTreeItem.getValue();
        
    EFactory eFactory = subClass.getEPackage().getEFactoryInstance();
    EObject newEObject = eFactory.create(subClass);
    
    int index = eObjectList.indexOf(eObject);
    if (!before) {
      index++;
    }
    LOGGER.info("index=" + index);
    eObjectList.add(index, newEObject);

    LOGGER.info("<=");
  }
  
  /**
   * Move this object up or down.
   * 
   * @param up if {@code true} the object is moved upwards, else it is moved downwards.
   */
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
    EObject eObjectToBeDeleted = (EObject) treeItem.getValue();  // By definition the object will be an EOBject
    
    // If the object to be deleted is referenced by a containment reference, check whether there are other references to this object. Inform the user about this.
    EReference eReferenceToObjectToBeDeleted = EObjectTreeItemForObject.getApplicableEReference(treeItem);
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
          
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) treeItem.getParent();
    Object parentObject = parentEObjectTreeItem.getValue();

    if (parentObject instanceof EList) {
      @SuppressWarnings("unchecked")
      EList<EObject> eObjectList = (EList<EObject>) parentObject;
      boolean removed = eObjectList.remove(eObjectToBeDeleted);
      LOGGER.severe("Object removed=" + removed);
    } else if (parentObject instanceof EObject parentEObject) {
      parentEObject.eSet(eReferenceToObjectToBeDeleted, null);
    } else {
      throw new RuntimeException("Type of parent not supported: type=" + parentObject.getClass().getName());
    }
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
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String buildText(Object value) {
    LOGGER.info("=>");
    
    String labelText = null;
    EObject eObject = (EObject) value;
    
    if (hasReferenceWithPresentationInfo()) {
      if (itemDescriptor.getBuildText() != null) {
        labelText = itemDescriptor.getBuildText().apply(eObject);
      }
    } else {
      if (classDescriptor.getBuildText() != null) {
        labelText = classDescriptor.getBuildText().apply(eObject);
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
  
  @Override
  public String getText() {
    return eObjectTreeCell.getText();
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
    return itemDescriptor != null &&
      (itemDescriptor.getBuildText() != null  ||
       itemDescriptor.isStrongText()  ||
       itemDescriptor.getNodeIconFunction() != null);
  }
}
