package goedegep.unitconverter.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class UnitConverterAppResourcesFx extends AbstractAppResourcesFx {

//  @Override
//  public ImageIcon getSmallIconDisabled() {
//    return null;
//  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[2];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("Stopwatch_32x32.png"));
      applicationImages[1] = new Image(getClass().getResourceAsStream("Stopwatch_128x128.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }

}
