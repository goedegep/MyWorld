package goedegep.appgen;

import javax.swing.*;
import javax.swing.table.*;

// A holder for data and an associated icon
@SuppressWarnings("serial")
public class TextWithIconCellRenderer extends DefaultTableCellRenderer {
  protected void setValue(Object value) {
    if (value == null) {
      setText("");
      setIcon(null);
    } else if (value instanceof DataWithIcon) {
      if (value != null) {
        DataWithIcon d = (DataWithIcon) value;
        Object dataValue = d.getData();

        setText(dataValue == null ? "" : dataValue.toString());
        setIcon(d.getIcon());
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
      }
    } else {
      super.setValue(value);
    }
  }
}