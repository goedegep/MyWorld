package goedegep.jfx.eobjecttable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;

/**
 * This class provides a descriptor to customize an <code>EObjectTable</code>
 *
 * @param <T>
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
   * A fixed filter to apply on the rows of the table.
   */
  private Predicate<T> filterPredicate;
  
  /**
   * Descriptors of the columns of the table.
   */
  private List<EObjectTableColumnDescriptorAbstract<T>> columnDescriptors;
    
  /**
   * Row operations.
   */
  private Map<TableRowOperation, TableRowOperationDescriptor<T>>  rowOperations;
  
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
      Map<TableRowOperation, TableRowOperationDescriptor<T>>  rowOperations
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
      Map<TableRowOperation, TableRowOperationDescriptor<T>>  rowOperations
      ) {
    this.placeHolderText = placeHolderText;
    this.comparator = comparator;
    this.columnDescriptors = columnDescriptors;
    this.rowOperations = rowOperations;
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
  public Map<TableRowOperation, TableRowOperationDescriptor<T>> getRowOperations() {
    return rowOperations;
  }

  /**
   * Set the row operations.
   * 
   * @param rowOperations the row operations
   */
  public void setRowOperations(Map<TableRowOperation, TableRowOperationDescriptor<T>> rowOperations) {
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
