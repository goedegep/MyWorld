package goedegep.unitconverter.svc;

import java.io.InputStream;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.unitconverter.gui.UnitConverterAppResourcesFx;
import goedegep.unitconverter.gui.UnitConverterWindow;
import goedegep.unitconverter.logic.UnitConverterRegistry;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is the main class of the Unit Converter application.
 */
public class UnitConverterService extends Service {

  /**
   * The singleton instance of the UnitConverterService.
   */
  private static UnitConverterService instance = null;
  
  private UnitConverterRegistry unitConverterRegistry = UnitConverterRegistry.getInstance();
  
  /**
   * Get the singleton instance of the UnitConverterService.
   * 
   * @return the singleton instance of UnitConverterService.
   */
  public static UnitConverterService getInstance() {
    if (instance == null) {
      instance = new UnitConverterService();
      instance.initialize();
    }
      
    return instance;
  }
  
  /**
   * Show the main window of the application.
   */
  public void showUnitConverterMenuWindow() {
    Stage stage = new UnitConverterWindow(customization);
    stage.show();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private UnitConverterService() {
    unitConverterRegistry = UnitConverterRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("UnitConverterApplication.properties")) {
        props.load(in);
        
        unitConverterRegistry.setVersion(props.getProperty("unitconverter.version"));
        unitConverterRegistry.setApplicationName(props.getProperty("unitconverter.name"));
        unitConverterRegistry.setAuthor(props.getProperty("unitconverter.author"));
        unitConverterRegistry.setCopyrightMessage(props.getProperty("unitconverter.copyright"));
        unitConverterRegistry.setShortProductInfo(props.getProperty("unitconverter.description"));

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
    return new UnitConverterAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return unitConverterRegistry;
  }
}
