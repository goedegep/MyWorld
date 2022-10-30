package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * This class describes a custom column of an EObjectTable.
 * <p>
 * One attribute is added to the EObjectTableColumnDescriptorBase; a cellFactory.
 */
public class EObjectTableColumnDescriptorCustom<T extends EObject> extends EObjectTableColumnDescriptorBase<T> {
  
  /**
   * An optional cell factory, to provide the value for this column.
   */
  private Callback<TableColumn<T, Object>, TableCell<T, Object>> cellFactory;
  
  /**
   * Constructor to specify only the feature.
   * 
   * @param eStructuralFeature the feature.
   */
  public EObjectTableColumnDescriptorCustom(EStructuralFeature eStructuralFeature) {
    this(eStructuralFeature, null, null, true, true, null);
  }
    
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   * @param cellFactory an optional cell factory for this column.
   */
  public EObjectTableColumnDescriptorCustom(ETypedElement eTypedElement,
      String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible, Callback<TableColumn<T, Object>, TableCell<T, Object>> cellFactory) {
    super(eTypedElement, null, columnName, minimumWidth, isEditable, isVisible);
    this.cellFactory = cellFactory;
  }
  
  /**
   * Create a descriptor for the specified feature.
   * 
   * @param <U> the type of the items in the table.
   * @param eStructuralFeature the feature that is shown in the column
   * @return a descriptor for the specified feature.
   */
  public static <U extends EObject> EObjectTableColumnDescriptorCustom<U> createForFeature(EStructuralFeature eStructuralFeature) {
    return new EObjectTableColumnDescriptorCustom<U>(eStructuralFeature);
  }
  
  /**
   * Set the column name.
   * 
   * @param columnName the column name.
   * @return this
   */
  public EObjectTableColumnDescriptorCustom<T> setColumnName(String columnName) {
    super.setColumnNameAbstract(columnName);
    return this;
  }

  /**
   * Get the <code>cellFactory</code>.
   * 
   * @return the <code>cellFactory</code>.
   */
  public Callback<TableColumn<T, Object>, TableCell<T, Object>> getCellFactory() {
    return cellFactory;
  }
  
  /**
   * Set the <code>cellFactory</code>.
   * 
   * @param cellFactory the new value for the <code>cellFactory</code>.
   */
  public EObjectTableColumnDescriptorCustom<T>  setCellFactory(Callback<TableColumn<T, Object>, TableCell<T, Object>> cellFactory) {
    this.cellFactory = cellFactory;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    
    buf.append(", cellFactory=");
    buf.append(cellFactory != null ? "set" : "not set");
    
    return buf.toString();
  }
}