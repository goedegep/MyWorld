package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;

/**
 * This class describes a column of an EObjectTable, which is a column for a boolean that is shown as a CheckBox.
 * <p>
 * This class doesn't have any extra attributes.
 */
public class EObjectTableColumnDescriptorCheckBox<T extends EObject> extends EObjectTableColumnDescriptorBase<T> {
  
  
  /**
   * Constructor to specify only an attribute.
   * <p>
   * The <code>columnName</code> is set to null.<br/>
   * The value is not editable.<br/>
   * The column will be visible.<br/>
   * There's no cell factory.
   * 
   * @param eStructuralFeature the <code>EStructuralFeature</code> for the attribute.
   */
  public EObjectTableColumnDescriptorCheckBox(EStructuralFeature eStructuralFeature) {
    this(eStructuralFeature, null, false, true);
  }
  
  /**
   * Constructor to specify all elements, except the minimumWidth and the cell factory.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param columnName the column name
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   */
  public EObjectTableColumnDescriptorCheckBox(ETypedElement eTypedElement,
      String columnName, boolean isEditable, boolean isVisible) {
    this(eTypedElement, columnName, null, isEditable, isVisible);
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
  public EObjectTableColumnDescriptorCheckBox(ETypedElement eTypedElement,
      String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    super(eTypedElement, null, columnName, minimumWidth, isEditable, isVisible);
  }
    

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    
    buf.append(", cellFactory=");
    
    buf.append(", stringConverter=");
    
    return buf.toString();
  }
}