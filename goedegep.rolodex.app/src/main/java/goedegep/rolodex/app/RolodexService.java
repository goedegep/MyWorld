package goedegep.rolodex.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.properties.app.PropertiesHandler;
import goedegep.rolodex.app.guifx.RolodexMenuWindow;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.emf.EMFResource;

public class RolodexService {
  
  private static final String VACATIONS_CONFIGURATION_FILE = "RolodexConfiguration.xmi";
  
  private static RolodexService instance = null;
  
  private static CustomizationFx customization; 

  public static RolodexService getInstance() {
    if (instance == null) {
      instance = new RolodexService();
    }
    
    return instance;
  }
  
  public void showMenuWindow() {
    RolodexMenuWindow menuWindow = new RolodexMenuWindow(customization);
    menuWindow.show();
  }
  
  private RolodexService() {

    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(RolodexRegistry.propertyDescriptorsFile);
      PropertiesHandler.handleProperties(url, null);

      // Read the customization info.
      url = getClass().getResource(VACATIONS_CONFIGURATION_FILE);
      customization = CustomizationsFx.readCustomization(url);
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
}
