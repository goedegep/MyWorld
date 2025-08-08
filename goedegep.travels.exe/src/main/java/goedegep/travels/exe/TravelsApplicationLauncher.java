package goedegep.travels.exe;

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
  
  private static final String PROGRAM_NAME = "Travels";
  private static final String LOG_SUBFOLDER = "MyWorld";
  private static final String PROGRAM_DESCRIPTION =
      PROGRAM_NAME + "Is an application for planning, enjoying and archiving travels.";
  


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public TravelsApplicationLauncher() {
    String logFileBaseName = null;
    if (!runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + PROGRAM_NAME + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
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
    } catch (Exception e) {
      LOGGER.severe("Error reading application properties: " + e.getMessage());
      System.exit(1);
    }
    
    TravelsApplication travelsApplication = TravelsApplication.getInstance();
    
    try {
      travelsApplication.showTravelsWindow();


//      // Read the customization info.
//      CustomizationsFx.addCustomizations(new VacationsRegistry().getCustomizationFileURL());
//      
//      LOGGER.severe("Customization added");
//
//      CustomizationFx customization = CustomizationsFx.getCustomization(MyWorldAppModule.VACATIONS.name());
//
//      Logger.getGlobal().severe("Hello World");

      Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
          reportException(DefaultCustomizationFx.getInstance(), (Exception) ex);
        }
      };
      Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
      javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

//      new VacationsWindow(customization);
    } catch (Exception ex) {
      reportException(DefaultCustomizationFx.getInstance(), ex);
    }
    
  }
  

}
