package goedegep.jfx.eobjecttable;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * This class provides a possible operation on a row in a table.
 *
 * @param <T> the type of the items in the table. This type will be the type for the input parameter of the consumer function.
 */
public class TableRowOperationDescriptor<T> {
  /**
   * The text in the context menu.
   */
  private String menuText;
  
  /**
   * The function called when the menu item is selected.
   * For the predefined row operations, this value is ignored. For any other row operation this value is mandatory.
   */
  private BiConsumer<List<T>, T> consumer;  
  
  /**
   * Constructor to be used for predefined row operations.
   * @param menuText the text for the context menu.
   */
  public TableRowOperationDescriptor(String menuText) {
    this(menuText, null);
  }
  
  /**
   * Constructor to be used for non-predefined row operations.
   * 
   * @param menuText the text for the context menu.
   * @param consumer the function called when the menu item is selected by the user.
   */
  public TableRowOperationDescriptor(String menuText, BiConsumer<List<T>, T> consumer) {
    this.menuText = menuText;
    this.consumer = consumer;
  }

  /**
   * Get the context menu text.
   * 
   * @return  the context menu text.
   */
  public String getMenuText() {
    return menuText;
  }

  /**
   * Get the function called when the menu item is selected by the user.
   * 
   * @return the function called when the menu item is selected by the user.
   */
  public BiConsumer<List<T>, T> getConsumer() {
    return consumer;
  }

  
  /**
   * Set the function called when the menu item is selected by the user.
   * 
   * @param consumer the function called when the menu item is selected by the user.
   */
  public void setConsumer(BiConsumer<List<T>, T> consumer) {
    this.consumer = consumer;
  }
  
}
