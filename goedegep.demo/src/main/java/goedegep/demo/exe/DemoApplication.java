package goedegep.demo.exe;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import goedegep.demo.svc.DemoService;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.MyWorldUtil;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


/**
 * This class is the main entry point for the Demo application.
 */
public class DemoApplication extends JfxApplication {
  
  /**  
   * Main method to start the Demo application.
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
      String applicationName = null;
      Properties props = new Properties();
      try (InputStream in = DemoService.class.getResourceAsStream(DemoService.DEMO_APPLICATION_PROPERTIES_FILE_NAME)) {
          props.load(in);
          
          applicationName = props.getProperty("demo.name");
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
    
    try {
      DemoService.getInstance().showDemoWindow();
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
}
