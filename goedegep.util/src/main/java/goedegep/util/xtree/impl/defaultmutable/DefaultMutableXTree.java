package goedegep.util.xtree.impl.defaultmutable;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNode;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.XTreeAbstract;
import goedegep.util.xtree.impl.XTreeNodeVisitorForPrinting;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;

/**
 * This class provides an implementation of a {@code MutableXTree}.
 */
public class DefaultMutableXTree extends XTreeAbstract implements MutableXTree {
  private static final Logger LOGGER = Logger.getLogger(DefaultMutableXTree.class.getName());

  private MutableXTreeNode root = null;

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
      MutableXTreeNode node = null;
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
            node = (MutableXTreeNode) node.getParent();
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
    
    MutableXTreeNode node = getRoot();
    
    int level = 0;
    while (node != null) {
      xTreeNodeVisitor.visitTreeItem(node.getDataType(), node.getData());
      
      if (node.hasChild()) {
        node = (MutableXTreeNode) node.getFirstChild();
        level++;
        xTreeNodeVisitor.preVisitChildren();
      } else if (node.hasSibling()) {
        node = (MutableXTreeNode) node.getNextSibling();
      } else {
        while ((level > 0) && (node.getParent().getNextSibling() == null)) {
          node = (DefaultMutableXTreeNode) node.getParent();
          level--;
          xTreeNodeVisitor.postVisitChildren();
        }
        if ((level > 0)  &&  node.hasParent()) {
          node = (MutableXTreeNode) node.getParent().getNextSibling();
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
  public MutableXTreeNode getRoot() {
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
      currentNode = (MutableXTreeNode) node.getFirstChild();
    }

    if (currentNode == null) {
      return 0;
    }

    int count = 1;
    while (currentNode.hasSibling()) {
      currentNode = (MutableXTreeNode) currentNode.getNextSibling();
      count++;
    }

    return count;
  }

  @Override
  public int getNumberOfRemainingSiblings(MutableXTreeNode node) {
    if (node == null) {
      throw new IllegalArgumentException("Argument node may not be null");
    }
    
    MutableXTreeNode currentNode = (MutableXTreeNode) node.getNextSibling();

    if (currentNode == null) {
      return 0;
    }

    int count = 1;
    while (currentNode.hasSibling()) {
      currentNode = (MutableXTreeNode) currentNode.getNextSibling();
      count++;
    }

    return count;
  }

  @Override
  public int getChildIndex(MutableXTreeNode child) {
    if (child == null) {
      throw new IllegalArgumentException("Argument child may not be null");
    }
    
    MutableXTreeNode parentNode = (MutableXTreeNode) child.getParent();
    
    MutableXTreeNode currentNode;
    int index = 0;
    
    if (parentNode == null) {
      currentNode = getRoot();
    } else {
      currentNode = (MutableXTreeNode) parentNode.getFirstChild();
    }
    
    while (!currentNode.equals(child)) {
      currentNode = (MutableXTreeNode) currentNode.getNextSibling();
      index++;
    }

    return index;
  }
 
  @Override
  public void removeNode(MutableXTreeNode xNode) {
    MutableXTreeNode defaultMutableXTreeNode = xNode;
    LOGGER.info("=> xNode=" + defaultMutableXTreeNode.toString());

    MutableXTreeNode currentNode;
    MutableXTreeNode parent = (MutableXTreeNode) defaultMutableXTreeNode.getParent();

    if (parent == null) {
      currentNode = getRoot();
    } else {
      currentNode = (MutableXTreeNode) parent.getFirstChild();
    }

    if (currentNode.equals(xNode)) {
      // removing first child.
      if (parent != null) {
        parent.setFirstChild((MutableXTreeNode) defaultMutableXTreeNode.getNextSibling());
      } else {
        root = (MutableXTreeNode) defaultMutableXTreeNode.getNextSibling();
      }
    } else {
      while (!currentNode.getNextSibling().equals(defaultMutableXTreeNode)) {
        currentNode = (MutableXTreeNode) currentNode.getNextSibling();
      }
      // Removing non-first child node.
      currentNode.setNextSibling((MutableXTreeNode) defaultMutableXTreeNode.getNextSibling());
    }
    
    LOGGER.info("<=");
  }
  
  @Override
  public boolean compareSubtrees(XTreeNode thisStartNode, XTree toTree, XTreeNode toStartNode, boolean includeSiblings) {
    return compareSubtrees(thisStartNode, toTree, toStartNode, includeSiblings, false);
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
  public boolean compareNodes(XTreeNode referenceNode,
      XTree secondTree, XTreeNode secondNode) {
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
      child = (MutableXTreeNode) referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }

    return (child);
  }
  
  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, boolean nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = (MutableXTreeNode) referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }

    return (child);
  }
  
  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, int nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = (MutableXTreeNode) referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }

    return (child);
  }


  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, String nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = (MutableXTreeNode) referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }
    return (child);
  }

  @Override
  public MutableXTreeNode findChild(MutableXTreeNode referenceNode, byte[] nodeValue) {
    MutableXTreeNode child;
    
    if (referenceNode != null) {
      child = (MutableXTreeNode) referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (doesNodeContainValue(child, nodeValue)) {
        break;
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }

    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, short value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = (MutableXTreeNode) node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, boolean value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = (MutableXTreeNode) node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, int value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = (MutableXTreeNode) node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, String value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = (MutableXTreeNode) node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = (MutableXTreeNode) child.getNextSibling();
    }
    
    return (child);
  }

  @Override
  public MutableXTreeNode findNode(MutableXTreeNode node, byte[] value) {
    MutableXTreeNode child = null;
    MutableXTreeNode grandchild = null;

    if (node != null) {
      child = (MutableXTreeNode) node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !doesNodeContainValue(child, value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = (MutableXTreeNode) child.getNextSibling();
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
          node = (MutableXTreeNode) node.getFirstChild();
          level++;
          xTreeNodeVisitor.preVisitChildren();
        }
      } else if (node.hasSibling()) {
        if (visitStartNodeSiblings  ||  (level > 0)) {
          node = (MutableXTreeNode) node.getNextSibling();
        }
      } else {
        while ((level > 0) && (node.getParent().getNextSibling() == null)) {
          node = (MutableXTreeNode) node.getParent();
          level--;
          xTreeNodeVisitor.postVisitChildren();
        }
        if ((level > 0)  &&  (visitStartNodeSiblings || (level > 1))) {
          node = (MutableXTreeNode) node.getParent().getNextSibling();
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
    LOGGER.severe("=>");
    
    XTreeNodeVisitorForPrinting visitor = new XTreeNodeVisitorForPrinting(outputStream);
    traverse(startNode, printChildren, printSiblings, visitor);
    
    LOGGER.severe("<=");
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
   * {@inheritDoc}
   */
  @Override
  public void mergeSubtree(MutableXTreeNode parentNode, MutableXTreeNode siblingNode, MutableXTree srcTree) {
    
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
      for (MutableXTreeNode node = (MutableXTreeNode) srcTree.getRoot(); node != null; node = (MutableXTreeNode) node.getNextSibling()) {
        node.setParent((MutableXTreeNode) siblingNode.getParent());
      }

      MutableXTreeNode srcTreeLastTopLevelNode = (MutableXTreeNode) srcTree.getRoot().getLastSibling();
      if (srcTreeLastTopLevelNode == null) {
        srcTreeLastTopLevelNode = srcTree.getRoot();
      }
      srcTreeLastTopLevelNode.setNextSibling((MutableXTreeNode) siblingNode.getNextSibling());
      siblingNode.setNextSibling(srcTree.getRoot());
    } else {
      // add as first child of parentNode:
      // - all top level nodes of srcTree will get parentNode as parent
      // - the first child of parentNode will be the root of the srcTree
      // - if parentNode had children, its first child will become the next sibling of the last top level node of the srcTree.
      
      MutableXTreeNode parentsFirstChild = (MutableXTreeNode) parentNode.getFirstChild();

      // give the nodes the right parent.
      for (MutableXTreeNode node = (MutableXTreeNode) srcTree.getRoot(); node != null; node = (MutableXTreeNode) node.getNextSibling()) {
        node.setParent(parentNode);
      }
      
      parentNode.setFirstChild(srcTree.getRoot());
      
      if (parentsFirstChild != null) {
        ((MutableXTreeNode) parentNode.getLastChild()).setNextSibling(parentsFirstChild);
      }
    }    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void addSubtreeCopy(MutableXTreeNode dstParentNode, MutableXTreeNode dstSiblingNode,
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
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode insertTagSibling(MutableXTreeNode referenceNode, XTreeTag data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeTagNode(data));    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode insertBooleanSibling(MutableXTreeNode referenceNode, boolean data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeBooleanNode(data));    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode insertIntegerSibling(MutableXTreeNode referenceNode, int data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeIntegerNode(data));    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode insertStringSibling(MutableXTreeNode referenceNode, String data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeStringNode(data));    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode insertBlobSibling(MutableXTreeNode referenceNode, byte[] data) {
    return insertSibling(referenceNode, new DefaultMutableXTreeBlobNode(data));    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode insertSibling(MutableXTreeNode referenceNode, MutableXTreeNode newNode) {
    MutableXTreeNode realReferenceNode = (MutableXTreeNode) referenceNode;

    if (realReferenceNode == null) {
      // Insert as (new) root.
      newNode.setNextSibling(root);
      root = newNode;
    } else {
      newNode.setParent((MutableXTreeNode) realReferenceNode.getParent());

      // Find the sibling before the referenceNode.
      MutableXTreeNode prevSibling;
      if (!realReferenceNode.hasParent()) {
        prevSibling = getRoot();
      } else {
        prevSibling = (MutableXTreeNode) realReferenceNode.getParent().getFirstChild();
      }
      
      if (prevSibling == realReferenceNode) {
        // Insert as first node as this level.
        if (!realReferenceNode.hasParent()) {
          root = newNode;
        } else {
          ((MutableXTreeNode) realReferenceNode.getParent()).setFirstChild(newNode);
        }
      } else {
        while (prevSibling.getNextSibling() != realReferenceNode) {
          prevSibling = (MutableXTreeNode) prevSibling.getNextSibling();
        }

        prevSibling.setNextSibling(newNode);
      }
      newNode.setNextSibling(realReferenceNode);
    }

    return newNode;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTreeNode setRoot(MutableXTreeNode rootNode) {
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

  private boolean compareSubtrees(XTreeNode thisStartNode, XTree toTree, XTreeNode toStartNode, boolean includeSiblings, boolean handlingSiblings) {
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

  private void addSubtreeCopy(MutableXTreeNode dstParentNode, MutableXTreeNode dstSiblingNode,
      boolean insert, MutableXTree srcTree, MutableXTreeNode srcReferenceNode,
      boolean includeSiblings, boolean addingSiblings) {
    LOGGER.info("=> dstParentNode=" + dstParentNode + ", dstSiblingNode=" + dstSiblingNode +
        ", insert=" + insert + ", srcTree=" + srcTree + ", srcReferenceNode=" + srcReferenceNode);
    
    if (srcReferenceNode == null) {
      srcReferenceNode = srcTree.getRoot();
    }
    
    MutableXTreeNode node = srcReferenceNode.cloneNode();
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
      addSubtreeCopy(node, (MutableXTreeNode) null,
                     insert, srcTree, (MutableXTreeNode) srcReferenceNode.getFirstChild(), true, false);
    }
    if (includeSiblings  &&  !addingSiblings) {
      while (srcReferenceNode.hasSibling()) {
        srcReferenceNode = (MutableXTreeNode) srcReferenceNode.getNextSibling();
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
                                MutableXTree resultTree, MutableXTreeNode resultParentNode,
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
        resultParentNode = (MutableXTreeNode) resultTree.getRoot();
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
          resultTree.addSubtreeCopy(resultParentNode, (MutableXTreeNode) null,
              true,
              (MutableXTree) this, node,
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
        node = (MutableXTreeNode) filterNode.getFirstChild();
        indexStart = node.getIntegerData();

        if (tag == XTreeTag.QUERY_INDEX) {
          indexEnd = indexStart;
        } else {
          node = (MutableXTreeNode) node.getNextSibling();
          indexEnd  = node.getIntegerData();
        }

        filterNode = (MutableXTreeNode) filterNode.getNextSibling();
        break;

      case QUERY_WHERE:
        treeFilterNode = (MutableXTreeNode) filterNode.getFirstChild();
        filterNode = (MutableXTreeNode) filterNode.getNextSibling();
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
           node = (MutableXTreeNode) node.getNextSibling()) {
        // check if a tree UNDER node equals the treeFilterNode-based tree
        for (node2 = (MutableXTreeNode) node.getFirstChild();
             node2 != null;
             node2 = (MutableXTreeNode) node2.getNextSibling()) {
          if (((DefaultMutableXTree) filterTree).isSubTree(treeFilterNode, this, node2, "")) {
            DefaultMutableXTreeNode newNode = null;
            if (copy) {
//              newNode = (POIDataTreeRealNode)
//              resultTree.addCopyOfNode(resultParentNode, addAss, sourceTree, node);
            }

            // if filterNode has a child, handle it by recursively calling filterSubTree
            // for this child.
            if (filterNode.hasChild()) {
              filterSubTree(filterTree, (MutableXTreeNode) filterNode.getFirstChild(),
                  (MutableXTreeNode) node.getFirstChild(),
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
           node = (MutableXTreeNode) node.getNextSibling()) {
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
              filterSubTree(filterTree, (MutableXTreeNode) filterNode.getFirstChild(),
                  (MutableXTreeNode) node.getFirstChild(),
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
        filterNode = (MutableXTreeNode) filterNode.getNextSibling();
        copy = filterSubTree(filterTree, filterNode,
                             sourceNode,
                             resultTree, resultParentNode,
                             copy, true, indent + "  ");
      }
    }
    
    LOGGER.info(indent + "<=" + copy);

    return copy;
  }
  
  private boolean compareNodesStructureAndContent(XTreeNode referenceNode, XTree secondTree, XTreeNode secondNode) {
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
          MutableXTreeNode firstTreeNextChild = (MutableXTreeNode) firstTreeNode.getFirstChild();
          for (int i = 0; (i < firstTreeCurrNodeNrChildren) && noDifferenceFound; i++) {
            foundSameTree = false;
            MutableXTreeNode secondTreeNextChild = (MutableXTreeNode) secondTreeNode.getFirstChild();
            for (int j = 0; (j < secondTreeCurrNodeNrChildren) && !foundSameTree; j++) {
              if (isSubTree(firstTreeNextChild,
                            secondTree, secondTreeNextChild, indent + "  ")) {
                foundSameTree = true;
              }
              secondTreeNextChild = (MutableXTreeNode) secondTreeNextChild.getNextSibling();
            }
            if (!foundSameTree) {
              noDifferenceFound = false;
            }
            firstTreeNextChild = (MutableXTreeNode) firstTreeNextChild.getNextSibling();
          }

          if (noDifferenceFound && firstTreeNode.hasSibling()) {
            MutableXTreeNode firstTreeCurrNodeNextSib = (MutableXTreeNode) firstTreeNode.getNextSibling();
            MutableXTreeNode secondTreeCurrNodeNextSib = (MutableXTreeNode) secondTreeNode.getNextSibling();
            for (int k = 0; (k < secondTreeCurrNodeNrSiblingsRemaining) && !foundSameTree; k++) {
              if (isSubTree(firstTreeCurrNodeNextSib,
                            secondTree, secondTreeCurrNodeNextSib, indent + "  ")) {
                foundSameTree = true;
              }
              secondTreeCurrNodeNextSib = (MutableXTreeNode) secondTreeCurrNodeNextSib.getNextSibling();
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
