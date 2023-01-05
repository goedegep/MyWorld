package goedegep.util.xtree.impl.nodebased;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.impl.XTreeAbstract;
import goedegep.util.xtree.nodebased.NodeBasedXTree;
import goedegep.util.xtree.nodebased.XTreeNode;

/**
 * This class implements the common functionality for a node based xtree.
 *
 */
public abstract class NodeBasedXTreeAbstract extends XTreeAbstract implements NodeBasedXTree {

  /**
   * {@inheritDoc}
   */
  @Override
  public void traverse(XTreeNodeVisitor xTreeNodeVisitor) {
    
    XTreeNode node = getRoot();
    
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
          node = node.getParent();
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
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void traverse(XTreeNode startNode, boolean visitChildren, boolean visitStartNodeSiblings, XTreeNodeVisitor xTreeNodeVisitor) {
    
    XTreeNode node;
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
  

  /**
   * {@inheritDoc}
   */
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
    
    NodeBasedXTree tree = (NodeBasedXTree) obj;
    return compareSubtrees(getRoot(), tree, tree.getRoot(), true);
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean compareSubtrees(XTreeNode thisStartNode, XTree toTree, XTreeNode toStartNode, boolean includeSiblings) {
    return compareSubtrees(thisStartNode, toTree, toStartNode, includeSiblings, false);
  }
  
  /*
   * Searching
   */
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findChild(XTreeNode referenceNode, XTreeTag nodeValue) {
    XTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (child.contains(nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findChild(XTreeNode referenceNode, boolean nodeValue) {
    XTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (child.contains(nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findChild(XTreeNode referenceNode, int nodeValue) {
    XTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (child.contains(nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findChild(XTreeNode referenceNode, String nodeValue) {
    XTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (child.contains(nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }
    return (child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findChild(XTreeNode referenceNode, byte[] nodeValue) {
    XTreeNode child;
    
    if (referenceNode != null) {
      child = referenceNode.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null) {
      if (child.contains(nodeValue)) {
        break;
      }
      child = child.getNextSibling();
    }

    return (child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findNode(XTreeNode node, short value) {
    XTreeNode child = null;
    XTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !child.contains(value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findNode(XTreeNode node, boolean value) {
    XTreeNode child = null;
    XTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !child.contains(value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findNode(XTreeNode node, int value) {
    XTreeNode child = null;
    XTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !child.contains(value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findNode(XTreeNode node, String value) {
    XTreeNode child = null;
    XTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !child.contains(value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode findNode(XTreeNode node, byte[] value) {
    XTreeNode child = null;
    XTreeNode grandchild = null;

    if (node != null) {
      child = node.getFirstChild();
    } else {
      child = getRoot();
    }
    
    while (child != null && !child.contains(value)) {
      if ((grandchild = findNode(child, value)) != null) {
        return (grandchild);
      }
      child = child.getNextSibling();
    }
    
    return (child);
  }
  
  
  
  /*
   * Internal methods from here.
   */

  /**
   * Compare a part of this tree to a part of another tree.
   * <p>
   * This method forms the implementation of compareSubtrees.
   * It shall initially be called with {@code handlingSiblings} set to false (in general when handling a first child). If the method calls itself when handling a sibling, this value
   * is set to true.
   * 
   * @param thisStartNode starting node in this tree
   * @param toTree the other tree
   * @param toStartNode starting node in the other tree.
   * @param includeSiblings if true also siblings of the startNodes are taken into account, else only
   *        the children of the startNodes are taken into account.
   * @param handlingSiblings to be set to true if siblings are being handled.
   * @return true if the sub trees are identical, false otherwise.
   */
  private boolean compareSubtrees(XTreeNode thisStartNode, XTree toTree, XTreeNode toStartNode, boolean includeSiblings, boolean handlingSiblings) {
    if (!XTreeNode.compareNodesStructureAndContent(thisStartNode, toStartNode)) {
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
}
