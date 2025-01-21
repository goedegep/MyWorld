package goedegep.events.app.guifx;

import java.util.Objects;
import java.util.logging.Logger;

import goedegep.events.app.EventsService;
import goedegep.events.model.EventInfo;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanel;
import goedegep.jfx.editor.EditorTemplate;

/**
 * This class provides a window to show and edit an event (of type {@link EventInfo}).
 */
public class EventEditor extends EditorTemplate<EventInfo> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EventEditor.class.getName());
  private static final String WINDOW_TITLE = "Event editor";
  
  
  /**
   * The {@code EventsService} providing support methods.
   */
  private EventsService eventsService;
  
  /**
   * Main {@code EditPanel}.
   */
  private EventEditPanel eventEditPanel;
  
  /**
   * Factory method to obtain a new instance of an {@code EventEditor}.
   * 
   * @param customization the GUI customization.
   * @param addEventInfotMethod a method used for adding events.
   * @return a newly created {@code EventEditor}.
   */
  public static EventEditor newInstance(CustomizationFx customization, EventsService eventsService) {
    Objects.requireNonNull(eventsService, "addEventInfotMethod may not be null");
    
    EventEditor eventEditor = new EventEditor(customization, eventsService);
    eventEditor.performInitialization();
     
    return eventEditor;
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param addEventInfotMethod a method used for adding events.
   */
  private EventEditor(CustomizationFx customization, EventsService eventsService) {
    super(customization, WINDOW_TITLE, eventsService::addEvent);
    
    this.eventsService = eventsService;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add event", "Add the event to the events");
    setUpdateObjectTexts("Update", "Update the current event");
    setNewObjectTexts("New", "Clear the controls to start entering new event data");
  }

  /**
   * {@inheritDoc}
   * 
   * In this case an EventEditPanel is created and returned.
   */
  @Override
  protected EditPanel<EventInfo> getMainEditPanel() {
    eventEditPanel = EventEditPanel.newInstance(customization, eventsService);

    return eventEditPanel;
  }
  
//  @Override
//  protected void installChangeListeners() {
//    addListener((e) -> handleStatusChanged());
//  }
  
//  private void handleStatusChanged() {
//    try {
//      EventInfo eventInfo = eventEditPanel.getCurrentValue();
//      LOGGER.severe("eventInfo: " + eventInfo.toString());
//    } catch (EditorException e) {
//      e.printStackTrace();
//    }
//  }

}
