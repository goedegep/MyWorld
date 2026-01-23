package goedegep.myworld.common;

import java.io.File;

public class MyWorldUtil {
  private static final String LOG_SUBFOLDER = "MyWorld";
  
  /**
   * Creates the base name (to use as parameter for {@link goedegep.jfx.JfxApplication.logSetup()}) for the log file for the given application.
   * 
   * @param applicationName the name of the application
   * @return the base name for the log file
   */
  public static String createLogFileBaseName(String applicationName) {
    return System.getProperty("user.home") + File.separator + LOG_SUBFOLDER + File.separator + applicationName + File.separator + applicationName + "_logfile";
  }
}
