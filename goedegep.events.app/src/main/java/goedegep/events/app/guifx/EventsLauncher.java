package goedegep.events.app.guifx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import goedegep.events.app.EventsRegistry;
import goedegep.events.model.Events;
import goedegep.events.model.EventsFactory;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.util.emf.EMFResource;

/**
 * This class is used to launch the EventsWindow.
 * <p>
 * It is checked whether required files/folders exist. If any of them is missing the user is asked whether he wants to edit
 * the User Settings, or to let the missing files/folder be created.
 * If the User Settings are edited, a restart of the application is needed (so the EventsWindow is not started).
 */
public class EventsLauncher {
  private static final Logger LOGGER = Logger.getLogger(EventsLauncher.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");


  /**
   * Launch the EventsWindow
   * 
   * @param customization the GUI customization.
   */
  public static void launchEventsWindow(CustomizationFx customization) {
    LOGGER.info("=>");
    
    boolean eventsInitializationOk = handleEventsInitialization(customization);
    
    if (eventsInitializationOk) {
      new EventsWindow(customization);      
    }
        
    LOGGER.info("<=");
  }
  
  /**
   * If the events file doesn't exist and/or the events folder doesn't exist, ask the user whether they should be created, or whether the user wants to edit the User Settings.
   */
  private static boolean handleEventsInitialization(CustomizationFx customization) {
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
  
  /**
   * Show the User Properties editor.
   */
  private static void showPropertiesEditor(CustomizationFx customization) {
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

}
