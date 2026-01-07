package goedegep.finan.app;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.finan.guifx.FinanMenuWindow;
import goedegep.finan.guifx.FinanResourcesFx;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The FinanService class is the main service class for the Finan application.
 * It provides methods to show the main window and menu window, and manages
 * application-wide resources and customization.
 */
public class FinanService extends Service {

  /**
   * The singleton instance of the FinanService.
   */
  private static FinanService instance = null;
  
  private FinanRegistry finanRegistry;
  
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
    Stage stage = new FinanMenuWindow(customization);
    stage.centerOnScreen();
    stage.show();
  }
  
  /**
   * Constructor.
   * <p>
   * The constructor is private, so the class can only be instantiated from within, using the getInstance() method.
   */
  private FinanService() {
    finanRegistry = FinanRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("FinanApplication.properties")) {
        props.load(in);
        
        finanRegistry.setVersion(props.getProperty("finan.app.version"));
        finanRegistry.setApplicationName(props.getProperty("finan.app.name"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  /**
   * Get the application properties from the FinanApplication.properties file.
   * 
   * @return the application properties.
   */
  public static Properties getApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = FinanService.class.getResourceAsStream("FinanApplication.properties")) {
        props.load(in);
        
        return props;
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
    
    return null;
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
  
  @Override
  protected Registry getRegistry() {
    return finanRegistry;
  }
}
