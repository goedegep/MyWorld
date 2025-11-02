package goedegep.vacations.app;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.resources.ImageResource;
import goedegep.util.dir.DirectoryChangesListener;
import goedegep.util.string.StringUtil;
import goedegep.vacations.app.guifx.VacationsAppResourcesFx;
import goedegep.vacations.app.guifx.VacationsWindow;
import goedegep.vacations.app.logic.PhotoThumbnailManager;
import goedegep.vacations.app.logic.VacationsRegistry;
import javafx.scene.paint.Color;

/**
 * This class is the Travels application.
 * <p>
 * It holds the customization information and provides methods to show the main window of the application.
 * It is built on top of logic and guifx sub packages.
 */
public class TravelsService extends Service {
  private static final Logger LOGGER = Logger.getLogger(TravelsService.class.getName());

  /**
   * Singleton instance of the TravelsService.
   */
  private static TravelsService instance;
  
  private VacationsRegistry vacationsRegistry;
  
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
      instance.initialize();
      
      ImageResource.checkResources();
    }
    
    return instance;    
  }
  
  /**
   * Show the main window of the application.
   */
  public void showTravelsWindow() {
    // Show the main window of the application
    VacationsWindow travelsWindow = new VacationsWindow(customization, this);
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
    vacationsRegistry = VacationsRegistry.getInstance();

    startPhotoThumbnailsCreation();
  }
  
  /**
   * Start the background task to create photo thumbnails.
   */
  private void startPhotoThumbnailsCreation() {
    List<String> ignoreFoldersAsList = StringUtil.semicolonSeparatedValuesToListOfValues(vacationsRegistry.getIgnoreVacationPictureFolders());
    String vacationPicturesFolderName = vacationsRegistry.getVacationPicturesFolderName();
    if (vacationPicturesFolderName == null) {
      LOGGER.severe("Photo thumbnails creation not started because the vacation pictures folder name isn't specified.");
      return;
    }
    
    Path vacationPicturesFolderPath = Paths.get(vacationPicturesFolderName);
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
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("TravelsApplication.properties")) {
        props.load(in);
        
        vacationsRegistry.setVersion(props.getProperty("travels.app.version"));
        vacationsRegistry.setApplicationName(props.getProperty("travels.app.name"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  public static Properties getApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = TravelsService.class.getResourceAsStream("TravelsApplication.properties")) {
        props.load(in);
        
        return props;
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
    
    return null;
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(238,238,238));
    look.setButtonBackgroundColor(Color.rgb(238,238,238));
    look.setPanelBackgroundColor(Color.rgb(238,238,238));
    look.setListBackgroundColor(Color.rgb(238,238,238));
    look.setLabelBackgroundColor(Color.rgb(238,238,238));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new VacationsAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return vacationsRegistry;
  }

  public void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, vacationsRegistry.getPropertyDescriptorsFileURI());
  }
}
