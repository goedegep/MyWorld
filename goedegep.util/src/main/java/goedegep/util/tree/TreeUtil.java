package goedegep.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TreeUtil {
  private static final Logger LOGGER = Logger.getLogger(TreeUtil.class.getName());

  public static <T> TreeNode<T> findNode(TreeNode<T> startNode, T nodeContent) {
    LOGGER.info("=>");
    FindVisitor<T> findVisitor = new FindVisitor<>(nodeContent);
    
    TreeWalker.visit(startNode, findVisitor);
    
    LOGGER.info("<=");
    return findVisitor.getTreeNodeFound();
  }

  public static <T> List<T> getValuesOfAllDecendentsOfANode(TreeNode<T> aNode, boolean includeThisNodesValue) {
    LOGGER.info("=> aNode=" + aNode.getContent().toString());
    List<T> valuesOfDecendentsOfANode = new ArrayList<>();
    
    if (includeThisNodesValue) {
      valuesOfDecendentsOfANode.add(aNode.getContent());
    }
    
    if (aNode.getChildren().size() > 0) {
      TreeNode<T> firstChild = aNode.getChildren().get(0);

      TreeNodeVisitor<T> visitor = new TreeNodeVisitor<T>() {

        @Override
        public TreeNodeVisitResult preVisitChildren(TreeNode<T> treeItem) {
          // no action
          return TreeNodeVisitResult.CONTINUE;
        }

        @Override
        public TreeNodeVisitResult visitTreeItem(TreeNode<T> treeItem) {
          LOGGER.info("treeItem=" + treeItem.getContent().toString());
          valuesOfDecendentsOfANode.add(treeItem.getContent());

          return TreeNodeVisitResult.CONTINUE;
        }

        @Override
        public TreeNodeVisitResult postVisitChildren(TreeNode<T> treeItem) {
          // no action
          return TreeNodeVisitResult.CONTINUE;
        }};

        TreeWalker.visit(firstChild, visitor);
    }

    LOGGER.info("<=");
    return valuesOfDecendentsOfANode;
  }

}


class FindVisitor<T> implements TreeNodeVisitor<T> {
  private static final Logger LOGGER = Logger.getLogger(FindVisitor.class.getName());
  
  private T nodeContent;
  TreeNode<T> treeNodeFound = null;
  
  public FindVisitor(T nodeContent) {
    this.nodeContent = nodeContent;
  }

  @Override
  public TreeNodeVisitResult preVisitChildren(TreeNode<T> treeItem) {
    // No action, just continue;
    return TreeNodeVisitResult.CONTINUE;
  }

  @Override
  public TreeNodeVisitResult visitTreeItem(TreeNode<T> treeItem) {
    LOGGER.info("Visiting node: " + (treeItem.getContent() != null ? treeItem.getContent().toString() : "null"));
    if ((treeItem.getContent() != null)  &&  treeItem.getContent().equals(nodeContent)) {
      treeNodeFound = treeItem;
      LOGGER.info("Node found");
      return TreeNodeVisitResult.TERMINATE;
    } else {
      LOGGER.info("Node not yet found (continue)");
      return TreeNodeVisitResult.CONTINUE;
    }
  }

  @Override
  public TreeNodeVisitResult postVisitChildren(TreeNode<T> treeItem) {
    // No action, just continue;
    return TreeNodeVisitResult.CONTINUE;
  }

  public TreeNode<T> getTreeNodeFound() {
    return treeNodeFound;
  }
}
