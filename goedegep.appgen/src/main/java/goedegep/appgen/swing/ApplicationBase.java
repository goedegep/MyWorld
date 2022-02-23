package goedegep.appgen.swing;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import goedegep.util.logging.MyLoggingFormatter;


/**
 * This class is a base class for applications.
 * <p>
 * By extending this class, applications get some handy methods for free.
 * <ul>
 * <li>showUsageInfoDialogAndExit<br/>
 * Can be called when the application was called with the wrong arguments.
 * </li>
 * </ul>
 * @author Peter
 *
 */
public class ApplicationBase {
  protected static final String         NEWLINE = System.getProperty("line.separator");
  
  private static boolean loggingAlreadySetup = false;  // Used to detect that logging is setup more than once.


  /**
   * Logging setup.
   * <p>
   * The following setup is performed:
   * <ul>
   * <li>Set the specified logging level</li>
   * <li>Install an {@link MyLoggingFormatter}.</li>
   * <li>Install logging to a file, if a logFileBaseName is specified.</li>
   * </ul>
   * 
   * @param level the logging level to be set up.
   * @param logFileBaseName base name of the file to which logging information will be written. The actual filename is this base name with ".txt" appended to it.
   *                        If null, no file logging takes place.
   */
  protected void logSetup(Level level, String logFileBaseName) {
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
    StringBuilder argInfo = new StringBuilder();
    argInfo.append("<html>");

    // Foutmeldingen
    argInfo.append("<b>");
    for (String errorText: errorTexts) {
      argInfo.append(errorText);
      argInfo.append("<br/>");
    }
    argInfo.append("</b>");
    argInfo.append("<br/>");

    // Hoe het programma aangeroepen is
    argInfo.append("<pre>");
    argInfo.append(programName);
    argInfo.append(" is alsvolgt aangeroepen:");
    argInfo.append(NEWLINE);

    boolean first = true;
    argInfo.append("    ");
    argInfo.append(programName);
    argInfo.append(" ");
    for (int i = 0; i < args.length; i++) {
      if (first) {
        first = false;
      } else {
        argInfo.append(" ");
      }
      argInfo.append(args[i]);
    }
    argInfo.append(NEWLINE);
    argInfo.append(NEWLINE);

    // Help tekst
    StringWriter out = new StringWriter();
    PrintWriter writer = new PrintWriter(out);
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(writer, 2000, programName, null, options, 4, 4, programDescription, true);
    String helpText = out.toString().replaceFirst("usage", "Gebruik");
    helpText = helpText.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    argInfo.append(helpText);
    argInfo.append("</pre>");
    argInfo.append("</html>");
    JTextPane textPane = new JTextPane();
    textPane.setContentType("text/html");
    textPane.setText(argInfo.toString());

    JOptionPane.showMessageDialog(null,
        textPane,
        "Foutmelding",
        JOptionPane.ERROR_MESSAGE);

    System.exit(0);
  }
}
