package goedegep.jfx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import goedegep.util.RunningInEclipse;
import goedegep.util.logging.MyLoggingFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;

public abstract class JfxApplication extends Application {
  protected static final String         NEWLINE = System.getProperty("line.separator");
  
  private static boolean loggingAlreadySetup = false;  // Used to detect that logging is setup more than once.
    
  /**
   * Logging setup.
   * <p>
   * The following setup is performed:
   * <ul>
   * <li>Set the specified logging level</li>
   * <li>Install a {@link MyLoggingFormatter}.</li>
   * <li>Install logging to a file, if a logFileBaseName is specified.</li>
   * </ul>
   * 
   * @param level the logging level to be set up.
   * @param logFileBaseName base name of the file to which logging information will be written. The actual filename is this base name with ".txt" appended to it.
   *                        If null, no file logging takes place.
   */
  protected static void logSetup(Level level, String logFileBaseName) {
    if (loggingAlreadySetup) {
      throw new RuntimeException("Logging is already setup");
    }
    
    loggingAlreadySetup = true;
    
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(level);
    
    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(level);
    
    if (logFileBaseName != null) {
      try {
        FileHandler fileHandler = new FileHandler(logFileBaseName + ".txt", false);   // true forces append mode
        Formatter simpleFormatter = new MyLoggingFormatter();
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);
      } catch (SecurityException | IOException e) {
        e.printStackTrace();
      }
    }
    
  }
  
  /**
   * Show usage information and exit.
   * <p>
   * This method displays an error dialog, showing:
   * <ul>
   * <li>what is wrong</li>
   * <li>with which arguments the application was actually started</li>
   * <li>how the application shall be started (the actual usage information)</li>
   * <li>a description of the functionality of the application</li>
   * </ul>
   * After user confirmation, the application will exit.
   * 
   * @param programName the name of the application.
   * @param options  as specification of the command line arguments.
   * @param programDescription the description of the functionality of the application.
   * @param errorTexts a list of error texts.
   * @param args the command line arguments with which the application was called.
   */
  protected static void showUsageInfoDialogAndExit(final String programName, Options options, final String programDescription,
      List<String> errorTexts, final String[] args) {
    StringBuilder buf = new StringBuilder();
    buf.append("<html>");
    
    // Error texts
    buf.append("<b>");
    for (String errorText: errorTexts) {
      buf.append(errorText);
      buf.append("<br/>");
    }
    buf.append("</b>");
    buf.append("<br/>");
    
    // How the application was called
    buf.append("<pre>");
    buf.append(programName);
    buf.append(" is alsvolgt aangeroepen:");
    buf.append(NEWLINE);
    
    boolean first = true;
    buf.append("    ");
    buf.append(programName);
    buf.append(" ");
    for (int i = 0; i < args.length; i++) {
      if (first) {
        first = false;
      } else {
        buf.append(" ");
      }
      buf.append(args[i]);
    }
    buf.append(NEWLINE);
    buf.append(NEWLINE);

    // The usage information and description
    StringWriter out = new StringWriter();
    PrintWriter writer = new PrintWriter(out);
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(writer, 110, programName, null, options, 4, 4, NEWLINE + programDescription, true);
    String helpText = out.toString().replaceFirst("usage", "Gebruik");
    helpText = helpText.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    buf.append(helpText);
    buf.append("</pre>");
    buf.append("</html>");
    
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Foutmelding");
    alert.setHeaderText(programName + " is niet correct aangeroepen");
    WebView webView = new WebView();
    webView.getEngine().loadContent(buf.toString());
    webView.setPrefSize(900, 300);
    alert.getDialogPane().setContent(webView);    
    alert.showAndWait();

    Platform.exit();
  }
  
  /**
   * Report an exception.
   * <p>
   * The stack trace of the exception is logged.<br/>
   * And if the application is not running in Eclipse (an official installation), the stack trace is also shown in an exception dialog.
   * @param exception
   */
  public static void reportException(CustomizationFx customization, Exception exception) {
    if (customization == null) {
      customization = DefaultCustomizationFx.getInstance();
    }
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    String exceptionText = sw.toString();
    Logger.getGlobal().severe(exceptionText);

    if (!RunningInEclipse.runningInEclipse()) {
      componentFactory.createExceptionDialog("An exception occurred", (Exception) exception).showAndWait();
    }
  }
}
