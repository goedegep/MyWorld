package goedegep.myworld.common;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import goedegep.util.Tuplet;

/**
 * Common part of the registry of each MyWorld application.
 */
public abstract class Registry {
  private static final String NEWLINE = System.getProperty("line.separator");
  
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
  
  /**
   * Get the URI for the Property Descriptors File.
   * 
   * @return the URI for the Property Descriptors File.
   */
  public URI getPropertyDescriptorsFileURI() {
      File file = new File(propertyDescriptorsFileName);
      return file.toURI();
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
  
  public List<Tuplet<Field, Object>> getFields() {
    return new ArrayList<Tuplet<Field, Object>>();
  }
  
  public String toString() {

    StringBuilder buf = new StringBuilder();

    Class<?> c = getClass();
    for (Tuplet<Field, Object> tuplet: getFields()) {
      java.lang.reflect.Field f = tuplet.getObject1();
      Object value = tuplet.getObject2();
      buf.append(c.getSimpleName()).append(".").append(f.getName())
      .append("=").append(String.valueOf(value)).append(NEWLINE);
    }
    
    c = c.getSuperclass();
    for (java.lang.reflect.Field f : c.getDeclaredFields()) {
      try {
        Object value = f.get(null); // null because field is static
        buf.append(c.getSimpleName()).append(".").append(f.getName())
        .append("=").append(String.valueOf(value)).append(NEWLINE);
      } catch (Exception ex) {
        buf.append(c.getSimpleName()).append(".").append(f.getName())
        .append("=<error>").append(ex.getClass().getSimpleName()).append(NEWLINE);
      }
    }


//    while (c != null && c != Object.class) {
//      for (java.lang.reflect.Field f : c.getDeclaredFields()) {
//        try {
//          Object value = f.get(null); // null because field is static
//          buf.append(c.getSimpleName()).append(".").append(f.getName())
//          .append("=").append(String.valueOf(value)).append(NEWLINE);
//        } catch (Exception ex) {
//          buf.append(c.getSimpleName()).append(".").append(f.getName())
//          .append("=<error>").append(ex.getClass().getSimpleName()).append(NEWLINE);
//        }
//      }
//      c = c.getSuperclass();
//    }
    return buf.toString();
  }
}
