package goedegep.travels.inst;

/**
 * This class is a wrapper around the TravelsApplicationLauncher.
 * <p>
 * It is needed because there seems to be a problem with starting a JavaFX application directly as the main class.
 */
public class TravelsWrapper {
  
  /**
   * Main method to start the Travels application.
   * <p>
   * This method just calls {@link TravelsApplicationLauncher.main}.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    TravelsApplicationLauncher.main(args);
  }

}
