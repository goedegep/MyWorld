package goedegep.jfx.eobjecttreeview;

import java.util.function.Predicate;

import goedegep.appgen.Operation;

/**
 * This class is the base class for the descriptors that describe the operations in an {@code EObjectTreeView}.
 * <p>
 * Known, non abstract, sub classes:
 * <ul>
 * <li>NodeOperationDescriptorCustom - for custom operations</li>
 * <li>NodeOperationDescriptorDelete - for deleting an object</li>
 * <li>NodeOperationDescriptorNew - for creating and adding a new object</li>
 * <li>NodeOperationDescriptorNewAfter - for creating and adding a new object after the current object</li>
 * <li>NodeOperationDescriptorNewBefore - for creating and adding a new object before the current object</li>
 * <li>NodeOperationDescriptorOpen - for opening an object</li>
 * </ul>
 * 
 */
public abstract class NodeOperationDescriptor {
  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * The {@link Operation}, a mandatory attribute.
   */
  private Operation operation = null;
  
  /**
   * The text to be shown in a context (pop-up) menu, a mandatory attribute.
   */
  private String menuText = null;
  
  /**
   * A {@link Predicate} to check whether the menu item is to be shown. Optional, by default the menu item is shown.
   */
  private Predicate<EObjectTreeItem> isMenuToBeEnabled;
  

  /**
   * Constructor
   * 
   * @param operation the {@code Operation} (mandatory).
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   */
  protected NodeOperationDescriptor(Operation operation, String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled) {
    this.operation = operation;
    this.menuText = menuText;
    this.isMenuToBeEnabled = isMenuToBeEnabled;
  }
  
  /**
   * Get the {@code Operation}.
   * @return the {@code Operation}.
   */
  protected Operation getOperation() {
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
   * Get the {@code Predicate} to determine whether the menu item is to be shown or not.
   * @return The {@code Predicate} to determine whether the menu item is to be shown or not. Or null if this value is not set.
   */
  public Predicate<EObjectTreeItem> getIsMenuToBeEnabled() {
    return isMenuToBeEnabled;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("operation: ").append(operation).append(NEW_LINE);
    buf.append("menu text: ").append(menuText).append(NEW_LINE);
//    buf.append("biConsumer: ").append(biConsumer != null ? "Set" : "Not set").append(NEW_LINE);
    
    return buf.toString();
  }
}
