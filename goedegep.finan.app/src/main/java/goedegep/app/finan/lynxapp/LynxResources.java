package goedegep.app.finan.lynxapp;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.AbstractAppResources;

public class LynxResources extends AbstractAppResources {
  private static ImageIcon  lynxLogoDisabledIcon = null;


  public ImageIcon getSmallIconDisabled() {
    if (lynxLogoDisabledIcon == null) {
      lynxLogoDisabledIcon = new ImageIcon(LynxResources.class.getResource("lynx_logo_disabled_17x17.png"));
    }

    return lynxLogoDisabledIcon;
  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageIO.read(LynxResources.class.getResource("lynx_logo_17x17.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}