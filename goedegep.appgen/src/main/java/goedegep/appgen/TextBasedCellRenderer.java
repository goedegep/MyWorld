package goedegep.appgen;

import java.awt.Component;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;


@SuppressWarnings("serial")
public class TextBasedCellRenderer extends DefaultTableCellRenderer {
  private static final Logger LOGGER = Logger.getLogger(TextBasedCellRenderer.class.getName());
  private static final String DEFAULT_DATE_FORMAT_STRING =  "dd-MM-yyyy";
  private static final Object[][] tableData = {
    {new Date()}
  };
  private static final String[] tableColumnNames = {"Date"};

  private Format format;
  
  public TextBasedCellRenderer(Format format) {
    this(format, null);
  }
  
  public TextBasedCellRenderer(Format format, String toolTipText) {
    this.format = format;
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
    }
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
    setText(getText(value));

    return this;
  }

  public String getText(Object value) {
    LOGGER.info("=> value = " + value);
    String text = null;
    
    if (value != null) {
      try {
        text = format.format(value);
      } catch (IllegalArgumentException e) {
        text = value.toString();
      }
    }
    
    return text;
  }
  
  public final static TextBasedCellRenderer getDateCellRendererInstance() {
    return getDateCellRendererInstance(DEFAULT_DATE_FORMAT_STRING, null);
  }
  
  public final static TextBasedCellRenderer getDateCellRendererInstance(String toolTipText) {
    return getDateCellRendererInstance(DEFAULT_DATE_FORMAT_STRING, toolTipText);
  }
  
  public final static TextBasedCellRenderer getDateCellRendererInstance(String formatString, String toolTipText) {
    TextBasedCellRenderer renderer = new TextBasedCellRenderer(new SimpleDateFormat(formatString), toolTipText);
    
    return renderer;
  }
  
  public final static TextBasedCellRenderer getFlexDateCellRendererInstance() {
    return getFlexDateCellRendererInstance((FlexDateFormat) null);
  }
  
  public final static TextBasedCellRenderer getFlexDateCellRendererInstance(FlexDateFormat format) {
    return getFlexDateCellRendererInstance(format, null);
  }
  
  public final static TextBasedCellRenderer getFlexDateCellRendererInstance(String toolTipText) {
    return getFlexDateCellRendererInstance(null, toolTipText);
  }
  
  public final static TextBasedCellRenderer getFlexDateCellRendererInstance(FlexDateFormat format, String toolTipText) {
    if (format == null) {
      format = new FlexDateFormat();
    }
    TextBasedCellRenderer renderer = new TextBasedCellRenderer(format, toolTipText);
    
    return renderer;
  }
  
  public final static TextBasedCellRenderer getFixedPointValueCellRendererInstance() {
    return getFixedPointValueCellRendererInstance(null, null);
  }
  
  public final static TextBasedCellRenderer getFixedPointValueCellRendererInstance(FixedPointValueFormat format, String toolTipText) {
    if (format == null) {
      format = new FixedPointValueFormat();
    }
    TextBasedCellRenderer renderer = new TextBasedCellRenderer(format, toolTipText);
    
    return renderer;
  }
  
  public static void main(String args[]) {
    JFrame frame = new JFrame("TextBasedCellRedererDemo");
    
    JTable table = new JTable(tableData, tableColumnNames);
    JPanel panel = new JPanel();
    panel.add(table);
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
    
  }
}
