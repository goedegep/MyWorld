package goedegep.app.finan.lynxapp.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class LynxResourcesFx extends AbstractAppResourcesFx {
//  private static ImageIcon  lynxLogoDisabledIcon = null;
//
//
//  public ImageIcon getSmallIconDisabled() {
//    if (lynxLogoDisabledIcon == null) {
//      lynxLogoDisabledIcon = new ImageIcon(LynxResources.class.getResource("lynx_logo_disabled_17x17.png"));
//    }
//
//    return lynxLogoDisabledIcon;
//  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("lynx_logo_17x17.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}