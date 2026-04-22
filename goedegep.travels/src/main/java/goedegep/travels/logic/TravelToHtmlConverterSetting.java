package goedegep.travels.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * This enum provides settings for the {@link TravelToHtmlConverter}.
 * <p>
 * A more detailed description of each setting is provided in that class.
 *
 */
public enum TravelToHtmlConverterSetting {
  SHOW_LOCATION_COORDINATES("Show the coordinates of locations", "If set the coordinates of locations are shown."),
  PARAGRAPH_MODE("Paragraph mode", "In paragraph mode, Days are shown as paragraphs. Otherwise they are rows in a table (referred to as table mode.");
  
  private final String displayName;
  private Object value;
  private final String description;
  
  private static Set<TravelToHtmlConverterSetting> defaultSettings = null;
  
  private TravelToHtmlConverterSetting(String displayName, String description) {
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
  
  public static Set<TravelToHtmlConverterSetting> getDefaultSettings() {
    if (defaultSettings == null) {
      defaultSettings = new HashSet<>();
      defaultSettings.add(SHOW_LOCATION_COORDINATES);
      defaultSettings.add(PARAGRAPH_MODE);
    }
    return defaultSettings;
  }

}
