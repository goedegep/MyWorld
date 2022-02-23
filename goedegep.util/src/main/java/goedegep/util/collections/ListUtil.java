package goedegep.util.collections;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
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
