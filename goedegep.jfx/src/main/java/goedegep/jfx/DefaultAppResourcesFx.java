package goedegep.jfx;


import java.util.logging.Logger;

import javafx.scene.image.Image;

public class DefaultAppResourcesFx extends AbstractAppResourcesFx {
  private static final Logger LOGGER = Logger.getLogger(DefaultAppResourcesFx.class.getName());
  private static DefaultAppResourcesFx defaultAppResources = null;
  
  public static AppResourcesFx getInstance() {
    if (defaultAppResources == null) {
      defaultAppResources = new DefaultAppResourcesFx();
    }
    
    LOGGER.info("<= " + defaultAppResources);
    return defaultAppResources;
  }
  
  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(goedegep.jfx.DefaultAppResourcesFx.class.getResourceAsStream("java_icon.gif"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
  
  @Override
  public String toString() {
    return super.toString();
  }
}
