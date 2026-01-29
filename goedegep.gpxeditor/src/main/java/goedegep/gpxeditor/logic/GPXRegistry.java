package goedegep.gpxeditor.logic;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete GPX application.
 */
public class GPXRegistry extends Registry {  
  
  /**
   * Singleton instance of the GPXRegistry.
   */
  private static GPXRegistry instance = null;

  /**
   * Get the singleton instance of the GPXRegistry.
   * 
   * @return the singleton instance of the GPXRegistry.
   */
  public static GPXRegistry getInstance() {
    if (instance == null) {
      instance = new GPXRegistry();
    }
    
    return instance;
  }
  
  /**
   * Private constructor for the GPXRegistry.
   */
  private GPXRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("GPX editor and viewer");
    setPropertyDescriptorsFileName("GPXPropertyDescriptors.xmi");
    setUserPropertiesFileName("GPXUserPreferences.xmi");
  }
}
