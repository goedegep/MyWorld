package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;

/**
 * A node in a DefaultMutableXTree which can contain a {@link XNodeDataType.STRING}.
 */
public class DefaultMutableXTreeStringNode extends DefaultMutableXTreeNode {

  private String             stringData;

  /**
   * Constructor.
   * 
   * @param stringData the value to store in this node, which may not be null.
   */
  public DefaultMutableXTreeStringNode(String stringData) {
    if (stringData == null) {
      throw new IllegalArgumentException("parameter stringData may not be null");
    }
    
    this.stringData = stringData;
  }
  
  @Override
  public XNodeDataType getDataType() {
    return XNodeDataType.STRING;
  }

  @Override
  public String getStringData()
  {
    return stringData;
  }

  @Override
  public void setStringData(String stringData)
  {
    if (stringData == null) {
      throw new IllegalArgumentException("parameter stringData may not be null");
    }
    
    this.stringData = stringData;
  }
  
  @Override
  public String toString(){
	  return stringData;
  }

  @Override
  public DefaultMutableXTreeNode cloneNode() {
    return new DefaultMutableXTreeStringNode(getStringData());
  }
}
