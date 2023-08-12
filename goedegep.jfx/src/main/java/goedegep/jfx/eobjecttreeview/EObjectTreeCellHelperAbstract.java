package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

/**
 * This class provides the common work for the implementation of the EObjectTreeCellHelper interface.
 * <p>
 * All EObject tree cell helpers shall extend this class, instead of implementing the EObjectTreeCellHelper interface.
 *
 * @param <D> the type of the EObjectTreeItemDescriptor
 */
public abstract class EObjectTreeCellHelperAbstract<I extends EObjectTreeItem> implements EObjectTreeCellHelper {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperAbstract.class.getName());
  
  protected EObjectTreeCell eObjectTreeCell;     // the EObjectTreeCell to which this helper is attached
  protected I treeItem;                          // the TreeItem of this cell
  
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
  @Override
  public void updateItem(Object eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));

    treeItem = (I) eObjectTreeCell.getTreeItem();
    eObjectTreeCell.setStyle(null);

    LOGGER.info("<=");
  }
  
  public I getTreeItem() {
    return treeItem;
  }
}
