package goedegep.util.projectinfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.logging.MyLoggingFormatter;

public class ProjectInfo {
  private static final Logger         LOGGER = Logger.getLogger(ProjectInfo.class.getName());
  private static final Level          logLevel = Level.INFO;
  private static ProjectType projectType = new ProjectType();

  private Path projectPath;
  private AggregatedProjectInfo aggregatedProjectInfo;
  private String projectName = null;
  private Integer numberOfClasses = 0;
  private Integer numberOfLines = 0;
  private boolean isEmfModel = false;
  
  public ProjectInfo(Path projectPath, AggregatedProjectInfo aggregatedProjectInfo) {
    this.projectPath = projectPath;
    this.aggregatedProjectInfo = aggregatedProjectInfo;
    
    String projectName = projectPath.getFileName().toString();
    setProjectName(projectName);
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public Integer getNumberOfClasses() {
    return numberOfClasses;
  }
  
  public void incrementNumberOfClasses() {
    numberOfClasses++;
  }

  public Integer getNumberOfLines() {
    return numberOfLines;
  }
  
  public void incrementNumberOfLines(int count) {
    numberOfLines += count;
  } 

  public boolean isEmfModel() {
    return isEmfModel;
  }

  public void setEmfModel(boolean isEmfModel) {
    this.isEmfModel = isEmfModel;
  }

  public void handleProject() {
    LOGGER.info("=> project = " + projectName);
    
    setEmfModel(projectType.evaluate(projectPath));
    
    if (aggregatedProjectInfo != null) {
      aggregatedProjectInfo.addProjectInfo(this);
    }
    
    Path startingDir = projectPath;
    String pattern = "*.java";

    Finder finder = new Finder(this, pattern);
    try {
      Files.walkFileTree(startingDir, finder);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void print() {
    System.out.print("Project name: " + getProjectName());
    if (isEmfModel()) {
      System.out.print(" (EMF model project)");
    }
    System.out.println();


    if (isEmfModel()) {
      System.out.println("Number of generated classes: " + getNumberOfClasses());
      System.out.println("Number of generated lines of code: " + getNumberOfLines());
    } else {
      System.out.println("Number of written classes: " + getNumberOfClasses());
      System.out.println("Number of written lines of code: " + getNumberOfLines());
    }
  }

  public static void main(String[] args) {
    logSetup();
    
    if (args.length == 0) {
      printHelp();
      System.exit(-1);
    }
    
    boolean first = true;
    for (String projectFolderName: args) {
      if (first) {
        first = false;
      } else {
        System.out.println();
      }
      Path projectParentFolder = Paths.get(projectFolderName);
      ProjectInfo projectInfo = new ProjectInfo(projectParentFolder, null);
      projectInfo.handleProject();
      projectInfo.print();
    }
  }

  private static void printHelp() {
    ProjectInfo.class.getName();
    System.out.println("usage: " + ProjectInfo.class.getSimpleName() + " <ProjectFolder> ...");
  }

  private static void logSetup() {
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
