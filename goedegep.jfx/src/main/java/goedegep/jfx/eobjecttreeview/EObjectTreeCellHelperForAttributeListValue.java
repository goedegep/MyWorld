package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import goedegep.appgen.TableRowOperation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;


/**
 * This class is a tree cell helper for a single value of an attribute of type many.
 * <p>
 * The representation consists of a single, editable value. Therefore there is no descriptor for this type of cell.
 */
public class EObjectTreeCellHelperForAttributeListValue extends EObjectTreeCellHelperAbstract<EObjectTreeItemDescriptor>  {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeListValue.class.getName());
  
  private HBox graphic = null;             // will contain either valueLabel (not editing) or valueTextField/valueChoiceBox (editing).
  private Label valueLabel = null;         // this label is always there (as it is the normal situation), but only part of the graphic when not in editing mode.
  private TextField valueTextField = null;          // this text field is created and made part of the graphic when editing starts, and set back to null when editing ends.
  private ChoiceBox<Object> valueChoiceBox = null;  // same as previous, but for enums. 
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell
   */
  public EObjectTreeCellHelperForAttributeListValue(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));
    
    super.updateItem(eObjectTreeItemContent);
    
    ContextMenu contextMenu = createContextMenu();
    eObjectTreeCell.setContextMenu(contextMenu);

    if (graphic == null) {
      createGraphic();
    }
    
    eObjectTreeCell.setText(null);
    eObjectTreeCell.setGraphic(graphic);
    
    if (eObjectTreeCell.isEditing()) {
      valueTextField.setText(getText(eObjectTreeItemContent));
    } else {
      valueLabel.setText(getText(eObjectTreeItemContent));
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
    
    /* Without an item descriptor we can't generate a ContextMenu. */
    if (itemDescriptor == null) {
      LOGGER.info("<= null");
      return null;
    }
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    /* Without node operation descriptors we can't generate a ContextMenu. */
    if (nodeOperationDescriptors == null) {
      LOGGER.info("<= null");
      return null;
    }
    
    /*
     * Determine whether this tree item is the first and/or last in the list.
     * The first item cannot be moved up, the last item cannot be moved down.
     */
    boolean first = false;
    boolean last = false;
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    if (eObjectTreeItem.previousSibling() == null) {
      first = true;
    }
    if (eObjectTreeItem.nextSibling() == null) {
      last = true;
    }
    
//    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
//    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
//    EStructuralFeature eStructuralFeature = eObjectParentTreeItemContent.getEStructuralFeature();
//    EReference eReference = (EReference) eStructuralFeature;
//    EClass listType = eReference.getEReferenceType();
//    LOGGER.info("Reference type=" + listType.getName());
//    EmfPackageHelper emfPackageHelper = new EmfPackageHelper(listType.getEPackage());
//    List<EClass> subTypes = emfPackageHelper.getSubTypes(listType);
//    if (subTypes != null) {
//      subTypes.add(0, listType);
//      for (EClass subType: subTypes) {
//        LOGGER.info("subType: " + subType.getInstanceTypeName());
//      }
//    }
    
    ContextMenu contextMenu = new ContextMenu();
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      LOGGER.info("Handling operation: " + nodeOperationDescriptor.getOperation().name());
      MenuItem menuItem;
      menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());

      if (first  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.MOVE_OBJECT_UP)) {
        menuItem.setDisable(true);
      }
      if (last  &&  (nodeOperationDescriptor.getOperation() == TableRowOperation.MOVE_OBJECT_DOWN)) {
        menuItem.setDisable(true);
      }

      contextMenu.getItems().add(menuItem);
      final TableRowOperation operation = nodeOperationDescriptor.getOperation();
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
          switch (operation) {
          case NEW_OBJECT:
            throw new IllegalArgumentException("Operatie 'Nieuw object' is niet mogelijk voor een object");

          case NEW_OBJECT_BEFORE:
            createAndAddValue(true);
            break;

          case NEW_OBJECT_AFTER:
            createAndAddValue(false);
            break;

          case DELETE_OBJECT:
            deleteValue();
            break;

          case MOVE_OBJECT_UP:
            moveValue(true);
            break;

          case MOVE_OBJECT_DOWN:
            moveValue(false);
            break;

          case ATTRIBUTE_EDITOR:
            throw new IllegalArgumentException("Operation 'Attribute editor' is (currently) not possible for an object");

          case OPEN:
            throw new IllegalArgumentException("Operation 'Open' is (currently) not possible for an object");
          
          case EXTENDED_OPERATION:
            throw new IllegalArgumentException("Operation 'Extended operation' is (currently) not possible for an object");
          }
        }
      });
    }
    
    LOGGER.info("<= contextMenu");
    return contextMenu;
  }
  
  private void deleteValue() {
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    
    Object object = eObjectTreeItemContent.getObject();
    @SuppressWarnings("unchecked")
    EList<Object> objectList = (EList<Object>) eObjectParentTreeItemContent.getObject();
    
    objectList.remove(object);
    
    parentEObjectTreeItem.rebuildChildren();
  }

  protected void createAndAddValue(boolean before) {
    LOGGER.info("=>");
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    
    Object object = eObjectTreeItemContent.getObject();
    @SuppressWarnings("unchecked")
    EList<Object> objectList = (EList<Object>) eObjectParentTreeItemContent.getObject();
        
    int index = objectList.indexOf(object);
    if (!before) {
      index++;
    }
    LOGGER.info("index=" + index);
    objectList.add(index, null);
    
    parentEObjectTreeItem.rebuildChildren();

    LOGGER.info("<=");
  }
  
  protected void moveValue(boolean up) {
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    
    Object object = eObjectTreeItemContent.getObject();
    @SuppressWarnings("unchecked")
    EList<Object> objectList = (EList<Object>) eObjectParentTreeItemContent.getObject();
    
    int currentIndex = objectList.indexOf(object);
    int newIndex;
    if (up) {
      newIndex = currentIndex - 1;
    } else {
      newIndex = currentIndex + 1;
    }
    objectList.move(newIndex, currentIndex);
    
    parentEObjectTreeItem.rebuildChildren();
  }

  @Override
  public void startEdit(EObjectTreeCell eObjectTreeCell) {
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem().getParent();
    EObjectTreeItemContent parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
    EStructuralFeature eStructuralFeature = parentEObjectTreeItemContent.getEStructuralFeature();
    LOGGER.severe("structural feature: " + eStructuralFeature.toString());
    EAttribute eAttribute = (EAttribute) eStructuralFeature;
    EDataType eDataType = eAttribute.getEAttributeType();
    LOGGER.severe("eDataType: " + eDataType.getName());
    Class<?> listElementClass = eDataType.getInstanceClass();
    LOGGER.severe("listElementClass: " + listElementClass.getName());
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeCell.getItem();
        
    // replace the valueLabel with a newly created TextField. The value for the text field is obtained from the item of this cell.
    graphic.getChildren().clear();
    
    if (listElementClass.isEnum()) {
      // Use a ChoiceBox for enums
      valueChoiceBox = new ChoiceBox<>();
      for (Object value: listElementClass.getEnumConstants()) {
        valueChoiceBox.getItems().add(value);
        
        valueChoiceBox.onActionProperty().set(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {
            eObjectTreeItemContent.setObject(valueChoiceBox.getValue());
            eObjectTreeCell.commitEdit(eObjectTreeItemContent);
          }
          
        });
      }
      graphic.getChildren().add(valueChoiceBox);
    } else {
      // Text
      valueTextField = new TextField();
      valueTextField.setText(getText(eObjectTreeItemContent));
      valueTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent keyEvent) {
          if ((keyEvent.getCode() == KeyCode.ENTER)) {
            eObjectTreeItemContent.setObject(valueTextField.getText());
            eObjectTreeCell.commitEdit(eObjectTreeItemContent);
          } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            eObjectTreeCell.cancelEdit();
          }
        }
      });
      graphic.getChildren().add(valueTextField);
    }    
  }
    
  @Override
  public void commitEdit(TreeItem<EObjectTreeItemContent> treeItem, EObjectTreeItemContent newValue) {
    LOGGER.info("=> newValue=" + newValue.toString());
    
    graphic.getChildren().clear();
    valueTextField = null;
    valueChoiceBox = null;
    valueLabel.setText(getText(newValue));
    graphic.getChildren().add(valueLabel);
    
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    EObjectTreeItemContent parentValue = parentItem.getValue();
    @SuppressWarnings("unchecked")
    EDataTypeUniqueEList<Object> list = (EDataTypeUniqueEList<Object>) parentValue.getObject();
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) treeItem;
    int index = eObjectTreeItem.getChildIndex();
    list.set(index, newValue.getObject());
    
    LOGGER.info("<=");
  }

  @Override
  public void cancelEdit() {
    LOGGER.info("=>");
    
    graphic.getChildren().clear();
    valueTextField = null;
    valueChoiceBox = null;
    graphic.getChildren().add(valueLabel);

    LOGGER.info("<=");
  }


  private void createGraphic() {
    LOGGER.info("=>");
    
    graphic = new HBox(50);
    valueLabel = new Label();
    graphic.getChildren().add(valueLabel);
    
    LOGGER.info("<=");
  }

  private String getText(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=>");
    
    Object object = eObjectTreeItemContent.getObject();
    String itemText = object != null ? object.toString() : "";
        
    LOGGER.info("<= itemText=" + itemText);
    return itemText;
  }
  
  @Override
  public String getText() {
    return valueLabel.getText();
  }
  
}
