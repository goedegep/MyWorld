package goedegep.jfx.eobjecttable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;

/**
 * This class defines some common properties.
 * <ul>
 * <li>eTypedElements<br/>
 * The attributes, references or operations of the object. If null, the cell value will be the complete object.
 * </li>
 * <li>minimumWidth<br/>
 * The minimum width of the column.
 * </li>
 * <li>isEditable<br/>
 * Indication of whether the value of this column is editable (if the table itself is editable) or not.
 * </li>
 * <li>isVisible<br/>
 * If set to false, this column will not be visible.
 * </li>
 * </ul>
 *
 * @param <T> the type of the items in the table.
 */
public class EObjectTableColumnDescriptorBase<T extends EObject> extends EObjectTableColumnDescriptorAbstract<T>  {
  
  /**
   * The attribute, reference or operation of the object. If null, the cell value will be the complete object.
   * Most of the time this will be a single ETypedElement, but sometimes an element within an element is needed.
   * This is typically the case for a group column. Example: a Person has a birthday attribute of type Birthday,
   * which has the attributes day, month and year. The column Birthday/Month will have the eTypedElements A_PACKAGE.getPerson_Birthday(), A_PACKAGE.getBirthday_Month().
   */
  private List<ETypedElement> eTypedElements;
  
  /**
   * Minimum width of the column
   */
  private Integer minimumWidth;
  
  /**
   * Indication of whether this value is editable (if the whole table itself is editable).
   */
  private boolean isEditable;
  
  /**
   * If set to false, this column will not be visible.
   */
  private boolean isVisible;

  /**
   * Constructor
   * 
   * @param eTypedElement a single attribute, reference or operation of the object. If null, the cell value will be the complete object.
   * @param id an identification of the column. This can e.g. be used to get a specific TableColumn from the Table.
   * @param columnName the text (title or name) of the column.
   * @param minimumWidth the minimum width of the column.
   * @param isEditable Indication of whether the value of this column is editable (if the table itself is editable) or not.
   * @param isVisible if set to false, this column will not be visible.
   * 
   */
  public EObjectTableColumnDescriptorBase(ETypedElement eTypedElement, String id, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    super(id, columnName);
    eTypedElements = new ArrayList<>();
    eTypedElements.add(eTypedElement);
    this.minimumWidth = minimumWidth;
    this.isEditable = isEditable;
    this.isVisible = isVisible;
  }

  /**
   * Constructor
   * 
   * @param eTypedElements a list of attributes, references or operations of the object. If null, the cell value will be the complete object.
   * @param id an identification of the column. This can e.g. be used to get a specific TableColumn from the Table.
   * @param columnName the text (title or name) of the column.
   * @param minimumWidth the minimum width of the column.
   * @param isEditable Indication of whether the value of this column is editable (if the table itself is editable) or not.
   * @param isVisible if set to false, this column will not be visible.
   * 
   */
  public EObjectTableColumnDescriptorBase(List<ETypedElement> eTypedElements, String id, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    super(id, columnName);
    this.eTypedElements= eTypedElements;
    this.minimumWidth = minimumWidth;
    this.isEditable = isEditable;
    this.isVisible = isVisible;
  }

  /**
   * Get the <code>eTypedElements</code>.
   * 
   * @return the <code>eTypedElements</code>.
   */
  public List<ETypedElement> getETypedElements() {
    return eTypedElements;
  }

  /**
   * Get the <code>minimumWidth</code>.
   * 
   * @return the <code>minimumWidth</code>.
   */
  public Integer getMinimumWidth() {
    return minimumWidth;
  }

  /**
   * Set the <code>minimumWidth</code>.
   * 
   * @param minimumWidth the minimum width of the column.
   */
  public void setMinimumWidth(Integer minimumWidth) {
    this.minimumWidth = minimumWidth;
  }

  /**
   * Check whether the column is editable.
   * 
   * @return true, if the column is editable, false otherwise.
   */
  public boolean isEditable() {
    return isEditable;
  }

  /**
   * Set whether this column is editable or not.
   * 
   * @param isEditable if true, values in the column will be editable, otherwise they're not editable.
   */
  public void setEditable(boolean isEditable) {
    this.isEditable = isEditable;
  }

  /**
   * Check whether the column will be visible.
   * 
   * @return true, if the column will be visible, false otherwise.
   */
  public boolean isVisible() {
    return isVisible;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("eTypedElement=[");
    if (eTypedElements != null) {
      boolean first = true;
      for (ETypedElement eTypedElement: eTypedElements) {
        if (first) {
          first = false;
        } else {
          buf.append(",");
        }
        buf.append(eTypedElement.getName());
      }
    } else {
      buf.append("(null");
    }
    buf.append("]");
    
    buf.append(", columnName=");
    buf.append(getColumnName() != null ? getColumnName() : "(null)");
    
    buf.append(", isEditable=");
    buf.append(isEditable);
    
    buf.append(", isVisible=");
    buf.append(isVisible);
        
    return buf.toString();
  }
}
