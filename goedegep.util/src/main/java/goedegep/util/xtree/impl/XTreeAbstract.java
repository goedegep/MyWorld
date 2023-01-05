package goedegep.util.xtree.impl;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

import goedegep.util.xtree.XTree;

/**
 * Common part of the implementation of the XTree interface.
 */
public abstract class XTreeAbstract implements XTree {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(XTreeAbstract.class.getName());
  
  @Override
  public String toString() {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    XTreeNodeVisitorForPrinting visitor = new XTreeNodeVisitorForPrinting(stream);
    traverse(visitor);
    
    return stream.toString();
  }

}