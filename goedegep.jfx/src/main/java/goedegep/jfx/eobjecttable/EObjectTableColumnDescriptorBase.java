package goedegep.jfx.eobjecttable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;

public class EObjectTableColumnDescriptorBase<T extends EObject> extends EObjectTableColumnDescriptorAbstract<T>  {
  
  /**
   * The attribute, reference or operation of the object. If null, the cell value will be the complete object.
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

  public EObjectTableColumnDescriptorBase(ETypedElement eTypedElement, String id, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    super(id, columnName);
    eTypedElements = new ArrayList<>();
    eTypedElements.add(eTypedElement);
    this.minimumWidth = minimumWidth;
    this.isEditable = isEditable;
    this.isVisible = isVisible;
  }

  public EObjectTableColumnDescriptorBase(List<ETypedElement> eTypedElements, String id, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    super(id, columnName);
    this.eTypedElements= eTypedElements;
    this.minimumWidth = minimumWidth;
    this.isEditable = isEditable;
    this.isVisible = isVisible;
  }

  /**
   * Get the <code>eTypedElement</code>.
   * 
   * @return the <code>eTypedElement</code>.
   */
  public List<ETypedElement> getETypedElements() {
    return eTypedElements;
  }

  public Integer getMinimumWidth() {
    return minimumWidth;
  }

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
