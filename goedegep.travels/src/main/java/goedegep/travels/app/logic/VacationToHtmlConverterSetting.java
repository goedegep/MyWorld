package goedegep.travels.app.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * This enum provides settings for the {@link VacationToHtmlConverter}.
 * <p>
 * A more detailed description of each setting is provided in that class.
 *
 */
public enum VacationToHtmlConverterSetting {
  SHOW_LOCATION_COORDINATES("Show the coordinates of locations", "If set the coordinates of locations are shown."),
  PARAGRAPH_MODE("Paragraph mode", "In paragraph mode, Days are shown as paragraphs. Otherwise they are rows in a table (referred to as table mode.");
  
  private final String displayName;
  private Object value;
  private final String description;
  
  private static Set<VacationToHtmlConverterSetting> defaultSettings = null;
  
  private VacationToHtmlConverterSetting(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean getBooleanValue() {
    return (boolean) value;
  }

  public void setBooleanValue(boolean value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }
  
  public static Set<VacationToHtmlConverterSetting> getDefaultSettings() {
    if (defaultSettings == null) {
      defaultSettings = new HashSet<>();
      defaultSettings.add(SHOW_LOCATION_COORDINATES);
      defaultSettings.add(PARAGRAPH_MODE);
    }
    return defaultSettings;
  }

}
