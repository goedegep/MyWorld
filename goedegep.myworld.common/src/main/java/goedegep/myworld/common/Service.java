package goedegep.myworld.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.properties.app.guifx.PropertyDescriptorsEditorFx;
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.PropertyGroup;
import goedegep.util.RunningInEclipse;
import goedegep.util.emf.EMFResource;

/**
 * This is the abstract base class for all MyWorld services.
 * <p>
 * It provides the basic functionality to initialize the service, read application properties, create customization and handle user properties.
 */
public abstract class Service implements IService {
  protected CustomizationFx customization;
  
  
  /**
   * Show the User Properties editor, using a {@link ResourceBundle}.
   */
  @Override
  public void showPropertiesEditor(ResourceBundle resourceBundle) {
    String applicationName = getRegistry().getApplicationName();
    new PropertiesEditor(applicationName + " user settings", customization, resourceBundle,
        getRegistry().getPropertyDescriptorsFileURI(), getUserSettingsFilePath());    
  }
  
  @Override
  public void showPropertiesEditor() {
    showPropertiesEditor(null);    
  }
  
  @Override
  public void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, getRegistry().getPropertyDescriptorsFileURI());
  }
  
  /**
   * Initialize the service.
   * <p>
   * This method sets the development mode, reads application properties, creates customization and handles user properties.
   * Every subclass must call this method during its initialization.
   */
  protected void initialize() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    setDevelopmentMode(RunningInEclipse.runningInEclipse());
    
    // Read application properties. These are typically stored in a properties file, named <application-name>Application.properties, within the application jar.
    // These are properties which are used by maven and the application.
    readApplicationProperties();
    
    // Create customization
    createCustomization();
    
    // Handle the user specific properties.
    handleUserProperties();
  }

  /**
   * Set the development mode of the application.
   * <p>
   * The application can use this information to add functionality which is for development only.
   * 
   * @param developmentMode if true, the application is in development mode.
   */
  protected void setDevelopmentMode(boolean developmentMode) {
    getRegistry().setDevelopmentMode(developmentMode);
  }

  /**
   * Read the application specific properties, which are read from a properties file.
   */
  protected abstract void readApplicationProperties();
  
  /**
   * Create the GUI customization.
   * <p>
   * The customization object holds the look and resources for the application.
   */
  protected void createCustomization() {
    ConfigurationFactory configurationFactory = ConfigurationFactory.eINSTANCE;
    Look look = configurationFactory.createLook();
    fillLook(look);
    AppResourcesFx resources = getAppResourcesFxClass();
    customization = new CustomizationFx(look, resources);
  }

  /**
   * Fill the look object with application specific look information.
   * 
   * @param look the look object to fill.
   */
  protected abstract void fillLook(Look look);
  
  /**
   * Get the application resources class.
   * 
   * @return the application resources class.
   */
  protected abstract AppResourcesFx getAppResourcesFxClass();
  
  /**
   * Handle the user properties.
   * <p>
   * If the user properties file exists, the properties are read and stored in the registry.
   */
  protected void handleUserProperties() {
    Registry registry = getRegistry();

    Path userPropertiesFilePath = getUserSettingsFilePath();

    if (userPropertiesFilePath == null  ||  !Files.exists(userPropertiesFilePath)) {
      return;
    }

    EMFResource<PropertyGroup> propertiesResource = new EMFResource<PropertyGroup>(
        PropertiesPackage.eINSTANCE,
        () -> PropertiesFactory.eINSTANCE.createPropertyGroup(), ".xmi");

    try {
      URI uri = userPropertiesFilePath.toUri();
      PropertyGroup propertyGroup = propertiesResource.load(uri);
      propertyGroup.getProperties().forEach(p -> {
        boolean known = registry.setValue(p.getName(), p.getValue());
        if (!known) {
          throw new RuntimeException("Unknown property name: " + p.getName() + " in user properties file: " + userPropertiesFilePath.toString());
        }
      });
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the application registry.
   * 
   * @return the application registry.
   */
  protected abstract Registry getRegistry();
  
  /**
   * Get the Path for the file with user settings.
   * <p>
   * The user settings file is located in [user-home]/MyWorld/[application-name]/[user-settings-file-name]
   * Where:<br/>
   * [user-home] is the users home directory.<br/>
   * [application-name] is the name of the application.<br/>
   * [user-settings-file-name] is the name of the user settings file.
   * 
   *  @return the Path for the file with user settings, or null if the user properties file name isn't set in the registry.
   */
  @Override
  public Path getUserSettingsFilePath() {
    String userHomeDir = System.getProperty("user.home");
    String applicationName = getRegistry().getApplicationName();
    String userSettingsFileName = getRegistry().getUserPropertiesFileName();
    if (userSettingsFileName == null) {
      return null;
    }
    
    Path userSettingsFilePath = Paths.get(userHomeDir, "MyWorld", applicationName, userSettingsFileName);

    return userSettingsFilePath;
  }
}
