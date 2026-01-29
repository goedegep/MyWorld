package goedegep.rolodex.svc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.rolodex.gui.AdminResourcesFx;
import goedegep.rolodex.gui.RolodexMenuWindow;
import goedegep.rolodex.logic.RolodexRegistry;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
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
  
  private RolodexRegistry rolodexRegistry;
  private EMFResource<Rolodex> rolodexResource = null;
  private Rolodex rolodex = null;


  /**
   * Get the singleton instance of the RolodexService class.
   * 
   * @return the singleton instance of the RolodexService class
   */
  public static RolodexService getInstance() {
    if (instance == null) {
      instance = new RolodexService();
      instance.initialize();

      // Read the Rolodex file.
      instance.rolodexResource = new EMFResource<>(
          RolodexPackage.eINSTANCE,
          () -> RolodexFactory.eINSTANCE.createRolodex(), ".xmi");
      File rolodexFile = new File(instance.rolodexRegistry.getRolodexFile());
      try {
        instance.rolodexResource.load(rolodexFile.getAbsolutePath());
        instance.rolodex = instance.rolodexResource.getEObject();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return instance;
  }
  
  /**
   * Show the main menu window.
   */
  public void showMenuWindow() {
    RolodexMenuWindow menuWindow = new RolodexMenuWindow(customization, this);
    menuWindow.show();
  }

  /**
   * Get the Rolodex resource.
   * <p>
   * If the RolodexRegistry.rolodexResource is null, a new RolodexResource is created.
   * 
   * @return the existing or newly created RolodexRegistry.rolodexResource
   */
  public EMFResource<Rolodex> getRolodexResource() {
    return rolodexResource;
  }
  
  /**
   * Get the Rolodex.
   * 
   * @return the Rolodex
   */
  public Rolodex getRolodex() {
    return rolodex;
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private RolodexService() {
    rolodexRegistry = RolodexRegistry.getInstance();
  }

  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("RolodexApplication.properties")) {
        props.load(in);
        
        rolodexRegistry.setVersion(props.getProperty("rolodex.version"));
        rolodexRegistry.setApplicationName(props.getProperty("rolodex.name"));
        rolodexRegistry.setAuthor(props.getProperty("rolodex.author"));
        rolodexRegistry.setCopyrightMessage(props.getProperty("rolodex.copyright"));
        rolodexRegistry.setShortProductInfo(props.getProperty("rolodex.description"));        
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
  
  @Override
  protected Registry getRegistry() {
    return rolodexRegistry;
  }
}
