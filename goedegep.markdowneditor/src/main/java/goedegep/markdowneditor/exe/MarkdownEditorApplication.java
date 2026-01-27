package goedegep.markdowneditor.exe;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import goedegep.jfx.JfxApplication;
import goedegep.markdowneditor.svc.MarkdownEditorService;
import goedegep.myworld.common.MyWorldUtil;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the Markdown editor JavaFX application.
 */
public class MarkdownEditorApplication extends JfxApplication {

  /**
   * Command line arguments passed to the application.
   * It seems {#link Application.getParameters()} does not work in this context, so we store them in this value.
   */
  private static String[] appArgs;

  /**  
   * Main method to start the Markdown editor application.
   * 
   * @param args command line arguments. If there is at least one argument, the first argument is treated as the filename to open.
   */
  public static void main(String[] args) {
    appArgs = args;
    launch();
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    
    // Setup logging. Only log to a file when not running in Eclipse.
    String logFileBaseName = null;
    
    if (!RunningInEclipse.runningInEclipse()) {
      String applicationName = null;
      Properties props = new Properties();
      try (InputStream in = MarkdownEditorService.class.getResourceAsStream(MarkdownEditorService.MARKDOWN_EDITOR_APPLICATION_PROPERTIES_FILE_NAME)) {
          props.load(in);
          
          applicationName = props.getProperty("markdowneditor.name");
      } catch (Exception e) {
        JfxApplication.reportException(null, e);
        System.exit(1);
      }

      logFileBaseName = MyWorldUtil.createLogFileBaseName(applicationName);
    }
    logSetup(Level.SEVERE, logFileBaseName);
    
    // Catch any uncaught exceptions and report them.
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        reportException(null, (Exception) ex);
      }
    };
    Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
    javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    
    String filename = null;
    if (appArgs.length > 0) {
      filename = appArgs[0];
    }
    
    try {
      MarkdownEditorService.getInstance().showMarkdownEditor(filename);
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
}
