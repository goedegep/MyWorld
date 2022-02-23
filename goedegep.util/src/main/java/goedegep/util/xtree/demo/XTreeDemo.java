package goedegep.util.xtree.demo;

import java.awt.Point;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.logging.MyLoggingFormatter;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTree;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;

public class XTreeDemo {
  private static final Logger LOGGER = Logger.getLogger(XTreeDemo.class.getName());
  
  private final static Level logLevel = Level.INFO;

  /**
   * @param args
   */
  public static void main(String[] args) {
    logSetup();
    
    // Create a tree and show it.
    MutableXTree tree = createSimpleTree();
    showXTree("Intial Tree", tree);
    
    // Show how to use operations for walking through a tree.
    walkThroughTheSimpleTree(tree);
    
    // Create a copy of the tree and show it.
    LOGGER.info("Going to create a copy of a tree");
    MutableXTree treeCopy = new DefaultMutableXTree(tree);
    showXTree("Tree Copy", treeCopy);
    
    // Create a subtree starting with the first child of the root node.
//    XTree subTree = tree.extractTree(tree.getFirstChildForNode(tree.getRoot()), false);
    MutableXTree subTree = new DefaultMutableXTree(tree, tree.getRoot().getFirstChild(), false);
    showXTree("SubTree", subTree);
    
    //    node = subTree.getRoot();
    //    assertEquals("Wrong value obtained", 5, subTree.getIntegerNodeData(node));
    //    assertNull("Unexpected sibling", subTree.getNextSiblingForNode(node));
    //    node = subTree.getFirstChildForNode(node);
    //    assertEquals("Wrong value obtained", 6, subTree.getIntegerNodeData(node));
    //    node = subTree.getLastSiblingOfNode(node);
    //    assertEquals("Wrong value obtained", 100000, subTree.getIntegerNodeData(node));

    //    XTreeInterface tree = XTreeFactory.createXTree();
    //    byte[]         rawData = {0x01, 0x02, 0x03, 0x04};
    //    
    //    // Get and print the tree type.
    //    short treeType = tree.getTreeType();
    //    System.out.println("Tree type is: " + treeType);
    //    
    //    // Create a simple tree.
    //    XTreeNodeInterface node;
    //    XTreeNodeInterface node2;
    //    
    //    node = tree.addIntegerChild(null, 4);
    //    node = tree.addIntegerChild(node, 5);
    //    node2 = tree.appendTagSibling(node, XTreeNodeInterface.TAG_QUERY_INDEX);
    //    node2 = tree.appendBooleanSibling(node2, true);
    //    node2 = tree.appendStringSibling(node2, "Peter was here.");
    //    node2 = tree.appendBlobSibling(node2, rawData);
    //    node = tree.addIntegerChild(node, 6);
    //    node = tree.appendIntegerSibling(node, 1000);
    //    node = tree.appendIntegerSibling(node, 10000);
    //    node = tree.appendIntegerSibling(node, 100000);
    //    
    //    // Show the tree.
    //    showXTree("Intial Tree", tree);
    //    
    //    // Serialize to binary format and deserialize. Show result.
    //    byte[] serializedTree = tree.serializeToBinary();
    //    System.out.println("Binary serialized tree:");
    //    for (int i = 0; i < serializedTree.length; i++) {
    //    	System.out.print(" " + serializedTree[i]);
    //    }
    //    System.out.println();
    //    XTreeInterface newTree = tree.treeReconstructFromBinary(serializedTree);
    //    showXTree("Tree reconstructed from binary data", newTree);

    // Get byte stream and reconstruct from byte stream.
    //    serializedTree = tree.serializeToAscii();
    //    for (int x = 0; x < serializedTree.length; x++) {
    //      char cc = (char) serializedTree[x];
    //      System.out.print(cc);
    //    }
    //    System.out.println();
    //   
    //    newTree = tree.treeReconstructFromAscii(serializedTree);
    //    showXTree("Reconstructed Tree", newTree);

    /*
    // Get and print the root of the tree.
    XTreeNodeInterface root = tree.getRoot();
    System.out.println("Root node value is: " + tree.getIntegerNodeData(root));  // normally you shouldn't assume this is an Integer node.

    // Get and print the first child of the root.
    node = tree.getFirstChildForNode(root);
    System.out.println("First node value is: " + tree.getIntegerNodeData(node));

    // Go to left most leave and go back up, printing the nodes.
    node = root;
    int level = 0;
    while (tree.doesNodeHaveChild(node)) {
    	System.out.println("Node has child, going down.");
    	node = tree.getFirstChildForNode(node);
    	level++;
    }

    System.out.println("Level = " + level + ", node value is: " + tree.getIntegerNodeData(node));
    while (tree.doesNodeHaveParent(node)) {
      node = tree.getParentForNode(node);
      System.out.println("Level = " + --level + ", node value is: " + tree.getIntegerNodeData(node));
    }

    // Go to left most leave and first print the last node and then all nodes.
    node = root;
    while (tree.doesNodeHaveChild(node)) {
    	System.out.println("Node has child, going down.");
    	node = tree.getFirstChildForNode(node);
    }

    node = tree.getParentForNode(node);
    node = tree.getLastChildForNode(node);
    System.out.println("Via getLastChildForNode(), node value is: " + tree.getIntegerNodeData(node));

    node = tree.getParentForNode(node);
    node = tree.getFirstChildForNode(node);
    int childIndex = 0;
    System.out.println("Index = " + childIndex + ", node value is: " + tree.getIntegerNodeData(node));
    while (tree.doesNodeHaveSibling(node)) {
      node = tree.getNextSiblingForNode(node);
      System.out.println("Index = " + ++childIndex + ", node value is: " + tree.getIntegerNodeData(node));
    }

    // Show usage of getLastSiblingForNode().
    node = tree.getParentForNode(node);
    node = tree.getFirstChildForNode(node);
    node = tree.getLastSiblingOfNode(node);    
    System.out.println("Via getLastSiblingForNode(), node value is: " + tree.getIntegerNodeData(node));


    // Show the node data type and size.
    short nodeDataType;
    nodeDataType = tree.getNodeDataType(node);
    System.out.println("nodeDataType = " + nodeDataType);
    //short dataSize;
    //dataSize = tree.getNodeDataSize(node);
    //System.out.println("nodeDataSize = " + dataSize);

    // Show usage of get data nodes.
    node = tree.getFirstChildForNode(root);  // is node with '5'.
    System.out.println("Tag node value = " + tree.getIntegerNodeData(node));
    node = tree.getNextSiblingForNode(node); // is node with TAG_QUERY_INDEX.
    System.out.println("Tag node value = " + tree.getTagNodeData(node));
    node = tree.getNextSiblingForNode(node); // is node with 'true'.
    System.out.println("Tag node value = " + tree.getBooleanNodeData(node));
    node = tree.getNextSiblingForNode(node); // is node with 'Peter was here.'.
    System.out.println("Tag node value = " + tree.getStringNodeData(node));
    node = tree.getNextSiblingForNode(node); // is node with rawData.
    byte[] buf = tree.getBlobNodeData(node);
    System.out.print("Tag node value = ");
    for (int i = 0; i < buf.length; i++) {
    	if (i != 0) {
        	System.out.print(", ");
    	}
    	System.out.print(String.valueOf(buf[i]));
    }
    System.out.println("");

    // Remove the Show the tree.
    node = tree.getFirstChildForNode(root);  // is node with '5'.
    node = tree.getFirstChildForNode(node);  // is node with '6'.
    tree.removeNode(node);                   // remove this node.
    showXTree("Tree after removing node", tree);

    // Change the type of the tree to SERIALIZED.
    // and .....
    // tree.changeTreeType(XTreeInterface.TREE_TYPE_SERIALIZED_TREE);
     */
  }

  private static void showXTree(String windowTitle, MutableXTree tree) {
    Point swLocation = new Point(100, 100);

    XTreeDialog dlg = new XTreeDialog(windowTitle, tree);
    dlg.setLocation(swLocation);
    dlg.pack();
    dlg.setVisible(true);
  }

  private static MutableXTree createSimpleTree() {
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
    DefaultMutableXTree tree = new DefaultMutableXTree();
    DefaultMutableXTreeNode node;      
    DefaultMutableXTreeNode node2;
    node = tree.setRoot(new DefaultMutableXTreeIntegerNode(4));
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

  private static void walkThroughTheSimpleTree(MutableXTree tree) {
    // get the root of the tree
    MutableXTreeNode root = tree.getRoot();
    LOGGER.info(tree.print(root, false, false));
  }
  
  private static void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(logLevel);
    
    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(Level.INFO);
  }
}
