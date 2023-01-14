package goedegep.util.xtree.impl.binary;

import java.util.logging.Logger;

import goedegep.util.bitsequence.BitSequence;
import goedegep.util.multibyteinteger.MultiByteIntegerUtil;
import goedegep.util.text.Indent;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.nodebased.NodeBasedXTreeAbstract;
import goedegep.util.xtree.nodebased.NodeBasedXTree;
import goedegep.util.xtree.serialized.SerializedXTree;

/**
 * This class provides an XTree, or more specific a SerializedXTree, in Binary Serialized Form.
 */
public class BinarySerializedXTree extends NodeBasedXTreeAbstract implements NodeBasedXTree, SerializedXTree {
  private static final Logger LOGGER = Logger.getLogger(BinarySerializedXTree.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");


  private BitSequence bitSequence;
  
  private BinarySerializedXTreeNode supportTreeRoot = null;

  /**
   * Constructor, where the content is provided as an XTree in Binary serialized form.
   * 
   * @param treeData a binary serialized tree
   */
  public BinarySerializedXTree(byte[] treeData) {
    bitSequence = new BitSequence(treeData);
  }
  
  
  /**
   * Constructor, where the content is provided as an XTree.
   * 
   * @param xTree the XTree for which a BinarySerializedXTree will be constructed.
   */
  public BinarySerializedXTree(XTree xtree) {
    bitSequence = new BitSequence();
    
    xtree.traverse(new XTreeNodeVisitor() {
      boolean firstChild = true;
      int upcount = 0;

      @Override
      public XTreeNodeVisitResult preVisitChildren() {
        firstChild = true;
        return XTreeNodeVisitResult.CONTINUE;
      }

      @Override
      public XTreeNodeVisitResult visitTreeItem(XNodeDataType dataType, Object value) {
        if (upcount != 0) {
          bitSequence.addFixedSizeInteger(BinaryDirection.UP.getValue(), BinaryDirection.getNrOfBitsBinaryIndicationDirection());
          bitSequence.addByteArray(MultiByteIntegerUtil.integerToMultiByteInteger(upcount));
          upcount = 0;
        } else if (firstChild) {
          bitSequence.addFixedSizeInteger(BinaryDirection.CHILD.getValue(), BinaryDirection.getNrOfBitsBinaryIndicationDirection());
          firstChild = false;
        } else {
          bitSequence.addFixedSizeInteger(BinaryDirection.SIBLING.getValue(), BinaryDirection.getNrOfBitsBinaryIndicationDirection());
        }
        serializeNodeDataToBinary(dataType, value, bitSequence);
        return XTreeNodeVisitResult.CONTINUE;
      }

      @Override
      public XTreeNodeVisitResult postVisitChildren() {
        upcount++;
        return XTreeNodeVisitResult.CONTINUE;
      }
      
    });
    
    bitSequence.addFixedSizeInteger(BinaryDirection.END.getValue(), BinaryDirection.getNrOfBitsBinaryIndicationDirection());
  }
  
  /*
   * Implementation of the XTree interface
   * 
   */

  @Override
  public BinarySerializedXTreeNode getRoot() {
    if (supportTreeRoot == null) {
      createSupportTree();
    }
    
    return supportTreeRoot;
  }
  
  private void createSupportTree() {
    LOGGER.info("=>");
    
    LOGGER.info("bitSequence=" + bitSequence.toString());
    
    // visitor info
    BinarySerializedXTreeNode visitorNode = null;
    int visitorUpcount = 0;
    boolean visitorFirstChild = true;

    int myBitsequenceIndex;  // save and restore our index upon calling external methods, as they may change the index in the bitSequence.
    boolean ready = false;
    boolean first = true;
    BinaryDirection binaryDirection;
    bitSequence.setPosition(0);

    while (!ready) {
      // Read the direction indication.
      binaryDirection = BinaryDirection.readFromBitSequence(bitSequence);
      LOGGER.info("Direction: " + binaryDirection.name());
      
      if (binaryDirection.equals(BinaryDirection.UP)) {
        int upcount = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
        while (upcount-- > 0) {
          LOGGER.info("Going up ");
          visitorUpcount++;
        }
        binaryDirection = BinaryDirection.SIBLING;
      } else if (binaryDirection.equals(BinaryDirection.END)) {
        ready = true;
      }
      
      int bitSequenceIndex;
      if (!ready) {
        bitSequenceIndex = bitSequence.getPosition();
        // Read a node. First its type and if applicable its value.
        BinaryTypeIndication binaryTypeIndication = BinaryTypeIndication.readFromBitSequence(bitSequence);
        LOGGER.info("binaryTypeIndication=" + binaryTypeIndication.name());
        if (binaryDirection == BinaryDirection.CHILD) {
          if (!first) {
            visitorFirstChild = true;
          } else {
            first = false;
          }
        }

        switch (binaryTypeIndication) {
        case BOOLEAN_TRUE:
          break;

        case BOOLEAN_FALSE:
          break;

        case TAG:
          MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          break;

        case POSITIVE_INTEGER:
          int value = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          break;

        case NEGATIVE_INTEGER:
          value = -MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          break;

        case INTEGER_ZERO:
          break;

        case INTEGER_ONE:
          break;

        case STRING:
          value = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          for (int i = 0; i < value; i++) {
            bitSequence.readFixedSizeInteger(8);
          }
          break;

        case BLOB:
          int count = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          byte[] data = new byte[count];
          for (int i = 0; i < count; i++) {
            data[i] = (byte) bitSequence.readFixedSizeInteger(8);
          }
          break;

        case INTEGER_TWO:
         break;

        case INTEGER_THREE:
          break;

        case INTEGER_FOUR:
          break;

        case INTEGER_MINUS_ONE:
          break;

        }
        
        if (visitorUpcount != 0) {
          while (visitorUpcount != 0) {
            myBitsequenceIndex = bitSequence.getPosition();
            visitorNode = (BinarySerializedXTreeNode) visitorNode.getParent();
            bitSequence.setPosition(myBitsequenceIndex);
            if (visitorNode == null) {
              throw new RuntimeException("Illegal value for upcount.");
            }
            visitorUpcount--;
          }
        }
        
        myBitsequenceIndex = bitSequence.getPosition();
        BinarySerializedXTreeNode newNode = BinarySerializedXTreeNode.create(bitSequence, bitSequenceIndex);
        LOGGER.info("New node: " + newNode.toString());
        if (supportTreeRoot == null) {
          supportTreeRoot = newNode;
          visitorNode = supportTreeRoot;
        } else if (visitorFirstChild) {
          visitorNode = visitorNode.addChild(newNode);
          visitorFirstChild = false;
        } else {
          visitorNode = visitorNode.appendSibling(newNode);
        }
        bitSequence.setPosition(myBitsequenceIndex);
      }
    }

    LOGGER.info("<=");    
  }
  
  @SuppressWarnings("unused")
  private String printSupportTree() {
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(4);
    
    buf.append(NEW_LINE);
    printSupportTreeNode(buf, indent, supportTreeRoot);
    
    return buf.toString();
  }


  private void printSupportTreeNode(StringBuilder buf, Indent indent, BinarySerializedXTreeNode node) {
    buf.append(indent).append(node.toString()).append(NEW_LINE);
    
    if (node.hasChild()) {
      indent.increment();
      printSupportTreeNode(buf, indent, node.getFirstChild());
      indent.decrement();
    }
    
    while (node.hasSibling()) {
      node = node.getNextSibling();
      buf.append(indent).append(node.toString()).append(NEW_LINE);
    }
  }


  public void traverse(XTreeNodeVisitor xTreeNodeVisitor) {
    LOGGER.info("=>");
    
    LOGGER.info("bitSequence=" + bitSequence.toString());
    
    boolean ready = false;
    boolean first = true;
    BinaryDirection binaryDirection;
    bitSequence.setPosition(0);

    while (!ready) {
      // Read the direction indication.
      binaryDirection = BinaryDirection.readFromBitSequence(bitSequence);
      LOGGER.info("Direction: " + binaryDirection.name());
      
      if (binaryDirection.equals(BinaryDirection.UP)) {
        int upcount = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
        while (upcount-- > 0) {
          LOGGER.info("Going up ");
          xTreeNodeVisitor.postVisitChildren();
        }
        binaryDirection = BinaryDirection.SIBLING;
      } else if (binaryDirection.equals(BinaryDirection.END)) {
        ready = true;
      }
      

      if (!ready) {
        // Read a node. First its type and if applicable its value.
        BinaryTypeIndication binaryTypeIndication = BinaryTypeIndication.readFromBitSequence(bitSequence);
        LOGGER.info("binaryTypeIndication=" + binaryTypeIndication.name());
        if (binaryDirection == BinaryDirection.CHILD) {
          if (!first) {
            xTreeNodeVisitor.preVisitChildren();
          } else {
            first = false;
          }
        }

        switch (binaryTypeIndication) {
        case BOOLEAN_TRUE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.BOOLEAN, true);
          break;

        case BOOLEAN_FALSE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.BOOLEAN, false);
          break;

        case TAG:
          short tagValue = (short) MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          XTreeTag tag = XTreeTag.getXTreeTagForValue(tagValue);
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.TAG, tag);
          break;

        case POSITIVE_INTEGER:
          int value = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, value);
          break;

        case NEGATIVE_INTEGER:
          value = -MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, value);
          break;

