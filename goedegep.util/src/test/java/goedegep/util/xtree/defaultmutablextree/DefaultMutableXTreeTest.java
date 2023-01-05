package goedegep.util.xtree.defaultmutablextree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import goedegep.util.bytearray.ByteArrayUtils;
import goedegep.util.logging.MyLoggingFormatter;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import goedegep.util.xtree.mutable.MutableXTreeFactory;

public class DefaultMutableXTreeTest {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final String SIMPLE_TREE_TEXT = 
      "INTEGER: 4" + NEWLINE +
      "  INTEGER: 5" + NEWLINE +
      "    INTEGER: 6" + NEWLINE +
      "    INTEGER: 1000" + NEWLINE +
      "    INTEGER: 10000" + NEWLINE +
      "    INTEGER: 100000" + NEWLINE +
      "  TAG: QUERY_INDEX (2)" + NEWLINE +
      "  BOOLEAN: TRUE" + NEWLINE +
      "  STRING: XTree string node test" + NEWLINE +
      "  BLOB: 1, 2, 3, 4" + NEWLINE +
      "STRING: Second Top Level Node" + NEWLINE +
      "STRING: Third Top Level Node" + NEWLINE;

  
  @BeforeClass
  public static void setup() {
    logSetup();
  }
  
  @Test
  /**
   * Test createSimpleTree() function.
   * The function createSimpleTree() creates a tree which is used in different tests.
   * This test is actually testing this 'test tree'.
   * 
   */
  public void testCreateSimpleTree() {
    XTree tree = createSimpleTree();
    assertEquals(SIMPLE_TREE_TEXT, tree.toString(), "Problem in createSimpleTree");
  }
  
  /**
   * Test methods on a newly created, empty, tree:
   * getNumberOfChildren()
   * getRoot()
   * print()
   */
  @Test
  public void testEmptyTree() {
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    assertNull(tree.getRoot(), "Root not null");
    assertEquals("", tree.toString(), "Print output not empty");
  }

