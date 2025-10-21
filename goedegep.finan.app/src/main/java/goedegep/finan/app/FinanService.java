package goedegep.finan.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import goedegep.app.finan.finanapp.FinanMainWindow;
import goedegep.app.finan.finanapp.FinanResources;
import goedegep.app.finan.guifx.FinanMenuWindow;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.swing.Customization;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Service;
import goedegep.properties.app.PropertiesHandler;
import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.rolodex.app.RolodexService;
import goedegep.util.RunningInEclipse;
import javafx.stage.Stage;

public class FinanService extends Service implements PropertyFileURLProvider {
  
  private static final String FINAN_PROPERTY_DESCRIPTORS_FILE = "FinanPropertyDescriptors.xmi";
  private static final String FINAN_CONFIGURATION_FILE = "FinanConfiguration.xmi";

  private Customization customization;
  private CustomizationFx customizationFx;
  private static FinanService instance = null;
  
  public static FinanService getInstance() {
    if (instance == null) {
      instance = new FinanService();
      instance.initialize();
    }
    return instance;
  }
  
  public void showFinanMenuWindow() {
    Stage stage = new FinanMenuWindow();
    stage.centerOnScreen();
    stage.show();
  }
  
  public void showFinanMainWindow() {
    new FinanMainWindow(customization, false, false, RolodexService.getInstance().getRolodex());
  }
  

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(FINAN_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }
  
  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(FINAN_CONFIGURATION_FILE);
    
    return url;
  }

  public CustomizationFx getCustomizationFx() {
    return customizationFx;
  }

  public Customization getCustomization() {
    return customization;
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

      // Read the customization info.
      url = getClass().getResource(FINAN_CONFIGURATION_FILE);
      customizationFx = CustomizationsFx.readCustomization(url);
    } catch (IOException e) {
      JfxApplication.reportException(null, e);
    }
    
    customization = new Customization(new FinanResources());    
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
}
