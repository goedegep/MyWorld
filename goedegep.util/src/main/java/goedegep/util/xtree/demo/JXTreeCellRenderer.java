package goedegep.util.xtree.demo;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class JXTreeCellRenderer extends DefaultTreeCellRenderer {
  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value,
      boolean sel, boolean expanded, boolean leaf, int row,
      boolean hasFocus) {
    // Determine and set the tooltip text.
    int level = -1;  // Start at -1 because of the dummy root node.
    TreeNode treeNode = (TreeNode) value;
    while (treeNode.getParent() != null) {
      treeNode = treeNode.getParent();
      level++;
    }
    setToolTipText("level: " + level);
    return super.getTreeCellRendererComponent(tree, value, sel,
        expanded, leaf, row, hasFocus);
  }
}
