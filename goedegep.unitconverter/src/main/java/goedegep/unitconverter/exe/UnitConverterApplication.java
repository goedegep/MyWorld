package goedegep.unitconverter.exe;

import java.io.File;
import java.util.logging.Level;

import goedegep.jfx.JfxApplication;
import goedegep.unitconverter.svc.UnitConverterService;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the Unit Converter JavaFX application.
 */
public class UnitConverterApplication extends JfxApplication {
  private static final String PROGRAM_NAME = "Unit Converter";
  private static final String LOG_SUBFOLDER = "MyWorld";


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   */
  public UnitConverterApplication() {
  }
  
  /**  
   * Main method to start the Unit Converter JavaFX application.
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
      UnitConverterService.getInstance().showUnitConverterMenuWindow();
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
}
