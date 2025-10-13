package goedegep.finan.exe;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import goedegep.app.finan.guifx.FinanMenuWindow;
import goedegep.finan.Finan;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.rolodex.app.RolodexRegistry;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.RunningInEclipse;
import goedegep.util.emf.EMFResource;
import goedegep.util.thread.ThreadUtil;
import javafx.stage.Stage;


public class FinanApplication extends JfxApplication {
  private static final String PROGRAM_NAME = "Finan";
  private static final String LOG_SUBFOLDER = "MyWorld";
  
  public static void main(String[] args) {
    launch();
  }


  /**
   * Constructor
   * <p>
   * Called during the JavaFx launch sequence.<br/>
   * The constructor sets up the logging.
   */
  public FinanApplication() {
//    String logfileName = null;
//    if (!runningInEclipse()) {
//      logfileName = PROGRAM_NAME + "_logfile";
//    }
    
    String logFileBaseName = null;
    if (!RunningInEclipse.runningInEclipse()) {
      logFileBaseName = System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + PROGRAM_NAME + "_logfile";
    }
    logSetup(Level.SEVERE, logFileBaseName);
//    logSetup(Level.SEVERE, logfileName);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    CustomizationFx customization = DefaultCustomizationFx.getInstance();
    
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        reportException(customization, (Exception) ex);
      }
    };
    Thread javaFxApplicationThread = ThreadUtil.getThread("JavaFX Application Thread");
    javaFxApplicationThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    
    try {
      Finan finan = new Finan(getRolodexResource().getEObject());
      Stage stage = new FinanMenuWindow(finan);
      stage.centerOnScreen();
      stage.show();
    } catch (Exception ex) {
      reportException(customization, ex);
    }
    
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
