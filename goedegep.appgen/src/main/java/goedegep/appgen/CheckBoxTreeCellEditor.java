package goedegep.appgen;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

/**
 * This tree cell editor is meant to be used in a ConfigurableTreeCellEditor.
 */
@SuppressWarnings("serial")
public class CheckBoxTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
  private static final Logger LOGGER = Logger.getLogger(CheckBoxTreeCellEditor.class.getName());

  private CheckBoxTreeCellRenderer checkBoxTreeCellRenderer = new CheckBoxTreeCellRenderer();
  
  JTree tree;
  DefaultTreeCellEditor owner;

  public CheckBoxTreeCellEditor(JTree tree, DefaultTreeCellEditor owner) {
    this.tree = tree;
    this.owner = owner;
  }

  public boolean isCellEditable(EventObject event) {
    LOGGER.severe("=>");
    boolean returnValue = false;
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
      if (path != null) {
        Object node = path.getLastPathComponent();
        // TODO change to getting editable from node itself, instead of its type.
        if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
          DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
          Object userObject = treeNode.getUserObject();
          returnValue = (userObject instanceof CheckBoxTreeNodeUserObjectWrapper);
        }
      }
    }
    LOGGER.severe("<= " + returnValue);
    return returnValue;
  }

  @Override
  public Component getTreeCellEditorComponent(JTree tree, Object value,
      boolean isSelected, boolean expanded, boolean leaf, int row) {
    LOGGER.severe("=>");

    Component editor = checkBoxTreeCellRenderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf,
        row, true);

    ItemListener itemListener = new ItemListener() {
      public void itemStateChanged(ItemEvent itemEvent) {
        if (stopCellEditing()) {
          fireEditingStopped();
        }
        owner.stopCellEditing();
      }
    };
    if (editor instanceof JCheckBox) {
      LOGGER.severe("Adding ItemListener");
      ((JCheckBox) editor).addItemListener(itemListener);
    }
    
    LOGGER.severe("<= ");
    return editor;
  }

  @Override
  public Object getCellEditorValue() {
    LOGGER.severe("=>");

    JCheckBox checkbox = checkBoxTreeCellRenderer.getRendererComponent();
    CheckBoxTreeNodeUserObjectWrapper checkBoxTreeNodeUserObjectWrapper = checkBoxTreeCellRenderer.getCheckBoxTreeNodeUserObjectWrapper();
    CheckBoxTreeNodeUserObjectWrapper editorValue = new CheckBoxTreeNodeUserObjectWrapper(checkBoxTreeNodeUserObjectWrapper.getUserObject(), checkbox.isSelected());
    LOGGER.severe("<= ");
    return editorValue;
  }
}
