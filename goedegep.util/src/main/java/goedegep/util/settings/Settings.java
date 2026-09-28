package goedegep.util.settings;

import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;


/**
 * This class provides a list of {@link Setting}s.
 * <p>
 * The settings are identified by their name.<br/>
 * As this class extends a {@link SimpleListProperty} you can listen to changees in the settings.
 */
public class Settings extends SimpleListProperty<Setting> {
  
  /**
   * Constructor.
   * <p>
   * Creates a new instance of the Settings class with the given settings.
   * 
   * @param settings The {@code Setting}s to be added to this instance.
   */
  public Settings(Setting... settings) {
    super();
    
    // Extractor to listen to changes in the value of the settings
    javafx.util.Callback<Setting, Observable[]> extractor = s -> new Observable[] {
        s.valueProperty()
    };
    ObservableList<Setting> list = javafx.collections.FXCollections.observableArrayList(extractor);
    
    for (Setting setting: settings) {
      list.add(setting);
    }
    
    set(list);
  }
  
  /**
   * Gets the {@code Setting} with the given name.
   * 
   * @param name The name of the {@code Setting} to get.
   * @return The {@code Setting} with the given name, or {@code null} if no such setting exists.
   */
  public Setting getSetting(String name) {
    for (Setting setting: get()) {
      if (setting.getName().equals(name)) {
        return setting;
      }
    }
    
    return null;
  }
  
  /**
   * Gets the value of the {@code Setting} with the given name.
   * 
   * @param name The name of the {@code Setting} to get the value of.
   * @return The value of the {@code Setting} with the given name, or {@code false} if no such setting exists.
   */
  public boolean getSettingValue(String name) {
    Setting setting = getSetting(name);
    if (setting != null) {
      return setting.getValue();
    }
    return false;
  }
  
  /**
   * Sets the value of the {@code Setting} with the given name.
   * 
   * @param name The name of the {@code Setting} to set the value of.
   * @param value The value to set.
   */
  public void setSettingValue(String name, boolean value) {
    Setting setting = getSetting(name);
    if (setting != null) {
      setting.setValue(value);
    }
  }
}
