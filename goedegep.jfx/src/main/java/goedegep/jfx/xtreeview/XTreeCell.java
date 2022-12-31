package goedegep.jfx.xtreeview;

import java.util.logging.Logger;

import goedegep.util.xtree.XTreeNode;
import javafx.scene.control.TreeCell;

public class XTreeCell extends TreeCell<XTreeNode> {
  private static final Logger LOGGER = Logger.getLogger(XTreeCell.class.getName());

  
  @Override
  public void updateItem(XTreeNode treeNode, boolean empty) {
    LOGGER.info("=> item=" + (treeNode != null ? treeNode.toString() : "(null)") + ", empty=" + empty);
    
    super.updateItem(treeNode, empty);
    
    if (empty) {
      setText(null);
      setGraphic(null);      
    } else {
      setText(treeNode.toString());
      
//      setText(XTree.nodeToString(treeNode.getDataType(), treeNode.getData()));
    }
        
    LOGGER.info("<=");
  }
  
}
