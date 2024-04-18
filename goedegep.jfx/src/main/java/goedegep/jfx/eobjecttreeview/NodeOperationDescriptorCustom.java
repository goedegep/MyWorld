package goedegep.jfx.eobjecttreeview;

import java.util.function.Consumer;
import java.util.function.Predicate;

import goedegep.appgen.Operation;

/**
 * This class is a {@code NodeOperationDescriptor} for a custom operation.
 * <p>
 * In this case a node operation function has to be specified.
 */
public class NodeOperationDescriptorCustom extends NodeOperationDescriptor {
  
  /**
   * The function that will be called to perform the operation.
   */
  private Consumer<EObjectTreeItem> nodeOperationFunction;
  
  /**
   * Constructor
   * 
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   * @param nodeOperationFunction The function that will be called to perform the operation.
   */
  public NodeOperationDescriptorCustom(String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled, Consumer<EObjectTreeItem> nodeOperationFunction) {
    super(Operation.EXTENDED_OPERATION, menuText, isMenuToBeEnabled);
    
    this.nodeOperationFunction = nodeOperationFunction;
  }

  /**
   * Get the function that will be called to perform the operation.
   * @return
   */
  protected Consumer<EObjectTreeItem> getNodeOperationFunction() {
    return nodeOperationFunction;
  }
  
}
