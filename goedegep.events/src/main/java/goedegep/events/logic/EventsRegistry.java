package goedegep.events.logic;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete Events application.
 */
public class EventsRegistry extends Registry {
  
  /**
   * Name of the file with information about the events.
   */
  public static String eventsFileName = "D:\\Database\\Events\\Events.xmi";
  
  /**
   * Name of the folder with all information about events.
   */
  public static String eventsFolderName = "D:\\Database\\Events";
  
  /**
   * Singleton instance of the EventsRegistry.
   */
  private static EventsRegistry instance = null;

  /**
   * Get the singleton instance of the EventsRegistry.
   * 
   * @return the singleton instance of the EventsRegistry.
   */
  public static EventsRegistry getInstance() {
    if (instance == null) {
      instance = new EventsRegistry();
    }
    
    return instance;
  }

  /**
   * Get the name of the file with information about the events.
   * 
   * @return the name of the file with information about the events.
   */
  public String getEventsFileName() {
    return eventsFileName;
  }

  /*
   * Set the name of the file with information about the events.
   * 
   * @param eventsFileName the name of the file with information about the events.
   */
  public void setEventsFileName(String eventsFileName) {
    EventsRegistry.eventsFileName = eventsFileName;
  }
  
  /**
   * Get the name of the folder with all information about events.
   * 
   * @return the name of the folder with all information about events.
   */
  public String getEventsFolderName() {
    return eventsFolderName;
  }

  /*
   * Set the name of the folder with all information about events.
   * 
   * @param eventsFolderName the name of the folder with all information about events.
   */
  public void setEventsFolderName(String eventsFolderName) {
    EventsRegistry.eventsFolderName = eventsFolderName;
  }
  
  @Override
  public boolean setValue(String name, String value) {
    if (super.setValue(name, value)) {
      return true;
    }
    
    boolean known = true;
    switch (name) {
      case "eventsFileName" -> eventsFileName = value;
      case "eventsFolderName" -> eventsFolderName = value;
      default -> known = false;
      
    }
    return known;

  }
  
  /**
   * Private constructor for the singleton EventsRegistry.
   */
  private EventsRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Information about events, etc.");
    setPropertyDescriptorsFileName("EventsPropertyDescriptors.xmi");
    setUserPropertiesFileName("EventsUserPreferences.xmi");
  }
}
