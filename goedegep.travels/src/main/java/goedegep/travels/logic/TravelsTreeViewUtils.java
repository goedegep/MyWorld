package goedegep.travels.logic;


import goedegep.travels.model.Travel;
import javafx.scene.control.TreeItem;

/**
 * Utility class for the TravelsTreeView.
 * <p>
 * This class provides utility methods for working with the TravelsTreeView.<br/>
 * Where possible, methods should work on the Travels data model, instead of on the TravelsTreeView (see {@link TravelsUtils}).
 * However this is not always possible. For example the value of a TreeItem may be an Object, which isn't an EObject and therefore you can't use EObject.eContainer() to get the parent EObject. But if the value of a TreeItem is an EObject, then you can use EObject.eContainer() to get the parent EObject.
 */
public class TravelsTreeViewUtils {
  
  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private TravelsTreeViewUtils() {
  }
  
  /**
   * Get the first ancestor (including the {@code treeItem} itself) of a specific type of EObject.
   * 
   * @param treeItem the {@code TreeItem} for which to get the ancestor.
   * @param clazz the class of the ancestor to get.
   * @return the first ancestor (including the {@code treeItem} itself) of (including the {@code treeItem itself) which is an instance of {@code clazz}, or null if no such ancestor exists.
   */
  public static <T> TreeItem<T> getAncestorOfType(TreeItem<Object> treeItem, Class<T> clazz) {
    if (treeItem == null) {
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    
    while (treeItem != null  &&  !clazz.isInstance(treeItemObject)) {
      treeItem = treeItem.getParent();
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
      }
    }
    
    @SuppressWarnings("unchecked") // This cast is safe because we have checked that the treeItemObject is an instance of clazz.
    TreeItem<T> result = (TreeItem<T>) treeItem;

    return result;
  }
  
  /**
   * For a given treeItem, try to find the related {@ code Travel}.
   * <p>
   * If the item is a {@code Travel}, this is the related travel. Otherwise the parent relations are followed until
   * either a {@code Travel} is found, or that the top of the hierarchy is encountered.
   * If a {@code Travel} is found, that will be the related Travel, otherwise there is no related Travel.
   * 
   * @param treeItem the {@code TreeItem} for which the related travel is to be found.
   * @return the related {@ code Travel}, or null if this doesn't exist.
   */
  public static Travel getTravelForTreeItem(TreeItem<Object> treeItem) {
    TreeItem<Travel> travelTreeItem = TravelsTreeViewUtils.getAncestorOfType(treeItem, Travel.class);

    if (travelTreeItem == null) {
      return null;
    }

    return travelTreeItem.getValue();
  }

}
