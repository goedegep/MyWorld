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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import goedegep.appgen.TableRowOperation;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.emf.EmfUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;

/**
 * This class is a tree cell helper for a list of objects.
 */
public class EObjectTreeCellHelperForObjectList extends EObjectTreeCellHelperAbstract<EObjectTreeItemClassListReferenceDescriptor> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForObjectList.class.getName());
    
  private final Node listIcon = new ImageView(new Image(EObjectTreeCellHelperForObjectList.class.getResourceAsStream("List 225x225.png"), 36, 18, true, true));
  
  public EObjectTreeCellHelperForObjectList(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));
    
    super.updateItem(eObjectTreeItemContent);
    
    ContextMenu contextMenu = createContextMenu();
    eObjectTreeCell.setContextMenu(contextMenu);

    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(getText(eObjectTreeItemContent));
    if (itemDescriptor.isStrongText()) {
      eObjectTreeCell.setStyle("-fx-font-weight: bold;");
    }
    
    
    ImageView iconImageView = null;
    if (eObjectTreeItemContent != null) {
      EObjectTreeItemDescriptor itemDescriptor = eObjectTreeItemContent.getPresentationDescriptor();
      if (itemDescriptor != null) {
        EObjectTreeItemClassListReferenceDescriptor descriptor = (EObjectTreeItemClassListReferenceDescriptor) itemDescriptor;
        Function<Object, Image> nodeIconFunction = descriptor.getNodeIconFunction();
        if (nodeIconFunction != null) {
          Image iconImage = nodeIconFunction.apply(eObjectTreeItemContent.getObject());
          if (iconImage != null) {
            iconImageView = new ImageView(iconImage);
            iconImageView.setPreserveRatio(true);
            iconImageView.setFitHeight(16);
          }
        }
      }
    }
    if (iconImageView != null) {
      eObjectTreeCell.setGraphic(iconImageView);
    } else {
      eObjectTreeCell.setGraphic(listIcon);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Create a context menu for this cell.
   * 
   * @return a context menu derived from the node operation descriptors, or null if no node operation descriptors are specified.
   */
  private ContextMenu createContextMenu() {
    LOGGER.info("=>");
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      return null;
    }
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EStructuralFeature eStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
    LOGGER.info("structural feature: " + eStructuralFeature.toString());
    EReference eReference = (EReference) eStructuralFeature;
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
          menuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
              createAndAddObject(null, nodeOperationDescriptor.getBiConsumer());
            }
          });

          if (!eReference.isContainment()) {
            LOGGER.info("Not containment: " + eReference.getName());
            // Find all items in the EObject hierarchy which are of the correct type, later add all subtypes.
            EObject root = (EObject) eObjectTreeCell.getTreeView().getRoot().getValue().getObject();
            List<EObject> candidates = EmfUtil.findObjectsOfType(root, eReference.getEReferenceType());

            // Make sub menu with all these objects
            Menu subMenu = new Menu(nodeOperationDescriptor.getMenuText());
            for (EObject candidate: candidates) {
              LOGGER.info("candidate: " + candidate);
              EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = ((EObjectTreeView) eObjectTreeCell.getTreeView()).getEObjectTreeDescriptor().getDescriptorForEClass(candidate.eClass());
              MenuItem subMenuItem = new MenuItem(eObjectTreeItemClassDescriptor.getBuildText().apply(candidate));
              subMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @SuppressWarnings("unchecked")
                public void handle(ActionEvent t) {
                  ((EObjectResolvingEList<Object>) eObjectTreeItemContent.getObject()).add(candidate);
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

  @Override
  public void startEdit(EObjectTreeCell eObjectTreeCell) {
    LOGGER.severe("=>");
  }
  
  private void createAndAddObject(EClass eClass, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    LOGGER.info("=>");
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContent.getObject();    
    LOGGER.info("eObjectList:" + eObjectList.toString());
    
    if (eClass == null) {
      EStructuralFeature eStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
      LOGGER.info("structural feature: " + eStructuralFeature.toString());
      EReference eReference = (EReference) eStructuralFeature;
      eClass = eReference.getEReferenceType();
    }
    LOGGER.info("eClass=" + eClass.getName());
    EFactory eFactory = eClass.getEPackage().getEFactoryInstance();
    EObject eObject = eFactory.create(eClass);
    
    if (biConsumer != null) {
      biConsumer.accept(eObject, eObjectTreeItem);
    }
    
    eObjectList.add(eObject);
    
    eObjectTreeItem.rebuildChildren();
    
    LOGGER.info("=>");
  }

  private String getText(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=>");
 
    
    String labelText = itemDescriptor.getLabelText() + ":";
            
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  @Override
  public String getText() {
    return eObjectTreeCell.getText();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDropPossible(EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {
    LOGGER.severe("=>");
    
    EObjectTreeItemContent eObjectTreeItemContent = thisEObjectTreeItem.getValue();
    EStructuralFeature structuralFeature = eObjectTreeItemContent.getEStructuralFeature();
    if (structuralFeature != null) {
      LOGGER.severe("structuralFeature=" + structuralFeature.toString());
      if (structuralFeature instanceof EReference eReference) {
        EClass contentReferenceType = eReference.getEReferenceType();
        if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleDragDropped(DragEvent dragEvent, EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {
    LOGGER.info("=>");
    
    if (!isDropPossible(sourceEObject, thisEObjectTreeItem)) {
      return;
    }
    
    // parent will be a reference.
    // If it is a 'many' reference, insert before this item.
    // If it isn't a 'many' reference, replace this item.
//    EObjectTreeItem eObjectParentTreeItem = (EObjectTreeItem)  thisEObjectTreeItem.getParent();
    EObjectTreeItemContent eObjectTreeItemContent = thisEObjectTreeItem.getValue();
    EStructuralFeature structuralFeature = eObjectTreeItemContent.getEStructuralFeature();
    if (structuralFeature != null) {
      LOGGER.severe("structuralFeature=" + structuralFeature.toString());
      if (structuralFeature instanceof EReference eReference) {
        EClass contentReferenceType = eReference.getEReferenceType();
        if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {  // Isn't this already checked in isDropPossible

          if (eReference.isMany()) {

            // It is a list, so add to the end of the list.
            @SuppressWarnings("unchecked")
            EList<Object> contentEList = (EList<Object>) eObjectTreeItemContent.getObject();
            LOGGER.info("contentEList=" + contentEList.toString());
//            EObjectTreeItemContent thisEObjectTreeItemContent = thisEObjectTreeItem.getValue();
            Object thisObject = eObjectTreeItemContent.getObject();
            LOGGER.info("contentObject=" + thisObject.toString());
            
            // cut the source object TODO this seems only valid for moving under the same parent
//            contentEList.remove(sourceEObject);
            
            // rebuild the children of the parent of the source, if it is not our parent.
            EObjectTreeCell sourceParentCell = (EObjectTreeCell) dragEvent.getSource();
            EObjectTreeItem sourceParent = (EObjectTreeItem) sourceParentCell.getTreeItem();
            EObjectTreeItemContent sourceParentTreeItemContent = sourceParent.getValue();
            @SuppressWarnings("unchecked")
            EList<Object> sourceContentEList = (EList<Object>) sourceParentTreeItemContent.getObject();
            sourceContentEList.remove(sourceEObject);
            
            contentEList.add(sourceEObject);
          } else {
            // It is a single value, which will be replaced.
            LOGGER.severe("Single item; replace");
          }
        }
      }
    }
  }
}
