package goedegep.myworld.common;

import goedegep.util.RunningInEclipse;

public abstract class Service {
  public void initialize() {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    setDevelopmentMode(RunningInEclipse.runningInEclipse());
    
    readApplicationProperties();
  }

  /**
   * Set the development mode of the application.
   * <p>
   * The application can use this information to add functionality which is for development only.
   * 
   * @param developmentMode if true, the application is in development mode.
   */
  protected abstract void setDevelopmentMode(boolean developmentMode);

  /**
   * Read the application specific properties, which are read from a properties file.
   */
  protected abstract void readApplicationProperties();
//  protected  void readApplicationProperties() {}

}
