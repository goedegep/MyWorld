package goedegep.applicationcomponent;

/**
 * An ApplicationComponent is the access point for application components.
 * 
 * Typical structure:
 * 
 * goedegep.acomponent.app
 *    AComponent extends ApplicationComponent
 *      AComponent getInstance()
 *        Creates the instance if not done yet.
 *        Creating the instance:
 *          read the property descriptors file (which by definition is part of the project) using hard-coded file name.
 *          read the user properties file if it exists. For a normal installation this will be under home.dir/MyWorld. When running in eclipse this may be part of the project.
 *               This because for testing in eclipse you may need the actual user settings.
 *          read the customization (which are by definition part of the project) using hard-coded file name.
 *          
 * goedegep.acomponent.logic
 *   Any non gui stuff
 *   
 * goedegep.acomponent.gui
 *   Any gui related stuff
 *          
 * @param T the component ??
 */
public abstract class ApplicationComponent<T> {
  public abstract T getInstance();
}
