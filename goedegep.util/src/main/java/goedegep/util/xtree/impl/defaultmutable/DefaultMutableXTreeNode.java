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
