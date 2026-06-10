package goedegep.travels.svc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.mapview.maptile.osm.TileCacheCleanupTask;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.resources.ImageResource;
import goedegep.travels.ITravelsService;
import goedegep.travels.gui.BoundariesPointsReductionWindow;
import goedegep.travels.gui.MapViewTestWindow;
import goedegep.travels.gui.TravelsAppResourcesFx;
import goedegep.travels.gui.TravelsWindow;
import goedegep.travels.logic.PhotoThumbnailManager;
import goedegep.travels.logic.TravelsRegistry;
import goedegep.travels.model.Location;
import goedegep.travels.model.Travels;
import goedegep.travels.model.TravelsFactory;
import goedegep.travels.model.TravelsPackage;
import goedegep.util.dir.DirectoryChangesListener;
import goedegep.util.emf.EMFResource;
import goedegep.util.string.StringUtil;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

/**
 * This class is the Service of the Travels application.
 * <p>
 * It holds the customization information and provides methods to show the main window of the application.
 * It is built on top of logic and gui packages.
 * The TravelsService is a singleton of which the instance can be obtained by {@link getInstance()}.<br/>
 * The service checks for changes in the vacation pictures folder and the vacations folder, and notifies registered listeners about these changes.
 */
public class TravelsService extends Service implements ITravelsService {
  private static final Logger LOGGER = Logger.getLogger(TravelsService.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final String TRAVELS_APPLICATION_PROPERTIES_FILE_NAME = "TravelsApplication.properties";
  

  /**
   * Singleton instance of the TravelsService.
   */
  private static TravelsService instance;
  
  /**
   * Registry.
   */
  private TravelsRegistry travelsRegistry;
    
  /**
   * The {@code ResourceBundle} for travels applicatoin. It is used for internationalization of the GUI texts.
   * Note: this is only implemented for a small part.
   */
  private static final ResourceBundle TRANSLATIONS = getBundle(TravelsService.class, "TravelsResource");
  
  /**
   * Registered listeners for directory changes.
   * <p>
   * Registering is done via {@link #addDirectoryChangesListener(DirectoryChangesListener)}.
   */
  private List<DirectoryChangesListener> directoryChangesListeners = new ArrayList<>();
  
  /**
   * Get the singleton instance of the TravelsApplication.
   * <p>
   * If the instance doesn't exist yet, it is created and initialized.
   * 
   * @return the singleton instance of TravelsApplication.
   */
  public static TravelsService getInstance() {
    if (instance == null) {
      instance = new TravelsService();
      instance.initialize();
      if (!instance.handleVacationsInitialization(instance.customization)) {
         // If the required files and folders don't exist and the user doesn't want to create them, we exit the application, because it can't function without these files and folders.
          Alert alert = new Alert(Alert.AlertType.INFORMATION, "The application will now exit because the required files and folders don't exist and the user doesn't want to create them.", ButtonType.OK);
          alert.showAndWait();
          System.exit(0);
      }

      instance.startPhotoThumbnailsCreation();
      instance.setOSMUserAgentSystemProperty();
      instance.startTileCacheCleanupTask();
    }
    
    return instance;    
  }
  
  /**
   * Set the http.agent system property to the OSM User Agent string from the registry, if it is not set yet.
   * <p>
   * This is needed for proper usage of the OpenStreetMap API's; map tiles and Nominatim geocoding service.
   */
  private void setOSMUserAgentSystemProperty() {
    String httpAgent = System.getProperty("http.agent");
    if (httpAgent == null) {
      String osmUserAgent = travelsRegistry.getOSMUserAgent();
      System.setProperty("http.agent", osmUserAgent);
    }    
  }
  
  /**
   * Show the main window of the application.
   */
  public void showTravelsWindow() {
        
    new TravelsWindow(customization, this, TRANSLATIONS);
//    new MapViewTestWindow();
  }
  
  /**
   * Show the Boundaries Points Reduction window.
   * 
   * @param location the location for which the boundaries points reduction window should be shown.
   */
  public void showBoundariesPointsReductionWindow(Location location) {
    new BoundariesPointsReductionWindow(customization, location);
  }
  
  /**
   * Add a listener for changes in the vacations folder and the vacation pictures folder.
   * <p>
   * When the first listener is added, the monitoring of the directories is started.
   * 
   * @param listener the {@link DirectoryChangesListener} to add.
   */
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
    travelsRegistry = TravelsRegistry.getInstance();
  }
  
  
  /**
   * If the vacations file doesn't exist and/or the vacations folder doesn't exist, ask the user whether they should be created, or whether the user wants to edit the User Settings.
   */
  private boolean handleVacationsInitialization(CustomizationFx customization) {
    boolean returnValue = false;
    
    Path vacationsFile = Paths.get(travelsRegistry.getTravelsFileName());
    Path vacationsFolder = Paths.get(travelsRegistry.getVacationsFolderName());
    Path checklistFile = Paths.get(travelsRegistry.getVacationChecklistFileName());
    
    if (!Files.exists(vacationsFile)  || !Files.exists(vacationsFolder)  ||  !Files.exists(checklistFile)) {
      StringBuilder buf = new StringBuilder();
      buf.append("The following files and/or folders don't exist yet:").append(NEWLINE);
      if (!Files.exists(vacationsFile)) {
        buf.append("* The travels file '").append(vacationsFile).append("'").append(NEWLINE);
      }
      if (!Files.exists(vacationsFolder)) {
        buf.append("* The vacations folder '").append(vacationsFolder).append("'").append(NEWLINE);
      }
      if (!Files.exists(checklistFile)) {
        buf.append("* The vacation checklist file '").append(checklistFile).append("'").append(NEWLINE);
      }
      buf.append("""
          If you are just starting to use this application, you may want to edit the User Settings, to set the file and folder names to your preference.
          In this case you have to restart the application after saving the changes.
          Otherwise you can let the files and/or folder be created for you.
          """);
      ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
      Optional<UserChoice> optionalUserChoice = componentFactory.createChoiceDialog("How to continue?", buf.toString(), "what to do?", UserChoice.SHOW_SETTINGS_EDITOR, UserChoice.values()).showAndWait();
      if (optionalUserChoice.isPresent()) {
        UserChoice userChoice = optionalUserChoice.get();
        switch (userChoice) {
        case SHOW_SETTINGS_EDITOR:
          returnValue = false; // If the user settings are changed, a restart of the application is needed
          showPropertiesEditor(getBundle(TravelsWindow.class, "TravelsPropertyDescriptorsResource"));
          break;
          
        case CREATE_MISSING_FILES_AND_OR_FOLDERS:
          try {
            // Create a vacations file if it doesn't exist
            if (!Files.exists(vacationsFile)) {
              // create the parent folder if it doesn't exist
              Path parent = vacationsFile.getParent();
              Files.createDirectories(parent);
              
              // create the file
              EMFResource<Travels> vacationsResource = new EMFResource<>(
                  TravelsPackage.eINSTANCE, 
                  () -> TravelsFactory.eINSTANCE.createTravels(),
                  ".xmi",
                  true);
              vacationsResource.newEObject();
              try {
                vacationsResource.save(travelsRegistry.getTravelsFileName());
              } catch (IOException e1) {
                e1.printStackTrace();
              }
              
            }
            
            // Create the vacations folder if it doesn't exist
            if (!Files.exists(vacationsFolder)) {
              Files.createDirectories(vacationsFolder);
            }
            // create the checklist file if it doesn't exist
            if (!Files.exists(checklistFile)) {
              // create the parent folder if it doesn't exist
              Path parent = checklistFile.getParent();
              Files.createDirectories(parent);

              EMFResource<VacationChecklist> vacationChecklistResource = new EMFResource<>(
                  VacationChecklistPackage.eINSTANCE, 
                  () -> VacationChecklistFactory.eINSTANCE.createVacationChecklist(),
                  ".xmi",
                  true);
              VacationChecklist vacationChecklist = vacationChecklistResource.newEObject();
              VacationChecklistCategoriesList vacationChecklistCategoriesList = VacationChecklistFactory.eINSTANCE.createVacationChecklistCategoriesList();
              vacationChecklist.setVacationChecklistCategoriesList(vacationChecklistCategoriesList);
              VacationChecklistLabelsList vacationChecklistLabelsList = VacationChecklistFactory.eINSTANCE.createVacationChecklistLabelsList();
              vacationChecklist.setVacationChecklistLabelsList(vacationChecklistLabelsList);
              vacationChecklistResource.save(travelsRegistry.getVacationChecklistFileName());
            }
            
            returnValue = true; // required file and folders now exist, so we can continue.
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        }
      }
      
    } else {
      returnValue = true;
    }

    return returnValue;
  }
  
  /**
   * Start the background task to create photo thumbnails.
   */
  private void startPhotoThumbnailsCreation() {
    List<String> ignoreFoldersAsList = StringUtil.semicolonSeparatedValuesToListOfValues(travelsRegistry.getIgnoreVacationPictureFolders());
    String vacationPicturesFolderName = travelsRegistry.getVacationPicturesFolderName();
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
//    if (travelsRegistry.vacationPicturesFolderName != null  ||  travelsRegistry.vacationsFolderName != null) {
//      DirectoryChangesMonitoringTask directoryMonitoringTask = new DirectoryChangesMonitoringTask(travelsRegistry.vacationPicturesFolderName, travelsRegistry.vacationsFolderName);
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
  
  /**
   * Start the background task to clean up the tile cache.
   */
  private void startTileCacheCleanupTask() {
    // Start the tile cache cleanup task in a background thread.
    Thread tileCacheCleanupThread = new Thread(new TileCacheCleanupTask());
    tileCacheCleanupThread.setDaemon(true);
    tileCacheCleanupThread.start();
  }
  
  /**
   * Notify the registered listeners for directory changes about a change in the monitored directories.
   * 
   * @param watchEvent the WatchEvent with information about the change in the monitored directories.
   */
  private void notifyDirectoryChangesListeners(WatchEvent<Path> watchEvent) {
    for (DirectoryChangesListener listener : directoryChangesListeners) {
      listener.directoryChange(watchEvent);
    }
  }
  
  @Override
  protected void handleApplicationProperties() {
    Properties props = getApplicationProperties();
    
    travelsRegistry.setVersion(props.getProperty("travels.version"));
    travelsRegistry.setApplicationName(props.getProperty("travels.name"));
    travelsRegistry.setAuthor(props.getProperty("travels.author"));
    travelsRegistry.setCopyrightMessage(props.getProperty("travels.copyright"));
    travelsRegistry.setShortProductInfo(props.getProperty("travels.description"));    
  }
  
  /**
   * Get the application properties from the properties file.
   * 
   * @return the application properties read from the properties file.
   */
  public static Properties getApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = TravelsService.class.getResourceAsStream(TRAVELS_APPLICATION_PROPERTIES_FILE_NAME)) {
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
    return new TravelsAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return travelsRegistry;
  }
  
  @Override
  public void showPropertiesEditor() {
    showPropertiesEditor(getBundle(TravelsWindow.class, "TravelsPropertyDescriptorsResource"));
  }
  
  /**
   * Get a {@link ResourceBundle} for the default locale.
   * 
   * @param clazz a class from the package in which the ResourceBundle is located.
   * @param bundle basename of the resource bundle
   * @return the requested ResourceBundle
   */
  public static ResourceBundle getBundle(Class<? extends Object> clazz, String bundle) {
      String bundlePath = clazz.getPackage().getName() + "." + bundle;
      Locale locale = Locale.getDefault();
      ClassLoader classLoader = clazz.getClassLoader();
      return ResourceBundle.getBundle(bundlePath, locale, classLoader);
  }
}


enum UserChoice {
  SHOW_SETTINGS_EDITOR("Edit User Settings"),
  CREATE_MISSING_FILES_AND_OR_FOLDERS("Create missing files and/or folders");

  private String text;

  UserChoice(String text) {
    this.text = text;
  }

  public String toString() {
    return text;
  }
}