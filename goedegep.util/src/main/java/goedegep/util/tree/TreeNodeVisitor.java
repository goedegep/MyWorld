package goedegep.util.tree;

import java.nio.file.FileVisitResult;

/**
 * A visitor of items in a tree. An implementation of this interface is provided to the
 * {@link TreeViewWalker} methods to visit each TreeItem in a TreeView.
 *
 */

public interface TreeNodeVisitor<T> {

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
    TreeNodeVisitResult preVisitChildren(TreeNode<T> treeItem);

    /**
     * Invoked for any node.
     *
     * @param   treeItem
     *          The tree node
     *
     * @return  the visit result
     */
    TreeNodeVisitResult visitTreeItem(TreeNode<T> treeItem);


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
    TreeNodeVisitResult postVisitChildren(TreeNode<T> treeItem);
}
