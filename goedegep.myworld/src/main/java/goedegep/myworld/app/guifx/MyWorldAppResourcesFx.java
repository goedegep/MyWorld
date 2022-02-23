package goedegep.myworld.app.guifx;


import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

/**
 * This class provides the resources (images) for the MyWorld component.
 *
 */
public class MyWorldAppResourcesFx extends AbstractAppResourcesFx {

  private Image emileImage = null;

  public Image getEmileImage() {
    if (emileImage == null) {
      try {
        emileImage = new Image(getClass().getResourceAsStream("EmileCursor.png"));
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return emileImage;
  }
   
  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[2];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("MyWorld_16x16.png"));
      applicationImages[1] = new Image(getClass().getResourceAsStream("MyWorld_97x97.png"));
      
      picture = new Image(getClass().getResourceAsStream("MyWorld1920x1080.jpg"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}