package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import javafx.scene.control.ContextMenu;

/**
 * This class provides the common work for the implementation of the EObjectTreeCellHelper interface.
 * <p>
 * All EObject tree cell helpers shall extend this class, instead of implementing the EObjectTreeCellHelper interface.
 *
 * @param <D> the type of the EObjectTreeItemDescriptor
 */
public abstract class EObjectTreeCellHelperAbstract<I extends EObjectTreeItem> implements EObjectTreeCellHelper {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperAbstract.class.getName());
  
  /**
   * The {@code EObjectTreeCell} to which this helper is attached.
   */
  protected EObjectTreeCell eObjectTreeCell;
  
  /**
   * The {@code EObjectTreeItem} of the {@code eObjectTreeCell}.
   */
  protected I treeItem;
  
  // TODO move itemDescriptor to here, type D
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the EObjectTreeCell to which this helper is attached.
   */
  @SuppressWarnings("unchecked")
  public EObjectTreeCellHelperAbstract(EObjectTreeCell eObjectTreeCell) {
    this.eObjectTreeCell = eObjectTreeCell;
    treeItem = (I) eObjectTreeCell.getTreeItem();
  }

  /**
   * The implementation of updateItem() of each class which extends this class, shall first call this method.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void updateItem(Object eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));

    treeItem = (I) eObjectTreeCell.getTreeItem();
    eObjectTreeCell.setStyle(null);

    LOGGER.info("<=");
  }
  
  /**
   * New implementation for changing to Template pattern.
   * TODO: AttributeBoolean, AttributeList, AttributeListValue, AttributeSimple, Object, ObjectList
   * 
   * @param eObjectTreeItemContent The {@code EObjectTreeItem} now represented by the cell.
   */
  @SuppressWarnings("unchecked")
  public void updateItemNew(Object object) {
    LOGGER.info("=> item=" + (object != null ? object.toString() : "(null)"));
    
    treeItem = (I) eObjectTreeCell.getTreeItem();
    eObjectTreeCell.setStyle(null);
    
    setEObjectTreeItemDescriptor();
    
    ContextMenu contextMenu = createContextMenu(object);
    eObjectTreeCell.setContextMenu(contextMenu); // also set when null to clear any previous value
    
    String cellText = buildText(object);
    eObjectTreeCell.setText(cellText);


    LOGGER.info("<=");
  }
  
  /**
   * Set {@code itemDescriptor} to the tree item descriptor from {@code treeItem}.
   */
  protected abstract void setEObjectTreeItemDescriptor();
  
  /**
   * Create a context menu for this cell.
   * <p>
   * Override this method if your cell shall have a context menu.
   * 
   * @param object the user value of the cell.
   * @return a context menu derived from the node operation descriptors, or null if no node operation descriptors are specified.
   */
  protected ContextMenu createContextMenu(Object object) {
    return null;
  }
  
  /**
   * Create the cell text from the cell value.
   * 
   * @param value the cell value.
   * @return the cell text for {@code value}.
   */
  protected abstract String buildText(Object value);
}
