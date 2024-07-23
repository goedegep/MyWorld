package goedegep.appgen.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import goedegep.appgen.MessageDialogType;
import goedegep.appgen.TextBasedCellRenderer;

/**
 * There are 2 ways to create an abstract table:
 * - Use the constructor with required arguments. This constructor directly leads to an initialized table.
 * - Use the basic constructor, set the other required properties and then call the init function.
 */
@SuppressWarnings("serial")
public abstract class AppGenAbstractTable extends JScrollPane {
  private static final Logger LOGGER = Logger.getLogger(AppGenAbstractTable.class.getName());
  
  private ComponentFactory           componentFactory;
  private int                        width;
  private int                        height;
  private AppFrame                     owner;
  private JTable                     table;
  
  public AppGenAbstractTable(AppFrame owner, int width, int height) {
    this.owner = owner;
    this.width = width;
    this.height = height;
    if (owner != null) {
      componentFactory = owner.getTheComponentFactory();
    } else {
      componentFactory = DefaultCustomization.getInstance().getComponentFactory();
    }
    
    init();
  }
  
  public AppGenAbstractTable(AppFrame owner) {
    this.owner = owner;
    componentFactory = owner.getTheComponentFactory();
  }
    
  protected Customization getCustomization() {
    return owner.getCustomization();
  }
    
