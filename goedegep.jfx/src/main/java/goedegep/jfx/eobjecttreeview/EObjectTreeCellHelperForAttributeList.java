package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import goedegep.appgen.Operation;
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
public class EObjectTreeCellHelperForAttributeList extends EObjectTreeCellHelperTemplate<EObjectTreeItemForAttributeList, EObjectTreeItemAttributeListDescriptor, ImageView> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeList.class.getName());
  
  private Image defaultImage = new Image(EObjectTreeCellHelperForObjectList.class.getResourceAsStream("List 225x225.png"), 36, 18, true, true);
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the EObjectTreeCell to which this helper is attached.
   */
  public EObjectTreeCellHelperForAttributeList(EObjectTreeCell eObjectTreeCell) {
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
    
    LOGGER.info("<=");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setEObjectTreeItemDescriptor() {
    itemDescriptor = treeItem.getEObjectTreeItemAttributeListDescriptor();
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
    
    ContextMenu contextMenu = new ContextMenu();
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      MenuItem menuItem;
      
      final Operation operation = nodeOperationDescriptor.getOperation();
      switch (operation) {
      case NEW_OBJECT:
        menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
        contextMenu.getItems().add(menuItem);
        menuItem.setOnAction((actionEvent) -> createAndAddObject());
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

  @Override
  public void updateContent(Object value) {
    LOGGER.info("=> item=" + (value != null ? value.toString() : "(null)"));
    
    // This cell type cannot be edited, so we don't have to check on isEditing()
    String cellText = buildText(value);
    eObjectTreeCell.setText(cellText);

    Image image = null;
    if (itemDescriptor.getNodeIconFunction() != null) {
      image = itemDescriptor.getNodeIconFunction().apply(value);
    }
    
    if (image == null) {
      image = defaultImage;
    }
    
    graphic.setImage(image);
    
    LOGGER.info("<=");
  }
  
  /**
   * Create a new object and add it to the list.
   */
  protected void createAndAddObject() {
    LOGGER.info("=>");
    
    LOGGER.severe("eObjectTreeItemContent=" + treeItem.getValue().toString());
    @SuppressWarnings("unchecked")
    EList<Object> eObjectList = (EList<Object>) treeItem.getValue();
    eObjectList.add(null);
    LOGGER.severe("eObjectList:" + eObjectList.toString());
        
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
  protected String buildText(Object eObjectTreeItemContent) {
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
