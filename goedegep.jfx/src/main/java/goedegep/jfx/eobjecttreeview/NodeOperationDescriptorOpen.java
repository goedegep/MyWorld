package goedegep.jfx.eobjecttreeview;

import java.util.function.Consumer;
import java.util.function.Predicate;

import goedegep.jfx.Operation;

/**
 * This class is a {@code NodeOperationDescriptor} for an 'Open' operation.
 * <p>
 * The 'Open' operation has one extra mandatory attribute, the function that will be called to open the object.
 */
public class NodeOperationDescriptorOpen extends NodeOperationDescriptor {
  private Consumer<EObjectTreeItem> openObjectFunction;
  
  /**
   * Constructor
   * 
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   * @param openObjectFunction The function that will be called to open the object.
   */
  public NodeOperationDescriptorOpen(String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled, Consumer<EObjectTreeItem> openObjectFunction) {
    super(Operation.OPEN, menuText, isMenuToBeEnabled);
    
    this.openObjectFunction = openObjectFunction;
  }

  /**
   * Get the function to open the object.
   * @return the function to open the object, or null if this value hasn't been set.
   */
  public Consumer<EObjectTreeItem> getOpenObjectFunction() {
    return openObjectFunction;
  }
    
}
