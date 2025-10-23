package goedegep.pctools.app.logic;

import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

/**
 * This registry class provides information to be shared within the complete PCTools application.
 */
public class PCToolsRegistry {

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
  public static String propertyDescriptorsFile = "PCToolsPropertyDescriptors.xmi";

  /**
   * Name of the file with custom properties.
   * <p>
   * This file shall be located in the "MyWorld" folder under the user's home directory.
   */
  public static String customPropertiesFile = "VacationsUserPreferences.xmi";
  
  /**
   * Directory where all data files are stored.
   */
  public static String dataDirectory = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "My computer tools";
  
  /**
   * Current software version.
   */
  public static String version = null;
  
  /**
   * For extra functionality during development.
   */
  public static boolean developmentMode = false;
  
  /**
   * Default file for the disc structure specification.
   */
  public static String defaultDiscStructureSpecificationFile = null;
  
  /**
   * EMFResource for the Property Descriptors.
   */
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
}