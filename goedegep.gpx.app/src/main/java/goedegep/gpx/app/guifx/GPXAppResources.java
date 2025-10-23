package goedegep.gpx.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

public class GPXAppResources extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[4];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageResource.GPX.getImage(ImageSize.SIZE_0);
      applicationImages[1] = ImageResource.GPX.getImage(ImageSize.SIZE_0);
      applicationImages[2] = ImageResource.GPX.getImage(ImageSize.SIZE_0);
      applicationImages[3] = ImageResource.GPX.getImage();
      
      picture = applicationImages[3];
      
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
  
}
