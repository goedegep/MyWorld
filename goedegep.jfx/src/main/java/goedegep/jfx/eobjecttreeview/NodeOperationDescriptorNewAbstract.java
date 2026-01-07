package goedegep.jfx.eobjecttreeview;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;

import goedegep.jfx.Operation;

/**
 * This class is the base class for all 'new' operations (being 'New', 'New Before' and 'New After').
 * <p>
 * It add one attribure; the function that will be called to initialize the object, after it has been created.
 */
public abstract class NodeOperationDescriptorNewAbstract extends NodeOperationDescriptor {
  
  /**
   * A new {@code EObject} initialization function (optional).
   * <p>
   * If set, this function is called after the object is created.
   */
  private BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction = null;
  

  /**
   * Constructor
   * 
   * @param operation the {@code Operation} (mandatory).
   * @param menuText the text to show in the context menu (mandatory).
   * @param isMenuToBeEnabled Optional {@code Predicate} to determine whether the menu item is to be shown or not.
   * @param newEObjectInitializationFunction The function that will be called to initialize the object, after it has been created.
   */
  protected NodeOperationDescriptorNewAbstract(Operation operation, String menuText, Predicate<EObjectTreeItem> isMenuToBeEnabled, BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction) {
    super(operation, menuText, isMenuToBeEnabled);
        
    this.newEObjectInitializationFunction = newEObjectInitializationFunction;
  }
  
  /**
   * Get the function that is called to initialize the object, after it has been created.
   * @return the function that is called to initialize the object, after it has been created. Or null if this value hasn't been set.
   */
  public BiConsumer<EObject, EObjectTreeItem> getNewEObjectInitializationFunction() {
    return newEObjectInitializationFunction;
  }
}
