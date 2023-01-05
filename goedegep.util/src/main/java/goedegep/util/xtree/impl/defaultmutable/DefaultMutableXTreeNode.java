package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.nodebased.XTreeNodeAbstract;
import goedegep.util.xtree.mutable.MutableXTreeNode;

/**
 * Common part for a node in a DefaultMutableXTree.
 * <p>
 * For each supported data type, there is a sub-type of this class.
 */
public abstract class DefaultMutableXTreeNode extends XTreeNodeAbstract implements MutableXTreeNode {

  /**
   * Constructor
   */
  public DefaultMutableXTreeNode() {    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getParent() {
    return (MutableXTreeNode) super.getParent();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(MutableXTreeNode parent) {
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getFirstChild() {
    return (MutableXTreeNode) super.getFirstChild();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getLastChild() {
    return (MutableXTreeNode) super.getLastChild();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setFirstChild(MutableXTreeNode firstChild) {
    this.firstChild = firstChild;
  }
  

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode getNextSibling() {
    return (MutableXTreeNode) super.getNextSibling();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultMutableXTreeNode getLastSibling() {
    return (DefaultMutableXTreeNode) super.getLastSibling();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setNextSibling(MutableXTreeNode nextSibling) {
    this.nextSibling = nextSibling;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getDataSize() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeTag getTagData() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getBooleanData() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIntegerData() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringData() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] getBlobData() {
    throw new UnsupportedOperationException();
  }
  
//  @Override
//  public XTreeTag getTagChildData() {
//    return getFirstChild().getTagData();
//  }
//
//  @Override
//  public boolean getBooleanChildData() {
//    return getFirstChild().getBooleanData();
//  }
//
//  @Override
//  public int getIntegerChildData() {
//    return getFirstChild().getIntegerData();
//  }
//
//  @Override
//  public String getStringChildData() {
//    return getFirstChild().getStringData();
//  }
//
//  @Override
//  public byte[] getBlobChildData() {
//    return getFirstChild().getBlobData();
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTagData(XTreeTag tagValue) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBooleanData(boolean booleanValue) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIntegerData(int integerValue) {
    throw new UnsupportedOperationException();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setStringData(String stringValue) {
    throw new UnsupportedOperationException();
  }
  
  /**
   * {@inheritDoc}
   */
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
  
//  /** SEE CLONE
//   * Create a DefaultMutableXTreeNode with the same data content as a given XNode.
//   * <p>
//   * Based on the data type of the given XNode an instance of the corresponding subtype of a DefaultMutableXTreeNode
//   * is created. The value of the node is set to the value of the given XNode.
//   * 
//   * @param xTree the XTree of which xNode is a node.
//   * @param xNode the node for which a DefaultMutableXTreeNode is to be created.
//   * @return a DefaultMutableXTreeNode with the same data content as the xNode.
//   */
//  public static DefaultMutableXTreeNode createFromXNode(MutableXTree xTree, MutableXTreeNode xNode) {
//    DefaultMutableXTreeNode defaultMutableXTreeNode;
//    
//    switch (xNode.getDataType()) {
//    case TAG:
//      defaultMutableXTreeNode = new DefaultMutableXTreeTagNode(xNode.getTagData());
//      break;
//      
//    case BOOLEAN:
//      defaultMutableXTreeNode = new DefaultMutableXTreeBooleanNode(xNode.getBooleanData());
//      break;
//      
//    case INTEGER:
//      defaultMutableXTreeNode = new DefaultMutableXTreeIntegerNode(xNode.getIntegerData());
//      break;
//      
//    case STRING:
//      defaultMutableXTreeNode = new DefaultMutableXTreeStringNode(xNode.getStringData());
//      break;
//      
//    case BLOB:
//      defaultMutableXTreeNode = new DefaultMutableXTreeBlobNode(xNode.getBlobData());
//      break;
//      
//    default:
//      throw new IllegalArgumentException("Data type "+ xNode.getDataType().name() + " of xNode is not supported.");
//    }
//    
//    return defaultMutableXTreeNode;
//  }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public void clearChildren() {
//    DefaultMutableXTreeNode child = firstChild;
//    
//    while (child != null) {
//      DefaultMutableXTreeNode sibling = child.nextSibling;
//      
//      // clear the node
//      child.nextSibling = null;
//      child.parent = null;
//      child.clearChildren();
//      
//      child = sibling;
//    }
//    
//    firstChild = null;
//  }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public DefaultMutableXTreeNode addChild(DefaultMutableXTreeNode newNode) {
//    if (!hasChild()) {
//      // No child yet, so add as first child.
//      setFirstChild(newNode);
//    } else {
//      // Find last child and add node as sibling of that node.
//      DefaultMutableXTreeNode lastChild = (DefaultMutableXTreeNode) getLastChild();
//      lastChild.setNextSibling(newNode);
//    }
//    newNode.setParent(this);
//
//    return newNode;
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode addTagChild(XTreeTag data) {
    return addChild(new DefaultMutableXTreeTagNode(data));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode addBooleanChild(boolean data) {
    return addChild(new DefaultMutableXTreeBooleanNode(data));
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode addIntegerChild(int data) {
    return addChild(new DefaultMutableXTreeIntegerNode(data));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode addStringChild(String data) {
    return addChild(new DefaultMutableXTreeStringNode(data));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode addBlobChild(byte[] data) {
    return addChild(new DefaultMutableXTreeBlobNode(data));
  }
//
//  /**
//   * Add a node as the next sibling of this node.
//   * 
//   * @param newNode the node to be added as a next sibling of referenceNode.
//   * @return newNode
//   */
//  public DefaultMutableXTreeNode appendSibling(DefaultMutableXTreeNode newNode) {
//    
//    newNode.setParent(this.getParent());
//    newNode.setNextSibling(this.getNextSibling());
//    setNextSibling(newNode);
//    
//    return newNode;
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode appendTagSibling(XTreeTag data) {
    return appendSibling(new DefaultMutableXTreeTagNode(data));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode appendBooleanSibling(boolean data) {
    return appendSibling(new DefaultMutableXTreeBooleanNode(data));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode appendIntegerSibling(int data) {
    return appendSibling(new DefaultMutableXTreeIntegerNode(data));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode appendStringSibling(String data) {
    return appendSibling(new DefaultMutableXTreeStringNode(data));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode appendBlobSibling(byte[] data) {
    return appendSibling(new DefaultMutableXTreeBlobNode(data));
  }
    
  
  /**
   * {@inheritDoc}
   */
  @Override
  public abstract MutableXTreeNode cloneNode();

  
//  /** Use toString()
//   * Get a String representation of the value of this node.
//   * 
//   * @return a String representation of the value of this node.
//   */
//  public abstract String nodeToString();
  
//  /**
//   * Get a String representation of the references (parent, firstChild and nextSibling) of this node.
//   * 
//   * @return a String representation of the references (parent, firstChild and nextSibling) of this node.
//   */
//  public String nodeStructureToString() {
//    StringBuilder buf = new StringBuilder();
//    buf.append("(");
//    if (parent != null) {
//      buf.append(parent.toString());      
//    } else {
//      buf.append("null");
//    }
//    buf.append(", ");
//    if (firstChild != null) {
//      buf.append(firstChild.toString());      
//    } else {
//      buf.append("null");
//    }
//    buf.append(", ");
//    if (nextSibling != null) {
//      buf.append(nextSibling.toString());      
//    } else {
//      buf.append("null");
//    }
//    buf.append(")");
//    return buf.toString();
//  }
  
}
