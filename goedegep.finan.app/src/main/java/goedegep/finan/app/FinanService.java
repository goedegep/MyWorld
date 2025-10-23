package goedegep.finan.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.app.finan.finanapp.FinanMainWindow;
import goedegep.app.finan.finanapp.FinanResources;
import goedegep.app.finan.finanapp.guifx.FinanResourcesFx;
import goedegep.app.finan.guifx.FinanMenuWindow;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.swing.Customization;
import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.properties.app.PropertiesHandler;
import goedegep.rolodex.app.RolodexService;
import goedegep.util.RunningInEclipse;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FinanService extends Service {

  /**
   * The 'old' Swing GUI customization.
   */
  private Customization swingCustomization;
  
  /**
   * The singleton instance of the FinanService.
   */
  private static FinanService instance = null;
  
  /**
   * Get the singleton instance of the FinanService.
   * 
   * @return the singleton instance of FinanService.
   */
  public static FinanService getInstance() {
    if (instance == null) {
      instance = new FinanService();
      instance.initialize();
    }
    return instance;
  }
  
  /**
   * Show the Finan menu window.
   */
  public void showFinanMenuWindow() {
    Stage stage = new FinanMenuWindow();
    stage.centerOnScreen();
    stage.show();
  }
  
  /**
   * Show the Finan main window.
   */
  public void showFinanMainWindow() {
    new FinanMainWindow(swingCustomization, false, false, RolodexService.getInstance().getRolodex());
  }
  
  public CustomizationFx getCustomizationFx() {
    return customization;
  }

  public Customization getCustomization() {
    return swingCustomization;
  }
  
  
  /**
   * Constructor.
   * <p>
   * The constructor is private, so the class can only be instantiated from within, using the getInstance() method.
   */
  private FinanService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      FinanRegistry.developmentMode = true;
    }
    
    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(FinanRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

    } catch (IOException e) {
      JfxApplication.reportException(null, e);
    }
    
    swingCustomization = new Customization(new FinanResources());    
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("FinanApplication.properties")) {
        props.load(in);
        
        FinanRegistry.version = props.getProperty("finan.app.version");
        FinanRegistry.applicationName = props.getProperty("finan.app.name");
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    FinanRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(255,255,100));
    look.setButtonBackgroundColor(Color.rgb(100,100,0));
    look.setPanelBackgroundColor(Color.rgb(255,255,100));
    look.setListBackgroundColor(Color.rgb(220,220,100));
    look.setLabelBackgroundColor(Color.rgb(255,255,100));
    look.setBoxBackgroundColor(Color.rgb(220,220,100));
    look.setTextFieldBackgroundColor(Color.rgb(150,150,50));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new FinanResourcesFx();
  }
}
