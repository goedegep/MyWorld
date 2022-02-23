package goedegep.util.tree;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * This class provides methods to walk through a tree, consisting of nodes of type {@link TreeNode}.
 * <p>
 * Normally all nodes of the tree are visited, following the Depth First principle.<br/>
 * Two ways are supported to walk through a tree:
 * <ul>
 * <li>
 * Iterator<br/>
 * This class implements the {@link Iterator} interface interface, which means you can use it as follows:</br>
 * <pre>
 *     TreeWalker<MyNodeContentType> tw = new TreeWalker<>(rootNode);
 *     while (tw.hasNext()) {
 *       TreeNode<MyNodeContentType> treeNode = tw.next();
 *     }
 * </pre>
 * </li>
 * <li>
 * Visitor pattern.<br/>
 * This is supported by the static method {@link #visit}. This method is called with the root node of a tree and a {@link TreeNodeVisitor}
 * as parameters. See the method visit() for details.
 * </li>
 * </ul>
 *
 * @param <T> The data type contained in the tree nodes.
 */
public class TreeWalker<T> implements Iterator<TreeNode<T>> {
  private static final Logger LOGGER = Logger.getLogger(TreeWalker.class.getName());
  
  private TreeNode<T> lastNode = null;
  private TreeNode<T> currentNode = null;
  private boolean beforeFirstChild;
  private int afterLastChildCount;
  private boolean terminate = false;


  /**
   * Initialize the walker.
   *
   * @param tree
   *            The tree to walk
   */
  public TreeWalker(TreeNode<T> rootNode) {
    super();
    
    if (rootNode.hasNextSibling()) {
      lastNode = rootNode.lastSibling();
    } else {
      lastNode = rootNode;
    }
    currentNode = rootNode;
    beforeFirstChild = false;
    afterLastChildCount = 0;
    LOGGER.info("<=> rootNode=" + rootNode.toString());
  }

  /**
   * @return True if has unserved items.
   */
  public boolean hasNext() {
    return !terminate  &&  (currentNode != null);
  }

  /**
   * @return The next tree item in depth walk order. The parent is returned
   *         before any of its children.
   */
  public TreeNode<T> next() {
    if (!hasNext()) {
      throw new IllegalStateException("");
    }
    TreeNode<T> next = currentNode;
    walk();
    return next;
  }
  
  public void setTerminate(boolean terminate) {
    this.terminate = terminate;
  }

  private void walk() {
    LOGGER.info("=> currentNode=" + currentNode.toString());
    beforeFirstChild = false;
    afterLastChildCount = 0;
    
    /*
     * If there are children, set beforeFirstChild and go to the first child.
     * Else
     *     If there is a sibling, go there
     *     Else
     *       Move up until there is a parent which has a sibling.
     *       If there is a parent, or parents parent, etc, which has a sibling, set afterLastChild and go to this node
     *           If the parent has a sibling, go there
     *           Else go to the parent of the parent 
     *       Else
     *           Done
     */
    if (currentNode.hasChildren()) {
      beforeFirstChild = true;
      currentNode = currentNode.getChildren().get(0);
      LOGGER.info("going to first child: currentItem=" + currentNode.toString());
    } else {
      if (currentNode.hasNextSibling()) {
        currentNode = currentNode.nextSibling();
        LOGGER.info("going to next sibling: currentItem=" + currentNode.toString());
      } else {
        do {
          if (currentNode.equals(lastNode)) {
            currentNode = null;
          } else {
            currentNode = currentNode.getParent();
            afterLastChildCount++;
            LOGGER.info("going to parent: currentNode=" + (currentNode != null ? currentNode.toString() : "(null)"));
            if (currentNode.equals(lastNode)) {
              currentNode = null;
            }
          }
          if ((currentNode != null)  &&  currentNode.hasNextSibling()) {
            currentNode = currentNode.nextSibling();
            LOGGER.info("going to parents next sibling: currentNode=" + currentNode.toString());
            break;
          }
        } while (currentNode != null);
      }
    }
    LOGGER.info("<=");
  }

  /**
   * Walks over the tree and calls the consumer for each tree item.
   *
   * @param tree
   *            The tree to visit.
   * @param visitor
   *            The visitor.
   */
  public static <T> void visit(TreeNode<T> rootNode, TreeNodeVisitor<T> visitor) {
    TreeWalker<T> tw = new TreeWalker<>(rootNode);
    TreeNodeVisitResult result;
    while (tw.hasNext()) {
      boolean beforeFirstChild = tw.beforeFirstChild;
      TreeNode<T> treeNode = tw.next();
      
      if (beforeFirstChild) {
        result = visitor.preVisitChildren(treeNode);
      }
      
      result = visitor.visitTreeItem(treeNode);
      switch (result) {
      case CONTINUE:
        // no action
        break;
        
      case SKIP_SIBLINGS:
        // TODO
        break;
        
      case SKIP_SUBTREE:
        // TODO
        break;
        
      case TERMINATE:
        tw.setTerminate(true);
        break;
      }
      
      while (tw.afterLastChildCount > 0) {
        result = visitor.postVisitChildren(null);
        tw.afterLastChildCount--;
      }
    }
  }

  /**
   * Walks over the tree and calls the consumer for each item value.
   *
   * @param tree
   *            The tree to visit.
   * @param visitor
   *            The visitor.
   */
  public static <T> void visitNodes(TreeNode<T> tree, Consumer<T> visitor) {
    TreeWalker<T> tw = new TreeWalker<>(tree);
    while (tw.hasNext()) {
      visitor.accept(tw.next().getContent());
    }
  }

}