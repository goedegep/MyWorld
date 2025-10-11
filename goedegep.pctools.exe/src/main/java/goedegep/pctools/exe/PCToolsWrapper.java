package goedegep.pctools.exe;

/*
 * This class is the entyry point for the PC Tools executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class PCToolsWrapper {
  public static void main(String[] args) {
    
    PCToolsApplication.main(args);
  }
}
