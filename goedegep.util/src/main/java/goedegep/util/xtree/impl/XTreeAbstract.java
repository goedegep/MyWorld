package goedegep.util.xtree.impl;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitorForPrinting;

/**
 * Tree that contains tagged data.
 */
public abstract class XTreeAbstract implements XTree {
  private static final Logger LOGGER = Logger.getLogger(XTreeAbstract.class.getName());
  
  @Override
  public String print() {
    LOGGER.info("=>");
    
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    XTreeNodeVisitorForPrinting visitor = new XTreeNodeVisitorForPrinting(stream);
    traverse(visitor);
    
    LOGGER.info("<=");
    return stream.toString();
  }

//  @Override
//  public static String nodeToString(XNodeDataType dataType, Object value) {
//    String text = "dummy";
//
//    switch (dataType) {
//    case TAG:
//      XTreeTag tagValue = (XTreeTag) value;
//      text = "TAG: " + tagValue.getName() + " (" + tagValue.getValue() + ")";
//      break;
//      
//    case BOOLEAN:
//      boolean booleanValue = (boolean) value;
//      String valueText = booleanValue ? "TRUE" : "FALSE";
//      text = "BOOLEAN: " + valueText;
//      break;
//
//    case INTEGER:
//      int integerValue = (int) value;
//      text = "INTEGER: " + String.valueOf(integerValue);
//      break;
//
//    case STRING:
//      text = "STRING: " + ((String) value);
//      break;
//
//    case BLOB:
//      byte[] blobValue = (byte[]) value;
//      StringBuffer sb = new StringBuffer();
//      sb.append("BLOB: ");
//      for (int i = 0; i < blobValue.length  && i < MAX_BLOB_BYTES_SHOWN; i++) {
//        if (i != 0) {
//            sb.append(", ");
//        }
//        sb.append(String.valueOf(blobValue[i]));          
//      }
//      if (blobValue.length >= MAX_BLOB_BYTES_SHOWN) {
//          sb.append(", ...");
//      }
//      text = sb.toString();
//      break;
//    }
//
//    return text;
//  }

  

  /* Is dit nuttig?
  public XTreeNodeInterface findNode(XTreeNodeInterface parentNode,
                                     XTreeNodeInterface searchNode,
                                     XTreeNodeInterface offsetNode) {
    XTreeNodeInterface child;
    if (offsetNode != null) {
      child = offsetNode;
    } else {
      if (parentNode != null) {
        child = getFirstChildForNode(parentNode);
      }
      else {
        child = getRoot();
      }
    }

    while (child != null)
    {
      if (compareNodes(child, this, searchNode)) {
        break;
      }
      child = getNextSiblingForNode(child);
    }
    return (child);
  }
  */



/*
     private boolean isNodeFound(POIDataTreeNode treeNode, short nodeType, boolean nodeValue)
     {
        boolean nodeFound = false;
        if (((short)treeNode.getNodeType()) == nodeType)
        {
           if (treeNode.getBoolData() == nodeValue)
           {
             nodeFound = true;
           }
        }
        return nodeFound;
     }
*/

