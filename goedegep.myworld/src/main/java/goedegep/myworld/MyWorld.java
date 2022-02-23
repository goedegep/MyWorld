package goedegep.myworld;

import java.io.File;
import java.io.FileNotFoundException;
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

import goedegep.app.finan.guifx.FinanMenuWindow;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.EMFResource;
import goedegep.appgen.swing.Customizations;
import goedegep.finan.Finan;
import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.app.guifx.InvoicesAndPropertiesMenuWindow;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.objectstringconverters.FixedPointValueObjectStringConverter;
import goedegep.jfx.eobjecttable.objectstringconverters.PgCurrencyObjectStringConverter;
import goedegep.media.app.MediaRegistry;
import goedegep.media.app.guifx.MediaMenuWindow;
import goedegep.myworld.app.MyWorldAppModule;
import goedegep.myworld.app.MyWorldRegistry;
import goedegep.myworld.app.guifx.MyWorldMenuWindowFx;
import goedegep.pctools.app.guifx.PCToolsMenuWindow;
import goedegep.pctools.app.logic.PCToolsRegistry;
import goedegep.properties.app.PropertiesHandler;
import goedegep.rolodex.app.RolodexRegistry;
import goedegep.rolodex.app.guifx.RolodexMenuWindow;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.unitconverter.app.UnitConverterRegistry;
import goedegep.unitconverter.app.guifx.UnitConverterWindow;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import goedegep.vacations.app.guifx.VacationsWindow;
import goedegep.vacations.app.logic.VacationsRegistry;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * This class starts the MyWorld application.
 * <p>
 */
public class MyWorld extends JfxApplication {
  private static final Logger LOGGER = Logger.getLogger(MyWorld.class.getName());
  
  private static final String         PROGRAM_NAME = "MyWorld";
  private static final String         PROGRAM_DESCRIPTION =
                                             PROGRAM_NAME + " is my world on the computer. It consists of the following modules:" + NEWLINE +
                                             "Finan                   - Financial related stuff" + NEWLINE +
                                             "Invoices and Properties - Information about expenses and properties we own" + NEWLINE +
                                             "Media                   - Music and photos (and later movies)" + NEWLINE +
                                             "Rolodex                 - Names, adressess, phonenumbers en phone memories" + NEWLINE +
                                             "Unit Converter          - To convert distances and times" + NEWLINE +
                                             "Vacations               - Information about vacations we've had" + NEWLINE +
                                             "PCTools                 - Some PC toolts + NEWLINE";

// EMF model files are accessed as File, and so they cannot be in jar files.
private static final String         MY_WORLD_PROPERTY_DESCRIPTORS_FILE = "MyWorldPropertyDescriptors.xmi";

// When running in Eclipse, files are read from the related project folder.
private static final String         FINAN_PROJECT_PATH = "../../../goedegep.finan.app/target/classes";
private static final String         MEDIA_PROJECT_PATH = "../../../goedegep.media.app/target/classes";
private static final String         INVOICES_AND_PROPERTIES_PROJECT_PATH = "../../../goedegep.invandprop.app/target/classes";
private static final String         ROLODEX_PROJECT_PATH = "../../../goedegep.rolodex.app/target/classes";
private static final String         UNIT_CONVERTER_PROJECT_PATH = "../../../goedegep.unitconverter.app/target/classes";
private static final String         PCTOOLS_PROJECT_PATH = "../../../goedegep.pctools/target/classes";
private static final String         VACATIONS_PROJECT_PATH = "../../../goedegep.vacations.app/target/classes";

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
    
    // Define command line arguments.
    Options options = new Options();
    Option applicationOption = Option.builder("a").hasArg().argName("application").
        desc("the application within MyWorld that has to be directly started (optional). Possible values are: " +
            "\"Finan\", \"MediaDb\", \"Invoices and Properties\", \"Rolodex\", \"Unit Converter\", \"PCTools\", \\\"Vacations\\\"").build();
    options.addOption(applicationOption);
    
    boolean optionsOK = true;
    MyWorldAppModule appModule = null;
    List<String> errorTexts = new ArrayList<>();

