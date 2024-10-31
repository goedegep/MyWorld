package goedegep.util.xtree.nodebased;

import java.util.Arrays;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeTag;

/**
 * This interface represents a node in an {@link NodeBasedXTree}, it defines the functionality of an XTree node.
 * <p>
 * An implementation only has to be provided by XTree implementations that support access via nodes, i.e. implementations that provide an implementation of {@link XTree#getRoot()}.
 *
 */
public interface XTreeNode {
  
  /*
   * Basic methods to walk through the tree
   */
  
  /**
   * Test whether a node has a parent. Only the top-level nodes have no parent.
   * 
   * @return
   *    true if the node has a parent, false otherwise.
   */
  boolean hasParent();
  
  /**
   * Get the parent of a node.
   * 
   * @return
   *    null if the node has no parent. <br>
   *    The parent of the node otherwise.
   */
  XTreeNode getParent();

  /**
   * Test whether a node has one or more children.
   * 
   * @return
   *    true if the node has a child, false otherwise.
   */
  boolean hasChild();

  /**
   * Get the first child of a node.
   * 
   * @param node - The node.
   * @return The first child of {@code node}, or null is the node doesn't have any children.
   */
  XTreeNode getFirstChild();

  /**
   * Get the last child of a node.
   * 
   * @return The last child of the node, or null if the node doesn't have any children.
   */
  XTreeNode getLastChild();

  /**
   * Get the number of children of a node.
   */
  int getNumberOfChildren();
  
  /**
   * Get the child index of a node.
   * 
   * @return the index of this node in the list of children of its parent, or -1 if the node has no parent.
   */
  int getChildIndex();

  /**
   * Test whether a node has a sibling.
   * 
   * @return TRUE if the node has a sibling, FALSE otherwise.
   */
  boolean hasSibling();

  /**
   * Get the next sibling of a node.
   * 
   * @return The next sibling of the node, or null if there is no next sibling.
   */
  XTreeNode getNextSibling();

  /**
   * Get the last sibling of a node.
   * 
   * @return The last sibling of the node, or null if there isn't any next sibling.
   */
  XTreeNode getLastSibling();

  /**
   * Get the number of siblings following a node.
   */
  int getNumberOfRemainingSiblings();
  
  /*
   * Getting data from a node.
   */

  /**
   * Get the data type of a node.
   * 
   * @return The data type of the node.
   */
  XNodeDataType getDataType();

  /**
   * Get the size of the data stored in a node.
   * <p>
   * This method shall be implemented for BLOB data, for other types it isn't interesting.
   * 
   * @return The size of the data in the node.
   */
  default int getDataSize() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Get the data from a node.
   * 
   * @return the data from this node.
   */
  Object getData();

  /**
   * Get the data from a node of type TAG.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type TAG.
   * 
   * @return the TAG data from this node, which is never null.
   */
  default XTreeTag getTagData() {
    return (XTreeTag) getData();
  }

  /**
   * Get the data from a node of type BOOLEAN.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BOOLEAN.
   * 
   * @return the BOOLEAN data from this node.
   */
  default boolean getBooleanData() {
    return (boolean) getData();
  }

  /**
   * Get the data from a node of type INTEGER.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type INTEGER.
   * 
   * @return the INTEGER data from this node.
   */
  default int getIntegerData() {
    return (int) getData();
  }

  /**
   * Get the data from a node of type STRING.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type STRING.
   * 
   * @return the STRING data from this node, which is never null.
   */
  default String getStringData() {
    return (String) getData();
  }

  /**
   * Get the data from a node of type BLOB.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BLOB.
   * 
   * @return the BLOB data from this node, which is never null.
   */
  default byte[] getBlobData() {
    return (byte[]) getData();
  }
  

  /**
   * Get the data, of type TAG, from the first child node of this node.
   * <p>
   * A NullPointerException will be thrown if the node has no children.
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type TAG.
   * 
   * @return the TAG data of the first child of this node, which is never null.
   */
  default XTreeTag getTagChildData() {
    return getFirstChild().getTagData();
  }
  
  /**
   * Get the data, of type BOOLEAN, from the first child node of this node.
   * <p>
   * A NullPointerException will be thrown if the node has no children.
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type BOOLEAN.
   * 
   * @return the BOOLEAN data of the first child of this node.
   */
  default boolean getBooleanChildData() {
    return getFirstChild().getBooleanData();
  }

