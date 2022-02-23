package goedegep.app.finan.stocksapp.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class StocksResourcesFx extends AbstractAppResourcesFx {
//  @Override
//  public ImageIcon getSmallIconDisabled() {
//    return null;
//  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("StocksAppLogo.jpg"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}
