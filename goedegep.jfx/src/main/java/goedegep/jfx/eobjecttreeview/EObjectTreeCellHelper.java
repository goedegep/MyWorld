package goedegep.jfx.eobjecttreeview;

import org.eclipse.emf.ecore.EObject;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;


/**
 * This interface can be used by a TreeCell to delegate the content specific work to a helper class.
 * <p>
 * 
 */
public interface EObjectTreeCellHelper {
  /**
   * Update the cell for a new value.
   * 
   * @param eObjectTreeItemContent the content of the tree item to which this helper is now assigned. This value is never null.
   */
  public void updateItem(Object value);
  
  /**
   * The implementation of this method shall perform the content specific work for {@link TreeCell.startEdit}.
   * <p>
   * Helpers for an item type that cannot be edited, don't have to implement this method.
   * 
   * @param eObjectTreeCell the cell on which the editing starts.
   */
  public default void startEdit() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * The implementation of this method shall perform the content specific work for {@link TreeCell.commitEdit}.
   * <p>
   * Helpers for an item type that cannot be edited, don't have to implement this method.
   * 
   * @param eObjectTreeCell the cell on which the editing is to be committed.
   */
  public default void commitEdit(TreeItem<Object> treeItem, Object newValue) {
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
   * This method shall return a text which represents as much as possible what is shown in the node.
   * <p>
   * This method is a kind of a toString method, which can e.g. be used to print a textual representation of a tree view.
   * 
   * @return an, as good as possible, textual representation of the cell.
   */
  public String getText();
  
//  /**
//   * Check whether a move drop of a source EObject is possible on this cell.
//   * 
//   * @param sourceEObject the EObject that is to be moved.
//   * @return true if the <code>sourceEObject</code> can be moved to this cell, false otherwise.
//   */
//  public default boolean isDropPossible(EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {
//    System.out.println("Drop possible not supported for: " + getClass().getName());
//    return false;
//  }
  
  /**
   * Handle a move drop of a source EObject to this cell.
   * 
   * @param dragEvent the related DragEvent.
   * @param sourceEObject the EObject that is to be moved.
   * @param thisEObjectTreeItem this EObjectTreeItem.
   */
  public default void handleDragDropped(DragEvent dragEvent, EObject sourceEObject, EObjectTreeItem thisEObjectTreeItem) {
    System.out.println("Drag drop not supported for: " + getClass().getName());
  }
}
