package goedegep.app.finan.abnamrobank;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.AbstractAppResources;

public class AbnAmroBankResources extends AbstractAppResources {
  private static ImageIcon  abnAmroLogoDisabledIcon = null;

  public ImageIcon getSmallIconDisabled() {
    if (abnAmroLogoDisabledIcon == null) {
      abnAmroLogoDisabledIcon = new ImageIcon(AbnAmroBankResources.class.getResource("abn_amro_logo_disabled_17x17.png"));
    }

    return abnAmroLogoDisabledIcon;
  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageIO.read(AbnAmroBankResources.class.getResource("abn_amro_logo_17x17.png"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}