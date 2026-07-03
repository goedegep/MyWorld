package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import goedegep.jfx.Operation;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.emf.EmfUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a tree cell helper for a list of objects.
 */
public class EObjectTreeCellHelperForObjectList extends EObjectTreeCellHelperTemplate<EObjectTreeItemForObjectList, EObjectTreeItemClassListReferenceDescriptor, ImageView> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForObjectList.class.getName());
  
  private Image defaultImage = new Image(EObjectTreeCellHelperForObjectList.class.getResourceAsStream("List 225x225.png"), 36, 18, true, true);
  
  public EObjectTreeCellHelperForObjectList(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  /**
   * {@inheritDoc}
   * The graphic is an {@code ImageView}.<br/>
   */
  @Override
  protected void createGraphic() {
    graphic = new ImageView();
    graphic.setPreserveRatio(true);
    graphic.setFitHeight(16);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setEObjectTreeItemDescriptor() {
    itemDescriptor = treeItem.getEObjectTreeItemClassListReferenceDescriptor();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextMenu createContextMenu(Object object) {
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      return null;
    }
    
    EObjectTreeItemForObjectList eObjectTreeItem = (EObjectTreeItemForObjectList) eObjectTreeCell.getTreeItem();
    
    EReference eReference = eObjectTreeItem.getEReference();
    EClass listType = eReference.getEReferenceType();
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
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      MenuItem menuItem;
      Menu menu;

      final Operation operation = nodeOperationDescriptor.getOperation();

      if ((nodeOperationDescriptor.getOperation() == Operation.NEW_OBJECT)  &&
          (subTypes != null)  &&  (subTypes.size() > 1)) {
        menu = createSubClassesMenu(nodeOperationDescriptor.getMenuText(), subTypes, ((NodeOperationDescriptorNew) nodeOperationDescriptor).getNewEObjectInitializationFunction());
        contextMenu.getItems().add(menu);
      } else {

        switch (operation) {
        case NEW_OBJECT:
          menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
          contextMenu.getItems().add(menuItem);
          menuItem.setOnAction((_) -> createAndAddObject(null, ((NodeOperationDescriptorNew) nodeOperationDescriptor).getNewEObjectInitializationFunction()));

          if (!eReference.isContainment()) {
            // Find all items in the EObject hierarchy which are of the correct type, later add all subtypes.
            EObject root = (EObject) eObjectTreeCell.getTreeView().getRoot().getValue();
            List<EObject> candidates = EmfUtil.findObjectsOfType(root, eReference.getEReferenceType());

            // Make sub menu with all these objects
            Menu subMenu = new Menu(nodeOperationDescriptor.getMenuText());
            for (EObject candidate: candidates) {
              EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = ((EObjectTreeView) eObjectTreeCell.getTreeView()).getDescriptorForEClass(candidate.eClass());
              MenuItem subMenuItem = new MenuItem(eObjectTreeItemClassDescriptor.getNodeTextFunction().apply(candidate));
              subMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @SuppressWarnings("unchecked")
                public void handle(ActionEvent t) {
                  ((EObjectResolvingEList<Object>) eObjectTreeItem.getValue()).add(candidate);
                }
              });

              subMenu.getItems().add(subMenuItem);
            }
            contextMenu.getItems().add(subMenu);

            // On selection, add the selected object
          }
          break;

        case NEW_OBJECT_BEFORE:
        case NEW_OBJECT_AFTER:
        case DELETE_OBJECT:
        case MOVE_OBJECT_UP:
        case MOVE_OBJECT_DOWN:
        case ATTRIBUTE_EDITOR:
        case OPEN:
          throw new IllegalArgumentException("EObjectTreeView doesn't support the operation: " + operation);
          
        case EXTENDED_OPERATION:
          NodeOperationDescriptorCustom nodeOperationDescriptorCustom = (NodeOperationDescriptorCustom) nodeOperationDescriptor;
          menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
          contextMenu.getItems().add(menuItem);
          menuItem.setOnAction((_) -> nodeOperationDescriptorCustom.getNodeOperationFunction().accept(eObjectTreeItem));
          break;
        }  
      }
    }
    
    if (contextMenu.getItems().size() > 0) {
      return contextMenu;
    } else {
      return null;
    }
  }
  
  /**
   * Create a menu for subclasses.
   * 
   * @param menuText the menu text
   * @param classes the subclasses
   * @param before 'before' (if set) or 'after' indication
   * @param newEObjectInitializationFunction this method, if specified, is called after the object is created. This is typically used to do some further initialization.
   * @return a {@code Menu} for the {@classes}.
   */
  private Menu createSubClassesMenu(String menuText, List<EClass> classes, BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction) {
    Menu menu = new Menu(menuText);
    MenuItem menuItem;
    
    for (EClass eClass: classes) {
      menuItem = new MenuItem(eClass.getName());
      
      menuItem.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          createAndAddObject(eClass, newEObjectInitializationFunction);
        }
        
      });
      
      menu.getItems().add(menuItem);
    }
    
    return menu;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateContent(Object object) {
    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(buildText(object));
    if (itemDescriptor.isStrongText()) {
      eObjectTreeCell.setStyle("-fx-font-weight: bold;");
    }
    
    Image image = null;

    if (object != null  &&  itemDescriptor != null) {
      Function<Object, Image> nodeIconFunction = itemDescriptor.getNodeIconFunction();
      if (nodeIconFunction != null) {
        image = nodeIconFunction.apply(object);
      }
    }
    
    if (image == null) {
      image = defaultImage;
    }
    
    graphic.setImage(image);
  }
  
  /**
   * Create an object of a specific class and add it to the object list.
   * 
   * @param eClass the object type to be created.
   *        This is an optional parameter and if specified it must be a type compatible to the type of the objects in the list.
   *        If the type is not specified, an object of the type of the objects in the list is created.
   *        This parameter is typically used if you want to create an object which may be a sub type of the type of the objects in the list.
   * @param newEObjectInitializationFunction this method, if specified, is called after the object is created. This is typically used to do some further initialization.
   */
  private void createAndAddObject(EClass eClass, BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction) {
    EObjectTreeItemForObjectList eObjectTreeItem = (EObjectTreeItemForObjectList) eObjectTreeCell.getTreeItem();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItem.getValue();    
    
    if (eClass == null) {
      EReference eReference = eObjectTreeItem.getEReference();
      eClass = eReference.getEReferenceType();
    }
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject eObject = eFactory.create(eClass);
    
    if (newEObjectInitializationFunction != null) {
      newEObjectInitializationFunction.accept(eObject, eObjectTreeItem);
    }
    
    eObjectList.add(eObject);
  }


  /**
   * Get the text to be shown for this cell.
   * <p>
   * The text is the labelText obtained from the itemDescriptor.
   * 
   * @param eObjectTreeItemContent the item content. This parameter is ignored.
   * @return the text to be shown for this cell.
   */
  @Override
  protected String buildText(Object value) {    
    String labelText = itemDescriptor.getNodeTextFunction().apply(value) + ":";
            
    return labelText;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getText() {
    return eObjectTreeCell.getText();
  }
  
}