  /**
   * Test all the basic tree/tree node operations:
   */
  @Test
  public void testBasicTreeOperations() {
    byte[]         rawData = {0x01, 0x02, 0x03, 0x04};
    String printedTree =
        "INTEGER: 4" + NEWLINE +
        "  INTEGER: 5" + NEWLINE +
        "    INTEGER: 6" + NEWLINE +
        "    INTEGER: 1000" + NEWLINE +
        "    INTEGER: 10000" + NEWLINE +
        "    INTEGER: 100000" + NEWLINE +
        "  TAG: QUERY_INDEX (2)" + NEWLINE +
        "  BOOLEAN: TRUE" + NEWLINE +
        "  STRING: XTree string node test" + NEWLINE +
        "  BLOB: 1, 2, 3, 4" + NEWLINE;
    MutableXTreeNode node;      
    MutableXTreeNode node2;
    
    /*
     *Create a tree and fill it with nodes of all supported types, with several levels
     * and some levels with siblings.
     * I: 4
     *   I: 5
     *     I: 6
     *     I: 1000
     *     I: 10000
     *     I: 100000
     *   G: TAG_QUERY_INDEX
     *   B: true
     *   S: "XTree string node test"
     *   D: 0x01 0x02 0x03 0x04
     */
    
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    node = MutableXTreeFactory.createIntegerMutableXTreeNode(4);
    node = tree.setRoot(node);
    node = node.addIntegerChild(5);
    node2 = node.appendTagSibling(XTreeTag.QUERY_INDEX);
    node2 = node2.appendBooleanSibling(true);
    node2 = node2.appendStringSibling("XTree string node test");
    node2.appendBlobSibling(rawData);
    node = node.addIntegerChild(6);
    node = node.appendIntegerSibling(1000);
    node = node.appendIntegerSibling(10000);
    node = node.appendIntegerSibling(100000);
    
    // walk through the tree testing types, values and structure.
    assertEquals(printedTree, tree.print(null, true, true), "Print output not correct");

    node = tree.getRoot();  // node = I: 4
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(4, node.getIntegerData(), "Wrong node value");
    assertNull(node.getNextSibling(), "Wrong structure; unexpected sibling");
    assertFalse(node.hasSibling(), "Wrong structure; unexpected sibling");
    assertEquals(5, node.getNumberOfChildren(), "Wrong number of children");
    assertEquals(0, node.getNumberOfRemainingSiblings(), "Wrong number of remaining siblings");
    assertEquals(-1, node.getChildIndex(), "Wrong child index");
    
    node = node.getFirstChild();  // node = I: 5
    assertNotNull(node, "Wrong structure, missing child");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(5, node.getIntegerData(), "Wrong node value");
    assertEquals(4, node.getNumberOfRemainingSiblings(), "Wrong number of remaining siblings");

    node2 = (MutableXTreeNode) node.getNextSibling();  // node = G: TAG_QUERY_INDEX
    assertNotNull(node2, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.TAG, node2.getDataType(), "Wrong node type");
    assertEquals(XTreeTag.QUERY_INDEX, node2.getTagData(), "Wrong node value");
    assertFalse(node2.hasChild(), "Wrong structure; unexpected child");

    node2 = (MutableXTreeNode) node2.getNextSibling();  // node = B: true
    assertNotNull(node2, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.BOOLEAN, node2.getDataType(), "Wrong node type");
    assertEquals(true, node2.getBooleanData(), "Wrong node value");
    assertFalse(node2.hasChild(), "Wrong structure; unexpected child");

    node2 = (MutableXTreeNode) node2.getNextSibling();  // node = S: "XTree string node test"
    assertNotNull(node2, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.STRING, node2.getDataType(), "Wrong node type");
    assertEquals("XTree string node test", node2.getStringData(), "Wrong node value");
    assertFalse(node2.hasChild(), "Wrong structure; unexpected child");
    assertEquals(1, node2.getNumberOfRemainingSiblings(), "Wrong number of remaining siblings");
    assertEquals(3, node2.getChildIndex(), "Wrong child index");

    node2 = (MutableXTreeNode) node2.getNextSibling();
    assertNotNull(node2, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.BLOB, node2.getDataType(), "Wrong node type");
    assertEquals(rawData, node2.getBlobData(), "Wrong node value");
    assertEquals(rawData.length, node2.getDataSize(), "Wrong data size");
    assertFalse(node2.hasChild(), "Wrong structure; unexpected child");
    assertFalse(node2.hasSibling(), "Wrong structure; unexpected sibling");

    node = node.getFirstChild();
    assertNotNull(node, "Wrong structure, missing child");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(6, node.getIntegerData(), "Wrong node value");
    assertFalse(node.hasChild(), "Wrong structure; unexpected child");
    node = (MutableXTreeNode) node.getNextSibling();
    assertNotNull(node, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(1000, node.getIntegerData(), "Wrong node value");
    assertFalse(node.hasChild(), "Wrong structure; unexpected child");
    node = (MutableXTreeNode) node.getNextSibling();
    assertNotNull(node, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(10000, node.getIntegerData(), "Wrong node value");
    assertFalse(node.hasChild(), "Wrong structure; unexpected child");
    node = (MutableXTreeNode) node.getNextSibling();
    assertNotNull(node, "Wrong structure, missing sibling");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(100000, node.getIntegerData(), "Wrong node value");
    assertNull(node.getFirstChild(), "Wrong structure; unexpected child");
    assertFalse(node.hasChild(), "Wrong structure; unexpected child");
    assertNull(node.getLastChild(), "Wrong structure; unexpected last child");
    assertNull(node.getNextSibling(), "Wrong structure; unexpected sibling");
    assertFalse(node.hasSibling(), "Wrong structure; unexpected sibling");
    
    // walk back up.
    assertTrue(node.hasParent(), "Wrong structure; missing parent");
    node = node.getParent();
    assertNotNull(node, "Wrong structure; missing parent");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(5, node.getIntegerData(), "Wrong node value");
    node2 = (MutableXTreeNode) node.getLastChild();
    assertNotNull(node2, "Wrong structure; missing (last) child");
    assertEquals(XNodeDataType.INTEGER, node2.getDataType(), "Wrong node type");
    assertEquals(100000, node2.getIntegerData(), "Wrong node value");
    node = node.getFirstChild();
    node = (MutableXTreeNode) node.getLastSibling();
    assertNotNull(node2, "Wrong structure; missing (last) sibling");
    assertEquals(XNodeDataType.INTEGER, node.getDataType(), "Wrong node type");
    assertEquals(100000, node.getIntegerData(), "Wrong node value");
    node = (MutableXTreeNode) node.getLastSibling();
    assertNull(node, "Unexpected 'last sibling'");
  }
  
  
  /**
   * Test the get<Type>ChildData methods:
   * getBlobChildData()
   * getBooleanChildData()
   * getIntegerChildData()
   * getStringChildData()
   * getTagChildData()
   */
  @Test
  public void testGetChildDataMethods() {
    byte[]         rawData = {0x01, 0x02, 0x03, 0x04};
    
    /*
     * Create a tree with single child nodes of all supported types.
     * I: 1
     *   I: 98765
     * I: 2
     *   G: TAG_QUERY_INDEX
     * I: 3
     *   B: false
     * I: 4
     *   S: "XTree string node test"
     * I: 5
     *   D: 0x01 0x02 0x03 0x04
     */
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node;
    node = MutableXTreeFactory.createIntegerMutableXTreeNode(1);
    node = tree.setRoot(node);
    node.addIntegerChild(98765);
    node = node.appendIntegerSibling(2);
    node.addTagChild(XTreeTag.QUERY_INDEX);
    node = node.appendIntegerSibling(3);
    node.addBooleanChild(false);
    node = node.appendIntegerSibling(4);
    node.addStringChild("XTree string node test");
    node = node.appendIntegerSibling(5);
    node.addBlobChild(rawData);
    
    // Test all the get<Type>ChildNodeData methods.
    node = tree.getRoot();
    assertEquals(98765, node.getIntegerChildData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_INDEX, node.getTagChildData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(false, node.getBooleanChildData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("XTree string node test", node.getStringChildData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(rawData, node.getBlobChildData(), "Wrong value obtained");
  }
  
  
  /**
   * Test the set<Type>Data methods:
   * setBlobData()
   * setBooleanData()
   * setIntegerData()
   * setStringData()
   * setTagData()
   */
  @Test
  public void testSettingDataMethods() {
    byte[]         rawData = {0x01, 0x02, 0x03, 0x04};
    byte[]         rawDataNew = {0x05, 0x06, 0x07, 0x08, 0x09};
    
    /*
     * Create a tree with nodes of all supported types.
     * I: 98765
     * G: TAG_QUERY_INDEX
     * B: false
     * S: "XTree string node test"
     * D: 0x01 0x02 0x03 0x04
     */
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node;
    node = MutableXTreeFactory.createIntegerMutableXTreeNode(98765);
    node = tree.setRoot(node);
    node = node.appendTagSibling(XTreeTag.QUERY_INDEX);
    node = node.appendBooleanSibling(false);
    node = node.appendStringSibling("XTree string node test");
    node = node.appendBlobSibling(rawData);
    
    /*
     * Change all values in the tree.
     * I: 2
     * G: TAG_QUERY_WHERE
     * B: true
     * S: "The new string value"
     * D: 0x05 0x06 0x07 0x08 0x09
     */
    node = (MutableXTreeNode) tree.getRoot();
    node.setIntegerData(2);
    node = (MutableXTreeNode) node.getNextSibling();
    node.setTagData(XTreeTag.QUERY_WHERE);
    node = (MutableXTreeNode) node.getNextSibling();
    node.setBooleanData(true);
    node = (MutableXTreeNode) node.getNextSibling();
    node.setStringData("The new string value");
    node = (MutableXTreeNode) node.getNextSibling();
    node.setBlobData(rawDataNew);
    
    // Check the values.
    node = (MutableXTreeNode) tree.getRoot();
    assertEquals(2, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_WHERE, node.getTagData(), "Wrong value obtained");
    node =(MutableXTreeNode) node.getNextSibling();
    assertEquals(true, node.getBooleanData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("The new string value", node.getStringData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(rawDataNew, node.getBlobData(), "Wrong value obtained");
  }
  
  
  /**
   * Test node insertion and append methods:
   */
  @Test
  public void testTreeInsertAndAppendMethods() {
    byte[]         rawData1 = {0x01, 0x02, 0x03, 0x04};
    byte[]         rawData2 = {0x05, 0x06, 0x07, 0x08, 0x09};
    
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node;
    
    // Test inserting as root.
    node = tree.insertTagSibling(null, XTreeTag.QUERY_WHERE);
    // For each data type, insert a node at the beginning and append one at the end.
    tree.insertTagSibling(node, XTreeTag.QUERY_INDEX);
    node = node.appendTagSibling(XTreeTag.QUERY_RESULT_ROOT);
    
    tree.insertBooleanSibling(tree.getRoot(), false);
    node = node.appendBooleanSibling(true);
    
    tree.insertIntegerSibling(tree.getRoot(), 555);
    node = node.appendIntegerSibling(9753);
    
    tree.insertStringSibling(tree.getRoot(), "First");
    node = node.appendStringSibling("Last");
    
    tree.insertBlobSibling(tree.getRoot(), rawData1);
    node = node.appendBlobSibling(rawData2);
    
    // Now try the same one level lower (below the last node).
    node.addTagChild(XTreeTag.QUERY_RANGE);
    tree.insertTagSibling(node.getFirstChild(), XTreeTag.QUERY_WHERE);
    ((MutableXTreeNode) node.getLastChild()).appendTagSibling(XTreeTag.QUERY_INDEX);
    
    tree.insertBooleanSibling(node.getFirstChild(), true);
    ((MutableXTreeNode) node.getLastChild()).appendBooleanSibling(false);
    
    tree.insertIntegerSibling(node.getFirstChild(), 444);
    ((MutableXTreeNode) node.getLastChild()).appendIntegerSibling(8642);
    
    tree.insertStringSibling(node.getFirstChild(), "Lower First");
    ((MutableXTreeNode) node.getLastChild()).appendStringSibling("Lower Last");
    
    tree.insertBlobSibling(node.getFirstChild(), rawData2);
    ((MutableXTreeNode) node.getLastChild()).appendBlobSibling(rawData1);
     
    // Check at top level.
    node = tree.getRoot();
    assertEquals(rawData1, node.getBlobData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("First", node.getStringData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(555, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(false, node.getBooleanData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_INDEX, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_WHERE, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_RESULT_ROOT, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(true, node.getBooleanData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(9753, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Last", node.getStringData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(rawData2, node.getBlobData(), "Wrong value obtained");
    
    // Check at lower level.
    node = node.getFirstChild();
    assertEquals(rawData2, node.getBlobData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Lower First", node.getStringData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(444, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(true, node.getBooleanData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_WHERE, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_RANGE, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(XTreeTag.QUERY_INDEX, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(false, node.getBooleanData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(8642, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Lower Last", node.getStringData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(rawData1, node.getBlobData(), "Wrong value obtained");
  }
  
  /**
   * Test removing first node at a lowest level:
   * removeNode()
   */
  @Test
  public void testRemovingFirstNodeAtLowestLevel() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    node = tree.getRoot();
    node = node.getFirstChild();
    node = node.getFirstChild();
    tree.removeNode(node);

    /*
     * Tree should now be as follows.
     * I: 4
     *   I: 5
     *     I: 1000
     *     I: 10000
     *     I: 100000
     *   G: TAG_QUERY_INDEX
     *   B: true
     *   S: "XTree string node test"
     *   D: 0x01 0x02 0x03 0x04
     * S: "Second Top Level Node"
     * S: "Third Top Level Node"
     */
    
    node = tree.getRoot();
    node = node.getFirstChild();
    node = node.getFirstChild();
    assertEquals(1000, node.getIntegerData(), "Wrong value obtained");
  }
  
  /**
   * Test removing last node at a lowest level:
   * removeNode()
   */
  @Test
  public void testRemovingLastNodeAtLowestLevel() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    node = tree.getRoot();
    node = node.getFirstChild();
    node = (MutableXTreeNode) node.getLastChild();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = node.getFirstChild();
    node = (MutableXTreeNode) node.getLastChild();
    assertEquals(10000, node.getIntegerData(), "Wrong value obtained");
  }
  
  
  /**
   * Test removing middle node at a lowest level:
   * removeNode()
   */
  @Test
  public void testRemovingMiddleNodeAtLowestLevel() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    node = tree.getRoot();
    node = node.getFirstChild();
    node = node.getFirstChild();
    node = (MutableXTreeNode) node.getNextSibling();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = node.getFirstChild();
    node = node.getFirstChild();
    assertEquals(6, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(10000, node.getIntegerData(), "Wrong value obtained");
  }
  
  
  /**
   * Test removing a node with a subtree:
   * removeNode()
   */
  @Test
  public void testRemovingSubtree() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    node = tree.getRoot();
    node = node.getFirstChild();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = node.getFirstChild();
    assertEquals(XTreeTag.QUERY_INDEX, node.getTagData(), "Wrong value obtained");
  }
  
  
  /**
   * Test removing first node at top level (i.e. the root):
   * removeNode()
   */
  @Test
  public void testRemovingFirstTopLevelNode() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    tree.removeNode(tree.getRoot());
    
    node = tree.getRoot();
    assertEquals(node.getStringData(), "Second Top Level Node", "Wrong value obtained");
  }
  
  /**
   * Test removing last node at top level.
   * removeNode()
   */
  @Test
  public void testRemovingLastTopLevelNode() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    node = tree.getRoot();
    node = (MutableXTreeNode) node.getLastSibling();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = (MutableXTreeNode) node.getLastSibling();
    assertEquals(node.getStringData(), "Second Top Level Node", "Wrong value obtained");
  }
  
  
  /**
   * Test removing middle node at top level:
   * removeNode()
   */
  @Test
  public void testRemovingMiddleTopLevelNode() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node;
    
    node = tree.getRoot();
    node = (MutableXTreeNode) node.getNextSibling();
    tree.removeNode(node);
    
    node = tree.getRoot();
    assertEquals(4, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals(node.getStringData(), "Third Top Level Node", "Wrong value obtained");
  }
  

  /**
   * Test creating a copy of a tree:
   * MutableXTree() - constructor, called with the tree to be copied.
   * equals()
   * 
   */
  @Test
  public void testTreeCopying() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    XTree treeCopy = MutableXTreeFactory.createMutableXTree(tree);
    
    assertTrue(tree.equals(treeCopy), "Diffences in cloned tree");
  }
  
  /**
   * Test extracting a part of a tree, without siblings.
   * MutableXTree() - constructor, called with the tree to be copied, and a starting node (without siblings).
   */
  @Test
  public void testExtractTreeWithoutSiblings() {
    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node = tree.getRoot();
    node = (MutableXTreeNode) node.getFirstChild();

    MutableXTree subTree = MutableXTreeFactory.createMutableXTree(tree, node, false);

    node = subTree.getRoot();
    assertEquals(5, node.getIntegerData(), "Wrong value obtained");
    assertNull(node.getNextSibling(), "Unexpected sibling");
    node = node.getFirstChild();
    assertEquals(6, node.getIntegerData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getLastSibling();
    assertEquals(100000, node.getIntegerData(), "Wrong value obtained");
  }
 
  /**
   * Test extracting a part of a tree, with siblings.
   * MutableXTree() - constructor, called with the tree to be copied, and a starting node (with siblings).
   */
  @Test
  public void testExtractTreeWithSiblings() {
    byte[]         rawData = {0x01, 0x02, 0x03, 0x04};

    // Create a tree.
    MutableXTree tree = createSimpleTree();
    MutableXTreeNode node = tree.getRoot();
    node = node.getFirstChild();
    node = (MutableXTreeNode) node.getNextSibling();

    MutableXTree subTree = MutableXTreeFactory.createMutableXTree(tree, node, true);

    node = subTree.getRoot();
    assertEquals(XTreeTag.QUERY_INDEX, node.getTagData(), "Wrong value obtained");
    node = (MutableXTreeNode) node.getLastSibling();
    assertTrue(Arrays.equals(rawData, node.getBlobData()), "Wrong value obtained expected" + ByteArrayUtils.toHex(rawData) +
        " but was " + ByteArrayUtils.toHex(node.getBlobData()));
  }
  
  
  /**
   * Test merging a tree into another tree:
   * mergeSubtree()
   */
  @Test
  public void testMergeSubtree() {
    String printedTree =
        "INTEGER: 4" + NEWLINE +
        "  INTEGER: 5" + NEWLINE +
        "    INTEGER: 6" + NEWLINE +
        "    INTEGER: 1000" + NEWLINE +
        "    INTEGER: 10000" + NEWLINE +
        "    INTEGER: 100000" + NEWLINE +
        "  TAG: QUERY_INDEX (2)" + NEWLINE +
        "  INTEGER: 4" + NEWLINE +
        "    INTEGER: 5" + NEWLINE +
        "      INTEGER: 6" + NEWLINE +
        "      INTEGER: 1000" + NEWLINE +
        "      INTEGER: 10000" + NEWLINE +
        "      INTEGER: 100000" + NEWLINE +
        "    TAG: QUERY_INDEX (2)" + NEWLINE +
        "    BOOLEAN: TRUE" + NEWLINE +
        "    STRING: XTree string node test" + NEWLINE +
        "    BLOB: 1, 2, 3, 4" + NEWLINE +
        "  STRING: Second Top Level Node" + NEWLINE +
        "  STRING: Third Top Level Node" + NEWLINE +
        "  BOOLEAN: TRUE" + NEWLINE +
        "  STRING: XTree string node test" + NEWLINE +
        "  BLOB: 1, 2, 3, 4" + NEWLINE +
        "STRING: Second Top Level Node" + NEWLINE +
        "STRING: Third Top Level Node" + NEWLINE;
    
    // Create two equal trees.
    MutableXTree treeOne = createSimpleTree();
    MutableXTreeNode node = treeOne.getRoot().getFirstChild();
    node = (MutableXTreeNode) node.getNextSibling();

    MutableXTree treeTwo = createSimpleTree();
    
    treeOne.mergeSubtree(null, node, treeTwo);
    assertEquals(printedTree, treeOne.print(null, true, true), "Wrong contents of merge tree");
  }
  
  /**
   * Test tree compare (via equals) on equal trees.
   */
  @Test
  public void testComparingEqualTrees() {
    // Create two equal trees.
    XTree treeOne = createSimpleTree();
    XTree treeTwo = createSimpleTree();
    
    assertEquals(treeOne, treeTwo, "Equals returned wrong value");
    assertEquals(treeTwo, treeOne, "Equals returned wrong value");
  }

  /**
   * Test tree compare (via equals) on non equal trees.
   */
  @Test
  public void testComparingNonEqualTrees() {
    // Create two equal trees.
    MutableXTree treeOne = createSimpleTree();
    MutableXTree treeTwo;
    MutableXTreeNode node;
    
    // In one tree change a node value.
    treeTwo = createSimpleTree();
    node = (MutableXTreeNode) treeTwo.getRoot();
    node = (MutableXTreeNode) node.getNextSibling();
    node.setStringData("Changed value");
    assertFalse(treeOne.equals(treeTwo), "Equals returned wrong value");
    assertFalse(treeTwo.equals(treeOne), "Equals returned wrong value");
    
    // In one tree add a node.
    treeTwo = createSimpleTree();
    ((MutableXTreeNode) treeTwo.getRoot().getLastSibling()).appendIntegerSibling(24);
    assertFalse(treeOne.equals(treeTwo), "Equals returned wrong value");
    assertFalse(treeTwo.equals(treeOne), "Equals returned wrong value");
    
    // In one tree remove a node.
    treeTwo = createSimpleTree();
    node = treeTwo.getRoot().getFirstChild();
    node = (MutableXTreeNode) node.getLastChild();
    treeTwo.removeNode(node);
    assertFalse(treeOne.equals(treeTwo), "Equals returned wrong value");
    assertFalse(treeTwo.equals(treeOne), "Equals returned wrong value");
    
    // Let one of the tree be empty.
    treeTwo = MutableXTreeFactory.createMutableXTree();
    assertFalse(treeOne.equals(treeTwo), "Equals returned wrong value");
    assertFalse(treeTwo.equals(treeOne), "Equals returned wrong value");
  }
  
  /**
   * Test filtering of a tree, where the whole tree is returned.
   * filterTree() - using TAG_QUERY_RESULT_ROOT and TAG_QUERY_SUBTREE
   */
  @Test
  public void testFilterTree() {
    MutableXTree tree = createSimpleTree();
    
    // define a simple filter that will return the complete tree.
    MutableXTree filterTree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node = filterTree.setRoot(MutableXTreeFactory.createTagMutableXTreeNode(XTreeTag.QUERY_RESULT_ROOT));
    node.appendTagSibling(XTreeTag.QUERY_SUBTREE);
    
    // apply the filter and check result.
    XTree filteredTree = tree.filterTree(filterTree);
    assertEquals(SIMPLE_TREE_TEXT, filteredTree.toString(), "Wrong filter result");
  }
  
  /**
   * Test filtering of a tree, where a subtree is returned.
   * filterTree() - using TAG_QUERY_RESULT_ROOT and TAG_QUERY_SUBTREE
   */
  @Test
  public void testFilterTree2() {
    MutableXTree tree = createSimpleTree();
    
    // define a simple filter that will return the complete tree.
    MutableXTree filterTree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node = filterTree.setRoot(MutableXTreeFactory.createIntegerMutableXTreeNode(4));
    node = node.addTagChild(XTreeTag.QUERY_RESULT_ROOT);
    node.appendTagSibling(XTreeTag.QUERY_SUBTREE);
    
    // apply the filter and check result.
    MutableXTree filteredTree = tree.filterTree(filterTree);
    assertEquals(SIMPLE_TREE_TEXT, filteredTree.print(null, true, true), "Wrong filter result");
  }
  
  /**
   * Create a simple tree which is used in several tests.
   * 
   * @return a tree with the following content:<pre>
   * I: 4
   *   I: 5
   *     I: 6
   *     I: 1000
   *     I: 10000
   *     I: 100000
   *   G: TAG_QUERY_INDEX
   *   B: true
   *   S: "XTree string node test"
   *   D: 0x01 0x02 0x03 0x04
   * S: "Second Top Level Node"
   * S: "Third Top Level Node"
   * </pre>
   */
  private MutableXTree createSimpleTree() {
    byte[]         rawData = {0x01, 0x02, 0x03, 0x04};
    
    /*
     * Create a tree with nodes of all supported types, with several levels
     * and some levels with siblings.
     * I: 4
     *   I: 5
     *     I: 6
     *     I: 1000
     *     I: 10000
     *     I: 100000
     *   G: TAG_QUERY_INDEX
     *   B: true
     *   S: "XTree string node test"
     *   D: 0x01 0x02 0x03 0x04
     * S: "Second Top Level Node"
     * S: "Third Top Level Node"
     */
    MutableXTree tree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node;      
    MutableXTreeNode node2;     
    node = tree.setRoot(MutableXTreeFactory.createIntegerMutableXTreeNode(4));
    node = node.addIntegerChild(5);
    node2 = node.appendTagSibling(XTreeTag.QUERY_INDEX);
    node2 = node2.appendBooleanSibling(true);
    node2 = node2.appendStringSibling("XTree string node test");
    node2.appendBlobSibling(rawData);
    node = node.addIntegerChild(6);
    node = node.appendIntegerSibling(1000);
    node = node.appendIntegerSibling(10000);
    node = node.appendIntegerSibling(100000);
    
    node = tree.getRoot();
    node = node.appendStringSibling("Second Top Level Node");
    node.appendStringSibling("Third Top Level Node");
    
    return tree;
  }
  
  private static void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(Level.SEVERE);
    
    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(Level.SEVERE);
  }
}
