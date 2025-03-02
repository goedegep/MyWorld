package goedegep.util.xtree.impl.asciitest;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import goedegep.util.bytearray.ByteArrayUtils;
import goedegep.util.xtree.impl.ascii.AsciiSerializedXTree;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTree;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;

public class AsciiSerializedXTreeTest {
  private static String NEW_LINE = System.getProperty("line.separator");
    
  private static final int INTEGER_NODE_VALUE = 0xa987; // = 43399
  
  private static final byte[] SERIALIZED_TREE_WITH_INTEGER_NODE = {
    (byte) '-',    // AsciiDirection.CHILD
    (byte) 'I',    // AsciiTypeIndication.INTEGER
    (byte) '4',    // First digit of 43399
    (byte) '3',    // Second digit of 43399
    (byte) '3',    // Third digit of 43399
    (byte) '9',    // Fouth digit of 43399
    (byte) '9',    // Fifth digit of 43399
    (byte) '!',    // AsciiDirection.END
  };

  
  /**
   * Test serializing a tree with a single integer node.
   */
  @Test
  public void testSerializingTreeWithIntegerNode() {
    DefaultMutableXTree tree = new DefaultMutableXTree();
    tree.setRoot(new DefaultMutableXTreeIntegerNode(INTEGER_NODE_VALUE));
    AsciiSerializedXTree binarySerializedXTree = new AsciiSerializedXTree(tree);
    byte[] serializedTree = binarySerializedXTree.getSerializedTreeData();
    assertTrue(Arrays.equals(SERIALIZED_TREE_WITH_INTEGER_NODE, serializedTree), "Wrong serialized data, expected:" + NEW_LINE +
        ByteArrayUtils.toCharString(SERIALIZED_TREE_WITH_INTEGER_NODE) + NEW_LINE +
        "was:" + NEW_LINE +
        ByteArrayUtils.toCharString(serializedTree)
        );
  }

}
