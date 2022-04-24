package goedegep.pctools.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class PCToolsAppResourcesFx extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[4];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("Computer 64x64.png"));
      applicationImages[1] = new Image(getClass().getResourceAsStream("Computer 128x128.png"));
      applicationImages[2] = new Image(getClass().getResourceAsStream("Computer 256x256.png"));
      applicationImages[3] = new Image(getClass().getResourceAsStream("computer 512x512.png"));
      
      picture = new Image(getClass().getResourceAsStream("Acer Aspire M7721.png"));
      
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
  
}
