package goedegep.gpxeditor.exe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import goedegep.gpxeditor.svc.GPXService;
import goedegep.jfx.JfxApplication;
import goedegep.util.RunningInEclipse;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the GPX Editor JavaFX application.
 */
public class GPXEditorApplication extends JfxApplication {
  private static final String PROGRAM_NAME = "GPX Editor";
  private static final String LOG_SUBFOLDER = "MyWorld";
  private static final String PROGRAM_DESCRIPTION =
      PROGRAM_NAME + " is an application for viewing and editing GPX files.";
  
  private static String[] args;


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   */
  public GPXEditorApplication() {
  }

  /**  
   * Main method to start the GPX Editor JavaFX application.
   * 
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    GPXEditorApplication.args = args;
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
    
    String fileToOpen = handleCommandLineArguments();
    
    
    try {
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
          reportException(null, (Exception) ex);
        }
      };
      Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
      javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

      GPXService.getInstance().showGPXWindow(fileToOpen);
    } catch (Exception ex) {
      reportException(null, ex);
    }
    
  }
  
  /**
   * Handle command line arguments.
   * <p>
   * Only a single optional filename argument is supported.
   * 
   * @return the filename to open, or null when no filename was specified.
   */
  private String handleCommandLineArguments() {
    // Define command line arguments.
    Options options = new Options();  // just empty options  
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

    if (!optionsOK) {
      showUsageInfoDialogAndExit(PROGRAM_NAME, options, PROGRAM_DESCRIPTION, errorTexts, args);
    }
    
    return fileToOpen;
  }
}
