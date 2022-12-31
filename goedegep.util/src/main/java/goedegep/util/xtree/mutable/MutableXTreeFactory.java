package goedegep.util.xtree.mutable;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTree;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeBlobNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeBooleanNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeStringNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeTagNode;

/**
 * This factory class is meant to be used to create mutable XTrees and their nodes.
 *
 */
public class MutableXTreeFactory {
  
  /**
   * Create a mutable xtree.
   * 
   * @return the newly created tree
   */
  public static MutableXTree createMutableXTree() {
    return new DefaultMutableXTree();
  }
  
  /**
   * Create a mutable xtree with the contents of an existing XTree.
   * 
   * @param xTree the XTree with who's content the new tree will be filled.
   * @return the newly created tree
   */
  public static MutableXTree createMutableXTree(XTree xTree) {
    return new DefaultMutableXTree(xTree);
  }
  
  /**
   * Create an XTree, which is filled with a copy of a subtree of an existing XTree.
   * 
   * @param tree The XTree of which the new tree will contain a partial copy.
   * @param node The node where the copying will start.
   * @param includeSiblings If true, also the siblings of node will be part of the tree.
   *        Otherwise only the children of node will be part of the tree.
   */
  public static MutableXTree createMutableXTree(MutableXTree tree, MutableXTreeNode node, boolean includeSiblings) {
    return new DefaultMutableXTree(tree, node, includeSiblings);
  }

  
  /**
   * Create a mutable xtree node.
   * 
   * @param dataType the XNodeDataType for the node
   * @param value the value for the node
   * @return the newly created node
   */
  public static MutableXTreeNode createMutableXTreeNode(XNodeDataType dataType, Object  value) {
    return DefaultMutableXTreeNode.create(dataType, value);
  }
  
  /**
   * Create an integer mutable xtree node.
   * 
   * @param intData the value to store in this node.
   * @return the newly created node
   */
  public static MutableXTreeNode createIntegerMutableXTreeNode(int intData) {
    return new DefaultMutableXTreeIntegerNode(intData);
  }
  
  /**
   * Create a BLOB mutable xtree node.
   * 
   * @param blobData the value to store in this node, which may not be null.
   * @return the newly created node
   */
  public static MutableXTreeNode createBlobMutableXTreeNode(byte[] blobData) {
    return new DefaultMutableXTreeBlobNode(blobData);
  }
  
  /**
   * Create a boolean mutable xtree node.
   * 
   * @param booleanData the value to store in this node.
   * @return the newly created node
   */
  public static MutableXTreeNode createBooleanMutableXTreeNode(boolean booleanData) {
    return new DefaultMutableXTreeBooleanNode(booleanData);
  }
  
  /**
   * Create a String mutable xtree node.
   * 
   * @param stringData the value to store in this node, which may not be null.
   * @return the newly created node
   */
  public static MutableXTreeNode createStringMutableXTreeNode(String stringData) {
    return new DefaultMutableXTreeStringNode(stringData);
  }
  
  /**
   * Create a Tag mutable xtree node.
   * 
   * @param xtreeTag the value to store in this node, which may not be null.
   * @return the newly created node
   */
  public static MutableXTreeNode createTagMutableXTreeNode(XTreeTag xtreeTag) {
    return new DefaultMutableXTreeTagNode(xtreeTag);
  }
}
