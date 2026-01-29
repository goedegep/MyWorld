package goedegep.travels.logic;

import goedegep.jfx.eobjecttreeview.EnumStringConverter;
import goedegep.poi.app.LocationCategory;

public class EnumStringConverterForLocationCategory {
  
  /**
   * Singleton instance of EnumStringConverter for LocationCategory.
   */
  private static EnumStringConverter<LocationCategory> instance;
  
  /**
   * Private constructor to prevent instantiation.
   */
  private EnumStringConverterForLocationCategory() {
  }
  
  /**
   * Get the instance of the EnumStringConverter for LocationCategory.
   */
  public static EnumStringConverter<LocationCategory> getInstance() {
    if (instance == null) {
      instance = new EnumStringConverter<>(LocationCategory.class, LocationCategory::getDisplayName);
    }
    
    return instance;
  }
}