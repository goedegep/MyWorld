package goedegep.myworld.common;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.util.RunningInEclipse;
import javafx.scene.paint.Color;

public abstract class Service {
  protected CustomizationFx customization;
  
  public void initialize() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    setDevelopmentMode(RunningInEclipse.runningInEclipse());
    
    readApplicationProperties();
    
    ConfigurationFactory configurationFactory = ConfigurationFactory.eINSTANCE;
    Look look = configurationFactory.createLook();
    fillLook(look);
    AppResourcesFx resources = getAppResourcesFxClass();
    customization = new CustomizationFx(look, resources);
  }

  /**
   * Set the development mode of the application.
   * <p>
   * The application can use this information to add functionality which is for development only.
   * 
   * @param developmentMode if true, the application is in development mode.
   */
  protected abstract void setDevelopmentMode(boolean developmentMode);

  /**
   * Read the application specific properties, which are read from a properties file.
   */
  protected abstract void readApplicationProperties();

  /**
   * Fill the look object with application specific look information.
   * 
   * @param look the look object to fill.
   */
//  protected abstract void fillLook(Look look);
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(238,238,238));
    look.setButtonBackgroundColor(Color.rgb(238,238,238));
    look.setPanelBackgroundColor(Color.rgb(238,238,238));
    look.setListBackgroundColor(Color.rgb(238,238,238));
    look.setLabelBackgroundColor(Color.rgb(238,238,238));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  /**
   * Get the application resources class.
   * 
   * @return the application resources class.
   */
  protected abstract AppResourcesFx getAppResourcesFxClass();
//  protected AppResourcesFx getAppResourcesFxClass() {
//    return new DefaultAppResourcesFx();
//  }
}
