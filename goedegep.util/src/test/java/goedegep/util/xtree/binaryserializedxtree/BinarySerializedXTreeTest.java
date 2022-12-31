package goedegep.util.xtree.binaryserializedxtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import goedegep.util.bytearray.ByteArrayUtils;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.impl.binary.BinarySerializedXTree;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeFactory;
import goedegep.util.xtree.mutable.MutableXTreeNode;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTree;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeStringNode;

public class BinarySerializedXTreeTest {
  private static String NEW_LINE = System.getProperty("line.separator");
  
  private static final int INTEGER_NODE_VALUE = 0xa987; // = 43399
  
  private static final byte[] SERIALIZED_TREE_WITH_INTEGER_NODE = {
    (byte) 0x1C,   // 00011100: BinaryDirection.CHILD = 00......, BinaryTypeIndication.POSITIVE_INTEGER = ..011...
    (byte) 0x3E,   // 00111110:  0xa987 = 43399 = T0000111 T1010011 F0000010 = .....100 00111110 10011000 00010...
    (byte) 0x98,   // 10011000:
    (byte) 0x16    // 00010110: BinaryDirection.END = .....11., remaining zero bit = .......0
  };
  
  private static final String PRINTED_TREE_WITH_INTEGER_NODE = "INTEGER: 43399" + NEW_LINE;

    
  /**
   * Test construncting a BinarySerializedTree from an XTree with a single Integer node.
   */
  @Test
  public void  testConstructingSerializedTreeWithSingleIntegerNode() {
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    tree.setRoot(MutableXTreeFactory.createIntegerMutableXTreeNode(INTEGER_NODE_VALUE));
    BinarySerializedXTree binarySerializedXTree = new BinarySerializedXTree(tree);
    byte[] serializedTree = binarySerializedXTree.getSerializedTreeData();
    assertTrue("Wrong serialized data, expected:" + NEW_LINE +
        ByteArrayUtils.arrayToBinaryString(SERIALIZED_TREE_WITH_INTEGER_NODE) + NEW_LINE +
        "was:" + NEW_LINE +
        ByteArrayUtils.arrayToBinaryString(serializedTree),
        Arrays.equals(SERIALIZED_TREE_WITH_INTEGER_NODE, serializedTree));
  }
  
  /**
   * Test constructing a BinarySerializedTree from a byte array, representing an XTree with a single Integer node.
   */
  @Test
  public void testDeserializingTreeWithIntegerNode() {
    XTree tree = new BinarySerializedXTree(SERIALIZED_TREE_WITH_INTEGER_NODE);
    String printedTree = tree.toString();
    assertEquals("Wrong result tree", PRINTED_TREE_WITH_INTEGER_NODE, printedTree);
  }
  
  
  /**
   * Test construncting a BinarySerializedTree from an XTree with a several nodes.
   */
  @Test
  public void testConstructingSerializedTreeWithSeveralNodes() {
    /*
     * Create a tree with nodes of all supported types, with several levels
     * and some levels with siblings.
     * S: "name level 1"
     *   I: 1
     * S: "name level 2"
     *   I: 2
     * S: "name level 3"
     *   I: 3
     */
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node;
    
    node = MutableXTreeFactory.createStringMutableXTreeNode("name level 1");
    tree.setRoot(node);
    
    node.addIntegerChild(1);
    node = node.appendStringSibling("name level 2");
    node.addIntegerChild(2);
    node = node.appendStringSibling("name level 3");
    node.addIntegerChild(3);

    BinarySerializedXTree binarySerializedXTree = new BinarySerializedXTree(tree);
    String printedMutableTree = tree.toString();
    String printedBinaryTree = binarySerializedXTree.toString();
    
    assertEquals("Wrong result tree", printedMutableTree, printedBinaryTree);
  }
}
