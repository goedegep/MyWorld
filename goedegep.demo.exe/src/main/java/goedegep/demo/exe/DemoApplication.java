package goedegep.demo.exe;

import java.io.File;
import java.util.logging.Level;

import goedegep.demo.svc.DemoService;
import goedegep.jfx.JfxApplication;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


/**
 * This class is the main entry point for the Demo JavaFX application.
 */
public class DemoApplication extends JfxApplication {
  private static final String PROGRAM_NAME = "Demo";
  private static final String LOG_SUBFOLDER = "MyWorld";

  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   */
  public DemoApplication() {
  }
  
  /**  
   * Main method to start the Demo JavaFX application.
   * 
   * @param args command line arguments, which are ignored in this application.
   */
  public static void main(String[] args) {
    launch();
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    
    // Setup logging. Only log to a file when not running in Eclipse.
    String logFileBaseName = null;
    if (!RunningInEclipse.runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + PROGRAM_NAME + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
    
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        reportException(null, (Exception) ex);
      }
    };
    Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
    javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    
    try {
      DemoService.getInstance().showDemoWindow();
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
}
