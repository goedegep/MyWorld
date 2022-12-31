package goedegep.util.xtree;

/**
 * This interface represents a node in an {@link XTree}, it defines the functionality of an XTree node.
 * <p>
 * An implementation only has to be provided by XTree implementations that support access via nodes, i.e. implementations that provide an implementation of {@link XTree#getRoot()}.
 *
 */
public interface XTreeNode {
  
  /**
   * Test whether a node has a parent. Only the top-level nodes have no parent.
   * 
   * @return
   *    true if the node has a parent, false otherwise.
   */
  public boolean hasParent();
  
  /**
   * Get the parent of a node.
   * 
   * @return
   *    null if the node has no parent. <br>
   *    The parent of the node otherwise.
   */
  public XTreeNode getParent();

  /**
   * Test whether a node has one or more children.
   * 
   * @return
   *    ture if the node has a child, false otherwise.
   */
  public boolean hasChild();

  /**
   * Get the first child of a node.
   * 
   * @param node - The node.
   * @return The first child of {@code node}, or null is the node doesn't have any children.
   */
  public XTreeNode getFirstChild();

  /**
   * Get the last child of a node.
   * 
   * @return The last child of the node, or null if the node doesn't have any children.
   */
  public default XTreeNode getLastChild() {
    XTreeNode node = getFirstChild();

    if (node == null) {
      return null;
    }

    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }

  /**
   * Test whether a node has a sibling.
   * 
   * @return TRUE if the node has a sibling, FALSE otherwise.
   */
  public boolean hasSibling();

  /**
   * Get the next sibling of a node.
   * 
   * @return The next sibling of the node, or null if there is no next sibling.
   */
  public XTreeNode getNextSibling();

  /**
   * Get the last sibling of a node.
   * 
   * @return The last sibling of the node, or null if there isn't any next sibling.
   */
  public default XTreeNode getLastSibling() {
    if (!hasSibling()) {
      return null;
    }
    
    XTreeNode node = getNextSibling();
    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }
  
  /*
   * Getting data from a node.
   */

  /**
   * Get the data type of a node.
   * 
   * @return The data type of the node.
   */
  public XNodeDataType getDataType();

  /**
   * Get the size of the data stored in a node.
   * <p>
   * This method shall be implemented for BLOB data, for other types it isn't interesting.
   * 
   * @return The size of the data in the node.
   */
  public default int getDataSize() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Get the data from a node.
   * 
   * @return the data from this node.
   */
  public Object getData();

  /**
   * Get the data from a node of type TAG.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type TAG.
   * 
   * @return the TAG data from this node, which is never null.
   */
  public default XTreeTag getTagData() {
    return (XTreeTag) getData();
  }

  /**
   * Get the data from a node of type BOOLEAN.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BOOLEAN.
   * 
   * @return the BOOLEAN data from this node.
   */
  public default boolean getBooleanData() {
    return (boolean) getData();
  }

  /**
   * Get the data from a node of type INTEGER.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type INTEGER.
   * 
   * @return the INTEGER data from this node.
   */
  public default int getIntegerData() {
    return (int) getData();
  }

  /**
   * Get the data from a node of type STRING.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type STRING.
   * 
   * @return the STRING data from this node, which is never null.
   */
  public default String getStringData() {
    return (String) getData();
  }

  /**
   * Get the data from a node of type BLOB.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BLOB.
   * 
   * @return the BLOB data from this node, which is never null.
   */
  public default byte[] getBlobData() {
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
  public default XTreeTag getTagChildData() {
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
  public default boolean getBooleanChildData() {
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
  public default int getIntegerChildData() {
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
  public default String getStringChildData() {
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
  public default byte[] getBlobChildData() {
    return getFirstChild().getBlobData();
  }

  /**
   * Get a String representation of the references (parent, firstChild and nextSibling) of this node.
   * 
   * @return a String representation of the references (parent, firstChild and nextSibling) of this node.
   */
  public default String nodeStructureToString() {
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
