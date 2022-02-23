package goedegep.util.xtree.mutable;

import java.io.OutputStream;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;

public interface MutableXTree extends XTree {
  /*
   * Walking through a tree.
   */

  /**
   * Get the root node of a tree.
   * 
   * @return The root node of the tree.
   */
  public MutableXTreeNode getRoot();
  
  /**
   * Walk through a part of the tree, depth first, calling the specified XTreeNodeVisitor for each node.
   * 
   * @param startNode node to start walking. If null walking starts at the root.
   * @param visitChildren if true children of each node are visited, else children are not visited.
   * @param visitStartNodeSiblings if true the siblings of the starting node are visited, else they are not visited.
   * @param xTreeNodeVisitor the <b>XTreeNodeVisitor</b> called for each node.
   */
  public void traverse(MutableXTreeNode startNode,
      boolean visitChildren, boolean visitStartNodeSiblings, XTreeNodeVisitor xTreeNodeVisitor);

  /*
   * Operations on (parts of a) tree.
   */

  /**
   * Get the number of children of a node.
   *
   * @param node
   *      A node. If null, the number of Top Level Nodes is returned.
   */
  public int getNumberOfChildren(MutableXTreeNode node);

  /**
   * Get the number of siblings following a node.
   *
   * @param node
   *      A node
   */
  public int getNumberOfRemainingSiblings(MutableXTreeNode node);
  
  /**
   * Get the child index of a node.
   * 
   * @param child
   * @return
   */
  public int getChildIndex(MutableXTreeNode child);
  
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
  public boolean compareSubtrees(MutableXTreeNode thisStartNode,
      MutableXTree toTree, MutableXTreeNode toStartNode,
      boolean includeSiblings);
  
  
  /**
   * Remove a subtree.
   * This function removes a specific node and, if applicable, all its children
   * from a tree.
   *
   * @param node
   *      The node to be removed, which may not be null.
   */
  public void removeNode(MutableXTreeNode node);

    
  /**
   * Filter a tree.
   * 
   * @param  filter
   *      The filter.
   * 
   * @return The filtered tree. 
   */
  public MutableXTree filterTree(MutableXTree filter);

  
  /*
   * Searching.
   */

  /**
   * Check whether two nodes have the same content.
   * 
   * @param referenceNode - First node for the compare compare.
   * @param secondTree - Tree that contains the second node for the compare.
   * @param secondNode - Second node for the compare.
   * @return
   *     true if the nodes have equal contents.<br>
   *     false otherwise.
   */
  public boolean compareNodes(MutableXTreeNode referenceNode,
      MutableXTree secondTree, MutableXTreeNode secondNode);

  /**
   * Find a child of a node of type TAG and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:<br>
   *      null if the node has no child that fits the criteria.<br>
   *      The first child that fits the criteria.
   */
  public MutableXTreeNode findChild(MutableXTreeNode node, XTreeTag value);

  /**
   * Find a child of a node of type BOOLEAN and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:<br>
   *      null if the node has no child that fits the criteria.<br>
   *      The first child that fits the criteria.
   */
  public MutableXTreeNode findChild(MutableXTreeNode node, boolean value);

  /**
   * Find a child of a node of type INTEGER and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:<br>
   *      null if the node has no child that fits the criteria.<br>
   *      The first child that fits the criteria.
   */
  public MutableXTreeNode findChild(MutableXTreeNode node, int value);

  /**
   * Find a child of a node of type STRING and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:<br>
   *      null if the node has no child that fits the criteria.<br>
   *      The first child that fits the criteria.
   */
  public MutableXTreeNode findChild(MutableXTreeNode node, String value);

  /**
   * Find a child of a node of type BLOB and which has a specific value.
   *
   * @param node
   *      A node, within the tree.
   * @param value
   *      The requested value.
   *
   * @return one of the following:<br>
   *      null if the node has no child that fits the criteria.<br>
   *      The first child that fits the criteria.
   */
  public MutableXTreeNode findChild(MutableXTreeNode node, byte[] value);

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
  public MutableXTreeNode findNode(MutableXTreeNode node, short value);

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
  public MutableXTreeNode findNode(MutableXTreeNode node, boolean value);

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
  public MutableXTreeNode findNode(MutableXTreeNode node, int value);

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
  public MutableXTreeNode findNode(MutableXTreeNode node, String value);

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
  public MutableXTreeNode findNode(MutableXTreeNode node, byte[] value);


  /*
   * Debug support.
   */
  /**
   * Print the contents of a tree to an OutputStream (for debugging purposes only).
   * The content of a single node can be printed by setting both printChildren
   * and printSiblings to FALSE.
   *
   * @param startNode
   *      The node from where printing should start. If this value is null,
   *      printing starts at the root node.
   * @param indent
   *      Initial number of spaces at the start of a line. The indentation increases
   *      for lower levels.
   * @param printChildren
   *      Indicates whether children of the current node have to be printed.
   * @param printSiblings
   *      Indicates whether siblings of the current node have to be printed.
   * @param stream
   *      Output stream to which the data shall be written.
   */
  public void print(MutableXTreeNode start_node,
                    boolean printChildren,boolean printSiblings,
                    OutputStream stream);

  /**
   * Print the contents of a tree to a String (for debugging purposes only).
   * The content of a single node can be printed by setting both printChildren
   * and printSiblings to FALSE.
   *
   * @param startNode
   *      The node from where printing should start. If this value is null,
   *      printing starts at the root node.
   * @param indent
   *      Initial number of spaces at the start of a line. The indentation increases
   *      for lower levels.
   * @param printChildren
   *      Indicates whether children of the current node have to be printed.
   * @param printSiblings
   *      Indicates whether siblings of the current node have to be printed.
   * @param stream
   *      Output stream to which the data shall be written.
   */
  public String print(MutableXTreeNode start_node,
                    boolean printChildren,boolean printSiblings);

  /**
   * Print the content of a single node (for debugging purposes only).
   * 
   * @param node - the node to be printed.
   * @return A string representation of the node.
   */
  public String nodeToString(MutableXTreeNode node);
  
}
