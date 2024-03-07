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

import goedegep.appgen.TableRowOperation;
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
    LOGGER.info("=>");
    
    graphic = new ImageView();
    graphic.setPreserveRatio(true);
    graphic.setFitHeight(16);
    
    LOGGER.info("<=");
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
    LOGGER.info("=>");
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      return null;
    }
    
    EObjectTreeItemForObjectList eObjectTreeItem = (EObjectTreeItemForObjectList) eObjectTreeCell.getTreeItem();
    
    EReference eReference = eObjectTreeItem.getEReference();
    EClass listType = eReference.getEReferenceType();
    LOGGER.info("Reference type=" + listType.getName());
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

      final TableRowOperation operation = nodeOperationDescriptor.getOperation();

      if ((nodeOperationDescriptor.getOperation() == TableRowOperation.NEW_OBJECT)  &&
          (subTypes != null)  &&  (subTypes.size() > 1)) {
        menu = createSubClassesMenu(nodeOperationDescriptor.getMenuText(), subTypes, true, nodeOperationDescriptor.getBiConsumer());
        contextMenu.getItems().add(menu);
      } else {

        switch (operation) {
        case NEW_OBJECT:
          menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
          contextMenu.getItems().add(menuItem);
          menuItem.setOnAction((actionEvent) -> createAndAddObject(null, nodeOperationDescriptor.getBiConsumer()));

          if (!eReference.isContainment()) {
            LOGGER.info("Not containment: " + eReference.getName());
            // Find all items in the EObject hierarchy which are of the correct type, later add all subtypes.
            EObject root = (EObject) eObjectTreeCell.getTreeView().getRoot().getValue();
            List<EObject> candidates = EmfUtil.findObjectsOfType(root, eReference.getEReferenceType());

            // Make sub menu with all these objects
            Menu subMenu = new Menu(nodeOperationDescriptor.getMenuText());
            for (EObject candidate: candidates) {
              LOGGER.info("candidate: " + candidate);
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
        case EXTENDED_OPERATION:
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
   * @param biCon TODO
   * @return a {@code Menu} for the {@classes}.
   */
  private Menu createSubClassesMenu(String menuText, List<EClass> classes, boolean before, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    Menu menu = new Menu(menuText);
    MenuItem menuItem;
    
    for (EClass eClass: classes) {
      menuItem = new MenuItem(eClass.getName());
      
      menuItem.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          createAndAddObject(eClass, biConsumer);
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
    LOGGER.info("=> item=" + (object != null ? object.toString() : "(null)"));
    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(buildText(object));
    if (itemDescriptor.isStrongText()) {
      eObjectTreeCell.setStyle("-fx-font-weight: bold;");
    }
    
    Image image = null;

    if (object != null) {
      if (itemDescriptor != null) {
        Function<Object, Image> nodeIconFunction = itemDescriptor.getNodeIconFunction();
        if (nodeIconFunction != null) {
          image = nodeIconFunction.apply(object);
        }
      }
    }
    
    if (image == null) {
      image = defaultImage;
    }
    
    graphic.setImage(image);
    
    LOGGER.info("<=");
  }
  
  /**
   * Create an object of a specific class and add it to the object list.
   * 
   * @param eClass the object type to be created.
   *        This is an optional parameter and if specified it must be a type compatible to the type of the objects in the list.
   *        If the type is not specified, an object of the type of the objects in the list is created.
   *        This parameter is typically used if you want to create an object which may be a sub type of the type of the objects in the list.
   * @param uponObjectCreatedMethod this method, if specified, is called after the object is created. This is typically used to do some further initialization.
   */
  private void createAndAddObject(EClass eClass, BiConsumer<EObject, EObjectTreeItem> uponObjectCreatedMethod) {
    LOGGER.info("=>");
    
    EObjectTreeItemForObjectList eObjectTreeItem = (EObjectTreeItemForObjectList) eObjectTreeCell.getTreeItem();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItem.getValue();    
    
    if (eClass == null) {
      EReference eReference = eObjectTreeItem.getEReference();
      eClass = eReference.getEReferenceType();
    }
    LOGGER.info("eClass=" + eClass.getName());
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject eObject = eFactory.create(eClass);
    
    if (uponObjectCreatedMethod != null) {
      uponObjectCreatedMethod.accept(eObject, eObjectTreeItem);
    }
    
    eObjectList.add(eObject);
    
    LOGGER.info("=>");
  }


  /**
   * Get the text to be shown for this cell.
   * <p>
   * The text is the labelText obtained from the itemDescriptor.
   * 
   * @param eObjectTreeItemContent the item content. This parameter is ignored.
   * @return the text to be shown for this cell.
   */
  protected String buildText(Object value) {
    LOGGER.info("=>");
 
    
    String labelText = itemDescriptor.getLabelText() + ":";
            
    LOGGER.info("<= labelText=" + labelText);
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
