package goedegep.finan.exe;

import java.io.File;
import java.util.logging.Level;

import goedegep.finan.app.FinanService;
import goedegep.jfx.JfxApplication;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the Finan JavaFX application.
 */
public class FinanApplication extends JfxApplication {
  private static final String PROGRAM_NAME = "Finan";
  private static final String LOG_SUBFOLDER = "MyWorld";


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   */
  public FinanApplication() {
  }
  
  /**
   * Main method to start the Finan JavaFX application.
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
      FinanService.getInstance().showFinanMenuWindow();
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
  
}