        case INTEGER_ZERO:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, 0);
          break;

        case INTEGER_ONE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, 1);
          break;

        case STRING:
          value = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          char[] charBuf = new char[value];
          for (int i = 0; i < value; i++) {
            charBuf[i] = (char) bitSequence.readFixedSizeInteger(8);
          }
          String s = String.copyValueOf(charBuf);
          if (xTreeNodeVisitor.visitTreeItem(XNodeDataType.STRING, s) ==  XTreeNodeVisitResult.TERMINATE) {
            ready = true;
          }
          break;

        case BLOB:
          int count = MultiByteIntegerUtil.parseMultiByteInteger(() -> (byte) bitSequence.readFixedSizeInteger(8));
          byte[] data = new byte[count];
          for (int i = 0; i < count; i++) {
            data[i] = (byte) bitSequence.readFixedSizeInteger(8);
          }
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.BLOB, data);
          break;

        case INTEGER_TWO:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, 2);
         break;

        case INTEGER_THREE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, 3);
          break;

        case INTEGER_FOUR:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, 4);
          break;

        case INTEGER_MINUS_ONE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, -1);
          break;

        }
      }
    }

    LOGGER.info("<=");
  }
  
  /*
   * End of implementation of the XTree interface
   * 
   */

      
  /**
   * Serialize the data of a node to a BitSet.
   * 
   * @param xTree the XTree which contains the node to be serialized.
   * @param node the node, within <b>xTree</b>, which has to be serialized.
   * @param bitSet the BitSet to which the <b>node</b> has to be serialized.
   * @param startIndex the index within <b>bitSet</b> at which the data will be written.
   * @return the index in <b>bitSet</b> of the location after the last bit written.
   */
  private static void serializeNodeDataToBinary(XNodeDataType dataType, Object value, BitSequence bitSequence) {
    LOGGER.info("=>");
    
    BinaryTypeIndication typeIndication;
    
    switch (dataType) {
    case TAG:
      typeIndication = BinaryTypeIndication.TAG;
      bitSequence.addBitSequence(typeIndication.getBitSequence());
      XTreeTag xTreeTag = (XTreeTag) value;
      bitSequence.addByteArray(MultiByteIntegerUtil.integerToMultiByteInteger(xTreeTag.getValue()));
      break;
      
    case BOOLEAN:
      if ((boolean) value) {
        typeIndication = BinaryTypeIndication.BOOLEAN_TRUE;
      } else {
        typeIndication = BinaryTypeIndication.BOOLEAN_FALSE;
      }
      bitSequence.addBitSequence(typeIndication.getBitSequence());
      break;
      
    case INTEGER:
      int integerValue = (int) value;
      switch (integerValue) {
      case -1:
        typeIndication = BinaryTypeIndication.INTEGER_MINUS_ONE;
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        break;
        
      case 0:
        typeIndication = BinaryTypeIndication.INTEGER_ZERO;
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        break;
        
      case 1:
        typeIndication = BinaryTypeIndication.INTEGER_ONE;
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        break;
        
      case 2:
        typeIndication = BinaryTypeIndication.INTEGER_TWO;
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        break;
        
      case 3:
        typeIndication = BinaryTypeIndication.INTEGER_THREE;
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        break;
        
      case 4:
        typeIndication = BinaryTypeIndication.INTEGER_FOUR;
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        break;
        
      default:
        if (integerValue < 0) {
          typeIndication = BinaryTypeIndication.NEGATIVE_INTEGER;
        } else {
          typeIndication = BinaryTypeIndication.POSITIVE_INTEGER;
        }
        bitSequence.addBitSequence(typeIndication.getBitSequence());
        bitSequence.addByteArray(MultiByteIntegerUtil.integerToMultiByteInteger(integerValue));
        break;
      }
      break;
      
    case STRING:
      typeIndication = BinaryTypeIndication.STRING;
      bitSequence.addBitSequence(typeIndication.getBitSequence());
      String s = (String) value;
      bitSequence.addByteArray(MultiByteIntegerUtil.integerToMultiByteInteger(s.length()));
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        LOGGER.fine("Adding STRING character: \'" + c + "\'");
        bitSequence.addFixedSizeInteger(c, 8);
      }
      break;
      
    case BLOB:
      typeIndication = BinaryTypeIndication.BLOB;
      bitSequence.addBitSequence(typeIndication.getBitSequence());
      byte[] blob = (byte[]) value;
      bitSequence.addByteArray(MultiByteIntegerUtil.integerToMultiByteInteger(blob.length));
      for (int i = 0; i < blob.length; i++) {
        bitSequence.addFixedSizeInteger(blob[i], 8);
      }
      break;
      
    default:
    }
    
    LOGGER.fine("<=");
  }

  /*
   * Implementation of SerializedXTree
   */
  
  @Override
  public byte[] getSerializedTreeData() {
    return bitSequence.toByteArray();
  }

  
}
