package goedegep.util.xtree.impl;

import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeNode;
import goedegep.util.xtree.XTreeTag;

public abstract class XTreeNodeAbstract implements XTreeNode {
  static final int MAX_BLOB_BYTES_SHOWN = 15;  // Just for testing.

  /**
   * {@inheritDoc}
   */
//  @Override
//  public XNodeDataType getDataType() {
//    return null;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public Object getData() {
//    return null;
//  }

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
