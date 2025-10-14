package goedegep.markdown.app;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.markdown.app.guifx.MarkdownViewer;
import goedegep.util.RunningInEclipse;

public class MarkdownService {
  private CustomizationFx customization;
  private static MarkdownService instance = null;
  
  public static MarkdownService getInstance() {
    if (instance == null) {
      instance = new MarkdownService();
    }
    return instance;
  }
  
  public void showMarkdownViewer() {
    new MarkdownViewer(customization, null);
  }
  
  private MarkdownService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      MarkdownRegistry.developmentMode = true;
    }
    
    customization = DefaultCustomizationFx.getInstance();
  }
}
