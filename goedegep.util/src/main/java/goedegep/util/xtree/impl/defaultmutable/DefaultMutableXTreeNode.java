package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;

/**
 * Common part for a node in a DefaultMutableXTree.
 * <p>
 * For each supported data type, there is a sub-type of this class.
 */
public abstract class DefaultMutableXTreeNode implements MutableXTreeNode {
  private DefaultMutableXTreeNode parent      = null;
  private DefaultMutableXTreeNode firstChild  = null;
  private DefaultMutableXTreeNode nextSibling = null;

  /**
   * Constructor
   */
  public DefaultMutableXTreeNode() {    
  }
  
  @Override
  public boolean hasParent() {
    return parent != null;
  }

  @Override
  public DefaultMutableXTreeNode getParent() {
    return parent;
  }
  
  /**
   * Set the parent node of this node.
   * 
   * @param parent the new value for the parent node, which may be null.
   */
  public void setParent(DefaultMutableXTreeNode parent) {
    this.parent = parent;
  }

  @Override
  public boolean hasChild() {
    return firstChild != null;
  }
  
  @Override
  public DefaultMutableXTreeNode getFirstChild() {
    return firstChild;
  }
  
  /**
   * Set the first child node of this node.
   * 
   * @param firstChild the new value for the first child, which may be null.
   */
  public void setFirstChild(DefaultMutableXTreeNode firstChild) {
    this.firstChild = firstChild;
  }
  
  @Override
  public DefaultMutableXTreeNode getLastChild() {
    DefaultMutableXTreeNode node = getFirstChild();

    if (node == null) {
      return null;
    }

    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }

  @Override
  public boolean hasSibling() {
    return nextSibling != null;
  }

  @Override
  public DefaultMutableXTreeNode getNextSibling() {
    return nextSibling;
  }
  
  /**
   * Set the next sibling node of this node.
   * 
   * @param nextSibling the new value for the next sibling, which may be null.
   */
  public void setNextSibling(DefaultMutableXTreeNode nextSibling) {
    this.nextSibling = nextSibling;
  }

  @Override
  public DefaultMutableXTreeNode getLastSibling() {
    if (!hasSibling()) {
      return null;
    }
    
    DefaultMutableXTreeNode node = this;
    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }
  
  @Override
  public int getDataSize() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public Object getData() {
    switch (getDataType()) {
    case TAG:
      return getTagData();
      
    case BOOLEAN:
      return getBooleanData();
    
    case INTEGER:
      return getIntegerData();
      
    case STRING:
      return getStringData();
      
    case BLOB:
      return getBlobData();
      
    default:
      throw new RuntimeException("Unknow data type: " + getDataType());
    }
  }

