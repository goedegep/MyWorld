package goedegep.unitconverter.app;

import java.net.URL;

import goedegep.properties.app.PropertyFileURLProvider;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

public class UnitConverterRegistry implements PropertyFileURLProvider {
  
  private static final String UNIT_CONVERTER_PROPERTY_DESCRIPTORS_FILE = "UnitConverterPropertyDescriptors.xmi";
  private static final String UNIT_CONVERTER_CONFIGURATION_FILE = "UnitConverterConfiguration.xmi";
  
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
  public static String propertyDescriptorsFile = "UnitConverterPropertyDescriptors.xmi";
  
  /**
   * Name of the file with custom properties.
   * <p>
   * This file shall be located in the "MyWorld" folder under the user's home directory.
   */
  public static String customPropertiesFile = "UnitConverterUserPreferences.xmi";
  
  /**
   * Directory where all data files are stored.
   */
  public static String dataDirectory = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "Unit conversion application.";
  
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


  @Override
  public URL getPropertyFileURL() {
    URL url = getClass().getResource(UNIT_CONVERTER_PROPERTY_DESCRIPTORS_FILE);
    
    return url;
  }

  @Override
  public URL getCustomizationFileURL() {
    URL url = getClass().getResource(UNIT_CONVERTER_CONFIGURATION_FILE);
    
    return url;
  }
}
