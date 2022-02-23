package goedegep.appgen;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

public class PgTableHeaderRenderer extends JPanel implements TableCellRenderer {
  private static final long          serialVersionUID = 1L;
  
  protected int   _verticalAlignment;
  protected int   _horizontalAlignment;
  protected float _alignmentX;

  // These attributes may be explicitly set
  // They are defaulted to the colors and attributes
  // of the table header
  protected Color _foreground;
  protected Color _background;

  // These attributes have fixed defaults
  protected Border _headerBorder = UIManager.getBorder("TableHeader.cellBorder");
  protected Font   _font = UIManager.getFont("TableHeader.font");

  public PgTableHeaderRenderer(int horizontalAlignment, int verticalAlignment) {
    _horizontalAlignment = horizontalAlignment;
    _verticalAlignment = verticalAlignment;
    switch (_horizontalAlignment) {
    case SwingConstants.LEFT:
      _alignmentX = (float)0.0;
      break;

    case SwingConstants.CENTER:
      _alignmentX = (float)0.5;
      break;

    case SwingConstants.RIGHT:
      _alignmentX = (float)1.0;
      break;

    default:
      throw new IllegalArgumentException("Illegal horizontal alignment value");
    }
    setBorder(_headerBorder);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(true);

    _background = null;
  }

  public void setForeground(Color foreground) {
    _foreground = foreground;
    super.setForeground(_foreground);
  }

  public void setBackground(Color background) {
    _background = background;
    super.setBackground(_background);
  }

  public void setFont(Font font) {
    _font = font;
  }

  // Implementation of TableCellRenderer interface
  public Component getTableCellRendererComponent(JTable table,
                                                 Object value, boolean isSelected,
                                                 boolean hasFocus, int row, int column) {
    removeAll();
    invalidate();

    if (value == null) {
      // Do nothing if no value
      return this;
    }

    // Set the foreground and background colors
    // from the table header if they are not set
    if (table != null) {
      JTableHeader header = table.getTableHeader();
      if (header != null) {
        if (_foreground == null) {
          super.setForeground(header.getForeground());
        }

        if (_background == null) {
          super.setBackground(header.getBackground());
        }
      }
    }

    if (_verticalAlignment != SwingConstants.TOP) {
      add(Box.createVerticalGlue());
    }

    Object[] values;
    int length;
    if (value instanceof Object[]) {
      // Input is an array - use it
      values = (Object[])value;
    } else {
      // Not an array - turn it into one
      values = new Object[1];
      values[0] = value;
    }
    length = values.length;

    // Configure each row of the header using
    // a separate JLabel. If a given row is
    // a JComponent, add it directly..
    for (int i = 0; i < length; i++) {
      Object thisRow = values[i];

      if (thisRow instanceof JComponent) {
        add((JComponent)thisRow);
      } else {
        JLabel l = new JLabel();
        setValue(l, thisRow, i);
        add(l);
      }
    }

    if (_verticalAlignment != SwingConstants.BOTTOM) {
      add(Box.createVerticalGlue());
    }

    return this;
  }

  // Configures a label for one line of the header.
  // This can be overridden by derived classes
  protected void setValue(JLabel l, Object value, int lineNumber) {
    if (value != null && value instanceof Icon) {
      l.setIcon((Icon)value);
    } else {
      l.setText(value == null ? "" : value.toString());
    }
    l.setHorizontalAlignment(_horizontalAlignment);
    l.setAlignmentX(_alignmentX);
    l.setOpaque(false);
    l.setForeground(_foreground);
    l.setFont(_font);
  }
}