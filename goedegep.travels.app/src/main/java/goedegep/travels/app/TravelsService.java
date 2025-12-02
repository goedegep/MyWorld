package goedegep.travels.app;

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
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.resources.ImageResource;
import goedegep.travels.app.guifx.BoundariesPointsReductionWindow;
import goedegep.travels.app.guifx.TravelsAppResourcesFx;
import goedegep.travels.app.guifx.TravelsWindow;
import goedegep.travels.app.logic.PhotoThumbnailManager;
import goedegep.travels.app.logic.TravelsRegistry;
import goedegep.util.dir.DirectoryChangesListener;
import goedegep.util.string.StringUtil;
import goedegep.vacations.model.Location;
import javafx.scene.paint.Color;

/**
 * This class is the Travels application.
 * <p>
 * It holds the customization information and provides methods to show the main window of the application.
 * It is built on top of logic and guifx sub packages.
 */
public class TravelsService extends Service {
  private static final Logger LOGGER = Logger.getLogger(TravelsService.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * Singleton instance of the TravelsService.
   */
  private static TravelsService instance;
  
  private TravelsRegistry vacationsRegistry;
  
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

      instance.startPhotoThumbnailsCreation();
    }
    
    return instance;    
  }
  
  /**
   * Show the main window of the application.
   */
  public void showTravelsWindow() {
    // Show the main window of the application
    TravelsWindow travelsWindow = new TravelsWindow(customization, this);
    travelsWindow.show();
  }
  
  /**
   * Show the Boundaries Points Reduction window.
   * 
   * @param location the location for which the boundaries points reduction window should be shown.
   */
  public void showBoundariesPointsReductionWindow(Location location) {
    new BoundariesPointsReductionWindow(customization, location);
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
    vacationsRegistry = TravelsRegistry.getInstance();
  }
  
  
//  /**
//   * If the vacations file doesn't exist and/or the vacations folder doesn't exist, ask the user whether they should be created, or whether the user wants to edit the User Settings.
//   */
//  private boolean handleVacationsInitiolization(CustomizationFx customization) {
//    boolean returnValue = false;
//    
//    File vacationsFile = new File(vacationsRegistry.getVacationsFileName());
//    File vacationsFolder = new File(vacationsRegistry.getVacationsFolderName());
//    File checklistFile = new File(vacationsRegistry.getVacationChecklistFileName());
//    
//    if (!vacationsFile.exists()  ||  !vacationsFolder.exists()  ||  !checklistFile.exists()) {
//      StringBuilder buf = new StringBuilder();
//      buf.append("The following files and/or folders don't exist yet:").append(NEWLINE);
//      if (!vacationsFile.exists()) {
//        buf.append("* The vacations file '").append(vacationsRegistry.getVacationsFileName()).append("'").append(NEWLINE);
//      }
//      if (!vacationsFolder.exists()) {
//        buf.append("* The vacations folder '").append(vacationsRegistry.getVacationsFolderName()).append("'").append(NEWLINE);
//      }
//      if (!checklistFile.exists()) {
//        buf.append("* The vacation checklist file '").append(vacationsRegistry.getVacationChecklistFileName()).append("'").append(NEWLINE);
//      }
//      buf.append("""
//          If you are just starting to use this application, you may want to edit the User Settings, to set the file and folder names to your preference.
//          In this case you have to restart the application after saving the changes.
//          Otherwise you can let the files and/or folder be created for you.
//          """);
//      ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
//      Optional<UserChoice> optionalUserChoice = componentFactory.createChoiceDialog("How to continue?", buf.toString(), "what to do?", UserChoice.SHOW_SETTINGS_EDITOR, UserChoice.values()).showAndWait();
//      if (optionalUserChoice.isPresent()) {
//        UserChoice userChoice = optionalUserChoice.get();
//        switch (userChoice) {
//        case SHOW_SETTINGS_EDITOR:
//          returnValue = false; // If the user settings are changed, a restart of the application is needed
//          showPropertiesEditor(customization);
//          break;
//          
//        case CREATE_MISSING_FILES_AND_OR_FOLDERS:
//          try {
//            // Create a vacations file if it doesn't exist
//            if (!vacationsFile.exists()) {
//              // create the parent folder if it doesn't exist
//              String parent = vacationsFile.getParent();
//              Files.createDirectories(Paths.get(parent));
//              
//              // create the file
//              EMFResource<Vacations> vacationsResource = new EMFResource<>(
//                  VacationsPackage.eINSTANCE, 
//                  () -> VacationsFactory.eINSTANCE.createVacations(),
//                  ".xmi",
//                  true);
//              vacationsResource.newEObject();
//              try {
//                vacationsResource.save(vacationsRegistry.getVacationsFileName());
//              } catch (IOException e1) {
//                e1.printStackTrace();
//              }
//              
//            }
//            
//            // Create the vacations folder if it doesn't exist
//            if (!vacationsFolder.exists()) {
//              Files.createDirectories(Paths.get(vacationsRegistry.getVacationsFolderName()));
//            }
//            // create the parent folder if it doesn't exist
//            String parent = checklistFile.getParent();
//            Files.createDirectories(Paths.get(parent));
//
//            // create the checklist file if it doesn't exist
//            if (!checklistFile.exists()) {
//              EMFResource<VacationChecklist> vacationChecklistResource = new EMFResource<>(
//                  VacationChecklistPackage.eINSTANCE, 
//                  () -> VacationChecklistFactory.eINSTANCE.createVacationChecklist(),
//                  ".xmi",
//                  true);
//              VacationChecklist vacationChecklist = vacationChecklistResource.newEObject();
//              VacationChecklistCategoriesList vacationChecklistCategoriesList = VacationChecklistFactory.eINSTANCE.createVacationChecklistCategoriesList();
//              vacationChecklist.setVacationChecklistCategoriesList(vacationChecklistCategoriesList);
//              VacationChecklistLabelsList vacationChecklistLabelsList = VacationChecklistFactory.eINSTANCE.createVacationChecklistLabelsList();
//              vacationChecklist.setVacationChecklistLabelsList(vacationChecklistLabelsList);
//              vacationChecklistResource.save(vacationsRegistry.getVacationChecklistFileName());
//            }
//            
//            returnValue = true; // required file and folders now exist, so we can continue.
//          } catch (IOException e) {
//            e.printStackTrace();
//          }
//          break;
//        }
//      }
//      
//    } else {
//      returnValue = true;
//    }
//
//    return returnValue;
//  }
  
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
    return new TravelsAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return vacationsRegistry;
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
