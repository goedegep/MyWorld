package goedegep.events.svc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import goedegep.configuration.model.Look;
import goedegep.events.gui.EventEditor;
import goedegep.events.gui.EventsAppResources;
import goedegep.events.gui.EventsWindow;
import goedegep.events.logic.EventsRegistry;
import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.events.model.EventsFactory;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.JfxApplication;
import goedegep.jfx.editor.Editor;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.myworld.common.UserChoice;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EMFResource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

/**
 * This class is the main class of the Events application.
 */
public class EventsService extends Service {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EventsService.class.getName());
  public static final String EVENTS_APPLICATION_PROPERTIES_FILE_NAME = "EventsApplication.properties";
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  /**
   * The singleton instance of the EventsService.
   */
  private static EventsService instance = null;
  
  /**
   * The EventsRegistry
   */
  private EventsRegistry eventsRegistry;
  
  /**
   * the {@link EMFResource} for loading and storing the events database.
   */
  private EMFResource<Events> eventsResource;
    
  /**
   * Get the singleton instance of the EventsService.
   * 
   * @return the singleton instance of EventsService.
   */
  public static EventsService getInstance() {
    if (instance == null) {
      instance = new EventsService();
      instance.initialize();
    }

    return instance;
  }
  
  /**
   * Show the EventsWindow
   */
  public void showEventsWindow() {
    if (!checkThatEventsFileNameIsSet()) {
      return;
    }
    
    boolean eventsInitializationOk = checkThatEventsFileAndFolderExist();
    
    if (!eventsInitializationOk) {
      return;
    }
    
    new EventsWindow(customization, this);      
  }
  
  /**
   * Launch an {@link EventsEditor}.
   * 
   * @param event the {@code EventInfo} to be edited.
   */
  public void LaunchEventsEditor(EventInfo event) {
    boolean eventsInitializationOk = checkThatEventsFileAndFolderExist();
    
    if (!eventsInitializationOk) {
      return;
    }

    Editor<EventInfo> eventEditor = EventEditor.newInstance(customization, this);
    if (event != null) {
      eventEditor.setObject(event);
    }
    eventEditor.show();
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
  
  /**
   * Get the events resource.
   * 
   * @return the {@link EMFResource} for loading and storing the events database.
   */
  public EMFResource<Events> getEventsResource() {
    if (eventsResource == null) {
      eventsResource = createEventsResource();
    }
    
    return eventsResource;
  }
  
  /**
   * Add an event to the events
   * <p>
   * The events are kept ordered by date.
   */
  public void addEvent(EventInfo eventInfo) {
    Events events = eventsResource.getEObject();
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
  public String determineEventFolderName(EventInfo event) {
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
  public String deriveEventFolderNameFromAttachments(EventInfo event) {
    if (eventsRegistry.getEventsFolderName() == null) {
      return null;
    }
    
    for (FileReference fileReference: event.getAttachments()) {      
      String fileName = fileReference.getFile();
      if (fileName == null) {
        continue;
      }
      
      File file = new File(eventsRegistry.getEventsFolderName(), fileName);
      if (file != null ) {
        file = file.getParentFile();  // This is the possible event directory
        if (file != null) {
          File eventsFolder = file.getParentFile();
          String eventsFolderName = eventsFolder.getAbsolutePath();
          if (eventsFolderName != null  &&  eventsFolderName.equals(eventsRegistry.getEventsFolderName())) {
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
  public String deriveEventFolderNameFromDateAndTitle(EventInfo event) {
    if (eventsRegistry.getEventsFolderName() == null) {
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
    File eventFolder = new File(eventsRegistry.getEventsFolderName(), subFolderName);
    
    return eventFolder.getAbsolutePath();
  }

  /**
   * Constructor.
   * 
   * @param eventsResource the {@link EMFResource} for loading and storing the events database.
   */
  private EventsService() {
    eventsRegistry = EventsRegistry.getInstance();
  }
  
  /**
   * Check that the events file name is set in the settings.
   */
  private boolean checkThatEventsFileNameIsSet() {
    
    if (eventsRegistry.getEventsFileName() == null) {
      Alert alert = customization.getComponentFactoryFx().createErrorDialog(
          "There's no filename configured for the file with the information about the events",
          "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application." +
              NEWLINE +
              "A restart is needed, because the settings are only read at startup.");
      
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor();
        }
      });
      
      return false;
    }
    
    return true;
  }
  
  /**
   * Check that the events file and events folder exist and are available via the settings.
   * <p>
   * If the events file doesn't exist and/or the events folder doesn't exist,
   * ask the user whether they should be created, or whether the user wants to edit the User Settings.
   */
  private boolean checkThatEventsFileAndFolderExist() {
    boolean returnValue = false;
    
    File eventsFile = new File(eventsRegistry.getEventsFileName());
    File eventsFolder = new File(eventsRegistry.getEventsFolderName());
    
    if (!eventsFile.exists()  ||  !eventsFolder.exists()) {
      StringBuilder buf = new StringBuilder();
      buf.append("The following files and/or folders don't exist yet:").append(NEWLINE);
      if (!eventsFile.exists()) {
        buf.append("* The events file '").append(eventsRegistry.getEventsFileName()).append("'").append(NEWLINE);
      }
      if (!eventsFolder.exists()) {
        buf.append("* The events folder '").append(eventsRegistry.getEventsFolderName()).append("'").append(NEWLINE);
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
          showPropertiesEditor();
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
                eventsResource.save(eventsRegistry.getEventsFileName());
              } catch (IOException e1) {
                e1.printStackTrace();
              }
              
            }
            
            // Create the events folder if it doesn't exist
            if (!eventsFolder.exists()) {
              Files.createDirectories(Paths.get(eventsRegistry.getEventsFolderName()));
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
   * @return the Events resource, or null if this couldn't be created.
   */
  private EMFResource<Events> createEventsResource() {
    boolean creationOK = false;

    EMFResource<Events> eventsResource = new EMFResource<>(EventsPackage.eINSTANCE, () -> EventsFactory.eINSTANCE.createEvents(), ".xmi", true);

    try {
      eventsResource.load(eventsRegistry.getEventsFileName());
      creationOK = true;
    } catch (IOException e) {
    }

    return creationOK ? eventsResource : null;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("EventsApplication.properties")) {
        props.load(in);
        
        eventsRegistry.setVersion(props.getProperty("events.version"));
        eventsRegistry.setApplicationName(props.getProperty("events.name"));
        eventsRegistry.setAuthor(props.getProperty("events.author"));
        eventsRegistry.setCopyrightMessage(props.getProperty("events.copyright"));
        eventsRegistry.setShortProductInfo(props.getProperty("events.description"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
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
    return new EventsAppResources();
  }
  
  @Override
  protected Registry getRegistry() {
    return eventsRegistry;
  }
}
