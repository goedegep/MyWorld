package goedegep.myworld;

import java.awt.SplashScreen;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.stringconverterandchecker.CurrencyStringConverterAndChecker;
import goedegep.jfx.stringconverterandchecker.FixedPointValueStringConverterAndChecker;
import goedegep.myworld.app.MyWorldAppModule;
import goedegep.myworld.app.MyWorldRegistry;
import goedegep.myworld.app.guifx.MyWorldMenuWindowFx;
import goedegep.properties.app.PropertiesHandler;
import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.util.RunningInEclipse;
import goedegep.util.file.FileUtils;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * This class starts the MyWorld application.
 * <p>
 */
public class MyWorld extends JfxApplication implements PropertyFileURLProvider {
  private static final Logger LOGGER = Logger.getLogger(MyWorld.class.getName());
  
  private static final String         PROGRAM_NAME = "MyWorld";
  private static final int MIN_JAVA_FEATURE_NUMBER = 22;
  private static final String         PROGRAM_DESCRIPTION =
                                             PROGRAM_NAME + " is my world on the computer. It consists of the following modules: <nothing anymore" + NEWLINE;

// EMF model files are accessed as File, and so they cannot be in jar files.
private static final String MY_WORLD_PROPERTY_DESCRIPTORS_FILE = "MyWorldPropertyDescriptors.xmi";
private static final String MY_WORLD_CONFIGURATION_FILE = "MyWorldConfiguration.xmi";

  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public MyWorld() {
    logSetup(Level.SEVERE, PROGRAM_NAME + "Logging");
    LOGGER.info("MyWorld constructor <=>");
  }
  
  /**
   * This method is called during the JavaFx launch sequence, after the constructor is called.<br/>
   * It doesn't do anything (so it could actually be removed, but I keep it in to experiment with JavaFx).
   */
  @Override
  public void init() {
    LOGGER.info("<=>");
  }

