package goedegep.util.xtree.nodebased;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;

/**
 * This interface adds the possibility to walk through an XTree via its nodes.
 */
public interface NodeBasedXTree extends XTree {
  
  /**
   * Get the root node of a tree.
   * <p>
   * 
   * @return The root node of the tree.
   */
  public XTreeNode getRoot();

  
  /**
   * Walk through a part of the tree, depth first, calling the specified XTreeNodeVisitor for each node.
   * 
   * @param startNode node to start walking. If null walking starts at the root.
   * @param visitChildren if true children of each node are visited, else children are not visited.
   * @param visitStartNodeSiblings if true the siblings of the starting node are visited, else they are not visited.
   * @param xTreeNodeVisitor the <b>XTreeNodeVisitor</b> called for each node.
   */
  public void traverse(XTreeNode startNode, boolean visitChildren, boolean visitStartNodeSiblings, XTreeNodeVisitor xTreeNodeVisitor);
  
  /*
   * Comparing
   */
  
  /**
   * Compare a part of this tree to a part of another tree.
   * 
   * @param thisStartNode starting node in this tree
   * @param toTree the other tree
   * @param toStartNode starting node in the other tree.
   * @param includeSiblings if true also siblings of the startNodes are taken into account, else only
   *        the children of the startNodes are taken into account.
   * @return true if the sub trees are identical, false otherwise.
   */
  public boolean compareSubtrees(XTreeNode thisStartNode, XTree toTree, XTreeNode toStartNode, boolean includeSiblings);
  
  /*
   * Searching
   */

  /**
   * Find a child of a node of type TAG and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return The first child that fits the criteria, or null if the node has no child that fits the criteria.
   */
  public XTreeNode findChild(XTreeNode node, XTreeTag value);

  /**
   * Find a child of a node of type BOOLEAN and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return The first child that fits the criteria, or null if the node has no child that fits the criteria.
   */
  public XTreeNode findChild(XTreeNode node, boolean value);

  /**
   * Find a child of a node of type INTEGER and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return The first child that fits the criteria, or null if the node has no child that fits the criteria.
   */
  public XTreeNode findChild(XTreeNode node, int value);

  /**
   * Find a child of a node of type STRING and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return The first child that fits the criteria, or null if the node has no child that fits the criteria.
   */
  public XTreeNode findChild(XTreeNode node, String value);

  /**
   * Find a child of a node of type BLOB and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return The first child that fits the criteria, or null if the node has no child that fits the criteria.
   */
  public XTreeNode findChild(XTreeNode node, byte[] value);

  
  /**
   * Find a (great)(grand)child of a node of type TAG which has a specific value.
   * 
   * This function searches according a depth first traversal.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:
   *      @retval NULL if the node has no (great)(grand)child that fits the criteria.
   *      @retval The first (great)(grand)child that fits the criteria.
   *              If multiple (great)(grand)children fit the criteria then
   *              this function returns a child rather then any sibling.
   */
  public XTreeNode findNode(XTreeNode node, short value);

  /**
   * Find a (great)(grand)child of a node of type BOOLEAN which has a specific value.
   * 
   * This function searches according a depth first traversal.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:
   *      @retval NULL if the node has no (great)(grand)child that fits the criteria.
   *      @retval The first (great)(grand)child that fits the criteria.
   *              If multiple (great)(grand)children fit the criteria then
   *              this function returns a child rather then any sibling.
   */
  public XTreeNode findNode(XTreeNode node, boolean value);

  /**
   * Find a (great)(grand)child of a node of type INTEGER which has a specific value.
   * 
   * This function searches according a depth first traversal.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:
   *      @retval NULL if the node has no (great)(grand)child that fits the criteria.
   *      @retval The first (great)(grand)child that fits the criteria.
   *              If multiple (great)(grand)children fit the criteria then
   *              this function returns a child rather then any sibling.
   */
  public XTreeNode findNode(XTreeNode node, int value);

  /**
   * Find a (great)(grand)child of a node of type STRING which has a specific value.
   * 
   * This function searches according a depth first traversal.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:
   *      @retval NULL if the node has no (great)(grand)child that fits the criteria.
   *      @retval The first (great)(grand)child that fits the criteria.
   *              If multiple (great)(grand)children fit the criteria then
   *              this function returns a child rather then any sibling.
   */
  public XTreeNode findNode(XTreeNode node, String value);

  /**
   * Find a (great)(grand)child of a node of type BLOB which has a specific value.
   * 
   * This function searches according a depth first traversal.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:
   *      @retval NULL if the node has no (great)(grand)child that fits the criteria.
   *      @retval The first (great)(grand)child that fits the criteria.
   *              If multiple (great)(grand)children fit the criteria then
   *              this function returns a child rather then any sibling.
   */
  public XTreeNode findNode(XTreeNode node, byte[] value);
  
}
