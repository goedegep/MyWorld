package goedegep.jfx.treeview;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Tree table item walker.
 *
 * @author bvissy
 *
 * @param <T>
 *            The type of the tree items.
 */
public class TreeViewWalker<T> implements Iterator<TreeItem<T>> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TreeViewWalker.class.getName());
  
  private TreeItem<T> currentItem = null;
  private boolean beforeFirstChild;
  private boolean afterLastChild;


  /**
   * Initialize the walker.
   *
   * @param tree
   *            The tree to walk
   */
  public TreeViewWalker(TreeView<T> tree) {
    super();
    
    currentItem = tree.getRoot();
    
  }

  /**
   * @return True if has unserved items.
   */
  public boolean hasNext() {
    return currentItem != null;
  }

  /**
   * @return The next tree item in depth walk order. The parent is returned
   *         before any of its children.
   */
  public TreeItem<T> next() {
    if (!hasNext()) {
      throw new IllegalStateException("");
    }
    TreeItem<T> next = currentItem;
    walk();
    return next;
  }
  
  private void walk() {
    beforeFirstChild = false;
    afterLastChild = false;
    
    /*
     * If there are children, go to the first child.
     * Else
     *     If there is a sibling, go there
     *     Else
     *       If there is a parent
     *           If the parent has a sibling, go there
     *           Else go to the parent of the parent 
     *       Else
     *           Done
     */
    if (!currentItem.isLeaf()  &&  (currentItem.getChildren().size() != 0)) {
      beforeFirstChild = true;
      currentItem = currentItem.getChildren().get(0);
    } else {
      if (currentItem.nextSibling() != null) {
        currentItem = currentItem.nextSibling();
      } else {
        afterLastChild = true;
        do {
          currentItem = currentItem.getParent();
          if ((currentItem != null)  &&  (currentItem.nextSibling() != null)) {
            currentItem = currentItem.nextSibling();
            break;
          }
        } while (currentItem != null);
      }
    }
  }


  //    /**
  //     * @return A stream of all (remaining) items. Note, that the walker could
  //     *         traverse only once over items.
  //     */
  //    public Stream<TreeItem<T>> stream() {
  //        return StreamSupport.stream(new Spliterator<TreeItem<T>>() {
  //
  //            @Override
  //            public int characteristics() {
  //                return 0;
  //            }
  //
  //            @Override
  //            public long estimateSize() {
  //                return Long.MAX_VALUE;
  //            }
  //
  //            @Override
  //            public boolean tryAdvance(Consumer<? super TreeItem<T>> action) {
  //                if (hasNext()) {
  //                    action.accept(next());
  //                    return true;
  //                } else {
  //                    return false;
  //                }
  //            }
  //
  //            @Override
  //            public Spliterator<TreeItem<T>> trySplit() {
  //                return null;
  //            }
  //        }, false);
  //    }

  /**
   * Walks over the tree and calls the consumer for each tree item.
   *
   * @param tree
   *            The tree to visit.
   * @param visitor
   *            The visitor.
   */
  public static <T> void visit(TreeView<T> tree, TreeItemVisitor<T> visitor) {
    TreeViewWalker<T> tw = new TreeViewWalker<>(tree);
    while (tw.hasNext()) {
      boolean beforeFirstChild = tw.beforeFirstChild;
      TreeItem<T> treeItem = tw.next();
      
      if (beforeFirstChild) {
        visitor.preVisitChildren(treeItem);
      }
      visitor.visitTreeItem(treeItem);
      if (tw.afterLastChild) {
        visitor.postVisitChildren(null);
      }
    }
  }

  /**
   * Walks over the tree and calls the consumer for each item value.
   *
   * @param tree
   *            The tree to visit.
   * @param visitor
   *            The visitor.
   */
  public static <T> void visitItems(TreeView<T> tree, Consumer<T> visitor) {
    TreeViewWalker<T> tw = new TreeViewWalker<>(tree);
    while (tw.hasNext()) {
      visitor.accept(tw.next().getValue());
    }
  }

}