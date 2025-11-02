package goedegep.vacations.app.guifx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.util.emf.EMFResource;
import goedegep.vacations.app.TravelsService;
import goedegep.vacations.app.logic.VacationsRegistry;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;

/**
 * This class is used to launch the VacationsWindow.
 * <p>
 * It is checked whether required files/folders exist. If any of them is missing the user is asked whether he wants to edit
 * the User Settings, or to let the missing files/folder be created.
 * If the User Settings are edited, a restart of the application is needed (so the VacationsWindow is not started).
 */
public class VacationsLauncher {
  private static final Logger LOGGER = Logger.getLogger(VacationsLauncher.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private static VacationsRegistry vacationsRegistry = VacationsRegistry.getInstance();
  /**
   * Launch the VactionsWindow
   * 
   * @param customization the GUI customization.
   */
  public static void launchVacationsWindow(CustomizationFx customization) {
    LOGGER.info("=>");
    
    boolean vacationsInitializationOk = handleVacationsInitiolization(customization);
    
    if (vacationsInitializationOk) {
      new VacationsWindow(customization, TravelsService.getInstance());      
    }
        
    LOGGER.info("<=");
  }
  
  /**
   * If the vacations file doesn't exist and/or the vacations folder doesn't exist, ask the user whether they should be created, or whether the user wants to edit the User Settings.
   */
  private static boolean handleVacationsInitiolization(CustomizationFx customization) {
    boolean returnValue = false;
    
    File vacationsFile = new File(vacationsRegistry.getVacationsFileName());
    File vacationsFolder = new File(vacationsRegistry.getVacationsFolderName());
    File checklistFile = new File(vacationsRegistry.getVacationChecklistFileName());
    
    if (!vacationsFile.exists()  ||  !vacationsFolder.exists()  ||  !checklistFile.exists()) {
      StringBuilder buf = new StringBuilder();
      buf.append("The following files and/or folders don't exist yet:").append(NEWLINE);
      if (!vacationsFile.exists()) {
        buf.append("* The vacations file '").append(vacationsRegistry.getVacationsFileName()).append("'").append(NEWLINE);
      }
      if (!vacationsFolder.exists()) {
        buf.append("* The vacations folder '").append(vacationsRegistry.getVacationsFolderName()).append("'").append(NEWLINE);
      }
      if (!checklistFile.exists()) {
        buf.append("* The vacation checklist file '").append(vacationsRegistry.getVacationChecklistFileName()).append("'").append(NEWLINE);
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
          showPropertiesEditor(customization);
          break;
          
        case CREATE_MISSING_FILES_AND_OR_FOLDERS:
          try {
            // Create a vacations file if it doesn't exist
            if (!vacationsFile.exists()) {
              // create the parent folder if it doesn't exist
              String parent = vacationsFile.getParent();
              Files.createDirectories(Paths.get(parent));
              
              // create the file
              EMFResource<Vacations> vacationsResource = new EMFResource<>(
                  VacationsPackage.eINSTANCE, 
                  () -> VacationsFactory.eINSTANCE.createVacations(),
                  ".xmi",
                  true);
              vacationsResource.newEObject();
              try {
                vacationsResource.save(vacationsRegistry.getVacationsFileName());
              } catch (IOException e1) {
                e1.printStackTrace();
              }
              
            }
            
            // Create the vacations folder if it doesn't exist
            if (!vacationsFolder.exists()) {
              Files.createDirectories(Paths.get(vacationsRegistry.getVacationsFolderName()));
            }
            // create the parent folder if it doesn't exist
            String parent = checklistFile.getParent();
            Files.createDirectories(Paths.get(parent));

            // create the checklist file if it doesn't exist
            if (!checklistFile.exists()) {
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
              vacationChecklistResource.save(vacationsRegistry.getVacationChecklistFileName());
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
    new PropertiesEditor("Vacation user settings", customization, getBundle(VacationsWindow.class, "VacationsPropertyDescriptorsResource"),
        vacationsRegistry.getPropertyDescriptorsFileURI(), vacationsRegistry.getUserPropertiesFileName());
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