  @Override
  public XTreeTag getTagData() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean getBooleanData() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getIntegerData() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getStringData() {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte[] getBlobData() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public XTreeTag getTagChildData() {
    return getFirstChild().getTagData();
  }

  @Override
  public boolean getBooleanChildData() {
    return getFirstChild().getBooleanData();
  }

  @Override
  public int getIntegerChildData() {
    return getFirstChild().getIntegerData();
  }

  @Override
  public String getStringChildData() {
    return getFirstChild().getStringData();
  }

  @Override
  public byte[] getBlobChildData() {
    return getFirstChild().getBlobData();
  }

  @Override
  public void setTagData(XTreeTag tagValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setBooleanData(boolean booleanValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setIntegerData(int integerValue) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void setStringData(String stringValue) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void setBlobData(byte[] blob) {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Create a DefaultMutableXTreeNode for a value of a specific type.
   * <p>
   * The created node will be of the DefaultMutableXTreeNode sub type for the specified type.
   * 
   * @param dataType the XNodeDataType of {@code value}.
   * @param value the value to be stored in the node.
   * @return a node of the sub type of DefaultMutableXTreeNode for {@code dataType} with {@code value}
   *         as content.
   */
  public static DefaultMutableXTreeNode create(XNodeDataType dataType, Object  value) {

    switch (dataType) {
    case BOOLEAN:
      return new DefaultMutableXTreeBooleanNode((boolean) value);

    case TAG:
      return new DefaultMutableXTreeTagNode((XTreeTag) value);

    case INTEGER:
      return new DefaultMutableXTreeIntegerNode((int) value);

    case STRING:
      return new DefaultMutableXTreeStringNode((String) value);

    case BLOB:
      return new DefaultMutableXTreeBlobNode((byte[]) value);

    default:
      throw new RuntimeException("Illegal data type: " + dataType);
    }
  }
  
  /**
   * Create a DefaultMutableXTreeNode with the same data content as a given XNode.
   * <p>
   * Based on the data type of the given XNode an instance of the corresponding subtype of a DefaultMutableXTreeNode
   * is created. The value of the node is set to the value of the given XNode.
   * 
   * @param xTree the XTree of which xNode is a node.
   * @param xNode the node for which a DefaultMutableXTreeNode is to be created.
   * @return a DefaultMutableXTreeNode with the same data content as the xNode.
   */
  public static DefaultMutableXTreeNode createFromXNode(MutableXTree xTree, MutableXTreeNode xNode) {
    DefaultMutableXTreeNode defaultMutableXTreeNode;
    
    switch (xNode.getDataType()) {
    case TAG:
      defaultMutableXTreeNode = new DefaultMutableXTreeTagNode(xNode.getTagData());
      break;
      
    case BOOLEAN:
      defaultMutableXTreeNode = new DefaultMutableXTreeBooleanNode(xNode.getBooleanData());
      break;
      
    case INTEGER:
      defaultMutableXTreeNode = new DefaultMutableXTreeIntegerNode(xNode.getIntegerData());
      break;
      
    case STRING:
      defaultMutableXTreeNode = new DefaultMutableXTreeStringNode(xNode.getStringData());
      break;
      
    case BLOB:
      defaultMutableXTreeNode = new DefaultMutableXTreeBlobNode(xNode.getBlobData());
      break;
      
    default:
      throw new IllegalArgumentException("Data type "+ xNode.getDataType().name() + " of xNode is not supported.");
    }
    
    return defaultMutableXTreeNode;
  }
  
  /**
   * Add a node as the last child of this node.
   * 
   * @param newNode the node to be added as a child of this node.
   * @return newNode
   */
  public DefaultMutableXTreeNode addChild(DefaultMutableXTreeNode newNode) {
    if (!hasChild()) {
      // No child yet, so add as first child.
      setFirstChild(newNode);
    } else {
      // Find last child and add node as sibling of that node.
      DefaultMutableXTreeNode lastChild = getLastChild();
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
  public DefaultMutableXTreeNode addTagChild(XTreeTag data) {
    return addChild(new DefaultMutableXTreeTagNode(data));
  }
  
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
  public DefaultMutableXTreeNode addBooleanChild(boolean data) {
    return addChild(new DefaultMutableXTreeBooleanNode(data));
  }
    
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
  public DefaultMutableXTreeNode addIntegerChild(int data) {
    return addChild(new DefaultMutableXTreeIntegerNode(data));
  }
  
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
  public DefaultMutableXTreeNode addStringChild(String data) {
    return addChild(new DefaultMutableXTreeStringNode(data));
  }

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
  public DefaultMutableXTreeNode addBlobChild(byte[] data) {
    return addChild(new DefaultMutableXTreeBlobNode(data));
  }

  /**
   * Add a node as the next sibling of this node.
   * 
   * @param newNode the node to be added as a next sibling of referenceNode.
   * @return newNode
   */
  public DefaultMutableXTreeNode appendSibling(DefaultMutableXTreeNode newNode) {
    
    newNode.setParent(this.getParent());
    newNode.setNextSibling(this.getNextSibling());
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
  public DefaultMutableXTreeNode appendTagSibling(XTreeTag data) {
    return appendSibling(new DefaultMutableXTreeTagNode(data));
  }
  
  /**
   * Create a node of type 'BOOLEAN' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode appendBooleanSibling(boolean data) {
    return appendSibling(new DefaultMutableXTreeBooleanNode(data));
  }
  
  /**
   * Create a node of type 'INTEGER' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode appendIntegerSibling(int data) {
    return appendSibling(new DefaultMutableXTreeIntegerNode(data));
  }
  
  /**
   * Create a node of type 'STRING' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode appendStringSibling(String data) {
    return appendSibling(new DefaultMutableXTreeStringNode(data));
  }
  
  /**
   * Create a node of type 'BLOB' and add this node to the tree as a sibling of this node.
   * The node is added AFTER this node.
   *
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode appendBlobSibling(byte[] data) {
    return appendSibling(new DefaultMutableXTreeBlobNode(data));
  }
  
  
  
  /**
   * Get a clone of this node.
   * <p>
   * Only the data of the node is cloned. The references (parent, firstChild and nextSibling) are all set to null.
   * 
   * @return a clone of this node.
   */
  public abstract DefaultMutableXTreeNode cloneNode();

  
  /**
   * Get a String representation of the value of this node.
   * 
   * @return a String representation of the value of this node.
   */
  public abstract String nodeToString();
  
  /**
   * Get a String representation of the references (parent, firstChild and nextSibling) of this node.
   * 
   * @return a String representation of the references (parent, firstChild and nextSibling) of this node.
   */
  public String nodeStructureToString() {
    StringBuilder buf = new StringBuilder();
    buf.append("(");
    if (parent != null) {
      buf.append(parent.toString());      
    } else {
      buf.append("null");
    }
    buf.append(", ");
    if (firstChild != null) {
      buf.append(firstChild.toString());      
    } else {
      buf.append("null");
    }
    buf.append(", ");
    if (nextSibling != null) {
      buf.append(nextSibling.toString());      
    } else {
      buf.append("null");
    }
    buf.append(")");
    return buf.toString();
  }
  

  public static void main(String[] args) {
    DefaultMutableXTreeNode node = new DefaultMutableXTreeTagNode(XTreeTag.QUERY_INDEX);
    System.out.println(node.getTagData().getName());
    System.out.println(node.nodeStructureToString());    
  }
}
