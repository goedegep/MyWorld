package goedegep.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.text.Indent;

/**
 * This class represents a node in a tree.
 * <p>
 * A tree is nothing else than a collection of related nodes, using parent/children relations.<br/>
 * Besides constructors to create a node, there are methods to:
 * <ul>
 * <li>
 * Get/set the content of a node.
 * </li>
 * </ul>
 *
 * @param <T> The data type of the content of a node.
 */
public class TreeNode<T> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TreeNode.class.getName());
  protected static final String         NEWLINE = System.getProperty("line.separator");
  
  private TreeNode<T> parent = null;
  private List<TreeNode<T>> children = null;
  private T content = null;
  
  /**
   * Create a TreeNode without any content.
   */
  public TreeNode() {
    this(null);
  }
  
  /**
   * Create a TreeNode with a specific content.
   * 
   * @param content the content of the newly created node.
   */
  public TreeNode(T content) {
    this.content = content;
  }
  
  /**
   * Get the content of the node.
   * 
   * @return the content of the node.
   */
  public T getContent() {
    return content;
  }
  
  /**
   * Set the content of the node.
   * 
   * @param content the new content of the node. This value may be null.
   */
  public void setContent(T content) {
    this.content = content;
  }
  
  /**
   * Get the parent node of this node.
   * 
   * @return the parent node of this node, or null if the node has no parent.
   */
  public TreeNode<T> getParent() {
    return parent;
  }
  
  /**
   * Set the parent node for this node.
   * 
   * @param parent the new parent of this node. This value may be null.
   */
  public void setParent(TreeNode<T> parent) {
    this.parent = parent;
  }
  
  /**
   * Get the list of children of this node.
   * 
   * @return the children of this node. The list can be empty.
   */
  public List<TreeNode<T>> getChildren() {
    if (children == null) {
      children = new ArrayList<>();
    }
    return children;
  }
  
  /**
   * Check whether this node has any children.
   * 
   * @return
   */
  public boolean hasChildren() {
    return (children != null)  &&  (children.size() != 0);
  }
  
  /**
   * Check whether this node has a sibling after this node.
   * 
   * @return true is there is a sibling after this node, false otherwise.
   */
  public boolean hasNextSibling() {
    if (parent == null) {
      return false;
    }
    
    int indexOfThisNode = parent.children.indexOf(this);
    
    return indexOfThisNode < parent.children.size() - 1;
  }
  
  /**
   * Get the next sibling of this node.
   * 
   * @return the next sibling of this node, or null is there is no next sibling.
   */
  public TreeNode<T> nextSibling() {
    int indexOfThisNode = parent.children.indexOf(this);
    if (indexOfThisNode == parent.children.size() - 1) {
      return null;
    } else {
      return parent.children.get(indexOfThisNode + 1);
    }
  }
  
  /**
   * Get the last sibling of this node.
   * 
   * @return the last sibling of this node, or null is there are no siblings.
   */
  public TreeNode<T> lastSibling() {
    int indexOfThisNode = parent.children.indexOf(this);
    if (indexOfThisNode == parent.children.size() - 1) {
      return null;
    } else {
      return parent.children.get(parent.children.size() - 1);
    }
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    if (parent != null) {
      buf.append("has parent");
    } else {
      buf.append("no parent");
    }
    if (hasChildren()) {
      buf.append(", nr of children=");
      buf.append(children.size());
    } else {
      buf.append(", no children");
    }
    if (content != null) {
      buf.append(", content=");
      buf.append(content.toString());
    } else {
      buf.append(", no content");
    }
    
    return buf.toString();
  }

  /**
   * Get a String representation of a (sub) tree, starting at this node.
   * <p>
   * <ul>
   * <li>
   * Each node of the tree is printed on a separate line.
   * </li>
   * <li>
   * The nodes are printed 'Depth First'.
   * </li>
   * <li>
   * Indentation is used for the level within the tree. Each level down, the identation is increased by 2 spaces.
   * </li>
   * <li>
   * Only the contents of the nodes is printed, using toString() on the value.
   * </li>
   * </ul>
   * 
   * @return a String representation of the (sub) tree, starting at this node.
   */
  public String toStringRecursively(boolean contentOnly) {
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(2);
    
    TreeWalker.visit(this, new TreeNodeVisitor<T>() {

      @Override
      public TreeNodeVisitResult preVisitChildren(TreeNode<T> treeNode) {
        indent.increment();        
        return TreeNodeVisitResult.CONTINUE;
      }

      @Override
      public TreeNodeVisitResult visitTreeItem(TreeNode<T> treeNode) {
        buf.append(indent.toString());
        if (contentOnly) {
          T t = treeNode.getContent();
          buf.append(t != null ? t.toString() : "<null>");
        } else {
          buf.append(treeNode.toString());
        }
        buf.append(NEWLINE);        
        return TreeNodeVisitResult.CONTINUE;
      }

      @Override
      public TreeNodeVisitResult postVisitChildren(TreeNode<T> treeNode) {
        indent.decrement();        
        return TreeNodeVisitResult.CONTINUE;
      }

    });
    
    return buf.toString();
  }
}
