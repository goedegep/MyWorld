package goedegep.util.xtree.mutable;

import java.io.OutputStream;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNode;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeBlobNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeBooleanNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeStringNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeTagNode;

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
   * Set the root node of a tree.
   * 
   * @return The root node of the tree.
   */
  public MutableXTreeNode setRoot(MutableXTreeNode rootNode);
  
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
   * Create a node of type 'TAG' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   * <p>
   * Note that this method operates on the tree and node on a node, so the node doesn't need to have
   * a reference to 
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode insertTagSibling(MutableXTreeNode referenceNode, XTreeTag data);
  
  /**
   * Create a node of type 'BOOLEAN' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode insertBooleanSibling(MutableXTreeNode referenceNode, boolean data);
  
  /**
   * Create a node of type 'INTEGER' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode insertIntegerSibling(MutableXTreeNode referenceNode, int data);

  /**
   * Create a node of type 'STRING' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode insertStringSibling(MutableXTreeNode referenceNode, String data);
  
  /**
   * Create a node of type 'BLOB' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode insertBlobSibling(MutableXTreeNode referenceNode, byte[] data);
  
  /**
   * Add a node as the previous sibling of a node.
   * 
   * @param referenceNode the node to which newNode has to be added as a previous sibling. If this node is null, the newNode will become the root node.
   * @param newNode the node to be added as a previous sibling of referenceNode.
   * @return newNode
   */
  public MutableXTreeNode insertSibling(MutableXTreeNode referenceNode, MutableXTreeNode newNode);
  
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
  public boolean compareNodes(XTreeNode referenceNode, XTree secondTree, XTreeNode secondNode);

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
  
  /**
   * Add a copy of a (sub)tree to this tree.
   * The (sub)tree is added as a child of the dstParentNode and as sibling of the
   * dstSiblingNode. If insert is TRUE, it is inserted before the dstSiblingNode,
   * else it is appended after it.<br>
   * The dstSiblingNode may be null. In this case the (sub)tree is added as
   * first child of dstParentNode if insert is TRUE, or as last child otherwise.<br>
   * If the dstSiblingNode is not null, parameter dstParentNode is ignored.<br>
   * If both dstParentNode and dstSiblingNode are NULL, the (sub)tree will be
   * added as root.<br>
   * If srcReferenceNode is null, the root of the srcTree is used as
   * srcReferenceNode. This implies that the complete srcTree can be added by setting
   * srcReferenceNode to null and includeSiblings to TRUE.<br>
   * The function recursively copies the nodes, starting at srcReferenceNode,
   * from srcTree to this tree.
   * 
   * @param dstParentNode
   *      Node below which the subtree of srcTree will be added.
   * @param dstSiblingNode
   *      Node before or after which the subtree of srcTree will be added.
   * @param insert
   *      Indicates whether the subtree of srcTree is added before (if TRUE) or
   *      after (if FALSE) dstSiblingNode.
   * @param srcTree
   *      The tree of which a subtree is to be added.
   * @param srcReferenceNode
   *      Starting point of the subtree in srcTree.
   * @param includeSiblings
   *      The new tree will always contain 'srcReferenceNode' and all its
   *      decendents.  If this value is true, the new tree also contains the
   *      siblings of 'srcReferenceNode' and all their decendents.
   */
  public void addSubtreeCopy(MutableXTreeNode dstParentNode, MutableXTreeNode dstSiblingNode,
      boolean insert, MutableXTree srcTree, MutableXTreeNode srcReferenceNode,
      boolean includeSiblings);

  /**
   * Add a tree as a subtree to another tree. After adding it, the srcTree
   * may no longer be used (it's not a valid tree anymore).
   * The srcTree is added as a child of the parentNode and as sibling of the
   * siblingNode.<br>
   * The siblingNode may be NULL. In this case the srcTree is added as
   * first child of parentNode.<br>
   * If the siblingNode is not NULL, parentNode may be NULL.
   *
   * @param parentNode
   *      Node in tree below which the srcTree will be added.
   * @param siblingNode
   *      Node in tree before or after which the srcTree will be added.
   * @param[in] srcTree
   *      The tree to be added to this tree.
   */
  public void mergeSubtree(MutableXTreeNode parentNode, MutableXTreeNode siblingNode, MutableXTree srcTree);

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
