package goedegep.appgen;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.Format;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

/**
 * A tree cell editor, which can be configured.
 * Specific editors can be added for specific classes of the user object of a DefaultMutableTreeNode.
 *
 */
public class ConfigurableTreeCellEditor extends DefaultTreeCellEditor {
  private static final Logger         LOGGER = Logger.getLogger(ConfigurableTreeCellEditor.class.getName());

  private Map<Class<?>, Format> classSpecificFormat = new HashMap<Class<?>, Format>();
  private Map<Class<?>, TreeCellEditor> classSpecificEditors = new HashMap<Class<?>, TreeCellEditor>();
  
  private TreeCellEditor currentEditor = null;
  
  public ConfigurableTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
    super(tree, renderer);
  }

  public void addClassSpecificFormat(Class<?> cellClass, Format format) {
    LOGGER.info("Setting class specific format for cellClass: " + cellClass.getName());
    classSpecificFormat.put(cellClass, format);
  }

  public void addClassSpecificCellEditor(Class<?> cellClass, TreeCellEditor cellEditor) {
    LOGGER.info("Setting class specific editor for cellClass: " + cellClass.getName());
    if (cellEditor instanceof DefaultTreeCellEditor) {
      ((DefaultTreeCellRenderer) cellEditor).setLeafIcon(null);
    }
    classSpecificEditors.put(cellClass, cellEditor);
  }

  @Override
  public Component getTreeCellEditorComponent(JTree tree, Object value,
      boolean isSelected, boolean expanded, boolean leaf, int row) {
    LOGGER.info("=> class=" + value.getClass().getName() + ", value=" + value.toString());

    currentEditor = null;
    TreeCellEditor editor;
    Format format;
    Object userObject = null;
    if (value instanceof DefaultMutableTreeNode) {
      userObject = ((DefaultMutableTreeNode) value).getUserObject();
      LOGGER.fine("class of userObject=" + userObject.getClass().getName());
    }

    // Check for class specific editor
    if (userObject != null) {
      LOGGER.fine("Checking for class specific editor.");
      editor = classSpecificEditors.get(userObject.getClass());
      if (editor != null) {
        LOGGER.info("<= Class specific editor: " + editor);
        currentEditor = editor;
        return editor.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
      } else {
        LOGGER.info("No Class specific editor");
      }
    }

    // Check for class specific format
    if (value != null) {
      LOGGER.info("Checking for class specific format.");
      format = classSpecificFormat.get(value.getClass());
      if (format != null) {
        LOGGER.info("<= Class specific format: " + format);
        return super.getTreeCellEditorComponent(tree, format.format(value), isSelected, expanded, leaf, row);
      } else {
        LOGGER.info("No class specific format");
      }
    }

    LOGGER.info("<= default to 'DefaultTreeCellRenderer'");
    return  super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
  }

  @Override
  public boolean isCellEditable(EventObject event) {
    LOGGER.info("=>");

    if (event != null) {
      if (event.getSource() instanceof JTree) {
        if (event instanceof MouseEvent) {
          TreePath path = tree.getPathForLocation(
              ((MouseEvent)event).getX(),
              ((MouseEvent)event).getY());
          if (path!=null) {
            Object value = path.getLastPathComponent();

            LOGGER.info("class of value=" + value.getClass().getName() + ", value=" + value.toString());

            // Check for class specific editor
            if (value != null) {
              LOGGER.severe("Checking for class specific editor.");
              if (value instanceof DefaultMutableTreeNode) {
                Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
                if (userObject != null) {
                  LOGGER.severe("class of userObject=" + userObject.getClass().getName() + ", userObject=" + userObject.toString());
                  TreeCellEditor editor = classSpecificEditors.get(userObject.getClass());
                  if (editor != null) {
                    LOGGER.severe("<= Class specific editor: " + editor);
                    return editor.isCellEditable(event);
                  } else {
                    LOGGER.severe("No Class specific editor");
                  }
                }
              }
            }
          }
        }
      }
    }

    LOGGER.info("<= default to 'DefaultTreeCellRenderer'");

    return super.isCellEditable(event);
  }
  
  @Override
  public boolean stopCellEditing() {
    LOGGER.fine("<=>");
    return super.stopCellEditing();
  }

  @Override
  public Object getCellEditorValue() {
    LOGGER.severe("=>");
    
    if (currentEditor != null) {
      LOGGER.severe("<= currentEditor.getCellEditorValue()");
      return currentEditor.getCellEditorValue();
    } else {
      LOGGER.severe("<= super.getCellEditorValue()");
      return super.getCellEditorValue();
    }
  }
}