  /*
     private POIDataTreeNode getPOIDataTreeNode(short startLocation)
     {
       short parentAddress;
       short siblingAddress;
       short childAddress;
       short tagNumber;
       POIDataTreeNode node = new POIDataTreeNode();
       byte[] byteHolder = new byte[2];

       node.setReferenceAddressWithInTree(startLocation);
       // Get parent address
       byteHolder[0] = m_saData[startLocation];
       byteHolder[1] = m_saData[startLocation+1];
       parentAddress = convertToShort(byteHolder);
       node.setParentLocation(parentAddress);
       // Get sibling address
       byteHolder[0] = m_saData[startLocation+2];
       byteHolder[1] = m_saData[startLocation+3];
       siblingAddress = convertToShort(byteHolder);
       node.setSiblingLocation(siblingAddress);
       // Get child address
       byteHolder[0] = m_saData[startLocation+4];
       byteHolder[1] = m_saData[startLocation+5];
       childAddress = convertToShort(byteHolder);
       node.setChildLocation(childAddress);
       // Get Tag type
       byteHolder[0] = m_saData[startLocation+6];
       byteHolder[1] = m_saData[startLocation+7];
       tagNumber = convertToShort(byteHolder);
       node.setNodeType(tagNumber);

       switch (tagNumber)
       {
         case POIDataTreeNodeType.TPD_TAG: // Type is tag.
                 node.setTagData(getTagValue(startLocation+8));
                 break;
         case POIDataTreeNodeType.BOOLEAN: // Type is Boolean
                 node.setBoolData(getBoolValue(startLocation+8));
                 break;
         case POIDataTreeNodeType.INTEGER: // Type is Integer.
                 node.setIntData(getIntValue(startLocation+8));
                 break;
         case POIDataTreeNodeType.STRING: // Type is String
                 node.setStringData(getStringValue(startLocation+8));
                 break;
        default: break;
       }
       return node;
     }
*/
    /**
      * Converts byte array to short, used to retrive addresses of parent, child,
      * sibling and tag number.
      @param input byte array
      @return short
      */
  /*
     public short convertToShort(byte[] input)
     {
        short result = 0;
        result = (short) ((input[0] << 8) + (input[1] & 0xff));
        return result;
     }
*/
     /**
      * Converts byte array to short.
        @param input byte array
        @return short
      */
  /*
     public short getTagValue(int startLocation)
     {
       short result = 0;
       result = (short)((m_saData[startLocation] << 8) + (m_saData[startLocation+1] & 0xff));
       return result;
     }
*/

     /**
      * Converts byte array to String.
       @param input byte array
       @return short
      */
  /*
     public String getStringValue(int startLoc)
     {
       StringBuffer result = new StringBuffer();
       char readChar;

       // read until '\0'.
       while ((readChar = (char)m_saData[startLoc++]) != '\0')
       {
         result.append(readChar);
       }
       return result.toString();
     }
*/

     /**
      * Gets 4 bytes from the byte array and convert to integer.
       @param startLoc
       @return short
      */
  /*
      public int getIntValue(int startLoc)
      {
         int result = 0;

         result = m_saData[startLoc];
         for (int x=1; x<4; x++)
         {
             result = (result<<8) + (m_saData[startLoc+x]& 0xFF) ;
         }
         return result;
      }
*/

      /**
       * Convert byte to a boolean.
         @param input byte array
         @return short
       */
  /*
      public boolean getBoolValue(int startLoc)
      {
        return m_saData[startLoc]!=0;
      }
*/

  /*
      private POIDataTree copyTree(POIDataTree poiTree) {
        POIDataTree tree = new POIDataTree();

        addSubTree(tree, null, poiTree, poiTree.getRoot(), false, "");

        return tree;
      }
*/
  
