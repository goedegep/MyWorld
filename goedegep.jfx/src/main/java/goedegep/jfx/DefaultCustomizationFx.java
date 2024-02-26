package goedegep.jfx;

/**
 * This class provides a default for the GUI customization ({@link CustomizationFx}).
 */
public class DefaultCustomizationFx {
  private static CustomizationFx customization = null;
  
  /**
   * Private constructor as this is a singleton.
   */
  private DefaultCustomizationFx() {
    
  }
  
  /**
   * Get the default customization instance.
   * 
   * @return the default customization instance.
   */
  public static CustomizationFx getInstance() {
    if (customization == null) {
      customization = new CustomizationFx(null, DefaultAppResourcesFx.getInstance());
    }
    
    return customization;
  }
}
