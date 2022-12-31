package goedegep.util.xtree.impl.binary;

import java.util.logging.Logger;

import goedegep.util.bitsequence.BitSequence;
import goedegep.util.multibyteinteger.MultiByteIntegerUtil;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeNode;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.XTreeNodeAbstract;

public class BinarySerializedXTreeNode extends XTreeNodeAbstract implements XTreeNode {
  private static final Logger LOGGER = Logger.getLogger(BinarySerializedXTreeNode.class.getName());

  private BitSequence bitSequence = null;
  private int index;
  private BinarySerializedXTreeNode parent = null;
  private BinarySerializedXTreeNode firstChild = null;
  private BinarySerializedXTreeNode nextSibling = null;
  
  public BinarySerializedXTreeNode(BitSequence bitSequence, int index) {
    this.bitSequence = bitSequence;
    this.index = index;
  }
  
  public boolean hasParent() {
    return parent != null;
  }
  
  public XTreeNode getParent() {
    return parent;
  }
  
  public void setParent(BinarySerializedXTreeNode parent) {
    this.parent = parent;
  }
  
  public static BinarySerializedXTreeNode create(BitSequence bitSequence, int index) {

    return new BinarySerializedXTreeNode(bitSequence, index);
  }

  @Override
  public boolean hasChild() {
    return firstChild != null;
//    LOGGER.severe("=> " + toString());
//    bitSequence.setIndex(index);
//    BinaryDirection binaryDirection = BinaryDirection.readFromBitSequence(bitSequence);
//    if (binaryDirection == BinaryDirection.CHILD) {
//      LOGGER.severe("<= true");
//      return true;
//    } else {
//      LOGGER.severe("<= false");
//      return false;
//    }
  }

  @Override
  public BinarySerializedXTreeNode getFirstChild() {
    return firstChild;
//    LOGGER.severe("=> " + toString());
//    bitSequence.setIndex(index);
//    BinaryDirection binaryDirection = BinaryDirection.readFromBitSequence(bitSequence);
//    if (binaryDirection == BinaryDirection.CHILD) {
//      getData();  // to let the index skip this node
//      return new BinarySerializedXTreeNode(bitSequence, bitSequence.getIndex());
//    } else {
//      LOGGER.severe("<= null");
//      return null;
//    }
  }
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
  
  public BinarySerializedXTreeNode getLastChild() {
    BinarySerializedXTreeNode node = getFirstChild();

    if (node == null) {
      return null;
    }

    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }
  
  public boolean hasSibling() {
    return nextSibling != null;
  }  
  
  public void setNextSibling(BinarySerializedXTreeNode nextSibling) {
    this.nextSibling = nextSibling;
  }

  @Override
  public BinarySerializedXTreeNode getNextSibling() {
    return nextSibling;
//    LOGGER.severe("=> " + toString());
//    bitSequence.setIndex(index);
//    BinaryDirection binaryDirection = BinaryDirection.readFromBitSequence(bitSequence);
//    if (binaryDirection == BinaryDirection.SIBLING) {
//      getData();  // to let the index skip this node
//      return new BinarySerializedXTreeNode(bitSequence, bitSequence.getIndex());
//    } else {
//      LOGGER.severe("<= null");
//      return null;
//    }
  }
  
  public BinarySerializedXTreeNode appendSibling(BinarySerializedXTreeNode newNode) {
    
    newNode.setParent((BinarySerializedXTreeNode) this.getParent());
    newNode.setNextSibling(this.getNextSibling());
    setNextSibling(newNode);
    
    return newNode;
  }

  @Override
  public XNodeDataType getDataType() {    
//    // skip the direction bits
//    bitSequence.setIndex(index + BinaryDirection.getNrOfBitsBinaryIndicationDirection());
    bitSequence.setIndex(index);

    // read the data type
    BinaryTypeIndication binaryTypeIndication = BinaryTypeIndication.readFromBitSequence(bitSequence);
    LOGGER.severe("binaryTypeIndication=" + binaryTypeIndication.name());
    

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
    LOGGER.severe("=>");
    
    Object result = null;
    
//    // skip the direction bits
//    bitSequence.setIndex(index + BinaryDirection.getNrOfBitsBinaryIndicationDirection());
    
    bitSequence.setIndex(index);

    // read the data type
    BinaryTypeIndication binaryTypeIndication = BinaryTypeIndication.readFromBitSequence(bitSequence);
    LOGGER.severe("binaryTypeIndication=" + binaryTypeIndication.name());
    

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
    
    
    LOGGER.severe("<=");
    return result;
  }

}
