package goedegep.util.xtree.impl.ascii;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

//import goedegep.util.bytesequence.ByteSequence;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.nodebased.NodeBasedXTreeAbstract;
import goedegep.util.xtree.nodebased.NodeBasedXTree;
import goedegep.util.xtree.nodebased.XTreeNode;
import goedegep.util.xtree.serialized.SerializedXTree;

/**
 * This class provides an XTree, or more specific a SerializedXTree, in ASCII Serialized Form.
 */
public class AsciiSerializedXTree extends NodeBasedXTreeAbstract implements NodeBasedXTree, SerializedXTree {
  private static final Logger LOGGER = Logger.getLogger(AsciiSerializedXTree.class.getName());
  
  private ByteBuffer treeData;
  
  private int length;

  /**
   * Constructor, where the content is provided as an XTree in ASCII serialized form.
   * 
   * @param treeData a binary serialized tree
   */
  public AsciiSerializedXTree(byte[] treeData) {
    this.treeData = ByteBuffer.wrap(treeData);
  }
  
  /**
   * Constructor, where the content is provided as an XTree.
   * 
   * @param xtree an XTree with the content for this ASCII Serialized XTree.
   */
  public AsciiSerializedXTree(XTree xtree) {
    treeData = ByteBuffer.allocate(100);
    
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
          treeData.put(AsciiDirection.UP.getValue());
          serializeIntegerToAscii(treeData, upcount);
          upcount = 0;
        } else if (firstChild) {
          treeData.put(AsciiDirection.CHILD.getValue());
          firstChild = false;
        } else {
          treeData.put(AsciiDirection.SIBLING.getValue());
        }
        serializeNodeToAscii(dataType, value, treeData);
        return XTreeNodeVisitResult.CONTINUE;
      }

      @Override
      public XTreeNodeVisitResult postVisitChildren() {
        upcount++;
        return XTreeNodeVisitResult.CONTINUE;
      }
      
    });
    
    treeData.put(AsciiDirection.END.getValue());
    length = treeData.position();
  }
  
  
  /*
   * Implementation of the XTree interface
   * 
   */

  @Override
  public XTreeNode getRoot() {
    throw new UnsupportedOperationException("getRoot is not supported for AsciiSerializedXTree");
  }
  
  
  public void traverse(XTreeNodeVisitor xTreeNodeVisitor) {
    LOGGER.info("=>");

    LOGGER.info("treeData=" + treeData.toString());

    boolean ready = false;
    boolean first = true;
    AsciiDirection asciiDirection;
    treeData.position(0);

    while (!ready) {
      // Read the direction indication.
      asciiDirection = AsciiDirection.getAsciiDirectionForValue(treeData.get());
      LOGGER.info("Direction: " + asciiDirection.name());

      if (asciiDirection.equals(AsciiDirection.UP)) {
        int upcount = deserializeIntegerFromAscii(treeData);
        while (upcount-- > 0) {
          LOGGER.info("Going up ");
          xTreeNodeVisitor.postVisitChildren();
        }
        asciiDirection = AsciiDirection.SIBLING;
      } else if (asciiDirection.equals(AsciiDirection.END)) {
        ready = true;
      }


      if (!ready) {
        // Read a node. First its type and if applicable its value.
        AsciiTypeIndication asciiTypeIndication = AsciiTypeIndication.getAsciiTypeIndicationForValue(treeData.get());
        LOGGER.info("asciiTypeIndication=" + asciiTypeIndication.name());
        if (asciiDirection == AsciiDirection.CHILD) {
          if (!first) {
            xTreeNodeVisitor.preVisitChildren();
          } else {
            first = false;
          }
        }

        switch (asciiTypeIndication) {
        case BOOLEAN_TRUE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.BOOLEAN, true);
          break;

        case BOOLEAN_FALSE:
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.BOOLEAN, false);
          break;

        case TAG:
          short tagValue = (short) deserializeIntegerFromAscii(treeData);
          XTreeTag tag = XTreeTag.getXTreeTagForValue(tagValue);
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.TAG, tag);
          break;

        case INTEGER:
          int value = deserializeIntegerFromAscii(treeData);
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.INTEGER, value);
          break;

        case STRING:
          int stringLength = deserializeIntegerFromAscii(treeData);
          char[] charBuf = new char[stringLength];
          for (int i = 0; i < stringLength; i++) {
            charBuf[i] = (char) treeData.get();
          }
          String s = String.copyValueOf(charBuf);
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.STRING, s);
          break;

        case BLOB:
          int count = deserializeIntegerFromAscii(treeData);
          byte[] data = new byte[count];
          for (int i = 0; i < count; i++) {
            data[i] = treeData.get();
          }
          xTreeNodeVisitor.visitTreeItem(XNodeDataType.BLOB, data);
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

  
  private void serializeNodeToAscii(XNodeDataType dataType, Object value, ByteBuffer treeData) {
    switch (dataType) {
    case TAG:
      treeData.put(AsciiTypeIndication.TAG.getValue());
      serializeIntegerToAscii(treeData, (int) value);
      break;
      
    case BOOLEAN:
      boolean booleanValue = (boolean) value;
      if (booleanValue) {
        treeData.put(AsciiTypeIndication.BOOLEAN_TRUE.getValue());
      } else {
        treeData.put(AsciiTypeIndication.BOOLEAN_FALSE.getValue());
      }
      break;
      
    case INTEGER:
      treeData.put(AsciiTypeIndication.INTEGER.getValue());
      serializeIntegerToAscii(treeData, (int) value);
      break;
      
    case STRING:
      treeData.put(AsciiTypeIndication.STRING.getValue());
      String s = (String) value;
      serializeIntegerToAscii(treeData, s.length());
      for (int i = 0; i < s.length(); i++) {
        treeData.put((byte) s.charAt(i));
      }
      break;
      
    case BLOB:
      treeData.put(AsciiTypeIndication.BLOB.getValue());
      byte[] blob = (byte[]) value;
      serializeIntegerToAscii(treeData, blob.length);
      for (int i = 0; i < blob.length; i++) {
        treeData.put(blob[i]);
      }
      break;
      
    default:
      throw new RuntimeException("Illegal node data type, type=" + dataType.name());
    }
    
  }
  
  /**
   * Write an integer value, in ASCII serialized form, to a byteSequence.
   * @param value the value to be written
   * @param buf
   * @param startIndex
   * @return
   */
  private void serializeIntegerToAscii(ByteBuffer byteSequence, int value) {
    byte[]    numberBuf = new byte[10];   // long enough for max C long = 2147483647.
    if (value < 0)
    {
      byteSequence.put((byte) '-');    /* negative value */
      value = -value;
    }
    
    int charCount = 0;
    while ((value > 0)  ||  (charCount == 0))
    {
      numberBuf[charCount++] = (byte) ('0' + (value % 10));
      value = value / 10;
    }

    while (--charCount >= 0)
    {
      byteSequence.put((numberBuf[charCount]));    /* the digits. */
    }      
  }

  /**
   * Read an integer value, in ASCII serialized form, from a byteSequence.
   * 
   * @param byteSequence the ByteSequence from which an integer value will be read.
   * @return the integer value read from {@code byteSequence}.
   */
  private int deserializeIntegerFromAscii(ByteBuffer byteSequence) {
    int value = 0;
    int sign = 1;
    int byteValue;

    byteValue = byteSequence.get();  
    if (byteValue == '-')
    {
        sign = -sign;
    } else {
      byteSequence.position(byteSequence.position() - 1);
    }
    
    while ( (byteValue = byteSequence.get()) >= '0'  &&  byteValue <= '9')
    {
        value *= 10;
        value += byteValue - '0';
    }
    byteSequence.position(byteSequence.position() - 1);
    
    value = sign * value;
    
    return value;
  }


  /*
   * Implementation of SerializedXTree
   */
  
  @Override
  public byte[] getSerializedTreeData() {
    if (length == 0) {
      return new byte[0];
    }
    
    byte[] result = Arrays.copyOf(treeData.array(), length);
        
    LOGGER.fine("<= result.length=" + result.length);
    return result;
  }

}
