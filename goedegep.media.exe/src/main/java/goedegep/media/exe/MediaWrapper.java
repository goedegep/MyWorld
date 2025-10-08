package goedegep.media.exe;

/*
 * This class is the entyry point for the Media executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class MediaWrapper {
  public static void main(String[] args) {
    
    MediaApplication.main(args);
  }
}
