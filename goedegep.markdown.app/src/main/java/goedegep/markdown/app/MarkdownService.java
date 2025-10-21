package goedegep.markdown.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.markdown.app.guifx.MarkdownViewer;
import goedegep.myworld.common.Service;
import goedegep.util.RunningInEclipse;

public class MarkdownService extends Service {
  private CustomizationFx customization;
  private static MarkdownService instance = null;
  
  public static MarkdownService getInstance() {
    if (instance == null) {
      instance = new MarkdownService();
      instance.initialize();
    }
    return instance;
  }
  
  /**
   * Show the main window of the application.
   */
  public void showMarkdownViewer() {
    new MarkdownViewer(customization, null);
  }
  
  /**
   * Private constructor to ensure singleton pattern.
   */
  private MarkdownService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      MarkdownRegistry.developmentMode = true;
    }
    
    customization = DefaultCustomizationFx.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("MarkdownApplication.properties")) {
        props.load(in);
        
        MarkdownRegistry.version = props.getProperty("markdown.app.version");
        MarkdownRegistry.applicationName = props.getProperty("markdown.app.name");
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    MarkdownRegistry.developmentMode = developmentMode;
  }
}
