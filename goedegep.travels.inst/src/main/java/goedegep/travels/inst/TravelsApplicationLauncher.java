package goedegep.travels.inst;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.util.thread.ThreadUtil;
import goedegep.vacations.app.TravelsApplication;
import goedegep.vacations.app.logic.VacationsRegistry;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the Travels JavaFX application.
 */
public class TravelsApplicationLauncher extends JfxApplication {
  private static final Logger LOGGER = Logger.getLogger(TravelsApplicationLauncher.class.getName());
  
  private static final String LOG_SUBFOLDER = "MyWorld";  


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public TravelsApplicationLauncher() {
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
    
    // DevelopmentMode
    // In development mode extra items are added to menu's.
    // For now DevelopmentMode is active when 'Running in eclipse'.
    if (runningInEclipse()) {
      VacationsRegistry.developmentMode = true;
    }
    
    /*
     * Read the application properties.
     * This is done here because the application properties contain the application version, which is derived from the version in the pom.xml file.
     */
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("/Application.properties")) {
        props.load(in);
        String appVersion = props.getProperty("app.version");
        VacationsRegistry.version = appVersion;
        String appName = props.getProperty("app.name");
        VacationsRegistry.applicationName = appName;
    } catch (Exception e) {
      LOGGER.severe("Error reading application properties: " + e.getMessage());
      System.exit(1);
    }
    
    // Setup logging. Only log to a file when not running in Eclipse.
    String logFileBaseName = null;
    if (!runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + VacationsRegistry.applicationName + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
    
    TravelsApplication travelsApplication = TravelsApplication.getInstance();
    
    try {
      travelsApplication.showTravelsWindow();

      // Catch any uncaught exceptions in the JavaFX application thread.
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
          reportException(DefaultCustomizationFx.getInstance(), (Exception) ex);
        }
      };
      Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
      javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

    } catch (Exception ex) {
      reportException(DefaultCustomizationFx.getInstance(), ex);
    }
    
  }
  

}
