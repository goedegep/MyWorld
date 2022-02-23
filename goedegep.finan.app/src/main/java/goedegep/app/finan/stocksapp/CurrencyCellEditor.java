package goedegep.app.finan.stocksapp;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.money.PgCurrencyPlusStatus;

import java.awt.Component;
import java.text.ParseException;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class CurrencyCellEditor extends AbstractCellEditor implements TableCellEditor {
  private final PgCurrencyFormat         cf; 
  
  public CurrencyCellEditor() {
    cf = new PgCurrencyFormat();
  }
  
  //This is the component that will handle the editing of the cell value
  JComponent component = new JTextField();
  
  //This method is called when a cell value is edited by the user.
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int rowIndex, int vColIndex) {
    // 'value' is value contained in the cell located at (rowIndex, vColIndex)

    // Configure the component with the specified value
    if (value != null) {
      if (value instanceof PgCurrency) {
        ((JTextField) component).setText(cf.format((PgCurrency) value));
      } else {
        ((JTextField) component).setText(cf.format(((PgCurrencyPlusStatus) value).getMoney()));
      }
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
    PgCurrency currency = null;
    try {
      currency = cf.parse(value);
    } catch (ParseException e) {
      // in case of an exception, return null.
    }
    return currency;
  }
}