  /**
   * This method is called during the JavaFx launch sequence, after the init method.<br/>
   * This method:
   * <ul>
   * <li>
   * Parses the command line arguments.
   * </li>
   * </ul>
   */
  @Override
  public void start(Stage primaryStage) {
    LOGGER.info("=>");
    
//    HttpUtil.setupFiddlerMonitoring();
    Runtime.Version version = Runtime.version();
    if (version.feature() < MIN_JAVA_FEATURE_NUMBER) {
      DefaultCustomizationFx.getInstance().getComponentFactoryFx()
        .createErrorDialog("Java runtime version too old", "You need at least version " + MIN_JAVA_FEATURE_NUMBER + ", but you have " +version.feature())
        .showAndWait();

      Platform.exit();
    }
    
    // Define command line arguments.
    Options options = new Options();
    Option applicationOption = Option.builder("a").hasArg().argName("application").
        desc("the application within MyWorld that has to be directly started (optional). Possible values are: <none>").build();
    options.addOption(applicationOption);
    
    boolean optionsOK = true;
    String fileToOpen = null;
    MyWorldAppModule appModule = null;
    List<String> errorTexts = new ArrayList<>();

    // Parse/handle command line arguments.
    CommandLineParser parser = new DefaultParser();
    List<String> argsList = getParameters().getUnnamed();
    String[] args = argsList.toArray(new String[argsList.size()]);
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
      
      if(cmd.hasOption("a")) {
        String applicationName = cmd.getOptionValue("a");
        appModule = MyWorldAppModule.getAppModuleForName(applicationName);
        if (appModule == null) {
          errorTexts.add("Unknown application (value of '-a' option): " + applicationName);
          optionsOK = false;
        }
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
    
    if (fileToOpen != null  &&  appModule != null) {
      errorTexts.add("You cannot specify an application and also provide a filename.");
      optionsOK = false;
    }

    if (!optionsOK) {
      LOGGER.severe("!optionsOK");
      showUsageInfoDialogAndExit(PROGRAM_NAME, options, PROGRAM_DESCRIPTION, errorTexts, args);
    }

    // Every module has its own registry.
    // Values in a registry are only set if the MyWorld Menu Window is used (so all modules are needed), or if the specific module is needed, 
    // i.e. either the module is started, or the module which is started depends on it.
    // The MyWorld Registry always has to be initialized.
    Set<MyWorldAppModule> modulesToInitialize = new HashSet<>();
    if (appModule != null) {
      modulesToInitialize.add(appModule);
      modulesToInitialize.addAll(appModule.getDependsOnModules());
    } else {
      modulesToInitialize.add(MyWorldAppModule.MY_WORLD);
      modulesToInitialize.addAll(MyWorldAppModule.MY_WORLD.getDependsOnModules());
    }

    getComputerName();
    
    // DevelopmentMode
    // In development mode extra items are added to menu's.
    // For now DevelopmentMode is active when 'Running in eclipse'.
    if (RunningInEclipse.runningInEclipse()) {
      MyWorldRegistry.developmentMode = true;
      // ToDo set development mode in each application.
    }

    // FIXME: //AAA HIER VERDER
    createPropertyDescriptorResources(RunningInEclipse.runningInEclipse(), modulesToInitialize);

    createCustomizations(RunningInEclipse.runningInEclipse(), modulesToInitialize);

    // Temp: create swing customizations. Remove each one when the app is converted to javafx.
//    createCustomizationsSwing(runningInEclipse, modulesToInitialize);
    
    // Customize the EStructuralFeatureValueCellFactory class with the default formatters
    EObjectTable.addDefaultStringConverter(PgCurrency.class, CurrencyStringConverterAndChecker.getDefaultFormatInstance());
    EObjectTable.addDefaultStringConverter(FixedPointValue.class, FixedPointValueStringConverterAndChecker.getDefaultFormatFixedPointValueStringConverterAndChecker());

    createAndShowApplicationWindow(appModule, fileToOpen);
    
    SplashScreen splashScreen = SplashScreen.getSplashScreen();
    if (splashScreen != null) {
      splashScreen.close();
    }
    
    LOGGER.fine("<=");
  }
  
  private String getComputerName() {
    String computerName = System.getenv("COMPUTERNAME");
    LOGGER.severe("computer name: " + computerName);
  
    return computerName;
  }
  
  /**
   * Handle settings by reading the PropertyDescriptors and Property files.
   * <p>
   * The {@code PropertyDescriptors} files are part of the project.
   * The user {@code Property} files are not part of the project.
   * Note: Remember that the PropertyDescriptorsResource also fills the related registry.
   * 
   * @param modulesToInitialize the set of modules for which the PropertyDescriptorsResource has to be created.
   */
  private void createPropertyDescriptorResources(boolean runningInEclipse, Set<MyWorldAppModule> modulesToInitialize) {
    try {
      LOGGER.fine("MIJN_WERELD_PROPERTY_DESCRIPTORS_FILE=" + MY_WORLD_PROPERTY_DESCRIPTORS_FILE);
      java.net.URL url = getPropertyFileURL();
      PropertiesHandler.handleProperties(url, null);
            
            
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Foutmelding");
      alert.setHeaderText(e.getMessage());
      alert.setContentText("Installeer " + this.getClass().getSimpleName() + " opnieuw.");

      alert.showAndWait();
      e.printStackTrace();
      System.exit(0);
    }
    
  }

  /**
   * Create the required Customizations.
   * 
   */
  private void createCustomizations(boolean runningInEclipse, Set<MyWorldAppModule> modulesToInitialize) {
    // TODO Files shall be in the system directory and not in the user directory. Only if, later, user config files are introduced, these shall be in the user directory.
    // Files are used already from system directory. Remove from Google Drive if Finan installation is also updated.
    
    // Add a Customization to Customizations for each application module, where:
    // - the name is taken from AppModules
    // - a File for the ConfigurationResource is taken for the related Registry (typically configurationFile)
    // - a new AppResources for the module
    
    // Currently the configurations are not editable, so they are in the installation directory.
    // If I make them editable there will be a copy of the configuration files in the dataDirectory.
    if (modulesToInitialize.contains(MyWorldAppModule.MY_WORLD)) {
//      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, null, MyWorldRegistry.configurationFile)));
      CustomizationsFx.addCustomizations(this.getCustomizationFileURL());
    }
  }
  
  /**
   * Create the path name for a resource file.
   * <p>
   * If the program is running in Eclipse, the resource path is the <code>fileName</code> in the <code>projectPath</code> directory.
   * Else it is simply <code>fileName</code> (as it is in the current directory).
   * 
   * @param runningInEclipse indicates whether the program is running in Eclipse or not.
   * @param projectPath path to the project which contains the resource.
   * @param fileName filename of the resource.
   * @return a path to the resource
   */
  private static String createResourcePath(boolean runningInEclipse, String projectPath, String fileName) {
    if (runningInEclipse  &&  projectPath != null) {
      File file = new File(projectPath, fileName);
      LOGGER.info("Resource path = " + file.getAbsolutePath());
      return file.getAbsolutePath();
    } else {
      LOGGER.info("Resource path = " + fileName);
      return fileName;
    }
  }
  
  /**
   * Create and show the main application window.
   * <p>
   * Which application window is started is determined by the <code>appModule</code> field.
   */
  private void createAndShowApplicationWindow(MyWorldAppModule appModule, String fileToOpen) {
    Stage stage = null;
    if ((appModule == null)  ||  (appModule.equals(MyWorldAppModule.MY_WORLD))) {
      stage = new MyWorldMenuWindowFx(CustomizationsFx.getCustomization(MyWorldAppModule.MY_WORLD.name()));
    } else {
      switch (appModule) {
      case MY_WORLD:
        // No action, as this is handled in the 'if'. This case is only here to keep cases compleet.
        break;
        
      default:
        throw new RuntimeException("Starting module: " + appModule.getModuleName() + " stand alone isn't implemented yet.");
      }
    }
    
    if (stage != null) {
      stage.centerOnScreen();
      stage.show();      
    }
  }  

  public static void main(String[] args) {
    MyWorld.launch(args);
  }

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(MY_WORLD_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(MY_WORLD_CONFIGURATION_FILE);
    
    return url;
  }
}

class PropertiesMetaInfo {
  public MyWorldAppModule myWorldAppModule;
  public String projectPath;
  public String propertyDescriptorFileName;
  public PropertyFileURLProvider propertyFileURLProvider;
  
  public PropertiesMetaInfo(MyWorldAppModule myWorldAppModule, String projectPath, String propertyDescriptorFileName, PropertyFileURLProvider propertyFileURLProvider) {
    this.myWorldAppModule = myWorldAppModule;
    this.projectPath = projectPath;
    this.propertyDescriptorFileName = propertyDescriptorFileName;
    this.propertyFileURLProvider = propertyFileURLProvider;
  }
}
