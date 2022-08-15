package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;
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
  public void setCellFactory(Callback<TableColumn<T, Object>, TableCell<T, Object>> cellFactory) {
    this.cellFactory = cellFactory;
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