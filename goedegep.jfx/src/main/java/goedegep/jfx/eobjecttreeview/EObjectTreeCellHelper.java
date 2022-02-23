package goedegep.jfx.eobjecttreeview;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;


/**
 * This interface can be used by a TreeCell to delegate the content specific work to a helper class.
 * <p>
 * 
 */
public interface EObjectTreeCellHelper {
  /**
   * Indicates that the helper is assigned to a new tree item.
   * 
   * @param eObjectTreeItemContent the content of the tree item to which this helper is now assigned.
   */
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent);
  
  /**
   * The implementation of this method shall perform the content specific work for {@link TreeCell.startEdit}.
   * <p>
   * Helpers for an item type that cannot be edited, don't have to implement this method.
   * 
   * @param eObjectTreeCell the cell on which the editing starts.
   */
  public default void startEdit(EObjectTreeCell eObjectTreeCell) {
    throw new UnsupportedOperationException();
  }
  
  /**
   * The implementation of this method shall perform the content specific work for {@link TreeCell.commitEdit}.
   * <p>
   * Helpers for an item type that cannot be edited, don't have to implement this method.
   * 
   * @param eObjectTreeCell the cell on which the editing is to be committed.
   */
  public default void commitEdit(TreeItem<EObjectTreeItemContent> treeItem, EObjectTreeItemContent newValue) {
  }
  
  /**
   * The implementation of this method shall perform the content specific work for {@link TreeCell.cancelEdit}.
   * <p>
   * Helpers for an item type that cannot be edited, don't have to implement this method.
   * 
   * @param eObjectTreeCell the cell on which the editing is to be cancelled.
   */
  public default void cancelEdit() {
  }
  
  /**
   * This method shall return a text which represents as much as possible what is show in the node.
   * 
   * @return an, as good as possible, textual representation of the cell.
   */
  public String getText();
}
