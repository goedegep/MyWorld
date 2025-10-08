package goedegep.rolodex.exe;


/*
 * This class is the entyry point for the Rolodex executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class RolodexWrapper {
  public static void main(String[] args) {
    
    RolodexApplication.main(args);
  }
}
