package goedegep.jfx.eobjecttable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;

/**
 * This class provides a descriptor to customize an <code>EObjectTable</code>
 * <p>
 * The following properties of the table can be specified:
 * <ul>
 * <li>placeHolderText<br/>
 *     The text shown by the <b>placeholder</b> property of the TableView.
 * </li>
 * <li>comparator<br/>
 *     The comparator property of the TableView.
 * </li>
 * <li>filterPredicate<br/>
 *     A filter to apply on the rows of the table.
 * </li>
 * <li>columnDescriptors<br/>
 *     A specification for each column of the table.
 * </li>
 * <li>rowOperations<br/>
 *     Operations that can be performed on the rows of the table. These operations are available via a context menu.
 * </li>
 * <li>subClassDescriptors<br/>
 *     Descriptors for the sub-classes of {@code T}. Sub-classes may have extra attributes to show/edit.
 * </li>
 * </ul>
 *
 * @param <T> The type of the items listed in the table.
 */
public class EObjectTableDescriptor<T extends EObject> {
  /**
   * Placeholder text; a text shown in the center of the table if there are no items in the table.
   */
  private String placeHolderText;
  
  /**
   * A <code>Comparator</code> for sorting the rows of the table.
   */
  private Comparator<T> comparator;
  
  /**
   * A filter to apply on the rows of the table.
   */
  private Predicate<T> filterPredicate;
  
  /**
   * Descriptors of the columns of the table.
   */
  private List<EObjectTableColumnDescriptorAbstract<T>> columnDescriptors;
    
  /**
   * Row operations.
   */
  private Map<Operation, TableRowOperationDescriptor<T>>  rowOperations;
  
  /**
   * ?
   */
  private Map<EClass, SubClassDescriptor<? extends EObject>> subClassDescriptors;
  
  /**
   * Constructor for creating an empty descriptor.
   */
  public EObjectTableDescriptor() {
    this(null, null, null, null);
  }
  
  /**
   * Constructor for creating a descriptor where the column descriptors and row operations are specified.
   * 
   * @param columnDescriptors descriptors for the columns of the table.
   * @param rowOperations row operations
   */
  public EObjectTableDescriptor(
      List<EObjectTableColumnDescriptorAbstract<T>> columnDescriptors,
      Map<Operation, TableRowOperationDescriptor<T>>  rowOperations
      ) {
    this(null, null, columnDescriptors, rowOperations);
  }
  
  /**
   * Constructor for creating a descriptor where the comparator, the column descriptors and row operations are specified.
   * 
   * @param placeHolderText the text shown in the center of the table if there are no items in the table
   * @param comparator
   * @param columnDescriptors
   * @param rowOperations
   */
  public EObjectTableDescriptor(
      String placeHolderText, Comparator<T> comparator,
      List<EObjectTableColumnDescriptorAbstract<T>> columnDescriptors,
      Map<Operation, TableRowOperationDescriptor<T>>  rowOperations
      ) {
    this.placeHolderText = placeHolderText;
    this.comparator = comparator;
    this.columnDescriptors = columnDescriptors;
    if (this.columnDescriptors == null) {
      this.columnDescriptors = new ArrayList<>();
    }
    this.rowOperations = rowOperations;
    if (this.rowOperations == null) {
      this.rowOperations = new HashMap<>();
    }
  }

  /**
   * Get the placeholder text
   * 
   * @return the placeholder text.
   */
  public String getPlaceHolderText() {
    return placeHolderText;
  }

  /**
   * Get the comparator for sorting.
   * 
   * @return the comparator for sorting.
   */
  public Comparator<T> getComparator() {
    return comparator;
  }

  /**
   * Set the comparator for sorting.
   * 
   * @param comparator the comparator for sorting.
   */
  public void setComparator(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  /**
   * Get the column descriptors.
   * 
   * @return the column descriptors.
   */
  public List<EObjectTableColumnDescriptorAbstract<T>> getColumnDescriptors() {
    return columnDescriptors;
  }

  /**
   * Set the column descriptors.
   * 
   * @param columnDescriptors the column descriptors.
   */
  public void setColumnDescriptors(List<EObjectTableColumnDescriptorAbstract<T>> columnDescriptors) {
    this.columnDescriptors = columnDescriptors;
  }

  /**
   * Get the row operations.
   * 
   * @return the row operations.
   */
  public Map<Operation, TableRowOperationDescriptor<T>> getRowOperations() {
    return rowOperations;
  }

  /**
   * Set the row operations.
   * 
   * @param rowOperations the row operations
   */
  public void setRowOperations(Map<Operation, TableRowOperationDescriptor<T>> rowOperations) {
    this.rowOperations = rowOperations;
  }

  
  public Map<EClass, SubClassDescriptor<? extends EObject>> getSubClassDescriptors() {
    return subClassDescriptors;
  }

  public void setSubClassDescriptors(Map<EClass, SubClassDescriptor<? extends EObject>> subClassDescriptors) {
    this.subClassDescriptors = subClassDescriptors;
  }

  public Predicate<T> getFilterPredicate() {
    return filterPredicate;
  }

  public void setFilterPredicate(Predicate<T> filterPredicate) {
    this.filterPredicate = filterPredicate;
  }

}
