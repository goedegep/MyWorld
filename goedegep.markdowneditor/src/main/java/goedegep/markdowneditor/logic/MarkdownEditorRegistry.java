package goedegep.markdowneditor.logic;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete Demo application.
 */
public class MarkdownEditorRegistry extends Registry {
  
  /**
   * Singleton instance of the MarkdownRegistry.
   */
  private static MarkdownEditorRegistry instance = null;

  /**
   * Get the singleton instance of the MarkdownRegistry.
   * 
   * @return the singleton instance of the MarkdownRegistry.
   */
  public static MarkdownEditorRegistry getInstance() {
    if (instance == null) {
      instance = new MarkdownEditorRegistry();
    }
    
    return instance;
  }
  
  /**
   * Private constructor for the MarkdownRegistry.
   */
  private MarkdownEditorRegistry() {
    super();
    
    setPropertyDescriptorsFileName("EventsPropertyDescriptors.xmi");
  }

}
