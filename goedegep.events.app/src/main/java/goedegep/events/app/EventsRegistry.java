package goedegep.events.app;

import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

/**
 * This registry class provides information to be shared within the complete Events application.
 */
public class EventsRegistry {
  /**
   * Name of the author of the application.
   */
  public static String author = null;
  
  /**
   * Name of the file with Configuration data.
   */
  public static String configurationFile = null;
  
  /**
   * Copyright message for the application.
   */
  public static String copyrightMessage = null;
  
  /**
   * Name of the file with custom properties.
   */
  public static String customPropertiesFile = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = null;
  
  /**
   * Current software version.
   */
  public static String version = null;
  
  /**
   * For extra functionality during development.
   */
  public static boolean developmentMode = false;
  
  /**
   * EMFResource for the Property Descriptors.
   */
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
  /**
   * Name of the file with information about the events.
   */
  public static String eventsFileName = null;
  
  /**
   * Name of the folder with all information about events.
   */
  public static String eventsFolderName = null;

}
