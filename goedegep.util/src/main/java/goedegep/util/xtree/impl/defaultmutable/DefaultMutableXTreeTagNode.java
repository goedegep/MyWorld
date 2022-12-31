package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeTag;

/**
 * A node in a DefaultMutableXTree which can contain a {@link XNodeDataType.TAG}.
 */
public class DefaultMutableXTreeTagNode extends DefaultMutableXTreeNode {
  private XTreeTag                xtreeTag;

  /**
   * Constructor.
   * 
   * @param xtreeTag the value to store in this node, which may not be null.
   */
  public DefaultMutableXTreeTagNode(XTreeTag xtreeTag) {
    if (xtreeTag == null) {
      throw new IllegalArgumentException("parameter xtreeTag may not be null");
    }
    
    this.xtreeTag = xtreeTag;
  }
  
  @Override
  public XNodeDataType getDataType() {
    return XNodeDataType.TAG;
  }

  @Override
  public XTreeTag getTagData()
  {
    return xtreeTag;
  }

  @Override
  public void setTagData(XTreeTag xtreeTag)
  {
    if (xtreeTag == null) {
      throw new IllegalArgumentException("parameter xtreeTag may not be null");
    }
    
    this.xtreeTag = xtreeTag;
  }
  
  @Override
  public String toString(){
	  return String.valueOf(xtreeTag);
  }

  @Override
  public DefaultMutableXTreeNode cloneNode() {
    return new DefaultMutableXTreeTagNode(getTagData());
  }
}