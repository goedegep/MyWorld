package goedegep.demo.exe;


/*
 * This class is the entyry point for the Demo executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class DemoWrapper {
  public static void main(String[] args) {
    
    DemoApplication.main(args);
  }
}
