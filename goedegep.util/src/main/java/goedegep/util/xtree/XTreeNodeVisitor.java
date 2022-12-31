package goedegep.util.xtree;

import java.nio.file.FileVisitResult;

/**
 * An implementation of this interface is to be passed as a parameter to {@link XTree#traverse(XTreeNodeVisitor)}.<br/>
 * This interface defines the methods which are called at specific situations encountered by the {@code traverse} method.
 */
public interface XTreeNodeVisitor {

  /**
   * Invoked before the first child of a node is handled.
   *
   * <p> If this method returns {@link FileVisitResult#CONTINUE CONTINUE},
   * then the children will be visited. If this method returns {@link
   * FileVisitResult#SKIP_SUBTREE SKIP_SUBTREE} or {@link
   * FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS} then the
   * children (and any descendants) will not be visited.
   *
   * @param   treeItem
   *          Is this needed?
   *
   * @return  the visit result
   */
  XTreeNodeVisitResult preVisitChildren();

  /**
   * Invoked for any node.
   *
   * @param   treeItem
   *          The tree node
   *
   * @return  the visit result
   */
  XTreeNodeVisitResult visitTreeItem(XNodeDataType dataType, Object value);


  /**
   * Invoked after the last child of a node is handled. This method is also invoked when iteration
   * over the children completes prematurely (by a {@link #visitFile visitFile}
   * method returning {@link FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS},
   * or an I/O error when iterating over the directory).
   *
   * @param   treeItem
   *          Is this needed?
   *
   * @return  the visit result
   */
  XTreeNodeVisitResult postVisitChildren();

}
