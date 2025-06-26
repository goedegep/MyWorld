package goedegep.gpx.exe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import goedegep.gpx.app.GPXWindow;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.app.MyWorldAppModule;
import goedegep.pctools.app.logic.PCToolsRegistry;
import goedegep.properties.app.PropertiesHandler;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


public class GPXApplication extends JfxApplication {
  private static final Logger LOGGER = Logger.getLogger(GPXApplication.class.getName());
  
  private static final String PROGRAM_NAME = "GPX Editor";
  private static final String LOG_SUBFOLDER = "MyWorld";
  private static final String PROGRAM_DESCRIPTION =
      PROGRAM_NAME + "Is an application for viewing and editing GPX files.";
  
  private static String[] args;
      
  public static void main(String[] args) {
    GPXApplication.args = args;
    launch();
  }


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public GPXApplication() {
    String logFileBaseName = null;
    if (!runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + PROGRAM_NAME + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    
    LOGGER.severe("=>");
    
    // Define command line arguments.
    Options options = new Options();    
    boolean optionsOK = true;
    String fileToOpen = null;
    List<String> errorTexts = new ArrayList<>();

    // Parse/handle command line arguments.
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine cmd = parser.parse(options, args);
      String[] arguments = cmd.getArgs();
      
      // Accept a single filename argument.
      if (arguments.length > 1) {
        errorTexts.add("At most one filename may be specified.");
        optionsOK = false;
      } else if (arguments.length == 1) {
        fileToOpen = arguments[0];
      }
    } catch (ParseException e) {
      if (e instanceof UnrecognizedOptionException unrecognizedOptionException) {
        errorTexts.add("Unknown option: '" + unrecognizedOptionException.getOption() + "'");
      } else if (e instanceof MissingArgumentException missingArgumentException) {
        errorTexts.add("Value is missing for option: '" + missingArgumentException.getOption().getOpt() + "'");
      } else if (e instanceof MissingOptionException missingOptionException) {
        @SuppressWarnings("unchecked")
        List<String> missingOptions = missingOptionException.getMissingOptions();
        if (missingOptions.size() == 1) {
          errorTexts.add("Mandatory option is missing: '" + missingOptions.get(0) + "'");
        } else {
          StringBuilder buf = new StringBuilder();
          for (int i = 0; i < missingOptions.size(); i++) {
            if (i > 0) {
              buf.append(", ");
              buf.append(missingOptions.get(i));
            }
          }
          errorTexts.add("Mandatory options are missing: '" + buf.toString() + "'");
        }
      } else {
        errorTexts.add("Systeem message: \"" + e.getMessage() + "\"");
      }
      optionsOK = false;
    }
    
    LOGGER.severe("Command line arguments handled");

    if (!optionsOK) {
      showUsageInfoDialogAndExit(PROGRAM_NAME, options, PROGRAM_DESCRIPTION, errorTexts, args);
    }
    
    LOGGER.severe("optionsOK");
    
    boolean runningInEclipse = runningInEclipse();

    // DevelopmentMode
    // In development mode extra items are added to menu's.
    // For now DevelopmentMode is active when 'Running in eclipse'.
    if (runningInEclipse) {
      PCToolsRegistry.developmentMode = true;  // FIXME TODO decouple GPX from PCTools
    }
    
    try {
      // Handle properties
      java.net.URL url = new PCToolsRegistry().getPropertyFileURL();
      LOGGER.severe("url = " + (url != null ? url.toString() : "<null>"));
      PropertiesHandler.handleProperties(runningInEclipse, url, null);
      
      LOGGER.severe("Properties handled");

      // Read the customization info.
      CustomizationsFx.addCustomizations(new PCToolsRegistry().getCustomizationFileURL());
      
      LOGGER.severe("Customization added");

      CustomizationFx customization = CustomizationsFx.getCustomization(MyWorldAppModule.PCTOOLS.name());

      Logger.getGlobal().severe("Hello World");

      Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
          reportException(customization, (Exception) ex);
        }
      };
      Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
      javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

      new GPXWindow(customization, fileToOpen);
    } catch (Exception ex) {
      reportException(DefaultCustomizationFx.getInstance(), ex);
    }
    
  }
  
}
