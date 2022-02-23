package goedegep.appgen;

import java.awt.Component;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class MonthCellRenderer extends DefaultTableCellRenderer {
  private static final DateTimeFormatter MF = DateTimeFormatter.ofPattern("MM-yyyy");

  private String toolTipText;
  
  public MonthCellRenderer() {
    this(null);
  }
  
  public MonthCellRenderer(String toolTipText) {
    this.toolTipText = toolTipText;
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if (value instanceof YearMonth){
      setText(((YearMonth) value).format(MF));
      if (toolTipText != null) {
        setToolTipText(toolTipText);
      }
    }

    return this;
  }
}
