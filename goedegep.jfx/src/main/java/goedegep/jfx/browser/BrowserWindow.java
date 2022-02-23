package goedegep.jfx.browser;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class BrowserWindow extends JfxStage {

  public BrowserWindow(String title, CustomizationFx customization, String url) {
    super(title, customization);
    
    Scene scene = new Scene(new Browser(url),750,500, Color.web("#666970"));
    setScene(scene);
//    getStylesheets().add("webviewsample/BrowserToolbar.css");        
    show();

  }

}
