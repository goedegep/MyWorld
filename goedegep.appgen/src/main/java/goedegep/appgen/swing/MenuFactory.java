package goedegep.appgen.swing;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComponent;

import goedegep.appgen.PgMenuItem;

public class MenuFactory {
  public static PgMenuItem addMenuItem(JComponent menu, String text,
      Icon defaultIcon, Icon disabledIcon, ActionListener actionListener) {
    PgMenuItem item = new PgMenuItem(text, defaultIcon);
    item.setDisabledIcon(disabledIcon);
    item.addActionListener(actionListener);
    menu.add(item);
    
    return item;
  }
  
  public static PgMenuItem addMenuItem(JComponent menu, String text,
      Icon defaultIcon, ActionListener actionListener) {
    PgMenuItem  item = new PgMenuItem(text, defaultIcon);
    if (actionListener != null) {
      item.addActionListener(actionListener);
    }
    menu.add(item);
    
    return item;
  }
  
  public static PgMenuItem addMenuItem(JComponent menu, String text, ActionListener actionListener) {
    PgMenuItem  item = new PgMenuItem(text);
    item.addActionListener(actionListener);
    menu.add(item);
    
    return item;
  }
}