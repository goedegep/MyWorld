package goedegep.finan.mortgage.svc;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.Look;
import goedegep.finan.mortgage.gui.HypotheekResourcesFx;
import goedegep.jfx.CustomizationFx;
import javafx.scene.paint.Color;

public class MortgageService {

  private CustomizationFx customization;
  
  private static MortgageService instance;
  
  public static MortgageService getInstance() {
    if (instance == null) {
      instance = new MortgageService();
    }
    return instance;
  }
  
  public CustomizationFx getCustomization() {
    return customization;
  }
  
  private MortgageService() {
    ConfigurationFactory factory = ConfigurationFactory.eINSTANCE;
    Look look = factory.createLook();    
    look.setBackgroundColor(new Color(30.0 / 255, 190.0 / 255, 255.0 / 255, 1));
    look.setButtonBackgroundColor(new Color(20.0 / 255, 150.0 / 255, 230.0 / 255, 1));
    look.setPanelBackgroundColor(new Color(30.0 / 255, 190.0 / 255, 255.0 / 255, 1));
    look.setListBackgroundColor(new Color(50.0 / 255, 210.0 / 255, 255.0 / 255, 1));
    look.setLabelBackgroundColor(new Color(30.0 / 255, 190.0 / 255, 255.0 / 255, 1));
    look.setBoxBackgroundColor(new Color(50.0 / 255, 210.0 / 255, 255.0 / 255, 1));
    look.setTextFieldBackgroundColor(new Color(60.0 / 255, 220.0 / 255, 255.0 / 255, 1));    
    
    customization = new CustomizationFx(look, new HypotheekResourcesFx());
  }
}