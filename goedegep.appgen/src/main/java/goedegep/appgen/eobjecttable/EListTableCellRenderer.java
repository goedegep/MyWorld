package goedegep.appgen.eobjecttable;

import java.awt.Component;

import javax.swing.JTable;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;

import goedegep.appgen.TextBasedCellRenderer;

@SuppressWarnings("serial")
public class EListTableCellRenderer extends TextBasedCellRenderer {

  private String toolTipText;
  
  public EListTableCellRenderer() {
    this(null);
  }
  
  public EListTableCellRenderer(String toolTipText) {
    super(null);
    this.toolTipText = toolTipText;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if (value instanceof EObjectWithInverseResolvingEList<?>){
      setText(getText(value));
      if (toolTipText != null) {
        setToolTipText(toolTipText);
      }
    }

    return this;
  }

  public String getText(Object value) {
    if (value instanceof EObjectWithInverseResolvingEList<?>) {
      StringBuilder buf = new StringBuilder();
      EObjectWithInverseResolvingEList<?> e = (EObjectWithInverseResolvingEList<?>) value;
      boolean first = true;
      for (Object object: e) {
        if (first) {
          first = false;
        } else {
          buf.append(", ");
        }
        buf.append(object.toString());
      }
      return buf.toString();
    }
    
    return null;
  }
}
