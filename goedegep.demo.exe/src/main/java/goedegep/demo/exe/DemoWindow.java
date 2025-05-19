package goedegep.demo.exe;

import goedegep.demo.guifx.DemoCustomization;
import goedegep.demo.guifx.DemoMenuWindow;
import javafx.stage.Stage;

public class DemoWindow extends Stage {
  public DemoWindow() {    
    new DemoMenuWindow(DemoCustomization.getInstance());
  }
}
