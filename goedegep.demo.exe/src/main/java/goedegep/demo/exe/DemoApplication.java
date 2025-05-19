package goedegep.demo.exe;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


public class DemoApplication extends JfxApplication {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    CustomizationFx customization = DefaultCustomizationFx.getInstance();
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        componentFactory.createExceptionDialog("An exception occurred", (Exception) ex).showAndWait();
      }
    };
    Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
    javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    
    try {
      new DemoWindow();
    } catch (Exception ex) {
      componentFactory.createExceptionDialog("An exception occurred", (Exception) ex).showAndWait();
    }
    
  }
}
