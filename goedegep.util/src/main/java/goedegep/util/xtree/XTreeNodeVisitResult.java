package goedegep.util.xtree;

/**
 * This enum defines the return values for the methods of the {@link XTreeNodeVisitor} interface.
 */
public enum XTreeNodeVisitResult {
  /**
   * Continue. When returned from a {@link FileVisitor#preVisitDirectory
   * preVisitDirectory} method then the entries in the directory should also
   * be visited.
   */
  CONTINUE,
  /**
   * Terminate.
   */
  TERMINATE,
  /**
   * Continue without visiting the entries in this directory. This result
   * is only meaningful when returned from the {@link
   * FileVisitor#preVisitDirectory preVisitDirectory} method; otherwise
   * this result type is the same as returning {@link #CONTINUE}.
   */
  SKIP_SUBTREE,
  /**
   * Continue without visiting the <em>siblings</em> of this file or directory.
   * If returned from the {@link FileVisitor#preVisitDirectory
   * preVisitDirectory} method then the entries in the directory are also
   * skipped and the {@link FileVisitor#postVisitDirectory postVisitDirectory}
   * method is not invoked.
   */
  SKIP_SIBLINGS;

}
