package goedegep.events.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EMFResource;

public class EventsService {
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  /**
   * the {@link EMFResource} for loading and storing the events database.
   */
  private EMFResource<Events> eventsResource;
  
  /**
   * The events
   */
  private Events events;

  /**
   * Constructor.
   * 
   * @param eventsResource the {@link EMFResource} for loading and storing the events database.

   */
  public EventsService(EMFResource<Events> eventsResource) {
    this.eventsResource = eventsResource;
    events = eventsResource.getEObject();
  }

  /**
   * Get the events resource.
   * 
   * @return the {@link EMFResource} for loading and storing the events database.
   */
  public EMFResource<Events> getEventsResource() {
    return eventsResource;
  }
  
  /**
   * Add an event to the events
   * <p>
   * The events are kept ordered by date.
   */
  public void addEvent(EventInfo eventInfo) {
    List<EventInfo> eventList = events.getEvents();
    
    FlexDate newEventDate = eventInfo.getDate();
    boolean eventInserted = false;
    for (int index = 0; index < eventList.size(); index++) {
      EventInfo event = eventList.get(index);
      if (newEventDate.compareTo(event.getDate()) == 1) {
        eventList.add(index, eventInfo);
        eventInserted = true;
        break;
      }
    }
    if (!eventInserted) {
      eventList.add(eventInfo);
    }
  }


  /**
   * Save the events information to the related file.
   */
  public Result saveEvents() {
    Result result = new Result();
    
    try {
      eventsResource.save();
      result.setResultType(ResultType.OK);
      result.setMessage("Events saved to '" + eventsResource.getFileName() + "'");
    } catch (IOException e) {
      result.setResultType(ResultType.FAILED);
      result.setMessage("System error message: "  + e.getMessage());
    }
    
    return result;
  }

  
  /**
   * Get the name of the folder where the attachments of an event are stored.
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   * 
   * @param event the {@code EventInfo} for which the folder name is requested.
   * @return the name of the folder where the attachments of a {@code event} are stored, or null if this cannot be determined.
   */
  public static String determineEventFolderName(EventInfo event) {
    String eventFolderName = deriveEventFolderNameFromAttachments(event);
    
    if (eventFolderName == null) {
      eventFolderName = deriveEventFolderNameFromDateAndTitle(event);
    }
    
    return eventFolderName;
  }
  
  /**
   * Get the event folder name derived from the attachments.
   * <p>
   * The event folder is the folder of the first attachment, which is a sub folder of the Events Folder.
   * 
   * @param event the {@code EventInfo} for which the folder name is requested.
   * @return the event folder name derived from the attachments, or null if this cannot be determined.
   */
  public static String deriveEventFolderNameFromAttachments(EventInfo event) {
    if (EventsRegistry.eventsFolderName == null) {
      return null;
    }
    
    for (FileReference fileReference: event.getAttachments()) {      
      String fileName = fileReference.getFile();
      if (fileName == null) {
        continue;
      }
      
      File file = new File(EventsRegistry.eventsFolderName, fileName);
      if (file != null ) {
        file = file.getParentFile();  // This is the possible event directory
        if (file != null) {
          File eventsFolder = file.getParentFile();
          String eventsFolderName = eventsFolder.getAbsolutePath();
          if (eventsFolderName != null  &&  eventsFolderName.equals(EventsRegistry.eventsFolderName)) {
            String eventFolderName = file.getAbsolutePath();
            return eventFolderName;
          }
        }
      }
    }
    
    return null;
  }
  
  /**
   * Derive the event folder from the date and title controls.
   * 
   * @param event the {@code EventInfo} for which the folder name is requested.
   * @return the event folder derived from the date and title controls, or null if these controls don't have the needed information.
   */
  public static String deriveEventFolderNameFromDateAndTitle(EventInfo event) {
    if (EventsRegistry.eventsFolderName == null) {
      return null;
    }
    
    FlexDate eventDate = event.getDate();
    if (eventDate == null) {
      return null;
    }
    
    String eventDateText = FDF.format(eventDate);
    
    String title = event.getTitle();
    if (title == null) {
      return null;
    }
    
    String subFolderName = eventDateText + " " + title;
    File eventFolder = new File(EventsRegistry.eventsFolderName, subFolderName);
    
    return eventFolder.getAbsolutePath();
  }
  
}
