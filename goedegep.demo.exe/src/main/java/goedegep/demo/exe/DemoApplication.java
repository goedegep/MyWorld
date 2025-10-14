package goedegep.demo.exe;

import java.io.File;
import java.util.logging.Level;

import goedegep.demo.DemoService;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


public class DemoApplication extends JfxApplication {
  private static final String PROGRAM_NAME = "Demo";
  private static final String LOG_SUBFOLDER = "MyWorld";
  
  public static void main(String[] args) {
    launch();
  }


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public DemoApplication() {
//    String logfileName = null;
//    if (!runningInEclipse()) {
//      logfileName = PROGRAM_NAME + "_logfile";
//    }
    
    String logFileBaseName = null;
    if (!RunningInEclipse.runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + PROGRAM_NAME + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
//    logSetup(Level.SEVERE, logfileName);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    CustomizationFx customization = DefaultCustomizationFx.getInstance();
    
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        reportException(customization, (Exception) ex);
      }
    };
    Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
    javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    
    try {
      DemoService.getInstance().showDemoWindow();
    } catch (Exception ex) {
      reportException(customization, ex);
    }
    
  }
}
