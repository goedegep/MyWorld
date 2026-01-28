package goedegep.finan.jobappointment.guifx;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class JobAppointmentResources extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("JobAppointmentIcon.png"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
}