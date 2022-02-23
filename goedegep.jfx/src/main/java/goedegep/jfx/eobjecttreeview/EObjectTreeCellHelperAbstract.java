package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

/**
 * This class provides the common work for the implementation of the EObjectTreeCellHelper interface.
 * <p>
 * All EObject tree cell helpers shall extend this class, instead of implementing the EObjectTreeCellHelper interface.
 *
 * @param <D> the type of the EObjectTreeItemDescriptor
 */
public abstract class EObjectTreeCellHelperAbstract<D extends EObjectTreeItemDescriptor> implements EObjectTreeCellHelper {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperAbstract.class.getName());
  
  protected EObjectTreeCell eObjectTreeCell;     // the EObjectTreeCell to which this helper is attached
  protected D itemDescriptor;                    // the, item type specific, EObjectTreeItemDescriptor
  
  /**
   * Constructor.
   * 
   * @param eObjectTreeCell the EObjectTreeCell to which this helper is attached.
   */
  public EObjectTreeCellHelperAbstract(EObjectTreeCell eObjectTreeCell) {
    this.eObjectTreeCell = eObjectTreeCell;
  }

  /**
   * The implementation of updateItem() of each class which extends this class, shall first call this method.
   */
  @Override
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));

    updateItemDescriptor(eObjectTreeItemContent);
    
    LOGGER.info("<=");
  }

  /**
   * Get the right EObjectTreeItemDescriptor for a specific item content.
   * 
   * @param eObjectTreeItemContent the item content that holds a reference to the item descriptor.
   */
  @SuppressWarnings("unchecked")
  private void updateItemDescriptor(EObjectTreeItemContent eObjectTreeItemContent) {
    itemDescriptor = (D) eObjectTreeItemContent.getPresentationDescriptor();
    if (itemDescriptor != null) {
      LOGGER.info("itemDescriptor=" + itemDescriptor.toString());
    } else {
//      throw new RuntimeException("No presentation descriptor for node: " + eObjectTreeItemContent.toString());
    }
  }

}
