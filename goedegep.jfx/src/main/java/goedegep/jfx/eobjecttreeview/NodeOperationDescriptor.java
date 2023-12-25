package goedegep.jfx.eobjecttreeview;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.TableRowOperation;

/**
 * This class describes the available operations for an item in a tree.
 */
public class NodeOperationDescriptor {
  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * The {@TableRowOperation}, a mandatory attribute.
   */
  private TableRowOperation operation = null;
  
  /**
   * The text to be shown in a context (pop-up) menu, a mandatory attribute.
   */
  private String menuText = null;
  
  /**
   * An optional method which is currently called after creating an object .
   */
  private BiConsumer<EObject, EObjectTreeItem> biConsumer = null;

  /**
   * Constructor
   * 
   * @param operation the {@code TableRowOperation} (mandatory).
   * @param menuText the text to show in the context menu (mandatory).
   */
  public NodeOperationDescriptor(TableRowOperation operation, String menuText) {
    this(operation, menuText, null);
  }

  /**
   * Constructor
   * 
   * @param operation the {@code TableRowOperation} (mandatory).
   * @param menuText the text to show in the context menu (mandatory).
   * @param biConsumer an optional method, which is currently called after creating an object.
   */
  public NodeOperationDescriptor(TableRowOperation operation, String menuText, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    this.operation = operation;
    this.menuText = menuText;
    this.biConsumer = biConsumer;
  }
  
  /**
   * Get the {@TableRowOperation}.
   * @return the {@TableRowOperation}.
   */
  public TableRowOperation getOperation() {
    return operation;
  }

  /**
   * Get the menu text.
   * 
   * @return the menu text.
   */
  public String getMenuText() {
    return menuText;
  }

  /**
   * Get the optional method.
   * 
   * @return the optional method.
   */
  public BiConsumer<EObject, EObjectTreeItem> getBiConsumer() {
    return biConsumer;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("operation: ").append(operation).append(NEW_LINE);
    buf.append("menu text: ").append(menuText).append(NEW_LINE);
    buf.append("biConsumer: ").append(biConsumer != null ? "Set" : "Not set").append(NEW_LINE);
    
    return buf.toString();
  }
}
