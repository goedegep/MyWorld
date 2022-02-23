package goedegep.jfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * This class provides utility methods for creating menu's.
 */
public class MenuUtil {
//  public static MenuItem addMenuItem(Menu menu, String text,
//      Icon defaultIcon, Icon disabledIcon, ActionListener actionListener) {
//    MenuItem item = new MenuItem(text, defaultIcon);
//    item.setDisabledIcon(disabledIcon);
//    item.addActionListener(actionListener);
//    menu.add(item);
//    
//    return item;
//  }
//  
//  public static MenuItem addMenuItem(Menu menu, String text, Icon defaultIcon, ActionListener actionListener) {
//    MenuItem  item = new MenuItem(text, defaultIcon);
//    if (actionListener != null) {
//      item.addActionListener(actionListener);
//    }
//    menu.add(item);
//    
//    return item;
//  }
  
  public static MenuItem addMenuItem(Menu menu, String text, EventHandler<ActionEvent> eventHandler) {
    MenuItem  item = new MenuItem(text);
    item.setOnAction(eventHandler);
    menu.getItems().add(item);
    
    return item;
  }
  
  public static MenuItem addCheckMenuItem(Menu menu, String text, EventHandler<ActionEvent> eventHandler) {
    CheckMenuItem  item = new CheckMenuItem(text);
    item.setOnAction(eventHandler);
    menu.getItems().add(item);
    
    return item;
  }
}
