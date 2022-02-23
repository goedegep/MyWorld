package goedegep.app.finan.direktbankapp;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.AbstractAppResources;

public class DirektbankResources extends AbstractAppResources {
  private static final Logger LOGGER = Logger.getLogger(DirektbankResources.class.getName());
//  private static ImageIcon  direktbankLogoIcon = null;
  private static ImageIcon  direktbankLogoDisabledIcon = null;

//  public ImageIcon getSmallIcon() {
//    if (direktbankLogoIcon == null) {
//      direktbankLogoIcon = new ImageIcon(DirektbankResources.class.getResource("direktbank_logo_17x17.png"));
//    }
//
//    return direktbankLogoIcon;
//  }

  public ImageIcon getSmallIconDisabled() {
    if (direktbankLogoDisabledIcon == null) {
      direktbankLogoDisabledIcon = new ImageIcon(DirektbankResources.class.getResource("direktbank_logo_disabled_17x17.png"));
    }

    return direktbankLogoDisabledIcon;
  }

  @Override
  protected void readResources() {
    LOGGER.info("Reading Direktbank resources");
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageIO.read(DirektbankResources.class.getResource("direktbank_logo_17x17.png"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}