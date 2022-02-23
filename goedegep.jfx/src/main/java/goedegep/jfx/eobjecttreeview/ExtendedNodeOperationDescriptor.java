package goedegep.jfx.eobjecttreeview;

import java.util.function.Consumer;
import java.util.function.Predicate;

import goedegep.appgen.TableRowOperation;

public class ExtendedNodeOperationDescriptor extends NodeOperationDescriptor {
  private Predicate<EObjectTreeItem> isMenuToBeEnabled;
  private Consumer<EObjectTreeItem> nodeOperation;
  
  public ExtendedNodeOperationDescriptor(String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled, Consumer<EObjectTreeItem> nodeOperation) {
    super(TableRowOperation.EXTENDED_OPERATION, menuText);
    
    this.isMenuToBeEnabled = isMenuToBeEnabled;
    this.nodeOperation = nodeOperation;
  }

  public Predicate<EObjectTreeItem> getIsMenuToBeEnabled() {
    return isMenuToBeEnabled;
  }

  public Consumer<EObjectTreeItem> getNodeOperation() {
    return nodeOperation;
  }
  
}
