package goedegep.invandprop.app.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class InvoicesAndPropertiesAppResourcesFx extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("InvoiceIcon.png"));
      
      picture = new Image(getClass().getResourceAsStream("Invoices and Properties background.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }

}