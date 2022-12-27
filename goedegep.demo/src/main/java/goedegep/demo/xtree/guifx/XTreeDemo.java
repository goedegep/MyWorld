package goedegep.demo.xtree.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.xtreetreeview.XTreeTreeView;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTree;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;
import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
import goedegep.util.xtree.mutable.MutableXTree;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class XTreeDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(XTreeDemo.class.getName());
  
  private final static String WINDOW_TITLE = "XTree demo";
  
  private MutableXTree tree = null;
  private XTreeTreeView treeView = null;
  private HBox resultHBox = null;

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public XTreeDemo(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
    
    // Create a tree and show it.
    tree = createSimpleTree();
    
    createGUI();
    
    treeView.setRootNode(tree.getRoot());
        
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
    //      System.out.print(" " + serializedTree[i]);
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
    
    show();
  }

  /**
   * Create the GUI
   * <p>
   * Top
   *   Left: Sample tree
   *   Right: Test result
   * Bottom
   *   Action buttons
   */
  private void createGUI() {
    VBox mainPanel = new VBox();
    
    HBox hBox = new HBox();
    
    treeView = new XTreeTreeView();
    hBox.getChildren().add(treeView);
    
    resultHBox = new HBox();
    hBox.getChildren().add(resultHBox);
    mainPanel.getChildren().add(hBox);
    
    mainPanel.getChildren().add(createButtonsPanel());
    
    
    Scene scene = new Scene(mainPanel, 1500, 1200);
    setScene(scene);
  }
  
  private Node createButtonsPanel() {
    FlowPane flowPane = new FlowPane();
    flowPane.setHgap(12.0);
    
    Button button = new Button("print");
    button.setOnAction((e) -> runPrintDemo());
    flowPane.getChildren().add(button);
    
    button = new Button("copy");
    button.setOnAction((e) -> runCopyDemo());
    flowPane.getChildren().add(button);
    
    button = new Button("SubtreeCopy");
    button.setOnAction((e) -> runSubtreeCopyDemo());
    flowPane.getChildren().add(button);
    
    return flowPane;
  }
  
  private void runPrintDemo() {
    resultHBox.getChildren().clear();
    
    TextArea printPanel = new TextArea(tree.print(null, true, true));
    resultHBox.getChildren().add(printPanel);
  }
  
  private void runCopyDemo() {
    resultHBox.getChildren().clear();
    
    MutableXTree treeCopy = new DefaultMutableXTree(tree);
    XTreeTreeView treeCopyView = new XTreeTreeView();
    treeCopyView.setRootNode(treeCopy.getRoot());
    resultHBox.getChildren().add(treeCopyView);
  }
  
  private void runSubtreeCopyDemo() {
    resultHBox.getChildren().clear();
    
    // Create a subtree starting with the first child of the root node.
    MutableXTree subTree = new DefaultMutableXTree(tree, tree.getRoot().getFirstChild(), false);
    XTreeTreeView subTreeCopyView = new XTreeTreeView();
    subTreeCopyView.setRootNode(subTree.getRoot());
    resultHBox.getChildren().add(subTreeCopyView);
  }

  /**
   * Create a simple MutableXTree.
   * <p>
   * This method creates a tree with nodes of all supported types, with several levels
   * and some levels with siblings.
   * <pre>
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
   * 
   * @return the newly created simple MutableXTree
   */
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
}
