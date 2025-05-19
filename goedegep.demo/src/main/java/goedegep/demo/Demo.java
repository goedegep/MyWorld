package goedegep.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.demo.guifx.DemoCustomization;
import goedegep.demo.guifx.DemoMenuWindow;
import goedegep.jfx.JfxApplication;
import javafx.stage.Stage;

public class Demo extends JfxApplication {
  private static final Logger LOGGER = Logger.getLogger(Demo.class.getName());
  
  private static final String         PROGRAM_NAME = "MyWorld";
  
  public static void main(String[] args) {
    launch();
  }
  

  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public Demo() {
    logSetup(Level.SEVERE, PROGRAM_NAME + "Logging");
    LOGGER.severe("<=>");
  }
  
  /**
   * This method is called during the JavaFx launch sequence, after the constructor is called.<br/>
   * It doesn't do anything (so it could actually be removed, but I keep it in to experiment with JavaFx).
   */
  @Override
  public void init() {
    LOGGER.severe("<=>");
  }
 

  /**
   * This method is called during the JavaFx launch sequence, after the init method.<br/>
   * This method:
   * <ul>
   * <li>
   * </li>
   * </ul>
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Stage stage = new DemoMenuWindow(DemoCustomization.getInstance());
    stage.centerOnScreen();
    stage.show();
  }
}
