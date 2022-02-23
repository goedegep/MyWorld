package goedegep.util.xtree.impl.defaultmutable;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeNodeVisitorForPrinting;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.XTreeAbstract;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;

/**
 * This class provides an implementation of a {@code MutableXTree}.
 */
public class DefaultMutableXTree extends XTreeAbstract implements MutableXTree {
  private static final Logger LOGGER = Logger.getLogger(DefaultMutableXTree.class.getName());

  private DefaultMutableXTreeNode root = null;

  static {
    LOGGER.setLevel(Level.SEVERE);
  }
  

  /**
   * Constructor to create an empty tree.
   */
  public DefaultMutableXTree() {
    LOGGER.info("<=>");
  }
  
  /**
   * Constructor, where the content is provided as an XTree.
   * 
   * @param xTree the XTree for which a DefaultMutableXTree will be constructed.
   */
  public DefaultMutableXTree(XTree xtree) {
    
    xtree.traverse(new XTreeNodeVisitor() {
      DefaultMutableXTreeNode node = null;
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
          while (upcount != 0) {
            node = node.getParent();
            if (node == null) {
              throw new RuntimeException("Illegal value for upcount.");
            }
            upcount--;
          }
        }
        
        DefaultMutableXTreeNode newNode = DefaultMutableXTreeNode.create(dataType, value);
        if (root == null) {
          root = newNode;
          node = root;
        } else if (firstChild) {
          node = node.addChild(newNode);
          firstChild = false;
        } else {
          node = node.appendSibling(newNode);
        }
        return XTreeNodeVisitResult.CONTINUE;
      }

      @Override
      public XTreeNodeVisitResult postVisitChildren() {
        upcount++;
        return XTreeNodeVisitResult.CONTINUE;
      }
      
    });
    
  }
    
  /**
   * Create an XTree, which is filled with a copy of a subtree of an existing XTree.
   * 
   * @param tree The XTree of which the new tree will contain a partial copy.
   * @param node The node where the copying will start.
   * @param includeSiblings If true, also the siblings of node will be part of the tree.
   *        Otherwise only the children of node will be part of the tree.
   */
  public DefaultMutableXTree(MutableXTree tree, MutableXTreeNode node, boolean includeSiblings) {
    this();
    
    LOGGER.info("=> (subtree)");
    
    addSubtreeCopy(null, null, false, tree, node, includeSiblings);
    
    LOGGER.info("<= (subtree)");
  }
  
  /*
   * Implementation of the XTree interface
   */

  @Override
  public void traverse(XTreeNodeVisitor xTreeNodeVisitor) {
    
    DefaultMutableXTreeNode node = getRoot();
    
    int level = 0;
    while (node != null) {
      xTreeNodeVisitor.visitTreeItem(node.getDataType(), node.getData());
      
      if (node.hasChild()) {
        node = node.getFirstChild();
        level++;
        xTreeNodeVisitor.preVisitChildren();
      } else if (node.hasSibling()) {
        node = node.getNextSibling();
      } else {
        while ((level > 0) && (node.getParent().getNextSibling() == null)) {
          node = (DefaultMutableXTreeNode) node.getParent();
          level--;
          xTreeNodeVisitor.postVisitChildren();
        }
        if ((level > 0)  &&  node.hasParent()) {
          node = node.getParent().getNextSibling();
          level--;
          xTreeNodeVisitor.postVisitChildren();
        } else {
          node = null;
        }
      }
    }
  }
  
  /*
   * End of implementation of the XTree interface
   */
    
  /*
   * Implementation of the MutableXTree interface
   */
  
  @Override
  public DefaultMutableXTreeNode getRoot() {
    LOGGER.info("=>");
    LOGGER.info("<= " + root);
    return root;
  }
  
  @Override
  public int getNumberOfChildren(MutableXTreeNode node) {
    MutableXTreeNode currentNode;

    if (node == null) {
      currentNode = getRoot();
    } else {
      currentNode = node.getFirstChild();
    }

    if (currentNode == null) {
      return 0;
    }

    int count = 1;
    while (currentNode.hasSibling()) {
      currentNode = currentNode.getNextSibling();
      count++;
    }

    return count;
  }

  @Override
  public int getNumberOfRemainingSiblings(MutableXTreeNode node) {
    if (node == null) {
      throw new IllegalArgumentException("Argument node may not be null");
    }
    
    MutableXTreeNode currentNode = node.getNextSibling();

    if (currentNode == null) {
      return 0;
    }

    int count = 1;
    while (currentNode.hasSibling()) {
      currentNode = currentNode.getNextSibling();
      count++;
    }

    return count;
  }

  @Override
  public int getChildIndex(MutableXTreeNode child) {
    if (child == null) {
      throw new IllegalArgumentException("Argument child may not be null");
    }
    
    MutableXTreeNode parentNode = child.getParent();
    
    MutableXTreeNode currentNode;
    int index = 0;
    
    if (parentNode == null) {
      currentNode = getRoot();
    } else {
      currentNode = parentNode.getFirstChild();
    }
    
    while (!currentNode.equals(child)) {
      currentNode = currentNode.getNextSibling();
      index++;
    }

    return index;
  }
 
  @Override
  public void removeNode(MutableXTreeNode xNode) {
    DefaultMutableXTreeNode defaultMutableXTreeNode = (DefaultMutableXTreeNode) xNode;
    LOGGER.info("=> xNode=" + defaultMutableXTreeNode.nodeToString());

    DefaultMutableXTreeNode currentNode;
    DefaultMutableXTreeNode parent = defaultMutableXTreeNode.getParent();

    if (parent == null) {
      currentNode = getRoot();
    } else {
      currentNode = parent.getFirstChild();
    }

    if (currentNode.equals(xNode)) {
      // removing first child.
      if (parent != null) {
        parent.setFirstChild(defaultMutableXTreeNode.getNextSibling());
      } else {
        root = defaultMutableXTreeNode.getNextSibling();
      }
    } else {
      while (!currentNode.getNextSibling().equals(defaultMutableXTreeNode)) {
        currentNode = currentNode.getNextSibling();
      }
      // Removing non-first child node.
      currentNode.setNextSibling(defaultMutableXTreeNode.getNextSibling());
    }
    
    LOGGER.info("<=");
  }
  
  @Override
  public boolean compareSubtrees(MutableXTreeNode thisStartNode,
      MutableXTree toTree, MutableXTreeNode toStartNode,
      boolean includeSiblings) {
    return compareSubtrees(thisStartNode,
        toTree, toStartNode,
        includeSiblings, false);
  }
  
  @Override
  public MutableXTree filterTree(MutableXTree filter) {
    LOGGER.info("=>");
    MutableXTreeNode filterNode = filter.getRoot();
    MutableXTreeNode sourceNode = getRoot();
    DefaultMutableXTree     resultTree = new DefaultMutableXTree();
    DefaultMutableXTreeNode resultNode = null;
    String    indent = "";

    filterSubTree(filter, filterNode,
                        sourceNode,
                        resultTree, resultNode,
                        false, false, indent);
    
    LOGGER.info("<=");
    return resultTree;
  }
  
  @Override
  public boolean compareNodes(MutableXTreeNode referenceNode,
      MutableXTree secondTree, MutableXTreeNode secondNode) {
    if (referenceNode.getDataType() != secondNode.getDataType()) {
      return false;
    }
    
    switch (referenceNode.getDataType()) {
    case TAG:
      return referenceNode.getTagData() == secondNode.getTagData();
      
    case BOOLEAN:
      return referenceNode.getBooleanData() == secondNode.getBooleanData();

    case INTEGER:
      return referenceNode.getIntegerData() == secondNode.getIntegerData();

    case STRING:
      return ((referenceNode.getStringData()).compareTo(secondNode.getStringData()) == 0);

    case BLOB:
      return Arrays.equals(referenceNode.getBlobData(), secondNode.getBlobData());
    }

    return false;
  }

  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, XTreeTag nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }
  
  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, boolean nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }
  
  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, int nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }


  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, String nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }
    return (child);
  }

  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, byte[] nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, short value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, boolean value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, int value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, String value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, byte[] value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }
  
  @Override
  public void traverse(MutableXTreeNode startNode,
      boolean visitChildren, boolean visitStartNodeSiblings, XTreeNodeVisitor xTreeNodeVisitor) {
    
    MutableXTreeNode node;
    if (startNode != null) {
      node = startNode;
    } else {
      node = getRoot();
    }
    
    int level = 0;
    while (node != null) {
      xTreeNodeVisitor.visitTreeItem(node.getDataType(), node.getData());
      
      if (node.hasChild()) {
        if (visitChildren) {
          node = node.getFirstChild();
          level++;
          xTreeNodeVisitor.preVisitChildren();
        }
      } else if (node.hasSibling()) {
        if (visitStartNodeSiblings  ||  (level > 0)) {
          node = node.getNextSibling();
        }
      } else {
        while ((level > 0) && (node.getParent().getNextSibling() == null)) {
          node = node.getParent();
          level--;
          xTreeNodeVisitor.postVisitChildren();
        }
        if ((level > 0)  &&  (visitStartNodeSiblings || (level > 1))) {
          node = node.getParent().getNextSibling();
          level--;
          xTreeNodeVisitor.postVisitChildren();
        } else {
          node = null;
        }
      }
    }
  }
  

  @Override
  public void print(MutableXTreeNode startNode,
      boolean printChildren,boolean printSiblings, OutputStream outputStream) {
    LOGGER.info("=>");
    
    XTreeNodeVisitorForPrinting visitor = new XTreeNodeVisitorForPrinting(outputStream);
    traverse(startNode, printChildren, printSiblings, visitor);
    
    LOGGER.info("<=");
  }
  
  @Override
  public String print(MutableXTreeNode startNode, boolean printChildren, boolean printSiblings) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    print(startNode, printChildren, printSiblings, stream);

    return stream.toString();
  }


  @Override
  public String nodeToString(MutableXTreeNode node) {
    DefaultMutableXTreeNode defaultMutableTreeNode = (DefaultMutableXTreeNode) node;
    return XTree.nodeToString(node.getDataType(), defaultMutableTreeNode.getData());
  }
  
  /*
   * End of implementation of the MutableXTree interface
   */

 
  
  /**
   * Extra methods for DefaultMutableXTree.
   */

  /**
   * Add a tree as a subtree to another tree. After adding it, the srcTree
   * may no longer be used (it's not a valid tree anymore).
   * The srcTree is added as a child of the parentNode and as sibling of the
   * siblingNode.<br>
   * The siblingNode may be NULL. In this case the srcTree is added as
   * first child of parentNode.<br>
   * If the siblingNode is not NULL, parentNode may be NULL.
   *
   * @param parentNode
   *      Node in tree below which the srcTree will be added.
   * @param siblingNode
   *      Node in tree before or after which the srcTree will be added.
   * @param[in] srcTree
   *      The tree to be added to this tree.
   */
  public void mergeSubtree(DefaultMutableXTreeNode parentNode, DefaultMutableXTreeNode siblingNode, DefaultMutableXTree srcTree) {
    
    if ((parentNode != null)  &&  (siblingNode != null)  &&
        (siblingNode.getParent() !=  parentNode)) {
      throw new IllegalArgumentException("siblingNode is not a child of parentNode");
    }

    if (siblingNode != null) {
      // append after sibling node:
      // - all top level nodes of srcTree will get parentNode as parent
      // - the next sibling of siblingNode will be the root of the srcTree
      // - if siblingNode had a next sibling, this will become the next sibling of the last top level node of the srcTree

      // give the top level nodes the right parent.
      for (DefaultMutableXTreeNode node = srcTree.getRoot(); node != null; node = node.getNextSibling()) {
        node.setParent(siblingNode.getParent());
      }

      DefaultMutableXTreeNode srcTreeLastTopLevelNode = srcTree.getRoot().getLastSibling();
      if (srcTreeLastTopLevelNode == null) {
        srcTreeLastTopLevelNode = srcTree.getRoot();
      }
      srcTreeLastTopLevelNode.setNextSibling(siblingNode.getNextSibling());
      siblingNode.setNextSibling(srcTree.getRoot());
    } else {
      // add as first child of parentNode:
      // - all top level nodes of srcTree will get parentNode as parent
      // - the first child of parentNode will be the root of the srcTree
      // - if parentNode had children, its first child will become the next sibling of the last top level node of the srcTree.
      
      DefaultMutableXTreeNode parentsFirstChild = parentNode.getFirstChild();

      // give the nodes the right parent.
      for (DefaultMutableXTreeNode node = srcTree.getRoot(); node != null; node = node.getNextSibling()) {
        node.setParent(parentNode);
      }
      
      parentNode.setFirstChild(srcTree.getRoot());
      
      if (parentsFirstChild != null) {
        parentNode.getLastChild().setNextSibling(parentsFirstChild);
      }
    }    
  }
  
  /**
   * Add a copy of a (sub)tree to this tree.
   * The (sub)tree is added as a child of the dstParentNode and as sibling of the
   * dstSiblingNode. If insert is TRUE, it is inserted before the dstSiblingNode,
   * else it is appended after it.<br>
   * The dstSiblingNode may be null. In this case the (sub)tree is added as
   * first child of dstParentNode if insert is TRUE, or as last child otherwise.<br>
   * If the dstSiblingNode is not null, parameter dstParentNode is ignored.<br>
   * If both dstParentNode and dstSiblingNode are NULL, the (sub)tree will be
   * added as root.<br>
   * If srcReferenceNode is null, the root of the srcTree is used as
   * srcReferenceNode. This implies that the complete srcTree can be added by setting
   * srcReferenceNode to null and includeSiblings to TRUE.<br>
   * The function recursively copies the nodes, starting at srcReferenceNode,
   * from srcTree to this tree.
   * 
   * @param dstParentNode
   *      Node below which the subtree of srcTree will be added.
   * @param dstSiblingNode
   *      Node before or after which the subtree of srcTree will be added.
   * @param insert
   *      Indicates whether the subtree of srcTree is added before (if TRUE) or
   *      after (if FALSE) dstSiblingNode.
   * @param srcTree
   *      The tree of which a subtree is to be added.
   * @param srcReferenceNode
   *      Starting point of the subtree in srcTree.
   * @param includeSiblings
   *      The new tree will always contain 'srcReferenceNode' and all its
   *      decendents.  If this value is true, the new tree also contains the
   *      siblings of 'srcReferenceNode' and all their decendents.
   */
  public void addSubtreeCopy(DefaultMutableXTreeNode dstParentNode, DefaultMutableXTreeNode dstSiblingNode,
      boolean insert, MutableXTree srcTree, MutableXTreeNode srcReferenceNode,
      boolean includeSiblings) {
    LOGGER.info("=> dstParentNode=" + dstParentNode + ", dstSiblingNode=" + dstSiblingNode +
        ", insert=" + insert + ", srcTree=" + srcTree + ", srcReferenceNode=" + srcReferenceNode);
    
    addSubtreeCopy(dstParentNode, dstSiblingNode,
        insert, srcTree, srcReferenceNode,
        includeSiblings, false);
    
    LOGGER.info("<=");
  }

  

  /**
   * Create a node of type 'TAG' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   * <p>
   * Note that this method operates on the tree and node on a node, so the node doesn't need to have
   * a reference to 
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode insertTagSibling(MutableXTreeNode referenceNode, XTreeTag data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeTagNode(data));    
  }
  
  /**
   * Create a node of type 'BOOLEAN' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode insertBooleanSibling(MutableXTreeNode referenceNode, boolean data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeBooleanNode(data));    
  }
  
  /**
   * Create a node of type 'INTEGER' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode insertIntegerSibling(MutableXTreeNode referenceNode, int data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeIntegerNode(data));    
  }

  /**
   * Create a node of type 'STRING' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode insertStringSibling(MutableXTreeNode referenceNode, String data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeStringNode(data));    
  }
  
  /**
   * Create a node of type 'BLOB' and add this node to the tree as a sibling of a specific node.
   * The node is added BEFORE the specified node.
   *
   * @param referenceNode
   *      The node before which the new node shall be inserted.
   * @param data
   *      The content of the new node.
   *
   * @return The new node, or null if this could not be created.
   */
  public DefaultMutableXTreeNode insertBlobSibling(MutableXTreeNode referenceNode, byte[] data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeBlobNode(data));    
  }
  
  /**
   * Add a node as the previous sibling of a node.
   * 
   * @param referenceNode the node to which newNode has to be added as a previous sibling. If this node is null, the newNode will become the root node.
   * @param newNode the node to be added as a previous sibling of referenceNode.
   * @return newNode
   */
  private DefaultMutableXTreeNode insertSibling(MutableXTreeNode referenceNode, DefaultMutableXTreeNode newNode) {
    DefaultMutableXTreeNode realReferenceNode = (DefaultMutableXTreeNode) referenceNode;

    if (realReferenceNode == null) {
      // Insert as (new) root.
      newNode.setNextSibling(root);
      root = newNode;
    } else {
      newNode.setParent(realReferenceNode.getParent());

      // Find the sibling before the referenceNode.
      DefaultMutableXTreeNode prevSibling;
      if (!realReferenceNode.hasParent()) {
        prevSibling = getRoot();
      } else {
        prevSibling = realReferenceNode.getParent().getFirstChild();
      }
      
      if (prevSibling == realReferenceNode) {
        // Insert as first node as this level.
        if (!realReferenceNode.hasParent()) {
          root = newNode;
        } else {
          realReferenceNode.getParent().setFirstChild(newNode);
        }
      } else {
        while (prevSibling.getNextSibling() != realReferenceNode) {
          prevSibling = prevSibling.getNextSibling();
        }

        prevSibling.setNextSibling(newNode);
      }
      newNode.setNextSibling(realReferenceNode);
    }

    return newNode;
  }
  
  /**
   * Set the root node of the tree.
   * 
   * @param rootNode the new root node of the tree.
   * @return The new root node of the tree.
   */
  public DefaultMutableXTreeNode setRoot(DefaultMutableXTreeNode rootNode) {
    LOGGER.info("=> rootNode=" + rootNode);
    root = rootNode;
    LOGGER.info("<= " + root);
    return root;
  }
  

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }
    
    MutableXTree tree = (MutableXTree) obj;
    return compareSubtrees(getRoot(), tree, tree.getRoot(), true);
  }
  
  /*
   * End of extra methods for DefaultMutableXTree.
   */

  
  /*
   * Internal methods from here.
   */

  private boolean compareSubtrees(MutableXTreeNode thisStartNode,
      MutableXTree toTree, MutableXTreeNode toStartNode,
      boolean includeSiblings, boolean handlingSiblings) {
    if (!compareNodesStructureAndContent(thisStartNode, toTree, toStartNode)) {
      return false;
    }

    if (thisStartNode.hasChild()) {
      if (!compareSubtrees(thisStartNode.getFirstChild(),
          toTree, toStartNode.getFirstChild(), true, false)) {
        return false;
      }
    }

    if (includeSiblings  &&  !handlingSiblings) {
      while (thisStartNode.hasSibling()) {
        thisStartNode = thisStartNode.getNextSibling();
        toStartNode = toStartNode.getNextSibling();
        if (!compareSubtrees(thisStartNode,
            toTree, toStartNode, includeSiblings, true)) {
          return false;
        }
      }
    }

    return true;
  }

  private void addSubtreeCopy(DefaultMutableXTreeNode dstParentNode, DefaultMutableXTreeNode dstSiblingNode,
      boolean insert, MutableXTree srcTree, MutableXTreeNode srcReferenceNode,
      boolean includeSiblings, boolean addingSiblings) {
    LOGGER.info("=> dstParentNode=" + dstParentNode + ", dstSiblingNode=" + dstSiblingNode +
        ", insert=" + insert + ", srcTree=" + srcTree + ", srcReferenceNode=" + srcReferenceNode);
    
    if (srcReferenceNode == null) {
      srcReferenceNode = srcTree.getRoot();
    }
    
    DefaultMutableXTreeNode node = DefaultMutableXTreeNode.createFromXNode(srcTree, srcReferenceNode);
    if (dstSiblingNode != null) {
      if (insert) {
        this.insertSibling(dstSiblingNode, node);
      } else {
        dstSiblingNode.appendSibling(node);
      }
    } else {
      if (dstParentNode == null) {
        root = node;
      } else {
        dstParentNode.addChild(node);
      }
    }

    if (srcReferenceNode.hasChild()) {
      addSubtreeCopy(node, (DefaultMutableXTreeNode) null,
                     insert, srcTree, srcReferenceNode.getFirstChild(), true, false);
    }
    if (includeSiblings  &&  !addingSiblings) {
      while (srcReferenceNode.hasSibling()) {
        srcReferenceNode = srcReferenceNode.getNextSibling();
        addSubtreeCopy(null, node, 
                   false,
                   srcTree, srcReferenceNode, includeSiblings, true);
        node = (DefaultMutableXTreeNode) node.getNextSibling();
      }
    }
    LOGGER.info("<=");
  }
  
  /**
   * Filter a tree.
   * 
   * @param filterTree
   * @param filterNode
   * @param sourceNode
   * @param resultTree
   * @param resultParentNode
   * @param copy
   * @param handlingSiblings
   * @param indent
   * @return
   */
  private boolean filterSubTree(MutableXTree filterTree, MutableXTreeNode filterNode,
                                MutableXTreeNode sourceNode,
                                DefaultMutableXTree resultTree, DefaultMutableXTreeNode resultParentNode,
                                boolean copy, boolean handlingSiblings, String indent) {
    LOGGER.info(indent + "=>" + " copy=" + copy + " handlingSiblings=" + handlingSiblings);
    MutableXTreeNode node = null;
    boolean            indexRange = false;
    int                indexStart = -1;
    int                indexEnd = -1;

    // Note: in the comments below 'copy' is used in several locations. However actual
    //       copying only takes place if parameter 'copy' is true.

    //
    // handle the filterNode
    //

    boolean handled = false;

    MutableXTreeNode treeFilterNode = null;
    boolean            onlySameTrees = false;
    // workaround for not recognizing root
    if (handlingSiblings) {
      if (resultParentNode == null) {
        resultParentNode = (DefaultMutableXTreeNode) resultTree.getRoot();
        if (resultParentNode != null) {
//          addAss = ADD_SIBLING;
        }
      }
    }
    
    // Handle special Query nodes.
    XNodeDataType nodeType = filterNode.getDataType();
    if (nodeType == XNodeDataType.TAG) {
      // Node may be a query node. Handle known query tags.
      XTreeTag tag = filterNode.getTagData();
      switch (tag) {
      case QUERY_SUBTREE:
        // Copy all child subtrees of sourceParentNode to the resultTree
        if (copy) {
          resultTree.addSubtreeCopy(resultParentNode, null,
              true,
              this, node,
              true);
        }
        handled = true;
        break;

      case QUERY_INDEX:
      case QUERY_RANGE:
        // if filterNode is QUERY_INDEX, read the index indexStart and set indexEnd to indexStart (a range of 1).
        // if filterNode is QUERY_RANGE, read the indices indexStart and indexEnd.
        // read next sibling s of filterNode.
        // find occurrences i through j equal to s under sourceParentNode, copy these nodes into
        // the resultTree and recursively call filterSubTree.
        indexRange = true;
        node = filterNode.getFirstChild();
        indexStart = node.getIntegerData();

        if (tag == XTreeTag.QUERY_INDEX) {
          indexEnd = indexStart;
        } else {
          node = node.getNextSibling();
          indexEnd  = node.getIntegerData();
        }

        filterNode = filterNode.getNextSibling();
        break;

      case QUERY_WHERE:
        treeFilterNode = filterNode.getFirstChild();
        filterNode = filterNode.getNextSibling();
        onlySameTrees = true;
        break;

      case QUERY_RESULT_ROOT:
      // After this node is seen filtered nodes must actually be copied to result tree.
      copy = true;
      handled = true;
      LOGGER.info("TAG_QUERY_RESULT_ROOT found, copy set to true");
      break;
      }
    }

    if (!handled && onlySameTrees) {
      MutableXTreeNode    node2 = null;
      for (node = sourceNode;
           node != null;
           node = node.getNextSibling()) {
        // check if a tree UNDER node equals the treeFilterNode-based tree
        for (node2 = node.getFirstChild();
             node2 != null;
             node2 = node2.getNextSibling()) {
          if (((DefaultMutableXTree) filterTree).isSubTree(treeFilterNode, this, node2, "")) {
            DefaultMutableXTreeNode newNode = null;
            if (copy) {
//              newNode = (POIDataTreeRealNode)
//              resultTree.addCopyOfNode(resultParentNode, addAss, sourceTree, node);
            }

            // if filterNode has a child, handle it by recursively calling filterSubTree
            // for this child.
            if (filterNode.hasChild()) {
              filterSubTree(filterTree, filterNode.getFirstChild(),
                  node.getFirstChild(),
                  resultTree, newNode,
                  copy, false, indent + "  ");
            }
          }
        }
      }
    } else if (!handled) {
      // filterNode is not a special query node:
      //   find all nodes with same content in sourceTree, copy them into the
      //   resultTree and recursively call filterSubTree.
      int index = 0;
      for (node = sourceNode;
           node != null;
           node = node.getNextSibling()) {
        if (!indexRange  ||
            (index >= indexStart  &&  index <= indexEnd)) {
          if (filterTree.compareNodes(filterNode, this, node)) {
            DefaultMutableXTreeNode newNode = null;
            if (copy) {
//              newNode = (POIDataTreeRealNode) resultTree.addCopyOfNode(resultParentNode, addAss, sourceTree, node);
            }

            // if filterNode has a child, handle it by recursively calling filterSubTree
            // for this child.
            if (filterNode.hasChild()) {
              filterSubTree(filterTree, filterNode.getFirstChild(),
                  node.getFirstChild(),
                  resultTree, newNode,
                  copy, false, indent + "  ");
            }
          }
        }
        index++;
      }
    }

    // if we are not in 'handlingSiblings mode', handle all siblings by recursively
    // calling filterSubtree for each sibling, using 'handlingSiblings mode'.
    if (!handlingSiblings) {
      while (filterNode.hasSibling()) {
        filterNode = filterNode.getNextSibling();
        copy = filterSubTree(filterTree, filterNode,
                             sourceNode,
                             resultTree, resultParentNode,
                             copy, true, indent + "  ");
      }
    }
    
    LOGGER.info(indent + "<=" + copy);

    return copy;
  }
  
  private boolean compareNodesStructureAndContent(MutableXTreeNode referenceNode,
      MutableXTree secondTree, MutableXTreeNode secondNode) {
    if (((referenceNode == null) && (secondNode != null))  ||
        ((referenceNode != null) && (secondNode == null))  ||
        (referenceNode.hasChild()  &&  !secondNode.hasChild())  ||
        (!referenceNode.hasChild()  &&  secondNode.hasChild())  ||
        (referenceNode.hasSibling()  &&  !secondNode.hasSibling())  ||
        (!referenceNode.hasSibling()  &&  secondNode.hasSibling())
        ) {
//      System.out.println(indent + "   Nodes not equal as structure differs.");
      return false;
    } else {
      return compareNodes(referenceNode, secondTree, secondNode);
    }
  }


  
  /**
   * Internal methods from here.
   */
  
  private boolean doesNodeContainValue(MutableXTreeNode node, XTreeTag value) {
    return (node.getDataType() == XNodeDataType.TAG &&
        node.getTagChildData() == value);  
  }
  
  private boolean doesNodeContainValue(MutableXTreeNode node, boolean value) {
    return (node.getDataType() == XNodeDataType.BOOLEAN &&
        node.getBooleanData() == value);    
  }
  
  private boolean doesNodeContainValue(MutableXTreeNode node, int value) {
    return (node.getDataType() == XNodeDataType.INTEGER &&
        node.getIntegerData() == value);   
  }
  
  private boolean doesNodeContainValue(MutableXTreeNode node, String value) {
    return (node.getDataType() == XNodeDataType.STRING &&
        node.getStringData().compareTo(value) == 0);
  }
  
  private boolean doesNodeContainValue(MutableXTreeNode node, byte[] value) { 
    // Not OK if type is not BLOB.
    if (node.getDataType() != XNodeDataType.BLOB) {
      return false;
    }
      
    // Not OK if lengths differ.
    byte[] nodeValue = node.getBlobData();
    if (nodeValue.length == value.length) {
      return false;
    }
    
    // Not OK if a byte differs.
    for (int i = 0; i < nodeValue.length; i++) {
      if (nodeValue[i] != value[i]) {
        return false;
      }
    }
    
    return false;
  }


  /**
   * Check if firstNode-based tree is contained (i.e. part of structure) in the secondNode-based tree.
   */
  private boolean isSubTree(MutableXTreeNode firstTreeNode,
      MutableXTree secondTree, MutableXTreeNode secondTreeNode,
                            String indent) {
//    System.out.println(indent + "                                                        => isSubTree" +
//                       nodeToString(firstTreeNode) + " vs. " + secondTree.nodeToString(secondTreeNode));

    boolean noDifferenceFound = compareNodes(firstTreeNode, secondTree, secondTreeNode);
    if (noDifferenceFound) {
      int     firstTreeCurrNodeNrChildren  = getNumberOfChildren(firstTreeNode);
      int     secondTreeCurrNodeNrChildren = secondTree.getNumberOfChildren(secondTreeNode);
      int     firstTreeCurrNodeNrSiblingsRemaining  = getNumberOfRemainingSiblings(firstTreeNode);
      int     secondTreeCurrNodeNrSiblingsRemaining = secondTree.getNumberOfRemainingSiblings(secondTreeNode);

      if ((firstTreeCurrNodeNrSiblingsRemaining <= secondTreeCurrNodeNrSiblingsRemaining) &&
          (firstTreeCurrNodeNrChildren <= secondTreeCurrNodeNrChildren)) {
        if (firstTreeCurrNodeNrChildren > 0) {
          boolean    foundSameTree = false;
          MutableXTreeNode firstTreeNextChild = firstTreeNode.getFirstChild();
          for (int i = 0; (i < firstTreeCurrNodeNrChildren) && noDifferenceFound; i++) {
            foundSameTree = false;
            MutableXTreeNode secondTreeNextChild = secondTreeNode.getFirstChild();
            for (int j = 0; (j < secondTreeCurrNodeNrChildren) && !foundSameTree; j++) {
              if (isSubTree(firstTreeNextChild,
                            secondTree, secondTreeNextChild, indent + "  ")) {
                foundSameTree = true;
              }
              secondTreeNextChild = secondTreeNextChild.getNextSibling();
            }
            if (!foundSameTree) {
              noDifferenceFound = false;
            }
            firstTreeNextChild = firstTreeNextChild.getNextSibling();
          }

          if (noDifferenceFound && firstTreeNode.hasSibling()) {
            MutableXTreeNode   firstTreeCurrNodeNextSib = firstTreeNode.getNextSibling();
            MutableXTreeNode   secondTreeCurrNodeNextSib = secondTreeNode.getNextSibling();
            for (int k = 0; (k < secondTreeCurrNodeNrSiblingsRemaining) && !foundSameTree; k++) {
              if (isSubTree(firstTreeCurrNodeNextSib,
                            secondTree, secondTreeCurrNodeNextSib, indent + "  ")) {
                foundSameTree = true;
              }
              secondTreeCurrNodeNextSib = secondTreeCurrNodeNextSib.getNextSibling();
            }
            if (!foundSameTree) {
              noDifferenceFound = false;
            }
          }
        }
      }
    }
//    System.out.println(indent + "                                                        <= isSubTree " + noDifferenceFound +
//                       (noDifferenceFound ? "" : "FALSE"));
    return noDifferenceFound;
  }
  
}
