package goedegep.app.finan.direktbankapp.guifx;

import java.util.logging.Logger;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class DirektbankResourcesFx extends AbstractAppResourcesFx {
  private static final Logger LOGGER = Logger.getLogger(DirektbankResourcesFx.class.getName());
//  private static ImageIcon  direktbankLogoDisabledIcon = null;
//
//  public ImageIcon getSmallIconDisabled() {
//    if (direktbankLogoDisabledIcon == null) {
//      direktbankLogoDisabledIcon = new ImageIcon(DirektbankResources.class.getResource("direktbank_logo_disabled_17x17.png"));
//    }
//
//    return direktbankLogoDisabledIcon;
//  }

  @Override
  protected void readResources() {
    LOGGER.info("Reading Direktbank resources");
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("direktbank_logo_17x17.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}