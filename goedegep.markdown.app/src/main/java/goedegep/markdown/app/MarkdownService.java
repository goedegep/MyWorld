package goedegep.markdown.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.markdown.app.guifx.MarkdownAppResources;
import goedegep.markdown.app.guifx.MarkdownViewer;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import javafx.scene.paint.Color;

/**
 * The MarkdownService class is the main class for the Markdown application.
 * <p>
 * It provides methods to show the main window and manages
 * application-wide resources and customization.
 */
public class MarkdownService extends Service {
  
  /**
   * The singleton instance of the MarkdownService.
   */
  private static MarkdownService instance = null;
  
  private MarkdownRegistry markdownRegistry;
  
  /**
   * Get the singleton instance of the MarkdownService.
   * 
   * @return the singleton instance of MarkdownService.
   */
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
    markdownRegistry = MarkdownRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("MarkdownApplication.properties")) {
        props.load(in);
        
        markdownRegistry.setVersion(props.getProperty("markdown.app.version"));
        markdownRegistry.setApplicationName(props.getProperty("markdown.app.name"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(238,238,238));
    look.setButtonBackgroundColor(Color.rgb(238,238,238));
    look.setPanelBackgroundColor(Color.rgb(238,238,238));
    look.setListBackgroundColor(Color.rgb(238,238,238));
    look.setLabelBackgroundColor(Color.rgb(238,238,238));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new MarkdownAppResources();
  }
  
  @Override
  protected Registry getRegistry() {
    return markdownRegistry;
  }
}
