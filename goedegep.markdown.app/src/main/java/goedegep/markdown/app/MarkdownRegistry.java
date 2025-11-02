package goedegep.markdown.app;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete Demo application.
 */
public class MarkdownRegistry extends Registry {
  
  /**
   * Singleton instance of the MarkdownRegistry.
   */
  private static MarkdownRegistry instance = null;

  /**
   * Get the singleton instance of the MarkdownRegistry.
   * 
   * @return the singleton instance of the MarkdownRegistry.
   */
  public static MarkdownRegistry getInstance() {
    if (instance == null) {
      instance = new MarkdownRegistry();
    }
    
    return instance;
  }
  
  /**
   * Private constructor for the MarkdownRegistry.
   */
  private MarkdownRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Markdown Viewer");
    setPropertyDescriptorsFileName("EventsPropertyDescriptors.xmi");
    setUserPropertiesFileName("EventsUserPreferences.xmi");
  }

}
