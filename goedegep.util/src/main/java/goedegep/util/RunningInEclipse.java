package goedegep.util;

import java.io.File;

public class RunningInEclipse {

  /**
   * Caches the information on whether the application is running in Eclipse or not.
   * This can be static because it always applies to the whole application and cannot change during runtime.
   */
  private static Boolean runningInEclipse = null;

  /**
   * This method determines whether the application is running in Eclipse or not (i.e. it is an official installation).
   * <p>
   * If the current directory ends with 'target/classes' we assume we're running within eclipse.
   * So make sure to set the working directory in your run configuration to .../target/classes.
   * 
   * @return true, if the program is running within Eclipse, false otherwise.
   */
  public static boolean runningInEclipse() {
    if (runningInEclipse == null) {
      String currentDirectory = System.getProperty("user.dir");
      runningInEclipse = currentDirectory.endsWith("target" + File.separator + "classes");
    }

    return runningInEclipse;
  }

}
