package goedegep.demo.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import goedegep.jfx.AppResourcesFx;
import goedegep.resources.ImageResource;
import javafx.scene.image.Image;

/**
 * Resources for the Demo application.
 */
public class DemoAppResources extends AbstractAppResourcesFx {
  private static DemoAppResources demoAppResources = null;
  
  public static AppResourcesFx getInstance() {
    if (demoAppResources == null) {
      demoAppResources = new DemoAppResources();
    }
    
    return demoAppResources;
  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    
    applicationImages[0] = ImageResource.DEMO.getImage();
    
    setRawImages(null, null, null, applicationImages);
  }
  
}
