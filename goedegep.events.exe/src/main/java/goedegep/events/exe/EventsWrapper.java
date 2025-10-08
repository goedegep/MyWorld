package goedegep.events.exe;


/*
 * This class is the entyry point for the Events executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class EventsWrapper {
  public static void main(String[] args) {
    
    EventsApplication.main(args);
  }
}
