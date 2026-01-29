package goedegep.events.gui;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class EventsAppResources extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[7];
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("event - 272x187.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
  }
  

}
