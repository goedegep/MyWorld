package goedegep.app.finan.abnamrobank.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class AbnAmroBankResourcesFx extends AbstractAppResourcesFx {
//  private static ImageIcon  abnAmroLogoDisabledIcon = null;
//
//  public ImageIcon getSmallIconDisabled() {
//    if (abnAmroLogoDisabledIcon == null) {
//      abnAmroLogoDisabledIcon = new ImageIcon(AbnAmroBankResources.class.getResource("abn_amro_logo_disabled_17x17.png"));
//    }
//
//    return abnAmroLogoDisabledIcon;
//  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("abn_amro_logo_17x17.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}