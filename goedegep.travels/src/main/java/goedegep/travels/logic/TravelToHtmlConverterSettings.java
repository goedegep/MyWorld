package goedegep.travels.logic;

import goedegep.util.settings.Setting;

/**
 * This class provides settings for the {@link TravelToHtmlConverter}.
 * <p>
 * The settings are identified by their name, for which constants are provided in this class.
 * <p>
 * The settings are:
 * <ul>
 * <li>{@link #SHOW_LOCATION_COORDINATES_NAME} - If set the coordinates of locations are shown.</li>
 * <li>{@link #PARAGRAPH_MODE_NAME} - In paragraph mode, Days are shown as paragraphs. Otherwise they are rows in a table (referred to as table mode).</li>
 * </ul>
 */
public class TravelToHtmlConverterSettings extends goedegep.util.settings.Settings {
  
  /**
   * The name of the setting that controls whether the coordinates of locations are shown.
   */
  public static final String SHOW_LOCATION_COORDINATES_NAME = "Show Location Coordinates";
  
  /**
   * The name of the setting that controls whether the output is in paragraph mode or table mode.
   */
  public static final String PARAGRAPH_MODE_NAME = "Paragraph Mode";
  
  /**
   * The setting that controls whether the coordinates of locations are shown.
   */
  public static final Setting SHOW_LOCATION_COORDINATES = new Setting(SHOW_LOCATION_COORDINATES_NAME, "If set the coordinates of locations are shown.", false);
  
  /**
   * The setting that controls whether the output is in paragraph mode or table mode.
   */
  public static final Setting PARAGRAPH_MODE = new Setting(PARAGRAPH_MODE_NAME, "In paragraph mode, Days are shown as paragraphs. Otherwise they are rows in a table (referred to as table mode).", false);

  
  /**
   * Creates a new instance of the TravelToHtmlConverterSettings class with the default settings.
   */
  public TravelToHtmlConverterSettings() {
    super(SHOW_LOCATION_COORDINATES, PARAGRAPH_MODE);
  }

}
