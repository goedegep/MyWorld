package goedegep.util.xtree.impl.nodebased;

import java.util.Arrays;

import goedegep.util.xtree.XTreeTag;
import goedegep.util.xtree.nodebased.XTreeNode;

/**
 * This class implements the common functionality for the nodes in a node based xtree.
 *
 */
public abstract class XTreeNodeAbstract implements XTreeNode {
  static final int MAX_BLOB_BYTES_SHOWN = 15;  // Just for testing.
  
  protected XTreeNode parent;
  protected XTreeNode firstChild  = null;
  protected XTreeNode nextSibling = null;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasParent() {
    return parent != null;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode getParent() {
    return parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasChild() {
    return firstChild != null;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode getFirstChild() {
    return firstChild;
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode getLastChild() {
    XTreeNode node = getFirstChild();

    if (node == null) {
      return null;
    }

    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getNumberOfChildren() {
    XTreeNode currentNode = getFirstChild();

    if (currentNode == null) {
      return 0;
    }

    int count = 1;
    while (currentNode.hasSibling()) {
      currentNode = currentNode.getNextSibling();
      count++;
    }

    return count;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildIndex() {
    XTreeNode parentNode = getParent();
    
    XTreeNode currentNode;
    int index = 0;
    
    if (parentNode == null) {
      return -1;
    } else {
      currentNode = parentNode.getFirstChild();
    }
    
    while (!currentNode.equals(this)) {
      currentNode = currentNode.getNextSibling();
      index++;
    }

    return index;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSibling() {
    return nextSibling != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode getNextSibling() {
    return nextSibling;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XTreeNode getLastSibling() {
    if (!hasSibling()) {
      return null;
    }
    
    XTreeNode node = getNextSibling();
    while (node.hasSibling()) {
      node = node.getNextSibling();
    }

    return node;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNumberOfRemainingSiblings() {
    XTreeNode currentNode = getNextSibling();

    if (currentNode == null) {
      return 0;
    }

    int count = 1;
    while (currentNode.hasSibling()) {
      currentNode = currentNode.getNextSibling();
      count++;
    }

    return count;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object object) {
    if (object instanceof XTreeNode node) {
      if (getDataType() != node.getDataType()) {
        return false;
      }

      switch (getDataType()) {
      case TAG:
        return getTagData() == node.getTagData();

      case BOOLEAN:
        return getBooleanData() == node.getBooleanData();

      case INTEGER:
        return getIntegerData() == node.getIntegerData();

      case STRING:
        return (getStringData().compareTo(node.getStringData()) == 0);

      case BLOB:
        return Arrays.equals(getBlobData(), node.getBlobData());
      }

      return false;
    } else {
      return false;
    }
  }
  
  @Override
  public String toString( ) {
    String text = "dummy";
    Object value = getData();

    switch (getDataType()) {
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
