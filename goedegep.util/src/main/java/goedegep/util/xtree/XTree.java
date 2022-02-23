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
 * But all representations share this interface, which provides a treeWalker method. This treeWalker method
 * makes it possible to do things with a tree in serialized form, without first converting it into a 'real'
 * tree (a MutableXTree).
 */
public interface XTree {
  static final int MAX_BLOB_BYTES_SHOWN = 15;  // Just for testing.
  
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
   * Print the contents of a tree to a String (for debugging purposes only).
   *
   * @return a String representation of the tree.
   */
  public String print();
  
  /**
   * Get a textual representation of the content of a node (for debugging purposes only).
   * 
   * @param dataType the data type of the node value
   * @param value the node value
   * @return a textual representation of the content of the node, specified by the {@code dataType} and {@code value}.
   */
  public static String nodeToString(XNodeDataType dataType, Object value) {
    String text = "dummy";

    switch (dataType) {
    case TAG:
      XTreeTag tagValue = (XTreeTag) value;
      text = "TAG: " + tagValue.getName() + " (" + tagValue.getValue() + ")";
      break;
      
    case BOOLEAN:
      boolean booleanValue = (boolean) value;
      String valueText = booleanValue ? "TRUE" : "FALSE";
      text = "BOOLEAN: " + valueText;
      break;

    case INTEGER:
      int integerValue = (int) value;
      text = "INTEGER: " + String.valueOf(integerValue);
      break;

    case STRING:
      text = "STRING: " + ((String) value);
      break;

    case BLOB:
      byte[] blobValue = (byte[]) value;
      StringBuffer sb = new StringBuffer();
      sb.append("BLOB: ");
      for (int i = 0; i < blobValue.length  && i < MAX_BLOB_BYTES_SHOWN; i++) {
        if (i != 0) {
            sb.append(", ");
        }
        sb.append(String.valueOf(blobValue[i]));          
      }
      if (blobValue.length >= MAX_BLOB_BYTES_SHOWN) {
          sb.append(", ...");
      }
      text = sb.toString();
      break;
    }

    return text;
  }

}