    // Parse command line arguments.
    CommandLineParser parser = new DefaultParser();
    List<String> argsList = getParameters().getUnnamed();
    String[] args = argsList.toArray(new String[argsList.size()]);
    try {
      CommandLine cmd = parser.parse(options, args);
      if(cmd.hasOption("a")) {
        String applicationName = cmd.getOptionValue("a");
        appModule = MyWorldAppModule.getAppModuleForName(applicationName);
        if (appModule == null) {
          errorTexts.add("Unknown application (value of '-a' option): " + applicationName);
          optionsOK = false;
        }
      }
    } catch (ParseException e) {
      if (e instanceof UnrecognizedOptionException) {
        UnrecognizedOptionException unrecognizedOptionException = (UnrecognizedOptionException) e;
        errorTexts.add("Unknown option: '" + unrecognizedOptionException.getOption() + "'");
      } else if (e instanceof MissingArgumentException) {
        MissingArgumentException missingArgumentException = (MissingArgumentException) e;
        errorTexts.add("Value is missing for option: '" + missingArgumentException.getOption().getOpt() + "'");
      } else if (e instanceof MissingOptionException) {
        MissingOptionException missingOptionException = (MissingOptionException) e;
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

    boolean runningInEclipse = areWeRunningInEclipse();

    // DevelopmentMode
    // In development mode extra items are added to menu's.
    // For now DevelopmentMode is active when 'Running in eclipse'.
    if (runningInEclipse) {
      MyWorldRegistry.developmentMode = true;
      if (modulesToInitialize.contains(MyWorldAppModule.FINAN)) {
        FinanRegistry.developmentMode = true;
      }
      if (modulesToInitialize.contains(MyWorldAppModule.MEDIA)) {
        MediaRegistry.developmentMode = true;
      }
      if (modulesToInitialize.contains(MyWorldAppModule.ROLODEX)) {
        RolodexRegistry.developmentMode = true;
      }
      if (modulesToInitialize.contains(MyWorldAppModule.INVOICES_AND_PROPERTIES)) {
        InvoicesAndPropertiesRegistry.developmentMode = true;
      }
      if (modulesToInitialize.contains(MyWorldAppModule.UNIT_CONVERTER)) {
        UnitConverterRegistry.developmentMode = true;
      }
      if (modulesToInitialize.contains(MyWorldAppModule.PCTOOLS)) {
        PCToolsRegistry.developmentMode = true;
      }
      if (modulesToInitialize.contains(MyWorldAppModule.VACATIONS)) {
        VacationsRegistry.developmentMode = true;
      }
    }

    // FIXME: //AAA HIER VERDER
    createPropertyDescriptorResources(runningInEclipse, modulesToInitialize);

    createCustomizations(runningInEclipse, modulesToInitialize);

    // Temp: create swing customizations. Remove each one when the app is converted to javafx.
    createCustomizationsSwing(runningInEclipse, modulesToInitialize);
    
    // Customize the EStructuralFeatureValueCellFactory class with the default formatters
    EObjectTable.addDefaultStringConverter(PgCurrency.class, new PgCurrencyObjectStringConverter());
    EObjectTable.addDefaultStringConverter(FixedPointValue.class, new FixedPointValueObjectStringConverter());

    createAndShowApplicationWindow(appModule);
    
    LOGGER.fine("<=");
  }
  
  /**
   * Create the PropertyDescriptorsResource for each module to initialize.
   * <p>
   * Note: Remember that the PropertyDescriptorsResource also fills the related registry.
   * 
   * @param modulesToInitialize the set of modules for which the PropertyDescriptorsResource has to be created.
   */
  private void createPropertyDescriptorResources(boolean runningInEclipse, Set<MyWorldAppModule> modulesToInitialize) {
    try {
      LOGGER.fine("MIJN_WERELD_PROPERTY_DESCRIPTORS_FILE=" + MY_WORLD_PROPERTY_DESCRIPTORS_FILE);
      PropertiesHandler.handleProperties(runningInEclipse, null, MY_WORLD_PROPERTY_DESCRIPTORS_FILE);
            
      PropertiesMetaInfo[] propertiesMetaInfos = {
          new PropertiesMetaInfo(MyWorldAppModule.FINAN, FINAN_PROJECT_PATH, MyWorldRegistry.finanPropertyDescriptorFileName),
          new PropertiesMetaInfo(MyWorldAppModule.MEDIA, MEDIA_PROJECT_PATH, MyWorldRegistry.mediaPropertyDescriptorFileName),
          new PropertiesMetaInfo(MyWorldAppModule.ROLODEX, ROLODEX_PROJECT_PATH, MyWorldRegistry.rolodexPropertyDescriptorFileName),
          new PropertiesMetaInfo(MyWorldAppModule.INVOICES_AND_PROPERTIES, INVOICES_AND_PROPERTIES_PROJECT_PATH, MyWorldRegistry.invoicesAndPropertiesPropertyDescriptorFileName),
          new PropertiesMetaInfo(MyWorldAppModule.UNIT_CONVERTER, UNIT_CONVERTER_PROJECT_PATH, MyWorldRegistry.unitConverterPropertyDescriptorFileName),
          new PropertiesMetaInfo(MyWorldAppModule.PCTOOLS, PCTOOLS_PROJECT_PATH, MyWorldRegistry.pctoolsPropertyDescriptorsFileName),
          new PropertiesMetaInfo(MyWorldAppModule.VACATIONS, VACATIONS_PROJECT_PATH, MyWorldRegistry.vacationsPropertyDescriptorsFileName)
      };
      
      for (PropertiesMetaInfo propertiesMetaInfo: propertiesMetaInfos) {
        if (modulesToInitialize.contains(propertiesMetaInfo.myWorldAppModule)) {
          PropertiesHandler.handleProperties(runningInEclipse, propertiesMetaInfo.projectPath, propertiesMetaInfo.propertyDescriptorFileName);
        }
      }
            
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
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, null, MyWorldRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.FINAN)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, FINAN_PROJECT_PATH, FinanRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.MEDIA)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, MEDIA_PROJECT_PATH, MediaRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.ROLODEX)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, ROLODEX_PROJECT_PATH, RolodexRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.INVOICES_AND_PROPERTIES)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, INVOICES_AND_PROPERTIES_PROJECT_PATH, InvoicesAndPropertiesRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.UNIT_CONVERTER)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, UNIT_CONVERTER_PROJECT_PATH, UnitConverterRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.PCTOOLS)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, PCTOOLS_PROJECT_PATH, PCToolsRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.VACATIONS)) {
      CustomizationsFx.addCustomizations(new File(createResourcePath(runningInEclipse, VACATIONS_PROJECT_PATH, VacationsRegistry.configurationFile)));
    }
  }


  // The top level application needs the customizations of all applications, because it shows their images in the
  // application start page.
  private void createCustomizationsSwing(boolean runningInEclipse, Set<MyWorldAppModule> modulesToInitialize) {
    // TODO Files shall be in the system directory and not in the user directory. Only if, later, user config files are introduced, these shall be in the user directory.
    // Files are used already from system directory. Remove from Google Drive is Finan installation is also updated.
    
    // Add a Customization to Customizations for each application module, where:
    // - the name is taken from AppModules
    // - a File for the ConfigurationResource is taken for the related Registry (typically configurationFile)
    // - a new AppResources for the module
    
    // Currently the configurations are not editable, so they are in the installation directory.
    // If I make them editable there will be a copy of the configuration files in the dataDirectory.
    if (modulesToInitialize.contains(MyWorldAppModule.FINAN)) {
      Customizations.addCustomizations(new File(createResourcePath(runningInEclipse, FINAN_PROJECT_PATH, FinanRegistry.configurationFile)));
    }
    if (modulesToInitialize.contains(MyWorldAppModule.INVOICES_AND_PROPERTIES)) {
      Customizations.addCustomizations(new File(createResourcePath(runningInEclipse, INVOICES_AND_PROPERTIES_PROJECT_PATH, InvoicesAndPropertiesRegistry.configurationFile)));
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
  private String createResourcePath(boolean runningInEclipse, String projectPath, String fileName) {
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
  private void createAndShowApplicationWindow(MyWorldAppModule appModule) {
    Stage stage = null;
    if ((appModule == null)  ||  (appModule.equals(MyWorldAppModule.MY_WORLD))) {
      getRolodexResource();
      stage = new MyWorldMenuWindowFx(CustomizationsFx.getCustomization(MyWorldAppModule.MY_WORLD.name()));
    } else {
      switch (appModule) {
      case FINAN:
        Finan finan = new Finan(getRolodexResource().getEObject());
        stage = new FinanMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.FINAN.name()), finan);
        break;
        
      case MEDIA:
        stage = new MediaMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.MEDIA.name()));
        break;
        
      case MY_WORLD:
        // No action, as this is handled in the 'if'. This case is only here to keep cases compleet.
        break;
        
      case INVOICES_AND_PROPERTIES:
        stage = new InvoicesAndPropertiesMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.INVOICES_AND_PROPERTIES.name()));
        break;
        
      case ROLODEX:
        getRolodexResource();
        stage = new RolodexMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.ROLODEX.name()));
        break;
        
      case UNIT_CONVERTER:
        stage = new UnitConverterWindow(CustomizationsFx.getCustomization(appModule.name()));
        break;
        
      case PCTOOLS:
        stage = new PCToolsMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.PCTOOLS.name()));
        break;
        
      case VACATIONS:
        stage = new VacationsWindow(CustomizationsFx.getCustomization(MyWorldAppModule.VACATIONS.name()));
        break;
        
      default:
        throw new RuntimeException("Starting module: " + appModule.getModuleName() + " stand alone isn't implemented yet.");
      }
    }
    stage.centerOnScreen();
    stage.show();
  }
  
  /**
   * Get the Rolodex resource.
   * <p>
   * If the RolodexRegistry.rolodexResource is null, a new RolodexResource is created.
   * 
   * @return the existing or newly created RolodexRegistry.rolodexResource
   */
  private EMFResource<Rolodex> getRolodexResource() {
    if (RolodexRegistry.rolodexResource == null) {
      try {
        RolodexRegistry.rolodexResource = new EMFResource<>(
            RolodexPackage.eINSTANCE,
            () -> RolodexFactory.eINSTANCE.createRolodex());
//        File rolodexFile = new File(RolodexRegistry.dataDirectory, RolodexRegistry.rolodexFile);
        File rolodexFile = new File(RolodexRegistry.rolodexFile);
        RolodexRegistry.rolodexResource.load(rolodexFile.getAbsolutePath());
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }

    return RolodexRegistry.rolodexResource;
  }
  

  public static void main(String[] args) {
    MyWorld.launch(args);
  }
}

class PropertiesMetaInfo {
  public MyWorldAppModule myWorldAppModule;
  public String projectPath;
  public String propertyDescriptorFileName;
  
  public PropertiesMetaInfo(MyWorldAppModule myWorldAppModule, String projectPath, String propertyDescriptorFileName) {
    this.myWorldAppModule = myWorldAppModule;
    this.projectPath = projectPath;
    this.propertyDescriptorFileName = propertyDescriptorFileName;
  }
}
