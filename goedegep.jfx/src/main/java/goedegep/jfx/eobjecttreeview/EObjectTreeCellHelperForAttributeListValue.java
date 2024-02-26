package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;

import goedegep.appgen.TableRowOperation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;


/**
 * This class is a tree cell helper for a single value of an attribute of type many.
 * <p>
 * The representation consists of a single, editable value.
 */
public class EObjectTreeCellHelperForAttributeListValue extends EObjectTreeCellHelperTemplate<EObjectTreeItemForAttributeListValue, EObjectTreeItemAttributeListValueDescriptor, HBox>  {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeListValue.class.getName());
  
//  private EObjectTreeItemAttributeListValueDescriptor itemDescriptor;
  
//  private HBox graphic = null;             // will contain either valueLabel (not editing) or valueTextField/valueChoiceBox (editing).
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

  /**
   * {@inheritDoc}
   * The graphic is an {@code ImageView}.<br/>
   */
  @Override
  protected void createGraphic() {
    LOGGER.info("=>");
    
    graphic = new HBox(50);
    valueLabel = new Label();
    graphic.getChildren().add(valueLabel);
    
    LOGGER.info("<=");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setEObjectTreeItemDescriptor() {
    itemDescriptor = treeItem.getEObjectTreeItemAttributeListValueDescriptor();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextMenu createContextMenu(Object object) {
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

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public void updateContent(Object value) {
    LOGGER.info("=> item=" + (value != null ? value.toString() : "(null)"));
    
    if (eObjectTreeCell.isEditing()) {
      valueTextField.setText(buildText(value));
    } else {
      valueLabel.setText(buildText(value));
    }
    
    LOGGER.info("<=");
  }

  /** Create a new value and add it to the many reference.
   * 
   * @param before if {@code true} the new value is inserted before this value, else after this value.
   */
  protected void createAndAddValue(boolean before) {
    LOGGER.info("=>");
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) treeItem.getParent();
    
    Object object = treeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<Object> objectList = (EList<Object>) parentEObjectTreeItem.getValue();
        
    int index = objectList.indexOf(object);
    if (!before) {
      index++;
    }
    LOGGER.info("index=" + index);
    objectList.add(index, null);

    LOGGER.info("<=");
  }
  
  /**
   * Move this value up or down.
   * 
   * @param up if {@code true} the value is moved upwards, else it is moved downwards.
   */
  protected void moveValue(boolean up) {
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    Object object = eObjectTreeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<Object> objectList = (EList<Object>) parentEObjectTreeItem.getValue();
    
    int currentIndex = objectList.indexOf(object);
    int newIndex;
    if (up) {
      newIndex = currentIndex - 1;
    } else {
      newIndex = currentIndex + 1;
    }
    objectList.move(newIndex, currentIndex);
  }
  
  /**
   * Delete the value related to this tree cell.
   */
  private void deleteValue() {
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    
    Object object = eObjectTreeItem.getValue();
    @SuppressWarnings("unchecked")
    EList<Object> objectList = (EList<Object>) parentEObjectTreeItem.getValue();
    
    objectList.remove(object);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicForEditing() {
    EObjectTreeItemForAttributeList parentEObjectTreeItem = (EObjectTreeItemForAttributeList) eObjectTreeCell.getTreeItem().getParent();
    EAttribute eAttribute = parentEObjectTreeItem.getEAttribute();
    EDataType eDataType = eAttribute.getEAttributeType();
    Class<?> listElementClass = eDataType.getInstanceClass();
        
    // replace the valueLabel with a newly created TextField. The value for the text field is obtained from the item of this cell.
    graphic.getChildren().clear();
    
    if (listElementClass.isEnum()) {
      // Use a ChoiceBox for enums
      valueChoiceBox = new ChoiceBox<>();
      
      // If there is a descriptor for this enum with the display names specified, fill the items with the provided names.
      // Else fill it with the enums themselves, relying on toString for the texts to display.
      LOGGER.severe("eDataType: " + eDataType.getName());
      final EEnum eEnum = (EEnum) eDataType;
      EObjectTreeView eObjectTreeView = treeItem.getEObjectTreeView();
//      EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
      final EEnumEditorDescriptor<?> eEnumEditorDescriptorForEEnum = eObjectTreeView.getEEnumEditorDescriptorForEEnum(eEnum);
      if (eEnumEditorDescriptorForEEnum != null) {
        valueChoiceBox.getItems().addAll(eEnumEditorDescriptorForEEnum.getDisplayNames());
      } else {
        for (Object value: listElementClass.getEnumConstants()) {
          valueChoiceBox.getItems().add(value);
        }
      }
      
      LOGGER.severe("Going to select: " + valueLabel.getText());
      valueChoiceBox.getSelectionModel().select(valueLabel.getText());
      
      valueChoiceBox.onActionProperty().set((actionEvent) -> {
        Object value = valueChoiceBox.getValue();
        if (eEnumEditorDescriptorForEEnum != null) {
          value = eEnumEditorDescriptorForEEnum.getEEnumLiteralForDisplayName((String) value);
          LOGGER.severe("value: " + value.toString() + ", " + value.getClass().getName());
        }
        eObjectTreeCell.commitEdit(value);
      });
      graphic.getChildren().add(valueChoiceBox);
    } else {
      // Text
      valueTextField = new TextField();
      valueTextField.setText(buildText(treeItem.getValue()));
      valueTextField.setOnKeyReleased((keyEvent) -> {
          if ((keyEvent.getCode() == KeyCode.ENTER)) {
            eObjectTreeCell.commitEdit(valueTextField.getText());
          } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            eObjectTreeCell.cancelEdit();
          }
      });
      graphic.getChildren().add(valueTextField);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicBackToNormal() {
    graphic.getChildren().clear();
    valueTextField = null;
    valueChoiceBox = null;
    graphic.getChildren().add(valueLabel);
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void storeNewValue(Object newValue) {
    LOGGER.info("=> newValue=" + newValue.toString());
    
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    @SuppressWarnings("unchecked")
    List<Object> list = (List<Object>) parentValue;
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) treeItem;
    int index = eObjectTreeItem.getChildIndex();
    list.set(index, newValue);
    
    LOGGER.info("<=");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String buildText(Object value) {
    LOGGER.info("=>");
    
    String itemText = value != null ? value.toString() : "";
        
    LOGGER.info("<= itemText=" + itemText);
    return itemText;
  }
  
  @Override
  public String getText() {
    Object value = treeItem.getValue();
    return value != null ? value.toString() : "";
  }
}
