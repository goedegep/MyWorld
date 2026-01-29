package goedegep.finan.exe;

/*
 * This class is the entyry point for the Finan executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class FinanWrapper {
  public static void main(String[] args) {
    
    FinanApplication.main(args);
  }
}
