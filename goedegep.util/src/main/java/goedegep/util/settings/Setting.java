package goedegep.util.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class represents a setting with a name, description, default value and current value.
 * <p>
 * The current value can be changed and listened to.
 */
public class Setting {
  /**
   * The name of the setting, which is also used to identify the setting.
   */
  private final String name;
  
  /**
   * A description of the setting, which is used to explain the purpose of the setting to the user.
   */
  private final String description;
  
  /**
   * The default value of the setting.
   */
  private final boolean defaultValue;
  
  /**
   * The current value of the setting, which can be changed and listened to.
   */
  private BooleanProperty value = new SimpleBooleanProperty();

  
  /**
   * Constructor.
   * <p>
   * @param name The name of the setting, which is also used to identify the setting.
   * @param description A description of the setting, which is used to explain the purpose of the setting to the user.
   * @param defaultValue The default value of the setting.
   */
  public Setting(String name, String description, boolean defaultValue) {
    this.name = name;
    this.description = description;
    this.defaultValue = defaultValue;
    this.value.set(defaultValue);
  }

  /*
   * Gets the name of the setting.
   * 
   * @return The name of the setting.
   */
  public String getName() {
    return name;
  }

  /*
   * Gets the description of the setting.
   * 
   * @return The description of the setting.    
   */
  public String getDescription() {
    return description;
  }

  /*
   * Gets the default value of the setting.
   * 
   * @return The default value of the setting.
   */
  public boolean getDefaultValue() {
    return defaultValue;
  }

  /*
   * Gets the current value of the setting.
   * 
   * @return The current value of the setting.
   */
  public boolean getValue() {
    return value.get();
  }
  
  /*
   * Gets the current value of the setting as a {@code BooleanProperty}, which can be listened to.
   * 
   * @return The current value of the setting as a {@code BooleanProperty}.
   */
  public BooleanProperty valueProperty() {
    return value;
  }

  /*
   * Sets the current value of the setting.
   * 
   * @param value The new value of the setting.
   */
  public void setValue(boolean value) {
    this.value.set(value);
  }

}
