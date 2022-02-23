package goedegep.util.tree;

/**
 * This class is a helper to create a tree.
 * <p>
 * The constructor creates the root node. There is always a current node, which is initially the root node. Whenever a node is added
 * the new node becomes the current node.<br/>
 * The root node cannot have siblings.<br/>
 * The easiest way to build a tree is in depth first order.
 * To creat the following tree:
 * A
 *   B
 *   C
 *     D
 *       E
 *       F
 *   G
 *   
 * Use (indentation is used to let the example look like the tree):
 * new TreeBuilder(A)     - current node is A
 *   addFirstChild(B)     - current node is B
 *   addSibling(C)        - current node is C
 *     addFirstChild(D)   - current node is D
 *       addFirstChild(E) - current node is E
 *       addSibling(F)    - current node is F
 *     toParent()         - current node is D
 *     toParent()         - current node is C
 *   addSibling(G)        - current node is G
 * 
 */
public class TreeBuilder<T> {
  private TreeNode<T> rootNode = null;
  private TreeNode<T> currentNode = null;
  
  /**
   * Constructor
   * <p>
   * Creates a tree with a single node; the root node.<br/>
   * The current node is set to the root node.
   * 
   * @param rootContent content of the root node.
   */
  public TreeBuilder(T rootContent) {
    currentNode = new TreeNode<>(rootContent);
    rootNode = currentNode;
  }
  
  /**
   * Add a sibling node to the current node.
   * <p>
   * The new sibling is added as last child of the parent of the current node.<br/>
   * The current node is set to the new node.
   * 
   * @param content the content of the new node.
   * @return
   */
  public TreeNode<T> addSiblingNode(T content) {
    TreeNode<T> parentNode = currentNode.getParent();
    if (parentNode == null) {
      throw new IllegalArgumentException("Cannot add sibling to this node, as parent node is null");
    }
    
    TreeNode<T> newNode = new TreeNode<>(content);
    newNode.setParent(currentNode.getParent());
    parentNode.getChildren().add(newNode);
    currentNode = newNode;
    
    return newNode;
  }
  
  /**
   * Add a first child node to the current node.
   * <p>
   * The child is added as last child of the current node. So 'first' only indicates that this method should only be used on a node which doesn't have
   * children yet.<br/>
   * The current node is set to the new node.
   * 
   * @param content the content of the new node.
   * @return
   */
  public TreeNode<T> addFirstChildNode(T content) {
    TreeNode<T> newNode = new TreeNode<>(content);
    newNode.setParent(currentNode);
    currentNode.getChildren().add(newNode);
    currentNode = newNode;
    
    return newNode;
  }
  
  /**
   * Get the root of the tree.
   * 
   * @return the root node of the tree.
   */
  public TreeNode<T> getRootNode() {
    return rootNode;
  }

  /**
   * Move the current node one level up.
   * <p>
   * So the current node is set to the parent of the current node.
   */
  public void toParent() {
    currentNode = currentNode.getParent();
  }

}
