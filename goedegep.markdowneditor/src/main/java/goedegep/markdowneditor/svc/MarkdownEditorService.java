package goedegep.markdowneditor.svc;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.markdowneditor.gui.MarkdownEditorResources;
import goedegep.markdowneditor.logic.MarkdownEditorRegistry;
import goedegep.markdowneditor.gui.MarkdownEditor;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import javafx.scene.paint.Color;

/**
 * The MarkdownService class is the main class for the Markdown application.
 * <p>
 * It provides methods to show the main window and manages
 * application-wide resources and customization.
 */
public class MarkdownEditorService extends Service {
  public static final String MARKDOWN_EDITOR_APPLICATION_PROPERTIES_FILE_NAME = "MarkdownEditorApplication.properties";
  
  /**
   * The singleton instance of the MarkdownEditorService.
   */
  private static MarkdownEditorService instance = null;
  
  /**
   * The registry for the Markdown editor.
   */
  private MarkdownEditorRegistry markdownEditorRegistry;
  
  /**
   * Get the singleton instance of the MarkdownEditorService.
   * 
   * @return the singleton instance of MarkdownService.
   */
  public static MarkdownEditorService getInstance() {
    if (instance == null) {
      instance = new MarkdownEditorService();
      instance.initialize();
    }
    return instance;
  }
  
  /**
   * Show the main window of the application.
   */
  public void showMarkdownEditor(String filename) {
    new MarkdownEditor(customization, filename);
  }
  
  /**
   * Private constructor to ensure singleton pattern.
   */
  private MarkdownEditorService() {
    markdownEditorRegistry = MarkdownEditorRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream(MARKDOWN_EDITOR_APPLICATION_PROPERTIES_FILE_NAME)) {
        props.load(in);
        
        markdownEditorRegistry.setVersion(props.getProperty("markdowneditor.version"));
        markdownEditorRegistry.setApplicationName(props.getProperty("markdowneditor.name"));
        markdownEditorRegistry.setAuthor(props.getProperty("markdowneditor.author"));
        markdownEditorRegistry.setCopyrightMessage(props.getProperty("markdowneditor.copyright"));
        markdownEditorRegistry.setShortProductInfo(props.getProperty("markdowneditor.description"));
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
    return new MarkdownEditorResources();
  }
  
  @Override
  protected Registry getRegistry() {
    return markdownEditorRegistry;
  }
}
