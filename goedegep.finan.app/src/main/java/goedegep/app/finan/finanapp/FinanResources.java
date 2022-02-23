package goedegep.app.finan.finanapp;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.AbstractAppResources;

/**
 */
public class FinanResources extends AbstractAppResources {

  @Override
  public ImageIcon getSmallIconDisabled() {
    return null;
  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageIO.read(goedegep.app.finan.gen.GenResources.class.getResource("FinanLogo.jpg"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}
