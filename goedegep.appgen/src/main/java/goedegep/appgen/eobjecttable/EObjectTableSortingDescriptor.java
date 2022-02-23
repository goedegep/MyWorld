package goedegep.appgen.eobjecttable;

import java.util.List;

import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;

public class EObjectTableSortingDescriptor {
  private boolean                 sortingEnabled;   // enable sorting on the table.
  private List<RowSorter.SortKey> sortKeys;         // perform actual sorting on specified columns.
  
  
  public EObjectTableSortingDescriptor(boolean sortingEnabled, List<SortKey> sortKeys) {
    this.sortingEnabled = sortingEnabled;
    this.sortKeys = sortKeys;
  }


  public boolean isSortingEnabled() {
    return sortingEnabled;
  }


  public List<RowSorter.SortKey> getSortKeys() {
    return sortKeys;
  }

}
