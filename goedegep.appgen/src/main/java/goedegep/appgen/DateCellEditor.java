package goedegep.appgen;

import java.awt.Component;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableCellEditor;

/**
 * A date cell editor, using a spinner.
 *
 */
@SuppressWarnings("serial")
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {
  private JSpinner spinner = null;
  private SpinnerDateModel spinnerDateModel = null;
  
  public DateCellEditor(String pattern) {
    spinnerDateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
    spinner = new JSpinner(spinnerDateModel);
    JSpinner.DateEditor spinnerDateEditor = new JSpinner.DateEditor(spinner, pattern);
    spinner.setEditor(spinnerDateEditor);      
  }
  
  //This method is called when a cell value is edited by the user.
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int rowIndex, int vColIndex) {
    // 'value' is value contained in the cell located at (rowIndex, vColIndex)
    Date date = (Date) value;
    if (date == null) {
      date = new Date();
    }

    spinnerDateModel.setValue(date);

    return spinner;
  }

  // This method is called when editing is completed.
  // It must return the new value to be stored in the cell.
  public Object getCellEditorValue() {    
    return spinner.getValue();
  }
}