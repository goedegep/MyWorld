package goedegep.util.xtree.demo;

import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;

@SuppressWarnings("serial")
public class JXTree extends JTree {

  public JXTree() {
    putClientProperty("JTree.lineStyle", "None");
    
    // Enable tool tips.
    setCellRenderer(new JXTreeCellRenderer());
    ToolTipManager.sharedInstance().registerComponent(this);
  }

  public void setRoot(MutableXTree xTree) {
    // Create tree model
    // We need a dummy root node, above the actual tree, as the JTree root node
    // can not have siblings. This root node is made invisible.
    DefaultMutableTreeNode  rootNode = new DefaultMutableTreeNode("XTree");

    addNode(rootNode, xTree, xTree.getRoot(), false, "");
    DefaultTreeModel        treeModel = new DefaultTreeModel(rootNode);

    setModel(treeModel);
    setRootVisible(false);
    setShowsRootHandles(true);
  }

  private void addNode(DefaultMutableTreeNode mutableTreeNode,
		               MutableXTree xTree,
                       MutableXTreeNode xTreeNode,
                       boolean addingSiblings,
                       String indent) {
    //System.out.println(indent + "=> addNode()");
//    if (xTreeNode == null) {
//      System.out.println("In DataTree.addNode(): dataNode = null");
//    }
    //System.out.println(indent + "Adding node");
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(xTree.nodeToString(xTreeNode));
    mutableTreeNode.add(node);
    if (xTreeNode.hasChild()) {
      //System.out.println(indent + "Adding child");
      addNode(node, xTree, xTreeNode.getFirstChild(), false, indent + "  ");
    }
    if (!addingSiblings) {
      while (xTreeNode.hasSibling()) {
        //System.out.println(indent + "Adding sibling");
        xTreeNode = xTreeNode.getNextSibling();
        addNode(mutableTreeNode, xTree, xTreeNode, true, indent);
      }
    }
    //System.out.println(indent + "<= addNode()");
  }
}