package goedegep.invandprop.app;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class InvoicesAndPropertiesRegistry implements PropertyFileURLProvider {
  
  private static final String INV_AND_PROP_PROPERTY_DESCRIPTORS_FILE = "InvoicesAndPropertiesPropertyDescriptors.xmi";
  private static final String INV_AND_PROP_CONFIGURATION_FILE = "InvoicesAndPropertiesConfiguration.xmi";
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
  public static String propertyDescriptorsFile = "InvoicesAndPropertiesPropertyDescriptors.xmi";
  
  
  /**
   * Name of the file with custom properties.
   * <p>
   * This file shall be located in the "MyWorld" folder under the user's home directory.
   */
  public static String customPropertiesFile = "VacationsUserPreferences.xmi";
  
  /**
   * Directory with files related to the properties (documents and pictures).
   */
  public static String propertyRelatedFilesFolder = null;
  
  /**
   * Name of the file with all invoices and properties.
   */
  public static String invoicesAndPropertiesFile = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "A database like application to store information about Invoices and Properties.";
  
  /**
   * Current software version.
   */
  public static String version = null;
  
  /**
   * For extra functionality during development.
   */
  public static boolean developmentMode = false;
  
  public static String projectPath = null;                   // Used in development mode to find e.g. the customPropertiesFile.
  
  /**
   * EMFResource for the Property Descriptors.
   */
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  

  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(INV_AND_PROP_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(INV_AND_PROP_CONFIGURATION_FILE);
    
    return url;
  }
}
