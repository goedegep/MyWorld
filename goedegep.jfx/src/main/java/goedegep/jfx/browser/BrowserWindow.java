package goedegep.jfx.browser;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * This simple browser is currently not used as it throws an exception.
 * thread "JavaFX Application Thread" java.lang.IllegalAccessError: class com.sun.media.jfxmediaimpl.NativeMediaManager (in module javafx.media) cannot access class com.sun.javafx.PlatformUtil (in module javafx.base) because module javafx.base does not export com.sun.javafx to module javafx.media
 */
public class BrowserWindow extends JfxStage {

  private BrowserWindow(String title, CustomizationFx customization, String url) {
    super(customization, title);
    
    Scene scene = new Scene(new Browser(url),750,500, Color.web("#666970"));
    setScene(scene);
//    getStylesheets().add("webviewsample/BrowserToolbar.css");        
    show();

  }

}
