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
  MutableXTreeNode getParent();
  
  /**
   * Set the parent node of this node.
   * 
   * @param parent the new value for the parent node, which may be null.
   */
  void setParent(MutableXTreeNode parent);

  /**
   * {@inheritDoc}
   */
  @Override
  MutableXTreeNode getFirstChild();
  
  /**
   * {@inheritDoc}
   */
  @Override
  MutableXTreeNode getLastChild();
  
  
  /**
   * Set the first child node of this node.
   * 
   * @param firstChild the new value for the first child, which may be null.
   */
  void setFirstChild(MutableXTreeNode firstChild);


  /**
   * {@inheritDoc}
   */
  @Override
  MutableXTreeNode getNextSibling();

  /**
   * {@inheritDoc}
   */
  @Override
  MutableXTreeNode getLastSibling();
  
  /**
   * Set the next sibling node of this node.
   * 
   * @param nextSibling the new value for the next sibling, which may be null.
   */
  void setNextSibling(MutableXTreeNode nextSibling);

  /*
   * Getting data from a node.
   */

  /*
   * Adding nodes and changing node values.
   */
    
  /**
   * Add a node as the last child of this node.
   * 
   * @param newNode the node to be added as a child of this node.
   * @return newNode
   */
  default MutableXTreeNode addChild(MutableXTreeNode newNode) {
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
  MutableXTreeNode addTagChild(XTreeTag data);
  
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
  MutableXTreeNode addBooleanChild(boolean data);
    
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
  MutableXTreeNode addIntegerChild(int data);
  
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
  MutableXTreeNode addStringChild(String data);

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
  MutableXTreeNode addBlobChild(byte[] data);

  /**
   * Add a node as the next sibling of this node.
   * 
   * @param newNode the node to be added as a next sibling of this node.
   * @return newNode
   */
  default MutableXTreeNode appendSibling(MutableXTreeNode newNode) {
    
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
  MutableXTreeNode appendTagSibling(XTreeTag data);
  /**
   * Create a node of type 'BOOLEAN' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  MutableXTreeNode appendBooleanSibling(boolean data);
  
  /**
   * Create a node of type 'INTEGER' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  MutableXTreeNode appendIntegerSibling(int data);
  
  /**
   * Create a node of type 'STRING' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  MutableXTreeNode appendStringSibling(String data);
  
  /**
   * Create a node of type 'BLOB' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  MutableXTreeNode appendBlobSibling(byte[] data);
  
  
  /**
   * Set the value of a node of type TAG.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type TAG.
   * 
   * @param tagValue the value to be stored in this node, which may not be null.
   */
  void setTagData(XTreeTag tagValue);

  /**
   * Set the value of a node of type BOOLEAN.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BOOLEAN.
   * 
   * @param booleanValue the value to be stored in this node.
   */
  void setBooleanData(boolean booleanValue);

  /**
   * Set the value of a node of type INTEGER.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type INTEGER.
   * 
   * @param integerValue the value to be stored in this node.
   */
  void setIntegerData(int integerValue);

  /**
   * Set the value of a node of type STRING.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type STRING.
   * 
   * @param stringValue the value to be stored in this node, which may not be null.
   */
  void setStringData(String stringValue);

  /**
   * Set the value of a node of type BLOB.
   * <p>
   * An UnsupportedOperationException will be thrown if this method is called on a node which isn't of type BLOB.
   * 
   * @param blob the value to be stored in this node, which may not be null.
   */
  void setBlobData(byte[] blob);

  
  /**
   * Get a clone of this node.
   * <p>
   * Only the data of the node is cloned. The references (parent, firstChild and nextSibling) are all set to null.
   * 
   * @return a clone of this node.
   */
  MutableXTreeNode cloneNode();
}
