package goedegep.jfx.eobjecttreeview;

import goedegep.appgen.TableRowOperation;

/**
 * This class describes the available operations for an item in a tree.
 */
public class NodeOperationDescriptor {
  private TableRowOperation operation = null;      // defines the operation (mandatory)
  private String menuText = null;                  // text shown in pop-up menu (mandatory)
  
  public TableRowOperation getOperation() {
    return operation;
  }

  public String getMenuText() {
    return menuText;
  }

  public NodeOperationDescriptor(TableRowOperation operation, String menuText) {
    super();
    this.operation = operation;
    this.menuText = menuText;
  }
  
}