  /*
      public void addSubTree(POIDataTreeNodeInterface destNode, POIDataTree srcTree, POIDataTreeNodeInterface srcNode) {
        addSubTree(this, destNode, srcTree, srcNode, false, "");
      }

      private static void addSubTree(POIDataTree destTree, POIDataTreeNodeInterface destNode,
                                     POIDataTree srcTree, POIDataTreeNodeInterface srcNode,
                                     boolean addingSiblings, String indent) {
        //System.out.println(indent + "=> POIDataTree: addSubTree()");
        POIDataTreeNodeInterface node = null;
        switch (srcTree.getNodeDataType(srcNode)) {
        case POIDataTreeNodeType.BOOLEAN:
          boolean booleanValue = srcTree.getBooleanNodeData(srcNode);
          //System.out.println(indent + "BOOLEAN node, value = " + booleanValue);
          node= destTree.addIntegralNode(destNode, POIDataTree.ADD_CHILD, POIDataTreeNodeType.BOOLEAN, booleanValue ? 1 : 0);
          break;

        case POIDataTreeNodeType.INTEGER:
          int intValue = srcTree.getIntNodeData(srcNode);
          //System.out.println(indent + "INTEGER node, value = " + intValue);
          node= destTree.addIntegralNode(destNode, POIDataTree.ADD_CHILD, POIDataTreeNodeType.INTEGER, intValue);
          break;

        case POIDataTreeNodeType.STRING:
          String stringValue = srcTree.getStringNodeData(srcNode);
          //System.out.println(indent + "STRING node, value = " + stringValue);
          node= destTree.addStringNode(destNode, POIDataTree.ADD_CHILD, stringValue);
          break;

        case POIDataTreeNodeType.TPD_TAG:
          short tagValue = srcTree.getTagNodeData(srcNode);
          //System.out.println(indent + "TPD_TAG node, value = " + tagValue);
          node= destTree.addIntegralNode(destNode, POIDataTree.ADD_CHILD, POIDataTreeNodeType.TPD_TAG, (int) tagValue);
          break;
        }

        if (srcTree.doesNodeHaveChild(srcNode)) {
          //System.out.println(indent + "Adding child");
          addSubTree(destTree, node, srcTree, srcTree.getFirstChildForNode(srcNode), false, indent + "  ");
        }
        if (!addingSiblings) {
          while (srcTree.doesNodeHaveSibling(srcNode)) {
            //System.out.println(indent + "Adding sibling");
            srcNode = srcTree.getNextSiblingForNode(srcNode);
            addSubTree(destTree, destNode, srcTree, srcNode, true, indent + "  ");
          }
        }
        //System.out.println(indent + "<= POIDataTree: addSubTree()");

      }
*/



/*
  private static boolean filterSubTree(POIDataTree filterTree, POIDataTreeNodeInterface filterNode,
                                       POIDataTree sourceTree, POIDataTreeNodeInterface sourceNode,
                                       POIDataTree resultTree, POIDataTreeNodeInterface resultParentNode,
                                       boolean copy, boolean handlingSiblings, String indent) {
    //System.out.println(indent + "=> POIDataTree: filterSubtree()");
    POIDataTreeNodeInterface node;
    boolean indexRange = false;
    int indexStart = -1;
    int indexEnd = -1;

    // Note: in the comments below 'copy' is used in several locations. However actual
    //       copying only takes place if resultTree in not null.

    //
    // handle the filterNode
    //

    // Handle special Query nodes.
    boolean handled = false;
    short nodeType = filterTree.getNodeDataType(filterNode);
    if (nodeType == POIDataTreeNodeType.TPD_TAG) {
      short tag = filterTree.getTagNodeData(filterNode);
      switch (tag) {
      case POITag.TPD_TAG_QUERY_SUBTREE:
        // filterNode is TPD_TAG_QUERY_SUBTREE, copy all child subtrees of sourceParentNode
        // to the resultTree
        //System.out.println(indent + "POIDataTree: Going to handle TPD_TAG_QUERY_SUBTREE");
        if (copy) {
          for (node = sourceNode;
               node != null;
               node = sourceTree.getNextSiblingForNode(node)) {
            //System.out.println(indent + "POIDataTree: Going to add subtree for: " + sourceTree.nodeToString(node));
            addSubTree(resultTree, resultParentNode, sourceTree, node, false, indent);
          }
        }
        handled = true;
        //System.out.println(indent + "POIDataTree: handled TPD_TAG_QUERY_SUBTREE");
        break;

      case POITag.TPD_TAG_QUERY_INDEX:
      case POITag.TPD_TAG_QUERY_RANGE:
        // filterNode is TPD_TAG_QUERY_INDEX OR TPD_TAG_QUERY_RANGE:
        // if filterNode is TPD_TAG_QUERY_INDEX, read the index i, set j to i.
        // if filterNode is TPD_TAG_QUERY_RANGE, read the indices i and j.
        // read next sibling s of filterNode.
        // find occurrences i through j equal to s under sourceParentNode, copy these nodes into
        // the resultTree and recursively call filterSubTree.
        //System.out.println(indent + "POIDataTree: Going to handle TPD_TAG_QUERY_INDEX or TPD_TAG_QUERY_RANGE");
        indexRange = true;
        node = filterTree.getFirstChildForNode(filterNode);
        indexStart = filterTree.getIntNodeData(node);

        if (tag == POITag.TPD_TAG_QUERY_INDEX) {
          indexEnd = indexStart;
        } else {
          node = filterTree.getNextSiblingForNode(node);
          indexEnd  = filterTree.getIntNodeData(node);
        }
        //System.out.println(indent + "POIDataTree: indexStart = " + indexStart + ", indexEnd = " + indexEnd);

        filterNode = filterTree.getNextSiblingForNode(filterNode);
        //System.out.println(indent + "POIDataTree: handled TPD_TAG_QUERY_INDEX or TPD_TAG_QUERY_RANGE");
        break;

      case POITag.Q_RESULT_ROOT:
        // filterNode is Q_RESULT_ROOT, create resultTree.
        //System.out.println(indent + "POIDataTree: Going to handle Q_RESULT_ROOT");
        copy = true;
        handled = true;
        //System.out.println(indent + "POIDataTree: handled Q_RESULT_ROOT");
        break;
      }
    }

    if (!handled) {
      // filterNode is not a special query node:
      //   find all nodes with same content in sourceTree, copy them into the
      //   resultTree and recursively call filterSubTree.
      //System.out.println(indent + "POIDataTree: !handled.");
      int index = 0;
      for (node = sourceNode;
           node != null;
           node = sourceTree.getNextSiblingForNode(node)) {
        if (!indexRange  ||
            (index >= indexStart  &&  index <= indexEnd)) {
          //System.out.println(indent + "POIDataTree: index OK.: " + index);
          //System.out.println(indent + "POIDataTree: comparing node: " + filterTree.nodeToString(filterNode));
          //System.out.println(indent + "POIDataTree:        to node: " + sourceTree.nodeToString(node));
          if (compareNodes(filterTree, filterNode, sourceTree, node)) {
            //System.out.println(indent + "POIDataTree: nodes are equal.");
            POIDataTreeRealNode newNode = null;
            if (copy) {
              //System.out.println(indent + "POIDataTree: going to copy node.");
              newNode = (POIDataTreeRealNode)
                  resultTree.addCopyOfNode(resultParentNode, ADD_CHILD, sourceTree, node);
            }

            // if filterNode has a child, handle it by recursively calling filterSubTree
            // for this child.
            if (filterTree.doesNodeHaveChild(filterNode)) {
              //System.out.println(indent + "POIDataTree: going to handle child.");
              filterSubTree(filterTree, filterTree.getFirstChildForNode(filterNode),
                            sourceTree, sourceTree.getFirstChildForNode(node),
                            resultTree, newNode,
                            copy, false, indent + "  ");

            }
          }
        }
        index++;
      }
    }

    // if we are not in 'handlingSiblings mode', handle all siblings by recursively
    // calling filterSubtree for each sibling, using 'handlingSiblings mode'.
    if (!handlingSiblings) {
      //System.out.println(indent + "POIDataTree: going to handle siblings.");
      while (filterTree.doesNodeHaveSibling(filterNode)) {
        //System.out.println(indent + "POIDataTree: Handling sibling");
        filterNode = filterTree.getNextSiblingForNode(filterNode);
        copy = filterSubTree(filterTree, filterNode,
                             sourceTree, sourceNode,
                             resultTree, resultParentNode,
                             copy, true, indent + "  ");
      }
    }

    //System.out.println(indent + "<= POIDataTree: filterSubtree()");

    return copy;
  }
*/

  
//  private static String byteToBinaryString(byte value) {
//    StringBuffer buf = new StringBuffer();
//    for (int i = 7; i >= 0; i--) {
//      if ((value & ((byte) 0x01 << i)) == 0) {
//        buf.append('0');
//      } else {
//        buf.append('1');
//      }
//    }
//    return buf.toString();
//    
//  }
  
  /*
   * Overriding Object functionality.
   */
}