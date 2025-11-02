package goedegep.myworld.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Common part of the registry of each MyWorld application.
 */
public abstract class Registry {
  
  /**
   * The name of the application.
   */
  private static String applicationName;
  
  /**
   * Current software version.
   */
  private static String version = null;
  
  /**
   * Name of the author of the application.
   */
  private static String author;
  
  /**
   * Copyright message for the application.
   */
  private static  String copyrightMessage = "Copyright (c) 2001-2025";
  
  /**
   * Short description of this application.
   */
  private static String shortProductInfo;
  
  /**
   * Name of the file with the property descriptors.
   */
  private static String propertyDescriptorsFileName;
  
  /**
   * The name of the file with user properties.
   */
  private static String userPropertiesFileName;

  /**
   * For extra functionality during development.
   */
  private static boolean developmentMode = false;

  
  /**
   * Get the name of the application.
   * 
   * @return the name of the application.
   */
  public String getApplicationName() {
    return applicationName;
  }

  /**
   * Set the name of the application.
   * 
   * @param applicationName the name of the application.
   */
  public void setApplicationName(String applicationName) {
    Registry.applicationName = applicationName;
  }
  
  /**
   * Get the current software version.
   * 
   * @return the current software version.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Set the current software version.
   * 
   * @param version the current software version.
   */
  public void setVersion(String version) {
    Registry.version = version;
  }

  /**
   * Get the name of the author of the application.
   * 
   * @return the name of the author of the application.
   */
  public String getAuthor() {
    return author;
  }
  
  /**
   * Set the name of the author of the application.
   * 
   * @param author the name of the author of the application.
   */
  public void setAuthor(String author) {
    Registry.author = author;
  }

   /**
    * Get the copyright message for the application.
    * 
    * @return the copyright message for the application.
    */
  public String getCopyrightMessage() {
    return copyrightMessage;
  }
  
  /**
   * Set the copyright message for the application.
   * 
   * @param copyrightMessage the copyright message for the application.
   */
  public void setCopyrightMessage(String copyrightMessage) {
    Registry.copyrightMessage = copyrightMessage;
  }

   /**
    * Get short description of this application.
    * 
    * @return short description of this application.
    */
  public String getShortProductInfo() {
    return shortProductInfo;
  }
  
  /**
   * Set the short description of this application.
   * 
   * @param shortProductInfo the short description of this application.
   */
  public void setShortProductInfo(String shortProductInfo) {
    Registry.shortProductInfo = shortProductInfo;
  }

  /**
   * Get the name of the file with the property descriptors.
   * 
   * @return the name of the file with the property descriptors.
   */  
  public String getPropertyDescriptorsFileName() {
    return propertyDescriptorsFileName;
  }

  /**
   * Set the name of the file with the property descriptors.
   * 
   * @param propertyDescriptorsFileName the name of the file with the property descriptors.
   */
  public void setPropertyDescriptorsFileName(String propertyDescriptorsFileName) {
    Registry.propertyDescriptorsFileName = propertyDescriptorsFileName;
  }
  
  public URI getPropertyDescriptorsFileURI() {
    URL url = getClass().getResource(propertyDescriptorsFileName);
    try {
      URI uri = url.toURI();
      return uri;
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  /**
   * Get the name of the file with user properties.
   */
  public String getUserPropertiesFileName() {
    return userPropertiesFileName;
  }

  /**
   * Set the name of the file with user properties.
   * 
   * @param userPropertiesFileName the name of the file with user properties.
   */
  public void setUserPropertiesFileName(String userPropertiesFileName) {
    Registry.userPropertiesFileName = userPropertiesFileName;
  }
  
  /**
   * Check if the application is in development mode.
   * <p>
   * The application can use this information to add functionality which is for development only.
   * 
   * @return true if the application is in development mode.
   */
  public boolean isDevelopmentMode() {
    return developmentMode;
  }

  /**
   * Set the development mode of the application.
   * <p>
   * The application can use this information to add functionality which is for development only.
   * 
   * @param developmentMode if true, the application is in development mode.
   */
  public void setDevelopmentMode(boolean developmentMode) {
    Registry.developmentMode = developmentMode;
  }
  
  /**
   * Set a value in the registry by name.
   * 
   * @param name the name of the value.
   * @param value the value to set.
   */
  public boolean setValue(String name, String value) {
    boolean known = true;
    switch (name) {
      case "applicationName" -> applicationName = value;
      case "version" -> version = value;
      case "userPropertiesFile" -> userPropertiesFileName = value;
      default -> known = false;
      
    }
    return known;

  }
}
