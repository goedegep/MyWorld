package goedegep.events.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import goedegep.events.app.guifx.EventEditor;
import goedegep.events.app.guifx.EventsWindow;
import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.events.model.EventsFactory;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.jfx.editor.Editor;
import goedegep.properties.app.PropertiesHandler;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.RunningInEclipse;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EMFResource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * This class is the main class of the Events application.
 */
public class EventsService {
  private static final Logger LOGGER = Logger.getLogger(EventsService.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private static final String EVENTS_CONFIGURATION_FILE = "EventsConfiguration.xmi";
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private CustomizationFx customization = null;
  private static EventsService instance = null;
  
  /**
   * the {@link EMFResource} for loading and storing the events database.
   */
  private EMFResource<Events> eventsResource;
  
  /**
   * The events
   */
  private Events events;
  
  public static EventsService getInstance() {
    if (instance == null) {
      instance = new EventsService();
    }
    
    return instance;
  }
  
  /**
   * Show the EventsWindow
   */
  public void showEventsWindow() {
    LOGGER.info("=>");
    
    boolean eventsInitializationOk = handleEventsInitialization(customization);
    
    if (!eventsInitializationOk) {
      return;
    }
    
    new EventsWindow(customization, this);      
        
    LOGGER.info("<=");
  }
  
  /**
   * Launch an {@link EventsEditor}.
   * 
   * @param event the {@code EventInfo} to be edited.
   */
  public void LaunchEventsEditor(EventInfo event) {
    Editor<EventInfo> eventEditor = EventEditor.newInstance(customization, this);
    if (event != null) {
      eventEditor.setObject(event);
    }
    eventEditor.show();
  }
  
  /**
   * Show the User Properties editor.
   */
  public void showPropertiesEditor(CustomizationFx customization) {
    new PropertiesEditor("Events properties", customization, null,
        EventsRegistry.propertyDescriptorsResource, EventsRegistry.customPropertiesFile);
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
      LOGGER.info("bundlePath: " + bundlePath);
      Locale locale = Locale.getDefault();
      ClassLoader classLoader = clazz.getClassLoader();
      LOGGER.info("classLoader: " + classLoader.getName());
      return ResourceBundle.getBundle(bundlePath, locale, classLoader);
  }

  /**
   * Get the events resource.
   * 
   * @return the {@link EMFResource} for loading and storing the events database.
   */
  public EMFResource<Events> getEventsResource() {
    return eventsResource;
  }
  
  /**
   * Add an event to the events
   * <p>
   * The events are kept ordered by date.
   */
  public void addEvent(EventInfo eventInfo) {
    List<EventInfo> eventList = events.getEvents();
    
    FlexDate newEventDate = eventInfo.getDate();
    boolean eventInserted = false;
    for (int index = 0; index < eventList.size(); index++) {
      EventInfo event = eventList.get(index);
      if (newEventDate.compareTo(event.getDate()) == 1) {
        eventList.add(index, eventInfo);
        eventInserted = true;
        break;
      }
    }
    if (!eventInserted) {
      eventList.add(eventInfo);
    }
  }


  /**
   * Save the events information to the related file.
   */
  public Result saveEvents() {
    Result result = new Result();
    
    try {
      eventsResource.save();
      result.setResultType(ResultType.OK);
      result.setMessage("Events saved to '" + eventsResource.getFileName() + "'");
    } catch (IOException e) {
      result.setResultType(ResultType.FAILED);
      result.setMessage("System error message: "  + e.getMessage());
    }
    
    return result;
  }

  
  /**
   * Get the name of the folder where the attachments of an event are stored.
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   * 
   * @param event the {@code EventInfo} for which the folder name is requested.
   * @return the name of the folder where the attachments of a {@code event} are stored, or null if this cannot be determined.
   */
  public static String determineEventFolderName(EventInfo event) {
    String eventFolderName = deriveEventFolderNameFromAttachments(event);
    
    if (eventFolderName == null) {
      eventFolderName = deriveEventFolderNameFromDateAndTitle(event);
    }
    
    return eventFolderName;
  }
  
  /**
   * Get the event folder name derived from the attachments.
   * <p>
   * The event folder is the folder of the first attachment, which is a sub folder of the Events Folder.
   * 
   * @param event the {@code EventInfo} for which the folder name is requested.
   * @return the event folder name derived from the attachments, or null if this cannot be determined.
   */
  public static String deriveEventFolderNameFromAttachments(EventInfo event) {
    if (EventsRegistry.eventsFolderName == null) {
      return null;
    }
    
    for (FileReference fileReference: event.getAttachments()) {      
      String fileName = fileReference.getFile();
      if (fileName == null) {
        continue;
      }
      
      File file = new File(EventsRegistry.eventsFolderName, fileName);
      if (file != null ) {
        file = file.getParentFile();  // This is the possible event directory
        if (file != null) {
          File eventsFolder = file.getParentFile();
          String eventsFolderName = eventsFolder.getAbsolutePath();
          if (eventsFolderName != null  &&  eventsFolderName.equals(EventsRegistry.eventsFolderName)) {
            String eventFolderName = file.getAbsolutePath();
            return eventFolderName;
          }
        }
      }
    }
    
    return null;
  }
  
  /**
   * Derive the event folder from the date and title controls.
   * 
   * @param event the {@code EventInfo} for which the folder name is requested.
   * @return the event folder derived from the date and title controls, or null if these controls don't have the needed information.
   */
  public static String deriveEventFolderNameFromDateAndTitle(EventInfo event) {
    if (EventsRegistry.eventsFolderName == null) {
      return null;
    }
    
    FlexDate eventDate = event.getDate();
    if (eventDate == null) {
      return null;
    }
    
    String eventDateText = FDF.format(eventDate);
    
    String title = event.getTitle();
    if (title == null) {
      return null;
    }
    
    String subFolderName = eventDateText + " " + title;
    File eventFolder = new File(EventsRegistry.eventsFolderName, subFolderName);
    
    return eventFolder.getAbsolutePath();
  }

  /**
   * Constructor.
   * 
   * @param eventsResource the {@link EMFResource} for loading and storing the events database.
   */
  private EventsService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      EventsRegistry.developmentMode = true;
    }
    
    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(EventsRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

      // Read the customization info.
      url = getClass().getResource(EVENTS_CONFIGURATION_FILE);
      customization = CustomizationsFx.readCustomization(url);
    } catch (IOException e) {
      JfxApplication.reportException(null, e);
    }
    
