package goedegep.util.xtree.mutable;

import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.nodebased.XTreeNode;

/**
 * This interface represents a node in an {@link MutableXTree}.
 */
public interface MutableXTreeNode extends XTreeNode {
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getParent();
  
  /**
   * Set the parent node of this node.
   * 
   * @param parent the new value for the parent node, which may be null.
   */
  public void setParent(MutableXTreeNode parent);

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getFirstChild();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getLastChild();
  
  
  /**
   * Set the first child node of this node.
   * 
   * @param firstChild the new value for the first child, which may be null.
   */
  public void setFirstChild(MutableXTreeNode firstChild);


  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getNextSibling();

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getLastSibling();
  
  /**
   * Set the next sibling node of this node.
   * 
   * @param nextSibling the new value for the next sibling, which may be null.
   */
  public void setNextSibling(MutableXTreeNode nextSibling);

  /*
   * Getting data from a node.
   */
//
//  /**
//   * Get the data type of a node.
//   * 
//   * @return The data type of the node.
//   */
//  public XNodeDataType getDataType();
//
//  /**
//   * Get the size of the data stored in a node.
//   * In this Java API this method only works for the BLOB data type.
//   * 
//   * @return The size of the data in the node.
//   */
//  public int getDataSize();
//  
//  /**
//   * Get the data from a node.
//   * 
//   * @return the data from this node.
//   */
//  public Object getData();
//
//  /**
//   * Get the data from a node of type TAG.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type TAG.
//   * 
//   * @return the TAG data from this node, which is never null.
//   */
//  public XTreeTag getTagData();
//
//  /**
//   * Get the data from a node of type BOOLEAN.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BOOLEAN.
//   * 
//   * @return the BOOLEAN data from this node.
//   */
//  public boolean getBooleanData();
//
//  /**
//   * Get the data from a node of type INTEGER.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type INTEGER.
//   * 
//   * @return the INTEGER data from this node.
//   */
//  public int getIntegerData();
//
//  /**
//   * Get the data from a node of type STRING.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type STRING.
//   * 
//   * @return the STRING data from this node, which is never null.
//   */
//  public String getStringData();
//
//  /**
//   * Get the data from a node of type BLOB.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BLOB.
//   * 
//   * @return the BLOB data from this node, which is never null.
//   */
//  public byte[] getBlobData();
//
//  /**
//   * Get the data, of type TAG, from the first child node of this node.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type TAG.
//   * 
//   * @return the TAG data of the first child of this node, which is never null.
//   */
//  public XTreeTag getTagChildData();
//  
//  /**
//   * Get the data, of type BOOLEAN, from the first child node of this node.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type BOOLEAN.
//   * 
//   * @return the BOOLEAN data of the first child of this node.
//   */
//  public boolean getBooleanChildData();
//
//  /**
//   * Get the data, of type INTEGER, from the first child node of this node.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type INTEGER.
//   * 
//   * @return the INTEGER data of the first child of this node.
//   */
//  public int getIntegerChildData();
//
//  /**
//   * Get the data, of type STRING, from the first child node of this node.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type STRING.
//   * 
//   * @return the STRING data of the first child of this node, which is never null.
//   */
//  public String getStringChildData();
//
//  /**
//   * Get the data, of type BLOB, from the first child node of this node.
//   * <p>
//   * An UnsupportedOperationException will be thrown if this method is called on a node of which the first child node isn't of type BLOB.
//   * 
//   * @return the BLOB data of the first child of this node, which is never null.
//   */
//  public byte[] getBlobChildData();

  /*
   * Adding nodes and changing node values.
   */
  
//  /**
//   * Remove all children from the node.
//   */
//  public void clearChildren();
  
  /**
   * Add a node as the last child of this node.
   * 
   * @param newNode the node to be added as a child of this node.
   * @return newNode
   */
  public default MutableXTreeNode addChild(MutableXTreeNode newNode) {
    if (!hasChild()) {
      // No child yet, so add as first child.
      setFirstChild(newNode);
    } else {
      // Find last child and add node as sibling of that node.
      MutableXTreeNode lastChild = (MutableXTreeNode) getLastChild();
      lastChild.setNextSibling(newNode);
    }
    newNode.setParent(this);

    return newNode;
  }
  
  /**
   * Create a node of type 'TAG' and add this node as a child of this node.
   * If this node doesn't have children yet, the new node will be the
   * first child, else the new node will be the last child of this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode addTagChild(XTreeTag data);
  
  /**
   * Create a node of type 'BOOLEAN' and add this node to the tree as a child of a this node.
   * If this node doesn't have children yet, the new node will be the
   * first child, else the new node will be the last child of this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode addBooleanChild(boolean data);
    
  /**
   * Create a node of type 'INTEGER' and add this node to the tree as a child of this node.
   * If this node doesn't have children yet, the new node will be the
   * first child, else the new node will be the last child of this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode addIntegerChild(int data);
  
  /**
   * Create a node of type 'STRING' and add this node to the tree as a child of this node.
   * If this node doesn't have children yet, the new node will be the
   * first child, else the new node will be the last child of this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode addStringChild(String data);

  /**
   * Create a node of type 'BLOB' and add this node to the tree as a child of this node.
   * If the referenceNode doesn't have children yet, the new node will be the
   * first child, else the new node will be the last child of this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode addBlobChild(byte[] data);

  /**
   * Add a node as the next sibling of this node.
   * 
   * @param newNode the node to be added as a next sibling of this node.
   * @return newNode
   */
  public default MutableXTreeNode appendSibling(MutableXTreeNode newNode) {
    
    newNode.setParent((MutableXTreeNode) this.getParent());
    newNode.setNextSibling((MutableXTreeNode) this.getNextSibling());
    setNextSibling(newNode);
    
    return newNode;
  }
  
  
  /**
   * Create a node of type 'TAG' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode appendTagSibling(XTreeTag data);
  /**
   * Create a node of type 'BOOLEAN' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode appendBooleanSibling(boolean data);
  
  /**
   * Create a node of type 'INTEGER' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode appendIntegerSibling(int data);
  
  /**
   * Create a node of type 'STRING' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode appendStringSibling(String data);
  
  /**
   * Create a node of type 'BLOB' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public MutableXTreeNode appendBlobSibling(byte[] data);
  
  
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

  
  /**
   * Get a clone of this node.
   * <p>
   * Only the data of the node is cloned. The references (parent, firstChild and nextSibling) are all set to null.
   * 
   * @return a clone of this node.
   */
  public MutableXTreeNode cloneNode();
}
