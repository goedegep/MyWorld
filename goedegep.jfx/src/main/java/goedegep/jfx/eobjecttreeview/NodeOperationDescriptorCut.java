package goedegep.jfx.eobjecttreeview;

import java.util.function.Predicate;

import goedegep.jfx.Operation;

/**
 * This class is a {@code NodeOperationDescriptor} for a 'Cut' operation.
 * <p>
 * The 'Cut' operation doesn't require any additional attributes.
 */
public class NodeOperationDescriptorCut extends NodeOperationDescriptor {
  
  /**
   * Constructor
   * 
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   */
  public NodeOperationDescriptorCut(String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled) {
    super(Operation.CUT_OBJECT, menuText, isMenuToBeEnabled);
  }
  
}
