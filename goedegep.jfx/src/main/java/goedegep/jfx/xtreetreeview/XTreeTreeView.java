package goedegep.jfx.xtreetreeview;

import java.util.logging.Logger;

import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeStringNode;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
import javafx.scene.control.TreeView;

/**
 * This class provides a TreeView for a {@link MutableXTree}.
 */
public class XTreeTreeView extends TreeView<MutableXTreeNode> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(XTreeTreeView.class.getName());
  
  /**
   * Constructor
   */
  public XTreeTreeView() {
    setCellFactory(treeView -> new XTreeTreeCell());
    
    // A TreeView doesn't support siblings of the root node. Therefore we use a dummy (String) root node, which isn't shown.
    MutableXTreeNode rootXTreeNode = new DefaultMutableXTreeStringNode("Dummy Root");
    XTreeTreeItem rootItem = new XTreeTreeItem(rootXTreeNode);
    rootItem.setExpanded(true);
    this.setShowRoot(false);
    setRoot(rootItem);
  }

  /**
   * Set the data for the tree view, which is a MutableXTreeNode
   * @param xTreeNode
   */
  public void setRootNode(MutableXTreeNode xTreeNode) {
    getRoot().getValue().clearChildren();
    getRoot().getValue().addChild((DefaultMutableXTreeNode) xTreeNode);
    ((XTreeTreeItem) getRoot()).rebuildChildren();
  }
}
