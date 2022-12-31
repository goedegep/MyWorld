package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;

/**
 * A node in a DefaultMutableXTree which can contain a {@link XNodeDataType.BOOLEAN}.
 */
public class DefaultMutableXTreeBooleanNode extends DefaultMutableXTreeNode {
  private boolean            booleanData;

  /**
   * Constructor.
   * 
   * @param booleanData the value to store in this node.
   */
  public DefaultMutableXTreeBooleanNode(boolean booleanData) {
    this.booleanData = booleanData;
  }
  
  @Override
  public XNodeDataType getDataType() {
    return XNodeDataType.BOOLEAN;
  }

  @Override
  public boolean getBooleanData()
  {
    return booleanData;
  }

  @Override
  public void setBooleanData(boolean booleanData)
  {
    this.booleanData = booleanData;
  }
  
  @Override
  public String toString(){
	  return booleanData ? "True" : "False";
  }

  @Override
  public DefaultMutableXTreeNode cloneNode() {
    return new DefaultMutableXTreeBooleanNode(getBooleanData());
  }
}
