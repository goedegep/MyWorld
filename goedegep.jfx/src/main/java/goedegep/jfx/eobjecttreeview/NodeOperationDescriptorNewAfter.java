package goedegep.jfx.eobjecttreeview;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.Operation;

/**
 * This class is a {@code NodeOperationDescriptor} for a 'New After' operation.
 * <p>
 * The 'New After' operation has one extra optional attribute, the function that will be called to initialize the object, after it has been created.
 */
public class NodeOperationDescriptorNewAfter extends NodeOperationDescriptorNewAbstract {
  
  /**
   * Constructor
   * 
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   * @param newEObjectInitializationFunction The function that will be called to initialize the object, after it has been created.
   */
  public NodeOperationDescriptorNewAfter(String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled, BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction) {
    super(Operation.NEW_OBJECT_AFTER, menuText, isMenuToBeEnabled, newEObjectInitializationFunction);
  }
  
}
