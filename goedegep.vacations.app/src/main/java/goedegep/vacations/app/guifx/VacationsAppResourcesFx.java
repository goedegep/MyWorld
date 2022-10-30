package goedegep.vacations.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class VacationsAppResourcesFx extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[7];
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("Snow mountain 16x16.png"));
      applicationImages[1] = new Image(getClass().getResourceAsStream("Snow mountain 32x32.png"));
      applicationImages[2] = new Image(getClass().getResourceAsStream("Snow mountain 48x48.png"));
      applicationImages[3] = new Image(getClass().getResourceAsStream("Snow mountain 64x64.png"));
      applicationImages[4] = new Image(getClass().getResourceAsStream("Snow mountain 128x128.png"));
      applicationImages[5] = new Image(getClass().getResourceAsStream("Snow mountain 256x256.png"));
      applicationImages[6] = new Image(getClass().getResourceAsStream("Snow mountain 512x512.png"));

    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
  }
  
}
