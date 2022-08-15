package goedegep.jfx.eobjecttable;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;

import javafx.util.StringConverter;


/**
 * This class describes a basic column of an EObjectTable, which is a column for a type that is shown as text in a table cell.
 * <p>
 * One attribute is added to the EObjectTableColumnDescriptorBase; a StringConverter for the object type of the column.
 */
public class EObjectTableColumnDescriptorBasic<T extends EObject> extends EObjectTableColumnDescriptorBase<T> {
  
  /**
   * Convert column values to and from String.
   */
  private StringConverter<? extends Object> stringConverter;

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
  public EObjectTableColumnDescriptorBasic(EStructuralFeature eStructuralFeature) {
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
  public EObjectTableColumnDescriptorBasic(ETypedElement eTypedElement,
      String columnName, boolean isEditable, boolean isVisible) {
    this(eTypedElement, null, columnName, null, isEditable, isVisible, null);
  }
    
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   */
  public EObjectTableColumnDescriptorBasic(ETypedElement eTypedElement,
      String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    this(eTypedElement, null, columnName, minimumWidth, isEditable, isVisible, null);
  }
  
  /**
   * Constructor to specify all elements, except the minimumWidth and the cell factory.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param id the column identification. If null, the column id will be set to the column name.
   * @param columnName the column name
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   */
  public EObjectTableColumnDescriptorBasic(ETypedElement eTypedElement,
      String id, String columnName, boolean isEditable, boolean isVisible) {
    this(eTypedElement, id, columnName, null, isEditable, isVisible, null);
  }
  
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   * @param stringConverter used convert the value to/from String
   */
  public EObjectTableColumnDescriptorBasic(ETypedElement eTypedElement,
      String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible, StringConverter<? extends Object> stringConverter) {
    this(eTypedElement, null, columnName, minimumWidth, isEditable, isVisible, stringConverter);
  }
  
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param id the column identification. If null, the column id will be set to the column name.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   * @param stringConverter used convert the value to/from String
   */
  public EObjectTableColumnDescriptorBasic(ETypedElement eTypedElement,
      String id, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible, StringConverter<? extends Object> stringConverter) {
    super(eTypedElement, id, columnName, minimumWidth, isEditable, isVisible);
    this.stringConverter = stringConverter;
  }
  
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElements the list of <code>ETypedElement</code>s for the attributes or operations.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   */
  public EObjectTableColumnDescriptorBasic(List<ETypedElement> eTypedElements,
      String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible) {
    super(eTypedElements, null, columnName, minimumWidth, isEditable, isVisible);
  }
  
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElements the list of <code>ETypedElement</code>s for the attributes or operations.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   * @param stringConverter used convert the value to/from String
   */
  public EObjectTableColumnDescriptorBasic(List<ETypedElement> eTypedElements,
      String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible, StringConverter<? extends Object> stringConverter) {
    this(eTypedElements, null, columnName, minimumWidth, isEditable, isVisible, stringConverter);
  }
  
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElements the list of <code>ETypedElement</code>s for the attributes or operations.
   * @param id the column identification. If null, the column id will be set to the column name.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   * @param stringConverter used convert the value to/from String
   */
  public EObjectTableColumnDescriptorBasic(List<ETypedElement> eTypedElements,
      String id, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible, StringConverter<? extends Object> stringConverter) {
    super(eTypedElements, id, columnName, minimumWidth, isEditable, isVisible);
    this.stringConverter = stringConverter;
  }

  public StringConverter<? extends Object> getStringConverter() {
    return stringConverter;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    
    buf.append(", cellFactory=");
    
    buf.append(", stringConverter=");
    buf.append(stringConverter != null ? stringConverter.getClass().getName() : "not set");
    
    return buf.toString();
  }
}