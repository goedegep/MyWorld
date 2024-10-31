/**
 * 
 */
package goedegep.util.xtree.serialized;

import goedegep.util.xtree.XTree;

/**
 * This interface adds funtionality for serialization to an XTree.
 *
 */
public interface SerializedXTree extends XTree {
  /**
   * Get the serialized data for the tree.
   * 
   * @return the serialized data for the tree.
   */
  byte[] getSerializedTreeData();
}
