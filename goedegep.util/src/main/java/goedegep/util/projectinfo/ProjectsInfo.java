package goedegep.util.projectinfo;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.logging.MyLoggingFormatter;

public class ProjectsInfo {
  private static final Logger         LOGGER = Logger.getLogger(ProjectsInfo.class.getName());
  private static final Level          logLevel = Level.INFO;

  public ProjectsInfo(String projectParentFolderName) {
    logSetup();
    
    LOGGER.info("=> projectParentFolderName = " + projectParentFolderName);
    
    AggregatedProjectInfo aggregatedProjectInfo = new AggregatedProjectInfo();
    
    Path projectParentFolder = Paths.get(projectParentFolderName);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(projectParentFolder)) {
      for (Path file: stream) {

        LOGGER.fine("Handling file: " + file.getFileName().toString());
        if (Files.isDirectory(file)  &&
            !file.getFileName().toString().startsWith(".")) {
          LOGGER.fine("Is directory: " + file.getFileName());
          
          ProjectInfo projectInfo = new ProjectInfo(file, aggregatedProjectInfo);
          projectInfo.handleProject();
        } else {
          LOGGER.fine("Skipping");
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
    
    aggregatedProjectInfo.print();
    
    LOGGER.info("<=");
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      printHelp();
      System.exit(-1);
    }
    
    new ProjectsInfo(args[0]);
  }

  private static void printHelp() {
    ProjectInfo.class.getName();
    System.out.println("usage: " + ProjectsInfo.class.getSimpleName() + " <EclipseWorkspace>");
  }
  
  private void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(logLevel);
    
    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(Level.SEVERE);
  }
}
