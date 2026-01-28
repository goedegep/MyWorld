package goedegep.events.exe;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import goedegep.events.svc.EventsService;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.MyWorldUtil;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


/**
 * This class is the main entry point for the Events application.
 */
public class EventsApplication extends JfxApplication {
  
  /**  
   * Main method to start the Events application.
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
      try (InputStream in = EventsService.class.getResourceAsStream(EventsService.EVENTS_APPLICATION_PROPERTIES_FILE_NAME)) {
          props.load(in);
          
          applicationName = props.getProperty("events.name");
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
      EventsService.getInstance().showEventsWindow();
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
}
