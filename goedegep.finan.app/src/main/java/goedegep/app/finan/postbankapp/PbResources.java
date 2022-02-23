package goedegep.app.finan.postbankapp;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.AbstractAppResources;


// TODO Creeer resources per rekening.
public class PbResources extends AbstractAppResources {
  private static ImageIcon  postbankBelGiroIcon = null;
  private static ImageIcon  postbankLogoIcon = null;
  private static ImageIcon  postbankLogoDisabledIcon = null;
  private static ImageIcon  postbankEffRekIcon = null;
  private static ImageIcon  postbankEffRekVModelIcon = null;

  public ImageIcon getSmallIcon() {
    if (postbankBelGiroIcon == null) {
      postbankBelGiroIcon = new ImageIcon(PbResources.class.getResource("PbBelGiro.gif"));
    }

    return postbankBelGiroIcon;
  }

  static public ImageIcon getPostbankLogoIcon() {
    if (postbankLogoIcon == null) {
      postbankLogoIcon = new ImageIcon(PbResources.class.getResource("Postbank_logo.gif"));
    }

    return postbankLogoIcon;
  }

 public ImageIcon getSmallIconDisabled() {
    if (postbankLogoDisabledIcon == null) {
      postbankLogoDisabledIcon = new ImageIcon(PbResources.class.getResource("PbEffRekVModel.gif"));
    }

    return postbankLogoDisabledIcon;
  }

  static public ImageIcon getPostbankEffRekIcon() {
    if (postbankEffRekIcon == null) {
      postbankEffRekIcon = new ImageIcon(PbResources.class.getResource("PbEffRek.gif"));
    }

    return postbankEffRekIcon;
  }

  static public ImageIcon getPostbankEffRekVModelIcon() {
    if (postbankEffRekVModelIcon == null) {
      postbankEffRekVModelIcon = new ImageIcon(PbResources.class.getResource("Postbank_logo_disabled.gif"));
    }

    return postbankEffRekVModelIcon;
  }


  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageIO.read(PbResources.class.getResource("Postbank_logo.gif"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}
