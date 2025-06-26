package goedegep.appgen.swing;

import java.util.logging.Logger;

/**
 * This class provides GUI customization information.
 * <p>
 * A customization consists of:
 * <ul>
 * <li>{@link AppResources} - providing icons.</li>
 * <li>{@link ComponentFactory} - a factory to create GUI components in the right Look.</li>
 * </ul>
 * All Swing code is considered to be legacy code. To make life easier, there's no {@code Look} anymore in the {@code Customization}.
 */
public class Customization {
  @SuppressWarnings("unused")
  private static final Logger     LOGGER = Logger.getLogger(Customization.class.getName());

  // Resources (icons, ..)
  private AppResources appResources;
  
  // Factory to create components in the right look.
  private ComponentFactory componentFactory;

  /**
   * Create a Customization, where the {@code AppResources} are specified.
   * 
   * @param appResources the {@link AppResources} for icons. This parameter may be null.
   */
  public Customization(AppResources appResources) {
    componentFactory = new ComponentFactory();
    this.appResources = appResources;
  }

  /**
   * Create a Customization, where there are no {@code AppResources} specified.
   */
  public Customization() {
    this(null);
  }
  
  /**
   * Set the {@code AppResources} of this Customization.
   * 
   * @param appResources the {@link AppResources} for icons. This parameter may be null.
   */
  public void setResources(AppResources appResources) {
    this.appResources = appResources;
  }
  
  /**
   * Get the {@code AppResources} of this Customization.
   * 
   * @return the AppResources of this Customization. This value can be null.
   */
  public AppResources getResources() {
    return appResources;
  }

  /**
   * Get the {@code ComponentFactory} for this {@code Customization}.
   * 
   * @return the {@code ComponentFactory} for this {@code Customization}. This value can never be null.
   */
  public ComponentFactory getComponentFactory() {
    return componentFactory;
  }
  
  /**
   * Get a String representation for this {@code Customization}.
   * The format of the String is: AppResources: <appResources>".
   * 
   * @return a string representation of this {@code Customization}.
   */
  public String toString() {
    return "AppResources: " + appResources.toString();
  }
    
}
