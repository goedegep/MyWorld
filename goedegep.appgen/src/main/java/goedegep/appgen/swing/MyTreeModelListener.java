package goedegep.appgen.swing;

import java.util.logging.Logger;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class MyTreeModelListener implements TreeModelListener {
  private static final Logger         LOGGER = Logger.getLogger(MyTreeModelListener.class.getName());

  @Override
  public void treeNodesChanged(TreeModelEvent arg0) {
    LOGGER.info("MyTreeModelListener: treeNodesChanged");
  }

  @Override
  public void treeNodesInserted(TreeModelEvent arg0) {
    LOGGER.info("MyTreeModelListener: treeNodesInserted");
  }

  @Override
  public void treeNodesRemoved(TreeModelEvent arg0) {
    LOGGER.info("MyTreeModelListener: treeNodesRemoved");
  }

  @Override
  public void treeStructureChanged(TreeModelEvent arg0) {
    LOGGER.info("MyTreeModelListener: treeStructureChanged");
  }

}
