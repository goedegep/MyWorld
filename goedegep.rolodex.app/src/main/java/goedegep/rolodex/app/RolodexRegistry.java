package goedegep.rolodex.app;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.rolodex.model.Rolodex;
import goedegep.util.emf.EMFResource;

/**
 * This registry class provides information to be shared within the complete Rolodex application.
 */
public class RolodexRegistry implements PropertyFileURLProvider {
  
  private static final String ROLODEX_PROPERTY_DESCRIPTORS_FILE = "RolodexPropertyDescriptors.xmi";
  private static final String ROLODEX_CONFIGURATION_FILE = "RolodexConfiguration.xmi";
  
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
  public static String propertyDescriptorsFile = "RolodexPropertyDescriptors.xmi";
  
  /**
   * Name of the file with custom properties.
   * <p>
   * This file shall be located in the "MyWorld" folder under the user's home directory.
   */
  public static String customPropertiesFile = "VacationsUserPreferences.xmi";
  
  /**
   * Name of the file with all rolodex data.
   */
  public static String rolodexFile = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "Addresses, phonenumbers and phone address books.";
  
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
  
  public static EMFResource<Rolodex> rolodexResource = null;  // TODO as the rolodex resource is part of the RolodexRegistry it doesn't have to be passed as a parameter
  

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(ROLODEX_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(ROLODEX_CONFIGURATION_FILE);
    
    return url;
  }
}
