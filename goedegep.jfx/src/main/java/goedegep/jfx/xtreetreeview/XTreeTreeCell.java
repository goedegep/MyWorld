package goedegep.jfx.xtreetreeview;

import java.util.logging.Logger;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import javafx.scene.control.TreeCell;

public class XTreeTreeCell extends TreeCell<MutableXTreeNode> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(XTreeTreeCell.class.getName());

  
  @Override
  public void updateItem(MutableXTreeNode treeNode, boolean empty) {
    LOGGER.severe("=> item=" + (treeNode != null ? treeNode.toString() : "(null)") + ", empty=" + empty);
    
    super.updateItem(treeNode, empty);
    
    if (empty) {
      setText(null);
      setGraphic(null);      
    } else {
      setText(XTree.nodeToString(treeNode.getDataType(), treeNode.getData()));
    }
        
    LOGGER.info("<=");
  }
  
}
