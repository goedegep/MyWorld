package goedegep.appgen;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class NumberCellRenderer extends DefaultTableCellRenderer {
  int     minimumLength;
  boolean alwaysSign;
  boolean alwaysSignPosition;

  /**
   * Default constructor - builds a renderer that right justifies the 
   * contents of a table cell.
   */
  public NumberCellRenderer(int minimumLength, boolean alwaysSign, boolean alwaysSignPosition) {
    super();
    this.minimumLength = minimumLength;
    this.alwaysSign = alwaysSign;
    this.alwaysSignPosition = alwaysSignPosition;
//    setHorizontalAlignment(SwingConstants.RIGHT);
  }

  /**
   * Returns itself as the renderer. Supports the TableCellRenderer interface.
   *
   * @param table  the table.
   * @param value  the data to be rendered.
   * @param isSelected  a boolean that indicates whether or not the cell is 
   *                    selected.
   * @param hasFocus  a boolean that indicates whether or not the cell has 
   *                  the focus.
   * @param row  the (zero-based) row index.
   * @param column  the (zero-based) column index.
   *
   * @return the component that can render the contents of the cell.
   */
  // TODO Cleanup, use formatter for creating the text.
  public Component getTableCellRendererComponent(final JTable table, 
      final Object value, final boolean isSelected, 
      final boolean hasFocus, final int row, final int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    StringBuffer output = new StringBuffer();

    if (value != null  && value instanceof Integer) {
      long         amount = ((Integer) value).longValue();

      if (amount < 0) {
        amount = -amount;
        output.append('-');
      } else {
        if (alwaysSign) {
          output.append('+');
        } else if (alwaysSignPosition) {
          output.append(' ');
        }
      }

      String      beforeCommaString = Long.toString(amount);

      int length =
        output.length() +                // length so far
        beforeCommaString.length();                // digits after comma
      while (length++ < minimumLength) {
        output.append(' ');
      }

      output.append(beforeCommaString);

    }

    int fontSize = getFont().getSize();
    setFont(new Font("Monospaced", Font.PLAIN, fontSize));
    setText(output.toString());
    return this;
  }
}
