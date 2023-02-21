package goedegep.util.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * This utility class provides support methods for working with collections.
 */
public class ListUtil {
  
  /**
   * Private constructor as this is a utility class.
   */
  private ListUtil() {
    
  }
  
  /**
   * Get all items which are in one list, but not in another list.
   * 
   * @param <T> The type of the items in the list.
   * @param listToCheck The list to check
   * @param listToCheckAgainst The list to check against
   * @return A list which contains all items which are in <code>listToCheckAgainst</code> but not in <code>listToCheck</code>.
   */
  public static <T> List<T> listItemsNotInList(List<T> listToCheck, List<T> listToCheckAgainst) {
    List<T> listItemsNotInList = new ArrayList<>();
    
    for (T item: listToCheck) {
      if (!listToCheckAgainst.contains(item)) {
        listItemsNotInList.add(item);
      }
    }
    
    return listItemsNotInList;
  }
}
