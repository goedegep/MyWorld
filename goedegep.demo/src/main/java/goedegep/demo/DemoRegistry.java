package goedegep.demo;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete Demo application.
 */
public class DemoRegistry extends Registry {
  
  /**
   * Singleton instance of the EventsRegistry.
   */
  private static DemoRegistry instance = null;

  /**
   * Get the singleton instance of the DemoRegistry.
   * 
   * @return the singleton instance of the DemoRegistry.
   */
  public static DemoRegistry getInstance() {
    if (instance == null) {
      instance = new DemoRegistry();
    }
    
    return instance;
  }

  /**
   * Private constructor for the DemoRegistry.
   */
  private DemoRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Demo application for the Goedegep MyWorld framework.");
  }
}
