package goedegep.util.xtree.impl.binary;

import java.util.logging.Logger;

import goedegep.util.bitsequence.BitSequence;
import goedegep.util.multibyteinteger.MultiByteIntegerUtil;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.nodebased.XTreeNodeAbstract;
import goedegep.util.xtree.nodebased.XTreeNode;

/**
 * This class represents a node in a binary serialized tree.
 */
public class BinarySerializedXTreeNode extends XTreeNodeAbstract implements XTreeNode {
  private static final Logger LOGGER = Logger.getLogger(BinarySerializedXTreeNode.class.getName());

  private BitSequence bitSequence = null;
  private int index;
  
  public BinarySerializedXTreeNode(BitSequence bitSequence, int index) {
    this.bitSequence = bitSequence;
    this.index = index;
  }
  
  
  static BinarySerializedXTreeNode create(BitSequence bitSequence, int index) {

    return new BinarySerializedXTreeNode(bitSequence, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BinarySerializedXTreeNode getParent() {
    return (BinarySerializedXTreeNode) super.getParent();
  }
  
  /**
   * Set the parent node of this node.
   * 
   * @param parent the new value for the parent node, which may be null.
   */
  public void setParent(BinarySerializedXTreeNode parent) {
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BinarySerializedXTreeNode getFirstChild() {
    return (BinarySerializedXTreeNode) super.getFirstChild();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BinarySerializedXTreeNode getLastChild() {
    return (BinarySerializedXTreeNode) super.getLastChild();
  }
  
  /**
   * Set the first child node of this node.
   * 
   * @param firstChild the new value for the first child, which may be null.
   */
  public void setFirstChild(BinarySerializedXTreeNode firstChild) {
    this.firstChild = firstChild;
  }
  
  public BinarySerializedXTreeNode addChild(BinarySerializedXTreeNode newNode) {
    if (!hasChild()) {
      // No child yet, so add as first child.
      setFirstChild(newNode);
    } else {
      // Find last child and add node as sibling of that node.
      BinarySerializedXTreeNode lastChild = getLastChild();
      lastChild.setNextSibling(newNode);
    }
    newNode.setParent(this);

    return newNode;
  }

  @Override
  public BinarySerializedXTreeNode getNextSibling() {
    return (BinarySerializedXTreeNode) nextSibling;
  }
  
  /**
   * Set the next sibling node of this node.
   * 
   * @param nextSibling the new value for the next sibling, which may be null.
   */
  public void setNextSibling(BinarySerializedXTreeNode nextSibling) {
    this.nextSibling = nextSibling;
  }
  
  public BinarySerializedXTreeNode appendSibling(BinarySerializedXTreeNode newNode) {
    
    newNode.setParent(this.getParent());
    newNode.setNextSibling(this.getNextSibling());
    setNextSibling(newNode);
    
    return newNode;
  }

  @Override
  public XNodeDataType getDataType() {    
//    // skip the direction bits
//    bitSequence.setIndex(index + BinaryDirection.getNrOfBitsBinaryIndicationDirection());
    bitSequence.setPosition(index);

    // read the data type
    BinaryTypeIndication binaryTypeIndication = BinaryTypeIndication.readFromBitSequence(bitSequence);
    LOGGER.info("binaryTypeIndication=" + binaryTypeIndication.name());
    

    XNodeDataType xNodeDataType = null;
    
    switch (binaryTypeIndication) {
    case BOOLEAN_TRUE:
      xNodeDataType = XNodeDataType.BOOLEAN;
      break;

    case BOOLEAN_FALSE:
      xNodeDataType = XNodeDataType.BOOLEAN;
      break;

    case TAG:
      xNodeDataType = XNodeDataType.TAG;
      break;

    case POSITIVE_INTEGER:
      xNodeDataType = XNodeDataType.INTEGER;
      break;

    case NEGATIVE_INTEGER:
      xNodeDataType = XNodeDataType.INTEGER;
      break;

    case INTEGER_ZERO:
      xNodeDataType = XNodeDataType.INTEGER;
      break;

    case INTEGER_ONE:
      xNodeDataType = XNodeDataType.INTEGER;
      break;

    case STRING:
      xNodeDataType = XNodeDataType.STRING;
      break;

    case BLOB:
      xNodeDataType = XNodeDataType.BLOB;
      break;

    case INTEGER_TWO:
      xNodeDataType = XNodeDataType.INTEGER;
     break;

    case INTEGER_THREE:
      xNodeDataType = XNodeDataType.INTEGER;
      break;

    case INTEGER_FOUR:
      xNodeDataType = XNodeDataType.INTEGER;
      break;

    case INTEGER_MINUS_ONE:
      xNodeDataType = XNodeDataType.INTEGER;
      break;
    }
    
    return xNodeDataType;
  }

  @Override
  public Object getData() {
    LOGGER.info("=>");
    
    Object result = null;
    
    bitSequence.setPosition(index);

    // read the data type
    BinaryTypeIndication binaryTypeIndication = BinaryTypeIndication.readFromBitSequence(bitSequence);
    LOGGER.info("binaryTypeIndication=" + binaryTypeIndication.name());
    

    switch (binaryTypeIndication) {
    case BOOLEAN_TRUE:
      result = true;
      break;

    case BOOLEAN_FALSE:
      result = false;
      break;

    case TAG:
      short tagValue = (short) MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
      XTreeTag tag = XTreeTag.getXTreeTagForValue(tagValue);
      result = tag;
      break;

    case POSITIVE_INTEGER:
      int value = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
      result = value;
      break;

    case NEGATIVE_INTEGER:
      value = -MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
      result = value;
      break;

    case INTEGER_ZERO:
      result = 0;
      break;

    case INTEGER_ONE:
      result = 1;
      break;

    case STRING:
      value = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
      char[] charBuf = new char[value];
      for (int i = 0; i < value; i++) {
        charBuf[i] = (char) bitSequence.readFixedSizeInteger(8);
      }
      String s = String.copyValueOf(charBuf);
      result = s;
      break;

    case BLOB:
      int count = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
      byte[] data = new byte[count];
      for (int i = 0; i < count; i++) {
        data[i] = (byte) bitSequence.readFixedSizeInteger(8);
      }
      result = data;
      break;

    case INTEGER_TWO:
      result = 2;
     break;

    case INTEGER_THREE:
      result = 3;
      break;

    case INTEGER_FOUR:
      result = 4;
      break;

    case INTEGER_MINUS_ONE:
      result = -1;
      break;

    }
    
    LOGGER.info("<=");
    return result;
  }

}
