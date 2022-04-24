package goedegep.appgen.swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.LookInfo;
import goedegep.configuration.model.ModuleLook;
import goedegep.util.emf.EMFResource;

/**
 * This class provides GUI customization information.
 * <p>
 * A customization consists of:
 * <ul>
 * <li>{@link Look} - specifying colors.</li>
 * <li>{@link AppResources} - providing icons.</li>
 * <li>{@link ComponentFactory} - a factory to create GUI components in the right Look.</li>
 * </ul>
 */
public class Customization {
  private static final Logger     LOGGER = Logger.getLogger(Customization.class.getName());

  // Look (colors, fonts, ..)
//  private Look look;
  
  // Resources (icons, ..)
  private AppResources appResources;
  
  // Factory to create components in the right look.
  private ComponentFactory componentFactory;

  /**
   * Create a Customization, where both the Look and the AppResources are specified.
   * 
   * @param look a {@link Look} to specify colors. This parameter may <b>not</b> be null.
   * @param appResources the {@link AppResources} for icons. This parameter may be null.
   */
  public Customization(AppResources appResources) {
//    if (look == null) {
//      throw new IllegalArgumentException("parameter look is null, which isn't allowed");
//    }
//    this.look = look;
    componentFactory = new ComponentFactory();
    this.appResources = appResources;
  }

  /**
   * Create a Customization, where only the Look is specified.
   * 
   * @param look a {@link Look} to specify colors. This parameter may <b>not</b> be null.
   */
  public Customization() {
    this(null);
  }
  
  /**
   * Set the AppResources of this Customization.
   * 
   * @param appResources the {@link AppResources} for icons. This parameter may be null.
   */
  public void setResources(AppResources appResources) {
    this.appResources = appResources;
  }

//  /**
//   * Get the Look of this Customization.
//   * 
//   * @return the Look of this Customization. This value can never be null.
//   */
//  public Look getLook() {
//    return look;
//  }
  
  /**
   * Get the AppResources of this Customization.
   * 
   * @return the AppResources of this Customization. This value can be null.
   */
  public AppResources getResources() {
    return appResources;
  }

  /**
   * Get the ComponentFactory for this Customization.
   * 
   * @return the ComponentFactory for this Customization. This value can never be null.
   */
  public ComponentFactory getComponentFactory() {
    return componentFactory;
  }
  
  /**
   * Get a String representation for this Customization.
   * The format of the String is: "Look: <look>, AppResources: <appResources>".
   * 
   * @return a String representation of this Customization.
   */
  public String toString() {
    return "AppResources: " + appResources.toString();
  }
  
  @SuppressWarnings("deprecation")  // Warning suppressed as this is legacy code.
  public static Customization createCustomization(ModuleLook moduleLook) {
    Customization customization = null;
    String resourcesClassName = moduleLook.getResourcesClassName();
    LOGGER.fine("resourcesClassName: " + resourcesClassName);
    Class<?> resourceClass;
    
    try {
      resourceClass = Class.forName(resourcesClassName);
      LOGGER.info("resourceClass: " + resourceClass.getName());
      AppResources appResources = (AppResources) resourceClass.newInstance();
      customization = new Customization(appResources);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    
    return customization;    
  }
  
  /**
   * Create a {@link Customization} from a configuration file.
   * <p>
   * This method should only be used in special situations. Instead use {@link Customizations#addCustomizations(File configurationFile)}.
   *  
   * @param configurationFileName name of the configuration file.
   * @return The Customization created from the configuration file.
   */
  public static Customization createCustomization(String configurationFileName) {
    EMFResource<LookInfo> emfResource = new EMFResource<>(
        ConfigurationPackage.eINSTANCE,
        () -> ConfigurationFactory.eINSTANCE.createLookInfo());
    LookInfo lookInfo;
    try {
      lookInfo = emfResource.load(configurationFileName);
      ModuleLook moduleLook = lookInfo.getModuleLooks().get(0);
      
      return createCustomization(moduleLook);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(-1);
      return null;
    }
//    ConfigurationResource configurationResource = new ConfigurationResource(new File(configurationFileName));
//    LookInfo lookInfo = configurationResource.getLookInfo();
  }
}
