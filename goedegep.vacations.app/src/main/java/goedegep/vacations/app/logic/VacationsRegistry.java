package goedegep.vacations.app.logic;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

/**
 * This registry class provides information to be shared within the complete Vacations application.
 */
public class VacationsRegistry implements PropertyFileURLProvider {
  
  private static final String VACATIONS_PROPERTY_DESCRIPTORS_FILE = "VacationsPropertyDescriptors.xmi";
  private static final String VACATIONS_CONFIGURATION_FILE = "VacationsConfiguration.xmi";
  
  /**
   * The name of the application.
   */
  public static String applicationName;
  
  /**
   * Name of the author of the application.
   */
  public static String author = "Peter Goedegebure";
  
  /**
   * Name of the file with Configuration data.
   */
  public static String configurationFile = null;
  
  /**
   * Copyright message for the application.
   */
  public static String copyrightMessage = "Copyright (c) 2001-2025";
  
  /**
   * Name of the file with the property descriptors.
   */
  public static String propertyDescriptorsFile = "VacationsPropertyDescriptors.xmi";
  
  /**
   * Name of the file with custom properties.
   * <p>
   * This file shall be located in the "MyWorld" folder under the user's home directory.
   */
  public static String customPropertiesFile = "VacationsUserPreferences.xmi";
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "Travels - Information about travels, like vacations, trips, etc.";
  
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
   * Name of the file with information about the vacations.
   */
  public static String vacationsFileName = null;
  
  /**
   * Name of the file with the vacation checklist information.
   */
  public static String vacationChecklistFileName = null;
  
  /**
   * Name of the folder with all information about vacations.
   */
  public static String vacationsFolderName = null;
  
  /**
   * Name of the folder with all vacation pictures.
   */
  public static String vacationPicturesFolderName = null;
  
  /**
   * List of known files in the vacations folder.
   */
  public static String knownFiles = null;
  
  /**
   * If true coordinates shall be shown in the document
   */
  public static boolean showCoordinatesInDocument = true;
  

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(VACATIONS_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }
  

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(VACATIONS_CONFIGURATION_FILE);
    
    return url;
  }
  
  public URL getURLForFileName(String fileName) {
    URL url = getClass().getResource(fileName);
    
    return url;
  }
}