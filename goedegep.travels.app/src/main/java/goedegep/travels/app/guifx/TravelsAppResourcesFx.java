package goedegep.travels.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import goedegep.resources.ImageResource;
import javafx.scene.image.Image;

public class TravelsAppResourcesFx extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = ImageResource.ROAD_TO_HORIZON.getImages();
    
    setRawImages(null, null, null, applicationImages);
  }
  
}