    eventsResource = getEventsResource(customization);
    if (eventsResource == null) {
      return;
    }
    
    events = eventsResource.getEObject();
  }  
  
  /**
   * If the events file doesn't exist and/or the events folder doesn't exist, ask the user whether they should be created, or whether the user wants to edit the User Settings.
   */
  private boolean handleEventsInitialization(CustomizationFx customization) {
    boolean returnValue = false;
    
    File eventsFile = new File(EventsRegistry.eventsFileName);
    File eventsFolder = new File(EventsRegistry.eventsFolderName);
    
    if (!eventsFile.exists()  ||  !eventsFile.exists()) {
      StringBuilder buf = new StringBuilder();
      buf.append("The following files and/or folders don't exist yet:").append(NEWLINE);
      if (!eventsFile.exists()) {
        buf.append("* The events file '").append(EventsRegistry.eventsFileName).append("'").append(NEWLINE);
      }
      if (!eventsFolder.exists()) {
        buf.append("* The events folder '").append(EventsRegistry.eventsFolderName).append("'").append(NEWLINE);
      }
      buf.append("""
          If you are just starting to use this application, you may want to edit the User Settings, to set the file and folder names to your preference.
          In this case you have to restart the application after saving the changes.
          Otherwise you can let the file and/or folder be created for you.
          """);
      ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
      Optional<UserChoice> optionalUserChoice = componentFactory.createChoiceDialog("How to continue?", buf.toString(), "what to do?", UserChoice.SHOW_SETTINGS_EDITOR, UserChoice.values()).showAndWait();
      if (optionalUserChoice.isPresent()) {
        UserChoice userChoice = optionalUserChoice.get();
        switch (userChoice) {
        case SHOW_SETTINGS_EDITOR:
          returnValue = false; // If the user settings are changed, a restart of the application is needed
          showPropertiesEditor(customization);
          break;
          
        case CREATE_MISSING_FILES_AND_OR_FOLDERS:
          try {
            // Create a events file if it doesn't exist
            if (!eventsFile.exists()) {
              // create the parent folder if it doesn't exist
              String parent = eventsFile.getParent();
              Files.createDirectories(Paths.get(parent));
              
              // create the file
              EMFResource<Events> eventsResource = new EMFResource<>(
                  EventsPackage.eINSTANCE, 
                  () -> EventsFactory.eINSTANCE.createEvents(),
                  ".xmi",
                  true);
              eventsResource.newEObject();
              try {
                eventsResource.save(EventsRegistry.eventsFileName);
              } catch (IOException e1) {
                e1.printStackTrace();
              }
              
            }
            
            // Create the events folder if it doesn't exist
            if (!eventsFolder.exists()) {
              Files.createDirectories(Paths.get(EventsRegistry.eventsFolderName));
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
   * Try to get (load) the Events resource.
   * <p>
   * The events database is loaded from the file specified in the registry.<br/>
   * If the file doesn't exist, there are two options; either the file name in the registry is incorrect, or the file hasn't been created yet.
   * Therefore a dialog is shown asking the user whether this file shall be created or not. In the latter case the user has to correct the file name in the registry.
   * 
   * @return true if the resource could be opened, false otherwise.
   */
  private EMFResource<Events> getEventsResource(CustomizationFx customization) {
    boolean returnValue = false;

    EMFResource<Events> eventsResource = new EMFResource<>(EventsPackage.eINSTANCE, () -> EventsFactory.eINSTANCE.createEvents(), ".xmi", true);

    try {
      eventsResource.load(EventsRegistry.eventsFileName);
      returnValue = true;
    } catch (IOException e) {
      Alert alert = customization.getComponentFactoryFx().createYesNoConfirmationDialog(
          null,
          "The file with event information (" + EventsRegistry.eventsFileName + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" the events application will not be started.");
      Optional<ButtonType> response = alert.showAndWait();
      if (response.isPresent()  &&  response.get() == ButtonType.YES) {
        eventsResource.newEObject();
        try {
          eventsResource.save(EventsRegistry.eventsFileName);
          returnValue = true;
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }

    return returnValue ? eventsResource : null;
  }
}


/**
 * Possible user choices.
 */
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
