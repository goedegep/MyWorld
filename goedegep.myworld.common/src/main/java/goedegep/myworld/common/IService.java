package goedegep.myworld.common;

import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * This is the common interface for all MyWorld services.
 */
public interface IService {

  /**
   * Show the User Properties editor, using a {@link ResourceBundle}.
   */
  void showPropertiesEditor(ResourceBundle resourceBundle);

  /**
   * Show the User Properties editor, without using a {@link ResourceBundle}.
   */
  void showPropertiesEditor();
  
  /**
   * Open an editor to edit the property descriptors (to be used in development mode only).
   */
  void showPropertyDescriptorsEditor();

  /**
   * Get the Path for the file with user settings.
   * <p>
   * The user settings file is located in [user-home]/MyWorld/[application-name]/[user-settings-file-name]
   * Where:<br/>
   * [user-home] is the users home directory.<br/>
   * [application-name] is the name of the application.<br/>
   * [user-settings-file-name] is the name of the user settings file.
   * 
   *  @return the Path for the file with user settings.
   */
  Path getUserSettingsFilePath();

}