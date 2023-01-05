package goedegep.jfx.xtreeview;

import java.util.logging.Logger;

import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeStringNode;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import goedegep.util.xtree.nodebased.XTreeNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
import javafx.scene.control.TreeView;

/**
 * This class provides a TreeView for a {@link MutableXTree}.
 */
public class XTreeView extends TreeView<XTreeNode> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(XTreeView.class.getName());
  
//  private MutableXTreeNode rootXTreeNode = null;
  
  /**
   * Constructor
   */
  public XTreeView() {
    setCellFactory(treeView -> new XTreeCell());
    
    // A TreeView doesn't support siblings of the root node. Therefore we use a dummy (String) root node, which isn't shown.
//    rootXTreeNode = new DefaultMutableXTreeStringNode("Dummy Root");
//    XTreeItem rootItem = new XTreeItem(rootXTreeNode);
//    XTreeItem rootItem = new XTreeItem(null);
//    rootItem.setExpanded(true);
    this.setShowRoot(false);
//    setRoot(rootItem);
  }

  /**
   * Set the data for the tree view, which is a MutableXTreeNode
   * @param xTreeNode
   */
  public void setRootNode(XTreeNode xTreeNode) {
    setRoot(new XTreeItem(xTreeNode));
//    rootXTreeNode.clearChildren();
//    rootXTreeNode.addChild(xTreeNode);
//    ((XTreeItem) getRoot()).rebuildChildren();
  }
}
