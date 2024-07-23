package goedegep.appgen.swing;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DefaultAppResources extends AbstractAppResources {
  private static DefaultAppResources defaultAppResources = null;
  
//  private static ImageIcon smallIcon = null;

  public static AppResources getInstance() {
    if (defaultAppResources == null) {
      defaultAppResources = new DefaultAppResources();
    }
    
    return defaultAppResources;
  }
  
//  @Override
//  public ImageIcon getSmallIcon() {
//    if (smallIcon == null) {
//      smallIcon = new ImageIcon(goedegep.app.gen.DefaultAppResources.class.getResource("java_icon.gif"));
//      if (smallIcon == null) {
//        System.out.println("No");
//      }
////      JFrame frame = new JFrame();
////      Image image = frame.getIconImage();
////      smallIcon = new ImageIcon(image);
//    }
//    
//    return smallIcon;
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
      applicationImages[0] = ImageIO.read(goedegep.appgen.swing.DefaultAppResources.class.getResource("java_icon.gif"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}
