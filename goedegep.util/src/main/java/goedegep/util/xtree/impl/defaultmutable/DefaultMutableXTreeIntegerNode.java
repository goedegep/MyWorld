package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;


/**
 * A node in a DefaultMutableXTree which can contain a {@link XNodeDataType.INTEGER}.
 */
public class DefaultMutableXTreeIntegerNode extends DefaultMutableXTreeNode {
  private int                intData;

  /**
   * Constructor.
   * 
   * @param intData the value to store in this node.
   */
  public DefaultMutableXTreeIntegerNode(int intData) {
    this.intData = intData;
  }
  

  @Override
  public XNodeDataType getDataType() {
    return XNodeDataType.INTEGER;
  }

  @Override
  public int getIntegerData()
  {
    return intData;
  }

  @Override
  public void setIntegerData(int intData)
  {
    this.intData = intData;
  }
  
  @Override
  public String toString(){
	  return String.valueOf(intData);
  }

  @Override
  public DefaultMutableXTreeNode cloneNode() {
    return new DefaultMutableXTreeIntegerNode(getIntegerData());
  }
}
