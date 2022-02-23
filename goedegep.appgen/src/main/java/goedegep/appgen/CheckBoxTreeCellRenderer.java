package goedegep.appgen;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;


/**
 * This tree cell renderer is meant to be used in a ConfigurableTreeCellRenderer.
 */
public class CheckBoxTreeCellRenderer implements TreeCellRenderer {
  private JCheckBox rendererComponent = new JCheckBox();
  private CheckBoxTreeNodeUserObjectWrapper checkBoxTreeNodeUserObjectWrapper = null;
  
  public CheckBoxTreeCellRenderer() {
    Font fontValue = UIManager.getFont("Tree.font");
    if (fontValue != null) {
      rendererComponent.setFont(fontValue);
    }
  }
  
  public JCheckBox getRendererComponent() {
    return rendererComponent;
  }
  
  public JCheckBox getLeafRenderer() {
    return rendererComponent;
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
      boolean expanded, boolean leaf, int row, boolean hasFocus) {
    Component returnValue;
    
    String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
    rendererComponent.setText(stringValue);
    rendererComponent.setSelected(false);
    rendererComponent.setEnabled(tree.isEnabled());
    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
      Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
      if (userObject instanceof CheckBoxTreeNodeUserObjectWrapper) {
        checkBoxTreeNodeUserObjectWrapper = (CheckBoxTreeNodeUserObjectWrapper) userObject;
        rendererComponent.setText(checkBoxTreeNodeUserObjectWrapper.getUserObject().toString());
        rendererComponent.setSelected(checkBoxTreeNodeUserObjectWrapper.isSelected());
      }
    }
    returnValue = rendererComponent;
    return returnValue;
  }

  public CheckBoxTreeNodeUserObjectWrapper getCheckBoxTreeNodeUserObjectWrapper() {
    return checkBoxTreeNodeUserObjectWrapper;
  }
  
}