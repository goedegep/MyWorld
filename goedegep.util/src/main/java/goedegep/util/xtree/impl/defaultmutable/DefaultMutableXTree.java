package goedegep.util.xtree.impl.defaultmutable;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.XTreeNodeVisitorForPrinting;
import goedegep.util.xtree.impl.nodebased.NodeBasedXTreeAbstract;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;

/**
 * This class provides an implementation of a {@code MutableXTree}.
 */
public class DefaultMutableXTree extends NodeBasedXTreeAbstract implements MutableXTree {
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
   * Implementation of the NodeBasedXTree interface
   */
  
  @Override
  public MutableXTreeNode getRoot() {
    LOGGER.info("=>");
    LOGGER.info("<= " + root);
    return root;
  }
  
  /*
   * End of implementation of the NodeBasedXTree interface
   */
    
  /*
   * Implementation of the MutableXTree interface
   */
  
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
  

  /*
   * Adding nodes to the tree
   */
  
  
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
      newNode.setParent(realReferenceNode.getParent());

      // Find the sibling before the referenceNode.
      MutableXTreeNode prevSibling;
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
  public void removeNode(MutableXTreeNode xNode) {
    MutableXTreeNode defaultMutableXTreeNode = xNode;
    LOGGER.info("=> xNode=" + defaultMutableXTreeNode.toString());

    MutableXTreeNode currentNode;
    MutableXTreeNode parent = defaultMutableXTreeNode.getParent();

    if (parent == null) {
      currentNode = getRoot();
    } else {
      currentNode = parent.getFirstChild();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableXTree filterTree(MutableXTree filter) {
    LOGGER.info("=>");
    MutableXTreeNode filterNode = filter.getRoot();
    MutableXTreeNode sourceNode = getRoot();
    DefaultMutableXTree     resultTree = new DefaultMutableXTree();
    DefaultMutableXTreeNode resultNode = null;
    String    indent = "";

    filterSubTree(filter, filterNode, sourceNode,
        resultTree, resultNode, false, false, indent);
    
    LOGGER.info("<=");
    return resultTree;
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
        node.setParent(siblingNode.getParent());
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
      
      MutableXTreeNode parentsFirstChild = parentNode.getFirstChild();

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

  
  /*
   * End of extra methods for DefaultMutableXTree.
   */

  
  /*
   * Internal methods from here.
   */

  /**
   * Add a copy of a subtree to a tree.
   * <p>
   * Used to implement {@link #addSubtreeCopy(MutableXTreeNode, MutableXTreeNode, boolean, MutableXTree, MutableXTreeNode, boolean)}.
   * 
   * @param dstParentNode Node below which the subtree of srcTree will be added.
   * @param dstSiblingNode Node before or after which the subtree of srcTree will be added.
   * @param insert Indicates whether the subtree of srcTree is added before (if TRUE) or
   *               after (if FALSE) dstSiblingNode.
   * @param srcTree The tree of which a subtree is to be added.
   * @param srcReferenceNode Starting point of the subtree in srcTree.
   * @param includeSiblings
   *      The new tree will always contain 'srcReferenceNode' and all its
   *      decendents.  If this value is true, the new tree also contains the
   *      siblings of 'srcReferenceNode' and all their decendents.
   * @param addingSiblings shall be set to true if siblings are being handle (recursively).
   */
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
                     insert, srcTree, srcReferenceNode.getFirstChild(), true, false);
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
   * @param filterTree the filter
   * @param filterNode current node in the {@code filterTree}
   * @param sourceNode current node in this tree
   * @param resultTree the result tree
   * @param resultParentNode parent node of ...
   * @param copy ...
   * @param handlingSiblings set to true if siblings are being handled.
   * @param indent indentation for pretty debug printing.
   * @return The filtered tree.
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
    if (handlingSiblings  &&  (resultParentNode == null)) {
      resultParentNode = (MutableXTreeNode) resultTree.getRoot();
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
        node = filterNode.getFirstChild();
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
        treeFilterNode = filterNode.getFirstChild();
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
        for (node2 = node.getFirstChild();
             node2 != null;
             node2 = (MutableXTreeNode) node2.getNextSibling()) {
          if (((DefaultMutableXTree) filterTree).isSubTree(treeFilterNode, this, node2, "")) {
            DefaultMutableXTreeNode newNode = null;

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
           node = (MutableXTreeNode) node.getNextSibling()) {
        if ((!indexRange  ||
            (index >= indexStart  &&  index <= indexEnd))  &&
            filterNode.equals(node)) {
          DefaultMutableXTreeNode newNode = null;

          // if filterNode has a child, handle it by recursively calling filterSubTree
          // for this child.
          if (filterNode.hasChild()) {
            filterSubTree(filterTree, filterNode.getFirstChild(),
                node.getFirstChild(),
                resultTree, newNode,
                copy, false, indent + "  ");
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


  /**
   * Check if firstNode-based tree is contained (i.e. part of structure) in the secondNode-based tree.
   */
  private boolean isSubTree(MutableXTreeNode firstTreeNode,
      MutableXTree secondTree, MutableXTreeNode secondTreeNode,
                            String indent) {

    boolean noDifferenceFound = firstTreeNode.equals(secondTreeNode);
    if (noDifferenceFound) {
      int     firstTreeCurrNodeNrChildren  = firstTreeNode.getNumberOfChildren();
      int     secondTreeCurrNodeNrChildren = secondTreeNode.getNumberOfChildren();
      int     firstTreeCurrNodeNrSiblingsRemaining  = firstTreeNode.getNumberOfRemainingSiblings();
      int     secondTreeCurrNodeNrSiblingsRemaining = secondTreeNode.getNumberOfRemainingSiblings();

      if ((firstTreeCurrNodeNrSiblingsRemaining <= secondTreeCurrNodeNrSiblingsRemaining) &&
          (firstTreeCurrNodeNrChildren <= secondTreeCurrNodeNrChildren)  &&
          (firstTreeCurrNodeNrChildren > 0)) {
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
    return noDifferenceFound;
  }
  
}
