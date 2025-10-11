package goedegep.pctools.markdown;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.pctools.markdown.guifx.MarkdownViewer;

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
    customization = DefaultCustomizationFx.getInstance();
  }
}
