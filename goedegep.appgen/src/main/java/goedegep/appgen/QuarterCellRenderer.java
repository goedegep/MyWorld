package goedegep.appgen;

import goedegep.util.datetime.Quarter;
import goedegep.util.datetime.QuarterFormat;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class QuarterCellRenderer extends DefaultTableCellRenderer {
  private static final QuarterFormat QF = new QuarterFormat();

  private String toolTipText;
  
  public QuarterCellRenderer() {
    this(null);
  }
  
  public QuarterCellRenderer(String toolTipText) {
    this.toolTipText = toolTipText;
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if (value instanceof Quarter){
      setText(QF.format((Quarter) value));
      if (toolTipText != null) {
        setToolTipText(toolTipText);
      }
    }

    return this;
  }
}
