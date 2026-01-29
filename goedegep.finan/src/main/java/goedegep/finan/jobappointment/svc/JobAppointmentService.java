package goedegep.finan.jobappointment.svc;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.Look;
import goedegep.finan.jobappointment.gui.JobAppointmentResources;
import goedegep.jfx.CustomizationFx;
import javafx.scene.paint.Color;

public class JobAppointmentService {

  private CustomizationFx customization;
  
  private static JobAppointmentService instance;
  
  public static JobAppointmentService getInstance() {
    if (instance == null) {
      instance = new JobAppointmentService();
    }
    return instance;
  }
  
  public CustomizationFx getCustomization() {
    return customization;
  }
  
  private JobAppointmentService() {
    ConfigurationFactory factory = ConfigurationFactory.eINSTANCE;
    Look look = factory.createLook();    
    look.setBackgroundColor(new Color(255.0 / 255, 255.0 / 255, 100.0 / 255, 1));
    look.setButtonBackgroundColor(new Color(100.0 / 255, 100.0 / 255, 0.0 / 255, 1));
    look.setPanelBackgroundColor(new Color(255.0 / 255, 255.0 / 255, 100.0 / 255, 1));
    look.setListBackgroundColor(new Color(220.0 / 255, 220.0 / 255, 100.0 / 255, 1));
    look.setLabelBackgroundColor(new Color(255.0 / 255, 255.0 / 255, 100.0 / 255, 1));
    look.setBoxBackgroundColor(new Color(220.0 / 255, 220.0 / 255, 100.0 / 255, 1));
    look.setTextFieldBackgroundColor(new Color(150.0 / 255, 150.0 / 255, 50.0 / 255, 1));    
    
    customization = new CustomizationFx(look, new JobAppointmentResources());
  }
}
