package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import goedegep.appgen.TableRowOperation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a tree cell helper for the 'list' node of an attribute of type many.
 * <p>
 * The representation consists of a 'list icon' followed by a label text provided by the item descriptor.<br/>
 * At the list level no editing is possible, so the related methods are not overridden by this class.
 */
public class EObjectTreeCellHelperForAttributeList extends EObjectTreeCellHelperAbstract<EObjectTreeItemForAttributeList> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeList.class.getName());
  
  private EObjectTreeItemAttributeListDescriptor itemDescriptor;
  private final Node listIcon = new ImageView(new Image(EObjectTreeCellHelperForObjectList.class.getResourceAsStream("List 225x225.png"), 36, 18, true, true));
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the EObjectTreeCell to which this helper is attached.
   */
  public EObjectTreeCellHelperForAttributeList(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(Object eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));

    super.updateItem(eObjectTreeItemContent);
    
    itemDescriptor = getTreeItem().getEObjectTreeItemAttributeListDescriptor();
    
    ContextMenu contextMenu = createContextMenu();
    eObjectTreeCell.setContextMenu(contextMenu);
    
    // This cell type cannot be edited, so we don't have to check on isEditing()
    eObjectTreeCell.setText(getText(eObjectTreeItemContent));
    eObjectTreeCell.setGraphic(listIcon);
    
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
    
    ContextMenu contextMenu = new ContextMenu();
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      MenuItem menuItem;
      
      final TableRowOperation operation = nodeOperationDescriptor.getOperation();
      switch (operation) {
      case NEW_OBJECT:
        EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
        // This operation is only there if there are no children yet.
        // Otherwise you add items before or after a specific item in the list.
        if (eObjectTreeItem.getChildren().size() == 0) {
          menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
          contextMenu.getItems().add(menuItem);
          menuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
              createAndAddObject();
            }
          });
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
    
    if (contextMenu.getItems().size() > 0) {
      LOGGER.info("<= contextMenu");
      return contextMenu;
    } else {
      LOGGER.info("<= (null)");
      return null;
    }
  }
  
  protected void createAndAddObject() {
    LOGGER.info("=>");
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    LOGGER.severe("eObjectTreeItem=" + eObjectTreeItem.toString());
    LOGGER.severe("eObjectTreeItemContent=" + eObjectTreeItem.getValue().toString());
    @SuppressWarnings("unchecked")
    EList<Object> eObjectList = (EList<Object>) eObjectTreeItem.getValue();
    eObjectList.add(null);
    LOGGER.severe("eObjectList:" + eObjectList.toString());
    eObjectTreeItem.rebuildChildren();
        
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
  private String getText(Object eObjectTreeItemContent) {
    LOGGER.info("=> eObjectTreeItemContent=" + eObjectTreeItemContent.toString());
    String labelText = itemDescriptor.getLabelText() + ":";
        
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  @Override
  public String getText() {
    return eObjectTreeCell.getText();
  }
}
