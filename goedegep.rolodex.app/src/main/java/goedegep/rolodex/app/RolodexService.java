package goedegep.rolodex.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.properties.app.PropertiesHandler;
import goedegep.rolodex.app.guifx.AdminResourcesFx;
import goedegep.rolodex.app.guifx.RolodexMenuWindow;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.RunningInEclipse;
import goedegep.util.emf.EMFResource;
import javafx.scene.paint.Color;

/**
 * The RolodexService class is a singleton which provides access to the Rolodex application functionality.
 * <p>
 * The class reads the configuration and property descriptor files, creates the Rolodex resource and provides access to the main menu window.
 *
 */
public class RolodexService extends Service {
  
  /**
   * The singleton instance of the RolodexService class.
   */
  private static RolodexService instance = null;

  /**
   * Get the singleton instance of the RolodexService class.
   * 
   * @return the singleton instance of the RolodexService class
   */
  public static RolodexService getInstance() {
    if (instance == null) {
      instance = new RolodexService();
      instance.initialize();
    }
    
    return instance;
  }
  
  /**
   * Show the main menu window.
   */
  public void showMenuWindow() {
    RolodexMenuWindow menuWindow = new RolodexMenuWindow(customization);
    menuWindow.show();
  }
  
  /**
   * Get the Rolodex.
   * 
   * @return the Rolodex
   */
  public Rolodex getRolodex() {
    return RolodexRegistry.rolodexResource.getEObject();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private RolodexService() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      RolodexRegistry.developmentMode = true;
    }

    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(RolodexRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

    } catch (IOException e) {
      JfxApplication.reportException(null, e);
    }

    getRolodexResource();
  }


  /**
   * Get the Rolodex resource.
   * <p>
   * If the RolodexRegistry.rolodexResource is null, a new RolodexResource is created.
   * 
   * @return the existing or newly created RolodexRegistry.rolodexResource
   */
  private void getRolodexResource() {
    if (RolodexRegistry.rolodexResource == null) {
      try {
        RolodexRegistry.rolodexResource = new EMFResource<>(
            RolodexPackage.eINSTANCE,
            () -> RolodexFactory.eINSTANCE.createRolodex(), ".xmi");
        //        File rolodexFile = new File(RolodexRegistry.dataDirectory, RolodexRegistry.rolodexFile);
        File rolodexFile = new File(RolodexRegistry.rolodexFile);
        RolodexRegistry.rolodexResource.load(rolodexFile.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }

  }

  @Override
  protected void setDevelopmentMode(boolean developmentMode) {
    RolodexRegistry.developmentMode = developmentMode;
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("RolodexApplication.properties")) {
        props.load(in);
        
        RolodexRegistry.version = props.getProperty("rolodex.app.version");
        RolodexRegistry.applicationName = props.getProperty("rolodex.app.name");
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(238,238,238));
    look.setButtonBackgroundColor(Color.rgb(238,238,238));
    look.setPanelBackgroundColor(Color.rgb(238,238,238));
    look.setListBackgroundColor(Color.rgb(238,238,238));
    look.setLabelBackgroundColor(Color.rgb(238,238,238));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new AdminResourcesFx();
  }
}
