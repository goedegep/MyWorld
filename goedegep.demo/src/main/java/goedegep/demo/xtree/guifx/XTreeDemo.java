package goedegep.demo.xtree.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.xtreeview.XTreeView;
import goedegep.util.text.Indent;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.binary.BinarySerializedXTree;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTree;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeIntegerNode;
//import goedegep.util.xtree.impl.defaultmutable.DefaultMutableXTreeNode;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeFactory;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import goedegep.util.xtree.nodebased.NodeBasedXTree;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class XTreeDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(XTreeDemo.class.getName());
  private final static String NEW_LINE = System.lineSeparator();
  
  private final static String WINDOW_TITLE = "XTree demo";
  
  private MutableXTree tree = null;
  private XTreeView treeView = null;
  private HBox resultHBox = null;

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public XTreeDemo(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    // Create a tree and show it.
    tree = createSimpleTree();
    
    createGUI();
    
    treeView.setRootNode(tree.getRoot());
        
    
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
    
    treeView = new XTreeView();
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
    
    button = new Button("Binary tree");
    button.setOnAction((e) -> runBinaryTreeDemo());
    flowPane.getChildren().add(button);
    
    button = new Button("Walk tree");
    button.setOnAction((e) -> runWalkTreeDemo());
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
    
    MutableXTree treeCopy = MutableXTreeFactory.createMutableXTree(tree);
    XTreeView treeCopyView = new XTreeView();
    treeCopyView.setRootNode(treeCopy.getRoot());
    resultHBox.getChildren().add(treeCopyView);
  }
  
  private void runSubtreeCopyDemo() {
    resultHBox.getChildren().clear();
    
    // Create a subtree starting with the first child of the root node.
    MutableXTree subTree = MutableXTreeFactory.createMutableXTree(tree, (MutableXTreeNode) tree.getRoot().getFirstChild(), false);
    XTreeView subTreeCopyView = new XTreeView();
    subTreeCopyView.setRootNode(subTree.getRoot());
    resultHBox.getChildren().add(subTreeCopyView);
  }
  
  private void runBinaryTreeDemo() {
    resultHBox.getChildren().clear();
    
    NodeBasedXTree binaryTree = new BinarySerializedXTree(tree);
    XTreeView binaryTreeView = new XTreeView();
    binaryTreeView.setRootNode(binaryTree.getRoot());
    resultHBox.getChildren().add(binaryTreeView);
  }
  
  private void runWalkTreeDemo() {
    resultHBox.getChildren().clear();
    StringBuilder buf = new StringBuilder();
    buf.append("<html><head></head><body><pre>");
    
    // Get and print the root of the tree.
    buf.append("<i>").append("Get the root of the tree via tree.getRoot()").append("</i><br/>");
    MutableXTreeNode root = tree.getRoot();
    buf.append("<i>").append("Check the data type, which shall be integer, by comparing root.getDataType() to XNodeDataType.INTEGER").append("</i><br/>");
    if (root.getDataType() == XNodeDataType.INTEGER) {
      buf.append("<i>").append("Obtain the value by calling root.getIntegerData()").append("</i><br/>");
      buf.append("     ").append("<b>").append(root.getIntegerData()).append("</b>").append(NEW_LINE);
    }

    // Go to left most leave and go back up, printing the nodes.
    buf.append("<i>").append("Go to left most leave using node.hasChild() and node.getFirstChild().").append("</i><br/>");
    MutableXTreeNode node = root;
    Indent indent = new Indent(4);
    while (node.hasChild()) {
      buf.append(indent).append("<b>").append("Node has child, going down.").append("</b><br/>");
      indent.increment();
      node = node.getFirstChild();
    }
    buf.append(indent).append("<b>").append("Reached the bottom.").append("</b><br/>");

    buf.append("<i>").append("Go back to the root using node.hasParent() and node.getParent().").append("</i><br/>");
    buf.append(indent).append("<b>").append(node).append("</b><br/>");
    while (node.hasParent()) {
      node = node.getParent();
      indent.decrement();
      buf.append(indent).append("<b>").append(node).append("</b><br/>");
    }
    
    buf.append("<pre></body></html>");
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.loadContent(buf.toString());
    resultHBox.getChildren().add(webView);
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
}
