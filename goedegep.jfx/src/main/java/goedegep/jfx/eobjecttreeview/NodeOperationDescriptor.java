package goedegep.jfx.eobjecttreeview;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.TableRowOperation;

/**
 * This class describes the available operations for an item in a tree.
 */
public class NodeOperationDescriptor {
  private TableRowOperation operation = null;      // defines the operation (mandatory)
  private String menuText = null;                  // text shown in pop-up menu (mandatory)
  private BiConsumer<EObject, EObjectTreeItem> biConsumer = null;

  public NodeOperationDescriptor(TableRowOperation operation, String menuText) {
    this(operation, menuText, null);
  }

  public NodeOperationDescriptor(TableRowOperation operation, String menuText, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    this.operation = operation;
    this.menuText = menuText;
    this.biConsumer = biConsumer;
  }
  
  public TableRowOperation getOperation() {
    return operation;
  }

  public String getMenuText() {
    return menuText;
  }

  public BiConsumer<EObject, EObjectTreeItem> getBiConsumer() {
    return biConsumer;
  }
  
}
