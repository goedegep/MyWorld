package goedegep.appgen;

import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

import java.awt.Component;
import java.text.ParseException;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class FlexDateCellEditor extends AbstractCellEditor implements TableCellEditor {
  private final FlexDateFormat         fdf;
  
  public FlexDateCellEditor() {
    fdf = new FlexDateFormat();
  }
  
  //This is the component that will handle the editing of the cell value
  JComponent component = new JTextField();
  
  //This method is called when a cell value is edited by the user.
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int rowIndex, int vColIndex) {
    // 'value' is value contained in the cell located at (rowIndex, vColIndex)

    // Configure the component with the specified value
    if (value != null) {
    ((JTextField) component).setText(fdf.format((FlexDate) value));
    } else {
      ((JTextField) component).setText("");
    }

    // Return the configured component
    return component;
  }

  // This method is called when editing is completed.
  // It must return the new value to be stored in the cell.
  public Object getCellEditorValue() {
    String value = ((JTextField) component).getText();
    FlexDate flexDate = null;
    try {
      flexDate = fdf.parse(value);
    } catch (ParseException e) {
      // in case of an exception, return null.
    }
    return flexDate;
  }

}
