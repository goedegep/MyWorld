package goedegep.vacations.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.resources.ImageResource;
import goedegep.util.RunningInEclipse;
import goedegep.util.dir.DirectoryChangesListener;
import goedegep.util.string.StringUtil;
import goedegep.vacations.app.guifx.VacationsWindow;
import goedegep.vacations.app.logic.PhotoThumbnailManager;
import goedegep.vacations.app.logic.VacationsRegistry;

/**
 * This class is the Travels application.
 * <p>
 * It holds the customization information and provides methods to show the main window of the application.
 * It is built on top of logic and guifx sub packages.
 */
public class TravelsService {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelsService.class.getName());
  
  private static final String VACATIONS_CONFIGURATION_FILE = "VacationsConfiguration.xmi";

  private static TravelsService instance;
  private static CustomizationFx customization;
  
  private List<DirectoryChangesListener> directoryChangesListeners = new ArrayList<>();
  
  /**
   * Get the singleton instance of the TravelsApplication.
   * 
   * @return the singleton instance of TravelsApplication.
   */
  public static TravelsService getInstance() {
    // Ensure that the application is a singleton
    if (instance == null) {
      instance = new TravelsService();
    }
    ImageResource.checkResources();
    
    return instance;    
  }
  
  /**
   * Show the main window of the application.
   */
  public void showTravelsWindow() {
    // Show the main window of the application
    VacationsWindow travelsWindow = new VacationsWindow(customization);
    travelsWindow.show();
  }

  public CustomizationFx getCustomization() {
    return customization;
  }
  
  public void addDirectoryChangesListener(DirectoryChangesListener listener) {
    if (!directoryChangesListeners.contains(listener)) {
      directoryChangesListeners.add(listener);
    }
    if (directoryChangesListeners.size() == 1) {
      startDirectoryChangesMonitoring();
    }
  }
  
  
  /**
   * Private constructor to ensure singleton pattern.
   */
  private TravelsService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      VacationsRegistry.developmentMode = true;
    }
    
    try {
      
      /*
       * Read the application properties.
       * This is done here because the application properties contain the application version, which is derived from the version in the pom.xml file.
       */
      Properties props = new Properties();
      try (InputStream in = getClass().getResourceAsStream("TravelsApplication.properties")) {
          props.load(in);
          String appVersion = props.getProperty("travels.app.version");
          VacationsRegistry.version = appVersion;
          String appName = props.getProperty("travals.app.name");
          VacationsRegistry.applicationName = appName;
      } catch (Exception e) {
        LOGGER.severe("Error reading application properties: " + e.getMessage());
        System.exit(1);
      }

      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(VacationsRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

      // Read the customization info.
      url = getClass().getResource(VACATIONS_CONFIGURATION_FILE);
      customization = CustomizationsFx.readCustomization(url);
    } catch (IOException e) {
      JfxApplication.reportException(null, e);
    }

    startPhotoThumbnailsCreation();
  }
  
  /**
   * Read application properties from the .properties file.
   */
  private void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("TravelsApplication.properties")) {
        props.load(in);
        String appVersion = props.getProperty("travels.app.version");
        VacationsRegistry.version = appVersion;
        String appName = props.getProperty("travals.app.name");
        VacationsRegistry.applicationName = appName;
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  /**
   * Start the background task to create photo thumbnails.
   */
  private void startPhotoThumbnailsCreation() {
    List<String> ignoreFoldersAsList = StringUtil.semicolonSeparatedValuesToListOfValues(VacationsRegistry.ignoreVacationPictureFolders);
    Path vacationPicturesFolderPath = Paths.get(VacationsRegistry.vacationPicturesFolderName);
    PhotoThumbnailManager photoThumbnailManager = new PhotoThumbnailManager(vacationPicturesFolderPath, ignoreFoldersAsList);
    Thread photoThumbnailManagerThread = new Thread(photoThumbnailManager);
    photoThumbnailManagerThread.setDaemon(true);
    photoThumbnailManagerThread.start();
  }
  
  private void startDirectoryChangesMonitoring() {
//    if (VacationsRegistry.vacationPicturesFolderName != null  ||  VacationsRegistry.vacationsFolderName != null) {
//      DirectoryChangesMonitoringTask directoryMonitoringTask = new DirectoryChangesMonitoringTask(VacationsRegistry.vacationPicturesFolderName, VacationsRegistry.vacationsFolderName);
//
//      directoryMonitoringTask.valueProperty().addListener((_, _, watchEvent) -> {
//        notifyDirectoryChangesListeners(watchEvent);
//      });
//
//      Thread directoryMonitoringThread = new Thread(directoryMonitoringTask);
//      directoryMonitoringThread.setDaemon(true);
//      directoryMonitoringThread.start();
//    }
  }
  
  private void notifyDirectoryChangesListeners(WatchEvent<Path> watchEvent) {
    for (DirectoryChangesListener listener : directoryChangesListeners) {
      listener.directoryChange(watchEvent);
    }
  }
}
