package goedegep.jfx.controls;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 * Source: https://gist.github.com/floralvikings/10290131
 * @author Caleb Brinkman
 */
public class AutoCompleteTextField extends TextField
{
  /**
   * The existing autocomplete entries.
   */
  private final SortedSet<String> entries;
  
  /**
   * The popup used to show the current matches and to select an entry.
   */
  private ContextMenu entriesPopup;

  /**
   * Constructor
   */
  public AutoCompleteTextField() {
    super();
    entries = new TreeSet<>(new Comparator<String>() {

      @Override
      public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
      }
      
    });
    entriesPopup = new ContextMenu();
    
    textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {
      if (getScene() == null) {  // Fix for when used in a TitledPane
        return;
      }
      
      if ((getText() == null) || getText().isEmpty())
      {
        entriesPopup.hide();
      } else {
        LinkedList<String> searchResult = new LinkedList<>();
        String text = getText().toLowerCase();
        searchResult.addAll(entries.subSet(text, text + Character.MAX_VALUE));
        if (entries.size() > 0)
        {
          populatePopup(searchResult);
          if (!entriesPopup.isShowing()) {
            entriesPopup.show(this, Side.BOTTOM, 0, 0);
          }
        } else {
          entriesPopup.hide();
        }
      }
    });

    focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) -> entriesPopup.hide());
  }

  /**
   * Get the existing set of autocomplete entries.
   * 
   * @return The existing autocomplete entries.
   */
  public SortedSet<String> getEntries() {
    return entries;
  }
  
  /**
   * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
   * 
   * @param searchResult The set of matching strings.
   */
  private void populatePopup(List<String> searchResult) {
    List<CustomMenuItem> menuItems = new LinkedList<>();
    // If you'd like more entries, modify this line.
    int maxEntries = 10;
    int count = Math.min(searchResult.size(), maxEntries);
    for (int i = 0; i < count; i++) {
      final String result = searchResult.get(i);
      Label entryLabel = new Label(result);
      CustomMenuItem item = new CustomMenuItem(entryLabel, true);
      item.setOnAction((ActionEvent actionEvent) -> {
        setText(result);
        entriesPopup.hide();
      });
      menuItems.add(item);
    }
    entriesPopup.getItems().clear();
    entriesPopup.getItems().addAll(menuItems);

  }
}