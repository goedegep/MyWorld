package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

/**
 * This class is the template part for EObjectTreeCellHelper implementations.
 * 
 * @param <I> the specific {@code EObjectTreeItem} of the {@code eObjectTreeCell}.
 * @param <D> the specific {@code EObjectTreeItemAttributeDescriptor} of the {@code eObjectTreeCell}.
 */
public abstract class EObjectTreeCellHelperTemplate<I extends EObjectTreeItem, D extends EObjectTreeItemDescriptor, G extends Node> implements EObjectTreeCellHelper {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperTemplate.class.getName());
  
  /**
   * The {@code EObjectTreeCell} to which this helper is attached.
   */
  protected EObjectTreeCell eObjectTreeCell = null;
  
  /**
   * The {@code EObjectTreeItem} of the {@code eObjectTreeCell}.
   */
  protected I treeItem = null;
  
  /**
   * The {@code EObjectTreeItemAttributeDescriptor} of the {@code eObjectTreeCell}.
   */
  protected D itemDescriptor = null;
  
  /**
   * The optional graphic of the cell
   */
  protected G graphic = null;
  
  /**
   * Indication of whether initialization has taken place or not.
   */
  private boolean initialized = false;
  
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the EObjectTreeCell to which this helper is attached.
   */
  public EObjectTreeCellHelperTemplate(EObjectTreeCell eObjectTreeCell) {
    this.eObjectTreeCell = eObjectTreeCell;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void updateItem(Object value) {
    LOGGER.info("=> item=" + (value != null ? value.toString() : "(null)"));
    
    init();
    
    Node cellGraphic = eObjectTreeCell.getGraphic();
    
    if (cellGraphic == null) {
      if (graphic != null) {
        eObjectTreeCell.setGraphic(graphic);
      }      
    }
    
    treeItem = (I) eObjectTreeCell.getTreeItem();
    eObjectTreeCell.setStyle(null);
    
    setEObjectTreeItemDescriptor();
    
    ContextMenu contextMenu = createContextMenu(value);
    eObjectTreeCell.setContextMenu(contextMenu); // also set when null to clear any previous value
    
    updateContent(value);

    LOGGER.info("<=");
  }
  
  /**
   * Perform initialization if it hasn't been done yet.
   */
  private void init() {
    if (!initialized) {
      createGraphic();
      if (graphic != null) {
        eObjectTreeCell.setGraphic(graphic);
      }      
      
      initialized = true;
    }
  }

  /**
   * Create the optional graphic for the normal (non edit mode).
   */
  protected void createGraphic() {
    // no action, so no graphic.
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
   * Update the content of the cell.
   * 
   * @param value the new value.
   */
  protected abstract void updateContent(Object value);
  
  /**
   * Create the text from the cell value.
   * <p>
   * This default implementation just returns null.
   * 
   * @param value the cell value.
   * @return the cell text for {@code value}.
   */
  protected String buildText(Object value) {
    return null;
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void startEdit() {
    LOGGER.info("=>");
    
    changeGraphicForEditing();
  }
  
  /**
   * Change the graphic to edit mode.
   * <p>
   * When the change is accepted, there must be a call to {@code eObjectTreeCell.commitEdit(newValue)}.
   * If editing is cancelled, there must be a call to {@code eObjectTreeCell.cancelEdit()}.
   */
  protected void changeGraphicForEditing() {
    
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  public void commitEdit(TreeItem<Object> eObjectTreeItem, Object newValue) {
    LOGGER.info("=> newValue=" + (newValue != null ? newValue.toString() : "<null>"));
    
    changeGraphicBackToNormal();
    storeNewValue(newValue);
    
    LOGGER.info("<=");
  }
  
  /**
   * Change the graphic back to the non editing state (same as it is after {@code createGraphic()}).
   */
  protected void changeGraphicBackToNormal() {
    
  }
  
  /**
   * Store the new value in the data model.
   * 
   * @param newValue the new value.
   */
  protected void storeNewValue(Object newValue) {
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cancelEdit() {
    LOGGER.info("=>");
    
    changeGraphicBackToNormal();

    LOGGER.info("<=");
  }
  
}
