package goedegep.util.xtree.impl;

import java.io.OutputStream;
import java.io.PrintStream;

import goedegep.util.text.Indent;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTree;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;

/**
 * This class is an {@link XTreeNodeVisitor} which is used by the toString implementation of the XTree.
 */
public class XTreeNodeVisitorForPrinting implements XTreeNodeVisitor {
  PrintStream stream;
  Indent indent = new Indent(2);
  
  public XTreeNodeVisitorForPrinting(OutputStream outputStream) {
    stream = new PrintStream(outputStream);
  }

  @Override
  public XTreeNodeVisitResult preVisitChildren() {
    indent.increment();
    return XTreeNodeVisitResult.CONTINUE;
  }

  @Override
  public XTreeNodeVisitResult visitTreeItem(XNodeDataType dataType, Object value) {
    stream.print(indent);
    stream.println(XTree.nodeToString(dataType, value));
    return XTreeNodeVisitResult.CONTINUE;
  }

  @Override
  public XTreeNodeVisitResult postVisitChildren() {
    indent.decrement();
    return XTreeNodeVisitResult.CONTINUE;
  }
  
}
