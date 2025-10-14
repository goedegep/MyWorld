package goedegep.finan.app;

import java.io.File;
import java.io.IOException;

import goedegep.app.finan.guifx.FinanMenuWindow;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.finan.Finan;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.rolodex.app.RolodexRegistry;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.RunningInEclipse;
import goedegep.util.emf.EMFResource;
import javafx.stage.Stage;

public class FinanService {
  private CustomizationFx customization;
  private static FinanService instance = null;
  private Finan finan;
  
  public static FinanService getInstance() {
    if (instance == null) {
      instance = new FinanService();
    }
    return instance;
  }
  
  public void showFinanMenuWindow() {
    Stage stage = new FinanMenuWindow(finan);
    stage.centerOnScreen();
    stage.show();
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
    
    customization = DefaultCustomizationFx.getInstance();
    
    finan = new Finan(getRolodexResource().getEObject());
  }

  
  /**
   * Get the Rolodex resource.
   * <p>
   * If the RolodexRegistry.rolodexResource is null, a new RolodexResource is created.
   * 
   * @return the existing or newly created RolodexRegistry.rolodexResource
   */
  private EMFResource<Rolodex> getRolodexResource() {
    if (RolodexRegistry.rolodexResource == null) {
      try {
        RolodexRegistry.rolodexResource = new EMFResource<>(
            RolodexPackage.eINSTANCE,
            () -> RolodexFactory.eINSTANCE.createRolodex(), ".xmi");
//        File rolodexFile = new File(RolodexRegistry.dataDirectory, RolodexRegistry.rolodexFile);
        File rolodexFile = new File("D:\\Database\\MyWorld\\Rolodex.xmi");
        RolodexRegistry.rolodexResource.load(rolodexFile.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }

    return RolodexRegistry.rolodexResource;
  }
}
