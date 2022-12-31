package goedegep.util.xtree.defaultmutablextree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

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
    assertEquals("Problem in createSimpleTree", SIMPLE_TREE_TEXT, tree.toString());
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
    assertNull("Root not null", tree.getRoot());
    assertEquals("Print output not empty", "", tree.toString());
    assertEquals("Wrong number of top level nodes", 0, tree.getNumberOfChildren(null));
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
    assertEquals("Wrong number of top level nodes", 1, tree.getNumberOfChildren(null));
    assertEquals("Print output not correct", printedTree, tree.print(null, true, true));

    node = tree.getRoot();  // node = I: 4
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 4, node.getIntegerData());
    assertNull("Wrong structure; unexpected sibling", node.getNextSibling());
    assertFalse("Wrong structure; unexpected sibling", node.hasSibling());
    assertEquals("Wrong number of children", 5, tree.getNumberOfChildren(node));
    assertEquals("Wrong number of remaining siblings", 0, tree.getNumberOfRemainingSiblings(node));
    assertEquals("Wrong child index", 0, tree.getChildIndex(node));
    
    node = (MutableXTreeNode) node.getFirstChild();  // node = I: 5
    assertNotNull("Wrong structure, missing child", node);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 5, node.getIntegerData());
    assertEquals("Wrong number of remaining siblings", 4, tree.getNumberOfRemainingSiblings(node));

    node2 = (MutableXTreeNode) node.getNextSibling();  // node = G: TAG_QUERY_INDEX
    assertNotNull("Wrong structure, missing sibling", node2);
    assertEquals("Wrong node type", XNodeDataType.TAG, node2.getDataType());
    assertEquals("Wrong node value", XTreeTag.QUERY_INDEX, node2.getTagData());
    assertFalse("Wrong structure; unexpected child", node2.hasChild());

    node2 = (MutableXTreeNode) node2.getNextSibling();  // node = B: true
    assertNotNull("Wrong structure, missing sibling", node2);
    assertEquals("Wrong node type", XNodeDataType.BOOLEAN, node2.getDataType());
    assertEquals("Wrong node value", true, node2.getBooleanData());
    assertFalse("Wrong structure; unexpected child", node2.hasChild());

    node2 = (MutableXTreeNode) node2.getNextSibling();  // node = S: "XTree string node test"
    assertNotNull("Wrong structure, missing sibling", node2);
    assertEquals("Wrong node type", XNodeDataType.STRING, node2.getDataType());
    assertEquals("Wrong node value", "XTree string node test", node2.getStringData());
    assertFalse("Wrong structure; unexpected child", node2.hasChild());
    assertEquals("Wrong number of remaining siblings", 1, tree.getNumberOfRemainingSiblings(node2));
    assertEquals("Wrong child index", 3, tree.getChildIndex(node2));

    node2 = (MutableXTreeNode) node2.getNextSibling();
    assertNotNull("Wrong structure, missing sibling", node2);
    assertEquals("Wrong node type", XNodeDataType.BLOB, node2.getDataType());
    assertEquals("Wrong node value", rawData, node2.getBlobData());
    assertEquals("Wrong data size", rawData.length, node2.getDataSize());
    assertFalse("Wrong structure; unexpected child", node2.hasChild());
    assertFalse("Wrong structure; unexpected sibling", node2.hasSibling());

    node = (MutableXTreeNode) node.getFirstChild();
    assertNotNull("Wrong structure, missing child", node);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 6, node.getIntegerData());
    assertFalse("Wrong structure; unexpected child", node.hasChild());
    node = (MutableXTreeNode) node.getNextSibling();
    assertNotNull("Wrong structure, missing sibling", node);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 1000, node.getIntegerData());
    assertFalse("Wrong structure; unexpected child", node.hasChild());
    node = (MutableXTreeNode) node.getNextSibling();
    assertNotNull("Wrong structure, missing sibling", node);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 10000, node.getIntegerData());
    assertFalse("Wrong structure; unexpected child", node.hasChild());
    node = (MutableXTreeNode) node.getNextSibling();
    assertNotNull("Wrong structure, missing sibling", node);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 100000, node.getIntegerData());
    assertNull("Wrong structure; unexpected child", node.getFirstChild());
    assertFalse("Wrong structure; unexpected child", node.hasChild());
    assertNull("Wrong structure; unexpected last child", node.getLastChild());
    assertNull("Wrong structure; unexpected sibling", node.getNextSibling());
    assertFalse("Wrong structure; unexpected sibling", node.hasSibling());
    
    // walk back up.
    assertTrue("Wrong structure; missing parent", node.hasParent());
    node = (MutableXTreeNode) node.getParent();
    assertNotNull("Wrong structure; missing parent", node);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 5, node.getIntegerData());
    node2 = (MutableXTreeNode) node.getLastChild();
    assertNotNull("Wrong structure; missing (last) child", node2);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node2.getDataType());
    assertEquals("Wrong node value", 100000, node2.getIntegerData());
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getLastSibling();
    assertNotNull("Wrong structure; missing (last) sibling", node2);
    assertEquals("Wrong node type", XNodeDataType.INTEGER, node.getDataType());
    assertEquals("Wrong node value", 100000, node.getIntegerData());
    node = (MutableXTreeNode) node.getLastSibling();
    assertNull("Unexpected 'last sibling'", node);
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
    assertEquals("Wrong value obtained", 98765, node.getIntegerChildData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_INDEX, node.getTagChildData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", false, node.getBooleanChildData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "XTree string node test", node.getStringChildData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", rawData, node.getBlobChildData());
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
    assertEquals("Wrong value obtained", 2, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_WHERE, node.getTagData());
    node =(MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", true, node.getBooleanData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "The new string value", node.getStringData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", rawDataNew, node.getBlobData());
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
    tree.insertTagSibling((MutableXTreeNode) node.getFirstChild(), XTreeTag.QUERY_WHERE);
    ((MutableXTreeNode) node.getLastChild()).appendTagSibling(XTreeTag.QUERY_INDEX);
    
    tree.insertBooleanSibling((MutableXTreeNode) node.getFirstChild(), true);
    ((MutableXTreeNode) node.getLastChild()).appendBooleanSibling(false);
    
    tree.insertIntegerSibling((MutableXTreeNode) node.getFirstChild(), 444);
    ((MutableXTreeNode) node.getLastChild()).appendIntegerSibling(8642);
    
    tree.insertStringSibling((MutableXTreeNode) node.getFirstChild(), "Lower First");
    ((MutableXTreeNode) node.getLastChild()).appendStringSibling("Lower Last");
    
    tree.insertBlobSibling((MutableXTreeNode) node.getFirstChild(), rawData2);
    ((MutableXTreeNode) node.getLastChild()).appendBlobSibling(rawData1);
     
    // Check at top level.
    node = tree.getRoot();
    assertEquals("Wrong value obtained", rawData1, node.getBlobData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "First", node.getStringData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", 555, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", false, node.getBooleanData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_INDEX, node.getTagData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_WHERE, node.getTagData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_RESULT_ROOT, node.getTagData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", true, node.getBooleanData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", 9753, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "Last", node.getStringData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", rawData2, node.getBlobData());
    
    // Check at lower level.
    node = (MutableXTreeNode) node.getFirstChild();
    assertEquals("Wrong value obtained", rawData2, node.getBlobData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "Lower First", node.getStringData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", 444, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", true, node.getBooleanData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_WHERE, node.getTagData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_RANGE, node.getTagData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_INDEX, node.getTagData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", false, node.getBooleanData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", 8642, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "Lower Last", node.getStringData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", rawData1, node.getBlobData());
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
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getFirstChild();
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
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getFirstChild();
    assertEquals("Wrong value obtained", 1000, node.getIntegerData());
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
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getLastChild();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getLastChild();
    assertEquals("Wrong value obtained", 10000, node.getIntegerData());
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
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getNextSibling();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getFirstChild();
    assertEquals("Wrong value obtained", 6, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", 10000, node.getIntegerData());
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
    node = (MutableXTreeNode) node.getFirstChild();
    tree.removeNode(node);
    
    node = tree.getRoot();
    node = (MutableXTreeNode) node.getFirstChild();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_INDEX, node.getTagData());
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
    assertEquals("Wrong value obtained", "Second Top Level Node", node.getStringData());
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
    assertEquals("Wrong value obtained", "Second Top Level Node", node.getStringData());
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
    assertEquals("Wrong value obtained", 4, node.getIntegerData());
    node = (MutableXTreeNode) node.getNextSibling();
    assertEquals("Wrong value obtained", "Third Top Level Node", node.getStringData());
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
    
    assertTrue("Diffences in cloned tree", tree.equals(treeCopy));
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
    assertEquals("Wrong value obtained", 5, node.getIntegerData());
    assertNull("Unexpected sibling", node.getNextSibling());
    node = (MutableXTreeNode) node.getFirstChild();
    assertEquals("Wrong value obtained", 6, node.getIntegerData());
    node = (MutableXTreeNode) node.getLastSibling();
    assertEquals("Wrong value obtained", 100000, node.getIntegerData());
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
    node = (MutableXTreeNode) node.getFirstChild();
    node = (MutableXTreeNode) node.getNextSibling();

    MutableXTree subTree = MutableXTreeFactory.createMutableXTree(tree, node, true);

    node = subTree.getRoot();
    assertEquals("Wrong value obtained", XTreeTag.QUERY_INDEX, node.getTagData());
    node = (MutableXTreeNode) node.getLastSibling();
    assertTrue("Wrong value obtained expected" + ByteArrayUtils.toHex(rawData) +
        " but was " + ByteArrayUtils.toHex(node.getBlobData()),
        Arrays.equals(rawData, node.getBlobData()));
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
    MutableXTreeNode node = (MutableXTreeNode) treeOne.getRoot().getFirstChild();
    node = (MutableXTreeNode) node.getNextSibling();

    MutableXTree treeTwo = createSimpleTree();
    
    treeOne.mergeSubtree(null, node, treeTwo);
    assertEquals("Wrong contents of merge tree", printedTree, treeOne.print(null, true, true));
  }
  
  /**
   * Test tree compare (via equals) on equal trees.
   */
  @Test
  public void testComparingEqualTrees() {
    // Create two equal trees.
    XTree treeOne = createSimpleTree();
    XTree treeTwo = createSimpleTree();
    
    assertEquals("Equals returned wrong value", treeOne, treeTwo);
    assertEquals("Equals returned wrong value", treeTwo, treeOne);
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
    assertFalse("Equals returned wrong value", treeOne.equals(treeTwo));
    assertFalse("Equals returned wrong value", treeTwo.equals(treeOne));
    
    // In one tree add a node.
    treeTwo = createSimpleTree();
    ((MutableXTreeNode) treeTwo.getRoot().getLastSibling()).appendIntegerSibling(24);
    assertFalse("Equals returned wrong value", treeOne.equals(treeTwo));
    assertFalse("Equals returned wrong value", treeTwo.equals(treeOne));
    
    // In one tree remove a node.
    treeTwo = createSimpleTree();
    node = (MutableXTreeNode) treeTwo.getRoot().getFirstChild();
    node = (MutableXTreeNode) node.getLastChild();
    treeTwo.removeNode(node);
    assertFalse("Equals returned wrong value", treeOne.equals(treeTwo));
    assertFalse("Equals returned wrong value", treeTwo.equals(treeOne));
    
    // Let one of the tree be empty.
    treeTwo = MutableXTreeFactory.createMutableXTree();
    assertFalse("Equals returned wrong value", treeOne.equals(treeTwo));
    assertFalse("Equals returned wrong value", treeTwo.equals(treeOne));
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
    assertEquals("Wrong filter result", SIMPLE_TREE_TEXT, filteredTree.toString());
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
    System.out.println(filterTree.print(null, true, true));
    System.out.println();
    System.out.println();
    
    // apply the filter and check result.
    MutableXTree filteredTree = tree.filterTree(filterTree);
    System.out.println(filteredTree.print(null, true, true));
    assertEquals("Wrong filter result", SIMPLE_TREE_TEXT, filteredTree.print(null, true, true));
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
