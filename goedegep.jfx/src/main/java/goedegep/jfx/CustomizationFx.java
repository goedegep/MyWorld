package goedegep.jfx;

import java.util.logging.Logger;

import goedegep.appgen.swing.AppResources;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.ModuleLook;

/**
 * This class provides GUI customization information.
 * <p>
 * A customization consists of:
 * <ul>
 * <li>{@link Look} - specifying colors.</li>
 * <li>{@link AppResourcesFx} - providing icons.</li>
 * <li>{@link ComponentFactoryFx} - a factory to create GUI components in the right Look.</li>
 * </ul>
 */
public class CustomizationFx {
  private final static String NEWLINE = System.getProperty("line.separator");
  private static final Logger LOGGER = Logger.getLogger(CustomizationFx.class.getName());

  // Look (colors, fonts, ..)
  private Look look;
  
  // Resources (icons, ..)
  private AppResourcesFx appResources;
  
  // Factory to create components in the right look.
  private ComponentFactoryFx componentFactoryFx;

  /**
   * Create a Customization, where both the Look and the AppResources are specified.
   * 
   * @param look a {@link Look} to specify colors. This parameter may be null.
   * @param appResources the {@link AppResourcesFx} for icons. This parameter may be null.
   */
  public CustomizationFx(Look look, AppResourcesFx appResources) {
    this.look = look;
    componentFactoryFx = new ComponentFactoryFx(look, appResources);
    this.appResources = appResources;
  }

  /**
   * Set the AppResources of this Customization.
   * 
   * @param appResources the {@link AppResources} for icons. This parameter may be null.
   */
  public void setResources(AppResourcesFx appResources) {
    this.appResources = appResources;
  }

  /**
   * Get the Look of this Customization.
   * 
   * @return the Look of this Customization. This value can never be null.
   */
  public Look getLook() {
    return look;
  }
  
  /**
   * Get the AppResourcesFx of this Customization.
   * 
   * @return the AppResourcesFx of this Customization. This value can be null.
   */
  public AppResourcesFx getResources() {
    return appResources;
  }

  /**
   * Get the ComponentFactoryFx for this Customization.
   * 
   * @return the ComponentFactoryFx for this Customization. This value can never be null.
   */
  public ComponentFactoryFx getComponentFactoryFx() {
    return componentFactoryFx;
  }
  
  /**
   * Get a String representation for this Customization.
   * The format of the String is: "Look: <look>, AppResources: <appResources>".
   * 
   * @return a String representation of this Customization.
   */
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("CustomizationFx:").append(NEWLINE);
    buf.append("look:").append(NEWLINE).append(look != null ? look.toString() : "<null>");
    buf.append("appResources:").append(NEWLINE).append(appResources.toString());
    
    return buf.toString();
  }
  
  public static CustomizationFx createCustomization(ModuleLook moduleLook) {
    CustomizationFx customization = null;
    Look look = moduleLook.getLook();
    String resourcesClassName = moduleLook.getResourcesClassName() + "Fx";
    LOGGER.fine("resourcesClassName: " + resourcesClassName);
    Class<?> resourceClass;
    
    try {
      resourceClass = Class.forName(resourcesClassName);
      LOGGER.info("resourceClass: " + resourceClass.getName());
      AppResourcesFx appResources = (AppResourcesFx) resourceClass.newInstance();
      customization = new CustomizationFx(look, appResources);
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
  
}