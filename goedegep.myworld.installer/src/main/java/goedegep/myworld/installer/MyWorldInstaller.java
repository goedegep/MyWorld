package goedegep.myworld.installer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.jfx.JfxApplication;
import goedegep.jfx.controls.FolderSelecter;
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.dir.DirectoryUtils;
import goedegep.util.emf.EMFResource;
import goedegep.util.mslinks.ShellLink;
import goedegep.util.mslinks.ShellLinkException;
import goedegep.util.mslinks.ShellLinkHeader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class provides an installer for the 'MyWorld' application.
 * <p>
 * As the required .jar files are taken from my maven repository, it is required to run 'maven install' before running the installer.<br/>
 * The installer patches the version information (by adding the current date/time) in the file 'MyWorldPropertyDescriptors.xmi'. This way it can
 * always be checked when the application was installed.<br/>
 * The installer creates and install 2 shortcuts:
 * <ul>
 * <li>
 * MyWorld.lnk - which starts the MyWorld main window.
 * </li>
 * <li>
 * Finan.lnk - which directly start the Finan component of the application.
 * </li>
 * </ul>
 * <p>
 * There are 2 ways to perform the installation:
 * <ul>
 * <li>Windows 'Program Files' directory<br/>
 * To install under this directory you need admistrator rights, which I currently cannot get working from within this program.
 * Therefore a .bat installation script is generated, which you then have to execute as administrator.<br/>
 * This method is used if the specified Installation Directory starts with "C:\Program Files" or "C:\Program Files (x86)".
 * </li>
 * <li>'Applic' directory<br/>
 * The 'Applic' directory is synchronized via my NAS. Therefore when the program is installed under this directory it can be used on any computer.
 * (currently this doesn't work as the data is not available on all computers).<br/>
 * As this is a normal user directory, files can just be copied to this directory.<br/>
 * This method is used if the specified Installation Directory doesn't start with "C:\Program Files" or "C:\Program Files (x86)".
 * </li>
 * </ul>
 * The installer will automatically run if it is started with the following arguments:
 * <ul>
 * <li>installation directory - the directory where the application will be installed.</li>
 * <li>user data directory - the directory with the user data.</li>
 * </ul>
 */
public class MyWorldInstaller extends JfxApplication {
  private static final Logger LOGGER = Logger.getLogger(MyWorldInstaller.class.getName());

  private static final String JAVAW_EXECUTABLE = "java.exe";  // TODO change back to javaw.exe for real installations.
  private static final String JAVA_MAIN_FOLDER = "C:\\Program Files\\Java";
  private static final String MAVEN_REPOSITORY_HOME = "C:\\Users\\Peter\\.m2\\repository";
  private static final String MY_WORLD_SHORTCUT_PATH = "target\\classes\\MyWorld.lnk";
  private static final String FINAN_SHORTCUT_PATH = "target\\classes\\Finan.lnk";
  private static final String MEDIA_SHORTCUT_PATH = "target\\classes\\Media.lnk";
  private static final String INVOICES_AND_PROPERTIES_SHORTCUT_PATH = "target\\classes\\InvoicesAndProperties.lnk";
  private static final String ROLODEX_SHORTCUT_PATH = "target\\classes\\Rolodex.lnk";
  private static final String UNIT_CONVERTER_SHORTCUT_PATH = "target\\classes\\UnitConverter.lnk";
  private static final String PC_TOOLS_SHORTCUT_PATH = "target\\classes\\PCTools.lnk";
  private static final String VACATIONS_SHORTCUT_PATH = "target\\classes\\Vacations.lnk";
  
  // In this file the version information is patched.
  private static final String MY_WORLD_PROPERTY_DESCRIPTORS_FILE = "..\\goedegep.myworld\\src\\main\\resources\\MyWorldPropertyDescriptors.xmi";
  
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
  
  private FolderSelecter installationFolder;
  private BooleanProperty installationFolderValidProperty;
  private FolderSelecter userDataFolder;
  private BooleanProperty userDataFolderValidProperty;
  private TextArea outputTextArea;
  private StringBuilder outputTextBuffer;
  private Button runButton;
  private Label statusLabel;
  private boolean installationRunning = false;
  

  // Resources, scripts and shortcuts to install.
  private static String[] resourceList = {
      MY_WORLD_SHORTCUT_PATH,
      FINAN_SHORTCUT_PATH,
      MEDIA_SHORTCUT_PATH,
      INVOICES_AND_PROPERTIES_SHORTCUT_PATH,
      ROLODEX_SHORTCUT_PATH,
      UNIT_CONVERTER_SHORTCUT_PATH,
      PC_TOOLS_SHORTCUT_PATH,
      VACATIONS_SHORTCUT_PATH,
      "..\\goedegep.myworld\\src\\main\\resources\\goedegep\\myworld\\app\\guifx\\MyWorld_32x32.ico",
      "..\\goedegep.myworld\\src\\main\\scripts\\MyWorld.bat",
      "..\\goedegep.finan.app\\src\\nsis\\FinanLogo.ico",
      "..\\goedegep.media.app\\src\\main\\resources\\goedegep\\media\\app\\Media.ico",
      "..\\goedegep.invandprop.app\\src\\main\\resources\\goedegep\\invandprop\\app\\guifx\\InvoicesAndProperties.ico",
      "..\\goedegep.rolodex.app\\src\\main\\resources\\goedegep\\rolodex\\app\\guifx\\Rolodex.ico",
      "..\\goedegep.unitconverter.app\\src\\main\\resources\\goedegep\\unitconverter\\app\\guifx\\UnitConverter.ico",
      "..\\goedegep.pctools\\src\\main\\resources\\goedegep\\pctools\\app\\guifx\\PCTools.ico",
      "..\\goedegep.vacations.app\\src\\main\\resources\\goedegep\\vacations\\app\\guifx\\Vacations.ico",
      MY_WORLD_PROPERTY_DESCRIPTORS_FILE,
      "..\\goedegep.myworld\\src\\main\\resources\\MyWorldConfiguration.xmi",
      "..\\goedegep.finan.app\\src\\main\\resources\\FinanPropertyDescriptors.xmi",
      "..\\goedegep.finan.app\\src\\main\\resources\\FinanConfiguration.xmi",
      "..\\goedegep.media.app\\src\\main\\resources\\MediaPropertyDescriptors.xmi",
      "..\\goedegep.media.app\\src\\main\\resources\\MediaConfiguration.xmi",
      "..\\goedegep.invandprop.app\\src\\main\\resources\\InvoicesAndPropertiesPropertyDescriptors.xmi",
      "..\\goedegep.invandprop.app\\src\\main\\resources\\InvoicesAndPropertiesConfiguration.xmi",
      "..\\goedegep.pctools\\src\\main\\resources\\PCToolsPropertyDescriptors.xmi",
      "..\\goedegep.pctools\\src\\main\\resources\\PCToolsConfiguration.xmi",
      "..\\goedegep.rolodex.app\\src\\main\\resources\\RolodexPropertyDescriptors.xmi",
      "..\\goedegep.rolodex.app\\src\\main\\resources\\RolodexConfiguration.xmi",
      "..\\goedegep.unitconverter.app\\src\\main\\resources\\UnitConverterPropertyDescriptors.xmi",
      "..\\goedegep.unitconverter.app\\src\\main\\resources\\UnitConverterConfiguration.xmi",
      "..\\goedegep.vacations.app\\src\\main\\resources\\VacationsPropertyDescriptors.xmi",
      "..\\goedegep.vacations.app\\src\\main\\resources\\VacationsUserPreferences.xmi",
      "..\\goedegep.vacations.app\\src\\main\\resources\\VacationsConfiguration.xmi"
  };

  // Jar files to install.
  private static String[] jarFiles = {
      "co\\kaleidok\\javaFlacEncoder\\0.3.2-SNAPSHOT\\javaFlacEncoder-0.3.2-SNAPSHOT.jar",
      "com\\atlassian\\commonmark\\commonmark\\0.12.1\\commonmark-0.12.1.jar",
      "com\\drewnoakes\\metadata-extractor\\2.11.0\\metadata-extractor-2.11.0.jar",
      "com\\ealva\\ealvatag\\0.4.3\\ealvatag-0.4.3.jar",
      "com\\google\\code\\findbugs\\jsr305\\3.0.2\\jsr305-3.0.2.jar",
      "com\\google\\code\\gson\\gson\\2.8.6\\gson-2.8.6.jar",
      "com\\google\\common\\geometry\\com.google.common.geometry\\1.0-SNAPSHOT\\com.google.common.geometry-1.0-SNAPSHOT.jar",
      "com\\google\\guava\\guava\\28.1-jre\\guava-28.1-jre.jar",
      "com\\google\\jimfs\\jimfs\\1.1\\jimfs-1.1.jar",
      "com\\itextpdf\\itextpdf\\5.5.3\\itextpdf-5.5.3.jar",
      "com\\itextpdf\\tool\\xmlworker\\5.5.3\\xmlworker-5.5.3.jar",
      "com\\mpatric\\mp3agic\\0.9.1\\mp3agic-0.9.1.jar",
      "com\\openhtmltopdf\\openhtmltopdf-core\\1.0.0\\openhtmltopdf-core-1.0.0.jar",
      "com\\openhtmltopdf\\openhtmltopdf-pdfbox\\1.0.0\\openhtmltopdf-pdfbox-1.0.0.jar",
      "com\\sun\\xml\\bind\\jaxb-impl\\2.2\\jaxb-impl-2.2.jar",
      "commons-cli\\commons-cli\\1.4\\commons-cli-1.4.jar",
      "commons-io\\commons-io\\2.6\\commons-io-2.6.jar",
      "commons-logging\\commons-logging\\1.2\\commons-logging-1.2.jar",
      "de\\micromata\\jak\\JavaAPIforKml\\2.2.1-SNAPSHOT\\JavaAPIforKml-2.2.1-SNAPSHOT.jar",
      "gluon-oss-maps\\gluon-oss-maps\\1.0-SNAPSHOT\\gluon-oss-maps-1.0-SNAPSHOT.jar",
      "goedegep\\appgen\\goedegep-appgen\\1.0-SNAPSHOT\\goedegep-appgen-1.0-SNAPSHOT.jar",
      "goedegep\\configuration\\goedegep-configuration-model\\1.0-SNAPSHOT\\goedegep-configuration-model-1.0-SNAPSHOT.jar",
      "goedegep\\finan\\goedegep-finan-app\\1.0-SNAPSHOT\\goedegep-finan-app-1.0-SNAPSHOT.jar",
      "goedegep\\finan\\goedegep-finan-investmentinsurance-model\\1.0-SNAPSHOT\\goedegep-finan-investmentinsurance-model-1.0-SNAPSHOT.jar",
      "goedegep\\finan\\goedegep-finan-jobappointment-model\\1.0-SNAPSHOT\\goedegep-finan-jobappointment-model-1.0-SNAPSHOT.jar",
      "goedegep\\finan\\goedegep-finan-lynx2finan-model\\1.0-SNAPSHOT\\goedegep-finan-lynx2finan-model-1.0-SNAPSHOT.jar",
      "goedegep\\finan\\goedegep-finan-mortgage-model\\1.0-SNAPSHOT\\goedegep-finan-mortgage-model-1.0-SNAPSHOT.jar",
      "goedegep\\geo\\goedegep-geo\\1.0-SNAPSHOT\\goedegep-geo-1.0-SNAPSHOT.jar",
      "goedegep\\gpx\\goedegep-gpx\\1.0-SNAPSHOT\\goedegep-gpx-1.0-SNAPSHOT.jar",
      "goedegep\\gpx\\goedegep-gpx-app\\1.0-SNAPSHOT\\goedegep-gpx-app-1.0-SNAPSHOT.jar",
      "goedegep\\gpx\\goedegep-gpx-model\\1.0-SNAPSHOT\\goedegep-gpx-model-1.0-SNAPSHOT.jar",
      "goedegep\\invandprop\\goedegep-invandprop-app\\1.0-SNAPSHOT\\goedegep-invandprop-app-1.0-SNAPSHOT.jar",
      "goedegep\\invandprop\\goedegep-invandprop-model\\1.0-SNAPSHOT\\goedegep-invandprop-model-1.0-SNAPSHOT.jar",
      "goedegep\\jfx\\goedegep-jfx\\1.0-SNAPSHOT\\goedegep-jfx-1.0-SNAPSHOT.jar",
      "goedegep\\media\\goedegep-media-app\\1.0-SNAPSHOT\\goedegep-media-app-1.0-SNAPSHOT.jar",
      "goedegep\\media\\goedegep-media-app-guifx\\1.0-SNAPSHOT\\goedegep-media-app-guifx-1.0-SNAPSHOT.jar",
      "goedegep\\media\\goedegep-media-mediadb-app\\1.0-SNAPSHOT\\goedegep-media-mediadb-app-1.0-SNAPSHOT.jar",
      "goedegep\\media\\goedegep-media-mediadb-model\\1.0-SNAPSHOT\\goedegep-media-mediadb-model-1.0-SNAPSHOT.jar",
      "goedegep\\media\\goedegep-media-photoshow-app\\1.0-SNAPSHOT\\goedegep-media-photoshow-app-1.0-SNAPSHOT.jar",
      "goedegep\\media\\goedegep-media-photoshow-model\\1.0-SNAPSHOT\\goedegep-media-photoshow-model-1.0-SNAPSHOT.jar",
      "goedegep\\myworld\\goedegep-myworld\\1.0-SNAPSHOT\\goedegep-myworld-1.0-SNAPSHOT.jar",
      "goedegep\\ov2\\goedegep-ov2\\1.0-SNAPSHOT\\goedegep-ov2-1.0-SNAPSHOT.jar",
      "goedegep\\pctools\\goedegep-pctools\\1.0-SNAPSHOT\\goedegep-pctools-1.0-SNAPSHOT.jar",
      "goedegep\\pctools\\goedegep-pctools-model\\1.0-SNAPSHOT\\goedegep-pctools-model-1.0-SNAPSHOT.jar",
      "goedegep\\poi\\goedegep-poi-app\\1.0-SNAPSHOT\\goedegep-poi-app-1.0-SNAPSHOT.jar",
      "goedegep\\poi\\goedegep-poi-model\\1.0-SNAPSHOT\\goedegep-poi-model-1.0-SNAPSHOT.jar",
      "goedegep\\properties\\goedegep-properties-app\\1.0-SNAPSHOT\\goedegep-properties-app-1.0-SNAPSHOT.jar",
      "goedegep\\properties\\goedegep-properties-model\\1.0-SNAPSHOT\\goedegep-properties-model-1.0-SNAPSHOT.jar",
      "goedegep\\resources\\goedegep-resources\\1.0-SNAPSHOT\\goedegep-resources-1.0-SNAPSHOT.jar",
      "goedegep\\rolodex\\goedegep-rolodex-app\\1.0-SNAPSHOT\\goedegep-rolodex-app-1.0-SNAPSHOT.jar",
      "goedegep\\rolodex\\goedegep-rolodex-model\\1.0-SNAPSHOT\\goedegep-rolodex-model-1.0-SNAPSHOT.jar",
      "goedegep\\types\\goedegep-types-model\\1.0-SNAPSHOT\\goedegep-types-model-1.0-SNAPSHOT.jar",
      "goedegep\\unitconverter\\goedegep-unitconverter-app\\1.0-SNAPSHOT\\goedegep-unitconverter-app-1.0-SNAPSHOT.jar",
      "goedegep\\util\\goedegep-util\\1.0-SNAPSHOT\\goedegep-util-1.0-SNAPSHOT.jar",
      "goedegep\\vacations\\goedegep-vacations-app\\1.0-SNAPSHOT\\goedegep-vacations-app-1.0-SNAPSHOT.jar",
      "goedegep\\vacations\\goedegep-vacations-model\\1.0-SNAPSHOT\\goedegep-vacations-model-1.0-SNAPSHOT.jar",
      "goedegep\\vacations\\goedegep-vacations-checklist-model\\1.0-SNAPSHOT\\goedegep-vacations-checklist-model-1.0-SNAPSHOT.jar",
      "java3d\\j3d-core\\1.7.0\\j3d-core-1.7.0.jar",
      "java3d\\j3d-utils\\1.7.0\\j3d-utils-1.7.0.jar",
      "java3d\\j3d-vecmath\\1.7.0\\j3d-vecmath-1.7.0.jar",
      "javax\\xml\\bind\\jaxb-api\\2.2\\jaxb-api-2.2.jar",
      "jfree\\jcommon\\1.0.15\\jcommon-1.0.15.jar",
      "jfree\\jfreechart\\1.0.12\\jfreechart-1.0.12.jar",
      "junit\\junit\\4.12\\junit-4.12.jar",
      "log4j\\log4j\\1.2.17\\log4j-1.2.17.jar",
      "me\\atlis\\atlis-location-base\\1.0.0\\atlis-location-base-1.0.0.jar",
      "me\\atlis\\nominatim-api\\1.0.1-SNAPSHOT\\nominatim-api-1.0.1-SNAPSHOT.jar",
      "net\\java\\dev\\jna\\jna\\4.2.0\\jna-4.2.0.jar",
      "net\\java\\dev\\jna\\jna-platform\\4.2.0\\jna-platform-4.2.0.jar",
      "net\\sf\\ofx4j\\ofx4j\\1.6-RC1\\ofx4j-1.6-RC1.jar",
      "org\\apache\\commons\\commons-exec\\1.1\\commons-exec-1.1.jar",
      "org\\apache\\commons\\commons-imaging\\1.0-goedegep\\commons-imaging-1.0-goedegep.jar",
      "org\\apache\\commons\\commons-lang3\\3.9\\commons-lang3-3.9.jar",
      "org\\apache\\commons\\commons-text\\1.3\\commons-text-1.3.jar",
      "org\\apache\\pdfbox\\pdfbox\\2.0.16\\pdfbox-2.0.16.jar",
      "org\\apiguardian\\apiguardian-api\\1.1.0\\apiguardian-api-1.1.0.jar",
      "org\\atp-fivt\\ljv\\1.02\\ljv-1.02.jar",
      "org\\controlsfx\\controlsfx\\11.0.2\\controlsfx-11.0.2.jar",
      "org\\eclipse\\emf\\org.eclipse.emf.ecore\\2.10.0-v20140514-1158\\org.eclipse.emf.ecore-2.10.0-v20140514-1158.jar",
      "org\\eclipse\\emf\\org.eclipse.emf.ecore.xmi\\2.10.0-v20140514-1158\\org.eclipse.emf.ecore.xmi-2.10.0-v20140514-1158.jar",
      "org\\eclipse\\emf\\org.eclipse.emf.common\\2.10.0-v20140514-1158\\org.eclipse.emf.common-2.10.0-v20140514-1158.jar",
      "org\\hamcrest\\hamcrest\\2.2\\hamcrest-2.2.jar",
      "org\\hamcrest\\hamcrest-core\\2.2\\hamcrest-core-2.2.jar",
      "org\\junit\\jupiter\\junit-jupiter-api\\5.6.2\\junit-jupiter-api-5.6.2.jar",
      "org\\junit\\jupiter\\junit-jupiter-params\\5.6.2\\junit-jupiter-params-5.6.2.jar",
      "org\\junit\\platform\\junit-platform-commons\\1.6.2\\junit-platform-commons-1.6.2.jar",
      "org\\locationtech\\spatial4j\\spatial4j\\0.7\\spatial4j-0.7.jar",
      "org\\openjfx\\javafx-base\\11\\javafx-base-11-win.jar",
      "org\\openjfx\\javafx-controls\\11\\javafx-controls-11-win.jar",
      "org\\openjfx\\javafx-media\\11\\javafx-media-11-win.jar",
      "org\\openjfx\\javafx-graphics\\11\\javafx-graphics-11-win.jar",
      "org\\openjfx\\javafx-swing\\11\\javafx-swing-11-win.jar",
      "org\\openjfx\\javafx-web\\11\\javafx-web-11-win.jar",
      "org\\opentest4j\\opentest4j\\1.2.0\\opentest4j-1.2.0.jar",
      "org\\springframework\\spring\\2.0.2\\spring-2.0.2.jar",
      "se\\trixon\\almond\\almond-util\\1.0-SNAPSHOT\\almond-util-1.0-SNAPSHOT.jar",
      "tim\\prune\\tim.prune.gpsprune\\1.0-SNAPSHOT\\tim.prune.gpsprune-1.0-SNAPSHOT.jar"
  };

  private final static Level LOG_LEVEL = Level.SEVERE;
  
  
  public MyWorldInstaller() {
    logSetup(LOG_LEVEL, null);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("MyWorld Installer");

    primaryStage.setScene(createGUI());
    primaryStage.show();
    
    List<String> argsList = getParameters().getUnnamed();
    
    // If a first argument is present, set this as install directory.
    // However, to avoid strange folders being created, the parent folder of the install folder shall exist.
    if (argsList.size() >= 1) {
      String installFolderName = argsList.get(0);
      if (parentFolderExists(installFolderName)) {
        try {
          makeSureFolderExists(installFolderName);
        } catch (IOException e) {
          statusLabel.setText("Specified installFolder '" + installFolderName + "' can't be created. System message: " + e.getMessage());
        }
      }
      installationFolder.getFolderPathTextField().setText(installFolderName);
    }
    
    // If a second argument is present, set it as User Data directory
    if (argsList.size() >= 2) {
      String userDataFolderName = argsList.get(1);
      if (parentFolderExists(userDataFolderName)) {
        try {
          makeSureFolderExists(userDataFolderName);
        } catch (IOException e) {
          statusLabel.setText("Specified User Data folder '" + userDataFolderName + "' can't be created. System message: " + e.getMessage());
        }
      }
      userDataFolder.getFolderPathTextField().setText(userDataFolderName);
    }
    
//    if (!runButton.isDisabled()) {
//      installMyWorld();
//    }
  }
  
  /**
   * Check whether the parent folder of the specified folder exists.
   * 
   * @param folder The path to be checked
   * @return true if the parent of <code>folder</code> exists, false otherwise.
   */
  private boolean parentFolderExists(String folder) {
    Path folderPath = Paths.get(folder);
    Path parentPath = folderPath.getParent();
    return Files.exists(parentPath);
  }
  
  /**
   * Make sure that the specified folder exists.
   * <p>
   * It is assumed that the parent of the specified folder exists.<br/>
   * If the specified folder doesn't exist yet, it will be created.
   * 
   * @param folder the folder to be created, if it doesn't exist yet.
   * @throws IOException If the folder cannot be created.
   */
  private void makeSureFolderExists(String folder) throws IOException {
    Path folderPath = Paths.get(folder);
    if (!Files.exists(folderPath)) {
      Files.createDirectory(folderPath);
    }
  }

  /**
   * Create the GUI.
   * 
   * @return the created <code>Scene</code>
   */
  private Scene createGUI() {
    /*
     * Main pane is an VBox.
     * = Information text
     * = Controls
     * = Output
     * = Status label
     */
    
    VBox vbox = new VBox();
    
    Node helpTextArea = createHelpTextArea();
    
    Node controlsPanel = createControlsPanel();
    
    outputTextArea = new TextArea();
    outputTextArea.setMinHeight(500.0);
    outputTextBuffer = new StringBuilder();
    
    statusLabel = new Label();
    HBox statusBox = new HBox();
    statusBox.getChildren().add(statusLabel);
    
    vbox.getChildren().addAll(helpTextArea, controlsPanel, outputTextArea, statusBox);
    
    return new Scene(vbox, 1700, 1200);
  }
  
  /**
   * Create the help text area, complete with the help text.
   * 
   * @return A <code>WebView</code> with a help text.
   */
  private WebView createHelpTextArea() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("This application installs the 'MyWorld' application.");
    buf.append("<p/>");
    buf.append("As the required .jar files are taken from my maven repository, it is required to run 'maven install' before running the installer.");
    buf.append("<p/>");
    buf.append("The installer patches the version information (by adding the current date/time) in the file 'MyWorldPropertyDescriptors.xmi'.");
    buf.append("This way it can always be checked when the application was installed.");
    buf.append("<p/>");
    buf.append("The installer creates and install 2 shortcuts:");
    buf.append("<ul>");
    buf.append("<li>");
    buf.append("MyWorld.lnk - which starts the MyWorld main window.");
    buf.append("</li>");
    buf.append("<li>");
    buf.append("Finan.lnk - which directly start the Finan component of the application.");
    buf.append("</li>");
    buf.append("</ul>");
    buf.append("<p/>");
    buf.append("There are 2 ways to perform the installation:");
    buf.append("<ul>");
    buf.append("<li>Windows 'Program Files' directory<br/>");
    buf.append("To install under this directory you need admistrator rights, which I currently cannot get working from within this program.");
    buf.append("Therefore a .bat installation script is generated, which you then have to execute as administrator.<br/>");
    buf.append("This method is used if the specified Installation Directory starts with \"C:\\Program Files\" or \"C:\\Program Files (x86)\".");
    buf.append("</li>");
    buf.append("<li>'Applic' directory<br/>");
    buf.append("'Applic' directory is synchronized via my NAS. Therefore when the program is installed under this directory it can be used on any computer.");
    buf.append("currently this doesn't work as the data is not available on all computers).<br/>");
    buf.append("As this is a normal user directory, files can just be copied to this directory.<br/>");
    buf.append("This method is used if the specified Installation Directory doesn't start with \"C:\\Program Files\" or \"C:\\Program Files (x86)\".");
    buf.append("</li>");
    buf.append("</ul>");
    buf.append("The installer will automatically run if it is started with the following arguments:");
    buf.append("<ul>");
    buf.append("<li>installation directory - the directory where the application will be installed.</li>");
    buf.append("<li>user data directory - the directory with the user data.</li>");
    buf.append("</ul>");
    
    WebView helpTextArea = new WebView();
    helpTextArea.getEngine().loadContent(buf.toString());
    helpTextArea.setMaxHeight(400);
    
    return helpTextArea;
  }
  
  /**
   * Create the panel with the controls.
   * <p>
   * This panel has controls to:
   * <ul>
   * <li>Select the installation directory</li>
   * <li>Select the User Data directory</li>
   * <li>Start the installation</li>
   * </ul>
   * @return
   */
  private Node createControlsPanel() {
    GridPane controlsPanel = new GridPane();
    controlsPanel.setPadding(new Insets(12.0, 12.0, 12.0, 12.0));
    controlsPanel.setHgap(12.0);
    controlsPanel.setVgap(12.0);
    
    Label label;
    
    // Installation folder
    label = new Label("Installation directory");
    controlsPanel.add(label, 0, 0);
    
    installationFolder = new FolderSelecter(null, 200, null, "Select installation directory", null, "Installation directory");
    installationFolderValidProperty = installationFolder.isValid();
    installationFolderValidProperty.addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateRunButtonStatus();
      }
      
    });
    
    controlsPanel.add(installationFolder.getFolderPathTextField(), 1, 0);
    controlsPanel.add(installationFolder.getFolderChooserButton(), 2, 0);
   
    // User data folder
    label = new Label("User Data directory");
    controlsPanel.add(label, 0, 1);
    userDataFolder = new FolderSelecter("D:\\Database\\MyWorld", 200, null, "User Data directory", null, "User Data directory");
    userDataFolderValidProperty = userDataFolder.isValid();
    controlsPanel.add(userDataFolder.getFolderPathTextField(), 1, 1);
    controlsPanel.add(userDataFolder.getFolderChooserButton(), 2, 1);
    
    runButton = new Button("Perform installation");
    runButton.setOnAction((e) -> installMyWorld());
    controlsPanel.add(runButton, 1, 2);
   
    return controlsPanel;
  }
  
  /**
   * Update the status of the run button.
   * <p>
   * The run button is enabled when:
   * <ul>
   * <li>There is currently no installation running</li>
   * <li>There is a valid installation directory selected</li>
   * <li>There is a valid User Data directory selected</li>
   * </ul>
   * Otherwise it is disabled.
   */
  private void updateRunButtonStatus() {
    if (!installationRunning  &&  installationFolderValidProperty.get()  &&  userDataFolderValidProperty.get()) {
      runButton.setDisable(false);
    } else {
      runButton.setDisable(true);
    }
  }
  
  /**
   * Append a line of text to the output area.
   * 
   * @param outputText the text to be added. The newline will be automatically added.
   */
  private void appendOutputTextLine(String outputText) {
    outputTextBuffer.append(outputText).append(NEWLINE);
    outputTextArea.setText(outputTextBuffer.toString());
  }
  
  /**
   * Install the MyWorld application, or generate a .bat file which has to be executed as superuser.
   * <p>
   * If the <code>installationDirectory</code> starts with 'C:\Program Files' or 'C:\Program Files (x86)',
   * a .bat file is generated. This script then has to be executed as superuser to perform the actual installation.
   * Otherwise the application is installed in the 
   * 
   * @throws IOException if a file or directory cannot be created.
   */
  public void installMyWorld() {
    statusLabel.setText("");
    appendOutputTextLine("Starting installation process ...");
    
    String javawPath = getJavawPath();
    if (javawPath == null) {
      appendOutputTextLine("No installation of javaw.exe was found, so the application cannot be installed. Javaw.exe is expected to be located under C:\\Program Files\\Java\\<java-version>\\bin.");
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because no installation of javaw.exe was found");
      
      return;
    }
    appendOutputTextLine("javaw.exe path is: " + javawPath);
   
       
    try {
      createMyWorldShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), MY_WORLD_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("MyWorld shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because MyWorld shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("MyWorld shortcut created: " + MY_WORLD_SHORTCUT_PATH);
    
    try {
      createFinanShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), FINAN_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("Finan shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because Finan shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("MyWorld shortcut created: " + FINAN_SHORTCUT_PATH);
    
    try {
      createMediaShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), MEDIA_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("Media shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because Media shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("Media shortcut created: " + MEDIA_SHORTCUT_PATH);
    
    try {
      createInvoicesAndPropertiesShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), INVOICES_AND_PROPERTIES_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("InvoicesAndProperties shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because InvoicesAndProperties shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("InvoicesAndProperties shortcut created: " + INVOICES_AND_PROPERTIES_SHORTCUT_PATH);
    
    try {
      createRolodexShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), ROLODEX_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("Rolodex shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because Rolodex shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("Rolodex shortcut created: " + ROLODEX_SHORTCUT_PATH);
    
    try {
      createUnitConverterShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), UNIT_CONVERTER_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("UnitConverter shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because UnitConverter shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("UnitConverter shortcut created: " + UNIT_CONVERTER_SHORTCUT_PATH);
    
    try {
      createPCToolsShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), PC_TOOLS_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("PCTools shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because PCTools shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("PCTools shortcut created: " + PC_TOOLS_SHORTCUT_PATH);
    
    try {
      createVacationsShortCut(javawPath, installationFolder.getFolderPathTextField().getText(), userDataFolder.getFolderPathTextField().getText(), VACATIONS_SHORTCUT_PATH);
    } catch (IOException e) {
      appendOutputTextLine("Vacations shortcut couldn't be created. System message: " + e.getMessage());
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because Vacations shortcut couldn't be created.");
      
      return;
    }
    appendOutputTextLine("Vacations shortcut created: " + VACATIONS_SHORTCUT_PATH);
    
    try {
      String updatedVersion = updateVersionInformation();
      appendOutputTextLine("Version information updated to version: " + updatedVersion);
    } catch (IOException | RuntimeException e) {
      if (e instanceof FileNotFoundException) {
        appendOutputTextLine("Version information couldn't be updated. The file '" + e.getMessage() + "' doesn't exist.");
      } else {
        appendOutputTextLine("Version information couldn't be updated. System message: " + e.getMessage());
      }
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because version information couldn't be updated.");
      
      return;
    }
    
    List<String> missingFiles = checkInstallationFilesAvailability();
    if (!missingFiles.isEmpty()) {
      appendOutputTextLine("The following installation files are missing:");
      for (String fileName: missingFiles) {
        appendOutputTextLine(fileName);
      }
      appendOutputTextLine("Installation aborted");
      statusLabel.setText("Installation failed, because one or more installation files are missing");
      
      return;
    }
    

    /*
     * To install in the 'Program Files' directory, administrator rights are needed, so in that case a script is generated which has
     * to be run as administrator.<br/>
     * Otherwise the files are just copied to the installation directory.<br/>
     * The 'Program Files' directory is assumed if the first two parts of installationPath are 'C:' and 'Program Files' or 'Program Files (x86)'.
     */
    String[] directoryParts = installationFolder.getFolderPathTextField().getText().split("\\\\");
    if ((directoryParts.length >= 2)  &&
        directoryParts[0].equalsIgnoreCase("C:")  &&
        (directoryParts[1].equals("Program Files")  ||  directoryParts[1].equals("Program Files (x86)"))) {
      appendOutputTextLine("Installation under Windows Program Files");
      //      installInProgramFiles(installationDirectory);
    } else {
      appendOutputTextLine("Installation NOT under Windows Program Files");
      try {
        installOutsideProgramFiles();
      } catch (IOException e) {
        appendOutputTextLine("Installing files failed. System message: " + e.getMessage());
        appendOutputTextLine("Installation aborted");
        statusLabel.setText("Installation failed.");
        
        return;
      }
    }

    appendOutputTextLine("Installation complete");
    statusLabel.setText("Installation complete");
  }
  
  private List<String> checkInstallationFilesAvailability() {
    List<String> missingFiles = new ArrayList<>();
    
    // Check the resource files.
    appendOutputTextLine("Checking the resource files ...");
    for (String resourceFilename: resourceList) {
      Path sourcePath = Paths.get(resourceFilename);
      appendOutputTextLine("    Checking file: " + sourcePath.toString());
      if (!Files.exists(sourcePath)) {
        missingFiles.add(resourceFilename);
      }
    }
    
    // Install the .jar files.
    appendOutputTextLine("Checking the jar files ...");
    for(String jarFilePath: jarFiles) {
      Path sourcePath = Paths.get(MAVEN_REPOSITORY_HOME, jarFilePath);
      appendOutputTextLine("    Checking file: " + sourcePath.toString());
      if (!Files.exists(sourcePath)) {
        missingFiles.add(jarFilePath);
      }
    }
    
    return missingFiles;
  }

  /**
   * Get the absolute path to javaw.exe.
   * <p>
   * The executable is expected to exist under C:\Program Files\Java\<java-version>\\bin.
   * 
   * @return the absolute path to javaw.exe, or null if it couldn't be found
   */
  private static String getJavawPath() {
    Path searchStartPath = Paths.get(JAVA_MAIN_FOLDER);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(searchStartPath)) {

      for (Path checkDirectory: stream) {
        if (Files.isDirectory(checkDirectory)) {
          String folderName = checkDirectory.getFileName().toString();
          LOGGER.info("checking: " + folderName);
          Path javawPath = checkDirectory.resolve("bin").resolve(JAVAW_EXECUTABLE);
          if (Files.exists(javawPath)) {
            LOGGER.info("Found javaw.exe: " + javawPath.toString());
            return javawPath.toString();
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      LOGGER.severe("Problem in finding javaw.exe: " + x.getMessage());
    }
    
    return null;
  }
  
  /**
   * Create a shortcut to start the main window of the application.
   * <p>
   * The shortcut, named 'MyWorld.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'MyWorld_32x32.ico'.
   * The command line argument (the data directory) is specified by the parameter <code>userDataDirectory<code>.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data.
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createMyWorldShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
//    ShellLink shellLink = ShellLink.createLink(javawPath)
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\MyWorld_32x32.ico");
//        .setCMDArgs("--module-path . --add-modules goedegep.myworld goedegep.myworld.MyWorld --add-opens \"java.base/java.lang=jaxb.impl\" --add-opens \"java.xml/javax.xml.namespace=com.google.gson\" --add-opens=\"javafx.base/com.sun.javafx.event=org.controlsfx.controls\" -d \"" + userDataDirectory + "\"");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the Finan component.
   * <p>
   * The shortcut, named 'Finan.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'FinanLogo.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a Finan' to start the Finan component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createFinanShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\FinanLogo.ico")
//        .setCMDArgs("--module-path . --add-modules goedegep.myworld goedegep.myworld.MyWorld --add-opens \"java.base/java.lang=jaxb.impl\" --add-opens \"java.xml/javax.xml.namespace=com.google.gson\" --add-opens=\"javafx.base/com.sun.javafx.event=org.controlsfx.controls\" -d \"" + userDataDirectory + "\" -a Finan");
        .setCMDArgs("Finan");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the Media component.
   * <p>
   * The shortcut, named 'Media.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'Media.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a Media' to start the Media component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createMediaShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\Media.ico")
        .setCMDArgs("Media");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the Invoices and Properties component.
   * <p>
   * The shortcut, named 'InvoicesAndProperties.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'InvoicesAndProperties.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a InvoicesAndProperties' to start the Invoices and Properties component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createInvoicesAndPropertiesShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\InvoicesAndProperties.ico")
        .setCMDArgs("InvoicesAndProperties");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the Rolodex component.
   * <p>
   * The shortcut, named 'Rolodex.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'Rolodex.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a Rolodex' to start the Rolodex component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createRolodexShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\Rolodex.ico")
        .setCMDArgs("Rolodex");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the UnitConverter component.
   * <p>
   * The shortcut, named 'UnitConverter.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'UnitConverter.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a UnitConverter' to start the UnitConverter component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createUnitConverterShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\UnitConverter.ico")
        .setCMDArgs("UnitConverter");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the PCTools component.
   * <p>
   * The shortcut, named 'PCTools.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'PCTools.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a PCTools' to start the UnitConverter component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createPCToolsShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\PCTools.ico")
        .setCMDArgs("PCTools");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);
  }

  /**
   * Create a shortcut to start the Vacations component.
   * <p>
   * The shortcut, named 'Vacations.lnk', starts java with the right .jar file in the specified Installation Directory.
   * The icon of the shortcut is 'Vacations.ico'.
   * The first command line argument (the data directory is specified by the parameter 'dataDirectory').<br/>
   * The second argument is '-a Vacations' to start the UnitConverter component.
   * 
   * @param javawPath path to the javaw.exe
   * @param installationDirectory the directory where the application will be installed.
   * @param userDataDirectory the directory with the user data
   * @param shortcutPath relative path for the shortcut to be created.
   * @throws IOException if the shortcut file cannot be created.
   */
  private static void createVacationsShortCut(String javawPath, String installationDirectory, String userDataDirectory, String shortcutPath) throws IOException {
    ShellLink shellLink = ShellLink.createLink("C:\\Applic\\MyWorld\\MyWorld.bat")
        .setWorkingDir(installationDirectory)
        .setIconLocation(installationDirectory + "\\Vacations.ico")
        .setCMDArgs("Vacations");
    try {
      shellLink.getHeader().setShowCommand(ShellLinkHeader.SW_SHOWMINNOACTIVE);
    } catch (ShellLinkException e) {
      // We know we use a valid value, so no action
    }
    shellLink.saveTo(shortcutPath);    
  }
    
  /**
   * This method updates (patches) the MyWorld version information.
   * <p/>
   * The version information is specified in the <b>version</b> property of the MyWorld Property Descriptors file.<br/>
   * The current version is assumed to have the format '&lt;version&gt; - &lt;date-time&gt;' or just '&lt;version&gt;',
   * where &lt;version&gt; has no spaces.
   * So, if the current version contains a space, the &lt;version&gt; is everything before the first space.
   * Otherwise the &lt;version&gt; is the complete current version value. In both cases ' - &lt;date-time&gt;' is appended to the &lt;version&gt;.
   * <p/>
   * Note: This method creates a {@link PropertyDescriptorsResource}, which fills the related <b>Registry</b>.
   */
  private String updateVersionInformation() throws IOException {
    // Remember that the PropertyDescriptorsResource also fills the related registry.
    LOGGER.info("MIJN_WERELD_PROPERTY_DESCRIPTORS_FILE=" + MY_WORLD_PROPERTY_DESCRIPTORS_FILE);

    EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = new EMFResource<>(
        PropertiesPackage.eINSTANCE,
        () -> PropertiesFactory.eINSTANCE.createPropertyDescriptorGroup());
    propertyDescriptorsResource.load(MY_WORLD_PROPERTY_DESCRIPTORS_FILE);
    PropertyDescriptorGroup propertyDescriptorGroup = propertyDescriptorsResource.getEObject();
    PropertyDescriptor versionPropertyDescriptor = propertyDescriptorGroup.getPropertyDescriptor("version");
    String version = null;
    if (versionPropertyDescriptor != null) {
      version = versionPropertyDescriptor.getInitialValue();
      int firstSpaceIndex = version.indexOf(" ");
      if (firstSpaceIndex != -1) {
        String versionNumber = version.substring(0, firstSpaceIndex);
        version = versionNumber + " - " + DF.format(new Date());
      } else {
        version = version + " - " + DF.format(new Date());
      }
      versionPropertyDescriptor.setInitialValue(version);
      propertyDescriptorsResource.save();
    } else {
      throw new RuntimeException("property descriptor for property \\'version\\' not found");
    }
    
    return version;
  }
  
  /**
   * This method installs MyWorld to a directory which doesn't require administrator rights.
   * <ul>
   * <li>
   * If there is a previous version (a directory with the name 'installationDirectory + Prev'), it is removed.
   * </li>
   * <li>
   * If there is a current version (a directory with the name 'installationDirectory') it is renamed to 'installationDirectory + Prev'.
   * </li>
   * <li>
   * The directory 'installationDirectory' is created and all files of the MyWorld application are copied into that directory.
   * </li>
   * </ul>
   * @throws IOException
   */
  private void installOutsideProgramFiles() throws IOException {
    LOGGER.info("=>");
    
    String installationDirectoryPrev = installationFolder.getFolderPathTextField().getText() + "Prev";
    Path installationPathPrev = Paths.get(installationDirectoryPrev);
    Path destinationPath = Paths.get(installationFolder.getFolderPathTextField().getText());

    if (Files.exists(installationPathPrev)) {
      appendOutputTextLine("There already exists a previous version, which will now be removed.");
      DirectoryUtils.recursivelyDeleteDirectory(installationPathPrev);
    }

    if (Files.exists(destinationPath)) {
      appendOutputTextLine("A current version exists, this is renamed to \"" + installationDirectoryPrev + "\".");
      Files.move(destinationPath, installationPathPrev);
      
      Files.createDirectory(destinationPath);
    }
    
    // Install the resource files.
    appendOutputTextLine("Installing the resource files ...");
    for (String fileInfo: resourceList) {
      Path sourcePath = Paths.get(fileInfo);
      String fileName = sourcePath.getFileName().toString();
      Path destinationFilePath = Paths.get(installationFolder.getFolderPathTextField().getText(), fileName);
      appendOutputTextLine("    Copying file from: " + sourcePath.toString() + ", to: " + destinationFilePath.toString());
      Files.copy(sourcePath, destinationFilePath);
    }
    
    // Install the .jar files.
    appendOutputTextLine("Installing the jar files ...");
    for(String jarFilePath: jarFiles) {
      Path sourcePath = Paths.get(MAVEN_REPOSITORY_HOME, jarFilePath);
      String fileName = sourcePath.getFileName().toString();
      Path destinationFilePath = Paths.get(installationFolder.getFolderPathTextField().getText(), fileName);
      appendOutputTextLine("    Copying file from: " + sourcePath.toString() + ", to: " + destinationFilePath.toString());
      Files.copy(sourcePath, destinationFilePath);
    }

    LOGGER.info("<=");
  }

  public static void main(String[] args) throws IOException {
    MyWorldInstaller.launch(args);
    
    
  }
}
