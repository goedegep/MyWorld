package goedegep.util.xtree.mutable;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;

/**
 * This interface represents a node in an {@link XTree}.
 * <p>
 * This interface doesn't define any method. This interface is only used as return value of methods and
 * for input parameters of methods in the XTree interface.<br/>
 * This interface does define constants for:
 * <ul>
 * <li>
 * Tags<br/>
 * Tags define the meaning of a node.
 * </li>
 * </ul>
 *
 */
public interface MutableXTreeNode {
  
  /**
   * Test whether a node has a parent. Only the top-level nodes have no parent.
   * 
   * @return
   *    true if the node has a parent.<br>
   *    false otherwise.
   */
  public boolean hasParent();

  /**
   * Get the parent of a node.
   * 
   * @return
   *    null if the node has no parent. <br>
   *    The parent of the node otherwise.
   */
  public MutableXTreeNode getParent();

  /**
   * Test whether a node has a child.
   * 
   * @return
   *    ture if the node has a child. <br>
   *    false otherwise.
   */
  public boolean hasChild();

  /**
   * Get the first child of a node.
   * 
   * @param node - The node.
   * @return
   *  null if the node has no child.<br>
   *  The first child of the node otherwise.
   */
  public MutableXTreeNode getFirstChild();

  /**
   * Get the last child of a node.
   * 
   * @return
   *  null if the node has no child.<br>
   *  The last child of the node otherwise.
   */
  public MutableXTreeNode getLastChild();

  /**
   * Test whether a node has a sibling.
   * 
   * @return
   *  TRUE if the node has a sibling. <br>
   *  FALSE otherwise.
   */
  public boolean hasSibling();

  /**
   * Get the next sibling of a node.
   * 
   * @return
   *    null if the node has no sibling. <br>
   *    The next sibling of the node otherwise.
   */
  public MutableXTreeNode getNextSibling();

  /**
   * Get the last sibling of a node.
   * 
   * @return
   *    null if the node has no sibling. <br>
   *    The last sibling of the node otherwise.
   */
  public MutableXTreeNode getLastSibling();

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
   * In this Java API this method only works for the BLOB data type.
   * 
   * @return The size of the data in the node.
   */
  public int getDataSize();
  
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
  public XTreeTag getTagData();

  /**
   * Get the data from a node of type BOOLEAN.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BOOLEAN.
   * 
   * @return the BOOLEAN data from this node.
   */
  public boolean getBooleanData();

  /**
   * Get the data from a node of type INTEGER.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type INTEGER.
   * 
   * @return the INTEGER data from this node.
   */
  public int getIntegerData();

  /**
   * Get the data from a node of type STRING.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type STRING.
   * 
   * @return the STRING data from this node, which is never null.
   */
  public String getStringData();

  /**
   * Get the data from a node of type BLOB.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BLOB.
   * 
   * @return the BLOB data from this node, which is never null.
   */
  public byte[] getBlobData();

  /**
   * Get the data, of type TAG, from the first child node of this node.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type TAG.
   * 
   * @return the TAG data of the first child of this node, which is never null.
   */
  public XTreeTag getTagChildData();
  
  /**
   * Get the data, of type BOOLEAN, from the first child node of this node.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type BOOLEAN.
   * 
   * @return the BOOLEAN data of the first child of this node.
   */
  public boolean getBooleanChildData();

  /**
   * Get the data, of type INTEGER, from the first child node of this node.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type INTEGER.
   * 
   * @return the INTEGER data of the first child of this node.
   */
  public int getIntegerChildData();

  /**
   * Get the data, of type STRING, from the first child node of this node.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type STRING.
   * 
   * @return the STRING data of the first child of this node, which is never null.
   */
  public String getStringChildData();

  /**
   * Get the data, of type BLOB, from the first child node of this node.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type BLOB.
   * 
   * @return the BLOB data of the first child of this node, which is never null.
   */
  public byte[] getBlobChildData();

  /*
   * Adding nodes and changing node values.
   */
  
  /**
   * Remove all children from the node.
   */
  public void clearChildren();
  
  /**
   * Add a node as the last child of this node.
   * 
   * @param newNode the node to be added as a child of this node.
   * @return newNode
   */
  public DefaultMutableXTreeNode addChild(DefaultMutableXTreeNode newNode);

  /**
   * Set the value of a node of type TAG.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type TAG.
   * 
   * @param tagValue the value to be stored in this node, which may not be null.
   */
  public void setTagData(XTreeTag tagValue);

  /**
   * Set the value of a node of type BOOLEAN.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BOOLEAN.
   * 
   * @param booleanValue the value to be stored in this node.
   */
  public void setBooleanData(boolean booleanValue);

  /**
   * Set the value of a node of type INTEGER.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type INTEGER.
   * 
   * @param integerValue the value to be stored in this node.
   */
  public void setIntegerData(int integerValue);

  /**
   * Set the value of a node of type STRING.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type STRING.
   * 
   * @param stringValue the value to be stored in this node, which may not be null.
   */
  public void setStringData(String stringValue);

  /**
   * Set the value of a node of type BLOB.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BLOB.
   * 
   * @param blob the value to be stored in this node, which may not be null.
   */
  public void setBlobData(byte[] blob);
    
}