  public void setDimensions(int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  public void setDimensions(Dimension dimension) {
    this.width = dimension.width;
    this.height = dimension.height;
  }
  
  public void init() {
    table = componentFactory.createTable(null, width, height);
    
    setOpaque(true);    
  }
    
  protected Window getOwner() {
    return owner;
  }

  public JTable getTable() {
    return table;
  }
    
  /*
   * This method picks good column sizes.
   * If all column heads are wider than the column's cells'
   * contents, then you can just use column.sizeWidthToFit().
   */
  protected void initColumnSizes(JTable table) {
    AppGenAbstractTableModel model = (AppGenAbstractTableModel) table.getModel();
    TableColumn column = null;
    Component comp = null;
    int headerWidth = 0;
    int cellWidth = 0;
    Object[] longValues = model.getLongValues();
    TableCellRenderer headerRenderer =
      table.getTableHeader().getDefaultRenderer();

    for (int i = 0; i < model.getColumnCount(); i++) {
      LOGGER.info("Column = " + i);
      column = table.getColumnModel().getColumn(i);

      comp = headerRenderer.getTableCellRendererComponent(
          null, column.getHeaderValue(),
          false, false, 0, 0);
      headerWidth = comp.getPreferredSize().width;

      Class<?> columnClass = model.getColumnClass(i);
      if (columnClass == null) {
        throw new RuntimeException("No class for column " + i);
      }
      TableCellRenderer cellRenderer = table.getDefaultRenderer(columnClass);
      if (cellRenderer == null) {
        throw new RuntimeException("No cellRenderer for column: " + i + ", class: " + columnClass);
      }
      Object longValue = longValues[i];
      if (longValue != null) {
        Component rendererComponent = cellRenderer.getTableCellRendererComponent(table, longValue, false, false, 0, i);
        if (rendererComponent == null) {
          throw new RuntimeException("No rendererComponent for column " + i);
        }
        //      comp = table.getDefaultRenderer(model.getColumnClass(i)).
        //      getTableCellRendererComponent(
        //          table, longValues[i],
        //          false, false, 0, i);
        //      cellWidth = comp.getPreferredSize().width;
        LOGGER.info("rendererComponent = " + rendererComponent.getClass().getName());
        cellWidth = rendererComponent.getPreferredSize().width;
        LOGGER.info("longValue = " + longValue);
        LOGGER.info("cellWidth = " + cellWidth);
      } else {
        cellWidth = headerWidth;
      }

      column.setPreferredWidth(Math.max(headerWidth, cellWidth));
    }
  }
  
  protected void installPopupMenu(final JTable table) {
    LOGGER.info("=>");
    // Add a mouse listener for a context menu.
    MouseListener tableMouseListener = new MouseAdapter() {
      @SuppressWarnings("deprecation")  // All my Swing code is legacy code that will not be maintained
      public void mouseClicked(MouseEvent e) {
        LOGGER.fine("=>");
        if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
          int x = e.getX();
          int y = e.getY();
          int clickedRow = table.rowAtPoint(new Point(x, y));
          table.changeSelection(clickedRow, 0, false, false);
          JPopupMenu popup = createTablePopupMenu(x, y);
          if (popup != null) {
            popup.show(e.getComponent(), x, y);
          }
        }
      }
    };
    table.addMouseListener(tableMouseListener);
  }
  
  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    return null;
  }
  
  public void showMessageDialog(MessageDialogType messageDialogType, String message) {
    owner.showMessageDialog(messageDialogType, message);
  }
  
  /*
   * This find works on the complete row.
   * The search operates on the displayed rows.
   * If there is a row selected, the search starts at the next row of the selected row.
   * Otherwise, the search starts at the beginning of the table (display row 0).
   */
  public Object findObjectForward(String searchText, boolean forceStartFromBeginning) {
    LOGGER.info("=>");
    JTable table = getTable();
    
    // Prepend the 'text' with "(?i)" to search with 'ignore case'.
    Pattern pattern = Pattern.compile("(?i)" + searchText);
    
    int searchStartRow = 0;
    
    if (!forceStartFromBeginning) {
      int[] selectedViewRows = table.getSelectedRows();

      if (selectedViewRows.length > 0) {
        searchStartRow = selectedViewRows[0] + 1;
      }
    }
    LOGGER.info("searchStartRow = " + searchStartRow);
    
    for (int rowIndex = searchStartRow; rowIndex < table.getRowCount(); rowIndex++) {
      int modelIndex = table.convertRowIndexToModel(rowIndex);
      LOGGER.info("rowIndex = " + rowIndex + ", modelIndex = " + modelIndex);
      if (checkObject(modelIndex, pattern)) {
        findObject(rowIndex);
      }
    }
    
    LOGGER.info("<= null");
    return null;
  }
  
  /*
   * This find works on the complete row.
   * The search operates on the displayed rows.
   * If there is a row selected, the search starts at the row before the selected row.
   * Otherwise, the search starts at the end of the table.
   */
  public Object findObjectBackward(String text) {
    JTable table = getTable();
    Pattern pattern = Pattern.compile(text);
    
    int searchStartRow = table.getRowCount() - 1;
    int[] selectedViewRows = table.getSelectedRows();
    
    if (selectedViewRows.length > 0) {
      searchStartRow = selectedViewRows[0] + -1;
    }
    LOGGER.severe("searchStartRow = " + searchStartRow);
    
    for (int rowIndex = searchStartRow; rowIndex >= 0; rowIndex--) {
      int modelIndex = table.convertRowIndexToModel(rowIndex);
      LOGGER.severe("rowIndex = " + rowIndex + ", modelIndex = " + modelIndex);
      if (checkObject(modelIndex, pattern)) {
        findObject(rowIndex);
      }
    }
    
    return null;
  }
  
  /**
   * Check whether an object contains a specific pattern.
   * @param eObject the object to be checked.
   * @param pattern the pattern to be checked on.
   * @param eStructuralFeature the name of a specific attribute that should contain the pattern. If null, all EAttributes are checked.
   *                       If searchCompleteObject is set, this parameter is ignored.
   * @param searchCompleteObject search on all attributes of the object, also 
   * @return true if the pattern is found, false otherwise.
   */
  private boolean checkObject(int rowIndex, Pattern pattern) {
    LOGGER.info("=>");
    JTable table = getTable();
    AppGenAbstractTableModel tableModel = (AppGenAbstractTableModel) table.getModel();

    for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
      LOGGER.info("In loop for column: " + columnIndex);

      Object object = tableModel.getValueAt(rowIndex, columnIndex);
      if (object != null) {
        if (object instanceof String) {
          String string = (String) object;
          LOGGER.info("type is String");
          Matcher matcher = pattern.matcher(string);
          if (matcher.find()) {
            LOGGER.info("<= Match found on: " + string);
            return true;
          }
        } else if (object instanceof Boolean) {
          Boolean booleanValue = (Boolean) object;
          LOGGER.info("type is Boolean");
          Matcher matcher = pattern.matcher(booleanValue.toString());
          if (matcher.find()) {
            LOGGER.info("<= Match found on: " + booleanValue.toString());
            return true;
          }
        } else {
          TableCellRenderer renderer = table.getDefaultRenderer(object.getClass());
          if (renderer instanceof TextBasedCellRenderer) {
            TextBasedCellRenderer tbRenderer = (TextBasedCellRenderer) renderer;
            String text = tbRenderer.getText(object);
            LOGGER.info("TextBasedCellRenderer for this type, text = " + text);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
              LOGGER.info("<= match found on: " + text);
              return true;
            }
          } else {
            LOGGER.severe("NO TextBasedCellRenderer for this type");
          }
        }
      } else {
        LOGGER.info("null object (so no match)");
      }
    }

    LOGGER.info("<= No match found ");
    return false;
  }
  
  public void findObject(int viewRow) {
      JTable table = getTable();
      table.setRowSelectionInterval(viewRow, viewRow);
      Rectangle viewPortBounds = getViewportBorderBounds();

      Rectangle bounds = table.getCellRect(viewRow, 0, true);
      if (bounds.getMaxY() < viewPortBounds.getMinY()  ||
          bounds.getMinY() > viewPortBounds.getMaxY()) {
        getViewport().setViewPosition(bounds.getLocation());
      }
  }

  public void setFilterExpression(String regex) {
    @SuppressWarnings("unchecked")
    TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) getTable().getRowSorter();
    if (regex.isEmpty()) {
      rowSorter.setRowFilter(null);
    } else {
      // If current expression doesn't parse, don't update.
      RowFilter<TableModel, Object> rowFilter = null;
      try {
        rowFilter = RowFilter.regexFilter("(?i)" + regex); // Prepend the 'text' with "(?i)" to search with 'ignore case'.
        rowSorter.setRowFilter(rowFilter);    
      } catch (java.util.regex.PatternSyntaxException e) {
        e.printStackTrace();
      }
    }
  }
}