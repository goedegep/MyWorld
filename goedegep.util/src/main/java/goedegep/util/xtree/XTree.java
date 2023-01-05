package goedegep.util.xtree;

/**
 * A Tree that contains tagged data.
 * <p>
 * A Data Type that contains an amount of (tree) structured data.
 * An XTree can contain any amount of tagged hierarchical data.
 * The tree consists of nodes, each node contains one data item of a specified type.
 * A node can have children, and or siblings.
 * The tree can be represented in many different ways, e.g.:
 * <ul>
 * <li>
 * optimized for modifying the tree (see {@link goedegep.util.xtree.mutable.MutableXTree}).
 * </li>
 * <li>
 * optimized for storing/transferring the tree (see {@link goedegep.util.xtree.serialized.SerializedXTree}).
 * </li>
 * </ul>
 * But all representations share this interface, which provides a {@link #traverse} method. This treeWalker method
 * makes it possible to do things with a tree in serialized form, without first converting it into a 'real'
 * tree (a MutableXTree).
 */
public interface XTree {
  /**
   * Maximum number of BLOB bytes show by the {@link #nodeToString} method.
   */
  static final int MAX_BLOB_BYTES_SHOWN = 15;
  
  
  /**
   * Walk through the tree, depth first, calling the specified XTreeNodeVisitor for each node.
   * 
   * @param xTreeNodeVisitor the <b>XTreeNodeVisitor</b> called for each node.
   */
  public void traverse(XTreeNodeVisitor xTreeNodeVisitor);

  
  /*
   * Debug support.
   */
  
  /**
   * Get a textual representation of the content of a node (for debugging purposes only).
   * <p>
   * The returned text has the format: <data-type>: <value><br/>
   * For BLOB nodes only the first {@link #MAX_BLOB_BYTES_SHOWN} are shown.
   * 
   * @param dataType the data type of the node value
   * @param value the node value
   * @return a textual representation of the content of the node, specified by the {@code dataType} and {@code value}.
   */
  public static String nodeToString(XNodeDataType dataType, Object value) {
    StringBuilder buf = new StringBuilder();

    switch (dataType) {
    case TAG:
      XTreeTag tagValue = (XTreeTag) value;
      buf.append("TAG: ").append(tagValue.getName()).append(" (").append(tagValue.getValue()).append(")");
      break;
      
    case BOOLEAN:
      boolean booleanValue = (boolean) value;
      buf.append("BOOLEAN: ").append(booleanValue ? "TRUE" : "FALSE");
      break;

    case INTEGER:
      int integerValue = (int) value;
      buf.append("INTEGER: ").append(String.valueOf(integerValue));
      break;

    case STRING:
      buf.append("STRING: ").append((String) value);
      break;

    case BLOB:
      byte[] blobValue = (byte[]) value;
      buf.append("BLOB: ");
      for (int i = 0; i < blobValue.length  && i < MAX_BLOB_BYTES_SHOWN; i++) {
        if (i != 0) {
            buf.append(", ");
        }
        buf.append(String.valueOf(blobValue[i]));          
      }
      if (blobValue.length >= MAX_BLOB_BYTES_SHOWN) {
          buf.append(", ...");
      }
      break;
    }

    return buf.toString();
  }

}
