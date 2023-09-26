package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

/**
 * This class is an {@link EObjectTreeCellHelper} for simple attribute nodes of type boolean (EBoolean).
 *
 */
public class EObjectTreeCellHelperForAttributeBoolean extends EObjectTreeCellHelperForAttributeSimple {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeBoolean.class.getName());
  
  private CheckBox checkBox = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeBoolean(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(Object eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));
    
    super.updateItem(eObjectTreeItemContent);
    
    itemDescriptor = treeItem.getEObjectTreeItemAttributeDescriptor();
    
    ContextMenu contextMenu = createContextMenu();
    if (contextMenu != null) {
      eObjectTreeCell.setContextMenu(contextMenu);
    }
        
    createGraphic();
    
    eObjectTreeCell.setText(null);
    eObjectTreeCell.setGraphic(graphic);
    labelLabel.setText(getLabelText(eObjectTreeItemContent));
    
    if (eObjectTreeCell.isEditing()) {
      throw new RuntimeException("Call to updateItem while editing");
    } else {
      checkBox.setSelected((boolean) eObjectTreeItemContent);
      checkBox.setOnAction((e) -> {
        EObjectTreeItem parentItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem().getParent();
        EObject parentValue = (EObject) parentItem.getValue();
        parentValue.eSet(treeItem.getEAttribute(), checkBox.isSelected());
      });
    }
    
    LOGGER.info("<=");
  }
  
  @Override
  public void startEdit(EObjectTreeCell eObjectTreeCell) {
    LOGGER.info("=>");
    
    // no action
    
  }
    
  @Override
  public void commitEdit(TreeItem<Object> eObjectTreeItem, Object newValue) {
    LOGGER. info("=> newValue=" + (newValue != null ? newValue.toString() : "<null>"));
    
    // no action
    
    LOGGER.info("<=");
  }

  @Override
  public void cancelEdit() {
    LOGGER.info("=>");
    
    // no action

    LOGGER.info("<=");
  }


  private void createGraphic() {
    LOGGER.info("=>");
    
    graphic = new HBox(5);
    labelLabel = new Label();
    labelLabel.setMinWidth(150d);
    graphic.getChildren().add(labelLabel);
    
    checkBox = new CheckBox();
    graphic.getChildren().add(checkBox);
    
    LOGGER.info("<=");
  }

  @Override
  public String getText() {
    return labelLabel.getText();
  }

}
