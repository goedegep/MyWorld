package goedegep.util.xtree.impl.defaultmutable;

import goedegep.util.xtree.XNodeDataType;

/**
 * A node in a DefaultMutableXTree which can contain a {@link XNodeDataType.BLOB}.
 */
public class DefaultMutableXTreeBlobNode extends DefaultMutableXTreeNode {

  private byte[]             blobData;

  /**
   * Constructor.
   * 
   * @param blobData the value to store in this node, which may not be null.
   */
  public DefaultMutableXTreeBlobNode(byte[] blobData) {
    if (blobData == null) {
      throw new IllegalArgumentException("parameter blobData may not be null");
    }
    
    this.blobData = blobData;
  }
  
  @Override
  public XNodeDataType getDataType() {
    return XNodeDataType.BLOB;
  }

  @Override
  public byte[] getBlobData()
  {
	return blobData;
  }

  @Override
  public void setBlobData(byte[] blobData)
  {
    if (blobData == null) {
      throw new IllegalArgumentException("parameter blobData may not be null");
    }
    
    this.blobData = blobData;
  }
  
  @Override
  public int getDataSize() {
    return blobData.length;
  }

  @Override
  public String nodeToString(){
	  return "Binary data";
  }

  @Override
  public DefaultMutableXTreeNode cloneNode() {
    return new DefaultMutableXTreeBlobNode(getBlobData());
  }
}