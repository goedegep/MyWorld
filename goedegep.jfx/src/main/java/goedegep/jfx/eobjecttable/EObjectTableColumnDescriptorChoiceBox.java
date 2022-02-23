package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;

import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/**
 * This class describes one column of an EObjectTable.
 */
public class EObjectTableColumnDescriptorChoiceBox<T extends EObject> extends EObjectTableColumnDescriptorBase<T> {
  
  /**
   * Convert column values to and from String.
   */
  private StringConverter<? extends Object> stringConverter;
  
  /**
   * The list of items to show in the ChoiceBox.
   */
  private ObservableList<Object> items;

  
  /**
   * Constructor to specify all elements.
   * 
   * @param eTypedElement the <code>ETypedElement</code> for the attribute or operation.
   * @param columnName the column name
   * @param minimumWidth minimum width of the column
   * @param isEditable indication of whether this value is editable (if the whole table itself is editable).
   * @param isVisible if set to false, this column will not be visible.
   * @param items The list of items to show in the ChoiceBox.
   * @param stringConverter To convert column values to and from String.
   */
  public EObjectTableColumnDescriptorChoiceBox(ETypedElement eTypedElement, String columnName, Integer minimumWidth, boolean isEditable, boolean isVisible, ObservableList<Object> items, StringConverter<? extends Object> stringConverter) {
    super(eTypedElement, null, columnName, minimumWidth, isEditable, isVisible);
    this.items = items;
    this.stringConverter = stringConverter;
  }

  public StringConverter<? extends Object> getStringConverter() {
    return stringConverter;
  }
  
  public void setStringConverter(StringConverter<? extends Object> stringConverter) {
    this.stringConverter = stringConverter;
  }

  public ObservableList<Object> getItems() {
    return items;
  }

  public void setItems(ObservableList<Object> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    
    buf.append(", items=");
    buf.append(items != null ? items.toString() : "not set");
    
    buf.append(", stringConverter=");
    buf.append(stringConverter != null ? stringConverter.getClass().getName() : "not set");
    
    return buf.toString();
  }
}