package goedegep.travels.exe;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.jfx.JfxApplication;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import goedegep.vacations.app.TravelsService;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the Travels JavaFX application.
 */
public class TravelsApplication extends JfxApplication {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelsApplication.class.getName());
  
  private static final String LOG_SUBFOLDER = "MyWorld";


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   */
  public TravelsApplication() {
  }
  
   /**  
   * Main method to start the Travels JavaFX application.
   * 
   * @param args command line arguments, which are ignored in this application.
   */
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    String applicationName = getApplicationNameFromApplicationProperties();
        
    // Setup logging. Only log to a file when not running in Eclipse.
    String logFileBaseName = null;
    if (!RunningInEclipse.runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + applicationName + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
    
    TravelsService travelsService = TravelsService.getInstance();
    
    try {
      travelsService.showTravelsWindow();

      // Catch any uncaught exceptions in the JavaFX application thread.
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
          reportException(null, (Exception) ex);
        }
      };
      Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
      javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
  
  protected String getApplicationNameFromApplicationProperties() {
    Properties properties = TravelsService.getApplicationProperties();
    return properties.getProperty("travels.app.name");
    
//    Properties props = new Properties();
//    try (InputStream in = getClass().getResourceAsStream("TravelsApplication.properties")) {
//        props.load(in);
//        
//        return props.getProperty("travels.app.name");
//    } catch (Exception e) {
//      JfxApplication.reportException(null, e);
//      System.exit(1);
//    }
//    
//    return null;
  }

}
