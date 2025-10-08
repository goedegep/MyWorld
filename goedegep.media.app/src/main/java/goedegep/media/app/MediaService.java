package goedegep.media.app;

import goedegep.jfx.CustomizationsFx;
//import goedegep.media.app.guifx.MediaMenuWindow;
import javafx.stage.Stage;

public class MediaService {
  private static MediaService instance = null;
  
  public static MediaService getInstance() {
    if (instance == null) {
      instance = new MediaService();
    }
    return instance;
  }
  
  public void showMediaMenuWindow() {
//    Stage stage = new MediaMenuWindow(customization);
//    stage.show();
    
  }
  
}
