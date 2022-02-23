package goedegep.app.finan.stocksapp;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.AbstractAppResources;

public class StocksResources extends AbstractAppResources {
//  static ImageIcon  _stocksAppLogoIcon = null;

//  public ImageIcon getSmallIcon() {
//    if (_stocksAppLogoIcon == null) {
//      _stocksAppLogoIcon = new ImageIcon(StocksResources.class.getResource("StocksAppLogo.jpg"));
//    }
//
//    return _stocksAppLogoIcon;
//  }

  @Override
  public ImageIcon getSmallIconDisabled() {
    return null;
  }

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageIO.read(StocksResources.class.getResource("StocksAppLogo.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}
