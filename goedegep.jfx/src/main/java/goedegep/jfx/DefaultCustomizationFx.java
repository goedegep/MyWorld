package goedegep.jfx;

import java.util.logging.Logger;

public class DefaultCustomizationFx {
  private static final Logger LOGGER = Logger.getLogger(DefaultCustomizationFx.class.getName());
  private static CustomizationFx customization = null;
  
  public static CustomizationFx getInstance() {
    if (customization == null) {
      customization = new CustomizationFx(null, DefaultAppResourcesFx.getInstance());
    }
    
    LOGGER.info("<= " + customization);
    return customization;
  }
}