  /**
   * Get the data, of type INTEGER, from the first child node of this node.
   * <p>
   * A NullPointerException will be thrown if the node has no children.
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type INTEGER.
   * 
   * @return the INTEGER data of the first child of this node.
   */
  default int getIntegerChildData() {
    return getFirstChild().getIntegerData();
  }

  /**
   * Get the data, of type STRING, from the first child node of this node.
   * <p>
   * A NullPointerException will be thrown if the node has no children.
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type STRING.
   * 
   * @return the STRING data of the first child of this node, which is never null.
   */
  default String getStringChildData() {
    return getFirstChild().getStringData();
  }

  /**
   * Get the data, of type BLOB, from the first child node of this node.
   * <p>
   * A NullPointerException will be thrown if the node has no children.
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type BLOB.
   * 
   * @return the BLOB data of the first child of this node, which is never null.
   */
  default byte[] getBlobChildData() {
    return getFirstChild().getBlobData();
  }
  
  /**
   * Comparing nodes and checking on specific values
   */
  
  /**
   * Compare two nodes.
   * <p>
   * The nodes are considered equal if:
   * <ul>
   * <li> both nodes are null, or not null AND</li>
   * <li> both nodes have a child, or don't have a child. AND</li>
   * <li> both nodes have a sibling, or don't have a sibling. AND</li>
   * <li> the nodes have the same contents</li> 
   * </ul>
   * The method is meant to be used for comparing nodes in different trees, therefore the child and sibling references will always be different.
   * 
   * @param firstNode
   * @param secondNode
   * @return true is the nodes are 'equal', false otherwise.
   */
  static boolean compareNodesStructureAndContent(XTreeNode firstNode, XTreeNode secondNode) {
    if (((firstNode == null) && (secondNode != null))  ||
        ((firstNode != null) && (secondNode == null))  ||
        (firstNode.hasChild()  &&  !secondNode.hasChild())  ||
        (!firstNode.hasChild()  &&  secondNode.hasChild())  ||
        (firstNode.hasSibling()  &&  !secondNode.hasSibling())  ||
        (!firstNode.hasSibling()  &&  secondNode.hasSibling())
        ) {
      return false;
    } else {
      return firstNode.equals(secondNode);
    }
  }
  
  /**
   * Check whether the node contains a specific XTreeTag value.
   * 
   * @param value the value to check against
   * @return true if the node contains {@code value}, false otherwise.
   */
  default boolean contains(XTreeTag value) {
    return getDataType() == XNodeDataType.TAG &&
        getTagChildData() == value;  
  }
  
  /**
   * Check whether the node contains a specific boolean value.
   * 
   * @param value the value to check against
   * @return true if the node contains {@code value}, false otherwise.
   */
  default boolean contains(boolean value) {
    return getDataType() == XNodeDataType.BOOLEAN &&
        getBooleanData() == value;    
  }
  
  /**
   * Check whether the node contains a specific int value.
   * 
   * @param value the value to check against
   * @return true if the node contains {@code value}, false otherwise.
   */
  default boolean contains(int value) {
    return getDataType() == XNodeDataType.INTEGER &&
        getIntegerData() == value;   
  }
  
  /**
   * Check whether the node contains a specific String value.
   * 
   * @param value the value to check against
   * @return true if the node contains {@code value}, false otherwise.
   */
  default boolean contains(String value) {
    return getDataType() == XNodeDataType.STRING &&
        getStringData().compareTo(value) == 0;
  }
  
  /**
   * Check whether the node contains a specific BLOB value.
   * 
   * @param value the value to check against
   * @return true if the node contains {@code value}, false otherwise.
   */
  default boolean contains(byte[] value) { 
    // Not OK if type is not BLOB.
    if (getDataType() != XNodeDataType.BLOB) {
      return false;
    }
     
    return Arrays.equals(getBlobData(), value);
  }

  
  /*
   * Debug funcionality
   */
  
  /**
   * Get a String representation of the references (parent, firstChild and nextSibling) of this node.
   * 
   * @return a String representation of the references (parent, firstChild and nextSibling) of this node.
   */
  default String nodeStructureToString() {
    StringBuilder buf = new StringBuilder();
    buf.append("(");
    if (getParent() != null) {
      buf.append(getParent().toString());      
    } else {
      buf.append("null");
    }
    buf.append(", ");
    if (getFirstChild() != null) {
      buf.append(getFirstChild().toString());      
    } else {
      buf.append("null");
    }
    buf.append(", ");
    if (getNextSibling() != null) {
      buf.append(getNextSibling().toString());      
    } else {
      buf.append("null");
    }
    buf.append(")");
    return buf.toString();
  }
}
