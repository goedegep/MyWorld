package goedegep.jfx.eobjecttreeview;

import java.util.function.Predicate;

import goedegep.jfx.Operation;

/**
 * This class is a {@code NodeOperationDescriptor} for a 'Paste' operation.
 * <p>
 * The 'Paste' operation doesn't require any additional attributes.
 */
public class NodeOperationDescriptorPaste extends NodeOperationDescriptor {
  
  /**
   * Constructor
   * 
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   */
  public NodeOperationDescriptorPaste(String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled) {
    super(Operation.PASTE_OBJECT, menuText, isMenuToBeEnabled);
  }
  
}
