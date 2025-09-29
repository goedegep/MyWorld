package goedegep.vacations.app;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.resources.ImageResource;
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
public class TravelsApplication {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelsApplication.class.getName());
  
  private static final String VACATIONS_CONFIGURATION_FILE = "VacationsConfiguration.xmi";

  private static TravelsApplication instance;
  private static CustomizationFx customization;
  
  private List<DirectoryChangesListener> directoryChangesListeners = new ArrayList<>();
  
  /**
   * Private constructor to ensure singleton pattern.
   */
  private TravelsApplication() {
    
    try {
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
   * Get the singleton instance of the TravelsApplication.
   * 
   * @return the singleton instance of TravelsApplication.
   */
  public static TravelsApplication getInstance() {
    // Ensure that the application is a singleton
    if (instance == null) {
      instance = new TravelsApplication();
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
